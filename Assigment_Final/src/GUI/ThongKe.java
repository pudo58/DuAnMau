/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class ThongKe extends javax.swing.JDialog {
 DefaultTableModel model,model_nguoihoc,model_diemchuyende,model_doanhthu;
 
    /**
     * Creates new form ThongKe
     * @throws java.sql.SQLException
     */
    public ThongKe() throws SQLException {
        initComponents();
   
        setResizable(false);
        loadCBBKH();
        nguoihoc();
        diemCHuyende();
        findNam();
       
    }
    public void doanhthu() throws SQLException{
        model_doanhthu=(DefaultTableModel)tbl_doanhthu.getModel();
        model_doanhthu.setRowCount(0);
        CallableStatement call=ConnectSQLServer.Connect.con.prepareCall("exec sp_doanhthu ?");
        call.setString(1,String.valueOf(cbb_doanhthu.getSelectedItem()));
        ResultSet rs=call.executeQuery();
        while (rs.next()) {
        model_doanhthu.addRow(new Object[]{
        rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),rs.getDouble(7)});
        

            
        }
    }
    public void diemCHuyende() throws SQLException{
        DecimalFormat format=new DecimalFormat("#.##");
        model_diemchuyende=(DefaultTableModel)tbl_chuyende.getModel();
        model_diemchuyende.setRowCount(0);
        CallableStatement call=ConnectSQLServer.Connect.con.prepareCall("exec sp_diemchuyende");
        ResultSet rs=call.executeQuery();
        while(rs.next()){
            model_diemchuyende.addRow(new Object[]{
            rs.getString(1),rs.getInt(2),rs.getDouble(3),rs.getDouble(4),format.format(rs.getDouble(5))});
        }
    }
    public void nguoihoc() throws SQLException{
        model_nguoihoc=(DefaultTableModel)tbl_nguoihoc.getModel();
    CallableStatement call=ConnectSQLServer.Connect.con.prepareCall("exec sp_luongnguoihoc");
    ResultSet rs=call.executeQuery();
        while (rs.next()) {
            model_nguoihoc.addRow(new Object[]{
            rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4)});
            
        }
}
    public void loadCBBKH() throws SQLException{
        Statement sta=ConnectSQLServer.Connect.con.createStatement();
        ResultSet rs=sta.executeQuery("select macd from khoahoc");
        while(rs.next()){
            cbb_khoahoc.addItem(rs.getString(1));
        }
    }
    public String XepLoai(double diem){
        if(diem<5){
            return "yếu";
        }else{
            if(diem<7){
                return "Trung bình";
            }else if(diem<8){
                return "Khá";
            }
            
        }
        return "Giỏi";
    }
public void BangDiem() throws SQLException{
   model= (DefaultTableModel)tbl_bangdiem.getModel();
   model.setRowCount(0);
    CallableStatement call=ConnectSQLServer.Connect.con.prepareCall("exec sp_bangdiem ?");
    call.setInt(1,getMaKH(String.valueOf(cbb_khoahoc.getSelectedItem())));
    ResultSet rs=call.executeQuery();
    while(rs.next()){
        model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getDouble(3),XepLoai(rs.getDouble(3))});
    }
    
}
void findNam() throws SQLException{
    String sql="SELECT DISTINCT YEAR(NgayKG) FROM KhoaHoc ORDER BY YEAR(NgayKG) DESC";
    PreparedStatement pre=ConnectSQLServer.Connect.con.prepareStatement(sql);
    ResultSet rs=pre.executeQuery();
    while (rs.next()) {
        cbb_doanhthu.addItem(rs.getString(1));
        
    }
    
}
public int getMaKH(String macd) throws SQLException{
    PreparedStatement pre=ConnectSQLServer.Connect.con.prepareStatement("select makh from KHOAHOC join CHUYENDE\n" +
"on KHOAHOC.MACD=CHUYENDE.MACD where Khoahoc.macd=? ");
    pre.setString(1,macd);
    ResultSet rs=pre.executeQuery();
    while(rs.next())return rs.getInt("makh");
    return -1;
    
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbb_khoahoc = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bangdiem = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_nguoihoc = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_chuyende = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbb_doanhthu = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_doanhthu = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("KHÓA HỌC");

        cbb_khoahoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_khoahocItemStateChanged(evt);
            }
        });

        tbl_bangdiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NH", "Họ tên", "Điểm", "Xếp loại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_bangdiem);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbb_khoahoc, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbb_khoahoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BẢNG ĐIỂM", jPanel1);

        tbl_nguoihoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Năm", "Số NH", "Đăng ký sớm nhất", "Đăng Ký muộn nhất"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_nguoihoc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("NGƯỜI HỌC", jPanel2);

        tbl_chuyende.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chuyên đề", "SL ", "Điểm TN", "Điểm CN", "Điểm TB"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_chuyende);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ĐIỂM CHUYÊN ĐỀ", jPanel3);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("NĂM");

        cbb_doanhthu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbb_doanhthuItemStateChanged(evt);
            }
        });

        tbl_doanhthu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chuyên đề", "Số khóa học", "Số học viên", "doanh thu", "Học phí cao nhất", "Thấp nhất", "Học phí avg"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbl_doanhthu);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addComponent(cbb_doanhthu, 0, 507, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane4)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbb_doanhthu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 67, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("DOANH THU", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbb_khoahocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_khoahocItemStateChanged
     try {
         BangDiem();        // TODO add your handling code here:
     } catch (SQLException ex) {
         Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
     }
    }//GEN-LAST:event_cbb_khoahocItemStateChanged

    private void cbb_doanhthuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbb_doanhthuItemStateChanged
     try {
         doanhthu();   
         // TODO add your handling code here:
     } catch (SQLException ex) {
         ex.printStackTrace();
         Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
     }
    }//GEN-LAST:event_cbb_doanhthuItemStateChanged

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbb_doanhthu;
    private javax.swing.JComboBox<String> cbb_khoahoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbl_bangdiem;
    private javax.swing.JTable tbl_chuyende;
    private javax.swing.JTable tbl_doanhthu;
    private javax.swing.JTable tbl_nguoihoc;
    // End of variables declaration//GEN-END:variables
}