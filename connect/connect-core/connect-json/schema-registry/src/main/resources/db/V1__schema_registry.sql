create table miranum_schema_registry
(
    id_        varchar(36)      not null
        constraint miranum_schema_registry_pkey
            primary key,
    key_        varchar(255)    not null,
    version_    int             not null,
    schema_     text            not null,
);

alter table miranum_schema_registry
    owner to "${flyway:user}";

create index if not exists miranum_schema_registry_key_version_idx
    on miranum_schema_registry (key_, version_);
