package com.shashi.MovieBookingApplication.Services;

import com.shashi.MovieBookingApplication.DTOs.TheaterDTO;
import com.shashi.MovieBookingApplication.Entities.Theater;
import com.shashi.MovieBookingApplication.Repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    // Defining the method `addTheater()`
    public Theater addTheater(TheaterDTO theaterDTO) {
        // Create a new theater
        Theater theater = new Theater();

        // Set the properties of the new theater
        theater.setName(theaterDTO.getName());
        theater.setLocation(theaterDTO.getLocation());
        theater.setCapacity(theaterDTO.getCapacity());
        theater.setScreenType(theaterDTO.getScreenType());

        // Save the new theater
        return theaterRepository.save(theater);
    }

    // Defining the method `updateTheater()`
    public Theater updateTheater(Long id, TheaterDTO theaterDTO) {
        // Check if the theater exists
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No theater found for the id " + id));

        // If it does, update it with new values
        theater.setName(theaterDTO.getName());
        theater.setLocation(theaterDTO.getLocation());
        theater.setCapacity(theaterDTO.getCapacity());
        theater.setScreenType(theaterDTO.getScreenType());

        // Save the theater
        return theaterRepository.save(theater);
    }

    // Defining the method `deleteTheater()`
    public void deleteTheater(@PathVariable Long id) {
        theaterRepository.deleteById(id);
    }

    // Defining the method `getTheaterByLocation()`
    public List<Theater> getTheaterByLocation(String location) {
        List<Theater> theaters = theaterRepository.findByLocation(location);

        if (theaters.isEmpty()) {
            throw new RuntimeException("No theaters found for the location entered " + location);
        }
        return theaters;
    }
}
