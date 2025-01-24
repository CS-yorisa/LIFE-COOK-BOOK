-- 더미 데이터 삽입 (User 테이블)
INSERT INTO "users" (nickname, email, password)
VALUES
    ('john_doe', 'john@example.com', 'password123'),
    ('jane_doe', 'jane@example.com', 'password456'),
    ('alice_smith', 'alice@example.com', 'password789');

-- 더미 데이터 삽입 (Group 테이블)
INSERT INTO "groups" (group_name, limit_person, group_password)
VALUES
    ('Group A', 10, '1234'),
    ('Group B', 5, 'abcd'),
    ('Group C', 8, NULL);

-- 더미 데이터 삽입 (GroupMapping 테이블)
INSERT INTO "group_mapping" (group_id, user_id) VALUES
  ((SELECT group_id FROM "groups" WHERE group_name = 'Group A' LIMIT 1),
   (SELECT id FROM "users" WHERE nickname = 'john_doe' LIMIT 1)),
  ((SELECT group_id FROM "groups" WHERE group_name = 'Group A' LIMIT 1),
   (SELECT id FROM "users" WHERE nickname = 'jane_doe' LIMIT 1)),
  ((SELECT group_id FROM "groups" WHERE group_name = 'Group B' LIMIT 1),
   (SELECT id FROM "users" WHERE nickname = 'alice_smith' LIMIT 1));