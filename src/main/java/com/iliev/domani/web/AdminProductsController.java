package com.iliev.domani.web;

import com.iliev.domani.model.dto.AddProductDto;
import com.iliev.domani.model.dto.EditProductDto;
import com.iliev.domani.model.view.ProductView;
import com.iliev.domani.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminProductsController {

    private final ProductService productService;

    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/menu/management")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String getMenuManagement(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<ProductView> allProducts = productService.getAllProducts(pageable);
        int totalPages = allProducts.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages - 1).boxed().toList();
        model.addAttribute("pageNumbers",pageNumbers);
        model.addAttribute("allProducts",allProducts);
        return "menu-management";
    }

    @GetMapping("/menu/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String getAddProduct(Model model) {
        if (!model.containsAttribute("newProduct")) {
            model.addAttribute("newProduct",new AddProductDto());
        }
        return "add-menu-item";
    }

    @PostMapping("/menu/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String addProduct(@Valid AddProductDto addProductDto, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newProduct",addProductDto);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "newProduct",bindingResult);
            return "redirect:/admin/menu/add";
        }
        productService.addProduct(addProductDto);
        return "redirect:/admin/menu/management";
    }

    @DeleteMapping("/menu/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/menu/management";
    }

    @GetMapping("/menu/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String getEdit(@PathVariable Long id,Model model) {
        if (!model.containsAttribute("editProduct")) {
            EditProductDto productToEdit = productService.getProductToEdit(id);
            model.addAttribute("editProduct",productToEdit);
        }
        return "edit-product";
    }

    @PatchMapping("/menu/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String doEdit(@Valid EditProductDto editProductDto,BindingResult bindingResult,
            RedirectAttributes redirectAttributes,@PathVariable String id) throws IOException {
        if (bindingResult.hasErrors()) {
            editProductDto.setProductId(Long.parseLong(id));
            redirectAttributes.addFlashAttribute("editProduct",editProductDto);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "editProduct",bindingResult);
            return "redirect:/admin/menu/edit/" + id;
        }

        productService.editProduct(id,editProductDto);
        return "redirect:/admin/menu/management";
    }
}
