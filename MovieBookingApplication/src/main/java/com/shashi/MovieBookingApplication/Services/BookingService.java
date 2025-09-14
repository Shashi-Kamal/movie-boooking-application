package com.shashi.MovieBookingApplication.Services;

import com.shashi.MovieBookingApplication.DTOs.BookingDTO;
import com.shashi.MovieBookingApplication.Entities.Booking;
import com.shashi.MovieBookingApplication.Entities.BookingStatus;
import com.shashi.MovieBookingApplication.Entities.Show;
import com.shashi.MovieBookingApplication.Entities.User;
import com.shashi.MovieBookingApplication.Repositories.BookingRepository;
import com.shashi.MovieBookingApplication.Repositories.ShowRepository;
import com.shashi.MovieBookingApplication.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    // createBooking()
    public Booking createBooking(BookingDTO bookingDTO) {
        Show show = showRepository.findById(bookingDTO.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        if(!isSeatsAvailable(show.getId(), bookingDTO.getNumberOfSeats())) {
            throw new RuntimeException("Not enough seat are available");
        }

        if(bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()) {
            throw new RuntimeException("Seat numbers and Number of Seats must be equal");
        }

        validateDuplicateSeats(show.getId(), bookingDTO.getSeatNumbers());

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setSeatNumbers(bookingDTO.getSeatNumbers());
        booking.setPrice(calculateTotalAmount(show.getPrice(), bookingDTO.getNumberOfSeats()));
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    // isSeatsAvailable()
    public boolean isSeatsAvailable(Long showId, Integer numberOfSeats) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        int bookedSeats = show.getBookings().stream()
                .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        return (show.getTheater().getCapacity() - bookedSeats) >= numberOfSeats;
    }

    // validateDuplicateSeats()
    public void validateDuplicateSeats(Long showId, List<String> seatNumbers) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        Set<String> occupiedSeats = show.getBookings().stream()
                .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELED)
                .flatMap(booking -> booking.getSeatNumbers().stream())
                .collect(Collectors.toSet());

        Set<String> duplicateSeats = seatNumbers.stream()
                .filter(occupiedSeats::contains)
                .collect(Collectors.toSet());

        if (!duplicateSeats.isEmpty()) {
            throw new RuntimeException("Seats are already booked");
        }
    }

    // calculateTotalAmount()
    public Double calculateTotalAmount(Double price, Integer numberOfSeats) {
        return price * numberOfSeats;
    }

    // getUserBookings()
    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // getShowBookings()
    public List<Booking> getShowBookings(Long showId) {
        return bookingRepository.findByShowId(showId);
    }

    // confirmBooking()
    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not in pending state");
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }

    // cancelBooking()
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        validateCancellation(booking);

        booking.setBookingStatus(BookingStatus.CANCELED);

        return bookingRepository.save(booking);
    }

    // validateCancellation()
    public void validateCancellation(Booking booking) {
        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadlineTime = showTime.minusHours(2);

        if(LocalDateTime.now().isAfter(deadlineTime)) {
            throw new RuntimeException("Cannot cancel the booking");
        }

        if(booking.getBookingStatus() == BookingStatus.CANCELED) {
            throw new RuntimeException("Booking already been cancelled");
        }
    }

    // findBookingByStatus()
    public List<Booking> getBookingsByStatus(BookingStatus status) {

        return bookingRepository.findByBookingStatus(status);
    }
}
