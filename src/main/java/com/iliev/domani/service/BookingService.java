package com.iliev.domani.service;

import com.iliev.domani.exception.ObjectNotFoundException;
import com.iliev.domani.model.dto.BookingDto;
import com.iliev.domani.model.entity.BookingEntity;
import com.iliev.domani.model.view.BookingView;
import com.iliev.domani.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public BookingService(BookingRepository bookingRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    public void createBooking(BookingDto bookingDto) {
        BookingEntity newBooking = modelMapper.map(bookingDto, BookingEntity.class);
        bookingRepository.save(newBooking);
    }

    public List<BookingView> getAllBookings() {
        return bookingRepository
                .findAll()
                .stream()
                .map(booking -> {
                    BookingView view = modelMapper.map(booking, BookingView.class);
                    view.setBookingDateTime(view.getBookingDateTime().replace("T"," at: "));
                    return view;
                }).toList();
    }

    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    public BookingDto getBookingToEdit(Long id) {
        return bookingRepository.findById(id).map(b -> modelMapper.map(b,BookingDto.class))
                .orElseThrow(() -> new ObjectNotFoundException("Booking with " + id + " not found."));
    }

    public void doEditBooking(Long id, BookingDto editedBookingDto) {
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Booking with " + id + " not found."));

       if (!bookingEntity.getFullName().equals(editedBookingDto.getFullName())) {
           bookingEntity.setFullName(editedBookingDto.getFullName());
       }

       if (!bookingEntity.getPhoneNumber().equals(editedBookingDto.getPhoneNumber().toString())) {
           bookingEntity.setPhoneNumber(editedBookingDto.getPhoneNumber().toString());
       }

       if (!bookingEntity.getBookingDateTime().equals(editedBookingDto.getBookingDateTime())) {
           bookingEntity.setBookingDateTime(editedBookingDto.getBookingDateTime());
       }

       if (bookingEntity.getNumberOfGuests() != editedBookingDto.getNumberOfGuests()) {
           bookingEntity.setNumberOfGuests(editedBookingDto.getNumberOfGuests());
       }
       bookingRepository.save(bookingEntity);
    }
    @Scheduled(cron = "0 0/20 * 1/1 * ?")
    private void deleteExpiredBookings() {
        List<BookingEntity> allByBookingDateTimeBefore = bookingRepository
                .findAllByBookingDateTimeBefore(LocalDateTime.now());
        bookingRepository.deleteAll(allByBookingDateTimeBefore);
    }
}
