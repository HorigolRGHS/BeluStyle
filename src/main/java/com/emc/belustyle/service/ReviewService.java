package com.emc.belustyle.service;

import com.emc.belustyle.dto.ReviewDTO;
import com.emc.belustyle.entity.Review;
import com.emc.belustyle.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAlls();
    }

    public List<ReviewDTO> getReviewsByProductId(String productId) {
        return reviewRepository.findReviewDetails(productId);
    }

public List<Review> getReviewsByOrderDetailId(Integer orderDetailId) {
    return reviewRepository.findReviewsByOrderDetailId(orderDetailId);
}

    public Review addReview(Review review) {
        review.setCreatedAt(new Date());
        return reviewRepository.save(review);
    }
}
