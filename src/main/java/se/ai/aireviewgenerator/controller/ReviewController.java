package se.ai.aireviewgenerator.controller;

import org.springframework.web.bind.annotation.*;
import se.ai.aireviewgenerator.model.Review;
import se.ai.aireviewgenerator.repository.ReviewRepository;
import se.ai.aireviewgenerator.service.AiReviewService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final AiReviewService aiReviewService;
    private final ReviewRepository reviewRepository;

    public ReviewController(AiReviewService aiReviewService,
                            ReviewRepository reviewRepository) {
        this.aiReviewService = aiReviewService;
        this.reviewRepository = reviewRepository;
    }

    // GET reviews
    @GetMapping("/{productId}")
    public List<Review> getReviews(@PathVariable String productId) {
        return aiReviewService.getRecentReviews(productId);
    }

    // POST review
    @PostMapping
    public Object createReview(@RequestBody Review review) {

        // Validering
        if (review.getRating() < 1 || review.getRating() > 5) {
            return "Rating måste vara mellan 1 och 5";
        }

        review.setReviewDate(LocalDate.now());

        return reviewRepository.save(review);
    }

    // DELETE review
    @DeleteMapping("/{id}")
    public Object deleteReview(@PathVariable String id) {

        if (!reviewRepository.existsById(id)) {
            return "Review hittades inte";
        }

        reviewRepository.deleteById(id);
        return "Review borttagen";
    }
}