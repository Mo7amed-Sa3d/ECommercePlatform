package com.ecommerce.ecommerceplatform.service.mailing;

public interface MailService {
    void sendEmail(String to, String subject, String text);
    void sendEmail(String to, String subject, String text,boolean isHtml);
}
