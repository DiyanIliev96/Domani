package com.iliev.domani.model.dto;

import com.iliev.domani.model.enums.CategoryNameEnum;
import com.iliev.domani.validation.ImageUnder1MB;
import com.iliev.domani.validation.NotNullImage;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddProductDto {
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
    @NotNullImage
    private MultipartFile image;

    public AddProductDto() {
    }

    public String getName() {
        return name;
    }

    public AddProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public AddProductDto setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddProductDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddProductDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public AddProductDto setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}
