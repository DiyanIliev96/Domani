package com.iliev.domani.model.view;

public class ProductView {

    private String name;
    private String price;

    private String categoryName;
    private String imageUrl;
    private String description;

    public String getName() {
        return name;
    }

    public ProductView setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public ProductView setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductView setDescription(String description) {
        this.description = description;
        return this;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public ProductView setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
