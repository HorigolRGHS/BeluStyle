package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ViewInfoDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.emc.belustyle.util.JwtUtil;

@RestController
@RequestMapping("/api/customer/info")
public class AccountRestController {

    private final UserService userService;

    public AccountRestController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/me")
    public ResponseEntity<?> getCustomerInfo() {
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
}
