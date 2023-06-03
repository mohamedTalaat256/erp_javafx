package com.example.erp.models;

public class SalesInvoiceDetails {

    int id, sales_invoice_id, qti;
    double price, total;
    String product_id;

    public SalesInvoiceDetails(int id, int sales_invoice_id, String product_id, int qti, double price, double total) {
        this.id = id;
        this.sales_invoice_id = sales_invoice_id;
        this.product_id = product_id;
        this.qti = qti;
        this.price = price;
        this.total = total;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSales_invoice_id() {
        return sales_invoice_id;
    }

    public void setSales_invoice_id(int invoice_id) {
        this.sales_invoice_id = invoice_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQti() {
        return qti;
    }

    public void setQti(int qti) {
        this.qti = qti;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
