/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class KhuyenMai {
    private int id;
    private String tieuDe;
    private double giaTri;
    private Date ngayBatDau,ngayKetThuc;
    private boolean hinhThucKm;
    private boolean trangThai;

    public KhuyenMai() {
    }

    public Object[] toDataRowKm(){
        return new Object[]{id,tieuDe,String.valueOf(giaTri) + (hinhThucKm ? "VND" :"%"),ngayBatDau,ngayKetThuc,trangThai};
    }
    public KhuyenMai(int id, String tieuDe, double giaTri, Date ngayBatDau, Date ngayKetThuc,boolean hinhThucKm, boolean trangThai) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.hinhThucKm = hinhThucKm;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isHinhThucKm() {
        return hinhThucKm;
    }

    public void setHinhThucKm(boolean hinhThucKm) {
        this.hinhThucKm = hinhThucKm;
    }
    
}
