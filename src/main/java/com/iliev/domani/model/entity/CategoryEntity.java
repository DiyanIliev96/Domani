package com.iliev.domani.model.entity;

import com.iliev.domani.model.enums.CategoryNameEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryNameEnum name;

    public CategoryEntity() {
    }

    public Long getId() {
        return id;
    }

    public CategoryEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryNameEnum getName() {
        return name;
    }

    public CategoryEntity setName(CategoryNameEnum name) {
        this.name = name;
        return this;
    }
}
