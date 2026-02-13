package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.projections.MovieCardProjection;
import com.devsuperior.movieflix.projections.MovieDetailsProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {


    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findAllPaged(Long genreId, Pageable pageable) {
        Page<MovieCardProjection> page = repository.searchAll(genreId, pageable);
        return page.map(MovieCardDTO::new);
    }

    @Transactional(readOnly = true)
    public MovieDetailsDTO findDetailsById(Long id) {

        MovieDetailsProjection projection = repository.searchDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        return new MovieDetailsDTO(projection);
    }

}
