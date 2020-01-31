package adf.homework.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;

import adf.homework.domain.Product;

public interface ProductRepository extends BaseRepository<Product> {

    @EntityGraph(attributePaths = { "orders" })
    Optional<Product> findById(long id);

}
