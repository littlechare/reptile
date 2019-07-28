/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : reptile

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-07-29 00:08:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` varchar(64) NOT NULL,
  `name` varchar(50) NOT NULL,
  `url` varchar(50) NOT NULL,
  `author` varchar(10) DEFAULT NULL,
  `publisher_time` datetime DEFAULT NULL COMMENT '发布时间',
  `word_num` int(11) DEFAULT NULL COMMENT '字数',
  `type` varchar(10) DEFAULT NULL,
  `status` varchar(20) DEFAULT '' COMMENT '书籍状态',
  `intro` varchar(200) DEFAULT NULL,
  `last_update_time` varchar(50) DEFAULT NULL COMMENT '最后更新时间',
  `pra_num` int(11) DEFAULT NULL COMMENT '章节数',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `data_status` varchar(1) DEFAULT '1',
  `remark` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('7222C09202FD439BBCE9AE5F2B143160', '渡尸之才', 'http://www.xinshubao.net/7/7828/', '作者：陈十三', null, null, null, '写作状态：连载中', '《渡尸之才》免费阅读全文，《渡尸之才》叶子韩雪孙仲谋是小说主角，小说《渡尸之才》全文简介：第1章 二十三年前的悬案叶子父亲离奇死亡。 他的人皮， 被挂在村口的那棵老槐树上。 二十三年后， 当年被过继出去的大哥孙仲谋回来。 竖旗为：捞尸人。', '最新章节：第690章 最后一战（下）', null, null, null, '1', null);

-- ----------------------------
-- Table structure for bookmark
-- ----------------------------
DROP TABLE IF EXISTS `bookmark`;
CREATE TABLE `bookmark` (
  `id` varchar(64) NOT NULL,
  `book_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bookmark
-- ----------------------------

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` varchar(50) NOT NULL,
  `title` varchar(50) DEFAULT NULL COMMENT '章节标题',
  `num` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL COMMENT '链接',
  `pre` varchar(50) DEFAULT NULL COMMENT '上一章id',
  `next` varchar(50) DEFAULT NULL COMMENT '下一章id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter
-- ----------------------------

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `font_size` int(11) DEFAULT NULL,
  `style` int(11) DEFAULT NULL,
  `process` varchar(255) DEFAULT NULL,
  `chapter` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of setting
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `pwd` varchar(64) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `log_device` varchar(64) DEFAULT NULL,
  `data_status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
