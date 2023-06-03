package com.example.erp.controllers;

import com.example.erp.database.Database;
import com.example.erp.models.Pair;
import com.example.erp.models.Product;
import com.example.erp.repository.CategoryLogic;
import com.example.erp.models.Category;
import com.example.erp.repository.ProductImpl;
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

public class ProductController implements Initializable {

    private final String tablName = "products";

    @FXML
    TableColumn col_id, col_name, col_qti, col_category, col_status, col_price;
    @FXML
    TextField text_id, text_name, text_price, text_qti, text_search;
    @FXML
    ComboBox combo_status, combo_category;

    @FXML
    TableView tableViewProducts;
    int indexTable = 0;
    private int category_id, status_id;
    ObservableList<Product> productsList;
    ObservableList<Pair> categoryList;

    void setColumns() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_qti.setCellValueFactory(new PropertyValueFactory<>("qti"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
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
        if(combo_category.getValue() == null){
            Utilities.alertError("Please Select Category");
            return false;
        }
        if(text_qti.getText() == ""){
            Utilities.alertError("Please Enter Quantity");
            return false;
        }
        if(text_price.getText() == ""){
            Utilities.alertError("Please Enter Price");
            return false;
        }

        return true;
    }

    void getProducts(){
        tableViewProducts.setItems(null);
        ResultSet rs = ProductImpl.getAllProducts();
                //Database.getAll( tablName);
        productsList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                productsList.add(new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6)
                        ));
            }
            tableViewProducts.setItems(productsList);
        }catch (Exception e){
            Utilities.alertError("error "+ e.getMessage());
        }
    }

    public void insert(){
        if (validateInputs()){
            Product product = new Product(
                    0,
                    text_name.getText().toString(),
                    Double.parseDouble(text_price.getText()),
                    combo_status.getValue().toString(),
                    Integer.parseInt(text_qti.getText()),
                    String.valueOf(category_id)
            );
            ProductImpl impl = new ProductImpl();

            if ( impl.insert(product)){
                Utilities.alertSuccess("Inserted Successfully");
            }else {
                Utilities.alertError("Fail To Insert");
            }
            getProducts();
            rest();
        }

    }

    public void update(){
        if (validateInputs()){
            Product product = new Product(
                    Integer.parseInt(text_id.getText()),
                    text_name.getText().toString(),
                    Double.parseDouble(text_price.getText()),
                    combo_status.getValue().toString(),
                    Integer.parseInt(text_qti.getText()),
                    String.valueOf(category_id)
            );
            ProductImpl impl = new ProductImpl();

            if ( impl.update(product)){
                Utilities.alertSuccess("Updated Successfully");
            }else {
                Utilities.alertError("Fail To Update");
            }
            getProducts();
            rest();
        }

    }

    public void delete(){
        ProductImpl impl = new ProductImpl();
        if ( impl.delete( Integer.parseInt(text_id.getText()))){
            Utilities.alertSuccess("Deleted Successfully");
        }else {
            Utilities.alertError("Fail To Insert");
        }
        getProducts();
        rest();
    }

    public void search(){
        tableViewProducts.setItems(null);
        ResultSet rs = Database.search( tablName, "name", text_search.getText());
        productsList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                productsList.add(new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6)
                ));
            }
            tableViewProducts.setItems(productsList);
        }catch (Exception e){
            Utilities.alertError("error"+ e.getMessage());
        }
    }

    public void getSelectedRow(){
        indexTable = tableViewProducts.getSelectionModel().getSelectedIndex();

        text_id.setText(col_id.getCellData(indexTable).toString());
        text_name.setText(col_name.getCellData(indexTable).toString());
        text_qti.setText(col_qti.getCellData(indexTable).toString());
        text_price.setText(col_price.getCellData(indexTable).toString());
        //combo_category.setValue(col_category.getCellData(indexTable).toString());

    }

    void rest(){
        text_name.setText("");
        text_qti.setText("");
        text_price.setText("");
        text_id.setText("");
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

    void setCategoriesCombo(){
        combo_category.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pair>() {
            @Override
            public void changed(ObservableValue<? extends Pair> arg0, Pair arg1, Pair arg2) {
                if (arg2 != null) {
                    category_id = arg2.getKey();
                    System.out.println(category_id);
                }
            }
        });
    }

    void setStatusCombo(){
        combo_category.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pair>() {
            @Override
            public void changed(ObservableValue<? extends Pair> arg0, Pair arg1, Pair arg2) {
                if (arg2 != null) {
                    status_id = arg2.getKey();
                    System.out.println(status_id);
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setColumns();
        combo_status.getItems().addAll(new Pair(0, "Active"), new Pair(1, "Not Active"));

        getCategories();
        setCategoriesCombo();
        setStatusCombo();

        getProducts();
    }


}
