package com.shashi.MovieBookingApplication.DTOs;

import com.shashi.MovieBookingApplication.Entities.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    private Integer numberOfSeats;
    private LocalDateTime bookingTime;
    private Double price;
    private BookingStatus BookingStatus ;
    private List<String> seatNumbers;
    private Long userId;
    private Long showId;
}
