package com.example.erp.repository.implementaion;

import com.example.erp.models.Category;

public interface CategoryRepository {

    boolean insert(Category category);
    boolean delete(int id);

    boolean update(Category category);



}
