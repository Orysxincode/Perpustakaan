/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasasa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ACER
 */
public class DataPerpustakaanAsa {
    ArrayList<Buku> listBuku = new ArrayList<Buku>();
    dbPerpustakaanAsa dbPerpus = null;

    public DataPerpustakaanAsa() {
        dbPerpus = new dbPerpustakaanAsa("jdbc:mysql://localhost/perpus_sederhana","root","");
        try {
            listBuku = dbPerpus.getBuku();
        } catch (SQLException ex) {
            Logger.getLogger(DataPerpustakaanAsa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Buku> getListBuku() {
        return listBuku;
    }

    public void setListBuku(ArrayList<Buku> listBuku) {
        this.listBuku = listBuku;
    }

    public dbPerpustakaanAsa getDbPerpus() {
        return dbPerpus;
    }

    public void setDbPerpus(dbPerpustakaanAsa dbPerpus) {
        this.dbPerpus = dbPerpus;
    }
    
}
