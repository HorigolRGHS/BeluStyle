package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ViewInfoDTO;
import com.emc.belustyle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
    @RequestMapping("/api/staff")
    public class StaffRestController {

        @Autowired
        private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping({"/me", "/{username}"})
    public ResponseEntity<?> getStaffInfo(@PathVariable(required = false) String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        // Lấy tên người dùng hiện tại từ authentication
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        // Nếu không có username thì lấy thông tin của người dùng hiện tại
        if (username == null || username.isEmpty()) {
            username = currentUsername;
        } else if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
            // Nếu là admin, kiểm tra xem username có phải là STAFF không
            ViewInfoDTO staffInfo = userService.getUserInfoByUsername(username);
            if (staffInfo == null || !staffInfo.getRole().equals("STAFF")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You only have access to staff information.");
            }
        } else {
            // Nếu không phải admin và cố gắng xem thông tin của người khác
            if (!username.equals(currentUsername)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to view this information.");
            }
        }
        // Gọi userService để lấy thông tin staff
        ViewInfoDTO viewInfoDTO = userService.getUserInfoByUsername(username);
        if (viewInfoDTO != null) {
            return ResponseEntity.ok(viewInfoDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
}