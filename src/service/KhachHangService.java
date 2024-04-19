/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.sun.jdi.connect.spi.Connection;
import java.util.List;
import model.KhachHang;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import untils.DBConnect;

/**
 *
 * @author PC MSI
 */
public class KhachHangService {

//    Connection con = null;
//    PreparedStatement ps = null;
    DBConnect db = new DBConnect();
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public List<KhachHang> listSearchKH = new ArrayList<>();
    public List<KhachHang> listAllKH = new ArrayList<>();
    public List<KhachHang> listKHOn = new ArrayList<>();
    public List<KhachHang> listKHOff = new ArrayList<>();

    public void getAllKhachHang() {
        listAllKH.clear();
        listKHOn.clear();
        listKHOff.clear();
        try {
            Statement st = db.openConnection().createStatement();
            ResultSet rs = st.executeQuery("""
                SELECT TOP (1000) [Id]
                      ,[Ten]
                      ,[Ten_dem]
                      ,[Ho]
                      ,[Gioi_tinh]
                      ,[Ngay_Sinh]
                      ,[Email]
                      ,[Sdt]
                      ,[Trang_thai]
                  FROM [DuAn1_FourShoes].[dbo].[KhachHang]""");
            while (rs.next()) {
                if (rs.getBoolean(9)) {
                    KhachHang kh = new KhachHang(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getBoolean(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getBoolean(9));
                    listKHOn.add(kh);
                    listAllKH.add(kh);
                } else {
                    KhachHang kh = new KhachHang(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getBoolean(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getBoolean(9));
                    listKHOff.add(kh);
                    listAllKH.add(kh);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addKhachHang(KhachHang kh) {
        String sql = """
                     INSERT INTO [dbo].[KhachHang]
                                          ([Ten]
                                          ,[Ten_dem]
                                          ,[Ho]
                                          ,[Gioi_tinh]
                                          ,[Ngay_Sinh]
                                          ,[Email]
                                          ,[Sdt]
                                          ,[Trang_thai])
                                    VALUES
                                (?,?,?,?,?,?,?,?)
                     """;
        int check = 0;
        try (PreparedStatement PS = db.openConnection().prepareStatement(sql)) {
            PS.setObject(1, kh.getTen());
            PS.setObject(2, kh.getTenDem());
            PS.setObject(3, kh.getHo());
            PS.setObject(4, kh.getGioiTinh());
            PS.setString(5, kh.getNgaySinh());
            PS.setObject(6, kh.getMail());
            PS.setObject(7, kh.getSDT());
            PS.setObject(8, kh.isTrangThai());
            check = PS.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean updateKhachHang(KhachHang kh) {
        String sql = """
                     UPDATE [dbo].[KhachHang]
                          SET [Ten] = ?
                             ,[Ten_dem] = ?
                             ,[Ho] = ?
                             ,[Gioi_tinh] = ?
                             ,[Ngay_Sinh] = ?
                             ,[Email] = ?
                             ,[Sdt] = ?
                             ,[Trang_thai] = ?
                        WHERE Id = ?
                     """;
        int check = 0;
        try (PreparedStatement PS = db.openConnection().prepareStatement(sql)) {
            PS.setObject(1, kh.getTen());
            PS.setObject(2, kh.getTenDem());
            PS.setObject(3, kh.getHo());
            PS.setObject(4, kh.getGioiTinh());
            PS.setString(5, kh.getNgaySinh());
            PS.setObject(6, kh.getMail());
            PS.setObject(7, kh.getSDT());
            PS.setObject(9, kh.getID());
            PS.setObject(8, kh.isTrangThai());
            check = PS.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean delete(int id) {
        int check = 0;
        try (PreparedStatement PS = db.openConnection().prepareStatement("""
                                                                         DELETE FROM [dbo].[KhachHang]
                                                                               WHERE ID = ?""")) {
            PS.setInt(1, id);
            check = PS.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return check > 0;
    }


    public List<KhachHang> cbxIndex(int key, KhachHang nv, String search) {
        switch (key) {
            case 0 -> {
                if (String.valueOf(nv.getID()).contains(search)) {
                    listSearchKH.add(nv);
                }
            }
            case 1 -> {
                String ten = nv.getTen();
                if (nv.getHo().isBlank() && !nv.getTenDem().isBlank()) {
                    ten = nv.getTenDem() + " " + nv.getTen();
                } else if (nv.getTenDem().isBlank() && !nv.getHo().isBlank()) {
                    ten = nv.getHo() + " " + nv.getTen();
                } else if (!nv.getTenDem().isBlank() && !nv.getHo().isBlank() && !nv.getTen().isBlank()) {
                    ten = nv.getHo() + " " + nv.getTenDem() + " " + nv.getTen();
                }
                if (ten.contains(search)) {
                    listSearchKH.add(nv);
                }
            }
            case 2 -> {
                String gioiTinh = "Nam";
                if (!nv.getGioiTinh()) {
                    gioiTinh = "Nữ";
                } 
                if (gioiTinh.contains(search)) {
                    listSearchKH.add(nv);
                }
            }
            case 3 -> {
                if (nv.getNgaySinh().contains(search)) {
                    listSearchKH.add(nv);
                }
            }
            case 4 -> {
                if (nv.getMail().contains(search)) {
                    listSearchKH.add(nv);
                }
            }
            case 5 -> {
                if (nv.getSDT().contains(search)) {
                    listSearchKH.add(nv);
                }
            }
            case 6 -> {
                String trangThai = "Ngừng Hoạt Động";
                if (nv.isTrangThai()) {
                    trangThai = "Đang Hoạt Động";
                }
                if (trangThai.contains(search)) {
                    listSearchKH.add(nv);
                }
            }
            default -> {

            }
        }
        return listSearchKH;
    }

}
