package it.prova.agenda.repository;

import org.springframework.data.repository.CrudRepository;

import it.prova.agenda.model.Ruolo;


public interface RuoloRepository extends CrudRepository<Ruolo, Long> {
	Ruolo findByDescrizioneAndCodice(String descrizione, String codice);
}
