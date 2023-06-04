package HMS;

import java.awt.Color;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Allein
 */
public class Laboratory extends javax.swing.JFrame {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String Imagename = null;
    byte[] uimage = null;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public Laboratory() {
        initComponents();
        System.out.println("Application Started\n" + dtf.format(now));
        view_table();
     }

    public void view_table() {

        try {
           con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from lab_results";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            lab_pattable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            lab_pattable.setModel(DbUtils.resultSetToTableModel(rs));
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

    public void Searching() {
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
                txtpatientid.setText(add1);
                String add2 = rs.getString("Firstname");
                txtFN.setText(add2);
                String add3 = rs.getString("Lastname");
                txtLN.setText(add3);
                String add4 = rs.getString("Gender");
                combogender.setSelectedItem(add4);
                String add5 = rs.getString("Maritalstatus");
                combomaritalstatus.setSelectedItem(add5);
                String add6 = rs.getString("Guardianname");
                txtguardian.setText(add6);
                String add7 = rs.getString("Bloodgroup");
                combobloodgroup.setSelectedItem(add7);
                String add8 = rs.getString("Patienttype");
                combopatienttype.setSelectedItem(add8);
                String add9 = rs.getString("Weight");
                txtweight.setText(add9);
                String add10 = rs.getString("Age");
                txtage.setText(add10);
                String add11 = rs.getString("Bodytemp");
                txtbodytemp.setText(add11);
                String ad = rs.getObject("Testdate").toString();
                java.util.Date test = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                testdate.setDate(test);
                String add13 = rs.getString("Type_test");
                combotypetest.setSelectedItem(add13);
                String add14 = rs.getString("Sampletype");
                combosampletype.setSelectedItem(add14);
                String add15 = rs.getString("Sampleid");
                txtsampleid.setText(add15);
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
    }

    public void username(String user) {
        lbluser.setText(user);
    }
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lbluser = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtpatientid = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtFN = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtLN = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        combobloodgroup = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        combopatienttype = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jtxtsearch = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtsampleid = new javax.swing.JTextField();
        combogender = new javax.swing.JComboBox<>();
        combosampletype = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        typeoftest = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txttechnicianname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtdiagnosis = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtresults = new javax.swing.JTextArea();
        btnsave = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btn_get_patient = new javax.swing.JButton();
        btnrefresh = new javax.swing.JButton();
        btnViewall = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        testdate = new com.toedter.calendar.JDateChooser();
        combomaritalstatus = new javax.swing.JComboBox<>();
        txtguardian = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtweight = new javax.swing.JTextField();
        txtage = new javax.swing.JTextField();
        resultsdate = new com.toedter.calendar.JDateChooser();
        typeoftest1 = new javax.swing.JLabel();
        combotypetest = new javax.swing.JComboBox<>();
        txtbodytemp = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lab_pattable = new javax.swing.JTable();
        btnrefresh1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jbtnchangepass = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblimage = new javax.swing.JLabel();
        btnuploadimage = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 0));
        setMaximizedBounds(new java.awt.Rectangle(1230, 780, 1230, 780));
        setMaximumSize(new java.awt.Dimension(1230, 780));
        setMinimumSize(new java.awt.Dimension(1230, 780));
        setPreferredSize(new java.awt.Dimension(1230, 780));
        setResizable(false);
        setSize(new java.awt.Dimension(1230, 780));

