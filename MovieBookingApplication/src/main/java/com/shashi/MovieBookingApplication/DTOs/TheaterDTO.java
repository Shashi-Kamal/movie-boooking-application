package com.shashi.MovieBookingApplication.DTOs;

import lombok.Data;

@Data
public class TheaterDTO {

    private String name;
    private String location;
    private Integer capacity;
    private String screenType;
}
