package es.udc.paproject.backend.rest.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.MovieWithSessions;
import es.udc.paproject.backend.model.services.SessionDetails;
import es.udc.paproject.backend.model.services.CinemaService;


@RestController
@RequestMapping("/cinema")
public class CinemaController {

    private final static String SESSION_ALREADY_STARTED_EXCEPTION_CODE =
            "project.exceptions.SessionAlreadyStartedException";
    private final static String NOT_ENOUGH_TICKETS_EXCEPTION_CODE =
            "project.exceptions.NotEnoughTicketsException";
    private final static String ALREADY_DELIVERED_EXCEPTION_CODE =
            "project.exceptions.AlreadyDeliveredException";
    private final static String CREDIT_CARD_DOES_NOT_MATCH_EXCEPTION_CODE =
            "project.exceptions.CreditCardDoesNotMatchException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CinemaService cinemaService;

    @ExceptionHandler(SessionAlreadyStartedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleSessionAlreadyStartedException(SessionAlreadyStartedException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(SESSION_ALREADY_STARTED_EXCEPTION_CODE, null,
                SESSION_ALREADY_STARTED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(NotEnoughTicketsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleNotEnoughTicketsException(NotEnoughTicketsException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(NOT_ENOUGH_TICKETS_EXCEPTION_CODE, null,
                NOT_ENOUGH_TICKETS_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(AlreadyDeliveredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleAlreadyDeliveredException(AlreadyDeliveredException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(ALREADY_DELIVERED_EXCEPTION_CODE, null,
                ALREADY_DELIVERED_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(CreditCardDoesNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleCreditCardDoesNotMatchException(CreditCardDoesNotMatchException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(CREDIT_CARD_DOES_NOT_MATCH_EXCEPTION_CODE, null,
                CREDIT_CARD_DOES_NOT_MATCH_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);

    }

    @GetMapping("/movies")
    public List<MovieSummaryDto> findMovies(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws MaxRangeDateException {

        LocalDate localDate = (date != null) ? date : LocalDate.now();

        List<MovieWithSessions> moviesWithSessions = cinemaService.findMovies(localDate);

        return moviesWithSessions.stream()
                .map(mws -> CinemaConversor.toMovieSummaryDto(mws.getMovie(), mws.getSessions()))
                .collect(Collectors.toList());
    }

    @GetMapping("/movies/{id}")
    public MovieDto findMovie(@PathVariable Long id) throws InstanceNotFoundException {
        return CinemaConversor.toMovieDto(cinemaService.findMovie(id));
    }

    @GetMapping("/sessions/{id}")
    public SessionDto findSession(@PathVariable Long id) throws InstanceNotFoundException, SessionAlreadyStartedException {
        SessionDetails sessionDetails = cinemaService.findSession(id);
        return CinemaConversor.toSessionDto(sessionDetails);
    }

    @PostMapping("/buy")
    public PurchaseDto buyTickets(@RequestAttribute Long userId,
                                 @Validated @RequestBody TicketParamsDto params)
            throws InstanceNotFoundException,
            SessionAlreadyStartedException, NotEnoughTicketsException {

        Purchase purchase = cinemaService.buyTickets(userId, params.getSessionId(),
                params.getNumberTickets(), params.getCreditCardNumber());

        return CinemaConversor.toPurchaseDto(purchase);
    }

    @GetMapping("/purchases")
    public BlockDto<PurchaseSummaryDto> findPurchases(@RequestAttribute Long userId,
                                                     @RequestParam(defaultValue = "0") int page) {

        Block<Purchase> purchaseBlock = cinemaService.findPurchases(userId, page, 2);

        return new BlockDto<>(CinemaConversor.toPurchaseSummaryDtos(purchaseBlock.getItems()),
                purchaseBlock.getExistMoreItems());
    }

    @PostMapping("/purchases/{purchaseId}/deliver")
    public ResponseEntity<Void> deliverTickets(@RequestAttribute Long userId,
                                               @PathVariable Long purchaseId,
                                               @Validated @RequestBody DeliverParamsDto params)
            throws InstanceNotFoundException, PermissionException, SessionAlreadyStartedException,
            AlreadyDeliveredException, CreditCardDoesNotMatchException {

        cinemaService.deliverTickets(userId, purchaseId, params.getCreditCardNumber());

        return ResponseEntity.noContent().build();
    }

}
