package com.emc.belustyle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductReviewDTO {
    private int reviewId;
    private String fullName;
    private int reviewRating;
    private String reviewComment;
}
