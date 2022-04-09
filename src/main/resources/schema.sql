create database if not exists tooth;
use tooth;
SET FOREIGN_KEY_CHECKS=0;


DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
  KEY `FKq4eq273l04bpu4efj0jd0jb98` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联表';

-- 系统角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `level` int(255) DEFAULT NULL COMMENT '角色级别, 多种控制方式',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `data_scope` varchar(255) DEFAULT NULL COMMENT '数据权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  KEY `role_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='role';

-- 角色部门表
DROP TABLE IF EXISTS `sys_roles_depts`;
CREATE TABLE `sys_roles_depts` (
  `role_id` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE,
  KEY `FK7qg6itn5ajdoa9h9o78v9ksur` (`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='role-dept';

-- 部门表
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级部门',
  `sub_count` int(5) DEFAULT 0 COMMENT '子部门数目',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `dept_sort` int(5) DEFAULT 999 COMMENT '排序',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE,
  KEY `inx_pid` (`pid`),
  KEY `inx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门';

-- 菜单角色关联表
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`menu_id`,`role_id`) USING BTREE,
  KEY `FKcngg2qadojhi3a651a5adkvbq` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='role-menu';

-- 系统菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
  `sub_count` int(5) DEFAULT 0 COMMENT '子菜单数目',
  `type` int(11) DEFAULT NULL COMMENT '菜单类型',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `menu_sort` int(5) DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE KEY `uniq_title` (`title`),
  UNIQUE KEY `uniq_name` (`name`),
  KEY `inx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';



-- 系统用户表
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE if not exists `user_info` (
    `id` bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `dept_id` bigint(20) DEFAULT NULL COMMENT '部门名称',
    `username` varchar(255) not null unique COMMENT '用户名',
    `password` varchar(255) not null COMMENT 'password',
    `avatarUrl` varchar(100) COMMENT '头像地址',
    `avatar_name` varchar(255) DEFAULT NULL COMMENT '头像地址',
    `level` int not null default 1 COMMENT '用户等级',
    `gender` varchar(2) null default '男' COMMENT '性别',
    `phone` varchar(20) not null unique COMMENT '移动电话',
    `email` varchar(50) COMMENT '邮箱',
    `telephone` varchar(20) COMMENT '固定电话',
    `percentage` smallint COMMENT '百分比，0-100',
    `nick_name` varchar(20) not null default "医生" COMMENT '用户类型',
    `enable` boolean not null default false COMMENT '用户是否激活',
    `isAdmin` boolean not null default 0 COMMENT '是否是管理员',
    `pwdResetTime` datetime null COMMENT '密码修改时间',
    `createdTime` datetime null COMMENT '创建时间',
    `createdBy` varchar(40) null COMMENT '创建时间',
    `updatedTime` datetime null COMMENT '修改时间',
    `updatedBy` varchar(40) null COMMENT '修改人'
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- insert into user_info(id, `username`, `password`, avatarUrl, level, gender, phone, email, percentage, nick_name, `enable`, pwdResetTime)
--     values(1, 'user', 'chuanzhi', 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', 10, '男', '18515747736', 'xiaolongorigin@gmail.com', 10, '管理员', 1, now());


DROP TABLE if EXISTS `user_hospital_detail`;
create table user_hospital_detail (
     `id` bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
     `user_id` bigint not null,
     `username` varchar(40) not null unique,
     `hospital` varchar(50) ,
     `province` varchar(50) ,
     `city` varchar(50) COMMENT '城市',
     `low_city` varchar(50) COMMENT '区、县级别',
     `address_detail` varchar(200) COMMENT '详细地址',
     `createdBy` varchar(40) null COMMENT '创建人',
     `createdTime` datetime null COMMENT '创建时间',
     `updatedTime` datetime null COMMENT '修改时间',
     `updatedEr` varchar(40) null COMMENT '修改人'
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;
