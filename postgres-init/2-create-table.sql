CREATE TABLE Users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nickname VARCHAR(30) NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(30) NOT NULL
);