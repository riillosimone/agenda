package it.prova.agenda.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.agenda.dto.AgendaDTO;
import it.prova.agenda.model.Agenda;
import it.prova.agenda.model.Utente;
import it.prova.agenda.service.AgendaService;
import it.prova.agenda.service.UtenteService;
import it.prova.agenda.web.api.exception.AgendaNotFoundException;
import it.prova.agenda.web.api.exception.IdNotNullForInsertException;
import it.prova.agenda.web.api.exception.UtenteNonAutorizzatoException;

@RestController
@RequestMapping("api/agenda")
public class AgendaController {

	@Autowired
	private AgendaService agendaService;
	
	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public List<AgendaDTO> getAll() {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		if (!utenteLoggato.isAdmin()) {
			return AgendaDTO.createAgendaDTOListFromModelList(agendaService.listAllYourElements(username, false));
		}
		
		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.listAllElements(false));
		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AgendaDTO createNew(@Valid @RequestBody AgendaDTO agendaInput) {
		if (agendaInput.getId() != null) {
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione.");
		}
		Agenda agendaInserita = agendaService.inserisciNuovo(agendaInput.buildAgendaModel());
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//		// estraggo le info dal principal
//		Utente utenteLoggato = utenteService.findByUsername(username);
//		if (!utenteLoggato.isAdmin() && agendaInserita.getUtente()!= utenteLoggato) {
//			throw new UtenteNonAutorizzatoException("Attenzione! Non hai l'autorizzazione per visualizzare questo elemento");
//		}
		return AgendaDTO.buildAgendaDTOFromModel(agendaInserita);
	}
	
	@GetMapping("/{id}")
	public AgendaDTO findById(@PathVariable(value = "id",required = true) long id) {
		Agenda agenda = agendaService.caricaSingoloElementoEager(id);
		if (agenda == null) {
			throw new AgendaNotFoundException("Agenda not found con id: "+id);
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		if (!utenteLoggato.isAdmin() && agenda.getUtente()!= utenteLoggato) {
			throw new UtenteNonAutorizzatoException("Attenzione! Non hai l'autorizzazione per visualizzare questo elemento");
		}
		return AgendaDTO.buildAgendaDTOFromModel(agenda);
	}
	
	@PutMapping("/{id}")
	public AgendaDTO update(@Valid @RequestBody AgendaDTO agendaInput, @PathVariable(value = "id",required = true) long id) {
		Agenda agenda = agendaService.caricaSingoloElementoEager(id);
		if (agenda == null) {
			throw new AgendaNotFoundException("Agenda not found con id: "+id);
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		if (!utenteLoggato.isAdmin() && agenda.getUtente()!= utenteLoggato) {
			throw new UtenteNonAutorizzatoException("Attenzione! Non hai l'autorizzazione per modificare questo elemento");
		}
		agendaInput.setId(id);
		Agenda agendaAggiornata = agendaService.aggiorna(agendaInput.buildAgendaModel());
		return AgendaDTO.buildAgendaDTOFromModel(agendaAggiornata);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete (@PathVariable(value = "id",required = true) Long id) {
		Agenda agenda = agendaService.caricaSingoloElementoEager(id);
		if (agenda == null) {
			throw new AgendaNotFoundException("Agenda not found con id: "+id);
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		if (!utenteLoggato.isAdmin() && agenda.getUtente()!= utenteLoggato) {
			throw new UtenteNonAutorizzatoException("Attenzione! Non hai l'autorizzazione per eliminare questo elemento");
		}
		agendaService.rimuovi(id);
	}
	
}
