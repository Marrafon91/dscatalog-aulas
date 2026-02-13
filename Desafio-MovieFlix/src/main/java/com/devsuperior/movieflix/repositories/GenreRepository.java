package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.projections.GenreProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("""
            SELECT g.id AS id, g.name AS name
            FROM Genre g
            ORDER BY g.name ASC
            """)
    List<GenreProjection> searchAllGenres();
}
