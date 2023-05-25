create table dwf_filter
(
    id_           varchar(36)  not null,
    filterstring_ varchar(255) not null,
    pageid_       varchar(255) not null,
    userid_       varchar(255) not null,
    primary key (id_)
);

create table dwf_json_schema
(
    key_    varchar(255) not null,
    schema_ text,
    primary key (key_)
);

create table dwf_process_instance_auth
(
    id_                varchar(36)  not null,
    processinstanceid_ varchar(255) not null,
    userid_            varchar(255) not null,
    primary key (id_)
);

create table dwf_process_instance_info
(
    id_                   varchar(36)  not null,
    processdefinitionkey_ varchar(255) not null,
    processname_          varchar(255) not null,
    description_          varchar(255),
    endtime_              timestamp,
    processinstanceid_    varchar(255),
    removaltime_          timestamp,
    starttime_            timestamp,
    status_               varchar(255),
    statuskey_            varchar(255),
    primary key (id_)
);

create table dwf_processconfig
(
    id      varchar(36)  not null,
    config  text,
    key     varchar(255) not null,
    version varchar(255) not null,
    primary key (id)
);


create index IDX_DWF_FILTER_USERID on dwf_filter (userid_);
create index IDX_DWF_SCHEMAKEY on dwf_json_schema (key_);
create index IDX_DWF_PROCAUTH_USERID on dwf_process_instance_auth (userid_);
create index IDX_DWF_PROCESSKEY on dwf_processconfig (key);

alter table dwf_processconfig
    add constraint UK_dwf_processconfig_key unique (key);

