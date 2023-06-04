package HMS;

import java.awt.Color;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;


/*@author Allein
 */
public class Nurse extends javax.swing.JFrame {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String Imagename = null;
    byte[] uimage = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public Nurse() {
        initComponents();
        System.out.println("Application Started\n" + dtf.format(now));
        view_table();
    }

    public void view_table() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from pat_nurse";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            Nurse_tablepatinfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            Nurse_tablepatinfo.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                con.close();
                System.out.println("Connection Closed");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void Assignward_print() {
        if (txtpatid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_age.getText().isEmpty() || cmbservice.getSelectedItem().equals("Select") || cmbgender.getSelectedItem().equals("Select") || cmbmarital.getSelectedItem().equals("Select") || cmbbloodgrp.getSelectedItem().equals("Select") || cmbpattype.getSelectedItem().equals("Select") || cmbwardtype.getSelectedItem().equals("Select") || regdate.getDate() == null || bookingdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(regdate.getDate());
            Date_Format.format(bookingdate.getDate());
            String Qty1 = (txtpatid.getText());
            String Qty2 = (txt_FN.getText());
            String Qty3 = (txt_LN.getText());
            String Qty4 = (cmbgender.getSelectedItem().toString());
            String Qty5 = (cmbmarital.getSelectedItem().toString());
            String Qty6 = (txt_guardian.getText());
            String Qty7 = (cmbbloodgrp.getSelectedItem().toString());
            String Qty8 = (cmbpattype.getSelectedItem().toString());
            String Qty9 = (txt_age.getText());
            String Qty10 = ((JTextField) regdate.getDateEditor().getUiComponent()).getText();
            String Qty11 = (cmbservice.getSelectedItem().toString());
            String Qty12 = ((JTextField) bookingdate.getDateEditor().getUiComponent()).getText();
            String Qty13 = (cmbwardtype.getSelectedItem().toString());
            String Qty14 = (cmbwardno.getSelectedItem().toString());
            String Qty15 = (cmbbedno.getSelectedItem().toString());
            txt_nurseprint_pat.setText("");
            txt_nurseprint_pat.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tWard Receipt\n"
                    + "\nPatient ID:\t\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nGender:\t\t" + Qty4
                    + "\nMarital Status:\t\t" + Qty5
                    + "\nGuardian Name:\t" + Qty6
                    + "\nBlood Group:\t\t" + Qty7
                    + "\nPatient Type:\t\t" + Qty8
                    + "\nAge:\t\t" + Qty9
                    + "\nReg Date:\t\t" + Qty10
                    + "\nService:\t\t" + Qty11
                    + "\nBooking Date:\t\t" + Qty12
                    + "\nWard Type:\t\t" + Qty13
                    + "\nWard No:\t\t" + Qty14
                    + "\nBed No:\t\t" + Qty15
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                    + "\n\tWE WISH YOU A QUICK RECOVERY");
            try {
                txt_nurseprint_pat.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            txtsrch.setText("");
            txtpatid.setText("");
            txt_FN.setText("");
            txt_LN.setText("");
            cmbgender.setSelectedItem("Select");
            cmbmarital.setSelectedItem("Select");
            txt_guardian.setText("");
            cmbbloodgrp.setSelectedItem("Select");
            cmbpattype.setSelectedItem("Select");
            txt_age.setText("");
            regdate.setDate(null);
            cmbservice.setSelectedItem("Select");
            bookingdate.setDate(null);
            cmbwardtype.setSelectedItem("Select");
            cmbwardno.setSelectedItem("Select");
            cmbbedno.removeAllItems();
            cmbbedno.addItem("Select");
        }
    }

    public void DischargedWard_print() {
        if (txtpatid2.getText().isEmpty() || txt_FN2.getText().isEmpty() || txt_LN2.getText().isEmpty() || txt_guardian2.getText().isEmpty() || txt_age2.getText().isEmpty() || cmbservice2.getSelectedItem().equals("Select") || cmbgender2.getSelectedItem().equals("Select") || cmbmarital2.getSelectedItem().equals("Select") || cmbbloodgrp2.getSelectedItem().equals("Select") || cmbpattype2.getSelectedItem().equals("Select") || cmbwardtype2.getSelectedItem().equals("Select") || regdate2.getDate() == null || bookingdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(regdate2.getDate());
            Date_Format.format(bookingdate2.getDate());
            Date_Format.format(dischargedate2.getDate());
            String Qty1 = (txtpatid2.getText());
            String Qty2 = (txt_FN2.getText());
            String Qty3 = (txt_LN2.getText());
            String Qty4 = (cmbgender2.getSelectedItem().toString());
            String Qty5 = (cmbmarital2.getSelectedItem().toString());
            String Qty6 = (txt_guardian2.getText());
            String Qty7 = (cmbbloodgrp2.getSelectedItem().toString());
            String Qty8 = (cmbpattype2.getSelectedItem().toString());
            String Qty9 = (txt_age2.getText());
            String Qty10 = ((JTextField) regdate2.getDateEditor().getUiComponent()).getText();
            String Qty11 = (cmbservice2.getSelectedItem().toString());
            String Qty12 = ((JTextField) bookingdate2.getDateEditor().getUiComponent()).getText();
            String Qty13 = (cmbwardtype2.getSelectedItem().toString());
            String Qty14 = (cmbwardno2.getSelectedItem().toString());
            String Qty15 = (cmbbedno2.getSelectedItem().toString());
            String Qty16 = ((JTextField) dischargedate2.getDateEditor().getUiComponent()).getText();
            String Qty17 = (cmbstatus.getSelectedItem().toString());

            txt_nurseprint_pat.setText("");
            txt_nurseprint_pat.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tWard Discharge Receipt\n"
                    + "\nPatient ID:\t\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nGender:\t\t" + Qty4
                    + "\nMarital Status:\t\t" + Qty5
                    + "\nGuardian Name:\t" + Qty6
                    + "\nBlood Group:\t\t" + Qty7
                    + "\nPatient Type:\t\t" + Qty8
                    + "\nAge:\t\t" + Qty9
                    + "\nReg Date:\t\t" + Qty10
                    + "\nService:\t\t" + Qty11
                    + "\nBooking Date:\t\t" + Qty12
                    + "\nWard Type:\t\t" + Qty13
                    + "\nWard No:\t\t" + Qty14
                    + "\nBed No:\t\t" + Qty15
                    + "\nDisCharge Date:\t" + Qty16
                    + "\nStatus:\t\t" + Qty17
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                    + "\n\tWE WISH YOU A QUICK RECOVERY");
            try {
                txt_nurseprint_pat.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            txtsrch2.setText("");
            txtpatid2.setText("");
            txt_FN2.setText("");
            txt_LN2.setText("");
            cmbgender2.setSelectedItem("Select");
            cmbmarital2.setSelectedItem("Select");
            txt_guardian2.setText("");
            cmbbloodgrp2.setSelectedItem("Select");
            cmbpattype2.setSelectedItem("Select");
            txt_age2.setText("");
            regdate2.setDate(null);
            cmbservice2.setSelectedItem("Select");
            bookingdate2.setDate(null);
            dischargedate2.setDate(null);
            cmbwardtype2.setSelectedItem("Select");
            cmbwardno2.setSelectedItem("Select");
            cmbbedno2.removeAllItems();
            cmbbedno2.addItem("Select");
            dischargedate2.setDate(null);
            cmbstatus.setSelectedItem("Select");
        }
    }

