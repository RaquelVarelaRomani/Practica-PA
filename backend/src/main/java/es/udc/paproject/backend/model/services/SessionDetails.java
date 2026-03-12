package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Session;

public class SessionDetails {
    private Session session;
    private int availableTickets;

    public SessionDetails(Session session, int availableTickets) {
        this.session = session;
        this.availableTickets = availableTickets;
    }

    public Session getSession() {
        return session;
    }
    public int getAvailableTickets() {
        return availableTickets;
    }
}
