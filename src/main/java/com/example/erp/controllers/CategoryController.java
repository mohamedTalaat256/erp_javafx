package com.example.erp.controllers;

import com.example.erp.database.Database;

import com.example.erp.repository.CategoryLogic;
import com.example.erp.models.Category;
import com.example.erp.models.Pair;
import com.example.erp.utils.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    TableColumn col_id, col_name, col_status;
    @FXML
    TextField text_id, text_name, text_search;
    @FXML
    ComboBox<Pair> combo_status;


    @FXML
    ChoiceBox<Pair> cBox;

    @FXML
    TableView<Category> tableViewCategories;
    int indexCategoriesTable = 0;

    ObservableList<Category> categoriesList;
    private final String tableName ="categories";

    void setColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    boolean validateInputs(){
        if(text_name.getText() == ""){
            Utilities.alertError("Please Enter Name");
            return false;
        }
        if(combo_status.getValue() == null){
            Utilities.alertError("Please Select Status");
            return false;
        }
        return true;
    }

    void getCategories(){
        tableViewCategories.setItems(null);
        ResultSet rs = Database.getAll( "categories");
        categoriesList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                categoriesList.add(new Category(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
            tableViewCategories.setItems(categoriesList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }


    public void insert(){
        if (validateInputs()){
            Category category = new Category(
                    0,
                    text_name.getText(),
                   combo_status.getValue().toString()
            );
            CategoryLogic categoryLogic = new CategoryLogic();

            if ( categoryLogic.insert(category)){
                Utilities.alertSuccess("Inserted Successfully");
            }else {
                Utilities.alertError("Fail To Insert");
            }
            getCategories();
            rest();
        }

    }

    public void update(){
        if (validateInputs()){
            Category category = new Category(
                    Integer.parseInt(text_id.getText()),
                    text_name.getText(),
                    combo_status.getValue().toString()
            );
            CategoryLogic categoryLogic = new CategoryLogic();

            if ( categoryLogic.update(category)){
                Utilities.alertSuccess("Updated Successfully");
            }else {
                Utilities.alertError("Fail To Update");
            }
            getCategories();
            rest();
        }

    }


    public void delete(){


        CategoryLogic categoryLogic = new CategoryLogic();


        if ( categoryLogic.delete( Integer.parseInt(text_id.getText()))){
            Utilities.alertSuccess("Deleted Successfully");
        }else {
            Utilities.alertError("Fail To Insert");
        }
        getCategories();
        rest();
    }


    public void search(){
        tableViewCategories.setItems(null);
        ResultSet rs = Database.search( "categories", "name", text_search.getText());
        categoriesList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                categoriesList.add(new Category(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
            tableViewCategories.setItems(categoriesList);
        }catch (Exception e){
            Utilities.alertError("error"+ e.getMessage());
        }
    }

    public void getSelectedRow(){
        indexCategoriesTable = tableViewCategories.getSelectionModel().getSelectedIndex();
        text_id.setText(col_id.getCellData(indexCategoriesTable).toString());
        text_name.setText(col_name.getCellData(indexCategoriesTable).toString());
    }

    void rest(){
        text_name.setText("");
        text_id.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumns();
        combo_status.getItems().addAll(new Pair(0, "Active"),new Pair(1, "Not Active"));

        getCategories();
    }


}
