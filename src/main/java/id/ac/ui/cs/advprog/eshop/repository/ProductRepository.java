package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository // Memberitahu Spring kalau ini adalah komponen repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    // Method menambahkan produk ke dalam repository
    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    // Method untuk mengambil semua produk dalam repository
    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
