package it.prova.agenda.repository.agenda;

import java.util.List;

import it.prova.agenda.model.Agenda;

public interface CustomAgendaRepository {

	List<Agenda> findByExample(Agenda example);
}
