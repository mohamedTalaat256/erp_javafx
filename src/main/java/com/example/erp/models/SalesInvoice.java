package com.example.erp.models;

public class SalesInvoice {
    int id, sales_invoice_id;
    double total, paid, remain;
    String date, status,customer_id ;

    public SalesInvoice(int id, int sales_invoice_id, String date, String customer_id, double total, double paid, double remain, String status) {
        this.id = id;
        this.sales_invoice_id = sales_invoice_id;
        this.customer_id = customer_id;
        this.total = total;
        this.paid = paid;
        this.remain = remain;
        this.date = date;
        this.status = status;
    }

    public int getInvoice_id() {
        return sales_invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.sales_invoice_id = invoice_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
