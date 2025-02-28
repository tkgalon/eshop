package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository implements  GenericRepository<Product> {

    private List<Product> productData = new ArrayList<>();

    @Override
    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    @Override
    public Product update(Product updatedProduct) {
        for(Product existingProduct: productData) {
            if (existingProduct.getProductId().equals(updatedProduct.getProductId())) {
                existingProduct.setProductName(updatedProduct.getProductName());
                existingProduct.setProductQuantity(updatedProduct.getProductQuantity());
                return existingProduct;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        productData.removeIf(product -> product.getProductId().equals(id));
    }

    @Override
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    @Override
    public Product findById(String id) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst()
                .orElse(null);
    }


}
