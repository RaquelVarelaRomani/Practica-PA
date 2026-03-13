package es.udc.paproject.backend.model.exceptions;

public class SessionAlreadyStartedException extends Exception {
    public SessionAlreadyStartedException(String message) {
        super(message);
    }
}
