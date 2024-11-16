package com.emc.belustyle.rest;

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

    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviewsByProductId(@RequestParam String productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
//
//    @GetMapping
//    public ResponseEntity<List<Review>> getReviewsByOrderDetailId(@RequestParam Integer orderDetailId) {
//        List<Review> reviews = reviewService.getReviewsByOrderDetailId(orderDetailId);
//        return ResponseEntity.ok(reviews);
//    }

    @GetMapping("/orderdetail")
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