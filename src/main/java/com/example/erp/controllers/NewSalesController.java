package com.example.erp.controllers;

import com.example.erp.database.Database;
import com.example.erp.models.*;
import com.example.erp.repository.ProductImpl;
import com.example.erp.repository.SalesInvoiceImpl;
import com.example.erp.utils.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewSalesController implements Initializable {



    @FXML
    TableColumn col_id, col_sales_invoice_id,col_item,col_price, col_qti, col_total;
    @FXML
    ComboBox combo_category, combo_product,combo_customer;
    @FXML
    TextField text_invoice_id, text_qti, text_price, text_paid, text_remain;
    @FXML
    Label label_total;
    @FXML
    DatePicker date_picker;
    @FXML
    TableView<SalesInvoiceDetails> tableViewSalesInvoiceDetails;
    private int category_id=0;
    private int product_id=0;
    private int product_price=0;
    private int product_stok_amount=0;
    private int customer_id=0;

    @FXML Button btn_save, btn_print;


    ObservableList<Pair> categoryList, productList, customerList;
    ObservableList<SalesInvoiceDetails> salesInvoiceDetailsList;



    void setColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_sales_invoice_id.setCellValueFactory(new PropertyValueFactory<>("sales_invoice_id"));
        col_item.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_qti.setCellValueFactory(new PropertyValueFactory<>("qti"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
    }


    boolean validateSaveInvoice(){
        if(salesInvoiceDetailsList.isEmpty()){
            Utilities.alertError("please add at least one item to the invoice");
            return false;
        }
        if(text_paid.getText() == "" ){
            Utilities.alertError("Please Enter Paid");
            return false;
        }
        if(text_remain.getText() == ""){
            Utilities.alertError("Please Enter Remain");
            return false;
        }
        if(customer_id == 0){
            Utilities.alertError("Please Select Customer");
            return false;
        }
        if(date_picker.getValue() == null){
            Utilities.alertError("Please Select Date");
            return false;
        }


        return true;
    }
    boolean validateAddInvoiceItem(){
        if(product_id == 0){
            Utilities.alertError("Please Select Product");
            return false;
        }

        if(text_qti.getText() == "" ){
            Utilities.alertError("Please Enter Amount");
            return false;
        }
        if(Integer.parseInt(text_qti.getText()) > product_stok_amount){
            Utilities.alertError("No Available Amount in store");
            return false;
        }
        if(text_price.getText() == ""){
            Utilities.alertError("Please Enter Price");
            return false;
        }
        return true;
    }

    void getProductsOfCategory(int cat_id){
        combo_product.setItems(null);
        ResultSet rs = ProductImpl.getAllProductsByCategoryId(cat_id);
        productList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                productList.add(new Pair(
                        rs.getInt(1),
                        rs.getString(2)
                ));
                System.out.println(rs.getString(2));
            }
            combo_product.setItems(productList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }
    void getCategories(){
        combo_category.setItems(null);
        ResultSet rs = Database.getAll("categories");
        categoryList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                categoryList.add(new Pair(
                        rs.getInt(1),
                        rs.getString(2)
                ));
            }
            combo_category.setItems(categoryList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }
    void getClients(){
        combo_customer.setItems(null);
        ResultSet rs = Database.getAll("customers");
        customerList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                customerList.add(new Pair(
                        rs.getInt(1),
                        rs.getString(2)
                ));
            }
            combo_customer.setItems(customerList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }
    void setCategoriesCombo(){
        combo_category.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pair>() {
            @Override
            public void changed(ObservableValue<? extends Pair> arg0, Pair arg1, Pair arg2) {
                if (arg2 != null) {
                    category_id = arg2.getKey();

                    getProductsOfCategory(category_id);
                    System.out.println(category_id);
                }
            }
        });
    }
    void setCustomersCombo(){
        combo_customer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pair>() {
            @Override
            public void changed(ObservableValue<? extends Pair> arg0, Pair arg1, Pair arg2) {
                if (arg2 != null) {
                    customer_id = arg2.getKey();
                }
            }
        });
    }
    void setProductsCombo(){
        combo_product.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pair>() {
            @Override
            public void changed(ObservableValue<? extends Pair> arg0, Pair arg1, Pair arg2) {
                if (arg2 != null) {
                    product_id = arg2.getKey();

                    if(product_id !=0){
                        try {
                            text_price.setText(String.valueOf(ProductImpl.getProductPrice(product_id)));
                            product_stok_amount = ProductImpl.getProductStock(product_id);
                            System.out.println("######################product stck:"+ product_stok_amount);

                        } catch (SQLException e) {
                            Utilities.alertError("fail to get product price or amount in stock");
                        }

                    }

                }
            }
        });
    }
    void getInvoiceDetails(){
        tableViewSalesInvoiceDetails.setItems(null);
        ResultSet rs = SalesInvoiceImpl.getAllInvoiceDetails(Integer.parseInt(text_invoice_id.getText()));
        salesInvoiceDetailsList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                salesInvoiceDetailsList.add(new SalesInvoiceDetails(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6)
                ));

            }
            tableViewSalesInvoiceDetails.setItems(salesInvoiceDetailsList);
            getInvoiceTotal();
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }

    public void addItemToInvoice(){
        if(validateAddInvoiceItem()){
            int qti = Integer.parseInt(text_qti.getText());
            double price = Integer.parseInt(text_price.getText());
            double total = qti * price;

            SalesInvoiceDetails salesInvoiceDetails = new SalesInvoiceDetails(
                    0,
                    Integer.parseInt(text_invoice_id.getText()),
                    String.valueOf(product_id),
                    qti,
                    price,
                    total
            );
            if(SalesInvoiceImpl.insertNewItemToInvoice(salesInvoiceDetails)){
                Utilities.alertSuccess("Item Added");
            }else{
                Utilities.alertError("Fail To Add item");
            }
            getInvoiceDetails();

        }


    }
    public void saveInvoice(){
        if(validateSaveInvoice()){
            SalesInvoice salesInvoice = new SalesInvoice(
                    0,
                    Integer.parseInt(text_invoice_id.getText()),
                    date_picker.getValue().toString(),
                    String.valueOf(customer_id),
                    Double.parseDouble(label_total.getText()),
                    Double.parseDouble(text_paid.getText()),
                    Double.parseDouble(text_remain.getText()),
                    "closed"
            );
            if ( SalesInvoiceImpl.insertNewInvoice(salesInvoice)){
                Utilities.alertSuccess("Inserted Successfully");
                init();
                //reset();
            }else {
                Utilities.alertError("Fail To Insert");
            }
        }



    }

    void getInvoiceTotal(){
        try{
            double total = SalesInvoiceImpl.getInvoiceTotal(Integer.parseInt(text_invoice_id.getText()));
            label_total.setText(String.valueOf(total));

        }catch (Exception ex){
            Utilities.alertError("fail to get total");
        }

    }

    void init(){
        setColumns();
        text_invoice_id.setText(Database.getOutoNumber("sales_invoices", "invoice_id"));
        setCategoriesCombo();
        setCustomersCombo();
        setProductsCombo();
        getCategories();
        getClients();
        getInvoiceDetails();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }
}
