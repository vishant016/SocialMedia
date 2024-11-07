CREATE TABLE verificationtoken (
                       id CHAR(36) PRIMARY KEY,
                       token VARCHAR(255) UNIQUE NOT NULL,
                       expiry_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE users ADD COLUMN name VARCHAR(255);

