package adf.homework.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;

import adf.homework.domain.Order;

public interface OrderRepository extends BaseRepository<Order> {

    @EntityGraph(attributePaths = { "customer", "product" })
    Optional<Order> findById(long id);

}
