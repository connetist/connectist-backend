-- Labs 테이블 생성
CREATE TABLE labs (
                      lab_id VARCHAR(255) PRIMARY KEY,
                      school VARCHAR(255),
                      major VARCHAR(255),
                      professor VARCHAR(255),
                      contents TEXT,
                      likeSum BIGINT,
                      createdAt BIGINT
);

-- Boards 테이블 생성
CREATE TABLE boards (
                        board_id VARCHAR(255) PRIMARY KEY,
                        userId VARCHAR(255),
                        labId VARCHAR(255),
                        contents TEXT,
                        deleted BOOLEAN,
                        createdAt BIGINT,
                        deletedAt BIGINT,
                        FOREIGN KEY (labId) REFERENCES labs(lab_id)
);

-- Comments 테이블 생성
CREATE TABLE comments (
                          comment_id VARCHAR(255) PRIMARY KEY,
                          boardId VARCHAR(255),
                          userId VARCHAR(255),
                          contents TEXT,
                          deleted BOOLEAN,
                          createdAt BIGINT,
                          deletedAt BIGINT,
                          FOREIGN KEY (boardId) REFERENCES boards(board_id)
);

-- Recomments 테이블 생성
CREATE TABLE recomments (
                            recomment_id VARCHAR(255) PRIMARY KEY,
                            commendId VARCHAR(255),
                            userId VARCHAR(255),
                            contents TEXT,
                            deleted BOOLEAN,
                            createdAt BIGINT,
                            deletedAt BIGINT,
                            FOREIGN KEY (commendId) REFERENCES comments(comment_id)
);

-- Likes 테이블 생성
CREATE TABLE likes (
                       id VARCHAR(255) PRIMARY KEY,
                       boardId VARCHAR(255),
                       commendId VARCHAR(255),
                       userId VARCHAR(255),
                       FOREIGN KEY (boardId) REFERENCES boards(board_id),
                       FOREIGN KEY (commendId) REFERENCES comments(comment_id)
);

-- Stars 테이블 생성
CREATE TABLE stars (
                       star_id VARCHAR(255) PRIMARY KEY,
                       labId VARCHAR(255),
                       starCount INT,
                       userId VARCHAR(255),
                       FOREIGN KEY (labId) REFERENCES labs(lab_id)
);



-- Lab 데이터 삽입
INSERT INTO labs (lab_id, school, major, professor, contents, likeSum, createdAt) VALUES
                                                                                      ('lab1', 'GIST', 'CS', 'Professor A', 'Lab 1 description', 10, 1622547800),
                                                                                      ('lab2', 'UNIST', 'EE', 'Professor B', 'Lab 2 description', 15, 1622547800);

-- Board 데이터 삽입
INSERT INTO boards (board_id, userId, labId, contents, deleted, createdAt, deletedAt) VALUES
                                                                                          ('board1', 'user1', 'lab1', 'Board 1 content', FALSE, 1622547800, NULL),
                                                                                          ('board2', 'user2', 'lab1', 'Board 2 content', FALSE, 1622547800, NULL),
                                                                                          ('board3', 'user3', 'lab2', 'Board 3 content', FALSE, 1622547800, NULL),
                                                                                          ('board4', 'user4', 'lab2', 'Board 4 content', FALSE, 1622547800, NULL),
                                                                                          ('board5', 'user5', 'lab1', 'Board 5 content', FALSE, 1622547800, NULL);

-- Comment 데이터 삽입
INSERT INTO comments (comment_id, boardId, userId, contents, deleted, createdAt, deletedAt) VALUES
                                                                                                ('comment1', 'board1', 'user2', 'Comment 1 on board 1', FALSE, 1622547800, NULL),
                                                                                                ('comment2', 'board1', 'user3', 'Comment 2 on board 1', FALSE, 1622547800, NULL),
                                                                                                ('comment3', 'board2', 'user4', 'Comment 1 on board 2', FALSE, 1622547800, NULL),
                                                                                                ('comment4', 'board2', 'user5', 'Comment 2 on board 2', FALSE, 1622547800, NULL),
                                                                                                ('comment5', 'board3', 'user1', 'Comment 1 on board 3', FALSE, 1622547800, NULL),
                                                                                                ('comment6', 'board3', 'user2', 'Comment 2 on board 3', FALSE, 1622547800, NULL),
                                                                                                ('comment7', 'board4', 'user3', 'Comment 1 on board 4', FALSE, 1622547800, NULL),
                                                                                                ('comment8', 'board4', 'user4', 'Comment 2 on board 4', FALSE, 1622547800, NULL),
                                                                                                ('comment9', 'board5', 'user5', 'Comment 1 on board 5', FALSE, 1622547800, NULL),
                                                                                                ('comment10', 'board5', 'user1', 'Comment 2 on board 5', FALSE, 1622547800, NULL);

-- Recomment 데이터 삽입
INSERT INTO recomments (recomment_id, commendId, userId, contents, deleted, createdAt, deletedAt) VALUES
                                                                                                      ('recomment1', 'comment1', 'user3', 'Recomment 1 on comment 1', FALSE, 1622547800, NULL),
                                                                                                      ('recomment2', 'comment1', 'user4', 'Recomment 2 on comment 1', FALSE, 1622547800, NULL),
                                                                                                      ('recomment3', 'comment2', 'user5', 'Recomment 1 on comment 2', FALSE, 1622547800, NULL),
                                                                                                      ('recomment4', 'comment2', 'user1', 'Recomment 2 on comment 2', FALSE, 1622547800, NULL),
                                                                                                      ('recomment5', 'comment3', 'user2', 'Recomment 1 on comment 3', FALSE, 1622547800, NULL),
                                                                                                      ('recomment6', 'comment3', 'user3', 'Recomment 2 on comment 3', FALSE, 1622547800, NULL),
                                                                                                      ('recomment7', 'comment4', 'user4', 'Recomment 1 on comment 4', FALSE, 1622547800, NULL),
                                                                                                      ('recomment8', 'comment4', 'user5', 'Recomment 2 on comment 4', FALSE, 1622547800, NULL),
                                                                                                      ('recomment9', 'comment5', 'user1', 'Recomment 1 on comment 5', FALSE, 1622547800, NULL),
                                                                                                      ('recomment10', 'comment5', 'user2', 'Recomment 2 on comment 5', FALSE, 1622547800, NULL);

-- Like 데이터 삽입
INSERT INTO likes (id, boardId, commendId, userId) VALUES
                                                       ('like1', 'board1', NULL, 'user1'),
                                                       ('like2', 'board2', NULL, 'user2'),
                                                       ('like3', 'board3', NULL, 'user3'),
                                                       ('like4', 'board4', NULL, 'user4'),
                                                       ('like5', 'board5', NULL, 'user5'),
                                                       ('like6', NULL, 'comment1', 'user1'),
                                                       ('like7', NULL, 'comment2', 'user2'),
                                                       ('like8', NULL, 'comment3', 'user3'),
                                                       ('like9', NULL, 'comment4', 'user4'),
                                                       ('like10', NULL, 'comment5', 'user5');

-- Star 데이터 삽입
INSERT INTO stars (star_id, labId, starCount, userId) VALUES
                                                          ('star1', 'lab1', 5, 'user1'),
                                                          ('star2', 'lab2', 4, 'user2');
