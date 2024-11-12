package com.emc.belustyle.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private int statusCode;
    private String message;
    private String token;
    private String expirationTime;
    private UserDTO user;

    // Constructor với statusCode và message
    public ResponseDTO(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // Constructor không tham số (mặc định)
    public ResponseDTO() {}
}
