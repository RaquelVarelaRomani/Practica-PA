package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SessionDto {

    private Long id;
    private Long movieId;
    private String movieTitle;
    private int durationMinutes;
    private BigDecimal price;
    private LocalDateTime startTime;
    private Long roomId;
    private String roomName;
    private int availableTickets;

    public SessionDto(){}

    public SessionDto(Long id, Long movieId, String movieTitle, int durationMinutes, BigDecimal price, LocalDateTime startTime, Long roomId, String roomName, int availableTickets) {
        this.id = id;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.durationMinutes = durationMinutes;
        this.price = price;
        this.startTime = startTime;
        this.roomId = roomId;
        this.roomName = roomName;
        this.availableTickets = availableTickets;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getRoomId() {
        return roomId;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }
    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}
