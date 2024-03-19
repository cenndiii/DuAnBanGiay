/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.SanPham;
import untils.DBConnect;

/**
 *
 * @author TIEN DUY
 */
public class SanPham_Service {

    List<SanPham> list;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<SanPham> getAll() {
        list = new ArrayList<>();
<<<<<<< Updated upstream
        con = DBConnect.getConnection();
        sql = "select sp.id,sp.Ten,sp.Gia_nhap,sp.Gia_ban,sp.So_tuong_ton,n.Loai,th.Loai,m.Loai,sz.Loai,sp.Mo_ta from SanPham sp\n"
=======
//        sql = "select sp.id,sp.Ten,sp.Gia_nhap,sp.Gia_ban,sp.So_tuong_ton,n.Loai,th.Loai,m.Loai,sz.Loai,sp.Mo_ta from SanPham sp\n"
//                + "join NSX n on sp.IdNsx = n.Id\n"
//                + "join ThuongHieu th on th.Id = sp.IdTH\n"
//                + "join MauSac m on m.Id = sp.IdMauSac\n"
//                + "join KichCo sz on sz.Id = sp.IdKC";
        
        
        try {
            ps = db.openConnection().prepareStatement("select sp.id,sp.Ten,sp.Gia_nhap,sp.Gia_ban,sp.So_tuong_ton,n.Chi_Tiet,th.Chi_Tiet,m.Chi_Tiet,sz.Chi_Tiet,sp.Mo_ta from SanPham sp\n"
>>>>>>> Stashed changes
                + "join NSX n on sp.IdNsx = n.Id\n"
                + "join ThuongHieu th on th.Id = sp.IdTH\n"
                + "join MauSac m on m.Id = sp.IdMauSac\n"
                + "join KichCo sz on sz.Id = sp.IdKC";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                SanPham sp = new SanPham(
                                        rs.getInt(1),
                                        rs.getString(2),
                                        rs.getDouble(3),
                                        rs.getDouble(4),
                                        rs.getInt(5),
                                        rs.getString(6),
                                        rs.getString(7),
                                        rs.getString(8),
                                        rs.getString(9),
                                        rs.getString(10));

                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Integer insert(SanPham x) {
        Integer row = null;
        try {
            
            try ( PreparedStatement ps = db.openConnection().prepareStatement("insert into SanPham(Ten,Gia_nhap,Gia_ban,So_tuong_ton,IdNsx,IdTH,IdMauSac,IdKC,Mo_ta) values(?,?,?,?,?,?,?,?,?)");) {
                ps.setObject(1, x.getTenSP());
                ps.setObject(2, x.getGiaNhap());
                ps.setObject(3, x.getGiaBan());
                ps.setObject(4, x.getSoLuong());
                ps.setObject(5, x.getXuatSu());
                ps.setObject(6, x.getHang());
                ps.setObject(7, x.getMauSac());
                ps.setObject(8, x.getSize());
                ps.setObject(9, x.getMoTa());


                row=ps.executeUpdate();
                return row;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
