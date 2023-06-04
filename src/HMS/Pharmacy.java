package HMS;

import java.awt.Color;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class Pharmacy extends javax.swing.JFrame {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String Imagename = null;
    byte[] uimage = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public Pharmacy() {
        initComponents();
        System.out.println("Application Started\n" + dtf.format(now));
        view_table();

    }

    public void Drugset() {
        try {
           con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select Drugid,Unit_msr,Category,Expdate,Unit_price,Storage,Precautions,Side_effects from drug_inventory where Drugname=?";
            System.out.println("Connecting to DB");
            pst = con.prepareStatement(sql);
            pst.setString(1, cmbdrugname.getSelectedItem().toString());
            rs = pst.executeQuery();
            System.out.println("Search Found........");
            if (rs.next()) {
                String add1 = rs.getString("Drugid");
                combodrugid.setSelectedItem(add1);
                String add2 = rs.getString("Unit_msr");
                cmbunitofmeasure.setSelectedItem(add2);
                String add3 = rs.getString("Category");
                cmbcategory.setSelectedItem(add3);

                String ad = rs.getObject("Expdate").toString();
                java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                exp_date.setDate(dat);

                String add5 = rs.getString("Unit_price");
                txtprc.setText(add5);
                String add6 = rs.getString("Storage");
                txtstorage.setText(add6);
                String add7 = rs.getString("Precautions");
                txtprecautions.setText(add7);
                String add8 = rs.getString("Side_effects");
                txsideeffects.setText(add8);

                System.out.println("Record Found");
            } else {
                JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                System.out.println("No Record Found");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "The Record Can't Be Found");
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

    public void view_table() {

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from drug_inventory";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            drugs_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            drugs_table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Drginventory_srch() {
        if (txt_invsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Drugid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from drug_inventory where Drugid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_invsearch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Drugname");
                    combodrugname.setSelectedItem(add1);
                    String add2 = rs.getString("Drugid");
                    combo_drugid.setSelectedItem(add2);
                    String add3 = rs.getString("Unit_msr");
                    combounitofmeasure.setSelectedItem(add3);
                    String add4 = rs.getString("Category");
                    combocategory.setSelectedItem(add4);
                    String add5 = rs.getString("Quantity");
                    txtquantity.setText(add5);
                    String add6 = rs.getString("Pur_price");
                    txtprice.setText(add6);
                    String ad = rs.getObject("Regdate").toString();
                    java.util.Date reg = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                    Regdate.setDate(reg);
                    String exp_date = rs.getObject("Expdate").toString();
                    java.util.Date result = new SimpleDateFormat("yyyy-MM-dd").parse(exp_date);
                    EXP_date.setDate(result);
                    String add10 = rs.getString("Batchno");
                    txtbatchno.setText(add10);
                    String add11 = rs.getString("Details");
                    txtdetails.setText(add11);
                    String add12 = rs.getString("Unit_price");
                    txtunitprice.setText(add12);
                    String add13 = rs.getString("Dosage");
                    cmb_dosage.setSelectedItem(add13);
                    String add14 = rs.getString("Storage");
                    txt_storage.setText(add14);
                    String add7 = rs.getString("Precautions");
                    txt_precautions.setText(add7);
                    String add8 = rs.getString("Side_effects");
                    txtsideeffects.setText(add8);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                    System.out.println("No Record Found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "The Record Can't Be Found");
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
    }

    public void issuedrug_printing() {
        if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txsideeffects.getText().isEmpty() || txtprecautions.getText().isEmpty() || txtstorage.getText().isEmpty() || txtprc.getText().isEmpty() || txtqnty.getText().isEmpty() || combogender.getSelectedItem().equals("Select") || cmbdrugname.getSelectedItem().equals("Select") || combodrugid.getSelectedItem().equals("Select") || cmbunitofmeasure.getSelectedItem().equals("Select") || cmbcategory.getSelectedItem().equals("Select") || cmbdosage.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Fill All The Fields Or Search A Record To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(exp_date.getDate());
            String Qty1 = (txtpatientid.getText());
            String Qty2 = (txtFN.getText());
            String Qty3 = (txtLN.getText());
            String Qty4 = (combogender.getSelectedItem().toString());
            String Qty5 = (cmbdrugname.getSelectedItem().toString());
            String Qty6 = (combodrugid.getSelectedItem().toString());
            String Qty7 = (cmbunitofmeasure.getSelectedItem().toString());
            String Qty8 = (cmbcategory.getSelectedItem().toString());
            String Qty9 = (txtqnty.getText());
            String Qty10 = ((JTextField) exp_date.getDateEditor().getUiComponent()).getText();
            String Qty11 = (txtprc.getText());
            String Qty12 = (txttotal_price.getText());
            String Qty13 = (cmbdosage.getSelectedItem().toString());
            String Qty14 = (txtstorage.getText());
            String Qty15 = (txtprecautions.getText());
            String Qty16 = (txsideeffects.getText());
            String Qty17 = (combo_issue.getSelectedItem().toString());
            txt_issuedrug_print.setText("");
            txt_issuedrug_print.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tPharmacy Receipt\n"
                    + "\nPatient ID:\t\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nGender:\t\t" + Qty4
                    + "\nDrug Name:\t\t" + Qty5
                    + "\nDrug Id:\t\t" + Qty6
                    + "\nUnit Of Measure:\t" + Qty7
                    + "\nCategory:\t\t" + Qty8
                    + "\nQuantity:\t\t" + Qty9
                    + "\nExpiry Date:\t\t" + Qty10
                    + "\nUNit Price:\t\t" + Qty11
                    + "\nPrice:\t\t" + Qty12
                    + "\nDosage:\t\t" + Qty13
                    + "\nStorage:\t\t" + Qty14
                    + "\nPrecautions:\t\t" + Qty15
                    + "\nSide Effects:\t\t" + Qty16
                    + "Issued:\t\t" + Qty17
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                    + "\n\tWE WISH YOU A QUICK RECOVERY");
            try {
                txt_issuedrug_print.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            txtsearch.setText("");
            txtpatientid.setText("");
            txtFN.setText("");
            txtLN.setText("");
            combogender.setSelectedItem("Select");
            cmbdrugname.setSelectedItem("Select");
            combodrugid.setSelectedItem("Select");
            cmbunitofmeasure.setSelectedItem("Select");
            cmbcategory.setSelectedItem("Select");
            txtqnty.setText("");
            exp_date.setDate(null);
            txtprc.setText("");
            txttotal_price.setText("");
            cmbdosage.setSelectedItem("Select");
            txtstorage.setText("");
            txtprecautions.setText("");
            txsideeffects.setText("");
            combo_issue.setSelectedItem("Select");
        }
    }

    public void calculate() {
        int a;
        int s = Integer.parseInt(txtqnty.getText());
        int q = Integer.parseInt(txtprc.getText());
        a = s * q;
        String x = String.valueOf(a);
        txttotal_price.setText(x);
    }

    public void username(String user) {
        lbluser.setText(user);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        lbluser = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        txtsearch = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        combogender = new javax.swing.JComboBox<>();
        txtLN = new javax.swing.JTextField();
        txtFN = new javax.swing.JTextField();
        txtpatientid = new javax.swing.JTextField();
        drugid1 = new javax.swing.JLabel();
        cmbdrugname = new javax.swing.JComboBox<>();
        drugname1 = new javax.swing.JLabel();
        combodrugid = new javax.swing.JComboBox<>();
        unitofmeasur1 = new javax.swing.JLabel();
        cmbunitofmeasure = new javax.swing.JComboBox<>();
        category1 = new javax.swing.JLabel();
        cmbcategory = new javax.swing.JComboBox<>();
        quantity1 = new javax.swing.JLabel();
        txtqnty = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        price1 = new javax.swing.JLabel();
        txtprc = new javax.swing.JTextField();
        price2 = new javax.swing.JLabel();
        cmbdosage = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        txtstorage = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtprecautions = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txsideeffects = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        btn_getpat = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btnrefresh1 = new javax.swing.JButton();
        btnsearch1 = new javax.swing.JButton();
        btnsearch2 = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        exp_date = new com.toedter.calendar.JDateChooser();
        price3 = new javax.swing.JLabel();
        txttotal_price = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txt_issuedrug_print = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        combo_issue = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        drugid = new javax.swing.JLabel();
        addnewdrug = new javax.swing.JLabel();
        drugname = new javax.swing.JLabel();
        unitofmeasur = new javax.swing.JLabel();
        quantity = new javax.swing.JLabel();
        combo_drugid = new javax.swing.JComboBox<>();
        combounitofmeasure = new javax.swing.JComboBox<>();
        category = new javax.swing.JLabel();
        combocategory = new javax.swing.JComboBox<>();
        combodrugname = new javax.swing.JComboBox<>();
        txtquantity = new javax.swing.JTextField();
        price = new javax.swing.JLabel();
        txtprice = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtbatchno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtsideeffects = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel10 = new javax.swing.JPanel();
        btn_invsave = new javax.swing.JButton();
        btn_invupdate = new javax.swing.JButton();
        btn_invrefresh = new javax.swing.JButton();
        btn_invsearch = new javax.swing.JButton();
        btn_invdelete = new javax.swing.JButton();
        Regdate = new com.toedter.calendar.JDateChooser();
        EXP_date = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtunitprice = new javax.swing.JTextField();
        txt_storage = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_precautions = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtdetails = new javax.swing.JTextArea();
        cmb_dosage = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        txt_invsearch = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        drugs_table = new javax.swing.JTable();
        btnrefresh2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jbtnchangepass = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblimage = new javax.swing.JLabel();
        btnuploadimage = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximizedBounds(new java.awt.Rectangle(1230, 780, 1230, 780));
        setMaximumSize(new java.awt.Dimension(1230, 780));
        setMinimumSize(new java.awt.Dimension(1230, 780));
        setPreferredSize(new java.awt.Dimension(1230, 780));
        setResizable(false);
        setSize(new java.awt.Dimension(1230, 780));

        jPanel1.setBackground(new java.awt.Color(0, 51, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/drugstore-icon.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Pharmacy");

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

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("Welcome");

        lbluser.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(216, 216, 216)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(lbluser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(2, 2, 2))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lbluser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel24, lbluser});

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel18.setText("Search ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Patient ID");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("First Name");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Last Name");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Gender");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combogender.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combogender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        txtLN.setEditable(false);
        txtLN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txtFN.setEditable(false);
        txtFN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txtpatientid.setEditable(false);
        txtpatientid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtpatientid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 1, true));

        drugid1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        drugid1.setText("Drug Name");
        drugid1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbdrugname.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbdrugname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Acetazolamide 125mg", "Amoxycilin 500mg", "Ampicilin 500mg", "Anticoagulant Citrate 10ml", "Antisnake Venom 10ml", "Betamethasone 2mg", "Biphasic Insulin 100 ml", "Biphasic Isophane 10ml", "Bisacodyl Suppository 5mg", "Bismuth Subsalicylate 262mg", "Carbenicillin 1g/ml", "Cascara Sagrada 125mg", "Castor Oil  10ml", "Cetirizine  Solution 1mg/ml", "Chloroquine 150ml", "Chlorpheniramine 2mg/5m", "Chlorpropamide 100mg", "Cimethdine  200mg/ml", "Cimethdine 200mg/ml", "Cloxacillin Sodium 250mg", "Clozapine 100mg", "Codeine Phosphate 15mg", "Codeine Phosphate 30mg/ml", "Diclofenac 50mg" }));
        cmbdrugname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbdrugnameActionPerformed(evt);
            }
        });

        drugname1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        drugname1.setText("Drug Id");
        drugname1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combodrugid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combodrugid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Acet12", "Amox97", "Ampi10", "Anti33", "Anti44", "Beta19", "Biph11", "Biph22", "Bisa12", "Bism45", "Carb47", "Cast10", "Ceti23", "Chlo32", "Chlo39", "Chlo98", "Cime24", "Clox32", "Cloz76", "Code09", "Code52", "Dicl09" }));

        unitofmeasur1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        unitofmeasur1.setText("Unit Measure");
        unitofmeasur1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbunitofmeasure.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbunitofmeasure.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "ml", "mg", "mg/ml", "g", "g/ml" }));

        category1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        category1.setText("Category");
        category1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbcategory.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbcategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Capsules", "Tablets", "Syrup", "Injection", "Liquid", "Gel", "Lotion", "Ointment", "Spray", "Cream", "Inhalers" }));

        quantity1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        quantity1.setText("Quantity");
        quantity1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtqnty.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtqnty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtqntyKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setText("Expiry Date");
        jLabel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        price1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price1.setText("Unit Price");
        price1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtprc.setEditable(false);
        txtprc.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        price2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price2.setText("Dosage");
        price2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbdosage.setEditable(true);
        cmbdosage.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbdosage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1x1", "1x2", "1x3", "1 Spn x3", "1 Injection", "Apply 3/day" }));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setOpaque(true);
        jSeparator2.setPreferredSize(new java.awt.Dimension(80, 4));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Storage");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtstorage.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Precautions");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtprecautions.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Side Effects");
        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txsideeffects.setColumns(10);
        txsideeffects.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txsideeffects.setLineWrap(true);
        txsideeffects.setRows(10);
        txsideeffects.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txsideeffects);

        jPanel6.setBackground(new java.awt.Color(153, 153, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_getpat.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_getpat.setText("PATIENT");
        btn_getpat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_getpat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_getpat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getpatActionPerformed(evt);
            }
        });

        btnupdate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_50px.png"))); // NOI18N
        btnupdate.setText("UPDATE");
        btnupdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnupdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btnrefresh1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnrefresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btnrefresh1.setText("REFRESH");
        btnrefresh1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnrefresh1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefresh1ActionPerformed(evt);
            }
        });

        btnsearch1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnsearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btnsearch1.setText("SEARCH");
        btnsearch1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsearch1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearch1ActionPerformed(evt);
            }
        });

        btnsearch2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnsearch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btnsearch2.setText("PRINT");
        btnsearch2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsearch2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearch2ActionPerformed(evt);
            }
        });

        btnsave.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btnsave.setText("SAVE");
        btnsave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnsearch1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnrefresh1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(btnupdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_getpat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsearch2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btn_getpat, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnrefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnsearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        exp_date.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        exp_date.setDateFormatString("yyyy-MM-dd");
        exp_date.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        price3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price3.setText("Price(Ksh.)");
        price3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txttotal_price.setEditable(false);
        txttotal_price.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txt_issuedrug_print.setColumns(20);
        txt_issuedrug_print.setRows(5);
        jScrollPane5.setViewportView(txt_issuedrug_print);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Issued");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combo_issue.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combo_issue.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Yes", "No" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFN, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                    .addComponent(txtpatientid)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(quantity1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(category1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(unitofmeasur1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtqnty)
                                        .addComponent(exp_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(drugname1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(drugid1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cmbcategory, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbdrugname, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(combodrugid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbunitofmeasure, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtLN)
                                        .addComponent(combogender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(price3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(7, 7, 7)
                                .addComponent(txttotal_price, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(price1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(price2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtstorage, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtprecautions, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                    .addGap(7, 7, 7)
                                                    .addComponent(cmbdosage, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(txtprc, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(combo_issue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbdosage, combo_issue, jScrollPane2, txtprc, txtprecautions, txtstorage});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbcategory, cmbdrugname, cmbunitofmeasure, combodrugid, combogender, txtFN, txtLN, txtpatientid});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel3});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttotal_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(price3)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtprc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(price1)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtLN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(combogender, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(drugid1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(cmbdrugname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(drugname1)
                                    .addComponent(combodrugid, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(unitofmeasur1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbunitofmeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbcategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(quantity1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtqnty, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(exp_date, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(price2)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(cmbdosage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtstorage))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtprecautions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(combo_issue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 62, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbcategory, cmbdosage, cmbdrugname, cmbunitofmeasure, combo_issue, combodrugid, combogender, exp_date, txtFN, txtLN, txtpatientid, txtprc, txtprecautions, txtqnty, txtstorage});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {category1, drugid1, drugname1, jLabel10, jLabel6, jLabel8, jLabel9, quantity1, unitofmeasur1});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {price1, price2});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel3});

        jTabbedPane1.addTab("Issue Drugs", jPanel2);

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        drugid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        drugid.setText("Drug Name");
        drugid.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        addnewdrug.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        addnewdrug.setText("Add New Drug");
        addnewdrug.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        drugname.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        drugname.setText("Drug Id");
        drugname.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        unitofmeasur.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        unitofmeasur.setText("Unit of Measure");
        unitofmeasur.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        quantity.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        quantity.setText("Quantity");
        quantity.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combo_drugid.setEditable(true);
        combo_drugid.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        combo_drugid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Acet12", "Amox97", "Ampi10", "Anti33", "Anti44", "Beta19", "Biph11", "Biph22", "Bisa12", "Bism45", "Carb47", "Cast10", "Ceti23", "Chlo32", "Chlo39", "Chlo98", "Cime24", "Clox32", "Cloz76", "Code09", "Code52", "Dicl09", "Dime98", "Dipy32", "Doxy32", "Ergo23", "Famo98", "Gent82", "Glib32", "Glip65", "Gluc45", "H2O32", "Halo32", "Ibup02", "Isof42", "Lact72", "Lora72", "Metf98", "Meth34", "Meto77", "Metr46", "Morp67", "Nali56", "Natr56", "Neom72", "Norf33", "Oxid56", "Para83", "Prom43", "Rabi32", "Tripo9" }));

        combounitofmeasure.setEditable(true);
        combounitofmeasure.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        combounitofmeasure.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "ml", "mg", "mg/ml", "g", "g/ml" }));

        category.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        category.setText("Category");
        category.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combocategory.setEditable(true);
        combocategory.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        combocategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Capsules", "Tablets", "Syrup", "Injection", "Liquid", "Gel", "Lotion", "Ointment", "Spray", "Cream", "Inhalers" }));

        combodrugname.setEditable(true);
        combodrugname.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        combodrugname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Acetazolamide 125mg", "Amoxycilin 500mg", "Ampicilin 500mg", "Anticoagulant Citrate 10ml", "Antisnake Venom 10ml", "Betamethasone 2mg", "Biphasic Insulin 100 ml", "Biphasic Isophane 10ml", "Bisacodyl Suppository 5mg", "Bismuth Subsalicylate 262mg", "Carbenicillin 1g/ml", "Cascara Sagrada 125mg", "Castor Oil  10ml", "Cetirizine  Solution 1mg/ml", "Chloroquine 150ml", "Chlorpheniramine 2mg/5m", "Chlorpropamide 100mg", "Cimethdine  200mg/ml", "Cimethdine 200mg/ml", "Cloxacillin Sodium 250mg", "Clozapine 100mg", "Codeine Phosphate 15mg", "Codeine Phosphate 30mg/ml", "Diclofenac 50mg", " Dimenhydrinate 500mg ", "Dipyrone 500mg/ml", "Doxycycline 100mg ", "Ergotamine 0.25mg/ml", "Famotidine 20mg", "Gentamicin 40mg/ml", "Glibenclamide 5mg", "Glipizide 2.5mg", "Glucose 200g ", "Halothene 250ml", "Ibuprufen 400mg", "Isoflurane 100ml", "Lactulose", "Loratadine 5mg/5ml ", "Metformin 500 mg", "Methadone 10mg/m", "Metoprolol", "Metronidazole 5mg/100ml", "Morphine Sulphate", "Nalidixic acid 500mg", "Neomycin 500mg", "Nitrofurantoin Cap 50mg", "Norfloxacin 400mg", "Oxidized Cellulose 10mg/ml", "Paracetamol 250mg", "Pentamidine 300mg", "Phenylephrine 3ml", "Primaquine 7.5mg", "Promathizine", "Propranolol", "Quinine 300mg/ml", "Rabies Vaccine 100ml", "Ranitidine 10mg/ml", "Scorpion Venom 100ml", "Sulfadoxine 500mg", "Tetanus Antitoxin 500mg", "Tolbutamide 500mg ", "Tramadol", "Tripotassuim 120mg/5ml", "Water for Injection 100ml" }));
        combodrugname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combodrugnameActionPerformed(evt);
            }
        });

        txtquantity.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N

        price.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price.setText("Purchase Price");
        price.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtprice.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Reg Date");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Expiry Date");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setText("Batch No");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtbatchno.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Details");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsideeffects.setColumns(10);
        txtsideeffects.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtsideeffects.setLineWrap(true);
        txtsideeffects.setRows(10);
        txtsideeffects.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtsideeffects);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Unit Price");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setOpaque(true);
        jSeparator1.setPreferredSize(new java.awt.Dimension(80, 10));

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_invsave.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_invsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btn_invsave.setText("SAVE");
        btn_invsave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_invsave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_invsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_invsaveActionPerformed(evt);
            }
        });

        btn_invupdate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_invupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_50px.png"))); // NOI18N
        btn_invupdate.setText("UPDATE");
        btn_invupdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_invupdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_invupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_invupdateActionPerformed(evt);
            }
        });

        btn_invrefresh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_invrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btn_invrefresh.setText("REFRESH");
        btn_invrefresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_invrefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_invrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_invrefreshActionPerformed(evt);
            }
        });

        btn_invsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_invsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btn_invsearch.setText("SEARCH");
        btn_invsearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_invsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_invsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_invsearchActionPerformed(evt);
            }
        });

        btn_invdelete.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_invdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_trash_32px.png"))); // NOI18N
        btn_invdelete.setText("DELETE");
        btn_invdelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_invdelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_invdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_invdeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_invsave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_invsearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_invupdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_invrefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_invdelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_invsave, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_invupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_invrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_invsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_invdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Regdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regdate.setDateFormatString("yyyy-MM-dd");
        Regdate.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        EXP_date.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EXP_date.setDateFormatString("yyyy-MM-dd");
        EXP_date.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Dosage");
        jLabel13.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("Storage");
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtunitprice.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txt_storage.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setText("Precautions");
        jLabel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_precautions.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setText("Side Effects");
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtdetails.setColumns(10);
        txtdetails.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtdetails.setLineWrap(true);
        txtdetails.setRows(10);
        txtdetails.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtdetails);

        cmb_dosage.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmb_dosage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1x1", "1x2", "1x3", "1 Spn x3", "1 Injection", "Apply 3/day" }));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel23.setText("Search ");
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_invsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(quantity, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(unitofmeasur, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(category, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(combocategory, javax.swing.GroupLayout.Alignment.LEADING, 0, 299, Short.MAX_VALUE)
                            .addComponent(txtquantity, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combounitofmeasure, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_precautions))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(drugname, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(drugid, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combo_drugid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combodrugname, 0, 299, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtunitprice))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(20, 20, 20)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_storage)
                                    .addComponent(cmb_dosage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(EXP_date, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtbatchno)))
                                    .addGap(141, 141, 141))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Regdate, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(addnewdrug, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(txt_invsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {drugid, drugname, unitofmeasur});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addnewdrug, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_invsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(drugid, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(combodrugname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtunitprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(drugname)
                        .addComponent(cmb_dosage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addComponent(combo_drugid))
                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitofmeasur, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combounitofmeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txt_storage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combocategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txt_precautions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(quantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtquantity)
                            .addComponent(jLabel22))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(price)
                            .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(Regdate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(EXP_date, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtbatchno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(167, 167, 167))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EXP_date, Regdate, combocategory, combodrugname, combounitofmeasure, txtbatchno, txtprice, txtquantity});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {category, drugid, drugname, quantity, unitofmeasur});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addnewdrug, jLabel23, txt_invsearch, txtunitprice});

        jTabbedPane1.addTab("Drug Inventory", jPanel4);

        jScrollPane4.setBackground(new java.awt.Color(153, 153, 255));
        jScrollPane4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 255, 255), 10, true));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        drugs_table.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        drugs_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "Title 15"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        drugs_table.setGridColor(new java.awt.Color(0, 0, 0));
        drugs_table.setRowHeight(25);
        drugs_table.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(drugs_table);

        btnrefresh2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnrefresh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btnrefresh2.setText("REFRESH");
        btnrefresh2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnrefresh2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrefresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefresh2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(btnrefresh2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnrefresh2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 105, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Drugs", jPanel5);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBackground(new java.awt.Color(153, 153, 255));

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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jbtnchangepass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jbtnchangepass, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 204, 255));

        lblimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/web_camera.png"))); // NOI18N

        btnuploadimage.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnuploadimage.setText("Upload Profile Photo");
        btnuploadimage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnuploadimageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
            .addComponent(lblimage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 0));
        jLabel20.setText("KEEP YOUR USERID CONFIDENTIAL");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(295, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Manage Account", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 727, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Login_Pharmacist HMS = new Login_Pharmacist();
        HMS.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        jButton3.setBackground(Color.gray);
    }//GEN-LAST:event_jButton3MouseEntered

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        jButton3.setBackground(Color.white);
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String user = lbluser.getText();
        PharmEditAccount HMS = new PharmEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        String user = lbluser.getText();
        PharmEditAccount HMS = new PharmEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jbtnchangepassMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnchangepassMouseEntered
        jbtnchangepass.setBackground(Color.gray);
    }//GEN-LAST:event_jbtnchangepassMouseEntered

    private void jbtnchangepassMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnchangepassMouseExited
        jbtnchangepass.setBackground(Color.white);
    }//GEN-LAST:event_jbtnchangepassMouseExited

    private void jbtnchangepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnchangepassActionPerformed
        String user = lbluser.getText();
        PharmEditAccount HMS = new PharmEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jbtnchangepassActionPerformed

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        String user = lbluser.getText();
        PharmEditAccount HMS = new PharmEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel25MouseClicked

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

    private void combodrugnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combodrugnameActionPerformed
        if (combodrugname.getSelectedItem().equals("Acetazolamide 125mg")) {
            combo_drugid.setSelectedItem("Acet12");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Tablets");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Amoxycilin 500mg")) {
            combo_drugid.setSelectedItem("Amox97");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Ampicilin 500mg")) {
            combo_drugid.setSelectedItem("Ampi10");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Anticoagulant Citrate 10ml")) {
            combo_drugid.setSelectedItem("Anti33");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Liquid");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Antisnake Venom 10ml")) {
            combo_drugid.setSelectedItem("Anti44");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Liquid");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Betamethasone 2mg")) {
            combo_drugid.setSelectedItem("Beta19");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Tablets");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Biphasic Insulin 100 ml")) {
            combo_drugid.setSelectedItem("Biph11");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Syrup");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Biphasic Isophane 10ml")) {
            combo_drugid.setSelectedItem("Biph22");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Liquid");
        } else if (combodrugname.getSelectedItem().equals("Bisacodyl Suppository 5mg")) {
            combo_drugid.setSelectedItem("Bisa12");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Gel");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Bismuth Subsalicylate 262mg")) {
            combo_drugid.setSelectedItem("Bism45");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Lotion");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Carbenicillin 1g/ml")) {
            combo_drugid.setSelectedItem("Carb47");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Lotion");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Cascara Sagrada 125mg")) {
            combo_drugid.setSelectedItem("Casc12");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Castor Oil  10ml")) {
            combo_drugid.setSelectedItem("Cast10");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Gel");
        } else if (combodrugname.getSelectedItem().equals("Cetirizine  Solution 1mg/ml")) {
            combo_drugid.setSelectedItem("Ceti23");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Injection");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Chloroquine 150ml")) {
            combo_drugid.setSelectedItem("Chlo98");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Liquid");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Chlorpheniramine 2mg/5m")) {
            combo_drugid.setSelectedItem("Chlo39");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Syrup");
        } else if (combodrugname.getSelectedItem().equals("Chlorpropamide 100mg")) {
            combo_drugid.setSelectedItem("Chlo32");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Chlorpropamide 100mg")) {
            combo_drugid.setSelectedItem("Chlo32");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Cimethdine  200mg/ml")) {
            combo_drugid.setSelectedItem("Cime24");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Liquid");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Cloxacillin Sodium 250mg")) {
            combo_drugid.setSelectedItem("Clox32");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Tablets");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Chlorpropamide 100mg")) {
            combo_drugid.setSelectedItem("Chlo32");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Clozapine 100mg")) {
            combo_drugid.setSelectedItem("Cloz76");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Tablets");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Codeine Phosphate 15mg")) {
            combo_drugid.setSelectedItem("Code52");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Codeine Phosphate 30mg/ml")) {
            combo_drugid.setSelectedItem("Code09");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Liquid");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Diclofenac 50mg")) {
            combo_drugid.setSelectedItem("Dicl09");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Tablets");
            txt_storage.setText("Cool dry place,away from the sunlight and children");
        } else if (combodrugname.getSelectedItem().equals("Dimenhydrinate 500mg ")) {
            combo_drugid.setSelectedItem("Dime98");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Dipyrone 500mg/ml")) {
            combo_drugid.setSelectedItem("Dipy32");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Syrup");
        } else if (combodrugname.getSelectedItem().equals("Doxycycline 100mg ")) {
            combo_drugid.setSelectedItem("Doxy32");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Gel");
        } else if (combodrugname.getSelectedItem().equals("Ergotamine 0.25mg/ml")) {
            combo_drugid.setSelectedItem("Ergo23");
            combounitofmeasure.setSelectedItem("mg/mml");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Famotidine 20mg")) {
            combo_drugid.setSelectedItem("Famo98");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Gentamicin 40mg/ml")) {
            combo_drugid.setSelectedItem("Gent82");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Tablets");
        } else if (combodrugname.getSelectedItem().equals("Glibenclamide 5mg")) {
            combo_drugid.setSelectedItem("Glib32");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Lotion");
        } else if (combodrugname.getSelectedItem().equals("Glipizide 2.5mg")) {
            combo_drugid.setSelectedItem("Glip65");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Liquid");
        } else if (combodrugname.getSelectedItem().equals("Glucose 200g ")) {
            combo_drugid.setSelectedItem("Gluc45");
            combounitofmeasure.setSelectedItem("g");
            combocategory.setSelectedItem("Syrup");
        } else if (combodrugname.getSelectedItem().equals("Halothene 250ml")) {
            combo_drugid.setSelectedItem("Halo32");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Liquid");
        } else if (combodrugname.getSelectedItem().equals("Ibuprufen 400mg")) {
            combo_drugid.setSelectedItem("Ibup02");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Isoflurane 100ml")) {
            combo_drugid.setSelectedItem("Isof42");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Liquid");
        } else if (combodrugname.getSelectedItem().equals("Lactulose")) {
            combo_drugid.setSelectedItem("Lact72");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Loratadine 5mg/5ml ")) {
            combo_drugid.setSelectedItem("Lora72");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Metformin 500 mg")) {
            combo_drugid.setSelectedItem("Metf98");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Methadone 10mg/m")) {
            combo_drugid.setSelectedItem("Meth34");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Metoprolol")) {
            combo_drugid.setSelectedItem("Meto77");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Metronidazole 5mg/100ml")) {
            combo_drugid.setSelectedItem("Metr46");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Morphine Sulphate")) {
            combo_drugid.setSelectedItem("Morp67");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Nalidixic acid 500mg")) {
            combo_drugid.setSelectedItem("Nali56");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Neomycin 500mg")) {
            combo_drugid.setSelectedItem("Neom72");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Nitrofurantoin Cap 50mg")) {
            combo_drugid.setSelectedItem("Natr56");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Norfloxacin 400mg")) {
            combo_drugid.setSelectedItem("Norf33");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Nalidixic acid 500mg")) {
            combo_drugid.setSelectedItem("Nali56");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Oxidized Cellulose 10mg/ml")) {
            combo_drugid.setSelectedItem("Oxid56");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Paracetamol 250mg")) {
            combo_drugid.setSelectedItem("Para83");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Capsules");
        } else if (combodrugname.getSelectedItem().equals("Tripotassuim 120mg/5ml")) {
            combo_drugid.setSelectedItem("Trip09");
            combounitofmeasure.setSelectedItem("mg/ml");
            combocategory.setSelectedItem("Tablets");
        } else if (combodrugname.getSelectedItem().equals("Rabies Vaccine 100ml")) {
            combo_drugid.setSelectedItem("Rabi32");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Liquid");
        } else if (combodrugname.getSelectedItem().equals("Promathizine")) {
            combo_drugid.setSelectedItem("Prom43");
            combounitofmeasure.setSelectedItem("mg");
            combocategory.setSelectedItem("Tablets");
        } else if (combodrugname.getSelectedItem().equals("Water for Injection 100ml")) {
            combo_drugid.setSelectedItem("H2O32");
            combounitofmeasure.setSelectedItem("ml");
            combocategory.setSelectedItem("Liquid");
        }
    }//GEN-LAST:event_combodrugnameActionPerformed

    private void btn_getpatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getpatActionPerformed
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        combogender.setSelectedItem("Select");
        cmbdrugname.setSelectedItem("Select");
        combodrugid.setSelectedItem("Select");
        cmbunitofmeasure.setSelectedItem("Select");
        cmbcategory.setSelectedItem("Select");
        txtqnty.setText("");
        exp_date.setDate(null);
        txtprc.setText("");
        txttotal_price.setText("");
        cmbdosage.setSelectedItem("Select");
        txtstorage.setText("");
        txtprecautions.setText("");
        txsideeffects.setText("");
        combo_issue.setSelectedItem("Select");
        if (txtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from presription where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsearch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add5 = rs.getString("Drugname");
                    cmbdrugname.setSelectedItem(add5);
                    String add1 = rs.getString("Patid");
                    txtpatientid.setText(add1);
                    String add2 = rs.getString("FN");
                    txtFN.setText(add2);
                    String add3 = rs.getString("LN");
                    txtLN.setText(add3);
                    String add4 = rs.getString("Gender");
                    combogender.setSelectedItem(add4);
                    String add6 = rs.getString("Drugid");
                    combodrugid.setSelectedItem(add6);
                    String add7 = rs.getString("Unit_msr");
                    cmbunitofmeasure.setSelectedItem(add7);
                    String add8 = rs.getString("Category");
                    cmbcategory.setSelectedItem(add8);
                    String add9 = rs.getString("Quantity");
                    txtqnty.setText(add9);
                    String add10 = rs.getObject("Expdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add10);
                    exp_date.setDate(dat);
                    String add11 = rs.getString("Unit_prc");
                    txtprc.setText(add11);
                    String add12 = rs.getString("Prc");
                    txttotal_price.setText(add12);
                    String add14 = rs.getString("Dosage");
                    cmbdosage.setSelectedItem(add14);
                    String add15 = rs.getString("Storage");
                    txtstorage.setText(add15);
                    String add16 = rs.getString("Precautions");
                    txtprecautions.setText(add16);
                    String add17 = rs.getString("Sideeffects");
                    txsideeffects.setText(add17);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "No Record Found");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
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

        /* Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        if (txtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from prescription where Patientid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
               pst.setString(1, txtsearch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add4 = rs.getString("Gender");
                    combogender.setSelectedItem(add4);
                    String add1 = rs.getString("Patientid");
                    txtpatientid.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txtFN.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txtLN.setText(add3);
                    String add5 = rs.getString("Drugname");
                    cmbdrugname.setSelectedItem(add5);
                    String add6 = rs.getString("Drugid");
                    combodrugid.setSelectedItem(add6);
                    String add7 = rs.getString("Unitofmeasure");
                    cmbunitofmeasure.setSelectedItem(add7);
                    String add8 = rs.getString("Category");
                    cmbcategory.setSelectedItem(add8);
                    String add9 = rs.getString("Quantity");
                    txtqnty.setText(add9);
                    String add10 = rs.getObject("Expdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add10);
                    exp_date.setDate(dat);
                    String add11 = rs.getString("Unit_price");
                    txtprc.setText(add11);
                    String add12 = rs.getString("Price");
                    txttotal_price.setText(add12);
                    String add14 = rs.getString("Dosage");
                    cmbdosage.setSelectedItem(add14);
                    String add15 = rs.getString("Storage");
                    txtstorage.setText(add15);
                    String add16 = rs.getString("Precautions");
                    txtprecautions.setText(add16);
                    String add17 = rs.getString("Sideeffects");
                    txsideeffects.setText(add17);
                    System.out.println("Record Found");
                }
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
                System.out.println("Connection Problems...");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }*/
    }//GEN-LAST:event_btn_getpatActionPerformed

    private void btnrefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefresh1ActionPerformed
        txtsearch.setText("");
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        combogender.setSelectedItem("Select");
        cmbdrugname.setSelectedItem("Select");
        combodrugid.setSelectedItem("Select");
        cmbunitofmeasure.setSelectedItem("Select");
        cmbcategory.setSelectedItem("Select");
        txtqnty.setText("");
        exp_date.setDate(null);
        txtprc.setText("");
        txttotal_price.setText("");
        cmbdosage.setSelectedItem("Select");
        txtstorage.setText("");
        txtprecautions.setText("");
        txsideeffects.setText("");
        combo_issue.setSelectedItem("Select");
    }//GEN-LAST:event_btnrefresh1ActionPerformed

    private void btn_invsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invsaveActionPerformed
        //FIELDS VALIDATION
        if (txtquantity.getText().isEmpty() || txtprice.getText().isEmpty() || txtbatchno.getText().isEmpty() || txtdetails.getText().isEmpty() || txtunitprice.getText().isEmpty()
                || txt_storage.getText().isEmpty() || txt_precautions.getText().isEmpty() || txtsideeffects.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combodrugname.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter The Drug Name",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combo_drugid.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter The Drug Id",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combounitofmeasure.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter The Unit Of Measure",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combocategory.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter The Category",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_dosage.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Enter The Dosage",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Regdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Insert The Registration Date",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (EXP_date.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Isert The Expiry Date",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //CONVERION TO STRING
            String Drg_Name = combodrugname.getSelectedItem().toString();
            String Drg_id = combo_drugid.getSelectedItem().toString();
            String Unit_msr = combounitofmeasure.getSelectedItem().toString();
            String Category = combocategory.getSelectedItem().toString();
            String Dosage = cmb_dosage.getSelectedItem().toString();
            String Reg_date = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Regdate.getDate());
            String Exp_date = ((JTextField) EXP_date.getDateEditor().getUiComponent()).getText();
            Date_Format.format(EXP_date.getDate());
            System.out.println("Fields Validation Complete");
            try {
                String sql = "insert into drug_inventory(Drugname,Drugid,Unit_msr,Category,Quantity,Pur_price,Regdate,Expdate,Batchno,Details,Unit_price,Dosage,Storage,Precautions,Side_effects) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to Database");
                pst = con.prepareStatement(sql);
                pst.setString(1, Drg_Name);
                pst.setString(2, Drg_id);
                pst.setString(3, Unit_msr);
                pst.setString(4, Category);
                pst.setString(5, txtquantity.getText());
                pst.setString(6, txtprice.getText());
                pst.setString(7, Reg_date);
                pst.setString(8, Exp_date);
                pst.setString(9, txtbatchno.getText());
                pst.setString(10, txtdetails.getText());
                pst.setString(11, txtunitprice.getText());
                pst.setString(12, Dosage);
                pst.setString(13, txt_storage.getText());
                pst.setString(14, txt_precautions.getText());
                pst.setString(15, txtsideeffects.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                txt_invsearch.setText("");
                combodrugname.setSelectedItem("Select");
                combo_drugid.setSelectedItem("Select");
                combounitofmeasure.setSelectedItem("Select");
                combocategory.setSelectedItem("Select");
                txtquantity.setText("");
                txtprice.setText("");
                Regdate.setDate(null);
                EXP_date.setDate(null);
                txtbatchno.setText("");
                txtdetails.setText("");
                txtunitprice.setText("");
                cmb_dosage.setSelectedItem("Select");
                txt_storage.setText("");
                txt_precautions.setText("");
                txtsideeffects.setText("");
                System.out.println("Record saved successfully");
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
    }//GEN-LAST:event_btn_invsaveActionPerformed

    private void btn_invrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invrefreshActionPerformed
        txt_invsearch.setText("");
        combodrugname.setSelectedItem("Select");
        combo_drugid.setSelectedItem("Select");
        combounitofmeasure.setSelectedItem("Select");
        combocategory.setSelectedItem("Select");
        txtquantity.setText("");
        txtprice.setText("");
        Regdate.setDate(null);
        EXP_date.setDate(null);
        txtbatchno.setText("");
        txtdetails.setText("");
        txtunitprice.setText("");
        cmb_dosage.setSelectedItem("Select");
        txt_storage.setText("");
        txt_precautions.setText("");
        txtsideeffects.setText("");
    }//GEN-LAST:event_btn_invrefreshActionPerformed

    private void cmbdrugnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbdrugnameActionPerformed
        if (cmbdrugname.getSelectedItem().equals("Select")) {
            cmbdrugname.setSelectedItem("Select");
        } else {
            this.Drugset();
        }
    }//GEN-LAST:event_cmbdrugnameActionPerformed

    private void btn_invupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invupdateActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you Sure to Update?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Update Dialog................");
        if (p == 0) {
            //FIELDS VALIDATION
            if (txtquantity.getText().isEmpty() || txtprice.getText().isEmpty() || txtbatchno.getText().isEmpty() || txtdetails.getText().isEmpty() || txtunitprice.getText().isEmpty()
                    || txt_storage.getText().isEmpty() || txt_precautions.getText().isEmpty() || txtsideeffects.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combodrugname.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Enter The Drug Name",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combo_drugid.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Enter The Drug Id",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combounitofmeasure.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Enter The Unit Of Measure",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combocategory.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Enter The Category",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmb_dosage.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Enter The Dosage",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Regdate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Insert The Registration Date",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (EXP_date.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Isert The Expiry Date",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //CONVERION TO STRING
                String Drg_Name = combodrugname.getSelectedItem().toString();
                String Drg_id = combo_drugid.getSelectedItem().toString();
                String Unit_msr = combounitofmeasure.getSelectedItem().toString();
                String Category = combocategory.getSelectedItem().toString();
                String Dosage = cmb_dosage.getSelectedItem().toString();
                String Reg_date = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(Regdate.getDate());
                String Exp_date = ((JTextField) EXP_date.getDateEditor().getUiComponent()).getText();
                Date_Format.format(EXP_date.getDate());
                try {

                    String sql = "update drug_inventory set Drugname='" + Drg_Name + "',Unit_msr='" + Unit_msr + "',Category='" + Category + "',Quantity='" + txtquantity.getText() + "',Pur_price='" + txtprice.getText() + "',Regdate='" + Reg_date + "',Expdate='" + Exp_date + "',Batchno='" + txtbatchno.getText() + "',Details='" + txtdetails.getText() + "',Unit_price='" + txtunitprice.getText() + "',Dosage='" + Dosage + "',Storage='" + txt_storage.getText() + "',Precautions='" + txt_precautions.getText() + "',Side_effects='" + txtsideeffects.getText() + "' where Drugid='" + Drg_id + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    System.out.println("Connecting to Db");
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                    txt_invsearch.setText("");
                    combodrugname.setSelectedItem("Select");
                    combo_drugid.setSelectedItem("Select");
                    combounitofmeasure.setSelectedItem("Select");
                    combocategory.setSelectedItem("Select");
                    txtquantity.setText("");
                    txtprice.setText("");
                    Regdate.setDate(null);
                    EXP_date.setDate(null);
                    txtbatchno.setText("");
                    txtdetails.setText("");
                    txtunitprice.setText("");
                    cmb_dosage.setSelectedItem("Select");
                    txt_storage.setText("");
                    txt_precautions.setText("");
                    txtsideeffects.setText("");
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
        }
    }//GEN-LAST:event_btn_invupdateActionPerformed

    private void btn_invsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invsearchActionPerformed
        combodrugname.setSelectedItem("Select");
        combo_drugid.setSelectedItem("Select");
        combounitofmeasure.setSelectedItem("Select");
        combocategory.setSelectedItem("Select");
        txtquantity.setText("");
        txtprice.setText("");
        Regdate.setDate(null);
        EXP_date.setDate(null);
        txtbatchno.setText("");
        txtdetails.setText("");
        txtunitprice.setText("");
        cmb_dosage.setSelectedItem("Select");
        txt_storage.setText("");
        txt_precautions.setText("");
        txtsideeffects.setText("");
        this.Drginventory_srch();
    }//GEN-LAST:event_btn_invsearchActionPerformed

    private void btn_invdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invdeleteActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "DO YOU REALLY WANT TO DELETE", "delete", JOptionPane.YES_NO_OPTION);
        System.out.println("Delete Confirmation");
        if (p == 0) {
            try {
                String sql = "delete from drug_inventory where Drugid=?";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_invsearch.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD DELETED SUCCESSFULLY");
                txt_invsearch.setText("");
                combodrugname.setSelectedItem("Select");
                combo_drugid.setSelectedItem("Select");
                combounitofmeasure.setSelectedItem("Select");
                combocategory.setSelectedItem("Select");
                txtquantity.setText("");
                txtprice.setText("");
                Regdate.setDate(null);
                EXP_date.setDate(null);
                txtbatchno.setText("");
                txtdetails.setText("");
                txtunitprice.setText("");
                cmb_dosage.setSelectedItem("Select");
                txt_storage.setText("");
                txt_precautions.setText("");
                txtsideeffects.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    con.close();
                    System.out.println("Delete Completed");
                    System.out.println("disconnected");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

        }
    }//GEN-LAST:event_btn_invdeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you Sure to Update?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Update Dialog................");
        if (p == 0) {
            //FIELDS VALIDATION
            if (txtpatientid.getText().isEmpty()
                    || txtFN.getText().isEmpty()
                    || txtLN.getText().isEmpty()
                    || txsideeffects.getText().isEmpty()
                    || txtprecautions.getText().isEmpty()
                    || txtstorage.getText().isEmpty()
                    || txtprc.getText().isEmpty()
                    || txtqnty.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combogender.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Gender",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbdrugname.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Drug Name",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combodrugid.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Drug Id",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbunitofmeasure.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Drug Unit Of Measure",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbcategory.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Drug Category",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbdosage.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select DrugUnit Dosage",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combo_issue.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Issued If The Drugs Are Given",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String Gender = combogender.getSelectedItem().toString();
                String Drugname = cmbdrugname.getSelectedItem().toString();
                String Drugid = combodrugid.getSelectedItem().toString();
                String Unitofmeasure = cmbunitofmeasure.getSelectedItem().toString();
                String Category = cmbcategory.getSelectedItem().toString();
                String Dosage = cmbdosage.getSelectedItem().toString();
                String Issued = combo_issue.getSelectedItem().toString();
                String val = ((JTextField) exp_date.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(exp_date.getDate());
                try {
                    String sql = "update issuedrugs set Firstname='" + txtFN.getText() + "',"
                            + "Lastname='" + txtLN.getText() + "',Gender='" + Gender + "',"
                            + "Drugname='" + Drugname + "',Drugid='" + Drugid + "',"
                            + "Unitofmeasure='" + Unitofmeasure + "',Category='" + Category + "',"
                            + "Quantity='" + txtqnty.getText() + "',Expdate='" + val + "',"
                            + "Unit_price='" + txtprc.getText() + "',Price='" + txttotal_price.getText() + "',"
                            + "Dosage='" + Dosage + "',Storage='" + txtstorage.getText() + "',"
                            + "Precautions='" + txtprecautions.getText() + "',Sideeffects='" + txsideeffects.getText() + "'"
                            + ",Issued='" + Issued + "' where Patientid='" + txtpatientid.getText() + "' ";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    System.out.println("Connecting to Db");
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                    JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                    this.issuedrug_printing();
                    txtsearch.setText("");
                    txtpatientid.setText("");
                    txtFN.setText("");
                    txtLN.setText("");
                    combogender.setSelectedItem("Select");
                    cmbdrugname.setSelectedItem("Select");
                    combodrugid.setSelectedItem("Select");
                    cmbunitofmeasure.setSelectedItem("Select");
                    cmbcategory.setSelectedItem("Select");
                    txtqnty.setText("");
                    exp_date.setDate(null);
                    txtprc.setText("");
                    txttotal_price.setText("");
                    cmbdosage.setSelectedItem("Select");
                    txtstorage.setText("");
                    txtprecautions.setText("");
                    txsideeffects.setText("");
                    combo_issue.setSelectedItem("Select");
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
        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnsearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearch1ActionPerformed
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        combogender.setSelectedItem("Select");
        cmbdrugname.setSelectedItem("Select");
        combodrugid.setSelectedItem("Select");
        cmbunitofmeasure.setSelectedItem("Select");
        cmbcategory.setSelectedItem("Select");
        txtqnty.setText("");
        exp_date.setDate(null);
        txtprc.setText("");
        txttotal_price.setText("");
        cmbdosage.setSelectedItem("Select");
        txtstorage.setText("");
        txtprecautions.setText("");
        txsideeffects.setText("");
        combo_issue.setSelectedItem("Select");
        if (txtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from issuedrugs where Patientid=?";
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
                    String add5 = rs.getString("Drugname");
                    cmbdrugname.setSelectedItem(add5);
                    String add6 = rs.getString("Drugid");
                    combodrugid.setSelectedItem(add6);
                    String add7 = rs.getString("Unitofmeasure");
                    cmbunitofmeasure.setSelectedItem(add7);
                    String add8 = rs.getString("Category");
                    cmbcategory.setSelectedItem(add8);
                    String add9 = rs.getString("Quantity");
                    txtqnty.setText(add9);
                    String add10 = rs.getObject("Expdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add10);
                    exp_date.setDate(dat);
                    String add11 = rs.getString("Unit_price");
                    txtprc.setText(add11);
                    String add12 = rs.getString("Price");
                    txttotal_price.setText(add12);
                    String add14 = rs.getString("Dosage");
                    cmbdosage.setSelectedItem(add14);
                    String add15 = rs.getString("Storage");
                    txtstorage.setText(add15);
                    String add16 = rs.getString("Precautions");
                    txtprecautions.setText(add16);
                    String add17 = rs.getString("Sideeffects");
                    txsideeffects.setText(add17);
                    String add18 = rs.getString("Issued");
                    combo_issue.setSelectedItem(add18);
                    System.out.println("Record Found");
                }
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
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
    }//GEN-LAST:event_btnsearch1ActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        if (txtpatientid.getText().isEmpty()
                || txtFN.getText().isEmpty()
                || txtLN.getText().isEmpty()
                || txsideeffects.getText().isEmpty()
                || txtprecautions.getText().isEmpty()
                || txtstorage.getText().isEmpty()
                || txtprc.getText().isEmpty()
                || txtqnty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbdrugname.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Name",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combodrugid.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Id",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbunitofmeasure.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Unit Of Measure",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbcategory.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Category",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbdosage.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Dosage",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combo_issue.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Issued If The Drugs Are Given",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = combogender.getSelectedItem().toString();
            String Drugname = cmbdrugname.getSelectedItem().toString();
            String Drugid = combodrugid.getSelectedItem().toString();
            String Unitofmeasure = cmbunitofmeasure.getSelectedItem().toString();
            String Category = cmbcategory.getSelectedItem().toString();
            String Dosage = cmbdosage.getSelectedItem().toString();
            String Issued = combo_issue.getSelectedItem().toString();
            String val = ((JTextField) exp_date.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(exp_date.getDate());
            try {

                String sql = "insert into issuedrugs (Patientid,Firstname,Lastname,Gender,Drugname,Drugid,Unitofmeasure,Category,Quantity,Expdate,Unit_price,Price,Dosage,Storage,Precautions,Sideeffects,Issued) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtpatientid.getText());
                pst.setString(2, txtFN.getText());
                pst.setString(3, txtLN.getText());
                pst.setString(4, Gender);
                pst.setString(5, Drugname);
                pst.setString(6, Drugid);
                pst.setString(7, Unitofmeasure);
                pst.setString(8, Category);
                pst.setString(9, txtqnty.getText());
                pst.setString(10, val);
                pst.setString(11, txtprc.getText());
                pst.setString(12, txttotal_price.getText());
                pst.setString(13, Dosage);
                pst.setString(14, txtstorage.getText());
                pst.setString(15, txtprecautions.getText());
                pst.setString(16, txsideeffects.getText());
                pst.setString(17, Issued);
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.issuedrug_printing();
                txtsearch.setText("");
                txtpatientid.setText("");
                txtFN.setText("");
                txtLN.setText("");
                combogender.setSelectedItem("Select");
                cmbdrugname.setSelectedItem("Select");
                combodrugid.setSelectedItem("Select");
                cmbunitofmeasure.setSelectedItem("Select");
                cmbcategory.setSelectedItem("Select");
                txtqnty.setText("");
                exp_date.setDate(null);
                txtprc.setText("");
                txttotal_price.setText("");
                cmbdosage.setSelectedItem("Select");
                txtstorage.setText("");
                txtprecautions.setText("");
                txsideeffects.setText("");
                combo_issue.setSelectedItem("Select");
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
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnsearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearch2ActionPerformed
        this.issuedrug_printing();
    }//GEN-LAST:event_btnsearch2ActionPerformed

    private void txtqntyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqntyKeyReleased
        this.calculate();
    }//GEN-LAST:event_txtqntyKeyReleased

    private void btnrefresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefresh2ActionPerformed
     view_table();
    }//GEN-LAST:event_btnrefresh2ActionPerformed

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
            java.util.logging.Logger.getLogger(Pharmacy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pharmacy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pharmacy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pharmacy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pharmacy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser EXP_date;
    private com.toedter.calendar.JDateChooser Regdate;
    private javax.swing.JLabel addnewdrug;
    private javax.swing.JButton btn_getpat;
    private javax.swing.JButton btn_invdelete;
    private javax.swing.JButton btn_invrefresh;
    private javax.swing.JButton btn_invsave;
    private javax.swing.JButton btn_invsearch;
    private javax.swing.JButton btn_invupdate;
    private javax.swing.JButton btnrefresh1;
    private javax.swing.JButton btnrefresh2;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsearch1;
    private javax.swing.JButton btnsearch2;
    private javax.swing.JButton btnupdate;
    private javax.swing.JButton btnuploadimage;
    private javax.swing.JLabel category;
    private javax.swing.JLabel category1;
    private javax.swing.JComboBox<String> cmb_dosage;
    private javax.swing.JComboBox<String> cmbcategory;
    private javax.swing.JComboBox<String> cmbdosage;
    private javax.swing.JComboBox<String> cmbdrugname;
    private javax.swing.JComboBox<String> cmbunitofmeasure;
    private javax.swing.JComboBox<String> combo_drugid;
    private javax.swing.JComboBox<String> combo_issue;
    private javax.swing.JComboBox<String> combocategory;
    private javax.swing.JComboBox<String> combodrugid;
    private javax.swing.JComboBox<String> combodrugname;
    private javax.swing.JComboBox<String> combogender;
    private javax.swing.JComboBox<String> combounitofmeasure;
    private javax.swing.JLabel drugid;
    private javax.swing.JLabel drugid1;
    private javax.swing.JLabel drugname;
    private javax.swing.JLabel drugname1;
    private javax.swing.JTable drugs_table;
    private com.toedter.calendar.JDateChooser exp_date;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnchangepass;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lbluser;
    private javax.swing.JLabel price;
    private javax.swing.JLabel price1;
    private javax.swing.JLabel price2;
    private javax.swing.JLabel price3;
    private javax.swing.JLabel quantity;
    private javax.swing.JLabel quantity1;
    private javax.swing.JTextArea txsideeffects;
    private javax.swing.JTextField txtFN;
    private javax.swing.JTextField txtLN;
    private javax.swing.JTextField txt_invsearch;
    private javax.swing.JTextArea txt_issuedrug_print;
    private javax.swing.JTextField txt_precautions;
    private javax.swing.JTextField txt_storage;
    private javax.swing.JTextField txtbatchno;
    private javax.swing.JTextArea txtdetails;
    private javax.swing.JTextField txtpatientid;
    private javax.swing.JTextField txtprc;
    private javax.swing.JTextField txtprecautions;
    private javax.swing.JTextField txtprice;
    private javax.swing.JTextField txtqnty;
    private javax.swing.JTextField txtquantity;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextArea txtsideeffects;
    private javax.swing.JTextField txtstorage;
    private javax.swing.JTextField txttotal_price;
    private javax.swing.JTextField txtunitprice;
    private javax.swing.JLabel unitofmeasur;
    private javax.swing.JLabel unitofmeasur1;
    // End of variables declaration//GEN-END:variables
}
