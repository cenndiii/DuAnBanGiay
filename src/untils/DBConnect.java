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
        return DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=DuAn1_FourShoes;user=sa;password=1234;characterEncoding=UTF-8;encrypt=true;trustservercertificate=true");
    }

}
