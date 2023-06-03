package com.example.erp.repository;

import com.example.erp.database.Database;
import com.example.erp.models.Pair;
import com.example.erp.utils.Utilities;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeImpl {





    public static List<Pair> getSalesStatus(){
        String sql = "select MONTH(date) as month, COUNT(*) as count from `sales_invoices` group by MONTH(date) order by MONTH(date) asc";
        ResultSet rs = Database.select(sql);
        List<Pair> l = new ArrayList<>();
        try{
            while (rs.next()){
                l.add(new Pair(rs.getInt(1), String.valueOf(rs.getInt(2))));

            }
            return  l;
        }catch (Exception e){
            Utilities.alertError(e.getMessage());
            return null;
        }



    }
}
