package com.iliev.domani.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Configuration
public class BookingNext1HourInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        if (requestURI.equals("/booking") && method.equalsIgnoreCase("post")) {
            LocalDateTime bookingDateTime = null;
            try {
                bookingDateTime = LocalDateTime.parse(request.getParameter("bookingDateTime"));
            } catch (NumberFormatException | DateTimeParseException exception) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
            int minute = LocalDateTime.now().getMinute();
            int bookingMinute = bookingDateTime.getMinute();
            int hour = LocalDateTime.now().getHour() + 1;
            int bookingHour = bookingDateTime.getHour();
            if (bookingHour == hour && bookingMinute <= minute) {
                response.sendRedirect("/booking-next-hour");
                return false;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
