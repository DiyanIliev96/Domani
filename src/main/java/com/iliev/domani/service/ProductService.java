package com.iliev.domani.service;

import com.iliev.domani.exception.ObjectNotFoundException;
import com.iliev.domani.model.dto.AddProductDto;
import com.iliev.domani.model.entity.CategoryEntity;
import com.iliev.domani.model.entity.ProductEntity;
import com.iliev.domani.model.enums.CategoryNameEnum;
import com.iliev.domani.model.view.ProductView;
import com.iliev.domani.repository.CategoryRepository;
import com.iliev.domani.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.categoryRepository = categoryRepository;
    }

    public void addProduct(AddProductDto addProductDto) throws IOException {
        ProductEntity newProduct = modelMapper.map(addProductDto, ProductEntity.class);
        CategoryEntity category = categoryRepository.findByName(addProductDto.getCategory())
                .orElseThrow(() -> new ObjectNotFoundException("Category with name: " + addProductDto.getCategory().name()
                        + " not found!"));
        String imageUrl = cloudinaryService.uploadImage(addProductDto.getImage());
        newProduct.setCategory(category)
                        .setImageUrl(imageUrl);
        productRepository.save(newProduct);
    }

    public List<ProductView> getAllProducts() {
        return productRepository.findAllByOrderByCategory_NameAscNameAsc()
                .stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductView.class))
                .toList();
    }

    public List<ProductView> getAllProductsByCategory (CategoryNameEnum name) {
        return productRepository.findAllByCategory_Name(name)
                .stream()
                .map(productEntity -> modelMapper.map(productEntity,ProductView.class))
                .toList();
    }
}
