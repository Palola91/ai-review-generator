package se.ai.aireviewgenerator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.ai.aireviewgenerator.model.Review;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    // Reviews för en produkt
    List<Review> findByProductId(String productId);

    // Reviews från 2 månader
    List<Review> findByProductIdAndReviewDateAfter(String productId, LocalDate date);
}