package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.interfaces.Create;
import id.ac.ui.cs.advprog.eshop.service.interfaces.Delete;
import id.ac.ui.cs.advprog.eshop.service.interfaces.RetrieveService;
import id.ac.ui.cs.advprog.eshop.service.interfaces.Update;


public interface ProductService extends Create<Product>, Update<Product>, Delete, RetrieveService<Product> {
    // Bisa ditambahkan method spesifik jika dibutuhkan dimasa mendatang
}
