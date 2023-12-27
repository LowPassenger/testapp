CREATE TABLE IF NOT EXISTS roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       role_name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles (
                             user_id BIGINT,
                             role_id BIGINT,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);