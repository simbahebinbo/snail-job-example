DROP DATABASE IF EXISTS demo;
CREATE DATABASE demo;
USE demo;

CREATE TABLE `demo`.`fail_order`  (
                                      `id` bigint NOT NULL COMMENT '自增主键Id',
                                      `orderId` bigint NOT NULL COMMENT '订单Id',
                                      `sourceId` int NOT NULL COMMENT '来源Id',
                                      `sceneName` varchar(255) NOT NULL COMMENT '场景名称',
    `executorName` varchar(255) NOT NULL COMMENT '执行器名称',
    `args` varchar(255) NULL COMMENT '参数信息',
    `create_dt`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_dt`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
    );