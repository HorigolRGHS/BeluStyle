package com.emc.belustyle.rest;

import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.dto.UserIdNameDTO;
import com.emc.belustyle.dto.ViewUserDTO;
import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserRole;
import com.emc.belustyle.service.UserRoleService;
import com.emc.belustyle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<Page<ViewUserDTO>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ViewUserDTO> users = userService.getAllUser(page, size);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (user != null) {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found.");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/search")
    public ResponseEntity<List<UserIdNameDTO>> getAllUsers() {
        List<UserIdNameDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            Optional<User> existingUser = userService.findByEmail(userDTO.getEmail());

            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists!");
            }

            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setFullName(userDTO.getFullName());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
            user.setPasswordHash(encoder.encode(userDTO.getPasswordHash()));

            user.setUserImage(userDTO.getUserImage());
            user.setEnable(true);

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
