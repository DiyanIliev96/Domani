package com.iliev.domani.repository;

import com.iliev.domani.model.entity.ProductEntity;
import com.iliev.domani.model.enums.CategoryNameEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Page<ProductEntity> findAllByOrderByCategory_NameAscNameAsc(Pageable pageable);
    List<ProductEntity> findAllByCategory_Name(CategoryNameEnum category_name);
    @Query(value = "SELECT * FROM products ORDER BY RAND() LIMIT 3",nativeQuery = true)
    Optional<List<ProductEntity>> findRandomProducts();
}
