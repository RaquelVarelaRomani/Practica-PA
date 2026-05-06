package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseDao extends CrudRepository<Purchase,Long> {

    //Historial del usuario por fecha descendente (paginado)
    Slice<Purchase> findByUserIdOrderByPurchaseDateDesc(Long userId, Pageable pageable);

}