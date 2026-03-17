package es.udc.paproject.backend.model.services;

import java.util.List;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.Session;

public class MovieWithSessions {

    private Movie movie;
    private List<Session> sessions;

    public MovieWithSessions(Movie movie, List<Session> sessions) {
        this.movie = movie;
        this.sessions = sessions;
    }

    public Movie getMovie() {
        return movie;
    }

    public List<Session> getSessions() {
        return sessions;
    }

}
