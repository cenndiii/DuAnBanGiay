/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package untils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hoàng Chi
 */
public class DBConnect {

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=DA1_FOURSHOES;user=sa;password=1234;encrypt=true;trustservercertificate=true");
    }

    public void a() {
        try {
            Statement s = openConnection().createStatement();
            ResultSet rs = s.executeQuery("Select * from NHANVIEN");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        DBConnect d = new DBConnect();
        d.a();
    }
}
