package org.stuart.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService service;

    @GetMapping
    public List<Booking> getBookings() {
        return service.getBookings();
    }

    @PostMapping
    public Booking addNewBooking(@RequestBody @Validated Booking booking) {
        return service.addNewBooking(booking);
    }
}
