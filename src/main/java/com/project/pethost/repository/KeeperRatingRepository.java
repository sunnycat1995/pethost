package com.project.pethost.repository;

import com.project.pethost.dbo.rating.KeeperRatingDbo;
import org.springframework.data.repository.CrudRepository;

public interface KeeperRatingRepository extends CrudRepository<KeeperRatingDbo, Long> {
}
