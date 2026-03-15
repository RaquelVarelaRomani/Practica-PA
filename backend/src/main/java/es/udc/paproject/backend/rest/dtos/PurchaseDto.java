package es.udc.paproject.backend.rest.dtos;

import java.time.LocalDateTime;

public class PurchaseDto {

    private Long id;
    private LocalDateTime purchaseDate;
    private Integer numberTickets;
    private String creditCardNumber;
    private Long userId;
    private Long sessionId;
    private boolean delivered;

    public PurchaseDto() {}

    public PurchaseDto(Long id, LocalDateTime purchaseDate, Integer numberTickets, String creditCardNumber, Long userId, Long sessionId, boolean delivered) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.numberTickets = numberTickets;
        this.creditCardNumber = creditCardNumber;
        this.userId = userId;
        this.sessionId = sessionId;
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

    public Integer getNumberTickets() {
        return numberTickets;
    }

    public void setNumberTickets(Integer numberTickets) {
        this.numberTickets = numberTickets;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
