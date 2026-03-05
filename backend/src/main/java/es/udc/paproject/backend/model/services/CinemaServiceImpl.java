package es.udc.paproject.backend.model.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.MovieDao;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.SessionDao;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

@Service
@Transactional(readOnly = true)
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private SessionDao sessionDao;

    @Override
    public Block<Movie> findMovies(LocalDate date) {

        //1)calculamos el rango del día (de 00:00:00 a 23:59:59)

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        //2)hoy para no mostrar las sesiones pasadas
        if (date.equals(LocalDate.now())) {
            start = LocalDateTime.now();
        }

        //3)recuperamos las pelis con sesiones en ese rango (ordenadas por título)
        List<Movie> movies = movieDao.findMoviesWithSessionsBetween(start, end);

        //4)devolvemos Block con películas
        return new Block<>(movies, false);
    }

}