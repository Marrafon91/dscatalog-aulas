package com.devsuperior.movieflix.services.copy;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;

public interface CopyDTO {

    default ReviewDTO toDTO(Review entity) {
        return new ReviewDTO(
                entity.getId(),
                entity.getText(),
                entity.getMovie().getId(),
                entity.getUser().getId(),
                entity.getUser().getName(),
                entity.getUser().getEmail()
        );
    }

}
