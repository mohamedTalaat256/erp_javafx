package com.example.erp.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

public class Utilities {

    private static Alert alert;

    public static void alertSuccess(String msg){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void alertInfo(String msg){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    public static void alertError(String msg){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public static void openWindowSmall(String title, String name){
        try{
            URL url = Paths.get("src/main/resources/"+name).toUri().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 450,700));
            stage.setTitle(title);
            stage.show();

        }catch (Exception e){
            alertError("Faild to open the window \n"+ e.getMessage());
        }
    }

    public static void openWindowMid(String title, String name){
        try{
            URL url = Paths.get("src/main/resources/"+name).toUri().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 650,700));
            stage.setTitle(title);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            alertError("Faild to open the window \n"+ e.getMessage());
        }
    }

    public static void openWindowLarg(String title, String name){
        try{
            URL url = Paths.get("src/main/resources/"+name).toUri().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1100,800));
            stage.setTitle(title);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
            alertError("Faild to open the window \n"+ e.getMessage());
        }
    }
}
