create table miranum_schema_registry
(
    id_          varchar(36)      not null
        constraint miranum_schema_registry_pkey
            primary key,
    ref_          varchar(255)    not null,
    version_      int             not null,
    json_node_    text            not null
);

create index if not exists miranum_schema_registry_key_version_idx
    on miranum_schema_registry (ref_, version_);
