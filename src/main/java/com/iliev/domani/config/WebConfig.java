package com.iliev.domani.config;

import com.iliev.domani.interceptor.BookingIsAuthenticatedInterceptor;
import com.iliev.domani.interceptor.BookingFridayInterceptor;
import com.iliev.domani.interceptor.BookingNext1HourInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final BookingFridayInterceptor bookingFridayInterceptor;
    private final BookingNext1HourInterceptor bookingNext1HourInterceptor;

    private final BookingIsAuthenticatedInterceptor bookingIsAuthenticatedInterceptor;

    public WebConfig(BookingFridayInterceptor bookingFridayInterceptor, BookingNext1HourInterceptor bookingNext1HourInterceptor, BookingIsAuthenticatedInterceptor bookingIsAuthenticatedInterceptor) {
        this.bookingFridayInterceptor = bookingFridayInterceptor;
        this.bookingNext1HourInterceptor = bookingNext1HourInterceptor;
        this.bookingIsAuthenticatedInterceptor = bookingIsAuthenticatedInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bookingFridayInterceptor);
        registry.addInterceptor(bookingNext1HourInterceptor);
        registry.addInterceptor(bookingIsAuthenticatedInterceptor);
    }
}
