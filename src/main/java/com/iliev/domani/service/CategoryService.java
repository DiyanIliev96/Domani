package com.iliev.domani.service;

import com.iliev.domani.model.entity.CategoryEntity;
import com.iliev.domani.model.enums.CategoryNameEnum;
import com.iliev.domani.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void initCategories() {
        if (categoryRepository.count() == 0) {
            List<CategoryEntity> categories = Arrays.stream(CategoryNameEnum.values())
                    .map(categoryNameEnum -> new CategoryEntity()
                            .setName(categoryNameEnum))
                    .toList();
            categoryRepository.saveAll(categories);
        }
    }
}
