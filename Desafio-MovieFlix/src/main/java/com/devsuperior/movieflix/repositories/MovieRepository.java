package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieCardProjection;
import com.devsuperior.movieflix.projections.MovieDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("""
       SELECT m.id AS id,
              m.title AS title,
              m.subTitle AS subTitle,
              m.year AS year,
              m.imgUrl AS imgUrl
       FROM Movie m
       WHERE (:genreId = 0 OR m.genre.id = :genreId)
       ORDER BY m.title ASC
       """)
	Page<MovieCardProjection> searchAll(Long genreId, Pageable pageable);


	@Query("""
       SELECT m.id AS id,
              m.title AS title,
              m.subTitle AS subTitle,
              m.year AS year,
              m.imgUrl AS imgUrl,
              m.synopsis AS synopsis,
              g.id AS genreId,
              g.name AS genreName
       FROM Movie m
       JOIN m.genre g
       WHERE m.id = :id
       """)
	Optional<MovieDetailsProjection> searchDetailsById(Long id);

}
