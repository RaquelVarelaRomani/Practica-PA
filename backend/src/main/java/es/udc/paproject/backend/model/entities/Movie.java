package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Movie {

    private Long id;
    private String title;
    private String summary;
    private int durationMinutes;
    private List<Session> sessions;

    public  Movie() {}

    public Movie(String title, String summary, int durationMinutes) {
        this.title = title;
        this.summary = summary;
        this.durationMinutes = durationMinutes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getSummary() {return summary;}
    public void setSummary(String summary) {this.summary = summary;}

    public int getDurationMinutes() {return durationMinutes;}
    public void setDurationMinutes(int durationMinutes) {this.durationMinutes = durationMinutes;}

    @OneToMany(mappedBy = "movie")
    public List<Session> getSessions() {return sessions;}
    public void setSessions(List<Session> sessions) {this.sessions = sessions;}
}
