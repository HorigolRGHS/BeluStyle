package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ViewInfoDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.emc.belustyle.util.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer/info")
public class AccountRestController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AccountRestController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/{userId}")
    public ResponseEntity<ViewInfoDTO> getUserById(@PathVariable String userId, @RequestHeader("Authorization") String tokenHeader){

        final String token = tokenHeader.replace("Bearer ", "");
        String currentUsername = jwtUtil.extractUsername(token);

        Optional<User> currentUser = userService.findByUsername(currentUsername);
        if (currentUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!currentUser.get().getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        if (!currentUser.get().getRole().getRoleName().equals("CUSTOMER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        ViewInfoDTO userDTO = new ViewInfoDTO(
                currentUser.get().getUsername(),
                currentUser.get().getEmail(),
                currentUser.get().getFullName(),
                currentUser.get().getUserImage(),
                currentUser.get().getCurrentPaymentMethod(),
                currentUser.get().getUserAddress(),
                currentUser.get().getCreatedAt(),
                currentUser.get().getUpdatedAt()
        );

        return ResponseEntity.ok(userDTO);
    }
}
