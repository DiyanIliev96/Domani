package com.iliev.domani.repository;

import com.iliev.domani.model.entity.CategoryEntity;
import com.iliev.domani.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    Optional<CategoryEntity> findByName(CategoryNameEnum name);
}
