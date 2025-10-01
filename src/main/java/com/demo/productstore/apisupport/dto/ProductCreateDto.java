package com.demo.productstore.apisupport.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(title = "Product create information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {

    @Size(min = 10, max = 10, message = "Product code should be 10 characters long")
    @Schema(name = "code", example = "TrZ756klP0")
    private String code;

    @Size(min = 1, max = 255, message = "Product name should be between 1 and 255 characters long")
    @Schema(name = "name", example = "Keyboard")
    private String name;

    @Size(min = 1, max = 255, message = "Product description should be between 1 and 255 characters long")
    @Schema(name = "description", example = "Mechanical keyboard")
    private String description;

    @DecimalMin(value = "0.0", message = "priceEur must be equal to or greater than zero")
    @Schema(name = "price_eur", example = "89.99")
    private BigDecimal priceEur;

    @Schema(name = "is_available", example = "true")
    private boolean isAvailable;

    public void setPriceEur(BigDecimal priceEur) {
        if (Objects.nonNull(priceEur)) {
            this.priceEur = priceEur.setScale(2, RoundingMode.HALF_UP);
        } else {
            this.priceEur = null;
        }
    }
}
