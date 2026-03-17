package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.services.SessionDetails;

public class CinemaConversor {

    private CinemaConversor() {}

    //FUNC-1: convierte sesion a resumen
    public static SessionSummaryDto toSessionSummaryDto(Session session) {
        return new SessionSummaryDto(session.getId(), session.getStartTime());
    }

    //FUNC-1: convierte peli y sesiones a resumen cartelera
    public static MovieSummaryDto toMovieSummaryDto(Movie movie, List<Session> sessions) {
        List<SessionSummaryDto> sessionDtos = sessions.stream()
                .map(CinemaConversor::toSessionSummaryDto)
                .collect(Collectors.toList());

        return new MovieSummaryDto(movie.getId(), movie.getTitle(), sessionDtos);
    }

    //FUNC-2: convertir movie en moviedto
    public static MovieDto toMovieDto(Movie movie) {
        return new MovieDto(movie.getId(), movie.getTitle(), movie.getSummary(), movie.getDurationMinutes());
    }

    //FUNC-3: convertir session en sessionDto
    public static SessionDto toSessionDto(SessionDetails sessionDetails) {
        Session session = sessionDetails.getSession();
        return new SessionDto(
                session.getId(),
                session.getMovie().getId(),
                session.getMovie().getTitle(),
                session.getMovie().getDurationMinutes(),
                session.getPrice(),
                session.getStartTime(),
                session.getRoom().getId(),
                session.getRoom().getName(),
                sessionDetails.getAvailableTickets());
    }

    //FUNC-4: convertir purchase en purchaseDto
    public static PurchaseDto toPurchaseDto(Purchase purchase) {
        return new PurchaseDto(
                purchase.getId(),
                purchase.getPurchaseDate(),
                purchase.getNumberTickets(),
                purchase.getCreditCardNumber(),
                purchase.getUser().getId(),
                purchase.getSession().getId(),
                purchase.isDelivered()
        );
    }

    //FUNC-5: convertir lista de purchases en lista de PurchaseSummaryDto
    public static List<PurchaseSummaryDto> toPurchaseSummaryDtos(List<Purchase> purchases) {
        return purchases.stream().map(CinemaConversor::toPurchaseSummaryDto).collect(Collectors.toList());
    }

    private static PurchaseSummaryDto toPurchaseSummaryDto(Purchase purchase) {
        BigDecimal totalPrice = purchase.getSession().getPrice()
                .multiply(BigDecimal.valueOf(purchase.getNumberTickets()));

        return new PurchaseSummaryDto(
                purchase.getId(),
                purchase.getPurchaseDate(),
                purchase.getSession().getMovie().getTitle(),
                purchase.getNumberTickets(),
                totalPrice,
                purchase.getSession().getStartTime(),
                purchase.isDelivered()
        );
    }

}