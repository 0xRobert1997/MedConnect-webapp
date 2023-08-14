CREATE TABLE app_user (
    user_id            SERIAL       PRIMARY KEY,
    user_name          VARCHAR(20)  NOT NULL        UNIQUE,
    password           VARCHAR(256) NOT NULL,
    email              VARCHAR(32)  NOT NULL        UNIQUE,
    active             BOOLEAN      NOT NULL,
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
    CONSTRAINT fk_app_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES app_user(user_id),
    CONSTRAINT fk_app_user_role_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES role(role_id)
);


INSERT INTO role (role_id, role_name) values (1, 'PATIENT'), (2, 'DOCTOR'), (3, 'REST_API');

ALTER TABLE patient
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES app_user (user_id);

ALTER TABLE doctor
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES app_user (user_id);

ALTER TABLE patient
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE doctor
ALTER COLUMN user_id SET NOT NULL;