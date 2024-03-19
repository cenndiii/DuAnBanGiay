/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import untils.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.MauSac;
import model.Size;

/**
 *
 * @author Viet Anh
 */
public class SizeService {
    DBConnect db = new DBConnect();
    public List<Size> getAll() {
        try {
            
            try(PreparedStatement ps = db.openConnection().prepareStatement("select Id, Loai,Chi_Tiet from KichCo");) {
                try(ResultSet rs = ps.executeQuery();) {
                    List<Size> list = new ArrayList<>();
                    while (rs.next()) {                        
                        Size x = new Size();
                        x.setId(rs.getInt("Id"));
                        x.setLoai(rs.getString("Loai"));
                        x.setChiTiet(rs.getString("Chi_Tiet"));
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
