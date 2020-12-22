ALTER TABLE posts
    ADD COLUMN preview VARCHAR(1024) NOT NULL DEFAULT '';

ALTER TABLE posts
    ALTER COLUMN preview DROP DEFAULT,
    ALTER COLUMN title DROP DEFAULT,
    ALTER COLUMN content DROP DEFAULT;

CREATE INDEX ix_posts_created_at_desc ON posts (created_at DESC);
