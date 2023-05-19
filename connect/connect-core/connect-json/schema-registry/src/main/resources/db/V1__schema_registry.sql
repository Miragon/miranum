create table miranum_schema_registry
(
    id_           varchar(36)      not null
        constraint miranum_schema_registry_pkey
            primary key,
    bundle_       varchar(255)    not null,
    ref_          varchar(255)    not null,
    version_      int             not null,
    json_node_    text            not null
);

alter table miranum_schema_registry
    owner to "${flyway:user}";

create index if not exists miranum_schema_registry_bundle_ref_idx
    on miranum_schema_registry (bundle_, ref_);

create index if not exists miranum_schema_registry_bundle_ref_version_idx
    on miranum_schema_registry (bundle_, ref_, version_);
