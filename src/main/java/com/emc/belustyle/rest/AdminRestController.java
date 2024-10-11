package com.emc.belustyle.rest;

import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.dto.UserIdNameDTO;
import com.emc.belustyle.dto.ViewInfoDTO;
import com.emc.belustyle.dto.ViewUserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserRole;
import com.emc.belustyle.exception.CustomException;
import com.emc.belustyle.service.UserRoleService;
import com.emc.belustyle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createStaffAccount(userDTO);
            return ResponseEntity.ok("Account staff created successfully!");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while creating a staff account.");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ViewUserDTO>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ViewUserDTO> users = userService.getAllUser(page, size);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<ViewUserDTO> getUserById(@PathVariable String userId) {
        try {
            ViewUserDTO userDTO = userService.getUserById(userId);
            return ResponseEntity.ok(userDTO);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("User has been deleted successfully.");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/search")
    public ResponseEntity<List<UserIdNameDTO>> getAllUsers() {
        List<UserIdNameDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<?> getAdminInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        if (currentUsername != null) {
            User user = userService.findByUsername(currentUsername);
            if (user != null) {
                ViewInfoDTO viewInfoDTO = new ViewInfoDTO();
                viewInfoDTO.setUsername(user.getUsername());
                viewInfoDTO.setEmail(user.getEmail());
                viewInfoDTO.setFullName(user.getFullName());
                viewInfoDTO.setUserImage(user.getUserImage());
                viewInfoDTO.setEnable(user.getEnable());
                viewInfoDTO.setRole(String.valueOf(user.getRole().getRoleName()));
                viewInfoDTO.setCurrentPaymentMethod(user.getCurrentPaymentMethod());
                viewInfoDTO.setUserAddress(user.getUserAddress());
                viewInfoDTO.setCreatedAt(user.getCreatedAt());
                viewInfoDTO.setUpdatedAt(user.getUpdatedAt());

                return ResponseEntity.ok(viewInfoDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this information.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        try {
            userService.updateUserDetails(userId, updatedUser);
            return ResponseEntity.ok("User has been updated successfully.");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

}
