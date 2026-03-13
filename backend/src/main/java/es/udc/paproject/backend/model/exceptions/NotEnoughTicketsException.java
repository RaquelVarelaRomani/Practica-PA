package es.udc.paproject.backend.model.exceptions;

public class NotEnoughTicketsException extends Exception {
    public NotEnoughTicketsException(String message) {
        super(message);
    }
}
