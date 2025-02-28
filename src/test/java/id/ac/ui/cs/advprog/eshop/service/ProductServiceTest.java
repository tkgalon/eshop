package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setProductId("12345");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(any(Product.class))).thenReturn(product);
        Product createdProduct = service.create(product);
        assertNotNull(createdProduct.getProductId());
        assertEquals("Test Product", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
        verify(productRepository, times(1)).create(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.update(any(Product.class))).thenReturn(product);
        Product updatedProduct = service.update(product);
        assertNotNull(updatedProduct);
        assertEquals("Test Product", updatedProduct.getProductName());
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).delete("12345");
        service.delete("12345");
        verify(productRepository, times(1)).delete("12345");
    }

    @Test
    void testFindAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        Iterator<Product> productIterator = products.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);
        List<Product> allProducts = service.findAll();
        assertEquals(1, allProducts.size());
        assertEquals("Test Product", allProducts.get(0).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdProductExists() {
        when(productRepository.findById("12345")).thenReturn(product);
        Product foundProduct = service.findById("12345");
        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getProductName());
        verify(productRepository, times(1)).findById("12345");
    }

    @Test
    void testFindByIdProductNotFound() {
        when(productRepository.findById("99999")).thenReturn(null);
        Product foundProduct = service.findById("99999");
        assertNull(foundProduct);
        verify(productRepository, times(1)).findById("99999");
    }
}
