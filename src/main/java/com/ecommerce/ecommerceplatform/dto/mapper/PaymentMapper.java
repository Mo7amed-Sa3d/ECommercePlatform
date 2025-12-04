package com.ecommerce.ecommerceplatform.dto.mapper;

import com.ecommerce.ecommerceplatform.dto.responsedto.PaymentResponseDTO;
import com.ecommerce.ecommerceplatform.entity.Payment;

public class PaymentMapper {

    public static PaymentResponseDTO toDTO(Payment payment) {
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setId(payment.getId());
        paymentResponseDTO.setProvider(payment.getProvider());
        paymentResponseDTO.setProviderTxnId(payment.getPaymentId());
        paymentResponseDTO.setStatus(payment.getStatus());
        paymentResponseDTO.setAmount(payment.getAmount());
        paymentResponseDTO.setPaidAt(payment.getPaidAt());
        return paymentResponseDTO;
    }
}
