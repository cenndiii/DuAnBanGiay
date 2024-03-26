/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import model.GioHang;
import model.SanPham;

/**
 *
 * @author Admin
 */
public class GioHang_Service {

    public ArrayList<GioHang> gioHang = new ArrayList<>();

    public ArrayList<GioHang> getProduct(GioHang spSelected) {
        boolean check = false;
        try {
            if (gioHang.isEmpty()) {
                spSelected.setTongTien(spSelected.getSoLuong() * spSelected.getGia());
                gioHang.add(spSelected);
            } else {
                for (GioHang gh : gioHang) {
                    // them sp             
                    if (gh.getIdSP() == spSelected.getIdSP()) {
                        gh.setSoLuong(gh.getSoLuong() + spSelected.getSoLuong());
                        gh.setTongTien(gh.getSoLuong() * gh.getGia());
                        check = true;
                    }
                }
                if (!check) {
                    spSelected.setTongTien(spSelected.getSoLuong() * spSelected.getGia());
                    gioHang.add(spSelected);
                }
            }
        } catch (Exception e) {
        }
        return gioHang;
    }
}
