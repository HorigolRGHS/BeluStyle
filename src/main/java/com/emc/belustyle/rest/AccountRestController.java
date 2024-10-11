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
}
