package se.ai.aireviewgenerator.model;

import lombok.Data;
import java.util.List;

@Data
public class ProductRequest {

    private String mode;
    private String productId;
    private String productName;
    private String category;
    private List<String> tags;

    private ReviewData review;

    @Data
    public static class ReviewData {
        private String name;
        private String text;
        private int rating;
    }
}