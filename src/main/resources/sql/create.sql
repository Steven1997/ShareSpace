SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- database for sharespace
-- ----------------------------
DROP DATABASE IF EXISTS `sharespace`;
CREATE DATABASE `sharespace` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `sharespace`;
-- ----------------------------
-- table for administrators
-- ----------------------------
DROP TABLE IF EXISTS `sadmin`;
CREATE TABLE `sadmin` (
  `adminid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `adminname` VARCHAR(20) NOT NULL,
  `adminpwd` VARCHAR(20) NOT NULL
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------
-- Table structure for users
-- -------------------------------
DROP TABLE IF EXISTS `suser`;
CREATE TABLE `suser` (
  `userid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL UNIQUE,
  `userpwd` VARCHAR(20) NOT NULL ,
  `usersex` VARCHAR(5) NOT NULL DEFAULT "男",
  `grade` INT(10) NOT NULL DEFAULT 0,
  `userimage` VARCHAR(50)
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------
-- Table structure for files
-- -------------------------------
DROP TABLE IF EXISTS `sfile`;
CREATE TABLE `sfile`(
  `fileid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `filepath` VARCHAR(50) NOT NULL ,
  `filename` VARCHAR(20) NOT NULL ,
  `filedesc` VARCHAR(100) NOT NULL ,
  `filetag` VARCHAR(50) NOT NULL ,
  `filecheck` INT(1) NOT NULL DEFAULT 0,
  `filestate` INT(1) NOT NULL DEFAULT 0,
  `downloadnum` INT(10) NOT NULL DEFAULT 0,
  `userid` INT(10) REFERENCES suser(userid),
  `filedate` DATE NOT NULL ,
  `filetype` VARCHAR(20) NOT NULL DEFAULT "未知类型"
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------
-- Table structure for groups
-- -------------------------------
DROP TABLE IF EXISTS `sgroup`;
CREATE TABLE `sgroup`(
  `groupid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `grouppwd` VARCHAR(20) NOT NULL ,
  `groupname` VARCHAR(20) NOT NULL ,
  `groupdesc` VARCHAR(50) NOT NULL ,
  `userid` INT(10) REFERENCES suser(userid)
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------
-- Table structure for group users
-- -------------------------------
DROP TABLE IF EXISTS `sguser`;
CREATE TABLE `sguser`(
  `guid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `groupid` INT(10) REFERENCES sgroup(groupid),
  `userid` INT(10) REFERENCES suser(userid),
  `username` VARCHAR(20) NOT NULL ,
  `groupname` VARCHAR(20) NOT NULL
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------
-- Table structure for group files
-- -------------------------------
DROP TABLE IF EXISTS `sgfile`;
CREATE TABLE `sgfile`(
  `sgid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `groupid` INT(10) REFERENCES sgroup(groupid),
  `fileid` INT(10) REFERENCES sfile(fileid),
  `groupname` VARCHAR(20) NOT NULL ,
  `filename` VARCHAR(20) NOT NULL
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------------
-- Table structure for user upload files
-- -------------------------------------
DROP TABLE IF EXISTS `sdfile`;
CREATE TABLE `sdfile`(
  `sdid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `fileid` INT(10) REFERENCES sfile(fileid),
  `userid` INT(10) REFERENCES suser(userid),
  `filename` VARCHAR(20),
  `usernmae` VARCHAR(20)
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;