CREATE TABLE Patient (
    patient_id        SERIAL        NOT NULL    PRIMARY KEY,
    name              VARCHAR(20)   NOT NULL,
    surname           VARCHAR(20)   NOT NULL,
    date_of_birth     DATE          NOT NULL,
    address           VARCHAR(100)  NOT NULL,
    email             VARCHAR(32)   NOT NULL    UNIQUE,
    photo             BYTEA
);

CREATE TABLE Doctor (
    doctor_id          SERIAL       NOT NULL    PRIMARY KEY,
    name               VARCHAR(20)  NOT NULL,
    surname            VARCHAR(20)  NOT NULL,
    specialization     VARCHAR(256),
    email              VARCHAR(32)  NOT NULL    UNIQUE,
    photo              BYTEA
);

CREATE TABLE AppUser (
    user_id            SERIAL       PRIMARY KEY,
    username           VARCHAR(20)  NOT NULL  UNIQUE,
    password           VARCHAR(256) NOT NULL
);

CREATE TABLE Role (
    role_id             SERIAL      PRIMARY KEY,
    role_name           VARCHAR(50) NOT NULL
);

CREATE TABLE User_Role (
    user_id             INT     NOT NULL,
    role_id             INT     NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id)
        REFERENCES AppUser(user_id),
    FOREIGN KEY (role_id)
        REFERENCES Role(role_id)
);

CREATE TABLE Visit (
    visit_id            SERIAL      PRIMARY KEY,
    patient_id          INT         NOT NULL,
    doctor_id           INT         NOT NULL,
    date_time_of_visit  TIMESTAMP   NOT NULL,
    visit_status        VARCHAR(50) NOT NULL,
    FOREIGN KEY (patient_id)
        REFERENCES Patient(patient_id),
    FOREIGN KEY (doctor_id)
        REFERENCES Doctor(doctor_id)
);

CREATE TABLE DiseaseHistory (
    disease_history_id        SERIAL        PRIMARY KEY,
    patient_id                INT           NOT NULL,
    disease_name              VARCHAR(64)   NOT NULL,
    diagnosis_date            DATE,
    FOREIGN KEY (patient_id)
        REFERENCES Patient(patient_id)
);

CREATE TABLE VisitHistory (
    visit_history_id    SERIAL PRIMARY KEY,
    patient_id          INT    NOT NULL,
    visit_id            INT    NOT NULL,
    FOREIGN KEY (patient_id)
        REFERENCES Patient(patient_id),
    FOREIGN KEY (visit_id)
        REFERENCES Visit(visit_id)
);

CREATE TABLE Notes (
    note_id         SERIAL  PRIMARY KEY,
    visit_id        INT     NOT NULL,
    note_content    TEXT,
    FOREIGN KEY (visit_id)
        REFERENCES Visit(visit_id)
);
