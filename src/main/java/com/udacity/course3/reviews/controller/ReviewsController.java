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
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.exceptions.ProductNotFoundException;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

	@Autowired
	ProductRepository productRepository;
	

	@Autowired
	ReviewRepository reviewRepository;

	/**
	 * Creates a review for a product.
	 * <p>
	 * 1. Add argument for review entity. Use {@link RequestBody} annotation. 2.
	 * Check for existence of product. 3. If product not found, return NOT_FOUND. 4.
	 * If found, save review.
	 *
	 * @param productId The id of the product.
	 * @return The created review or 404 if product id is not found.
	 */
	@RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
	public ResponseEntity<?> createReviewForProduct(@Valid @RequestBody Review review,
			@PathVariable("productId") Integer productId) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
    	Product product = optionalProduct.orElseThrow(ProductNotFoundException::new);
		review.setProduct(product);
		Review createdReview = reviewRepository.save(review);
		return new ResponseEntity<Review>(createdReview, HttpStatus.CREATED);

	}

	/**
	 * Lists reviews by product.
	 *
	 * @param productId The id of the product.
	 * @return The list of reviews.
	 */
	@RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
	public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
		List<Review> reviews = reviewRepository.findReviewsByProductId(productId);
		return new ResponseEntity<List<?>>(reviews, HttpStatus.OK);
	}
}