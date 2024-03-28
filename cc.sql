/*
 Navicat MySQL Data Transfer

 Source Server         : jj
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : cc

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 21/03/2024 01:28:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apply_recruiter
-- ----------------------------
DROP TABLE IF EXISTS `apply_recruiter`;
CREATE TABLE `apply_recruiter`  (
  `apply_id` int NOT NULL AUTO_INCREMENT COMMENT '申请应聘人员权限id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_at` datetime(6) NOT NULL COMMENT '申请时间',
  `apply_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'IRREGULARITY' COMMENT '权限状态(COMPLIANCE/IRREGULARITY)',
  `handling_user_id` bigint NULL DEFAULT NULL COMMENT '处理用户',
  `handling_at` datetime(6) NULL DEFAULT NULL COMMENT '处理时间',
  `notes` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Pending' COMMENT '备注',
  `truth_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `student_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  PRIMARY KEY (`apply_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '申请招聘人员权限信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apply_recruiter
-- ----------------------------
INSERT INTO `apply_recruiter` VALUES (1, 2, '2024-03-08 01:31:22.939000', 'COMPLIANCE', 1, '2024-03-10 22:19:42.887000', '测试啊', '王小美', '202015270235', '16616669111');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` int NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `article_title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '文章标题',
  `article_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT '文章内容',
  `created_id` bigint NULL DEFAULT NULL COMMENT '创建者id',
  `created_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_id` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `updated_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `view_count` int NULL DEFAULT 0 COMMENT '阅读量',
  `topic_id` int NULL DEFAULT NULL COMMENT '主题id',
  `article_status` varchar(7) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT 'PUBLIC' COMMENT '可见/不可见',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '公告文章' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '测试11', '<p>三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1</p>', 1, '2023-11-02 16:55:19.000000', 1, '2024-03-13 18:19:58.627000', 10, 4, 'PUBLIC');
INSERT INTO `article` VALUES (2, '测试2', '三生三世十里桃花222222222', 1, '2023-11-17 16:55:32.000000', 1, '2023-12-07 16:10:18.288000', 0, 1, 'PUBLIC');
INSERT INTO `article` VALUES (3, '测试33', '<p>三生三世十里桃花33333345</p>', 1, '2023-11-23 16:55:40.000000', 1, '2024-03-13 18:19:53.083000', 0, 2, 'PUBLIC');
INSERT INTO `article` VALUES (7, '水水水水水水水水啊', '<h1>查看提交</h1><p style=\"text-align: left;\">1231231231<img src=\"https://postfiles.oss-cn-guangzhou.aliyuncs.com/mmexport1639369490349.jpg\" alt=\"\" data-href=\"\" style=\"width: 147.30px;height: 146.88px;\"><img src=\"https://postfiles.oss-cn-guangzhou.aliyuncs.com/getThumbnail.jpg\" alt=\"\" data-href=\"\" style=\"width: 147.31px;height: 147.31px;\"></p>', 1, '2023-11-24 16:55:46.000000', 1, '2024-03-13 18:20:19.961000', 0, 5, 'PUBLIC');
INSERT INTO `article` VALUES (8, '生生世世', '<p>生生世世</p>', 1, '2023-11-26 16:55:53.000000', 1, '2024-03-13 18:19:39.900000', 0, 4, 'PUBLIC');
INSERT INTO `article` VALUES (9, '测试4', '<p>这是一段内容，\\n可以在这里添加多行文本。\\n\\n &nbsp;这是新的一行，开头空两格。\\n &nbsp;这也是新的一行，同样开头空两格。\\n\\n再来一段新的内容。这是一段内容，<br></p><p>可以在这里添加多行文本。<br></p><p><br></p><p> &nbsp;这是新的一行，开头空两格。<br></p><p> &nbsp;这也是新的一行，同样开头空两格。<br></p><p><br></p><p>再来一段新的内容。</p>', 1, '2024-02-22 16:55:59.000000', 1, '2024-03-13 18:19:36.447000', 0, 3, 'PUBLIC');
INSERT INTO `article` VALUES (11, '重构', '123132132', 1, '2024-03-15 00:14:32.000000', 1, '2024-03-13 18:19:31.104000', 0, 1, 'PUBLIC');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `post_id` int NOT NULL COMMENT '帖子id',
  `user_id` bigint NOT NULL COMMENT '评论用户id',
  `comment_content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '评论内容',
  `parent_comment_id` int NULL DEFAULT NULL COMMENT '父评论id指向comment_id',
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '回复时间',
  `status_for_user` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT 'Unread' COMMENT '评论状态已读/未读（Unread/Read）',
  `status_for_compliance` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT 'COMPLIANCE' COMMENT '评论状态是否合规（COMPLIANCE/IRREGULARITY）',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '评论' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (24, 1, 2, '测试', NULL, '2024-02-08 18:13:10.000000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (25, 1, 2, '测试1', 24, '2024-02-08 18:13:10.000000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (26, 1, 2, '测试2', 25, '2024-02-08 18:13:10.000000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (27, 1, 2, '测试3', 26, '2024-02-10 18:13:22.695000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (28, 1, 2, '测试3', 26, '2024-02-10 18:13:30.129000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (29, 1, 2, '测试4', 27, '2024-02-10 18:13:22.695000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (30, 1, 2, '测试4', 27, '2024-02-10 18:13:22.695000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (31, 1, 2, '测试5', 29, '2024-02-10 18:45:35.158000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (32, 1, 2, '测试5', 29, '2024-02-10 18:45:35.158000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (33, 1, 2, '测试6', 31, '2024-02-10 18:45:35.158000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (34, 1, 2, '测试6', 31, '2024-02-10 18:45:35.158000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (35, 1, 2, 'hhhhhhhhhhhhh', 24, '2024-02-11 16:02:38.180000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (36, 1, 2, '哈哈哈很好', NULL, '2024-02-11 16:15:47.051000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (37, 1, 2, '牛的', 36, '2024-02-11 16:15:54.431000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (38, 1, 2, '牛的牛', 37, '2024-02-11 16:15:59.188000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (39, 1, 2, '牛的牛的牛', 38, '2024-02-11 16:16:11.442000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (40, 1, 2, '牛的牛的牛', 38, '2024-02-11 16:16:18.466000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (41, 1, 2, 'veryGood', NULL, '2024-02-11 19:43:11.692000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (42, 1, 2, '123', 41, '2024-02-11 19:43:17.181000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (43, 1, 3, '厉害厉害', NULL, '2024-03-01 00:19:52.591000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (44, 1, 3, '好牛', 42, '2024-03-01 00:20:03.098000', 'Read', 'COMPLIANCE');
INSERT INTO `comments` VALUES (45, 1, 3, 'good', 41, '2024-03-01 00:20:10.673000', 'Read', 'COMPLIANCE');

-- ----------------------------
-- Table structure for daily_unique_visitors
-- ----------------------------
DROP TABLE IF EXISTS `daily_unique_visitors`;
CREATE TABLE `daily_unique_visitors`  (
  `log_id` int NOT NULL AUTO_INCREMENT COMMENT '访问id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `first_access_time` datetime(6) NULL DEFAULT NULL COMMENT '生成时间',
  `ip` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'ip地址',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '每日用户单个访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of daily_unique_visitors
-- ----------------------------
INSERT INTO `daily_unique_visitors` VALUES (3, 2, '2024-02-23 15:43:08.000000', '0:0:0:0:0:0:0:1');
INSERT INTO `daily_unique_visitors` VALUES (4, 2, '2024-03-17 19:43:24.000000', '0:0:0:0:0:0:0:1');
INSERT INTO `daily_unique_visitors` VALUES (5, 2, '2024-03-17 13:43:58.000000', '0:0:0:0:0:0:0:1');
INSERT INTO `daily_unique_visitors` VALUES (6, 2, '2024-03-18 12:44:22.000000', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `dict_id` int NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_type_id` int NULL DEFAULT NULL COMMENT '类型id',
  `dict_type_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型名',
  `dict_item_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '属性',
  `dict_color` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '字典颜色',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (1, 0, 'role', 'student', '#FFD2D2');
INSERT INTO `dict` VALUES (2, 1, 'role', 'manager', '#FFE1B7');
INSERT INTO `dict` VALUES (3, 0, '图片类别', '走马灯', '#ECFBB0');
INSERT INTO `dict` VALUES (4, 1, '图片类别', '广告', '#C3FFED');
INSERT INTO `dict` VALUES (6, 3, '图片类别', '4', '#C8E1FF');
INSERT INTO `dict` VALUES (7, 4, '图片类别', '4444', '#EAC5FF');
INSERT INTO `dict` VALUES (8, 2, 'role', 'recruiter', '#FF96AB');

-- ----------------------------
-- Table structure for dislikes_for_post
-- ----------------------------
DROP TABLE IF EXISTS `dislikes_for_post`;
CREATE TABLE `dislikes_for_post`  (
  `user_id` bigint NOT NULL COMMENT '用户id',
  `post_id` int NOT NULL COMMENT '帖子id',
  `dislike_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否踩Disliked/Undisliked',
  `timestamp` datetime(6) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '踩贴' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dislikes_for_post
-- ----------------------------
INSERT INTO `dislikes_for_post` VALUES (2, 1, 'Disliked', '2024-02-06 17:55:02.370000');
INSERT INTO `dislikes_for_post` VALUES (2, 2, 'Undisliked', '2024-02-11 19:46:57.605000');

-- ----------------------------
-- Table structure for favorite_for_post
-- ----------------------------
DROP TABLE IF EXISTS `favorite_for_post`;
CREATE TABLE `favorite_for_post`  (
  `favorite_id` int NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `post_id` int NULL DEFAULT NULL COMMENT '帖子id',
  `favorite_note` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `folder_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收藏夹id',
  `timestamp` datetime(6) NULL DEFAULT NULL COMMENT '收藏时间',
  `favorite_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否收藏Favorite/Unfavorite',
  `favorite_post_status` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子的所有状态（COMPLIANCE/IRREGULARITY）（PUBLIC/PRIVATE）',
  PRIMARY KEY (`favorite_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收藏帖子' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorite_for_post
-- ----------------------------

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `feedback_id` int NOT NULL AUTO_INCREMENT COMMENT '反馈id',
  `topic_id` int NULL DEFAULT NULL COMMENT '反馈类型id',
  `feedback_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '反馈内容',
  `creator_id` bigint NULL DEFAULT NULL COMMENT '提交的用户的id',
  `created_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `handler_id` bigint NULL DEFAULT NULL COMMENT '处理的管理员的id',
  `handle_time` datetime(6) NULL DEFAULT NULL COMMENT '处理时间',
  `feedback_status` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Pending' COMMENT '该反馈的状态未处理Pending/处理中InProgress/已处理Processed/已关闭Closed',
  `reminder_status` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'NotReminded' COMMENT '是否催促中Reminder/NotReminded',
  PRIMARY KEY (`feedback_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户提交关于网站相关报告' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (1, 1, '测试', 2, '2024-03-20 23:33:59.000000', NULL, NULL, 'Pending', 'NotReminded');

-- ----------------------------
-- Table structure for file_for_post
-- ----------------------------
DROP TABLE IF EXISTS `file_for_post`;
CREATE TABLE `file_for_post`  (
  `file_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件id',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `created_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `post_id` int NULL DEFAULT NULL COMMENT '帖子id',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子附件文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_for_post
-- ----------------------------

-- ----------------------------
-- Table structure for folder_for_post
-- ----------------------------
DROP TABLE IF EXISTS `folder_for_post`;
CREATE TABLE `folder_for_post`  (
  `folder_id` int NOT NULL AUTO_INCREMENT COMMENT '收藏夹id',
  `folder_name` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收藏夹名称',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `created_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`folder_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收藏夹' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of folder_for_post
-- ----------------------------

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `follower_id` bigint NOT NULL COMMENT '关注者id（本人）',
  `following_id` bigint NOT NULL COMMENT '被关注者id',
  `follow_date` datetime(0) NULL DEFAULT NULL COMMENT '关注时间',
  `follow_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Follow' COMMENT '状态',
  PRIMARY KEY (`follower_id`, `following_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '关注表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES (2, 1, '2024-03-06 21:13:06', 'Follow');
INSERT INTO `follow` VALUES (2, 2, '2024-03-06 21:16:06', 'Unfollowed');

-- ----------------------------
-- Table structure for images
-- ----------------------------
DROP TABLE IF EXISTS `images`;
CREATE TABLE `images`  (
  `img_id` int NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `img_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片名',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片地址',
  `upload_time` datetime(6) NULL DEFAULT NULL COMMENT '上传时间',
  `img_status` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'legitimate' COMMENT '是否合法违规删除(legitimate/illegality)',
  PRIMARY KEY (`img_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '云端图片' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of images
-- ----------------------------
INSERT INTO `images` VALUES (1, 'getThumbnail.jpg', 'https://postfiles.oss-cn-guangzhou.aliyuncs.com/getThumbnail.jpg', '2024-02-22 14:13:15.409000', 'legitimate');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `job_id` int NOT NULL AUTO_INCREMENT COMMENT '招聘id',
  `topic_id` int NOT NULL COMMENT '主题',
  `job_title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工作标题',
  `job_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工作内容',
  `user_id` int NOT NULL COMMENT '发布用户id',
  `created_at` datetime(6) NOT NULL COMMENT '创建时间',
  `job_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'IRREGULARITY' COMMENT '是否合规（COMPLIANCE/IRREGULARITY）',
  `job_visibility` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PUBLIC' COMMENT '是否可见（PUBLIC/PRIVATE）',
  `view_count` int NOT NULL DEFAULT 0 COMMENT '点击数',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '招聘信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (1, 3, '天河区', '<p>撒大大飒飒打撒打撒</p>', 2, '2024-03-05 17:15:19.481000', 'COMPLIANCE', 'PUBLIC', 0);
INSERT INTO `job` VALUES (2, 1, '中国人保2024校园招聘简章中国人保2024校园招聘简章', '<p> &nbsp; &nbsp;中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章中国人保2024校园招聘简章</p>', 2, '2024-03-06 22:35:55.690000', 'COMPLIANCE', 'PUBLIC', 0);

-- ----------------------------
-- Table structure for likes_for_post
-- ----------------------------
DROP TABLE IF EXISTS `likes_for_post`;
CREATE TABLE `likes_for_post`  (
  `user_id` bigint NOT NULL COMMENT '用户id',
  `post_id` int NOT NULL COMMENT '帖子id',
  `like_status` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否点赞Liked/Unliked',
  `timestamp` datetime(6) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点赞贴' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of likes_for_post
-- ----------------------------
INSERT INTO `likes_for_post` VALUES (2, 1, 'Liked', '2024-02-06 17:56:54.810000');
INSERT INTO `likes_for_post` VALUES (2, 2, 'Liked', '2024-02-18 08:32:51.475000');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `post_id` int NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `topic_id` int NOT NULL COMMENT '所属主题id',
  `post_title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '帖子标题',
  `post_content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '帖子内容',
  `user_id` bigint NOT NULL COMMENT '发布用户id',
  `created_at` datetime(6) NOT NULL COMMENT '发布时间',
  `post_status` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT 'COMPLIANCE' COMMENT '是否合规（COMPLIANCE/IRREGULARITY）',
  `post_visibility` varchar(7) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT 'PUBLIC' COMMENT '是否可见（PUBLIC/PRIVATE）',
  `view_count` int NULL DEFAULT 0 COMMENT '阅读量',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞数量',
  `dislike_count` int NULL DEFAULT 0 COMMENT '踩贴数量',
  `comment_count` int NULL DEFAULT 0 COMMENT '评论数量',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '帖子' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (1, 1, '测试', '测试', 2, '2023-11-24 10:22:54.000000', 'COMPLIANCE', 'PUBLIC', 62, 1, 1, 22);
INSERT INTO `post` VALUES (2, 2, '失物手表', '测试啊2', 1, '2024-03-14 10:22:54.000000', 'COMPLIANCE', 'PUBLIC', 73, 1, 0, 0);
INSERT INTO `post` VALUES (3, 1, '测试提交', '<h1>查看提交</h1><p style=\"text-align: left;\">1231231231<img src=\"https://postfiles.oss-cn-guangzhou.aliyuncs.com/mmexport1639369490349.jpg\" alt=\"\" data-href=\"\" style=\"width: 30%;\"></p>', 2, '2024-03-18 14:51:58.000000', 'COMPLIANCE', 'PUBLIC', 29, 0, 0, 0);

-- ----------------------------
-- Table structure for replies
-- ----------------------------
DROP TABLE IF EXISTS `replies`;
CREATE TABLE `replies`  (
  `reply_id` int NOT NULL AUTO_INCREMENT COMMENT '回复评论id',
  `reply_content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '评论内容',
  `user_id` bigint NOT NULL COMMENT '回复评论用户id',
  `comment_id` int NOT NULL COMMENT '评论id',
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`reply_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '回复' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of replies
-- ----------------------------

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `report_id` int NOT NULL COMMENT '报告id',
  `report_type` int NULL DEFAULT NULL COMMENT '报告类型',
  `report_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '报告内容',
  `user_id` bigint NULL DEFAULT NULL COMMENT '提交用户的id',
  PRIMARY KEY (`report_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户提交关于网站相关报告' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of report
-- ----------------------------

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
  `visible` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT 'VISIBLE' COMMENT '可见性',
  `type` int NULL DEFAULT NULL COMMENT '用的地方',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `img_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统图片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_images
-- ----------------------------
INSERT INTO `sys_images` VALUES (1, 'index', 'static/sys_img/725a25d6-d433-49fb-bf14-fa41a336693a-动漫女孩 水花 海浪 海洋之女 4k壁纸_彼岸图网.jpg', 1, '2023-08-21 14:24:16.000000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (3, '金克丝 泳池派对 4K壁纸_彼岸图网.jpg', 'static/sys_img/金克丝 泳池派对 4K壁纸_彼岸图网.jpg', 1, '2023-12-07 11:01:01.907000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (4, '咒术回战 五条悟4k壁纸_彼岸图网.jpg', 'static/sys_img/咒术回战 五条悟4k壁纸_彼岸图网.jpg', 1, '2023-12-07 11:05:38.008000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (5, '动漫美女 鲜花 帽子 唯美 好看 4K壁纸_彼岸图网.jpg', 'static/sys_img/动漫美女 鲜花 帽子 唯美 好看 4K壁纸_彼岸图网.jpg', 1, '2023-12-07 11:26:18.464000', 'VISIBLE', 1);
INSERT INTO `sys_images` VALUES (6, '宣纸国画 紫罗兰永恒花园 赛博color 4k壁纸_彼岸图网.jpg', 'static/sys_img/宣纸国画 紫罗兰永恒花园 赛博color 4k壁纸_彼岸图网.jpg', 1, '2023-12-26 00:18:55.407000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (16, '动漫女孩 水花 海浪 海洋之女 4k壁纸_彼岸图网.jpg', 'static/sys_img/07957ce3-b0ea-49e2-957e-c2b791befb5d-动漫女孩 水花 海浪 海洋之女 4k壁纸_彼岸图网.jpg', 1, '2024-02-02 21:30:56.749000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (20, '七海深奈实4K壁纸_彼岸图网.jpg', 'static/sys_img/0c8e7560-74f2-4994-8f62-ea4727d45182-七海深奈实4K壁纸_彼岸图网.jpg', 1, '2024-03-21 01:27:38.868000', 'VISIBLE', 0);

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token`  (
  `token_id` int NOT NULL AUTO_INCREMENT COMMENT '令牌id',
  `user_id` int NOT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `redis_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '键',
  `redis_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '值',
  `token_status` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'EFFECTIVE' COMMENT '是否删除(EFFECTIVE/INVALID)',
  PRIMARY KEY (`token_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '令牌' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of token
-- ----------------------------
INSERT INTO `token` VALUES (37, 2, 'zhangsan', '45076059-c159-4f97-9176-55529e378f0a', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (38, 2, 'zhangsan', '45c8bc85-4301-424c-a779-9c8ddd8911b4', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (39, 2, 'zhangsan', '69c09c38-e4c6-41f5-8436-557728eabdd8', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (40, 2, 'zhangsan', 'ca2b9e2b-6508-4b81-9f60-a26cdae2fc6f', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (41, 2, 'zhangsan', '8a9865e5-4bae-4328-8e16-85cc97e7f997', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (42, 2, 'zhangsan', '8cbc8b76-f1fc-422f-9e79-d50dbe76dd01', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (43, 2, 'zhangsan', '44db9a02-87fb-45ed-8cec-cef8d350d02f', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (44, 2, 'zhangsan', '040b80b0-d719-44c9-9eeb-dbfd953a638c', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (45, 1, 'root', 'c3b847e3-9e16-40b7-af61-e3eeebc1bc0d', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (46, 1, 'root', 'b86ec1ba-5c83-4915-8508-84533899f81a', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (47, 1, 'root', '3b6bfde5-8fc5-473c-b606-14fdf702bd8b', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (48, 1, 'root', 'c4ec804d-bb6e-4665-a512-27079cb82cb4', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (49, 1, 'root', 'ac6801e7-99d1-482d-ab38-abb295cf6d1b', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (50, 1, 'root', '0d2086c0-8ecd-42f4-a7d4-6620d2d80dcc', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (51, 2, 'zhangsan', '22a4d346-fd31-4ac4-b267-6f69d68383f0', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (52, 2, 'zhangsan', '6251945c-c0e6-4ead-a435-4d1ca1b3cb0f', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (53, 2, 'zhangsan', '95b10633-5a45-430e-82df-3ca8b56d8dc2', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (54, 2, 'zhangsan', 'ead3af4d-4077-468b-86a4-ce67a2fd037b', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (55, 3, '123123123', 'd3b65252-a1ee-41a6-8ad3-e1c83e2441be', '{\"id\":3,\"userHead\":null,\"userName\":\"123123123\",\"nickName\":\"\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":\"12341234123\",\"userAddress\":null,\"userDate\":1707900041175,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (56, 2, 'zhangsan', '1f535708-eb74-4f25-ac94-2095674c5fae', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市1\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":2}', 'INVALID');
INSERT INTO `token` VALUES (57, 1, 'root', 'c07ae7fc-14a2-4fb0-863c-03a2843d91f3', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (58, 2, 'zhangsan', '4a70fe42-b745-4e7a-90a2-8ba5b9dd808e', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (59, 2, 'zhangsan', '27fe51bc-c4d7-4864-9268-9d080342eb4f', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (60, 1, 'root', '5509b4a5-6bac-41bc-bd28-069e0bbffd77', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (61, 2, 'zhangsan', 'a1f46c97-1947-4ea1-87a5-30a2d4d6e313', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (62, 2, 'zhangsan', 'a6165f59-23cb-485a-aba8-16a9a25e4bc5', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":0,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (63, 1, 'root', '1ab91e71-70e2-4c92-82b7-a140fdf9ef56', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (64, 1, 'root', '35ea083c-52bf-46d3-bbef-67a81bdea579', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (65, 1, 'root', 'cc87e2d1-f66b-4fc2-8208-4f48b7e967cd', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (66, 1, 'root', '78d3043e-903c-456f-b082-b48be6268033', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (67, 1, 'root', 'db2a3edd-d60f-4421-85d6-4dee30f77e0d', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (68, 1, 'root', '00a2384d-10b1-401c-bfa3-c39bb2c6b11c', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (69, 1, 'root', 'b91cf0c3-05c5-469b-82e1-f135260668a7', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (70, 1, 'root', '55b9537d-5034-4d26-bf2f-323d4270ae97', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (71, 1, 'root', '9e911cf1-1009-4c5d-bdfe-22558a39eeaf', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'INVALID');
INSERT INTO `token` VALUES (72, 2, 'zhangsan', 'a9b7a39e-72e2-463d-954c-3d4ba467313e', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":2,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":1,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (73, 2, 'zhangsan', 'bd8ab0e7-7a64-4500-9787-904fcef7ca08', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":2,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (74, 2, 'zhangsan', '02d483f4-5cff-44cf-9ccd-eaea6168d7ad', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":2,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (75, 2, 'zhangsan', '5639b5ce-4451-4778-9fc5-6cf1734f2535', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":2,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (76, 2, 'zhangsan', '6bd59f24-1555-4322-bd40-295fe5a72872', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":2,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (77, 2, 'zhangsan', '645dca28-c283-4e2d-ad72-dd2fb93f2094', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":2,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (78, 2, 'zhangsan', '17a3b9e5-a82d-4cbd-b61f-d65b481befca', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":2,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":1}', 'INVALID');
INSERT INTO `token` VALUES (79, 2, 'zhangsan', 'cf636fec-7cf6-419c-967d-b65710db98cd', '{\"id\":2,\"userHead\":\"static/sys_img/adminHead/2023.jpg\",\"userName\":\"zhangsan\",\"nickName\":\"小宜\",\"password\":null,\"userSex\":1,\"userAge\":20,\"userPhone\":\"16616649103\",\"userAddress\":\"广西省桂林市\",\"userDate\":1707895578207,\"userRole\":2,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":\"123随便\",\"fans\":1}', 'EFFECTIVE');
INSERT INTO `token` VALUES (80, 1, 'root', '66419331-64f3-4e81-b23a-5d299ee8e0f1', '{\"id\":1,\"userHead\":null,\"userName\":\"root\",\"nickName\":\"管理员\",\"password\":null,\"userSex\":null,\"userAge\":null,\"userPhone\":null,\"userAddress\":null,\"userDate\":null,\"userRole\":1,\"dictItemName\":null,\"dictColor\":null,\"userStatus\":\"ENABLE\",\"userDeleted\":1,\"userVersion\":0,\"userPersonality\":null,\"fans\":0}', 'EFFECTIVE');

-- ----------------------------
-- Table structure for topic_for_article
-- ----------------------------
DROP TABLE IF EXISTS `topic_for_article`;
CREATE TABLE `topic_for_article`  (
  `topic_id` int NOT NULL AUTO_INCREMENT COMMENT '主键，主题id',
  `topic_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主题名称',
  `created_id` bigint NOT NULL COMMENT '创建者id',
  `created_time` datetime(6) NOT NULL COMMENT '创建时间',
  `topic_status` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE' COMMENT '主题状态（ENABLE/DISABLE）',
  `topic_color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主题标签颜色#FF0000十六进制',
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '公告文章主题' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic_for_article
-- ----------------------------
INSERT INTO `topic_for_article` VALUES (1, '测试', 1, '2023-12-20 02:50:42.000000', 'ENABLE', '#ECF5FF');
INSERT INTO `topic_for_article` VALUES (2, '行政管理', 1, '2024-03-13 18:16:44.764000', 'ENABLE', '#FFD5D5');
INSERT INTO `topic_for_article` VALUES (3, '校园活动', 1, '2024-03-13 18:17:48.977000', 'ENABLE', '#CFBFFC');
INSERT INTO `topic_for_article` VALUES (4, '学术', 1, '2024-03-13 18:18:36.912000', 'ENABLE', '#CC95EB');
INSERT INTO `topic_for_article` VALUES (5, '招生', 1, '2024-03-13 18:19:01.236000', 'ENABLE', '#CFC2C8');

-- ----------------------------
-- Table structure for topic_for_feedback
-- ----------------------------
DROP TABLE IF EXISTS `topic_for_feedback`;
CREATE TABLE `topic_for_feedback`  (
  `topic_id` int NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `topic_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '类型名',
  `user_id` int NULL DEFAULT NULL COMMENT '创建者id',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `topic_color` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '类型颜色',
  `topic_status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT 'ENABLE' COMMENT '主题状态（ENABLE/DISABLE）',
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic_for_feedback
-- ----------------------------
INSERT INTO `topic_for_feedback` VALUES (1, '测试', 1, '2024-03-18 20:10:18', '#FFC7C7', 'ENABLE');
INSERT INTO `topic_for_feedback` VALUES (2, '技术问题反馈', 1, '2024-03-20 23:29:35', '#E7C6C6', 'ENABLE');
INSERT INTO `topic_for_feedback` VALUES (3, '投诉与举报', 1, '2024-03-20 23:32:18', '#CFFFD4', 'ENABLE');

-- ----------------------------
-- Table structure for topic_for_job
-- ----------------------------
DROP TABLE IF EXISTS `topic_for_job`;
CREATE TABLE `topic_for_job`  (
  `topic_id` int NOT NULL AUTO_INCREMENT COMMENT '招聘主题',
  `topic_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主题信息',
  `topic_color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主题标签颜色#FF0000十六进制',
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '招聘主题' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic_for_job
-- ----------------------------
INSERT INTO `topic_for_job` VALUES (1, '长期', '#FDD666');
INSERT INTO `topic_for_job` VALUES (2, '短期', '#8FDEB5');
INSERT INTO `topic_for_job` VALUES (3, '兼职', '#ECF5FF');

-- ----------------------------
-- Table structure for topic_for_post
-- ----------------------------
DROP TABLE IF EXISTS `topic_for_post`;
CREATE TABLE `topic_for_post`  (
  `topic_id` int NOT NULL AUTO_INCREMENT COMMENT '主键，主题id',
  `topic_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主题名称',
  `created_id` bigint NOT NULL COMMENT '发布者id',
  `created_time` datetime(6) NOT NULL COMMENT '发布时间',
  `updated_id` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `updated_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `topic_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子状态ENABLE/DISABLE',
  `topic_color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主题颜色',
  PRIMARY KEY (`topic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子主题' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic_for_post
-- ----------------------------
INSERT INTO `topic_for_post` VALUES (1, '测试', 1, '2023-12-17 01:26:03.000000', 1, '2024-02-03 18:59:58.250000', 'ENABLE', '#FFFABD');
INSERT INTO `topic_for_post` VALUES (2, '失物招领', 1, '2023-12-17 01:26:03.000000', NULL, NULL, 'ENABLE', '#ACF5CF');
INSERT INTO `topic_for_post` VALUES (3, '寻物启事', 1, '2024-01-09 00:11:36.117000', NULL, NULL, 'ENABLE', '#FDD666');
INSERT INTO `topic_for_post` VALUES (4, '表白墙', 1, '2024-01-09 00:15:09.480000', NULL, NULL, 'ENABLE', '#FFD3BD');
INSERT INTO `topic_for_post` VALUES (5, '生活趣事', 1, '2024-01-09 00:16:41.275000', 1, '2024-02-20 11:45:45.509000', 'ENABLE', '#ECF5FF');
INSERT INTO `topic_for_post` VALUES (6, '考研交流', 1, '2024-01-09 00:17:22.665000', 1, '2024-02-03 18:59:21.172000', 'ENABLE', '#DEBAFA');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_head` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_name` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `nick_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `user_sex` int NULL DEFAULT NULL COMMENT '性别（0女/1男）',
  `user_age` int NULL DEFAULT NULL COMMENT '年龄',
  `user_phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话',
  `user_address` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '住址',
  `user_personality` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `user_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '注册时间',
  `user_role` int NOT NULL DEFAULT 0 COMMENT '角色（0用户/1管理员）',
  `user_status` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT 'ENABLE' COMMENT '状态，查看是否被禁用违规用户(ENABLE/DISABLE)',
  `user_deleted` int NOT NULL DEFAULT 1 COMMENT '逻辑删除（1存在/0删除）',
  `user_version` int NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `fans` int NULL DEFAULT 0 COMMENT '粉丝',
  PRIMARY KEY (`id`, `user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'static/sys_img/adminHead/head.jpg', 'root', '管理员', 'root', 1, 18, '16616649102', '广东省佛山市', NULL, '2024-02-14 15:26:15.410000', 1, 'ENABLE', 1, 0, 1);
INSERT INTO `user` VALUES (2, 'static/sys_img/adminHead/2023.jpg', 'zhangsan', '小宜', 'zhangsan', 1, 20, '16616649103', '广西省桂林市', '123随便', '2024-02-14 15:26:18.207000', 2, 'ENABLE', 1, 0, 1);
INSERT INTO `user` VALUES (3, NULL, '123123123', '123123123', '123123123', NULL, NULL, '12341234123', NULL, NULL, '2024-02-14 16:40:41.175000', 0, 'ENABLE', 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
