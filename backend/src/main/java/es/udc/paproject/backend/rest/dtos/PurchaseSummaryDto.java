package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseSummaryDto {

    private Long id;
    private LocalDateTime purchaseDate;
    private String movieTitle;
    private int numberTickets;
    private BigDecimal totalPrice;
    private LocalDateTime sessionStartTime;
    private boolean delivered;

    public PurchaseSummaryDto() {}

    public PurchaseSummaryDto(Long id, LocalDateTime purchaseDate, String movieTitle, int numberTickets,
                              BigDecimal totalPrice, LocalDateTime sessionStartTime, boolean delivered) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.movieTitle = movieTitle;
        this.numberTickets = numberTickets;
        this.totalPrice = totalPrice;
        this.sessionStartTime = sessionStartTime;
        this.delivered = delivered;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getNumberTickets() {
        return numberTickets;
    }

    public void setNumberTickets(int numberTickets) {
        this.numberTickets = numberTickets;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(LocalDateTime sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

}
