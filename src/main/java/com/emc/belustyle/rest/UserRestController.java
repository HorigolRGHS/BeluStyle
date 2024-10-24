package com.emc.belustyle.rest;



import com.emc.belustyle.dto.UpdateUserDTO;
import com.emc.belustyle.dto.ViewInfoDTO;
import com.emc.belustyle.service.UserRoleService;
import com.emc.belustyle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


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
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        UpdateUserDTO updateUserDTO = userService.getCustomerInfoByUsername(currentUsername);
            if (updateUserDTO != null) {
                return ResponseEntity.ok(updateUserDTO);
            }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found.");
    }
}
