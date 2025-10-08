

drop table if exists passenger;

create table passenger (
  `id` bigint(20) not null auto_increment comment '主键ID',
  `member_id` bigint(20) not null comment '会员ID',
  `name` varchar(20) not null comment '姓名',
  `id_card` varchar(18) not null comment '证件号码',
  `type` tinyint(4) not null comment '证件类型|枚举[PassengerTypeEnum]',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(64) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='旅客表';

select * from passenger;
select * from member where id = '1975115796904546304'