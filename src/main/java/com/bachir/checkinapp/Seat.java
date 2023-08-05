package com.bachir.checkinapp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

class Seat implements Serializable {
    private static final long serialVersionUID = 1L;

    private String flight_no;
    private String seat_no;
    private String seatClass;
    private String price;
    private String availability;
    private LocalDate departure_date;
    private LocalTime departure_time;
    private String departure_place;
    private LocalDate arrival_date;
    private LocalTime arrival_time;
    private String landing_place;
    private String options;

    public Seat(String flight_no, String seat_no, String seatClass, String price , String availability, String options) {
        this.flight_no = flight_no;
        this.seat_no = seat_no;
        this.seatClass = seatClass;
        this.price = price;
        this.availability = availability;
        this.options = options;
    }

    public String getFlightNumber() {
        return flight_no;
    }

    public String getSeatNumber() {
        return seat_no;
    }

    public String getSeatClass() {
        return seatClass;
    }
    
    public String getAvailability() {
        return availability;
    }
    
    public String getPrice() {
        return price;
    }

    public LocalDate getDepartureDate() {
        return departure_date;
    }

    public LocalTime getDepartureTime() {
        return departure_time;
    }

    public String getDeparturePlace() {
        return departure_place;
    }

    public LocalDate getArrivalDate() {
        return arrival_date;
    }

    public LocalTime getArrivalTime() {
        return arrival_time;
    }

    public String getLandingPlace() {
        return landing_place;
    }

    public String getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return "Flight Number: " + flight_no +
                ", Seat Number: " + seat_no +
                ", Seat Class: " + seatClass +
                ", Departure Date: " + departure_date +
                ", Departure Time: " + departure_time +
                ", Departure Place: " + departure_place +
                ", Arrival Date: " + arrival_date +
                ", Arrival Time: " + arrival_time +
                ", Landing Place: " + landing_place +
                ", Options: " + options;
    }
}
