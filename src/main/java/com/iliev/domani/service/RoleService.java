package com.iliev.domani.service;

import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.entity.RoleNameEnum;
import com.iliev.domani.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void initRoles() {
        if (roleRepository.count() == 0) {
            Arrays.stream(RoleNameEnum.values())
                    .map(roleNameEnum -> new RoleEntity().setName(roleNameEnum))
                    .forEach(roleRepository::save);
        }
    }
}
