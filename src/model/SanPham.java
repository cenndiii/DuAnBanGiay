/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class SanPham {
    private int idSP;
    private String tenSP;
    private double giaNhap;
    private double giaBan;
    private int soLuong;
    private String xuatSu;
    private String hang;
    private String mauSac;
    private String size;
    private String moTa;

    public Object[] toDataRow(){
        return new Object[]{
            idSP,tenSP,giaNhap,giaBan,soLuong,xuatSu,hang,mauSac,size,moTa
        };
    }

    public SanPham(String tenSP, double giaNhap, double giaBan, int soLuong, String xuatSu, String hang, String mauSac, String size, String moTa) {
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.xuatSu = xuatSu;
        this.hang = hang;
        this.mauSac = mauSac;
        this.size = size;
        this.moTa = moTa;
    }
    
    
    
    public SanPham(int idSP, String tenSP, double giaNhap, double giaBan, int soLuong, String xuatSu, String hang, String mauSac, String size, String moTa) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.xuatSu = xuatSu;
        this.hang = hang;
        this.mauSac = mauSac;
        this.size = size;
        this.moTa = moTa;
    }

    public SanPham() {
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    
}
