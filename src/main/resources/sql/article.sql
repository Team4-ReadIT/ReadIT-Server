-- Article 데이터 삽입
INSERT INTO article (title, pub_date, source, summary, article_link, scrap_count, view_count, img_url, created_at, updated_at, status) VALUES
                                                                                                                                           ('트렌드한 기술', '2024-10-30 10:00:00', 'https://example.com/source1.jpg', '2024년 최신 기술 트렌드에 대해 소개하는 기사', 'https://example.com/article1', 2, 2, 'https://example.com/img1.jpg', NOW(), NOW(), 'ACTIVE'),
                                                                                                                                           ('프로그래밍 팁', '2024-10-31 14:00:00', 'https://example.com/source2.jpg', '프로그래밍을 배우는 사람들을 위한 유용한 팁을 제공합니다.', 'https://example.com/article2', 1, 2, 'https://example.com/img2.jpg', NOW(), NOW(), 'ACTIVE'),
                                                                                                                                           ('디자인 트렌드', '2024-11-01 16:00:00', 'https://example.com/source3.jpg', '디자인 분야에서 중요한 트렌드에 대한 기사', 'https://example.com/article3', 1, 1, 'https://example.com/img3.jpg', NOW(), NOW(), 'ACTIVE');

-- Scrap 데이터 삽입
INSERT INTO scrap (user_id, article_id, created_at, updated_at, status) VALUES
                                                                            (1, 1, '2024-10-30 10:30:00', '2024-11-02 10:30:00', 'ACTIVE'),
                                                                            (1, 2, '2024-11-01 12:00:00', '2024-11-06 12:00:00', 'ACTIVE'),
                                                                            (1, 3, '2024-11-05 13:00:00', '2024-11-11 13:00:00', 'ACTIVE'),
                                                                            (2, 1, '2024-10-26 15:30:00', '2024-11-07 15:30:00', 'ACTIVE');

-- ArticleView 데이터 삽입
INSERT INTO article_view (job_id, article_id, created_at, updated_at, status) VALUES
                                                                                  (5, 1, '2024-10-31 10:30:00', '2024-11-01 10:30:00', 'ACTIVE'),
                                                                                  (5, 2, '2024-11-01 14:30:00', '2024-11-05 14:30:00', 'ACTIVE'),
                                                                                  (5, 3, '2024-11-06 16:30:00', '2024-11-10 16:30:00', 'ACTIVE'),
                                                                                  (6, 1, '2024-11-05 16:30:00', '2024-11-10 16:30:00', 'ACTIVE'),
                                                                                  (6, 2, '2024-11-04 16:30:00', '2024-11-10 16:30:00', 'ACTIVE'),

-- Keyword 데이터 삽입
insert into keyword (img_url, created_at, updated_at, status) values
                                                                 ("img url입니다.",now(), now(), 'ACTIVE');
insert into keyword (img_url, created_at, updated_at, status) values
    ("img url입니다2",now(), now(), 'ACTIVE');

INSERT INTO highlight (article_id, user_id, memo_text, start_index, end_index, status, created_at, updated_at)
VALUES (1, 1, '메모메모.', 0, 10, 'ACTIVE', NOW(), NOW());

INSERT INTO highlight (article_id, user_id, memo_text, start_index, end_index, status, created_at, updated_at)
VALUES (1, 1, '알아야 하는 워딩', 14, 17, 'ACTIVE', NOW(), NOW());

INSERT INTO highlight (article_id, user_id, memo_text, start_index, end_index, status, created_at, updated_at)
VALUES (1, 2, null, 20, 35, 'ACTIVE', NOW(), NOW());

INSERT INTO mindmap (article_id, user_id, content, parent_id, status, created_at, updated_at)
VALUES
    (1, 1, '상위1', NULL, 'ACTIVE', '2024-11-07 10:30:00', '2024-11-07 10:30:00'),
    (1, 1, '중위1', 1, 'ACTIVE', '2024-11-07 10:30:00', '2024-11-07 10:30:00'),
    (1, 1, '하위1', 2, 'ACTIVE', '2024-11-07 10:30:00', '2024-11-07 10:30:00'),
    (1, 1, '하위2', 2, 'ACTIVE', '2024-11-07 10:30:00', '2024-11-07 10:30:00'),
    (1, 1, '중위2', 1, 'ACTIVE', '2024-11-07 10:30:00', '2024-11-07 10:30:00');