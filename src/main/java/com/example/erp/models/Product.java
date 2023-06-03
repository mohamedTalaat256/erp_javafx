package com.example.erp.models;

public class Product {
    int id, qti;
    double price;
    String name, category_id, status ;

    public Product(int id, String name, double price, String status, int qti, String category_id) {
        this.id = id;
        this.status = status;
        this.price = price;
        this.name = name;
        this.qti = qti;
        this.category_id = category_id;

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQti() {
        return qti;
    }

    public void setQti(int qti) {
        this.qti = qti;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
