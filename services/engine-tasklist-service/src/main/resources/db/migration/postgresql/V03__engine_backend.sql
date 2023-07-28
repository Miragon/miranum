create table miranum_filter
(
    id_           varchar(36)  not null,
    filterstring_ varchar(255) not null,
    pageid_       varchar(255) not null,
    userid_       varchar(255) not null,
    primary key (id_)
);


create table miranum_process_instance_auth
(
    id_                varchar(36)  not null,
    processinstanceid_ varchar(255) not null,
    userid_            varchar(255) not null,
    primary key (id_)
);

create table miranum_process_instance
(
    id_                   varchar(36)  not null,
    processdefinitionkey_ varchar(255) not null,
    processname_          varchar(255) not null,
    description_          varchar(255),
    endtime_              timestamp,
    removaltime_          timestamp,
    starttime_            timestamp,
    status_               varchar(255),
    statuskey_            varchar(255),
    primary key (id_)
);

create table miranum_start_context
(
    id_            varchar(36)  not null,
    definitionkey_ varchar(255) not null,
    filecontext_   varchar(255) not null,
    userid_        varchar(255) not null,
    primary key (id_)
);

create index IDX_DWF_FILTER_USERID on miranum_filter (userid_);
create index IDX_DWF_PROCAUTH_USERID on miranum_process_instance_auth (userid_);
