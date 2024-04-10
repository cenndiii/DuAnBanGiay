/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import model.GioHang;
import untils.DBConnect;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.HoaDonChiTiet;

/**
 *
 * @author Admin
 */
public class HoaDonCT_Service {

    DBConnect db = new DBConnect();
    public List<HoaDonChiTiet> hdDaTt = new ArrayList<>();
    public List<HoaDonChiTiet> hdHuy = new ArrayList<>();
    public List<HoaDonChiTiet> listAll = new ArrayList<>();
    public List<HoaDonChiTiet> listSearch = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void getDetailBill() {
        listAll.clear();
        hdHuy.clear();
        hdDaTt.clear();
        try (Statement st = db.openConnection().createStatement(); ResultSet rs = st.executeQuery("select * from HoaDon")) {
            while (rs.next()) {
                if (rs.getString("Tinh_trang").equalsIgnoreCase("Đã Thanh Toán")) {
                    HoaDonChiTiet hdct = new HoaDonChiTiet(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getString(5),
                            rs.getDouble(8));
                    hdDaTt.add(hdct);
                    listAll.add(hdct);
                } else if (rs.getString("Tinh_trang").equalsIgnoreCase("Hủy")) {
                    HoaDonChiTiet hdct = new HoaDonChiTiet(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            "null",
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getString(5),
                            rs.getDouble(8));
                    hdHuy.add(hdct);
                    listAll.add(hdct);
                }
            }
        } catch (Exception e) {
        }
    }

    public List<GioHang> getDataProduct(int id) {
        List<GioHang> list = new ArrayList<>();
        try (PreparedStatement ps = db.openConnection().prepareStatement("""
                                                                          select hdct.IdHD, sp.Id ,sp.Ten, hdct.So_luong ,hdct.Don_gia,sp.Mo_ta, n.Chi_Tiet,m.Chi_Tiet,dmsp.Chi_Tiet,sz.Chi_Tiet,cl.Chi_Tiet,th.Chi_Tiet,(hdct.So_luong * hdct.Don_gia) from HoaDonChiTiet hdct 
                                                                                                                                                                                join SanPham sp on sp.Id = hdct.IdSP
                                                                                                                                                                                join NSX n on sp.IdNsx = n.Id
                                                                                                                                                                                join ThuongHieu th on th.Id = sp.IdTH
                                                                                                                                                                                join MauSac m on m.Id = sp.IdMauSac
                                                                                                                                                                                join KichCo sz on sz.Id = sp.IdKC
                                                                                                                                                                                join ChatLieu cl on sp.IdCL =cl.Id
                                                                                                                                                                                join DanhMucSP dmsp on sp.IdDMuc = dmsp.Id
                                                                                                                                                                                where hdct.IdHD = ?""")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GioHang sphdct = new GioHang(
                        rs.getInt(2), rs.getInt(4), rs.getString(3), rs.getString(7), rs.getString(12), rs.getString(8), rs.getString(10), rs.getString(11), rs.getString(9), rs.getString(6), rs.getDouble(5), rs.getDouble(13));
                list.add(sphdct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonChiTiet> CbxIndex(int key, HoaDonChiTiet hdct, String search) {

        switch (key) {
            case 0 -> {
                if (hdct.getMaHDC().contains(search)) {
                    listSearch.add(hdct);
                }
            }
            case 1 -> {
                if (hdct.getIdKh() == Integer.parseInt(search)) {
                    listSearch.add(hdct);
                }
            }
            case 2 -> {
                if (hdct.getIdNV() == Integer.parseInt(search)) {
                    listSearch.add(hdct);
                }
            }
            case 3 -> {
                if (hdct.getTrangThai().contains(search)) {
                    listSearch.add(hdct);
                }
            }
            default -> {
            }
        }
        return listSearch;
    }

    public List<HoaDonChiTiet> search(java.util.Date f, java.util.Date l, String search, int key, int keyDayType) throws ParseException {
        listSearch.clear();

        try {
            switch (keyDayType) {
                case 0 -> {
                    for (HoaDonChiTiet hdct : listAll) {
                        if (f != null && l == null) {
                            // tim trước ngày cần tìm tới ngày cần tìm
                            if (sdf.parse(hdct.getNgayTao()).after(f)) {
                                if (search.isBlank()) {
                                    listSearch.add(hdct);
                                } else {
                                    CbxIndex(key, hdct, search);
                                }
                            }

                        } else if (l != null && f == null) {
                            // tim từ ngày tìm tới hôm nay
                            if (sdf.parse(hdct.getNgayTao()).before(l)) {
                                if (search.isBlank()) {
                                    listSearch.add(hdct);
                                } else {
                                    CbxIndex(key, hdct, search);
                                }
                            }
                        } else if (f != null && l != null) {
                            // nếu không cái nào null
                            if (sdf.parse(hdct.getNgayTao()).before(l) && sdf.parse(hdct.getNgayTao()).after(f)) {
                                if (search.isBlank()) {
                                    // nếu ô tìm kiếm trống -> tìm theo ngày
                                    listSearch.add(hdct);
                                } else {
                                    CbxIndex(key, hdct, search);
                                }
                            }
                        }
                    }
                }
                case 1 -> {
                    for (HoaDonChiTiet hdct : listAll) {

                        if (!hdct.getNgayThanhToan().contains("null")) {
                            if (f != null && l == null) {
                                // tim trước ngày cần tìm tới ngày cần tìm
                                if (sdf.parse(hdct.getNgayThanhToan()).after(f)) {
                                    if (search.isBlank()) {
                                        listSearch.add(hdct);
                                    } else {
                                        CbxIndex(key, hdct, search);
                                    }
                                }

                            } else if (l != null && f == null) {
                                // tim từ ngày tìm tới hôm nay
                                if (sdf.parse(hdct.getNgayThanhToan()).before(l)) {
                                    if (search.isBlank()) {
                                        listSearch.add(hdct);
                                    } else {
                                        CbxIndex(key, hdct, search);
                                    }
                                }
                            } else if (f != null && l != null) {
                                // nếu không cái nào null
                                if (sdf.parse(hdct.getNgayThanhToan()).before(l) && sdf.parse(hdct.getNgayThanhToan()).after(f)) {
                                    if (search.isBlank()) {
                                        // nếu ô tìm kiếm trống -> tìm theo ngày
                                        listSearch.add(hdct);
                                    } else {
                                        CbxIndex(key, hdct, search);
                                    }
                                }
                            }
                        }
                    }
                }
                default ->
                    System.out.println("a");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSearch;
    }
}
