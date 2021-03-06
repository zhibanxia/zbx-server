drop table if exists tb_user;
create table tb_user(
id              		bigint(20)  	unsigned NOT NULL auto_increment 	comment '主键自增',
user_type   			tinyint(2)    	not null 				comment '用户类型：1.业主；2.回收人员；3.管理员',
wx_open_id     			varchar(128)  	not null				comment '微信openId',
wx_nick_name			varchar(128)  	not null				comment '微信昵称',
wx_logo          		varchar(256)  	not null				comment '微信头像',
wx_id  		       		varchar(128)   	default null			comment '微信id',
user_status				tinyint(2)		not null				comment '用户状态：1.正常；2.待激活；3.审核不通过，请重新提交；4.审核审核中；5.禁用；6.注销',
mobile_phone			varchar(16)		default null			comment '手机号码',
verify_logo				varchar(256) 	default null			comment '回收人员提交的审核头像',
verify_remark           varchar(256)    default null            comment '审核失败备注',
gmt_create      		datetime     DEFAULT CURRENT_TIMESTAMP		comment '创建时间',
gmt_modified    		datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP		comment '修改时间',
primary key (id),
unique key unidx_user (wx_open_id,user_type)
)ENGINE=InnoDB default charset=utf8 comment='用户表';

drop table if exists tb_user_address;
create table tb_user_address(
id              		bigint(20)  	unsigned NOT NULL auto_increment 	comment '主键自增',
user_id   				bigint(20)    	not null 				comment '用户id',
biz_type     			tinyint(2)  	not null				comment '地址类型：1.业主常用住址；2.回收人员住址；3.回收人员关注的小区信息',
complex_id              bigint(20)      default null			    comment '小区库id',
door_info               varchar(32)		default null			    comment '门牌号信息，小区库id+门牌号组成详细地址',
deleted					tinyint(1)		not null default 0		comment '删除状态：0否，1是',
gmt_create      		datetime        DEFAULT CURRENT_TIMESTAMP	comment '创建时间',
gmt_modified    		datetime        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP	comment '修改时间',
primary key (id),
key idx_user_addr (user_id,biz_type)
)ENGINE=InnoDB default charset=utf8 comment='用户地址表';

drop table if exists tb_recycle_request;
create table tb_recycle_request(
id              		bigint(20)  	unsigned NOT NULL auto_increment 	comment '主键自增',
create_user_id   		bigint(20)    	not null 				comment '发布用户id',
recycle_user_id     	bigint(20)  	default null			comment '回收人员id，确认回收后填充',
res_type				tinyint(2)		not null   				comment '回收资源类型：1.纸板；2.塑料瓶; 3.纸板和塑料瓶',
res_status				tinyint(2)		not null  				comment '回收资源状态：1.已发布；2.已确认待回收；3.已确认已回收',
deleted					tinyint(2)		not null default 0		comment '删除状态：0否，1是',
res_amount				tinyint(2)		not null  				comment '回收数量:1: 0-0.5kg,免费回收; 2: 0.5-1kg; 3: 1-3kg; 4: 3-5kg; 5: 5kg+',
res_images				varchar(1024)	default null 			comment '废品照片，多张，逗号分隔，最多3张',
take_garbage_flag		tinyint(1)		not null default 0		comment '是否帮忙带垃圾, 默认否',
free_take_flag			tinyint(1)		not null default 0		comment '是否免费回收，默认否',
res_note				varchar(512)	default null 			comment '备注说明',
door_serv_start_time	datetime		default null 			comment '上门时间段：起始',
door_serv_end_time		datetime		default null 			comment '上门时间段：截止',
complex_id              bigint(20)      default null			comment '小区库id',
door_info               varchar(32)		default null			comment '门牌号信息，小区库id+门牌号组成详细地址',
mobile_phone			varchar(16)		default null			comment '手机号码',
publish_time			datetime		default null 			comment '发布时间',
confirm_recycle_time		datetime		default null 			comment '确认回收时间',
complete_recycle_time	datetime		default null 			comment '完成回收时间',
gmt_create      		datetime        DEFAULT CURRENT_TIMESTAMP	comment '创建时间',
gmt_modified    		datetime        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP	comment '修改时间',
primary key (id),
key inx_create (create_user_id,res_status),
key inx_recycle (recycle_user_id,res_status),
key inx_status (res_status)
)ENGINE=InnoDB default charset=utf8 comment='回收请求';

