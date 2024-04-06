/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class HoaDonChiTiet {
    private int stt;
    private String maHDC;
    private String ngayTao;
    private String ngayThanhToan;
    private int idKh;
    private int idNV;
    private String trangThai;
    private double tongTien;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int stt, String maHDC, String ngayTao, String ngayThanhToan, int idKh, int idNV, String trangThai, double tongTien) {
        this.stt = stt;
        this.maHDC = maHDC;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.idKh = idKh;
        this.idNV = idNV;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMaHDC() {
        return maHDC;
    }

    public void setMaHDC(String maHDC) {
        this.maHDC = maHDC;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public int getIdKh() {
        return idKh;
    }

    public void setIdKh(int idKh) {
        this.idKh = idKh;
    }

    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
}
