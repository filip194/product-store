package com.demo.productstore.apisupport.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Generic API response wrapper that can hold either a successful response or an error.
 *
 * @param <T> the type of the response data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @JsonProperty("response")
    private T response;

    @JsonProperty("error")
    private ApiError error;

    /** Static factory method to create a successful ApiResponse with the given response data.
     *
     * @param response the response data
     * @param <T>      the type of the response data
     * @return an ApiResponse containing the response data and no error
     */
    public static <T> ApiResponse<T> with(T response) {
        return new ApiResponse<>(response, null);
    }

    /** Static factory method to create an ApiResponse with an error.
     *
     * @param message the error message
     * @param code    the error code
     * @param <T>     the type of the response data
     * @return an ApiResponse containing no response data and the error details
     */
    public static <T> ApiResponse<T> withError(String message, String code) {
        return new ApiResponse<>(null, new ApiError(message, code));
    }
}
