package es.udc.paproject.backend.rest.dtos;

public class MovieDto {
    private Long id;
    private String title;
    private String summary;
    private int durationMinutes;

    public MovieDto() {}

    public MovieDto(Long id, String title, String summary, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.durationMinutes = durationMinutes;
    }

    //GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
}