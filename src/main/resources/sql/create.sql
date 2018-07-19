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
  `filepath` VARCHAR(256) NOT NULL ,
  `filename` VARCHAR(128) NOT NULL ,
  `filedesc` VARCHAR(256) NOT NULL ,
  `filetag` VARCHAR(64) NOT NULL ,
  `filecheck` INT(1) NOT NULL DEFAULT 0,
  `filestate` INT(1) NOT NULL DEFAULT 0,
  `downloadnum` INT(10) NOT NULL DEFAULT 0,
  `userid` INT(10) REFERENCES suser(userid) ON DELETE CASCADE ON UPDATE CASCADE ,
  `filedate` DATE NOT NULL ,
  `filetype` VARCHAR(64) NOT NULL DEFAULT "未知类型"
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;


-- -------------------------------
-- Table structure for groups
-- -------------------------------
DROP TABLE IF EXISTS `sgroup`;
CREATE TABLE `sgroup`(
  `groupid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `grouppwd` VARCHAR(20) NOT NULL ,
  `groupname` VARCHAR(20) NOT NULL ,
  `groupdesc` VARCHAR(50) NOT NULL
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------
-- Table structure for group users
-- -------------------------------
DROP TABLE IF EXISTS `sguser`;
CREATE TABLE `sguser`(
  `guid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `groupid` INT(10) REFERENCES sgroup(groupid) ON DELETE CASCADE ON UPDATE CASCADE ,
  `userid` INT(10) REFERENCES suser(userid) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------
-- Table structure for group files
-- -------------------------------
DROP TABLE IF EXISTS `sgfile`;
CREATE TABLE `sgfile`(
  `sgid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `groupid` INT(10) REFERENCES sgroup(groupid) ON DELETE CASCADE ON UPDATE CASCADE ,
  `fileid` INT(10) REFERENCES sfile(fileid) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------------
-- Table structure for user download files
-- -------------------------------------
DROP TABLE IF EXISTS `sdfile`;
CREATE TABLE `sdfile`(
  `sdid` INT(10) PRIMARY KEY AUTO_INCREMENT,
  `fileid` INT(10) REFERENCES sfile(fileid) ON DELETE CASCADE ON UPDATE CASCADE ,
  `userid` INT(10) REFERENCES suser(userid) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- -------------------------------
-- insert administrators
-- -------------------------------
INSERT INTO sadmin(adminname, adminpwd) VALUES("admin", "admin");

-- -------------------------------
-- insert users
-- -------------------------------
INSERT INTO suser(username, userpwd, usersex, grade) VALUES("user1", "1", "男", 1000);
INSERT INTO suser(username, userpwd, usersex, grade) VALUES("user2", "1", "男", 1000);
INSERT INTO suser(username, userpwd, usersex, grade) VALUES("user3", "1", "男", 1000);


-- -------------------------------
-- insert groups
-- -------------------------------
INSERT INTO sgroup(grouppwd, groupname, groupdesc) VALUES("1", "group1", "group1 description");
INSERT INTO sgroup(grouppwd, groupname, groupdesc) VALUES("1", "group2", "group2 description");

-- -------------------------------
-- insert group users
-- -------------------------------
INSERT INTO sguser(groupid, userid) VALUES(1, 1);
INSERT INTO sguser(groupid, userid) VALUES(1, 2);
INSERT INTO sguser(groupid, userid) VALUES(2, 1);

-- -------------------------------
-- insert files
-- -------------------------------
INSERT INTO sfile(filepath, filename, filedesc, filetag, userid, filedate) VALUES("none", "file1", "file1 description", "none", 1, "2017-8-19");
INSERT INTO sfile(filepath, filename, filedesc, filetag, userid, filedate) VALUES("none", "file2", "file2 description", "none", 1, "2017-8-19");
INSERT INTO sfile(filepath, filename, filedesc, filetag, userid, filedate) VALUES("none", "file3", "file1 description", "none", 2, "2017-8-19");

-- -------------------------------
-- insert group files
-- -------------------------------

SET FOREIGN_KEY_CHECKS = 1;