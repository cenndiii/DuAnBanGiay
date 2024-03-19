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
import model.Hang;


/**
 *
 * @author Viet Anh
 */
public class HangService {
    
    DBConnect db = new DBConnect();
    
    public List<Hang> getAll() {
        try {
            
            try(PreparedStatement ps = db.openConnection().prepareStatement("select * from ThuongHieu");) {
                try(ResultSet rs = ps.executeQuery();) {
                    List<Hang> list = new ArrayList<>();
                    while (rs.next()) {                        
                        Hang x = new Hang();
                        x.setId(rs.getInt("Id"));
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
