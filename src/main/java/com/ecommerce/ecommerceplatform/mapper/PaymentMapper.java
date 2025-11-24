package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.PaymentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Payment;

public class PaymentMapper {

    public static Payment toEntity(PaymentResponseDTO paymentResponseDTO) {
        Payment payment = new Payment();
        payment.setId(paymentResponseDTO.getId());
        payment.setProvider(paymentResponseDTO.getProvider());
        payment.setProviderTxnId(paymentResponseDTO.getProviderTxnId());
        payment.setStatus(paymentResponseDTO.getStatus());
        payment.setAmount(paymentResponseDTO.getAmount());
        payment.setPaidAt(paymentResponseDTO.getPaidAt());
        return payment;
    }

    public static PaymentResponseDTO toDTO(Payment payment) {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setId(payment.getId());
        paymentResponseDTO.setProvider(payment.getProvider());
        paymentResponseDTO.setProviderTxnId(payment.getProviderTxnId());
        paymentResponseDTO.setStatus(payment.getStatus());
        paymentResponseDTO.setAmount(payment.getAmount());
        paymentResponseDTO.setPaidAt(payment.getPaidAt());
        return paymentResponseDTO;
    }
}
