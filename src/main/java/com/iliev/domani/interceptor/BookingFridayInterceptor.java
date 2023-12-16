package com.iliev.domani.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Configuration
public class BookingFridayInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        if (requestURI.equals("/booking") && method.equalsIgnoreCase("post")) {
            int numberOfGuests = 0;
            LocalDateTime bookingDateTime = null;
            try {
                numberOfGuests = Integer.parseInt(request.getParameter("numberOfGuests"));
                bookingDateTime = LocalDateTime.parse(request.getParameter("bookingDateTime"));
            } catch (NumberFormatException | DateTimeParseException exception) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }

            DayOfWeek dayOfWeek = bookingDateTime.getDayOfWeek();
            if (dayOfWeek.name().equalsIgnoreCase("Friday") && numberOfGuests > 10) {
                response.sendRedirect("/booking-friday");
                return false;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
