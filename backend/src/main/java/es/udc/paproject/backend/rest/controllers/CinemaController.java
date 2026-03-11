package es.udc.paproject.backend.rest.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


import org.springframework.web.bind.annotation.PathVariable;
import es.udc.paproject.backend.rest.dtos.MovieDto;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

import org.springframework.format.annotation.DateTimeFormat;

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
}