    public void username(String user) {
        lbluser.setText(user);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Menu = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        nurse_panel = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        btn_getpatient = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        nurse_btnrefesh = new javax.swing.JButton();
        btn_search = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtsampleid = new javax.swing.JTextField();
        combosampletype = new javax.swing.JComboBox<>();
        txtbodytemp = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        txtsearch = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtpatientid = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        txtFN = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        txtLN = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        combogender = new javax.swing.JComboBox<>();
        jLabel106 = new javax.swing.JLabel();
        combomaritalstatus = new javax.swing.JComboBox<>();
        jLabel107 = new javax.swing.JLabel();
        txtguardian = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        combobloodgroup = new javax.swing.JComboBox<>();
        jLabel109 = new javax.swing.JLabel();
        combopatienttype = new javax.swing.JComboBox<>();
        jLabel110 = new javax.swing.JLabel();
        txtweight = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        txtage = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        comboservice = new javax.swing.JComboBox<>();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        Regdate = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_nurseprint_pat = new javax.swing.JTextArea();
        txtdisease = new javax.swing.JTextField();
        txtbldpressure = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtsymptoms = new javax.swing.JTextArea();
        ward_management = new javax.swing.JPanel();
        ward_menu = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        btn_wardpat = new javax.swing.JButton();
        btn_ward_update = new javax.swing.JButton();
        btn_wardrefresh = new javax.swing.JButton();
        btn_wardsearch = new javax.swing.JButton();
        btn_wardassign = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        txtsrch = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtpatid = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txt_FN = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txt_LN = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        cmbgender = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        cmbmarital = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        txt_guardian = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        cmbbloodgrp = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        cmbpattype = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        txt_age = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        regdate = new com.toedter.calendar.JDateChooser();
        jLabel48 = new javax.swing.JLabel();
        cmbservice = new javax.swing.JComboBox<>();
        jLabel51 = new javax.swing.JLabel();
        bookingdate = new com.toedter.calendar.JDateChooser();
        jLabel50 = new javax.swing.JLabel();
        cmbwardno = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        cmbbedno = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        cmbwardtype = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        btn_warddis_getpat = new javax.swing.JButton();
        btn_dischargerefresh = new javax.swing.JButton();
        btnsrch = new javax.swing.JButton();
        btn_discharge = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        txtsrch2 = new javax.swing.JTextField();
        txtpatid2 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        txt_FN2 = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        txt_LN2 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        cmbgender2 = new javax.swing.JComboBox<>();
        jLabel75 = new javax.swing.JLabel();
        cmbmarital2 = new javax.swing.JComboBox<>();
        jLabel76 = new javax.swing.JLabel();
        txt_guardian2 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        cmbbloodgrp2 = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        cmbpattype2 = new javax.swing.JComboBox<>();
        jLabel79 = new javax.swing.JLabel();
        txt_age2 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        regdate2 = new com.toedter.calendar.JDateChooser();
        jLabel81 = new javax.swing.JLabel();
        cmbbedno2 = new javax.swing.JComboBox<>();
        cmbwardno2 = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        cmbwardtype2 = new javax.swing.JComboBox<>();
        dischargedate2 = new com.toedter.calendar.JDateChooser();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        bookingdate2 = new com.toedter.calendar.JDateChooser();
        jLabel86 = new javax.swing.JLabel();
        cmbservice2 = new javax.swing.JComboBox<>();
        jLabel87 = new javax.swing.JLabel();
        cmbstatus = new javax.swing.JComboBox<>();
        Pat_info = new javax.swing.JPanel();
        Scrollpane = new javax.swing.JScrollPane();
        Nurse_tablepatinfo = new javax.swing.JTable();
        nurse_btnrefesh1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtpatientid1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtFN1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtLN1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        combobloodgroup1 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        combopatienttype1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jtxtsearch = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtsampleid1 = new javax.swing.JTextField();
        combogender1 = new javax.swing.JComboBox<>();
        combosampletype1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        typeoftest = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txttechnicianname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtdiagnosis = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtresults = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        testdate = new com.toedter.calendar.JDateChooser();
        combomaritalstatus1 = new javax.swing.JComboBox<>();
        txtguardian1 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtweight1 = new javax.swing.JTextField();
        txtage1 = new javax.swing.JTextField();
        resultsdate = new com.toedter.calendar.JDateChooser();
        typeoftest1 = new javax.swing.JLabel();
        combotypetest = new javax.swing.JComboBox<>();
        txtbodytemp1 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnrefresh = new javax.swing.JButton();
        btnViewall = new javax.swing.JButton();
        man_account = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jbtnchangepass = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        lblimage = new javax.swing.JLabel();
        btnuploadimage = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lbluser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximizedBounds(new java.awt.Rectangle(1230, 780, 1230, 780));
        setMaximumSize(new java.awt.Dimension(1230, 780));
        setPreferredSize(new java.awt.Dimension(1230, 780));
        setResizable(false);
        setSize(new java.awt.Dimension(1230, 780));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/prescription (2).png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        Menu.setBackground(new java.awt.Color(153, 204, 255));
        Menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Menu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        nurse_panel.setBackground(new java.awt.Color(204, 204, 255));

        jPanel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel24.setMaximumSize(new java.awt.Dimension(163, 600));
        jPanel24.setMinimumSize(new java.awt.Dimension(163, 600));

        btn_getpatient.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_getpatient.setText("PATIENT");
        btn_getpatient.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_getpatient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_getpatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getpatientActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_50px.png"))); // NOI18N
        btn_update.setText("UPDATE");
        btn_update.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        nurse_btnrefesh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        nurse_btnrefesh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        nurse_btnrefesh.setText("REFRESH");
        nurse_btnrefesh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        nurse_btnrefesh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nurse_btnrefesh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nurse_btnrefeshActionPerformed(evt);
            }
        });

        btn_search.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btn_search.setText("SEARCH");
        btn_search.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        btn_save.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btn_save.setText("SAVE");
        btn_save.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_search, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_getpatient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_update, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nurse_btnrefesh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_save, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(btn_getpatient, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(nurse_btnrefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );

        jPanel24Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_search, nurse_btnrefesh});

        jPanel25.setBackground(new java.awt.Color(204, 204, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel25.setMaximumSize(new java.awt.Dimension(1052, 600));
        jPanel25.setMinimumSize(new java.awt.Dimension(1052, 600));
        jPanel25.setPreferredSize(new java.awt.Dimension(1052, 600));

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel99.setText("Symptoms");
        jLabel99.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Blood Pressure");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setText("Sample Id");
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsampleid.setEditable(false);
        txtsampleid.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        combosampletype.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combosampletype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Blood", "Stool", "Urine", "Saliva", "Skin", "Semen", "Rectal", "Spinal Fluids", "Cervical", "Molecular(DNA)", "Tissue" }));
        combosampletype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosampletypeActionPerformed(evt);
            }
        });

        txtbodytemp.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel100.setText("Service");
        jLabel100.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel101.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel101.setText("Sample type");
        jLabel101.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsearch.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel102.setText("Search ");
        jLabel102.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setText("Patient ID");
        jLabel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtpatientid.setEditable(false);
        txtpatientid.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel103.setText("First Name");
        jLabel103.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtFN.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel104.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel104.setText("Last Name");
        jLabel104.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtLN.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel105.setText("Gender");
        jLabel105.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combogender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combogender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel106.setText("Marital Status");
        jLabel106.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combomaritalstatus.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combomaritalstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Married", "Single", "Divorced", "Widowed" }));
        combomaritalstatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel107.setText("Guardians Name");
        jLabel107.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtguardian.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel108.setText("Blood Group");
        jLabel108.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combobloodgroup.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combobloodgroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "AB", "O", "A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+", "Unknown" }));
        combobloodgroup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel109.setText("Patient Type");
        jLabel109.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combopatienttype.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combopatienttype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Inpatient", "Outpatient", "Pending" }));
        combopatienttype.setToolTipText("");
        combopatienttype.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel110.setText("Weight(Kgs)");
        jLabel110.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtweight.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel111.setText("Age");
        jLabel111.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtage.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel112.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel112.setText("Reg Date");
        jLabel112.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        comboservice.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        comboservice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "General", "Gynacological", "ICU", "Maternity", "Pediatric", "Prostratic", "Surgery" }));

        jLabel113.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel113.setText("Disease");
        jLabel113.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel114.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel114.setText("Body Temp(oC)");
        jLabel114.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Regdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regdate.setDateFormatString("yyyy-MM-dd");
        Regdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        txt_nurseprint_pat.setColumns(20);
        txt_nurseprint_pat.setRows(5);
        jScrollPane1.setViewportView(txt_nurseprint_pat);

        txtdisease.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        txtbldpressure.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        txtsymptoms.setColumns(10);
        txtsymptoms.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtsymptoms.setLineWrap(true);
        txtsymptoms.setRows(5);
        jScrollPane2.setViewportView(txtsymptoms);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel105, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel110, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel112, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtLN)
                                    .addComponent(txtFN)
                                    .addComponent(txtpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(txtguardian, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(combogender, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(combomaritalstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtage)
                                        .addComponent(txtweight, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(combopatienttype, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(combobloodgroup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(Regdate, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel100, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel114, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtbldpressure)
                                    .addComponent(txtbodytemp)
                                    .addComponent(combosampletype, 0, 306, Short.MAX_VALUE)
                                    .addComponent(txtsampleid, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(comboservice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtdisease)))))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel102)
                        .addGap(18, 18, 18)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboservice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel100))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel103)
                                    .addComponent(jLabel114))
                                .addGap(7, 7, 7)
                                .addComponent(jLabel104)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel105)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel106)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel107)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jLabel109)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel110)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel111)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(txtbodytemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combosampletype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(txtsampleid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtbldpressure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel113)
                                    .addComponent(txtdisease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel99)
                                    .addComponent(jScrollPane2)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtLN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(combogender, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(txtguardian, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(combomaritalstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(combobloodgroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(combopatienttype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txtweight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(txtage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Regdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel25Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {combobloodgroup, combogender, combomaritalstatus, combopatienttype, combosampletype, comboservice, jLabel100, jLabel101, jLabel103, jLabel104, jLabel105, jLabel106, jLabel107, jLabel108, jLabel109, jLabel110, jLabel111, jLabel112, jLabel113, jLabel114, jLabel16, jLabel17, jLabel23, jLabel99, txtFN, txtLN, txtage, txtbodytemp, txtguardian, txtpatientid, txtsampleid, txtsearch, txtweight});

        javax.swing.GroupLayout nurse_panelLayout = new javax.swing.GroupLayout(nurse_panel);
        nurse_panel.setLayout(nurse_panelLayout);
        nurse_panelLayout.setHorizontalGroup(
            nurse_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nurse_panelLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        nurse_panelLayout.setVerticalGroup(
            nurse_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nurse_panelLayout.createSequentialGroup()
                .addGroup(nurse_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nurse_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nurse_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Menu.addTab("Nurse Panel", jPanel4);

        ward_management.setBackground(new java.awt.Color(204, 204, 255));

        ward_menu.setBackground(new java.awt.Color(153, 153, 255));
        ward_menu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 8, true));
        ward_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ward_menu.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        ward_menu.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));

        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_wardpat.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_wardpat.setText("PATIENT");
        btn_wardpat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_wardpat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_wardpat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_wardpatActionPerformed(evt);
            }
        });

        btn_ward_update.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_ward_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_50px.png"))); // NOI18N
        btn_ward_update.setText("UPDATE");
        btn_ward_update.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_ward_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ward_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ward_updateActionPerformed(evt);
            }
        });

        btn_wardrefresh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_wardrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btn_wardrefresh.setText("REFRESH");
        btn_wardrefresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_wardrefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_wardrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_wardrefreshActionPerformed(evt);
            }
        });

        btn_wardsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_wardsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btn_wardsearch.setText("SEARCH");
        btn_wardsearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_wardsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_wardsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_wardsearchActionPerformed(evt);
            }
        });

        btn_wardassign.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_wardassign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btn_wardassign.setText("ASSIGN");
        btn_wardassign.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_wardassign.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_wardassign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_wardassignActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_wardrefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ward_update, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_wardpat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_wardsearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_wardassign, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_wardpat, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_wardassign, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_ward_update, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_wardrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_wardsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel47.setText("Search ");
        jLabel47.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsrch.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel37.setText("Patient ID");
        jLabel37.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtpatid.setEditable(false);
        txtpatid.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setText("First Name");
        jLabel38.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_FN.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel39.setText("Last Name");
        jLabel39.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_LN.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel40.setText("Gender");
        jLabel40.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbgender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbgender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setText("Marital Status");
        jLabel41.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbmarital.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbmarital.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Married", "Single", "Divorced", "Widowed" }));
        cmbmarital.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel42.setText("Guardians Name");
        jLabel42.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_guardian.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel43.setText("Blood Group");
        jLabel43.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbbloodgrp.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbbloodgrp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "AB", "O", "A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+", "Unknown" }));
        cmbbloodgrp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel44.setText("Patient Type");
        jLabel44.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbpattype.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbpattype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Inpatient", "Outpatient", "Pending" }));
        cmbpattype.setToolTipText("");
        cmbpattype.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel46.setText("Age");
        jLabel46.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_age.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel52.setText("Reg Date");
        jLabel52.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        regdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        regdate.setDateFormatString("yyyy-MM-dd");
        regdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel48.setText("Service");
        jLabel48.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbservice.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbservice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "General", "Gynacological", "ICU", "Maternity", "Pediatric", "Prostratic", "Surgery" }));
        cmbservice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbserviceActionPerformed(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel51.setText("Booking date");
        jLabel51.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        bookingdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bookingdate.setDateFormatString("yyyy-MM-dd");
        bookingdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel50.setText("Ward No");
        jLabel50.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbwardno.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbwardno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "WD1", "WD2", "WD3", "WD4", "WD5", " " }));
        cmbwardno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbwardnoMouseClicked(evt);
            }
        });
        cmbwardno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbwardnoActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel49.setText("Bed No");
        jLabel49.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbbedno.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbbedno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4", "5" }));
        cmbbedno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbbednoMouseClicked(evt);
            }
        });

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel53.setText("Ward Type");
        jLabel53.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbwardtype.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbwardtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "ICU", "Male", "Female", "Gynecology", "Maternity" }));
        cmbwardtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbwardtypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(1, 1, 1)
                                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(regdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(cmbpattype, 0, 273, Short.MAX_VALUE)
                                                    .addComponent(txt_age)))))
                                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txt_LN, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                                            .addComponent(txtpatid, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_FN, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel42)
                                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbbloodgrp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txt_guardian)
                                            .addComponent(cmbgender, 0, 272, Short.MAX_VALUE)
                                            .addComponent(cmbmarital, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(28, 28, 28)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmbservice, 0, 281, Short.MAX_VALUE)
                                        .addComponent(bookingdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbwardno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbwardtype, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(cmbbedno, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jLabel47)
                        .addGap(18, 18, 18)
                        .addComponent(txtsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbgender, txt_FN, txt_LN, txtpatid});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel37, jLabel38, jLabel39, jLabel40, jLabel41, jLabel42, jLabel43, jLabel44, jLabel46, jLabel52});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel48)
                                .addGap(1, 1, 1))
                            .addComponent(cmbservice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(bookingdate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel53)
                            .addComponent(cmbwardtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(cmbwardno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addGap(2, 2, 2))
                            .addComponent(cmbbedno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtpatid, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(txt_FN, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(txt_LN, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(cmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbmarital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel41)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel42)
                                    .addComponent(txt_guardian, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbbloodgrp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(cmbpattype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46)
                    .addComponent(txt_age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(regdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 87, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel37, regdate, txt_FN, txt_LN, txt_age, txtpatid});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbgender, cmbmarital});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bookingdate, cmbservice});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel47, txtsrch});

        ward_menu.addTab("Assign Ward", jPanel3);

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_warddis_getpat.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_warddis_getpat.setText("PATIENT");
        btn_warddis_getpat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_warddis_getpat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_warddis_getpat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_warddis_getpatActionPerformed(evt);
            }
        });

        btn_dischargerefresh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_dischargerefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btn_dischargerefresh.setText("REFRESH");
        btn_dischargerefresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_dischargerefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dischargerefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dischargerefreshActionPerformed(evt);
            }
        });

        btnsrch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnsrch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btnsrch.setText("SEARCH");
        btnsrch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsrch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsrch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsrchActionPerformed(evt);
            }
        });

        btn_discharge.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_discharge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btn_discharge.setText("DISCHARGE");
        btn_discharge.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_discharge.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_discharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dischargeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_dischargerefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_warddis_getpat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsrch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_discharge, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_warddis_getpat, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_discharge, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_dischargerefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(204, 204, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel70.setText("Patient ID");
        jLabel70.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel71.setText("Search ");
        jLabel71.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsrch2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        txtpatid2.setEditable(false);
        txtpatid2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel72.setText("First Name");
        jLabel72.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_FN2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel73.setText("Last Name");
        jLabel73.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_LN2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel74.setText("Gender");
        jLabel74.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbgender2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbgender2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel75.setText("Marital Status");
        jLabel75.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbmarital2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbmarital2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Married", "Single", "Divorced", "Widowed" }));
        cmbmarital2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel76.setText("Guardians Name");
        jLabel76.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_guardian2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel77.setText("Blood Group");
        jLabel77.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbbloodgrp2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbbloodgrp2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "AB", "O", "A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+", "Unknown" }));
        cmbbloodgrp2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel78.setText("Patient Type");
        jLabel78.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbpattype2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbpattype2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Inpatient", "Outpatient", "Pending" }));
        cmbpattype2.setToolTipText("");
        cmbpattype2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel79.setText("Age");
        jLabel79.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_age2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel80.setText("Reg Date");
        jLabel80.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        regdate2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        regdate2.setDateFormatString("yyyy-MM-dd");
        regdate2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel81.setText("Bed No");
        jLabel81.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbbedno2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbbedno2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cmbbedno2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbbedno2MouseClicked(evt);
            }
        });

        cmbwardno2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbwardno2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "WD1", "WD2", "WD3", "WD4", "WD5", " " }));
        cmbwardno2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbwardno2MouseClicked(evt);
            }
        });
        cmbwardno2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbwardno2ActionPerformed(evt);
            }
        });

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel82.setText("Ward No");
        jLabel82.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel83.setText("Ward Type");
        jLabel83.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbwardtype2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbwardtype2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "ICU", "Male", "Female", "Gynecology", "Maternity" }));
        cmbwardtype2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbwardtype2ActionPerformed(evt);
            }
        });

        dischargedate2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dischargedate2.setDateFormatString("yyyy-MM-dd");
        dischargedate2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel84.setText("Discharge Date");
        jLabel84.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel85.setText("Booking Date");
        jLabel85.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        bookingdate2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bookingdate2.setDateFormatString("yyyy-MM-dd");
        bookingdate2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel86.setText("Service");
        jLabel86.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbservice2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbservice2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "General", "Gynacological", "ICU", "Maternity", "Pediatric", "Prostratic", "Surgery" }));

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel87.setText("Status");
        jLabel87.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbstatus.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Active", "Discharged" }));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(jLabel71)
                        .addGap(18, 18, 18)
                        .addComponent(txtsrch2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_FN2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                                    .addComponent(txtpatid2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_LN2)))
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel21Layout.createSequentialGroup()
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel21Layout.createSequentialGroup()
                                            .addGap(1, 1, 1)
                                            .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(regdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbmarital2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                        .addComponent(jLabel76)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_guardian2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbbloodgrp2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_age2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbpattype2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbgender2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                        .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(17, 17, 17)
                                    .addComponent(cmbbedno2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                    .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmbwardno2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbwardtype2, 0, 281, Short.MAX_VALUE)))
                                .addGroup(jPanel21Layout.createSequentialGroup()
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                        .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmbservice2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bookingdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dischargedate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtsrch2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(txtpatid2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_FN2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72)))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel86)
                                .addGap(1, 1, 1))
                            .addComponent(cmbservice2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel85)
                            .addComponent(bookingdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73)
                            .addComponent(txt_LN2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel74)
                            .addComponent(cmbgender2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel75)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel76)
                                    .addComponent(txt_guardian2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cmbmarital2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbbloodgrp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbwardtype2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel83))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel82)
                            .addComponent(cmbwardno2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel81)
                            .addComponent(cmbbedno2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel84)
                            .addComponent(dischargedate2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel87)
                            .addComponent(cmbstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(cmbpattype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_age2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79))
                .addGap(6, 6, 6)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(regdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel21Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bookingdate2, cmbbedno2, cmbbloodgrp2, cmbgender2, cmbmarital2, cmbpattype2, cmbservice2, cmbwardno2, cmbwardtype2, dischargedate2, jLabel70, jLabel72, jLabel73, jLabel74, jLabel75, jLabel76, jLabel77, jLabel78, jLabel79, jLabel80, jLabel81, jLabel82, jLabel83, jLabel84, jLabel85, jLabel86, regdate2, txt_FN2, txt_LN2, txt_age2, txt_guardian2, txtpatid2});

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        ward_menu.addTab("Discharge", jPanel6);

        javax.swing.GroupLayout ward_managementLayout = new javax.swing.GroupLayout(ward_management);
        ward_management.setLayout(ward_managementLayout);
        ward_managementLayout.setHorizontalGroup(
            ward_managementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ward_managementLayout.createSequentialGroup()
                .addComponent(ward_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 1214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ward_managementLayout.setVerticalGroup(
            ward_managementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ward_managementLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ward_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        Menu.addTab("Ward Management", ward_management);

        Scrollpane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 255), 10, true));
        Scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        Scrollpane.setToolTipText("");
        Scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Scrollpane.setAutoscrolls(true);
        Scrollpane.setName("JscrollPat_reg"); // NOI18N

        Nurse_tablepatinfo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Nurse_tablepatinfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Nurse_tablepatinfo.setGridColor(new java.awt.Color(0, 0, 0));
        Nurse_tablepatinfo.setRowHeight(25);
        Nurse_tablepatinfo.getTableHeader().setReorderingAllowed(false);
        Scrollpane.setViewportView(Nurse_tablepatinfo);

        nurse_btnrefesh1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        nurse_btnrefesh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        nurse_btnrefesh1.setText("REFRESH");
        nurse_btnrefesh1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        nurse_btnrefesh1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nurse_btnrefesh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nurse_btnrefesh1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Pat_infoLayout = new javax.swing.GroupLayout(Pat_info);
        Pat_info.setLayout(Pat_infoLayout);
        Pat_infoLayout.setHorizontalGroup(
            Pat_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pat_infoLayout.createSequentialGroup()
                .addComponent(nurse_btnrefesh1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE))
        );
        Pat_infoLayout.setVerticalGroup(
            Pat_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pat_infoLayout.createSequentialGroup()
                .addGroup(Pat_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nurse_btnrefesh1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 73, Short.MAX_VALUE))
        );

        Menu.addTab("Patient information", Pat_info);

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel7.setMaximumSize(new java.awt.Dimension(1225, 586));
        jPanel7.setMinimumSize(new java.awt.Dimension(1225, 586));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Patient ID");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtpatientid1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtpatientid1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setText("First Name");
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtFN1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtFN1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setText("Last Name");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtLN1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtLN1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setText("Gender");
        jLabel20.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setText("Marital Status");
        jLabel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel24.setText("Blood Group");
        jLabel24.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combobloodgroup1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combobloodgroup1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "AB", "O", "A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+", "Unknown" }));
        combobloodgroup1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combobloodgroup1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel27.setText("Patient Type");
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combopatienttype1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combopatienttype1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Inpatient", "Outpatient", "Pending" }));
        combopatienttype1.setToolTipText("");
        combopatienttype1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combopatienttype1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel22.setText("Search ");
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtxtsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jtxtsearch.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel28.setText("Sample type");
        jLabel28.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel29.setText("Sample Id");
        jLabel29.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsampleid1.setEditable(false);
        txtsampleid1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtsampleid1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combogender1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combogender1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));
        combogender1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combosampletype1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combosampletype1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Blood", "Stool", "Urine", "Saliva", "Skin", "Semen", "Rectal", "Spinal Fluids", "Cervical", "Molecular(DNA)", "Tissue" }));
        combosampletype1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combosampletype1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosampletype1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Body Temp");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        typeoftest.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        typeoftest.setText("Test Date");
        typeoftest.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Technician Name");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txttechnicianname.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txttechnicianname.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Results Date");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Diagnosis");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtdiagnosis.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtdiagnosis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Results");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtresults.setColumns(20);
        txtresults.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtresults.setLineWrap(true);
        txtresults.setRows(5);
        txtresults.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jScrollPane3.setViewportView(txtresults);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jSeparator2.setOpaque(true);
        jSeparator2.setPreferredSize(new java.awt.Dimension(80, 10));

        testdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        testdate.setDateFormatString("yyyy-MM-dd");
        testdate.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        combomaritalstatus1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combomaritalstatus1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Married", "Single", "Divorced", "Widowed" }));
        combomaritalstatus1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combomaritalstatus1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txtguardian1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtguardian1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel30.setText("Guardian Name");
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel32.setText("Age");
        jLabel32.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel33.setText("Weight(Kgs)");
        jLabel33.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtweight1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtweight1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtage1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtage1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        resultsdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        resultsdate.setDateFormatString("yyyy-MM-dd");
        resultsdate.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        typeoftest1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        typeoftest1.setText("Type of Test");
        typeoftest1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combotypetest.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combotypetest.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Blood Count", "Urinalysis", "Parasitology", "Virology", "Toxicology", "Surgical", "DNA", "Metabolic", "Prothrombine", "Lipid", "Molecular", "Histopathology" }));
        combotypetest.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combotypetest.setPreferredSize(new java.awt.Dimension(328, 22));

        txtbodytemp1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtbodytemp1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel9.setMaximumSize(new java.awt.Dimension(188, 582));
        jPanel9.setMinimumSize(new java.awt.Dimension(188, 582));

        btnrefresh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btnrefresh.setText("REFRESH");
        btnrefresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnrefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefreshActionPerformed(evt);
            }
        });

        btnViewall.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnViewall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btnViewall.setText("SEARCH");
        btnViewall.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnViewall.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewallActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnrefresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewall, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnViewall, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(374, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(typeoftest, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFN1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtLN1)
                                    .addComponent(txtpatientid1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(combogender1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combomaritalstatus1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbodytemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtage1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtweight1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combopatienttype1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combobloodgroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtguardian1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(testdate, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(typeoftest1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(combosampletype1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtsampleid1)
                                    .addComponent(combotypetest, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addGap(110, 110, 110)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtdiagnosis)
                                            .addComponent(resultsdate, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txttechnicianname, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(jLabel22)
                        .addGap(36, 36, 36)
                        .addComponent(jtxtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel15, jLabel18, jLabel19, jLabel20, jLabel21, jLabel30});

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel28, jLabel29, jLabel6, jLabel7, jLabel8, typeoftest1});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(198, 198, 198)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(typeoftest, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtpatientid1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(198, 198, 198)
                                .addComponent(combobloodgroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(6, 6, 6)
                                .addComponent(combopatienttype1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtweight1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtbodytemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(6, 6, 6)
                                .addComponent(testdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtFN1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtLN1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(combogender1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(combomaritalstatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtguardian1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combotypetest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeoftest1))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(combosampletype1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(txtsampleid1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel29))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txttechnicianname, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(resultsdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtdiagnosis, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {combobloodgroup1, combogender1, combomaritalstatus1, combopatienttype1, combosampletype1, combotypetest, jLabel15, jLabel18, jLabel19, jLabel20, jLabel21, jLabel24, jLabel27, jLabel28, jLabel29, jLabel3, jLabel30, jLabel32, jLabel33, jLabel6, jLabel7, jLabel8, jtxtsearch, resultsdate, testdate, txtFN1, txtLN1, txtage1, txtbodytemp1, txtdiagnosis, txtguardian1, txtpatientid1, txtsampleid1, txttechnicianname, txtweight1, typeoftest, typeoftest1});

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 51, Short.MAX_VALUE))
        );

        Menu.addTab("Lab Results", jPanel5);

        man_account.setBackground(new java.awt.Color(204, 204, 204));
        man_account.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/user (2).png"))); // NOI18N
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton3MouseExited(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setText("Change Username");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(153, 153, 255));

        jbtnchangepass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/password.png"))); // NOI18N
        jbtnchangepass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnchangepass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbtnchangepassMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbtnchangepassMouseExited(evt);
            }
        });
        jbtnchangepass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnchangepassActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel25.setText("Change Password");
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbtnchangepass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jbtnchangepass, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(255, 204, 255));

        lblimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/web_camera.png"))); // NOI18N

        btnuploadimage.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnuploadimage.setText("Upload Profile Photo");
        btnuploadimage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnuploadimageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
            .addComponent(lblimage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnuploadimage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 0, 0));
        jLabel31.setText("KEEP YOUR USERID CONFIDENTIAL");

        javax.swing.GroupLayout man_accountLayout = new javax.swing.GroupLayout(man_account);
        man_account.setLayout(man_accountLayout);
        man_accountLayout.setHorizontalGroup(
            man_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(man_accountLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(man_accountLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        man_accountLayout.setVerticalGroup(
            man_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(man_accountLayout.createSequentialGroup()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(man_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(man_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(232, Short.MAX_VALUE))
        );

        Menu.addTab("Manage Account", man_account);

        jPanel2.setBackground(new java.awt.Color(0, 51, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Nurse");

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_shutdown_100px.png"))); // NOI18N
        jButton2.setText("Logout");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Welcome");

        lbluser.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbluser, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbluser, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(41, 41, 41))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, lbluser});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Menu)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Login_Nurse HMS = new Login_Nurse();
        HMS.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnuploadimageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnuploadimageActionPerformed
         JFileChooser fc = new JFileChooser();
        int fileState = fc.showOpenDialog(null);
        if (fileState == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            Imagename = f.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(Imagename).getImage().getScaledInstance(lblimage.getWidth(), lblimage.getHeight(), Image.SCALE_SMOOTH));
            lblimage.setIcon(imageIcon);
            try {
            File image = new File(Imagename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            uimage = bos.toByteArray();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        } else if (fileState == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "No image selected");
        }
    }//GEN-LAST:event_btnuploadimageActionPerformed

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        String user = lbluser.getText();
        NurseEditAccount HMS = new NurseEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jbtnchangepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnchangepassActionPerformed
        String user = lbluser.getText();
        NurseEditAccount HMS = new NurseEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jbtnchangepassActionPerformed

    private void jbtnchangepassMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnchangepassMouseExited
        jbtnchangepass.setBackground(Color.white);
    }//GEN-LAST:event_jbtnchangepassMouseExited

    private void jbtnchangepassMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnchangepassMouseEntered
        jbtnchangepass.setBackground(Color.gray);
    }//GEN-LAST:event_jbtnchangepassMouseEntered

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        String user = lbluser.getText();
        NurseEditAccount HMS = new NurseEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String user = lbluser.getText();
        NurseEditAccount HMS = new NurseEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        jButton3.setBackground(Color.white);
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        jButton3.setBackground(Color.gray);
    }//GEN-LAST:event_jButton3MouseEntered

    private void cmbserviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbserviceActionPerformed
        if (cmbgender.getSelectedItem().equals("Male") && cmbservice.getSelectedItem().equals("Gynacological") || cmbgender.getSelectedItem().equals("Male") && cmbservice.getSelectedItem().equals("Maternity")) {
            JOptionPane.showMessageDialog(this, "This Service Is For Female Patients Only",
                    "Error", JOptionPane.ERROR_MESSAGE);
            cmbservice.setSelectedItem("Select");
        } else if (cmbgender.getSelectedItem().equals("Female") && cmbservice.getSelectedItem().equals("Prostratic")) {
            JOptionPane.showMessageDialog(this, "This Service Is For Male Patients Only",
                    "Error", JOptionPane.ERROR_MESSAGE);
            cmbservice.setSelectedItem("Select");
        }
    }//GEN-LAST:event_cmbserviceActionPerformed

    private void cmbwardnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbwardnoActionPerformed
        if (cmbwardno.getSelectedItem().equals("WD1")) {
            cmbbedno.removeAllItems();
            cmbbedno.addItem("101");
            cmbbedno.addItem("102");
            cmbbedno.addItem("103");
            cmbbedno.addItem("104");
            cmbbedno.addItem("105");
        } else if (cmbwardno.getSelectedItem().equals("WD2")) {
            cmbbedno.removeAllItems();
            cmbbedno.addItem("201");
            cmbbedno.addItem("202");
            cmbbedno.addItem("203");
            cmbbedno.addItem("204");
            cmbbedno.addItem("205");
        } else if (cmbwardno.getSelectedItem().equals("WD3")) {
            cmbbedno.removeAllItems();
            cmbbedno.addItem("301");
            cmbbedno.addItem("302");
            cmbbedno.addItem("303");
            cmbbedno.addItem("304");
            cmbbedno.addItem("305");
        } else if (cmbwardno.getSelectedItem().equals("WD4")) {
            cmbbedno.removeAllItems();
            cmbbedno.addItem("401");
            cmbbedno.addItem("402");
            cmbbedno.addItem("403");
            cmbbedno.addItem("404");
            cmbbedno.addItem("405");
        } else if (cmbwardno.getSelectedItem().equals("WD5")) {
            cmbbedno.removeAllItems();
            cmbbedno.addItem("501");
            cmbbedno.addItem("502");
            cmbbedno.addItem("503");
            cmbbedno.addItem("504");
            cmbbedno.addItem("505");
        }
    }//GEN-LAST:event_cmbwardnoActionPerformed

    private void btn_wardpatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_wardpatActionPerformed
        txtpatid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmbgender.setSelectedItem("Select");
        cmbmarital.setSelectedItem("Select");
        txt_guardian.setText("");
        cmbbloodgrp.setSelectedItem("Select");
        cmbpattype.setSelectedItem("Select");
        txt_age.setText("");
        regdate.setDate(null);
        cmbservice.setSelectedItem("Select");
        bookingdate.setDate(null);
        cmbwardtype.setSelectedItem("Select");
        cmbwardno.setSelectedItem("Select");
        cmbbedno.removeAllItems();
        cmbbedno.addItem("Select");
        if (txtsrch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Get Patient ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_nurse where Patientid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsrch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patientid");
                    txtpatid.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txt_FN.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txt_LN.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmbgender.setSelectedItem(add4);
                    String add5 = rs.getString("Marital");
                    cmbmarital.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txt_guardian.setText(add6);
                    String add7 = rs.getString("Bld_grp");
                    cmbbloodgrp.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    cmbpattype.setSelectedItem(add8);
                    String add10 = rs.getString("Age");
                    txt_age.setText(add10);
                    String add11 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add11);
                    regdate.setDate(dat);
                    String add12 = rs.getString("Service");
                    cmbservice.setSelectedItem(add12);
                    System.out.println("Record Found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btn_wardpatActionPerformed

    private void btn_wardrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_wardrefreshActionPerformed
        txtsrch.setText("");
        txtpatid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmbgender.setSelectedItem("Select");
        cmbmarital.setSelectedItem("Select");
        txt_guardian.setText("");
        cmbbloodgrp.setSelectedItem("Select");
        cmbpattype.setSelectedItem("Select");
        txt_age.setText("");
        regdate.setDate(null);
        cmbservice.setSelectedItem("Select");
        bookingdate.setDate(null);
        cmbwardtype.setSelectedItem("Select");
        cmbwardno.setSelectedItem("Select");
        cmbbedno.removeAllItems();
        cmbbedno.addItem("Select");
    }//GEN-LAST:event_btn_wardrefreshActionPerformed

    private void btn_warddis_getpatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_warddis_getpatActionPerformed
        txtpatid2.setText("");
        txt_FN2.setText("");
        txt_LN2.setText("");
        cmbgender2.setSelectedItem("Select");
        cmbmarital2.setSelectedItem("Select");
        txt_guardian2.setText("");
        cmbbloodgrp2.setSelectedItem("Select");
        cmbpattype2.setSelectedItem("Select");
        txt_age2.setText("");
        regdate2.setDate(null);
        cmbservice2.setSelectedItem("Select");
        bookingdate2.setDate(null);
        dischargedate2.setDate(null);
        cmbwardtype2.setSelectedItem("Select");
        cmbwardno2.setSelectedItem("Select");
        cmbbedno2.removeAllItems();
        cmbbedno2.addItem("Select");
        dischargedate2.setDate(null);
        cmbstatus.setSelectedItem("Select");
        if (txtsrch2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_assignward where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsrch2.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patid");
                    txtpatid2.setText(add1);
                    String add2 = rs.getString("FN");
                    txt_FN2.setText(add2);
                    String add3 = rs.getString("LN");
                    txt_LN2.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmbgender2.setSelectedItem(add4);
                    String add5 = rs.getString("Marital");
                    cmbmarital2.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txt_guardian2.setText(add6);
                    String add7 = rs.getString("Bld_grp");
                    cmbbloodgrp2.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    cmbpattype2.setSelectedItem(add8);
                    String add9 = rs.getString("Age");
                    txt_age2.setText(add9);
                    String add10 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add10);
                    regdate2.setDate(dat);
                    String add11 = rs.getString("Service");
                    cmbservice2.setSelectedItem(add11);
                    String add12 = rs.getObject("Bookdate").toString();
                    java.util.Date Book = new SimpleDateFormat("yyyy-MM-dd").parse(add12);
                    bookingdate2.setDate(Book);
                    String add13 = rs.getString("Wardtype");
                    cmbwardtype2.setSelectedItem(add13);
                    String add14 = rs.getString("Wardno");
                    cmbwardno2.setSelectedItem(add14);
                    String add15 = rs.getString("Bedno");
                    cmbbedno2.setSelectedItem(add15);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD CAN'T BE FOUND");

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btn_warddis_getpatActionPerformed

    private void btn_dischargerefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dischargerefreshActionPerformed
        txtsrch2.setText("");
        txtpatid2.setText("");
        txt_FN2.setText("");
        txt_LN2.setText("");
        cmbgender2.setSelectedItem("Select");
        cmbmarital2.setSelectedItem("Select");
        txt_guardian2.setText("");
        cmbbloodgrp2.setSelectedItem("Select");
        cmbpattype2.setSelectedItem("Select");
        txt_age2.setText("");
        regdate2.setDate(null);
        cmbservice2.setSelectedItem("Select");
        bookingdate2.setDate(null);
        dischargedate2.setDate(null);
        cmbwardtype2.setSelectedItem("Select");
        cmbwardno2.setSelectedItem("Select");
        cmbbedno2.removeAllItems();
        cmbbedno2.addItem("Select");
        dischargedate2.setDate(null);
        cmbstatus.setSelectedItem("Select");
    }//GEN-LAST:event_btn_dischargerefreshActionPerformed

    private void cmbwardno2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbwardno2ActionPerformed
        if (cmbwardno2.getSelectedItem().equals("WD1")) {
            cmbbedno2.removeAllItems();
            cmbbedno2.addItem("101");
            cmbbedno2.addItem("102");
            cmbbedno2.addItem("103");
            cmbbedno2.addItem("104");
            cmbbedno2.addItem("105");
        } else if (cmbwardno2.getSelectedItem().equals("WD2")) {
            cmbbedno2.removeAllItems();
            cmbbedno2.addItem("201");
            cmbbedno2.addItem("202");
            cmbbedno2.addItem("203");
            cmbbedno2.addItem("204");
            cmbbedno2.addItem("205");
        } else if (cmbwardno2.getSelectedItem().equals("WD3")) {
            cmbbedno2.removeAllItems();
            cmbbedno2.addItem("301");
            cmbbedno2.addItem("302");
            cmbbedno2.addItem("303");
            cmbbedno2.addItem("304");
            cmbbedno2.addItem("305");
        } else if (cmbwardno2.getSelectedItem().equals("WD4")) {
            cmbbedno2.removeAllItems();
            cmbbedno2.addItem("401");
            cmbbedno2.addItem("402");
            cmbbedno2.addItem("403");
            cmbbedno2.addItem("404");
            cmbbedno2.addItem("405");
        } else if (cmbwardno2.getSelectedItem().equals("WD5")) {
            cmbbedno2.removeAllItems();
            cmbbedno2.addItem("501");
            cmbbedno2.addItem("502");
            cmbbedno2.addItem("503");
            cmbbedno2.addItem("504");
            cmbbedno2.addItem("505");
        }
    }//GEN-LAST:event_cmbwardno2ActionPerformed

    private void btn_getpatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getpatientActionPerformed
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        combogender.setSelectedItem("Select");
        combomaritalstatus.setSelectedItem("Select");
        txtguardian.setText("");
        combobloodgroup.setSelectedItem("Select");
        combopatienttype.setSelectedItem("Select");
        txtweight.setText("");
        txtage.setText("");
        Regdate.setDate(null);
        comboservice.setSelectedItem("Select");
        txtbodytemp.setText("");
        combosampletype.setSelectedItem("Select");
        txtsampleid.setText("");
        txtbldpressure.setText("");
        txtdisease.setText("");
        txtsymptoms.setText("");
        if (txtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_doctor where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsearch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patid");
                    txtpatientid.setText(add1);
                    String add2 = rs.getString("FN");
                    txtFN.setText(add2);
                    String add3 = rs.getString("LN");
                    txtLN.setText(add3);
                    String add4 = rs.getString("Gender");
                    combogender.setSelectedItem(add4);
                    String add5 = rs.getString("Marital");
                    combomaritalstatus.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txtguardian.setText(add6);
                    String add7 = rs.getString("Bld_grp");
                    combobloodgroup.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    combopatienttype.setSelectedItem(add8);
                    String add9 = rs.getString("Weight");
                    txtweight.setText(add9);
                    String add10 = rs.getString("Age");
                    txtage.setText(add10);
                    String ad = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                    Regdate.setDate(dat);
                    String add11 = rs.getString("Service");
                    comboservice.setSelectedItem(add11);
                    String add12 = rs.getString("Samp_typ");
                    combosampletype.setSelectedItem(add12);
                    String add13 = rs.getString("Bld_prs");
                    txtbldpressure.setText(add13);
                    String add14 = rs.getString("Disease");
                    txtdisease.setText(add14);
                    String add15 = rs.getString("Symptoms");
                    txtsymptoms.setText(add15);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                    System.out.println("No Record Found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

    }//GEN-LAST:event_btn_getpatientActionPerformed

    private void nurse_btnrefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nurse_btnrefeshActionPerformed
        txtsearch.setText("");
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        combogender.setSelectedItem("Select");
        combomaritalstatus.setSelectedItem("Select");
        txtguardian.setText("");
        combobloodgroup.setSelectedItem("Select");
        combopatienttype.setSelectedItem("Select");
        txtweight.setText("");
        txtage.setText("");
        Regdate.setDate(null);
        comboservice.setSelectedItem("Select");
        txtbodytemp.setText("");
        combosampletype.setSelectedItem("Select");
        txtsampleid.setText("");
        txtbldpressure.setText("");
        txtdisease.setText("");
        txtsymptoms.setText("");
    }//GEN-LAST:event_nurse_btnrefeshActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        String Bodytemp = txtbodytemp.getText();
        String Weight = txtweight.getText();
        String Age = txtage.getText();
        String BldPre = txtbldpressure.getText();
        if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txtguardian.getText().isEmpty() || txtweight.getText().isEmpty() || txtage.getText().isEmpty() || txtbodytemp.getText().isEmpty() || txtsampleid.getText().isEmpty() || txtsymptoms.getText().isEmpty() || txtbldpressure.getText().isEmpty() || txtsymptoms.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combomaritalstatus.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combobloodgroup.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The The Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combopatienttype.getSelectedItem().equals("Select") || combopatienttype.getSelectedItem().equals("Pending")) {
            JOptionPane.showMessageDialog(this, "Please Set The Type Of Patient To Outpatient or Inpatient Based On Diagnosis",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (comboservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Type Of Service", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combosampletype.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please The Type Of Sample", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Regdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Weight.length() > 4) {
            JOptionPane.showMessageDialog(this,
                    "Please Insert a Valid weight", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this,
                    "Please Insert a Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Bodytemp.length() > 2) {
            JOptionPane.showMessageDialog(this,
                    "Please Insert a Valid Body Temp", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (BldPre.length() > 3) {
            JOptionPane.showMessageDialog(this,
                    "Please Insert a Valid Blood Pressure", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = combogender.getSelectedItem().toString();
            String Maritalstatus = combomaritalstatus.getSelectedItem().toString();
            String Blood = combobloodgroup.getSelectedItem().toString();
            String Pattype = combopatienttype.getSelectedItem().toString();
            String Service = comboservice.getSelectedItem().toString();
            String Sample = combosampletype.getSelectedItem().toString();
            String Reg_date = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Regdate.getDate());
            System.out.println("Fields Validation Complete");
            try {
                String sql = "insert into pat_nurse (Patientid,Firstname,Lastname,Gender,Marital,Guardian"
                        + ",Bld_grp,Pat_type,Weight,Age,Regdate,Service,Body_temp,Samp_type,Samp_id,Bld_pressure"
                        + ",Disease,Symptoms) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to Database");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtpatientid.getText());
                pst.setString(2, txtFN.getText());
                pst.setString(3, txtLN.getText());
                pst.setString(4, Gender);
                pst.setString(5, Maritalstatus);
                pst.setString(6, txtguardian.getText());
                pst.setString(7, Blood);
                pst.setString(8, Pattype);
                pst.setString(9, txtweight.getText());
                pst.setString(10, txtage.getText());
                pst.setString(11, Reg_date);
                pst.setString(12, Service);
                pst.setString(13, txtbodytemp.getText());
                pst.setString(14, Sample);
                pst.setString(15, txtsampleid.getText());
                pst.setString(16, txtbldpressure.getText());
                pst.setString(17, txtdisease.getText());
                pst.setString(18, txtsymptoms.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                txtsearch.setText("");
                txtpatientid.setText("");
                txtFN.setText("");
                txtLN.setText("");
                combogender.setSelectedItem("Select");
                combomaritalstatus.setSelectedItem("Select");
                txtguardian.setText("");
                combobloodgroup.setSelectedItem("Select");
                combopatienttype.setSelectedItem("Select");
                txtweight.setText("");
                txtage.setText("");
                Regdate.setDate(null);
                comboservice.setSelectedItem("Select");
                txtbodytemp.setText("");
                combosampletype.setSelectedItem("Select");
                txtsampleid.setText("");
                txtbldpressure.setText("");
                txtdisease.setText("");
                txtsymptoms.setText("");
                System.out.println("Record saved successfully");
                System.out.println("Issuing receipt to patient....,");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connecting to DB Problems");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void combosampletypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosampletypeActionPerformed
        if (combosampletype.getSelectedItem().equals("Blood")) {
            txtsampleid.setText("BL21");
        } else if (combosampletype.getSelectedItem().equals("Stool")) {
            txtsampleid.setText("ST89");
        } else if (combosampletype.getSelectedItem().equals("Urine")) {
            txtsampleid.setText("UR56");
        } else if (combosampletype.getSelectedItem().equals("Saliva")) {
            txtsampleid.setText("SA09");
        } else if (combosampletype.getSelectedItem().equals("Skin")) {
            txtsampleid.setText("SK76");
        } else if (combosampletype.getSelectedItem().equals("Semen")) {
            txtsampleid.setText("SE69");
        } else if (combosampletype.getSelectedItem().equals("Rectal")) {
            txtsampleid.setText("RE11");
        } else if (combosampletype.getSelectedItem().equals("Spinal Fluids")) {
            txtsampleid.setText("SP49");
        } else if (combosampletype.getSelectedItem().equals("Molecular(DNA)")) {
            txtsampleid.setText("DN71");
        } else if (combosampletype.getSelectedItem().equals("Tissue")) {
            txtsampleid.setText("TE21");
        } else if (combosampletype.getSelectedItem().equals("Cervical")) {
            txtsampleid.setText("CE21");
        }
    }//GEN-LAST:event_combosampletypeActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you Sure to Update?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Update Dialog................");
        if (p == 0) {
            String Bodytemp = txtbodytemp.getText();
            String Weight = txtweight.getText();
            String Age = txtage.getText();
            if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txtguardian.getText().isEmpty() || txtweight.getText().isEmpty() || txtage.getText().isEmpty() || txtbodytemp.getText().isEmpty() || txtsampleid.getText().isEmpty() || txtsymptoms.getText().isEmpty() || txtsymptoms.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combogender.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Gender", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combomaritalstatus.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combobloodgroup.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The The Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combopatienttype.getSelectedItem().equals("Select") || combopatienttype.getSelectedItem().equals("Pending")) {
                JOptionPane.showMessageDialog(this, "Please Set The Type Of Patient To Outpatient or Inpatient Based On Diagnosis",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (comboservice.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Type Of Service", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combosampletype.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please The Type Of Sample", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Regdate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Select Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Weight.length() > 4) {
                JOptionPane.showMessageDialog(this,
                        "Please Insert a Valid weight", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Age.length() > 3) {
                JOptionPane.showMessageDialog(this,
                        "Please Insert a Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Bodytemp.length() > 2) {
                JOptionPane.showMessageDialog(this,
                        "Please Insert a Valid Body Temp", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String Gender = combogender.getSelectedItem().toString();
                String Maritalstatus = combomaritalstatus.getSelectedItem().toString();
                String Blood = combobloodgroup.getSelectedItem().toString();
                String Pattype = combopatienttype.getSelectedItem().toString();
                String Service = comboservice.getSelectedItem().toString();
                String Sample = combosampletype.getSelectedItem().toString();
                String val = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(Regdate.getDate());
                System.out.println("Fields Validation Complete");
                try {
                    String sql = "update pat_nurse set Firstname='" + txtFN.getText() + "',Lastname='" + txtLN.getText() + "',Gender='" + Gender + "'"
                            + ",Marital='" + Maritalstatus + "',Guardian='" + txtguardian.getText() + "',Bld_grp='" + Blood + "',Pat_type='" + Pattype + "'"
                            + ",Weight='" + txtweight.getText() + "',Age='" + txtage.getText() + "',Regdate='" + val + "',Service='" + Service + "'"
                            + ",Body_temp='" + txtbodytemp.getText() + "',Samp_type='" + Sample + "',Samp_id='" + txtsampleid.getText() + "',Bld_pressure='" + txtbldpressure.getText() + "'"
                            + ",Disease='" + txtdisease.getText() + "',Symptoms='" + txtsymptoms.getText() + "' where Patientid='" + txtpatientid.getText() + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    System.out.println("Connecting to Db\n" + dtf.format(now));
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                    txtsearch.setText("");
                    txtpatientid.setText("");
                    txtFN.setText("");
                    txtLN.setText("");
                    combogender.setSelectedItem("Select");
                    combomaritalstatus.setSelectedItem("Select");
                    txtguardian.setText("");
                    combobloodgroup.setSelectedItem("Select");
                    combopatienttype.setSelectedItem("Select");
                    txtweight.setText("");
                    txtage.setText("");
                    Regdate.setDate(null);
                    comboservice.setSelectedItem("Select");
                    txtbodytemp.setText("");
                    combosampletype.setSelectedItem("Select");
                    txtsampleid.setText("");
                    txtbldpressure.setText("");
                    txtdisease.setText("");
                    txtsymptoms.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                } finally {
                    try {
                        con.close();
                        System.out.println("Connection Closed\n" + dtf.format(now));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        combogender.setSelectedItem("Select");
        combomaritalstatus.setSelectedItem("Select");
        txtguardian.setText("");
        combobloodgroup.setSelectedItem("Select");
        combopatienttype.setSelectedItem("Select");
        txtweight.setText("");
        txtage.setText("");
        Regdate.setDate(null);
        comboservice.setSelectedItem("Select");
        txtbodytemp.setText("");
        combosampletype.setSelectedItem("Select");
        txtsampleid.setText("");
        txtbldpressure.setText("");
        txtdisease.setText("");
        txtsymptoms.setText("");
        if (txtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_nurse where Patientid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsearch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patientid");
                    txtpatientid.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txtFN.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txtLN.setText(add3);
                    String add4 = rs.getString("Gender");
                    combogender.setSelectedItem(add4);
                    String add5 = rs.getString("Marital");
                    combomaritalstatus.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txtguardian.setText(add6);
                    String add7 = rs.getString("Bld_grp");
                    combobloodgroup.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    combopatienttype.setSelectedItem(add8);
                    String add9 = rs.getString("Weight");
                    txtweight.setText(add9);
                    String add10 = rs.getString("Age");
                    txtage.setText(add10);
                    String add11 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add11);
                    Regdate.setDate(dat);
                    String add12 = rs.getString("Service");
                    comboservice.setSelectedItem(add12);
                    String add13 = rs.getString("Body_temp");
                    txtbodytemp.setText(add13);
                    String add14 = rs.getString("Samp_type");
                    combosampletype.setSelectedItem(add14);
                    String add15 = rs.getString("Samp_id");
                    txtsampleid.setText(add15);
                    String add16 = rs.getString("Bld_pressure");
                    txtbldpressure.setText(add16);
                    String add17 = rs.getString("Disease");
                    txtdisease.setText(add17);
                    String add18 = rs.getString("Symptoms");
                    txtsymptoms.setText(add18);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                    System.out.println("No Record Found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_wardassignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_wardassignActionPerformed
        String Age = txt_age.getText();
        if (txtpatid.getText().isEmpty()
                || txt_FN.getText().isEmpty()
                || txt_LN.getText().isEmpty()
                || txt_guardian.getText().isEmpty()
                || txt_age.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Service", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbgender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbmarital.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbbloodgrp.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbpattype.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Patient Type", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbwardtype.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Type Of Ward", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (regdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (bookingdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The Booking Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmbgender.getSelectedItem().toString();
            String Marital = cmbmarital.getSelectedItem().toString();
            String Blood = cmbbloodgrp.getSelectedItem().toString();
            String Pattype = cmbpattype.getSelectedItem().toString();
            String Service = cmbservice.getSelectedItem().toString();
            String Ward = cmbwardtype.getSelectedItem().toString();
            String WardNo = cmbwardno.getSelectedItem().toString();
            String BedNo = cmbbedno.getSelectedItem().toString();
            String Reg_date = ((JTextField) regdate.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(regdate.getDate());
            String Book_date = ((JTextField) bookingdate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(bookingdate.getDate());
            System.out.println("Fields Validation Complete");
            try {
                String sql = "insert into pat_assignward (Patid,FN,LN,Gender,Marital,Guardian"
                        + ",Bld_grp,Pat_type,Age,Regdate,Service,Bookdate,Wardtype,Wardno,Bedno)"
                        + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to Database");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtpatid.getText());
                pst.setString(2, txt_FN.getText());
                pst.setString(3, txt_LN.getText());
                pst.setString(4, Gender);
                pst.setString(5, Marital);
                pst.setString(6, txt_guardian.getText());
                pst.setString(7, Blood);
                pst.setString(8, Pattype);
                pst.setString(9, txt_age.getText());
                pst.setString(10, Reg_date);
                pst.setString(11, Service);
                pst.setString(12, Book_date);
                pst.setString(13, Ward);
                pst.setString(14, WardNo);
                pst.setString(15, BedNo);
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.Assignward_print();
                System.out.println("Record saved successfully");
                System.out.println("Issuing receipt to patient....,");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connecting to DB Problems");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

        }

    }//GEN-LAST:event_btn_wardassignActionPerformed

    private void btn_dischargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dischargeActionPerformed
        String Age = txt_age2.getText();
        if (txtpatid2.getText().isEmpty() || txt_FN2.getText().isEmpty() || txt_LN2.getText().isEmpty() || txt_guardian2.getText().isEmpty() || txt_age2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbservice2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Service", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbgender2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbmarital2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbbloodgrp2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbpattype2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Patient Type", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbwardtype2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Type Of Ward", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (regdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (dischargedate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The Discharge Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (bookingdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The Booking Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbstatus.getSelectedItem().equals("Select") || cmbstatus.getSelectedItem().equals("Active")) {
            JOptionPane.showMessageDialog(this, "The Status Of Patient Sholud Be Discharged", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmbgender2.getSelectedItem().toString();
            String Marital = cmbmarital2.getSelectedItem().toString();
            String Blood = cmbbloodgrp2.getSelectedItem().toString();
            String Pattype = cmbpattype2.getSelectedItem().toString();
            String Service = cmbservice2.getSelectedItem().toString();
            String Ward = cmbwardtype2.getSelectedItem().toString();
            String WardNo = cmbwardno2.getSelectedItem().toString();
            String BedNo = cmbbedno2.getSelectedItem().toString();
            String Status = cmbstatus.getSelectedItem().toString();
            String Reg_date = ((JTextField) regdate2.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(regdate2.getDate());
            String Book_date = ((JTextField) bookingdate2.getDateEditor().getUiComponent()).getText();
            Date_Format.format(bookingdate2.getDate());
            String Disc = ((JTextField) dischargedate2.getDateEditor().getUiComponent()).getText();
            Date_Format.format(dischargedate2.getDate());
            System.out.println("Fields Validation Complete");
            try {
                String sql = "update pat_assignward set FN='" + txt_FN2.getText() + "',LN='" + txt_LN2.getText() + "'"
                        + ",Gender='" + Gender + "',Marital='" + Marital + "',Guardian='" + txt_guardian2.getText() + "'"
                        + ",Bld_grp='" + Blood + "',Pat_type='" + Pattype + "',Age='" + txt_age2.getText() + "'"
                        + ",Regdate='" + Reg_date + "',Service='" + Service + "',Bookdate='" + Book_date + "',"
                        + "Wardtype='" + Ward + "',Wardno='" + WardNo + "',Bedno='" + BedNo + "',Disch_date='" + Disc + "',Status='" + Status + "' where Patid='" + txtpatid2.getText() + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("DB connecting");
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "PATIENT DISCHARGED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.DischargedWard_print();
                System.out.println("Record saved successfully");
                System.out.println("Issuing receipt to patient....,");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connecting to DB Problems");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btn_dischargeActionPerformed

    private void cmbwardtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbwardtypeActionPerformed
        if (cmbgender.getSelectedItem().equals("Male") && cmbwardtype.getSelectedItem().equals("Female") || cmbgender.getSelectedItem().equals("Male") && cmbwardtype.getSelectedItem().equals("Maternity") || cmbgender.getSelectedItem().equals("Male") && cmbwardtype.getSelectedItem().equals("Gynecology")) {
            JOptionPane.showMessageDialog(null, "This Type Of Ward Is For Female Patients Only");
            cmbwardtype.setSelectedItem("Select");
        } else if (cmbgender.getSelectedItem().equals("Female") && cmbwardtype.getSelectedItem().equals("Male")) {
            JOptionPane.showMessageDialog(null, "This Type Of Ward Is For Male Patients Only");
        }
    }//GEN-LAST:event_cmbwardtypeActionPerformed

    private void cmbwardtype2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbwardtype2ActionPerformed
        if (cmbgender2.getSelectedItem().equals("Male") && cmbwardtype2.getSelectedItem().equals("Female") || cmbgender2.getSelectedItem().equals("Male") && cmbwardtype2.getSelectedItem().equals("Maternity") || cmbgender2.getSelectedItem().equals("Male") && cmbwardtype2.getSelectedItem().equals("Gynecology")) {
            JOptionPane.showMessageDialog(null, "This Type Of Ward Is For Female Patients Only");
            cmbwardtype2.setSelectedItem("Select");
        } else if (cmbgender2.getSelectedItem().equals("Female") && cmbwardtype2.getSelectedItem().equals("Male")) {
            JOptionPane.showMessageDialog(null, "This Type Of Ward Is For Male Patients Only");
        }
    }//GEN-LAST:event_cmbwardtype2ActionPerformed

    private void cmbbednoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbbednoMouseClicked
        if (cmbwardno.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Ward Type And Ward No First",
                    "Error", JOptionPane.ERROR_MESSAGE);
            cmbbedno.removeAllItems();
            cmbbedno.addItem("Select");
        }
    }//GEN-LAST:event_cmbbednoMouseClicked

    private void cmbwardnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbwardnoMouseClicked
        if (cmbwardtype.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Ward Type First",
                    "Error", JOptionPane.ERROR_MESSAGE);
            cmbwardtype.setSelectedItem("Select");
        }
    }//GEN-LAST:event_cmbwardnoMouseClicked

    private void btn_wardsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_wardsearchActionPerformed
        txtpatid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmbgender.setSelectedItem("Select");
        cmbmarital.setSelectedItem("Select");
        txt_guardian.setText("");
        cmbbloodgrp.setSelectedItem("Select");
        cmbpattype.setSelectedItem("Select");
        txt_age.setText("");
        regdate.setDate(null);
        cmbservice.setSelectedItem("Select");
        bookingdate.setDate(null);
        cmbwardtype.setSelectedItem("Select");
        cmbwardno.setSelectedItem("Select");
        cmbbedno.removeAllItems();
        cmbbedno.addItem("Select");
        if (txtsrch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_assignward where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsrch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patid");
                    txtpatid.setText(add1);
                    String add2 = rs.getString("FN");
                    txt_FN.setText(add2);
                    String add3 = rs.getString("LN");
                    txt_LN.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmbgender.setSelectedItem(add4);
                    String add5 = rs.getString("Marital");
                    cmbmarital.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txt_guardian.setText(add6);
                    String add7 = rs.getString("Bld_grp");
                    cmbbloodgrp.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    cmbpattype.setSelectedItem(add8);
                    String add9 = rs.getString("Age");
                    txt_age.setText(add9);
                    String add10 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add10);
                    regdate.setDate(dat);
                    String add11 = rs.getString("Service");
                    cmbservice.setSelectedItem(add11);
                    String add12 = rs.getObject("Bookdate").toString();
                    java.util.Date Book = new SimpleDateFormat("yyyy-MM-dd").parse(add12);
                    bookingdate.setDate(Book);
                    String add13 = rs.getString("Wardtype");
                    cmbwardtype.setSelectedItem(add13);
                    String add14 = rs.getString("Wardno");
                    cmbwardno.setSelectedItem(add14);
                    String add15 = rs.getString("Bedno");
                    cmbbedno.setSelectedItem(add15);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD CAN'T BE FOUND");

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btn_wardsearchActionPerformed

    private void btn_ward_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ward_updateActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you Sure to Update?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Update Dialog................");
        if (p == 0) {
            String Age = txtage.getText();
            if (txtpatid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_age.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbservice.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Service", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbgender.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Gender", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbmarital.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbbloodgrp.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbpattype.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Patient Type", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbwardtype.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Type Of Ward", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (regdate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Select The Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (bookingdate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Select The Booking Date", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Age.length() > 3) {
                JOptionPane.showMessageDialog(this, "Please Enter A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String Gender = cmbgender.getSelectedItem().toString();
                String Marital = cmbmarital.getSelectedItem().toString();
                String Blood = cmbbloodgrp.getSelectedItem().toString();
                String Pattype = cmbpattype.getSelectedItem().toString();
                String Service = cmbservice.getSelectedItem().toString();
                String Ward = cmbwardtype.getSelectedItem().toString();
                String WardNo = cmbwardno.getSelectedItem().toString();
                String BedNo = cmbbedno.getSelectedItem().toString();
                String Reg_date = ((JTextField) regdate.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(regdate.getDate());
                String Book = ((JTextField) bookingdate.getDateEditor().getUiComponent()).getText();
                Date_Format.format(bookingdate.getDate());
                System.out.println("Fields Validation Complete");
                try {
                    String sql = "update pat_assignward set FN='" + txt_FN.getText() + "',LN='" + txt_LN.getText() + "'"
                            + ",Gender='" + Gender + "',Marital='" + Marital + "',Guardian='" + txt_guardian.getText() + "'"
                            + ",Bld_grp='" + Blood + "',Pat_type='" + Pattype + "',Age='" + txt_age2.getText() + "'"
                            + ",Regdate='" + Reg_date + "',Service='" + Service + "',Bookdate='" + Book + "',"
                            + "Wardtype='" + Ward + "',Wardno='" + WardNo + "',Bedno='" + BedNo + "' where Patid='" + txtpatid.getText() + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    System.out.println("DB connecting");
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                    JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                    this.Assignward_print();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                } finally {
                    try {
                        con.close();
                        System.out.println("Connection Closed\n" + dtf.format(now));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_ward_updateActionPerformed

    private void combosampletype1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosampletype1ActionPerformed
        if (combosampletype1.getSelectedItem().equals("Blood")) {
            txtsampleid1.setText("BL21");
        } else if (combosampletype1.getSelectedItem().equals("Stool")) {
            txtsampleid1.setText("ST89");
        } else if (combosampletype1.getSelectedItem().equals("Urine")) {
            txtsampleid1.setText("UR56");
        } else if (combosampletype1.getSelectedItem().equals("Saliva")) {
            txtsampleid1.setText("SA09");
        } else if (combosampletype1.getSelectedItem().equals("Skin")) {
            txtsampleid1.setText("SK76");
        } else if (combosampletype1.getSelectedItem().equals("Semen")) {
            txtsampleid1.setText("SE69");
        } else if (combosampletype1.getSelectedItem().equals("Rectal")) {
            txtsampleid1.setText("RE11");
        }
    }//GEN-LAST:event_combosampletype1ActionPerformed

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        txtpatientid1.setText("");
        txtFN1.setText("");
        txtLN1.setText("");
        combogender1.setSelectedItem("Select");
        combomaritalstatus1.setSelectedItem("Select");
        txtguardian1.setText("");
        combobloodgroup1.setSelectedItem("Select");
        combopatienttype1.setSelectedItem("Select");
        txtweight1.setText("");
        txtage1.setText("");
        txtbodytemp1.setText("");
        testdate.setDate(null);
        combotypetest.setSelectedItem("Select");
        combosampletype1.setSelectedItem("Select");
        txtsampleid1.setText("");
        txttechnicianname.setText("");
        resultsdate.setDate(null);
        txtdiagnosis.setText("");
        txtresults.setText("");
        jtxtsearch.setText("");
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void btnViewallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewallActionPerformed
        txtpatientid1.setText("");
        txtFN1.setText("");
        txtLN1.setText("");
        combogender1.setSelectedItem("Select");
        combomaritalstatus1.setSelectedItem("Select");
        txtguardian1.setText("");
        combobloodgroup1.setSelectedItem("Select");
        combopatienttype1.setSelectedItem("Select");
        txtweight1.setText("");
        txtage1.setText("");
        txtbodytemp1.setText("");
        testdate.setDate(null);
        combotypetest.setSelectedItem("Select");
        combosampletype1.setSelectedItem("Select");
        txtsampleid1.setText("");
        txttechnicianname.setText("");
        resultsdate.setDate(null);
        txtdiagnosis.setText("");
        txtresults.setText("");
        if (jtxtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from lab_results where Patientid=?";
            System.out.println("Connecting To DB");
            pst = con.prepareStatement(sql);
            pst.setString(1, jtxtsearch.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                String add1 = rs.getString("Patientid");
                txtpatientid1.setText(add1);
                String add2 = rs.getString("Firstname");
                txtFN1.setText(add2);
                String add3 = rs.getString("Lastname");
                txtLN1.setText(add3);
                String add4 = rs.getString("Gender");
                combogender1.setSelectedItem(add4);
                String add5 = rs.getString("Maritalstatus");
                combomaritalstatus1.setSelectedItem(add5);
                String add6 = rs.getString("Guardianname");
                txtguardian1.setText(add6);
                String add7 = rs.getString("Bloodgroup");
                combobloodgroup1.setSelectedItem(add7);
                String add8 = rs.getString("Patienttype");
                combobloodgroup1.setSelectedItem(add8);
                String add9 = rs.getString("Weight");
                txtweight1.setText(add9);
                String add10 = rs.getString("Age");
                txtage1.setText(add10);
                String add11 = rs.getString("Bodytemp");
                txtbodytemp1.setText(add11);
                String ad = rs.getObject("Testdate").toString();
                java.util.Date test = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                testdate.setDate(test);
                String add13 = rs.getString("Type_test");
                combotypetest.setSelectedItem(add13);
                String add14 = rs.getString("Sampletype");
                combosampletype1.setSelectedItem(add14);
                String add15 = rs.getString("Sampleid");
                txtsampleid1.setText(add15);
                String add16 = rs.getString("Tech_name");
                txttechnicianname.setText(add16);
                String results_date = rs.getObject("Resultsdate").toString();
                java.util.Date result = new SimpleDateFormat("yyyy-MM-dd").parse(results_date);
                resultsdate.setDate(result);
                String add18 = rs.getString("Diagnosis");
                txtdiagnosis.setText(add18);
                String add19 = rs.getString("Results");
                txtresults.setText(add19);
                System.out.println("Record Found");
            } else {
                JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                System.out.println("No record found");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "The Record Can't Be Found");
            System.out.println("Searching Problems DB");
        } finally {
            try {
                con.close();
                System.out.println("Connection Closed");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btnViewallActionPerformed

    private void cmbbedno2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbbedno2MouseClicked
        if (cmbwardno2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Ward Type And Ward No First", "Error", JOptionPane.ERROR_MESSAGE);
            cmbbedno2.removeAllItems();
            cmbbedno2.addItem("Select");
        }
    }//GEN-LAST:event_cmbbedno2MouseClicked

    private void cmbwardno2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbwardno2MouseClicked
        if (cmbwardtype2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Ward Type First", "Error", JOptionPane.ERROR_MESSAGE);
            cmbwardtype2.setSelectedItem("Select");
        }
    }//GEN-LAST:event_cmbwardno2MouseClicked

    private void btnsrchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsrchActionPerformed
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        txtpatid2.setText("");
        txt_FN2.setText("");
        txt_LN2.setText("");
        cmbgender2.setSelectedItem("Select");
        cmbmarital2.setSelectedItem("Select");
        txt_guardian2.setText("");
        cmbbloodgrp2.setSelectedItem("Select");
        cmbpattype2.setSelectedItem("Select");
        txt_age2.setText("");
        regdate2.setDate(null);
        cmbservice2.setSelectedItem("Select");
        bookingdate2.setDate(null);
        dischargedate2.setDate(null);
        cmbwardtype2.setSelectedItem("Select");
        cmbwardno2.setSelectedItem("Select");
        cmbbedno2.removeAllItems();
        cmbbedno2.addItem("Select");
        dischargedate2.setDate(null);
        cmbstatus.setSelectedItem("Select");
        if (txtsrch2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_assignward where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsrch2.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patid");
                    txtpatid2.setText(add1);
                    String add2 = rs.getString("FN");
                    txt_FN2.setText(add2);
                    String add3 = rs.getString("LN");
                    txt_LN2.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmbgender2.setSelectedItem(add4);
                    String add5 = rs.getString("Marital");
                    cmbmarital2.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txt_guardian2.setText(add6);
                    String add7 = rs.getString("Bld_grp");
                    cmbbloodgrp2.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    cmbpattype2.setSelectedItem(add8);
                    String add9 = rs.getString("Age");
                    txt_age2.setText(add9);
                    String add10 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add10);
                    regdate2.setDate(dat);
                    String add11 = rs.getString("Service");
                    cmbservice2.setSelectedItem(add11);
                    String add12 = rs.getObject("Bookdate").toString();
                    java.util.Date Book = new SimpleDateFormat("yyyy-MM-dd").parse(add12);
                    bookingdate2.setDate(Book);
                    String add13 = rs.getString("Wardtype");
                    cmbwardtype2.setSelectedItem(add13);
                    String add14 = rs.getString("Wardno");
                    cmbwardno2.setSelectedItem(add14);
                    String add15 = rs.getString("Bedno");
                    cmbbedno2.setSelectedItem(add15);
                    String add16 = rs.getObject("Disch_date").toString();
                    java.util.Date Disc = new SimpleDateFormat("yyyy-MM-dd").parse(add16);
                    dischargedate2.setDate(Disc);
                    String add17 = rs.getString("Status");
                    cmbstatus.setSelectedItem(add17);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD CAN'T BE FOUND");

                }
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btnsrchActionPerformed

    private void nurse_btnrefesh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nurse_btnrefesh1ActionPerformed
        view_table();
    }//GEN-LAST:event_nurse_btnrefesh1ActionPerformed
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
            java.util.logging.Logger.getLogger(Nurse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Nurse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Nurse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Nurse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Nurse().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Menu;
    private javax.swing.JTable Nurse_tablepatinfo;
    private javax.swing.JPanel Pat_info;
    private com.toedter.calendar.JDateChooser Regdate;
    private javax.swing.JScrollPane Scrollpane;
    private com.toedter.calendar.JDateChooser bookingdate;
    private com.toedter.calendar.JDateChooser bookingdate2;
    private javax.swing.JButton btnViewall;
    private javax.swing.JButton btn_discharge;
    private javax.swing.JButton btn_dischargerefresh;
    private javax.swing.JButton btn_getpatient;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton btn_ward_update;
    private javax.swing.JButton btn_wardassign;
    private javax.swing.JButton btn_warddis_getpat;
    private javax.swing.JButton btn_wardpat;
    private javax.swing.JButton btn_wardrefresh;
    private javax.swing.JButton btn_wardsearch;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton btnsrch;
    private javax.swing.JButton btnuploadimage;
    private javax.swing.JComboBox<String> cmbbedno;
    private javax.swing.JComboBox<String> cmbbedno2;
    private javax.swing.JComboBox<String> cmbbloodgrp;
    private javax.swing.JComboBox<String> cmbbloodgrp2;
    private javax.swing.JComboBox<String> cmbgender;
    private javax.swing.JComboBox<String> cmbgender2;
    private javax.swing.JComboBox<String> cmbmarital;
    private javax.swing.JComboBox<String> cmbmarital2;
    private javax.swing.JComboBox<String> cmbpattype;
    private javax.swing.JComboBox<String> cmbpattype2;
    private javax.swing.JComboBox<String> cmbservice;
    private javax.swing.JComboBox<String> cmbservice2;
    private javax.swing.JComboBox<String> cmbstatus;
    private javax.swing.JComboBox<String> cmbwardno;
    private javax.swing.JComboBox<String> cmbwardno2;
    private javax.swing.JComboBox<String> cmbwardtype;
    private javax.swing.JComboBox<String> cmbwardtype2;
    private javax.swing.JComboBox<String> combobloodgroup;
    private javax.swing.JComboBox<String> combobloodgroup1;
    private javax.swing.JComboBox<String> combogender;
    private javax.swing.JComboBox<String> combogender1;
    private javax.swing.JComboBox<String> combomaritalstatus;
    private javax.swing.JComboBox<String> combomaritalstatus1;
    private javax.swing.JComboBox<String> combopatienttype;
    private javax.swing.JComboBox<String> combopatienttype1;
    private javax.swing.JComboBox<String> combosampletype;
    private javax.swing.JComboBox<String> combosampletype1;
    private javax.swing.JComboBox<String> comboservice;
    private javax.swing.JComboBox<String> combotypetest;
    private com.toedter.calendar.JDateChooser dischargedate2;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton jbtnchangepass;
    private javax.swing.JTextField jtxtsearch;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lbluser;
    private javax.swing.JPanel man_account;
    private javax.swing.JButton nurse_btnrefesh;
    private javax.swing.JButton nurse_btnrefesh1;
    private javax.swing.JPanel nurse_panel;
    private com.toedter.calendar.JDateChooser regdate;
    private com.toedter.calendar.JDateChooser regdate2;
    private com.toedter.calendar.JDateChooser resultsdate;
    private com.toedter.calendar.JDateChooser testdate;
    private javax.swing.JTextField txtFN;
    private javax.swing.JTextField txtFN1;
    private javax.swing.JTextField txtLN;
    private javax.swing.JTextField txtLN1;
    private javax.swing.JTextField txt_FN;
    private javax.swing.JTextField txt_FN2;
    private javax.swing.JTextField txt_LN;
    private javax.swing.JTextField txt_LN2;
    private javax.swing.JTextField txt_age;
    private javax.swing.JTextField txt_age2;
    private javax.swing.JTextField txt_guardian;
    private javax.swing.JTextField txt_guardian2;
    private javax.swing.JTextArea txt_nurseprint_pat;
    private javax.swing.JTextField txtage;
    private javax.swing.JTextField txtage1;
    private javax.swing.JTextField txtbldpressure;
    private javax.swing.JTextField txtbodytemp;
    private javax.swing.JTextField txtbodytemp1;
    private javax.swing.JTextField txtdiagnosis;
    private javax.swing.JTextField txtdisease;
    private javax.swing.JTextField txtguardian;
    private javax.swing.JTextField txtguardian1;
    private javax.swing.JTextField txtpatid;
    private javax.swing.JTextField txtpatid2;
    private javax.swing.JTextField txtpatientid;
    private javax.swing.JTextField txtpatientid1;
    private javax.swing.JTextArea txtresults;
    private javax.swing.JTextField txtsampleid;
    private javax.swing.JTextField txtsampleid1;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextField txtsrch;
    private javax.swing.JTextField txtsrch2;
    private javax.swing.JTextArea txtsymptoms;
    private javax.swing.JTextField txttechnicianname;
    private javax.swing.JTextField txtweight;
    private javax.swing.JTextField txtweight1;
    private javax.swing.JLabel typeoftest;
    private javax.swing.JLabel typeoftest1;
    private javax.swing.JPanel ward_management;
    private javax.swing.JTabbedPane ward_menu;
    // End of variables declaration//GEN-END:variables
}
