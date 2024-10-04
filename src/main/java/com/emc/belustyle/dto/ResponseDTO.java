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
}

