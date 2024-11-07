package com.socialmedia.nicheplatform.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {


    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String verificationUrl) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(to);
            helper.setSubject("Verify your email");
            helper.setText("<p>Please click the link below to verify your email:</p>"
                    + "<a href=\"" + verificationUrl + "\">Verify Now</a>", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to send email");
        }
    }
}

