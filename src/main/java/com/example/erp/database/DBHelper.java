package com.example.erp.database;

import com.example.erp.utils.Utilities;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.sql.*;
public class DBHelper {

    public static boolean  insert(String tableName, String[] values){

        Connection connect = Database.connect();
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+ tableName);

            if (rs != null) {

                ResultSetMetaData columns = rs.getMetaData();
                int i = 1;
                int j = 1;
                String coloumnsNamesString = "";
                String coloumnsValuesString = "";

                while (i < columns.getColumnCount()) {
                    i++;
                    coloumnsNamesString += columns.getColumnName(i);
                    coloumnsValuesString += "?";

                    if(i != columns.getColumnCount()){
                        coloumnsNamesString += ", ";
                        coloumnsValuesString += ", ";
                    }
                }
                String regData = "INSERT INTO "+tableName+" ( " + coloumnsNamesString+ ") VALUES ( "+coloumnsValuesString+")";
                PreparedStatement prepare = connect.prepareStatement(regData);

                String val;
                while (j < columns.getColumnCount()) {

                    val = values[j-1];
                    if(columns.getColumnType(j+1) == 4){
                        prepare.setInt(j, Integer.parseInt(val) );//int
                    } else if (columns.getColumnType(j+1) == 8) {
                        prepare.setString(j, val);                //double
                    }else if (columns.getColumnType(j+1) == 12) {
                        prepare.setString(j, val);                //string
                    }else {
                        prepare.setString(j,val);                //date
                    }

                    j++;
                }
                prepare.executeUpdate();
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static boolean deleteByOneKey(String tableName, String key,String value){
        Connection connect = Database.connect();
        String deleteData = "DELETE FROM "+tableName+" WHERE "+value+" = " + key;
        try {
            PreparedStatement prepare = connect.prepareStatement(deleteData);
            prepare.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(String tableName, String[] cols, String[] values, String key, String value){
        Connection connect = Database.connect();
        try{
            String str = "";
            int i =0;

            while( i <=cols.length-1){
                str += cols[i]+" = '"+ values[i]+"'";
                if(i!= cols.length-1){
                    str +=", ";
                }
                i++;
            }

            String updateData = "UPDATE "+tableName+ " SET " + str +" WHERE "+ key+" = " + value;

            PreparedStatement prepare = connect.prepareStatement(updateData);
            prepare.executeUpdate();

            return  true;

        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public static void fillComboBox(  ComboBox combobox, String columnName ,String tableName){
        try {
            Connection connect = Database.connect();
            ResultSet rs = connect.createStatement().executeQuery( "select "+ columnName +" from "+ tableName );
            combobox.getItems().clear();
            while (rs.next()) {
                combobox.getItems().add(rs.getString(columnName));
            }
        } catch (SQLException ex) {
            Utilities.alertError(ex.getMessage());
        }

    }
    public boolean isExist(String tableName, String key, String value){


        String selctData = "SELECT username, password FROM "+tableName+" WHERE username = ? and password = ?";

        if(true){
            return true;
        }else{
            return false;
        }
    }



}
