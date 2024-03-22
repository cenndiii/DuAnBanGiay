/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dinhq
 */
public class KhachHang {
    private String ID;
    private String Ten;
    private String TenDem;
    private String ho;
    private Boolean gioiTinh;
    private String ngaySinh;
    private String mail;
    private String SDT;
   
    

    public KhachHang() {
    }

    public KhachHang(String ID, String Ten, String TenDem, String ho, Boolean gioiTinh, String ngaySinh, String mail, String SDT) {
        this.ID = ID;
        this.Ten = Ten;
        this.TenDem = TenDem;
        this.ho = ho;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.mail = mail;
        this.SDT = SDT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public String getTenDem() {
        return TenDem;
    }

    public void setTenDem(String TenDem) {
        this.TenDem = TenDem;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    

}
