package it.prova.agenda.web.api.exception;

public class AgendaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AgendaNotFoundException(String message) {
		super(message);
	}
}
