package com.example.erp.repository.implementaion;

import com.example.erp.models.Product;

public interface ProductRepository {

    boolean insert(Product product);
    boolean delete(int id);
    boolean update(Product product);



}
