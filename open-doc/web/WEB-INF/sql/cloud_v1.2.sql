alter table t_doc_file add column status int;
alter table t_doc_file add column uniqueId varchar(36);
alter table t_doc_file add column isLatest varchar(1);
alter table t_doc_file add column docVersion varchar(100);
alter table t_doc_file add column checkoutor varchar(36);
