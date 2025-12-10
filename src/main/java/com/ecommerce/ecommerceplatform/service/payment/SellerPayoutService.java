package com.ecommerce.ecommerceplatform.service.payment;

import com.ecommerce.ecommerceplatform.entity.OrderItem;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.repository.OrderItemRepository;
import com.ecommerce.ecommerceplatform.service.mailing.MailServiceImplementation;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import com.stripe.param.TransferCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SellerPayoutService {

    private final OrderItemRepository orderItemRepository;
    private final MailServiceImplementation mailService;

    @Value("${currencyMultiplier}")
    private Long currencyMultiplier;

    @Autowired
    public SellerPayoutService(OrderItemRepository orderItemRepository, MailServiceImplementation mailService) {
        this.orderItemRepository = orderItemRepository;
        this.mailService = mailService;
    }

    @Scheduled(cron = "0 42 20 * * *")
    @Transactional
    public void payout() {
        System.err.println("Entered PayoutService");
        List<OrderItem> orderItems = orderItemRepository.findAll();
        for (OrderItem orderItem : orderItems) {
            if(orderItem.getDues() == null || orderItem.getDues() <= 0)
                continue;
            try {
                Long dues = orderItem.getDues();
                if (dues != null && dues > 0) {
                    dues *= currencyMultiplier;
                    Seller seller = orderItem.getProduct().getSeller();
                    TransferCreateParams params =
                        TransferCreateParams.builder()
                                .setAmount(dues)
                                .setCurrency(orderItem.getOrder().getCurrency())
                                .setDestination(seller.getPaymentAccountId())
                                .setTransferGroup("ORDER_" +  orderItem.getId())
                                .build();

                    Transfer transfer = Transfer.create(params);
                    //Logging
                    System.err.println("transfer result "  + transfer.toString());
                    System.out.println("Transferred " + dues + " to seller " + seller.getId());

                    orderItem.setDues(0L);
                    orderItemRepository.save(orderItem);
                    mailService.sendEmail(seller.getUser().getEmail(),"Payout Received Successfully",
                            "You received payout successfully for item with id " + orderItem.getId());
                }
            } catch (StripeException e) {
                System.err.println("Failed to transfer dues for order item " + orderItem.getId() + ": " + e.getMessage());
            }
        }
    }
}
