package com.iliev.domani.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminBookingController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/bookings")
    public String getBookingsView() {
        return "booking-view";
    }
}
