create table t_pushed_message_record_${appId}
(
id                   int unsigned not null auto_increment comment '主键,自增',
message_type         char(1) not null default '1' comment '1:验证码',
message_body         varchar(255) not null default '' comment '消息内容',
receiver_account     varchar(64) not null default '' comment '接收消息的账号',
receiver_id          int not null default 1 comment '接收消息的用户ID',
create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
update_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
operator_id          int not null default 0 comment '操作人ID',
operator_name        varchar(64) not null default '' comment '操作人姓名',
is_deleted           char(1) not null default '0' comment '逻辑删除,0否1是',
primary key (id)
);

alter table t_pushed_message_record_${appId} comment '消息推送记录';