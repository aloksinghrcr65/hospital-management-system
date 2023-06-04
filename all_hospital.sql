-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2020 at 12:35 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `all_hospital`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `userid` varchar(10) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `confirmpass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`userid`, `username`, `password`, `confirmpass`) VALUES
('adm1', 'admin1', '2c7b0576873ffcbb4ca61c5a225b94e7', '2c7b0576873ffcbb4ca61c5a225b94e7'),
('adm2', 'admin2', '2a43bf7ab34cd6bf5401343115eaf325', '2a43bf7ab34cd6bf5401343115eaf325'),
('adm3', 'qwerty', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b');

-- --------------------------------------------------------

--
-- Table structure for table `admin_finances`
--

CREATE TABLE `admin_finances` (
  `Outpatients` int(9) NOT NULL,
  `Inpatients` int(9) NOT NULL,
  `Pharmacy` int(9) NOT NULL,
  `Salaries` int(9) NOT NULL,
  `Income` int(9) NOT NULL,
  `Expenses` int(9) NOT NULL,
  `Date` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin_finances`
--

INSERT INTO `admin_finances` (`Outpatients`, `Inpatients`, `Pharmacy`, `Salaries`, `Income`, `Expenses`, `Date`) VALUES
(4440, 233676823, 7261, 129750, 233688524, 129750, '2020-04-09'),
(13500, 908000, 62681, 510750, 984181, 510750, '2020-04-14'),
(16000, 1693400, 209482, 570000, 1918882, 570000, '2020-04-16');

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE `appointments` (
  `Appointmentid` varchar(20) NOT NULL,
  `Firstname` varchar(50) NOT NULL,
  `Lastname` varchar(50) NOT NULL,
  `Age` int(3) NOT NULL,
  `Guardianname` varchar(50) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Time` varchar(6) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Phoneno` varchar(10) NOT NULL,
  `Appdate` varchar(30) NOT NULL,
  `Service` varchar(50) NOT NULL,
  `Department` varchar(50) NOT NULL,
  `Assigndoc` varchar(50) NOT NULL,
  `Servicefee` int(6) NOT NULL,
  `Paid` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`Appointmentid`, `Firstname`, `Lastname`, `Age`, `Guardianname`, `Gender`, `Time`, `Address`, `Email`, `Phoneno`, `Appdate`, `Service`, `Department`, `Assigndoc`, `Servicefee`, `Paid`) VALUES
