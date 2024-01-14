package com.iliev.domani.web;

import com.iliev.domani.model.dto.OrderDto;
import com.iliev.domani.service.CartItemService;
import com.iliev.domani.service.OrderService;
import com.iliev.domani.user.DomaniUserDetail;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartItemService cartItemService;

    public OrderController(OrderService orderService, CartItemService cartItemService) {
        this.orderService = orderService;
        this.cartItemService = cartItemService;
    }


    @GetMapping("/order")
    public String getOrder(@AuthenticationPrincipal DomaniUserDetail domaniUserDetail, Model model) {
        if (cartItemService.getItemsByUserId(domaniUserDetail.getId()).isEmpty()) {
            return "redirect:/cart";
        }

        if (!model.containsAttribute("newOrder")) {
            model.addAttribute("newOrder",new OrderDto());
        }

        return "order";
    }

    @PostMapping("/order")
    public String makeOrder(@Valid OrderDto orderDto, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal DomaniUserDetail domaniUserDetail) {

        orderService.makeOrder(orderDto,domaniUserDetail.getId());
        return "redirect:/cart";
    }
}
