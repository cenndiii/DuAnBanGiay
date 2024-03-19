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
import model.XuatXu;
import untils.DBConnect;

/**
 *
 * @author Viet Anh
 */
public class XuatXuService {
    DBConnect db = new DBConnect();
    public List<XuatXu> getAll() {
        try {
            try(PreparedStatement ps = db.openConnection().prepareStatement("select * from NSX");) {
                try(ResultSet rs = ps.executeQuery();) {
                    List<XuatXu> list = new ArrayList<>();
                    while (rs.next()) {                        
                        XuatXu x = new XuatXu();
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
