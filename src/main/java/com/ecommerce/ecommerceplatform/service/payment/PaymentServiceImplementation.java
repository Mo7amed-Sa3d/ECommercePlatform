package com.ecommerce.ecommerceplatform.service.payment;

import com.ecommerce.ecommerceplatform.entity.Order;
import com.ecommerce.ecommerceplatform.entity.Seller;
import com.ecommerce.ecommerceplatform.utility.UserUtility;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.PaymentIntent;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImplementation implements PaymentService {

        private final UserUtility userUtility;
        @Value("${supportedCurrencies}")
            private List<String> supportedCurrencies;

            @Value("${currencyMultiplier}")
            private Long currencyMultiplier;

        public PaymentServiceImplementation(UserUtility userUtility) {
            this.userUtility = userUtility;
        }

        @Override
        public Account createSellerAccount() throws StripeException {
            AccountCreateParams params = AccountCreateParams.builder()
                    .setType(AccountCreateParams.Type.EXPRESS)
                    .build();

            return Account.create(params);
        }

        @Override
        public String generateOnboardingLink() throws StripeException {
            Seller seller = userUtility.getCurrentUser().getSeller();
            String accountId = seller.getPaymentAccountId();
            AccountLink link = AccountLink.create(
                    AccountLinkCreateParams.builder()
                            .setAccount(accountId)
                            .setRefreshUrl("https://yourapp.com/stripe/refresh")
                            .setReturnUrl("https://yourapp.com/stripe/complete")
                            .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                            .build()
            );
            return link.getUrl();
        }

        @Override
        public PaymentIntent createPaymentIntent(Order order) throws StripeException {

            if(!checkSupportedCurrencies(order.getCurrency()))
                throw new RuntimeException("Currency " + order.getCurrency() +" is not supported");

            Long totalAmount = order.getTotalAmount().multiply(BigDecimal.valueOf(currencyMultiplier)).longValue();
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                            .setAmount(totalAmount)
                            .setCurrency(order.getCurrency())
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                                            .build()
                            )
                            .putMetadata("orderId", String.valueOf(order.getId()))
                            .putMetadata("UserEmail",order.getUser().getEmail())
                            .putMetadata("UserFirstName",order.getUser().getFirstName())
                            .putMetadata("UserLastName",order.getUser().getLastName())
                            .build();

            return PaymentIntent.create(params);
        }

        private boolean checkSupportedCurrencies(String currency) {
            for(var curr : supportedCurrencies){
                if(curr.toUpperCase().equals(currency)) {
                    return true;
                }
            }
            return false;
        }

}
