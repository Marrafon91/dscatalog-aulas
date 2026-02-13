package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.projections.MovieCardProjection;

public record MovieCardDTO(
        Long id,
        String title,
        String subTitle,
        Integer year,
        String imgUrl
) {

    public MovieCardDTO(MovieCardProjection projection) {
        this(
                projection.getId(),
                projection.getTitle(),
                projection.getSubTitle(),
                projection.getYear(),
                projection.getImgUrl()
        );
    }
}
