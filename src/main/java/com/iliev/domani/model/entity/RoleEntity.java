package com.iliev.domani.model.entity;

import com.iliev.domani.model.enums.RoleNameEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private RoleNameEnum name;

    public RoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public RoleEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public RoleNameEnum getName() {
        return name;
    }

    public RoleEntity setName(RoleNameEnum name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
