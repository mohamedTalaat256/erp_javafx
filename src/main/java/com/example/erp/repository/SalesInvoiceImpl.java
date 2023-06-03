package com.example.erp.repository;

import com.example.erp.database.Database;
import com.example.erp.models.Product;
import com.example.erp.models.SalesInvoice;
import com.example.erp.models.SalesInvoiceDetails;
import com.example.erp.repository.implementaion.ProductRepository;
import com.example.erp.repository.implementaion.SalesInvoiceRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesInvoiceImpl implements SalesInvoiceRepository {
    private final static String tableName = "sales_invoices";


    public static boolean insertNewInvoice(SalesInvoice salesInvoice) {

        String[] values = {
                String.valueOf(0),
                String.valueOf(salesInvoice.getInvoice_id()),
                salesInvoice.getDate(),
                String.valueOf(salesInvoice.getCustomer_id()),
                String.valueOf(salesInvoice.getTotal()),
                String.valueOf(salesInvoice.getPaid()),
                String.valueOf(salesInvoice.getRemain()),
                salesInvoice.getStatus(),
        };

        return Database.insert(tableName, values);
    }

    public static boolean insertNewItemToInvoice(SalesInvoiceDetails salesInvoiceDetails) {

        String[] values = {
                String.valueOf(0),
                String.valueOf(salesInvoiceDetails.getSales_invoice_id()),
                String.valueOf(salesInvoiceDetails.getProduct_id()),
                String.valueOf(salesInvoiceDetails.getQti()),
                String.valueOf(salesInvoiceDetails.getPrice()),
                String.valueOf(salesInvoiceDetails.getTotal())
        };

        return Database.insert("sales_invoice_details", values);
    }


    public boolean delete(int id) {
        return Database.deleteByOneKey(tableName, "id", String.valueOf(id));
    }


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





    static String getInvoicesQuery = "SELECT sales_invoices.id,"+
            " sales_invoices.invoice_id,"+
            " sales_invoices.date,"+
            " customers.name AS customer_name,"+
            " sales_invoices.total,"+
            " sales_invoices.paid,"+
            " sales_invoices.remain,"+
            " sales_invoices.status"+
            " FROM sales_invoices INNER JOIN customers ON customers.id = sales_invoices.customer_id";


    public static ResultSet getAllSalesInvoices(){
        String query = getInvoicesQuery;
        return  Database.select(query);
    }
    public static ResultSet findSalesInvoicesById(int id){
        String query = getInvoicesQuery +" WHERE sales_invoices.id="+id;;
        return  Database.select(query);
    }

    public static ResultSet findSalesInvoicesByCustomer(int customer_id){
        if(customer_id==0){
            String query = getInvoicesQuery;
            return  Database.select(query);
        }
        String query = getInvoicesQuery+" WHERE sales_invoices.customer_id="+customer_id;
        return  Database.select(query);
    }


    public static ResultSet getAllInvoiceDetails(int inv_id){
        String query ="SELECT sales_invoice_details.id,"+
                " sales_invoice_details.sales_invoice_id,"+
                " products.name AS product_name,"+
                " sales_invoice_details.qti,"+
                " sales_invoice_details.price,"+
                " sales_invoice_details.total"+

                " FROM sales_invoice_details INNER JOIN products ON products.id = sales_invoice_details.product_id WHERE sales_invoice_details.sales_invoice_id="+ inv_id;
        return  Database.select(query);
    }


    public static double getInvoiceTotal(int id) throws SQLException {
        String query ="SELECT SUM(total) from sales_invoice_details where sales_invoice_id="+ id;
        ResultSet rs = Database.select(query);
        while (rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

}
