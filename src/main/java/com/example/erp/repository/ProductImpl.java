package com.example.erp.repository;

import com.example.erp.database.Database;
import com.example.erp.models.Product;
import com.example.erp.repository.implementaion.ProductRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductImpl implements ProductRepository {
    private String tableName = "products";

    @Override
    public boolean insert(Product product) {

        String[] values = {
                String.valueOf(0),
                product.getName(),
                String.valueOf(product.getPrice()),
                product.getStatus(),
                String.valueOf(product.getQti()),
                product.getCategory_id()
        };

        return Database.insert(tableName, values);

    }

    @Override
    public boolean delete(int id) {
        return Database.deleteByOneKey(tableName, "id", String.valueOf(id));
    }

    @Override
    public boolean update(Product product) {
        String[] values = {
                product.getName(),
                String.valueOf(product.getPrice()),
                product.getStatus(),
                String.valueOf(product.getQti()),
                product.getCategory_id()
        };

        String[] cols ={"name","price", "status", "qti", "category_id"};
        return Database.update(tableName, cols, values, "id", String.valueOf(product.getId()) );
    }



    //select join

    public static ResultSet getAllProducts(){
        String query ="SELECT products.id, products.name, products.price ,products.status, products.qti, categories.name AS cat_name"+
                " FROM products INNER JOIN categories ON categories.id = products.category_id";
        return  Database.select(query);
    }

    public static ResultSet getAllProductsByCategoryId(int cat_id){
        String query ="SELECT products.id, products.name, products.price, products.status, products.qti, categories.name AS cat_name"+
                " FROM products INNER JOIN categories ON categories.id = products.category_id WHERE products.category_id="+ cat_id;
        return  Database.select(query);
    }

    public static int getProductPrice(int id) throws SQLException {
        String query ="SELECT products.price from Products where id="+ id;
        ResultSet rs = Database.select(query);
        while (rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    public static int getProductStock(int id) throws SQLException {
        String query ="SELECT products.qti from Products where id="+ id;
        ResultSet rs = Database.select(query);
        while (rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

}
