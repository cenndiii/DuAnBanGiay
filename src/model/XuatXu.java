
package model;


public class XuatXu {
    private int id;
    private String loai,chiTiet;

    public XuatXu() {
    }

    public XuatXu(int id, String loai, String chiTiet) {
        this.id = id;
        this.loai = loai;
        this.chiTiet = chiTiet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }
    
}
