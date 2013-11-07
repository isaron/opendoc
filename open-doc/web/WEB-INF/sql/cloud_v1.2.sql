alter table t_doc_file add column status int;
alter table t_doc_file add column uniqueId varchar(36);
alter table t_doc_file add column isLatest varchar(1);
alter table t_doc_file add column docVersion varchar(100);
alter table t_doc_file add column checkoutor varchar(36);

CREATE TABLE t_doc_record (
    Id varchar(36) NOT NULL default '',
    docId varchar(36) default NULL,
    note varchar(2048) default NULL,
    operate varchar(100) default NULL,
    creator varchar(36) default NULL,
    createTime varchar(36) default NULL,
    PRIMARY KEY  (Id)
);
