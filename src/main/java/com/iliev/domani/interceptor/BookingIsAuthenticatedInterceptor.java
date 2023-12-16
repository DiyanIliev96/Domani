package com.iliev.domani.interceptor;

import com.iliev.domani.user.DomaniUserDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class BookingIsAuthenticatedInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (requestURI.equals("/booking") && method.equalsIgnoreCase("get")
                && !authentication.getName().equalsIgnoreCase("anonymousUser")) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            DomaniUserDetail domaniUserDetail = (DomaniUserDetail) principal;
                request.setAttribute("email",domaniUserDetail.getEmail());
                request.setAttribute("fullName",domaniUserDetail.getFullName());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
