package com.iliev.domani.repository;

import com.iliev.domani.model.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity,Long> {

    List<BookingEntity> findAllByBookingDateTimeBefore(LocalDateTime bookingDateTime);
}
