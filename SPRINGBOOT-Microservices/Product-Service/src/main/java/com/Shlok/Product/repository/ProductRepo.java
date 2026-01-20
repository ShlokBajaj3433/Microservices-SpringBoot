package com.Shlok.Product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.Shlok.Product.model.Product;

public interface ProductRepo extends MongoRepository<Product, String> {

}
