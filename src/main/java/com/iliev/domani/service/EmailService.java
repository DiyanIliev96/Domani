package com.iliev.domani.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendRegistrationEmail(String userEmail,String userFullName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Welcome!");
            mimeMessageHelper.setText(generateMessageContentRegistration(userFullName),true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateMessageContentRegistration(String fullName) {
        Context ctx = new Context();
        ctx.setVariable("fullName",fullName);
     return  templateEngine.process("email/welcome",ctx);
    }

    public void sendBookingEmail(String userEmail,String userFullName,String bookingDateTime) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Booking!");
            mimeMessageHelper.setText(generateMessageContentBooking(userFullName,bookingDateTime),true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateMessageContentBooking(String userFullName,String bookingDateTime) {
        Context ctx = new Context();
        ctx.setVariable("userFullName",userFullName);
        ctx.setVariable("bookingDateTime",bookingDateTime);
        return templateEngine.process("email/booking-email",ctx);
    }
}
