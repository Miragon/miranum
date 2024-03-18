create table miranum_schema_registry
(
    id_           varchar(36)      not null
        constraint miranum_schema_registry_pkey
            primary key,
    bundle_       varchar(255)    not null,
    ref_          varchar(255)    not null,
    tag_          varchar(255)    not null,
    json_node_    nvarchar(max)    not null
);

alter table miranum_schema_registry
    alter column json_node_ nvarchar(max);

if not exists (select * from sys.indexes where name = 'miranum_schema_registry_bundle_ref_idx' and object_id = object_id('miranum_schema_registry'))
create index miranum_schema_registry_bundle_ref_idx
    on miranum_schema_registry (bundle_, ref_);

if not exists (select * from sys.indexes where name = 'miranum_schema_registry_bundle_ref_tag_idx' and object_id = object_id('miranum_schema_registry'))
create index miranum_schema_registry_bundle_ref_tag_idx
    on miranum_schema_registry (bundle_, ref_, tag_);
