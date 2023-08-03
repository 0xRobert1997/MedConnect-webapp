CREATE TABLE address
(
    address_id  SERIAL      PRIMARY KEY,
    country     VARCHAR(32) NOT NULL,
    city        VARCHAR(32) NOT NULL,
    postal_code VARCHAR(32) NOT NULL,
    address     VARCHAR(32) NOT NULL
);
CREATE TABLE patient (
    patient_id        SERIAL        PRIMARY KEY,
    name              VARCHAR(20)   NOT NULL,
    surname           VARCHAR(20)   NOT NULL,
    pesel             VARCHAR(11)   NOT NULL    UNIQUE,
    date_of_birth     DATE          NOT NULL,
    sex               VARCHAR(256),
    phone             VARCHAR(32)   NOT NULL,
    email             VARCHAR(32)   NOT NULL    UNIQUE,
    photo             BYTEA,
    address_id        INT           NOT NULL,
    visit_history_id  INT,
    FOREIGN KEY (address_id)
        REFERENCES address(address_id)
    FOREIGN KEY (visit_id)
        REFERENCES visit(visit_id)
    FOREIGN KEY (visit_history_id)
        REFERENCES visit_history(visit_history_id)
);

CREATE TABLE doctor (
    doctor_id               SERIAL       PRIMARY KEY,
    name                    VARCHAR(20)  NOT NULL,
    surname                 VARCHAR(20)  NOT NULL,
    specialization          VARCHAR(256),
    phone                   VARCHAR(32)  NOT NULL,
    email                   VARCHAR(32)  NOT NULL    UNIQUE,
    address_id              INT          NOT NULL,
    doctor_availability_id  INT,
    photo                   BYTEA,
    FOREIGN KEY (address_id)
         REFERENCES address(address_id)
     FOREIGN KEY (doctor_availability_id)
        REFERENCES doctor_availability(doctor_availability_id)

);

CREATE TABLE doctor_availability (
    doctor_availability_id    SERIAL        PRIMARY KEY,
    doctor_id                 INT           NOT NULL,
    day_of_week               VARCHAR(10)   NOT NULL,
    start_time                TIME          NOT NULL,
    end_time                  TIME          NOT NULL,
    FOREIGN KEY (doctor_id)
        REFERENCES doctor(doctor_id)
);

CREATE TABLE app_user (
    user_id            SERIAL       PRIMARY KEY,
    username           VARCHAR(20)  NOT NULL        UNIQUE,
    password           VARCHAR(256) NOT NULL,
    email              VARCHAR(32)  NOT NULL        UNIQUE,
    patient_id         INT,
    doctor_id          INT,
    FOREIGN KEY (patient_id)
        REFERENCES patient(patient_id),
    FOREIGN KEY (doctor_id)
        REFERENCES doctor(doctor_id)
);

CREATE TABLE role (
    role_id             SERIAL      PRIMARY KEY,
    role_name           VARCHAR(20) NOT NULL
);

CREATE TABLE user_role (
    user_id             INT     NOT NULL,
    role_id             INT     NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id)
        REFERENCES app_user(user_id),
    FOREIGN KEY (role_id)
        REFERENCES role(role_id)
);

CREATE TABLE notes (
    note_id         SERIAL     PRIMARY KEY,
    note_content    TEXT       NOT NULL,
    date_time       TIMESTAMP  NOT NULL
);

CREATE TABLE Visit (
    visit_id            SERIAL      PRIMARY KEY,
    patient_id          INT         NOT NULL,
    doctor_id           INT         NOT NULL,
    date_time           TIMESTAMP   NOT NULL,
    cancelled           BOOLEAN     NOT NULL    DEFAULT false,
    notes_id            INT         NOT NULL,
    FOREIGN KEY (patient_id)
        REFERENCES patient(patient_id),
    FOREIGN KEY (doctor_id)
        REFERENCES doctor(doctor_id),
    FOREIGN KEY (notes_id)
            REFERENCES notes(notes_id)
);


CREATE TABLE disease (
    disease_id        SERIAL        PRIMARY KEY,
    patient_id                INT           NOT NULL,
    disease_name              VARCHAR(64)   NOT NULL,
    diagnosis_date            DATE,
    description               TEXT,
    FOREIGN KEY (patient_id)
        REFERENCES patient(patient_id)
);

CREATE TABLE visit_history (
    visit_history_id    SERIAL  PRIMARY KEY,
    patient_id          INT     NOT NULL,
    FOREIGN KEY (patient_id)
        REFERENCES patient(patient_id),
);


