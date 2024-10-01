package com.emc.belustyle.rest;


import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserRole;
import com.emc.belustyle.service.UserRoleService;
import com.emc.belustyle.service.UserService;
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

    private final UserService userService;
    private final UserRoleService userRoleService;

    @Autowired
    public UserRestController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        if (userService.findByEmail(userDTO.getEmail()) != null) {
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

        UserRole role = userRoleService.findById(userDTO.getRoleId());
        user.setRole(role);

        user.setCurrentPaymentMethod(userDTO.getCurrentPaymentMethod());
        user.setUserAddress(userDTO.getUserAddress());

        userService.createUser(user);

        return ResponseEntity.ok("Register successfully!");
    }
}
