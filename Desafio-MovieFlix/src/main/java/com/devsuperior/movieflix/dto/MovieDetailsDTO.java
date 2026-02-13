package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.projections.MovieDetailsProjection;

public record MovieDetailsDTO(
        Long id,
        String title,
        String subTitle,
        Integer year,
        String imgUrl,
        String synopsis,
        GenreDTO genre
) {

    public MovieDetailsDTO(MovieDetailsProjection projection) {
        this(
                projection.getId(),
                projection.getTitle(),
                projection.getSubTitle(),
                projection.getYear(),
                projection.getImgUrl(),
                projection.getSynopsis(),
                new GenreDTO(
                        projection.getGenreId(),
                        projection.getGenreName()
                )
        );
    }
}
