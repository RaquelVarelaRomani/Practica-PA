package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Session {

    private Long id;
    private Movie movie;
    private Room room;
    private LocalDateTime startTime;
    private BigDecimal price;
    private List<Purchase> purchases;

    public  Session() {}

    public Session(Movie movie, Room room, LocalDateTime startTime, BigDecimal price) {
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Movie getMovie() {return movie;}
    public void setMovie(Movie movie) {this.movie = movie;}

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    public Room getRoom() {return room;}
    public void setRoom(Room room) {this.room = room;}

    public LocalDateTime getStartTime() {return startTime;}
    public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}

    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal price) {this.price = price;}

    @OneToMany(mappedBy = "session")
    public List<Purchase> getPurchases() { return purchases; }
    public void setPurchases(List<Purchase> purchases) { this.purchases = purchases; }
}

