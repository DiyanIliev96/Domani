package com.iliev.domani.repository;

import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName(RoleNameEnum name);
}
