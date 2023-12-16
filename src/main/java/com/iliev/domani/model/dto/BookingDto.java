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
    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Enter valid email!")
    private String email;
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BookingDto other = (BookingDto) obj;

        // Null checks to handle potential null values
        if (getFullName() != null ? !getFullName().equals(other.getFullName()) : other.getFullName() != null) {
            return false;
        }
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(other.getPhoneNumber()) : other.getPhoneNumber() != null) {
            return false;
        }
        if (getBookingDateTime() != null ? !getBookingDateTime().equals(other.getBookingDateTime()) : other.getBookingDateTime() != null) {
            return false;
        }

        return getNumberOfGuests() == other.getNumberOfGuests();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getBookingDateTime() != null ? getBookingDateTime().hashCode() : 0);
        result = 31 * result + getNumberOfGuests();
        return result;
    }

    public String getEmail() {
        return email;
    }

    public BookingDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
