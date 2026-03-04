package es.udc.paproject.backend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDateTime;

public interface MovieDao extends CrudRepository<Movie, Long> {

    List<Movie> findAllByOrderByTitleAsc();

    //FUNC-1 visualizar la cartelera
    //DISTINCT s.movie para que si una película tiene varias sesiones solo aparece 1 vez
    //LocalDateTime start/end rango de tiempo desde inicio (o ahora si es hoy) hasta el final
    //El enunciado manda las peliculas en orden alfabetico ORDER BY s.movie.title ASC
    @Query("SELECT DISTINCT s.movie FROM Session s " +
            "WHERE s.startTime >= :start AND s.startTime <= :end " +
            "ORDER BY s.movie.title ASC")
    List<Movie> findMoviesWithSessionsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
