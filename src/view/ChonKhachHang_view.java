/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;
import service.KhachHangService;

/**
 *
 * @author Admin
 */
public class ChonKhachHang_view extends javax.swing.JFrame {

    private JTextField txtName;
    private JTextField txtPhoneNumber;
    private JTextField txtId;
    
    DefaultTableModel dtm;
    KhachHangService khs = new KhachHangService();
    int index = -1;
    public String ten, sdt;
    
    public ChonKhachHang_view(JTextField txtName, JTextField txtPhoneNumber, JTextField txtIdKh) {
        initComponents();
        this.txtId = txtIdKh;
        this.txtName = txtName;
        this.txtPhoneNumber = txtPhoneNumber;
        fillTableGuest(khs.getAllKhachHang());
        setLocationRelativeTo(null);
        setTitle("Chọn Khách Hàng");
    }
    
    private ChonKhachHang_view() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void fillTableGuest(List<KhachHang> list) {
        dtm = (DefaultTableModel) tblChooseGuest.getModel();
        dtm.setRowCount(0);
        String Ten = null;
        for (KhachHang kH : list) {
            Ten = kH.getTen();
            if (kH.getHo().isBlank() && !kH.getTenDem().isBlank()) {
                Ten = kH.getTenDem() + " " + kH.getTen();
            } else if (kH.getTenDem().isBlank() && !kH.getHo().isBlank()) {
                Ten = kH.getHo() + " " + kH.getTen();
            } else if (!kH.getTenDem().isBlank() && !kH.getHo().isBlank() && !kH.getTen().isBlank()) {
                Ten = kH.getHo() + " " + kH.getTenDem() + " " + kH.getTen();
            }
            dtm.addRow(new Object[]{kH.getID(), Ten, kH.getGioiTinh() == true ? "Nam" : "Nữ", kH.getNgaySinh(), kH.getMail(), kH.getSDT()});
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tblChooseGuest = new javax.swing.JTable();
        btnChoose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblChooseGuest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên", "Giới Tính", "Ngày Sinh", "Email", "Số Điện Thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChooseGuest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChooseGuestMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblChooseGuest);

        btnChoose.setText("Chọn");
        btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(276, 276, 276)
                .addComponent(btnChoose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChoose)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblChooseGuestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChooseGuestMouseClicked
        index = tblChooseGuest.getSelectedRow();
    }//GEN-LAST:event_tblChooseGuestMouseClicked

    private void btnChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActionPerformed
        try {
            int idkh;
            idkh = (int) tblChooseGuest.getValueAt(index, 0);
            ten = (String) tblChooseGuest.getValueAt(index, 1);
            sdt = (String) tblChooseGuest.getValueAt(index, 5);
            this.txtName.setText(ten);
            this.txtPhoneNumber.setText(sdt);
            this.txtId.setText(String.valueOf(idkh));
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnChooseActionPerformed

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
            java.util.logging.Logger.getLogger(ChonKhachHang_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChonKhachHang_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChonKhachHang_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChonKhachHang_view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ChonKhachHang_view().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChoose;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblChooseGuest;
    // End of variables declaration//GEN-END:variables
}
