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

 Date: 17/02/2024 18:34:20
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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '公告文章' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '测试11', '三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1三生三世十里桃花1', 1, '2023-11-02 16:55:19.000000', 1, '2023-12-02 17:52:07.472000', 10, 1);
INSERT INTO `article` VALUES (2, '测试2', '三生三世十里桃花222222222', 1, '2023-11-17 16:55:32.000000', 1, '2023-12-07 16:10:18.288000', 0, 1);
INSERT INTO `article` VALUES (3, '测试33', '三生三世十里桃花33333345', 1, '2023-11-23 16:55:40.000000', NULL, NULL, 0, 1);
INSERT INTO `article` VALUES (7, '水水水水水水水水', '少时诵诗书', 1, '2023-11-24 16:55:46.000000', NULL, NULL, 0, 1);
INSERT INTO `article` VALUES (8, '生生世世', '生生世世', 1, '2023-11-26 16:55:53.000000', NULL, NULL, 0, 1);
INSERT INTO `article` VALUES (9, '测试4', '这是一段内容，\\n可以在这里添加多行文本。\\n\\n  这是新的一行，开头空两格。\\n  这也是新的一行，同样开头空两格。\\n\\n再来一段新的内容。这是一段内容，\r\n可以在这里添加多行文本。\r\n\r\n  这是新的一行，开头空两格。\r\n  这也是新的一行，同样开头空两格。\r\n\r\n再来一段新的内容。', 1, '2023-11-30 16:55:59.000000', NULL, NULL, 0, 1);
INSERT INTO `article` VALUES (11, '重构', '123132132', 1, '2023-12-26 00:14:32.341000', NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `post_id` int(11) NOT NULL COMMENT '帖子id',
  `user_id` bigint(20) NOT NULL COMMENT '评论用户id',
  `comment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '评论内容',
  `parent_comment_id` int(11) NULL DEFAULT NULL COMMENT '父评论id指向comment_id',
  `created_at` datetime(6) NULL DEFAULT NULL COMMENT '回复时间',
  `status_for_user` varchar(6) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'Unread' COMMENT '评论状态已读/未读（Unread/Read）',
  `status_for_compliance` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'COMPLIANCE' COMMENT '评论状态是否合规（COMPLIANCE/IRREGULARITY）',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '评论' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (24, 1, 2, '测试', NULL, '2024-02-08 18:13:10.000000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (25, 1, 2, '测试1', 24, '2024-02-08 18:13:10.000000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (26, 1, 2, '测试2', 25, '2024-02-08 18:13:10.000000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (27, 1, 2, '测试3', 26, '2024-02-10 18:13:22.695000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (28, 1, 2, '测试3', 26, '2024-02-10 18:13:30.129000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (29, 1, 2, '测试4', 27, '2024-02-10 18:13:22.695000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (30, 1, 2, '测试4', 27, '2024-02-10 18:13:22.695000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (31, 1, 2, '测试5', 29, '2024-02-10 18:45:35.158000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (32, 1, 2, '测试5', 29, '2024-02-10 18:45:35.158000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (33, 1, 2, '测试6', 31, '2024-02-10 18:45:35.158000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (34, 1, 2, '测试6', 31, '2024-02-10 18:45:35.158000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (35, 1, 2, 'hhhhhhhhhhhhh', 24, '2024-02-11 16:02:38.180000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (36, 1, 2, '哈哈哈很好', NULL, '2024-02-11 16:15:47.051000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (37, 1, 2, '牛的', 36, '2024-02-11 16:15:54.431000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (38, 1, 2, '牛的牛', 37, '2024-02-11 16:15:59.188000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (39, 1, 2, '牛的牛的牛', 38, '2024-02-11 16:16:11.442000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (40, 1, 2, '牛的牛的牛', 38, '2024-02-11 16:16:18.466000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (41, 1, 2, 'veryGood', NULL, '2024-02-11 19:43:11.692000', 'Unread', 'COMPLIANCE');
INSERT INTO `comments` VALUES (42, 1, 2, '123', 41, '2024-02-11 19:43:17.181000', 'Unread', 'COMPLIANCE');

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
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `post_id` int(11) NOT NULL COMMENT '帖子id',
  `dislike_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否踩Disliked/Undisliked',
  `timestamp` datetime(6) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '踩贴' ROW_FORMAT = Dynamic;

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
-- Table structure for file_for_post
-- ----------------------------
DROP TABLE IF EXISTS `file_for_post`;
CREATE TABLE `file_for_post`  (
  `file_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件id',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `created_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '帖子id',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子附件文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_for_post
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
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `follower_id` bigint(20) NOT NULL COMMENT '关注者id（本人）',
  `following_id` bigint(20) NOT NULL COMMENT '被关注者id',
  `follow_date` datetime(0) NULL DEFAULT NULL COMMENT '关注时间',
  `follow_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Follow' COMMENT '状态',
  PRIMARY KEY (`follower_id`, `following_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '关注表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES (2, 1, '2024-02-15 18:32:21', 'Unfollowed');
INSERT INTO `follow` VALUES (2, 2, '2024-02-15 18:32:19', 'Unfollowed');

-- ----------------------------
-- Table structure for likes_for_post
-- ----------------------------
DROP TABLE IF EXISTS `likes_for_post`;
CREATE TABLE `likes_for_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `post_id` int(11) NOT NULL COMMENT '帖子id',
  `like_status` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否点赞Liked/Unliked',
  `timestamp` datetime(6) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点赞贴' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of likes_for_post
-- ----------------------------
INSERT INTO `likes_for_post` VALUES (2, 1, 'Liked', '2024-02-06 17:56:54.810000');
INSERT INTO `likes_for_post` VALUES (2, 2, 'Liked', '2024-02-11 19:46:56.198000');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `post_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `topic_id` int(5) NOT NULL COMMENT '所属主题id',
  `post_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '帖子标题',
  `post_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '帖子内容',
  `user_id` bigint(20) NOT NULL COMMENT '发布用户id',
  `created_at` datetime(6) NOT NULL COMMENT '发布时间',
  `post_status` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'COMPLIANCE' COMMENT '是否合规（COMPLIANCE/IRREGULARITY）',
  `post_visibility` varchar(7) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'PUBLIC' COMMENT '是否可见（PUBLIC/PRIVATE）',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '阅读量',
  `like_count` int(11) NULL DEFAULT 0 COMMENT '点赞数量',
  `dislike_count` int(11) NULL DEFAULT 0 COMMENT '踩贴数量',
  `comment_count` int(11) NULL DEFAULT 0 COMMENT '评论数量',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '帖子' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (1, 1, '测试', '测试', 2, '2023-11-24 10:22:54.000000', 'COMPLIANCE', 'PUBLIC', 48, 1, 1, 19);
INSERT INTO `post` VALUES (2, 2, '失物手表', '测试啊2', 1, '2023-11-24 10:22:54.000000', 'COMPLIANCE', 'PUBLIC', 59, 1, 0, 0);
INSERT INTO `post` VALUES (3, 1, '测试提交', '<h1>查看提交</h1><p style=\"text-align: left;\">1231231231<img src=\"https://postfiles.oss-cn-guangzhou.aliyuncs.com/mmexport1639369490349.jpg\" alt=\"\" data-href=\"\" style=\"width: 30%;\"></p>', 2, '2024-02-16 14:51:58.222000', 'COMPLIANCE', 'PUBLIC', 4, 0, 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统图片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_images
-- ----------------------------
INSERT INTO `sys_images` VALUES (1, 'index', 'static/sys_img/725a25d6-d433-49fb-bf14-fa41a336693a-动漫女孩 水花 海浪 海洋之女 4k壁纸_彼岸图网.jpg', 1, '2023-08-21 14:24:16.000000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (3, '金克丝 泳池派对 4K壁纸_彼岸图网.jpg', 'static/sys_img/金克丝 泳池派对 4K壁纸_彼岸图网.jpg', 1, '2023-12-07 11:01:01.907000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (4, '咒术回战 五条悟4k壁纸_彼岸图网.jpg', 'static/sys_img/咒术回战 五条悟4k壁纸_彼岸图网.jpg', 1, '2023-12-07 11:05:38.008000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (5, '动漫美女 鲜花 帽子 唯美 好看 4K壁纸_彼岸图网.jpg', 'static/sys_img/动漫美女 鲜花 帽子 唯美 好看 4K壁纸_彼岸图网.jpg', 1, '2023-12-07 11:26:18.464000', 'VISIBLE', 1);
INSERT INTO `sys_images` VALUES (6, '宣纸国画 紫罗兰永恒花园 赛博color 4k壁纸_彼岸图网.jpg', 'static/sys_img/宣纸国画 紫罗兰永恒花园 赛博color 4k壁纸_彼岸图网.jpg', 1, '2023-12-26 00:18:55.407000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (16, '动漫女孩 水花 海浪 海洋之女 4k壁纸_彼岸图网.jpg', 'static/sys_img/07957ce3-b0ea-49e2-957e-c2b791befb5d-动漫女孩 水花 海浪 海洋之女 4k壁纸_彼岸图网.jpg', 1, '2024-02-02 21:30:56.749000', 'VISIBLE', 0);
INSERT INTO `sys_images` VALUES (19, '1703957554963.jpg', 'static/sys_img/65ecdf7b-bf71-479d-978e-cc8e38493b99-1703957554963.jpg', 1, '2024-02-03 16:44:21.323000', 'VISIBLE', 4);

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子主题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic_for_post
-- ----------------------------
INSERT INTO `topic_for_post` VALUES (1, '测试', 1, '2023-12-17 01:26:03.000000', 1, '2024-02-03 18:59:58.250000', 'ENABLE');
INSERT INTO `topic_for_post` VALUES (2, '失物招领', 1, '2023-12-17 01:26:03.000000', NULL, NULL, 'ENABLE');
INSERT INTO `topic_for_post` VALUES (3, '寻物启事', 1, '2024-01-09 00:11:36.117000', NULL, NULL, 'ENABLE');
INSERT INTO `topic_for_post` VALUES (4, '表白墙', 1, '2024-01-09 00:15:09.480000', NULL, NULL, 'ENABLE');
INSERT INTO `topic_for_post` VALUES (5, '生活趣事', 1, '2024-01-09 00:16:41.275000', 1, '2024-02-03 18:59:39.881000', 'ENABLE');
INSERT INTO `topic_for_post` VALUES (6, '考研交流', 1, '2024-01-09 00:17:22.665000', 1, '2024-02-03 18:59:21.172000', 'ENABLE');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_head` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_sex` int(11) NULL DEFAULT NULL COMMENT '性别（0女/1男）',
  `user_age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `user_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住址',
  `user_personality` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `user_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '注册时间',
  `user_role` int(11) NOT NULL DEFAULT 0 COMMENT '角色（0用户/1管理员）',
  `user_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'ENABLE' COMMENT '状态，查看是否被禁用违规用户(ENABLE/DISABLE)',
  `user_deleted` int(11) NOT NULL DEFAULT 1 COMMENT '逻辑删除（1存在/0删除）',
  `user_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `fans` int(11) NULL DEFAULT 0 COMMENT '粉丝',
  PRIMARY KEY (`id`, `user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'static/sys_img/adminHead/head.jpg', 'root', '管理员', 'root', 1, 18, '16616649102', '广东省佛山市高明区xx镇xx街道xx栋', NULL, '2024-02-14 15:26:15.410000', 1, 'ENABLE', 1, 3, 0);
INSERT INTO `user` VALUES (2, 'static/sys_img/adminHead/2023.jpg', 'zhangsan', '小宜', 'zhangsan', 1, 20, '16616649103', '广西省桂林市1', '123随便', '2024-02-14 15:26:18.207000', 0, 'ENABLE', 1, 3, 0);
INSERT INTO `user` VALUES (3, NULL, '123123123', '', '123123123', NULL, NULL, '12341234123', NULL, NULL, '2024-02-14 16:40:41.175000', 0, 'ENABLE', 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
