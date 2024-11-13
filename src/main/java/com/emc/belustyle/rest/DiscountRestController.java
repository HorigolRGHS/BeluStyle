package com.emc.belustyle.rest;

import com.emc.belustyle.dto.DiscountDTO;
import com.emc.belustyle.dto.DiscountUserDTO;
import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.UserDiscount;
import com.emc.belustyle.service.DiscountService;
import com.emc.belustyle.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discounts")
public class DiscountRestController {

    private final DiscountService discountService;
    private static final Logger logger = LoggerFactory.getLogger(DiscountRestController.class);
    private final JwtUtil jwtUtil;

    @Autowired
    public DiscountRestController(DiscountService discountService, JwtUtil jwtUtil) {
        this.discountService = discountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> createDiscount(@RequestBody DiscountDTO discountDTO) {
        try {
            DiscountDTO createdDiscount = discountService.addDiscount(discountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to create discount: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable int discountId) {
        return discountService.getDiscountById(discountId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Discount ID {} not found.", discountId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<DiscountDTO> updateDiscount(
            @PathVariable Integer discountId,
            @RequestBody DiscountDTO discountDTO) {
        return discountService.updateDiscount(discountId, discountDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Discount ID {} not found for update.", discountId);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<Page<DiscountDTO>> getAllDiscounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<DiscountDTO> discounts = discountService.getAllDiscounts(page, size);
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/search")
    public ResponseEntity<DiscountDTO> findDiscountByCode(@RequestParam("code") String discountCode) {
        return discountService.findDiscountByCode(discountCode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Discount with code {} not found.", discountCode);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{discountId}/end")
    public ResponseEntity<DiscountDTO> endDiscount(@PathVariable int discountId) {
        Optional<DiscountDTO> discountOpt = discountService.getDiscountById(discountId);

        if (discountOpt.isPresent()) {
            if (discountOpt.get().getDiscountStatus().equals("EXPIRED")) {
                logger.info("Discount ID {} is already expired.", discountId);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(discountOpt.get());
            } else {
                return discountService.endDiscount(discountId)
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
            }
        }
        logger.warn("Discount ID {} not found for ending.", discountId);
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable int discountId) {
        discountService.deleteDiscount(discountId);
        logger.info("Deleted discount ID {}", discountId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-usage")
    public ResponseEntity<Integer> checkUsage(
            @RequestParam("userId") String userId,
            @RequestParam("discountCode") String discountCode) {
        Optional<DiscountDTO> discountOpt = discountService.findDiscountByCode(discountCode);
        if (!discountOpt.isPresent()) {
            logger.warn("Discount code {} not found for user {}", discountCode, userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
        int usageCount = discountService.checkDiscountUsage(userId, discountCode);
        return ResponseEntity.ok(usageCount);
    }

    @PostMapping("/{discountId}/users")
    public ResponseEntity<?> addUsersToDiscount(@PathVariable Integer discountId, @RequestBody List<String> userIds) {
        try {
            discountService.addUsersToDiscount(userIds, discountId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Users added to discount.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


    @DeleteMapping("/{discountId}/users")
    public ResponseEntity<?> removeUserFromDiscount(@PathVariable Integer discountId, @RequestParam String userId) {
        discountService.removeUserFromDiscount(userId, discountId);
        logger.info("Removed user {} from discount ID {}", userId, discountId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{discountId}/users")
    public ResponseEntity<Map<String, Object>> getUsersByDiscountId(@PathVariable Integer discountId) {
        Map<String, Object> usersInfo = discountService.getUsersByDiscountId(discountId);
        return ResponseEntity.ok(usersInfo);
    }



    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getDiscountsByUserId(@PathVariable String userId) {
        List<DiscountDTO> discounts = discountService.getDiscountsByUserId(userId);

        // Chuyển đổi DiscountDTO thành dạng bạn yêu cầu
        List<Map<String, Object>> myDiscounts = discounts.stream().map(discount -> {
            Map<String, Object> discountMap = new HashMap<>();
            discountMap.put("discountCode", discount.getDiscountCode());
            discountMap.put("discountType", discount.getDiscountType());
            discountMap.put("discountValue", discount.getDiscountValue());
            discountMap.put("usageCount", discount.getUsageCount());
            discountMap.put("endDate", discount.getEndDate().toString());  // Chuyển đổi date thành định dạng string

            return discountMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(myDiscounts);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/me")
    public ResponseEntity<?> getDiscountsByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            currentUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        List<DiscountDTO> userDiscounts = discountService.getDiscountsByUsername(currentUsername);
        return ResponseEntity.ok().body(userDiscounts);
    }


    // Phương thức phụ để giải mã userId từ token
    private String extractUserIdFromToken(String token) {
        return jwtUtil.extractSubject(token);
    }
}