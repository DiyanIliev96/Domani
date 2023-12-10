package com.iliev.domani.unit;

import com.iliev.domani.model.view.BookingView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BookingViewTest {

    private BookingView bookingView;
    private LocalDateTime dateTime;
    @BeforeEach
    void setUp() {
      dateTime = LocalDateTime.of(2023,1,1,1,1);
       bookingView = new BookingView()
                .setBookingId(1L)
                .setBookingDateTime(dateTime.toString())
                .setFullName("Test")
                .setPhoneNumber("123456789")
                .setNumberOfGuests("6");
    }

    @Test
    void testGetAndSetBookingViewId() {
        Assertions.assertEquals(1L,bookingView.getBookingId());
        bookingView.setBookingId(2L);
        Assertions.assertEquals(2L,bookingView.getBookingId());
    }

    @Test
    void testGetAndSetBookingFullName() {
        Assertions.assertEquals("Test",bookingView.getFullName());
        bookingView.setFullName("Test2");
        Assertions.assertEquals("Test2",bookingView.getFullName());
    }

    @Test
    void testGetAndSetBookingPhoneNumber() {
        Assertions.assertEquals("123456789",bookingView.getPhoneNumber());
        bookingView.setPhoneNumber("987654321");
        Assertions.assertEquals("987654321",bookingView.getPhoneNumber());
    }

    @Test
    void testGetAndSetBookingDateTime() {
        LocalDateTime time = LocalDateTime.of(2023, 1, 1, 1, 1);
        Assertions.assertEquals(time.toString(),bookingView.getBookingDateTime());
        LocalDateTime newDateTime = LocalDateTime.of(2023,2,2,2,2);
        bookingView.setBookingDateTime(newDateTime.toString());
        Assertions.assertEquals(newDateTime.toString(),bookingView.getBookingDateTime());
    }

    @Test
    void testGetAndSetBookingNumberOfGuests() {
        Assertions.assertEquals("6",bookingView.getNumberOfGuests());
        bookingView.setNumberOfGuests("7");
        Assertions.assertEquals("7",bookingView.getNumberOfGuests());
    }
}
