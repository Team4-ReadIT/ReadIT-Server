INSERT INTO mindmap (article_id, user_id, content, parent_id, status, created_at, updated_at)
VALUES
    (101, 1, '상위1', NULL, 'ACTIVE', '2024-11-07', '2024-11-07'),
    (101, 1, '중위1', 1, 'ACTIVE', '2024-11-07', '2024-11-07'),
    (101, 1, '하위1', 2, 'ACTIVE', '2024-11-07', '2024-11-07'),
    (101, 1, '하위2', 2, 'ACTIVE', '2024-11-07', '2024-11-07'),
    (101, 1, '중위2', 1, 'ACTIVE', '2024-11-07', '2024-11-07');
