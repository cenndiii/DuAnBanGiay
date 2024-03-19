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
            
<<<<<<< HEAD
            try(PreparedStatement ps = db.openConnection().prepareStatement("select * from NSX");) {
=======
            try(PreparedStatement ps = db.openConnection().prepareStatement("select Id, Loai,Chi_Tiet from NSX");) {
>>>>>>> 7ff67621f32b3f1714ffbfcca5e1cfe99dc19da2
                try(ResultSet rs = ps.executeQuery();) {
                    List<XuatXu> list = new ArrayList<>();
                    while (rs.next()) {                        
                        XuatXu x = new XuatXu();
                        x.setId(rs.getInt("Id"));
<<<<<<< HEAD
                        x.setChiTiet(rs.getString(3));
=======
                        x.setLoai(rs.getString("Loai"));
                        x.setChiTiet(rs.getString("Chi_Tiet"));
>>>>>>> 7ff67621f32b3f1714ffbfcca5e1cfe99dc19da2
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
