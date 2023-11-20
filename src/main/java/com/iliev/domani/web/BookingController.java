package com.iliev.domani.web;

import com.iliev.domani.model.dto.BookingDto;
import com.iliev.domani.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/booking")
    private String getBooking(Model model) {
        if (!model.containsAttribute("newBooking")) {
            model.addAttribute("newBooking",new BookingDto());
        }
        return "booking";
    }

    @PostMapping("/booking")
    private String doBooking(@Valid BookingDto bookingDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newBooking",bookingDto);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "newBooking",bindingResult);
            return "redirect:/booking";
        }
        bookingService.createBooking(bookingDto);
        return "redirect:/";
    }

    @GetMapping("/booking-friday")
    public String getBookingFriday() {
        return "booking-friday";
    }
}
