package id.ac.ui.cs.advprog.eshop.service.interfaces;

import java.util.List;

public interface RetrieveService<T> {
    List<T> findAll();
    T findById(String id);
}

