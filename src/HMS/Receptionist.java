package HMS;

import javax.swing.*;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Allein
 */
public class Receptionist extends javax.swing.JFrame {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String Imagename = null;
    byte[] uimage = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public Receptionist() {
        initComponents();
        System.out.println("Application Started\n" + dtf.format(now));
        System.out.println("Initializing IDS....");
        autoId();
        AppointautoId();
        view_table();

    }

    public void view_table() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from pat_reg";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            pat_regtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            pat_regtable.setModel(DbUtils.resultSetToTableModel(rs));
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

    private Icon ResizeImage(String imagePath) {
        int imageX = 400;
        int imageY = 283;
        lblimage.setSize(imageX, imageY);
        ImageIcon myImage = new ImageIcon(imagePath);
        Image img = myImage.getImage();
        Image newImage = img.getScaledInstance(lblimage.getWidth(), lblimage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);

        return image;
    }

    public void Searching() {
        if (jtxtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_reg where Patientid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, jtxtsearch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patientid");
                    jtxtpatientid.setText(add1);
                    String add2 = rs.getString("Firstname");
                    jtxtFN.setText(add2);
                    String add3 = rs.getString("Lastname");
                    jtxtLN.setText(add3);
                    String add4 = rs.getString("Gender");
                    jcombogender.setSelectedItem(add4);
                    String add5 = rs.getString("Phoneno");
                    jtxtphoneno.setText(add5);
                    String add6 = rs.getString("Email");
                    txtemail.setText(add6);
                    String add7 = rs.getString("MaritalStatus");
                    jcombomaritalstatus.setSelectedItem(add7);
                    String add8 = rs.getString("Guardian");
                    jtxtguardian.setText(add8);
                    String add9 = rs.getString("County");
                    combocounty.setSelectedItem(add9);
                    String add10 = rs.getString("Subcounty");
                    cmbsubcounty.setSelectedItem(add10);
                    String add11 = rs.getString("Address");
                    txtaddress.setText(add11);
                    String add12 = rs.getString("Bloodgroup");
                    jcombobloodgroup.setSelectedItem(add12);
                    String add13 = rs.getString("Patienttype");
                    jcombopatienttype.setSelectedItem(add13);
                    String add14 = rs.getString("Weight");
                    jtxtweight.setText(add14);
                    String add15 = rs.getString("Age");
                    jtxtage.setText(add15);
                    String ad = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                    Regdate.setDate(dat);
                    String add17 = rs.getString("Medhist");
                    jTxtareamedhist.setText(add17);
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

    public void autoId() {
        try {
            String sql = "SELECT Patientid FROM pat_reg ORDER BY Patientid DESC LIMIT 1";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            System.out.println("Connecting to DB....");
            System.out.println("Selecting Patient id....");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String rnno = rs.getString("Patientid");
                System.out.println("Patientid....");
                int co = rnno.length();
                String txt = rnno.substring(0, 2);
                String num = rnno.substring(2, co);
                int n = Integer.parseInt(num);
                n++;
                String snum = Integer.toString(n);
                String ftxt = txt + snum;
                jtxtpatientid.setText(ftxt);
            } else {
                jtxtpatientid.setText("PT1000");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        } finally {
            try {
                con.close();
                System.out.println("Connection Closed");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    public void AppointautoId() {
        try {
            String sql = "SELECT Appointmentid FROM appointments ORDER BY Appointmentid DESC LIMIT 1";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            System.out.println("Connecting to DB....");
            System.out.println("Selecting Appointment id....");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String rnno = rs.getString("Appointmentid");
                System.out.println("Appointment id....");
                int co = rnno.length();
                String txt = rnno.substring(0, 2);
                String num = rnno.substring(2, co);
                int n = Integer.parseInt(num);
                n++;
                String snum = Integer.toString(n);
                String ftxt = txt + snum;
                txtAppointmentId.setText(ftxt);
            } else {
                txtAppointmentId.setText("AP1000");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        } finally {
            try {
                con.close();
                System.out.println("Connection Closed");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void printing_patreceipt() {
        if (jtxtpatientid.getText().isEmpty()
                || jtxtFN.getText().isEmpty()
                || jtxtLN.getText().isEmpty()
                || jcombogender.getSelectedItem().equals("Select")
                || jtxtphoneno.getText().isEmpty()
                || jcombomaritalstatus.getSelectedItem().equals("Select")
                || jtxtguardian.getText().isEmpty()
                || combocounty.getSelectedItem().equals("Select")
                || cmbsubcounty.getSelectedItem().equals("Select")
                || txtaddress.getText().isEmpty()
                || jcombobloodgroup.getSelectedItem().equals("Select")
                || jcombopatienttype.getSelectedItem().equals("Select")
                || jtxtweight.getText().isEmpty()
                || jtxtage.getText().isEmpty()
                || Regdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Regdate.getDate());
            String Qty1 = (jtxtpatientid.getText());
            String Qty2 = (jtxtFN.getText());
            String Qty3 = (jtxtLN.getText());
            String Qty4 = (jcombogender.getSelectedItem().toString());
            String Qty5 = (jtxtphoneno.getText());
            String Qty6 = (txtemail.getText());
            String Qty7 = (jcombomaritalstatus.getSelectedItem().toString());
            String Qty8 = (jtxtguardian.getText());
            String Qty9 = (combocounty.getSelectedItem().toString());
            String Qty10 = (cmbsubcounty.getSelectedItem().toString());
            String Qty11 = (txtaddress.getText());
            String Qty12 = (jcombobloodgroup.getSelectedItem().toString());
            String Qty13 = (jcombopatienttype.getSelectedItem().toString());
            String Qty14 = (jtxtweight.getText());
            String Qty15 = (jtxtage.getText());
            String Qty16 = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
            String Qty17 = (jTxtareamedhist.getText());
            pat_reg_txtarea.setText("");
            pat_reg_txtarea.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tPatient Receipt\n"
                    + "\nPatient ID:\t\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nGender:\t\t" + Qty4
                    + "\nPhone Number:\t" + Qty5
                    + "\nEmail:\t\t" + Qty6
                    + "\nMarital Status:\t\t" + Qty7
                    + "\nGuardian Name:\t" + Qty8
                    + "\nCounty:\t\t" + Qty9
                    + "\nSub County:\t\t" + Qty10
                    + "\nAddress:\t\t" + Qty11
                    + "\nBlood Group:\t\t" + Qty12
                    + "\nPatient Type:\t\t" + Qty13
                    + "\nWeight:\t\t" + Qty14
                    + "\nAge:\t\t" + Qty15
                    + "\nRegistration Date:\t" + Qty16
                    + "\nMedical History:\t\n" + Qty17
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                    + "\n\tWE WISH YOU A QUICK RECOVERY");
            try {
                pat_reg_txtarea.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            autoId();
            jtxtsearch.setText("");
            jtxtFN.setText("");
            jtxtLN.setText("");
            jcombogender.setSelectedItem("Select");
            jtxtage.setText("");
            jcombomaritalstatus.setSelectedItem("Select");
            jtxtguardian.setText("");
            combocounty.setSelectedItem("Select");
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Select");
            txtaddress.setText("");
            jtxtphoneno.setText("");
            txtemail.setText("");
            jcombobloodgroup.setSelectedItem("Select");
            jcombopatienttype.setSelectedItem("Select");
            jtxtweight.setText("");
            Regdate.setDate(null);
            jTxtareamedhist.setText("");
        }
    }

    public void app_patrecipt() {
        if (txtAppointmentId.getText().isEmpty()
                || txtFirstName.getText().isEmpty()
                || txtLastName.getText().isEmpty()
                || txtAge.getText().isEmpty()
                || txtGuardianname.getText().isEmpty()
                || jcmbgender.getSelectedItem().equals("Select")
                || txtAddress.getText().isEmpty()
                || txtappemail.getText().isEmpty() || txtAddress.getText().isEmpty()
                || txtPhoneNo.getText().isEmpty()
                || Regdate1.getDate() == null
                || combotime.getSelectedItem().equals("Time")
                || cmbservice.getSelectedItem().equals("Select")
                || jComboBoxDepartment.getSelectedItem().equals("Select")
                || combo_doctor.getSelectedItem().equals("Select")
                || combopaid.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Regdate1.getDate());
            String Qty1 = (txtAppointmentId.getText());
            String Qty2 = (txtFirstName.getText());
            String Qty3 = (txtLastName.getText());
            String Qty4 = (txtAge.getText());
            String Qty5 = (txtGuardianname.getText());
            String Qty6 = (jcmbgender.getSelectedItem().toString());
            String Qty7 = (combotime.getSelectedItem().toString());
            String Qty8 = (txtAddress.getText());
            String Qty9 = (txtappemail.getText());
            String Qty10 = (txtPhoneNo.getText());
            String Qty11 = ((JTextField) Regdate1.getDateEditor().getUiComponent()).getText();
            String Qty12 = (cmbservice.getSelectedItem().toString());
            String Qty13 = (jComboBoxDepartment.getSelectedItem().toString());
            String Qty14 = (combo_doctor.getSelectedItem().toString());
            String Qty15 = (txtservicefee.getText());
            String Qty16 = (combopaid.getSelectedItem().toString());
            app_receipt.setText("");
            app_receipt.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tAppointment Receipt\n"
                    + "\nAppointment ID:\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nAge:\t\t" + Qty4
                    + "\nGuardian Name:\t" + Qty5
                    + "\nGender:\t\t" + Qty6
                    + "\nAppointment Time:\t" + Qty7
                    + "\nAddress:\t\t" + Qty8
                    + "\nEmail:\t\t" + Qty9
                    + "\nPhone Number:\t" + Qty10
                    + "\nAppointment Date:\t" + Qty11
                    + "\nService:\t\t" + Qty12
                    + "\nDepartment:\t\t" + Qty13
                    + "\nDoctor:\t\t" + Qty14
                    + "\nAppointment Fee:\t" + Qty15
                    + "\nPaid For Appointment:\t" + Qty16
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                    + "\n\tWE WISH YOU A QUICK RECOVERY");
            try {
                app_receipt.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            this.AppointautoId();
            appsearch.setText("");
            txtFirstName.setText("");
            txtLastName.setText("");
            txtAge.setText("");
            txtGuardianname.setText("");
            jcmbgender.setSelectedItem("Select");
            combotime.setSelectedItem("Time");
            txtAddress.setText("");
            txtappemail.setText("");
            txtPhoneNo.setText("");
            Regdate1.setDate(null);
            cmbservice.setSelectedItem("Select");
            jComboBoxDepartment.setSelectedItem("Select");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Select");
            txtservicefee.setText("");
            combopaid.setSelectedItem("Select");
        }
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
        jLabel37 = new javax.swing.JLabel();
        lbluser = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        pat_reg_txtarea = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtxtpatientid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtFN = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtLN = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtguardian = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtxtage = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jcombomaritalstatus = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jtxtsearch = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jcombobloodgroup = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jcombopatienttype = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jtxtweight = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTxtareamedhist = new javax.swing.JTextArea();
        jtxtphoneno = new javax.swing.JTextField();
        jcombogender = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jbtnsave1 = new javax.swing.JButton();
        jbtnedit = new javax.swing.JButton();
        jbtnrefreshregpat = new javax.swing.JButton();
        btnprint = new javax.swing.JButton();
        jbtnsearch3 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        combocounty = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        txtaddress = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        cmbsubcounty = new javax.swing.JComboBox<>();
        Regdate = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtAppointmentId = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        txtGuardianname = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jComboBoxDepartment = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        txtPhoneNo = new javax.swing.JTextField();
        jcmbgender = new javax.swing.JComboBox<>();
        combotime = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel12 = new javax.swing.JPanel();
        btnsaveappointment = new javax.swing.JButton();
        btnappedit = new javax.swing.JButton();
        jbtnrefreshapp = new javax.swing.JButton();
        btnapp_search = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        btnapp_print = new javax.swing.JButton();
        Regdate1 = new com.toedter.calendar.JDateChooser();
        jLabel29 = new javax.swing.JLabel();
        appsearch = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        cmbservice = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtservicefee = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtappemail = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        combopaid = new javax.swing.JComboBox<>();
        combo_doctor = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        app_receipt = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        Scrollpane = new javax.swing.JScrollPane();
        pat_regtable = new javax.swing.JTable();
        jbtnrefreshregpat1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jbtnchangepass = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblimage = new javax.swing.JLabel();
        btnuploadimage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximizedBounds(new java.awt.Rectangle(1230, 780, 1230, 780));
        setMaximumSize(new java.awt.Dimension(1230, 780));
        setPreferredSize(new java.awt.Dimension(1230, 780));
        setResizable(false);
        setSize(new java.awt.Dimension(1230, 780));

        jPanel1.setBackground(new java.awt.Color(0, 51, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/receptionist.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Receptionist");

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

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel37.setText("Welcome");

        lbluser.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jScrollPane4.setBackground(new java.awt.Color(0, 51, 255));
        jScrollPane4.setForeground(new java.awt.Color(0, 51, 255));

        pat_reg_txtarea.setColumns(20);
        pat_reg_txtarea.setRows(5);
        jScrollPane4.setViewportView(pat_reg_txtarea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbluser, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbluser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 51, 0));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Patient ID");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtxtpatientid.setEditable(false);
        jtxtpatientid.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jtxtpatientid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtpatientidActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("First Name");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtxtFN.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Last Name");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtxtLN.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Gender");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtxtguardian.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Guardians Name");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtxtage.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Age");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Marital Status");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jcombomaritalstatus.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jcombomaritalstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Married", "Single", "Divorced", "Widowed" }));
        jcombomaritalstatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel17.setText("Search ");

        jtxtsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setText("Blood Group");
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jcombobloodgroup.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jcombobloodgroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "AB", "O", "A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+", "Unknown" }));
        jcombobloodgroup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setText("Patient Type");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jcombopatienttype.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jcombopatienttype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Inpatient", "Outpatient", "Pending" }));
        jcombopatienttype.setToolTipText("");
        jcombopatienttype.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setText("Weight(Kgs)");
        jLabel20.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jtxtweight.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setText("Phone No");
        jLabel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setText("Reg Date");
        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setText("Med. Hist");
        jLabel23.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jTxtareamedhist.setColumns(10);
        jTxtareamedhist.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTxtareamedhist.setLineWrap(true);
        jTxtareamedhist.setRows(5);
        jScrollPane2.setViewportView(jTxtareamedhist);

