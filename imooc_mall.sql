/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : imooc_mall

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 07/12/2022 22:55:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for imooc_mall_cart
-- ----------------------------
DROP TABLE IF EXISTS `imooc_mall_cart`;
CREATE TABLE `imooc_mall_cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `product_id` int(0) NOT NULL COMMENT '商品id',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `quantity` int(0) NOT NULL COMMENT '商品数量',
  `selected` int(0) NOT NULL COMMENT '是否已勾选；0代表未勾选，1代表已勾选',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of imooc_mall_cart
-- ----------------------------

-- ----------------------------
-- Table structure for imooc_mall_category
-- ----------------------------
DROP TABLE IF EXISTS `imooc_mall_category`;
CREATE TABLE `imooc_mall_category`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主题',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类目录名称',
  `type` int(0) NOT NULL COMMENT '分类目录级别,例如1代表一级，2代表二级，3代表三级',
  `parent_id` int(0) NOT NULL COMMENT '父id，也就是上一级目录的id，如果是一级目录，那么父id为0',
  `order_num` int(0) NOT NULL COMMENT '目录展示时的排序',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of imooc_mall_category
-- ----------------------------
INSERT INTO `imooc_mall_category` VALUES (3, '新鲜水果', 1, 0, 1, '2022-11-09 01:30:00', '2022-11-09 10:41:45');
INSERT INTO `imooc_mall_category` VALUES (4, '橘子橙子', 2, 3, 1, '2022-11-09 01:30:00', '2022-11-09 10:41:54');
INSERT INTO `imooc_mall_category` VALUES (5, '海鲜水产', 1, 0, 2, '2022-11-09 01:30:00', '2022-11-09 10:30:00');
INSERT INTO `imooc_mall_category` VALUES (6, '精选肉类', 1, 0, 3, '2022-11-09 01:30:00', '2022-11-09 10:30:00');
INSERT INTO `imooc_mall_category` VALUES (7, '螃蟹', 2, 5, 1, '2022-11-09 01:30:00', '2022-11-09 10:30:00');
INSERT INTO `imooc_mall_category` VALUES (8, '鱼类', 2, 5, 1, '2022-11-09 01:30:00', '2022-11-09 10:30:00');
INSERT INTO `imooc_mall_category` VALUES (9, '冷饮食品', 1, 0, 4, '2022-11-09 01:30:00', '2022-11-09 10:30:00');
INSERT INTO `imooc_mall_category` VALUES (10, '蔬菜蛋品', 1, 0, 5, '2022-11-07 03:30:00', '2022-11-07 12:30:00');
INSERT INTO `imooc_mall_category` VALUES (11, '草莓', 2, 3, 2, '2022-11-07 03:30:00', '2022-11-07 12:30:00');
INSERT INTO `imooc_mall_category` VALUES (12, '奇异果', 2, 3, 3, '2022-11-08 05:25:00', '2022-11-08 16:35:00');
INSERT INTO `imooc_mall_category` VALUES (13, '海参', 2, 5, 3, '2022-11-08 05:25:00', '2022-11-08 16:35:00');
INSERT INTO `imooc_mall_category` VALUES (14, '车厘子', 2, 3, 4, '2022-11-08 05:25:00', '2022-11-08 16:35:00');
INSERT INTO `imooc_mall_category` VALUES (15, '火锅食材', 2, 27, 5, '2022-11-08 05:25:00', '2022-11-08 16:35:00');
INSERT INTO `imooc_mall_category` VALUES (16, '牛羊肉', 2, 6, 1, '2022-11-08 05:25:00', '2022-11-08 16:35:00');
INSERT INTO `imooc_mall_category` VALUES (17, '冰淇淋', 2, 9, 1, '2022-11-08 05:25:00', '2022-11-08 16:35:00');
INSERT INTO `imooc_mall_category` VALUES (18, '蔬菜综合', 2, 10, 1, '2022-11-08 05:25:00', '2022-11-08 16:35:00');
INSERT INTO `imooc_mall_category` VALUES (19, '果冻橙', 3, 4, 1, '2022-11-09 02:25:00', '2022-11-09 09:35:00');
INSERT INTO `imooc_mall_category` VALUES (27, '美味菌菇', 1, 0, 7, '2022-11-09 02:25:00', '2022-11-09 09:35:00');
INSERT INTO `imooc_mall_category` VALUES (28, '其他水果', 2, 3, 4, '2022-11-09 02:25:00', '2022-11-09 09:35:00');
INSERT INTO `imooc_mall_category` VALUES (29, '测试类别1', 2, 6, 10, '2022-12-07 22:17:31', '2022-12-07 22:17:31');

-- ----------------------------
-- Table structure for imooc_mall_order
-- ----------------------------
DROP TABLE IF EXISTS `imooc_mall_order`;
CREATE TABLE `imooc_mall_order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号(非主键id)',
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `total_price` int(0) NOT NULL COMMENT '订单总价格',
  `receiver_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人姓名快照',
  `receiver_mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人手机号快照',
  `receiver_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人地址快照',
  `order_status` int(0) NOT NULL COMMENT '订单状态:0用户已取消，10未付款（初始状态），20已付款，40交易完成',
  `postage` int(0) NULL DEFAULT NULL COMMENT '运费，默认为0',
  `payment_type` int(0) NOT NULL COMMENT '支付类型，1-在线支付',
  `delivery_time` timestamp(0) NULL DEFAULT NULL COMMENT '发货时间',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `end_time` timestamp(0) NULL DEFAULT NULL COMMENT '交易完成时间',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of imooc_mall_order
-- ----------------------------

-- ----------------------------
-- Table structure for imooc_mall_order_item
-- ----------------------------
DROP TABLE IF EXISTS `imooc_mall_order_item`;
CREATE TABLE `imooc_mall_order_item`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '归属订单id',
  `product_id` int(0) NOT NULL COMMENT '商品id',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `product_img` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品图片',
  `unit_price` int(0) NOT NULL COMMENT '单价（下单的快照）',
  `quantity` int(0) NOT NULL COMMENT '商品数量',
  `total_price` int(0) NOT NULL COMMENT '商品总价',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of imooc_mall_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for imooc_mall_product
-- ----------------------------
DROP TABLE IF EXISTS `imooc_mall_product`;
CREATE TABLE `imooc_mall_product`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '商品主题id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品图片，相对路径地址',
  `detail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品详情',
  `category_id` int(0) NOT NULL COMMENT '分类id',
  `price` int(0) NOT NULL COMMENT '价格，单位-分',
  `stock` int(0) NOT NULL COMMENT '库存数量',
  `status` int(0) NOT NULL COMMENT '商品上架状态：0-下架，1-上架',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of imooc_mall_product
