/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.DanhMucSp;
import model.Size;
import untils.DBConnect;

/**
 *
 * @author Admin
 */
public class DanhMuc_Service {
    DBConnect db = new DBConnect();
    public List<DanhMucSp> getAll() {
        try {
            
            try(PreparedStatement ps = db.openConnection().prepareStatement("select * from DanhMucSP");) {

                try(ResultSet rs = ps.executeQuery();) {
                    List<DanhMucSp> list = new ArrayList<>();
                    while (rs.next()) {                        
                        DanhMucSp x = new DanhMucSp();
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
