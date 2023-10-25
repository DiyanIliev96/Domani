package com.iliev.domani.repository;

import com.iliev.domani.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
}
