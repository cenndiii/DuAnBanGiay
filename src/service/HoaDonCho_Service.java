/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.HoaDonCho;
import untils.DBConnect;

/**
 *
 * @author Admin
 */
public class HoaDonCho_Service {

    List<HoaDonCho> list;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    DBConnect db = new DBConnect();

    public List<HoaDonCho> getAll() throws SQLException, SQLException {

        list = new ArrayList<>();
        con = db.openConnection();
        sql = """
              select hd.Id,hd.Ma,hd.Ngay_tao,u.Ten,hd.Tinh_trang
                            from HoaDon hd
                            join Users u on u.Id= hd.IdNV
                            where Tinh_trang like 'Ch%'
              """;
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                HoaDonCho hdc = new HoaDonCho(rs.getInt(1), rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));

                list.add(hdc);
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer insert(HoaDonCho x) {
        Integer row = null;

        try {
            try (PreparedStatement PS = db.openConnection().prepareStatement("insert into HoaDon(Ma,Ngay_tao,IdNV,Tinh_trang) values(?,getDate(),?,?)");) {
                PS.setObject(1, x.getMaHDC());
                PS.setObject(2, x.getIdNv());
                PS.setString(3, "Chưa thanh toán");
                row = PS.executeUpdate();
                return row;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer CancelBill(int x) {
        Integer row = null;

        try {
            try (PreparedStatement PS = db.openConnection().prepareStatement("update HoaDon set Tinh_trang = N'Hủy' where Id = ?");) {
                PS.setObject(1, x);
                row = PS.executeUpdate();
                return row;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer Pay(double tongTien, int idHd, String idKh) {
        Integer row = null;

        try {

            try (PreparedStatement PS = db.openConnection().prepareStatement("update HoaDon set Tinh_trang = N'Đã Thanh Toán', Ngay_thanh_toan = GETDATE(),IdKH = ?, Tong_tien = ? where Id = ?");) {
                if (idKh.isBlank()) {
                    PS.setNull(1, java.sql.Types.INTEGER);
                } else {
                    PS.setInt(1, Integer.parseInt(idKh));
                }
                PS.setDouble(2, tongTien);
                PS.setInt(3, idHd);
                row = PS.executeUpdate();
                return row;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer updateQuantityProduct(int SoLuongConLai, int id) {
        Integer row = null;
        try {
            try (PreparedStatement PS = db.openConnection().prepareStatement("update SanPham set So_tuong_ton = ? where Id = ?")) {
                PS.setInt(1, SoLuongConLai);
                PS.setInt(2, id);
                row = PS.executeUpdate();
                return row;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            HoaDonCho_Service h = new HoaDonCho_Service();
            List<HoaDonCho> l = h.getAll();
            for (HoaDonCho hdc : l) {
                System.out.println(hdc.getTrangThai());
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonCho_Service.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
