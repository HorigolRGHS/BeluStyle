package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ReviewDTO;
import com.emc.belustyle.entity.Review;
import com.emc.belustyle.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable String productId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/order-details")
    public ResponseEntity<List<Review>> getReviewsByOrderDetailId(@RequestParam Integer orderDetailId) {
        List<Review> reviews = reviewService.getReviewsByOrderDetailId(orderDetailId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review createdReview = reviewService.addReview(review);
        return ResponseEntity.ok(createdReview);
    }
}