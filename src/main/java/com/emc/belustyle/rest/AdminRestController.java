package com.emc.belustyle.rest;

import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.dto.UserIdNameDTO;
import com.emc.belustyle.dto.ViewUserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserRole;
import com.emc.belustyle.service.UserRoleService;
import com.emc.belustyle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final UserService userService;
    private final UserRoleService userRoleService;

    @Autowired
    public AdminRestController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    // Endpoint to view all user details
    @GetMapping("/users")
    public ResponseEntity<Page<ViewUserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ViewUserDTO> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ViewUserDTO> getUserById(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (user != null) {
            ViewUserDTO viewUserDTO = new ViewUserDTO(
                    user.getUsername(),
                    user.getEmail(),
                    user.getEnable(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );
            return ResponseEntity.ok(viewUserDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (user != null) {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/search/fullname")
    public ResponseEntity<List<UserIdNameDTO>> searchUsersByFullName(@RequestParam String fullName) {
        List<UserIdNameDTO> users = userService.searchUsersByFullName(fullName);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            if (userService.findByEmail(userDTO.getEmail()) != null) {
                return ResponseEntity.badRequest().body(null);
            }
            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setFullName(userDTO.getFullName());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
            user.setPasswordHash(encoder.encode(userDTO.getPasswordHash()));

            user.setUserImage(userDTO.getUserImage());
            user.setEnable(userDTO.getEnable());

            UserRole role = userRoleService.findById(3);
            user.setRole(role);

            user.setCurrentPaymentMethod(userDTO.getCurrentPaymentMethod());
            user.setUserAddress(userDTO.getUserAddress());

            userService.createUser(user);
            return ResponseEntity.ok("Account staff created successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while creating a staff account.");
        }
    }

}
