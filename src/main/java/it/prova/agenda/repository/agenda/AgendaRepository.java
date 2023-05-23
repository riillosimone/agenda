package it.prova.agenda.repository.agenda;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.agenda.model.Agenda;

public interface AgendaRepository extends CrudRepository<Agenda,Long>,CustomAgendaRepository{

	@Query("from Agenda a join fetch a.utente where a.id =?1")
	Agenda findSingleAgendaEager (Long id);
	
	List<Agenda> findAllByDescrizione(String descrizione);
	
	@Query("select a from Agenda a join fetch a.utente u where u.username like ?1")
	List<Agenda> findAllAgendaEagerByUsername(String username);
	
	@Query("select a from Agenda a where a.utente.username like ?1")
	List<Agenda> findAllAgendaByUsername(String username);
	
	@Query("select a from Agenda a join fetch a.utente u ")
	List<Agenda> findAllAgendaEager();
	
	Agenda findByDescrizione(String descrizione);
}
