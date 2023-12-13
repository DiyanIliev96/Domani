package com.iliev.domani.unit;

import com.iliev.domani.model.entity.BookingEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BookingEntityTest {

    private BookingEntity bookingEntity;

    @BeforeEach
    void setUp() {
        bookingEntity = new BookingEntity()
                .setFullName("test")
                .setPhoneNumber("123456789")
                .setId(1L)
                .setBookingDateTime(LocalDateTime.of(2023,1,1,1,1))
                .setNumberOfGuests(5);
    }

    @Test
    void testBookingEntity_getId() {
        Assertions.assertEquals(1L,bookingEntity.getId());
    }

    @Test
    void testBookingEntity_getFullName() {
        Assertions.assertEquals("test",bookingEntity.getFullName());
    }

    @Test
    void testBookingEntity_getPhoneNumber() {
        Assertions.assertEquals("123456789",bookingEntity.getPhoneNumber());
    }

    @Test
    void testBookingEntity_getBookingDateTime() {
        Assertions
                .assertEquals(LocalDateTime.of(2023,1,1,1,1)
                        ,bookingEntity.getBookingDateTime());
    }

    @Test
    void testBookingEntity_getNumberOfGuests() {
        Assertions.assertEquals(5,bookingEntity.getNumberOfGuests());
    }
}
