package com.iliev.domani.web;

import com.iliev.domani.model.dto.CartItemDto;
import com.iliev.domani.model.view.CartItemView;
import com.iliev.domani.service.CartItemService;
import com.iliev.domani.user.DomaniUserDetail;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class CartController {

    private final CartItemService cartItemService;

    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;

    }

    @GetMapping("/cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getShoppingCart(Model model, @AuthenticationPrincipal DomaniUserDetail domaniUserDetail) {
        if (!model.containsAttribute("items")) {
            String shoppingCartTotalPrice = getShoppingCartTotalPrice(domaniUserDetail);
            setShoppingCartTotalPrice(model, shoppingCartTotalPrice);
            model.addAttribute("items",cartItemService.getItemsByUserId(domaniUserDetail.getId()));
        }
        return "shopping-cart";
    }

    private static void setShoppingCartTotalPrice(Model model, String shoppingCartTotalPrice) {
        if (shoppingCartTotalPrice.equals("0")) {
            model.addAttribute("shoppingCartTotalPrice","0.00");
        } else {
            model.addAttribute("shoppingCartTotalPrice", shoppingCartTotalPrice);
        }
    }

    private String getShoppingCartTotalPrice(DomaniUserDetail domaniUserDetail) {
        return cartItemService.getItemsByUserId(domaniUserDetail.getId())
                .stream().map(CartItemView::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toString();
    }

    @PostMapping("/cart/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String addToCart(@Valid CartItemDto cartItemDto, BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            , @AuthenticationPrincipal DomaniUserDetail domaniUserDetail
            , @RequestParam(name = "category") String category
            ,Model model) {

        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                FieldError fieldError = (FieldError) object;
                String errorMessage = fieldError.getDefaultMessage();
                if (fieldError.getCode().contains("typeMismatch")) {
                    errorMessage = "Quantity must be a number.";
                }
                model.addAttribute("errorMessage",errorMessage);
            }
            return "error/cart-error";
        }

        cartItemService.addToCart(cartItemDto.getProductId(),domaniUserDetail.getId(),cartItemDto.getQuantity());
        return "redirect:/menu?category=" + category;
    }

    @DeleteMapping("/cart/delete/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String deleteFromCart(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return "redirect:/cart";
    }

    @DeleteMapping("/cart/delete/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String deleteAllFromCart(@AuthenticationPrincipal DomaniUserDetail domaniUserDetail) {
        cartItemService.deleteAllCartItems(domaniUserDetail.getId());
    return "redirect:/cart";
    }

}
