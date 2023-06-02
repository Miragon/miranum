-- create table dwf_filter
create table dwf_filter
(
    id_           varchar(36)  not null,
    filterstring_ varchar(255) not null,
    pageid_       varchar(255) not null,
    userid_       varchar(255) not null,
    primary key (id_)
);

-- create table dwf_json_schema
create table dwf_json_schema
(
    [key]    varchar(255) not null,
    schema_ nvarchar(max),
    primary key ([key])
);

-- create table dwf_process_instance_auth
create table dwf_process_instance_auth
(
    id_                varchar(36)  not null,
    processinstanceid_ varchar(255) not null,
    userid_            varchar(255) not null,
    primary key (id_)
);

-- create table dwf_process_instance_info
create table dwf_process_instance_info
(
    id_                   varchar(36)  not null,
    processdefinitionkey_ varchar(255) not null,
    processname_          varchar(255) not null,
    description_          varchar(255),
    endtime_              datetime,
    processinstanceid_    varchar(255),
    removaltime_          datetime,
    starttime_            datetime,
    status_               varchar(255),
    statuskey_            varchar(255),
    primary key (id_)
);

-- create table dwf_processconfig
create table dwf_processconfig
(
    id      varchar(36)  not null,
    config  nvarchar(max),
    [key]   varchar(255) not null,
    version varchar(255) not null,
    primary key (id)
);

-- create indexes
create index IDX_DWF_FILTER_USERID on dwf_filter (userid_);
create index IDX_DWF_SCHEMAKEY on dwf_json_schema ([key]);
create index IDX_DWF_PROCAUTH_USERID on dwf_process_instance_auth (userid_);
create index IDX_DWF_PROCESSKEY on dwf_processconfig ([key]);

-- add unique constraint
alter table dwf_processconfig
    add constraint UK_dwf_processconfig_key unique ([key]);
