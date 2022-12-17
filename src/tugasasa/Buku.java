/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasasa;

/**
 *
 * @author ACER
 */
public class Buku {
    private int kode;
    private String judul;
    private String pengarang;
    private int halaman;
    private String lokasi;

    public Buku(int kode, String judul, String pengarang, int halaman, String lokasi) {
        this.kode = kode;
        this.judul = judul;
        this.pengarang = pengarang;
        this.halaman = halaman;
        this.lokasi = lokasi;
    }

    public int getKode() {
        return kode;
    }

    public String getJudul() {
        return judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public int getHalaman() {
        return halaman;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public void setHalaman(int halaman) {
        this.halaman = halaman;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    @Override
    public String toString() {
        return "Buku{" + "kode=" + kode + ", judul=" + judul + ", pengarang=" + pengarang + ", halaman=" + halaman + ", lokasi=" + lokasi + '}';
    }

}
