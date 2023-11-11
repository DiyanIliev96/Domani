package com.iliev.domani.model.dto;

import com.iliev.domani.model.enums.CategoryNameEnum;
import com.iliev.domani.validation.ImageUnder1MB;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class EditProductDto {

    private Long productId;

    @NotBlank
    @Size(min = 2,max = 100)
    private String name;

    @NotNull(message = "You must select category!")
    private CategoryNameEnum category;
    @NotBlank(message = "Description cannot be blank!")
    private String description;
    @Positive(message = "Price must be positive!")
    @NotNull(message = "Price cannot be empty!")
    private BigDecimal price;
    @ImageUnder1MB
    private MultipartFile image;

    public EditProductDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public EditProductDto setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public EditProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public EditProductDto setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditProductDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EditProductDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public EditProductDto setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}
