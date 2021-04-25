# Week 6作业

作业：基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）。

```mysql
/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50730

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 25/04/2021 17:10:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_address
-- ----------------------------
DROP TABLE IF EXISTS `order_address`;
CREATE TABLE `order_address`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `order_id` bigint(30) NOT NULL COMMENT '订单id',
  `receiver` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人',
  `receive_mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人电话',
  `receive_province` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货省',
  `receive_city` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货市',
  `receive_area` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货区',
  `receive_street` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货街道',
  `receive_address_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货详细信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` bigint(30) NOT NULL COMMENT '订单id',
  `product_id` bigint(30) NOT NULL COMMENT '产品id',
  `product_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品总金额',
  `product_num` int(10) NULL DEFAULT NULL COMMENT '产品数量',
  `product_discount_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品优惠金额',
  `product_pay_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '实付金额',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0:不删除 1：删除） 默认0',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_main
-- ----------------------------
DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(30) NOT NULL COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户Id',
  `order_total_price` decimal(10, 2) NOT NULL COMMENT '订单总金额',
  `order_pay_price` decimal(10, 2) NOT NULL COMMENT '应付金额',
  `order_discount_price` decimal(10, 2) NOT NULL COMMENT '优惠金额',
  `order_freight_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `order_status` tinyint(10) NOT NULL COMMENT '订单状态(0:新订单 1:待付款 2:待发货 3:待收货 4:待评价 5:已完成 6:已取消 7:售后)',
  `order_after_sales_status` tinyint(10) NULL DEFAULT NULL COMMENT '售后状态：(0:待退货退款 1:已退货 2:已退货退款 3:待换货 4:已换货 5:待退款 6:已退款 )',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `receiving_time` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '评价时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0:不删除 1：删除） 默认0',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_order_id`(`order_id`) USING BTREE COMMENT '订单号索引',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_main
-- ----------------------------
DROP TABLE IF EXISTS `product_main`;
CREATE TABLE `product_main`  (
  `id` bigint(20) NOT NULL COMMENT 'id主键',
  `product_id` bigint(20) NOT NULL COMMENT '产品id',
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品名称',
  `product_model` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品型号',
  `product_category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品分类',
  `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '产品价格',
  `product_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品描述',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0:不删除 1：删除） 默认0',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_product_id`(`product_id`) USING BTREE COMMENT '产品id索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for user_main
-- ----------------------------
DROP TABLE IF EXISTS `user_main`;
CREATE TABLE `user_main`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名（长度在1到50个字符串之间，不为空，允许重复 ',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码, 长度255, 不为空 ',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户的手机号码, 长度是11,手机号码不为空且唯一 ',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别 0:女; 1:男 ',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户的邮箱, 长度是50, 唯一, 允许为空',
  `is_deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '逻辑删除（0:不删除 1：删除） 默认0',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE COMMENT '用户名索引',
  UNIQUE INDEX `idx_mobile`(`mobile`) USING BTREE COMMENT '手机号索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```

