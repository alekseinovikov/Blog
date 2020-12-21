ALTER TABLE posts
    ADD COLUMN created_at timestamptz NOT NULL DEFAULT NOW(),
    ADD COLUMN updated_at timestamptz NOT NULL DEFAULT NOW();

CREATE TRIGGER posts_updated_at_trigger
    BEFORE UPDATE ON posts
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_updated_at();
