package com.shashi.MovieBookingApplication.Services;

import com.shashi.MovieBookingApplication.DTOs.MovieDTO;
import com.shashi.MovieBookingApplication.Entities.Movie;
import com.shashi.MovieBookingApplication.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Defining the method `addMovie()`
    public Movie addMovie(MovieDTO movieDTO) {
        // Create a movie object
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setLanguage(movieDTO.getLanguage());

        return movieRepository.save(movie);
    }

    // Defining the method `getAllMovies()`
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Defining the method `getMoviesByGenre()`
    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> movies = movieRepository.findByGenre(genre);

        if (movies.isEmpty()) {
            throw new RuntimeException("No movies found for genre " + genre);
        }
        return movies;
    }

    // Defining the method `getMoviesByLanguage()`
    public List<Movie> getMoviesByLanguage(String language) {
        List<Movie> movies = movieRepository.findByLanguage(language);

        if (movies.isEmpty()) {
            throw new RuntimeException("No movies found for language " + language);
        }
        return movies;
    }

    // Defining the method `getMovieByTitle()`
    public Movie getMovieByTitle(String title) {
        Optional<Movie> movieBox = movieRepository.findByName(title);

        if (movieBox.isPresent()) {
            return movieBox.get();
        } else {
            throw new RuntimeException("No movie found for the title");
        }
    }

    public Movie updateMovie(Long id, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No movie found for the id " + id));

        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setLanguage(movieDTO.getLanguage());

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
