DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL
);

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
);

DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
);

DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE oauth_approvals
(
  `userid` varchar(256), -- 登录的用户名
  `clientid` varchar(256), -- 客户端ID
  `scope` varchar(256), -- 申请的权限
  `status` varchar(10), -- 状态（Approve或Deny）
  `expiresat` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 过期时间
  `lastmodifiedat` TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 最终修改时间
);

DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`
(
  `code` varchar(256), -- 授权码(未加密)
  `authentication` blob -- AuthorizationRequestHolder.java对象序列化后的二进制数据
);