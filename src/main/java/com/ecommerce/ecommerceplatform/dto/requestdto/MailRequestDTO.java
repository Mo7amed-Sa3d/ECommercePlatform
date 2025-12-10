package com.ecommerce.ecommerceplatform.dto.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MailRequestDTO {
    private String to;
    private String subject;
    private String text;
    private Boolean isHtml;
}
