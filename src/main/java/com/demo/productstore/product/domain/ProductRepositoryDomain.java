package com.demo.productstore.product.domain;

import com.demo.productstore.product.model.Product;
import com.demo.productstore.product.model.ProductCreate;
import com.demo.productstore.product.model.ProductUpdate;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository interface for managing Product domain operations.
 */
public interface ProductRepositoryDomain {

    /**
     * Persists a new product in the repository.
     *
     * @param productCreate the product creation details
     * @return the persisted Product
     */
    Product persist(ProductCreate productCreate);

    /**
     * Finds a Product by its code.
     *
     * @param code the product code
     * @return an Optional containing the found Product, or empty if not found
     */
    Optional<Product> findByCode(String code);

    /**
     * Finds all Products with pagination.
     *
     * @param pageable the pagination information
     * @return a collection of Products for the specified page
     */
    Collection<Product> findAll(Pageable pageable);

    /**
     * Updates a Product by its code with the provided update details.
     *
     * @param code          the product code
     * @param productUpdate the product update details
     * @return an Optional containing the updated Product, or empty if not found
     */
    Optional<Product> updateByCode(String code, ProductUpdate productUpdate);

    /**
     * Soft deletes a Product by its code by setting the deleted timestamp.
     *
     * @param code the product code
     * @return an Optional containing the soft-deleted Product, or empty if not found
     */
    boolean deleteByCode(String code);

}
