package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.projections.GenreProjection;

public record GenreDTO(Long id, String name) {

    public GenreDTO(GenreProjection projection) {
        this(projection.getId(), projection.getName());
    }
}
