package org.stuart.bookings;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;

    @NotBlank
    private String location;

    @NotBlank
    private String activity;

    @Column(name = "num_participants")
    @Size(min = 1, max = 10)
    private int numParticipants;

    public Booking() {
    }

    public Booking(LocalDateTime dateAndTime, String location, String activity, int numParticipants) {
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.activity = activity;
        this.numParticipants = numParticipants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public void setNumParticipants(int numParticipants) {
        this.numParticipants = numParticipants;
    }
}
