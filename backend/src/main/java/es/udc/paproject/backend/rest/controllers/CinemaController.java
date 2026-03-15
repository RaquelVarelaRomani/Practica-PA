package es.udc.paproject.backend.rest.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.exceptions.NotEnoughTicketsException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.SessionAlreadyStartedException;
import es.udc.paproject.backend.rest.dtos.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.SessionDao;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.services.SessionDetails;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CinemaService;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private SessionDao sessionDao;

    @GetMapping("/movies")
    public List<MovieSummaryDto> findMovies
            (@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//Cambie el formato a como dijeron en el enunciado de semana 4-5
        //1)determinar fecha si date es null usamos hoy
        LocalDate localDate = (date != null) ? date : LocalDate.now();

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

    @GetMapping("/movies/{id}")
    public MovieDto findMovie(@PathVariable Long id) throws InstanceNotFoundException {
        // 1) Llamamos al servicio
        Movie movie = cinemaService.findMovie(id);

        // 2) Convertimos a DTO y devolvemos
        return CinemaConversor.toMovieDto(movie);
    }

    @GetMapping("/sessions/{id}")
    public SessionDto findSession(@PathVariable Long id) throws InstanceNotFoundException {
        SessionDetails sessionDetails = cinemaService.findSession(id);
        return CinemaConversor.toSessionDto(sessionDetails);
    }

    @PostMapping("/buy")
    public PurchaseDto buyTickets(@RequestAttribute Long userId, @Validated @RequestBody TicketParamsDto params) throws InstanceNotFoundException, PermissionException, SessionAlreadyStartedException, NotEnoughTicketsException {
        Purchase purchase = cinemaService.buyTickets(userId, params.getSessionId(), params.getNumberTickets(), params.getCreditCardNumber());
        return CinemaConversor.toPurchaseDto(purchase);
    }

}