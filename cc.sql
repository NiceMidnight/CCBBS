/*
 Navicat MySQL Data Transfer

 Source Server         : ccDB
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : cc0305.mysql.polardb.rds.aliyuncs.com:3306
 Source Schema         : cc

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 21/12/2023 15:32:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文章标题',
  `article_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文章内容',
  `created_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者id',
  `created_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者id',
  `updated_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '阅读量',
  `topic_id` int(11) NULL DEFAULT NULL COMMENT '主题id',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '文章' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '测试11', '三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1', 1, '2023-11-02 16:55:19.000000', 1, '2023-12-02 17:52:07.472000', 10, 1);
INSERT INTO `article` VALUES (2, '测试2', '三生三世十里桃花222222222', 1, '2023-11-17 16:55:32.000000', 1, '2023-12-07 16:10:18.288000', 0, 1);
INSERT INTO `article` VALUES (3, '测试33', '三生三世十里桃花33333345', 1, '2023-11-23 16:55:40.000000', NULL, NULL, 0, 1);
INSERT INTO `article` VALUES (7, '水水水水水水水水', '少时诵诗书', 1, '2023-11-24 16:55:46.000000', NULL, NULL, 0, 1);
INSERT INTO `article` VALUES (8, '生生世世', '生生世世', 1, '2023-11-26 16:55:53.000000', NULL, NULL, 0, 1);
INSERT INTO `article` VALUES (9, '测试4', '这是一段内容，\\n可以在这里添加多行文本。\\n\\n  这是新的一行，开头空两格。\\n  这也是新的一行，同样开头空两格。\\n\\n再来一段新的内容。这是一段内容，\r\n可以在这里添加多行文本。\r\n\r\n  这是新的一行，开头空两格。\r\n  这也是新的一行，同样开头空两格。\r\n\r\n再来一段新的内容。', 1, '2023-11-30 16:55:59.000000', NULL, NULL, 0, 1);
INSERT INTO `article` VALUES (10, '测试7', '少时诵诗书是', 1, '2023-12-02 17:54:46.891000', NULL, NULL, 10, 1);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `comment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '评论内容',
  `post_id` int(11) NOT NULL COMMENT '帖子id',
  `user_id` bigint(20) NOT NULL COMMENT '评论用户id',
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '评论' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_type_id` int(11) NULL DEFAULT NULL COMMENT '类型id',
  `dict_type_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型名',
  `dict_item_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '属性',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (1, 0, 'role', 'user');
INSERT INTO `dict` VALUES (2, 1, 'role', 'manager');
INSERT INTO `dict` VALUES (3, 0, '图片类别', '走马灯');
INSERT INTO `dict` VALUES (4, 1, '图片类别', '广告');
INSERT INTO `dict` VALUES (6, 3, '图片类别', '4');
INSERT INTO `dict` VALUES (7, 4, '图片类别', '4444');

-- ----------------------------
-- Table structure for dislikes_for_post
-- ----------------------------
DROP TABLE IF EXISTS `dislikes_for_post`;
CREATE TABLE `dislikes_for_post`  (
  `dislike_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '踩id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '帖子id',
  `dislike_status` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否踩Disliked/Undisliked',
  `timestamp` datetime(6) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`dislike_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '踩贴' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dislikes_for_post
-- ----------------------------

-- ----------------------------
-- Table structure for favorite_for_post
-- ----------------------------
DROP TABLE IF EXISTS `favorite_for_post`;
CREATE TABLE `favorite_for_post`  (
  `favorite_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '帖子id',
  `favorite_note` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `folder_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收藏夹id',
  `timestamp` datetime(6) NULL DEFAULT NULL COMMENT '收藏时间',
  `favorite_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否收藏Favorite/Unfavorite',
  `favorite_post_status` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子的所有状态（COMPLIANCE/IRREGULARITY）（PUBLIC/PRIVATE）',
  PRIMARY KEY (`favorite_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收藏帖子' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorite_for_post
-- ----------------------------

-- ----------------------------
-- Table structure for folder_for_post
-- ----------------------------
DROP TABLE IF EXISTS `folder_for_post`;
CREATE TABLE `folder_for_post`  (
  `folder_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏夹id',
  `folder_name` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收藏夹名称',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `created_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`folder_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收藏夹' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of folder_for_post
-- ----------------------------

-- ----------------------------
-- Table structure for likes_for_post
-- ----------------------------
DROP TABLE IF EXISTS `likes_for_post`;
CREATE TABLE `likes_for_post`  (
  `like_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '点赞id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '帖子id',
  `like_status` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否点赞Liked/Unliked',
  `timestamp` datetime(6) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`like_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点赞贴' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of likes_for_post
-- ----------------------------

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `post_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `topic_id` int(5) NULL DEFAULT NULL COMMENT '所属主题id',
  `post_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '帖子标题',
  `post_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '帖子内容',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '发布用户id',
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '发布时间',
  `post_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否合规（COMPLIANCE/IRREGULARITY）',
  `post_visibility` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否可见（PUBLIC/PRIVATE）',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '阅读量',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '帖子' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (1, 1, '测试', '测试', 2, '2023-11-24 10:22:54.000000', 'COMPLIANCE', 'PUBLIC', 0);
INSERT INTO `post` VALUES (2, 2, '测试2', '测试啊2', 1, '2023-11-24 10:22:54.000000', 'COMPLIANCE', 'PUBLIC', 0);

-- ----------------------------
-- Table structure for replies
-- ----------------------------
DROP TABLE IF EXISTS `replies`;
CREATE TABLE `replies`  (
  `reply_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复评论id',
  `reply_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '评论内容',
  `user_id` bigint(20) NOT NULL COMMENT '回复评论用户id',
  `comment_id` int(11) NOT NULL COMMENT '评论id',
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`reply_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '回复' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of replies
-- ----------------------------

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `report_id` int(11) NOT NULL COMMENT '报告id',
  `report_type` int(11) NULL DEFAULT NULL COMMENT '报告类型',
  `report_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '报告内容',
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT '提交用户的id',
  PRIMARY KEY (`report_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户提交关于网站相关报告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report
-- ----------------------------

-- ----------------------------
-- Table structure for sys_images
-- ----------------------------
DROP TABLE IF EXISTS `sys_images`;
CREATE TABLE `sys_images`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `img_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名字',
  `img_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '上传用户id',
  `upload_time` timestamp(6) NULL DEFAULT NULL COMMENT '上传时间',
  `visible` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'VISIBLE' COMMENT '可见性',
  `type` int(11) NULL DEFAULT NULL COMMENT '用的地方',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `img_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统图片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_images
-- ----------------------------
INSERT INTO `sys_images` VALUES (1, 'index', 'static/sys_img/2023.jpg', 1, '2023-08-21 14:24:16.000000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (2, 'index2', 'static/sys_img/2023.jpg', 1, '2023-08-21 19:19:28.000000', 'INVISIBLE', 1);
INSERT INTO `sys_images` VALUES (3, '金克丝 泳池派对 4K壁纸_彼岸图网.jpg', 'static/sys_img/金克丝 泳池派对 4K壁纸_彼岸图网.jpg', 1, '2023-12-07 11:01:01.907000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (4, '咒术回战 五条悟4k壁纸_彼岸图网.jpg', 'static/sys_img/咒术回战 五条悟4k壁纸_彼岸图网.jpg', 1, '2023-12-07 11:05:38.008000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (5, '动漫美女 鲜花 帽子 唯美 好看 4K壁纸_彼岸图网.jpg', 'static/sys_img/动漫美女 鲜花 帽子 唯美 好看 4K壁纸_彼岸图网.jpg', 1, '2023-12-07 11:26:18.464000', 'VISIBLE', 1);

-- ----------------------------
-- Table structure for topic_for_article
-- ----------------------------
DROP TABLE IF EXISTS `topic_for_article`;
CREATE TABLE `topic_for_article`  (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，主题id',
  `topic_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主题名称',
  `created_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者id',
  `created_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者id',
  `updated_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `topic_status` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主题状态（ENABLE/DISABLE）',
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '公告文章主题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic_for_article
-- ----------------------------
INSERT INTO `topic_for_article` VALUES (1, '测试', 1, '2023-12-20 02:50:42.000000', NULL, NULL, 'ENABLE');

-- ----------------------------
-- Table structure for topic_for_post
-- ----------------------------
DROP TABLE IF EXISTS `topic_for_post`;
CREATE TABLE `topic_for_post`  (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，主题id',
  `topic_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主题名称',
  `created_id` bigint(20) NOT NULL COMMENT '发布者id',
  `created_time` datetime(6) NOT NULL COMMENT '发布时间',
  `updated_id` bigint(20) NULL DEFAULT NULL COMMENT '更新者id',
  `updated_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `topic_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子状态Enabled/Disabled',
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子主题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic_for_post
-- ----------------------------
INSERT INTO `topic_for_post` VALUES (1, '测试', 1, '2023-12-17 01:26:03.000000', 1, '2023-12-19 02:16:42.193000', 'ENABLE');
INSERT INTO `topic_for_post` VALUES (2, '失物招领', 1, '2023-12-17 01:26:03.000000', NULL, NULL, 'ENABLE');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_sex` int(11) NULL DEFAULT NULL COMMENT '性别（0女/1男）',
  `user_age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `user_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址',
  `user_personality` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `user_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '注册时间',
  `user_role` int(11) NOT NULL DEFAULT 0 COMMENT '角色（0用户/1管理员）',
  `user_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态，查看是否被禁用违规用户',
  `user_deleted` int(11) NOT NULL DEFAULT 1 COMMENT '逻辑删除（1存在/0删除）',
  `user_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  PRIMARY KEY (`id`, `user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'static/sys_img/adminHead/head.jpg', 'root', 'root', 1, 18, '16616649102', '广东省佛山市高明区xx镇xx街道xx栋', NULL, '2023-08-07 13:10:17.000000', 1, 'ENABLE', 1, 1);
INSERT INTO `user` VALUES (2, 'static/sys_img/adminHead/2023.jpg', 'zhangsan', 'root', 0, 20, '16616649103', '广西省桂林市1', '123随便', '2023-12-18 02:47:08.066561', 0, 'ENABLE', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
