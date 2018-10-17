/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : schooldeal

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2018-10-17 19:09:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_area`
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area` (
  `area_id` int(10) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(255) NOT NULL,
  `priority` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_area
-- ----------------------------
INSERT INTO `tb_area` VALUES ('1', '东校区', '20', '2018-05-03 13:57:45', '2018-05-21 13:57:48');
INSERT INTO `tb_area` VALUES ('2', '西校区', '19', '2018-05-01 19:36:13', '2018-05-03 19:36:16');
INSERT INTO `tb_area` VALUES ('3', '东门外街', '10', '2018-05-03 19:37:15', '2018-05-04 19:37:20');
INSERT INTO `tb_area` VALUES ('4', '西门外街', '11', '2018-05-01 19:37:50', '2018-05-02 19:37:55');

-- ----------------------------
-- Table structure for `tb_cart`
-- ----------------------------
DROP TABLE IF EXISTS `tb_cart`;
CREATE TABLE `tb_cart` (
  `cart_id` int(20) NOT NULL AUTO_INCREMENT,
  `person_info_id` int(20) NOT NULL,
  `product_id` int(20) NOT NULL,
  `product_count` int(10) NOT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `fk_person` (`person_info_id`),
  KEY `fk_product` (`product_id`),
  CONSTRAINT `fk_person` FOREIGN KEY (`person_info_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cart
-- ----------------------------
INSERT INTO `tb_cart` VALUES ('8', '1', '29', '9');
INSERT INTO `tb_cart` VALUES ('13', '1', '11', '2');
INSERT INTO `tb_cart` VALUES ('14', '1', '15', '1');

-- ----------------------------
-- Table structure for `tb_favorite_product`
-- ----------------------------
DROP TABLE IF EXISTS `tb_favorite_product`;
CREATE TABLE `tb_favorite_product` (
  `favorite_product_id` int(20) NOT NULL AUTO_INCREMENT,
  `product_id` int(20) NOT NULL,
  `personInfo_id` int(20) NOT NULL,
  PRIMARY KEY (`favorite_product_id`),
  KEY `fk_favorite_product` (`product_id`),
  KEY `fk_favorite_personInfo` (`personInfo_id`),
  CONSTRAINT `fk_favorite_personInfo` FOREIGN KEY (`personInfo_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_favorite_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_favorite_product
-- ----------------------------
INSERT INTO `tb_favorite_product` VALUES ('1', '13', '1');
INSERT INTO `tb_favorite_product` VALUES ('2', '16', '1');
INSERT INTO `tb_favorite_product` VALUES ('5', '22', '1');
INSERT INTO `tb_favorite_product` VALUES ('23', '18', '1');
INSERT INTO `tb_favorite_product` VALUES ('25', '27', '1');
INSERT INTO `tb_favorite_product` VALUES ('28', '29', '1');
INSERT INTO `tb_favorite_product` VALUES ('29', '15', '1');

-- ----------------------------
-- Table structure for `tb_favorite_shop`
-- ----------------------------
DROP TABLE IF EXISTS `tb_favorite_shop`;
CREATE TABLE `tb_favorite_shop` (
  `favorite_shop_id` int(20) NOT NULL AUTO_INCREMENT,
  `shop_id` int(20) NOT NULL,
  `personInfo_id` int(20) NOT NULL,
  PRIMARY KEY (`favorite_shop_id`),
  KEY `fk_favorite_shop` (`shop_id`),
  KEY `fk_favorite_personInfo_id` (`personInfo_id`),
  CONSTRAINT `fk_favorite_personInfo_id` FOREIGN KEY (`personInfo_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_favorite_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_favorite_shop
-- ----------------------------
INSERT INTO `tb_favorite_shop` VALUES ('6', '4', '1');

-- ----------------------------
-- Table structure for `tb_local_auth`
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth` (
  `local_auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`),
  KEY `fk_localauth_perfile` (`user_id`),
  CONSTRAINT `fk_localauth_perfile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_local_auth
-- ----------------------------
INSERT INTO `tb_local_auth` VALUES ('1', 'admin', 'admin', '2018-06-05 15:32:01', '2018-06-05 15:32:05', '1');
INSERT INTO `tb_local_auth` VALUES ('2', 'testuser', 'testuser', '2018-06-05 15:32:31', '2018-06-05 15:32:34', '2');
INSERT INTO `tb_local_auth` VALUES ('3', 'testuser2', 'testuser2', '2018-06-06 19:44:24', '2018-06-06 19:44:24', '1');
INSERT INTO `tb_local_auth` VALUES ('4', 'testuser2', 'testuser2', '2018-06-06 19:58:32', '2018-06-06 19:58:32', '4');
INSERT INTO `tb_local_auth` VALUES ('5', 'testuser3', 'testuser3', '2018-06-06 20:00:50', '2018-06-06 20:00:50', '5');
INSERT INTO `tb_local_auth` VALUES ('6', 'testuser4', 'admin', '2018-06-06 22:55:17', '2018-06-06 22:55:17', '6');

-- ----------------------------
-- Table structure for `tb_person_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `profile_img` varchar(255) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `gender` varchar(5) NOT NULL,
  `enable_status` int(10) DEFAULT NULL,
  `user_type` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_person_info
-- ----------------------------
INSERT INTO `tb_person_info` VALUES ('1', '管理员1号', '1', '1001@sd.com', '1', '1', '1', '2018-05-01 13:59:20', '2018-05-15 13:59:23');
INSERT INTO `tb_person_info` VALUES ('2', '测试用户2号', '1', '1002@sd.com', '1', '1', '2', '2018-05-22 21:47:15', '2018-05-23 21:47:19');
INSERT INTO `tb_person_info` VALUES ('4', '测试注册账号', null, '10003@test.com', '1', null, '2', '2018-06-06 19:58:20', '2018-06-06 19:58:20');
INSERT INTO `tb_person_info` VALUES ('5', '测试注册账号2', null, '1007@3kingdom.com', '0', null, '2', '2018-06-06 20:00:50', '2018-06-06 20:00:50');
INSERT INTO `tb_person_info` VALUES ('6', '测试注册校验', null, '1005@3kingdom.com', '1', '1', '2', '2018-06-06 22:55:17', '2018-06-06 22:55:17');

-- ----------------------------
-- Table structure for `tb_product`
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `product_category_id` int(11) DEFAULT NULL,
  `product_desc` varchar(255) DEFAULT NULL,
  `img_addr` varchar(255) DEFAULT NULL,
  `normal_price` int(11) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL,
  `enable_status` int(11) DEFAULT '1' COMMENT '0代表上架中,1代表下架中',
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `fk_shop` (`shop_id`),
  KEY `fk_product_category` (`product_category_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`),
  CONSTRAINT `fk_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES ('11', '热淇淋', '1', '绝对不冰', 'upload\\item\\product\\11\\8409620180609150056.jpg', '5', null, '2018-06-09 15:00:56', '2018-06-09 15:00:56', '0', '4');
INSERT INTO `tb_product` VALUES ('12', '蛋矮', '1', '蛋糕中的小四', 'upload\\item\\product\\12\\9290220180531224009.jpg', '1', null, '2018-05-31 22:40:09', '2018-05-31 22:40:09', '0', '4');
INSERT INTO `tb_product` VALUES ('13', '原谅色蛋糕', '1', '春天来了?', 'upload\\item\\product\\13\\4113120180531224044.jpg', '2', null, '2018-05-31 22:40:44', '2018-05-31 22:40:44', '1', '4');
INSERT INTO `tb_product` VALUES ('14', '饼干', '6', '下午茶配小饼干,咖啡配甜甜圈!', 'upload\\item\\product\\14\\8105620180531224148.jpg', '2', null, '2018-05-31 22:41:48', '2018-05-31 22:41:48', '0', '4');
INSERT INTO `tb_product` VALUES ('15', '酒桶杯', '27', '复古酒桶造型咖啡杯', 'upload\\item\\product\\15\\7987020180607204030.jpg', '5', null, '2018-06-07 20:40:30', '2018-06-07 20:40:30', '0', '4');
INSERT INTO `tb_product` VALUES ('16', '深色咖啡杯', '27', '深色带图案', 'upload\\item\\product\\16\\2649020180607204105.jpg', '5', null, '2018-06-07 20:41:05', '2018-06-07 20:41:05', '0', '4');
INSERT INTO `tb_product` VALUES ('17', '烫金咖啡杯', '27', '深色带图案', 'upload\\item\\product\\17\\1178820180607204129.jpg', '5', null, '2018-06-07 20:41:29', '2018-06-07 20:41:29', '0', '4');
INSERT INTO `tb_product` VALUES ('18', '浅草色茶杯', '27', '浅绿加深绿', 'upload\\item\\product\\18\\3805520180607204217.jpg', '5', null, '2018-06-07 20:42:17', '2018-06-07 20:42:17', '1', '2');
INSERT INTO `tb_product` VALUES ('19', '蓝色便携茶杯', '27', '蓝色,带盖子,方便携带', 'upload\\item\\product\\19\\1078820180607204256.jpg', '6', null, '2018-06-07 20:42:56', '2018-06-07 20:42:56', '0', '2');
INSERT INTO `tb_product` VALUES ('20', '红色便携茶杯', '27', '红色,带盖子,方便携带', 'upload\\item\\product\\20\\5726720180607204308.jpg', '6', null, '2018-06-07 20:43:08', '2018-06-07 20:43:08', '0', '2');
INSERT INTO `tb_product` VALUES ('21', '袋装咖啡豆1', '6', '新品上市', 'upload\\item\\product\\21\\8029420180607204422.jpg', '10', null, '2018-06-07 20:44:22', '2018-06-07 20:44:22', '1', '2');
INSERT INTO `tb_product` VALUES ('22', '袋装咖啡豆2', '6', '新品上市', 'upload\\item\\product\\22\\8830820180607204430.jpg', '10', null, '2018-06-07 20:44:30', '2018-06-07 20:44:30', '0', '2');
INSERT INTO `tb_product` VALUES ('23', '袋装咖啡豆3', '6', '新品上市', 'upload\\item\\product\\23\\4180820180607204437.jpg', '10', null, '2018-06-07 20:44:37', '2018-06-07 20:44:37', '0', '2');
INSERT INTO `tb_product` VALUES ('24', '袋装咖啡豆4', '6', '新品上市', 'upload\\item\\product\\24\\7673720180607204445.jpg', '10', null, '2018-06-07 20:44:45', '2018-06-07 20:44:45', '1', '2');
INSERT INTO `tb_product` VALUES ('27', '手提袋装咖啡豆', '6', '方便手提,纸质手提袋', 'upload\\item\\product\\27\\3459120180608124507.jpg', '10', null, '2018-06-08 12:45:07', '2018-06-08 12:45:07', '0', '2');
INSERT INTO `tb_product` VALUES ('28', '手提袋装咖啡豆2', '6', '方便手提,纸质手提袋', 'upload\\item\\product\\28\\3531620180608124518.jpg', '10', null, '2018-06-08 12:45:18', '2018-06-08 12:45:18', '0', '2');
INSERT INTO `tb_product` VALUES ('29', '蛋挞', '1', '口味微甜', 'upload\\item\\product\\29\\3795020180609165905.jpg', '2', null, '2018-06-09 16:59:05', '2018-06-09 16:59:05', '0', '2');

-- ----------------------------
-- Table structure for `tb_product_category`
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category` (
  `product_category_id` int(10) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(255) DEFAULT NULL,
  `priority` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`product_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_category
-- ----------------------------
INSERT INTO `tb_product_category` VALUES ('1', '甜点', '1', '2018-05-04 20:36:25');
INSERT INTO `tb_product_category` VALUES ('2', '调料', '3', '2018-05-04 20:36:43');
INSERT INTO `tb_product_category` VALUES ('3', '熟食', '3', '2018-05-01 20:37:02');
INSERT INTO `tb_product_category` VALUES ('4', '凉菜', '3', '2018-05-04 20:39:23');
INSERT INTO `tb_product_category` VALUES ('6', '零食', '1', '2018-05-31 22:42:53');
INSERT INTO `tb_product_category` VALUES ('22', '饮料', '2', '2018-06-08 16:36:38');
INSERT INTO `tb_product_category` VALUES ('23', '牛奶', '2', '2018-06-08 16:36:54');
INSERT INTO `tb_product_category` VALUES ('24', '豆奶', '2', '2018-06-08 16:37:31');
INSERT INTO `tb_product_category` VALUES ('25', '咖啡', '2', '2018-06-08 16:38:11');
INSERT INTO `tb_product_category` VALUES ('26', '餐具', '4', '2018-06-08 16:39:12');
INSERT INTO `tb_product_category` VALUES ('27', '茶杯', '4', '2018-06-08 16:39:28');
INSERT INTO `tb_product_category` VALUES ('28', '瓜果', '1', '2018-06-09 19:08:16');
INSERT INTO `tb_product_category` VALUES ('30', '保温杯', '4', '2018-06-09 21:10:13');
INSERT INTO `tb_product_category` VALUES ('31', '坚果', '1', '2018-06-16 14:18:40');

-- ----------------------------
-- Table structure for `tb_product_img`
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img` (
  `product_img_id` int(11) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(255) DEFAULT NULL,
  `img_desc` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_img
-- ----------------------------
INSERT INTO `tb_product_img` VALUES ('1', '测试产品图片地址', '测试产品图片描述', '1', '2018-05-01 20:34:56', '1');
INSERT INTO `tb_product_img` VALUES ('2', '测试产品图片地址', '测试产品图片描述', '1', '2018-05-01 20:34:56', '1');

-- ----------------------------
-- Table structure for `tb_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `role_id` int(2) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(25) NOT NULL,
  `role_pet_name` varchar(25) NOT NULL,
  `role_authority_Level` int(3) unsigned zerofill NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', 'ROLE_ADMIN', '管理员', '000');
INSERT INTO `tb_role` VALUES ('2', 'ROLE_USER', '普通用户', '199');
INSERT INTO `tb_role` VALUES ('3', 'ROLE_VIPUSER', 'vip用户', '101');
INSERT INTO `tb_role` VALUES ('4', 'ROLE_TOURIST', '游客', '899');

-- ----------------------------
-- Table structure for `tb_role_localauth`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_localauth`;
CREATE TABLE `tb_role_localauth` (
  `role_localauth_id` int(100) NOT NULL AUTO_INCREMENT,
  `role_id` int(100) NOT NULL,
  `localauth_id` int(100) NOT NULL,
  PRIMARY KEY (`role_localauth_id`),
  KEY `localauth` (`localauth_id`),
  KEY `role` (`role_id`),
  CONSTRAINT `localauth` FOREIGN KEY (`localauth_id`) REFERENCES `tb_local_auth` (`local_auth_id`),
  CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_localauth
-- ----------------------------
INSERT INTO `tb_role_localauth` VALUES ('1', '1', '1');
INSERT INTO `tb_role_localauth` VALUES ('2', '2', '2');
INSERT INTO `tb_role_localauth` VALUES ('3', '2', '1');
INSERT INTO `tb_role_localauth` VALUES ('4', '2', '4');
INSERT INTO `tb_role_localauth` VALUES ('5', '2', '5');
INSERT INTO `tb_role_localauth` VALUES ('6', '2', '6');

-- ----------------------------
-- Table structure for `tb_shop`
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `shop_id` int(20) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(255) NOT NULL,
  `shop_desc` varchar(255) DEFAULT NULL,
  `shop_addr` varchar(255) DEFAULT NULL,
  `phone` bigint(25) DEFAULT NULL,
  `shop_img` varchar(255) DEFAULT NULL,
  `priority` int(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL,
  `enable_status` int(10) DEFAULT NULL,
  `advice` varchar(255) DEFAULT NULL,
  `area_id` int(10) NOT NULL,
  `owner_id` int(10) NOT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_person` (`owner_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_person` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES ('2', '店铺1号', '爱买不买,又不是不能用', '上海市郊区', '111111', 'upload\\item\\shop\\2\\5989420180609185604.jpg', '1', '2018-06-16 14:17:57', '2018-06-16 14:17:57', '0', '管理员正忙,没空理你', '3', '1');
INSERT INTO `tb_shop` VALUES ('3', '店铺2号', '爱买不买,又不是不能用', '上海', '111111', 'upload\\item\\shop\\3\\2759620180609185807.jpg', '1', '2018-06-09 18:58:07', '2018-06-09 18:58:07', '0', '管理员正忙,没空理你', '1', '1');
INSERT INTO `tb_shop` VALUES ('4', '店铺3号', '爱买不买,又不是不能用', '上海', '111111', 'upload\\item\\shop\\4\\7294220180609185818.jpg', '1', '2018-06-09 18:58:18', '2018-06-09 18:58:18', '0', '管理员正忙,没空理你', '1', '1');
INSERT INTO `tb_shop` VALUES ('5', '店铺4号', '爱买不买,又不是不能用', '上海', '111111', 'upload\\item\\shop\\5\\6761620180609182300.jpg', '1', '2018-06-09 18:23:00', '2018-06-09 18:23:00', '0', '管理员正忙,没空理你', '1', '1');
INSERT INTO `tb_shop` VALUES ('6', '店铺6号', '爱买不买,又不是不能用', '上海', '111111', 'upload\\item\\shop\\6\\2581520180615125717.jpg', '1', '2018-06-15 12:57:17', '2018-06-15 12:57:17', '0', '管理员正忙,没空理你', '1', '1');
INSERT INTO `tb_shop` VALUES ('7', '店铺5号', '爱买不买,又不是不能用', '上海', '111111', 'upload\\item\\shop\\7\\3579520180609142459.jpg', '1', '2018-06-09 14:45:25', '2018-06-09 14:45:25', '0', '管理员正忙,没空理你', '1', '1');
