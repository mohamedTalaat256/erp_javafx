package com.example.erp.controllers;

import com.example.erp.database.Database;
import com.example.erp.repository.CustomerLogic;
import com.example.erp.models.Customer;
import com.example.erp.utils.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {


    @FXML
    TableColumn col_id, col_name, col_phone, col_debit_status;
    @FXML
    TextField text_id, text_name, text_phone, text_debit, text_search;
    @FXML
    TableView<Customer> tableViewCustomer;
    int indexCustomerTable = 0;

    ObservableList<Customer> customersList;

    void setColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_debit_status.setCellValueFactory(new PropertyValueFactory<>("debit_status"));
    }

    boolean validateInputs(){
        if(text_name.getText() == ""){
            Utilities.alertError("Please Enter Name");
            return false;
        }
        if(text_phone.getText() == ""){
            Utilities.alertError("Please Enter Phone");
            return false;
        }
        if(text_debit.getText() == ""){
            Utilities.alertError("Please Enter Debit");
            return false;
        }
        return true;
    }

    void getCustomers(){
        tableViewCustomer.setItems(null);
        ResultSet rs = Database.getAll( "customers");
        customersList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                customersList.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        Integer.parseInt(rs.getString(4))));
            }
            tableViewCustomer.setItems(customersList);
        }catch (Exception e){
            Utilities.alertError("error"+ e.getMessage());
        }
    }


    public void insert(){
        if (validateInputs()){
            Customer customer = new Customer(
                    0,
                    text_name.getText(),
                    text_phone.getText(),
                    Integer.parseInt(text_debit.getText())
            );
            CustomerLogic customerLogic = new CustomerLogic();

            if ( customerLogic.insert(customer)){
                Utilities.alertSuccess("Inserted Successfully");
            }else {
                Utilities.alertError("Fail To Insert");
            }
            getCustomers();
            rest();
        }

    }

    public void update(){
        if (validateInputs()){
            Customer customer = new Customer(
                    Integer.parseInt(text_id.getText()),
                    text_name.getText(),
                    text_phone.getText(),
                    Integer.parseInt(text_debit.getText())
            );
            CustomerLogic customerLogic = new CustomerLogic();

            if ( customerLogic.update(customer)){
                Utilities.alertSuccess("Updated Successfully");
            }else {
                Utilities.alertError("Fail To Update");
            }
            getCustomers();
            rest();
        }

    }


    public void delete(){
       CustomerLogic customerLogic = new CustomerLogic();


        if ( customerLogic.delete( Integer.parseInt(text_id.getText()))){
            Utilities.alertSuccess("Deleted Successfully");
        }else {
            Utilities.alertError("Fail To Insert");
        }
        getCustomers();
        rest();
    }


    public void search(){
        tableViewCustomer.setItems(null);
        ResultSet rs = Database.search( "customers", "name", text_search.getText());
        customersList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                customersList.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        Integer.parseInt(rs.getString(4))));
            }
            tableViewCustomer.setItems(customersList);
        }catch (Exception e){
            Utilities.alertError("error"+ e.getMessage());
        }
    }

    public void getSelectedRow(){
        indexCustomerTable = tableViewCustomer.getSelectionModel().getSelectedIndex();
        text_name.setText(col_name.getCellData(indexCustomerTable).toString());
        text_phone.setText(col_phone.getCellData(indexCustomerTable).toString());
        text_debit.setText(col_debit_status.getCellData(indexCustomerTable).toString());
        text_id.setText(col_id.getCellData(indexCustomerTable).toString());
    }


    void rest(){
        text_name.setText("");
        text_phone.setText("");
        text_debit.setText("");
        text_id.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumns();
        getCustomers();
    }

}
