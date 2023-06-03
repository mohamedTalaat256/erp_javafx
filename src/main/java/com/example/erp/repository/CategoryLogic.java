package com.example.erp.repository;

import com.example.erp.database.Database;
import com.example.erp.repository.implementaion.CategoryRepository;
import com.example.erp.models.Category;

public class CategoryLogic implements CategoryRepository {
    private String tableName = "categories";

    @Override
    public boolean insert(Category category) {

        String[] values = {
                String.valueOf(0),
                category.getName(),
               category.getStatus()
        };

        return Database.insert(tableName, values);

    }

    @Override
    public boolean delete(int id) {

        return Database.deleteByOneKey(tableName, "id", String.valueOf(id));
    }

    @Override
    public boolean update(Category category) {
        String[] values = {
                category.getName(),
                category.getStatus()
        };



        String[] cols ={"name", "status"};
        return Database.update(tableName, cols, values, "id", String.valueOf(category.getId()) );
    }
}
