package es.udc.paproject.backend.test.model.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.entities.UserDao;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.PurchaseDao;
import es.udc.paproject.backend.model.services.*;
import es.udc.paproject.backend.model.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //cargar el contexto de la app para poder hacer pruebas integracion
@ActiveProfiles("test") //indica que use la config de bd test
@Transactional //IMPORTANTEEEE despues de cada test hace rollback para que la bd quede limpia para el siguiente
public class CinemaServiceTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PurchaseDao purchaseDao;


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

    //Crea usuario
    private User createUser(String userName) {
        User user = new User(userName, "password", "firstName", "lastName", userName + "@gmail.com", User.RoleType.VIEWER);
        return userDao.save(user);
    }

    //Crea un vendedor de tickets
    private User createTicketSeller(String userName){
        User user = new User(userName, "password", "firstName", "lastName", userName + "@gmail.com", User.RoleType.TICKET_SELLER);
        return userDao.save(user);
    }

    //Crea un compra de un usuario
    private Purchase createPurchase(User user, Session session, int numberTickets, String creditCardNumber) {
        return purchaseDao.save(new Purchase(LocalDateTime.now(), numberTickets, creditCardNumber, user, session));
    }

    // TESTS FUNC-1

    @Test
    public void testFindMoviesByDate() {
        //1) ESCENARIO: Creamos una película y una sesión para un día concreto
        Movie movie = createMovie("Dune");
        Room room = createRoom("Sala 1");
        LocalDate date = LocalDate.now().plusDays(2);
        Session session = createSession(movie, room, date.atTime(21, 0));

        //2) ACCIÓN: Buscamos las películas de ese día a través del servicio
        List<MovieWithSessions> result = cinemaService.findMovies(date);

        //3) VERIFICACIÓN: Comprobamos que contiene nuestra película con su sesión
        assertEquals(1, result.size());
        assertEquals(movie, result.get(0).getMovie());
        assertEquals(1, result.get(0).getSessions().size());
        assertEquals(session.getId(), result.get(0).getSessions().get(0).getId());
    }

    @Test
    public void testFindMoviesOrderedAlphabetically() {
        //1) ESCENARIO: crear pelis y sesiones
        Movie movieB = createMovie("Batman");
        Movie movieA = createMovie("Avatar");
        Room room = createRoom("Sala 1");
        LocalDate date = LocalDate.now().plusDays(1);

        createSession(movieA, room, date.atTime(18, 0));
        createSession(movieB, room, date.atTime(20, 0));

        //2) ACCION: ver cartelera
        List<MovieWithSessions> result = cinemaService.findMovies(date);

        //3) VERIFICACION: avatar tiene que ir antes por el orden alfabetico
        assertEquals(2, result.size());
        assertEquals(movieA, result.get(0).getMovie());
        assertEquals(movieB, result.get(1).getMovie());
    }

    @Test
    public void testFindMoviesTodayExcludingPastSessions() {
        //1) ESCENARIO: peli con sesion pasada y peli con sesion futura
        Movie moviePast = createMovie("Peli antigua");
        Movie movieFuture = createMovie("Peli nueva");
        Room room = createRoom("Sala 1");

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        //La sesión pasada no debe aparecer
        LocalDateTime pastSessionTime = now.minusHours(2);

        //La futura dentro del mismo día
        LocalDateTime futureSessionTime = today.atTime(23, 0);
        if (!futureSessionTime.isAfter(now)) {
            futureSessionTime = now.plusMinutes(30);
        }

        createSession(moviePast, room, pastSessionTime);
        createSession(movieFuture, room, futureSessionTime);

        //2) ACCION: buscar las pelis para hoy
        List<MovieWithSessions> result = cinemaService.findMovies(today);

        //3) VERIFICACION: solo debe enseñar la que aun no fue
        assertEquals(1, result.size());
        assertEquals(movieFuture, result.get(0).getMovie());
    }

    @Test
    public void testFindMoviesNoSessionsForDate() {
        //1) ESCENARIO: nada programado y no puede devolver ninguna
        LocalDate date = LocalDate.now().plusDays(5);

        //2) ACCION: ver la cartelera
        List<MovieWithSessions> result = cinemaService.findMovies(date);

        //3) VERIFICACION: que la lista este vacia
        assertTrue(result.isEmpty());
    }

    // TESTS FUNC-2

    @Test
    public void testFindMovie() throws InstanceNotFoundException {
        //1) ESCENARIO: buscar una peli existente
        Movie movie = createMovie("Dune");

        //2) ACCION: buscar la peli
        Movie foundMovie = cinemaService.findMovie(movie.getId());

        //3) VERIFICACION: que la peli que encuentra sea Dune
        assertEquals(movie, foundMovie);
        assertEquals("Dune", foundMovie.getTitle());
    }

    @Test
    public void testFindNonExistentIdMovie() {
        //1) ESCENARIO: buscar un id que no existe
        assertThrows(InstanceNotFoundException.class, () -> cinemaService.findMovie(NON_EXISTENT_ID));
    }


    //TESTS FUNC-3
    @Test
    public void testFindSessionWithoutPurchases() throws InstanceNotFoundException {
        //Sesión sin compras
        Movie movie = createMovie("Interstellar");
        Room room = createRoom("Sala 2");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        //Buscamos el detalle de la sesion
        SessionDetails sessionDetails = cinemaService.findSession(session.getId());

        assertEquals(session.getId(), sessionDetails.getSession().getId());
        assertEquals(100, sessionDetails.getAvailableTickets());
    }

    @Test
    public void testFindSessionWithPurchases() throws InstanceNotFoundException {
        //Sesión con compras ya realizadas
        Movie movie = createMovie("Blade Runner");
        Room room = createRoom("Sala 3");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        User user1 = createUser("user1");
        User user2 = createUser("user2");

        createPurchase(user1, session, 5, "1111222233334444");
        createPurchase(user2, session, 7, "5555666677778888");

        //Buscamos el detalle de la sesión
        SessionDetails sessionDetails = cinemaService.findSession(session.getId());

        //100 - (5 + 7) = 88 disponibles
        assertEquals(session.getId(), sessionDetails.getSession().getId());
        assertEquals(88, sessionDetails.getAvailableTickets());
    }

    @Test
    public void testFindSessionWithNoAvailableTickets() throws InstanceNotFoundException {
        //Sesión sin butacas disponibles
        Movie movie = createMovie("Harry Potter");
        Room room = createRoom("Sala 4");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        User user = createUser("user3");
        createPurchase(user, session, 100, "9999888877776666");

        SessionDetails sessionDetails = cinemaService.findSession(session.getId());

        assertEquals(0, sessionDetails.getAvailableTickets());
    }

    @Test
    public void testFindNonExistentSession() {
        //Buscamos una sesión que no existe
        assertThrows(InstanceNotFoundException.class,
                () -> cinemaService.findSession(NON_EXISTENT_ID));
    }

    //TESTS FUNC-4
    @Test
    public void testBuyTickets() throws Exception {
        //Compra correcta
        User user = createUser("viewer1");
        Movie movie = createMovie("Matrix");
        Room room = createRoom("Sala 5");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        Purchase purchase = cinemaService.buyTickets(user.getId(), session.getId(), 3, "1234567890123456");

        assertNotNull(purchase.getId());
        assertEquals(user.getId(), purchase.getUser().getId());
        assertEquals(session.getId(), purchase.getSession().getId());
        assertEquals(3, purchase.getNumberTickets());
        assertEquals("1234567890123456", purchase.getCreditCardNumber());
        assertFalse(purchase.isDelivered());

        SessionDetails sessionDetails = cinemaService.findSession(session.getId());
        assertEquals(97, sessionDetails.getAvailableTickets());
    }

    @Test
    public void testBuyTicketsNonExistentUser() {
        //Compra de un usuario que no existe
        Movie movie = createMovie("Los juegos del hambre");
        Room room = createRoom("Sala 6");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        assertThrows(InstanceNotFoundException.class,
                () -> cinemaService.buyTickets(NON_EXISTENT_ID, session.getId(), 2, "1234567890123456"));
    }

    @Test
    public void testBuyTicketsNonExistentSession() {
        //Intento de compra en una sesión que no existe
        User user = createUser("viewer2");

        assertThrows(InstanceNotFoundException.class,
                () -> cinemaService.buyTickets(user.getId(), NON_EXISTENT_ID, 2, "1234567890123456"));
    }

    @Test
    public void testBuyTicketsWhenSessionAlreadyStarted() {
        //Intento de compra en una sesión ya empezada
        User user = createUser("viewer3");
        Movie movie = createMovie("Divergente");
        Room room = createRoom("Sala 7");
        Session session = createSession(movie, room, LocalDateTime.now().minusMinutes(5));

        assertThrows(SessionAlreadyStartedException.class,
                () -> cinemaService.buyTickets(user.getId(), session.getId(), 2, "1234567890123456"));
    }

    @Test
    public void testBuyTicketsWhenThereAreNotEnoughTickets() {
        //No hay suficientes entradas
        User user = createUser("viewer4");
        Movie movie = createMovie("Avatar");
        Room room = createRoom("Sala 8");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        createPurchase(user, session, 99, "9999888877776666");

        assertThrows(NotEnoughTicketsException.class,
                () -> cinemaService.buyTickets(user.getId(), session.getId(), 2, "1234567890123456"));
    }

    @Test
    public void testBuyTicketsTicketSellerCannotBuy() {
        //Vendedor no puede comprar entradas
        User ticketSeller = createTicketSeller("seller1");
        Movie movie = createMovie("Expediente Warren");
        Room room = createRoom("Sala 9");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        assertThrows(PermissionException.class,
                () -> cinemaService.buyTickets(ticketSeller.getId(), session.getId(), 1, "1234567890123456"));
    }

    // TESTS FUNC-5

    @Test
    public void testFindPurchases() throws Exception {
        //Compras de un usuario con paginación (size=2)
        User user = createUser("viewer5");
        Movie movie = createMovie("Titanic");
        Room room = createRoom("Sala 10");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        Purchase purchase1 = createPurchase(user, session, 2, "1111222233334444");
        Purchase purchase2 = createPurchase(user, session, 3, "1111222233334444");
        Purchase purchase3 = createPurchase(user, session, 1, "1111222233334444");

        //Primera página (size=2): debe tener 2 compras y existMoreItems=true
        Block<Purchase> page0 = cinemaService.findPurchases(user.getId(), 0, 2);
        assertEquals(2, page0.getItems().size());
        assertTrue(page0.getExistMoreItems());

        //Segunda página: debe tener 1 compra y existMoreItems=false
        Block<Purchase> page1 = cinemaService.findPurchases(user.getId(), 1, 2);
        assertEquals(1, page1.getItems().size());
        assertFalse(page1.getExistMoreItems());
    }

    @Test
    public void testFindPurchasesNoPurchases() {
        //Usuario sin compras
        User user = createUser("viewer6");

        Block<Purchase> result = cinemaService.findPurchases(user.getId(), 0, 2);
        assertEquals(0, result.getItems().size());
        assertFalse(result.getExistMoreItems());
    }

    @Test
    public void testFindPurchasesOrderedByDateDesc() throws Exception {
        //Las compras deben venir ordenadas por fecha descendente
        User user = createUser("viewer7");
        Movie movie = createMovie("El Padrino");
        Room room = createRoom("Sala 11");
        Session session = createSession(movie, room, LocalDateTime.now().plusDays(1));

        //Creamos compras con fechas diferentes simulando orden
        Purchase older = purchaseDao.save(new Purchase(
                LocalDateTime.now().minusHours(2), 1, "1111222233334444", user, session));
        Purchase newer = purchaseDao.save(new Purchase(
                LocalDateTime.now().minusHours(1), 2, "5555666677778888", user, session));

        Block<Purchase> result = cinemaService.findPurchases(user.getId(), 0, 2);
        assertEquals(2, result.getItems().size());
        //La más reciente debe estar primera
        assertEquals(newer.getId(), result.getItems().get(0).getId());
        assertEquals(older.getId(), result.getItems().get(1).getId());
    }

}
