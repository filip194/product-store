package com.demo.productstore.product.db;

import com.demo.productstore.currency.model.Price;
import com.demo.productstore.product.model.ProductCode;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
public class ProductEntity {

    public ProductEntity(UUID externalId, ProductCode code, String name, String description, Price priceEur, Price priceUsd, boolean isAvailable) {
        this.externalId = externalId;
        this.code = code.value();
        this.name = name;
        this.description = description;
        this.priceEur = priceEur.value();
        this.priceUsd = priceUsd.value();
        this.isAvailable = isAvailable;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_product_generator")
    @SequenceGenerator(name = "pk_product_generator", sequenceName = "pk_product_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "external_id")
    private UUID externalId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price_eur")
    private BigDecimal priceEur;

    @Column(name = "price_usd")
    private BigDecimal priceUsd;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    @Column(name = "deleted")
    private Timestamp deleted;

}
