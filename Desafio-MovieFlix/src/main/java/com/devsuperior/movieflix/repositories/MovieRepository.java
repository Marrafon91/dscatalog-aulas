package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieTitleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {


	@Query("""
       SELECT m.id AS id,
              m.title AS title,
              m.subTitle AS subTitle,
              m.year AS year,
              m.imgUrl AS imgUrl
       FROM Movie m
       """)
	Page<MovieTitleProjection> searchByTitle(Pageable pageable);
}
