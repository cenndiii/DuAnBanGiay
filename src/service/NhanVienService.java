/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.NhanVien;
import untils.DBConnect;

/**
 *
 * @author Admin
 */
public class NhanVienService {

    List<NhanVien> list;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
//    String sql = null;
    DBConnect db = new DBConnect();
    public List<NhanVien> listSearchNV = new ArrayList<>();

    public List<NhanVien> getAllNhanVien() {

        list = new ArrayList<>();

        try {
            Statement st = db.openConnection().createStatement();
            rs = st.executeQuery("""
                SELECT [Id]
                      ,[Ten]
                      ,[Ten_dem]
                      ,[Ho]
                      ,[Ngay_sinh]
                      ,[Gioi_tinh]
                      ,[Sdt]
                      ,[Vai_tro]
                      ,[Tai_khoan]
                      ,[Mat_khau]
                      ,[Email]
                      ,[Trang_thai]
                  FROM [dbo].[Users]""");
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("Id"),
                        rs.getString("Ten"),
                        rs.getString("Ten_dem"),
                        rs.getString("Ho"),
                        rs.getString("Ngay_sinh"),
                        rs.getBoolean("Gioi_tinh"),
                        rs.getString("Sdt"),
                        rs.getInt("Vai_tro"),
                        rs.getString("Email"),
                        rs.getString("Tai_khoan"),
                        rs.getString("Mat_khau"),
                        rs.getBoolean("Trang_thai"));

                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<NhanVien> cbxIndex(int key, NhanVien nv, String search) {
        try {
            switch (key) {
                case 0 -> {
                    if (String.valueOf(nv.getID()).contains(search)) {
                        listSearchNV.add(nv);
                    }
                }
                case 1 -> {
                    String ten = nv.getTen();
                    if (nv.getHo().isBlank() && !nv.getTenDem().isBlank()) {
                        ten = nv.getTenDem() + " " + nv.getTen();
                    } else if (nv.getTenDem().isBlank() && !nv.getHo().isBlank()) {
                        ten = nv.getHo() + " " + nv.getTen();
                    } else if (!nv.getTenDem().isBlank() && !nv.getHo().isBlank()) {
                        ten = nv.getHo() + " " + nv.getTenDem() + " " + nv.getTen();
                    }
                    if (ten.contains(search)) {
                        listSearchNV.add(nv);
                    }
                }

                case 2 -> {
                    String gioiTinh = "Nam";
                    if (!nv.getGioiTinh()) {
                        gioiTinh = "Nữ";
                    }

                    if (gioiTinh.contains(search)) {
                        listSearchNV.add(nv);
                    }
                }
                case 3 -> {
                    String trangThai = "Nghỉ Làm";
                    if (nv.getTrangThai()) {
                        trangThai = "Nghỉ Làm";
                    }
                    if (trangThai.contains(search)) {
                        listSearchNV.add(nv);
                    }
                }
                case 4 -> {
                    String vaiTro = "Nhân viên";
                    if (nv.getVaiTro() == 1) {
                        vaiTro = "Quản lý";
                    }

                    if (vaiTro.contains(search)) {
                        listSearchNV.add(nv);
                    }
                }
                default -> {

                }
            }
        } catch (Exception e) {
        }
        return listSearchNV;
    }

    public boolean addNhanVien(NhanVien nv) {
        String sql = """
                     INSERT INTO [dbo].[Users]
                                               ([Ten]
                                               ,[Ten_dem]
                                               ,[Ho]
                                               ,[Ngay_sinh]
                                               ,[Gioi_tinh]
                                               ,[Sdt]
                                               ,[Vai_tro]
                                               ,[Tai_khoan]
                                               ,[Mat_khau]
                                               ,[Email]
                                               ,[Trang_thai])
                                         VALUES                                             
                                (?,?,?,?,?,?,?,?,?,?,?)
                     """;
        int check = 0;
        try (PreparedStatement PS = db.openConnection().prepareStatement(sql)) {
            PS.setObject(1, nv.getTen());
            PS.setObject(2, nv.getTenDem());
            PS.setObject(3, nv.getHo());
            PS.setObject(4, nv.getNgaySinh());
            PS.setObject(5, nv.getGioiTinh());
            PS.setObject(6, nv.getSdt());
            PS.setObject(7, nv.getVaiTro());
            PS.setObject(8, nv.getTaiKhoan());
            PS.setObject(9, nv.getMatKhau());
            PS.setObject(10, nv.getEmail());
            PS.setObject(11, nv.getTrangThai());
            check = PS.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean updateNV(NhanVien nv, String id) {
        String sql = """
                    UPDATE [dbo].[Users]
                           SET [Ten] = ?
                              ,[Ten_dem] = ?
                              ,[Ho] = ?
                              ,[Ngay_sinh] = ?
                              ,[Gioi_tinh] = ?
                              ,[Sdt] = ?
                              ,[Vai_tro] =?
                              
                              ,[Mat_khau] = ?
                              ,[Email] = ?
                              ,[Trang_thai] =?
                         WHERE  Id = ?
                     """;
        int check = 0;
        try (PreparedStatement PS = db.openConnection().prepareStatement(sql)) {
            PS.setObject(1, nv.getTen());
            PS.setObject(2, nv.getTenDem());
            PS.setObject(3, nv.getHo());
            PS.setObject(4, nv.getNgaySinh());
            PS.setObject(5, nv.getGioiTinh());
            PS.setObject(6, nv.getSdt());
            PS.setObject(7, nv.getVaiTro());

            PS.setObject(8, nv.getMatKhau());
            PS.setObject(9, nv.getEmail());
            PS.setObject(10, nv.getTrangThai());
            PS.setObject(11, id);
            check = PS.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean Delete(Integer id) {
        int check = 0;
        try (PreparedStatement PS = db.openConnection().prepareStatement("""
                                                                         DELETE FROM [dbo].[Users]
                                                                               WHERE Id = ?""")) {
            PS.setInt(1, id);
            check = PS.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return check > 0;
    }
}
