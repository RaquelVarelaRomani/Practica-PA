package es.udc.paproject.backend.test.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.MovieDao;
import es.udc.paproject.backend.model.entities.Room;
import es.udc.paproject.backend.model.entities.RoomDao;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.SessionDao;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CinemaService;

@SpringBootTest //cargar el contexto de la app para poder hacer pruebas integracion
@ActiveProfiles("test") //indica que use la config de bd test
@Transactional //IMPORTANTEEEE despues de cada test hace rollback para que la bd quede limpia para el siguiente
public class CinemaServiceTest {

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private SessionDao sessionDao;

    //METODOS AUX

    //crea y guarda peli
    private Movie createMovie(String title) {
        //constructor de Movie pide (titulo, resumen, duracion)
        return movieDao.save(new Movie(title, "Resumen de " + title, 120));
    }

    //crea y guarda sala de cine
    private Room createRoom(String name) {
        //constructor ddeRoom pide (nombre, capacidad)
        return roomDao.save(new Room(name, 100));
    }

    //crea y guarda sesion
    //vincula peli sala hora precio
    private Session createSession(Movie movie, Room room, LocalDateTime startTime) {
        //constructor sesion pide (pelicula, sala, hora inicio, precio)
        return sessionDao.save(new Session(movie, room, startTime, new BigDecimal("8.50")));
    }

    // TESTS FUNC-1

    @Test
    public void testFindMoviesByDate() {
        //1) ESCENARIO: Creamos una película y una sesión para un día concreto
        Movie movie = createMovie("Dune");
        Room room = createRoom("Sala 1");
        LocalDate date = LocalDate.of(2026, 3, 4);
        createSession(movie, room, date.atTime(21, 0)); // Sesión a las 21:00

        //2) ACCIÓN: Buscamos las películas de ese día a través del servicio
        Block<Movie> movieBlock = cinemaService.findMovies(date);

        //3) VERIFICACIÓN: Comprobamos que el bloque contiene nuestra película
        assertEquals(Arrays.asList(movie), movieBlock.getItems());
    }

    @Test
    public void testFindMoviesOrderedAlphabetically() {
        //1) ESCENARIO: crear pelis y sesiones
        Movie movieB = createMovie("Batman");
        Movie movieA = createMovie("Avatar");
        Room room = createRoom("Sala 1");
        LocalDate date = LocalDate.now().plusDays(1); //mañana

        createSession(movieA, room, date.atTime(18, 0));
        createSession(movieB, room, date.atTime(20, 0));

        //2) ACCION: ver cartelera
        Block<Movie> movieBlock = cinemaService.findMovies(date);

        //3) VERIFICACION: avatar tiene que ir antes por el orden alfabetico y tiene que dar dos
        assertEquals(2, movieBlock.getItems().size());
        assertEquals(movieA, movieBlock.getItems().get(0));
        assertEquals(movieB, movieBlock.getItems().get(1));
    }

    @Test
    public void testFindMoviesTodayExcludingPastSessions() {
        //1) ESCENARIO: peli con sesion pasada y peli con sesion futura
        Movie moviePast = createMovie("Peli antigua");
        Movie movieFuture = createMovie("Peli nueva");
        Room room = createRoom("Sala 1");

        LocalDateTime now = LocalDateTime.now();

        //sesion de hace 2 horas que no debe aparecer en la cartelera
        createSession(moviePast, room, now.minusHours(2));
        //sesion de dentro de 3 que si debe aparecer
        createSession(movieFuture, room, now.plusHours(3));

        //2) ACCION: buscar las pelis para hoy
        Block<Movie> movieBlock = cinemaService.findMovies(now.toLocalDate());

        //3) VERIFICACION: solo debe enseñar la que aun no fue
        assertEquals(1, movieBlock.getItems().size());
        assertEquals(movieFuture, movieBlock.getItems().get(0));
    }

    @Test
    public void testFindMoviesNoSessionsForDate() {
        //1) ESCENARIO: nada programado y no puede devolver ninguna
        LocalDate date = LocalDate.now().plusDays(5);

        //2) ACCION: ver la cartelera
        Block<Movie> movieBlock = cinemaService.findMovies(date);

        //3) VERIFICACION: que la lista este vacia
        assertTrue(movieBlock.getItems().isEmpty());
    }
}
