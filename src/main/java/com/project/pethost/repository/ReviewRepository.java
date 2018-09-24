package com.project.pethost.repository;

import com.project.pethost.dbo.ReviewDbo;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<ReviewDbo, Long> {
}
