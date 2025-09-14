package com.shashi.MovieBookingApplication.Controllers;

import com.shashi.MovieBookingApplication.DTOs.ShowDTO;
import com.shashi.MovieBookingApplication.Entities.Show;
import com.shashi.MovieBookingApplication.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/create-show")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Show> createShow(@RequestBody ShowDTO showDTO) {
        return ResponseEntity.ok(showService.createShow(showDTO));
    }

    @PostMapping("/update-show")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody ShowDTO showDTO) {
        return ResponseEntity.ok(showService.updateShow(id, showDTO));
    }

    @PostMapping("/delete-show")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all-shows")
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/get-shows-by-movie/{id}")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowsByMovie(id));
    }

    @GetMapping("/get-shows-by-theater/{id}")
    public ResponseEntity<List<Show>> getShowsByTheatre(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowsByTheater(id));
    }
}
