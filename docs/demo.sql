DROP DATABASE IF EXISTS demo;
CREATE DATABASE demo;
USE demo;

CREATE TABLE fail_order
(`id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_id` varchar(255) NOT NULL COMMENT '订单Id',
    `source_id` int NOT NULL COMMENT '来源Id',
    `scene_name` varchar(255) NOT NULL COMMENT '场景名称',
`executor_name` varchar(255) NOT NULL COMMENT '执行器名称',
`args` varchar(255) NULL COMMENT '参数信息',
`create_dt`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_dt`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
PRIMARY KEY (`id`)
);

-- 手机号表
CREATE TABLE `phone_number` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `phone_number` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='手机号表'
