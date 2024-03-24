-- liquibase formatted sql

-- changeset fill tables:N.Semeniuk
INSERT INTO "metadata" ("name", artist, album, length, resource_id, "year")
VALUES
('Grape', 'Alexandr Soloduha', 'Songs of our youth', '00:03:00', 1, 2000),
('Grape', 'Nesoloduha', 'Best songs', '03:00:00', 2, 2000);
