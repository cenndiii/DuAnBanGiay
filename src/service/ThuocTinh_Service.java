/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.HTML;
import model.ThuocTinh;
import untils.DBConnect;

public class ThuocTinh_Service {

    private final DBConnect dbcn = new DBConnect();

    public List<ThuocTinh> getFullData() {
        List<ThuocTinh> list = new ArrayList<>();
        try {
            Statement s = dbcn.openConnection().createStatement();
            ResultSet rs = s.executeQuery("""
                                          SELECT * FROM MauSac
                                          UNION ALL
                                          SELECT * FROM NSX
                                          UNION ALL
                                          SELECT * FROM ChatLieu
                                          UNION ALL
                                          SELECT * FROM ThuongHieu
                                          UNION ALL
                                          SELECT * FROM KichCo
                                          UNION ALL
                                          SELECT * FROM DanhMucSP""");
            while (rs.next()) {
                list.add(new ThuocTinh(rs.getInt("Id"), rs.getString("Loai"), rs.getString("Chi_Tiet")));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<ThuocTinh> getDataFromEachTable(String bang) {
        List<ThuocTinh> list = new ArrayList<>();
        try {
            Statement s = dbcn.openConnection().createStatement();
            ResultSet rs = s.executeQuery("select * from "+ bang);
            while (rs.next()) {
                list.add(new ThuocTinh(rs.getInt("Id"), rs.getString("Loai"), rs.getString("Chi_Tiet")));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public void themThuocTinh(String bang, String loai, String chiTietThuocTinh) {
        try {
            try (PreparedStatement ps = dbcn.openConnection().prepareStatement("insert into " + bang + " values(?,?)")) {
                ps.setString(1, loai);
                ps.setString(2, chiTietThuocTinh);
                ps.executeUpdate();
            }
        } catch (Exception e) {
        }
    }

    public void xoaThuocTinh(String bang, int id) {
        try {
            try (PreparedStatement ps = dbcn.openConnection().prepareStatement("delete from " + bang + " where Id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void suaThuocTinh(String bang, String chiTietThuocTinh, int id) {
        try (PreparedStatement ps = dbcn.openConnection().prepareStatement("update " + bang + " set Chi_Tiet = ? where Id = ?")) {
            ps.setString(1, chiTietThuocTinh);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public String checkLoaiTimKiem(String loai) {
        return switch (loai) {
            case "Theo Kích cỡ" ->
                "Kich Co";
            case "Theo Màu Sắc" ->
                "Mau Sac";
            case "Theo Thương Hiệu" ->
                "Thuong Hieu";
            case "Theo Danh Mục Sản Phẩm" ->
                "Danh Muc Sp";
            case "Theo Chất Liệu" ->
                "Chat Lieu";
            case "Nơi Sản Xuất" ->
                "Noi San Xuat";
            default ->
                "Tat ca";
        };
    }

    public List<ThuocTinh> timTheoThuocTinh(String loai, String chiTiet) {
        List<ThuocTinh> list = new ArrayList<>();
        for (ThuocTinh thuocTinh : getFullData()) {
            if (thuocTinh.getLoai().equals(loai)) {
                if (thuocTinh.getChiTiet().contains(chiTiet)) {
                    list.add(thuocTinh);
                }
            }
        }
        return list;
    }

}
