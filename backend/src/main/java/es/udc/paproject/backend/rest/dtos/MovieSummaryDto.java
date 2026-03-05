package es.udc.paproject.backend.rest.dtos;

import java.util.List;

public class MovieSummaryDto {

    private Long id;
    private String title;
    private List<SessionSummaryDto> sessions;

    public MovieSummaryDto() {}

    public MovieSummaryDto(Long id, String title, List<SessionSummaryDto> sessions) {
        this.id = id;
        this.title = title;
        this.sessions = sessions;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<SessionSummaryDto> getSessions() { return sessions; }
    public void setSessions(List<SessionSummaryDto> sessions) { this.sessions = sessions; }
}