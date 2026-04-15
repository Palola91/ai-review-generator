package se.ai.aireviewgenerator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.ai.aireviewgenerator.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}