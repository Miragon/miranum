create table File
(
    id           varchar(36)   not null,
    created_time TIMESTAMP     not null,
    version      bigint,
    end_of_life  date,
    path_to_file varchar(1024) not null,
    primary key (id)
);

create table Folder
(
    id           varchar(36)  not null,
    created_time TIMESTAMP    not null,
    version      bigint,
    end_of_life  date,
    ref_id       varchar(512) not null,
    primary key (id)
);

create index index_path_to_file on File (path_to_file);
create index index_file_end_of_life on File (end_of_life);

alter table File
    drop constraint if exists UK_s3m33g9j4d6qjueuqc2c23qpj;

alter table File
    add constraint UK_s3m33g9j4d6qjueuqc2c23qpj unique (path_to_file);

create index index_ref_id on Folder (ref_id);
create index index_folder_end_of_life on Folder (end_of_life);