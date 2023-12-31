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
    name              VARCHAR(32)   NOT NULL,
    surname           VARCHAR(32)   NOT NULL,
    pesel             VARCHAR(12)   NOT NULL    UNIQUE,
    date_of_birth     DATE,
    sex               VARCHAR(256),
    phone             VARCHAR(32)   NOT NULL,
    email             VARCHAR(32)   NOT NULL    UNIQUE,
    address_id        INT           NOT NULL,
    photo_data        BYTEA,
    imgur_photo_id    VARCHAR(32),
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
    imgur_photo_id          VARCHAR(32),
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


CREATE TABLE Visit (
    visit_id            SERIAL      PRIMARY KEY,
    patient_id          INT         NOT NULL,
    doctor_id           INT         NOT NULL,
    day                 DATE        NOT NULL,
    start_time          TIME        NOT NULL,
    end_time            TIME        NOT NULL,
    canceled            BOOLEAN     NOT NULL    DEFAULT false,
    FOREIGN KEY (patient_id)
        REFERENCES patient(patient_id),
    FOREIGN KEY (doctor_id)
        REFERENCES doctor(doctor_id)
);
CREATE TABLE note (
    note_id         SERIAL     PRIMARY KEY,
    note_content    TEXT       NOT NULL,
    date_time       TIMESTAMP  NOT NULL,
    visit_id        INT,
    FOREIGN KEY (visit_id)
        REFERENCES visit(visit_id)
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




