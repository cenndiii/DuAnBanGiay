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
    private double gia,tongTien;

    public GioHang() {
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
    
    
}
