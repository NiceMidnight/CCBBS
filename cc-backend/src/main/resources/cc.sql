/*
 Navicat MySQL Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : cc

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 24/08/2023 16:08:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `dict_id` int NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_type_id` int NULL DEFAULT NULL COMMENT '类型id',
  `dict_type_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型名',
  `dict_item_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '属性',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (1, 0, 'role', 'user');
INSERT INTO `dict` VALUES (2, 1, 'role', 'manager');
INSERT INTO `dict` VALUES (3, 0, '图片类别', '走马灯');
INSERT INTO `dict` VALUES (4, 1, '图片类别', '广告');

-- ----------------------------
-- Table structure for sys_images
-- ----------------------------
DROP TABLE IF EXISTS `sys_images`;
CREATE TABLE `sys_images`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `img_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '文件名字',
  `img_path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '文件路径',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '上传用户id',
  `upload_time` timestamp(6) NULL DEFAULT NULL COMMENT '上传时间',
  `visible` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '1' COMMENT '可见性',
  `type` int NULL DEFAULT NULL COMMENT '用的地方',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `img_user`(`user_id`) USING BTREE,
  CONSTRAINT `img_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_images
-- ----------------------------
INSERT INTO `sys_images` VALUES (1, 'index', 'static/sys_img/2023.jpg', 1, '2023-08-21 14:24:16.000000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (2, 'index2', 'static/sys_img/2023.jpg', 1, '2023-08-21 19:19:28.000000', 'VISIBLE', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '用户id',
  `user_head` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `user_sex` int NULL DEFAULT NULL COMMENT '性别（0女/1男）',
  `user_age` int NULL DEFAULT NULL COMMENT '年龄',
  `user_phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话',
  `user_address` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '住址',
  `user_date` timestamp(6) NOT NULL COMMENT '注册时间',
  `user_role` int NOT NULL DEFAULT 0 COMMENT '角色（0用户/1管理员）',
  `user_status` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '状态，查看是否被禁用违规用户',
  `user_deleted` int NOT NULL DEFAULT 1 COMMENT '逻辑删除（1存在/0删除）',
  `user_version` int NOT NULL DEFAULT 1 COMMENT '乐观锁',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'static/sys_img/adminHead/head.jpg', 'root', 'root', 0, 18, '16616649102', '广东省佛山市高明区xx镇xx街道xx栋', '2023-08-07 13:10:17.000000', 1, 'ENABLE', 1, 1);
INSERT INTO `user` VALUES (2, 'static/sys_img/adminHead/2023.jpg', 'zhangsan', 'root', 1, 20, '16616649102', '广西省桂林市', '2023-08-06 13:14:21.000000', 0, 'ENABLE', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
