package it.prova.agenda.repository.utente;

import java.util.List;

import it.prova.agenda.model.Utente;


public interface CustomUtenteRepository {

	List<Utente> findByExample(Utente example);
}
