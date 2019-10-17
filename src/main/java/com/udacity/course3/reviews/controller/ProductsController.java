package com.udacity.course3.reviews.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.exceptions.ProductNotFoundException;
import com.udacity.course3.reviews.repository.ProductRepository;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

	
	@Autowired
	ProductRepository productRepository;
	
    /**
     * Creates a product.
     *
     * 1. Accept product as argument. Use {@link RequestBody} annotation.
     * 2. Save product.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
   
    ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
    	Product createdProduct = productRepository.save(product);
    	return new ResponseEntity<Product>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
    	Optional<Product> optionalProduct = productRepository.findById(id);
    	Product product = optionalProduct.orElseThrow(ProductNotFoundException::new);
    	return new ResponseEntity<Product>(product, HttpStatus.OK);

    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listProducts() {
    	List<Product> products = (List<Product>) productRepository.findAll();
        return new ResponseEntity<List<?>>(products, HttpStatus.OK);
    }
}