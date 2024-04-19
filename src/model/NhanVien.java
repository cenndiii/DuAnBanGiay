/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class NhanVien {

    private int ID;
    private String ten;
    private String tenDem;
    private String ho;
    private String ngaySinh;
    private Boolean gioiTinh;
    private String sdt;
    private int vaiTro;
    private String email;
    private String taiKhoan;
    private String matKhau;
    private Boolean trangThai;

    public Object[] toDataROw() {
        String gt = "";
        if (gioiTinh) {
            gt = "Nam";
        } else if (!gioiTinh) {
            gt = "Nữ";
        }
        String vt = "";
        if (vaiTro == 0) {
            vt = "Nhân viên";
        } else if (vaiTro == 1) {
            vt = "Admin";
        }

        if (ho.isBlank() && !tenDem.isBlank()) {
            ten = tenDem + " " + ten;
        } else if (tenDem.isBlank() && !ho.isBlank()) {
            ten = ho + " " + ten;
        } else if (!tenDem.isBlank() && !ho.isBlank() && !ten.isBlank()) {
            ten = ho + " " + tenDem + " " + ten;
        }
        return new Object[]{
            ID, ten, email, gt, sdt, taiKhoan, matKhau, ngaySinh, trangThai == true ? "Đang làm" : "Nghỉ Làm", vt
        };

    }

    public NhanVien() {
    }

    public NhanVien(int ID, String ten, String tenDem, String ho, String ngaySinh, Boolean gioiTinh, String sdt, int vaiTro, String email, String taiKhoan, String matKhau, Boolean trangThai) {
        this.ID = ID;
        this.ten = ten;
        this.tenDem = tenDem;
        this.ho = ho;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.vaiTro = vaiTro;
        this.email = email;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenDem() {
        return tenDem;
    }

    public void setTenDem(String tenDem) {
        this.tenDem = tenDem;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(int vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
