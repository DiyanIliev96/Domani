package com.iliev.domani.web;

import com.iliev.domani.model.dto.BookingDto;
import com.iliev.domani.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testBooking_Successfully() throws Exception {
        mockMvc.perform(post("/booking").with(csrf())
                        .param("fullName", "testtest")
                        .param("phoneNumber", "12356675")
                        .param("bookingDateTime", "2025-12-14T21:16")
                        .param("numberOfGuests", "6")
                        .param("email", "test@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testGetBooking() throws Exception {
        mockMvc.perform(get("/booking"))
                .andExpect(status().isOk())
                .andExpect(view().name("booking"))
                .andExpect(model().attributeExists("newBooking"))
                .andExpect(model().attribute("newBooking", new BookingDto()));

    }

    @Test
    void testBooking_UnSuccessfully_shortFullName() throws Exception {
        mockMvc.perform(post("/booking").with(csrf())
                        .param("fullName", "te")
                        .param("phone", "12356")
                        .param("bookingDateTime", "2025-12-15T18:59")
                        .param("numberOfGuests", "6")
                        .param("email", "test@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/booking"));
    }

    @Test
    void testBooking_UnSuccessfully_FridayMoreThen10Guests() throws Exception {
        mockMvc.perform(post("/booking").with(csrf())
                        .param("fullName", "test")
                        .param("phone", "12356")
                        .param("bookingDateTime", "2023-12-15T18:59")
                        .param("numberOfGuests", "11")
                        .param("email", "test@test.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/booking-friday"));
    }

}