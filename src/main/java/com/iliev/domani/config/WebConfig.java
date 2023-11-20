package com.iliev.domani.config;

import com.iliev.domani.interceptor.BookingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final BookingInterceptor bookingInterceptor;

    public WebConfig(BookingInterceptor bookingInterceptor) {
        this.bookingInterceptor = bookingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bookingInterceptor);
    }
}
