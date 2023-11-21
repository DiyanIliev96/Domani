package com.iliev.domani.model.view;

public class BookingView {

    private Long bookingId;
    private String fullName;
    private String phoneNumber;
    private String bookingDateTime;
    private String numberOfGuests;

    public BookingView() {
    }

    public String getFullName() {
        return fullName;
    }

    public BookingView setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BookingView setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public BookingView setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
        return this;
    }

    public String getNumberOfGuests() {
        return numberOfGuests;
    }

    public BookingView setNumberOfGuests(String numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public BookingView setBookingId(Long bookingId) {
        this.bookingId = bookingId;
        return this;
    }
}
