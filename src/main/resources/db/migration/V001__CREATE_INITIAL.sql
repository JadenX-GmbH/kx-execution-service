CREATE TABLE execution_job
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    price_token    DOUBLE       NULL,
    `description`  LONGTEXT     NOT NULL,
    execution_type VARCHAR(100) NOT NULL,
    workerpool     VARCHAR(255) NULL,
    worker         VARCHAR(255) NULL,
    deal_id        VARCHAR(66) NULL,
    deal_blockchain_identifier        VARCHAR(100) NULL,
    task_id        VARCHAR(66) NULL,
    task_blockchain_identifier        VARCHAR(100) NULL,
    category         INT       NULL,
    trust         INT       NULL,
    gig_id         BIGINT       NOT NULL,
    dataset_id       BIGINT NULL,
    date_created   timestamp    NOT NULL,
    last_updated   timestamp    NOT NULL,
    CONSTRAINT PK_EXECUTION_JOB PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE exploration_job
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    `description` LONGTEXT NULL,
    code_hash     VARCHAR(255) NULL,
    gig_id        BIGINT    NOT NULL,
    dataset_id       BIGINT NULL,
    date_created  timestamp NOT NULL,
    last_updated  timestamp NOT NULL,
    CONSTRAINT PK_EXPLORATION_JOB PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE execution_result
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    location              VARCHAR(255) NOT NULL,
    storage_type          VARCHAR(255) NOT NULL,
    transaction_id        VARCHAR(255) NOT NULL,
    blockchain_identifier VARCHAR(255) NOT NULL,
    execution_job_id      BIGINT       NOT NULL,
    date_created          timestamp    NOT NULL,
    last_updated          timestamp    NOT NULL,
    CONSTRAINT PK_EXECUTION_RESULT PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE exploration_result
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    location           VARCHAR(255) NOT NULL,
    storate_type       VARCHAR(255) NOT NULL,
    exploration_job_id BIGINT       NOT NULL,
    date_created       timestamp    NOT NULL,
    last_updated       timestamp    NOT NULL,
    CONSTRAINT PK_EXPLORATION_RESULT PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE gig
(
    id           BIGINT NOT NULL,
    data_owner   char(36)  NOT NULL,
    specialist   char(36) NULL,
    date_created timestamp NOT NULL,
    last_updated timestamp NOT NULL,
    CONSTRAINT PK_GIG PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE dataset
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(100) NULL,
    data_owner   char(36)  NOT NULL,
    `description` LONGTEXT NULL,
    hash          VARCHAR(255) NOT NULL,
    type          VARCHAR(100) NOT NULL,
    location      VARCHAR(255) NOT NULL,
    storage_type  VARCHAR(255) NOT NULL,
    blockchain_address VARCHAR(42) NULL,
    date_created  timestamp    NOT NULL,
    last_updated  timestamp    NOT NULL,
    CONSTRAINT PK_DATASET PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE program
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    hash             VARCHAR(255) NOT NULL,
    location         VARCHAR(255) NOT NULL,
    storage_type     VARCHAR(100) NOT NULL,
    execution_job_id BIGINT       NOT NULL,
    blockchain_address VARCHAR(42) NULL,
    date_created     timestamp    NOT NULL,
    last_updated     timestamp    NOT NULL,
    CONSTRAINT PK_PROGRAM PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE `order`
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255) NOT NULL,
    transaction_id        VARCHAR(255) NOT NULL,
    blockchain_identifier VARCHAR(255) NOT NULL,
    execution_job_id BIGINT       NOT NULL,
    order_details LONGTEXT NULL,
    date_created          timestamp    NOT NULL,
    last_updated          timestamp    NOT NULL,
    CONSTRAINT PK_ORDER PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE gig_dataset
(
    gig_id     BIGINT NOT NULL,
    dataset_id BIGINT NOT NULL
);

CREATE TABLE execution_input_parameter
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    execution_job_id BIGINT       NOT NULL,
    input_parameter VARCHAR(255) NOT NULL,
    date_created          timestamp    NOT NULL,
    last_updated          timestamp    NOT NULL,
    CONSTRAINT PK_EXECUTION_INPUT_PARAMETER PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

ALTER TABLE execution_job
    ADD CONSTRAINT fk_execution_job_gig_id FOREIGN KEY (gig_id) REFERENCES gig (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE exploration_job
    ADD CONSTRAINT fk_exploration_job_gig_id FOREIGN KEY (gig_id) REFERENCES gig (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE execution_result
    ADD CONSTRAINT fk_execution_result_execution_job_id FOREIGN KEY (execution_job_id) REFERENCES execution_job (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE exploration_result
    ADD CONSTRAINT fk_exploration_result_exploration_job_id FOREIGN KEY (exploration_job_id) REFERENCES exploration_job (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE program
    ADD CONSTRAINT fk_program_execution_job_id FOREIGN KEY (execution_job_id) REFERENCES execution_job (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE gig_dataset
    ADD PRIMARY KEY (gig_id, dataset_id);

ALTER TABLE gig_dataset
    ADD CONSTRAINT fk_gig_dataset_gig_id FOREIGN KEY (gig_id) REFERENCES gig (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE gig_dataset
    ADD CONSTRAINT fk_gig_dataset_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `order`
    ADD CONSTRAINT fk_order_execution_job_id FOREIGN KEY (execution_job_id) REFERENCES execution_job (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE execution_job
    ADD CONSTRAINT fk_execution_job_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE execution_input_parameter
    ADD CONSTRAINT fk_execution_input_parameter_execution_job_id FOREIGN KEY (execution_job_id) REFERENCES execution_job (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE exploration_job
    ADD CONSTRAINT fk_exploration_job_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

