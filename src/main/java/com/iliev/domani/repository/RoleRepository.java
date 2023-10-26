package com.iliev.domani.repository;

import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.entity.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName(RoleNameEnum name);
}
