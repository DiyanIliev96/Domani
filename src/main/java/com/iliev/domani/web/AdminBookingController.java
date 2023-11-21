package com.iliev.domani.web;

import com.iliev.domani.model.view.BookingView;
import com.iliev.domani.service.BookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/bookings")
    public String getBookingsView(Model model) {
        if (!model.containsAttribute("allBookings")) {
            List<BookingView> allBookings = bookingService.getAllBookings();
            model.addAttribute("allBookings",allBookings);
        }
        return "booking-view";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/bookings/{id}")
    public String doDelete(@PathVariable Long id) {
        bookingService.deleteById(id);
        return "redirect:/admin/bookings";
    }
}
