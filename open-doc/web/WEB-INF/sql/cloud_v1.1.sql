alter table t_doc_dir add column isDepartDir varchar(1);

CREATE TABLE t_sys_note (
    Id varchar(36) NOT NULL default '',
    entityId varchar(36) default NULL,
    note varchar(2048) default NULL,
    creatorId varchar(36) default NULL,
    createTime varchar(36) default NULL,
    PRIMARY KEY  (Id)
);

alter table t_sys_config add column attachMaxSize int default 100;

insert into t_user_res(Id, sortSn, typeStr, catStr, resStr, name) values('000000000000000000000000000000000007', 7, 'URL', 'SYS', '/pages/system/attachConfig.jsp', '附件配置');