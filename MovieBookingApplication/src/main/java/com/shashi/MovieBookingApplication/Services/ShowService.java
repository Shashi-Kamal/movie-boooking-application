package com.shashi.MovieBookingApplication.Services;

import com.shashi.MovieBookingApplication.DTOs.ShowDTO;
import com.shashi.MovieBookingApplication.Entities.Booking;
import com.shashi.MovieBookingApplication.Entities.Movie;
import com.shashi.MovieBookingApplication.Entities.Show;
import com.shashi.MovieBookingApplication.Entities.Theater;
import com.shashi.MovieBookingApplication.Repositories.MovieRepository;
import com.shashi.MovieBookingApplication.Repositories.ShowRepository;
import com.shashi.MovieBookingApplication.Repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public Show createShow(ShowDTO showDTO) {
        // Find if the movie exists
        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("No movie found for the id " + showDTO.getMovieId()));

        // Find if the theater exists
        Theater theater = theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(() -> new RuntimeException("No theater found for the id " + showDTO.getTheaterId()));

        // Create a new Show
        Show show = new Show();

        // Set the properties of the new Show
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);

        // Save the new Show
        return showRepository.save(show);

    }

    public Show updateShow(Long id, ShowDTO showDTO) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Show found for the id" + id));

        // Find if the movie exists
        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("No movie found for the id " + showDTO.getMovieId()));

        // Find if the theater exists
        Theater theater = theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(() -> new RuntimeException("No theater found for the id " + showDTO.getTheaterId()));

        // Set the properties of the existing Show
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);

        // Save the Show
        return showRepository.save(show);
    }

    // Fetch all the shows
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovie(Long movieId) {
        List<Show> shows = showRepository.findByMovieId(movieId);

        if (shows.isEmpty()) {
            throw new RuntimeException("No shows available for the movie");
        }
        return shows;
    }

    public List<Show> getShowsByTheater(Long theaterid) {
        List<Show> shows = showRepository.findByTheaterId(theaterid);

        if (shows.isEmpty()) {
            throw new RuntimeException("No shows available for the theater");
        }
        return shows;
    }

    public void deleteShow(Long id) {
        // Step 1: Check if a Show exists with this id
        if(!showRepository.existsById(id)) {
            throw new RuntimeException("No Show available for the id " + id);
        }
        // Step 2: Fetch the Show and its associated Bookings
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Show available for the id " + id));

        List<Booking> bookings = show.getBookings();

        // Step 3: Ensure no bookings exist for this Show
        if(!bookings.isEmpty()) {
            throw new RuntimeException("Can't delete show with existing bookings.");
        }

        // Step 4: Safe to delete the Show
        showRepository.deleteById(id);
    }
}
