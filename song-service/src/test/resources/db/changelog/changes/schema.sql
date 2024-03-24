-- liquibase formatted sql

-- changeset create tables:N.Semeniuk
CREATE TABLE IF NOT EXISTS "metadata" (
    metadata_id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    artist VARCHAR(255),
    album VARCHAR(255),
    length VARCHAR(255),
    resource_id BIGINT NOT NULL UNIQUE,
    "year" INTEGER
);

CREATE INDEX idx_artist_name ON "metadata" (artist);
CREATE UNIQUE INDEX idx_name_artist ON "metadata" ("name", artist);