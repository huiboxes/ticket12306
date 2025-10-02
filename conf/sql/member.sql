
CREATE DATABASE IF NOT EXISTS `ticket_member`
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;
#
# show databases;
use ticket_member;

drop table if exists member;
CREATE TABLE `member` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `mobile` varchar(20) NOT NULL COMMENT '手机号',
    `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（1-启用，0-禁用）',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator` varchar(64) DEFAULT NULL COMMENT '创建人',
    `modifier` varchar(64) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    KEY `idx_mobile` (`mobile`) COMMENT '手机号索引',
    KEY `idx_status` (`status`) COMMENT '状态索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员信息表';


select * from member;
