-- Create sequence
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1;

-- Create table association_value_entry
CREATE TABLE association_value_entry
(
    id                BIGINT        IDENTITY(1,1) NOT NULL,
    association_key   VARCHAR(255)  NOT NULL,
    association_value VARCHAR(255),
    saga_id           VARCHAR(255)  NOT NULL,
    saga_type         VARCHAR(255),
    PRIMARY KEY (id)
);

-- Create table saga_entry
CREATE TABLE saga_entry
(
    saga_id         VARCHAR(255)  NOT NULL,
    revision        VARCHAR(255),
    saga_type       VARCHAR(255),
    serialized_saga VARBINARY(MAX),
    PRIMARY KEY (saga_id)
);

-- Create table token_entry
CREATE TABLE token_entry
(
    processor_name VARCHAR(255) NOT NULL,
    segment        INT          NOT NULL,
    owner          VARCHAR(255),
    timestamp      VARCHAR(255) NOT NULL,
    token          VARBINARY(MAX),
    token_type     VARCHAR(255),
    PRIMARY KEY (processor_name, segment)
);

-- Create indexes
CREATE INDEX IDX_association_value_entry_stakav
    ON association_value_entry (saga_type, association_key, association_value);

CREATE INDEX IDX_association_value_entry_sist
    ON association_value_entry (saga_id, saga_type);

-- Create table plf_data_entry
CREATE TABLE plf_data_entry
(
    entry_id           VARCHAR(255) NOT NULL,
    entry_type         VARCHAR(255) NOT NULL,
    application_name   VARCHAR(255) NOT NULL,
    date_created       DATETIME     NOT NULL,
    description        VARCHAR(2048),
    form_key           VARCHAR(255),
    date_last_modified DATETIME     NOT NULL,
    name               VARCHAR(255) NOT NULL,
    payload            VARCHAR(MAX),
    revision           BIGINT,
    processing_type    VARCHAR(255) NOT NULL,
    state              VARCHAR(255) NOT NULL,
    type               VARCHAR(255) NOT NULL,
    PRIMARY KEY (entry_id, entry_type)
);

-- Create table plf_data_entry_authorizations
CREATE TABLE plf_data_entry_authorizations
(
    entry_id             VARCHAR(255) NOT NULL,
    entry_type           VARCHAR(255) NOT NULL,
    authorized_principal VARCHAR(255) NOT NULL,
    PRIMARY KEY (entry_id, entry_type, authorized_principal)
);

-- Create table plf_data_entry_payload_attributes
CREATE TABLE plf_data_entry_payload_attributes
(
    entry_id   VARCHAR(255) NOT NULL,
    entry_type VARCHAR(255) NOT NULL,
    path       VARCHAR(255) NOT NULL,
    value      VARCHAR(255) NOT NULL,
    PRIMARY KEY (entry_id, entry_type, path, value)
);