        jtxtphoneno.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jcombogender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jcombogender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setOpaque(true);
        jSeparator1.setPreferredSize(new java.awt.Dimension(80, 10));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel27.setText("Email");
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtemail.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jbtnsave1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtnsave1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        jbtnsave1.setText("SAVE");
        jbtnsave1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbtnsave1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnsave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnsave1ActionPerformed(evt);
            }
        });

        jbtnedit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtnedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_50px.png"))); // NOI18N
        jbtnedit.setText("UPDATE");
        jbtnedit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbtnedit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtneditActionPerformed(evt);
            }
        });

        jbtnrefreshregpat.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtnrefreshregpat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        jbtnrefreshregpat.setText("REFRESH");
        jbtnrefreshregpat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbtnrefreshregpat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnrefreshregpat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnrefreshregpatActionPerformed(evt);
            }
        });

        btnprint.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnprint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_print_32px.png"))); // NOI18N
        btnprint.setText("PRINT");
        btnprint.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnprint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintActionPerformed(evt);
            }
        });

        jbtnsearch3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtnsearch3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jbtnsearch3.setText("SEARCH");
        jbtnsearch3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbtnsearch3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnsearch3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnsearch3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnsave1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnedit, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jbtnrefreshregpat, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(jbtnsearch3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnprint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnsave1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnrefreshregpat, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnsearch3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnprint, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel28.setText("County");
        jLabel28.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setText("Sub County");
        jLabel38.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combocounty.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combocounty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Baringo", "Bomet", "Bungoma", "Busia", "Elgeyo-Marakwet", "Embu", "Garissa", "Homa Bay", "Isiolo", "Kajiado", "Kakamega", "Kericho", "Kiambu", "Kilifi", "Kirinyaga", "Kisii", "Kisumu", "Kitui", "Kwale", "Laikipia", "Lamu", "Machakos\t", "Makueni", "Mandera", "Marsabit", "Meru", "Migori", "Mombasa", "Murang'a", "Nairobi", "Nakuru", "Nandi", "Narok", "Nyamira", "Nyandarua", "Nyeri", "Samburu", "Siaya", "Taita–Taveta", "Tana River", "Tharaka-Nithi", "Trans-Nzoia", "Turkana", "Uasin Gishu", "Vihiga", "Wajir", "West Pokot" }));
        combocounty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocountyActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel39.setText("Address");
        jLabel39.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtaddress.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setText("Optional");

        cmbsubcounty.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbsubcounty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1.Changamwe ", "2.Jomvu ", "3.Kisauni ", "4.Nyali ", "5.Likoni ", "6.Mvita ", "7. Msambweni ", "8. Lunga Lunga ", "9. Matuga ", "10. Kinango ", "11. Kilifi North ", "12. Kilifi South ", "13. Kaloleni ", "14. Rabai ", "15. Ganze ", "16. Malindi ", "17. Magarini ", "18. Garsen ", "19. Galole ", "20. Bura ", "21. Lamu East ", "22. Lamu West ", "23. Taveta ", "24. Wundanyi ", "25. Mwatate ", "26. Voi ", "27. Garissa Township ", "28. Balambala ", "29. Lagdera ", "30. Dadaab ", "31. Fafi ", "32. Ijara ", "33. Wajir North ", "34. Wajir East ", "35. Tarbaj ", "36. Wajir West ", "37. Eldas ", "38. Wajir South ", "39. Mandera West ", "40. Banissa ", "41. Mandera North ", "42. Mandera South ", "43. Mandera East", "44. Lafey ", "45. Moyale ", "46. North Horr ", "47. Saku ", "48. Laisamis ", "49. Isiolo North ", "50. Isiolo South ", "51. Igembe South ", "52. Igembe Central ", "53. Igembe North ", "54. Tigania West ", "55. Tigania East ", "56. North Imenti ", "57. Buuri ", "58. Central Imenti ", "59. South Imenti ", "60. Maara ", "61. Chuka ", "62. Tharaka ", "63. Manyatta ", "64. Runyenjes ", "65. Mbeere South ", "66. Mbeere North ", "67. Mwingi North ", "68. Mwingi West ", "69. Mwingi Central ", "70. Kitui West ", "71. Kitui Rural ", "72. Kitui Central", "73. Kitui East ", "74. Kitui South ", "75. Masinga ", "76. Yatta ", "77. Kangundo ", "78. Matungulu ", "79. Kathiani ", "80. Mavoko ", "81. Machakos Town ", "82. Mwala ", "83. Mbooni ", "84. Kilome ", "85. Kaiti ", "86. Makueni ", "87. Kibwezi West ", "88. Kibwezi East", "89. Kinangop ", "90. Kipipiri ", "91. Ol Kalou ", "92. Ol Jorok ", "93. Ndaragwa ", "94. Tetu ", "95. Kieni ", "96. Mathira ", "97. Othaya ", "98. Mukurweini ", "99. Nyeri Town ", "100. Mwea ", "101. Gichugu ", "102. Ndia ", "103. Kirinyaga Central ", "104. Kangema ", "105. Mathioya ", "106. Kiharu ", "107. Kigumo ", "108. Maragwa ", "109. Kandara ", "110. Gatanga", "111. Gatundu South ", "112. Gatundu North ", "113. Juja ", "114. Thika Town ", "115. Ruiru ", "116. Githunguri ", "117. Kiambu ", "118. Kiambaa", "119. Kabete ", "120. Kikuyu ", "121. Limuru ", "122. Lari ", "123. Turkana North ", "124. Turkana West ", "125. Turkana Central ", "126. Loima ", "127. Turkana South ", "128. Turkana East ", "129. Kapenguria ", "130. Sigor ", "131. Kacheliba ", "132. Pokot South ", "133. Samburu ", "134. Samburu North ", "135. Samburu East ", "136. Kwanza ", "137. Endebess ", "138. Saboti ", "139. Kiminini ", "140. Cherang’any ", "141. Soy Constituency ", "142. Turbo ", "143. Moiben ", "144. Ainabkio ", "145. Kapseret ", "146. Kesses ", "147. Marakwet East ", "148. Marakwet West ", "149. Keiyo North ", "150. Keiyo South ", "151. Tinderet ", "152. Aldai ", "153. Nandi Hills ", "154. Chesumei ", "155. Emgwen ", "156. Mosop ", "157. Tiaty ", "158. Baringo North ", "159. Baringo Central", "160. Baringo South ", "161. Mogotio ", "162. Eldama Ravine", "163. Laikipia West ", "164. Laikipia East", "165. Laikipia North ", "166. Molo ", "167. Njoro ", "168. Naivasha ", "169. Gilgil ", "170. Kuresoi South", "171. Kuresoi North ", "172. Subukia ", "173. Rongai ", "174. Bahati ", "175. Nakuru Town West", "176. Nakuru Town East", "177. Kilgoris ", "178. Emurua Dikirr ", "179. Narok North ", "180. Narok East ", "181. Narok South ", "182. Narok West ", "183. Kajiado North ", "184. Kajiado Central ", "185. Kajiado East ", "186. Kajiado West ", "187. Kajiado South", "188. Kipkelion East ", "189. Kipkelion West ", "190. Ainamoi ", "191. Bureti ", "192. Belgut ", "193. Sigowet-Soin ", "194. Sotik ", "195. Chepalungu ", "196. Bomet East ", "197. Bomet Central ", "198. Konoin ", "199. Lugari ", "200. Likuyani ", "201. Malava ", "202. Lurambi ", "203. Navakholo ", "204. Mumias West ", "205. Mumias East ", "206. Matungu ", "207. Butere ", "208. Khwisero ", "209. Shinyalu ", "210. Ikolomani ", "211. Vihiga ", "212. Sabatia ", "213. Hamisi ", "214. Luanda ", "215. Emuhaya ", "216. Mount Elgon ", "217. Sirisia ", "218. Kabuchai ", "219. Bumula ", "220 Kanduyi ", "221. Webuye East ", "222. Webuye West ", "223. Kimilili ", "224. Tongaren ", "225. Teso North ", "226. Teso South ", "227. Nambale ", "228. Matayos ", "229. Butula ", "230. Funyula ", "231. Budalangi ", "232. Ugenya ", "233. Ugunja ", "234. Alego Usonga ", "235. Gem ", "236. Bondo ", "237. Rarieda ", "238. Kisumu East ", "239. Kisumu West ", "240. Kisumu Central ", "241. Seme ", "242. Nyando ", "243. Muhoroni ", "244. Nyakach", "245. Kasipul ", "246. Kabondo Kasipul ", "247. Karachuonyo ", "248. Rangwe ", "249. Homa Bay Town ", "250. Ndhiwa ", "251. Mbita ", "252. Suba ", "253. Rongo ", "254. Awendo ", "255. Suna East ", "256. Suna West ", "257. Uriri ", "258. Nyatike ", "259. Kuria West ", "260. Kuria East ", "261. Bonchari ", "262. South Mugirango ", "263. Bomachoge Borabu ", "264. Bobasi ", "265. Bomachoge Chache ", "266. Nyaribari Masaba ", "267. Nyaribari Chache ", "268. Kitutu Chache North ", "269. Kitutu Chache South ", "270. Kitutu Masaba ", "271. West Mugirango ", "272. North Mugirango", "273. Borabu ", "274. Westlands ", "275. Dogoretti North ", "276. Dogoretti South ", "277. Lang’ata ", "278. Kibra ", "279. Roysambu ", "280. Kasarani ", "281. Ruaraka ", "282. Embakasi South ", "283. Embakasi North ", "284. Embakasi Central ", "285. Embakasi East", "286. Embakasi West ", "287. Makadara ", "288. Kamukunji ", "289. Starehe ", "290. Mathare" }));
        cmbsubcounty.setAutoscrolls(true);

        Regdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regdate.setDateFormatString("yyyy-MM-dd");
        Regdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combocounty, 0, 0, Short.MAX_VALUE)
                                    .addComponent(txtaddress)
                                    .addComponent(cmbsubcounty, 0, 1, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jtxtpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtFN, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jtxtguardian))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jcombogender, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jtxtLN, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                                                .addComponent(jcombomaritalstatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(1, 1, 1)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtphoneno, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))))
                                .addGap(8, 8, 8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Regdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtweight)
                                    .addComponent(jcombopatienttype, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcombobloodgroup, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jtxtage, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcombogender, jcombomaritalstatus, jtxtFN, jtxtLN, jtxtguardian, jtxtpatientid, jtxtphoneno, txtemail});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel18, jLabel19, jLabel20});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jtxtpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jtxtFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel19)))
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jtxtLN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jcombogender, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcombobloodgroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcombopatienttype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtweight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtxtphoneno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22))
                            .addComponent(Regdate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtxtguardian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(combocounty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel38)
                                    .addComponent(cmbsubcounty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel39)
                                    .addComponent(txtaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcombomaritalstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 89, Short.MAX_VALUE))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Regdate, cmbsubcounty, combocounty, jcombobloodgroup, jcombomaritalstatus, jcombopatienttype, jtxtFN, jtxtLN, jtxtage, jtxtguardian, jtxtpatientid, jtxtphoneno, jtxtweight, txtaddress, txtemail});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel15, jLabel18, jLabel19, jLabel20, jLabel21, jLabel22, jLabel3, jLabel4, jLabel5, jLabel7, jLabel8});

        jTabbedPane1.addTab("Register Patient", jPanel2);

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("ADD APPOINTMENTS");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Appointmnet ID");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtAppointmentId.setEditable(false);
        txtAppointmentId.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtAppointmentId.setPreferredSize(new java.awt.Dimension(6, 38));

        txtFirstName.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("First Name");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtAge.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Age");
        jLabel13.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("Last Name");
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtLastName.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        txtGuardianname.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel24.setText("Guardian Name");
        jLabel24.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel30.setText("Service");
        jLabel30.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel31.setText("Gender");
        jLabel31.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel32.setText("Appointmen Time");
        jLabel32.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel33.setText("App Date");
        jLabel33.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel34.setText(" Address");
        jLabel34.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtAddress.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel35.setText("Assigned Doctor");
        jLabel35.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jComboBoxDepartment.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jComboBoxDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cardiology", "General", "Haematology", "Maternity", "Neurology", "Radiology" }));
        jComboBoxDepartment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxDepartmentMouseClicked(evt);
            }
        });
        jComboBoxDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDepartmentActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel36.setText("Phone No.");
        jLabel36.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtPhoneNo.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jcmbgender.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jcmbgender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        combotime.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combotime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Time", "08:00", "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00" }));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setDoubleBuffered(true);
        jSeparator2.setFocusCycleRoot(true);
        jSeparator2.setFocusTraversalPolicyProvider(true);
        jSeparator2.setFocusable(true);
        jSeparator2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jSeparator2.setOpaque(true);

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnsaveappointment.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnsaveappointment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btnsaveappointment.setText("SAVE");
        btnsaveappointment.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsaveappointment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsaveappointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveappointmentActionPerformed(evt);
            }
        });

        btnappedit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnappedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_50px.png"))); // NOI18N
        btnappedit.setText("UPDATE");
        btnappedit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnappedit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnappedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnappeditActionPerformed(evt);
            }
        });

        jbtnrefreshapp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtnrefreshapp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        jbtnrefreshapp.setText("REFRESH");
        jbtnrefreshapp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbtnrefreshapp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnrefreshapp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnrefreshappActionPerformed(evt);
            }
        });

        btnapp_search.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnapp_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btnapp_search.setText("SEARCH");
        btnapp_search.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnapp_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnapp_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnapp_searchActionPerformed(evt);
            }
        });

        delete.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_trash_32px.png"))); // NOI18N
        delete.setText("DELETE");
        delete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        btnapp_print.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnapp_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_print_32px.png"))); // NOI18N
        btnapp_print.setText("PRINT");
        btnapp_print.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnapp_print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnapp_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnapp_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnsaveappointment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnrefreshapp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnappedit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(btnapp_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnapp_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnsaveappointment, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(btnappedit)
                .addGap(16, 16, 16)
                .addComponent(jbtnrefreshapp)
                .addGap(16, 16, 16)
                .addComponent(btnapp_search)
                .addGap(16, 16, 16)
                .addComponent(delete)
                .addGap(16, 16, 16)
                .addComponent(btnapp_print)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnapp_print, btnapp_search, btnappedit, btnsaveappointment, delete, jbtnrefreshapp});

        Regdate1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regdate1.setDateFormatString("yyyy-MM-dd");
        Regdate1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel29.setText("Search ");

        appsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel42.setText("Department");
        jLabel42.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbservice.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbservice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Antenatal Care", "Blood Donation", "Blood Screening", "Checkup", "Chemotherapy", "Consultation", "DNA Test", "Medical Followup", "Nerve Training", "Postnatal Care", "Pregnancy Test", "Pressure Test", "Radiotherapy", "X-ray" }));
        cmbservice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbserviceActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Appointment Fee");
        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtservicefee.setEditable(false);
        txtservicefee.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtservicefee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtservicefeeMouseClicked(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setText("Email");
        jLabel41.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtappemail.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel43.setText("Paid");
        jLabel43.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combopaid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combopaid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "No", "Yes" }));

        combo_doctor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combo_doctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", " " }));
        combo_doctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_doctorMouseClicked(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 255));
        jScrollPane1.setForeground(new java.awt.Color(204, 204, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(2500, 113));

        app_receipt.setColumns(20);
        app_receipt.setRows(5);
        jScrollPane1.setViewportView(app_receipt);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(289, 289, 289)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(jLabel29)
                                .addGap(18, 18, 18)
                                .addComponent(appsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtappemail))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAddress))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combotime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGuardianname, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                    .addComponent(txtAge, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                    .addComponent(txtLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                    .addComponent(txtFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                    .addComponent(txtAppointmentId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Regdate1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                            .addComponent(txtservicefee)
                            .addComponent(combopaid, 0, 268, Short.MAX_VALUE)
                            .addComponent(jComboBoxDepartment, 0, 270, Short.MAX_VALUE)
                            .addComponent(cmbservice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_doctor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(17, Short.MAX_VALUE))))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {combotime, jcmbgender, txtAge, txtAppointmentId, txtFirstName, txtGuardianname, txtLastName});

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel30, jLabel33, jLabel35, jLabel42, jLabel43});

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Regdate1, cmbservice, combopaid, jComboBoxDepartment, txtservicefee});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(appsearch)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel24)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtAppointmentId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel33))
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel30))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGuardianname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel13)
                                                    .addComponent(jLabel35)
                                                    .addComponent(combo_doctor))
                                                .addGap(11, 11, 11)
                                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(4, 4, 4)))))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jcmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(Regdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(cmbservice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jComboBoxDepartment)
                                .addGap(58, 58, 58)
                                .addComponent(txtservicefee)
                                .addGap(11, 11, 11)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(combopaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combotime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addGap(4, 4, 4))
                            .addComponent(txtappemail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 65, Short.MAX_VALUE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Regdate1, cmbservice, combotime, jComboBoxDepartment, jLabel33, jcmbgender, txtAddress, txtAge, txtAppointmentId, txtFirstName, txtGuardianname, txtLastName, txtPhoneNo, txtappemail, txtservicefee});

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel36});

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {appsearch, jLabel13});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Schedule Appointment", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        Scrollpane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 255), 10, true));
        Scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        Scrollpane.setToolTipText("");
        Scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Scrollpane.setAutoscrolls(true);
        Scrollpane.setName("JscrollPat_reg"); // NOI18N

        pat_regtable.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        pat_regtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pat_regtable.setGridColor(new java.awt.Color(0, 0, 0));
        pat_regtable.setRowHeight(25);
        pat_regtable.getTableHeader().setReorderingAllowed(false);
        Scrollpane.setViewportView(pat_regtable);

        jbtnrefreshregpat1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtnrefreshregpat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        jbtnrefreshregpat1.setText("REFRESH");
        jbtnrefreshregpat1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbtnrefreshregpat1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnrefreshregpat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnrefreshregpat1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jbtnrefreshregpat1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1064, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(Scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnrefreshregpat1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Patient Information", jPanel5);

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
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jbtnchangepass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jbtnchangepass, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
        );

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 0, 0));
        jLabel16.setText("KEEP YOUR USERID CONFIDENTIAL");

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
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(lblimage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(217, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel7, jPanel8});

        jTabbedPane1.addTab("Manage Account", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1232, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        Login_Receptionist HMS = new Login_Receptionist();
        HMS.setVisible(true);
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

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        String user = lbluser.getText();
        RecepEditAccount HMS = new RecepEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String user = lbluser.getText();
        RecepEditAccount HMS = new RecepEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        jButton3.setBackground(Color.white);
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        jButton3.setBackground(Color.gray);
    }//GEN-LAST:event_jButton3MouseEntered

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        String user = lbluser.getText();
        RecepEditAccount HMS = new RecepEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jbtnchangepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnchangepassActionPerformed
        String user = lbluser.getText();
        RecepEditAccount HMS = new RecepEditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jbtnchangepassActionPerformed

    private void jbtnchangepassMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnchangepassMouseExited
        jbtnchangepass.setBackground(Color.white);
    }//GEN-LAST:event_jbtnchangepassMouseExited

    private void jbtnchangepassMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnchangepassMouseEntered
        jbtnchangepass.setBackground(Color.gray);
    }//GEN-LAST:event_jbtnchangepassMouseEntered

    private void cmbserviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbserviceActionPerformed
        if (cmbservice.getSelectedItem().equals("Select")) {
            jComboBoxDepartment.setSelectedItem("Select");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Select");
            txtservicefee.setText("");
        } else if (cmbservice.getSelectedItem().equals("Antenatal Care")) {
            jComboBoxDepartment.setSelectedItem("Maternity");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Mark Mark");
            combo_doctor.addItem("Dr John Mania");
            combo_doctor.addItem("Dr Peter Wafula");
            txtservicefee.setText("500");
        } else if (cmbservice.getSelectedItem().equals("Postnatal Care")) {
            jComboBoxDepartment.setSelectedItem("Maternity");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Mark Mark");
            combo_doctor.addItem("Dr John Mania");
            combo_doctor.addItem("Dr Peter Wafula");
            txtservicefee.setText("500");
        } else if (cmbservice.getSelectedItem().equals("Pregnancy Test")) {
            jComboBoxDepartment.setSelectedItem("Maternity");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Mark Mark");
            combo_doctor.addItem("Dr John Mania");
            combo_doctor.addItem("Dr Peter Wafula");
            txtservicefee.setText("500");
        } else if (cmbservice.getSelectedItem().equals("Blood Donation")) {
            jComboBoxDepartment.setSelectedItem("Haematology");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Ken Ken");
            combo_doctor.addItem("Dr Lucy Natasha");
            combo_doctor.addItem("Dr Morgan Wafula");
            txtservicefee.setText("600");
        } else if (cmbservice.getSelectedItem().equals("Blood Screening")) {
            jComboBoxDepartment.setSelectedItem("Haematology");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Ken Ken");
            combo_doctor.addItem("Dr Lucy Natasha");
            combo_doctor.addItem("Dr Morgan Wafula");
            txtservicefee.setText("600");
        } else if (cmbservice.getSelectedItem().equals("DNA TEst")) {
            jComboBoxDepartment.setSelectedItem("Haematology");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Ken Ken");
            combo_doctor.addItem("Dr Lucy Natasha");
            combo_doctor.addItem("Dr Morgan Wafula");
            txtservicefee.setText("600");
        } else if (cmbservice.getSelectedItem().equals("Checkup")) {
            jComboBoxDepartment.setSelectedItem("General");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Lutherford Main");
            combo_doctor.addItem("Dr David John");
            combo_doctor.addItem("Dr Mary Martha");
            txtservicefee.setText("0");
        } else if (cmbservice.getSelectedItem().equals("Consultation")) {
            jComboBoxDepartment.setSelectedItem("General");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Lutherford Main");
            combo_doctor.addItem("Dr David John");
            combo_doctor.addItem("Dr Mary Martha");
            txtservicefee.setText("0");
        } else if (cmbservice.getSelectedItem().equals("Medical Followup")) {
            jComboBoxDepartment.setSelectedItem("General");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Lutherford Main");
            combo_doctor.addItem("Dr David John");
            combo_doctor.addItem("Dr Mary Martha");
            txtservicefee.setText("0");
        } else if (cmbservice.getSelectedItem().equals("Radiotherapy")) {
            jComboBoxDepartment.setSelectedItem("Radiology");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Ali Mohamed");
            combo_doctor.addItem("Dr Mary Lucy");
            combo_doctor.addItem("Dr Allan Joe");
            txtservicefee.setText("800");
        } else if (cmbservice.getSelectedItem().equals("Chemotherapy")) {
            jComboBoxDepartment.setSelectedItem("Radiology");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Ali Mohamed");
            combo_doctor.addItem("Dr Mary Lucy");
            combo_doctor.addItem("Dr Allan Joe");
            txtservicefee.setText("800");
        } else if (cmbservice.getSelectedItem().equals("X-ray")) {
            jComboBoxDepartment.setSelectedItem("Radiology");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Ali Mohamed");
            combo_doctor.addItem("Dr Mary Lucy");
            combo_doctor.addItem("Dr Allan Joe");
            txtservicefee.setText("800");
        } else if (cmbservice.getSelectedItem().equals("Nerve Training")) {
            jComboBoxDepartment.setSelectedItem("Neurology");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Stewart");
            combo_doctor.addItem("Dr Kennedy");
            combo_doctor.addItem("Dr Luke Joe");
            txtservicefee.setText("800");
        } else if (cmbservice.getSelectedItem().equals("Pressure Test")) {
            jComboBoxDepartment.setSelectedItem("Cardiology");
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr VIncent");
            combo_doctor.addItem("Dr Allein Wapt");
            combo_doctor.addItem("Dr Patrick Mwene");
            txtservicefee.setText("800");
        }
    }//GEN-LAST:event_cmbserviceActionPerformed

    private void jbtnrefreshappActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnrefreshappActionPerformed
        this.AppointautoId();
        appsearch.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAge.setText("");
        txtGuardianname.setText("");
        jcmbgender.setSelectedItem("Select");
        combotime.setSelectedItem("Time");
        txtAddress.setText("");
        txtappemail.setText("");
        txtPhoneNo.setText("");
        Regdate1.setDate(null);
        cmbservice.setSelectedItem("Select");
        jComboBoxDepartment.setSelectedItem("Select");
        combo_doctor.removeAllItems();
        combo_doctor.addItem("Select");
        txtservicefee.setText("");
        combopaid.setSelectedItem("Select");
    }//GEN-LAST:event_jbtnrefreshappActionPerformed

    private void btnappeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnappeditActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "ARE YOU SURE YOU WANT TO UPDATE?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Appointment updating.....");
        if (p == 0) {
            EmailValidator emailValidator = new EmailValidator();
            String Age = txtAge.getText();
            String Phoneno2 = txtPhoneNo.getText();
            if (txtAppointmentId.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtappemail.getText().isEmpty() || txtAge.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhoneNo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Regdate1.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Select Appointment Date",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combotime.getSelectedItem().equals("Time")) {
                JOptionPane.showMessageDialog(this, "Please Select time",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!emailValidator.validate(txtappemail.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Invalid Email Address",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbservice.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Type of Service",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (jcmbgender.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select the Gender",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (jComboBoxDepartment.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Department",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combo_doctor.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Doctor",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combopaid.getSelectedItem().equals("Select") || combopaid.getSelectedItem().equals("No")) {
                JOptionPane.showMessageDialog(this, "Please This Service Is Payable",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Phoneno2.length() < 10 || Phoneno2.length() > 10) {
                JOptionPane.showMessageDialog(this,
                        "The Phone Number should only be 10 digits",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Age.length() > 3) {
                JOptionPane.showMessageDialog(this,
                        "Please insert a valid Age",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String Service = cmbservice.getSelectedItem().toString();
                String Time = combotime.getSelectedItem().toString();
                String Paid = combopaid.getSelectedItem().toString();
                String Gender = jcmbgender.getSelectedItem().toString();
                String Department = jComboBoxDepartment.getSelectedItem().toString();
                String Doctor = combo_doctor.getSelectedItem().toString();
                String val = ((JTextField) Regdate1.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(Regdate1.getDate());
                System.out.println("Fields validation complete");
                try {
                    String sql = "update appointments set Firstname='" + txtFirstName.getText() + "',Lastname='" + txtLastName.getText() + "',Age='" + txtAge.getText() + "',Guardianname='" + txtGuardianname.getText() + "',Gender='" + Gender + "',Time='" + Time + "',Address='" + txtAddress.getText() + "',Email='" + txtappemail.getText() + "',Phoneno='" + txtPhoneNo.getText() + "',Appdate='" + val + "',Service='" + Service + "',Department='" + Department + "',Assigndoc='" + Doctor + "',Servicefee='" + txtservicefee.getText() + "',Paid='" + Paid + "' where Appointmentid='" + txtAppointmentId.getText() + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    System.out.println("DB connecting");
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                    JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                    this.app_patrecipt();
                    this.AppointautoId();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("Update Problems...");
                } finally {
                    try {
                        con.close();
                        System.out.println("Connection closed");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnappeditActionPerformed

    private void btnsaveappointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveappointmentActionPerformed
        System.out.println("Appointment booking.....");
        EmailValidator emailValidator = new EmailValidator();
        String Age = txtAge.getText();
        String Phoneno2 = txtPhoneNo.getText();
        if (txtAppointmentId.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtappemail.getText().isEmpty() || txtAge.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhoneNo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Regdate1.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Appointment Date",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combotime.getSelectedItem().equals("Time")) {
            JOptionPane.showMessageDialog(this, "Please Select time",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!emailValidator.validate(txtappemail.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Invalid Email Address",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Type of Service",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jcmbgender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Gender",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jComboBoxDepartment.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Department",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combo_doctor.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Doctor",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combopaid.getSelectedItem().equals("Select") || combopaid.getSelectedItem().equals("No")) {
            JOptionPane.showMessageDialog(this, "Please This Service Is Payable",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jcmbgender.getSelectedItem().equals("Male") && cmbservice.getSelectedItem().equals("Antenatal Care") || jcmbgender.getSelectedItem().equals("Male") && cmbservice.getSelectedItem().equals("Postnatal Care") || jcmbgender.getSelectedItem().equals("Male") && cmbservice.getSelectedItem().equals("Pregnancy Test")) {
            JOptionPane.showMessageDialog(this, "The Selected Service is For Female Patients Only",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Phoneno2.length() < 10 || Phoneno2.length() > 10) {
            JOptionPane.showMessageDialog(this,
                    "The Phone Number should only be 10 digits",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this,
                    "Please insert a valid Age",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Service = cmbservice.getSelectedItem().toString();
            String Time = combotime.getSelectedItem().toString();
            String Paid = combopaid.getSelectedItem().toString();
            String Gender = jcmbgender.getSelectedItem().toString();
            String Department = jComboBoxDepartment.getSelectedItem().toString();
            String Doctor = combo_doctor.getSelectedItem().toString();
            String val = ((JTextField) Regdate1.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Regdate1.getDate());
            System.out.println("Fields validation complete");
            try {
                String sql = "insert into appointments (Appointmentid,Firstname,Lastname,Age,Guardianname,Gender,Time,Address,Email,Phoneno,Appdate,Service,Department,Assigndoc,Servicefee,Paid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to DB.....");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtAppointmentId.getText());
                pst.setString(2, txtFirstName.getText());
                pst.setString(3, txtLastName.getText());
                pst.setString(4, txtAge.getText());
                pst.setString(5, txtGuardianname.getText());
                pst.setString(6, Gender);
                pst.setString(7, Time);
                pst.setString(8, txtAddress.getText());
                pst.setString(9, txtappemail.getText());
                pst.setString(10, txtPhoneNo.getText());
                pst.setString(11, val);
                pst.setString(12, Service);
                pst.setString(13, Department);
                pst.setString(14, Doctor);
                pst.setString(15, txtservicefee.getText());
                pst.setString(16, Paid);
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.app_patrecipt();
                System.out.println("Appointment booked");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection to db problems");
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btnsaveappointmentActionPerformed

    private void jbtnsearch3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnsearch3ActionPerformed
        autoId();
        jtxtFN.setText("");
        jtxtLN.setText("");
        jcombogender.setSelectedItem("Select");
        jtxtage.setText("");
        jcombomaritalstatus.setSelectedItem("Select");
        jtxtguardian.setText("");
        combocounty.setSelectedItem("Select");
        cmbsubcounty.removeAllItems();
        cmbsubcounty.addItem("Select");
        txtaddress.setText("");
        jtxtphoneno.setText("");
        txtemail.setText("");
        jcombobloodgroup.setSelectedItem("Select");
        jcombopatienttype.setSelectedItem("Select");
        jtxtweight.setText("");
        Regdate.setDate(null);
        jTxtareamedhist.setText("");
        this.Searching();
    }//GEN-LAST:event_jbtnsearch3ActionPerformed

    private void jbtnrefreshregpatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnrefreshregpatActionPerformed
        autoId();
        jtxtsearch.setText("");
        jtxtFN.setText("");
        jtxtLN.setText("");
        jcombogender.setSelectedItem("Select");
        jtxtage.setText("");
        jcombomaritalstatus.setSelectedItem("Select");
        jtxtguardian.setText("");
        combocounty.setSelectedItem("Select");
        cmbsubcounty.removeAllItems();
        cmbsubcounty.addItem("Select");
        txtaddress.setText("");
        jtxtphoneno.setText("");
        txtemail.setText("");
        jcombobloodgroup.setSelectedItem("Select");
        jcombopatienttype.setSelectedItem("Select");
        jtxtweight.setText("");
        Regdate.setDate(null);
        jTxtareamedhist.setText("");
    }//GEN-LAST:event_jbtnrefreshregpatActionPerformed

    private void jbtneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtneditActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you Sure to Update?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Update Dialog................");
        if (p == 0) {
            EmailValidator emailValidator = new EmailValidator();
            String Phoneno = jtxtphoneno.getText();
            String Weight = jtxtweight.getText();
            String Age = jtxtage.getText();
            if (jtxtpatientid.getText().isEmpty() || jtxtFN.getText().isEmpty() || jtxtLN.getText().isEmpty() || jtxtage.getText().isEmpty() || jtxtguardian.getText().isEmpty() || jtxtphoneno.getText().isEmpty() || txtemail.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (jcombogender.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Gender",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!emailValidator.validate(txtemail.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Invalid Email Address",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (jcombomaritalstatus.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Marital Status",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combocounty.getSelectedItem().equals("Select") || cmbsubcounty.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select County or Sub County",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (jcombobloodgroup.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Blood Group",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (jcombopatienttype.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Patient Type",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Regdate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Select Registration Date",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Phoneno.length() < 10 || Phoneno.length() > 10) {
                JOptionPane.showMessageDialog(this,
                        "The Phone Number should only be 10 digits",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Weight.length() > 4) {
                JOptionPane.showMessageDialog(this,
                        "Please insert a valid weight",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Age.length() > 3) {
                JOptionPane.showMessageDialog(this,
                        "Please insert a valid Age",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //CONVERION TO STRING
                String Gender = jcombogender.getSelectedItem().toString();
                String Maritalstatus = jcombomaritalstatus.getSelectedItem().toString();
                String County = combocounty.getSelectedItem().toString();
                String Subcounty = cmbsubcounty.getSelectedItem().toString();
                String Bloodgroup = jcombobloodgroup.getSelectedItem().toString();
                String Patienttype = jcombopatienttype.getSelectedItem().toString();
                String val = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(Regdate.getDate());
                System.out.println("Fields Validation Complete");
                try {

                    String sql = "update pat_reg set Firstname='" + jtxtFN.getText() + "',Lastname='" + jtxtLN.getText() + "',Gender='" + Gender + "',Phoneno='" + jtxtphoneno.getText() + "',Email='" + txtemail.getText() + "',Maritalstatus='" + Maritalstatus + "',Guardian='" + jtxtguardian.getText() + "',County='" + County + "',Subcounty='" + Subcounty + "',Address='" + txtaddress.getText() + "',Bloodgroup='" + Bloodgroup + "',Patienttype='" + Patienttype + "',Weight='" + jtxtweight.getText() + "',Age='" + jtxtage.getText() + "',Regdate='" + val + "',Medhist='" + jTxtareamedhist.getText() + "' where Patientid='" + jtxtpatientid.getText() + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    System.out.println("Connecting to Db");
                    pst = con.prepareStatement(sql);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                    autoId();
                    JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                    this.printing_patreceipt();
                } catch (Exception e) {

                    JOptionPane.showMessageDialog(null, e);
                } finally {
                    try {
                        con.close();
                        System.out.println("Connection Closed:");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }

            }
        }
    }//GEN-LAST:event_jbtneditActionPerformed

    private void jbtnsave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnsave1ActionPerformed
        //FIELDS VALIDATION
        System.out.println("Patient Saving.....");
        EmailValidator emailValidator = new EmailValidator();
        String Phoneno = jtxtphoneno.getText();
        String Weight = jtxtweight.getText();
        String Age = jtxtage.getText();
        if (jtxtpatientid.getText().isEmpty() || jtxtFN.getText().isEmpty() || jtxtLN.getText().isEmpty() || jtxtage.getText().isEmpty() || jtxtguardian.getText().isEmpty() || jtxtphoneno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jcombogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!emailValidator.validate(txtemail.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Invalid Email Address",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jcombomaritalstatus.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Marital Status",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combocounty.getSelectedItem().equals("Select") || cmbsubcounty.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select County or Sub County",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jcombobloodgroup.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Blood Group",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (jcombopatienttype.getSelectedItem().equals("Select") || jcombopatienttype.getSelectedItem().equals("Inpatient") || jcombopatienttype.getSelectedItem().equals("Outpatient")) {
            JOptionPane.showMessageDialog(this, "Please Patient Type At The Time Of Registartion Is Pending",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Regdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Registration Date",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Phoneno.length() < 10 || Phoneno.length() > 10) {
            JOptionPane.showMessageDialog(this,
                    "The Phone Number should only be 10 digits",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Weight.length() > 4) {
            JOptionPane.showMessageDialog(this,
                    "Please insert a valid weight",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this,
                    "Please insert a valid Age",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //CONVERION TO STRING
            String Gender = jcombogender.getSelectedItem().toString();
            String Maritalstatus = jcombomaritalstatus.getSelectedItem().toString();
            String County = combocounty.getSelectedItem().toString();
            String Subcounty = cmbsubcounty.getSelectedItem().toString();
            String Bloodgroup = jcombobloodgroup.getSelectedItem().toString();
            String Patienttype = jcombopatienttype.getSelectedItem().toString();
            String val = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Regdate.getDate());
            System.out.println("Fields Validation Complete");
            try {
                String sql = "insert into pat_reg (Patientid,Firstname,Lastname,Gender,Phoneno,Email,Maritalstatus,Guardian,County,Subcounty,Address,Bloodgroup,Patienttype,Weight,Age,Regdate,Medhist) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to Database");
                pst = con.prepareStatement(sql);
                pst.setString(1, jtxtpatientid.getText());
                pst.setString(2, jtxtFN.getText());
                pst.setString(3, jtxtLN.getText());
                pst.setString(4, Gender);
                pst.setString(5, jtxtphoneno.getText());
                pst.setString(6, txtemail.getText());
                pst.setString(7, Maritalstatus);
                pst.setString(8, jtxtguardian.getText());
                pst.setString(9, County);
                pst.setString(10, Subcounty);
                pst.setString(11, txtaddress.getText());
                pst.setString(12, Bloodgroup);
                pst.setString(13, Patienttype);
                pst.setString(14, jtxtweight.getText());
                pst.setString(15, jtxtage.getText());
                pst.setString(16, val);
                pst.setString(17, jTxtareamedhist.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.printing_patreceipt();
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
    }//GEN-LAST:event_jbtnsave1ActionPerformed

    private void jtxtpatientidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtpatientidActionPerformed
        this.autoId();
    }//GEN-LAST:event_jtxtpatientidActionPerformed

    private void btnapp_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnapp_searchActionPerformed
        this.AppointautoId();
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAge.setText("");
        txtGuardianname.setText("");
        jcmbgender.setSelectedItem("Select");
        combotime.setSelectedItem("Time");
        txtAddress.setText("");
        txtappemail.setText("");
        txtPhoneNo.setText("");
        Regdate1.setDate(null);
        cmbservice.setSelectedItem("Select");
        jComboBoxDepartment.setSelectedItem("Select");
        combo_doctor.removeAllItems();
        combo_doctor.addItem("Select");
        txtservicefee.setText("");
        combopaid.setSelectedItem("Select");
        if (appsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Appointmentid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from appointments where Appointmentid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, appsearch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Appointmentid");
                    txtAppointmentId.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txtFirstName.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txtLastName.setText(add3);
                    String add4 = rs.getString("Age");
                    txtAge.setText(add4);
                    String add5 = rs.getString("Guardianname");
                    txtGuardianname.setText(add5);
                    String add6 = rs.getString("Gender");
                    jcmbgender.setSelectedItem(add6);
                    String add7 = rs.getString("Time");
                    combotime.setSelectedItem(add7);
                    String add8 = rs.getString("Address");
                    txtAddress.setText(add8);
                    String add9 = rs.getString("Email");
                    txtappemail.setText(add9);
                    String add10 = rs.getString("Phoneno");
                    txtPhoneNo.setText(add10);
                    String ad = rs.getObject("Appdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                    Regdate1.setDate(dat);
                    String add12 = rs.getString("Service");
                    cmbservice.setSelectedItem(add12);
                    String add13 = rs.getString("Department");
                    jComboBoxDepartment.setSelectedItem(add13);
                    String add14 = rs.getString("Assigndoc");
                    combo_doctor.setSelectedItem(add14);
                    String add15 = rs.getString("Servicefee");
                    txtservicefee.setText(add15);
                    String add16 = rs.getString("Paid");
                    combopaid.setSelectedItem(add16);
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
    }//GEN-LAST:event_btnapp_searchActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "DO YOU REALLY WANT TO DELETE", "delete", JOptionPane.YES_NO_OPTION);
        System.out.println("Delete Confirmation");
        if (p == 0) {
            try {
                String sql = "delete from appointments where Appointmentid=?";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.setString(1, appsearch.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "APPOINTMENT DELETED SUCCESSFULLY");
                this.AppointautoId();
                appsearch.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
                txtAge.setText("");
                txtGuardianname.setText("");
                jcmbgender.setSelectedItem("Select");
                combotime.setSelectedItem("Time");
                txtAddress.setText("");
                txtappemail.setText("");
                txtPhoneNo.setText("");
                Regdate1.setDate(null);
                cmbservice.setSelectedItem("Select");
                jComboBoxDepartment.setSelectedItem("Select");
                combo_doctor.removeAllItems();
                combo_doctor.addItem("Select");
                txtservicefee.setText("");
                combopaid.setSelectedItem("Select");
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
    }//GEN-LAST:event_deleteActionPerformed

    private void combocountyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocountyActionPerformed
        if (combocounty.getSelectedItem().equals("Baringo")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Tiaty");
            cmbsubcounty.addItem("Baringo North");
            cmbsubcounty.addItem("Baringo Central");
            cmbsubcounty.addItem("Baringo South");
            cmbsubcounty.addItem("Mogotio");
            cmbsubcounty.addItem("Eldama Ravine");
        } else if (combocounty.getSelectedItem().equals("Bomet")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Sotik");
            cmbsubcounty.addItem("Chepalungu");
            cmbsubcounty.addItem("Bomet East");
            cmbsubcounty.addItem("Bomet Central");
            cmbsubcounty.addItem("Konoin");
        } else if (combocounty.getSelectedItem().equals("Bungoma")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Mount Elgon");
            cmbsubcounty.addItem("Sirisia");
            cmbsubcounty.addItem("Kabuchai");
            cmbsubcounty.addItem("Bumula");
            cmbsubcounty.addItem("Kanduyi");
            cmbsubcounty.addItem("Webuye East");
            cmbsubcounty.addItem("Webuye West");
            cmbsubcounty.addItem("Kimilili");
            cmbsubcounty.addItem("Tongaren");
        } else if (combocounty.getSelectedItem().equals("Busia")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Teso North");
            cmbsubcounty.addItem("Teso South");
            cmbsubcounty.addItem("Nambale");
            cmbsubcounty.addItem("Matayos");
            cmbsubcounty.addItem("Butula");
            cmbsubcounty.addItem("Funyula");
            cmbsubcounty.addItem("Budalangi");
        } else if (combocounty.getSelectedItem().equals("Elgeyo-Marakwet")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Marakwet East");
            cmbsubcounty.addItem("Marakwet West");
            cmbsubcounty.addItem("Keiyo North");
            cmbsubcounty.addItem("Keiyo South");
        } else if (combocounty.getSelectedItem().equals("Embu")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Manyatta");
            cmbsubcounty.addItem("Runyenjes");
            cmbsubcounty.addItem("Mbeere South");
            cmbsubcounty.addItem("Mbeere North");
        } else if (combocounty.getSelectedItem().equals("Garissa")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Garissa Township");
            cmbsubcounty.addItem("Balambala");
            cmbsubcounty.addItem("Lagdera");
            cmbsubcounty.addItem("Dadaab");
            cmbsubcounty.addItem("Fafi");
            cmbsubcounty.addItem("Ijara");
        } else if (combocounty.getSelectedItem().equals("Homa Bay")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kasipul");
            cmbsubcounty.addItem("Kabondo Kasipul");
            cmbsubcounty.addItem("Karachuonyo");
            cmbsubcounty.addItem("Rangwe");
            cmbsubcounty.addItem("Homa Bay Town");
            cmbsubcounty.addItem("Ndhiwa");
            cmbsubcounty.addItem("Mbita");
            cmbsubcounty.addItem("Suba");
        } else if (combocounty.getSelectedItem().equals("Isiolo")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Isiolo North");
            cmbsubcounty.addItem("Isiolo South ");
        } else if (combocounty.getSelectedItem().equals("Kajiado")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kajiado South");
            cmbsubcounty.addItem("Kipkelion East");
            cmbsubcounty.addItem("Kipkelion West");
            cmbsubcounty.addItem("Kajiado North");
            cmbsubcounty.addItem("Kajiado Central");
        } else if (combocounty.getSelectedItem().equals("Kakamega")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Lugari");
            cmbsubcounty.addItem("Likuyani");
            cmbsubcounty.addItem("Malava");
            cmbsubcounty.addItem("Lurambi");
            cmbsubcounty.addItem("Navakholo");
            cmbsubcounty.addItem("Mumias West");
            cmbsubcounty.addItem("Mumias East");
            cmbsubcounty.addItem("Matungu");
            cmbsubcounty.addItem("Butere");
            cmbsubcounty.addItem("Khwisero");
            cmbsubcounty.addItem("Shinyalu");
            cmbsubcounty.addItem("Ikolomani");
        } else if (combocounty.getSelectedItem().equals("Kericho")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Ainamoi");
            cmbsubcounty.addItem("Bureti");
            cmbsubcounty.addItem("Belgut");
            cmbsubcounty.addItem("Sigowet-Soin");
        } else if (combocounty.getSelectedItem().equals("Kiambu")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Gatundu South");
            cmbsubcounty.addItem("Gatundu North");
            cmbsubcounty.addItem("Juja");
            cmbsubcounty.addItem("Thika Town");
            cmbsubcounty.addItem("Ruiru");
            cmbsubcounty.addItem("Githunguri");
            cmbsubcounty.addItem("Kiambu");
            cmbsubcounty.addItem("Kiambaa");
            cmbsubcounty.addItem("Kabete");
            cmbsubcounty.addItem("Kikuyu");
            cmbsubcounty.addItem("Limuru");
            cmbsubcounty.addItem("Lari");
        } else if (combocounty.getSelectedItem().equals("Kilifi")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kilifi North");
            cmbsubcounty.addItem("Kilifi South");
            cmbsubcounty.addItem("Kaloleni");
            cmbsubcounty.addItem("Rabai");
            cmbsubcounty.addItem("Ganze");
            cmbsubcounty.addItem("Malindi");
            cmbsubcounty.addItem("Magarini");
        } else if (combocounty.getSelectedItem().equals("Kirinyaga")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Mwea");
            cmbsubcounty.addItem("Gichugu");
            cmbsubcounty.addItem("Ndia");
            cmbsubcounty.addItem("Kirinyaga Central");
        } else if (combocounty.getSelectedItem().equals("Kisii")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Bonchari");
            cmbsubcounty.addItem("South Mugirango");
            cmbsubcounty.addItem("Bomachoge Borabu");
            cmbsubcounty.addItem("Bobasi");
            cmbsubcounty.addItem("Bomachoge Chache");
            cmbsubcounty.addItem("Nyaribari Masaba");
            cmbsubcounty.addItem("Nyaribari Chache");
        } else if (combocounty.getSelectedItem().equals("Kisumu")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kisumu East");
            cmbsubcounty.addItem("Kisumu West");
            cmbsubcounty.addItem("Kisumu Central");
            cmbsubcounty.addItem("Seme ");
            cmbsubcounty.addItem("Nyando");
            cmbsubcounty.addItem("Muhoroni");
            cmbsubcounty.addItem("Nyakach");
        } else if (combocounty.getSelectedItem().equals("Kitui")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Mwingi North");
            cmbsubcounty.addItem("Mwingi West");
            cmbsubcounty.addItem("Mwingi Central");
            cmbsubcounty.addItem("Kitui West");
            cmbsubcounty.addItem("Kitui Central");
            cmbsubcounty.addItem("Kitui East");
            cmbsubcounty.addItem("Kitui South");
        } else if (combocounty.getSelectedItem().equals("Kwale")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Msambweni");
            cmbsubcounty.addItem("Lunga Lunga");
            cmbsubcounty.addItem("Matuga");
            cmbsubcounty.addItem("Kinango ");
        } else if (combocounty.getSelectedItem().equals("Laikipia")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Laikipia West");
            cmbsubcounty.addItem("Laikipia East");
            cmbsubcounty.addItem("Laikipia North");
        } else if (combocounty.getSelectedItem().equals("Lamu")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Lamu East");
            cmbsubcounty.addItem("Lamu West");
        } else if (combocounty.getSelectedItem().equals("Machakos")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Masinga");
            cmbsubcounty.addItem("Yatta");
            cmbsubcounty.addItem("Kangundo");
            cmbsubcounty.addItem("Matungulu");
            cmbsubcounty.addItem("Kathiani");
            cmbsubcounty.addItem("Mavoko");
            cmbsubcounty.addItem("Machakos Town");
            cmbsubcounty.addItem("Mwala");
        } else if (combocounty.getSelectedItem().equals("Makueni")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Mbooni");
            cmbsubcounty.addItem("Kilome");
            cmbsubcounty.addItem("Kaiti");
            cmbsubcounty.addItem("Makueni");
            cmbsubcounty.addItem("Kibwezi West");
            cmbsubcounty.addItem("Kibwezi East");
        } else if (combocounty.getSelectedItem().equals("Marsabit")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Moyale");
            cmbsubcounty.addItem("North Horr");
            cmbsubcounty.addItem("Saku ");
            cmbsubcounty.addItem("Laisamis");
        } else if (combocounty.getSelectedItem().equals("Mandera")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Mandera West");
            cmbsubcounty.addItem("Banissa");
            cmbsubcounty.addItem("Mandera North");
            cmbsubcounty.addItem("Mandera South");
            cmbsubcounty.addItem("Mandera East");
            cmbsubcounty.addItem("Lafey ");
        } else if (combocounty.getSelectedItem().equals("Meru")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Igembe South");
            cmbsubcounty.addItem("Igembe Central");
            cmbsubcounty.addItem("Igembe North");
            cmbsubcounty.addItem("Tigania West");
            cmbsubcounty.addItem("Tigania East");
            cmbsubcounty.addItem("North Imenti ");
            cmbsubcounty.addItem("Buuri");
            cmbsubcounty.addItem("Central Imenti");
            cmbsubcounty.addItem("South Imenti ");
        } else if (combocounty.getSelectedItem().equals("Migori")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Rongo");
            cmbsubcounty.addItem("Awendo");
            cmbsubcounty.addItem("Suna East");
            cmbsubcounty.addItem("Suna West");
            cmbsubcounty.addItem("Uriri");
            cmbsubcounty.addItem("Nyatike");
            cmbsubcounty.addItem("Kuria West");
            cmbsubcounty.addItem("Kuria East");
        } else if (combocounty.getSelectedItem().equals("Mombasa")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Changamwe");
            cmbsubcounty.addItem("Jomvu");
            cmbsubcounty.addItem("Kisauni");
            cmbsubcounty.addItem("Nyali");
            cmbsubcounty.addItem("Likoni");
            cmbsubcounty.addItem("Mvita");
        } else if (combocounty.getSelectedItem().equals("Murang'a")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kangema");
            cmbsubcounty.addItem("Mathioya");
            cmbsubcounty.addItem("Kiharu");
            cmbsubcounty.addItem(" Kigumo");
            cmbsubcounty.addItem("Maragwa");
            cmbsubcounty.addItem("Kandara");
            cmbsubcounty.addItem("Gatanga");
        } else if (combocounty.getSelectedItem().equals("Nairobi")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Westlands");
            cmbsubcounty.addItem("Dogoretti North");
            cmbsubcounty.addItem("Dogoretti South");
            cmbsubcounty.addItem("Lang’ata");
            cmbsubcounty.addItem("Kibra");
            cmbsubcounty.addItem("Roysambu");
            cmbsubcounty.addItem("Kasarani");
            cmbsubcounty.addItem("Ruaraka");
            cmbsubcounty.addItem("Embakasi South");
            cmbsubcounty.addItem("Embakasi North");
            cmbsubcounty.addItem("Embakasi Central");
            cmbsubcounty.addItem("Embakasi East");
            cmbsubcounty.addItem("Embakasi West");
            cmbsubcounty.addItem("Makadara");
            cmbsubcounty.addItem("Kamukunji");
            cmbsubcounty.addItem("Starehe");
            cmbsubcounty.addItem("Mathare");
        } else if (combocounty.getSelectedItem().equals("Nakuru")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Molo");
            cmbsubcounty.addItem("Njoro");
            cmbsubcounty.addItem("Naivasha");
            cmbsubcounty.addItem("Gilgil");
            cmbsubcounty.addItem("Kuresoi South");
            cmbsubcounty.addItem("Kuresoi North");
            cmbsubcounty.addItem("Subukia");
            cmbsubcounty.addItem("Naivasha");
            cmbsubcounty.addItem("Rongai");
            cmbsubcounty.addItem("Bahati");
            cmbsubcounty.addItem("Nakuru Town West");
            cmbsubcounty.addItem("Nakuru Town East");
        } else if (combocounty.getSelectedItem().equals("Nandi")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Tinderet");
            cmbsubcounty.addItem("Aldai");
            cmbsubcounty.addItem("Nandi Hills");
            cmbsubcounty.addItem("Chesumei");
            cmbsubcounty.addItem("Emgwen");
            cmbsubcounty.addItem("Mosop");
        } else if (combocounty.getSelectedItem().equals("Narok")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kilgoris");
            cmbsubcounty.addItem("Emurua Dikirr");
            cmbsubcounty.addItem("Narok North");
            cmbsubcounty.addItem("Narok East");
            cmbsubcounty.addItem("Narok South");
            cmbsubcounty.addItem("Narok West");
        } else if (combocounty.getSelectedItem().equals("Nyamira")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kitutu Chache North");
            cmbsubcounty.addItem("Kitutu Chache South");
            cmbsubcounty.addItem("West Mugirango");
            cmbsubcounty.addItem("North Mugirango");
            cmbsubcounty.addItem("Borabu");
        } else if (combocounty.getSelectedItem().equals("Nyandarua")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kinangop");
            cmbsubcounty.addItem("Kipipiri");
            cmbsubcounty.addItem("Ol Kalou");
            cmbsubcounty.addItem("Ol Jorok");
            cmbsubcounty.addItem("Ndaragwa");
        } else if (combocounty.getSelectedItem().equals("Nyeri")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Tetu");
            cmbsubcounty.addItem("Kieni");
            cmbsubcounty.addItem("Mathira");
            cmbsubcounty.addItem("Othaya");
            cmbsubcounty.addItem("Mukurweini");
            cmbsubcounty.addItem("Nyeri Town");
        } else if (combocounty.getSelectedItem().equals("Samburu")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Samburu");
            cmbsubcounty.addItem("Samburu North");
            cmbsubcounty.addItem("Samburu East");
        } else if (combocounty.getSelectedItem().equals("Siaya")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Ugenya");
            cmbsubcounty.addItem("Ugunja");
            cmbsubcounty.addItem("Alego Usonga");
            cmbsubcounty.addItem("Gem");
            cmbsubcounty.addItem("Bondo");
            cmbsubcounty.addItem("Rarieda");
        } else if (combocounty.getSelectedItem().equals("Taita–Taveta")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Taveta");
            cmbsubcounty.addItem("Wundanyi");
            cmbsubcounty.addItem("Mwatate");
            cmbsubcounty.addItem("Voi");
        } else if (combocounty.getSelectedItem().equals("Tana River")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Garsen");
            cmbsubcounty.addItem("Galole");
            cmbsubcounty.addItem("Bura");
        } else if (combocounty.getSelectedItem().equals("Tharaka-Nithi")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Maara");
            cmbsubcounty.addItem("Chuka");
            cmbsubcounty.addItem("Tharaka");
        } else if (combocounty.getSelectedItem().equals("Trans-Nzoia")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kwanza");
            cmbsubcounty.addItem("Endebess");
            cmbsubcounty.addItem("Saboti");
            cmbsubcounty.addItem("Kiminini ");
            cmbsubcounty.addItem("Cherang’any");
        } else if (combocounty.getSelectedItem().equals("Turkana")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Turkana North");
            cmbsubcounty.addItem("Turkana West");
            cmbsubcounty.addItem("Turkana Central");
            cmbsubcounty.addItem("Loima");
            cmbsubcounty.addItem("Turkana South");
            cmbsubcounty.addItem("Turkana East");
        } else if (combocounty.getSelectedItem().equals("Uasin Gishu")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Soy");
            cmbsubcounty.addItem("Turbo");
            cmbsubcounty.addItem("Moiben");
            cmbsubcounty.addItem("Ainabkio");
            cmbsubcounty.addItem("Kapseret");
            cmbsubcounty.addItem("Kesses");
        } else if (combocounty.getSelectedItem().equals("Vihiga")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Vihiga");
            cmbsubcounty.addItem("Sabatia");
            cmbsubcounty.addItem("Hamisi");
            cmbsubcounty.addItem("Luanda");
            cmbsubcounty.addItem("Emuhaya ");
        } else if (combocounty.getSelectedItem().equals("Wajir")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Wajir North");
            cmbsubcounty.addItem("Wajir East");
            cmbsubcounty.addItem("Tarbaj");
            cmbsubcounty.addItem("Wajir West");
            cmbsubcounty.addItem("Eldas");
            cmbsubcounty.addItem("Wajir South");
        } else if (combocounty.getSelectedItem().equals("West Pokot")) {
            cmbsubcounty.removeAllItems();
            cmbsubcounty.addItem("Kapenguria");
            cmbsubcounty.addItem("Sigor");
            cmbsubcounty.addItem("Kacheliba");
            cmbsubcounty.addItem("Pokot South");
        }
    }//GEN-LAST:event_combocountyActionPerformed

    private void btnprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintActionPerformed
        this.printing_patreceipt();
    }//GEN-LAST:event_btnprintActionPerformed

    private void txtservicefeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtservicefeeMouseClicked
        if (txtservicefee.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select Service And Department First",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtservicefeeMouseClicked

    private void combo_doctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_doctorMouseClicked
        if (jComboBoxDepartment.getSelectedItem().equals("Select") || cmbservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Service And Department First",
                    "Error", JOptionPane.ERROR_MESSAGE);
            jComboBoxDepartment.setSelectedItem("Select");
        }
    }//GEN-LAST:event_combo_doctorMouseClicked

    private void jComboBoxDepartmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxDepartmentMouseClicked
        if (cmbservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Service First",
                    "Error", JOptionPane.ERROR_MESSAGE);
            jComboBoxDepartment.setSelectedItem("Select");
        }
    }//GEN-LAST:event_jComboBoxDepartmentMouseClicked

    private void jComboBoxDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDepartmentActionPerformed
        if (jComboBoxDepartment.getSelectedItem().equals("Maternity")) {
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Mark Mark");
            combo_doctor.addItem("Dr John Mania");
            combo_doctor.addItem("Dr Peter Wafula");
            txtservicefee.setText("500");
        } else if (jComboBoxDepartment.getSelectedItem().equals("Haematology")) {
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Ken Ken");
            combo_doctor.addItem("Dr Lucy Natasha");
            combo_doctor.addItem("Dr Morgan Wafula");
            txtservicefee.setText("600");
        } else if (jComboBoxDepartment.getSelectedItem().equals("General")) {
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Lutherford Main");
            combo_doctor.addItem("Dr David John");
            combo_doctor.addItem("Dr Mary Martha");
            txtservicefee.setText("0");
        } else if (jComboBoxDepartment.getSelectedItem().equals("Radiology")) {
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Ali Mohamed");
            combo_doctor.addItem("Dr Mary Lucy");
            combo_doctor.addItem("Dr Allan Joe");
            txtservicefee.setText("800");
        } else if (jComboBoxDepartment.getSelectedItem().equals("Neurology")) {
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr Stewart");
            combo_doctor.addItem("Dr Kennedy");
            combo_doctor.addItem("Dr Luke Joe");
            txtservicefee.setText("800");
        } else if (jComboBoxDepartment.getSelectedItem().equals("Cardiology")) {
            combo_doctor.removeAllItems();
            combo_doctor.addItem("Dr VIncent");
            combo_doctor.addItem("Dr Allein Wapt");
            combo_doctor.addItem("Dr Patrick Mwene");
            txtservicefee.setText("800");
        }
    }//GEN-LAST:event_jComboBoxDepartmentActionPerformed

    private void btnapp_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnapp_printActionPerformed
        this.app_patrecipt();
    }//GEN-LAST:event_btnapp_printActionPerformed

    private void jbtnrefreshregpat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnrefreshregpat1ActionPerformed
        view_table();
    }//GEN-LAST:event_jbtnrefreshregpat1ActionPerformed

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
            java.util.logging.Logger.getLogger(Receptionist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Receptionist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Receptionist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Receptionist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Receptionist().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Regdate;
    private com.toedter.calendar.JDateChooser Regdate1;
    private javax.swing.JScrollPane Scrollpane;
    private javax.swing.JTextArea app_receipt;
    private javax.swing.JTextField appsearch;
    private javax.swing.JButton btnapp_print;
    private javax.swing.JButton btnapp_search;
    private javax.swing.JButton btnappedit;
    private javax.swing.JButton btnprint;
    private javax.swing.JButton btnsaveappointment;
    private javax.swing.JButton btnuploadimage;
    private javax.swing.JComboBox<String> cmbservice;
    private javax.swing.JComboBox<String> cmbsubcounty;
    private javax.swing.JComboBox<String> combo_doctor;
    private javax.swing.JComboBox<String> combocounty;
    private javax.swing.JComboBox<String> combopaid;
    private javax.swing.JComboBox<String> combotime;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBoxDepartment;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTxtareamedhist;
    private javax.swing.JButton jbtnchangepass;
    private javax.swing.JButton jbtnedit;
    private javax.swing.JButton jbtnrefreshapp;
    private javax.swing.JButton jbtnrefreshregpat;
    private javax.swing.JButton jbtnrefreshregpat1;
    private javax.swing.JButton jbtnsave1;
    private javax.swing.JButton jbtnsearch3;
    private javax.swing.JComboBox<String> jcmbgender;
    private javax.swing.JComboBox<String> jcombobloodgroup;
    private javax.swing.JComboBox<String> jcombogender;
    private javax.swing.JComboBox<String> jcombomaritalstatus;
    private javax.swing.JComboBox<String> jcombopatienttype;
    private javax.swing.JTextField jtxtFN;
    private javax.swing.JTextField jtxtLN;
    private javax.swing.JTextField jtxtage;
    private javax.swing.JTextField jtxtguardian;
    private javax.swing.JTextField jtxtpatientid;
    private javax.swing.JTextField jtxtphoneno;
    private javax.swing.JTextField jtxtsearch;
    private javax.swing.JTextField jtxtweight;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lbluser;
    private javax.swing.JTextArea pat_reg_txtarea;
    private javax.swing.JTable pat_regtable;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtAppointmentId;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtGuardianname;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPhoneNo;
    private javax.swing.JTextField txtaddress;
    private javax.swing.JTextField txtappemail;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtservicefee;
    // End of variables declaration//GEN-END:variables
}
