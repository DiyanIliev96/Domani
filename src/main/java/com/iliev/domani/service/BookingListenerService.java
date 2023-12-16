package com.iliev.domani.service;

import com.iliev.domani.event.BookingSendEmailEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class BookingListenerService {


    private final EmailService emailService;

    public BookingListenerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener(BookingSendEmailEvent.class)
    public void sendBookingEmail(BookingSendEmailEvent bookingSendEmailEvent) {
        emailService.sendBookingEmail(bookingSendEmailEvent.getUserEmail()
        , bookingSendEmailEvent.getFullName(), bookingSendEmailEvent.getBookingDateTime());
    }
}
