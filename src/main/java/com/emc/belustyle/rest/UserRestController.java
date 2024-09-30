package com.emc.belustyle.rest;

import com.emc.belustyle.dao.UserRepository;
import com.emc.belustyle.dao.UserRoleRepository;
import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRestController(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email đã tồn tại!");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());

        // Hashing password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
        user.setPasswordHash(encoder.encode(userDTO.getPasswordHash()));

        user.setUserImage(userDTO.getUserImage());
        user.setEnable(userDTO.getEnable());

        UserRole role = userRoleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Not found the role with ID: " + userDTO.getRoleId()));
        user.setRole(role);

        user.setCurrentPaymentMethod(userDTO.getCurrentPaymentMethod());
        user.setUserAddress(userDTO.getUserAddress());

        userRepository.save(user);

        return ResponseEntity.ok("Register successfully!");
    }
}
