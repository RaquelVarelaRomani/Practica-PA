package es.udc.paproject.backend.rest.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.SessionDao;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CinemaService;
import es.udc.paproject.backend.rest.dtos.CinemaConversor;
import es.udc.paproject.backend.rest.dtos.MovieSummaryDto;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private SessionDao sessionDao;

    @GetMapping("/movies")
    public List<MovieSummaryDto> findMovies(@RequestParam(required = false) String date) {

        //1)determinar fecha (hoy si es lo de por defecto)
        LocalDate localDate;
        if (date != null) {
            localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } else {
            localDate = LocalDate.now();
        }

        //2)llamar al service para obtener las peliculas de ese dia
        Block<Movie> movieBlock = cinemaService.findMovies(localDate);
        List<Movie> movies = movieBlock.getItems();

        //3)agrupar peli con sesiones para ese dia
        return movies.stream().map(movie -> {
            LocalDateTime start = localDate.atStartOfDay();
            LocalDateTime end = localDate.atTime(LocalTime.MAX);

            //si es hoy no mostramos las sesiones que ya empezaron
            if (localDate.equals(LocalDate.now())) {
                start = LocalDateTime.now();
            }

            List<Session> sessions = sessionDao.findByMovieIdAndStartTimeBetweenOrderByStartTimeAsc(
                    movie.getId(), start, end);

            return CinemaConversor.toMovieSummaryDto(movie, sessions);
        }).collect(Collectors.toList());
    }
}