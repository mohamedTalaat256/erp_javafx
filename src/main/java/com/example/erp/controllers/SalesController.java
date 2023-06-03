package com.example.erp.controllers;

import com.example.erp.database.Database;
import com.example.erp.models.Pair;
import com.example.erp.models.Product;
import com.example.erp.models.SalesInvoice;
import com.example.erp.repository.ProductImpl;
import com.example.erp.repository.SalesInvoiceImpl;
import com.example.erp.utils.GlobalData;
import com.example.erp.utils.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SalesController implements Initializable {

    private final String tablName = "sales_invoices";

    @FXML
    TableColumn col_id, col_date, col_customer, col_total, col_paid, col_remain, col_status;
    @FXML
    TextField text_search_id;
    @FXML
    ComboBox combo_search_customer;
    int customer_id=0;

    @FXML
    TableView tableViewSales;
    int indexTable = 0;

    ObservableList<SalesInvoice> salesInvoicesList;
    ObservableList<Pair> customerList;



    public void openSalesWindow(){
        GlobalData.selectedInvoiceDetails= Integer.parseInt(text_search_id.getText());
        Utilities.openWindowLarg("Sales Invoice Details", "SalesDetails.fxml");

    }

   void setColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_paid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        col_remain.setCellValueFactory(new PropertyValueFactory<>("remain"));
       col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }


    void getSalesInvoices(){
        tableViewSales.setItems(null);
        ResultSet rs = SalesInvoiceImpl.getAllSalesInvoices();
        salesInvoicesList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                salesInvoicesList.add(new SalesInvoice(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getString(8)
                        ));
            }
            tableViewSales.setItems(salesInvoicesList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }
    void findSalesInvoicesByCustomer(int c_id){
        tableViewSales.setItems(null);
        ResultSet rs = SalesInvoiceImpl.findSalesInvoicesByCustomer(c_id);
        salesInvoicesList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                salesInvoicesList.add(new SalesInvoice(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getString(8)
                ));
            }
            tableViewSales.setItems(salesInvoicesList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }





    public void search(){

    }

    public void getSelectedRow(){
       indexTable = tableViewSales.getSelectionModel().getSelectedIndex();

        int inv_id = Integer.parseInt(col_id.getCellData(indexTable).toString());
        text_search_id.setText(String.valueOf(inv_id));


        System.out.println(":::::::::::::::::::::"+inv_id);

    }


    void setCustomerCombo(){
        combo_search_customer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pair>() {
            @Override
            public void changed(ObservableValue<? extends Pair> arg0, Pair arg1, Pair arg2) {
                if (arg2 != null) {

                    customer_id = arg2.getKey();
                    findSalesInvoicesByCustomer(arg2.getKey());
                }
            }
        });
    }

    void getCustomers(){
        combo_search_customer.setItems(null);

        ResultSet rs = Database.getAll("customers");
        customerList = FXCollections.observableArrayList();
        try{
            customerList.add(new Pair( 0,"all"));
            while (rs.next()){
                customerList.add(new Pair(
                        rs.getInt(1),
                        rs.getString(2)
                ));
            }
            combo_search_customer.setItems(customerList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableViewSales.setOnMouseClicked( event -> {
            getSelectedRow();
            GlobalData.selectedInvoiceDetails= Integer.parseInt(text_search_id.getText());
            if( event.getClickCount() == 2 ) {

                Utilities.openWindowMid("Sales Invoice Details", "SalesDetails.fxml");
            }});

        setColumns();
        getCustomers();
        setCustomerCombo();
        getSalesInvoices();
    }


}
