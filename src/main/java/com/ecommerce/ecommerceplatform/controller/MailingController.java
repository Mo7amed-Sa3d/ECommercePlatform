package com.ecommerce.ecommerceplatform.controller;

import com.ecommerce.ecommerceplatform.dto.requestdto.MailRequestDTO;
import com.ecommerce.ecommerceplatform.service.mailing.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailingController {

    MailService mailService;

    @Autowired
    public MailingController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody MailRequestDTO mailRequestDTO) {
        mailService.sendEmail(mailRequestDTO.getTo(), mailRequestDTO.getSubject(), mailRequestDTO.getText(),mailRequestDTO.getIsHtml());
        return ResponseEntity.ok("Mail Sent Successfully");
    }
}