alter table tb_recycle_request add column hsu_recomm_flag tinyint(1) not null default 0 comment '是否已经推荐过回收人员，0.没有，1.推荐过了' after complete_recycle_time;

-- 小区库
drop table if exists tb_complex_repo;
create table tb_complex_repo (
id              		bigint(20)  	unsigned NOT NULL auto_increment 	comment '主键自增',
province_id             varchar(32)  	not null				comment '省份或直辖市id',
city_id          		varchar(32)  	not null				comment '市id',
area_id  		       	varchar(32)   	not null				comment '区id',
subdistrict_id			varchar(32)		not null				comment '街道id',
addr_block              varchar(32)		not null				comment '区块，比街道小一级',
addr_detail				varchar(128)	not null			    comment '详细地址',
complex_name            varchar(32)	    not null			    comment '小区名称',
gmt_create      		datetime        DEFAULT CURRENT_TIMESTAMP	comment '创建时间',
gmt_modified    		datetime        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP	comment '修改时间',
primary key (id),
key inx_complex_name (complex_name),
key inx_area (province_id,city_id,area_id,subdistrict_id),
unique key unidx_complex (province_id,city_id,area_id,subdistrict_id,complex_name)
)ENGINE=InnoDB default charset=utf8 comment='小区库';

-- 小区服务信息
drop table if exists tb_xiaoqu_service_info;
create table tb_xiaoqu_service_info (
id              		bigint(20)  	unsigned NOT NULL auto_increment 	comment '主键自增',
type             		tinyint(2)   	unsigned not null comment '服务类别：1.回收；2.家政；3.团购；4.其他',
service_areas			varchar(512)	not null				comment '服务区域，到区县一级，多个以逗号分隔，all表示全部',
icon_img          		varchar(64)  	not null				comment '服务信息头像',
detail_imgs  		    varchar(1024)   not null				comment '服务详情多图，以逗号分隔',
service_desc			varchar(512)	not null				comment '服务详情介绍',
contact_name            varchar(32)		not null				comment '联系人名称',
contact_phone			varchar(16)		not null			    comment '联系电话',
serv_star_value      	int(5)	 		unsigned   not null		comment '服务星级，使用时除以10',
deleted                 tinyint(2)   	unsigned   not null  default 0 comment '删除状态：0.未删除；1.删除',
gmt_create      		datetime        DEFAULT CURRENT_TIMESTAMP	comment '创建时间',
gmt_modified    		datetime        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP	comment '修改时间',
primary key (id),
key inx_type (type, serv_star_value),
key inx_create (gmt_create)
)ENGINE=InnoDB default charset=utf8 comment='小区服务信息';


-- 回收人员与小区关联信息
drop table if exists tb_huishou_complex_relation;
create table tb_huishou_complex_relation (
id              		bigint(20)  	unsigned NOT NULL auto_increment 	comment '主键自增',
complex_id				bigint(20)		unsigned NOT null  comment '小区Id',
contact_name            varchar(32)		not null				comment '联系人名称',
contact_phone			varchar(16)		not null			    comment '联系电话',
service_desc			varchar(512)	not null				comment '服务详情介绍',
gmt_create      		datetime        DEFAULT CURRENT_TIMESTAMP	comment '创建时间',
gmt_modified    		datetime        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP	comment '修改时间',
primary key (id),
key inx_complex_id (complex_id),
key inx_create (gmt_create)
)ENGINE=InnoDB default charset=utf8 comment='回收人员与小区关联信息';
