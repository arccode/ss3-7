## 简介

ss3-*一共12个demo, 每个demo介绍springsecurity的部分功能, 对springsecurity进行迭代展示.

### ss3-7

ss3-1 ~ ss3-6使用基于session的方式进行认证, 从ss3-7开始将构建restful的无状态带凭证认证.

### ss3-6

该项目在ss3-5的基础上进行迭代, 完成接口标准化

1. 配置springMVC, 确保访问接口返回的是json而不是ModelAndView.
2. servlet拦截根目录配置为/api/*
3. 更新spring版本至4.1.2, springsecurity版本至3.2.5
4. 扩展user类, 实现userDetails, CredentialsContainer接口
5. 添加退出链接

待完成:
1. 接口hateos标准化

#### sql

```
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `accountNonExpired` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户未过期',
  `credentialsNonExpired` tinyint(1) NOT NULL DEFAULT '1' COMMENT '证书未过期',
  `accountNonLocked` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账户未锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'user', 'user', '0', '1', '1', '1');
COMMIT;
```

### ss3-5

1. 开发环境, 暂时不使用缓存, 取消ehcache
2. 采用rbac模型, springsecurity3默认没有使用rbac模型, 所以新建数据库ss3-5并创建相关的表
3. dao层使用mybatis

#### sql

```
-- ----------------------------
--  Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键id',
  `resource_name` varchar(255) NOT NULL DEFAULT '' COMMENT '资源名称, ',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '权限对应的url地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
--  Records of `resource`
-- ----------------------------
BEGIN;
INSERT INTO `resource` VALUES ('1', '登陆', '/login'), ('2', '注册', '/sign'), ('3', '首页', '/index'), ('4', '登陆页面', '/login-page'), ('5', 'Dashboard', '/dashboard');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', 'ROLE_USER'), ('2', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
--  Table structure for `role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-资源表';

-- ----------------------------
--  Records of `role_resource`
-- ----------------------------
BEGIN;
INSERT INTO `role_resource` VALUES ('1', '1', '1'), ('2', '2', '2'), ('3', '2', '1'), ('4', '2', '3'), ('5', '2', '4'), ('6', '2', '5');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'user', 'user');
COMMIT;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户-角色表';

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('1', '1', '2');
COMMIT;
```



### ss3-4

1. 使用缓存, 缓存userDetails
2. 添加退出链接及页面

#### 问题

1. 加了缓存后, 使用正确的用户名密码无法跳转到首页

默认情况下，在认证成功后ProviderManager将清除返回的Authentication中的凭证信息，如密码。所以如果你在无状态的应用中将返回的Authentication信息缓存起来了，那么以后你再利用缓存的信息去认证将会失败，因为它已经不存在密码这样的凭证信息了。所以在使用缓存的时候你应该考虑到这个问题。一种解决办法是设置ProviderManager的erase-credentials属性为false，或者想办法在缓存时将凭证信息一起缓存。

### ss3-3

1. 使用mysql持久化用户
2. 用户密码加密

#### sql

```
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `authorities` VALUES ('1', 'user', 'ROLE_ADMIN'), ('3', 'user', 'ROLE_TEST'), ('2', 'user', 'ROLE_USER');
COMMIT;

DROP TABLE IF EXISTS `group_authorities`;
CREATE TABLE `group_authorities` (
  `group_id` bigint(20) NOT NULL,
  `authority` varchar(50) NOT NULL,
  KEY `fk_group_authorities_group` (`group_id`),
  CONSTRAINT `fk_group_authorities_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `group_members`;
CREATE TABLE `group_members` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_members_group` (`group_id`),
  CONSTRAINT `fk_group_members_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(50) NOT NULL DEFAULT '',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### ss3-2

1. 自定义登陆页面
2. 自定义登陆成功的处理方法, 并记录登陆日志
3. 自定义登陆失败的处理方法, 并记录登陆日志

### ss3-1

展示springsecurity3最基础配置, 内置登陆界面, 内置用户.