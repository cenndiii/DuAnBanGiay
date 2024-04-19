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
//            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("Trang_thai")) {
                    return new NhanVien(rs.getInt("Id"), rs.getString("Ten"), rs.getString("Ten_dem"), rs.getString("Ho"), rs.getString("Ngay_sinh"), rs.getBoolean("Gioi_tinh"), rs.getString("Sdt"), rs.getInt("Vai_tro"), rs.getString("Email"), rs.getString("Tai_khoan"), rs.getString("Mat_khau"), rs.getBoolean("Trang_thai"));
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    
}
