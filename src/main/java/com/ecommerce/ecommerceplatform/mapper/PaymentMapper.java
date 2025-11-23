package com.ecommerce.ecommerceplatform.mapper;

import com.ecommerce.ecommerceplatform.dto.PaymentDTO;
import com.ecommerce.ecommerceplatform.entity.Payment;

public class PaymentMapper {

    public static Payment toEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setProvider(paymentDTO.getProvider());
        payment.setProviderTxnId(paymentDTO.getProviderTxnId());
        payment.setStatus(paymentDTO.getStatus());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaidAt(paymentDTO.getPaidAt());
        return payment;
    }

    public static PaymentDTO toDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setProvider(payment.getProvider());
        paymentDTO.setProviderTxnId(payment.getProviderTxnId());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaidAt(payment.getPaidAt());
        return paymentDTO;
    }
}
