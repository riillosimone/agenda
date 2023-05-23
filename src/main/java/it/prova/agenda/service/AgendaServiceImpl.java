package it.prova.agenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.agenda.model.Agenda;
import it.prova.agenda.model.Utente;
import it.prova.agenda.repository.agenda.AgendaRepository;
import it.prova.agenda.web.api.exception.AgendaNotFoundException;

@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl  implements AgendaService{

	
	@Autowired
	private AgendaRepository repository;
	
	@Autowired
	private UtenteService utenteService;

	@Override
	public List<Agenda> listAllElements(boolean eager) {
		if (eager) {
			return (List<Agenda>) repository.findAllAgendaEager();
		}
		return (List<Agenda>) repository.findAll();
	}

	@Override
	public Agenda caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Agenda caricaSingoloElementoEager(Long id) {
		return repository.findSingleAgendaEager(id);
	}

	@Override
	@Transactional
	public Agenda aggiorna(Agenda agendaInstance) {
		
		return repository.save(agendaInstance);
	}

	@Override
	@Transactional
	public Agenda inserisciNuovo(Agenda agendaInstance) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		agendaInstance.setUtente(utenteLoggato);
		return repository.save(agendaInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		repository.findById(idToRemove).orElseThrow(() -> new AgendaNotFoundException("Agenda not found con id: "+idToRemove));
		repository.deleteById(idToRemove);
		
	}

	@Override
	public List<Agenda> findAllByDescrizione(String descrizione) {
		return repository.findAllByDescrizione(descrizione);
	}

	@Override
	public List<Agenda> findByExample(Agenda example) {
		return repository.findByExample(example);
	}

	@Override
	public Agenda findByDescrizione(String descrizione) {
		
		return repository.findByDescrizione(descrizione);
	}

	@Override
	public List<Agenda> listAllYourElements(String username, boolean eager) {
		if (eager) {
			return repository.findAllAgendaEagerByUsername(username);
		}
		return repository.findAllAgendaByUsername(username);
	}
	
	
}
