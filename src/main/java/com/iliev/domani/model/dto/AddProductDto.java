package com.iliev.domani.model.dto;

import com.iliev.domani.model.enums.CategoryNameEnum;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddProductDto {

    private String name;

    private CategoryNameEnum category;

    private String description;

    private BigDecimal price;

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
