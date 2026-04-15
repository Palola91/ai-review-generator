package se.ai.aireviewgenerator.service;

import org.springframework.stereotype.Service;
import se.ai.aireviewgenerator.model.Review;
import se.ai.aireviewgenerator.repository.ReviewRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiReviewService {

    private static final int REVIEW_COUNT = 5;

    private final CatFactService catFactService;
    private final ReviewRepository reviewRepository;

    public AiReviewService(CatFactService catFactService,
                           ReviewRepository reviewRepository) {
        this.catFactService = catFactService;
        this.reviewRepository = reviewRepository;
    }

    // GENERATE AI REVIEWS
    public List<Review> generateReviews(String productId) {

        List<Review> existingReviews = reviewRepository.findByProductId(productId);
        List<Review> newReviews = new ArrayList<>();

        for (int i = 0; i < REVIEW_COUNT; i++) {

            String catFact = safeGetCatFact();

            Review review = new Review();
            review.setProductId(productId);
            review.setReviewerName("CatUser" + (i + 1));

            // Slumpmässigt betyg (1–5)
            int rating = (int) (Math.random() * 5) + 1;
            review.setRating(rating);


            String text;

            if (rating <= 2) {
                text = "Bad experience: " + catFact;
            } else if (rating == 3) {
                text = "Okay experience: " + catFact;
            } else {
                text = "Great experience: " + catFact;
            }

            review.setReviewText(text);
            review.setReviewDate(LocalDate.now());


            boolean exists = existingReviews.stream()
                    .anyMatch(r -> r.getReviewText().equals(review.getReviewText()));

            if (!exists) {
                newReviews.add(review);
            }
        }

        return reviewRepository.saveAll(newReviews);
    }

    // GET RECENT REVIEWS (last 2 months)
    public List<Review> getRecentReviews(String productId) {

        LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);

        return reviewRepository
                .findByProductIdAndReviewDateAfter(productId, twoMonthsAgo)
                .stream()
                .limit(10)
                .toList();
    }


    private String safeGetCatFact() {
        try {
            return catFactService.getCatFact();
        } catch (Exception e) {
            return "Could not fetch cat fact.";
        }
    }
}