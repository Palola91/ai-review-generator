package se.ai.aireviewgenerator.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;
    private String productId;
    private String reviewerName;
    private int rating;
    private String reviewText;
    private LocalDate reviewDate;
}