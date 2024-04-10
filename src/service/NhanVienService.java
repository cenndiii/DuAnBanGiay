/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    
    
//    public List<NhanVien> timKiemNV1(Integer id, String ho, String tenDem, String ten) {
//        for (NhanVien nv : getAllNhanVien()) {
//            if (nv.getID() == id) {
//                list.add(nv);
//            }
//            if (nv.getHo().equalsIgnoreCase(ho)) {
//                list.add(nv);
//            }
//            if (nv.getTenDem().equalsIgnoreCase(tenDem)) {
//                list.add(nv);
//            }
//            if (nv.getTen().equalsIgnoreCase(ten)) {
//                list.add(nv);
//            }
//
//        }
//        return list;
//    }
//
//    public List<NhanVien> timKiemNV(Integer id, String ten) {
//        String sql = """
//                    SELECT                [Id]
//                                          ,[Ten]
//                                          ,[Ten_dem]
//                                          ,[Ho]
//                                          ,[Ngay_sinh]
//                                          ,[Gioi_tinh]
//                                          ,[Sdt]
//                                          ,[Vai_tro]
//                                          ,[Tai_khoan]
//                                          ,[Mat_khau]
//                                          ,[Email]
//                                          ,[Trang_thai]
//FROM [dbo].[Users] where Id = ? or Ten = ?
//                     """;
//        try (PreparedStatement PS = db.openConnection().prepareStatement(sql)) {
//            PS.setObject(1, id);
//            PS.setObject(2, "%" + ten + "%");
//            ResultSet rs = PS.executeQuery();
//            List<NhanVien> lists = new ArrayList<>();
//            while (rs.next()) {
//                NhanVien nv = new NhanVien();
//                nv.setID(rs.getInt(1));
//                nv.setTen(rs.getString(2));
//                nv.setTenDem(rs.getString(3));
//                nv.setHo(rs.getString(4));
//                nv.setNgaySinh(rs.getString(5));
//                nv.setGioiTinh(rs.getBoolean(6));
//                nv.setSdt(rs.getString(7));
//                nv.setVaiTro(rs.getInt(8));
//                nv.setTaiKhoan(rs.getString(9));
//                nv.setMatKhau(rs.getString(10));
//                nv.setEmail(rs.getString(11));
//                nv.setTrangThai(rs.getBoolean(12));
//                list.add(nv);
//            }
//            return lists;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

    public static void main(String[] args) {
        List<NhanVien> lists = new NhanVienService().getAllNhanVien();
        for (NhanVien kh : lists) {
            System.out.println(kh.getGioiTinh());
        }
    }
}
