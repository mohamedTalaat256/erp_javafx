package com.example.erp.models;

public class Customer {

    private int id, debitStatus;
    private String name, phone;
    public Customer(int id, String name, String phone, int debitStatus) {
        this.id = id;

        this.name = name;
        this.phone = phone;
        this.debitStatus = debitStatus;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDebit_status() {
        return debitStatus;
    }

    public void setDebit_status(int debitStatus) {
        this.debitStatus = debitStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
