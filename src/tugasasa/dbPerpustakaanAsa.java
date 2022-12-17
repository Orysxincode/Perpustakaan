/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasasa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class dbPerpustakaanAsa {
    ArrayList<Buku> listBuku = new ArrayList<>();    
    Buku buku = null;
    
    String url = "jdbc:mysql://localhost/perpus_sederhana";
    String user = "root";
    String password = "";
    Connection conn = null;
    PreparedStatement pstmt;
    Statement stmt;
    ResultSet rs;
    
    dbPerpustakaanAsa (String url, String usr, String pwd){
        this.url = url;
        user = usr;
        password = pwd;
        dbConnect();
    }
    
    void dbConnect(){
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(MariaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("sukses");
    }
    
    Connection getConnection () {
        return conn;
    }
    
    //get data dari database
    ArrayList<Buku> getBuku () throws SQLException {
        listBuku.clear();
        stmt = null;
        rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM buku");
        if (rs != null){ 
            while (rs.next()){
                buku = new Buku(
                    rs.getInt("kode"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getInt("halaman"),
                    rs.getString("lokasi")
                );
                listBuku.add(buku);
            }
        }
        return listBuku;
    }
    
    
    //insert data ke database
    int insertBuku(String kode, String judul, String pengarang,            //    I N S E R T 
            String halaman, String lokasi){ 
        String kueri = "INSERT INTO buku VALUES (?,?,?,?,?)"; 
        pstmt = null;
        int rq = 0;
        try {
            pstmt = conn.prepareStatement(kueri);
            pstmt.setString(1, kode);
            pstmt.setString(2, judul);
            pstmt.setString(3, pengarang);
            pstmt.setString(4, halaman);
            pstmt.setString(5, lokasi);
            rq = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rq;
    }
    
    //update data pada database
    int updateBuku(String kode, String judul, String pengarang,            //    U P D A T E
            String halaman, String lokasi){ 
        String kueri = "UPDATE buku SET judul = ?, "
                + "pengarang = ?, halaman = ?, lokasi = ? "
                + "WHERE kode = ?";
        pstmt = null;
        int rq = 0;
        try {
            pstmt = conn.prepareStatement(kueri);
            pstmt.setString(1, judul);
            pstmt.setString(2, pengarang);
            pstmt.setString(3, halaman);
            pstmt.setString(4, lokasi);
            pstmt.setString(5, kode);
            rq = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rq;
    }
    
    //delete data pada database
    int deleteBuku(String kode){
        String kueri = "DELETE FROM buku WHERE kode = ?";
        pstmt = null;
        int rq = 0;
        try {
            pstmt = conn.prepareStatement(kueri); 
            pstmt.setString(1, kode);
            rq = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rq;
    }
}