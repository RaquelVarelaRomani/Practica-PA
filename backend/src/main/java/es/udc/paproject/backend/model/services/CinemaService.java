package es.udc.paproject.backend.model.services;

import java.time.LocalDate;
import java.util.List;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.*;

public interface CinemaService {
    //FUNC-1 Recuperar películas de cartelera para x día
    List<MovieWithSessions> findMovies(LocalDate date);
    //FUNC-2 Recuperar información detallada de una película
    Movie findMovie(Long movieId) throws InstanceNotFoundException;

    //FUNC-3 Recuperar información detallada de una sesión
    SessionDetails findSession(Long sessionId) throws InstanceNotFoundException;
    //FUNC-4 Comprar entradas
    Purchase buyTickets(Long userId, Long sessionId, Integer numberTickets, String creditCardNumber) throws InstanceNotFoundException, PermissionException, SessionAlreadyStartedException, NotEnoughTicketsException;

    //FUNC-5 Visualizar el historico de compras
    Block<Purchase> findPurchases(Long userId, int page, int size);

    //FUNC-6 Entregar las entradas de una compra
    void deliverTickets(Long userId, Long purchaseId, String creditCardNumber)
            throws InstanceNotFoundException, PermissionException, SessionAlreadyStartedException,
            AlreadyDeliveredException, CreditCardDoesNotMatchException;
}