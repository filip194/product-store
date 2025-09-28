package com.demo.productstore.apisupport.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @JsonProperty("response")
    private T response;

    @JsonProperty("error")
    private ApiError error;

    public static <T> ApiResponse<T> with(T response) {
        return new ApiResponse<>(response, null);
    }

    public static <T> ApiResponse<T> withError(String message, String code) {
        return new ApiResponse<>(null, new ApiError(message, code));
    }
}
