package com.shashi.MovieBookingApplication.Repositories;

import com.shashi.MovieBookingApplication.Entities.Booking;
import com.shashi.MovieBookingApplication.Entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByShowId(Long showId);

    List<Booking> findByBookingStatus(BookingStatus status);
}
