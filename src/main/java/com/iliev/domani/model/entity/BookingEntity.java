package com.iliev.domani.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private LocalDateTime bookingDateTime;

    @Column(nullable = false)
    private int numberOfGuests;

    public BookingEntity() {
    }

    public Long getId() {
        return id;
    }

    public BookingEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public BookingEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BookingEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public BookingEntity setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
        return this;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public BookingEntity setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BookingEntity setEmail(String email) {
        this.email = email;
        return this;
    }
}
