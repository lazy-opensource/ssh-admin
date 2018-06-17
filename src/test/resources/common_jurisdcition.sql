/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : common_jurisdcition

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-10-08 17:17:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOG_ACTION` varchar(500) DEFAULT NULL,
  `LOG_PARAMETERS` varchar(500) DEFAULT NULL,
  `LOG_RES` varchar(500) DEFAULT NULL,
  `LOG_ACCOUNT` varchar(500) DEFAULT NULL,
  `LOG_IP` varchar(200) DEFAULT NULL,
  `LOG_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_NAME` varchar(50) DEFAULT NULL,
  `PARENT_NODE` int(11) DEFAULT NULL,
  `MENU_ACTION` varchar(200) DEFAULT NULL,
  `MENU_SORTORDER` int(11) DEFAULT NULL,
  `MENU_ICON` varchar(200) DEFAULT NULL,
  `MENU_REMARK` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`),
  KEY `FK_mkc18rrchqhnm3y13xdc2pn3q` (`PARENT_NODE`),
  CONSTRAINT `FK_mkc18rrchqhnm3y13xdc2pn3q` FOREIGN KEY (`PARENT_NODE`) REFERENCES `sys_menu` (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', 'SSH-权限框架', null, null, '0', 'icon-home2', '根菜单');
INSERT INTO `sys_menu` VALUES ('2', '操作管理', '1', 'operation/toList', '0', 'icon-rights', '对操作进行增、删、改');
INSERT INTO `sys_menu` VALUES ('3', '角色管理', '1', 'role/toList', '1', 'icon-grade', '对角色进行增、删、改');
INSERT INTO `sys_menu` VALUES ('4', '用户管理', '1', 'user/toList', '2', 'icon-user', '对用户进行增、删、改');
INSERT INTO `sys_menu` VALUES ('5', '菜单管理', '1', 'menu/toList', '3', 'icon-menu', '对菜单进行增、删、改');

-- ----------------------------
-- Table structure for sys_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation`;
CREATE TABLE `sys_operation` (
  `OPERATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OPERATION_NAME` varchar(50) DEFAULT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  `OPERATION_ACTION` varchar(200) DEFAULT NULL,
  `OPERATION_REMARK` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`OPERATION_ID`),
  KEY `FK_s9wsrngp0vau0qhbcslhppfwn` (`MENU_ID`),
  CONSTRAINT `FK_s9wsrngp0vau0qhbcslhppfwn` FOREIGN KEY (`MENU_ID`) REFERENCES `sys_menu` (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_operation
-- ----------------------------
INSERT INTO `sys_operation` VALUES ('1', '新增操作', '2', 'operationAdd()', null);
INSERT INTO `sys_operation` VALUES ('2', '修改操作', '2', 'operationEdit()', null);
INSERT INTO `sys_operation` VALUES ('3', '删除操作', '2', 'operationDele()', null);
INSERT INTO `sys_operation` VALUES ('4', '新增角色', '3', 'roleAdd()', null);
INSERT INTO `sys_operation` VALUES ('5', '修改角色', '3', 'roleEdit()', null);
INSERT INTO `sys_operation` VALUES ('6', '删除角色', '3', 'roleDele()', null);
INSERT INTO `sys_operation` VALUES ('7', '新增用户', '4', 'userAdd()', null);
INSERT INTO `sys_operation` VALUES ('8', '修改用户', '4', 'userEdit()', null);
INSERT INTO `sys_operation` VALUES ('9', '删除用户', '4', 'userDele()', null);
INSERT INTO `sys_operation` VALUES ('10', '新增菜单', '5', 'menuAdd()', null);
INSERT INTO `sys_operation` VALUES ('11', '修改菜单', '5', 'menuEdit()', null);
INSERT INTO `sys_operation` VALUES ('12', '删除菜单', '5', 'menuDele()', null);
INSERT INTO `sys_operation` VALUES ('13', '导出操作excl', '2', 'exportOperationExcl()', '导出excl报表');
INSERT INTO `sys_operation` VALUES ('14', '导出操作excl', '4', 'exportUserExcl()', '导出excl报表');
INSERT INTO `sys_operation` VALUES ('15', '导出操作excl', '3', 'exportRoleExcl()', '导出excl报表');
INSERT INTO `sys_operation` VALUES ('16', '导出操作excl', '5', 'exportMenuExcl()', '导出excl报表');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(50) DEFAULT NULL,
  `ROLE_STATUS` varchar(50) DEFAULT '0',
  `ROLE_REMARK` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '0', '拥有所有的权限');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `MENU_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MENU_ID`),
  KEY `FK_migyafn30107ghjk7iiy3ubp8` (`MENU_ID`),
  CONSTRAINT `FK_iqxqhghdj0rvaiw37yyb2m1ct` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `FK_migyafn30107ghjk7iiy3ubp8` FOREIGN KEY (`MENU_ID`) REFERENCES `sys_menu` (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('3', '1');
INSERT INTO `sys_role_menu` VALUES ('4', '1');
INSERT INTO `sys_role_menu` VALUES ('5', '1');

-- ----------------------------
-- Table structure for sys_role_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_operation`;
CREATE TABLE `sys_role_operation` (
  `OPERATION_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`OPERATION_ID`),
  KEY `FK_k15ntn8id8krpgkx4ovfha315` (`OPERATION_ID`),
  CONSTRAINT `FK_k15ntn8id8krpgkx4ovfha315` FOREIGN KEY (`OPERATION_ID`) REFERENCES `sys_operation` (`OPERATION_ID`),
  CONSTRAINT `FK_rq67hfk9hwob65qm5a7bnta3h` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_operation
-- ----------------------------
INSERT INTO `sys_role_operation` VALUES ('1', '1');
INSERT INTO `sys_role_operation` VALUES ('2', '1');
INSERT INTO `sys_role_operation` VALUES ('3', '1');
INSERT INTO `sys_role_operation` VALUES ('4', '1');
INSERT INTO `sys_role_operation` VALUES ('5', '1');
INSERT INTO `sys_role_operation` VALUES ('6', '1');
INSERT INTO `sys_role_operation` VALUES ('7', '1');
INSERT INTO `sys_role_operation` VALUES ('8', '1');
INSERT INTO `sys_role_operation` VALUES ('9', '1');
INSERT INTO `sys_role_operation` VALUES ('10', '1');
INSERT INTO `sys_role_operation` VALUES ('11', '1');
INSERT INTO `sys_role_operation` VALUES ('12', '1');
INSERT INTO `sys_role_operation` VALUES ('13', '1');
INSERT INTO `sys_role_operation` VALUES ('14', '1');
INSERT INTO `sys_role_operation` VALUES ('15', '1');
INSERT INTO `sys_role_operation` VALUES ('16', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(50) DEFAULT NULL,
  `USER_REALNAME` varchar(50) DEFAULT NULL,
  `USER_PASSWORD` varchar(100) DEFAULT NULL,
  `USER_STATUS` int(11) DEFAULT '0',
  `USER_REMARK` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '超级用户', '123', '0', null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `ROLE_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_8jdwkd6kf4h7g82dkhdyfkq19` (`ROLE_ID`),
  CONSTRAINT `FK_8jdwkd6kf4h7g82dkhdyfkq19` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `FK_n20d1r0ktmljuefk8g7nqixrp` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
