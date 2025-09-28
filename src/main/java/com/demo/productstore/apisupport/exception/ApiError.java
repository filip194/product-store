package com.demo.productstore.apisupport.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private String code;
}
