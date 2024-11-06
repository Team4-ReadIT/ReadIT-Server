-- Job 데이터 삽입
INSERT INTO job (parent_id, job_name, created_at, updated_at, status) VALUES
                                                                          (NULL, '개발', NOW(), NOW(), 'ACTIVE'),
                                                                          (NULL, '디자인', NOW(), NOW(), 'ACTIVE'),
                                                                          (NULL, '기획', NOW(), NOW(), 'ACTIVE'),
                                                                          (NULL, '비즈니스', NOW(), NOW(), 'ACTIVE'),

-- 개발 하위 직무
                                                                          (1, '백엔드개발자', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '프론트엔드개발자', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '웹개발자', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '앱개발자', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '시스템엔지니어', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '네트워크엔지니어', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, 'DBA (데이터베이스 관리자)', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '데이터엔지니어', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '데이터사이언티스트', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '보안엔지니어', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '소프트웨어개발자', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '게임개발자', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '하드웨어개발자', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '머신러닝엔지니어', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '블록체인개발자', NOW(), NOW(), 'ACTIVE'),
                                                                          (1, '클라우드엔지니어', NOW(), NOW(), 'ACTIVE'),

-- 디자인 하위 직무
                                                                          (2, '일러스트레이터', NOW(), NOW(), 'ACTIVE'),
                                                                          (2, 'UI/UX 디자이너', NOW(), NOW(), 'ACTIVE'),
                                                                          (2, '웹퍼블리셔', NOW(), NOW(), 'ACTIVE'),

-- 기획 하위 직무
                                                                          (3, '서비스 기획자', NOW(), NOW(), 'ACTIVE'),
                                                                          (3, 'PM(프로덕트 매니저)', NOW(), NOW(), 'ACTIVE'),
                                                                          (3, 'IT컨설팅', NOW(), NOW(), 'ACTIVE'),
                                                                          (3, 'QA (품질 보증)', NOW(), NOW(), 'ACTIVE'),

-- 비즈니스 하위 직무
                                                                          (4, '경영전략/사업기회 전문가', NOW(), NOW(), 'ACTIVE'),
                                                                          (4, '마케팅/홍보 전문가', NOW(), NOW(), 'ACTIVE'),
                                                                          (4, '컨설턴트 전문가', NOW(), NOW(), 'ACTIVE');