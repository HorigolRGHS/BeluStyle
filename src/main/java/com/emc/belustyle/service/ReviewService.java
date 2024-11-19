package com.emc.belustyle.service;

import com.emc.belustyle.dto.ReviewDTO;
import com.emc.belustyle.entity.Product;
import com.emc.belustyle.entity.Review;
import com.emc.belustyle.repo.OrderDetailRepository;
import com.emc.belustyle.repo.ProductRepository;
import com.emc.belustyle.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private OrderDetailRepository orderDetailRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, OrderDetailRepository orderDetailRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAlls();
    }

    public List<ReviewDTO> getReviewsByProductId(String productId) {
        return reviewRepository.findReviewDetails(productId);
    }

        public List<Review> getReviewsByOrderDetailId(Integer orderDetailId) {
    return reviewRepository.findReviewsByOrderDetailId(orderDetailId);
    }

    public Review addReview(ReviewDTO review) {
        Review newReview = new Review();
        newReview.setReviewComment(review.getReviewComment());
        newReview.setReviewRating(review.getReviewRating());
        newReview.setProduct(productRepository.findById(review.getProductId()).orElse(null));
        newReview.setOrderDetail(orderDetailRepository.findById(review.getOrderDetailId()).orElse(null));
        return reviewRepository.save(newReview);
    }
}
