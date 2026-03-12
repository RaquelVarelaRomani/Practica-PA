package es.udc.paproject.backend.model.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import es.udc.paproject.backend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

@Service
@Transactional(readOnly = true)
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private PurchaseDao purchaseDao;

    //para func 1 ver las pelis que hay en cartelera
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

    //para func 2 encontrar la peli y ver su info
    @Override
    public Movie findMovie(Long movieId) throws InstanceNotFoundException {
        //buscamos la peli por el id
        Optional<Movie> movie = movieDao.findById(movieId);

        //si no existe InstanceNotFoundException
        if (!movie.isPresent()) {
            throw new InstanceNotFoundException("project.entities.movie", movieId);
        }

        //si llega hasta aqui es porque existe y devolvemos un tipo Movie
        return movie.get();
    }

    //para FUNC-3 encontrar la sesión y ver su info
    @Override
    public SessionDetails findSession (Long sessionId) throws InstanceNotFoundException {
        //Buscamos la sesión por el id
        Optional<Session> optionalSession = sessionDao.findById(sessionId);

        //si no existe InstanceNotFoundException
        if(!optionalSession.isPresent()) {
            throw new InstanceNotFoundException("project.entities.session", sessionId);
        }

        //Recuperamos las compras de la sesión
        Session session = optionalSession.get();
        List<Purchase> purchases = purchaseDao.findBySessionId(sessionId);

        //Sumamos cuantas entradas se vendieron
        int soldTickets = purchases.stream().mapToInt(Purchase::getNumberTickets).sum();
        //Calculamos cuantas quedan
        int availableTickets = session.getRoom().getCapacity() - soldTickets;
        //Devolvemos los datos juntos
        return new SessionDetails(session, availableTickets);
    }

}