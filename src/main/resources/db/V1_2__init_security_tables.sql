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