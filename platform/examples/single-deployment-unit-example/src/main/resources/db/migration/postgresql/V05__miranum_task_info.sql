create table miranum_task_info
(
    id_              varchar(36) not null,
    description_     varchar(1024),
    definitionname_  varchar(255) not null,
    assignee_        varchar(255),
    instanceid_      varchar(36) not null,
    form_            varchar(255),
    primary key (id_)
);

CREATE TABLE miranum_task_authorities (
    id_ VARCHAR(36) NOT NULL,
    type_ VARCHAR(255) NOT NULL,
    value_ VARCHAR(255) NOT NULL,
    taskinfo_id VARCHAR(36),
    PRIMARY KEY (id_),
    FOREIGN KEY (taskinfo_id) REFERENCES MIRANUM_TASK_INFO(id_)
);
