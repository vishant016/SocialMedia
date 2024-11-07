CREATE TABLE users (
                       id CHAR(36) PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       username VARCHAR(255) UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       is_verified BOOLEAN DEFAULT FALSE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       phone_number VARCHAR(15),
                       status VARCHAR(20) DEFAULT 'active'
);

CREATE TABLE user_roles (
                            user_id CHAR(36) REFERENCES users(id) ON DELETE CASCADE,
                            role_name VARCHAR(50) NOT NULL,
                            assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (user_id, role_name)
);

CREATE TABLE social_auth (
                             user_id CHAR(36) REFERENCES users(id) ON DELETE CASCADE,
                             provider VARCHAR(50) NOT NULL,
                             provider_id VARCHAR(255) NOT NULL,
                             access_token VARCHAR(255),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (user_id, provider),
                             UNIQUE (provider, provider_id)
);

CREATE TABLE password_reset_tokens (
                                       user_id CHAR(36) REFERENCES users(id) ON DELETE CASCADE,
                                       token VARCHAR(255) UNIQUE NOT NULL,
                                       expires_at TIMESTAMP NOT NULL,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       is_used BOOLEAN DEFAULT FALSE,
                                       PRIMARY KEY (token)
);

CREATE TABLE two_factor_auth (
                                 user_id CHAR(36) REFERENCES users(id) ON DELETE CASCADE,
                                 method VARCHAR(50) NOT NULL,
                                 secret_key VARCHAR(255),
                                 is_enabled BOOLEAN DEFAULT FALSE,
                                 last_verified TIMESTAMP,
                                 PRIMARY KEY (user_id, method)
);

CREATE TABLE audit_log (
                           id CHAR(36) PRIMARY KEY,
                           user_id CHAR(36) REFERENCES users(id) ON DELETE SET NULL,
                           action VARCHAR(100) NOT NULL,
                           timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           ip_address VARCHAR(45),
                           user_agent TEXT,
                           description TEXT
);
