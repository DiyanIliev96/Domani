package com.iliev.domani.repository;

import com.iliev.domani.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findAllByOrderByCategory_NameAscNameAsc();
}
