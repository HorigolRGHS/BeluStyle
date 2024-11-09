package com.emc.belustyle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {
    private String user;
    private int rating;
    private String comment;
}
