package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;
import com.devsuperior.movieflix.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @Autowired
    ReviewService reviewService;

    @PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
    @GetMapping
    public ResponseEntity<Page<MovieCardDTO>> findAll(
            @RequestParam(name = "genreId", defaultValue = "0") Long genreId,
            Pageable pageable) {
        Page<MovieCardDTO> page = service.findAllPaged(genreId, pageable);
        return ResponseEntity.ok().body(page);
    }

    @PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
        MovieDetailsDTO dto = service.findDetailsById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_VISITOR', 'ROLE_MEMBER')")
    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<List<ReviewDTO>> findByMovieId(@PathVariable Long movieId) {
        List<ReviewDTO> dto = reviewService.findByMovieId(movieId);
        return ResponseEntity.ok(dto);
    }
}
