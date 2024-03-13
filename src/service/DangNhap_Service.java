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
            PreparedStatement ps = db.openConnection().prepareStatement("select * from NHANVIEN where ID = ?");
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new NhanVien(rs.getString("ID"), rs.getString("HoTenNV"), rs.getString("Email"), rs.getString("Matkhau"), rs.getString("VaiTro"), rs.getString("TinhTrang"));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
}
