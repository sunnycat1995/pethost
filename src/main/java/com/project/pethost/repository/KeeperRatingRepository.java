package com.project.pethost.repository;

import com.project.pethost.dbo.rating.KeeperRatingDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KeeperRatingRepository extends CrudRepository<KeeperRatingDbo, Long> {
    Optional<KeeperRatingDbo> findById(final Long id);
}
