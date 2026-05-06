package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Session;

public class SessionDetails {
    private Session session;

    public SessionDetails(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
    public int getAvailableTickets() {
        return session.getAvailableTickets();
    }
}
