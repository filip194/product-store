package com.demo.productstore.apisupport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(title = "Product information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @Schema(name = "external_id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID externalId;

    @Schema(name = "code", example = "TrZ756klP0")
    private String code;

    @Schema(name = "name", example = "Keyboard")
    private String name;

    @Schema(name = "description", example = "Mechanical keyboard")
    private String description;

    @Schema(name = "price_eur", example = "89.99")
    private BigDecimal priceEur;

    @Schema(name = "price_usd", example = "99.99")
    private BigDecimal priceUsd;

    @Schema(name = "is_available", example = "true")
    private boolean isAvailable;

    @Schema(name = "created", example = "2025-10-05T14:48:00.000")
    private Timestamp created;

    @Schema(name = "updated", example = "2025-10-05T14:48:00.000")
    private Timestamp updated;

    @Schema(name = "deleted", example = "2025-10-05T14:48:00.000")
    private Timestamp deleted;

    public void setPriceEur(BigDecimal priceEur) {
        if (Objects.nonNull(priceEur)) {
            this.priceEur = priceEur.setScale(2, RoundingMode.HALF_UP);
        } else {
            this.priceEur = null;
        }
    }
}
