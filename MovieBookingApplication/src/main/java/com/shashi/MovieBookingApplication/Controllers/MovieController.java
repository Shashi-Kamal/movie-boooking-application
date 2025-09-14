package com.shashi.MovieBookingApplication.Controllers;

import com.shashi.MovieBookingApplication.DTOs.MovieDTO;
import com.shashi.MovieBookingApplication.Entities.Movie;
import com.shashi.MovieBookingApplication.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    //Only admin can access this api
    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping(value = "/add-movie")
    // Add a new movie
    public ResponseEntity<Movie> addMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.addMovie(movieDTO));
    }

    //Only admin can access this api
    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping(value = "/update-movie/{id}")
    // Update a movie by its id
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(id, movieDTO));
    }

    //Only admin can access this api
    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping(value = "/delete-movie/{id}")
    // Delete a movie by its id
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get-all-movies")
    // Fetch all movies
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // Fetch a movie by its id
    @GetMapping(value = "/get-movies-by-genre")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre));
    }

    // Fetch all movies by a specific language
    @GetMapping(value = "/get-movies-by-language")
    public ResponseEntity<List<Movie>> getMoviesByLanguage(@RequestParam String language) {
        return ResponseEntity.ok(movieService.getMoviesByLanguage(language));
    }

    // Fetch a movie by its title
    @GetMapping(value = "/get-movie-by-title")
    public ResponseEntity<Movie> getMovieByTitle(@RequestParam String title) {
        return ResponseEntity.ok(movieService.getMovieByTitle(title));
    }
}
