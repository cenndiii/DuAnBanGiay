/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class GioHang {
    private int IdSP,soLuong;
    private String tenSp;
    private String xuatSu;
    private String hang;
    private String mauSac;
    private String size, chatLieu, danhMuc;
    private String moTa;
    private double gia,tongTien;

    public GioHang() {
    }

    public GioHang(int IdSP, int soLuong, String tenSp, String xuatSu, String hang, String mauSac, String size, String chatLieu, String danhMuc, String moTa, double gia, double tongTien) {
        this.IdSP = IdSP;
        this.soLuong = soLuong;
        this.tenSp = tenSp;
        this.xuatSu = xuatSu;
        this.hang = hang;
        this.mauSac = mauSac;
        this.size = size;
        this.chatLieu = chatLieu;
        this.danhMuc = danhMuc;
        this.moTa = moTa;
        this.gia = gia;
        this.tongTien = tongTien;
    }

    
    public GioHang(int IdSP, int soLuong, String tenSp, double gia, double tongTien) {
        this.IdSP = IdSP;
        this.soLuong = soLuong;
        this.tenSp = tenSp;
        this.gia = gia;
        this.tongTien = tongTien;
    }

    public int getIdSP() {
        return IdSP;
    }

    public void setIdSP(int IdSP) {
        this.IdSP = IdSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getXuatSu() {
        return xuatSu;
    }

    public void setXuatSu(String xuatSu) {
        this.xuatSu = xuatSu;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
}
