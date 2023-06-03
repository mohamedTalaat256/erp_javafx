package com.example.erp.controllers;

import com.example.erp.database.Database;
import com.example.erp.models.Pair;
import com.example.erp.models.SalesInvoice;
import com.example.erp.models.SalesInvoiceDetails;
import com.example.erp.repository.SalesInvoiceImpl;
import com.example.erp.utils.GlobalData;
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
import java.util.ResourceBundle;

public class SalesDetailsController implements Initializable {

    private final String tablName = "sales_invoices_details";

    @FXML
    TableColumn col_id, col_sales_invoice_id,col_item,col_price, col_qti, col_total;

    @FXML
    Label label_date, label_paid, label_total, label_remain, label_customer, label_id;

    int customer_id=0;

    @FXML
    TableView tableViewSalesInvoiceDetails;
    int indexTable = 0;

    ObservableList<SalesInvoiceDetails> salesInvoiceDetailsList;


   void setColumns() {
       col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
       col_sales_invoice_id.setCellValueFactory(new PropertyValueFactory<>("sales_invoice_id"));
       col_item.setCellValueFactory(new PropertyValueFactory<>("product_id"));
       col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
       col_qti.setCellValueFactory(new PropertyValueFactory<>("qti"));
       col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    void getSalesInvoice(){

        ResultSet rs = SalesInvoiceImpl.findSalesInvoicesById(GlobalData.selectedInvoiceDetails);
        try{
            rs.next();
            label_id.setText(String.valueOf(rs.getInt(2)));
            label_date.setText(rs.getString(3));
            label_customer.setText(rs.getString(4));
            label_total.setText(String.valueOf(rs.getInt(5)));
            label_paid.setText(String.valueOf(rs.getInt(6)));
            label_remain.setText(String.valueOf(rs.getInt(7)));

        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }


    }


    void getSalesInvoiceDetails(){
        tableViewSalesInvoiceDetails.setItems(null);
        ResultSet rs = SalesInvoiceImpl.getAllInvoiceDetails(GlobalData.selectedInvoiceDetails);
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

        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setColumns();
        getSalesInvoice();
        getSalesInvoiceDetails();

    }


}
