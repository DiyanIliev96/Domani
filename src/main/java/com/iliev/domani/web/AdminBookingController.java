package com.iliev.domani.web;

import com.iliev.domani.model.dto.BookingDto;
import com.iliev.domani.model.view.BookingView;
import com.iliev.domani.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/bookings/edit/{id}")
    public String getEditBooking(@PathVariable Long id,Model model) {
        if (!model.containsAttribute("bookingToEdit")) {
            BookingDto bookingToEdit = bookingService.getBookingToEdit(id);
            model.addAttribute("bookingToEdit", bookingToEdit);
        }
        return "booking-edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/bookings/edit/{id}")
    public String doEditBooking(@Valid BookingDto editedBookingDto, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, @PathVariable Long id) {
        BookingDto bookingToEdit = bookingService.getBookingToEdit(id);


        if (!editedBookingDto.equals(bookingToEdit) && bindingResult.hasErrors()) {
            editedBookingDto.setBookingId(id);
          redirectAttributes.addFlashAttribute("bookingToEdit",editedBookingDto);
          redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "bookingToEdit",bindingResult);
          return "redirect:/admin/bookings/edit/" + bookingToEdit.getBookingId();
        }

        bookingService.doEditBooking(id,editedBookingDto);
        return "redirect:/admin/bookings";
    }
}
