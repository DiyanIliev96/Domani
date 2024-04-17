package com.iliev.domani.service;

import com.iliev.domani.model.dto.OrderDto;
import com.iliev.domani.model.enums.Currency;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public Charge charge(OrderDto orderDto) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount",orderDto.getOrderAmount());
        chargeParams.put("currency", Currency.BGN);
        chargeParams.put("source",orderDto.getStripeToken());
        return Charge.create(chargeParams);
    }
}
