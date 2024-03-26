package service;

import untils.DBConnect;
import java.sql.*;
import model.NhanVien;

/**
 *
 * @author Admin
 */
public class DangNhap_Service {

    DBConnect db = new DBConnect();
    String GETDATA_NHANVIEN = "select * from NHANVIEN";

    public NhanVien login(String ID) {
        try {
            PreparedStatement ps = db.openConnection().prepareStatement("select * from Users where Tai_khoan = ?");
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new NhanVien(rs.getInt("Id"), rs.getString("Ten"), rs.getString("Email"), rs.getString("Mat_khau"), rs.getString("Trang_thai"), rs.getInt("Vai_tro"));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
}