-- Create table plf_data_entry_protocol
CREATE TABLE plf_data_entry_protocol
(
    id              VARCHAR(255) NOT NULL,
    log_details     VARCHAR(255),
    log_message     VARCHAR(255),
    processing_type VARCHAR(255) NOT NULL,
    state           VARCHAR(255) NOT NULL,
    time            DATETIME     NOT NULL,
    username        VARCHAR(255),
    entry_id        VARCHAR(255) NOT NULL,
    entry_type      VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Create table plf_proc_def
CREATE TABLE plf_proc_def
(
    proc_def_id             VARCHAR(255) NOT NULL,
    application_name        VARCHAR(255) NOT NULL,
    description             VARCHAR(2048),
    name                    VARCHAR(255) NOT NULL,
    proc_def_key            VARCHAR(255) NOT NULL,
    proc_def_version        INT           NOT NULL,
    start_form_key          VARCHAR(255),
    startable_from_tasklist BIT,
    version_tag             VARCHAR(255),
    PRIMARY KEY (proc_def_id)
);

-- Create table plf_proc_def_authorizations
CREATE TABLE plf_proc_def_authorizations
(
    proc_def_id                  VARCHAR(255) NOT NULL,
    authorized_starter_principal VARCHAR(255) NOT NULL,
    PRIMARY KEY (proc_def_id, authorized_starter_principal)
);

-- Create table plf_proc_instance
CREATE TABLE plf_proc_instance
(
    instance_id         VARCHAR(255) NOT NULL,
    business_key        VARCHAR(255),
    delete_reason       VARCHAR(255),
    end_activity_id     VARCHAR(255),
    application_name    VARCHAR(255) NOT NULL,
    source_def_id       VARCHAR(255) NOT NULL,
    source_def_key      VARCHAR(255) NOT NULL,
    source_execution_id VARCHAR(255) NOT NULL,
    source_instance_id  VARCHAR(255) NOT NULL,
    source_name         VARCHAR(255) NOT NULL,
    source_type         VARCHAR(255) NOT NULL,
    source_tenant_id    VARCHAR(255),
    start_activity_id   VARCHAR(255),
    start_user_id       VARCHAR(255),
    run_state           VARCHAR(255) NOT NULL,
    super_instance_id   VARCHAR(255),
    PRIMARY KEY (instance_id)
);

-- Create table plf_task
CREATE TABLE plf_task
(
    task_id             VARCHAR(255) NOT NULL,
    assignee_id         VARCHAR(255),
    business_key        VARCHAR(255),
    date_created        DATETIME      NOT NULL,
    description         VARCHAR(2048),
    date_due            DATETIME,
    date_follow_up      DATETIME,
    form_key            VARCHAR(255),
    name                VARCHAR(255) NOT NULL,
    owner_id            VARCHAR(255),
    payload             VARCHAR(MAX),
    priority            INT           DEFAULT NULL,
    application_name    VARCHAR(255) NOT NULL,
    source_def_id       VARCHAR(255) NOT NULL,
    source_def_key      VARCHAR(255) NOT NULL,
    source_execution_id VARCHAR(255) NOT NULL,
    source_instance_id  VARCHAR(255) NOT NULL,
    source_name         VARCHAR(255) NOT NULL,
    source_type         VARCHAR(255) NOT NULL,
    source_tenant_id    VARCHAR(255),
    task_def_key        VARCHAR(255) NOT NULL,
    PRIMARY KEY (task_id)
);

-- Create table plf_task_authorizations
CREATE TABLE plf_task_authorizations
(
    task_id              VARCHAR(255) NOT NULL,
    authorized_principal VARCHAR(255) NOT NULL,
    PRIMARY KEY (task_id, authorized_principal)
);

-- Create table plf_task_correlations
CREATE TABLE plf_task_correlations
(
    task_id    VARCHAR(255) NOT NULL,
    entry_id   VARCHAR(255) NOT NULL,
    entry_type VARCHAR(255) NOT NULL,
    PRIMARY KEY (task_id, entry_id, entry_type)
);

-- Create table plf_task_payload_attributes
CREATE TABLE plf_task_payload_attributes
(
    task_id VARCHAR(255) NOT NULL,
    path    VARCHAR(255) NOT NULL,
    value   VARCHAR(255) NOT NULL,
    PRIMARY KEY (task_id, path, value)
);

-- Add foreign key constraints
ALTER TABLE plf_data_entry_authorizations
    ADD CONSTRAINT FK_authorizations_have_data_entry
        FOREIGN KEY (entry_id, entry_type)
            REFERENCES plf_data_entry (entry_id, entry_type);

ALTER TABLE plf_data_entry_payload_attributes
    ADD CONSTRAINT FK_payload_attributes_have_data_entry
        FOREIGN KEY (entry_id, entry_type)
            REFERENCES plf_data_entry (entry_id, entry_type);

ALTER TABLE plf_data_entry_protocol
    ADD CONSTRAINT FK_protocol_have_data_entry
        FOREIGN KEY (entry_id, entry_type)
            REFERENCES plf_data_entry (entry_id, entry_type);

ALTER TABLE plf_proc_def_authorizations
    ADD CONSTRAINT FK_authorizations_have_proc_def
        FOREIGN KEY (proc_def_id)
            REFERENCES plf_proc_def (proc_def_id);

-- Add foreign key constraint to plf_task_authorizations
ALTER TABLE plf_task_authorizations
    ADD CONSTRAINT FK_authorizations_have_task
        FOREIGN KEY (task_id)
            REFERENCES plf_task (task_id);

-- Add foreign key constraint to plf_task_correlations
ALTER TABLE plf_task_correlations
    ADD CONSTRAINT FK_correlation_have_task
        FOREIGN KEY (task_id)
            REFERENCES plf_task (task_id);

-- Add foreign key constraint to plf_task_payload_attributes
ALTER TABLE plf_task_payload_attributes
    ADD CONSTRAINT FK_payload_attributes_have_task
        FOREIGN KEY (task_id)
            REFERENCES plf_task (task_id);

-- Create table dead_letter_entry
CREATE TABLE dead_letter_entry
(
    dead_letter_id       VARCHAR(255)   NOT NULL,
    cause_message        VARCHAR(255),
    cause_type           VARCHAR(255),
    diagnostics          VARBINARY(max),
    enqueued_at          DATETIME       NOT NULL,
    last_touched         DATETIME,
    aggregate_identifier VARCHAR(255),
    event_identifier     VARCHAR(255)   NOT NULL,
    message_type         VARCHAR(255)   NOT NULL,
    meta_data            VARBINARY(max),
    payload              VARBINARY(max) NOT NULL,
    payload_revision     VARCHAR(255),
    payload_type         VARCHAR(255)   NOT NULL,
    sequence_number      BIGINT,
    time_stamp           VARCHAR(255)   NOT NULL,
    token                VARBINARY(max),
    token_type           VARCHAR(255),
    type                 VARCHAR(255),
    processing_group     VARCHAR(255)   NOT NULL,
    processing_started   DATETIME,
    sequence_identifier  VARCHAR(255)   NOT NULL,
    sequence_index       BIGINT        NOT NULL,
    PRIMARY KEY (dead_letter_id)
);

-- Add date_deleted column to plf_data_entry
ALTER TABLE plf_data_entry
    ADD date_deleted DATETIME;
