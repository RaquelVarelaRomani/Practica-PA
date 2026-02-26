package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Purchase {

    private Long id;
    private LocalDateTime purchaseDate;
    private Integer numberTickets;
    private String creditCardNumber;
    private User user;
    private Session session;
    private boolean delivered;

    public Purchase(){};
    public Purchase(LocalDateTime purchaseDate, Integer numberTickets, String creditCardNumber, User user, Session session) {
        this.purchaseDate = purchaseDate;
        this.numberTickets = numberTickets;
        this.creditCardNumber = creditCardNumber;
        this.user = user;
        this.session = session;
        this.delivered = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

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

    public boolean isDelivered() {
        return delivered;
    }
    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }
}
