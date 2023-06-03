package com.example.erp.models;

public class Supplier {

    private int id, debitStatus;
    private String name, phone, address;
    public Supplier(int id, String name, String phone, String address, int debitStatus) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.debitStatus = debitStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
