package com.iliev.domani.service;

import com.iliev.domani.model.dto.BookingDto;
import com.iliev.domani.model.entity.BookingEntity;
import com.iliev.domani.model.view.BookingView;
import com.iliev.domani.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
