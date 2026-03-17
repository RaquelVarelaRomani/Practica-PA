package es.udc.paproject.backend.model.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.exceptions.*;

@Service
@Transactional(readOnly = true)
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private PermissionChecker permissionChecker;

    //para func 1 ver las pelis que hay en cartelera
    @Override
    public List<MovieWithSessions> findMovies(LocalDate date) {

        //1)calculamos el rango del día (de 00:00:00 a 23:59:59)
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        //2)hoy para no mostrar las sesiones pasadas
        if (date.equals(LocalDate.now())) {
            start = LocalDateTime.now();
        }

        //3)recuperamos las pelis con sesiones en ese rango (ordenadas por título)
        List<Movie> movies = movieDao.findMoviesWithSessionsBetween(start, end);

        //4)para cada película, recuperamos sus sesiones en ese rango
        LocalDateTime finalStart = start;
        return movies.stream().map(movie -> {
            List<Session> sessions = sessionDao.findByMovieIdAndStartTimeBetweenOrderByStartTimeAsc(
                    movie.getId(), finalStart, end);
            return new MovieWithSessions(movie, sessions);
        }).collect(Collectors.toList());
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

    //para FUNC-4 realizar una compra
    @Override
    @Transactional
    public Purchase buyTickets (Long userId, Long sessionId, Integer numberTickets, String creditCardNumber) throws InstanceNotFoundException, PermissionException, SessionAlreadyStartedException, NotEnoughTicketsException {

        User user = permissionChecker.checkUser(userId);
        //Si no es un cliente del cine no puede comprar
        if (user.getRole() != User.RoleType.VIEWER) {
            throw new PermissionException();
        }

        Optional<Session> optionalSession = sessionDao.findById(sessionId);
        if (!optionalSession.isPresent()) {
            throw new InstanceNotFoundException("project.entities.session", sessionId);
        }

        Session session = optionalSession.get();
        if (!session.getStartTime().isAfter(LocalDateTime.now())) {
            throw new SessionAlreadyStartedException();
        }

        //Recuperamos las compras de la sesión
        List<Purchase> purchases = purchaseDao.findBySessionId(sessionId);
        //Calculamos las butacas disponibles
        int soldTickets = purchases.stream().mapToInt(Purchase::getNumberTickets).sum();
        int availableTickets = session.getRoom().getCapacity() - soldTickets;

        //Si se intentan comprar más entradas de las que hay
        if (availableTickets < numberTickets) {
            throw new NotEnoughTicketsException();
        }

        Purchase purchase = new Purchase(LocalDateTime.now(), numberTickets, creditCardNumber, user, session);
        return purchaseDao.save(purchase);
    }

    //para FUNC-5 ver el historial de compras del usuario
    @Override
    public Block<Purchase> findPurchases(Long userId, int page, int size) {

        Slice<Purchase> slice = purchaseDao.findByUserIdOrderByPurchaseDateDesc(userId, PageRequest.of(page, size));

        return new Block<>(slice.getContent(), slice.hasNext());
    }

    //para FUNC-6 entregar las entradas de una compra
    @Override
    @Transactional
    public void deliverTickets(Long userId, Long purchaseId, String creditCardNumber)
            throws InstanceNotFoundException, PermissionException, SessionAlreadyStartedException,
            AlreadyDeliveredException, CreditCardDoesNotMatchException {

        User user = permissionChecker.checkUser(userId);

        //Solo el taquillero puede entregar entradas
        if (user.getRole() != User.RoleType.TICKET_SELLER) {
            throw new PermissionException();
        }

        //Buscamos la compra
        Optional<Purchase> optionalPurchase = purchaseDao.findById(purchaseId);
        if (!optionalPurchase.isPresent()) {
            throw new InstanceNotFoundException("project.entities.purchase", purchaseId);
        }

        Purchase purchase = optionalPurchase.get();

        //Verificamos que la tarjeta de crédito coincida
        if (!purchase.getCreditCardNumber().equals(creditCardNumber)) {
            throw new CreditCardDoesNotMatchException();
        }

        //Verificamos que la sesión no haya empezado
        if (!purchase.getSession().getStartTime().isAfter(LocalDateTime.now())) {
            throw new SessionAlreadyStartedException();
        }

        //Verificamos que no se hayan entregado ya
        if (purchase.isDelivered()) {
            throw new AlreadyDeliveredException();
        }

        //Marcamos como entregada (dirty checking se encarga del UPDATE)
        purchase.setDelivered(true);
    }

}