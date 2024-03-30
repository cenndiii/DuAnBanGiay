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

    List<KhachHang> list;
    Connection con = null;
    PreparedStatement ps = null;
//    ResultSet rs = null;
//    String sql = null;
    DBConnect db = new DBConnect();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public List<KhachHang> getAllKhachHang() {

        list = new ArrayList<>();

        try {
            Statement st = db.openConnection().createStatement();
            ResultSet rs = st.executeQuery("""
                SELECT [Id]
                    ,[Ten]
                    ,[Ten_dem]
                    ,[Ho]
                    ,[Gioi_tinh]
                    ,[Ngay_Sinh]
                    ,[Email]
                    ,[Sdt]
                FROM [dbo].[KhachHang]""");
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8));
                list.add(kh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
                                          ,[Sdt])
                                    VALUES
                                (?,?,?,?,?,?,?)
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
            PS.setObject(8, kh.getID());
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

    public List<KhachHang> timKiemKhachHang(String id, String ten) {
        String sql = """
                    SELECT [Id]
                            ,[Ten]
                            ,[Ten_dem]
                            ,[Ho]
                            ,[Gioi_tinh]
                            ,[Ngay_Sinh]
                            ,[Email]
                            ,[Sdt]
                    FROM [dbo].[KhachHang] where ID = ? or Ten = ?
                     """;
        try (PreparedStatement PS = db.openConnection().prepareStatement(sql)) {
            PS.setObject(1, id);
            PS.setObject(2, ten);
            ResultSet rs = PS.executeQuery();
            List<KhachHang> lists = new ArrayList<>();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setID(rs.getInt(1));
                kh.setTen(rs.getString(2));
                kh.setTenDem(rs.getString(3));
                kh.setHo(rs.getString(4));
                kh.setGioiTinh(rs.getBoolean(5));
                kh.setNgaySinh(rs.getString(6));
                kh.setMail(rs.getString(7));
                kh.setSDT(rs.getString(8));
                lists.add(kh);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        List<KhachHang> lists = new KhachHangService().getAllKhachHang();
        for (KhachHang kh : lists) {
            System.out.println(kh.toString());
        }
    }

}
