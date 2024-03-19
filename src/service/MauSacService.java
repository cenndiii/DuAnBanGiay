/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import model.MauSac;
import untils.DBConnect;


/**
 *
 * @author Viet Anh
 */
public class MauSacService {
    DBConnect db = new DBConnect();
    public List<MauSac> getAll() {
        try {
           
            try(PreparedStatement ps = db.openConnection().prepareStatement("select * from MauSac");) {
                try(ResultSet rs = ps.executeQuery();) {
                    List<MauSac> list = new ArrayList<>();
                    while (rs.next()) {                        
                        MauSac x = new MauSac();
                        x.setId(rs.getInt("Id"));
                        x.setChiTiet(rs.getString(3));
                        list.add(x);
                    }
                    return list;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}
