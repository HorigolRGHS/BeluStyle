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

@RestController
@RequestMapping("/api/account")
public class AccountRestController {

    private final UserService userService;

    public AccountRestController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER','STAFF','ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        ViewInfoDTO viewInfoDTO = userService.getUserInfoByUsername(currentUsername);
        if (viewInfoDTO != null) {
            return ResponseEntity.ok(viewInfoDTO);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/{userId}")
    public ResponseEntity<?> getStaffInfoById(@PathVariable String userId) {
        ViewInfoDTO viewInfoDTO = userService.getUserInfoById(userId);
        if (viewInfoDTO != null && viewInfoDTO.getRole().equals("STAFF")) {
            return ResponseEntity.ok(viewInfoDTO);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only view staff information.");
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateUserInfo(@RequestBody User updatedUserInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        User currentUser = userService.findByUsername(currentUsername);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found.");
        }
        if (!currentUser.getUserId().equals(updatedUserInfo.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot update userId or other restricted fields.");
        }
        User updated = userService.updateUserInfo(updatedUserInfo);
        if (updated != null) {
            return ResponseEntity.ok("User information updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update user information.");
        }
    }
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/request-delete")
    public ResponseEntity<?> requestDeleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        User currentUser = userService.findByUsername(currentUsername);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        boolean success = userService.disableAccount(currentUser.getUserId());

        if (success) {
            return ResponseEntity.ok("Account has been disabled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to disable the account.");
        }
    }

}

