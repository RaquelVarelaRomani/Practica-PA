package es.udc.paproject.backend.model.entities;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionDao extends CrudRepository<Session, Long> {

    //Sesiones futuras
    List<Session> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime now);

    //Sesiones de un día concreto
    List<Session> findByStartTimeBetweenOrderByStartTimeAsc(LocalDateTime startTime, LocalDateTime endTime);

    //Sesiones futuras de una película concreta
    List<Session> findByMovieIdAndStartTimeAfterOrderByStartTimeAsc(Long movieId, LocalDateTime now);
}

