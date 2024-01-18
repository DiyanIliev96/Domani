package com.iliev.domani.service;

import com.iliev.domani.exception.ObjectNotFoundException;
import com.iliev.domani.model.dto.AddProductDto;
import com.iliev.domani.model.dto.EditProductDto;
import com.iliev.domani.model.entity.CategoryEntity;
import com.iliev.domani.model.entity.ProductEntity;
import com.iliev.domani.model.enums.CategoryNameEnum;
import com.iliev.domani.model.view.ProductView;
import com.iliev.domani.repository.CategoryRepository;
import com.iliev.domani.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductView> getAllProducts(Pageable pageable) {
        return productRepository.findAllByOrderByCategory_NameAscNameAsc(pageable)
                .map(productEntity -> modelMapper.map(productEntity, ProductView.class));
    }

    public List<ProductView> getAllProductsByCategory (CategoryNameEnum name) {
        return productRepository.findAllByCategory_Name(name)
                .stream()
                .map(productEntity -> modelMapper.map(productEntity,ProductView.class))
                .toList();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public EditProductDto getProductToEdit(Long id) {
        return productRepository.findById(id).map(p -> modelMapper.map(p, EditProductDto.class))
                .orElseThrow(() -> new ObjectNotFoundException("Product with id:" + id + " not found"));
    }

    public void editProduct(String id, EditProductDto editProductDto) throws IOException {
        ProductEntity productEntity = productRepository.findById(Long.parseLong(id)).orElseThrow();
        if (!productEntity.getName().equals(editProductDto.getName())) {
            productEntity.setName(editProductDto.getName());
        }
        if (!productEntity.getCategory().getName().equals(editProductDto.getCategory())) {
            CategoryEntity newProductCategory = categoryRepository.findByName(editProductDto.getCategory())
                    .orElseThrow();
            productEntity.setCategory(newProductCategory);
        }
        if (!productEntity.getDescription().equals(editProductDto.getDescription())) {
            productEntity.setDescription(editProductDto.getDescription());
        }
        if (productEntity.getPrice().compareTo(editProductDto.getPrice()) != 0) {
            productEntity.setPrice(editProductDto.getPrice());
        }
        if (!editProductDto.getImage().isEmpty()) {
            String newProductImageUrl = cloudinaryService.uploadImage(editProductDto.getImage());
            productEntity.setImageUrl(newProductImageUrl);
        }
        productRepository.save(productEntity);
    }

    public List<ProductView> getRandomSpecialties() {

        List<ProductView> productsList = productRepository.findRandomProducts()
                .orElseThrow(() -> new ObjectNotFoundException("List with ProductEntities not found!"))
                .stream().map(p -> modelMapper.map(p,ProductView.class)).toList();

        if (productsList.isEmpty()) {
            throw new ObjectNotFoundException("Products not found in the list!");
        } else {
            return productsList;
        }

    }
}
