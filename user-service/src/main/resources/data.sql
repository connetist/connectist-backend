-- 기존 테이블 삭제
DROP TABLE IF EXISTS users;

-- Table 생성
CREATE TABLE users (
                       id VARCHAR(255) PRIMARY KEY,
                       pw VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       school VARCHAR(255) NOT NULL,
                       degree VARCHAR(255) NOT NULL,
                       sex VARCHAR(255) NOT NULL,
                       major VARCHAR(255) NOT NULL,
                       status VARCHAR(255) NOT NULL,
                       nickname VARCHAR(255),
                       created_at BIGINT NOT NULL
);

INSERT INTO users (id, pw, email, school, degree, sex, major, status, nickname, created_at)
VALUES ('admin', 'admin', 'admin@admin.com', 'GIST', 'Bachelor', 'MALE', 'AI', 'ADMIN', 'admin', UNIX_TIMESTAMP());
