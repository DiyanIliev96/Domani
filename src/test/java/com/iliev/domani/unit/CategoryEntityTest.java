package com.iliev.domani.unit;

import com.iliev.domani.model.entity.CategoryEntity;
import com.iliev.domani.model.enums.CategoryNameEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryEntityTest {

    private CategoryEntity category;


    @BeforeEach
    void setUp() {
        category = new CategoryEntity()
                .setId(1L)
                .setName(CategoryNameEnum.Breakfast);
    }

    @Test
    void testCategoryEntity_getId() {
        Assertions.assertEquals(1L,category.getId());
    }

    @Test
    void testCategoryEntity_getName() {
        Assertions.assertEquals(CategoryNameEnum.Breakfast,category.getName());
    }

    @Test
    void testCategoryEntity_setId() {
        Assertions.assertEquals(1L,category.getId());
        category.setId(2L);
        Assertions.assertEquals(2L,category.getId());
    }
}
