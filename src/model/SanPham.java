
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
    private String size, chatLieu, danhMuc;
    private String moTa;
    private boolean trangThai;
    private int idKm;

    public Object[] toDataRow() {
        return new Object[]{
            idSP, tenSP, giaNhap, giaBan, soLuong, xuatSu, hang, mauSac, size, chatLieu, danhMuc, moTa, trangThai ? "Đang Hoạt Động" : "Chưa Hoạt Động"
        };
    }

    public Object[] toDataBhSanPhamRow() {
        return new Object[]{
            idSP, tenSP, giaBan, soLuong, xuatSu, hang, mauSac, size, chatLieu, danhMuc, moTa
        };
    }

    public Object[] toDataKmSanPhamRow() {
        return new Object[]{
            idSP, tenSP, idKm==0 ? "Không có": idKm,false
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

    public SanPham(int idSP, String tenSP, double giaNhap, double giaBan, int soLuong, String xuatSu, String hang, String mauSac, String size, String chatLieu, String danhMuc, String moTa, int idKm, boolean trangThai) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.xuatSu = xuatSu;
        this.hang = hang;
        this.mauSac = mauSac;
        this.size = size;
        this.chatLieu = chatLieu;
        this.danhMuc = danhMuc;
        this.moTa = moTa;
        this.idKm = idKm;
        this.trangThai = trangThai;
    }

    public SanPham(int idSP, String tenSP, double giaBan, int soLuong, String xuatSu, String hang, String mauSac, String size, String chatLieu, String danhMuc, String moTa) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.xuatSu = xuatSu;
        this.hang = hang;
        this.mauSac = mauSac;
        this.size = size;
        this.chatLieu = chatLieu;
        this.danhMuc = danhMuc;
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

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdKm() {
        return idKm;
    }

    public void setIdKm(int idKm) {
        this.idKm = idKm;
    }

}
