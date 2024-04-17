package com.iliev.domani.web;

import com.iliev.domani.model.dto.OrderDto;
import com.iliev.domani.model.enums.Currency;
import com.iliev.domani.model.enums.PaymentMethodEnum;
import com.iliev.domani.model.enums.PaymentStatusEnum;
import com.iliev.domani.service.CartItemService;
import com.iliev.domani.service.OrderService;
import com.iliev.domani.service.StripeService;
import com.iliev.domani.user.DomaniUserDetail;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartItemService cartItemService;

    private final StripeService stripeService;

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    public OrderController(OrderService orderService, CartItemService cartItemService, StripeService stripeService) {
        this.orderService = orderService;
        this.cartItemService = cartItemService;
        this.stripeService = stripeService;
    }


    @GetMapping("/order")
    public String getOrder(@AuthenticationPrincipal DomaniUserDetail domaniUserDetail, Model model) {
        if (cartItemService.getItemsByUserId(domaniUserDetail.getId()).isEmpty()) {
            return "redirect:/cart";
        }

        if (!model.containsAttribute("newOrder")) {
            model.addAttribute("newOrder", new OrderDto());
        }

        if (!model.containsAttribute("amount")) {
            String totalAmount = cartItemService.getShoppingCartTotalPrice(domaniUserDetail);
            double amountInCents = Double.parseDouble(totalAmount) * 100;
            int amountToInt = (int) amountInCents;
            model.addAttribute("amount", amountToInt);

        }
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", Currency.BGN);


        if (!model.containsAttribute("userEmail")) {
            model.addAttribute("userEmail", domaniUserDetail.getEmail());
        }


        return "order";
    }

    @PostMapping("/order")
    public String makeOrder(@Valid OrderDto orderDto, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal DomaniUserDetail domaniUserDetail) throws StripeException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newOrder", orderDto);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "newOrder", bindingResult);
            return "redirect:/order";
        }

        if (orderDto.getStripeToken() != null) {
            Charge charge = stripeService.charge(orderDto);
            if (charge.getPaid()) {
                orderDto.setPaymentStatus(PaymentStatusEnum.PAID);
                orderDto.setPaymentMethod(PaymentMethodEnum.CARD);
            }
        } else {
            orderDto.setPaymentMethod(PaymentMethodEnum.CASH);
            orderDto.setPaymentStatus(PaymentStatusEnum.NOT_PAID);
        }

        orderService.makeOrder(orderDto, domaniUserDetail.getId());
        return "redirect:/cart";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getStripeError().getMessage());
        return "charge-result";
    }
}
