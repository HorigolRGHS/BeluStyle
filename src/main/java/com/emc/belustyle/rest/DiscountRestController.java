package com.emc.belustyle.rest;

import com.emc.belustyle.dto.DiscountDTO;
import com.emc.belustyle.entity.UserDiscount;
import com.emc.belustyle.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/discounts")
public class DiscountRestController {

    private final DiscountService discountService;

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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable int discountId) {
        Optional<DiscountDTO> discountDTO = discountService.getDiscountById(discountId);
        return discountDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<DiscountDTO> updateDiscount(
            @PathVariable Integer discountId,
            @RequestBody DiscountDTO discountDTO) {
        Optional<DiscountDTO> updatedDiscount = discountService.updateDiscount(discountId, discountDTO);

        if (updatedDiscount.isPresent()) {
            return ResponseEntity.ok(updatedDiscount.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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
        Optional<DiscountDTO> discount = discountService.findDiscountByCode(discountCode);
        return discount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{discountId}/end")
    public ResponseEntity<DiscountDTO> endDiscount(@PathVariable int discountId) {
        Optional<DiscountDTO> discount = discountService.endDiscount(discountId);
        return discount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable int discountId) {
        discountService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }

    // Kiểm tra số lần sử dụng mã giảm giá
    @GetMapping("/check-usage")
    public ResponseEntity<Integer> checkUsage(
            @RequestParam("userId") String userId,
            @RequestParam("discountCode") String discountCode) {
        // Kiểm tra mã giảm giá có tồn tại không
        Optional<DiscountDTO> discountOpt = discountService.findDiscountByCode(discountCode);
        if (!discountOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0); // Trả về 0 nếu không tìm thấy mã giảm giá
        }
        int usageCount = discountService.checkDiscountUsage(userId, discountCode);
        return ResponseEntity.ok(usageCount);
    }
    @PostMapping("/{discountId}/users")
    public ResponseEntity<?> addUserToDiscount(@PathVariable Integer discountId, @RequestParam String userId) {
        try {
            discountService.addUserToDiscount(userId, discountId);
            return ResponseEntity.status(HttpStatus.CREATED).body("User added to discount.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{discountId}/users")
    public ResponseEntity<?> removeUserFromDiscount(@PathVariable Integer discountId, @RequestParam String userId) {
        discountService.removeUserFromDiscount(userId, discountId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{discountId}/users")
    public ResponseEntity<List<UserDiscount>> getUsersByDiscountId(@PathVariable Integer discountId) {
        List<UserDiscount> users = discountService.getUsersByDiscountId(discountId);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/users/{userId}/discounts")
    public ResponseEntity<List<DiscountDTO>> getDiscountsByUserId(@PathVariable String userId) {
        List<DiscountDTO> discounts = discountService.getDiscountsByUserId(userId);
        return ResponseEntity.ok(discounts);
    }
}