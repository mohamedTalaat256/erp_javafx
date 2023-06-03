package com.example.erp.database;
import com.example.erp.utils.Utilities;

import java.sql.*;


public class Database {
    public static Connection connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/java_erp", "root", "");
            return connect;
        }
        catch( Exception e){
            Utilities.alertError( "database not Connected");
        }
        return null;
    }


    public static ResultSet getAll( String tableName  ){
        try {
            Connection con =connect();

            ResultSet rs = con.createStatement().executeQuery( "select * from " +tableName );
            return rs;
        } catch (SQLException ex) {
            Utilities.alertError( ex.getMessage());
        }
        return null;
    }

    public static ResultSet select( String query  ){
        try {
            Connection con =connect();

            ResultSet rs = con.createStatement().executeQuery( query);
            return rs;
        } catch (SQLException ex) {
            Utilities.alertError( ex.getMessage());
        }
        return null;
    }

    public static ResultSet search( String tableName, String col, String val  ){
        try {
            Connection con =connect();
            ResultSet rs = con.createStatement().executeQuery( "select * from " +tableName +" where "+ col +" like '%"+ val+"%'" );
            return rs;
        } catch (SQLException ex) {
            Utilities.alertError( ex.getMessage());
        }
        return null;
    }

    public static String getOutoNumber(String tableName,String column ){
        try{
            Connection con =connect();
            Statement stmt=con.createStatement();
            String strAuto="select max("+column+")+1 as outonum from "+tableName;
            stmt.executeQuery(strAuto);
            String num="";
            while(stmt.getResultSet().next()){
                num=stmt.getResultSet().getString("outonum");
            }
            con.close();
            if(num==null||"".equals(num)){
                return "1";
            }else{
                return num;
            }
        } catch (SQLException ex) {
            Utilities.alertError(ex.getMessage());
            return "0";
        }
    }


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


                System.out.println(regData);

                for (String val : values){
                    System.out.println(val);
                }
                PreparedStatement prepare = connect.prepareStatement(regData);

                String val;
                while (j < columns.getColumnCount()) {

                    val = values[j];
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
            Utilities.alertError( e.getMessage());
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
            System.out.println(updateData);
            PreparedStatement prepare = connect.prepareStatement(updateData);
            prepare.executeUpdate();

            return  true;

        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public static int getCount( String tableName){
        String sql = "SELECT COUNT(*) AS Row_Count FROM "+ tableName;
        ResultSet rs = Database.select(sql);
        try{
            rs.next();

            return rs.getInt(1);

        }catch (Exception e){
            Utilities.alertError(e.getMessage());
            return 0;
        }
    }
}
