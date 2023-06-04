package HMS;

import java.awt.Color;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
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

/**
 *
 * @author Allein
 */
public class Doctor extends javax.swing.JFrame {

    Connection con = null;
    ResultSet rs;
    PreparedStatement pst = null;
    String Imagename = null;
    byte[] uimage = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public Doctor() {
        initComponents();
        System.out.println("Application Started\n" + dtf.format(now));
        AppointautoId();
        view_table();
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
        }

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

    public void calculate() {
        int a;
        int s = Integer.parseInt(txtqnty.getText());
        int q = Integer.parseInt(txtprc.getText());
        a = s * q;
        String x = String.valueOf(a);
        txttotal_price.setText(x);
    }

    public void issuedrug_printing() {
        if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txsideeffects.getText().isEmpty() || txtprecautions.getText().isEmpty() || txtstorage.getText().isEmpty() || txtprc.getText().isEmpty() || txtqnty.getText().isEmpty() || cmb_gender.getSelectedItem().equals("Select") || cmbdrugname.getSelectedItem().equals("Select") || combodrugid.getSelectedItem().equals("Select") || cmbunitofmeasure.getSelectedItem().equals("Select") || cmbcategory.getSelectedItem().equals("Select") || cmbdosage.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Fill All The Fields Or Search A Record To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(exp_date.getDate());
            String Qty1 = (txtpatientid.getText());
            String Qty2 = (txtFN.getText());
            String Qty3 = (txtLN.getText());
            String Qty4 = (cmb_gender.getSelectedItem().toString());
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
            txt_issuedrug_print.setText("");
            txt_issuedrug_print.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tPrescription Receipt\n"
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
            cmb_gender.setSelectedItem("Select");
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
        }
    }

    public void view_table() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from pat_doctor";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            Doc_tablepatinfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            Doc_tablepatinfo.setModel(DbUtils.resultSetToTableModel(rs));
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

    public void printing_patreceipt() {
        if (txt_patid.getText().isEmpty() || txt_symptoms.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_disease.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_weight.getText().isEmpty() || txt_age.getText().isEmpty() || txt_age.getText().isEmpty() || txt_bldpressure.getText().isEmpty() || cmbGender.getSelectedItem().equals("Select") || cmbMarital.getSelectedItem().equals("Select") || cmd_Bldgrp.getSelectedItem().equals("Select") || cmb_patyp.getSelectedItem().equals("Select") || cmb_Service.getSelectedItem().equals("Select") || cmb_samptyp.getSelectedItem().equals("Select") || Regdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Regdate.getDate());
            String Qty1 = (txt_patid.getText());
            String Qty2 = (txt_FN.getText());
            String Qty3 = (txt_LN.getText());
            String Qty4 = (cmbGender.getSelectedItem().toString());
            String Qty5 = (cmbMarital.getSelectedItem().toString());
            String Qty6 = (txt_guardian.getText());
            String Qty7 = (cmd_Bldgrp.getSelectedItem().toString());
            String Qty8 = (cmb_patyp.getSelectedItem().toString());
            String Qty9 = (txt_weight.getText());
            String Qty10 = (txt_age.getText());
            String Qty11 = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
            String Qty12 = (cmb_Service.getSelectedItem().toString());
            String Qty13 = (cmb_samptyp.getSelectedItem().toString());
            String Qty14 = (txt_sampid.getText());
            String Qty15 = (txt_bldpressure.getText());
            String Qty16 = (txt_disease.getText());
            String Qty17 = (txt_symptoms.getText());
            txt_nurseprint_pat.setText("");
            txt_nurseprint_pat.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tTreatment Receipt\n"
                    + "\nPatient ID:\t\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nGender:\t\t" + Qty4
                    + "\nMarital Status:\t\t" + Qty5
                    + "\nGuardian Name:\t" + Qty6
                    + "\nBlood Group:\t\t" + Qty7
                    + "\nPatient Type:\t\t" + Qty8
                    + "\nWeight:\t\t" + Qty9
                    + "\nAge:\t\t" + Qty10
                    + "\nReg Date:\t\t" + Qty11
                    + "\nService:\t\t" + Qty12
                    + "\n:Sample Type\t\t" + Qty13
                    + "\nSample Id:\t\t" + Qty14
                    + "\nBlood Pressure:\t" + Qty15
                    + "\nDisease:\t\t" + Qty16
                    + "\nSymptoms:\t\t" + Qty17
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                    + "\n\tWE WISH YOU A QUICK RECOVERY");
            try {
                txt_nurseprint_pat.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            txt_srch.setText("");
            txt_patid.setText("");
            txt_FN.setText("");
            txt_LN.setText("");
            cmbGender.setSelectedItem("Select");
            cmbMarital.setSelectedItem("Select");
            txt_guardian.setText("");
            cmd_Bldgrp.setSelectedItem("Select");
            cmb_patyp.setSelectedItem("Select");
            txt_weight.setText("");
            txt_age.setText("");
            Regdate.setDate(null);
            cmb_Service.setSelectedItem("Select");
            cmb_samptyp.setSelectedItem("Select");
            txt_sampid.setText("");
            txt_bldpressure.setText("");
            txt_disease.setText("");
            txt_symptoms.setText("");

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

        jTabappointment = new javax.swing.JTabbedPane();
        jPanelDoctorspanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_sampid = new javax.swing.JTextField();
        cmb_samptyp = new javax.swing.JComboBox<>();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        txt_srch = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_patid = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        txt_FN = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        txt_LN = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        cmbGender = new javax.swing.JComboBox<>();
        jLabel106 = new javax.swing.JLabel();
        cmbMarital = new javax.swing.JComboBox<>();
        jLabel107 = new javax.swing.JLabel();
        txt_guardian = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        cmd_Bldgrp = new javax.swing.JComboBox<>();
        jLabel109 = new javax.swing.JLabel();
        cmb_patyp = new javax.swing.JComboBox<>();
        jLabel110 = new javax.swing.JLabel();
        txt_weight = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        txt_age = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        cmb_Service = new javax.swing.JComboBox<>();
        jLabel113 = new javax.swing.JLabel();
        Regdate = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_nurseprint_pat = new javax.swing.JTextArea();
        txt_disease = new javax.swing.JTextField();
        txt_bldpressure = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        txt_symptoms = new javax.swing.JTextArea();
        jPanel24 = new javax.swing.JPanel();
        btn_getpatientdoc = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        nurse_btnrefesh = new javax.swing.JButton();
        btn_search = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtsearch = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        cmb_gender = new javax.swing.JComboBox<>();
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
        jLabel58 = new javax.swing.JLabel();
        price1 = new javax.swing.JLabel();
        txtprc = new javax.swing.JTextField();
        price2 = new javax.swing.JLabel();
        cmbdosage = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel59 = new javax.swing.JLabel();
        txtstorage = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txtprecautions = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txsideeffects = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        btn_getpat = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btnrefresh1 = new javax.swing.JButton();
        btn_prsc_srch = new javax.swing.JButton();
        btnsearch2 = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        exp_date = new com.toedter.calendar.JDateChooser();
        price3 = new javax.swing.JLabel();
        txttotal_price = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txt_issuedrug_print = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtpatientid1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtFN1 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtLN1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        combobloodgroup1 = new javax.swing.JComboBox<>();
        jLabel51 = new javax.swing.JLabel();
        combopatienttype1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jtxtsearch = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtsampleid1 = new javax.swing.JTextField();
        combogender1 = new javax.swing.JComboBox<>();
        combosampletype1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        typeoftest = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txttechnicianname = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtdiagnosis = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtresults = new javax.swing.JTextArea();
        jSeparator4 = new javax.swing.JSeparator();
        testdate = new com.toedter.calendar.JDateChooser();
        combomaritalstatus1 = new javax.swing.JComboBox<>();
        txtguardian1 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        txtweight1 = new javax.swing.JTextField();
        txtage1 = new javax.swing.JTextField();
        resultsdate = new com.toedter.calendar.JDateChooser();
        typeoftest1 = new javax.swing.JLabel();
        combotypetest = new javax.swing.JComboBox<>();
        txtbodytemp1 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnrefresh = new javax.swing.JButton();
        btnViewall = new javax.swing.JButton();
        jpanelAppointments = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtAppointmentId = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        txtGuardianname = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jComboBoxDepartment = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        txtPhoneNo = new javax.swing.JTextField();
        jcmbgender = new javax.swing.JComboBox<>();
        combotime = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel13 = new javax.swing.JPanel();
        btnsaveappointment = new javax.swing.JButton();
        btnappedit = new javax.swing.JButton();
        jbtnrefreshapp = new javax.swing.JButton();
        btnapp_search = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        Regdate1 = new com.toedter.calendar.JDateChooser();
        jLabel44 = new javax.swing.JLabel();
        appsearch = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        cmbservice = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        txtservicefee = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtappemail = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        combopaid = new javax.swing.JComboBox<>();
        combo_doctor = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        app_receipt = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        Pat_info = new javax.swing.JPanel();
        Scrollpane = new javax.swing.JScrollPane();
        Doc_tablepatinfo = new javax.swing.JTable();
        btnviewtablerefersh = new javax.swing.JButton();
        jPanelmanageaccount = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jbtnchangepass = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lblimage = new javax.swing.JLabel();
        btnuploadimage = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lbluser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximizedBounds(new java.awt.Rectangle(1230, 780, 1230, 780));
        setMaximumSize(new java.awt.Dimension(1230, 780));
        setMinimumSize(new java.awt.Dimension(1230, 780));
        setPreferredSize(new java.awt.Dimension(1230, 780));
        setResizable(false);
        setSize(new java.awt.Dimension(1230, 780));

        jTabappointment.setBackground(new java.awt.Color(255, 255, 255));
        jTabappointment.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabappointment.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanelDoctorspanel.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDoctorspanel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel25.setBackground(new java.awt.Color(204, 204, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel25.setMaximumSize(new java.awt.Dimension(1032, 742));
        jPanel25.setMinimumSize(new java.awt.Dimension(1032, 742));

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel99.setText("Symptoms");
        jLabel99.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setText("Blood Pressure");
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel31.setText("Sample Id");
        jLabel31.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_sampid.setEditable(false);
        txt_sampid.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        cmb_samptyp.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_samptyp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Blood", "Stool", "Urine", "Saliva", "Skin", "Semen", "Rectal", "Spinal Fluids", "Cervical", "Molecular(DNA)", "Tissue" }));
        cmb_samptyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_samptypActionPerformed(evt);
            }
        });

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel100.setText("Service");
        jLabel100.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel101.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel101.setText("Sample type");
        jLabel101.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_srch.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel102.setText("Search ");
        jLabel102.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setText("Patient ID");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_patid.setEditable(false);
        txt_patid.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel103.setText("First Name");
        jLabel103.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_FN.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel104.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel104.setText("Last Name");
        jLabel104.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_LN.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel105.setText("Gender");
        jLabel105.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbGender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel106.setText("Marital Status");
        jLabel106.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbMarital.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbMarital.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Married", "Single", "Divorced", "Widowed" }));
        cmbMarital.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel107.setText("Guardians Name");
        jLabel107.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_guardian.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel108.setText("Blood Group");
        jLabel108.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmd_Bldgrp.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmd_Bldgrp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "AB", "O", "A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+", "Unknown" }));
        cmd_Bldgrp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel109.setText("Patient Type");
        jLabel109.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_patyp.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_patyp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Inpatient", "Outpatient", "Pending" }));
        cmb_patyp.setToolTipText("");
        cmb_patyp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel110.setText("Weight(Kgs)");
        jLabel110.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_weight.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel111.setText("Age");
        jLabel111.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_age.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel112.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel112.setText("Reg Date");
        jLabel112.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_Service.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_Service.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "General", "Gynacological", "ICU", "Maternity", "Pediatric", "Prostratic", "Surgery" }));

        jLabel113.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel113.setText("Disease");
        jLabel113.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Regdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regdate.setDateFormatString("yyyy-MM-dd");
        Regdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        txt_nurseprint_pat.setColumns(20);
        txt_nurseprint_pat.setRows(5);
        jScrollPane1.setViewportView(txt_nurseprint_pat);

        txt_disease.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        txt_bldpressure.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        txt_symptoms.setColumns(10);
        txt_symptoms.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txt_symptoms.setLineWrap(true);
        txt_symptoms.setRows(5);
        jScrollPane6.setViewportView(txt_symptoms);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel102)
                        .addGap(18, 18, 18)
                        .addComponent(txt_srch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel109, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel108, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel107, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel106, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel105, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel103, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel104, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_LN)
                                        .addComponent(txt_FN)
                                        .addComponent(txt_patid, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_guardian, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cmbMarital, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txt_age)
                                                .addComponent(txt_weight, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(cmb_patyp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(cmd_Bldgrp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Regdate, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel100, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_bldpressure)
                                    .addComponent(cmb_samptyp, 0, 306, Short.MAX_VALUE)
                                    .addComponent(txt_sampid, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmb_Service, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_disease)))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel99)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_srch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmb_Service, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel100))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel103)
                                .addGap(6, 6, 6)
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
                                .addComponent(jLabel109))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmb_samptyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel31)
                                    .addComponent(txt_sampid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(txt_bldpressure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel113)
                                    .addComponent(txt_disease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel99)))
                        .addGap(8, 8, 8)
                        .addComponent(jLabel110)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel111)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel25Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(txt_patid, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_FN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txt_LN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbMarital, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_guardian, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(cmd_Bldgrp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(cmb_patyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8)
                        .addComponent(txt_weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(txt_age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Regdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(243, 243, 243))))
        );

        jPanel25Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Regdate, cmbGender, cmb_Service, cmb_patyp, cmb_samptyp, cmd_Bldgrp, jLabel100, jLabel101, jLabel103, jLabel104, jLabel105, jLabel106, jLabel107, jLabel108, jLabel109, jLabel110, jLabel111, jLabel112, jLabel113, jLabel18, jLabel19, jLabel31, jLabel99, txt_FN, txt_LN, txt_age, txt_bldpressure, txt_disease, txt_guardian, txt_patid, txt_sampid, txt_srch, txt_weight});

        jPanel24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_getpatientdoc.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_getpatientdoc.setText("PATIENT");
        btn_getpatientdoc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_getpatientdoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_getpatientdoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getpatientdocActionPerformed(evt);
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

        btn_print.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_print_32px.png"))); // NOI18N
        btn_print.setText("PRINT");
        btn_print.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_search, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_getpatientdoc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_update, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nurse_btnrefesh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_save, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_print, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_getpatientdoc, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(nurse_btnrefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 650, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelDoctorspanelLayout = new javax.swing.GroupLayout(jPanelDoctorspanel);
        jPanelDoctorspanel.setLayout(jPanelDoctorspanelLayout);
        jPanelDoctorspanelLayout.setHorizontalGroup(
            jPanelDoctorspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelDoctorspanelLayout.setVerticalGroup(
            jPanelDoctorspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabappointment.addTab("Doctors Panel", jPanelDoctorspanel);

        jPanel10.setBackground(new java.awt.Color(153, 153, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel23.setText("Search ");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Patient ID");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setText("First Name");
        jLabel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel56.setText("Last Name");
        jLabel56.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel57.setText("Gender");
        jLabel57.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_gender.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        txtLN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txtFN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

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

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel58.setText("Expiry Date");
        jLabel58.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        price1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price1.setText("Unit Price");
        price1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtprc.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        price2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price2.setText("Dosage");
        price2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbdosage.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbdosage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1x1", "1x2", "1x3", "1 Spn x3", "1 Injection", "Apply 3/day" }));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setOpaque(true);
        jSeparator2.setPreferredSize(new java.awt.Dimension(80, 4));

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel59.setText("Storage");
        jLabel59.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtstorage.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel60.setText("Precautions");
        jLabel60.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtprecautions.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel61.setText("Side Effects");
        jLabel61.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txsideeffects.setColumns(10);
        txsideeffects.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txsideeffects.setLineWrap(true);
        txsideeffects.setRows(10);
        txsideeffects.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txsideeffects);

        jPanel15.setBackground(new java.awt.Color(153, 153, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

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

        btn_prsc_srch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_prsc_srch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btn_prsc_srch.setText("SEARCH");
        btn_prsc_srch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_prsc_srch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_prsc_srch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prsc_srchActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_prsc_srch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnrefresh1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnupdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(btn_getpat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsearch2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(btn_getpat, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnrefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_prsc_srch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 79, Short.MAX_VALUE))
        );

        exp_date.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        exp_date.setDateFormatString("yyyy-MM-dd");
        exp_date.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        price3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price3.setText("Price(Ksh.)");
        price3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txttotal_price.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txt_issuedrug_print.setColumns(20);
        txt_issuedrug_print.setRows(5);
        jScrollPane5.setViewportView(txt_issuedrug_print);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFN)
                                    .addComponent(txtpatientid)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(quantity1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(category1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(unitofmeasur1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtqnty)
                                    .addComponent(exp_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(drugname1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(drugid1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbdrugname, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combodrugid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbunitofmeasure, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbcategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLN)
                                    .addComponent(cmb_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane2))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(price1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(price2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtstorage, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtprecautions, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                            .addGap(7, 7, 7)
                                            .addComponent(cmbdosage, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(txtprc, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(price3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(7, 7, 7)
                                .addComponent(txttotal_price, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttotal_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(price3)))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtprc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(price1)))
                .addGap(11, 11, 11)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(txtLN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel57)
                            .addComponent(cmb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(drugid1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(cmbdrugname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(drugname1)
                            .addComponent(combodrugid, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(unitofmeasur1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbunitofmeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbcategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(quantity1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtqnty, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel58)
                            .addComponent(exp_date, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(price2)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(cmbdosage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtstorage))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(txtprecautions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel61)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {category1, cmb_gender, cmbcategory, cmbdosage, cmbdrugname, cmbunitofmeasure, combodrugid, drugid1, drugname1, exp_date, jLabel16, jLabel17, jLabel56, jLabel57, jLabel58, jLabel59, jLabel60, price1, price2, price3, quantity1, txtFN, txtLN, txtpatientid, txtprc, txtprecautions, txtqnty, txtstorage, txttotal_price, unitofmeasur1});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 40, Short.MAX_VALUE))
        );

        jTabappointment.addTab("Prescription", jPanel3);

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Patient ID");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtpatientid1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtpatientid1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel49.setText("First Name");
        jLabel49.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtFN1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtFN1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel50.setText("Last Name");
        jLabel50.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

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

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel51.setText("Patient Type");
        jLabel51.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

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

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Body Temp");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        typeoftest.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        typeoftest.setText("Test Date");
        typeoftest.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Technician Name");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txttechnicianname.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txttechnicianname.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Results Date");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel52.setText("Diagnosis");
        jLabel52.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtdiagnosis.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtdiagnosis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel53.setText("Results");
        jLabel53.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtresults.setColumns(20);
        txtresults.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtresults.setLineWrap(true);
        txtresults.setRows(5);
        txtresults.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jScrollPane4.setViewportView(txtresults);

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jSeparator4.setOpaque(true);
        jSeparator4.setPreferredSize(new java.awt.Dimension(80, 10));

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

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel54.setText("Age");
        jLabel54.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel55.setText("Weight(Kgs)");
        jLabel55.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtFN1, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtLN1)
                                            .addComponent(txtpatientid1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(combogender1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(combomaritalstatus1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtguardian1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(typeoftest, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(testdate, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtbodytemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtage1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtweight1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combopatienttype1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combobloodgroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(typeoftest1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(combosampletype1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtsampleid1)
                                    .addComponent(combotypetest, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                            .addGap(110, 110, 110)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel53))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtdiagnosis)
                                            .addComponent(resultsdate, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txttechnicianname, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jLabel22)
                        .addGap(36, 36, 36)
                        .addComponent(jtxtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel15, jLabel20, jLabel21, jLabel24, jLabel30, jLabel49, jLabel50});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combotypetest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeoftest1)
                            .addComponent(jLabel15))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(combosampletype1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(txtsampleid1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel29))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txttechnicianname, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(resultsdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtdiagnosis, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(9, 9, 9)
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(9, 9, 9)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtpatientid1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(txtFN1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(txtLN1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(combogender1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(combomaritalstatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(txtguardian1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combobloodgroup1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combopatienttype1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtweight1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbodytemp1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(testdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeoftest, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(117, Short.MAX_VALUE))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {combobloodgroup1, combogender1, combomaritalstatus1, combopatienttype1, combosampletype1, combotypetest, jLabel10, jLabel15, jLabel20, jLabel21, jLabel24, jLabel28, jLabel29, jLabel30, jLabel49, jLabel50, jLabel51, jLabel52, jLabel54, jLabel55, jLabel7, jLabel8, jtxtsearch, resultsdate, testdate, txtFN1, txtLN1, txtage1, txtbodytemp1, txtdiagnosis, txtguardian1, txtpatientid1, txtsampleid1, txttechnicianname, txtweight1, typeoftest, typeoftest1});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabappointment.addTab("Lab Results", jPanel4);

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("APPOINTMENTS");
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

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel27.setText("Age");
        jLabel27.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel35.setText("Last Name");
        jLabel35.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtLastName.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        txtGuardianname.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel36.setText("Guardian Name");
        jLabel36.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel37.setText("Service");
        jLabel37.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setText("Gender");
        jLabel38.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel39.setText("Appointmen Time");
        jLabel39.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel40.setText("App Date");
        jLabel40.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setText(" Address");
        jLabel41.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtAddress.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel42.setText("Assigned Doctor");
        jLabel42.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

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

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel43.setText("Phone No.");
        jLabel43.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtPhoneNo.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jcmbgender.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jcmbgender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        combotime.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combotime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Time", "08:00", "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00" }));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setDoubleBuffered(true);
        jSeparator3.setFocusCycleRoot(true);
        jSeparator3.setFocusTraversalPolicyProvider(true);
        jSeparator3.setFocusable(true);
        jSeparator3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jSeparator3.setOpaque(true);

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnsaveappointment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnrefreshapp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnappedit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(btnapp_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnsaveappointment, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnappedit)
                .addGap(16, 16, 16)
                .addComponent(jbtnrefreshapp)
                .addGap(16, 16, 16)
                .addComponent(btnapp_search)
                .addGap(16, 16, 16)
                .addComponent(delete)
                .addContainerGap(216, Short.MAX_VALUE))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnapp_search, btnappedit, btnsaveappointment, delete, jbtnrefreshapp});

        Regdate1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Regdate1.setDateFormatString("yyyy-MM-dd");
        Regdate1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel44.setText("Search ");

        appsearch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel45.setText("Department");
        jLabel45.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbservice.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmbservice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Antenatal Care", "Blood Donation", "Blood Screening", "Checkup", "Chemotherapy", "Consultation", "DNA Test", "Medical Followup", "Nerve Training", "Postnatal Care", "Pregnancy Test", "Pressure Test", "Radiotherapy", "X-ray" }));
        cmbservice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbserviceActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel46.setText("Appointment Fee");
        jLabel46.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtservicefee.setEditable(false);
        txtservicefee.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtservicefee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtservicefeeMouseClicked(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel47.setText("Email");
        jLabel47.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtappemail.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel48.setText("Paid");
        jLabel48.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combopaid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combopaid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "No", "Yes" }));

        combo_doctor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combo_doctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", " " }));
        combo_doctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_doctorMouseClicked(evt);
            }
        });

        jScrollPane3.setBackground(new java.awt.Color(204, 204, 255));
        jScrollPane3.setForeground(new java.awt.Color(204, 204, 255));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(2500, 113));

        app_receipt.setColumns(20);
        app_receipt.setRows(5);
        jScrollPane3.setViewportView(app_receipt);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(289, 289, 289)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(jLabel44)
                                .addGap(18, 18, 18)
                                .addComponent(appsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtappemail))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAddress))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGuardianname)
                                    .addComponent(txtAge)
                                    .addComponent(txtLastName)
                                    .addComponent(txtFirstName)
                                    .addComponent(txtAppointmentId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(combotime, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcmbgender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Regdate1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(txtservicefee)
                            .addComponent(combopaid, 0, 270, Short.MAX_VALUE)
                            .addComponent(jComboBoxDepartment, 0, 270, Short.MAX_VALUE)
                            .addComponent(cmbservice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_doctor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(11, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(appsearch)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel36)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtAppointmentId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel40))
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel37))
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel35)
                                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGuardianname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel27)
                                                    .addComponent(jLabel42)
                                                    .addComponent(combo_doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(8, 8, 8)
                                                .addComponent(jLabel46)
                                                .addGap(4, 4, 4)))))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jcmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(Regdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(cmbservice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jComboBoxDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtservicefee, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(combopaid, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combotime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addGap(4, 4, 4))
                            .addComponent(txtappemail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 142, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Regdate1, appsearch, cmbservice, combo_doctor, combopaid, combotime, jComboBoxDepartment, jLabel11, jLabel27, jLabel35, jLabel36, jLabel37, jLabel38, jLabel39, jLabel40, jLabel41, jLabel42, jLabel43, jLabel44, jLabel45, jLabel46, jLabel47, jLabel48, jLabel9, jcmbgender, txtAddress, txtAge, txtAppointmentId, txtFirstName, txtGuardianname, txtLastName, txtPhoneNo, txtappemail, txtservicefee});

        javax.swing.GroupLayout jpanelAppointmentsLayout = new javax.swing.GroupLayout(jpanelAppointments);
        jpanelAppointments.setLayout(jpanelAppointmentsLayout);
        jpanelAppointmentsLayout.setHorizontalGroup(
            jpanelAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelAppointmentsLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelAppointmentsLayout.setVerticalGroup(
            jpanelAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabappointment.addTab("Appointments", jpanelAppointments);

        Scrollpane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 255), 10, true));
        Scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        Scrollpane.setToolTipText("");
        Scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Scrollpane.setAutoscrolls(true);
        Scrollpane.setName("JscrollPat_reg"); // NOI18N

        Doc_tablepatinfo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Doc_tablepatinfo.setModel(new javax.swing.table.DefaultTableModel(
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
        Doc_tablepatinfo.setGridColor(new java.awt.Color(0, 0, 0));
        Doc_tablepatinfo.setRowHeight(25);
        Doc_tablepatinfo.getTableHeader().setReorderingAllowed(false);
        Scrollpane.setViewportView(Doc_tablepatinfo);

        btnviewtablerefersh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnviewtablerefersh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btnviewtablerefersh.setText("REFRESH");
        btnviewtablerefersh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnviewtablerefersh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnviewtablerefersh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnviewtablerefershActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Pat_infoLayout = new javax.swing.GroupLayout(Pat_info);
        Pat_info.setLayout(Pat_infoLayout);
        Pat_infoLayout.setHorizontalGroup(
            Pat_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pat_infoLayout.createSequentialGroup()
                .addComponent(btnviewtablerefersh, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        Pat_infoLayout.setVerticalGroup(
            Pat_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pat_infoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnviewtablerefersh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Pat_infoLayout.createSequentialGroup()
                .addComponent(Scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pat_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(Pat_info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        jTabappointment.addTab("Patient Information", jPanel7);

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
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
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jbtnchangepass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jbtnchangepass, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(255, 204, 255));

        lblimage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/web_camera.png"))); // NOI18N

        btnuploadimage.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnuploadimage.setText("Upload Profile Photo");
        btnuploadimage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnuploadimage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnuploadimageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
            .addComponent(lblimage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
        );

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 0));
        jLabel34.setText("KEEP YOUR USERID CONFIDENTIAL");

        javax.swing.GroupLayout jPanelmanageaccountLayout = new javax.swing.GroupLayout(jPanelmanageaccount);
        jPanelmanageaccount.setLayout(jPanelmanageaccountLayout);
        jPanelmanageaccountLayout.setHorizontalGroup(
            jPanelmanageaccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelmanageaccountLayout.createSequentialGroup()
                .addGroup(jPanelmanageaccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelmanageaccountLayout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelmanageaccountLayout.setVerticalGroup(
            jPanelmanageaccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelmanageaccountLayout.createSequentialGroup()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelmanageaccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(285, Short.MAX_VALUE))
        );

        jTabappointment.addTab("Manage Account", jPanelmanageaccount);

        jPanel5.setBackground(new java.awt.Color(0, 51, 255));
        jPanel5.setMaximumSize(new java.awt.Dimension(666, 108));
        jPanel5.setPreferredSize(new java.awt.Dimension(666, 108));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Doctor");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_doctors_bag_100px.png"))); // NOI18N

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

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Welcome");

        lbluser.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbluser, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(lbluser, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
            .addComponent(jTabappointment, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabappointment))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Login_Doctor HMS = new Login_Doctor();
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
        Doctor_EditAccount HMS = new Doctor_EditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        String user = lbluser.getText();
        Doctor_EditAccount HMS = new Doctor_EditAccount();
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
        Doctor_EditAccount HMS = new Doctor_EditAccount();
        HMS.setVisible(true);
        HMS.username(user);

    }//GEN-LAST:event_jbtnchangepassActionPerformed

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        String user = lbluser.getText();
        Doctor_EditAccount HMS = new Doctor_EditAccount();
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
                AppointautoId();
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
                    //this.app_patrecipt();
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

    private void cmbdrugnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbdrugnameActionPerformed
        if (cmbdrugname.getSelectedItem().equals("Select")) {
            cmbdrugname.setSelectedItem("Select");
        } else {
            this.Drugset();
        }
    }//GEN-LAST:event_cmbdrugnameActionPerformed

    private void txtqntyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqntyKeyReleased
        this.calculate();
    }//GEN-LAST:event_txtqntyKeyReleased

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you Sure to Update?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Update Dialog................");
        if (p == 0) {
            //FIELDS VALIDATION
            if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txsideeffects.getText().isEmpty() || txtprecautions.getText().isEmpty() || txtstorage.getText().isEmpty() || txtprc.getText().isEmpty() || txtqnty.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmb_gender.getSelectedItem().equals("Select")) {
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
            } else {
                String Gender = cmb_gender.getSelectedItem().toString();
                String Drugname = cmbdrugname.getSelectedItem().toString();
                String Drugid = combodrugid.getSelectedItem().toString();
                String Unitofmeasure = cmbunitofmeasure.getSelectedItem().toString();
                String Category = cmbcategory.getSelectedItem().toString();
                String Dosage = cmbdosage.getSelectedItem().toString();
                String val = ((JTextField) exp_date.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(exp_date.getDate());
                try {
                    String sql = "update presription set FN='" + txtFN.getText() + "',"
                            + "LN='" + txtLN.getText() + "',Gender='" + Gender + "',"
                            + "Drugname='" + Drugname + "',Drugid='" + Drugid + "',"
                            + "Unit_msr='" + Unitofmeasure + "',Category='" + Category + "',"
                            + "Quantity='" + txtqnty.getText() + "',Expdate='" + val + "',"
                            + "Unit_prc='" + txtprc.getText() + "',Prc='" + txttotal_price.getText() + "',"
                            + "Dosage='" + Dosage + "',Storage='" + txtstorage.getText() + "',"
                            + "Precautions='" + txtprecautions.getText() + "',Sideeffects='" + txsideeffects.getText() + "'"
                            + "where Patid='" + txtpatientid.getText() + "' ";
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
                    cmb_gender.setSelectedItem("Select");
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

    private void btnrefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefresh1ActionPerformed
        txtsearch.setText("");
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        cmb_gender.setSelectedItem("Select");
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
    }//GEN-LAST:event_btnrefresh1ActionPerformed

    private void btn_prsc_srchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prsc_srchActionPerformed
        ResultSet rs = null;
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        cmb_gender.setSelectedItem("Select");
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
                    String add1 = rs.getString("Patid");
                    txtpatientid.setText(add1);
                    String add2 = rs.getString("FN");
                    txtFN.setText(add2);
                    String add3 = rs.getString("LN");
                    txtLN.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmb_gender.setSelectedItem(add4);
                    String add5 = rs.getString("Drugname");
                    cmbdrugname.setSelectedItem(add5);
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
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
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
    }//GEN-LAST:event_btn_prsc_srchActionPerformed

    private void btnsearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearch2ActionPerformed
        this.issuedrug_printing();
    }//GEN-LAST:event_btnsearch2ActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txsideeffects.getText().isEmpty() || txtprecautions.getText().isEmpty() || txtstorage.getText().isEmpty() || txtprc.getText().isEmpty() || txtqnty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_gender.getSelectedItem().equals("Select")) {
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
        } else {
            String Gender = cmb_gender.getSelectedItem().toString();
            String Drugname = cmbdrugname.getSelectedItem().toString();
            String Drugid = combodrugid.getSelectedItem().toString();
            String Unitofmeasure = cmbunitofmeasure.getSelectedItem().toString();
            String Category = cmbcategory.getSelectedItem().toString();
            String Dosage = cmbdosage.getSelectedItem().toString();
            String val = ((JTextField) exp_date.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(exp_date.getDate());
            try {
                String sql = "insert into presription (Patid,FN,LN,Gender,Drugname,Drugid,Unit_msr,Category,Quantity,Expdate,Unit_prc,Prc,Dosage,Storage,Precautions,Sideeffects) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.issuedrug_printing();
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

    private void btn_getpatientdocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getpatientdocActionPerformed
        Connection con = null;
        ResultSet rs;
        PreparedStatement pst = null;
        txt_patid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmbGender.setSelectedItem("Select");
        cmbMarital.setSelectedItem("Select");
        txt_guardian.setText("");
        cmd_Bldgrp.setSelectedItem("Select");
        cmb_patyp.setSelectedItem("Select");
        txt_weight.setText("");
        txt_age.setText("");
        Regdate.setDate(null);
        cmb_Service.setSelectedItem("Select");
        cmb_samptyp.setSelectedItem("Select");
        txt_sampid.setText("");
        txt_bldpressure.setText("");
        txt_disease.setText("");
        txt_symptoms.setText("");
        if (txt_srch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select Patientid,Firstname,Lastname,Gender,Maritalstatus,Guardian,Bloodgroup,Patienttype,Weight,Age,Regdate from pat_reg where Patientid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_srch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patientid");
                    txt_patid.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txt_FN.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txt_LN.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmbGender.setSelectedItem(add4);
                    String add5 = rs.getString("Maritalstatus");
                    cmbMarital.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txt_guardian.setText(add6);
                    String add7 = rs.getString("Bloodgroup");
                    cmd_Bldgrp.setSelectedItem(add7);
                    String add8 = rs.getString("Patienttype");
                    cmb_patyp.setSelectedItem(add8);
                    String add9 = rs.getString("Weight");
                    txt_weight.setText(add9);
                    String add10 = rs.getString("Age");
                    txt_age.setText(add10);
                    String ad = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(ad);
                    Regdate.setDate(dat);
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
    }//GEN-LAST:event_btn_getpatientdocActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you Sure to Update?", "Update", JOptionPane.YES_NO_OPTION);
        System.out.println("Update Dialog................");
        if (p == 0) {
            String Age = txt_age.getText();
            String Weight = txt_weight.getText();
            if (txt_patid.getText().isEmpty() || txt_symptoms.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_disease.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_weight.getText().isEmpty() || txt_age.getText().isEmpty() || txt_age.getText().isEmpty() || txt_bldpressure.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbGender.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Gender", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmbMarital.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmd_Bldgrp.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmb_patyp.getSelectedItem().equals("Select") || cmb_patyp.getSelectedItem().equals("Pending")) {
                JOptionPane.showMessageDialog(this, "Please The Patient Type Should Be Inpatient Or OutPatient", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmb_Service.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select The Service Offered To The Patient", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cmb_samptyp.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Type Of Sample Taken From The Patient", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Regdate.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Select Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Age.length() > 3) {
                JOptionPane.showMessageDialog(this, "Please Insert A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Weight.length() > 3) {
                JOptionPane.showMessageDialog(this, "Please Insert A Valid Weight", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String Gender = cmbGender.getSelectedItem().toString();
                String Maritalstatus = cmbMarital.getSelectedItem().toString();
                String Blood = cmd_Bldgrp.getSelectedItem().toString();
                String Pattype = cmb_patyp.getSelectedItem().toString();
                String Service = cmb_Service.getSelectedItem().toString();
                String Sample = cmb_samptyp.getSelectedItem().toString();
                String Reg_date = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(Regdate.getDate());
                System.out.println("Fields Validation Complete");
                try {
                    String sql = "update pat_doctor set FN='" + txt_FN.getText() + "',LN='" + txt_LN.getText() + "',Gender='" + Gender + "'"
                            + ",Marital='" + Maritalstatus + "',Guardian='" + txt_guardian.getText() + "',Bld_grp='" + Blood + "',Pat_type='" + Pattype + "'"
                            + ",Weight='" + txt_weight.getText() + "',Age='" + txt_age.getText() + "',Regdate='" + Reg_date + "',Service='" + Service + "'"
                            + ",Samp_typ='" + Sample + "',Samp_id='" + txt_sampid.getText() + "',Bld_prs ='" + txt_bldpressure.getText() + "'"
                            + ",Disease='" + txt_disease.getText() + "',Symptoms='" + txt_symptoms.getText() + "' where Patid='" + txt_patid.getText() + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    System.out.println("Connecting to Db\n" + dtf.format(now));
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                    JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                    this.printing_patreceipt();
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

    private void nurse_btnrefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nurse_btnrefeshActionPerformed
        txt_srch.setText("");
        txt_patid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmbGender.setSelectedItem("Select");
        cmbMarital.setSelectedItem("Select");
        txt_guardian.setText("");
        cmd_Bldgrp.setSelectedItem("Select");
        cmb_patyp.setSelectedItem("Select");
        txt_weight.setText("");
        txt_age.setText("");
        Regdate.setDate(null);
        cmb_Service.setSelectedItem("Select");
        cmb_samptyp.setSelectedItem("Select");
        txt_sampid.setText("");
        txt_bldpressure.setText("");
        txt_disease.setText("");
        txt_symptoms.setText("");

    }//GEN-LAST:event_nurse_btnrefeshActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        txt_patid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmbGender.setSelectedItem("Select");
        cmbMarital.setSelectedItem("Select");
        txt_guardian.setText("");
        cmd_Bldgrp.setSelectedItem("Select");
        cmb_patyp.setSelectedItem("Select");
        txt_weight.setText("");
        txt_age.setText("");
        Regdate.setDate(null);
        cmb_Service.setSelectedItem("Select");
        cmb_samptyp.setSelectedItem("Select");
        txt_sampid.setText("");
        txt_bldpressure.setText("");
        txt_disease.setText("");
        txt_symptoms.setText("");
        if (txt_srch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_doctor where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_srch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patid");
                    txt_patid.setText(add1);
                    String add2 = rs.getString("FN");
                    txt_FN.setText(add2);
                    String add3 = rs.getString("LN");
                    txt_LN.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmbGender.setSelectedItem(add4);
                    String add5 = rs.getString("Marital");
                    cmbMarital.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txt_guardian.setText(add6);
                    String add7 = rs.getString("Bld_grp");
                    cmd_Bldgrp.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    cmb_patyp.setSelectedItem(add8);
                    String add9 = rs.getString("Weight");
                    txt_weight.setText(add9);
                    String add10 = rs.getString("Age");
                    txt_age.setText(add10);
                    String add11 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add11);
                    Regdate.setDate(dat);
                    String add12 = rs.getString("Service");
                    cmb_Service.setSelectedItem(add12);
                    String add14 = rs.getString("Samp_typ");
                    cmb_samptyp.setSelectedItem(add14);
                    String add15 = rs.getString("Samp_id");
                    txt_sampid.setText(add15);
                    String add16 = rs.getString("Bld_prs");
                    txt_bldpressure.setText(add16);
                    String add17 = rs.getString("Disease");
                    txt_disease.setText(add17);
                    String add18 = rs.getString("Symptoms");
                    txt_symptoms.setText(add18);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
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

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        String Age = txt_age.getText();
        String Weight = txt_weight.getText();
        String BLD = txt_bldpressure.getText();
        if (txt_patid.getText().isEmpty() || txt_symptoms.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_disease.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_weight.getText().isEmpty() || txt_age.getText().isEmpty() || txt_age.getText().isEmpty() || txt_bldpressure.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbGender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbMarital.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmd_Bldgrp.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_patyp.getSelectedItem().equals("Select") || cmb_patyp.getSelectedItem().equals("Pending")) {
            JOptionPane.showMessageDialog(this, "Please The Patient Type Should Be Inpatient Or OutPatient", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_Service.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Service Offered To The Patient", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_samptyp.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Type Of Sample Taken From The Patient", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Regdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Insert A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Weight.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Insert A Valid Weight", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (BLD.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Insert A Valid Blood Pressure", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmbGender.getSelectedItem().toString();
            String Maritalstatus = cmbMarital.getSelectedItem().toString();
            String Blood = cmd_Bldgrp.getSelectedItem().toString();
            String Pattype = cmb_patyp.getSelectedItem().toString();
            String Service = cmb_Service.getSelectedItem().toString();
            String Sample = cmb_samptyp.getSelectedItem().toString();
            String Reg_date = ((JTextField) Regdate.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Regdate.getDate());
            System.out.println("Fields Validation Complete");
            try {
                String sql = "INSERT INTO pat_doctor(Patid,FN,LN,Gender,Marital,Guardian,Bld_grp,Pat_type,Weight,Age, "
                        + "Regdate,Service,Samp_typ,Samp_id,Bld_prs,Disease,Symptoms)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to Database");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_patid.getText());
                pst.setString(2, txt_FN.getText());
                pst.setString(3, txt_LN.getText());
                pst.setString(4, Gender);
                pst.setString(5, Maritalstatus);
                pst.setString(6, txt_guardian.getText());
                pst.setString(7, Blood);
                pst.setString(8, Pattype);
                pst.setString(9, txt_weight.getText());
                pst.setString(10, txt_age.getText());
                pst.setString(11, Reg_date);
                pst.setString(12, Service);
                pst.setString(13, Sample);
                pst.setString(14, txt_sampid.getText());
                pst.setString(15, txt_bldpressure.getText());
                pst.setString(16, txt_disease.getText());
                pst.setString(17, txt_symptoms.getText());
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
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        this.printing_patreceipt();
    }//GEN-LAST:event_btn_printActionPerformed

    private void cmb_samptypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_samptypActionPerformed
        if (cmb_samptyp.getSelectedItem().equals("Blood")) {
            txt_sampid.setText("BL21");
        } else if (cmb_samptyp.getSelectedItem().equals("Stool")) {
            txt_sampid.setText("ST89");
        } else if (cmb_samptyp.getSelectedItem().equals("Urine")) {
            txt_sampid.setText("UR56");
        } else if (cmb_samptyp.getSelectedItem().equals("Saliva")) {
            txt_sampid.setText("SA09");
        } else if (cmb_samptyp.getSelectedItem().equals("Skin")) {
            txt_sampid.setText("SK76");
        } else if (cmb_samptyp.getSelectedItem().equals("Semen")) {
            txt_sampid.setText("SE69");
        } else if (cmb_samptyp.getSelectedItem().equals("Rectal")) {
            txt_sampid.setText("RE11");
        } else if (cmb_samptyp.getSelectedItem().equals("Spinal Fluids")) {
            txt_sampid.setText("SP49");
        } else if (cmb_samptyp.getSelectedItem().equals("Molecular(DNA)")) {
            txt_sampid.setText("DN71");
        } else if (cmb_samptyp.getSelectedItem().equals("Tissue")) {
            txt_sampid.setText("TE21");
        } else if (cmb_samptyp.getSelectedItem().equals("Cervical")) {
            txt_sampid.setText("CE21");
        }
    }//GEN-LAST:event_cmb_samptypActionPerformed

    private void btnviewtablerefershActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnviewtablerefershActionPerformed
        this.view_table();
    }//GEN-LAST:event_btnviewtablerefershActionPerformed

    private void btn_getpatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getpatActionPerformed
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        cmb_gender.setSelectedItem("Select");
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
                    cmb_gender.setSelectedItem(add4);
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
    }//GEN-LAST:event_btn_getpatActionPerformed

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
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Doctor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Doc_tablepatinfo;
    private javax.swing.JPanel Pat_info;
    private com.toedter.calendar.JDateChooser Regdate;
    private com.toedter.calendar.JDateChooser Regdate1;
    private javax.swing.JScrollPane Scrollpane;
    private javax.swing.JTextArea app_receipt;
    private javax.swing.JTextField appsearch;
    private javax.swing.JButton btnViewall;
    private javax.swing.JButton btn_getpat;
    private javax.swing.JButton btn_getpatientdoc;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_prsc_srch;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton btnapp_search;
    private javax.swing.JButton btnappedit;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton btnrefresh1;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsaveappointment;
    private javax.swing.JButton btnsearch2;
    private javax.swing.JButton btnupdate;
    private javax.swing.JButton btnuploadimage;
    private javax.swing.JButton btnviewtablerefersh;
    private javax.swing.JLabel category1;
    private javax.swing.JComboBox<String> cmbGender;
    private javax.swing.JComboBox<String> cmbMarital;
    private javax.swing.JComboBox<String> cmb_Service;
    private javax.swing.JComboBox<String> cmb_gender;
    private javax.swing.JComboBox<String> cmb_patyp;
    private javax.swing.JComboBox<String> cmb_samptyp;
    private javax.swing.JComboBox<String> cmbcategory;
    private javax.swing.JComboBox<String> cmbdosage;
    private javax.swing.JComboBox<String> cmbdrugname;
    private javax.swing.JComboBox<String> cmbservice;
    private javax.swing.JComboBox<String> cmbunitofmeasure;
    private javax.swing.JComboBox<String> cmd_Bldgrp;
    private javax.swing.JComboBox<String> combo_doctor;
    private javax.swing.JComboBox<String> combobloodgroup1;
    private javax.swing.JComboBox<String> combodrugid;
    private javax.swing.JComboBox<String> combogender1;
    private javax.swing.JComboBox<String> combomaritalstatus1;
    private javax.swing.JComboBox<String> combopaid;
    private javax.swing.JComboBox<String> combopatienttype1;
    private javax.swing.JComboBox<String> combosampletype1;
    private javax.swing.JComboBox<String> combotime;
    private javax.swing.JComboBox<String> combotypetest;
    private javax.swing.JButton delete;
    private javax.swing.JLabel drugid1;
    private javax.swing.JLabel drugname1;
    private com.toedter.calendar.JDateChooser exp_date;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBoxDepartment;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
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
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelDoctorspanel;
    private javax.swing.JPanel jPanelmanageaccount;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabappointment;
    private javax.swing.JButton jbtnchangepass;
    private javax.swing.JButton jbtnrefreshapp;
    private javax.swing.JComboBox<String> jcmbgender;
    private javax.swing.JPanel jpanelAppointments;
    private javax.swing.JTextField jtxtsearch;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lbluser;
    private javax.swing.JButton nurse_btnrefesh;
    private javax.swing.JLabel price1;
    private javax.swing.JLabel price2;
    private javax.swing.JLabel price3;
    private javax.swing.JLabel quantity1;
    private com.toedter.calendar.JDateChooser resultsdate;
    private com.toedter.calendar.JDateChooser testdate;
    private javax.swing.JTextArea txsideeffects;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtAppointmentId;
    private javax.swing.JTextField txtFN;
    private javax.swing.JTextField txtFN1;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtGuardianname;
    private javax.swing.JTextField txtLN;
    private javax.swing.JTextField txtLN1;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPhoneNo;
    private javax.swing.JTextField txt_FN;
    private javax.swing.JTextField txt_LN;
    private javax.swing.JTextField txt_age;
    private javax.swing.JTextField txt_bldpressure;
    private javax.swing.JTextField txt_disease;
    private javax.swing.JTextField txt_guardian;
    private javax.swing.JTextArea txt_issuedrug_print;
    private javax.swing.JTextArea txt_nurseprint_pat;
    private javax.swing.JTextField txt_patid;
    private javax.swing.JTextField txt_sampid;
    private javax.swing.JTextField txt_srch;
    private javax.swing.JTextArea txt_symptoms;
    private javax.swing.JTextField txt_weight;
    private javax.swing.JTextField txtage1;
    private javax.swing.JTextField txtappemail;
    private javax.swing.JTextField txtbodytemp1;
    private javax.swing.JTextField txtdiagnosis;
    private javax.swing.JTextField txtguardian1;
    private javax.swing.JTextField txtpatientid;
    private javax.swing.JTextField txtpatientid1;
    private javax.swing.JTextField txtprc;
    private javax.swing.JTextField txtprecautions;
    private javax.swing.JTextField txtqnty;
    private javax.swing.JTextArea txtresults;
    private javax.swing.JTextField txtsampleid1;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextField txtservicefee;
    private javax.swing.JTextField txtstorage;
    private javax.swing.JTextField txttechnicianname;
    private javax.swing.JTextField txttotal_price;
    private javax.swing.JTextField txtweight1;
    private javax.swing.JLabel typeoftest;
    private javax.swing.JLabel typeoftest1;
    private javax.swing.JLabel unitofmeasur1;
    // End of variables declaration//GEN-END:variables
}
