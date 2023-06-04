package HMS;

import java.awt.Color;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Finance extends javax.swing.JFrame {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String Imagename = null;
    byte[] uimage = null;

    public Finance() {
        initComponents();
        autoId();
    }

    public void Drugset() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select Drugid,Unit_msr,Category,Expdate,Unit_price from drug_inventory where Drugname=?";
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
                expdate2.setDate(dat);
                String add5 = rs.getString("Unit_price");
                txtprc.setText(add5);
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

    public void print_OutPat_receipt() {
        if (txt_receipt.isEnabled() == true) {
            if (txt_patid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_age.getText().isEmpty() || txt_charges.getText().isEmpty() || txt_total.getText().isEmpty() || cmbgender.getSelectedItem().equals("Select") || cmbmarital.getSelectedItem().equals("Select") || cmbBldgrp.getSelectedItem().equals("Select") || cmbpattype.getSelectedItem().equals("Select") || cmbservice.getSelectedItem().equals("Select") || cmb_pay.getSelectedItem().equals("Select") || Reg_date.getDate() == null || Bill_date.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Validation of fields .....");
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(Reg_date.getDate());
                Date_Format.format(Bill_date.getDate());
                String Qty1 = (txt_patid.getText());
                String Qty2 = (txt_FN.getText());
                String Qty3 = (txt_LN.getText());
                String Qty4 = (cmbgender.getSelectedItem().toString());
                String Qty5 = ((cmbmarital.getSelectedItem().toString()));
                String Qty6 = (txt_guardian.getText());
                String Qty7 = (cmbBldgrp.getSelectedItem().toString());
                String Qty8 = (cmbpattype.getSelectedItem().toString());
                String Qty9 = (txt_age.getText());
                String Qty10 = ((JTextField) Reg_date.getDateEditor().getUiComponent()).getText();
                String Qty11 = (cmbservice.getSelectedItem().toString());
                String Qty12 = ((JTextField) Bill_date.getDateEditor().getUiComponent()).getText();
                String Qty13 = (txt_charges.getText());
                String Qty14 = (txt_total.getText());
                String Qty15 = (cmb_pay.getSelectedItem().toString());
                String Qty16 = (txt_receipt.getText());
                System.out.println("Done validating");
                outpat_receipt.setText("");
                outpat_receipt.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tOutpatient Bill Receipt\n"
                        + "\nPatient ID:\t\t" + Qty1
                        + "\nFirstname:\t\t" + Qty2
                        + "\nLastname:\t\t" + Qty3
                        + "\nGender:\t\t" + Qty4
                        + "\nMarital:\t\t" + Qty5
                        + "\nGuardian:\t\t" + Qty6
                        + "\nBlood Group:\t\t" + Qty7
                        + "\nPatient Type:\t\t" + Qty8
                        + "\nAge:\t\t" + Qty9
                        + "\nReg Date:\t\t" + Qty10
                        + "\nService:\t\t" + Qty11
                        + "\nBill Date:\t\t" + Qty12
                        + "\nCharges:\t\t" + Qty13
                        + "\nTotal:\t\t" + Qty14
                        + "\nPayment Mode:\t" + Qty15
                        + "\nReceipt No:\t\t" + Qty16
                        + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                        + "\n\tWE WISH YOU A QUICK RECOVERY");
                System.out.println("Setting done");
                try {
                    boolean complete = outpat_receipt.print();
                    if (complete) {
                        JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
                    } else {
                        JOptionPane.showMessageDialog(null, "PRINTING CANCELLED");
                    }
                } catch (PrinterException ex) {

                    Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.out.println("error.." + ex);
                }
                System.out.println("refresh");
                txt_srch.setText("");
                txt_patid.setText("");
                txt_FN.setText("");
                txt_LN.setText("");
                cmbgender.setSelectedItem("Select");
                txt_guardian.setText("");
                cmbmarital.setSelectedItem("Select");
                cmbBldgrp.setSelectedItem("Select");
                cmbpattype.setSelectedItem("Select");
                txt_age.setText("");
                Reg_date.setDate(null);
                Bill_date.setDate(null);
                cmbservice.setSelectedItem("Select");
                txt_charges.setText("");
                txt_total.setText(null);
                cmb_pay.setSelectedItem("Select");
                txt_receipt.setText("");
            }
        } else if (txt_receipt.isEnabled() == false) {
            if (txt_patid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_age.getText().isEmpty() || txt_charges.getText().isEmpty() || txt_total.getText().isEmpty() || cmbgender.getSelectedItem().equals("Select") || cmbmarital.getSelectedItem().equals("Select") || cmbBldgrp.getSelectedItem().equals("Select") || cmbpattype.getSelectedItem().equals("Select") || cmbservice.getSelectedItem().equals("Select") || cmb_pay.getSelectedItem().equals("Select") || Reg_date.getDate() == null || Bill_date.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Validation of fields .....");
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(Reg_date.getDate());
                Date_Format.format(Bill_date.getDate());
                String Qty1 = (txt_patid.getText());
                String Qty2 = (txt_FN.getText());
                String Qty3 = (txt_LN.getText());
                String Qty4 = (cmbgender.getSelectedItem().toString());
                String Qty5 = ((cmbmarital.getSelectedItem().toString()));
                String Qty6 = (txt_guardian.getText());
                String Qty7 = (cmbBldgrp.getSelectedItem().toString());
                String Qty8 = (cmbpattype.getSelectedItem().toString());
                String Qty9 = (txt_age.getText());
                String Qty10 = ((JTextField) Reg_date.getDateEditor().getUiComponent()).getText();
                String Qty11 = (cmbservice.getSelectedItem().toString());
                String Qty12 = ((JTextField) Bill_date.getDateEditor().getUiComponent()).getText();
                String Qty13 = (txt_charges.getText());
                String Qty14 = (txt_total.getText());
                String Qty15 = (cmb_pay.getSelectedItem().toString());
                System.out.println("Done validating");
                outpat_receipt.setText("");
                outpat_receipt.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tOutpatient Bill Receipt\n"
                        + "\nPatient ID:\t\t" + Qty1
                        + "\nFirstname:\t\t" + Qty2
                        + "\nLastname:\t\t" + Qty3
                        + "\nGender:\t\t" + Qty4
                        + "\nMarital:\t\t" + Qty5
                        + "\nGuardian:\t\t" + Qty6
                        + "\nBlood Group:\t\t" + Qty7
                        + "\nPatient Type:\t\t" + Qty8
                        + "\nAge:\t\t" + Qty9
                        + "\nReg Date:\t\t" + Qty10
                        + "\nService:\t\t" + Qty11
                        + "\nBill Date:\t\t" + Qty12
                        + "\nCharges:\t\t" + Qty13
                        + "\nTotal:\t\t" + Qty14
                        + "\nPayment Mode:\t" + Qty15
                        + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                        + "\n\tWE WISH YOU A QUICK RECOVERY");
                System.out.println("Setting done");
                try {
                    boolean complete = outpat_receipt.print();
                    if (complete) {
                        JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
                    } else {
                        JOptionPane.showMessageDialog(null, "PRINTING CANCELLED");
                    }
                } catch (PrinterException ex) {
                    Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.out.println("error.." + ex);
                }
                System.out.println("refresh");
                txt_srch.setText("");
                txt_patid.setText("");
                txt_FN.setText("");
                txt_LN.setText("");
                cmbgender.setSelectedItem("Select");
                txt_guardian.setText("");
                cmbmarital.setSelectedItem("Select");
                cmbBldgrp.setSelectedItem("Select");
                cmbpattype.setSelectedItem("Select");
                txt_age.setText("");
                Reg_date.setDate(null);
                Bill_date.setDate(null);
                cmbservice.setSelectedItem("Select");
                txt_charges.setText("");
                txt_total.setText(null);
                cmb_pay.setSelectedItem("Select");
                txt_receipt.setText("");
            }
        }
    }

    public void print_inpat_bill() {
        if (txtreceipt_no.isEnabled() == true) {
            if (txtpatid2.getText().isEmpty() || txtFN2.getText().isEmpty() || txtLN2.getText().isEmpty() || txtguardian2.getText().isEmpty() || txt_servicecharge.getText().isEmpty() || txt_total2.getText().isEmpty() || txtward_charges1.getText().isEmpty() || age2.getText().isEmpty() || cmbgender2.getSelectedItem().equals("Select") || cmbpattype2.getSelectedItem().equals("Select") || cmbbloodgrp2.getSelectedItem().equals("Select") || billdate2.getDate() == null || bookdate.getDate() == null || cmbbedno2.getSelectedItem().equals("Select") || cmbwardno2.getSelectedItem().equals("Select") || cmbwardtype2.getSelectedItem().equals("Select") || regdate2.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(regdate2.getDate());
                Date_Format.format(bookdate.getDate());
                Date_Format.format(billdate2.getDate());
                String Qty1 = (txtpatid2.getText());
                String Qty2 = (txtFN2.getText());
                String Qty3 = (txtLN2.getText());
                String Qty4 = (cmbgender2.getSelectedItem().toString());
                String Qty5 = ((txtguardian2.getText()));
                String Qty6 = (cmbbloodgrp2.getSelectedItem().toString());
                String Qty7 = (cmbpattype2.getSelectedItem().toString());
                String Qty8 = (age2.getText());
                String Qty9 = ((JTextField) regdate2.getDateEditor().getUiComponent()).getText();
                String Qty10 = (cmbservice2.getSelectedItem().toString());
                String Qty11 = ((JTextField) bookdate.getDateEditor().getUiComponent()).getText();
                String Qty12 = (cmbwardtype2.getSelectedItem().toString());
                String Qty13 = (cmbwardno2.getSelectedItem().toString());
                String Qty14 = (cmbbedno2.getSelectedItem().toString());
                String Qty15 = (txtward_charges1.getText());
                String Qty16 = (txt_servicecharge.getText());
                String Qty17 = ((JTextField) billdate2.getDateEditor().getUiComponent()).getText();
                String Qty18 = (txt_total2.getText());
                String Qty19 = (cmbpay.getSelectedItem().toString());
                String Qty20 = (txtreceipt_no.getText());
                outpat_receipt.setText("");
                outpat_receipt.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tInpatient Bill Receipt\n"
                        + "\nPatient ID:\t\t" + Qty1
                        + "\nFirstname:\t\t" + Qty2
                        + "\nLastname:\t\t" + Qty3
                        + "\nGender:\t\t" + Qty4
                        + "\nGuardian:\t\t" + Qty5
                        + "\nBlood Group:\t\t" + Qty6
                        + "\nPatient Type:\t\t" + Qty7
                        + "\nAge:\t\t" + Qty8
                        + "\nReg Date:\t\t" + Qty9
                        + "\nService:\t\t" + Qty10
                        + "\nBooking Date:\t\t" + Qty11
                        + "\nWard Type:\t\t" + Qty12
                        + "\nWard No:\t\t" + Qty13
                        + "\nBed No:\t\t" + Qty14
                        + "\nWard Charges:\t" + Qty15
                        + "\nService Charges:\t" + Qty16
                        + "\nBilling Date:\t\t" + Qty17
                        + "\nTotal:\t\t" + Qty18
                        + "\nPayment Mode:\t" + Qty19
                        + "\nReceipt No:\t\t" + Qty20
                        + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                        + "\n\tWE WISH YOU A QUICK RECOVERY");
                System.out.println("Setting done");
                try {
                    boolean complete = outpat_receipt.print();
                    if (complete) {
                        JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
                    } else {
                        JOptionPane.showMessageDialog(null, "PRINTING CANCELLED");
                    }
                } catch (PrinterException ex) {
                    Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.out.println("error.." + ex);
                }
                txtsrch.setText("");
                txtpatid2.setText("");
                txtFN2.setText("");
                txtLN2.setText("");
                cmbgender2.setSelectedItem("Select");
                txtguardian2.setText("");
                cmbbloodgrp2.setSelectedItem("Select");
                cmbpattype2.setSelectedItem("Select");
                age2.setText("");
                regdate2.setDate(null);
                cmbservice2.setSelectedItem("Select");
                bookdate.setDate(null);
                billdate2.setDate(null);
                cmbwardtype2.setSelectedItem("Select");
                cmbwardno2.setSelectedItem("Select");
                cmbbedno2.removeAllItems();
                cmbbedno2.addItem("Select");
                txtward_charges1.setText("");
                txt_servicecharge.setText("");
                txt_total2.setText("");
                cmbpay.setSelectedItem("Select");
                txtreceipt_no.setText("");

            }
        } else if (txtreceipt_no.isEnabled() == false) {
            if (txtpatid2.getText().isEmpty() || txtFN2.getText().isEmpty() || txtLN2.getText().isEmpty() || txtguardian2.getText().isEmpty() || txt_servicecharge.getText().isEmpty() || txt_total2.getText().isEmpty() || txtward_charges1.getText().isEmpty() || age2.getText().isEmpty() || cmbgender2.getSelectedItem().equals("Select") || cmbpattype2.getSelectedItem().equals("Select") || cmbbloodgrp2.getSelectedItem().equals("Select") || billdate2.getDate() == null || bookdate.getDate() == null || cmbbedno2.getSelectedItem().equals("Select") || cmbwardno2.getSelectedItem().equals("Select") || cmbwardtype2.getSelectedItem().equals("Select") || regdate2.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(regdate2.getDate());
                Date_Format.format(bookdate.getDate());
                Date_Format.format(billdate2.getDate());
                String Qty1 = (txtpatid2.getText());
                String Qty2 = (txtFN2.getText());
                String Qty3 = (txtLN2.getText());
                String Qty4 = (cmbgender2.getSelectedItem().toString());
                String Qty5 = ((txtguardian2.getText()));
                String Qty6 = (cmbbloodgrp2.getSelectedItem().toString());
                String Qty7 = (cmbpattype2.getSelectedItem().toString());
                String Qty8 = (age2.getText());
                String Qty9 = ((JTextField) regdate2.getDateEditor().getUiComponent()).getText();
                String Qty10 = (cmbservice2.getSelectedItem().toString());
                String Qty11 = ((JTextField) bookdate.getDateEditor().getUiComponent()).getText();
                String Qty12 = (cmbwardtype2.getSelectedItem().toString());
                String Qty13 = (cmbwardno2.getSelectedItem().toString());
                String Qty14 = (cmbbedno2.getSelectedItem().toString());
                String Qty15 = (txtward_charges1.getText());
                String Qty16 = (txt_servicecharge.getText());
                String Qty17 = ((JTextField) billdate2.getDateEditor().getUiComponent()).getText();
                String Qty18 = (txt_total2.getText());
                String Qty19 = (cmbpay.getSelectedItem().toString());
                outpat_receipt.setText("");
                outpat_receipt.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tInpatient Bill Receipt\n"
                        + "\nPatient ID:\t\t" + Qty1
                        + "\nFirstname:\t\t" + Qty2
                        + "\nLastname:\t\t" + Qty3
                        + "\nGender:\t\t" + Qty4
                        + "\nGuardian:\t\t" + Qty5
                        + "\nBlood Group:\t\t" + Qty6
                        + "\nPatient Type:\t\t" + Qty7
                        + "\nAge:\t\t" + Qty8
                        + "\nReg Date:\t\t" + Qty9
                        + "\nService:\t\t" + Qty10
                        + "\nBooking Date:\t\t" + Qty11
                        + "\nWard Type:\t\t" + Qty12
                        + "\nWard No:\t\t" + Qty13
                        + "\nBed No:\t\t" + Qty14
                        + "\nWard Charges:\t" + Qty15
                        + "\nService Charges:\t" + Qty16
                        + "\nBilling Date:\t\t" + Qty17
                        + "\nTotal:\t\t" + Qty18
                        + "\nPayment Mode:\t" + Qty19
                        + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                        + "\n\tWE WISH YOU A QUICK RECOVERY");
                System.out.println("Setting done");
                try {
                    boolean complete = outpat_receipt.print();
                    if (complete) {
                        JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
                    } else {
                        JOptionPane.showMessageDialog(null, "PRINTING CANCELLED");
                    }
                } catch (PrinterException ex) {
                    Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                }
                txtsrch.setText("");
                txtpatid2.setText("");
                txtFN2.setText("");
                txtLN2.setText("");
                cmbgender2.setSelectedItem("Select");
                txtguardian2.setText("");
                cmbbloodgrp2.setSelectedItem("Select");
                cmbpattype2.setSelectedItem("Select");
                age2.setText("");
                regdate2.setDate(null);
                cmbservice2.setSelectedItem("Select");
                bookdate.setDate(null);
                billdate2.setDate(null);
                cmbwardtype2.setSelectedItem("Select");
                cmbwardno2.setSelectedItem("Select");
                cmbbedno2.removeAllItems();
                cmbbedno2.addItem("Select");
                txtward_charges1.setText("");
                txt_servicecharge.setText("");
                txt_total2.setText("");
                cmbpay.setSelectedItem("Select");
                txtreceipt_no.setText("");

            }

        }

    }

    public void srch() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from inpat_billing where Patid=?";
            System.out.println("Connecting to DB");
            pst = con.prepareStatement(sql);
            pst.setString(1, txtsrch.getText());
            rs = pst.executeQuery();
            System.out.println("Search Found........");
            if (rs.next()) {
                String add21 = rs.getObject("Bookdate").toString();
                java.util.Date were = new SimpleDateFormat("yyyy-MM-dd").parse(add21);
                bookdate.setDate(were);
                String add13 = rs.getString("Wrd_typ");
                cmbwardtype2.setSelectedItem(add13);
                String add14 = rs.getString("Wrd_no");
                cmbwardno2.setSelectedItem(add14);
                String add67 = rs.getString("Bed_no");
                cmbbedno2.setSelectedItem(add67);
                String add44 = rs.getString("Wrd_chrg");
                txtward_charges1.setText(add44);
                String add56 = rs.getString("Srv_chrg");
                txt_servicecharge.setText(add56);
                String add12 = rs.getObject("Billdate").toString();
                java.util.Date daer = new SimpleDateFormat("yyyy-MM-dd").parse(add12);
                billdate2.setDate(daer);
                String add15 = rs.getString("Total");
                txt_total2.setText(add15);
                String add35 = rs.getString("Pay");
                cmbpay.setSelectedItem(add35);
                String add66 = rs.getString("Rcp_No");
                txtreceipt_no.setText(add66);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Connection Problems...");
        }

    }

    public void Noofdays() {
        Date ago = bookdate.getDate();
        Date now = billdate2.getDate();
        long difference = (now.getTime() - ago.getTime()) / 86400000;
        int a, b, c, Ward_cost, total;
        a = Integer.parseInt(txtward_charges1.getText());
        c = Integer.parseInt(txt_servicecharge.getText());
        b = Math.toIntExact(difference);
        Ward_cost = a + c;
        total = Ward_cost * b;
        String output = String.valueOf(total);
        txt_total2.setText(output);
    }

    public void print_Pharm_receipt() {
        if (txtReceptNo.isEnabled() == true) {
            if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txtqnty.getText().isEmpty() || txtprc.getText().isEmpty() || txt_totalprc.getText().isEmpty() || combodrugid.getSelectedItem().equals("Select") || cmbunitofmeasure.getSelectedItem().equals("Select") || combogender.getSelectedItem().equals("Select") || cmbdrugname.getSelectedItem().equals("Select") || cmbcategory.getSelectedItem().equals("Select") || cmbdosage.getSelectedItem().equals("Select") || cmb_paymentmode.getSelectedItem().equals("Select") || Combopaid.getSelectedItem().equals("Select") || expdate2.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(expdate2.getDate());
                String Qty1 = (txtpatientid.getText());
                String Qty2 = (txtFN.getText());
                String Qty3 = (txtLN.getText());
                String Qty4 = (combogender.getSelectedItem().toString());
                String Qty5 = (cmbdrugname.getSelectedItem().toString());
                String Qty6 = (combodrugid.getSelectedItem().toString());
                String Qty7 = (cmbunitofmeasure.getSelectedItem().toString());
                String Qty8 = (cmbcategory.getSelectedItem().toString());
                String Qty9 = (txtqnty.getText());
                String Qty10 = ((JTextField) expdate2.getDateEditor().getUiComponent()).getText();
                String Qty11 = (txtprc.getText());
                String Qty12 = (txt_totalprc.getText());
                String Qty13 = (cmbdosage.getSelectedItem().toString());
                String Qty14 = (cmb_paymentmode.getSelectedItem().toString());
                String Qty15 = (txtReceptNo.getText());
                String Qty16 = (Combopaid.getSelectedItem().toString());
                outpat_receipt.setText("");
                outpat_receipt.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tPharmacy Bill Receipt\n"
                        + "\nPatient ID:\t\t" + Qty1
                        + "\nFirstname:\t\t" + Qty2
                        + "\nLastname:\t\t" + Qty3
                        + "\nGender:\t\t" + Qty4
                        + "\nDrug Name:\t\t" + Qty5
                        + "\nDrug Id:\t\t" + Qty6
                        + "\nUnit_msr:\t\t" + Qty7
                        + "\nCategory:\t\t" + Qty8
                        + "\nQuantity:\t\t" + Qty9
                        + "\nExp Date:\t\t" + Qty10
                        + "\nUnit Price:\t\t" + Qty11
                        + "\nPrice:\t\t" + Qty12
                        + "\nDosage:\t\t" + Qty13
                        + "\nPayment Mode:\t" + Qty14
                        + "\nReceipt No:\t\t" + Qty15
                        + "\nPaid:\t\t" + Qty16
                        + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                        + "\n\tWE WISH YOU A QUICK RECOVERY");
                try {
                    boolean complete = outpat_receipt.print();
                    if (complete) {
                        JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
                    } else {
                        JOptionPane.showMessageDialog(null, "PRINTING CANCELLED");
                    }
                } catch (PrinterException ex) {
                    Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.out.println("error.." + ex);
                }
                txt_search.setText("");
                txtpatientid.setText("");
                txtFN.setText("");
                txtLN.setText("");
                combogender.setSelectedItem("Select");
                cmbdrugname.setSelectedItem("Select");
                combodrugid.setSelectedItem("Select");
                cmbunitofmeasure.setSelectedItem("Select");
                cmbcategory.setSelectedItem("Select");
                txtqnty.setText("");
                expdate2.setDate(null);
                txtprc.setText("");
                txt_totalprc.setText("");
                cmbdosage.setSelectedItem("Select");
                cmb_paymentmode.setSelectedItem("Select");
                txtReceptNo.setText("");
                Combopaid.setSelectedItem("Select");
            }
        } else if (txtReceptNo.isEnabled() == false) {
            if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txtqnty.getText().isEmpty() || txtprc.getText().isEmpty() || txt_totalprc.getText().isEmpty() || combodrugid.getSelectedItem().equals("Select") || cmbunitofmeasure.getSelectedItem().equals("Select") || combogender.getSelectedItem().equals("Select") || cmbdrugname.getSelectedItem().equals("Select") || cmbcategory.getSelectedItem().equals("Select") || cmbdosage.getSelectedItem().equals("Select") || cmb_paymentmode.getSelectedItem().equals("Select") || Combopaid.getSelectedItem().equals("Select") || expdate2.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(expdate2.getDate());
                String Qty1 = (txtpatientid.getText());
                String Qty2 = (txtFN.getText());
                String Qty3 = (txtLN.getText());
                String Qty4 = (combogender.getSelectedItem().toString());
                String Qty5 = (cmbdrugname.getSelectedItem().toString());
                String Qty6 = (combodrugid.getSelectedItem().toString());
                String Qty7 = (cmbunitofmeasure.getSelectedItem().toString());
                String Qty8 = (cmbcategory.getSelectedItem().toString());
                String Qty9 = (txtqnty.getText());
                String Qty10 = ((JTextField) expdate2.getDateEditor().getUiComponent()).getText();
                String Qty11 = (txtprc.getText());
                String Qty12 = (txt_totalprc.getText());
                String Qty13 = (cmbdosage.getSelectedItem().toString());
                String Qty14 = (cmb_paymentmode.getSelectedItem().toString());
                String Qty16 = (Combopaid.getSelectedItem().toString());
                outpat_receipt.setText("");
                outpat_receipt.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tPharmacy Bill Receipt\n"
                        + "\nPatient ID:\t\t" + Qty1
                        + "\nFirstname:\t\t" + Qty2
                        + "\nLastname:\t\t" + Qty3
                        + "\nGender:\t\t" + Qty4
                        + "\nDrug Name:\t\t" + Qty5
                        + "\nDrug Id:\t\t" + Qty6
                        + "\nUnit_msr:\t\t" + Qty7
                        + "\nCategory:\t\t" + Qty8
                        + "\nQuantity:\t\t" + Qty9
                        + "\nExp Date:\t\t" + Qty10
                        + "\nUnit Price:\t\t" + Qty11
                        + "\nPrice:\t\t" + Qty12
                        + "\nDosage:\t\t" + Qty13
                        + "\nPayment Mode:\t" + Qty14
                        + "\nPaid:\t\t" + Qty16
                        + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                        + "\n\tWE WISH YOU A QUICK RECOVERY");
                try {
                    boolean compelete = outpat_receipt.print();
                    if (compelete) {
                        JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
                    } else {
                        JOptionPane.showMessageDialog(null, "PRINTING CANCELLED");
                    }
                } catch (PrinterException ex) {
                    Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                    System.out.println("error.." + ex);
                }
                txt_search.setText("");
                txtpatientid.setText("");
                txtFN.setText("");
                txtLN.setText("");
                combogender.setSelectedItem("Select");
                cmbdrugname.setSelectedItem("Select");
                combodrugid.setSelectedItem("Select");
                cmbunitofmeasure.setSelectedItem("Select");
                cmbcategory.setSelectedItem("Select");
                txtqnty.setText("");
                expdate2.setDate(null);
                txtprc.setText("");
                txt_totalprc.setText("");
                cmbdosage.setSelectedItem("Select");
                cmb_paymentmode.setSelectedItem("Select");
                txtReceptNo.setText("");
                Combopaid.setSelectedItem("Select");
            }
        }
    }

    public void autoId() {
        try {
            String sql = "SELECT Payid FROM emp_payment ORDER BY Payid DESC LIMIT 1";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            System.out.println("Selecting Employee id....");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String rnno = rs.getString("Payid");
                System.out.println("Paymentid....");
                int co = rnno.length();
                String txt = rnno.substring(0, 2);
                String num = rnno.substring(2, co);
                int n = Integer.parseInt(num);
                n++;
                String snum = Integer.toString(n);
                String ftxt = txt + snum;
                Payid.setText(ftxt);
            } else {
                Payid.setText("SA1000");
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

    public void salaries() {
        int salary = Integer.parseInt(txtbasicsalary.getText());
        int overtime = Integer.parseInt(txtovertime.getText());
        double eight = 8;
        double days = 25;
        double dbop = 0;
        double overtimeRate = 1.5;
        //calculate the total hours of overtime
        double total_overtime = overtime * overtimeRate;
        String x = String.valueOf(total_overtime);
        txt_total_Ovrtme.setText(x);
        //calculate overall overtime
        dbop = salary / days / eight;
        String s = String.valueOf(dbop);
        txt_Rate.setText(s);

        int med = Integer.parseInt(txtmedical.getText());
        int bonus = Integer.parseInt(txtbonus.getText());
        int other = Integer.parseInt(txtother.getText());
        int f = med + bonus + other;
        double calc = total_overtime * dbop + f;
        String t = String.valueOf(calc);
        txttotal.setText(t);
    }

    public void print_Sal_receipt() {
        if (Payid.getText().isEmpty() || txtempid.getText().isEmpty() || txtfn.getText().isEmpty() || txtln.getText().isEmpty() || cmb_gender.getSelectedItem().equals("Select") || DOB.getDate() == null || combodept.getSelectedItem().equals("Select") || combostatus.getSelectedItem().equals("Select") || datehired.getDate() == null || txtjobtittle.getText().isEmpty() || paydate.getDate() == null || cmb_paid.getSelectedItem().equals("Select") || txtbasicsalary.getText().isEmpty() || txtovertime.getText().isEmpty() || txtmedical.getText().isEmpty() || txtbonus.getText().isEmpty() || txtother.getText().isEmpty() || txt_Rate.getText().isEmpty() || txt_total_Ovrtme.getText().isEmpty() || txttotal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields Or Search Record To Print", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB.getDate());
            Date_Format.format(datehired.getDate());
            Date_Format.format(paydate.getDate());
            String Qty1 = (Payid.getText());
            String Qty2 = (txtempid.getText());
            String Qty3 = (txtfn.getText());
            String Qty4 = (txtln.getText());
            String Qty5 = (cmb_gender.getSelectedItem().toString());
            String Qty6 = ((JTextField) DOB.getDateEditor().getUiComponent()).getText();
            String Qty7 = (combodept.getSelectedItem().toString());
            String Qty8 = (combostatus.getSelectedItem().toString());
            String Qty9 = ((JTextField) datehired.getDateEditor().getUiComponent()).getText();
            String Qty10 = (txtjobtittle.getText());
            String Qty11 = ((JTextField) paydate.getDateEditor().getUiComponent()).getText();
            String Qty12 = (txtbasicsalary.getText());
            String Qty13 = (txtovertime.getText());
            String Qty14 = (txtmedical.getText());
            String Qty15 = (txtbonus.getText());
            String Qty16 = (txtother.getText());
            String Qty17 = (txt_Rate.getText());
            String Qty18 = (txt_total_Ovrtme.getText());
            String Qty19 = (txttotal.getText());
            String Qty20 = (cmb_paid.getSelectedItem().toString());
            outpat_receipt.setText("");
            outpat_receipt.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tSalary Receipt\n"
                    + "\nPayment ID:\t\t" + Qty1
                    + "\nEmployeee ID:\t\t" + Qty2
                    + "\nFirstname:\t\t" + Qty3
                    + "\nLastname:\t\t" + Qty4
                    + "\nGender:\t\t" + Qty5
                    + "\nDOB:\t\t" + Qty6
                    + "\nDept:\t\t" + Qty7
                    + "\nStatus:\t\t" + Qty8
                    + "\nDate Hired:\t\t" + Qty9
                    + "\nTittle:\t\t" + Qty10
                    + "\nPayed On:\t\t" + Qty11
                    + "\nBasic Sal:\t\t" + Qty12
                    + "\nOvertime:\t\t" + Qty13
                    + "\nMedical:\t\t" + Qty14
                    + "\nBonus:\t\t" + Qty15
                    + "\nOther:\t\t" + Qty16
                    + "\nRate:\t\t" + Qty17
                    + "\nTotal Overtime:\t" + Qty18
                    + "\nTotal:\t\t" + Qty19
                    + "\nPaid:\t\t" + Qty20
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
                    + "\n\tWE WISH YOU A QUICK RECOVERY");
            try {
                boolean compelete = outpat_receipt.print();
                if (compelete) {
                    JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
                } else {
                    JOptionPane.showMessageDialog(null, "PRINTING CANCELLED");
                }
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex);
                System.out.println("error.." + ex);
            }
            this.autoId();
            txt_Srch.setText("");
            txtempid.setText("");
            txtfn.setText("");
            txtln.setText("");
            cmb_gender.setSelectedItem("Select");
            DOB.setDate(null);
            combodept.setSelectedItem("Select");
            combostatus.setSelectedItem("Select");
            datehired.setDate(null);
            txtjobtittle.setText("");
            paydate.setDate(null);
            cmb_paid.setSelectedItem("Select");
            txtbasicsalary.setText("0");
            txtovertime.setText("0");
            txtmedical.setText("0");
            txtbonus.setText("0");
            txtother.setText("0");
            txt_Rate.setText("0");
            txt_total_Ovrtme.setText("0");
            txttotal.setText("0.00");
        }
    }

    public void username(String user) {
        lbluser.setText(user);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btn_getpat = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_srch = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jbtnsave2 = new javax.swing.JButton();
        Print = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txt_srch = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_patid = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_FN = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_LN = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbgender = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txt_guardian = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cmbmarital = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cmbBldgrp = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        cmbpattype = new javax.swing.JComboBox<>();
        jLabel54 = new javax.swing.JLabel();
        txt_age = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        Reg_date = new com.toedter.calendar.JDateChooser();
        jLabel56 = new javax.swing.JLabel();
        Bill_date = new com.toedter.calendar.JDateChooser();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        txt_charges = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        cmbservice = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        cmb_pay = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        txt_receipt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        outpat_receipt = new javax.swing.JTextArea();
        jPanel17 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        btnrefresh = new javax.swing.JButton();
        btn_outpat_srch = new javax.swing.JButton();
        btn_update_inpat = new javax.swing.JButton();
        btn_pat = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_prt = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        txtsrch = new javax.swing.JTextField();
        txtpatid2 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        txtFN2 = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        txtLN2 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        cmbgender2 = new javax.swing.JComboBox<>();
        jLabel76 = new javax.swing.JLabel();
        txtguardian2 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        cmbbloodgrp2 = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        cmbpattype2 = new javax.swing.JComboBox<>();
        jLabel79 = new javax.swing.JLabel();
        age2 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        regdate2 = new com.toedter.calendar.JDateChooser();
        jLabel86 = new javax.swing.JLabel();
        cmbservice2 = new javax.swing.JComboBox<>();
        jLabel84 = new javax.swing.JLabel();
        billdate2 = new com.toedter.calendar.JDateChooser();
        jLabel83 = new javax.swing.JLabel();
        cmbwardtype2 = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        cmbwardno2 = new javax.swing.JComboBox<>();
        jLabel81 = new javax.swing.JLabel();
        cmbbedno2 = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        txtward_charges1 = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txt_servicecharge = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        txt_total2 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        cmbpay = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        txtreceipt_no = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        bookdate = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        Btn_refresh = new javax.swing.JButton();
        btnsrch = new javax.swing.JButton();
        btn_updt = new javax.swing.JButton();
        btn_getpat_phar = new javax.swing.JButton();
        btn_save_Pharmbill = new javax.swing.JButton();
        btn_print_pharbill = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        expdate2 = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        quantity1 = new javax.swing.JLabel();
        txtqnty = new javax.swing.JTextField();
        category1 = new javax.swing.JLabel();
        cmbcategory = new javax.swing.JComboBox<>();
        unitofmeasur1 = new javax.swing.JLabel();
        cmbunitofmeasure = new javax.swing.JComboBox<>();
        drugname1 = new javax.swing.JLabel();
        combodrugid = new javax.swing.JComboBox<>();
        drugid1 = new javax.swing.JLabel();
        cmbdrugname = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        combogender = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        txtLN = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtFN = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtpatientid = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        price1 = new javax.swing.JLabel();
        txtprc = new javax.swing.JTextField();
        price2 = new javax.swing.JLabel();
        cmbdosage = new javax.swing.JComboBox<>();
        Price = new javax.swing.JLabel();
        txt_totalprc = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        cmb_paymentmode = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        txtReceptNo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Combopaid = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btn_RefresH = new javax.swing.JButton();
        btnsal_srch = new javax.swing.JButton();
        btnupdt = new javax.swing.JButton();
        btn_get_employee = new javax.swing.JButton();
        btn_sal_save = new javax.swing.JButton();
        btnprint = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        txt_Srch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtempid = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtfn = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtln = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        DOB = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        combodept = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        combostatus = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtjobtittle = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        lblbasicsalary = new javax.swing.JLabel();
        txtbasicsalary = new javax.swing.JTextField();
        lblovertime = new javax.swing.JLabel();
        txtovertime = new javax.swing.JTextField();
        lblmedical = new javax.swing.JLabel();
        Bonus2 = new javax.swing.JLabel();
        txtbonus = new javax.swing.JTextField();
        txtmedical = new javax.swing.JTextField();
        llovertime = new javax.swing.JLabel();
        txtother = new javax.swing.JTextField();
        lblrateperhour = new javax.swing.JLabel();
        txt_Rate = new javax.swing.JTextField();
        lblother = new javax.swing.JLabel();
        txt_total_Ovrtme = new javax.swing.JTextField();
        lblototal = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        cmb_paid = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        cmb_gender = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        datehired = new com.toedter.calendar.JDateChooser();
        jLabel42 = new javax.swing.JLabel();
        Payid = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        paydate = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        lbluser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Finance");
        setFocusCycleRoot(false);
        setMaximizedBounds(new java.awt.Rectangle(1230, 780, 1230, 780));
        setMaximumSize(new java.awt.Dimension(1230, 780));
        setMinimumSize(new java.awt.Dimension(1230, 780));
        setPreferredSize(new java.awt.Dimension(1230, 780));
        setResizable(false);
        setSize(new java.awt.Dimension(1230, 780));

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jTabbedPane2.setBackground(new java.awt.Color(153, 153, 255));
        jTabbedPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 8, true));
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jPanel18.setBackground(new java.awt.Color(204, 204, 255));

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_getpat.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_getpat.setText("PATIENT");
        btn_getpat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_getpat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_getpat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getpatActionPerformed(evt);
            }
        });

        btn_refresh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btn_refresh.setText("REFRESH");
        btn_refresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_srch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_srch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btn_srch.setText("SEARCH");
        btn_srch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_srch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_srch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_srchActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_30px_2.png"))); // NOI18N
        btn_update.setText("UPDATE");
        btn_update.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        jbtnsave2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtnsave2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        jbtnsave2.setText("SAVE");
        jbtnsave2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbtnsave2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnsave2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnsave2ActionPerformed(evt);
            }
        });

        Print.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_print_32px.png"))); // NOI18N
        Print.setText("PRINT");
        Print.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_update, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_getpat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnsave2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Print, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_srch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btn_getpat, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jbtnsave2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_srch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(Print, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel19.setBackground(new java.awt.Color(204, 204, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel17.setText("Search ");

        txt_srch.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Patient ID");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_patid.setEditable(false);
        txt_patid.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("First Name");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_FN.setEditable(false);
        txt_FN.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Last Name");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_LN.setEditable(false);
        txt_LN.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Gender");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbgender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbgender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Guardians Name");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_guardian.setEditable(false);
        txt_guardian.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Marital Status");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbmarital.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbmarital.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Married", "Single", "Divorced", "Widowed" }));
        cmbmarital.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel24.setText("Blood Group");
        jLabel24.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbBldgrp.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbBldgrp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "A", "B", "AB", "O", "A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+", "Unknown" }));
        cmbBldgrp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel53.setText("Patient Type");
        jLabel53.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbpattype.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbpattype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Inpatient", "Outpatient", "Pending" }));
        cmbpattype.setToolTipText("");
        cmbpattype.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel54.setText("Age");
        jLabel54.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_age.setEditable(false);
        txt_age.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel55.setText("Reg Date");
        jLabel55.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Reg_date.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Reg_date.setDateFormatString("yyyy-MM-dd");
        Reg_date.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel56.setText("Billing Date");
        jLabel56.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Bill_date.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Bill_date.setDateFormatString("yyyy-MM-dd");
        Bill_date.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel57.setText("Charges");
        jLabel57.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel58.setText("Total");
        jLabel58.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        txt_charges.setEditable(false);
        txt_charges.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txt_charges.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_chargesMouseClicked(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel59.setText("Service");
        jLabel59.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbservice.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbservice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "General", "Gynacological", "ICU", "Maternity", "Pediatric", "Prostratic", "Surgery" }));
        cmbservice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbserviceMouseClicked(evt);
            }
        });
        cmbservice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbserviceActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel29.setText("Payment Mode");
        jLabel29.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_pay.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_pay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "Cheque", "Debit Card", "Credit Card" }));
        cmb_pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_payActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel64.setText("Receipt No");
        jLabel64.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_receipt.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txt_receipt.setEnabled(false);

        outpat_receipt.setColumns(20);
        outpat_receipt.setRows(5);
        jScrollPane1.setViewportView(outpat_receipt);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_srch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbmarital, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_guardian))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_LN, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(11, 11, 11)
                                            .addComponent(cmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txt_patid, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txt_FN, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbpattype, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbBldgrp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10)
                                .addComponent(txt_age, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel19Layout.createSequentialGroup()
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                            .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmb_pay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txt_receipt)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(Reg_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbservice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(Bill_date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel19Layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel19Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(txt_charges))))))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jPanel19Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabel7});

        jPanel19Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbBldgrp, cmbgender, cmbmarital, txt_FN, txt_LN, txt_guardian, txt_patid});

        jPanel19Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel55, jLabel56, jLabel57, jLabel58, jLabel59});

        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_srch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_patid, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55)
                            .addComponent(Reg_date, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_FN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel59)
                                .addComponent(cmbservice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(12, 12, 12)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                        .addComponent(Bill_date, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_charges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel57))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel58)
                                    .addComponent(cmbmarital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_LN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cmbgender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(54, 54, 54)))
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(cmb_pay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel16)
                        .addComponent(txt_guardian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addGap(61, 61, 61)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_receipt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbBldgrp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel53)
                            .addComponent(cmbpattype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmb_pay, cmbservice, jLabel29, jLabel3, jLabel4, jLabel5, jLabel55, jLabel56, jLabel57, jLabel58, jLabel59, jLabel64, txt_FN, txt_LN, txt_charges, txt_guardian, txt_patid, txt_receipt, txt_srch, txt_total});

        jPanel19Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbBldgrp, cmbgender, cmbmarital, jLabel24});

        jPanel19Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel53, jLabel54});

        jPanel19Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Bill_date, Reg_date, cmbpattype, txt_age});

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Outpatients", jPanel18);

        jPanel17.setBackground(new java.awt.Color(153, 153, 255));
        jPanel17.setFocusTraversalPolicyProvider(true);
        jPanel17.setMaximumSize(new java.awt.Dimension(1208, 598));

        jPanel20.setBackground(new java.awt.Color(204, 204, 204));
        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

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

        btn_outpat_srch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_outpat_srch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btn_outpat_srch.setText("SEARCH");
        btn_outpat_srch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_outpat_srch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_outpat_srch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_outpat_srchActionPerformed(evt);
            }
        });

        btn_update_inpat.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_update_inpat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_30px_2.png"))); // NOI18N
        btn_update_inpat.setText("UPDATE");
        btn_update_inpat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_update_inpat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update_inpat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_inpatActionPerformed(evt);
            }
        });

        btn_pat.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_pat.setText("PATIENT");
        btn_pat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_pat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_pat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_patActionPerformed(evt);
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

        btn_prt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_prt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_print_32px.png"))); // NOI18N
        btn_prt.setText("PRINT");
        btn_prt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_prt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_prt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_update_inpat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnrefresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_pat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_save, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_prt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_outpat_srch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btn_pat, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_update_inpat, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_outpat_srch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_prt, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(153, 153, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel70.setText("Patient ID");
        jLabel70.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel71.setText("Search ");
        jLabel71.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsrch.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        txtpatid2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel72.setText("First Name");
        jLabel72.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtFN2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel73.setText("Last Name");
        jLabel73.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtLN2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel74.setText("Gender");
        jLabel74.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbgender2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbgender2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel76.setText("Guardians Name");
        jLabel76.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtguardian2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

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

        age2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel80.setText("Reg Date");
        jLabel80.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        regdate2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        regdate2.setDateFormatString("yyyy-MM-dd");
        regdate2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel86.setText("Service");
        jLabel86.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbservice2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbservice2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "General", "Gynacological", "ICU", "Maternity", "Pediatric", "Prostratic", "Surgery" }));
        cmbservice2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbservice2MouseClicked(evt);
            }
        });
        cmbservice2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbservice2ActionPerformed(evt);
            }
        });

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel84.setText("Billing Date");
        jLabel84.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        billdate2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        billdate2.setDateFormatString("yyyy-MM-dd");
        billdate2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel83.setText("Ward Type");
        jLabel83.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbwardtype2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbwardtype2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "ICU", "Male", "Female", "Gynecology", "Maternity" }));
        cmbwardtype2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbwardtype2MouseClicked(evt);
            }
        });
        cmbwardtype2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbwardtype2ActionPerformed(evt);
            }
        });

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel82.setText("Ward No");
        jLabel82.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbwardno2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbwardno2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "WD1", "WD2", "WD3", "WD4", "WD5" }));
        cmbwardno2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbwardno2ActionPerformed(evt);
            }
        });

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

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setText("Ward Charges");
        jLabel38.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtward_charges1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel60.setText("Service Charges");
        jLabel60.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txt_servicecharge.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel61.setText("Total");
        jLabel61.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_total2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_total2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_total2FocusGained(evt);
            }
        });
        txt_total2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_total2MouseClicked(evt);
            }
        });
        txt_total2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total2ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel28.setText("Payment Mode");
        jLabel28.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbpay.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbpay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "Cheque", "Debit Card", "Credit Card" }));
        cmbpay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbpayMouseClicked(evt);
            }
        });
        cmbpay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbpayActionPerformed(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel63.setText("Receipt No");
        jLabel63.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtreceipt_no.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtreceipt_no.setEnabled(false);

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel85.setText("Book Date");
        jLabel85.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        bookdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bookdate.setDateFormatString("yyyy-MM-dd");
        bookdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel21Layout.createSequentialGroup()
                                    .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cmbbloodgrp2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(27, 27, 27))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cmbpattype2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtFN2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtpatid2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLN2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(9, 9, 9)
                                        .addComponent(cmbgender2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(jLabel76)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtguardian2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                            .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cmbservice2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel21Layout.createSequentialGroup()
                                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                                        .addGap(1, 1, 1)
                                                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(regdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(age2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(23, 23, 23)))
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)))
                                    .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bookdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbwardno2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbbedno2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtward_charges1)
                                    .addComponent(txt_servicecharge)
                                    .addComponent(cmbwardtype2, 0, 303, Short.MAX_VALUE)))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                    .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(cmbpay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(txtreceipt_no))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(billdate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(txt_total2))))))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jLabel71)
                        .addGap(18, 18, 18)
                        .addComponent(txtsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel85)
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bookdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbwardtype2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel83))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel82)
                            .addComponent(cmbwardno2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel81)
                            .addComponent(cmbbedno2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtward_charges1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(txt_servicecharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel84)
                            .addComponent(billdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61)
                            .addComponent(txt_total2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(cmbpay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtreceipt_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63)))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtpatid2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFN2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel72))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLN2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel73))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbgender2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel74))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtguardian2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel76))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbbloodgrp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel78)
                            .addComponent(cmbpattype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(age2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel79))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(regdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel86)
                            .addComponent(cmbservice2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel21Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {age2, billdate2, bookdate, cmbbedno2, cmbbloodgrp2, cmbgender2, cmbpattype2, cmbservice2, cmbwardno2, cmbwardtype2, regdate2, txtFN2, txtLN2, txt_servicecharge, txt_total2, txtguardian2, txtpatid2, txtreceipt_no, txtward_charges1});

        jPanel21Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel28, jLabel38, jLabel60, jLabel61, jLabel63, jLabel74, jLabel76, jLabel77, jLabel78, jLabel79, jLabel80, jLabel81, jLabel82, jLabel83, jLabel84, jLabel86});

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Inpatients", jPanel17);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane1.addTab("Patient Billing", jPanel16);

        jPanel11.setBackground(new java.awt.Color(153, 153, 255));

        jPanel13.setBackground(new java.awt.Color(153, 153, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        Btn_refresh.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        Btn_refresh.setText("REFRESH");
        Btn_refresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_refreshActionPerformed(evt);
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

        btn_updt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_updt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_30px_2.png"))); // NOI18N
        btn_updt.setText("UPDATE");
        btn_updt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_updt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_updt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updtActionPerformed(evt);
            }
        });

        btn_getpat_phar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_getpat_phar.setText("PATIENT");
        btn_getpat_phar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_getpat_phar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_getpat_phar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getpat_pharActionPerformed(evt);
            }
        });

        btn_save_Pharmbill.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_save_Pharmbill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btn_save_Pharmbill.setText("SAVE");
        btn_save_Pharmbill.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_save_Pharmbill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save_Pharmbill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_PharmbillActionPerformed(evt);
            }
        });

        btn_print_pharbill.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_print_pharbill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_print_32px.png"))); // NOI18N
        btn_print_pharbill.setText("PRINT");
        btn_print_pharbill.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_print_pharbill.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_print_pharbill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print_pharbillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_updt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Btn_refresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_getpat_phar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_save_Pharmbill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_print_pharbill, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsrch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btn_getpat_phar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_save_Pharmbill, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_updt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btnsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(Btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_print_pharbill, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(153, 153, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        expdate2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        expdate2.setDateFormatString("yyyy-MM-dd");
        expdate2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setText("Expiry Date");
        jLabel20.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        quantity1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        quantity1.setText("Quantity");
        quantity1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtqnty.setEditable(false);
        txtqnty.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        category1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        category1.setText("Category");
        category1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbcategory.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbcategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Capsules", "Tablets", "Syrup", "Injection", "Liquid", "Gel", "Lotion", "Ointment", "Spray", "Cream", "Inhalers" }));

        unitofmeasur1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        unitofmeasur1.setText("Unit of Measure");
        unitofmeasur1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbunitofmeasure.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbunitofmeasure.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "ml", "mg", "mg/ml", "g", "g/ml" }));

        drugname1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        drugname1.setText("Drug Id");
        drugname1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combodrugid.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combodrugid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Acet12", "Amox97", "Ampi10", "Anti33", "Anti44", "Beta19", "Biph11", "Biph22", "Bisa12", "Bism45", "Carb47", "Cast10", "Ceti23", "Chlo32", "Chlo39", "Chlo98", "Cime24", "Clox32", "Cloz76", "Code09", "Code52", "Dicl09" }));

        drugid1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        drugid1.setText("Drug Name");
        drugid1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbdrugname.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbdrugname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Acetazolamide 125mg", "Amoxycilin 500mg", "Ampicilin 500mg", "Anticoagulant Citrate 10ml", "Antisnake Venom 10ml", "Betamethasone 2mg", "Biphasic Insulin 100 ml", "Biphasic Isophane 10ml", "Bisacodyl Suppository 5mg", "Bismuth Subsalicylate 262mg", "Carbenicillin 1g/ml", "Cascara Sagrada 125mg", "Castor Oil  10ml", "Cetirizine  Solution 1mg/ml", "Chloroquine 150ml", "Chlorpheniramine 2mg/5m", "Chlorpropamide 100mg", "Cimethdine  200mg/ml", "Cimethdine 200mg/ml", "Cloxacillin Sodium 250mg", "Clozapine 100mg", "Codeine Phosphate 15mg", "Codeine Phosphate 30mg/ml", "Diclofenac 50mg" }));
        cmbdrugname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbdrugnameActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel21.setText("Gender");
        jLabel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combogender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combogender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel44.setText("Last Name");
        jLabel44.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtLN.setEditable(false);
        txtLN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel45.setText("First Name");
        jLabel45.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtFN.setEditable(false);
        txtFN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel46.setText("Patient ID");
        jLabel46.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtpatientid.setEditable(false);
        txtpatientid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtpatientid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 1, true));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel47.setText("Search ");

        txt_search.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        price1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price1.setText("Unit Price");
        price1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtprc.setEditable(false);
        txtprc.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        price2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        price2.setText("Dosage");
        price2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmbdosage.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmbdosage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1x1", "1x2", "1x3", "1 Spn x3", "1 Injection", "Apply 3/day" }));

        Price.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Price.setText("Price");
        Price.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_totalprc.setEditable(false);
        txt_totalprc.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel49.setText("Payment Mode");
        jLabel49.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_paymentmode.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_paymentmode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "Cheque", "Debit Card", "Credit Card" }));
        cmb_paymentmode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_paymentmodeActionPerformed(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel62.setText("Receipt No");
        jLabel62.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtReceptNo.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtReceptNo.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Paid");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Combopaid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Combopaid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Yes", "No" }));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(quantity1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(unitofmeasur1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(category1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbcategory, javax.swing.GroupLayout.Alignment.LEADING, 0, 257, Short.MAX_VALUE)
                            .addComponent(cmbunitofmeasure, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtqnty)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(drugname1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(drugid1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbdrugname, 0, 0, Short.MAX_VALUE)
                            .addComponent(combodrugid, 0, 251, Short.MAX_VALUE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combogender, 0, 251, Short.MAX_VALUE)
                            .addComponent(txtLN)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(expdate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFN)
                            .addComponent(txtpatientid))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(price1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprc, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(price2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbdosage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_paymentmode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtReceptNo))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(Price, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_totalprc, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Combopaid, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(44, 44, 44))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jLabel47)
                .addGap(18, 18, 18)
                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 304, Short.MAX_VALUE))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {category1, drugid1, drugname1, jLabel20, jLabel21, jLabel44, jLabel45, jLabel46, quantity1, unitofmeasur1});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbcategory, cmbdrugname, cmbunitofmeasure, combodrugid, combogender});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, jLabel62});

        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Price)
                                    .addComponent(txt_totalprc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtprc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(price1))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(price2)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(cmbdosage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(cmb_paymentmode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel62)
                            .addComponent(txtReceptNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(Combopaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel45)
                                    .addComponent(txtFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(txtLN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(combogender, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(drugid1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(cmbdrugname, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(drugname1)
                            .addComponent(combodrugid, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(unitofmeasur1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbunitofmeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbcategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(quantity1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtqnty, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(expdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbcategory, cmbdrugname, cmbunitofmeasure, combodrugid, combogender});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {expdate2, txtqnty});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Combopaid, txtReceptNo, txt_totalprc, txtprc});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmb_paymentmode, cmbdosage});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, jLabel62});

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Pharmacy Billing", jPanel11);

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jPanel10.setBackground(new java.awt.Color(153, 153, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_RefresH.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_RefresH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btn_RefresH.setText("REFRESH");
        btn_RefresH.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_RefresH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_RefresH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefresHActionPerformed(evt);
            }
        });

        btnsal_srch.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnsal_srch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btnsal_srch.setText("SEARCH");
        btnsal_srch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsal_srch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsal_srch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsal_srchActionPerformed(evt);
            }
        });

        btnupdt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnupdt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_30px_2.png"))); // NOI18N
        btnupdt.setText("UPDATE");
        btnupdt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnupdt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnupdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdtActionPerformed(evt);
            }
        });

        btn_get_employee.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_get_employee.setText("EMPLOYEE");
        btn_get_employee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_get_employee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_get_employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_get_employeeActionPerformed(evt);
            }
        });

        btn_sal_save.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_sal_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_40px_1.png"))); // NOI18N
        btn_sal_save.setText("SAVE");
        btn_sal_save.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_sal_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_sal_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sal_saveActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnupdt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_RefresH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_get_employee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_sal_save, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnprint, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsal_srch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btn_get_employee, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_sal_save, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btnupdt, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btnsal_srch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_RefresH, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btnprint, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setOpaque(true);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel18.setText("Search ");

        txt_Srch.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Employee ID");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtempid.setEditable(false);
        txtempid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtempid.setFocusCycleRoot(true);
        txtempid.setFocusTraversalPolicyProvider(true);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("First Name");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtfn.setEditable(false);
        txtfn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Last Name");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtln.setEditable(false);
        txtln.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Date of Birth");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        DOB.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DOB.setDateFormatString("yyyy-MM-dd");
        DOB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Department");
        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combodept.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combodept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Catering", "Doctor", "Finance", "House Keeping", "HR", "Laboratory", "Nurse", "Pharmacy", "Reception" }));
        combodept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combodeptActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Status");
        jLabel13.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combostatus.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combostatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Active", "Suspended", "On Leave" }));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("Job Tittle");
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtjobtittle.setEditable(false);
        txtjobtittle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblbasicsalary.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblbasicsalary.setText("Basic Salary");
        lblbasicsalary.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtbasicsalary.setEditable(false);
        txtbasicsalary.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtbasicsalary.setText("0");

        lblovertime.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblovertime.setText("Overtime");
        lblovertime.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtovertime.setEditable(false);
        txtovertime.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtovertime.setText("0");

        lblmedical.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblmedical.setText("Medical");
        lblmedical.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Bonus2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Bonus2.setText("Bonus");
        Bonus2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtbonus.setEditable(false);
        txtbonus.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtbonus.setText("0");

        txtmedical.setEditable(false);
        txtmedical.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtmedical.setText("0");

        llovertime.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        llovertime.setText("Others");
        llovertime.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtother.setEditable(false);
        txtother.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtother.setText("0");

        lblrateperhour.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblrateperhour.setText("Rate Per Hour");
        lblrateperhour.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_Rate.setEditable(false);
        txt_Rate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_Rate.setText("0");

        lblother.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblother.setText("Total Overtime");
        lblother.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_total_Ovrtme.setEditable(false);
        txt_total_Ovrtme.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_total_Ovrtme.setText("0");

        lblototal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblototal.setText("Total");
        lblototal.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txttotal.setEditable(false);
        txttotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txttotal.setText("0.00");

        cmb_paid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmb_paid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Yes", "No" }));

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel48.setText("Paid");
        jLabel48.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lblbasicsalary, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbasicsalary, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Bonus2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblmedical, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmedical)
                            .addComponent(txtbonus)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lblovertime, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtovertime))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(llovertime, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtother))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lblrateperhour, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_Rate))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lblother, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_total_Ovrtme))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblototal, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_paid, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txttotal))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblbasicsalary)
                    .addComponent(txtbasicsalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblovertime)
                    .addComponent(txtovertime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(lblmedical)
                        .addGap(6, 6, 6)
                        .addComponent(Bonus2))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(txtmedical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtbonus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(llovertime)
                    .addComponent(txtother, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblrateperhour)
                    .addComponent(txt_Rate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblother)
                    .addComponent(txt_total_Ovrtme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblototal)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_paid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmb_paid, jLabel48});

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setText("Gender");
        jLabel41.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_gender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel36.setText("Date Hired");
        jLabel36.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        datehired.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        datehired.setDateFormatString("yyyy-MM-dd");
        datehired.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel42.setText("Payment Id");
        jLabel42.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Payid.setEditable(false);
        Payid.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        Payid.setText(" ");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel43.setText("Payment Date");
        jLabel43.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        paydate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        paydate.setDateFormatString("yyyy-MM-dd");
        paydate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(txt_Srch, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 299, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(datehired, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combostatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combodept, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtln))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtfn))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmb_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtjobtittle, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtempid, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                                    .addComponent(Payid)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(paydate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Srch, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(Payid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(txtempid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(txtfn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(txtln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(cmb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(DOB, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(combodept, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(combostatus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(datehired, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(txtjobtittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel43)
                            .addComponent(paydate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 44, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {DOB, txtln});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {combostatus, datehired});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Payid, txt_Srch, txtempid});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {paydate, txtjobtittle});

        jTabbedPane1.addTab("Salaries", jPanel4);

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
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
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
            .addComponent(jbtnchangepass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnuploadimage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 0));
        jLabel34.setText("KEEP YOUR USERID CONFIDENTIAL");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(212, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Manage Account", jPanel3);

        jPanel1.setBackground(new java.awt.Color(0, 51, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/dollar.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Finance");

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

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("Welcome");

        lbluser.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbluser, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(lbluser)))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel19, lbluser});

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Login_Finance HMS = new Login_Finance();
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
        Finance_EditAccount HMS = new Finance_EditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jbtnchangepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnchangepassActionPerformed
        String user = lbluser.getText();
        Finance_EditAccount HMS = new Finance_EditAccount();
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
        Finance_EditAccount HMS = new Finance_EditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String user = lbluser.getText();
        Finance_EditAccount HMS = new Finance_EditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseExited
        jButton3.setBackground(Color.white);
    }//GEN-LAST:event_jButton3MouseExited

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        jButton3.setBackground(Color.gray);
    }//GEN-LAST:event_jButton3MouseEntered

    private void combodeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combodeptActionPerformed
        if (combodept.getSelectedItem().equals("Select")) {
            combodept.setSelectedItem("Select");
        } else {
            String dept = combodept.getSelectedItem().toString();
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from salaries where Dept=?";
                pst = con.prepareStatement(sql);
                pst.setString(1, dept);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String add1 = rs.getString("Bsc_sal");
                    txtbasicsalary.setText(add1);
                    String add2 = rs.getString("Ovr_tme");
                    txtovertime.setText(add2);
                    String add3 = rs.getString("Medical");
                    txtmedical.setText(add3);
                    String add4 = rs.getString("Bonus");
                    txtbonus.setText(add4);
                    String add5 = rs.getString("Other");
                    txtother.setText(add5);
                    this.salaries();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_combodeptActionPerformed

    private void cmb_paymentmodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_paymentmodeActionPerformed
        if (cmb_paymentmode.getSelectedItem().equals("Cheque")) {
            txtReceptNo.setEnabled(true);
        } else if (cmb_paymentmode.getSelectedItem().equals("Select") || cmb_paymentmode.getSelectedItem().equals("Cash") || cmb_paymentmode.getSelectedItem().equals("Debit Card") || cmb_paymentmode.getSelectedItem().equals("Credit Card")) {
            txtReceptNo.setText("");
            txtReceptNo.setEnabled(false);
        }
    }//GEN-LAST:event_cmb_paymentmodeActionPerformed

    private void cmbdrugnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbdrugnameActionPerformed
        if (cmbdrugname.getSelectedItem().equals("Select")) {
            cmbdrugname.setSelectedItem("Select");
        } else {
            this.Drugset();
        }
    }//GEN-LAST:event_cmbdrugnameActionPerformed

    private void btn_getpat_pharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getpat_pharActionPerformed
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
        expdate2.setDate(null);
        txtprc.setText("");
        txt_totalprc.setText("");
        cmbdosage.setSelectedItem("Select");
        cmb_paymentmode.setSelectedItem("Select");
        txtReceptNo.setText("");
        Combopaid.setSelectedItem("Select");
        if (txt_search.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from issuedrugs where Patientid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_search.getText());
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
                    expdate2.setDate(dat);
                    String add11 = rs.getString("Unit_price");
                    txtprc.setText(add11);
                    String add12 = rs.getString("Price");
                    txt_totalprc.setText(add12);
                    String add14 = rs.getString("Dosage");
                    cmbdosage.setSelectedItem(add14);
                    System.out.println("Record Found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_btn_getpat_pharActionPerformed

    private void Btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_refreshActionPerformed
        txt_search.setText("");
        txtpatientid.setText("");
        txtFN.setText("");
        txtLN.setText("");
        combogender.setSelectedItem("Select");
        cmbdrugname.setSelectedItem("Select");
        combodrugid.setSelectedItem("Select");
        cmbunitofmeasure.setSelectedItem("Select");
        cmbcategory.setSelectedItem("Select");
        txtqnty.setText("");
        expdate2.setDate(null);
        txtprc.setText("");
        txt_totalprc.setText("");
        cmbdosage.setSelectedItem("Select");
        cmb_paymentmode.setSelectedItem("Select");
        txtReceptNo.setText("");
        Combopaid.setSelectedItem("Select");
    }//GEN-LAST:event_Btn_refreshActionPerformed

    private void cmbpayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbpayActionPerformed
        if (cmbpay.getSelectedItem().equals("Select")) {
            txtreceipt_no.setText("");
            txtreceipt_no.setEnabled(false);
        } else if (cmbpay.getSelectedItem().equals("Cash")) {
            txtreceipt_no.setText("");
            txtreceipt_no.setEnabled(false);
        } else if (cmbpay.getSelectedItem().equals("Cheque")) {
            txtreceipt_no.setEnabled(true);
            txtreceipt_no.setText("");
        } else if (cmbpay.getSelectedItem().equals("Debit Card")) {
            txtreceipt_no.setText("");
            txtreceipt_no.setEnabled(false);
        } else if (cmbpay.getSelectedItem().equals("Credit Card")) {
            txtreceipt_no.setText("");
            txtreceipt_no.setEnabled(false);
        }
    }//GEN-LAST:event_cmbpayActionPerformed

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

    private void cmbservice2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbservice2ActionPerformed
        if (cmbservice2.getSelectedItem().equals("Select")) {
            cmbservice2.setSelectedItem("Select");
        } else {
            String Service = cmbservice2.getSelectedItem().toString();
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select Total from services where Service=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, Service);
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add2 = rs.getString("Total");
                    txt_servicecharge.setText(add2);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "SERVICE NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_cmbservice2ActionPerformed

    private void btn_patActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_patActionPerformed
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        txtpatid2.setText("");
        txtFN2.setText("");
        txtLN2.setText("");
        cmbgender2.setSelectedItem("Select");
        txtguardian2.setText("");
        cmbbloodgrp2.setSelectedItem("Select");
        cmbpattype2.setSelectedItem("Select");
        age2.setText("");
        regdate2.setDate(null);
        cmbservice2.setSelectedItem("Select");
        bookdate.setDate(null);
        billdate2.setDate(null);
        cmbwardtype2.setSelectedItem("Select");
        cmbwardno2.setSelectedItem("Select");
        cmbbedno2.removeAllItems();
        cmbbedno2.addItem("Select");
        txtward_charges1.setText("");
        txt_servicecharge.setText("");
        txt_total2.setText("");
        cmbpay.setSelectedItem("Select");
        txtreceipt_no.setText("");
        if (txtsrch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Get Patient ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select Patid,FN,LN,Gender,Guardian,Bld_grp,Pat_type,Age,Regdate,Service,Bookdate,Wardtype,Wardno,Bedno from pat_assignward where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsrch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patid");
                    txtpatid2.setText(add1);
                    String add2 = rs.getString("FN");
                    txtFN2.setText(add2);
                    String add3 = rs.getString("LN");
                    txtLN2.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmbgender2.setSelectedItem(add4);
                    String add5 = rs.getString("Guardian");
                    txtguardian2.setText(add5);
                    String add6 = rs.getString("Bld_grp");
                    cmbbloodgrp2.setSelectedItem(add6);
                    String add7 = rs.getString("Pat_type");
                    cmbpattype2.setSelectedItem(add7);
                    String add8 = rs.getString("Age");
                    age2.setText(add8);
                    String add9 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add9);
                    regdate2.setDate(dat);
                    String add10 = rs.getString("Service");
                    cmbservice2.setSelectedItem(add10);
                    String add11 = rs.getObject("Bookdate").toString();
                    java.util.Date book = new SimpleDateFormat("yyyy-MM-dd").parse(add11);
                    bookdate.setDate(book);
                    String add12 = rs.getString("Wardtype");
                    cmbwardtype2.setSelectedItem(add12);
                    String add13 = rs.getString("Wardno");
                    cmbwardno2.setSelectedItem(add13);
                    String add14 = rs.getString("Bedno");
                    cmbbedno2.setSelectedItem(add14);
                    // this.getdate();
                    //  this.getward();
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_btn_patActionPerformed

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        txtsrch.setText("");
        txtpatid2.setText("");
        txtFN2.setText("");
        txtLN2.setText("");
        cmbgender2.setSelectedItem("Select");
        txtguardian2.setText("");
        cmbbloodgrp2.setSelectedItem("Select");
        cmbpattype2.setSelectedItem("Select");
        age2.setText("");
        regdate2.setDate(null);
        cmbservice2.setSelectedItem("Select");
        bookdate.setDate(null);
        billdate2.setDate(null);
        cmbwardtype2.setSelectedItem("Select");
        cmbwardno2.setSelectedItem("Select");
        cmbbedno2.removeAllItems();
        cmbbedno2.addItem("Select");
        txtward_charges1.setText("");
        txt_servicecharge.setText("");
        txt_total2.setText("");
        cmbpay.setSelectedItem("Select");
        txtreceipt_no.setText("");
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void cmb_payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_payActionPerformed
        if (cmb_pay.getSelectedItem().equals("Cheque")) {
            txt_receipt.setEnabled(true);
        } else if (cmb_pay.getSelectedItem().equals("Cash") || cmb_pay.getSelectedItem().equals("Debit Card") || cmb_pay.getSelectedItem().equals("Credit Card")) {
            txt_receipt.setText("");
            txt_receipt.setEnabled(false);
        }
    }//GEN-LAST:event_cmb_payActionPerformed

    private void cmbserviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbserviceActionPerformed
        if (cmbservice.getSelectedItem().equals("Select")) {
            cmbservice.setSelectedItem("Select");
        } else {
            String Service = cmbservice.getSelectedItem().toString();
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from services where Service=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, Service);
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Service");
                    cmbservice.setSelectedItem(add1);
                    String add2 = rs.getString("Charges");
                    txt_charges.setText(add2);
                    String add3 = rs.getString("Total");
                    txt_total.setText(add3);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_cmbserviceActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        txt_srch.setText("");
        txt_patid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmbgender.setSelectedItem("Select");
        txt_guardian.setText("");
        cmbmarital.setSelectedItem("Select");
        cmbBldgrp.setSelectedItem("Select");
        cmbpattype.setSelectedItem("Select");
        txt_age.setText("");
        Reg_date.setDate(null);
        Bill_date.setDate(null);
        cmbservice.setSelectedItem("Select");
        txt_charges.setText("");
        txt_total.setText("");
        cmb_pay.setSelectedItem("Select");
        txt_receipt.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_getpatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getpatActionPerformed
        txt_patid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmbgender.setSelectedItem("Select");
        txt_guardian.setText("");
        cmbmarital.setSelectedItem("Select");
        cmbBldgrp.setSelectedItem("Select");
        cmbpattype.setSelectedItem("Select");
        txt_age.setText("");
        Reg_date.setDate(null);
        Bill_date.setDate(null);
        cmbservice.setSelectedItem("Select");
        txt_charges.setText("");
        txt_total.setText("");
        cmb_pay.setSelectedItem("Select");
        txt_receipt.setText("");
        if (txt_srch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Get Patient ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pat_nurse where Patientid=?";
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
                    cmbgender.setSelectedItem(add4);
                    String add5 = rs.getString("Marital");
                    cmbmarital.setSelectedItem(add5);
                    String add6 = rs.getString("Guardian");
                    txt_guardian.setText(add6);
                    String add7 = rs.getString("Bld_grp");
                    cmbBldgrp.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    cmbpattype.setSelectedItem(add8);
                    String add10 = rs.getString("Age");
                    txt_age.setText(add10);
                    String add11 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add11);
                    Reg_date.setDate(dat);
                    String add12 = rs.getString("Service");
                    cmbservice.setSelectedItem(add12);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_btn_getpatActionPerformed

    private void btn_RefresHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefresHActionPerformed
        this.autoId();
        txt_Srch.setText("");
        txtempid.setText("");
        txtfn.setText("");
        txtln.setText("");
        cmb_gender.setSelectedItem("Select");
        DOB.setDate(null);
        combodept.setSelectedItem("Select");
        combostatus.setSelectedItem("Select");
        datehired.setDate(null);
        txtjobtittle.setText("");
        paydate.setDate(null);
        cmb_paid.setSelectedItem("Select");
        txtbasicsalary.setText("0");
        txtovertime.setText("0");
        txtmedical.setText("0");
        txtbonus.setText("0");
        txtother.setText("0");
        txt_Rate.setText("0");
        txt_total_Ovrtme.setText("0");
        txttotal.setText("0.00");
    }//GEN-LAST:event_btn_RefresHActionPerformed

    private void btn_get_employeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_get_employeeActionPerformed
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection con = null;
        this.autoId();
        txtempid.setText("");
        txtfn.setText("");
        txtln.setText("");
        cmb_gender.setSelectedItem("Select");
        DOB.setDate(null);
        combodept.setSelectedItem("Select");
        combostatus.setSelectedItem("Select");
        datehired.setDate(null);
        txtjobtittle.setText("");
        paydate.setDate(null);
        cmb_paid.setSelectedItem("Select");
        txtbasicsalary.setText("0");
        txtovertime.setText("0");
        txtmedical.setText("0");
        txtbonus.setText("0");
        txtother.setText("0");
        txt_Rate.setText("0");
        txt_total_Ovrtme.setText("0");
        txttotal.setText("0.00");
        if (txt_Srch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Empid To Get Employee ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from employees where Empid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_Srch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Empid");
                    txtempid.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txtfn.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txtln.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmb_gender.setSelectedItem(add4);
                    String add5 = rs.getObject("DOB").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add5);
                    DOB.setDate(dat);
                    String add6 = rs.getString("Department");
                    combodept.setSelectedItem(add6);
                    String add7 = rs.getString("Status");
                    combostatus.setSelectedItem(add7);
                    String add8 = rs.getObject("Datehired").toString();
                    java.util.Date hire = new SimpleDateFormat("yyyy-MM-dd").parse(add8);
                    datehired.setDate(hire);
                    String add9 = rs.getString("Jobtittle");
                    txtjobtittle.setText(add9);
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                    System.out.println("No Record Found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btn_get_employeeActionPerformed

    private void btn_save_PharmbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_PharmbillActionPerformed
        if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txtqnty.getText().isEmpty() || txtprc.getText().isEmpty() || txt_totalprc.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbdrugname.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select DrugName", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combodrugid.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drugid", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbunitofmeasure.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Unit Of Measure", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbcategory.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Category", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbdosage.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Dosage", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_paymentmode.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Payment Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Combopaid.getSelectedItem().equals("Select") || Combopaid.getSelectedItem().equals("No")) {
            JOptionPane.showMessageDialog(this, "Only Paid Records Are Saved", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (expdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Expiry Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = combogender.getSelectedItem().toString();
            String Drgname = cmbdrugname.getSelectedItem().toString();
            String Drugid = combodrugid.getSelectedItem().toString();
            String Unit_Msr = cmbunitofmeasure.getSelectedItem().toString();
            String Cat = cmbcategory.getSelectedItem().toString();
            String Dose = cmbdosage.getSelectedItem().toString();
            String Pay = cmb_paymentmode.getSelectedItem().toString();
            String Paid = Combopaid.getSelectedItem().toString();
            String Expdate = ((JTextField) expdate2.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(expdate2.getDate());
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "insert into pharm_billing (Patid,FN,LN,Gender,Drugname,Drugid,Unit_msr,Category,Quantity,Expdate,Unit_prc,Prc,Dosage,Pay,Rcp_no,Paid)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, txtpatientid.getText());
                pst.setString(2, txtFN.getText());
                pst.setString(3, txtLN.getText());
                pst.setString(4, Gender);
                pst.setString(5, Drgname);
                pst.setString(6, Drugid);
                pst.setString(7, Unit_Msr);
                pst.setString(8, Cat);
                pst.setString(9, txtqnty.getText());
                pst.setString(10, Expdate);
                pst.setString(11, txtprc.getText());
                pst.setString(12, txt_totalprc.getText());
                pst.setString(13, Dose);
                pst.setString(14, Pay);
                pst.setString(15, txtReceptNo.getText());
                pst.setString(16, Paid);
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.print_Pharm_receipt();
                txt_search.setText("");
                txtpatientid.setText("");
                txtFN.setText("");
                txtLN.setText("");
                combogender.setSelectedItem("Select");
                cmbdrugname.setSelectedItem("Select");
                combodrugid.setSelectedItem("Select");
                cmbunitofmeasure.setSelectedItem("Select");
                cmbcategory.setSelectedItem("Select");
                txtqnty.setText("");
                expdate2.setDate(null);
                txtprc.setText("");
                txt_totalprc.setText("");
                cmbdosage.setSelectedItem("Select");
                cmb_paymentmode.setSelectedItem("Select");
                txtReceptNo.setText("");
                Combopaid.setSelectedItem("Select");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection to db problems");
            }
        }

    }//GEN-LAST:event_btn_save_PharmbillActionPerformed

    private void jbtnsave2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnsave2ActionPerformed
        String Age = txt_age.getText();
        if (txt_srch.getText().isEmpty() || txt_patid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_age.getText().isEmpty() || txt_charges.getText().isEmpty() || txt_total.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbgender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbmarital.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbBldgrp.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbpattype.getSelectedItem().equals("Select") || cmbpattype.getSelectedItem().equals("Pending") || cmbpattype.getSelectedItem().equals("Inpatient")) {
            JOptionPane.showMessageDialog(this, "Please Only Outpatients Are Billable", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Service", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_pay.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Payment Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Reg_date.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select the Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Bill_date.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select the Billing Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txt_receipt.isEnabled() == true && txt_receipt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill details Of The Cheque", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmbgender.getSelectedItem().toString();
            String Marital = cmbmarital.getSelectedItem().toString();
            String Bld_grp = cmbBldgrp.getSelectedItem().toString();
            String Pattype = cmbpattype.getSelectedItem().toString();
            String Service = cmbservice.getSelectedItem().toString();
            String Pay = cmb_pay.getSelectedItem().toString();
            String Regdate = ((JTextField) Reg_date.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Reg_date.getDate());
            String Billdate = ((JTextField) Bill_date.getDateEditor().getUiComponent()).getText();
            Date_Format.format(Bill_date.getDate());
            try {
                String sql = "insert into outpat_billing (Patid,FN,LN,Gender,Marital,Guardian,Bld_grp,Pat_type,Age,Regdate,Service,Billdate,Charges,Total,Pay_mode,Rcp_No) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to DB.....");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_patid.getText());
                pst.setString(2, txt_FN.getText());
                pst.setString(3, txt_LN.getText());
                pst.setString(4, Gender);
                pst.setString(5, Marital);
                pst.setString(6, txt_guardian.getText());
                pst.setString(7, Bld_grp);
                pst.setString(8, Pattype);
                pst.setString(9, txt_age.getText());
                pst.setString(10, Regdate);
                pst.setString(11, Service);
                pst.setString(12, Billdate);
                pst.setString(13, txt_charges.getText());
                pst.setString(14, txt_total.getText());
                pst.setString(15, Pay);
                pst.setString(16, txt_receipt.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.print_OutPat_receipt();
                txt_srch.setText("");
                txt_patid.setText("");
                txt_FN.setText("");
                txt_LN.setText("");
                cmbgender.setSelectedItem("Select");
                txt_guardian.setText("");
                cmbmarital.setSelectedItem("Select");
                cmbBldgrp.setSelectedItem("Select");
                cmbpattype.setSelectedItem("Select");
                txt_age.setText("");
                Reg_date.setDate(null);
                Bill_date.setDate(null);
                cmbservice.setSelectedItem("Select");
                txt_charges.setText("");
                txt_total.setText("");
                cmb_pay.setSelectedItem("Select");
                txt_receipt.setText("");
                System.out.println("outpatieint billed");
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
    }//GEN-LAST:event_jbtnsave2ActionPerformed

    private void btn_srchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_srchActionPerformed
        if (txt_srch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Get Patient ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from outpat_billing where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_srch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add16 = rs.getString("Rcp_No");
                    txt_receipt.setText(add16);
                    String add15 = rs.getString("Pay_mode");
                    cmb_pay.setSelectedItem(add15);
                    String add12 = rs.getObject("Billdate").toString();
                    java.util.Date daer = new SimpleDateFormat("yyyy-MM-dd").parse(add12);
                    Bill_date.setDate(daer);
                    String add1 = rs.getString("Patid");
                    txt_patid.setText(add1);
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
                    cmbBldgrp.setSelectedItem(add7);
                    String add8 = rs.getString("Pat_type");
                    cmbpattype.setSelectedItem(add8);
                    String add10 = rs.getString("Age");
                    txt_age.setText(add10);
                    String add11 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add11);
                    Reg_date.setDate(dat);
                    String add18 = rs.getString("Service");
                    cmbservice.setSelectedItem(add18);
                    String add13 = rs.getString("Charges");
                    txt_charges.setText(add13);
                    String add14 = rs.getString("Total");
                    txt_total.setText(add14);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_btn_srchActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        this.Noofdays();
        String Age = age2.getText();
        if (txtpatid2.getText().isEmpty() || txtFN2.getText().isEmpty() || txtLN2.getText().isEmpty() || txtguardian2.getText().isEmpty() || txt_servicecharge.getText().isEmpty() || txt_total2.getText().isEmpty() || txtward_charges1.getText().isEmpty() || age2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbgender2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbbloodgrp2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbpattype2.getSelectedItem().equals("Select") || cmbpattype2.getSelectedItem().equals("Pending") || cmbpattype2.getSelectedItem().equals("Outpatient")) {
            JOptionPane.showMessageDialog(this, "Please Only Inpatients Can Be Billed", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (regdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Reg Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbservice2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Service", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbwardtype2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Wardtype", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbwardno2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Wardno", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbbedno2.getSelectedItem().equals("Select") || cmbbedno2.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select Bedno", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (bookdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Booking Dates", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (billdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Billing Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbpay.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Slectt Payment Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txtreceipt_no.isEnabled() == true && txtreceipt_no.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill details Of The Cheque", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmbgender2.getSelectedItem().toString();
            String Bldgrp = cmbbloodgrp2.getSelectedItem().toString();
            String Pattyp = cmbpattype2.getSelectedItem().toString();
            String Service = cmbservice2.getSelectedItem().toString();
            String Wrdtyp = cmbwardtype2.getSelectedItem().toString();
            String Wrdno = cmbwardno2.getSelectedItem().toString();
            String Bdno = cmbbedno2.getSelectedItem().toString();
            String Payment = cmbpay.getSelectedItem().toString();
            String Reg_date = ((JTextField) regdate2.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(regdate2.getDate());
            String Book = ((JTextField) bookdate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(bookdate.getDate());
            String Bill = ((JTextField) billdate2.getDateEditor().getUiComponent()).getText();
            Date_Format.format(billdate2.getDate());
            try {
                String sql = "insert into  inpat_billing (Patid,FN,LN,Gender,Guardian,Bld_grp,Pat_typ,Age,Regdate,Service,Bookdate,Wrd_typ,Wrd_no,Bed_no,Wrd_chrg,Srv_chrg,Billdate,Total,Pay,Rcp_no) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to DB.....");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtpatid2.getText());
                pst.setString(2, txtFN2.getText());
                pst.setString(3, txtLN2.getText());
                pst.setString(4, Gender);
                pst.setString(5, txtguardian2.getText());
                pst.setString(6, Bldgrp);
                pst.setString(7, Pattyp);
                pst.setString(8, age2.getText());
                pst.setString(9, Reg_date);
                pst.setString(10, Service);
                pst.setString(11, Book);
                pst.setString(12, Wrdtyp);
                pst.setString(13, Wrdno);
                pst.setString(14, Bdno);
                pst.setString(15, txtward_charges1.getText());
                pst.setString(16, txt_servicecharge.getText());
                pst.setString(17, Bill);
                pst.setString(18, txt_total2.getText());
                pst.setString(19, Payment);
                pst.setString(20, txtreceipt_no.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.print_inpat_bill();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection to db problems");
            }
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintActionPerformed
        this.print_OutPat_receipt();
    }//GEN-LAST:event_PrintActionPerformed

    private void cmbserviceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbserviceMouseClicked
        if (cmbservice.getSelectedItem().equals("Select")) {
            cmbservice.setSelectedItem("Select");
        } else {
            String Service = cmbservice.getSelectedItem().toString();
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from services where Service=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, Service);
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Service");
                    cmbservice.setSelectedItem(add1);
                    String add2 = rs.getString("Charges");
                    txt_charges.setText(add2);
                    String add3 = rs.getString("Total");
                    txt_total.setText(add3);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_cmbserviceMouseClicked

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        if (cmbservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(null, "Please Select Service First");
        }
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_chargesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_chargesMouseClicked
        if (cmbservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(null, "Please Select Service First");
        }
    }//GEN-LAST:event_txt_chargesMouseClicked

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        String Age = txt_age.getText();
        if (txt_srch.getText().isEmpty() || txt_patid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_guardian.getText().isEmpty() || txt_age.getText().isEmpty() || txt_charges.getText().isEmpty() || txt_total.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbgender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbmarital.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Marital Status", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbBldgrp.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbpattype.getSelectedItem().equals("Select") || cmbpattype.getSelectedItem().equals("Pending") || cmbpattype.getSelectedItem().equals("Outpatient")) {
            JOptionPane.showMessageDialog(this, "Please Only Outpatients Are Billable", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbservice.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Service", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_pay.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select the Payment Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Reg_date.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select the Registration Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Bill_date.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select the Billing Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txt_receipt.isEnabled() == true && txt_receipt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill details Of The Cheque", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmbgender.getSelectedItem().toString();
            String Marital = cmbmarital.getSelectedItem().toString();
            String Bld_grp = cmbBldgrp.getSelectedItem().toString();
            String Pattype = cmbpattype.getSelectedItem().toString();
            String Service = cmbservice.getSelectedItem().toString();
            String Pay = cmb_pay.getSelectedItem().toString();
            String Reg_da = ((JTextField) Reg_date.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(Reg_date.getDate());
            String Bill_da = ((JTextField) Bill_date.getDateEditor().getUiComponent()).getText();
            Date_Format.format(Bill_date.getDate());
            try {
                String sql = "update outpat_billing set FN='" + txt_FN.getText() + "',LN='" + txt_LN.getText() + "',Gender='" + Gender + "',Marital='" + Marital + "',Guardian='" + txt_guardian.getText() + "',Bld_grp='" + Bld_grp + "',Pat_type='" + Pattype + "',Age='" + txt_age.getText() + "',Regdate='" + Reg_da + "',Service='" + Service + "',Billdate='" + Bill_da + "',Charges='" + txt_charges.getText() + "',Total='" + txt_total.getText() + "',Pay_mode='" + Pay + "',Rcp_No='" + txt_receipt.getText() + "' where Patid='" + txt_patid.getText() + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                System.out.println("Connecting to Db");
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.print_OutPat_receipt();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_prtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prtActionPerformed
        this.print_inpat_bill();
    }//GEN-LAST:event_btn_prtActionPerformed

    private void cmbservice2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbservice2MouseClicked
        if (cmbservice2.getSelectedItem().equals("Select")) {
            cmbservice2.setSelectedItem("Select");
        } else {
            String Service = cmbservice2.getSelectedItem().toString();
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from services where Service=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, Service);
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Service");
                    cmbservice2.setSelectedItem(add1);
                    String add2 = rs.getString("Charges");
                    txt_servicecharge.setText(add2);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_cmbservice2MouseClicked

    private void cmbbedno2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbbedno2MouseClicked
        if (cmbwardno2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Ward Type And Ward No First", "Error", JOptionPane.ERROR_MESSAGE);
            cmbbedno2.removeAllItems();
            cmbbedno2.addItem("Select");
        }
    }//GEN-LAST:event_cmbbedno2MouseClicked

    private void cmbwardtype2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbwardtype2ActionPerformed
        if (cmbwardtype2.getSelectedItem().equals("Select")) {
            cmbwardtype2.setSelectedItem("Select");
        } else {
            String Ward = cmbwardtype2.getSelectedItem().toString();
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select Wrd_Chrg from wardcharges where Wardtype=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, Ward);
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add2 = rs.getString("Wrd_Chrg");
                    txtward_charges1.setText(add2);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "WARD CHARGE NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_cmbwardtype2ActionPerformed

    private void cmbwardtype2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbwardtype2MouseClicked
        if (cmbwardtype2.getSelectedItem().equals("Select")) {
            cmbwardtype2.setSelectedItem("Select");
        } else {
            String Ward = cmbwardtype2.getSelectedItem().toString();
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select Wrd_Chrg from wardcharges where Wardtype=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, Ward);
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add2 = rs.getString("Wrd_Chrg");
                    txtward_charges1.setText(add2);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "WARD CHARGE NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_cmbwardtype2MouseClicked

    private void btn_update_inpatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update_inpatActionPerformed
        String Age = age2.getText();
        if (txtpatid2.getText().isEmpty() || txtFN2.getText().isEmpty() || txtLN2.getText().isEmpty() || txtguardian2.getText().isEmpty() || txt_servicecharge.getText().isEmpty() || txt_total2.getText().isEmpty() || txtward_charges1.getText().isEmpty() || age2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbgender2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbbloodgrp2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Blood Group", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbpattype2.getSelectedItem().equals("Select") || cmbpattype2.getSelectedItem().equals("Pending") || cmbpattype2.getSelectedItem().equals("Outpatient")) {
            JOptionPane.showMessageDialog(this, "Please Only Inpatients Can Be Billed", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (regdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Reg Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbservice2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Service", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbwardtype2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Wardtype", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbwardno2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Wardno", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbbedno2.getSelectedItem().equals("Select") || cmbbedno2.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select Bedno", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (bookdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Booking Dates", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (billdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Billing Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbpay.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Slectt Payment Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Age.length() > 3) {
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Age", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txtreceipt_no.isEnabled() == true && txtreceipt_no.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill details Of The Cheque", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmbgender2.getSelectedItem().toString();
            String Bldgrp = cmbbloodgrp2.getSelectedItem().toString();
            String Pattyp = cmbpattype2.getSelectedItem().toString();
            String Service = cmbservice2.getSelectedItem().toString();
            String Wrdtyp = cmbwardtype2.getSelectedItem().toString();
            String Wrdno = cmbwardno2.getSelectedItem().toString();
            String Bdno = cmbbedno2.getSelectedItem().toString();
            String Payment = cmbpay.getSelectedItem().toString();
            String Reg_date = ((JTextField) regdate2.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(regdate2.getDate());
            String Book = ((JTextField) bookdate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(bookdate.getDate());
            String Bill = ((JTextField) billdate2.getDateEditor().getUiComponent()).getText();
            Date_Format.format(billdate2.getDate());
            try {
                String sql = "update inpat_billing set FN='" + txtFN2.getText() + "',LN='" + txtLN2.getText() + "',Gender='" + Gender + "',Guardian='" + txtguardian2.getText() + "',Bld_grp='" + Bldgrp + "',Pat_typ='" + Pattyp + "',Age='" + age2.getText() + "',Regdate='" + Reg_date + "',Service='" + Service + "',Bookdate='" + Book + "',Wrd_typ='" + Wrdtyp + "',Wrd_no='" + Wrdno + "',Bed_no='" + Bdno + "',Wrd_chrg='" + txtward_charges1.getText() + "',Srv_chrg='" + txt_servicecharge.getText() + "',Billdate='" + Bill + "',Total='" + txt_total2.getText() + "',Pay='" + Payment + "',Rcp_No='" + txtreceipt_no.getText() + "' where Patid='" + txtpatid2.getText() + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.print_inpat_bill();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btn_update_inpatActionPerformed

    private void btn_outpat_srchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_outpat_srchActionPerformed
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        txtpatid2.setText("");
        txtFN2.setText("");
        txtLN2.setText("");
        cmbgender2.setSelectedItem("Select");
        txtguardian2.setText("");
        cmbbloodgrp2.setSelectedItem("Select");
        cmbpattype2.setSelectedItem("Select");
        age2.setText("");
        regdate2.setDate(null);
        cmbservice2.setSelectedItem("Select");
        bookdate.setDate(null);
        billdate2.setDate(null);
        cmbwardtype2.setSelectedItem("Select");
        cmbwardno2.setSelectedItem("Select");
        cmbbedno2.removeAllItems();
        cmbbedno2.addItem("Select");
        txtward_charges1.setText("");
        txt_servicecharge.setText("");
        txt_total2.setText("");
        cmbpay.setSelectedItem("Select");
        txtreceipt_no.setText("");
        if (txtsrch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Get Patient ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from inpat_billing where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsrch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Patid");
                    txtpatid2.setText(add1);
                    String add2 = rs.getString("FN");
                    txtFN2.setText(add2);
                    String add3 = rs.getString("LN");
                    txtLN2.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmbgender2.setSelectedItem(add4);
                    String add5 = rs.getString("Guardian");
                    txtguardian2.setText(add5);
                    String add6 = rs.getString("Bld_grp");
                    cmbbloodgrp2.setSelectedItem(add6);
                    String add7 = rs.getString("Pat_typ");
                    cmbpattype2.setSelectedItem(add7);
                    String add8 = rs.getString("Age");
                    age2.setText(add8);
                    String add9 = rs.getObject("Regdate").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add9);
                    regdate2.setDate(dat);
                    String add10 = rs.getString("Service");
                    cmbservice2.setSelectedItem(add10);
                    String add11 = rs.getObject("Bookdate").toString();
                    java.util.Date book = new SimpleDateFormat("yyyy-MM-dd").parse(add11);
                    bookdate.setDate(book);
                    String add12 = rs.getString("Wrd_typ");
                    cmbwardtype2.setSelectedItem(add12);
                    String add13 = rs.getString("Wrd_no");
                    cmbwardno2.setSelectedItem(add13);
                    String add14 = rs.getString("Bed_no");
                    cmbbedno2.setSelectedItem(add14);
                    String add15 = rs.getString("Wrd_chrg");
                    txtward_charges1.setText(add15);
                    String add16 = rs.getString("Srv_chrg");
                    txt_servicecharge.setText(add16);
                    String add17 = rs.getObject("Billdate").toString();
                    java.util.Date bill = new SimpleDateFormat("yyyy-MM-dd").parse(add17);
                    billdate2.setDate(bill);
                    String add18 = rs.getString("Total");
                    txt_total2.setText(add18);
                    String add19 = rs.getString("Pay");
                    cmbpay.setSelectedItem(add19);
                    String add20 = rs.getString("Rcp_no");
                    txtreceipt_no.setText(add20);
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_btn_outpat_srchActionPerformed

    private void btn_sal_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sal_saveActionPerformed
        if (combostatus.getSelectedItem().equals("Select") || combostatus.getSelectedItem().equals("Suspended")) {
            JOptionPane.showMessageDialog(this, "The Status Of The Employee Is Not valid For Payment", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_paid.getSelectedItem().equals("Select") || cmb_paid.getSelectedItem().equals("No")) {
            JOptionPane.showMessageDialog(this, "Please Confirm Payment", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmb_gender.getSelectedItem().toString();
            String Dept = combodept.getSelectedItem().toString();
            String Status = combostatus.getSelectedItem().toString();
            String Paid = cmb_paid.getSelectedItem().toString();
            String Dob = ((JTextField) DOB.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB.getDate());
            String DH = ((JTextField) datehired.getDateEditor().getUiComponent()).getText();
            Date_Format.format(datehired.getDate());
            String PD = ((JTextField) paydate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(paydate.getDate());
            try {
                String sql = "INSERT INTO emp_payment(Payid,Empid,FN,LN,Gender,DOB,Dept,Status,Hire_date,Job_ttle,Regdate,Bsc_Sal,Ovr_tme,Medical,Bonus,Other,Total_Ovrtme,RPH,Total,Paid) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.setString(1, Payid.getText());
                pst.setString(2, txtempid.getText());
                pst.setString(3, txtfn.getText());
                pst.setString(4, txtln.getText());
                pst.setString(5, Gender);
                pst.setString(6, Dob);
                pst.setString(7, Dept);
                pst.setString(8, Status);
                pst.setString(9, DH);
                pst.setString(10, txtjobtittle.getText());
                pst.setString(11, PD);
                pst.setString(12, txtbasicsalary.getText());
                pst.setString(13, txtovertime.getText());
                pst.setString(14, txtmedical.getText());
                pst.setString(15, txtbonus.getText());
                pst.setString(16, txtother.getText());
                pst.setString(17, txt_Rate.getText());
                pst.setString(18, txt_total_Ovrtme.getText());
                pst.setString(19, txttotal.getText());
                pst.setString(20, Paid);
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO EMPLOYEE");
                this.print_Sal_receipt();
                this.autoId();
                txt_Srch.setText("");
                txtempid.setText("");
                txtfn.setText("");
                txtln.setText("");
                cmb_gender.setSelectedItem("Select");
                DOB.setDate(null);
                combodept.setSelectedItem("Select");
                combostatus.setSelectedItem("Select");
                datehired.setDate(null);
                txtjobtittle.setText("");
                paydate.setDate(null);
                cmb_paid.setSelectedItem("Select");
                txtbasicsalary.setText("0");
                txtovertime.setText("0");
                txtmedical.setText("0");
                txtbonus.setText("0");
                txtother.setText("0");
                txt_Rate.setText("0");
                txt_total_Ovrtme.setText("0");
                txttotal.setText("0.00");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btn_sal_saveActionPerformed

    private void btn_print_pharbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_pharbillActionPerformed
        print_Pharm_receipt();
    }//GEN-LAST:event_btn_print_pharbillActionPerformed

    private void btnprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintActionPerformed
        this.print_Sal_receipt();
    }//GEN-LAST:event_btnprintActionPerformed

    private void btn_updtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updtActionPerformed
        if (txtpatientid.getText().isEmpty() || txtFN.getText().isEmpty() || txtLN.getText().isEmpty() || txtqnty.getText().isEmpty() || txtprc.getText().isEmpty() || txt_totalprc.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbdrugname.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select DrugName", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combodrugid.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drugid", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbunitofmeasure.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Unit Of Measure", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbcategory.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Category", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmbdosage.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Dosage", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_paymentmode.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Payment Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Combopaid.getSelectedItem().equals("Select") || Combopaid.getSelectedItem().equals("No")) {
            JOptionPane.showMessageDialog(this, "Only Paid Records Are Saved", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (expdate2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select Drug Expiry Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = combogender.getSelectedItem().toString();
            String Drgname = cmbdrugname.getSelectedItem().toString();
            String Drugid = combodrugid.getSelectedItem().toString();
            String Unit_Msr = cmbunitofmeasure.getSelectedItem().toString();
            String Cat = cmbcategory.getSelectedItem().toString();
            String Dose = cmbdosage.getSelectedItem().toString();
            String Pay = cmb_paymentmode.getSelectedItem().toString();
            String Paid = Combopaid.getSelectedItem().toString();
            String Expdate = ((JTextField) expdate2.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(expdate2.getDate());
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "update pharm_billing set FN='" + txtFN.getText() + "',LN='" + txtLN.getText() + "' "
                        + ",Gender='" + Gender + "',Drugname='" + Drgname + "'"
                        + ",Drugid='" + Drugid + "',Unit_msr='" + Unit_Msr + "',Category='" + Cat + "',Quantity='" + txtqnty.getText() + "',"
                        + "Expdate='" + Expdate + "',Unit_prc='" + txtprc.getText() + "',Prc='" + txt_totalprc.getText() + "',Dosage='" + Dose + "',Pay='" + Pay + "',Rcp_no='" + txtReceptNo.getText() + "',Paid='" + Paid + "' where Patid='" + txtpatientid.getText() + "'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO PATIENT");
                this.print_Pharm_receipt();
                txt_search.setText("");
                txtpatientid.setText("");
                txtFN.setText("");
                txtLN.setText("");
                combogender.setSelectedItem("Select");
                cmbdrugname.setSelectedItem("Select");
                combodrugid.setSelectedItem("Select");
                cmbunitofmeasure.setSelectedItem("Select");
                cmbcategory.setSelectedItem("Select");
                txtqnty.setText("");
                expdate2.setDate(null);
                txtprc.setText("");
                txt_totalprc.setText("");
                cmbdosage.setSelectedItem("Select");
                cmb_paymentmode.setSelectedItem("Select");
                txtReceptNo.setText("");
                Combopaid.setSelectedItem("Select");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    con.close();
                    System.out.println("Connection Closed");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btn_updtActionPerformed

    private void btnsrchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsrchActionPerformed
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
        expdate2.setDate(null);
        txtprc.setText("");
        txt_totalprc.setText("");
        cmbdosage.setSelectedItem("Select");
        cmb_paymentmode.setSelectedItem("Select");
        txtReceptNo.setText("");
        Combopaid.setSelectedItem("Select");
        if (txt_search.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from pharm_billing where Patid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_search.getText());
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
                    expdate2.setDate(dat);
                    String add11 = rs.getString("Unit_prc");
                    txtprc.setText(add11);
                    String add12 = rs.getString("Prc");
                    txt_totalprc.setText(add12);
                    String add14 = rs.getString("Dosage");
                    cmbdosage.setSelectedItem(add14);
                    String add15 = rs.getString("Pay");
                    cmb_paymentmode.setSelectedItem(add15);
                    String add16 = rs.getString("Rcp_no");
                    txtReceptNo.setText(add16);
                    String add17 = rs.getString("Paid");
                    Combopaid.setSelectedItem(add17);
                    System.out.println("Record Found");
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
                System.out.println("Connection Problems...");
            }
        }
    }//GEN-LAST:event_btnsrchActionPerformed

    private void btnupdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdtActionPerformed
        if (combostatus.getSelectedItem().equals("Select") || combostatus.getSelectedItem().equals("Suspended")) {
            JOptionPane.showMessageDialog(this, "The Status Of The Employee Is Not valid For Payment", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_paid.getSelectedItem().equals("Select") || cmb_paid.getSelectedItem().equals("No")) {
            JOptionPane.showMessageDialog(this, "Please Confirm Payment", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmb_gender.getSelectedItem().toString();
            String Dept = combodept.getSelectedItem().toString();
            String Status = combostatus.getSelectedItem().toString();
            String Paid = cmb_paid.getSelectedItem().toString();
            String Dob = ((JTextField) DOB.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB.getDate());
            String DH = ((JTextField) datehired.getDateEditor().getUiComponent()).getText();
            Date_Format.format(datehired.getDate());
            String PD = ((JTextField) paydate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(paydate.getDate());
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = " Update emp_payment set Empid='" + txtempid.getText() + "',"
                        + "FN='" + txtfn.getText() + "',LN='" + txtln.getText() + "',Gender='" + Gender + "',"
                        + "DOB='" + Dob + "',Dept='" + Dept + "',Status='" + Status + "',"
                        + "Hire_date='" + DH + "',Job_ttle='" + txtjobtittle.getText() + "',Regdate='" + PD + "',"
                        + "Bsc_Sal='" + txtbasicsalary.getText() + "',Ovr_tme='" + txtovertime.getText() + "',Medical='" + txtmedical.getText() + "',"
                        + "Bonus='" + txtbonus.getText() + "',Other='" + txtother.getText() + "',Total_Ovrtme='" + txt_total_Ovrtme.getText() + "',"
                        + " RPH='" + txt_Rate.getText() + "',Total='" + txttotal.getText() + "',Paid='" + Paid + "' where Payid='" + Payid.getText() + "'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO EMPLOYEE");
                this.print_Sal_receipt();
                this.autoId();
                txt_Srch.setText("");
                txtempid.setText("");
                txtfn.setText("");
                txtln.setText("");
                cmb_gender.setSelectedItem("Select");
                DOB.setDate(null);
                combodept.setSelectedItem("Select");
                combostatus.setSelectedItem("Select");
                datehired.setDate(null);
                txtjobtittle.setText("");
                paydate.setDate(null);
                cmb_paid.setSelectedItem("Select");
                txtbasicsalary.setText("0");
                txtovertime.setText("0");
                txtmedical.setText("0");
                txtbonus.setText("0");
                txtother.setText("0");
                txt_Rate.setText("0");
                txt_total_Ovrtme.setText("0");
                txttotal.setText("0.00");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btnupdtActionPerformed

    private void btnsal_srchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsal_srchActionPerformed
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection con = null;
        this.autoId();
        txtempid.setText("");
        txtfn.setText("");
        txtln.setText("");
        cmb_gender.setSelectedItem("Select");
        DOB.setDate(null);
        combodept.setSelectedItem("Select");
        combostatus.setSelectedItem("Select");
        datehired.setDate(null);
        txtjobtittle.setText("");
        paydate.setDate(null);
        cmb_paid.setSelectedItem("Select");
        txtbasicsalary.setText("0");
        txtovertime.setText("0");
        txtmedical.setText("0");
        txtbonus.setText("0");
        txtother.setText("0");
        txt_Rate.setText("0");
        txt_total_Ovrtme.setText("0");
        txttotal.setText("0.00");
        if (txt_Srch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Empid To Get Employee ");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from emp_payment where Empid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txt_Srch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Payid");
                    Payid.setText(add1);
                    String add22 = rs.getString("Empid");
                    txtempid.setText(add22);
                    String add2 = rs.getString("FN");
                    txtfn.setText(add2);
                    String add3 = rs.getString("LN");
                    txtln.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmb_gender.setSelectedItem(add4);
                    String add5 = rs.getObject("DOB").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add5);
                    DOB.setDate(dat);
                    String add6 = rs.getString("Dept");
                    combodept.setSelectedItem(add6);
                    String add7 = rs.getString("Status");
                    combostatus.setSelectedItem(add7);
                    String add8 = rs.getObject("Hire_date").toString();
                    java.util.Date hire = new SimpleDateFormat("yyyy-MM-dd").parse(add8);
                    datehired.setDate(hire);
                    String add9 = rs.getString("Job_ttle");
                    txtjobtittle.setText(add9);
                    String add33 = rs.getObject("Regdate").toString();
                    java.util.Date hir = new SimpleDateFormat("yyyy-MM-dd").parse(add33);
                    paydate.setDate(hir);
                    String add19 = rs.getString("Paid");
                    cmb_paid.setSelectedItem(add19);
                } else {
                    JOptionPane.showMessageDialog(null, "RECORD NOT FOUND");
                    System.out.println("No Record Found");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_btnsal_srchActionPerformed

    private void txt_total2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total2ActionPerformed
        this.Noofdays();
    }//GEN-LAST:event_txt_total2ActionPerformed

    private void txt_total2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_total2FocusGained
        this.Noofdays();
    }//GEN-LAST:event_txt_total2FocusGained

    private void txt_total2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_total2MouseClicked
        this.Noofdays();
    }//GEN-LAST:event_txt_total2MouseClicked

    private void cmbpayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbpayMouseClicked
        this.Noofdays();
    }//GEN-LAST:event_cmbpayMouseClicked

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
            java.util.logging.Logger.getLogger(Finance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Finance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Finance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Finance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Finance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Bill_date;
    private javax.swing.JLabel Bonus2;
    private javax.swing.JButton Btn_refresh;
    private javax.swing.JComboBox<String> Combopaid;
    private com.toedter.calendar.JDateChooser DOB;
    private javax.swing.JTextField Payid;
    private javax.swing.JLabel Price;
    private javax.swing.JButton Print;
    private com.toedter.calendar.JDateChooser Reg_date;
    private javax.swing.JTextField age2;
    private com.toedter.calendar.JDateChooser billdate2;
    private com.toedter.calendar.JDateChooser bookdate;
    private javax.swing.JButton btn_RefresH;
    private javax.swing.JButton btn_get_employee;
    private javax.swing.JButton btn_getpat;
    private javax.swing.JButton btn_getpat_phar;
    private javax.swing.JButton btn_outpat_srch;
    private javax.swing.JButton btn_pat;
    private javax.swing.JButton btn_print_pharbill;
    private javax.swing.JButton btn_prt;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_sal_save;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_save_Pharmbill;
    private javax.swing.JButton btn_srch;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton btn_update_inpat;
    private javax.swing.JButton btn_updt;
    private javax.swing.JButton btnprint;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton btnsal_srch;
    private javax.swing.JButton btnsrch;
    private javax.swing.JButton btnupdt;
    private javax.swing.JButton btnuploadimage;
    private javax.swing.JLabel category1;
    private javax.swing.JComboBox<String> cmbBldgrp;
    private javax.swing.JComboBox<String> cmb_gender;
    private javax.swing.JComboBox<String> cmb_paid;
    private javax.swing.JComboBox<String> cmb_pay;
    private javax.swing.JComboBox<String> cmb_paymentmode;
    private javax.swing.JComboBox<String> cmbbedno2;
    private javax.swing.JComboBox<String> cmbbloodgrp2;
    private javax.swing.JComboBox<String> cmbcategory;
    private javax.swing.JComboBox<String> cmbdosage;
    private javax.swing.JComboBox<String> cmbdrugname;
    private javax.swing.JComboBox<String> cmbgender;
    private javax.swing.JComboBox<String> cmbgender2;
    private javax.swing.JComboBox<String> cmbmarital;
    private javax.swing.JComboBox<String> cmbpattype;
    private javax.swing.JComboBox<String> cmbpattype2;
    private javax.swing.JComboBox<String> cmbpay;
    private javax.swing.JComboBox<String> cmbservice;
    private javax.swing.JComboBox<String> cmbservice2;
    private javax.swing.JComboBox<String> cmbunitofmeasure;
    private javax.swing.JComboBox<String> cmbwardno2;
    private javax.swing.JComboBox<String> cmbwardtype2;
    private javax.swing.JComboBox<String> combodept;
    private javax.swing.JComboBox<String> combodrugid;
    private javax.swing.JComboBox<String> combogender;
    private javax.swing.JComboBox<String> combostatus;
    private com.toedter.calendar.JDateChooser datehired;
    private javax.swing.JLabel drugid1;
    private javax.swing.JLabel drugname1;
    private com.toedter.calendar.JDateChooser expdate2;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton jbtnchangepass;
    private javax.swing.JButton jbtnsave2;
    private javax.swing.JLabel lblbasicsalary;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lblmedical;
    private javax.swing.JLabel lblother;
    private javax.swing.JLabel lblototal;
    private javax.swing.JLabel lblovertime;
    private javax.swing.JLabel lblrateperhour;
    private javax.swing.JLabel lbluser;
    private javax.swing.JLabel llovertime;
    private javax.swing.JTextArea outpat_receipt;
    private com.toedter.calendar.JDateChooser paydate;
    private javax.swing.JLabel price1;
    private javax.swing.JLabel price2;
    private javax.swing.JLabel quantity1;
    private com.toedter.calendar.JDateChooser regdate2;
    private javax.swing.JTextField txtFN;
    private javax.swing.JTextField txtFN2;
    private javax.swing.JTextField txtLN;
    private javax.swing.JTextField txtLN2;
    private javax.swing.JTextField txtReceptNo;
    private javax.swing.JTextField txt_FN;
    private javax.swing.JTextField txt_LN;
    private javax.swing.JTextField txt_Rate;
    private javax.swing.JTextField txt_Srch;
    private javax.swing.JTextField txt_age;
    private javax.swing.JTextField txt_charges;
    private javax.swing.JTextField txt_guardian;
    private javax.swing.JTextField txt_patid;
    private javax.swing.JTextField txt_receipt;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_servicecharge;
    private javax.swing.JTextField txt_srch;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total2;
    private javax.swing.JTextField txt_total_Ovrtme;
    private javax.swing.JTextField txt_totalprc;
    private javax.swing.JTextField txtbasicsalary;
    private javax.swing.JTextField txtbonus;
    private javax.swing.JTextField txtempid;
    private javax.swing.JTextField txtfn;
    private javax.swing.JTextField txtguardian2;
    private javax.swing.JTextField txtjobtittle;
    private javax.swing.JTextField txtln;
    private javax.swing.JTextField txtmedical;
    private javax.swing.JTextField txtother;
    private javax.swing.JTextField txtovertime;
    private javax.swing.JTextField txtpatid2;
    private javax.swing.JTextField txtpatientid;
    private javax.swing.JTextField txtprc;
    private javax.swing.JTextField txtqnty;
    private javax.swing.JTextField txtreceipt_no;
    private javax.swing.JTextField txtsrch;
    private javax.swing.JTextField txttotal;
    private javax.swing.JTextField txtward_charges1;
    private javax.swing.JLabel unitofmeasur1;
    // End of variables declaration//GEN-END:variables
}
