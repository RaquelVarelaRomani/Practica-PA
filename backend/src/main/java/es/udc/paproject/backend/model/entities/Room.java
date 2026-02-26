package es.udc.paproject.backend.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Room {

    private Long id;
    private String name;
    private Integer capacity;
    private List<Session> sessions;

    public Room(){}
    public Room(String name, Integer capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return id;}
    public void setId(Long id) { this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Integer getCapacity() {return capacity;}
    public void setCapacity(Integer capacity) {this.capacity = capacity;}

    @OneToMany(mappedBy = "room")
    public List<Session> getSessions() {return sessions;}
    public void setSessions(List<Session> sessions) {this.sessions = sessions;}
}
