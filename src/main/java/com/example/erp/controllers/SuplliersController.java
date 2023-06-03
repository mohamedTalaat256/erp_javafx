package com.example.erp.controllers;

import com.example.erp.database.Database;
import com.example.erp.repository.SupplierLogic;
import com.example.erp.models.Supplier;
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

public class SuplliersController implements Initializable {

    @FXML
    TableColumn col_id, col_name, col_phone, col_debit_status, col_address;
    @FXML
    TextField text_id, text_name, text_phone, text_debit, text_search, text_address;
    @FXML
    TableView<Supplier> tableViewSuppliers;
    int indexSuppliersTable = 0;

    ObservableList<Supplier> suppliersList;

    void setColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
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
        if(text_address.getText() == ""){
            Utilities.alertError("Please Enter Address");
            return false;
        }
        return true;
    }

    void getSuppliers(){
        tableViewSuppliers.setItems(null);
        ResultSet rs = Database.getAll( "suppliers");
        suppliersList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                suppliersList.add(new Supplier(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        Integer.parseInt(rs.getString(5))));
            }
            tableViewSuppliers.setItems(suppliersList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }


    public void insert(){
        if (validateInputs()){
            Supplier supplier = new Supplier(
                    0,
                    text_name.getText(),
                    text_phone.getText(),
                    text_address.getText(),
                    Integer.parseInt(text_debit.getText())
            );
            SupplierLogic supplierLogic = new SupplierLogic();

            if ( supplierLogic.insert(supplier)){
                Utilities.alertSuccess("Inserted Successfully");
            }else {
                Utilities.alertError("Fail To Insert");
            }
            getSuppliers();
            rest();
        }

    }

    public void update(){
        if (validateInputs()){
            Supplier supplier = new Supplier(
                    Integer.parseInt(text_id.getText()),
                    text_name.getText(),
                    text_phone.getText(),
                    text_address.getText(),
                    Integer.parseInt(text_debit.getText())
            );
            SupplierLogic supplierLogic = new SupplierLogic();

            if ( supplierLogic.update(supplier)){
                Utilities.alertSuccess("Updated Successfully");
            }else {
                Utilities.alertError("Fail To Update");
            }
            getSuppliers();
            rest();
        }

    }


    public void delete(){
       SupplierLogic supplierLogic = new SupplierLogic();


        if ( supplierLogic.delete( Integer.parseInt(text_id.getText()))){
            Utilities.alertSuccess("Deleted Successfully");
        }else {
            Utilities.alertError("Fail To Insert");
        }
        getSuppliers();
        rest();
    }


    public void search(){
        tableViewSuppliers.setItems(null);
        ResultSet rs = Database.search( "suppliers", "name", text_search.getText());
        suppliersList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                suppliersList.add(new Supplier(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),

                        Integer.parseInt(rs.getString(5))));
            }
            tableViewSuppliers.setItems(suppliersList);
        }catch (Exception e){
            Utilities.alertError("error"+ e.getMessage());
        }
    }

    public void getSelectedRow(){
        indexSuppliersTable = tableViewSuppliers.getSelectionModel().getSelectedIndex();

        text_id.setText(col_id.getCellData(indexSuppliersTable).toString());
        text_name.setText(col_name.getCellData(indexSuppliersTable).toString());
        text_phone.setText(col_phone.getCellData(indexSuppliersTable).toString());
        text_address.setText(col_address.getCellData(indexSuppliersTable).toString());
        text_debit.setText(col_debit_status.getCellData(indexSuppliersTable).toString());

    }


    void rest(){
        text_name.setText("");
        text_phone.setText("");
        text_address.setText("");
        text_debit.setText("");
        text_id.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumns();
        getSuppliers();
    }

}
