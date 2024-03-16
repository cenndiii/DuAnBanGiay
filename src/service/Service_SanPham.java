/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.SanPham;
import untils.DBConnect;

/**
 *
 * @author TIEN DUY
 */
public class Service_SanPham {
    List<SanPham> list;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    
}
