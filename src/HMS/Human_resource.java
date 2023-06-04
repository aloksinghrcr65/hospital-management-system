package HMS;

import java.awt.Color;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.JTextField;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 * @author Allein
 */
public class Human_resource extends javax.swing.JFrame {

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String Imagename = null;
    byte[] uimage = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    public Human_resource() {
        initComponents();
        System.out.println("Application Started\n" + dtf.format(now));
        autoId();
        view_table();
    }

    public void autoId() {
        try {
            String sql = "SELECT Empid FROM employees ORDER BY Empid DESC LIMIT 1";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            System.out.println("Connecting to DB....");
            System.out.println("Selecting Patient id....");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String rnno = rs.getString("Empid");
                System.out.println("Patientid....");
                int co = rnno.length();
                String txt = rnno.substring(0, 2);
                String num = rnno.substring(2, co);
                int n = Integer.parseInt(num);
                n++;
                String snum = Integer.toString(n);
                String ftxt = txt + snum;
                txtempid.setText(ftxt);
            } else {
                txtempid.setText("EP1000");
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

    public void view_table() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
            String sql = "select * from employees";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            all_employees_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            all_employees_table.setModel(DbUtils.resultSetToTableModel(rs));
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

    public void printEmp() {
        if (txtempid.getText().isEmpty() || txtfn.getText().isEmpty() || txtln.getText().isEmpty() || txtjobtittle.getText().isEmpty() || combodept.getSelectedItem().equals("Select") || combostatus.getSelectedItem().equals("Select") || DOB.getDate() == null || datehired.getDate() == null || combogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB.getDate());
            Date_Format.format(datehired.getDate());
            String Qty1 = (txtempid.getText());
            String Qty2 = (txtfn.getText());
            String Qty3 = (txtln.getText());
            String Qty4 = (combogender.getSelectedItem().toString());
            String Qty5 = ((JTextField) DOB.getDateEditor().getUiComponent()).getText();
            String Qty6 = (combodept.getSelectedItem().toString());
            String Qty7 = (combostatus.getSelectedItem().toString());
            String Qty8 = ((JTextField) datehired.getDateEditor().getUiComponent()).getText();
            String Qty9 = (txtjobtittle.getText());
            txt_print_allemp.setText("");
            txt_print_allemp.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tEmployee Receipt\n"
                    + "\nEmployee ID:\t\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nGender:\t\t" + Qty4
                    + "\nDOB:\t\t" + Qty5
                    + "\nDepartment:\t\t" + Qty6
                    + "\nStatus:\t\t" + Qty7
                    + "\nDate Hired:\t\t" + Qty8
                    + "\nJob Tittle:\t\t" + Qty9
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
            );
            try {
                txt_print_allemp.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            autoId();
            txtempid.setText("");
            txtfn.setText("");
            txtln.setText("");
            DOB.setDate(null);
            combodept.setSelectedItem("Select");
            combostatus.setSelectedItem("Select");
            combogender.setSelectedItem("Select");
            datehired.setDate(null);
            txtsearch.setText("");
            txtjobtittle.setText("");
        }
    }

    public void printEmpClear() {
        if (txtempid2.getText().isEmpty() || txtfn2.getText().isEmpty() || txtln2.getText().isEmpty() || txtjobtittle2.getText().isEmpty() || combodept2.getSelectedItem().equals("Select") || combostatus2.getSelectedItem().equals("Select") || DOB2.getDate() == null || datehired2.getDate() == null || combogender2.getSelectedItem().equals("Select") || cleardate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB2.getDate());
            Date_Format.format(datehired2.getDate());
            Date_Format.format(cleardate.getDate());
            String Qty1 = (txtempid2.getText());
            String Qty2 = (txtfn2.getText());
            String Qty3 = (txtln2.getText());
            String Qty4 = (combogender2.getSelectedItem().toString());
            String Qty5 = ((JTextField) DOB2.getDateEditor().getUiComponent()).getText();
            String Qty6 = (combodept2.getSelectedItem().toString());
            String Qty7 = (combostatus2.getSelectedItem().toString());
            String Qty8 = ((JTextField) datehired2.getDateEditor().getUiComponent()).getText();
            String Qty9 = (txtjobtittle2.getText());
            String Qty10 = ((JTextField) cleardate.getDateEditor().getUiComponent()).getText();
            txt_print_allemp.setText("");
            txt_print_allemp.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tClear Employee Receipt\n"
                    + "\nEmployee ID:\t\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nGender:\t\t" + Qty4
                    + "\nDOB:\t\t" + Qty5
                    + "\nDepartment:\t\t" + Qty6
                    + "\nStatus:\t\t" + Qty7
                    + "\nDate Hired:\t\t" + Qty8
                    + "\nJob Tittle:\t\t" + Qty9
                    + "\nClear Date:\t\t" + Qty10
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
            );
            try {
                txt_print_allemp.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            txtempid2.setText("");
            txtfn2.setText("");
            txtln2.setText("");
            DOB2.setDate(null);
            combodept2.setSelectedItem("Select");
            combostatus2.setSelectedItem("Select");
            combogender2.setSelectedItem("Select");
            datehired2.setDate(null);
            txtsearch2.setText("");
            txtjobtittle2.setText("");
            cleardate.setDate(null);
        }
    }

