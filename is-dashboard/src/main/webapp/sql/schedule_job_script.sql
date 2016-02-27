/*
Navicat MySQL Data Transfer

Source Server         : 172.16.5.114
Source Server Version : 50542
Source Host           : 172.16.5.114:3306
Source Database       : jiea_ischedule

Target Server Type    : MYSQL
Target Server Version : 50542
File Encoding         : 65001

Date: 2016-01-07 10:44:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `qrtz_schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_schedule_job`;
CREATE TABLE `qrtz_schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `job_name` varchar(255) NOT NULL COMMENT '任务名称',
  `job_group` varchar(255) NOT NULL COMMENT '任务分组',
  `job_status` varchar(255) NOT NULL DEFAULT '0' COMMENT '0：终止状态，1：开启状态',
  `cron_expression` varchar(255) NOT NULL COMMENT 'corn表达式',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `bean_class` varchar(255) DEFAULT NULL COMMENT '反射类，不建议使用',
  `is_concurrent` varchar(255) DEFAULT '1' COMMENT '是否同步1:是，0否',
  `spring_id` varchar(255) NOT NULL COMMENT 'spring bean id',
  `method_name` varchar(255) NOT NULL COMMENT '执行方法名(无参）',
  `ext_params` varchar(255) DEFAULT NULL COMMENT '附加参数,需要符合JSON格式',
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_schedule_job
-- ----------------------------
INSERT INTO `qrtz_schedule_job` VALUES ('11', '2015-12-29 17:30:27', '2016-01-05 16:30:18', 'demoJob', 'G2', '0', '0/10 * * * * ?', '测试任务', '', '1', 'demojob', 'printName', '{\"id\":12,\"name\":\"kevin\"}');
INSERT INTO `qrtz_schedule_job` VALUES ('12', '2015-12-30 17:30:27', '2016-01-05 14:52:35', 'remindRepaymentPushTask', 'G1', '1', '0/15 * * * * ?', '三天还款提醒', null, '1', 'remindRepaymentPushTask', 'executePush', '{\"date\":3}');
INSERT INTO `qrtz_schedule_job` VALUES ('14', '2016-01-04 15:45:17', '2016-01-07 10:43:12', 'timingDrawTask', 'G3', '1', '0 0/2 * * * ?', '定时划扣', null, '1', 'timingDrawTask', 'executeDrawCharge', '');
INSERT INTO `qrtz_schedule_job` VALUES ('15', '2016-01-07 10:43:31', '2016-01-07 10:43:24', 'abandonLoanJob', 'G4', '1', '0/15 * * * * ?', '用户放弃借款调度', null, '1', 'abandonLoanJob', 'abandonLoan', '{\"3\":20,\"4\":3}');