-- ----------------------------

-- ----------------------------
-- Table structure for imooc_mall_user
-- ----------------------------
DROP TABLE IF EXISTS `imooc_mall_user`;
CREATE TABLE `imooc_mall_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码，MD5加密',
  `personalized_signature` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '个性签名',
  `role` int(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT '角色，1-普通用户，2-管理员',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of imooc_mall_user
-- ----------------------------
INSERT INTO `imooc_mall_user` VALUES (1, '1', '1', '3', 1, '2022-11-14 11:09:07', '2022-11-15 11:05:11');
INSERT INTO `imooc_mall_user` VALUES (2, 'xiaomu', 'asdwjkli123', '更新了喔的签名', 2, '2022-11-13 11:04:24', '2022-11-13 12:04:24');
INSERT INTO `imooc_mall_user` VALUES (3, '你好', 'asdwjkli123', '', 1, '2022-11-13 11:05:24', '2022-11-16 11:12:11');
INSERT INTO `imooc_mall_user` VALUES (4, 'xiaoliu', '12345678', ' ', 1, '2022-11-23 11:06:17', '2022-11-23 11:31:35');
INSERT INTO `imooc_mall_user` VALUES (5, 'xiaowang', 'B1jE2T3lRJcFWRW3NhzDdQ==', ' ', 1, '2022-11-23 11:31:02', '2022-11-23 11:31:41');
INSERT INTO `imooc_mall_user` VALUES (6, 'xiaohong', 'B1jE2T3lRJcFWRW3NhzDdQ==', '', 1, '2022-11-23 11:34:13', '2022-11-23 11:34:13');
INSERT INTO `imooc_mall_user` VALUES (7, 'xiaozi', 'B1jE2T3lRJcFWRW3NhzDdQ==', '', 1, '2022-11-23 11:35:03', '2022-11-23 11:35:03');
INSERT INTO `imooc_mall_user` VALUES (8, 'qqq', '1123424323', '', 1, '2022-12-07 20:22:21', '2022-12-07 20:22:21');
INSERT INTO `imooc_mall_user` VALUES (11, 'test', 'B1jE2T3lRJcFWRW3NhzDdQ==', 'test', 2, '2022-12-07 20:31:35', '2022-12-07 21:08:32');
INSERT INTO `imooc_mall_user` VALUES (12, 'test1', 'B1jE2T3lRJcFWRW3NhzDdQ==', 'test', 2, '2022-12-07 20:57:09', '2022-12-07 22:13:16');
INSERT INTO `imooc_mall_user` VALUES (13, 'test2', 'B1jE2T3lRJcFWRW3NhzDdQ==', 'test', 1, '2022-12-07 21:05:16', '2022-12-07 21:05:47');

SET FOREIGN_KEY_CHECKS = 1;