    public void printLeave() {
        if ((txt_empid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_jobtittle.getText().isEmpty() || txt_noofdays.getText().isEmpty() || txt_amount.getText().isEmpty() || cmb_gender.getSelectedItem().equals("Select") || cmb_period.getSelectedItem().equals("Select") || cmb_payment.getSelectedItem().equals("Select") || DOB3.getDate() == null) || hireddate.getDate() == null || Fromdate.getDate() == null || Todate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Fill all The Fields To Print",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB3.getDate());
            Date_Format.format(hireddate.getDate());
            Date_Format.format(Fromdate.getDate());
            Date_Format.format(Todate.getDate());
            String Qty1 = (txt_empid.getText());
            String Qty2 = (txt_FN.getText());
            String Qty3 = (txt_LN.getText());
            String Qty4 = (cmb_gender.getSelectedItem().toString());
            String Qty5 = ((JTextField) DOB3.getDateEditor().getUiComponent()).getText();
            String Qty6 = (cmb_dept.getSelectedItem().toString());
            String Qty7 = (cmb_status.getSelectedItem().toString());
            String Qty8 = ((JTextField) hireddate.getDateEditor().getUiComponent()).getText();
            String Qty9 = (txt_jobtittle.getText());
            String Qty10 = (cmb_typeofleave.getSelectedItem().toString());
            String Qty11 = (txt_noofdays.getText());
            String Qty12 = (cmb_period.getSelectedItem().toString());
            String Qty13 = ((JTextField) Fromdate.getDateEditor().getUiComponent()).getText();
            String Qty14 = ((JTextField) Todate.getDateEditor().getUiComponent()).getText();
            String Qty15 = (cmb_payment.getSelectedItem().toString());
            String Qty16 = (txt_amount.getText());
            txt_print_allemp.setText("");
            txt_print_allemp.append("\n\n\tHOSPITAL MANAGEMENT SYSTEM(HMS)\n\tLeave Application Receipt\n"
                    + "\nEmployee ID:\t\t" + Qty1
                    + "\nFirstname:\t\t" + Qty2
                    + "\nLastname:\t\t" + Qty3
                    + "\nGender:\t\t" + Qty4
                    + "\nDOB:\t\t" + Qty5
                    + "\nDepartment:\t\t" + Qty6
                    + "\nStatus:\t\t" + Qty7
                    + "\nDate Hired:\t\t" + Qty8
                    + "\nJob Tittle:\t\t" + Qty9
                    + "\nType Of Leave:\t" + Qty10
                    + "\nNo Of Days:\t\t" + Qty11
                    + "\nPeriod:\t\t" + Qty11
                    + "\nFrom:\t\t" + Qty13
                    + "\nTo:\t\t" + Qty14
                    + "\n:Payment\t\t" + Qty15
                    + "\nAmount:\t\t" + Qty16
                    + "\n\n\tKindly Keep This Receipt Safely\n\tProduce It When You Are Asked\n\t"
            );
            try {
                txt_print_allemp.print();
                JOptionPane.showMessageDialog(null, "RECEIPT PRINTED SUCCESSFULLY");
            } catch (PrinterException ex) {
                Logger.getLogger(Receptionist.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "THE RECEIPT CAN'T BE PRINTED");
            }
            txtsrch.setText("");
            txt_empid.setText("");
            txt_FN.setText("");
            txt_LN.setText("");
            cmb_gender.setSelectedItem("Select");
            DOB3.setDate(null);
            cmb_dept.setSelectedItem("Select");
            cmb_status.setSelectedItem("Select");
            hireddate.setDate(null);
            txt_jobtittle.setText("");
            cmb_typeofleave.setSelectedItem("Select");
            txt_noofdays.setText("");
            cmb_period.setSelectedItem("Select");
            Fromdate.setDate(null);
            Todate.setDate(null);
            cmb_payment.setSelectedItem("Select");
            txt_amount.setText("");
        }
    }

