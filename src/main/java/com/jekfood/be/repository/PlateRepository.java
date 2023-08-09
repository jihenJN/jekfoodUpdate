package com.jekfood.be.repository;

import com.jekfood.be.domain.Plate;
import com.jekfood.be.domain.Restaurant;
import com.jekfood.be.service.dto.PlateDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Plate entity.
 */
@Repository
public interface PlateRepository extends MongoRepository<Plate, String> {
    @Query("{}")
    Page<Plate> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Plate> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Plate> findOneWithEagerRelationships(String id);

    List<Plate> findAllByRestaurant(Restaurant restaurant);

    Page<Plate> findAllByRestaurant(Restaurant restaurant, Pageable pageable);
}
