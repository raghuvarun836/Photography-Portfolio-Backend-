package com.Photography.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Photography.dto.ContactFormDto;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping
    public String sendContactForm(@RequestBody ContactFormDto contactFormDto) {
        try {
            // Create a SimpleMailMessage and set its values
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("raghuvarun836@gmail.com");
            mailMessage.setTo("raghuvarun836@gmail.com");
            mailMessage.setSubject("New Contact Form Submission From Your Website");
            mailMessage.setText(
                "Name: " + contactFormDto.getName() + "\n" +
                "Email: " + contactFormDto.getEmail() + "\n" +
                "Contact No: " + contactFormDto.getContactNo() + "\n" +
                "Shoot Location: " + contactFormDto.getShootLocation() + "\n" +
                "Preferred Shoot Date: " + contactFormDto.getPreferredDate() + "\n" +
                "Additional Requirements: " + contactFormDto.getAdditionalRequirements()
            );

            // Send the email
            javaMailSender.send(mailMessage);

            return "Form submission successful!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error submitting the form.";
        }
    }
}
