package com.emc.belustyle.rest;

import com.emc.belustyle.dto.DiscountDTO;
import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.UserDiscount;
import com.emc.belustyle.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/discounts")
public class DiscountRestController {

    private final DiscountService discountService;
    private static final Logger logger = LoggerFactory.getLogger(DiscountRestController.class);

    @Autowired
    public DiscountRestController(DiscountService discountService) {
        this.discountService = discountService;
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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



    @GetMapping("/users/{userId}/discounts")
    public ResponseEntity<List<DiscountDTO>> getDiscountsByUserId(@PathVariable String userId) {
        List<DiscountDTO> discounts = discountService.getDiscountsByUserId(userId);
        return ResponseEntity.ok(discounts);
    }
}
