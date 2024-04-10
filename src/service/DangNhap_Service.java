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
            PreparedStatement ps = db.openConnection().prepareStatement("select * from Users where Tai_khoan = ?, Trang_thai = 1");
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new NhanVien(rs.getInt("Id"), rs.getString("Ten"), rs.getString("Ten_dem"), rs.getString("Ho"), rs.getString("Ngay_sinh"), rs.getBoolean("Gioi_tinh"),rs.getString("Sdt"),rs.getInt("Vai_tro"),rs.getString("Email"),rs.getString("Tai_khoan"),rs.getString("Mat_khau"),rs.getBoolean("Trang_thai"));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
}
