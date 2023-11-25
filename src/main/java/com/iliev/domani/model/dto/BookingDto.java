package com.iliev.domani.model.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;

public class BookingDto {
    private Long bookingId;
    @NotBlank
    @Size(min = 4,max = 30)
    private String fullName;
    @NumberFormat(pattern = "^-?\\d+$")
    @NotNull(message = "Phone number cannot be null!")
    private Long phoneNumber;
    @FutureOrPresent(message = "Date and time cannot be in past!")
    @NotNull(message = "Date and time cannot be empty!")
    @DateTimeFormat
    private LocalDateTime bookingDateTime;

    @NumberFormat(pattern = "^-?\\d+$")
    @NotNull(message = "Number of guests cannot be null!")
    @Positive(message = "Number of guests must be positive!")
    @Min(value = 1,message = "Number of guests cannot be less then 1!")
    @Max(value = 20,message = "Number of guests cannot be more then 20! Contact us directly.")
    private int numberOfGuests;

    public BookingDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public BookingDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public BookingDto setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public BookingDto setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
        return this;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public BookingDto setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public BookingDto setBookingId(Long bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingDto that)) return false;

        if (getNumberOfGuests() != that.getNumberOfGuests()) return false;
        if (!getFullName().equals(that.getFullName())) return false;
        if (!getPhoneNumber().equals(that.getPhoneNumber())) return false;
        if (getBookingDateTime() != null) {
            return getBookingDateTime().equals(that.getBookingDateTime());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = getFullName().hashCode();
        result = 31 * result + getPhoneNumber().hashCode();
        result = 31 * result + getBookingDateTime().hashCode();
        result = 31 * result + getNumberOfGuests();
        return result;
    }
}
