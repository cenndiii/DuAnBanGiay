package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.KhuyenMai;
import model.SanPham;
import untils.DBConnect;

public class KhuyenMai_Service {

    DBConnect db = new DBConnect();

    public List<KhuyenMai> getAllVoucher() {
        List<KhuyenMai> list = new ArrayList<>();
        try (Statement st = db.openConnection().createStatement(); ResultSet rs = st.executeQuery("Select * from KhuyenMai")) {
            while (rs.next()) {
                list.add(new KhuyenMai(rs.getInt(1), rs.getString(2), rs.getDouble(7), rs.getDate(3), rs.getDate(4), rs.getBoolean(6), rs.getBoolean(5)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void AddVoucher(KhuyenMai km, List<SanPham> listIdSp) {
        try {
            double tienGiam;
            java.sql.Date f = new java.sql.Date(km.getNgayBatDau().getTime());
            java.sql.Date l = new java.sql.Date(km.getNgayKetThuc().getTime());
            PreparedStatement ps = db.openConnection().prepareStatement("insert into KhuyenMai(Id,Ten,Ngay_bat_dau,Ngay_ket_thuc,Trang_thai,Hinh_thuc_KM,Gia_tri_giam) values (?,?,?,?,?,?,?)");
            ps.setInt(1, km.getId());
            ps.setString(2, km.getTieuDe());
            ps.setDate(3, f);
            ps.setDate(4, l);
            ps.setBoolean(5, km.isTrangThai());
            ps.setBoolean(6, km.isHinhThucKm());
            ps.setDouble(7, km.getGiaTri());
            ps.executeUpdate();
            for (SanPham sp : listIdSp) {
                ps = db.openConnection().prepareStatement("update SanPham set IdKM = ? ,Tien_Km = ? where Id = ?");
                ps.setInt(1, km.getId());
                if (km.isHinhThucKm()) {
                    // giảm theo VND
                    tienGiam = km.getGiaTri();
                } else {
                    tienGiam = km.getGiaTri() * sp.getGiaBan() / 100;
                }
                ps.setDouble(2, tienGiam);
                ps.setInt(3, sp.getIdSP());
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateVoucher(KhuyenMai km, List<SanPham> listIdSp) {
        try {
            double tienGiam;
            java.sql.Date f = new java.sql.Date(km.getNgayBatDau().getTime());
            java.sql.Date l = new java.sql.Date(km.getNgayKetThuc().getTime());
            PreparedStatement ps = db.openConnection().prepareStatement("update KhuyenMai set Ten = ?, Ngay_bat_dau = ?, Ngay_ket_thuc = ?, Trang_thai = ?, Hinh_thuc_KM = ?, Gia_tri_giam = ? where Id = ?");
            ps.setString(1, km.getTieuDe());
            ps.setDate(2, f);
            ps.setDate(3, l);
            ps.setBoolean(4, km.isTrangThai());
            ps.setBoolean(5, km.isHinhThucKm());
            ps.setDouble(6, km.getGiaTri());
            ps.setInt(7, km.getId());
            ps.executeUpdate();
            ps = db.openConnection().prepareStatement("update SanPham set IdKM = null, Tien_Km = null where IdKM = ?");
            ps.setInt(1, km.getId());
            ps.executeUpdate();
            for (SanPham sp : listIdSp) {
                ps = db.openConnection().prepareStatement("update SanPham set IdKM = ? ,Tien_Km = ? where Id = ?");
                ps.setInt(1, km.getId());
                if (km.isHinhThucKm()) {
                    // giảm theo VND
                    tienGiam = km.getGiaTri();
                } else {
                    tienGiam = km.getGiaTri() * sp.getGiaBan() / 100;
                }
                ps.setDouble(2, tienGiam);
                ps.setInt(3, sp.getIdSP());
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteVoucher(int idKm){
        try {
            PreparedStatement ps = db.openConnection().prepareStatement("update SanPham set IdKM = null, Tien_Km = null where IdKM = ?");
            ps.setInt(1, idKm);
            ps.executeUpdate();
            ps = db.openConnection().prepareStatement("delete from KhuyenMai where Id = ?");
            ps.setInt(1, idKm);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
