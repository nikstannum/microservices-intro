INSERT INTO resources (resource_id, binary_file)
VALUES ((select NEXT VALUE FOR resources_seq), FILE_READ('classpath:songs/Король и Шут - Лесник.mp3'));