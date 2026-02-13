package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.projections.ReviewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
    SELECT r.id AS id,
           r.text AS text,
           r.movie.id AS movieId,
           r.user.id AS userId,
           r.user.name AS userName,
           r.user.email AS userEmail
    FROM Review r
    WHERE r.movie.id = :movieId
""")
    List<ReviewProjection> searchByMovieId(Long movieId);

}