('AP1000', 'Rosemary', 'Ursula', 30, 'Miranda Miriam', 'Female', '10:00', '12th Waterway Estate', 'rose@email.com', '0789223344', '2020-04-24', 'Antenatal Care', 'Maternity', 'Dr Peter Wafula', 500, 'Yes'),
('AP1001', 'Bob', 'Albert', 23, 'Susan Stephanie', 'Male', '12:00', 'Avenue Park Estate', 'bob@gmail.com', '0723232323', '2020-04-14', 'Radiotherapy', 'Radiology', 'Dr Allan Joe', 800, 'Yes'),
('AP1002', 'Hillary', 'Hilda', 45, 'Mandy Margaret', 'Female', '16:00', 'Jumia rd avenue', 'suzy@gmail.com', '0712345676', '2020-04-24', 'Pregnancy Test', 'Maternity', 'Dr John Mania', 500, 'Yes'),
('AP1003', 'Philip', 'Max', 34, 'David Jeru', 'Male', '10:00', 'Bruce Village', 'bruse@email.com', '0789676776', '2020-04-14', 'Nerve Training', 'Neurology', 'Dr Luke Joe', 800, 'Yes'),
('AP1004', 'Nora', 'Silvia', 34, 'Caleb Kay', 'Female', '14:00', 'bulh avennue', 'nora@email.com', '0789343433', '2020-04-22', 'Blood Screening', 'Haematology', 'Dr Lucy Natasha', 600, 'Yes'),
('AP1005', 'Ken', 'Walibora', 67, 'Mary Walibora', 'Male', '10:00', 'Mt elgon Estate', 'ken@gmail.com', '0745454545', '2020-04-15', 'Nerve Training', 'Neurology', 'Dr Kennedy', 800, 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `drug_inventory`
--

CREATE TABLE `drug_inventory` (
  `Drugname` varchar(100) NOT NULL,
  `Drugid` varchar(7) NOT NULL,
  `Unit_msr` varchar(7) NOT NULL,
  `Category` varchar(9) NOT NULL,
  `Quantity` int(10) NOT NULL,
  `Pur_price` int(10) NOT NULL,
  `Regdate` varchar(15) NOT NULL,
  `Expdate` varchar(15) NOT NULL,
  `Batchno` varchar(10) NOT NULL,
  `Details` varchar(100) NOT NULL,
  `Unit_price` int(8) NOT NULL,
  `Dosage` varchar(12) NOT NULL,
  `Storage` varchar(100) NOT NULL,
  `Precautions` varchar(100) NOT NULL,
  `Side_effects` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `drug_inventory`
--

INSERT INTO `drug_inventory` (`Drugname`, `Drugid`, `Unit_msr`, `Category`, `Quantity`, `Pur_price`, `Regdate`, `Expdate`, `Batchno`, `Details`, `Unit_price`, `Dosage`, `Storage`, `Precautions`, `Side_effects`) VALUES
('Acetazolamide 125mg', 'Acet12', 'mg', 'Tablets', 800, 80000, '2020-03-27', '2022-03-27', 'WE42222d', 'Drug_No: 232,Order Level:   Critical,Stock Type: Restock', 400, '1x1', 'Cool dry place,away from the sunlight and children', ' Do Not Work After Taking The Drug', 'Dizziness,Headache,Vomiting'),
('Amoxycilin 500mg', 'Amox97', 'mg', 'Capsules', 600, 100000, '2020-03-27', '2023-03-22', 'DS343h', 'MOH delivery,Drug_No:232,Order Level:Critical', 300, '1x3', 'Cool dry place,away from the sunlight and children', 'Don\'t Take Alcohol', 'Dizziness,Headache,Vomiting'),
('Ampicilin 500mg', 'Ampi10', 'mg', 'Capsules', 100, 50000, '2020-03-27', '2022-03-31', 'DFS34453fg', 'Drug for malaria', 200, '1x2', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting'),
('Anticoagulant Citrate 10ml', 'Anti33', 'ml', 'Liquid', 454, 78000, '2020-03-27', '2024-03-22', 'SFS454444', 'Medicine For Patients', 500, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting'),
('Antisnake Venom 10ml', 'Anti44', 'ml', 'Liquid', 50, 200000, '2020-03-27', '2022-03-25', 'GD5j676', 'Order Level:  Critical,Stock Type: Restock', 1000, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting'),
('Artemether 20 mg', 'Arte98', 'mg', 'Tablets', 1212, 600000, '2020-04-15', '2022-04-20', 'LRC398', 'Bliss Gvs Pharma LTD', 800, '1x3', 'Store in cool dry place', 'Only Swallow after Meals', 'nausea,headache,heartburn'),
('Betamethasone 2mg', 'Beta19', 'mg', 'Tablets', 454, 45455, '2020-03-27', '2022-03-25', 'DAfD3545', 'Medicine For Patients', 500, '1x3', 'Cool dry place,away from the sunlight and children', 'Do Not Work After Taking The Drug', 'Dizziness,Headache,Vomiting\n'),
('Biphasic Insulin 100 ml', 'Biph11', 'ml', 'Syrup', 456, 125000, '2020-03-27', '2022-03-25', 'DDF45fgh', 'Drug_No: 232,Order Level:   Critical,Stock Type: Restock', 700, '1 Spn x3', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting'),
('Bisacodyl Suppository 5mg', 'Bisa12', 'mg', 'Gel', 6556, 565656, '2020-03-27', '2022-03-25', 'LK045495oj', 'Medicine For Patients', 600, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting'),
('Bismuth Subsalicylate 262mg', 'Bism45', 'mg', 'Lotion', 5445, 90000, '2020-03-27', '2024-03-23', 'JHK2323Kf', 'Order Level:Critical,Stock Type: Restock', 900, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Skin Rash'),
('Carbenicillin 1g/ml', 'Carb47', 'mg/ml', 'Lotion', 879, 90000, '2020-03-27', '2023-03-24', 'DSA3434f', 'Stock Type: Restock', 700, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Skin Rash'),
('Castor Oil  10ml', 'Cast10', 'ml', 'Gel', 79, 90000, '2020-03-27', '2023-03-17', 'TY56uu5', 'Order Level:Critical', 700, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Skin Rash\n'),
('Cetirizine  Solution 1mg/ml', 'Ceti23', 'mg/ml', 'Injection', 656, 5655, '2020-03-27', '2022-03-25', 'DS456t', 'FS3r35', 767, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Don\'t Take Alcohol', 'Dizziness,Headache,Vomiting'),
('Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', 787, 78787, '2020-03-20', '2025-03-15', '7887D', 'Drug for malaria,Medicine For Patients', 787, '1 Spn x3', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting\n'),
('Cascara Sagrada 125mg', 'Glip65', 'mg', 'Capsules', 5676, 56000, '2020-03-27', '2023-03-24', 'DSfg5656', 'Drug_No: 232m,Order Level:Critical,Stock Type: Restock', 600, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Work After Taking The Drug', 'Dizziness,Headache,Vomiting');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `Empid` varchar(15) NOT NULL,
  `Firstname` varchar(50) NOT NULL,
  `Lastname` varchar(50) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `DOB` varchar(30) NOT NULL,
  `Department` varchar(30) NOT NULL,
  `Status` varchar(12) NOT NULL,
  `Datehired` varchar(30) NOT NULL,
  `Jobtittle` varchar(50) NOT NULL,
  `ClearDate` varchar(15) DEFAULT NULL,
  `Leave_typ` varchar(10) DEFAULT NULL,
  `Days` int(3) DEFAULT NULL,
  `Period` varchar(8) DEFAULT NULL,
  `Fromdate` varchar(11) DEFAULT NULL,
  `Todate` varchar(11) DEFAULT NULL,
  `Pay` varchar(3) DEFAULT NULL,
  `Amount` int(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`Empid`, `Firstname`, `Lastname`, `Gender`, `DOB`, `Department`, `Status`, `Datehired`, `Jobtittle`, `ClearDate`, `Leave_typ`, `Days`, `Period`, `Fromdate`, `Todate`, `Pay`, `Amount`) VALUES
('EP1000', 'Martin', 'Wanyama', 'Male', '1989-04-15', 'Catering', 'Suspended', '2020-04-14', 'Caterer', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1001', 'Lucy', 'Njeri', 'Female', '1993-04-15', 'Catering', 'Active', '2019-03-14', 'Cleaner', NULL, 'Medical', 30, '1 Mnth', '2020-04-14', '2020-05-14', 'Yes', 2000),
('EP1002', 'Aarone', 'Kemboi', 'Male', '1994-02-02', 'Catering', 'Active', '2020-04-14', 'Cook', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1003', 'John', 'Otienno', 'Male', '1980-04-18', 'Doctor', 'Active', '2020-04-14', 'Dentist', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1004', 'Caren', 'Martha', 'Female', '1993-10-21', 'Doctor', 'Active', '2020-04-14', 'Cardiologist', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1005', 'Moon', 'Abdi', 'Male', '1993-04-22', 'Doctor', 'Active', '2020-04-14', 'Dermatologist', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1006', 'Fatuma', 'Hassan', 'Female', '1989-04-07', 'Doctor', 'Active', '2020-04-14', 'Physicians', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1007', 'Faith', 'Nkatha', 'Female', '1982-04-09', 'Finance', 'Active', '2020-04-14', 'Accountant', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1008', 'Hillary', 'Mwendia', 'Male', '1995-04-20', 'Finance', 'Active', '2020-04-14', 'Bursar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1009', 'Mark', 'Jonah', 'Male', '1979-04-13', 'Finance', 'Cleared', '2020-04-14', 'Finance Clerk', '2020-07-14', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1010', 'Ruth', 'Njeri', 'Female', '1976-04-15', 'House Keeping', 'Suspended', '2020-04-14', 'Cleaner', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1011', 'Peter', 'Kiptoo', 'Male', '1981-04-22', 'House Keeping', 'Active', '2020-04-14', 'Cleaner', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1012', 'Celine', 'Ikoku', 'Female', '1987-04-02', 'House Keeping', 'Active', '2020-04-14', 'Cleaner', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1013', 'Rose', 'Wamaitha', 'Female', '1985-04-09', 'HR', 'Active', '2020-04-14', 'Recruiting Officer', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1014', 'Kevin', 'Omondi', 'Male', '1992-04-16', 'HR', 'Active', '2020-04-14', 'Financial officer', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1015', 'Allein', 'Joe', 'Male', '1979-04-12', 'HR', 'Active', '2013-04-12', 'Team Manager', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1016', 'Nancy', 'Ndaqwaj', 'Female', '1974-04-30', 'Laboratory', 'Active', '2020-04-14', 'Immunologist', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1017', 'Biram', 'Wanyonyi', 'Male', '1998-04-23', 'Laboratory', 'Active', '2020-04-14', 'Biologist', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1018', 'Mohabe', 'Chacha', 'Male', '1985-11-27', 'Laboratory', 'Active', '2020-04-14', 'Molecular Specialist', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1019', 'Joan', 'Wafula', 'Female', '1993-04-08', 'Nurse', 'Active', '2020-04-14', 'Midwife', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1020', 'Paul', 'Kennedy', 'Male', '1991-04-30', 'Nurse', 'Active', '2020-04-14', 'Anesthetist', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1021', 'Purity', 'Chepkoech', 'Female', '1982-04-21', 'Nurse', 'Active', '2020-04-14', 'Clinical Nurse', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1022', 'Matheka', 'Mbithi', 'Male', '1980-04-16', 'Pharmacy', 'Active', '2020-04-14', 'Clinician', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1023', 'Cecil', 'Kwoba', 'Male', '2002-04-24', 'Pharmacy', 'Active', '2020-04-14', 'Regulator', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1024', 'Phelix', 'Waudo', 'Male', '1993-04-22', 'Pharmacy', 'Active', '2020-04-14', 'Standards Officer', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1025', 'Pauline', 'Atieno', 'Female', '1984-04-19', 'Reception', 'Active', '2020-04-14', 'Public Relations', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1026', 'Valarie', 'Cherono', 'Female', '1992-04-15', 'Reception', 'Active', '2020-04-14', 'Communication Officer', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1027', 'Winnie', 'Mwanza', 'Female', '1986-04-10', 'Reception', 'Active', '2020-04-14', 'Team Manager', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1028', 'Caren', 'Meru', 'Female', '1985-04-18', 'Catering', 'Active', '2020-04-15', 'cook', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('EP1029', 'Paul', 'Paul', 'Male', '1983-04-21', 'HR', 'Cleared', '2020-04-15', 'jhjgjhghj', '2020-07-23', 'Vacation', 30, '1 Mnth', '2020-04-15', '2020-05-15', 'Yes', 1500);

-- --------------------------------------------------------

--
-- Table structure for table `emp_payment`
--

CREATE TABLE `emp_payment` (
  `Payid` varchar(10) NOT NULL,
  `Empid` varchar(10) NOT NULL,
  `FN` varchar(50) NOT NULL,
  `LN` varchar(50) NOT NULL,
  `Gender` varchar(9) NOT NULL,
  `DOB` varchar(11) NOT NULL,
  `Dept` varchar(18) NOT NULL,
  `Status` varchar(10) NOT NULL,
  `Hire_date` varchar(12) NOT NULL,
  `Job_ttle` varchar(30) NOT NULL,
  `Regdate` varchar(12) NOT NULL,
  `Bsc_Sal` int(8) NOT NULL,
  `Ovr_tme` int(8) NOT NULL,
  `Medical` int(8) NOT NULL,
  `Bonus` int(8) NOT NULL,
  `Other` int(8) NOT NULL,
  `Total_Ovrtme` int(8) NOT NULL,
  `RPH` int(8) NOT NULL,
  `Total` int(8) NOT NULL,
  `Paid` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `emp_payment`
--

INSERT INTO `emp_payment` (`Payid`, `Empid`, `FN`, `LN`, `Gender`, `DOB`, `Dept`, `Status`, `Hire_date`, `Job_ttle`, `Regdate`, `Bsc_Sal`, `Ovr_tme`, `Medical`, `Bonus`, `Other`, `Total_Ovrtme`, `RPH`, `Total`, `Paid`) VALUES
('SA1002', 'EP1001', 'Lucy', 'Njeri', 'Female', '1993-04-15', 'Catering', 'Active', '2019-03-14', 'Cleaner', '2020-04-14', 30000, 150, 1000, 1000, 1000, 150, 225, 36750, 'Yes'),
('SA1003', 'EP1003', 'John', 'Otienno', 'Male', '1980-04-18', 'Doctor', 'Active', '2020-04-14', 'Dentist', '2020-04-14', 63000, 150, 1000, 1000, 1000, 315, 225, 73875, 'Yes'),
('SA1004', 'EP1007', 'Faith', 'Nkatha', 'Female', '1982-04-09', 'Finance', 'Active', '2020-04-14', 'Accountant', '2020-04-14', 55000, 150, 1000, 1000, 1000, 275, 225, 64875, 'Yes'),
('SA1005', 'EP1011', 'Peter', 'Kiptoo', 'Male', '1981-04-22', 'House Keeping', 'Active', '2020-04-14', 'Cleaner', '2020-04-14', 25000, 150, 1000, 1000, 1000, 125, 225, 31125, 'Yes'),
('SA1006', 'EP1013', 'Rose', 'Wamaitha', 'Female', '1985-04-09', 'HR', 'Active', '2020-04-14', 'Recruiting Officer', '2020-04-14', 50000, 150, 1000, 1000, 1000, 250, 225, 59250, 'Yes'),
('SA1007', 'EP1016', 'Nancy', 'Ndaqwaj', 'Female', '1974-04-30', 'Laboratory', 'Active', '2020-04-14', 'Immunologist', '2020-04-14', 58000, 150, 1000, 1000, 1000, 290, 225, 68250, 'Yes'),
('SA1008', 'EP1019', 'Joan', 'Wafula', 'Female', '1993-04-08', 'Nurse', 'Active', '2020-04-14', 'Midwife', '2020-04-14', 60000, 150, 1000, 1000, 1000, 300, 225, 70500, 'Yes'),
('SA1009', 'EP1022', 'Matheka', 'Mbithi', 'Male', '1980-04-16', 'Pharmacy', 'Active', '2020-04-14', 'Clinician', '2020-04-14', 54000, 150, 1000, 1000, 1000, 270, 225, 63750, 'Yes'),
('SA1010', 'EP1025', 'Pauline', 'Atieno', 'Female', '1984-04-19', 'Reception', 'Active', '2020-04-14', 'Public Relations', '2020-04-14', 35000, 150, 1000, 1000, 1000, 175, 225, 42375, 'Yes'),
('SA1011', 'EP1029', 'Paul', 'Paul', 'Male', '1983-04-21', 'HR', 'Active', '2020-04-15', 'jhjgjhghj', '2020-04-15', 50000, 150, 1000, 1000, 1000, 250, 225, 59250, 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `hms_doctors`
--

CREATE TABLE `hms_doctors` (
  `Empid` varchar(10) NOT NULL,
  `FN` varchar(50) NOT NULL,
  `LN` varchar(50) NOT NULL,
  `Gender` varchar(15) NOT NULL,
  `DOB` varchar(15) NOT NULL,
  `Dept` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hms_doctors`
--

INSERT INTO `hms_doctors` (`Empid`, `FN`, `LN`, `Gender`, `DOB`, `Dept`) VALUES
('EP1003', 'John', 'Otienno', 'Male', '1980-04-18', 'Doctor'),
('EP1004', 'Caren', 'Martha', 'Female', '1993-10-21', 'Doctor'),
('EP1005', 'Moon', 'Abdi', 'Male', '1993-04-22', 'Doctor'),
('EP1006', 'Fatuma', 'Hassan', 'Female', '1989-04-07', 'Doctor');

-- --------------------------------------------------------

--
-- Table structure for table `inpat_billing`
--

CREATE TABLE `inpat_billing` (
  `Patid` varchar(10) NOT NULL,
  `FN` varchar(50) NOT NULL,
  `LN` varchar(50) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Guardian` varchar(50) NOT NULL,
  `Bld_grp` varchar(10) NOT NULL,
  `Pat_typ` varchar(12) NOT NULL,
  `Age` int(3) NOT NULL,
  `Regdate` varchar(12) NOT NULL,
  `Service` varchar(15) NOT NULL,
  `Bookdate` varchar(12) NOT NULL,
  `Wrd_typ` varchar(12) NOT NULL,
  `Wrd_no` varchar(4) NOT NULL,
  `Bed_no` varchar(3) NOT NULL,
  `Wrd_chrg` int(12) NOT NULL,
  `Srv_chrg` int(12) NOT NULL,
  `Billdate` varchar(12) NOT NULL,
  `Total` int(12) NOT NULL,
  `Pay` varchar(15) NOT NULL,
  `Rcp_no` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `inpat_billing`
--

INSERT INTO `inpat_billing` (`Patid`, `FN`, `LN`, `Gender`, `Guardian`, `Bld_grp`, `Pat_typ`, `Age`, `Regdate`, `Service`, `Bookdate`, `Wrd_typ`, `Wrd_no`, `Bed_no`, `Wrd_chrg`, `Srv_chrg`, `Billdate`, `Total`, `Pay`, `Rcp_no`) VALUES
('PT1002', 'Vincent', 'Kwoba', 'Male', 'Elizabeth Potoril', 'AB+', 'Inpatient', 56, '2020-04-14', 'Prostratic', '2020-04-14', 'ICU', 'WD1', '102', 2000, 2800, '2020-05-31', 225600, 'Cash', ''),
('PT1003', 'Emily', 'Anita', 'Female', 'Daina Wawery', 'B', 'Inpatient', 45, '2020-04-14', 'Maternity', '2020-04-14', 'Maternity', 'WD1', '101', 1700, 2900, '2020-04-30', 73600, 'Cheque', 'RJH766J9'),
('PT1005', 'Rebecca', 'Moraa', 'Female', 'Abraham Mrume', 'Unknown', 'Inpatient', 14, '2020-04-14', 'Surgery', '2020-04-14', 'ICU', 'WD2', '201', 2000, 3200, '2020-04-23', 46800, 'Cash', ''),
('PT1007', 'Patricia', 'Mwende', 'Female', 'Morgan Nadia', 'O+', 'Inpatient', 67, '2020-04-14', 'Surgery', '2020-04-14', 'ICU', 'WD3', '305', 2000, 3200, '2020-06-23', 364000, 'Debit Card', ''),
('PT1009', 'Sandra', 'Sandy', 'Female', 'Rosa Awinja', 'O-', 'Inpatient', 34, '2020-04-14', 'ICU', '2020-04-14', 'ICU', 'WD3', '304', 2000, 3500, '2020-05-20', 198000, 'Cheque', 'JGIO08990'),
('PT1010', 'Paul', 'Walker', 'Male', 'Were Wanja', 'B', 'Inpatient', 25, '2020-04-15', 'Surgery', '2020-04-10', 'Male', 'WD2', '203', 1000, 3200, '2020-10-14', 785400, 'Cheque', 'TFG787Hl');

-- --------------------------------------------------------

--
-- Table structure for table `issuedrugs`
--

CREATE TABLE `issuedrugs` (
  `Patientid` varchar(10) NOT NULL,
  `Firstname` varchar(50) NOT NULL,
  `Lastname` varchar(50) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Drugname` varchar(50) NOT NULL,
  `Drugid` varchar(8) NOT NULL,
  `Unitofmeasure` varchar(8) NOT NULL,
  `Category` varchar(15) NOT NULL,
  `Quantity` varchar(50) NOT NULL,
  `Expdate` varchar(30) NOT NULL,
  `Unit_price` int(10) NOT NULL,
  `Price` int(6) NOT NULL,
  `Dosage` varchar(30) NOT NULL,
  `Storage` varchar(50) NOT NULL,
  `Precautions` varchar(50) DEFAULT NULL,
  `Sideeffects` varchar(100) DEFAULT NULL,
  `Issued` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `issuedrugs`
--

INSERT INTO `issuedrugs` (`Patientid`, `Firstname`, `Lastname`, `Gender`, `Drugname`, `Drugid`, `Unitofmeasure`, `Category`, `Quantity`, `Expdate`, `Unit_price`, `Price`, `Dosage`, `Storage`, `Precautions`, `Sideeffects`, `Issued`) VALUES
('PT1000', 'Mark', 'Wayne', 'Male', 'Acetazolamide 125mg', 'Acet12', 'mg', 'Tablets', '12', '2022-03-27', 400, 4800, '1x3', 'Cool dry place,away from the sunlight and children', ' Do Not Work After Taking The Drug', 'Dizziness,Headache,Vomiting', 'Yes'),
('PT1001', 'Mercy', 'Lucky', 'Female', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', '12', '2025-03-15', 787, 9444, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting\n', 'Yes'),
('PT1002', 'Vincent', 'Kwoba', 'Male', 'Anticoagulant Citrate 10ml', 'Anti33', 'ml', 'Liquid', '12', '2024-03-22', 500, 6000, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting', 'No'),
('PT1003', 'Emily', 'Anita', 'Female', 'Amoxycilin 500mg', 'Amox97', 'mg', 'Capsules', '32', '2023-03-22', 300, 9600, '1x3', 'Cool dry place,away from the sunlight and children', 'Don\'t Take Alcohol', 'Dizziness,Headache,Vomiting', 'Yes'),
('PT1004', 'Henry', 'Wanyama', 'Male', 'Antisnake Venom 10ml', 'Anti44', 'ml', 'Liquid', '2', '2022-03-25', 1000, 2000, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting', 'Yes'),
('PT1005', 'Rebecca', 'Moraa', 'Female', 'Betamethasone 2mg', 'Beta19', 'mg', 'Tablets', '12', '2022-03-25', 500, 6000, '1x1', 'Cool dry place,away from the sunlight and children', 'Do Not Work After Taking The Drug', 'Dizziness,Headache,Vomiting\n', 'Yes'),
('PT1006', 'Adam', 'Wainana', 'Male', 'Biphasic Insulin 100 ml', 'Biph11', 'ml', 'Syrup', '12', '2022-03-25', 700, 6000, '1 Spn x3', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting', 'Yes'),
('PT1007', 'Patricia', 'Mwende', 'Female', 'Castor Oil  10ml', 'Cast10', 'ml', 'Gel', '4', '2023-03-17', 700, 2000, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Skin Rash\n', 'Yes'),
('PT1008', 'Mark', 'Well', 'Male', 'Cetirizine  Solution 1mg/ml', 'Ceti23', 'mg/ml', 'Injection', '11', '2022-03-25', 767, 8437, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Don\'t Take Alcohol', 'Dizziness,Headache,Vomiting', 'Yes'),
('PT1009', 'Sandra', 'Sandy', 'Female', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', '12', '2025-03-15', 787, 8400, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting\n', 'Yes'),
('PT1010', 'Paul', 'Walker', 'Male', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', '123', '2025-03-15', 787, 96801, '1 Spn x3', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting\n', 'Yes'),
('PT1011', 'Maria', 'Lutherford', 'Female', 'Antisnake Venom 10ml', 'Anti44', 'ml', 'Liquid', '50', '2022-03-25', 1000, 50000, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting', 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `lab_results`
--

CREATE TABLE `lab_results` (
  `Patientid` varchar(10) NOT NULL,
  `Firstname` varchar(50) NOT NULL,
  `Lastname` varchar(50) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Maritalstatus` varchar(8) NOT NULL,
  `Guardianname` varchar(50) NOT NULL,
  `Bloodgroup` varchar(8) NOT NULL,
  `Patienttype` varchar(12) NOT NULL,
  `Weight` int(3) NOT NULL,
  `Age` int(3) NOT NULL,
  `Bodytemp` int(3) NOT NULL,
  `Testdate` varchar(15) NOT NULL,
  `Type_test` varchar(15) NOT NULL,
  `Sampletype` varchar(16) NOT NULL,
  `Sampleid` varchar(5) NOT NULL,
  `Tech_name` varchar(30) NOT NULL,
  `Resultsdate` varchar(15) NOT NULL,
  `Diagnosis` varchar(50) NOT NULL,
  `Results` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `lab_results`
--

INSERT INTO `lab_results` (`Patientid`, `Firstname`, `Lastname`, `Gender`, `Maritalstatus`, `Guardianname`, `Bloodgroup`, `Patienttype`, `Weight`, `Age`, `Bodytemp`, `Testdate`, `Type_test`, `Sampletype`, `Sampleid`, `Tech_name`, `Resultsdate`, `Diagnosis`, `Results`) VALUES
('PT1000', 'Mark', 'Wayne', 'Male', 'Single', 'Paul Wandera', 'O', 'Outpatient', 76, 23, 32, '2020-04-15', 'Blood Count', 'Blood', 'BL21', 'Dr Bruce Wakoli', '2020-04-15', 'Malaria', 'WBC-6.9 K/mcL 4.8-10.8\nRBC-1.8 M/mcl 4.6-6.1\nHB/Hgb-6.5 g/dL 14.0-18.0'),
('PT1001', 'Mercy', 'Lucky', 'Female', 'Married', 'John Thui', 'Unknown', 'Outpatient', 65, 34, 33, '2020-04-15', 'Molecular', 'Semen', 'SE69', 'Dr Bruce Wakoli', '2020-04-15', 'Menengitis', 'CRP 233.34/ch/ML\nPJIs 10mg/L-78%\nneutrophil 1.2-4.5\n'),
('PT1002', 'Vincent', 'Kwoba', 'Male', 'Divorced', 'Elizabeth Potoril', 'AB+', 'Inpatient', 78, 56, 33, '2020-04-15', 'Histopathology', 'Blood', 'BL21', 'Dr Bruce Wakoli', '2020-04-15', 'Prostate Cancer', 'CRP 233.34/ch/ML,PJIs 10mg/L-78%,neutrophil 1.2-4.5WBC-6.9 K/mcL 4.8-10.8,RBC-1.8 M/mcl 4.6-6.1,HB/Hgb-6.5 g/dL 14.0-18.0'),
('PT1003', 'Emily', 'Anita', 'Female', 'Married', 'Daina Wawery', 'B', 'Inpatient', 98, 45, 38, '2020-04-15', 'Prothrombine', 'Blood', 'BL21', 'Dr Emiliy Emily', '2020-04-22', 'Pregnant', 'RDW 16.0 H 15-16%,Platelet Count 180 K/mcL 150-450,MCHC 33.3 g/dL 32.0-36.0'),
('PT1004', 'Henry', 'Wanyama', 'Male', 'Single', 'Julia Murray', 'AB', 'Outpatient', 89, 45, 34, '2020-04-15', 'Toxicology', 'Stool', 'ST89', 'Dr Plilip Oman', '2020-04-17', 'Cholera', 'Pathogens:Cholerae,Bacteria-Bascolli,Prescence of Blood and Mucus'),
('PT1005', 'Rebecca', 'Moraa', 'Female', 'Single', 'Abraham Mrume', 'Unknown', 'Inpatient', 55, 14, 37, '2020-04-15', 'Lipid', 'Tissue', 'TE21', 'Dr Henry Waithaka', '2020-04-17', 'Kidney Failure', 'HPV 12.44%,Pale yellow,Kidney stones'),
('PT1006', 'Adam', 'Wainana', 'Male', 'Married', 'Jessica Wanja', 'B', 'Outpatient', 78, 45, 45, '2020-04-15', 'Virology', 'Saliva', 'SA09', 'Dr Mary Moraa', '2020-04-22', 'TB', 'Mucus Cnc 88.8%,pathogen-brucelae,Starch Conc 11%'),
('PT1007', 'Patricia', 'Mwende', 'Female', 'Divorced', 'Morgan Nadia', 'O+', 'Inpatient', 178, 67, 38, '2020-04-15', 'Toxicology', 'Stool', 'ST89', 'Dr Bruce Wayne', '2020-04-22', 'Appendicis', 'Bacteria-Bascolli&Ciciliae,Prescence of Blood and Mucus'),
('PT1008', 'Mark', 'Well', 'Male', 'Married', 'Paul obama', 'Unknown', 'Outpatient', 112, 56, 44, '2020-04-15', 'Blood Count', 'Blood', 'BL21', 'Dr Ken Wery', '2020-04-23', 'Malaria', 'Bacteria-Plasmodium,WBC-6.9 K/mcL 4.8-10.8,RBC-1.8 M/mcl 4.6-6.1,HB/Hgb-6.5 g/dL 14.0-18.0,CRP 233.34/ch/ML,PJIs 10mg/L-78%,neutrophil 1.2-4.5'),
('PT1009', 'Sandra', 'Sandy', 'Female', 'Single', 'Rosa Awinja', 'O-', 'Inpatient', 78, 34, 38, '2020-04-15', 'Histopathology', 'Skin', 'SK76', 'Dr Luke maina', '2020-04-16', 'Lukemia', 'Dry Skin,Pale White,Low Melanin'),
('PT1010', 'Paul', 'Walker', 'Male', 'Single', 'Were Wanja', 'B', 'Inpatient', 78, 25, 23, '2020-04-15', 'Histopathology', 'Tissue', 'TE21', 'Dr mark', '2020-04-17', 'Hepatitis B', 'WBC-6.9 K/mcL 4.8-10.8'),
('PT1011', 'Maria', 'Lutherford', 'Female', 'Single', 'Man Wetu', 'AB', 'Outpatient', 88, 26, 56, '2020-04-15', 'Blood Count', 'Blood', 'BL21', 'Dr Bruce Wakoli', '2020-04-17', 'Malaria', 'WBC-6.9 K/mcL 4.8-10.8');

-- --------------------------------------------------------

--
-- Table structure for table `outpat_billing`
--

CREATE TABLE `outpat_billing` (
  `Patid` varchar(10) NOT NULL,
  `FN` varchar(30) NOT NULL,
  `LN` varchar(30) NOT NULL,
  `Gender` varchar(12) NOT NULL,
  `Marital` varchar(8) NOT NULL,
  `Guardian` varchar(50) NOT NULL,
  `Bld_grp` varchar(8) NOT NULL,
  `Pat_type` varchar(11) NOT NULL,
  `Age` int(3) NOT NULL,
  `Regdate` varchar(11) NOT NULL,
  `Service` varchar(14) NOT NULL,
  `Billdate` varchar(11) NOT NULL,
  `Charges` int(8) NOT NULL,
  `Total` int(8) NOT NULL,
  `Pay_mode` varchar(15) NOT NULL,
  `Rcp_No` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `outpat_billing`
--

INSERT INTO `outpat_billing` (`Patid`, `FN`, `LN`, `Gender`, `Marital`, `Guardian`, `Bld_grp`, `Pat_type`, `Age`, `Regdate`, `Service`, `Billdate`, `Charges`, `Total`, `Pay_mode`, `Rcp_No`) VALUES
('PT1000', 'Mark', 'Wayne', 'Male', 'Single', 'Paul Wandera', 'O', 'Outpatient', 23, '2020-04-14', 'General', '2020-04-14', 2000, 2500, 'Cash', ''),
('PT1001', 'Mercy', 'Lucky', 'Female', 'Married', 'John Thui', 'Unknown', 'Outpatient', 34, '2020-04-14', 'Gynacological', '2020-04-14', 3000, 3500, 'Debit Card', ''),
('PT1004', 'Henry', 'Wanyama', 'Male', 'Single', 'Julia Murray', 'AB', 'Outpatient', 45, '2020-04-14', 'General', '2020-04-14', 2000, 2500, 'Debit Card', ''),
('PT1006', 'Adam', 'Wainana', 'Male', 'Married', 'Jessica Wanja', 'B', 'Outpatient', 45, '2020-04-14', 'General', '2020-04-14', 2000, 2500, 'Cheque', 'TR768J8'),
('PT1008', 'Mark', 'Well', 'Male', 'Married', 'Paul obama', 'Unknown', 'Outpatient', 56, '2020-04-14', 'General', '2020-04-14', 2000, 2500, 'Cash', ''),
('PT1011', 'Maria', 'Lutherford', 'Female', 'Single', 'Man Wetu', 'AB', 'Outpatient', 26, '2020-04-15', 'General', '2020-04-15', 2000, 2500, 'Cash', '');

-- --------------------------------------------------------

--
-- Table structure for table `pat_assignward`
--

CREATE TABLE `pat_assignward` (
  `Patid` varchar(10) NOT NULL,
  `FN` varchar(30) NOT NULL,
  `LN` varchar(30) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Marital` varchar(8) NOT NULL,
  `Guardian` varchar(50) NOT NULL,
  `Bld_grp` varchar(8) NOT NULL,
  `Pat_type` varchar(11) NOT NULL,
  `Age` int(3) NOT NULL,
  `Regdate` varchar(11) NOT NULL,
  `Service` varchar(14) NOT NULL,
  `Bookdate` varchar(11) NOT NULL,
  `Wardtype` varchar(11) NOT NULL,
  `Wardno` varchar(3) NOT NULL,
  `Bedno` int(3) NOT NULL,
  `Disch_date` varchar(11) DEFAULT NULL,
  `Status` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pat_assignward`
--

INSERT INTO `pat_assignward` (`Patid`, `FN`, `LN`, `Gender`, `Marital`, `Guardian`, `Bld_grp`, `Pat_type`, `Age`, `Regdate`, `Service`, `Bookdate`, `Wardtype`, `Wardno`, `Bedno`, `Disch_date`, `Status`) VALUES
('PT1002', 'Vincent', 'Kwoba', 'Male', 'Divorced', 'Elizabeth Potoril', 'AB+', 'Inpatient', 56, '2020-04-14', 'Prostratic', '2020-04-14', 'ICU', 'WD1', 102, '2020-04-22', 'Discharged'),
('PT1003', 'Emily', 'Anita', 'Female', 'Married', 'Daina Wawery', 'B', 'Inpatient', 45, '2020-04-14', 'Maternity', '2020-04-14', 'Maternity', 'WD1', 101, '2020-04-25', 'Discharged'),
('PT1005', 'Rebecca', 'Moraa', 'Female', 'Single', 'Abraham Mrume', 'Unknown', 'Inpatient', 14, '2020-04-14', 'Surgery', '2020-04-14', 'ICU', 'WD2', 201, '2020-04-25', 'Discharged'),
('PT1007', 'Patricia', 'Mwende', 'Female', 'Divorced', 'Morgan Nadia', 'O+', 'Inpatient', 67, '2020-04-14', 'Surgery', '2020-04-14', 'ICU', 'WD3', 305, '2020-04-29', 'Discharged'),
('PT1009', 'Sandra', 'Sandy', 'Female', 'Single', 'Rosa Awinja', 'O-', 'Inpatient', 34, '2020-04-14', 'ICU', '2020-04-14', 'ICU', 'WD3', 304, '2020-04-30', 'Discharged'),
('PT1010', 'Paul', 'Walker', 'Male', 'Single', 'Were Wanja', 'B', 'Inpatient', 25, '2020-04-15', 'Surgery', '2020-04-10', 'Male', 'WD2', 203, '2020-08-27', 'Discharged');

-- --------------------------------------------------------

--
-- Table structure for table `pat_doctor`
--

CREATE TABLE `pat_doctor` (
  `Patid` varchar(10) NOT NULL,
  `FN` varchar(50) NOT NULL,
  `LN` varchar(50) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Marital` varchar(8) NOT NULL,
  `Guardian` varchar(50) NOT NULL,
  `Bld_grp` varchar(8) NOT NULL,
  `Pat_type` varchar(11) NOT NULL,
  `Weight` int(3) NOT NULL,
  `Age` int(3) NOT NULL,
  `Regdate` varchar(11) NOT NULL,
  `Service` varchar(13) NOT NULL,
  `Samp_typ` varchar(14) NOT NULL,
  `Samp_id` varchar(4) NOT NULL,
  `Bld_prs` int(3) NOT NULL,
  `Disease` varchar(50) NOT NULL,
  `Symptoms` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pat_doctor`
--

INSERT INTO `pat_doctor` (`Patid`, `FN`, `LN`, `Gender`, `Marital`, `Guardian`, `Bld_grp`, `Pat_type`, `Weight`, `Age`, `Regdate`, `Service`, `Samp_typ`, `Samp_id`, `Bld_prs`, `Disease`, `Symptoms`) VALUES
('PT1000', 'Mark', 'Wayne', 'Male', 'Single', 'Paul Wandera', 'O', 'Outpatient', 76, 23, '2020-04-14', 'General', 'Blood', 'BL21', 122, 'Malaria', 'High fever,headache and vomiting'),
('PT1001', 'Mercy', 'Lucky', 'Female', 'Married', 'John Thui', 'Unknown', 'Outpatient', 65, 34, '2020-04-14', 'Gynacological', 'Semen', 'SE69', 123, 'Menengitis', 'High fever,vomiting'),
('PT1002', 'Vincent', 'Kwoba', 'Male', 'Divorced', 'Elizabeth Potoril', 'AB+', 'Inpatient', 78, 56, '2020-04-14', 'Prostratic', 'Blood', 'BL21', 122, 'Prostate Cancer', 'High fever,headache and vomiting'),
('PT1003', 'Emily', 'Anita', 'Female', 'Married', 'Daina Wawery', 'B', 'Inpatient', 98, 45, '2020-04-14', 'Maternity', 'Blood', 'BL21', 122, 'Pregnant', 'High fever,headache and vomiting'),
('PT1004', 'Henry', 'Wanyama', 'Male', 'Single', 'Julia Murray', 'AB', 'Outpatient', 89, 45, '2020-04-14', 'General', 'Stool', 'ST89', 132, 'Cholera', 'Diarrhea,headache and vomiting'),
('PT1005', 'Rebecca', 'Moraa', 'Female', 'Single', 'Abraham Mrume', 'Unknown', 'Inpatient', 55, 14, '2020-04-14', 'Surgery', 'Tissue', 'TE21', 133, 'Kidney Failure', 'Diarrhea,headache '),
('PT1006', 'Adam', 'Wainana', 'Male', 'Married', 'Jessica Wanja', 'B', 'Outpatient', 78, 45, '2020-04-14', 'General', 'Saliva', 'SA09', 122, 'TB', 'Coughing,headache '),
('PT1007', 'Patricia', 'Mwende', 'Female', 'Divorced', 'Morgan Nadia', 'O+', 'Inpatient', 178, 67, '2020-04-14', 'Surgery', 'Stool', 'ST89', 122, 'Appendicis', 'Stomachache,Diarrhea '),
('PT1008', 'Mark', 'Well', 'Male', 'Married', 'Paul obama', 'Unknown', 'Outpatient', 112, 56, '2020-04-14', 'General', 'Blood', 'BL21', 122, 'Malaria', 'High fever,headache and vomiting'),
('PT1009', 'Sandra', 'Sandy', 'Female', 'Single', 'Rosa Awinja', 'O-', 'Inpatient', 78, 34, '2020-04-14', 'ICU', 'Skin', 'SK76', 122, 'Leukemia', 'High fever,headache and vomiting'),
('PT1010', 'Paul', 'Walker', 'Male', 'Single', 'Were Wanja', 'B', 'Inpatient', 78, 25, '2020-04-15', 'Surgery', 'Tissue', 'TE21', 122, 'Hepatitis B', 'Abdominal pains,yellow stained urine'),
('PT1011', 'Maria', 'Lutherford', 'Female', 'Single', 'Man Wetu', 'AB', 'Outpatient', 88, 26, '2020-04-15', 'General', 'Blood', 'BL21', 122, 'Malaria', 'High fever,Headache,vomiting');

-- --------------------------------------------------------

--
-- Table structure for table `pat_nurse`
--

CREATE TABLE `pat_nurse` (
  `Patientid` varchar(10) NOT NULL,
  `Firstname` varchar(50) NOT NULL,
  `Lastname` varchar(50) NOT NULL,
  `Gender` varchar(12) NOT NULL,
  `Marital` varchar(20) NOT NULL,
  `Guardian` varchar(50) NOT NULL,
  `Bld_grp` varchar(8) NOT NULL,
  `Pat_type` varchar(15) NOT NULL,
  `Weight` int(3) NOT NULL,
  `Age` int(3) NOT NULL,
  `Regdate` varchar(12) NOT NULL,
  `Service` varchar(15) NOT NULL,
  `Body_temp` int(3) NOT NULL,
  `Samp_type` varchar(30) NOT NULL,
  `Samp_id` varchar(8) NOT NULL,
  `Bld_pressure` int(10) NOT NULL,
  `Disease` varchar(100) NOT NULL,
  `Symptoms` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pat_nurse`
--

INSERT INTO `pat_nurse` (`Patientid`, `Firstname`, `Lastname`, `Gender`, `Marital`, `Guardian`, `Bld_grp`, `Pat_type`, `Weight`, `Age`, `Regdate`, `Service`, `Body_temp`, `Samp_type`, `Samp_id`, `Bld_pressure`, `Disease`, `Symptoms`) VALUES
('PT1000', 'Mark', 'Wayne', 'Male', 'Single', 'Paul Wandera', 'O', 'Outpatient', 76, 23, '2020-04-14', 'General', 32, 'Blood', 'BL21', 122, 'Malaria', 'High fever,headache and vomiting'),
('PT1001', 'Mercy', 'Lucky', 'Female', 'Married', 'John Thui', 'Unknown', 'Outpatient', 65, 34, '2020-04-14', 'Gynacological', 33, 'Semen', 'SE69', 123, 'Menengitis', 'High fever,vomiting'),
('PT1002', 'Vincent', 'Kwoba', 'Male', 'Divorced', 'Elizabeth Potoril', 'AB+', 'Inpatient', 78, 56, '2020-04-14', 'Prostratic', 33, 'Blood', 'BL21', 122, 'Prostate Cancer', 'High fever,headache and vomiting'),
('PT1003', 'Emily', 'Anita', 'Female', 'Married', 'Daina Wawery', 'B', 'Inpatient', 98, 45, '2020-04-14', 'Maternity', 38, 'Blood', 'BL21', 122, 'Pregnant', 'High fever,headache and vomiting'),
('PT1004', 'Henry', 'Wanyama', 'Male', 'Single', 'Julia Murray', 'AB', 'Outpatient', 89, 45, '2020-04-14', 'General', 34, 'Stool', 'ST89', 132, 'Cholera', 'Diarrhea,headache and vomiting'),
('PT1005', 'Rebecca', 'Moraa', 'Female', 'Single', 'Abraham Mrume', 'Unknown', 'Inpatient', 55, 14, '2020-04-14', 'Surgery', 37, 'Tissue', 'TE21', 133, 'Kidney Failure', 'Diarrhea,headache '),
('PT1006', 'Adam', 'Wainana', 'Male', 'Married', 'Jessica Wanja', 'B', 'Outpatient', 78, 45, '2020-04-14', 'General', 45, 'Saliva', 'SA09', 122, 'TB', 'Coughing,headache '),
('PT1007', 'Patricia', 'Mwende', 'Female', 'Divorced', 'Morgan Nadia', 'O+', 'Inpatient', 178, 67, '2020-04-14', 'Surgery', 38, 'Stool', 'ST89', 122, 'Appendicis', 'Stomachache,Diarrhea '),
('PT1008', 'Mark', 'Well', 'Male', 'Married', 'Paul obama', 'Unknown', 'Outpatient', 112, 56, '2020-04-14', 'General', 44, 'Blood', 'BL21', 122, 'Malaria', 'High fever,headache and vomiting'),
('PT1009', 'Sandra', 'Sandy', 'Female', 'Single', 'Rosa Awinja', 'O-', 'Inpatient', 78, 34, '2020-04-14', 'ICU', 38, 'Skin', 'SK76', 122, 'Lukemia', 'High fever,headache and vomiting'),
('PT1010', 'Paul', 'Walker', 'Male', 'Single', 'Were Wanja', 'B', 'Inpatient', 78, 25, '2020-04-15', 'Surgery', 23, 'Tissue', 'TE21', 122, 'Hepatitis B', 'Abdominal pains,yellow stained urine'),
('PT1011', 'Maria', 'Lutherford', 'Female', 'Single', 'Man Wetu', 'AB', 'Outpatient', 88, 26, '2020-04-15', 'General', 56, 'Blood', 'BL21', 122, 'Malaria', 'High fever,Headache,vomiting');

-- --------------------------------------------------------

--
-- Table structure for table `pat_reg`
--

CREATE TABLE `pat_reg` (
  `Patientid` varchar(10) NOT NULL,
  `Firstname` varchar(50) NOT NULL,
  `Lastname` varchar(50) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Phoneno` varchar(10) NOT NULL,
  `Email` varchar(30) DEFAULT NULL,
  `Maritalstatus` varchar(10) NOT NULL,
  `Guardian` varchar(50) NOT NULL,
  `County` varchar(50) NOT NULL,
  `Subcounty` varchar(50) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Bloodgroup` varchar(8) NOT NULL,
  `Patienttype` varchar(12) NOT NULL,
  `Weight` int(3) DEFAULT NULL,
  `Age` int(3) NOT NULL,
  `Regdate` varchar(30) NOT NULL,
  `Medhist` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pat_reg`
--

INSERT INTO `pat_reg` (`Patientid`, `Firstname`, `Lastname`, `Gender`, `Phoneno`, `Email`, `Maritalstatus`, `Guardian`, `County`, `Subcounty`, `Address`, `Bloodgroup`, `Patienttype`, `Weight`, `Age`, `Regdate`, `Medhist`) VALUES
('PT1000', 'Mark', 'Wayne', 'Male', '0789237612', 'markwayne@gmail.com', 'Single', 'Paul Wandera', 'Nairobi', 'Kasarani', 'Baraka Est Ruaraka', 'O', 'Pending', 76, 23, '2020-04-14', ''),
('PT1001', 'Mercy', 'Lucky', 'Female', '0723091038', 'mercy@email.com', 'Married', 'John Thui', 'Nyandarua', 'Kinangop', 'Waterway Park Estate', 'Unknown', 'Pending', 65, 34, '2020-04-14', 'Prolonged epilepsy and high blood pressure'),
('PT1002', 'Vincent', 'Kwoba', 'Male', '0712763498', 'vincent@email.com', 'Divorced', 'Elizabeth Potoril', 'Trans-Nzoia', 'Kiminini ', 'Soi village', 'AB+', 'Pending', 78, 56, '2020-04-14', 'Epileptic,high blood pressure,allergic to vanila scennt and very sensitive of loud noise'),
('PT1003', 'Emily', 'Anita', 'Female', '0789892343', 'anita@gmail.org', 'Married', 'Daina Wawery', 'Embu', 'Mbeere South', 'Shallom Estate', 'B', 'Pending', 98, 45, '2020-04-14', ''),
('PT1004', 'Henry', 'Wanyama', 'Male', '0756785423', 'henry@gmail.com', 'Single', 'Julia Murray', 'Bungoma', 'Kanduyi', 'Kero Village', 'AB', 'Pending', 89, 45, '2020-04-14', ''),
('PT1005', 'Rebecca', 'Moraa', 'Female', '0723456578', 'moraa@gmail.com', 'Single', 'Abraham Mrume', 'Tharaka-Nithi', 'Chuka', 'Muungoni village', 'Unknown', 'Pending', 55, 14, '2020-04-14', 'Epileptic,high blood '),
('PT1006', 'Adam', 'Wainana', 'Male', '0712344556', 'adamwainana@gmail.com', 'Married', 'Jessica Wanja', 'Kisii', 'Bonchari', 'Boncah village', 'B', 'Pending', 78, 45, '2020-04-14', ''),
('PT1007', 'Patricia', 'Mwende', 'Female', '0767234545', 'pat@hotmail.com', 'Divorced', 'Morgan Nadia', 'Kakamega', 'Malava', 'Malannge village', 'O+', 'Pending', 178, 67, '2020-04-14', 'Obesity'),
('PT1008', 'Mark', 'Well', 'Male', '0712345678', 'well@oulook.com', 'Married', 'Paul obama', 'Kisumu', 'Nyakach', 'Uloge usonga estate', 'Unknown', 'Pending', 112, 56, '2020-04-14', ''),
('PT1009', 'Sandra', 'Sandy', 'Female', '0786571234', 'sandy@gmail.com', 'Single', 'Rosa Awinja', 'Nairobi', 'Mathare', 'Matahre', 'O-', 'Pending', 78, 34, '2020-04-14', ''),
('PT1010', 'Paul', 'Walker', 'Male', '0712345634', 'paul@email.com', 'Single', 'Were Wanja', 'West Pokot', 'Kapenguria', 'Kapsara Village', 'B', 'Pending', 78, 25, '2020-04-15', ''),
('PT1011', 'Maria', 'Lutherford', 'Female', '0712232323', 'mar@gmail.com', 'Single', 'Man Wetu', 'Kirinyaga', 'Mwea', 'Rice Scheme estate', 'AB', 'Pending', 88, 26, '2020-04-15', ''),
('PT1012', 'cvvvvvv', 'cvvvvvvv', 'Male', '3434343434', 'dfdfdg@fdf.dfd', 'Married', 'dffffffff', 'Elgeyo-Marakwet', 'Marakwet East', 'dffffff', 'AB', 'Pending', 34, 34, '2020-05-22', ''),
('PT1013', 'John', 'Doe', 'Male', '0789232323', 'john@email.com', 'Single', 'Paul ken', 'Embu', 'Manyatta', 'municpal town hall', 'AB+', 'Pending', 75, 454, '2020-05-22', ''),
('PT1014', 'mary', 'mary', 'Female', '5656565656', 'fgfgfgf@dfdf.dfdf.fgf', 'Single', 'dff', 'Embu', 'Manyatta', 'dfffffff', 'O', 'Pending', 66, 56, '2020-05-22', ''),
('PT1015', 'dfff', 'dffff', 'Female', '4545454545', 'fggggggg@fgg.fggg', 'Single', 'dssssss', 'Embu', 'Manyatta', 'sddddddd', 'O', 'Pending', 43, 45, '2020-05-22', '');

-- --------------------------------------------------------

--
-- Table structure for table `pharm_billing`
--

CREATE TABLE `pharm_billing` (
  `Patid` varchar(10) NOT NULL,
  `FN` varchar(50) NOT NULL,
  `LN` varchar(50) NOT NULL,
  `Gender` varchar(12) NOT NULL,
  `Drugname` varchar(50) NOT NULL,
  `Drugid` varchar(7) NOT NULL,
  `Unit_msr` varchar(6) NOT NULL,
  `Category` varchar(10) NOT NULL,
  `Quantity` int(3) NOT NULL,
  `Expdate` varchar(12) NOT NULL,
  `Unit_prc` int(12) NOT NULL,
  `Prc` int(12) NOT NULL,
  `Dosage` varchar(12) NOT NULL,
  `Pay` varchar(13) NOT NULL,
  `Rcp_no` varchar(12) DEFAULT NULL,
  `Paid` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pharm_billing`
--

INSERT INTO `pharm_billing` (`Patid`, `FN`, `LN`, `Gender`, `Drugname`, `Drugid`, `Unit_msr`, `Category`, `Quantity`, `Expdate`, `Unit_prc`, `Prc`, `Dosage`, `Pay`, `Rcp_no`, `Paid`) VALUES
('PT1000', 'Mark', 'Wayne', 'Male', 'Acetazolamide 125mg', 'Acet12', 'mg', 'Tablets', 12, '2022-03-27', 400, 4800, '1x3', 'Cash', '', 'Yes'),
('PT1001', 'Mercy', 'Lucky', 'Female', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', 12, '2025-03-15', 787, 9444, '1 Injection', 'Cash', '', 'Yes'),
('PT1002', 'Vincent', 'Kwoba', 'Male', 'Anticoagulant Citrate 10ml', 'Anti33', 'ml', 'Liquid', 12, '2024-03-22', 500, 6000, 'Apply 3/day', 'Cheque', 'GFqA9989s', 'Yes'),
('PT1003', 'Emily', 'Anita', 'Female', 'Amoxycilin 500mg', 'Amox97', 'mg', 'Capsules', 32, '2023-03-22', 300, 9600, '1x3', 'Debit Card', '', 'Yes'),
('PT1004', 'Henry', 'Wanyama', 'Male', 'Antisnake Venom 10ml', 'Anti44', 'ml', 'Liquid', 2, '2022-03-25', 1000, 2000, '1 Injection', 'Credit Card', '', 'Yes'),
('PT1005', 'Rebecca', 'Moraa', 'Female', 'Betamethasone 2mg', 'Beta19', 'mg', 'Tablets', 12, '2022-03-25', 500, 6000, '1x1', 'Cash', '', 'Yes'),
('PT1006', 'Adam', 'Wainana', 'Male', 'Biphasic Insulin 100 ml', 'Biph11', 'ml', 'Syrup', 12, '2022-03-25', 700, 6000, '1 Spn x3', 'Cash', '', 'Yes'),
('PT1007', 'Patricia', 'Mwende', 'Female', 'Castor Oil  10ml', 'Cast10', 'ml', 'Gel', 4, '2023-03-17', 700, 2000, 'Apply 3/day', 'Cash', '', 'Yes'),
('PT1008', 'Mark', 'Well', 'Male', 'Cetirizine  Solution 1mg/ml', 'Ceti23', 'mg/ml', 'Injection', 11, '2022-03-25', 767, 8437, '1 Injection', 'Debit Card', '', 'Yes'),
('PT1009', 'Sandra', 'Sandy', 'Female', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', 12, '2025-03-15', 787, 8400, '1 Injection', 'Cheque', 'JKh8887U', 'Yes'),
('PT1010', 'Paul', 'Walker', 'Male', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', 123, '2025-03-15', 787, 96801, '1 Spn x3', 'Cash', '', 'Yes'),
('PT1011', 'Maria', 'Lutherford', 'Female', 'Antisnake Venom 10ml', 'Anti44', 'ml', 'Liquid', 50, '2022-03-25', 1000, 50000, '1 Injection', 'Cash', '', 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `presription`
--

CREATE TABLE `presription` (
  `Patid` varchar(10) NOT NULL,
  `FN` varchar(50) NOT NULL,
  `LN` varchar(50) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Drugname` varchar(50) NOT NULL,
  `Drugid` varchar(8) NOT NULL,
  `Unit_msr` varchar(6) NOT NULL,
  `Category` varchar(10) NOT NULL,
  `Quantity` int(12) NOT NULL,
  `Expdate` varchar(15) NOT NULL,
  `Unit_prc` int(12) NOT NULL,
  `Prc` int(12) NOT NULL,
  `Dosage` varchar(11) NOT NULL,
  `Storage` varchar(50) NOT NULL,
  `Precautions` varchar(50) DEFAULT NULL,
  `Sideeffects` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `presription`
--

INSERT INTO `presription` (`Patid`, `FN`, `LN`, `Gender`, `Drugname`, `Drugid`, `Unit_msr`, `Category`, `Quantity`, `Expdate`, `Unit_prc`, `Prc`, `Dosage`, `Storage`, `Precautions`, `Sideeffects`) VALUES
('PT1000', 'Mark', 'Wayne', 'Male', 'Acetazolamide 125mg', 'Acet12', 'mg', 'Tablets', 12, '2022-03-27', 400, 4800, '1x3', 'Cool dry place,away from the sunlight and children', ' Do Not Work After Taking The Drug', 'Dizziness,Headache,Vomiting'),
('PT1001', 'Mercy', 'Lucky', 'Female', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', 12, '2025-03-15', 787, 9444, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting\n'),
('PT1002', 'Vincent', 'Kwoba', 'Male', 'Anticoagulant Citrate 10ml', 'Anti33', 'ml', 'Liquid', 12, '2024-03-22', 500, 6000, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting'),
('PT1003', 'Emily', 'Anita', 'Female', 'Amoxycilin 500mg', 'Amox97', 'mg', 'Capsules', 32, '2023-03-22', 300, 9600, '1x3', 'Cool dry place,away from the sunlight and children', 'Don\'t Take Alcohol', 'Dizziness,Headache,Vomiting'),
('PT1004', 'Henry', 'Wanyama', 'Male', 'Antisnake Venom 10ml', 'Anti44', 'ml', 'Liquid', 2, '2022-03-25', 1000, 2000, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting'),
('PT1005', 'Rebecca', 'Moraa', 'Female', 'Betamethasone 2mg', 'Beta19', 'mg', 'Tablets', 12, '2022-03-25', 500, 6000, '1x1', 'Cool dry place,away from the sunlight and children', 'Do Not Work After Taking The Drug', 'Dizziness,Headache,Vomiting\n'),
('PT1006', 'Adam', 'Wainana', 'Male', 'Biphasic Insulin 100 ml', 'Biph11', 'ml', 'Syrup', 12, '2022-03-25', 700, 6000, '1 Spn x3', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting'),
('PT1007', 'Patricia', 'Mwende', 'Female', 'Castor Oil  10ml', 'Cast10', 'ml', 'Gel', 4, '2023-03-17', 700, 2000, 'Apply 3/day', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Skin Rash\n'),
('PT1008', 'Mark', 'Well', 'Male', 'Cetirizine  Solution 1mg/ml', 'Ceti23', 'mg/ml', 'Injection', 11, '2022-03-25', 767, 8437, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Don\'t Take Alcohol', 'Dizziness,Headache,Vomiting'),
('PT1009', 'Sandra', 'Sandy', 'Female', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', 12, '2025-03-15', 787, 8400, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting\n'),
('PT1010', 'Paul', 'Walker', 'Male', 'Chloroquine 150ml', 'Chlo98', 'mg', 'Liquid', 123, '2025-03-15', 787, 96801, '1 Spn x3', 'Cool dry place,away from the sunlight and children', 'Take After Meals', 'Dizziness,Headache,Vomiting\n'),
('PT1011', 'Maria', 'Lutherford', 'Female', 'Antisnake Venom 10ml', 'Anti44', 'ml', 'Liquid', 50, '2022-03-25', 1000, 50000, '1 Injection', 'Cool dry place,away from the sunlight and children', 'Do Not Swallow/Ingest', 'Dizziness,Headache,Vomiting');

-- --------------------------------------------------------

--
-- Table structure for table `salaries`
--

CREATE TABLE `salaries` (
  `Dept` varchar(17) NOT NULL,
  `Bsc_sal` int(6) NOT NULL,
  `Ovr_tme` int(6) NOT NULL,
  `Medical` int(6) NOT NULL,
  `Bonus` int(6) NOT NULL,
  `Other` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `salaries`
--

INSERT INTO `salaries` (`Dept`, `Bsc_sal`, `Ovr_tme`, `Medical`, `Bonus`, `Other`) VALUES
('Catering', 30000, 150, 1000, 1000, 1000),
('Doctor', 63000, 150, 1000, 1000, 1000),
('Finance', 55000, 150, 1000, 1000, 1000),
('House Keeping', 25000, 150, 1000, 1000, 1000),
('HR', 50000, 150, 1000, 1000, 1000),
('Laboratory', 58000, 150, 1000, 1000, 1000),
('Nurse', 60000, 150, 1000, 1000, 1000),
('Pharmacy', 54000, 150, 1000, 1000, 1000),
('Reception', 35000, 150, 1000, 1000, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `services`
--

CREATE TABLE `services` (
  `Service` varchar(15) NOT NULL,
  `Charges` int(12) NOT NULL,
  `Total` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `services`
--

INSERT INTO `services` (`Service`, `Charges`, `Total`) VALUES
('General', 2000, 2500),
('ICU', 3000, 3500),
('Maternity', 2400, 2900),
('Pediatric', 1800, 2000),
('Prostratic', 2500, 2800),
('Surgery', 3000, 3200),
('Gynacological', 3000, 3500);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Userid` varchar(10) NOT NULL,
  `Empid` varchar(10) NOT NULL,
  `FN` varchar(30) NOT NULL,
  `LN` varchar(30) NOT NULL,
  `Account` varchar(12) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `ConfirmPass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Userid`, `Empid`, `FN`, `LN`, `Account`, `Username`, `Password`, `ConfirmPass`) VALUES
('US1000', 'EP1003', 'John', 'Otienno', 'Doctor', 'doctor1', '60abd7a777443dfe1d4b25941b8704c6', '60abd7a777443dfe1d4b25941b8704c6'),
('US1001', 'EP1004', 'Caren', 'Martha', 'Doctor', 'doctor2', '60abd7a777443dfe1d4b25941b8704c6', '60abd7a777443dfe1d4b25941b8704c6'),
('US1003', 'EP1007', 'Faith', 'Nkatha', 'Finance', 'fin1', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1004', 'EP1008', 'Hillary', 'Mwendia', 'Finance', 'qwerty', '3fc0a7acf087f549ac2b266baf94b8b1', '3fc0a7acf087f549ac2b266baf94b8b1'),
('US1005', 'EP1013', 'Rose', 'Wamaitha', 'HR', 'hr1', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1006', 'EP1014', 'Kevin', 'Omondi', 'HR', 'hr2', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1007', 'EP1016', 'Nancy', 'Ndaqwaj', 'Laboratory', 'lab1', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1008', 'EP1017', 'Biram', 'Wanyonyi', 'Laboratory', 'lab2', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1009', 'EP1019', 'Joan', 'Wafula', 'Nurse', 'nur1', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1010', 'EP1020', 'Paul', 'Kennedy', 'Nurse', 'nur2', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1011', 'EP1022', 'Matheka', 'Mbithi', 'Pharmacy', 'pharm1', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1012', 'EP1023', 'Cecil', 'Kwoba', 'Pharmacy', 'pharm2', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1013', 'EP1025', 'Pauline', 'Atieno', 'Reception', 'recep1', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b'),
('US1014', 'EP1026', 'Valarie', 'Cherono', 'Reception', 'recep2', '25f9e794323b453885f5181f1b624d0b', '25f9e794323b453885f5181f1b624d0b');

-- --------------------------------------------------------

--
-- Table structure for table `wardcharges`
--

CREATE TABLE `wardcharges` (
  `Wardtype` varchar(10) NOT NULL,
  `Wrd_Chrg` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wardcharges`
--

INSERT INTO `wardcharges` (`Wardtype`, `Wrd_Chrg`) VALUES
('ICU', 2000),
('Male', 1000),
('Female', 1000),
('Gynecology', 1500),
('Maternity', 1700);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`userid`);

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`Appointmentid`);

--
-- Indexes for table `drug_inventory`
--
ALTER TABLE `drug_inventory`
  ADD PRIMARY KEY (`Drugid`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`Empid`);

--
-- Indexes for table `emp_payment`
--
ALTER TABLE `emp_payment`
  ADD PRIMARY KEY (`Empid`),
  ADD UNIQUE KEY `Payid` (`Payid`);

--
-- Indexes for table `hms_doctors`
--
ALTER TABLE `hms_doctors`
  ADD PRIMARY KEY (`Empid`);

--
-- Indexes for table `inpat_billing`
--
ALTER TABLE `inpat_billing`
  ADD PRIMARY KEY (`Patid`);

--
-- Indexes for table `issuedrugs`
--
ALTER TABLE `issuedrugs`
  ADD PRIMARY KEY (`Patientid`);

--
-- Indexes for table `lab_results`
--
ALTER TABLE `lab_results`
  ADD PRIMARY KEY (`Patientid`);

--
-- Indexes for table `outpat_billing`
--
ALTER TABLE `outpat_billing`
  ADD PRIMARY KEY (`Patid`);

--
-- Indexes for table `pat_assignward`
--
ALTER TABLE `pat_assignward`
  ADD PRIMARY KEY (`Patid`),
  ADD UNIQUE KEY `Bedno` (`Bedno`);

--
-- Indexes for table `pat_doctor`
--
ALTER TABLE `pat_doctor`
  ADD PRIMARY KEY (`Patid`);

--
-- Indexes for table `pat_nurse`
--
ALTER TABLE `pat_nurse`
  ADD PRIMARY KEY (`Patientid`);

--
-- Indexes for table `pat_reg`
--
ALTER TABLE `pat_reg`
  ADD PRIMARY KEY (`Patientid`);

--
-- Indexes for table `pharm_billing`
--
ALTER TABLE `pharm_billing`
  ADD PRIMARY KEY (`Patid`);

--
-- Indexes for table `presription`
--
ALTER TABLE `presription`
  ADD PRIMARY KEY (`Patid`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`Userid`),
  ADD UNIQUE KEY `Empid` (`Empid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
