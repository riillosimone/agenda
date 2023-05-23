package it.prova.agenda.web.api.exception;

public class UtenteNonAutorizzatoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UtenteNonAutorizzatoException(String message) {
		super(message);
	}
}
