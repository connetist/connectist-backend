-- Inserting initial data into labs table
INSERT INTO labs (lab_id, professor, contents, like_sum, created_at)
VALUES
    (UUID(), 'Prof. John Doe', 'Research on AI', 10, UNIX_TIMESTAMP()),
    (UUID(), 'Prof. Jane Smith', 'Quantum Computing', 5, UNIX_TIMESTAMP());

-- Inserting initial data into boards table
INSERT INTO boards (board_id, user_id, lab_id, contents, deleted, created_at, deleted_at)
VALUES
    (UUID(), 'user1', (SELECT lab_id FROM labs LIMIT 1 OFFSET 0), 'Board content 1', 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP()),
    (UUID(), 'user2', (SELECT lab_id FROM labs LIMIT 1 OFFSET 1), 'Board content 2', 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP());

-- Inserting initial data into comments table
INSERT INTO comments (comment_id, board_id, user_id, contents, deleted, created_at, deleted_at)
VALUES
    (UUID(), (SELECT board_id FROM boards LIMIT 1 OFFSET 0), 'user3', 'Comment on board 1', 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP()),
    (UUID(), (SELECT board_id FROM boards LIMIT 1 OFFSET 1), 'user4', 'Comment on board 2', 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP());

-- Inserting initial data into likes table
INSERT INTO likes (id, board_id, comment_id,like_unit)
VALUES
    (UUID(), (SELECT board_id FROM boards LIMIT 1 OFFSET 0), NULL,5),
    (UUID(), NULL, (SELECT comment_id FROM comments LIMIT 1 OFFSET 0),5),
    (UUID(), NULL, NULL,3);

-- Inserting initial data into recomments table
INSERT INTO recomments (recomment_id, comment_id, user_id, contents, deleted, created_at, deleted_at)
VALUES
    (UUID(), (SELECT comment_id FROM comments LIMIT 1 OFFSET 0), 'user5', 'Recomment on comment 1', 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP());
