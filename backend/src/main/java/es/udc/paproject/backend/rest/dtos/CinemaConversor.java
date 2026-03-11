package es.udc.paproject.backend.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.Session;

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

}