package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdExistingProduct() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Produk Ada");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("123");

        assertNotNull(foundProduct);
        assertEquals("123", foundProduct.getProductId());
    }
    @Test
    void testFindByIdNonExistentProduct() {
        Product foundProduct = productRepository.findById("GAADA BRO");
        assertNull(foundProduct);
    }



    @Test
    void testUpdateProductSuccess() {
        Product product = new Product();
        product.setProductId("12345");
        product.setProductName("Nama lama Tono");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("12345");
        updatedProduct.setProductName("Nama baru Tono");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals("Nama baru Tono", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testUpdateProductNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("99999");
        updatedProduct.setProductName("Hanyalah fiktif belaka");
        updatedProduct.setProductQuantity(30);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testUpdateProductWithNullId() {
        Product product = new Product();
        product.setProductId("valid-id");
        product.setProductName("Produk Awal");
        product.setProductQuantity(50);
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("");
        product2.setProductName("Produk Baru");
        product2.setProductQuantity(20);

        Product editedProduct = productRepository.update(product2);
        assertNull(editedProduct);
    }


    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductId("54321");
        product.setProductName("Pengen di-delete");
        product.setProductQuantity(5);
        productRepository.create(product);

        productRepository.delete("54321");

        Product deletedProduct = productRepository.findById("54321");
        assertNull(deletedProduct);
    }

    @Test
    void testDeleteProductNotFound() {
        productRepository.delete("00000"); // Trying to delete non-existent product
        assertNull(productRepository.findById("00000"));
    }

}