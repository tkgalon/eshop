package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

public interface GenericRepository<T> {
    T create(T entity);
    T update(T entity);
    void delete(String id);
    Iterator<T> findAll();
    T findById(String id);
}

