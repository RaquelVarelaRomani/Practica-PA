package es.udc.paproject.backend.model.services;

import java.time.LocalDate;
import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.NotEnoughTicketsException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.SessionAlreadyStartedException;

public interface CinemaService {
    //FUNC-1 Recuperar películas de cartelera para x día
    Block<Movie> findMovies(LocalDate date);
    //FUNC-2 Recuperar información detallada de una película
    Movie findMovie(Long movieId) throws InstanceNotFoundException;

    //FUNC-3 Recuperar información detallada de una sesión
    SessionDetails findSession(Long sessionId) throws InstanceNotFoundException;
    //FUNC-4 Comprar entradas
    Purchase buyTickets(Long userId, Long sessionId, Integer numberTickets, String creditCardNumber) throws InstanceNotFoundException, PermissionException, SessionAlreadyStartedException, NotEnoughTicketsException;
}