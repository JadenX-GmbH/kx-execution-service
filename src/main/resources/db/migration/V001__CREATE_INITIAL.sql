CREATE TABLE execution_job
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    price_token    DOUBLE       NOT NULL,
    `description`  LONGTEXT     NOT NULL,
    execution_type VARCHAR(100) NOT NULL,
    workerpool     VARCHAR(255) NOT NULL,
    worker         VARCHAR(255) NOT NULL,
    gig_id         BIGINT       NOT NULL,
    order_id       BIGINT NULL,
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
    blockchian_identifier VARCHAR(255) NOT NULL,
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
    id           BIGINT AUTO_INCREMENT NOT NULL,
    gig_id       BIGINT    NOT NULL,
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
    `description` LONGTEXT NULL,
    hash          VARCHAR(255) NOT NULL,
    type          VARCHAR(100) NOT NULL,
    location      VARCHAR(255) NOT NULL,
    storage_type  VARCHAR(255) NOT NULL,
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
    date_created     timestamp    NOT NULL,
    last_updated     timestamp    NOT NULL,
    CONSTRAINT PK_PROGRAM PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE `order`
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    transaction_id        VARCHAR(255) NOT NULL,
    blockchain_identifier VARCHAR(255) NOT NULL,
    date_created          timestamp    NOT NULL,
    last_updated          timestamp    NOT NULL,
    CONSTRAINT PK_ORDER PRIMARY KEY (id)
) AUTO_INCREMENT=10000;

CREATE TABLE gig_dataset
(
    gig_id     BIGINT NOT NULL,
    dataset_id BIGINT NOT NULL
);

ALTER TABLE execution_job
    ADD CONSTRAINT fk_execution_job_gig_id FOREIGN KEY (gig_id) REFERENCES gig (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE execution_job
    ADD CONSTRAINT fk_execution_job_order_id FOREIGN KEY (order_id) REFERENCES `order` (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

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

