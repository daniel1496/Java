package org.stuart.bookings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepo repo;

    public List<Booking> getBookings() {
        return repo.findAll();
    }

    public Booking addNewBooking(Booking booking) {
        return repo.save(booking);
    }
}
