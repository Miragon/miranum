create table miranum_task_info
(
    id_              VARCHAR(36) NOT NULL,
    description_     VARCHAR(1024),
    definitionname_  varchar(255) NOT NULL,
    assignee_        VARCHAR(255),
    instanceid_      VARCHAR(36) NOT NULL,
    formkey_         VARCHAR(255),
    PRIMARY KEY (id_)
);

CREATE TABLE miranum_task_authorities (
    id_          VARCHAR(36) NOT NULL,
    type_        VARCHAR(255) NOT NULL,
    value_       VARCHAR(255) NOT NULL,
    taskinfo_id  VARCHAR(36),
    PRIMARY KEY (id_),
    FOREIGN KEY (taskinfo_id) REFERENCES MIRANUM_TASK_INFO(id_)
);

CREATE TABLE miranum_task_custom_fields (
    id_          VARCHAR(36) NOT NULL,
    key_         VARCHAR(255) NOT NULL,
    value_       VARCHAR(1024) NOT NULL,
    taskinfo_id  VARCHAR(36),
    PRIMARY KEY (id_),
    FOREIGN KEY (taskinfo_id) REFERENCES MIRANUM_TASK_INFO(id_)
);
