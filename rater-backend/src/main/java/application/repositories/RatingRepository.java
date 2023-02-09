package application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import application.models.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
}