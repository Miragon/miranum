create table miranum_task_info
(
    id_             varchar(36) not null,
    description_    varchar(1024),
    definitionname_ varchar(255) not null,
    assignee_       varchar(255),
    instanceid_     varchar(36) not null,
    form_           varchar(255),
    primary key (id_)
);
