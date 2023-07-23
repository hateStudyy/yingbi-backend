# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists yubi;

-- 切换库
use yubi;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_AccountId (userAccount)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 图标表
create table if not exists chart
(
    id         bigint auto_increment comment 'id' primary key,
    userId     bigint                             null comment '用户id',
    `name`     varchar(129)                       null comment '图标名称',
    goal       text                               null comment '分析目标',
    chartDate  text                               null comment '图标数据',
    chartType  varchar(128)                       null comment '图标类型',
    genChart   text                               null comment '生成的图标数据',
    genResult  text                               null comment '生成的分析结论',
    status      varchar(128) not null default 'wait' comment 'wait,running,succeed,failed',
    execMessage text         null comment '执行信息',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '图表信息表' collate = utf8mb4_unicode_ci;
