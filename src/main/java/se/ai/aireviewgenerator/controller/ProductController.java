package se.ai.aireviewgenerator.controller;

import org.springframework.web.bind.annotation.*;
import se.ai.aireviewgenerator.model.Product;
import se.ai.aireviewgenerator.model.ProductRequest;
import se.ai.aireviewgenerator.repository.ProductRepository;
import se.ai.aireviewgenerator.service.AiReviewService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final AiReviewService aiReviewService;

    public ProductController(ProductRepository productRepository,
                             AiReviewService aiReviewService) {
        this.productRepository = productRepository;
        this.aiReviewService = aiReviewService;
    }

    // CREATE PRODUCT + AI REVIEWS
    @PostMapping
    public Object handleProduct(@RequestBody ProductRequest request) {

        if (request.getMode() == null) {
            return "Mode måste anges.";
        }

        switch (request.getMode()) {

            // Scenario A – endast produkt-ID
            case "productOnly" -> {
                Product product = new Product();
                product.setId(request.getProductId());

                productRepository.save(product);
                aiReviewService.generateReviews(product.getId());

                return product;
            }

            // Scenario B – produkt med detaljer
            case "withDetails" -> {
                Product product = new Product();
                product.setId(request.getProductId());
                product.setName(request.getProductName());
                product.setCategory(request.getCategory());
                product.setTags(request.getTags());

                productRepository.save(product);
                aiReviewService.generateReviews(product.getId());

                return product;
            }

            default -> {
                return "Ogiltigt mode.";
            }
        }
    }

    // READ PRODUCT
    @GetMapping("/{id}")
    public Object getProduct(@PathVariable String id) {
        return productRepository.findById(id)
                .orElse(null);
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public Object updateProduct(@PathVariable String id,
                                @RequestBody Product updatedProduct) {

        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setCategory(updatedProduct.getCategory());
                    product.setTags(updatedProduct.getTags());
                    return productRepository.save(product);
                })
                .orElse(null);
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {

        if (!productRepository.existsById(id)) {
            return "Produkt hittades inte.";
        }

        productRepository.deleteById(id);
        return "Produkt borttagen.";
    }
}