        jPanel4.setBackground(new java.awt.Color(0, 51, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/labPerson.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Laboratory");

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(27, 27, 27)
                        .addComponent(lbluser, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)))
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(7, 7, 7)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lbluser)))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, lbluser});

        jTabbedPane1.setBackground(new java.awt.Color(255, 102, 102));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Patient ID");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtpatientid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtpatientid.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("First Name");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtFN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtFN.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setText("Last Name");
        jLabel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtLN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtLN.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setText("Gender");
        jLabel20.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setText("Marital Status");
        jLabel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setText("Blood Group");
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combobloodgroup.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combobloodgroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "AB", "O", "A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+", "Unknown" }));
        combobloodgroup.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combobloodgroup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel24.setText("Patient Type");
        jLabel24.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combopatienttype.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combopatienttype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Inpatient", "Outpatient", "Pending" }));
        combopatienttype.setToolTipText("");
        combopatienttype.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combopatienttype.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel22.setText("Search ");
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtxtsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jtxtsearch.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setText("Sample type");
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setText("Sample Id");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsampleid.setEditable(false);
        txtsampleid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtsampleid.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combogender.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combogender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));
        combogender.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combosampletype.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combosampletype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Blood", "Stool", "Urine", "Saliva", "Skin", "Semen", "Rectal", "Spinal Fluids", "Cervical", "Molecular(DNA)", "Tissue" }));
        combosampletype.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combosampletype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosampletypeActionPerformed(evt);
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
        jScrollPane1.setViewportView(txtresults);

        btnsave.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btnsave.setText("SAVE");
        btnsave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnsaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnsaveMouseExited(evt);
            }
        });
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btnupdate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_50px.png"))); // NOI18N
        btnupdate.setText("UPDATE");
        btnupdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnupdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnupdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnupdateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnupdateMouseExited(evt);
            }
        });
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btn_get_patient.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_get_patient.setText("PATIENT");
        btn_get_patient.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_get_patient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_get_patient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_get_patientMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_get_patientMouseExited(evt);
            }
        });
        btn_get_patient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_get_patientActionPerformed(evt);
            }
        });

        btnrefresh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btnrefresh.setText("REFRESH");
        btnrefresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnrefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnrefreshMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnrefreshMouseExited(evt);
            }
        });
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
        btnViewall.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewallMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewallMouseExited(evt);
            }
        });
        btnViewall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewallActionPerformed(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jSeparator2.setOpaque(true);
        jSeparator2.setPreferredSize(new java.awt.Dimension(80, 10));

        testdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        testdate.setDateFormatString("yyyy-MM-dd");
        testdate.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        combomaritalstatus.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combomaritalstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Married", "Single", "Divorced", "Widowed" }));
        combomaritalstatus.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        combomaritalstatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txtguardian.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtguardian.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel27.setText("Guardian Name");
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel32.setText("Age");
        jLabel32.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel30.setText("Weight(Kgs)");
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtweight.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtweight.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtage.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtage.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

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

        txtbodytemp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtbodytemp.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnrefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                        .addComponent(btnupdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnsave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnViewall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btn_get_patient, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtbodytemp, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtweight, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtFN, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                                            .addComponent(txtpatientid)
                                            .addComponent(txtLN)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel27))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtguardian)
                                            .addComponent(combomaritalstatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(combogender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(combobloodgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(combopatienttype, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtage))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(typeoftest, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(testdate, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(resultsdate, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txttechnicianname, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(typeoftest1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(combosampletype, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtsampleid, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                        .addComponent(combotypetest, 0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtdiagnosis, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jLabel22)
                        .addGap(36, 36, 36)
                        .addComponent(jtxtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel15, jLabel16, jLabel17, jLabel20, jLabel21, jLabel23, jLabel24, jLabel27, jLabel3, jLabel30, jLabel32});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel18, jLabel19, jLabel6});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel7, jLabel8});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combotypetest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeoftest1)
                            .addComponent(txtpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addGap(6, 6, 6)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFN, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLN, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combogender, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combomaritalstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(combosampletype, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(txtsampleid, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel19))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txttechnicianname, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(resultsdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtguardian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combobloodgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combopatienttype, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtweight, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbodytemp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeoftest, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(testdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtdiagnosis, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(82, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_get_patient, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnViewall, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel8, txtdiagnosis});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {combosampletype, jLabel18, jLabel19, txtsampleid});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, txttechnicianname});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel7, resultsdate});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {combotypetest, typeoftest1});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {combobloodgroup, combogender, combomaritalstatus, combopatienttype, jLabel15, jLabel16, jLabel17, jLabel20, jLabel21, jLabel23, jLabel24, jLabel27, jLabel3, jLabel30, jLabel32, jtxtsearch, testdate, txtFN, txtLN, txtage, txtbodytemp, txtguardian, txtpatientid, txtweight, typeoftest});

        jTabbedPane1.addTab("Lab Results", jPanel1);

        jScrollPane2.setBackground(new java.awt.Color(153, 153, 255));
        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 255, 255), 10, true));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        lab_pattable.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lab_pattable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "Title 18", "Title 19"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lab_pattable.setGridColor(new java.awt.Color(0, 0, 0));
        lab_pattable.setRowHeight(25);
        lab_pattable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(lab_pattable);

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(btnrefresh1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnrefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lab Patients", jPanel5);

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
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jbtnchangepass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jbtnchangepass, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
        );

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 0));
        jLabel34.setText("KEEP YOUR USERID CONFIDENTIAL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(226, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Manage Account", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       Login_Laboratory HMS = new Login_Laboratory();
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
        LabEditAccount HMS = new LabEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        String user = lbluser.getText();
        LabEditAccount HMS = new LabEditAccount();
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
        LabEditAccount HMS = new LabEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jbtnchangepassActionPerformed

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        String user = lbluser.getText();
        LabEditAccount HMS = new LabEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel25MouseClicked

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        //FIELDS VALIDATION
        System.out.println("Lab Saving Record..... ");
        String Temp = txtbodytemp.getText();
        String Weight = txtweight.getText();
        String Age = txtage.getText();
        if (txtpatientid.getText().equals("") || txtFN.getText().equals("") || txtLN.getText().equals("") || txtguardian.getText().equals("") || txtweight.getText().equals("") || txtage.getText().equals("") || txtbodytemp.getText().equals("") || txtsampleid.getText().equals("") || txttechnicianname.getText().equals("") || txtdiagnosis.getText().equals("") || txtresults.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Fill All The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Gender",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combomaritalstatus.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Marital Status",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combobloodgroup.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Blood Group",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combopatienttype.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Patient Type",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (testdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Fill The Date Of Testing",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combotypetest.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Type of Test",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combosampletype.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Sample Type",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (resultsdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Result Date",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Weight.length() > 4) {
            JOptionPane.showMessageDialog(this,
                    "Please Insert a Valid Body Weight",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this,
                    "Please Insert a Valid Body Temprature",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Temp.length() > 2) {
            JOptionPane.showMessageDialog(null,
                    "Please insert a valid Age",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //CONVERSION TO STRING
            String Gender = combogender.getSelectedItem().toString();
            String Marital = combomaritalstatus.getSelectedItem().toString();
            String Blood = combobloodgroup.getSelectedItem().toString();
            String Patienttype = combopatienttype.getSelectedItem().toString();
            String Typetest = combotypetest.getSelectedItem().toString();
            String Sampletype = combosampletype.getSelectedItem().toString();
            String val1 = ((JTextField) testdate.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(testdate.getDate());
            String val2 = ((JTextField) resultsdate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(resultsdate.getDate());
            System.out.println("Fields Validation Complete....");
            //INSERT INTO DB
            try {
                String sql = "insert into lab_results (Patientid,Firstname,Lastname,Gender,Maritalstatus,Guardianname,Bloodgroup,Patienttype,Weight,Age,Bodytemp,Testdate,Type_test,Sampletype,Sampleid,Tech_name,Resultsdate,Diagnosis,Results)Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connection Established");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtpatientid.getText());
                pst.setString(2, txtFN.getText());
                pst.setString(3, txtLN.getText());
                pst.setString(4, Gender);
                pst.setString(5, Marital);
                pst.setString(6, txtguardian.getText());
                pst.setString(7, Blood);
                pst.setString(8, Patienttype);
                pst.setString(9, txtweight.getText());
                pst.setString(10, txtage.getText());
                pst.setString(11, txtbodytemp.getText());
                pst.setString(12, val1);
                pst.setString(13, Typetest);
                pst.setString(14, Sampletype);
                pst.setString(15, txtsampleid.getText());
                pst.setString(16, txttechnicianname.getText());
                pst.setString(17, val2);
                pst.setString(18, txtdiagnosis.getText());
                pst.setString(19, txtresults.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "DATA SAVED SUCCESSFULLY");
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
                txtbodytemp.setText("");
                testdate.setDate(null);
                combotypetest.setSelectedItem("Select");
                combosampletype.setSelectedItem("Select");
                txtsampleid.setText("");
                txttechnicianname.setText("");
                resultsdate.setDate(null);
                txtdiagnosis.setText("");
                txtresults.setText("");
                jtxtsearch.setText("");

                System.out.println("Data Saved Successfully.....");
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

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
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
        txtbodytemp.setText("");
        testdate.setDate(null);
        combotypetest.setSelectedItem("Select");
        combosampletype.setSelectedItem("Select");
        txtsampleid.setText("");
        txttechnicianname.setText("");
        resultsdate.setDate(null);
        txtdiagnosis.setText("");
        txtresults.setText("");
        jtxtsearch.setText("");
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void btnsaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsaveMouseEntered
        btnsave.setBackground(Color.gray);
    }//GEN-LAST:event_btnsaveMouseEntered

    private void btnsaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsaveMouseExited
        btnsave.setBackground(jPanel1.getBackground());
    }//GEN-LAST:event_btnsaveMouseExited

    private void btnupdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnupdateMouseEntered
        btnupdate.setBackground(Color.gray);
    }//GEN-LAST:event_btnupdateMouseEntered

    private void btnupdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnupdateMouseExited
        btnupdate.setBackground(jPanel1.getBackground());
    }//GEN-LAST:event_btnupdateMouseExited

    private void btnrefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshMouseEntered
        btnrefresh.setBackground(Color.gray);
    }//GEN-LAST:event_btnrefreshMouseEntered

    private void btnrefreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnrefreshMouseExited
        btnrefresh.setBackground(jPanel1.getBackground());
    }//GEN-LAST:event_btnrefreshMouseExited

    private void btn_get_patientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_get_patientMouseEntered
        btn_get_patient.setBackground(Color.gray);
    }//GEN-LAST:event_btn_get_patientMouseEntered

    private void btn_get_patientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_get_patientMouseExited
        btn_get_patient.setBackground(jPanel1.getBackground());
    }//GEN-LAST:event_btn_get_patientMouseExited

    private void btnViewallMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewallMouseEntered
        btnViewall.setBackground(Color.gray);
    }//GEN-LAST:event_btnViewallMouseEntered

    private void btnViewallMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewallMouseExited
        btnViewall.setBackground(jPanel1.getBackground());
    }//GEN-LAST:event_btnViewallMouseExited

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

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you Sure to Update?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Update Dialog\n" + dtf.format(now));
        String Temp = txtbodytemp.getText();
        String Weight = txtweight.getText();
        String Age = txtage.getText();
        if (txtpatientid.getText().equals("") || txtFN.getText().equals("") || txtLN.getText().equals("") || txtguardian.getText().equals("") || txtweight.getText().equals("") || txtage.getText().equals("") || txtbodytemp.getText().equals("") || txtsampleid.getText().equals("") || txttechnicianname.getText().equals("") || txtdiagnosis.getText().equals("") || txtresults.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Fill All The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Gender",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combomaritalstatus.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Marital Status",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combobloodgroup.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Blood Group",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combopatienttype.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Patient Type",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (testdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Fill The Date Of Testing",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combotypetest.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Type of Test",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combosampletype.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Sample Type",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (resultsdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Result Date",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Weight.length() > 4) {
            JOptionPane.showMessageDialog(this,
                    "Please Insert a Valid Body Weight",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this,
                    "Please Insert a Valid Body Temprature",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Temp.length() > 3) {
            JOptionPane.showMessageDialog(this,
                    "Please insert a valid Age",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (p == 0) {

            //CONVERSION TO STRING
            String Gender = combogender.getSelectedItem().toString();
            String Marital = combomaritalstatus.getSelectedItem().toString();
            String Blood = combobloodgroup.getSelectedItem().toString();
            String Patienttype = combopatienttype.getSelectedItem().toString();
            String Typetest = combotypetest.getSelectedItem().toString();
            String Sampletype = combosampletype.getSelectedItem().toString();
            String val1 = ((JTextField) testdate.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(testdate.getDate());
            String val2 = ((JTextField) resultsdate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(resultsdate.getDate());
            System.out.println("Fields Validation Complete\n" + dtf.format(now));
            //INSERT INTO DB
            try {
                String sql = "update lab_results set Firstname='" + txtFN.getText() + "',Lastname='" + txtLN.getText() + "',Gender='" + Gender + "',Maritalstatus='" + Marital + "',Guardianname='" + txtguardian.getText() + "',Bloodgroup='" + Blood + "',Patienttype='" + Patienttype + "',Weight='" + txtweight.getText() + "',Age='" + txtage.getText() + "',Bodytemp='" + txtbodytemp.getText() + "',Testdate='" + val1 + "',Type_test='" + Typetest + "',Sampletype='" + Sampletype + "',Sampleid='" + txtsampleid.getText() + "',Tech_name='" + txttechnicianname.getText() + "',Resultsdate='" + val2 + "',Diagnosis='" + txtdiagnosis.getText() + "',Results='" + txtresults.getText() + "' where Patientid='" + txtpatientid.getText() + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to DB\n" + dtf.format(now));
                pst = con.prepareStatement(sql);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                System.out.println("Record successfully updated\n" + dtf.format(now));
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
                txtbodytemp.setText("");
                testdate.setDate(null);
                combotypetest.setSelectedItem("Select");
                combosampletype.setSelectedItem("Select");
                txtsampleid.setText("");
                txttechnicianname.setText("");
                resultsdate.setDate(null);
                txtdiagnosis.setText("");
                txtresults.setText("");
                jtxtsearch.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems\n" + dtf.format(now));
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed\n" + dtf.format(now));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnViewallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewallActionPerformed
        this.Searching();
    }//GEN-LAST:event_btnViewallActionPerformed

    private void btn_get_patientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_get_patientActionPerformed
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        if (jtxtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from pat_nurse where Patientid=?";
            System.out.println("Connecting To DB");
            pst = con.prepareStatement(sql);
            pst.setString(1, jtxtsearch.getText());
            rs = pst.executeQuery();
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
                String add11 = rs.getString("Body_temp");
                txtbodytemp.setText(add11);
                String add14 = rs.getString("Samp_type");
                combosampletype.setSelectedItem(add14);
                String add15 = rs.getString("Disease");
                txtdiagnosis.setText(add15);
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
    }//GEN-LAST:event_btn_get_patientActionPerformed

    private void btnrefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefresh1ActionPerformed
        view_table();
    }//GEN-LAST:event_btnrefresh1ActionPerformed

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
            java.util.logging.Logger.getLogger(Laboratory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laboratory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laboratory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laboratory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laboratory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnViewall;
    private javax.swing.JButton btn_get_patient;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton btnrefresh1;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.JButton btnuploadimage;
    private javax.swing.JComboBox<String> combobloodgroup;
    private javax.swing.JComboBox<String> combogender;
    private javax.swing.JComboBox<String> combomaritalstatus;
    private javax.swing.JComboBox<String> combopatienttype;
    private javax.swing.JComboBox<String> combosampletype;
    private javax.swing.JComboBox<String> combotypetest;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnchangepass;
    private javax.swing.JTextField jtxtsearch;
    private javax.swing.JTable lab_pattable;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lbluser;
    private com.toedter.calendar.JDateChooser resultsdate;
    private com.toedter.calendar.JDateChooser testdate;
    private javax.swing.JTextField txtFN;
    private javax.swing.JTextField txtLN;
    private javax.swing.JTextField txtage;
    private javax.swing.JTextField txtbodytemp;
    private javax.swing.JTextField txtdiagnosis;
    private javax.swing.JTextField txtguardian;
    private javax.swing.JTextField txtpatientid;
    private javax.swing.JTextArea txtresults;
    private javax.swing.JTextField txtsampleid;
    private javax.swing.JTextField txttechnicianname;
    private javax.swing.JTextField txtweight;
    private javax.swing.JLabel typeoftest;
    private javax.swing.JLabel typeoftest1;
    // End of variables declaration//GEN-END:variables
}
