package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import models.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
}