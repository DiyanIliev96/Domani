package com.iliev.domani.event;

import org.springframework.context.ApplicationEvent;

public class BookingSendEmailEvent extends ApplicationEvent {

    private final String userEmail;
    private final String fullName;
    private final String bookingDateTime;

    public BookingSendEmailEvent(Object source, String userEmail, String fullName, String bookingDateTime) {
        super(source);
        this.userEmail = userEmail;
        this.fullName = fullName;
        this.bookingDateTime = bookingDateTime;

    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

}
