package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.projections.ReviewProjection;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Transactional(readOnly = true)
    public List<ReviewDTO> findByMovieId(Long movieId) {

        List<ReviewProjection> list = repository.searchByMovieId(movieId);

        return list.stream()
                .map(r -> new ReviewDTO(
                        r.getId(),
                        r.getText(),
                        r.getMovieId(),
                        r.getUserId(),
                        r.getUserName(),
                        r.getUserEmail()
                )).toList();
    }
}
