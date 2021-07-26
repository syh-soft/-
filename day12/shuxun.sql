/*
Navicat MySQL Data Transfer

Source Server         : 考生系统2
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : shuxun

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2021-07-09 12:49:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admission_info
-- ----------------------------
DROP TABLE IF EXISTS `admission_info`;
CREATE TABLE `admission_info` (
  `Admission_number` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Time` datetime(6) NOT NULL,
  `Area` varchar(255) NOT NULL,
  PRIMARY KEY (`Admission_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admission_info
-- ----------------------------

-- ----------------------------
-- Table structure for candidate_notice
-- ----------------------------
DROP TABLE IF EXISTS `candidate_notice`;
CREATE TABLE `candidate_notice` (
  `Name` varchar(255) NOT NULL,
  `Admission_number` varchar(255) NOT NULL,
  `Grade` varchar(10) NOT NULL,
  PRIMARY KEY (`Admission_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of candidate_notice
-- ----------------------------

-- ----------------------------
-- Table structure for examinee
-- ----------------------------
DROP TABLE IF EXISTS `examinee`;
CREATE TABLE `examinee` (
  `Name` varchar(255) NOT NULL,
  `Student_number` varchar(255) NOT NULL,
  `ID` varchar(255) NOT NULL,
  `sex` int(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of examinee
-- ----------------------------

-- ----------------------------
-- Table structure for registration
-- ----------------------------
DROP TABLE IF EXISTS `registration`;
CREATE TABLE `registration` (
  `Name` varchar(255) NOT NULL,
  `Registration_number` varchar(255) NOT NULL,
  `ID` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of registration
-- ----------------------------

-- ----------------------------
-- Table structure for school_report
-- ----------------------------
DROP TABLE IF EXISTS `school_report`;
CREATE TABLE `school_report` (
  `Name` varchar(255) NOT NULL,
  `Admission_number` varchar(255) NOT NULL,
  `Grade` varchar(10) NOT NULL,
  PRIMARY KEY (`Admission_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of school_report
-- ----------------------------

-- ----------------------------
-- Table structure for statistical_analysis_table
-- ----------------------------
DROP TABLE IF EXISTS `statistical_analysis_table`;
CREATE TABLE `statistical_analysis_table` (
  `Area` varchar(255) NOT NULL,
  `Admission_number` varchar(255) NOT NULL,
  `Grade` varchar(10) NOT NULL,
  PRIMARY KEY (`Admission_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of statistical_analysis_table
-- ----------------------------
