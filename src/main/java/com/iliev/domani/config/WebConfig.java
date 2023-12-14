package com.iliev.domani.config;

import com.iliev.domani.interceptor.BookingInterceptor;
import com.iliev.domani.interceptor.BookingNext1HourInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final BookingInterceptor bookingInterceptor;
    private final BookingNext1HourInterceptor bookingNext1HourInterceptor;

    public WebConfig(BookingInterceptor bookingInterceptor, BookingNext1HourInterceptor bookingNext1HourInterceptor) {
        this.bookingInterceptor = bookingInterceptor;
        this.bookingNext1HourInterceptor = bookingNext1HourInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bookingInterceptor);
        registry.addInterceptor(bookingNext1HourInterceptor);
    }
}
