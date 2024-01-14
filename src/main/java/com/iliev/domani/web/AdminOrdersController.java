package com.iliev.domani.web;

import com.iliev.domani.model.view.CartItemView;
import com.iliev.domani.model.view.OrderView;
import com.iliev.domani.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AdminOrdersController {

    private final OrderService orderService;

    public AdminOrdersController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/admin/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getOrders(Model model) {
        if (!model.containsAttribute("allOrders")) {
            List<OrderView> allOrders = orderService.getAllOrdersStatusPending();
            model.addAttribute("allOrders",allOrders);
        }

        return "orders-view";
    }

    @DeleteMapping("/admin/orders/delete/{id}/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteOrder(@PathVariable Long id, @PathVariable Long userId) {
        orderService.deleteOrder(id,userId);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders/approve/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String doApprove(@PathVariable Long orderId) {
        orderService.approveOrder(orderId);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders/{orderId}/details")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getOrderDetails(@PathVariable Long orderId,Model model) {
        if (!model.containsAttribute("orderItems")) {
            List<CartItemView> orderItemsDetails = orderService.getOrderItemsDetails(orderId);
            model.addAttribute("orderItems",orderItemsDetails);
            String orderItemsTotalPrice = orderItemsDetails.stream()
                    .map(CartItemView::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .toString();
            model.addAttribute("orderTotalPrice",orderItemsTotalPrice);
        }
        return "order-details";
    }
}
