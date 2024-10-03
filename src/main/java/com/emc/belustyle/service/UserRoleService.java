package com.emc.belustyle.service;

import com.emc.belustyle.repo.UserRoleRepository;
import com.emc.belustyle.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    public UserRole findById(int id) {
        return userRoleRepository.findById(id).orElse(null);
    }

    @Transactional
    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Transactional
    public UserRole updateUserRole(Integer id, UserRole updatedUserRole) {
        Optional<UserRole> existingUserRole = userRoleRepository.findById(id);

        if (existingUserRole.isPresent()) {
            UserRole userRole = existingUserRole.get();
            userRole.setRoleName(updatedUserRole.getRoleName());
            return userRoleRepository.save(userRole);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteUserRole(Integer id) {
        userRoleRepository.deleteById(id);
    }
}
