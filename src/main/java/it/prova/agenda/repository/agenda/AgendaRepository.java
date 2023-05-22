package it.prova.agenda.repository.agenda;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.agenda.model.Agenda;

public interface AgendaRepository extends CrudRepository<Agenda,Long>,CustomAgendaRepository{

	@Query("from Agenda a join fetch a.utente where a.id =?1")
	Agenda findSingleAgendaEager (Long id);
	
	List<Agenda> findAllByDescrizione(String descrizione);
	
	@Query("select a from Agenda a join fetch a.utente")
	List<Agenda> findAllAgendaEager();
	
	Agenda findByDescrizione(String descrizione);
}
