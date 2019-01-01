ALTER TABLE `tb_user`
ADD COLUMN wx_notify_flag tinyint(2) not null default 0 comment '微信通知，0不通知，1通知',
ADD COLUMN voice_notify_flag tinyint(2) NOT NULL default 1 comment '电语音通知，0不通知，1通知';
