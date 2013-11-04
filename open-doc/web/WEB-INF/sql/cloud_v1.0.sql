CREATE TABLE t_sys_user (
    Id varchar(36) NOT NULL default '',
    isValid varchar(1) default 'Y',
    username varchar(255) default NULL,
    password varchar(255) default NULL,
    email varchar(255) default NULL,
    address varchar(255) default NULL,
    departmentId varchar(36) default NULL,
    positionId varchar(36) default NULL,
    roleIds varchar(2048) default NULL,
    PRIMARY KEY  (Id)
);

CREATE TABLE t_user_depart (
    Id varchar(36) NOT NULL default '',
    name varchar(255) default NULL,
    parentId varchar(36) default NULL,
    managerId varchar(36) default NULL,
    assistantIds varchar(2048) default NULL,
    PRIMARY KEY  (Id)
);

CREATE TABLE t_user_pos (
    Id varchar(36) NOT NULL default '',
    name varchar(255) default NULL,
    intro varchar(2048) default NULL,
    PRIMARY KEY  (Id)
);

CREATE TABLE t_user_role (
    Id varchar(36) NOT NULL default '',
    name varchar(255) default NULL,
    intro varchar(2048) default NULL,
    resourceIds varchar(2048) default NULL,
    PRIMARY KEY  (Id)
);

CREATE TABLE t_user_res (
    Id varchar(36) NOT NULL default '',
    typeStr varchar(255) default NULL,
    catStr varchar(255) default NULL,
    resStr varchar(255) default NULL,
    name varchar(255) default NULL,
    intro varchar(2048) default NULL,
    sortSn int default NULL,
    PRIMARY KEY  (Id)
);

CREATE TABLE t_sys_attach (
    Id varchar(36) NOT NULL default '',
    attachId varchar(36) default NULL,
    entityId varchar(36) default NULL,
    fileName varchar(255) default NULL,
    extendType varchar(10) default NULL,
    fileSize int default NULL,
    PRIMARY KEY  (Id)
);

CREATE TABLE t_sys_config (
    Id varchar(36) NOT NULL default '',
    version varchar(36) default NULL,
    systemName varchar(255) default NULL,
    codeSn int default 1,
    PRIMARY KEY  (Id)
);

CREATE TABLE t_doc_dir (
    Id varchar(36) NOT NULL default '',
    name varchar(255) default NULL,
    parentId varchar(36) default NULL,
    PRIMARY KEY  (Id)
);

CREATE TABLE t_doc_file (
    Id varchar(36) NOT NULL default '',
    name varchar(255) default NULL,
    parentId varchar(36) default NULL,
    creator varchar(36) default NULL,
    createTime varchar(36) default NULL,
    PRIMARY KEY  (Id)
);

insert into t_sys_user(Id, username, password, email, address) values('000000000000000000000000000000000000', 'admin', 'c4ca4238a0b923820dcc509a6f75849b', 'admin@admin.com', 'admin address');

insert into t_sys_config(Id, version, systemName) values('000000000000000000000000000000000000', 'ppm-doc v1.0', 'PPM Document Manage');

insert into t_user_pos(Id, name, intro) values('000000000000000000000000000000000000', '部门经理', '部门经理');
insert into t_user_pos(Id, name, intro) values('000000000000000000000000000000000001', '部门成员', '部门成员');

insert into t_user_res(Id, sortSn, typeStr, catStr, resStr, name) values('000000000000000000000000000000000001', 1, 'URL', 'ORG', '/pages/account/structure.jsp', '查看组织架构');
insert into t_user_res(Id, sortSn, typeStr, catStr, resStr, name) values('000000000000000000000000000000000002', 2, 'URL', 'ORG', '/entity/dispatch.do?operate=list&model=com.cloud.security.model.Position', '管理职位列表');
insert into t_user_res(Id, sortSn, typeStr, catStr, resStr, name) values('000000000000000000000000000000000003', 3, 'URL', 'ORG', '/entity/dispatch.do?operate=list&model=com.cloud.security.model.Role', '管理角色权限');
insert into t_user_res(Id, sortSn, typeStr, catStr, resStr, name) values('000000000000000000000000000000000004', 4, 'URL', 'SYS', '/pages/system/password.jsp', '修改个人密码');
insert into t_user_res(Id, sortSn, typeStr, catStr, resStr, name) values('000000000000000000000000000000000005', 5, 'URL', 'SYS', '/pages/system/systemName.jsp', '修改系统名称');
insert into t_user_res(Id, sortSn, typeStr, catStr, resStr, name) values('000000000000000000000000000000000006', 6, 'OP', 'ORG', 'org_userdepart', '管理组织架构(部门,用户)');