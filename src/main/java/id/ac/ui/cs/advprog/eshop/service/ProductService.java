package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;


public interface ProductService {
    Product create(Product product);
    Product update(Product product);
    void delete(String id);
    List<Product> findAll();
    Product findById(String id);
}
