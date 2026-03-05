package es.udc.paproject.backend.rest.dtos;

import java.time.LocalDateTime;

public class SessionSummaryDto {

    private Long id;
    private LocalDateTime startTime;

    public SessionSummaryDto() {}

    public SessionSummaryDto(Long id, LocalDateTime startTime) {
        this.id = id;
        this.startTime = startTime;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
}