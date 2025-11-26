package com.ecommerce.ecommerceplatform.dto.responsedto;


public class AuthResponseDto {
    private String token;
    public AuthResponseDto(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

}