    public void insertdoctor() {
        if (combodept.getSelectedItem().equals("Doctor")) {
            if (txtempid.getText().isEmpty() || txtfn.getText().isEmpty() || txtln.getText().isEmpty() || combodept.getSelectedItem().equals("Select") || combostatus.getSelectedItem().equals("Select") || txtjobtittle.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill All The Fields",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (DOB.getDate() == null || datehired.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Select DOB or Hired Date",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combostatus.getSelectedItem().equals("Suspended") || combostatus.getSelectedItem().equals("On Leave")) {
                JOptionPane.showMessageDialog(this, "Employee Status At The Time of Registration is Active",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (combogender.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please Select Gender",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String Dept = combodept.getSelectedItem().toString();

                String Gender = combogender.getSelectedItem().toString();
                String Dob = ((JTextField) DOB.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(DOB.getDate());
                try {
                    String sql = "insert into hms_doctors(Empid,FN,LN,Gender,DOB,Dept) values(?,?,?,?,?,?)";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    pst = con.prepareStatement(sql);
                    pst.setString(1, txtempid.getText());
                    pst.setString(2, txtfn.getText());
                    pst.setString(3, txtln.getText());
                    pst.setString(4, Gender);
                    pst.setString(5, Dob);
                    pst.setString(6, Dept);
                    pst.execute();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
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
        jLabel20 = new javax.swing.JLabel();
        lbluser = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtempid = new javax.swing.JTextField();
        txtfn = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtln = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtjobtittle = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        combostatus = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        combogender2 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        combodept = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtfn2 = new javax.swing.JTextField();
        txtln2 = new javax.swing.JTextField();
        DOB = new com.toedter.calendar.JDateChooser();
        datehired = new com.toedter.calendar.JDateChooser();
        txtsearch = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        combogender = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtsearch2 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtempid2 = new javax.swing.JTextField();
        DOB2 = new com.toedter.calendar.JDateChooser();
        combodept2 = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        combostatus2 = new javax.swing.JComboBox<>();
        datehired2 = new com.toedter.calendar.JDateChooser();
        cleardate = new com.toedter.calendar.JDateChooser();
        jLabel48 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        btnemp = new javax.swing.JButton();
        btnsearch = new javax.swing.JButton();
        btnAccrefresh = new javax.swing.JButton();
        btn_empclear1 = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnrefresh = new javax.swing.JButton();
        btn_empsrch = new javax.swing.JButton();
        txtjobtittle2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_search = new javax.swing.JButton();
        btn_employee = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        txt_empid = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txt_FN = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        txt_LN = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        DOB3 = new com.toedter.calendar.JDateChooser();
        jLabel63 = new javax.swing.JLabel();
        cmb_dept = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        cmb_status = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        hireddate = new com.toedter.calendar.JDateChooser();
        jLabel66 = new javax.swing.JLabel();
        txt_jobtittle = new javax.swing.JTextField();
        txtsrch = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel68 = new javax.swing.JLabel();
        cmb_period = new javax.swing.JComboBox<>();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        Fromdate = new com.toedter.calendar.JDateChooser();
        Todate = new com.toedter.calendar.JDateChooser();
        jLabel71 = new javax.swing.JLabel();
        cmb_typeofleave = new javax.swing.JComboBox<>();
        jLabel72 = new javax.swing.JLabel();
        txt_noofdays = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        cmb_payment = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        cmb_gender = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_print_allemp = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        Scrollpane = new javax.swing.JScrollPane();
        all_employees_table = new javax.swing.JTable();
        btnrefresh1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
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
        setMaximizedBounds(new java.awt.Rectangle(1230, 780, 1230, 780));
        setMaximumSize(new java.awt.Dimension(1230, 780));
        setMinimumSize(new java.awt.Dimension(1230, 780));
        setPreferredSize(new java.awt.Dimension(1230, 780));
        setResizable(false);
        setSize(new java.awt.Dimension(1230, 780));

        jPanel1.setBackground(new java.awt.Color(0, 51, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/sign_up.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Human Resource");

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

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText("Welcome");

        lbluser.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbluser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(lbluser))
                                .addGap(39, 39, 39)))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel20, lbluser});

        jTabbedPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Add Employee");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Employee ID");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtempid.setEditable(false);
        txtempid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtempid.setFocusCycleRoot(true);
        txtempid.setFocusTraversalPolicyProvider(true);

        txtfn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("First Name");
        jLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Last Name");
        jLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtln.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Date of Birth");
        jLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Department");
        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Status");
        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setText("Date Hired");
        jLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtjobtittle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setText("Job Tittle");
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combostatus.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combostatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Active", "Suspended", "On Leave" }));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setOpaque(true);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setText("Gender");
        jLabel13.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combogender2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combogender2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Female", "Male", "Any Other" }));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("Date Of Birth");
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Department");
        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Date Hired");
        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel17.setText("Job Tittle");
        jLabel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combodept.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combodept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Catering", "Doctor", "Finance", "House Keeping", "HR", "Laboratory", "Nurse", "Pharmacy", "Reception" }));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel18.setText("First Name");
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel19.setText("Last Name");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtfn2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        txtln2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        DOB.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DOB.setDateFormatString("yyyy-MM-dd");
        DOB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        datehired.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        datehired.setDateFormatString("yyyy-MM-dd");
        datehired.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        txtsearch.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("Search");

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel73.setText("Gender");
        jLabel73.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combogender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        combogender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Female", "Male", "Any Other" }));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setText("Clear Employee");
        jLabel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        jLabel45.setText("Search");
        jLabel45.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtsearch2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtsearch2.setPreferredSize(new java.awt.Dimension(4, 34));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel46.setText("Employee ID");
        jLabel46.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtempid2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        txtempid2.setFocusCycleRoot(true);
        txtempid2.setFocusTraversalPolicyProvider(true);

        DOB2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DOB2.setDateFormatString("yyyy-MM-dd");
        DOB2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        combodept2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combodept2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Catering", "House Keeping", "Doctor", "Nurse", "Laboratory", "Pharmacy", "Finance", "HR", "Reception" }));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel47.setText("Status");
        jLabel47.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        combostatus2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        combostatus2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Active", "Suspended", "On Leave", "Cleared" }));

        datehired2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        datehired2.setDateFormatString("yyyy-MM-dd");
        datehired2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        cleardate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cleardate.setDateFormatString("yyyy-MM-dd");
        cleardate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel48.setText("Cleared Date");
        jLabel48.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jPanel17.setBackground(new java.awt.Color(153, 153, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jPanel18.setBackground(new java.awt.Color(153, 204, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnemp.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnemp.setText("EMPLOYEE");
        btnemp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnemp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnempActionPerformed(evt);
            }
        });

        btnsearch.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btnsearch.setText("SEARCH");
        btnsearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        btnAccrefresh.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnAccrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btnAccrefresh.setText("REFRESH");
        btnAccrefresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAccrefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAccrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccrefreshActionPerformed(evt);
            }
        });

        btn_empclear1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btn_empclear1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_30px_1.png"))); // NOI18N
        btn_empclear1.setText("SAVE");
        btn_empclear1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_empclear1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_empclear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_empclear1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnemp, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_empclear1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnAccrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnemp, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_empclear1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsave.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_30px_1.png"))); // NOI18N
        btnsave.setText("SAVE");
        btnsave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btnedit.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_edit_30px_2.png"))); // NOI18N
        btnedit.setText("UPDATE");
        btnedit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnedit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        btnrefresh.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnrefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btnrefresh.setText("REFRESH");
        btnrefresh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnrefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefreshActionPerformed(evt);
            }
        });

        btn_empsrch.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btn_empsrch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_search_32px.png"))); // NOI18N
        btn_empsrch.setText("SEARCH");
        btn_empsrch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_empsrch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_empsrch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_empsrchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_empsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_empsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_empsrch, btnedit, btnrefresh, btnsave});

        txtjobtittle2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtjobtittle, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtfn, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(DOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(combodept, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(combostatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(datehired, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtsearch))
                                        .addComponent(txtempid, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtln)
                                        .addComponent(combogender, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(combodept2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(combostatus2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(datehired2, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                                                .addComponent(txtjobtittle2))))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(cleardate, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(94, 94, 94))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(36, 36, 36))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtln2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtfn2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtempid2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(DOB2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(combogender2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel3))
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtempid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txtfn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(txtln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combogender))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(DOB, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(combodept, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(combostatus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(datehired, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(txtjobtittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtempid2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtln2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtfn2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combogender2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DOB2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combodept2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combostatus2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(datehired2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtjobtittle2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cleardate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {combodept, combogender, combostatus});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {DOB, txtln});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {datehired, txtjobtittle});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {DOB2, cleardate, combodept2, combogender2, combostatus2, datehired2, jLabel13, jLabel14, jLabel15, jLabel16, jLabel17, jLabel18, jLabel19, jLabel46, jLabel47, jLabel48, txtempid2, txtfn2, txtjobtittle2, txtln2, txtsearch2});

        jTabbedPane1.addTab("Manage Employees", jPanel5);

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        jPanel16.setBackground(new java.awt.Color(204, 204, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btn_save.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_save_30px_1.png"))); // NOI18N
        btn_save.setText("SAVE");
        btn_save.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
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

        btn_employee.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btn_employee.setText("EMPLOYEE");
        btn_employee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_employee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_employeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_refresh)
                            .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_employee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_employee, btn_refresh, btn_save, btn_search, btn_update});

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_employee, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_update, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_refresh, btn_save, btn_search, btn_update});

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel59.setText("Employee ID");
        jLabel59.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_empid.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_empid.setFocusCycleRoot(true);
        txt_empid.setFocusTraversalPolicyProvider(true);

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel60.setText("First Name");
        jLabel60.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_FN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel61.setText("Last Name");
        jLabel61.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_LN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel62.setText("Date of Birth");
        jLabel62.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        DOB3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DOB3.setDateFormatString("yyyy-MM-dd");
        DOB3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel63.setText("Department");
        jLabel63.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_dept.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmb_dept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Catering", "House Keeping", "Doctor", "Nurse", "Laboratory", "Pharmacy", "Finance", "HR", "Reception" }));

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel64.setText("Status");
        jLabel64.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_status.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmb_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Active", "Suspended", "On Leave", "Cleared" }));

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel65.setText("Date Hired");
        jLabel65.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        hireddate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        hireddate.setDateFormatString("yyyy-MM-dd");
        hireddate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel66.setText("Job Tittle");
        jLabel66.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_jobtittle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txtsrch.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel67.setText("Search");

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setOpaque(true);

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel68.setText("Period");
        jLabel68.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_period.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cmb_period.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1/2 Mnth", "1 Mnth", "2 Mnth", "3 Mnth" }));

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel69.setText("From");
        jLabel69.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel70.setText("To");
        jLabel70.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        Fromdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Fromdate.setDateFormatString("yyyy-MM-dd");
        Fromdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        Todate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Todate.setDateFormatString("yyyy-MM-dd");
        Todate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel71.setText("Type of Leave");
        jLabel71.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_typeofleave.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_typeofleave.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Compulsary", "Deceased", "Medical", "Vacation", "Sabbatical", "Special", "Maternity", "Paternity" }));
        cmb_typeofleave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_typeofleaveActionPerformed(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel72.setText("No of Days");
        jLabel72.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_noofdays.setEditable(false);
        txt_noofdays.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel42.setText("Payment");
        jLabel42.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_payment.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_payment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Yes", "No" }));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel43.setText("Amount/Mnth");
        jLabel43.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txt_amount.setEditable(false);
        txt_amount.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel44.setText("Gender");
        jLabel44.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        cmb_gender.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        cmb_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female", "Any Other" }));

        txt_print_allemp.setColumns(20);
        txt_print_allemp.setRows(5);
        jScrollPane2.setViewportView(txt_print_allemp);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_noofdays, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmb_period, javax.swing.GroupLayout.Alignment.TRAILING, 0, 300, Short.MAX_VALUE)
                            .addComponent(Todate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Fromdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(cmb_typeofleave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cmb_dept, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(DOB3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txt_LN))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txt_FN))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txt_empid, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmb_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmb_status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 313, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmb_payment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txt_amount)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txt_jobtittle, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(hireddate, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
                                        .addGap(493, 493, 493)))))))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {DOB3, cmb_dept, cmb_gender, cmb_status, txt_FN, txt_LN, txt_empid});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel44, jLabel59, jLabel60, jLabel61, jLabel62, jLabel63, jLabel64});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel59)
                            .addComponent(txt_empid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel71)
                            .addComponent(cmb_typeofleave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel60)
                            .addComponent(txt_FN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel61)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_LN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel68)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(cmb_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel62)
                            .addComponent(DOB3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63)
                            .addComponent(cmb_dept, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(cmb_status, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel72)
                            .addComponent(txt_noofdays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(cmb_period, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Fromdate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel69))
                                .addGap(11, 11, 11)
                                .addComponent(Todate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel70))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_payment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel65)
                    .addComponent(hireddate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(txt_jobtittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator3))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {DOB3, txt_LN});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {hireddate, txt_jobtittle});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Fromdate, Todate, cmb_payment, cmb_period, cmb_typeofleave, jLabel68, txt_amount, txt_noofdays});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel42, jLabel43, jLabel70});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmb_gender, cmb_status});

        jTabbedPane1.addTab("Leave Application", jPanel2);

        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        Scrollpane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 255), 10, true));
        Scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        Scrollpane.setToolTipText("");
        Scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Scrollpane.setAutoscrolls(true);
        Scrollpane.setName("JscrollPat_reg"); // NOI18N

        all_employees_table.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        all_employees_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "null", "null", "null", "null", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        all_employees_table.setGridColor(new java.awt.Color(0, 0, 0));
        all_employees_table.setRowHeight(25);
        all_employees_table.getTableHeader().setReorderingAllowed(false);
        Scrollpane.setViewportView(all_employees_table);

        btnrefresh1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnrefresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images and icons/icons8_refresh_32px.png"))); // NOI18N
        btnrefresh1.setText("REFRESH");
        btnrefresh1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnrefresh1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefresh1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(btnrefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnrefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("All Employees", jPanel11);

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

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
            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
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
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(245, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Manage Account", jPanel4);

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
                .addComponent(jTabbedPane1))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Login_HR HMS = new Login_HR();
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
        HR_EditAccount HMS = new HR_EditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        String user = lbluser.getText();
        HR_EditAccount HMS = new HR_EditAccount();
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
        HR_EditAccount HMS = new HR_EditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jbtnchangepassActionPerformed

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        String user = lbluser.getText();
        HR_EditAccount HMS = new HR_EditAccount();
        HMS.setVisible(true);
        HMS.username(user);
    }//GEN-LAST:event_jLabel25MouseClicked

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        autoId();
        txtfn.setText("");
        txtln.setText("");
        DOB.setDate(null);
        combodept.setSelectedItem("Select");
        combostatus.setSelectedItem("Select");
        combogender.setSelectedItem("Select");
        datehired.setDate(null);
        txtsearch.setText("");
        txtjobtittle.setText("");
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void btnAccrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccrefreshActionPerformed
        txtempid2.setText("");
        txtfn2.setText("");
        txtln2.setText("");
        DOB2.setDate(null);
        combodept2.setSelectedItem("Select");
        combostatus2.setSelectedItem("Select");
        combogender2.setSelectedItem("Select");
        datehired2.setDate(null);
        txtsearch2.setText("");
        txtjobtittle2.setText("");
        cleardate.setDate(null);
    }//GEN-LAST:event_btnAccrefreshActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        this.insertdoctor();
        if (txtempid.getText().isEmpty() || txtfn.getText().isEmpty() || txtln.getText().isEmpty() || combodept.getSelectedItem().equals("Select") || combostatus.getSelectedItem().equals("Select") || txtjobtittle.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill All The Fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (DOB.getDate() == null || datehired.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select DOB or Hired Date",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combostatus.getSelectedItem().equals("Suspended") || combostatus.getSelectedItem().equals("On Leave")) {
            JOptionPane.showMessageDialog(this, "Employee Status At The Time of Registration is Active",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combogender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Department = combodept.getSelectedItem().toString();
            String Status = combostatus.getSelectedItem().toString();
            String Gender = combogender.getSelectedItem().toString();
            String Dob = ((JTextField) DOB.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB.getDate());
            String Datehired = ((JTextField) datehired.getDateEditor().getUiComponent()).getText();
            Date_Format.format(datehired.getDate());
            try {
                String sql = "insert into employees(Empid,Firstname,Lastname,Gender,DOB,Department,Status,Datehired,Jobtittle) values(?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtempid.getText());
                pst.setString(2, txtfn.getText());
                pst.setString(3, txtln.getText());
                pst.setString(4, Gender);
                pst.setString(5, Dob);
                pst.setString(6, Department);
                pst.setString(7, Status);
                pst.setString(8, Datehired);
                pst.setString(9, txtjobtittle.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO EMPLOYEE");
                this.printEmp();
                autoId();
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
    }//GEN-LAST:event_btnsaveActionPerformed

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

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "ARE YOU SURE YOU WANT TO UPDATE?", "UPDATE", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            if (txtempid.getText().isEmpty() || txtfn.getText().isEmpty() || txtln.getText().isEmpty() || combodept.getSelectedItem().equals("Select") || combostatus.getSelectedItem().equals("Select") || txtjobtittle.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please Fill All The Fields",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (DOB.getDate() == null || datehired.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please Select DOB or Hired Date",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String Department = combodept.getSelectedItem().toString();
                String Status = combostatus.getSelectedItem().toString();
                String Gender = combogender.getSelectedItem().toString();
                String Dob = ((JTextField) DOB.getDateEditor().getUiComponent()).getText();
                SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
                Date_Format.format(DOB.getDate());
                String Datehired = ((JTextField) datehired.getDateEditor().getUiComponent()).getText();
                Date_Format.format(datehired.getDate());
                try {
                    String sql = "update employees set Firstname='" + txtfn.getText() + "',Lastname='" + txtln.getText() + "',Gender='" + Gender + "',DOB='" + Dob + "',Department='" + Department + "',Status='" + Status + "',Datehired='" + Datehired + "',Jobtittle='" + txtjobtittle.getText() + "' where Empid='" + txtempid.getText() + "'";
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                    pst = con.prepareStatement(sql);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                    JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO EMPLOYEE");
                    this.printEmp();
                    autoId();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btneditActionPerformed

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        txtempid2.setText("");
        txtfn2.setText("");
        txtln2.setText("");
        DOB2.setDate(null);
        combodept2.setSelectedItem("Select");
        combostatus2.setSelectedItem("Select");
        combogender2.setSelectedItem("Select");
        datehired2.setDate(null);
        txtjobtittle2.setText("");
        cleardate.setDate(null);
        if (txtsearch2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select Empid,Firstname,Lastname,Gender,DOB,Department,Status,Datehired,Jobtittle,ClearDate from employees where Empid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsearch2.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Empid");
                    txtempid2.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txtfn2.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txtln2.setText(add3);
                    String add4 = rs.getString("Gender");
                    combogender2.setSelectedItem(add4);
                    String add5 = rs.getObject("DOB").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add5);
                    DOB2.setDate(dat);
                    String add6 = rs.getString("Department");
                    combodept2.setSelectedItem(add6);
                    String add7 = rs.getString("Status");
                    combostatus2.setSelectedItem(add7);
                    String add8 = rs.getObject("Datehired").toString();
                    java.util.Date hire = new SimpleDateFormat("yyyy-MM-dd").parse(add8);
                    datehired2.setDate(hire);
                    String add9 = rs.getString("Jobtittle");
                    txtjobtittle2.setText(add9);
                    String add10 = rs.getObject("ClearDate").toString();
                    java.util.Date clear = new SimpleDateFormat("yyyy-MM-dd").parse(add10);
                    cleardate.setDate(clear);
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
    }//GEN-LAST:event_btnsearchActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        if (txt_empid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_jobtittle.getText().isEmpty() || txt_noofdays.getText().isEmpty() || txt_amount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill All The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_gender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_dept.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The DePartment", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_period.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Period Of Leave", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_payment.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Payment", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_status.getSelectedItem().equals("Suspended") || cmb_status.getSelectedItem().equals("On Leave") || cmb_status.getSelectedItem().equals("Select") || cmb_status.getSelectedItem().equals("Cleared")) {
            JOptionPane.showMessageDialog(this, "Only Active Employees Can Apply For Leave", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_typeofleave.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Type Of Leave", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (DOB3.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The DOB", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (hireddate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The Hired Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Fromdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The From Date Of Leave", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Todate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The End Of Leave Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmb_gender.getSelectedItem().toString();
            String Dept = cmb_dept.getSelectedItem().toString();
            String Status = cmb_status.getSelectedItem().toString();
            String Leave_type = cmb_typeofleave.getSelectedItem().toString();
            String Period = cmb_period.getSelectedItem().toString();
            String Pay = cmb_payment.getSelectedItem().toString();
            String DoB = ((JTextField) DOB3.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB3.getDate());
            String Datehired = ((JTextField) hireddate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(hireddate.getDate());
            String From = ((JTextField) Fromdate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(Fromdate.getDate());
            String To = ((JTextField) Todate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(Todate.getDate());
            try {
                String sql = "UPDATE `employees` SET Firstname='" + txt_FN.getText() + "',Lastname='" + txt_LN.getText() + "',Gender='" + Gender + "',DOB='" + DoB + "',Department='" + Dept + "',Status='" + Status + "',Datehired='" + Datehired + "',Jobtittle='" + txt_jobtittle.getText() + "',Leave_typ='" + Leave_type + "',Days='" + txt_noofdays.getText() + "',Period='" + Period + "',Fromdate='" + From + "',Todate='" + To + "',Pay='" + Pay + "',Amount='" + txt_amount.getText() + "' WHERE Empid='" + txt_empid.getText() + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "RECORD SAVED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO EMPLOYEE");
                this.printLeave();
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
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        if (txt_empid.getText().isEmpty() || txt_FN.getText().isEmpty() || txt_LN.getText().isEmpty() || txt_jobtittle.getText().isEmpty() || txt_noofdays.getText().isEmpty() || txt_amount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill All The Fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_gender.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Gender", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_dept.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The DePartment", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_period.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Period Of Leave", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_payment.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Payment", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_status.getSelectedItem().equals("Suspended") || cmb_status.getSelectedItem().equals("On Leave") || cmb_status.getSelectedItem().equals("Select") || cmb_status.getSelectedItem().equals("Cleared")) {
            JOptionPane.showMessageDialog(this, "Only Active Employees Can Apply For Leave", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (cmb_typeofleave.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select The Type Of Leave", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (DOB3.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The DOB", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (hireddate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The Hired Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Fromdate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The From Date Of Leave", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Todate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select The End Of Leave Date", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Gender = cmb_gender.getSelectedItem().toString();
            String Dept = cmb_dept.getSelectedItem().toString();
            String Status = cmb_status.getSelectedItem().toString();
            String Leave_type = cmb_typeofleave.getSelectedItem().toString();
            String Period = cmb_period.getSelectedItem().toString();
            String Pay = cmb_payment.getSelectedItem().toString();
            String DoB = ((JTextField) DOB3.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB3.getDate());
            String Datehired = ((JTextField) hireddate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(hireddate.getDate());
            String From = ((JTextField) Fromdate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(Fromdate.getDate());
            String To = ((JTextField) Todate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(Todate.getDate());
            try {
                String sql = "UPDATE `employees` SET Firstname='" + txt_FN.getText() + "',Lastname='" + txt_LN.getText() + "',Gender='" + Gender + "',DOB='" + DoB + "',Department='" + Dept + "',Status='" + Status + "',Datehired='" + Datehired + "',Jobtittle='" + txt_jobtittle.getText() + "',Leave_typ='" + Leave_type + "',Days='" + txt_noofdays.getText() + "',Period='" + Period + "',Fromdate='" + From + "',Todate='" + To + "',Pay='" + Pay + "',Amount='" + txt_amount.getText() + "' WHERE Empid='" + txt_empid.getText() + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "RECORD UPDATED SUCCESSFULLY");
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO EMPLOYEE");
                this.printLeave();
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
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        txtsrch.setText("");
        txt_empid.setText("");
        txt_FN.setText("");
        txt_LN.setText("");
        cmb_gender.setSelectedItem("Select");
        DOB3.setDate(null);
        cmb_dept.setSelectedItem("Select");
        cmb_status.setSelectedItem("Select");
        hireddate.setDate(null);
        txt_jobtittle.setText("");
        cmb_typeofleave.setSelectedItem("Select");
        txt_noofdays.setText("");
        cmb_period.setSelectedItem("Select");
        Fromdate.setDate(null);
        Todate.setDate(null);
        cmb_payment.setSelectedItem("Select");
        txt_amount.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        if (txtsrch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from employees where Empid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsrch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Empid");
                    txt_empid.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txt_FN.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txt_LN.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmb_gender.setSelectedItem(add4);
                    String add5 = rs.getObject("DOB").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add5);
                    DOB3.setDate(dat);
                    String add6 = rs.getString("Department");
                    cmb_dept.setSelectedItem(add6);
                    String add7 = rs.getString("Status");
                    cmb_status.setSelectedItem(add7);
                    String add8 = rs.getObject("Datehired").toString();
                    java.util.Date hire = new SimpleDateFormat("yyyy-MM-dd").parse(add8);
                    hireddate.setDate(hire);
                    String add22 = rs.getString("Jobtittle");
                    txt_jobtittle.setText(add22);
                    String add9 = rs.getString("Leave_typ");
                    cmb_typeofleave.setSelectedItem(add9);
                    String add10 = rs.getString("Days");
                    txt_noofdays.setText(add10);
                    String add12 = rs.getString("Period");
                    cmb_period.setSelectedItem(add12);
                    String add13 = rs.getObject("Fromdate").toString();
                    java.util.Date hre = new SimpleDateFormat("yyyy-MM-dd").parse(add13);
                    Fromdate.setDate(hre);
                    String add14 = rs.getObject("Todate").toString();
                    java.util.Date pire = new SimpleDateFormat("yyyy-MM-dd").parse(add14);
                    Todate.setDate(pire);
                    String add15 = rs.getString("Pay");
                    cmb_payment.setSelectedItem(add15);
                    String add16 = rs.getString("Amount");
                    txt_amount.setText(add16);
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

    private void cmb_typeofleaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_typeofleaveActionPerformed
        if (cmb_typeofleave.getSelectedItem().equals("Compulsary")) {
            txt_noofdays.setText("90");
            cmb_period.setSelectedItem("3 Mnth");
            cmb_payment.setSelectedItem("No");
            txt_amount.setText("0");
        } else if (cmb_typeofleave.getSelectedItem().equals("Deceased")) {
            txt_noofdays.setText("15");
            cmb_period.setSelectedItem("1/2 Mnth");
            cmb_payment.setSelectedItem("No");
            txt_amount.setText("0");
        } else if (cmb_typeofleave.getSelectedItem().equals("Medical")) {
            txt_noofdays.setText("30");
            cmb_period.setSelectedItem("1 Mnth");
            cmb_payment.setSelectedItem("Yes");
            txt_amount.setText("2000");
        } else if (cmb_typeofleave.getSelectedItem().equals("Vacation")) {
            txt_noofdays.setText("30");
            cmb_period.setSelectedItem("1 Mnth");
            cmb_payment.setSelectedItem("Yes");
            txt_amount.setText("1500");
        } else if (cmb_typeofleave.getSelectedItem().equals("Sabbatical")) {
            txt_noofdays.setText("30");
            cmb_period.setSelectedItem("1 Mnth");
            cmb_payment.setSelectedItem("Yes");
            txt_amount.setText("1500");
        } else if (cmb_typeofleave.getSelectedItem().equals("Special")) {
            txt_noofdays.setText("60");
            cmb_period.setSelectedItem("2 Mnth");
            cmb_payment.setSelectedItem("Yes");
            txt_amount.setText("2000");
        } else if (cmb_typeofleave.getSelectedItem().equals("Maternity")) {
            txt_noofdays.setText("60");
            cmb_period.setSelectedItem("2 Mnth");
            cmb_payment.setSelectedItem("Yes");
            txt_amount.setText("2000");
        } else if (cmb_typeofleave.getSelectedItem().equals("Paternity")) {
            txt_noofdays.setText("60");
            cmb_period.setSelectedItem("2 Mnth");
            cmb_payment.setSelectedItem("Yes");
            txt_amount.setText("2000");
        }
    }//GEN-LAST:event_cmb_typeofleaveActionPerformed

    private void btnempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnempActionPerformed
        txtempid2.setText("");
        txtfn2.setText("");
        txtln2.setText("");
        DOB2.setDate(null);
        combodept2.setSelectedItem("Select");
        combostatus2.setSelectedItem("Select");
        combogender2.setSelectedItem("Select");
        datehired2.setDate(null);
        txtjobtittle2.setText("");
        cleardate.setDate(null);
        if (txtsearch2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select Empid,Firstname,Lastname,Gender,DOB,Department,Status,Datehired,Jobtittle from employees where Empid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsearch2.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Empid");
                    txtempid2.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txtfn2.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txtln2.setText(add3);
                    String add4 = rs.getString("Gender");
                    combogender2.setSelectedItem(add4);
                    String add5 = rs.getObject("DOB").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add5);
                    DOB2.setDate(dat);
                    String add6 = rs.getString("Department");
                    combodept2.setSelectedItem(add6);
                    String add7 = rs.getString("Status");
                    combostatus2.setSelectedItem(add7);
                    String add8 = rs.getObject("Datehired").toString();
                    java.util.Date hire = new SimpleDateFormat("yyyy-MM-dd").parse(add8);
                    datehired2.setDate(hire);
                    String add9 = rs.getString("Jobtittle");
                    txtjobtittle2.setText(add9);
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
    }//GEN-LAST:event_btnempActionPerformed

    private void btn_empsrchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_empsrchActionPerformed
        autoId();
        txtfn.setText("");
        txtln.setText("");
        DOB.setDate(null);
        combodept.setSelectedItem("Select");
        combostatus.setSelectedItem("Select");
        combogender.setSelectedItem("Select");
        datehired.setDate(null);
        txtjobtittle.setText("");
        if (txtsearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Search ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select Empid,Firstname,Lastname,Gender,DOB,Department,Status,Datehired,Jobtittle from employees where Empid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsearch.getText());
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
                    combogender.setSelectedItem(add4);
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
    }//GEN-LAST:event_btn_empsrchActionPerformed

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        this.view_table();
    }//GEN-LAST:event_jPanel11MouseClicked

    private void btn_employeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_employeeActionPerformed
        if (txtsrch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill The Searchbox Using Patientid To Get The Employee ");
            System.out.println("Fill to fields to search dialog DB");
        } else {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                String sql = "select * from employees where Empid=?";
                System.out.println("Connecting to DB");
                pst = con.prepareStatement(sql);
                pst.setString(1, txtsrch.getText());
                rs = pst.executeQuery();
                System.out.println("Search Found........");
                if (rs.next()) {
                    String add1 = rs.getString("Empid");
                    txt_empid.setText(add1);
                    String add2 = rs.getString("Firstname");
                    txt_FN.setText(add2);
                    String add3 = rs.getString("Lastname");
                    txt_LN.setText(add3);
                    String add4 = rs.getString("Gender");
                    cmb_gender.setSelectedItem(add4);
                    String add5 = rs.getObject("DOB").toString();
                    java.util.Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(add5);
                    DOB3.setDate(dat);
                    String add6 = rs.getString("Department");
                    cmb_dept.setSelectedItem(add6);
                    String add7 = rs.getString("Status");
                    cmb_status.setSelectedItem(add7);
                    String add8 = rs.getObject("Datehired").toString();
                    java.util.Date hire = new SimpleDateFormat("yyyy-MM-dd").parse(add8);
                    hireddate.setDate(hire);
                    String add9 = rs.getString("Jobtittle");
                    txt_jobtittle.setText(add9);
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
    }//GEN-LAST:event_btn_employeeActionPerformed

    private void btnrefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefresh1ActionPerformed
        this.view_table();
    }//GEN-LAST:event_btnrefresh1ActionPerformed

    private void btn_empclear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_empclear1ActionPerformed
        if (txtempid2.getText().isEmpty() || txtfn2.getText().isEmpty() || txtln2.getText().isEmpty() || combodept2.getSelectedItem().equals("Select") || combostatus2.getSelectedItem().equals("Select") || txtjobtittle2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill All The Fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (DOB2.getDate() == null || datehired2.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please Select DOB or Hired Date",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combostatus2.getSelectedItem().equals("Suspended") || combostatus2.getSelectedItem().equals("On Leave") || combostatus2.getSelectedItem().equals("Active") || combostatus2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Employee Status Should Be Set To Cleared",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (combogender2.getSelectedItem().equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String Dept = combodept2.getSelectedItem().toString();
            String Status = combostatus2.getSelectedItem().toString();
            String Gender = combogender2.getSelectedItem().toString();
            String Dob = ((JTextField) DOB2.getDateEditor().getUiComponent()).getText();
            SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-mm-dd");
            Date_Format.format(DOB2.getDate());
            String Datehired = ((JTextField) datehired2.getDateEditor().getUiComponent()).getText();
            Date_Format.format(datehired2.getDate());
            String Cleardate = ((JTextField) cleardate.getDateEditor().getUiComponent()).getText();
            Date_Format.format(cleardate.getDate());
            try {
                String sql = "update employees set Firstname='" + txtfn2.getText() + "',Lastname='" + txtln2.getText() + "',Gender='" + Gender + "',DOB='" + Dob + "',Department='" + Dept + "',Status='" + Status + "',Datehired='" + Datehired + "',Jobtittle='" + txtjobtittle2.getText() + "',ClearDate='" + Cleardate + "' where Empid='" + txtempid2.getText() + "'";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3308/psg", "root", "");
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "EMPLOYEE CLEARED SUCCESSFULLY");
                this.autoId();
                JOptionPane.showMessageDialog(null, "PLEASE PRINT THE RECEIPT AND GIVE IT TO EMPLOYEE");
                this.printEmpClear();
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
    }//GEN-LAST:event_btn_empclear1ActionPerformed

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
            java.util.logging.Logger.getLogger(Human_resource.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Human_resource.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Human_resource.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Human_resource.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Human_resource().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DOB;
    private com.toedter.calendar.JDateChooser DOB2;
    private com.toedter.calendar.JDateChooser DOB3;
    private com.toedter.calendar.JDateChooser Fromdate;
    private javax.swing.JScrollPane Scrollpane;
    private com.toedter.calendar.JDateChooser Todate;
    private javax.swing.JTable all_employees_table;
    private javax.swing.JButton btnAccrefresh;
    private javax.swing.JButton btn_empclear1;
    private javax.swing.JButton btn_employee;
    private javax.swing.JButton btn_empsrch;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnemp;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton btnrefresh1;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnuploadimage;
    private com.toedter.calendar.JDateChooser cleardate;
    private javax.swing.JComboBox<String> cmb_dept;
    private javax.swing.JComboBox<String> cmb_gender;
    private javax.swing.JComboBox<String> cmb_payment;
    private javax.swing.JComboBox<String> cmb_period;
    private javax.swing.JComboBox<String> cmb_status;
    private javax.swing.JComboBox<String> cmb_typeofleave;
    private javax.swing.JComboBox<String> combodept;
    private javax.swing.JComboBox<String> combodept2;
    private javax.swing.JComboBox<String> combogender;
    private javax.swing.JComboBox<String> combogender2;
    private javax.swing.JComboBox<String> combostatus;
    private javax.swing.JComboBox<String> combostatus2;
    private com.toedter.calendar.JDateChooser datehired;
    private com.toedter.calendar.JDateChooser datehired2;
    private com.toedter.calendar.JDateChooser hireddate;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnchangepass;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lbluser;
    private javax.swing.JTextField txt_FN;
    private javax.swing.JTextField txt_LN;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_empid;
    private javax.swing.JTextField txt_jobtittle;
    private javax.swing.JTextField txt_noofdays;
    private javax.swing.JTextArea txt_print_allemp;
    private javax.swing.JTextField txtempid;
    private javax.swing.JTextField txtempid2;
    private javax.swing.JTextField txtfn;
    private javax.swing.JTextField txtfn2;
    private javax.swing.JTextField txtjobtittle;
    private javax.swing.JTextField txtjobtittle2;
    private javax.swing.JTextField txtln;
    private javax.swing.JTextField txtln2;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JTextField txtsearch2;
    private javax.swing.JTextField txtsrch;
    // End of variables declaration//GEN-END:variables
}
