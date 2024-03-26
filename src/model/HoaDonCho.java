/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class HoaDonCho {
    private int stt;
    private String maHDC;
    private String ngayTao;
    private int idNv;
    private String tenNV;
    private String trangThai;
    

    public HoaDonCho() {
    }

    public HoaDonCho(int stt, String maHDC, String ngayTao, String tenNV, String trangThai) {
        this.stt = stt;
        this.maHDC = maHDC;
        this.ngayTao = ngayTao;
        this.tenNV = tenNV;
        this.trangThai = trangThai;
    }

    public HoaDonCho(String maHDC, int idNv) {
        this.maHDC = maHDC;
        this.idNv = idNv;
    }

    
    public HoaDonCho(String maHDC, String tenNV) {
        this.maHDC = maHDC;
        this.tenNV = tenNV;
    }

    public HoaDonCho(int stt, String maHDC, String ngayTao, int idNv, String trangThai) {
        this.stt = stt;
        this.maHDC = maHDC;
        this.ngayTao = ngayTao;
        this.idNv = idNv;
        this.trangThai = trangThai;
    }

    public int getIdNv() {
        return idNv;
    }

    public void setIdNv(int idNv) {
        this.idNv = idNv;
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

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
