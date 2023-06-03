package com.example.erp.controllers;

import com.example.erp.database.Database;
import com.example.erp.models.Pair;
import com.example.erp.repository.HomeImpl;
import com.example.erp.utils.Utilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    @FXML
    private AreaChart<?, ?> chart_sales;
    @FXML
    private Label label_customer_count, label_sales_count, label_products_count;

    @FXML
    Button closeButton;

    public void openNewSalesWindow(){Utilities.openWindowMid("New Sales", "NewSale.fxml");}

    public void openPurchasesWindow() {
        Utilities.openWindowLarg("Purchases", "Purchases.fxml");
    }
    public void openProductsWindow() {
        Utilities.openWindowLarg("Products", "Products.fxml");
    }

    public void openCategoriesWindow() {
        Utilities.openWindowSmall("Categories", "Categories.fxml");
    }
    public void openCustomersWindow() {
        Utilities.openWindowMid("Customers", "Customers.fxml");
    }
    public void openSuppliersWindow() {
        Utilities.openWindowLarg("Suppliers", "Suppliers.fxml");
    }

    public void openSalesWindow() {
        Utilities.openWindowLarg("Sales", "Sales.fxml");
    }



    void setChart(){
        chart_sales.getData().clear();

        XYChart.Series chart = new XYChart.Series();

        List<Pair> data = HomeImpl.getSalesStatus();
        for (Pair pais: data) {
            chart.getData().add(new XYChart.Data<>( String.valueOf(pais.getKey()), Integer.parseInt(pais.getValue())));

        }
        chart_sales.getData().add(chart);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                ((Stage) closeButton.getScene().getWindow()).close();
            }
        });


        HomeImpl.getSalesStatus();
        label_customer_count.setText(String.valueOf(Database.getCount("customers")));
        label_sales_count.setText(String.valueOf(Database.getCount("sales_invoices")));
        label_products_count.setText(String.valueOf(Database.getCount("products")));
        setChart();

    }
}