/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugasasa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class GUIPerpustakaan extends javax.swing.JFrame {
    DataPerpustakaanAsa dataPerpus = new DataPerpustakaanAsa();
    List <String> listPencarian;
    List <String> dictionary = new ArrayList<>();
    /**
     * Creates new form GUIPerpustakaan
     */
    public GUIPerpustakaan() {
        initComponents();
        fillTableArrayList();
        dictionary = getDictionary();
        System.out.println(dictionary);
    }
    
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        
        Connection con = null;
        
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/perpus_sederhana", "root", "");
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return con;
    }
    
    public void fillTableSQL(){
        Connection con = getConnection();
        Statement ps;
        ResultSet rs;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        try{
            ps = con.createStatement();
            rs = ps.executeQuery("SELECT * from `buku`");
            
            while(rs.next()){
                Object[] row = new Object[jTable1.getColumnCount()];
                row[0] = rs.getInt("kode");
                row[1] = rs.getString("judul");
                row[2] = rs.getString("pengarang");
                row[3] = rs.getInt("halaman");
                row[4] = rs.getString("lokasi");
                model.addRow(row);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void fillTableArrayList(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (Buku data:dataPerpus.listBuku) {
            Object[] row = new Object[jTable1.getColumnCount()];
            row[0] = data.getKode();
            row[1] = data.getJudul();
            row[2] = data.getPengarang();
            row[3] = data.getHalaman();
            row[4] = data.getLokasi();
            model.addRow(row);
        }
    }
    
    public List<String> getDictionary(){
        String str = "";
        for(Buku data:dataPerpus.listBuku){
            str += data.getJudul() + " ";
        }
        String[] temp = str.toLowerCase().split(" ");

        List<String> dict = Arrays.asList(temp);
        return dict;
    }
    
    // Source code dari: https://www.geeksforgeeks.org/word-break-problem-using-backtracking/
    
    public void wordBreak(int n, List<String> dict, String s){
        String ans = "";
        wordBreakUtil(n, s, dict, ans);
    }
    
    public void wordBreakUtil(int n, String s, List<String> dict, String ans){
        for(int i = 1; i <= n; i++){
            // Mengekstrak substring dengan range dari 0 ke i dari prefix (huruf awal ke akhir)
            String prefix = s.substring(0, i);
            
            // Jika di dalam dictionary terdapat prefix dari substring,
            // maka program akan mengecek string yang tersisa. Selain itu,
            // prefix diabaikan dan mencoba prefix selanjutnya
            if(dict.contains(prefix)){
                // Jika elemen habis, maka akan dimasukkan dalam array
                if(i == n){
                    // Tambahkan elemen ke dalam prefix sebelumnya
                    ans += prefix;
                    listPencarian.add(ans);
                    System.out.println(listPencarian);
                    return;
                }
                wordBreakUtil(n - i, s.substring(i, n), dict, ans+prefix+" ");
            }
        }
    } // Time Complexity: O(2^n) karena terdapat sebuah rekursi dalam perulangan for
    
    // Method untuk mencari (Menggunakan brute-force algorithm berupa Sequential Search)
    public void search(String judul) {
        String[] wordList; // Kumpulan kata dari hasil backtracking dictionary
        listPencarian = new ArrayList<>(); // Mengosongkan listPencarian yang didapatkan dari wordBreak
                                            //agar tidak terjadi error secara semantik ketika mengisikan ulang laman pencarian
        judul = judul.replaceAll("\\s+", ""); //Menghapus spasi pada pencarian
        wordBreak(judul.length(), dictionary, judul); // Menjalankan metode wordBreak untuk memisah-misah kata yang berhubungan
        if (!judul.isBlank()) { // Jika string judul tidak kosong (hanya mengandung whitespace atau spasi)
            wordList = new String[listPencarian.size()]; // Menginisialisasikan array dengan panjang dari hasil wordBreak
            int i = 0; // Menginisialisasikan iterasi i dengan nilai 0
            for (String pencarian : listPencarian) { // Untuk setiap array dari listPencarian akan dijadikan variabel String pencarian | O(n)
                wordList[i] = pencarian; // Memasukkan listPencarian ke dalam array yang telah diisi
                i++; // Menambahkan iterasi i dengan 1
            }
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel(); // Mendapatkan model dari tabel
            model.setRowCount(0); // Mereset tabel untuk digunakan dalam menampilkan hasil search
            for (Buku cari : dataPerpus.getListBuku()) { // Untuk setiap buku yang terdapat dalam arrayList dataPerpus | O(n)
                try { // Mencoba kode dan menghindari/mengganti error Exception dengan menggunakan catch
                    if (wordList.length != 0) { // Jika wordList tidak kosong, maka line ini dijalankan
                        for (String word : wordList) { // Untuk setiap String yang terdapat di wordList | O(n*n) = O(n^2)
                            if (cari.getJudul() != null && cari.getJudul().toLowerCase().contains(word.toLowerCase())) { // Jika buku ditemukan dengan menggunakan wordList
                                Object[] row = new Object[jTable1.getColumnCount()]; // Menginstansiasikan Object array untuk membuat tabel dalam bentuk baris
                                row[0] = cari.getKode(); // Menambahkan kode ke baris
                                row[1] = cari.getJudul(); // Menambahkan judul ke baris
                                row[2] = cari.getPengarang(); // Menambahkan pengarang ke baris
                                row[3] = cari.getHalaman(); // Menambahkan halaman ke baris
                                row[4] = cari.getLokasi(); // Menambahkan lokasi ke baris
                                model.addRow(row); // Menambahkan semua hasil row ke dalam tabel
                            }
                        }
                    } else { // Jika wordList kosong, maka line ini dijalankan
                        if (cari.getJudul() != null && cari.getJudul().toLowerCase().contains(judul.toLowerCase())) { // Jika buku ditemukan dengan menggunakan judul
                            Object[] row = new Object[jTable1.getColumnCount()]; // Menginstansiasikan Object array untuk membuat tabel dalam bentuk baris
                            row[0] = cari.getKode(); // Menambahkan kode ke baris
                            row[1] = cari.getJudul(); // Menambahkan judul ke baris
                            row[2] = cari.getPengarang(); // Menambahkan pengarang ke baris
                            row[3] = cari.getHalaman(); // Menambahkan halaman ke baris
                            row[4] = cari.getLokasi(); // Menambahkan lokasi ke baris
                            model.addRow(row); // Menambahkan semua hasil row ke dalam tabel
                        }
                    }
                } catch (Exception e) { // Menangkap error yang terdapat dalam percobaan try dan menggantikan pesan error dengan perintah tertentu
                    model = (DefaultTableModel) jTable1.getModel(); // Mendapatkan model dari tabel
                    model.setRowCount(0); // Mereset tabel untuk digunakan dalam menampilkan hasil search
                }
            }
        }
    }
    // Search time complexity: O(n + n^2) karena terdapat 1 perulangan for-loop, dan 1 perulangan bercabang dalam for-loop
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        mencari = new javax.swing.JButton();
        resetPencarian = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cariJudul = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Perpustakaan Sederhana");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Judul", "Pengarang", "Halaman", "Lokasi"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        mencari.setText("Cari");
        mencari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mencariActionPerformed(evt);
            }
        });

        resetPencarian.setText("Reset Pencarian");
        resetPencarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetPencarianActionPerformed(evt);
            }
        });

        jLabel3.setText("Judul");

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel5.setText("Cari Berdasarkan Judul");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(51, 51, 51)
                                .addComponent(cariJudul)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 164, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(143, 143, 143))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(resetPencarian)
                                .addGap(18, 18, 18)
                                .addComponent(mencari, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cariJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetPencarian)
                    .addComponent(mencari))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Tombol cari ditekan
    private void mencariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mencariActionPerformed
        
        String text = cariJudul.getText();
        if(!text.isEmpty()){
            search(text);
        }
    }//GEN-LAST:event_mencariActionPerformed
    
    // Tombol reset pencarian ditekan
    private void resetPencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPencarianActionPerformed
        fillTableArrayList();
        cariJudul.setText("");
    }//GEN-LAST:event_resetPencarianActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIPerpustakaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIPerpustakaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIPerpustakaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIPerpustakaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIPerpustakaan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cariJudul;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton mencari;
    private javax.swing.JButton resetPencarian;
    // End of variables declaration//GEN-END:variables
}
