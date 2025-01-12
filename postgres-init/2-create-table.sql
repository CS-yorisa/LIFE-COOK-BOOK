-- User 테이블 생성
CREATE TABLE "users" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nickname VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL
);

-- Group 테이블 생성
CREATE TABLE "groups" (
    group_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_name VARCHAR(200) NOT NULL,
    limit_person INT NOT NULL,
    group_password VARCHAR(4)
);

-- GroupMapping 테이블 생성 (User와 Group의 관계를 나타내는 테이블)
CREATE TABLE "group_mapping" (
    group_id UUID,
    user_id UUID,
    PRIMARY KEY (group_id, user_id),
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES "groups" (group_id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "users" (id) ON DELETE CASCADE
);