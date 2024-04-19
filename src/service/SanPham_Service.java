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
import model.SanPham;
import untils.DBConnect;

/**
 *
 * @author TIEN DUY
 */
public class SanPham_Service {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    DBConnect db = new DBConnect();
    public List<SanPham> listSpHD = new ArrayList<>();
    public List<SanPham> listSpCHD = new ArrayList<>();
    public List<SanPham> listAllSp = new ArrayList<>();
    public List<SanPham> listSearchSP = new ArrayList<>();

    public void getAll() throws SQLException, SQLException {
        listSpHD.clear();
        listAllSp.clear();
        listSpCHD.clear();
        con = db.openConnection();
        sql = """
              select sp.Id, sp.Ten, sp.So_tuong_ton, sp.Gia_nhap, Sp.Gia_ban, sp.Mo_ta, n.Chi_Tiet,m.Chi_Tiet,dmsp.Chi_Tiet,sz.Chi_Tiet,cl.Chi_Tiet,th.Chi_Tiet,sp.IdKM, sp.Trang_thai ,sp.Tien_Km from SanPham sp
                                                        join NSX n on sp.IdNsx = n.Id
                                                        join ThuongHieu th on th.Id = sp.IdTH
                                                        join MauSac m on m.Id = sp.IdMauSac
                                                        join KichCo sz on sz.Id = sp.IdKC
                                                        join ChatLieu cl on sp.IdCL =cl.Id
                                                        join DanhMucSP dmsp on sp.IdDMuc = dmsp.Id
                            				left join KhuyenMai km on sp.IdKM = km.Id""";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                double giaBan;
                if (rs.getDouble(15) == 0) {
                    giaBan = rs.getDouble("Gia_ban");
                } else {
                    giaBan = rs.getDouble("Gia_ban") - rs.getDouble(15);
                }

                if (rs.getBoolean(14)) {
                    SanPham sp = new SanPham(
                            rs.getInt("Id"),
                            rs.getString("Ten"),
                            rs.getDouble("Gia_nhap"),
                            giaBan,
                            rs.getInt("So_tuong_ton"),
                            rs.getString(7),
                            rs.getString(12),
                            rs.getString(8),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(9),
                            rs.getString(6),
                            rs.getInt(13),
                            rs.getBoolean(14));
                    listSpHD.add(sp);
                    listAllSp.add(sp);
                } else {
                    SanPham sp = new SanPham(
                            rs.getInt("Id"),
                            rs.getString("Ten"),
                            rs.getDouble("Gia_nhap"),
                            giaBan,
                            rs.getInt("So_tuong_ton"),
                            rs.getString(7),
                            rs.getString(12),
                            rs.getString(8),
                            rs.getString(10),
                            rs.getString(11),
                            rs.getString(9),
                            rs.getString(6),
                            rs.getInt(13),
                            rs.getBoolean(14));
                    listSpCHD.add(sp);
                    listAllSp.add(sp);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer insert(SanPham x) {
        Integer row = null;
        try {
            try (PreparedStatement PS = db.openConnection().prepareStatement("insert into SanPham(Ten,Gia_nhap,Gia_ban,So_tuong_ton,IdNsx,IdTH,IdMauSac,IdKC,Mo_ta,IdCL,IdDMuc,Trang_thai) values(?,?,?,?,?,?,?,?,?,?,?,?)");) {
                PS.setObject(1, x.getTenSP());
                PS.setObject(2, x.getGiaNhap());
                PS.setObject(3, x.getGiaBan());
                PS.setObject(4, x.getSoLuong());
                PS.setObject(5, x.getXuatSu());
                PS.setObject(6, x.getHang());
                PS.setObject(7, x.getMauSac());
                PS.setObject(8, x.getSize());
                PS.setObject(9, x.getMoTa());
                PS.setObject(10, x.getChatLieu());
                PS.setObject(11, x.getDanhMuc());
                PS.setBoolean(12, x.getTrangThai());
                row = PS.executeUpdate();
                return row;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer update(SanPham x) {
        Integer row = null;
        try {
            try (PreparedStatement PS = db.openConnection().prepareStatement("update SanPham set Ten= ?, So_tuong_ton = ?, Gia_nhap = ? ,Gia_ban = ? ,Mo_ta = ? , IdNsx =? ,IdMauSac = ? ,IdDMuc = ?,IdKC = ?, IdCL = ?, IdTH = ?, IdKM = null,Trang_thai = ? where Id = ?")) {
                PS.setObject(1, x.getTenSP());
                PS.setObject(2, x.getSoLuong());
                PS.setObject(3, x.getGiaNhap());
                PS.setObject(4, x.getGiaBan());
                PS.setObject(5, x.getMoTa());
                PS.setObject(6, x.getXuatSu());
                PS.setObject(7, x.getMauSac());
                PS.setObject(8, x.getDanhMuc());
                PS.setObject(9, x.getSize());
                PS.setObject(10, x.getChatLieu());
                PS.setObject(11, x.getHang());
                PS.setBoolean(12, x.getTrangThai());
                PS.setObject(13, x.getIdSP());
                row = PS.executeUpdate();
                return row;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Integer delete(int id) {
        Integer row = null;
        try (PreparedStatement PS = db.openConnection().prepareStatement("update SanPham set Trang_thai = 0 where Id = ?")) {
            PS.setInt(1, id);
            row = PS.executeUpdate();
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SanPham> cbxIndex(int key, SanPham sp, String search) {
        switch (key) {
            case 0 -> {
                if (String.valueOf(sp.getIdSP()).contains(search)) {
                    listSearchSP.add(sp);
                }
            }
            case 1 -> {
                if (sp.getTenSP().contains(search)) {
                    listSearchSP.add(sp);
                }
            }
            case 2 -> {
                if (sp.getXuatSu().contains(search)) {
                    listSearchSP.add(sp);
                }
            }
            case 3 -> {
                if (sp.getHang().contains(search)) {
                    listSearchSP.add(sp);
                }
            }
            case 4 -> {
                if (sp.getMauSac().contains(search)) {
                    listSearchSP.add(sp);
                }
            }
            case 5 -> {
                if (sp.getSize().contains(search)) {
                    listSearchSP.add(sp);
                }
            }
            case 6 -> {
                if (sp.getChatLieu().contains(search)) {
                    listSearchSP.add(sp);
                }
            }
            case 7 -> {
                if (sp.getDanhMuc().contains(search)) {
                    listSearchSP.add(sp);
                }
            }
            default -> {

            }
        }
        return listSearchSP;
    }
}
