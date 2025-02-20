package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("CreateProduct", viewName);
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPostValid() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Valid Product")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void testCreateProductPostEmptyName() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "")
                        .param("productQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testCreateProductPostNegativeQuantity() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Test Product")
                        .param("productQuantity", "-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testCreateProductPostNullName() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", (String) null) // Simulasi nama null
                        .param("productQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testEditProductPageProductExists() {
        Product product = new Product();
        product.setProductId("123");
        when(productService.findById("123")).thenReturn(product);

        String viewName = productController.editProductPage("123", model);
        assertEquals("EditProduct", viewName);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProductPageProductNotFound() throws Exception {
        when(productService.findById("999")).thenReturn(null);
        mockMvc.perform(get("/product/edit/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void testEditProductPostEmptyName() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "123")
                        .param("productName", "")
                        .param("productQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testEditProductPostNegativeQuantity() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "123")
                        .param("productName", "Test Product")
                        .param("productQuantity", "-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testEditProductPostValid() throws Exception {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Updated Product");
        product.setProductQuantity(15);
        when(productService.edit(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/edit")
                        .param("productId", "123")
                        .param("productName", "Updated Product")
                        .param("productQuantity", "15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void testEditProductPostNullName() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "123")
                        .param("productName", (String) null) // Simulasi nama null
                        .param("productQuantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).delete("123");
        mockMvc.perform(get("/product/delete/123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testProductListPage() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);
        assertEquals("ListProduct", viewName);
        verify(model, times(1)).addAttribute("products", productList);
    }
}
