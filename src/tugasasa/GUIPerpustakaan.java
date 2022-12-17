/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    // Source: https://www.geeksforgeeks.org/remove-an-element-at-specific-index-from-an-array-in-java/
    public static String[] removeTheElement(String[] arr, int index)
    {
 
        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null || index < 0
            || index >= arr.length) {
 
            return arr;
        }
 
        // Create another array of size one less
        String[] anotherArray = new String[arr.length - 1];
 
        // Copy the elements except the index
        // from original array to the other array
        for (int i = 0, k = 0; i < arr.length; i++) {
 
            // if the index is
            // the removal element index
            if (i == index) {
                continue;
            }
 
            // if the index is not
            // the removal element index
            anotherArray[k++] = arr[i];
        }
 
        // return the resultant array
        return anotherArray;
    }
    
    public List<String> getDictionary(){
        String str = "";
        for(Buku data:dataPerpus.listBuku){
            str += data.getJudul() + " ";
        }
        String[] temp = str.toLowerCase().split(" ");
//        
//        for(int i = 0; i < temp.length; i++){
//            for(int j = 1; j < temp.length - i; j++){
//                if (temp[i].equalsIgnoreCase(temp[j])){
//                    temp = removeTheElement(temp, j);
//                }
//            }
//        }
        List<String> dict = Arrays.asList(temp);
        return dict;
    }
    
    // Source code dari: https://www.geeksforgeeks.org/word-break-problem-using-backtracking/
    
    public void wordBreak(int n, List<String> dict, String s){
        String ans = "";
        wordBreakUtil(n, s, dict, ans);
    }
    
    public void wordBreakUtil(int n, String s, List<String> dict, String ans){
        listPencarian = new ArrayList<>();
        for(int i = 1; i <= n; i++){
            // Mengekstrak substring dari 0 ke i dari prefix (huruf awal ke akhir)
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
    } // Time Complexity: O(2^n)
    
    public void search(String judul) {
        String[] wordList;
        listPencarian = null;
        wordBreak(judul.length(), dictionary, judul);
        if (!judul.isBlank()) {
            wordList = new String[listPencarian.size()];
            int i = 0;
            for (String pencarian : listPencarian) {
                wordList[i] = pencarian;
                i++;
            }
            for (Buku cari : dataPerpus.getListBuku()) {
                try {
                    if (cari.getJudul() != null && cari.getJudul().toLowerCase().contains(wordList[0])) {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        model.setRowCount(0);
                        Object[] row = new Object[jTable1.getColumnCount()];
                        row[0] = cari.getKode();
                        row[1] = cari.getJudul();
                        row[2] = cari.getPengarang();
                        row[3] = cari.getHalaman();
                        row[4] = cari.getLokasi();
                        model.addRow(row);
                    }
//                    } else if (cari.getJudul() != null && cari.getJudul().toLowerCase().contains(judul)) {
//                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//                        model.setRowCount(0);
//                        Object[] row = new Object[jTable1.getColumnCount()];
//                        row[0] = cari.getKode();
//                        row[1] = cari.getJudul();
//                        row[2] = cari.getPengarang();
//                        row[3] = cari.getHalaman();
//                        row[4] = cari.getLokasi();
//                        model.addRow(row);
//                    }
                } catch (Exception e) {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);
                }
            }
        }
    }
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
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

        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset Pencarian");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Judul");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

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
                                .addComponent(jTextField2)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 164, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(143, 143, 143))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        String text = jTextField2.getText();
        if(!text.isEmpty()){
            search(text);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        fillTableArrayList();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyTyped

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
