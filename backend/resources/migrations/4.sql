ALTER TABLE posts
    ADD COLUMN title   varchar(512) NOT NULL DEFAULT '',
    ADD COLUMN content TEXT         NOT NULL DEFAULT '';
