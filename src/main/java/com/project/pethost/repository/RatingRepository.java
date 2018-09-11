package com.project.pethost.repository;

import com.project.pethost.dbo.RatingDbo;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<RatingDbo, Long> {
}
