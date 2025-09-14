package com.shashi.MovieBookingApplication.Repositories;

import com.shashi.MovieBookingApplication.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenre(String genre);

    List<Movie> findByLanguage(String language);

    Optional<Movie> findByName(String title);
}
