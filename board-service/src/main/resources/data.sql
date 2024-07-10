


-- 기존 테이블 삭제
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS recomments;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS boards;
DROP TABLE IF EXISTS stars;
DROP TABLE IF EXISTS labs;


-- 테이블 생성

CREATE TABLE labs (
                      lab_id VARCHAR(255) PRIMARY KEY,
                      school VARCHAR(255),
                      major VARCHAR(255),
                      professor VARCHAR(255),
                      contents TEXT,
                      created_at BIGINT
);

CREATE TABLE boards (
                        board_id VARCHAR(255) PRIMARY KEY,
                        user_id VARCHAR(255),
                        lab_id VARCHAR(255),
                        contents TEXT,
                        deleted BOOLEAN,
                        created_at BIGINT,
                        deleted_at BIGINT
);

CREATE TABLE comments (
                          comment_id VARCHAR(255) PRIMARY KEY,
                          board_id VARCHAR(255),
                          user_id VARCHAR(255),
                          contents TEXT,
                          deleted BOOLEAN,
                          created_at BIGINT,
                          deleted_at BIGINT
);

CREATE TABLE recomments (
                            recomment_id VARCHAR(255) PRIMARY KEY,
                            comment_id VARCHAR(255),
                            user_id VARCHAR(255),
                            contents TEXT,
                            deleted BOOLEAN,
                            created_at BIGINT,
                            deleted_at BIGINT,
                            FOREIGN KEY (comment_id) REFERENCES comments(comment_id)
);

CREATE TABLE stars (
                       star_id VARCHAR(255) PRIMARY KEY,
                       star_count INT,
                       user_id VARCHAR(255),
                       lab_id VARCHAR(255),
                       FOREIGN KEY (lab_id) REFERENCES labs(lab_id)
);

CREATE TABLE likes (
                       id VARCHAR(255) PRIMARY KEY,
                       user_id VARCHAR(255),
                       board_id VARCHAR(255),
                       comment_id VARCHAR(255),
                       FOREIGN KEY (board_id) REFERENCES boards(board_id),
                       FOREIGN KEY (comment_id) REFERENCES comments(comment_id)
);

-- 데이터 삽입
INSERT INTO labs (lab_id, school, major, professor, contents, created_at)
VALUES
    ('lab1', 'School A', 'Major A', 'Professor A', 'Lab Contents A',  UNIX_TIMESTAMP()),
    ('lab2', 'School B', 'Major B', 'Professor B', 'Lab Contents B', UNIX_TIMESTAMP());

INSERT INTO boards (board_id, user_id, lab_id, contents, deleted, created_at, deleted_at)
VALUES
    ('board1', 'user1', 'lab1', 'Board Contents 1', false, UNIX_TIMESTAMP(), 0),
    ('board2', 'user2', 'lab1', 'Board Contents 2', false, UNIX_TIMESTAMP(), 0),
    ('board3', 'user3', 'lab2', 'Board Contents 3', false, UNIX_TIMESTAMP(), 0);

INSERT INTO comments (comment_id, board_id, user_id, contents, deleted, created_at, deleted_at)
VALUES
    ('comment1', 'board1', 'user1', 'Comment Contents 1', false, UNIX_TIMESTAMP(), 0),
    ('comment2', 'board1', 'user2', 'Comment Contents 2', false, UNIX_TIMESTAMP(), 0),
    ('comment3', 'board1', 'user3', 'Comment Contents 3', false, UNIX_TIMESTAMP(), 0),
    ('comment4', 'board2', 'user1', 'Comment Contents 4', false, UNIX_TIMESTAMP(), 0),
    ('comment5', 'board2', 'user2', 'Comment Contents 5', false, UNIX_TIMESTAMP(), 0),
    ('comment6', 'board2', 'user3', 'Comment Contents 6', false, UNIX_TIMESTAMP(), 0),
    ('comment7', 'board3', 'user1', 'Comment Contents 7', false, UNIX_TIMESTAMP(), 0),
    ('comment8', 'board3', 'user2', 'Comment Contents 8', false, UNIX_TIMESTAMP(), 0),
    ('comment9', 'board3', 'user3', 'Comment Contents 9', false, UNIX_TIMESTAMP(), 0),
    ('comment10', 'board3', 'user1', 'Comment Contents 10', false, UNIX_TIMESTAMP(), 0);

INSERT INTO recomments (recomment_id, comment_id, user_id, contents, deleted, created_at, deleted_at)
VALUES
    ('recomment1', 'comment1', 'user2', 'Recomment Contents 1', false, UNIX_TIMESTAMP(), 0),
    ('recomment2', 'comment1', 'user3', 'Recomment Contents 2', false, UNIX_TIMESTAMP(), 0),
    ('recomment3', 'comment2', 'user1', 'Recomment Contents 3', false, UNIX_TIMESTAMP(), 0),
    ('recomment4', 'comment2', 'user3', 'Recomment Contents 4', false, UNIX_TIMESTAMP(), 0),
    ('recomment5', 'comment3', 'user1', 'Recomment Contents 5', false, UNIX_TIMESTAMP(), 0),
    ('recomment6', 'comment3', 'user2', 'Recomment Contents 6', false, UNIX_TIMESTAMP(), 0),
    ('recomment7', 'comment4', 'user1', 'Recomment Contents 7', false, UNIX_TIMESTAMP(), 0),
    ('recomment8', 'comment4', 'user2', 'Recomment Contents 8', false, UNIX_TIMESTAMP(), 0),
    ('recomment9', 'comment5', 'user1', 'Recomment Contents 9', false, UNIX_TIMESTAMP(), 0),
    ('recomment10', 'comment5', 'user2', 'Recomment Contents 10', false, UNIX_TIMESTAMP(), 0);

INSERT INTO stars (star_id, star_count, user_id, lab_id)
VALUES
    ('star1', 5, 'user1', 'lab1'),
    ('star2', 4, 'user2', 'lab1'),
    ('star3', 3, 'user3', 'lab1'),
    ('star4', 5, 'user1', 'lab2'),
    ('star5', 4, 'user2', 'lab2'),
    ('star6', 3, 'user3', 'lab2'),
    ('star7', 5, 'user1', 'lab1'),
    ('star8', 4, 'user2', 'lab1'),
    ('star9', 3, 'user3', 'lab1'),
    ('star10', 5, 'user1', 'lab2');

INSERT INTO likes (id, user_id, board_id, comment_id)
VALUES
    ('like1', 'user1', 'board1', NULL),
    ('like2', 'user2', 'board1', NULL),
    ('like3', 'user3', 'board1', NULL),
    ('like4', 'user1', 'board2', NULL),
    ('like5', 'user2', 'board2', NULL),
    ('like6', 'user3', 'board2', NULL),
    ('like7', 'user1', 'board3', NULL),
    ('like8', 'user2', 'board3', NULL),
    ('like9', 'user3', 'board3', NULL),
    ('like10', 'user1', NULL, 'comment1');
