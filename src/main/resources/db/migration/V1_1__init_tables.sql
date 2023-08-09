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
    photo_data        BYTEA,
    address_id        INT           NOT NULL,
    FOREIGN KEY (address_id)
        REFERENCES address(address_id)
);

CREATE TABLE doctor (
    doctor_id               SERIAL       PRIMARY KEY,
    name                    VARCHAR(20)  NOT NULL,
    surname                 VARCHAR(20)  NOT NULL,
    specialization          VARCHAR(256),
    phone                   VARCHAR(32)  NOT NULL,
    email                   VARCHAR(32)  NOT NULL    UNIQUE,
    address_id              INT          NOT NULL,
    photo_data              BYTEA,
    FOREIGN KEY (address_id)
         REFERENCES address(address_id)
);

CREATE TABLE doctor_availability (
    doctor_availability_id    SERIAL        PRIMARY KEY,
    doctor_id                 INT           NOT NULL,
    day                       DATE          NOT NULL,
    start_time                TIME          NOT NULL,
    end_time                  TIME          NOT NULL,
    FOREIGN KEY (doctor_id)
        REFERENCES doctor(doctor_id)
);


CREATE TABLE note (
    note_id         SERIAL     PRIMARY KEY,
    note_content    TEXT       NOT NULL,
    date_time       TIMESTAMP  NOT NULL
);

CREATE TABLE Visit (
    visit_id            SERIAL      PRIMARY KEY,
    patient_id          INT         NOT NULL,
    doctor_id           INT         NOT NULL,
    day                 DATE        NOT NULL,
    start_time          TIME        NOT NULL,
    end_time            TIME        NOT NULL,
    canceled            BOOLEAN     NOT NULL    DEFAULT false,
    note_id             INT,
    FOREIGN KEY (patient_id)
        REFERENCES patient(patient_id),
    FOREIGN KEY (doctor_id)
        REFERENCES doctor(doctor_id),
    FOREIGN KEY (note_id)
        REFERENCES note(note_id)

);


CREATE TABLE disease (
    disease_id                SERIAL                    PRIMARY KEY,
    patient_id                INT                       NOT NULL,
    disease_name              VARCHAR(64)               NOT NULL,
    diagnosis_date            TIMESTAMP WITH TIME ZONE  NOT NULL,
    description               TEXT,
    FOREIGN KEY (patient_id)
        REFERENCES patient(patient_id)
);




