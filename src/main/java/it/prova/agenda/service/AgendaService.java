package it.prova.agenda.service;

import java.util.List;

import it.prova.agenda.model.Agenda;

public interface AgendaService {
	
	List<Agenda> listAllElements(boolean eager);
	
	Agenda caricaSingoloElemento(Long id);
	
	Agenda caricaSingoloElementoEager(Long id);
	
	Agenda aggiorna(Agenda agendaInstance);
	
	Agenda inserisciNuovo(Agenda agendaInstance);

	void rimuovi(Long idToRemove);
	
	Agenda findByDescrizione(String descrizione);
	
	List<Agenda> findAllByDescrizione(String descrizione);
	
	List<Agenda> findByExample(Agenda example);
}
