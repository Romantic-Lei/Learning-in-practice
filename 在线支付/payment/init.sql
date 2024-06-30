/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : springcloud

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2024-06-30 13:32:17
*/

SET
FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_order_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_info`;
CREATE TABLE `t_order_info`
(
    `id`           bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '' 订单id '',
    `title`        varchar(256) DEFAULT NULL COMMENT '' 订单标题 '',
    `order_no`     varchar(50)  DEFAULT NULL COMMENT '' 商户订单编号 '',
    `user_id`      bigint(20) DEFAULT NULL COMMENT '' 用户id '',
    `product_id`   bigint(20) DEFAULT NULL COMMENT '' 支付产品id '',
    `payment_type` varchar(20)  DEFAULT NULL,
    `total_fee`    int(11) DEFAULT NULL COMMENT '' 订单金额(分)'',
    `code_url`     varchar(50)  DEFAULT NULL COMMENT '' 订单二维码连接 '',
    `order_status` varchar(10)  DEFAULT NULL COMMENT '' 订单状态 '',
    `create_time`  datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '' 创建时间 '',
    `update_time`  datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '' 更新时间 '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `t_payment_info`;
CREATE TABLE `t_payment_info`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '' 支付记录id '',
    `order_no`         varchar(50)  DEFAULT NULL COMMENT '' 商户订单编号 '',
    `transaction_id`   varchar(50)  DEFAULT NULL COMMENT '' 支付系统交易编号 '',
    `payment_type`     varchar(20)  DEFAULT NULL COMMENT '' 支付类型 '',
    `trade_type`       varchar(20)  DEFAULT NULL COMMENT '' 交易类型 '',
    `trade_state`      varchar(50)  DEFAULT NULL COMMENT '' 交易状态 '',
    `trade_state_desc` varchar(256) DEFAULT NULL COMMENT '' 交易状态描述 '',
    `total`            int(11) DEFAULT NULL COMMENT '' 订单总金额(分)'',
    `payer_total`      int(11) DEFAULT NULL COMMENT '' 支付金额(分)'',
    `content`          text COMMENT '' 通知参数 '',
    `create_time`      datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '' 创建时间 '',
    `update_time`      datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '' 更新时间 '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '' 商品id '',
    `title`       varchar(20) DEFAULT NULL COMMENT '' 商品名称 '',
    `price`       int(11) DEFAULT NULL COMMENT '' 价格（分）'',
    `create_time` datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '' 创建时间 '',
    `update_time` datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '' 更新时间 '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `t_refund_info`;
CREATE TABLE `t_refund_info`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '' 退款单id '',
    `order_no`       varchar(50) DEFAULT NULL COMMENT '' 商户订单编号 '',
    `refund_no`      varchar(50) DEFAULT NULL COMMENT '' 商户退款单编号 '',
    `refund_id`      varchar(50) DEFAULT NULL COMMENT '' 支付系统退款单号 '',
    `total_fee`      int(11) DEFAULT NULL COMMENT '' 原订单金额(分)'',
    `refund`         int(11) DEFAULT NULL COMMENT '' 退款金额(分)'',
    `reason`         varchar(50) DEFAULT NULL COMMENT '' 退款原因 '',
    `refund_status`  varchar(50) DEFAULT NULL COMMENT '' 退款状态 '',
    `content_return` text COMMENT '' 申请退款返回参数 '',
    `content_notify` text COMMENT '' 退款结果通知参数 '',
    `create_time`    datetime    DEFAULT CURRENT_TIMESTAMP COMMENT '' 创建时间 '',
    `update_time`    datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '' 更新时间 '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;
