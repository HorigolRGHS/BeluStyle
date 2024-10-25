package com.emc.belustyle.rest;

import com.emc.belustyle.dto.DiscountDTO;
import com.emc.belustyle.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @PostMapping
    public ResponseEntity<?> createDiscount(@RequestBody DiscountDTO discountDTO) {
        try {
            DiscountDTO createdDiscount = discountService.addDiscount(discountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // Trả về thông báo lỗi nếu discount code đã tồn tại
        }
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/{discountId}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable int discountId) {
        Optional<DiscountDTO> discountDTO = discountService.getDiscountById(discountId);
        return discountDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @GetMapping
    public ResponseEntity<Page<DiscountDTO>> getAllDiscounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<DiscountDTO> discounts = discountService.getAllDiscounts(page, size);
        return ResponseEntity.ok(discounts);
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    public ResponseEntity<DiscountDTO> findDiscountByCode(@RequestParam String discountCode) {
        Optional<DiscountDTO> discountDTO = discountService.findDiscountByCode(discountCode);
        return discountDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF')")
    @PutMapping("/{discountId}/end")
    public ResponseEntity<DiscountDTO> endDiscount(@PathVariable int discountId) {
        Optional<DiscountDTO> endedDiscount = discountService.endDiscount(discountId);
        return endedDiscount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // API xóa discount
    @DeleteMapping("/{discountId}")
    public ResponseEntity<String> deleteDiscount(@PathVariable Integer discountId) {
        if (discountService.deleteDiscount(discountId)) {
            return ResponseEntity.ok("Discount has been successfully deleted."); // Trả về thông báo thành công
        } else {
            return ResponseEntity.notFound().build(); // Trả về 404 Not Found nếu không tìm thấy Discount
        }
    }
}

