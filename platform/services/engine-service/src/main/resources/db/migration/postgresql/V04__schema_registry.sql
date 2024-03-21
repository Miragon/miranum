create table miranum_schema_registry
(
    id_           varchar(36)      not null
        constraint miranum_schema_registry_pkey
            primary key,
    bundle_       varchar(255)    not null,
    ref_          varchar(255)    not null,
    tag_          varchar(255)    not null,
    json_node_    text            not null
);

alter table miranum_schema_registry
    owner to "${flyway:user}";

create index if not exists miranum_schema_registry_bundle_ref_idx
    on miranum_schema_registry (bundle_, ref_);

create index if not exists miranum_schema_registry_bundle_ref_tag_idx
    on miranum_schema_registry (bundle_, ref_, tag_);
