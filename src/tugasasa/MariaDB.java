/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasasa;

/**
 *
 * @author ACER
 */
public class MariaDB {
    
    public static void main(String[] args) {
        DataPerpustakaanAsa dataPerpus = new DataPerpustakaanAsa();

        
        //print daftar buku 
        for (Buku data:dataPerpus.listBuku) {
            System.out.println(data);
        }
        dataPerpus.dbPerpus.insertBuku("595", "Time and the Literary", "Jay Clayton", "288", "9H");
//        dataPerpus.dbPerpus.insertBuku("74", "The Time Keeper", "Mitch Alborn", "224", "5F");
        
    }    
}

