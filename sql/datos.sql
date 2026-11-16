-- Datos de prueba para la base discografica.
-- Ejecutar DESPUES de schema.sql. Se puede correr varias veces:
-- vacia las tablas y reinicia los AUTO_INCREMENT antes de insertar.

USE discografica;
SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE cancion;
TRUNCATE TABLE banda_integrante;
TRUNCATE TABLE lanzamiento;
TRUNCATE TABLE contrato;
TRUNCATE TABLE artista;
SET FOREIGN_KEY_CHECKS = 1;

-- ---------------------------------------------------------------
-- Artistas
-- ---------------------------------------------------------------
INSERT INTO artista (id, tipo, nombre, pais, estado, nombre_real) VALUES
    (1, 'SOLISTA', 'Andrés Calamaro',    'Argentina',      'ACTIVO', 'Andrés Calamaro Masel'),
    (2, 'SOLISTA', 'Paul McCartney',     'Reino Unido',    'ACTIVO', 'James Paul McCartney'),
    (3, 'SOLISTA', 'Adele',              'Reino Unido',    'ACTIVO', 'Adele Laurie Blue Adkins'),
    (4, 'BANDA',   'The Rolling Stones', 'Reino Unido',    'ACTIVO', NULL),
    (5, 'BANDA',   'Pearl Jam',          'Estados Unidos', 'ACTIVO', NULL);

-- Integrantes (solo para artistas tipo BANDA), formacion actual
INSERT INTO banda_integrante (artista_id, nombre) VALUES
    (4, 'Mick Jagger'),
    (4, 'Keith Richards'),
    (4, 'Ronnie Wood'),
    (5, 'Eddie Vedder'),
    (5, 'Stone Gossard'),
    (5, 'Jeff Ament'),
    (5, 'Mike McCready'),
    (5, 'Matt Cameron');

-- ---------------------------------------------------------------
-- Contratos: uno de cada estado para probar estaVigente(),
-- actualizarEstado() y rescindir().
-- ---------------------------------------------------------------
INSERT INTO contrato (id, artista_id, fecha_inicio, fecha_fin, porcentaje_regalias, estado) VALUES
    (1, 1, '2025-01-01', '2027-12-31', 15.00, 'VIGENTE'),
    (2, 3, '2024-06-01', '2028-05-31', 20.00, 'VIGENTE'),
    (3, 4, '2026-03-01', '2029-02-28', 25.00, 'VIGENTE'),
    (4, 2, '2018-01-01', '2024-12-31', 18.00, 'VENCIDO'),     -- McCartney: sin contrato vigente
    (5, 5, '2023-01-01', '2026-12-31', 12.50, 'RESCINDIDO');  -- Pearl Jam: rescindido antes de tiempo

-- ---------------------------------------------------------------
-- Lanzamientos
-- ---------------------------------------------------------------
INSERT INTO lanzamiento (id, artista_id, titulo, fecha, precio, unidades_vendidas, tipo, genero) VALUES
    (1,  1, 'Flaca',                          '1997-06-01',  1.49, 15000, 'SINGLE', 'ROCK'),
    (2,  1, 'Alta Suciedad',                  '1997-06-01',  9.99,  8000, 'ALBUM',  'ROCK'),
    (3,  2, 'Come On to Me',                  '2018-06-20',  1.29,  6000, 'SINGLE', 'ROCK'),
    (4,  2, 'McCartney',                      '1970-04-17', 12.99,  4500, 'ALBUM',  'ROCK'),
    (5,  3, 'Hello',                          '2015-10-23',  1.99, 30000, 'SINGLE', 'POP'),
    (6,  3, '21',                             '2011-01-24', 11.99, 22000, 'ALBUM',  'POP'),
    (7,  3, 'Easy On Me',                     '2021-10-15',  1.99,     0, 'SINGLE', 'POP'), -- sin ventas: ingresos = 0
    (8,  4, '(I Can''t Get No) Satisfaction', '1965-06-06',  1.49, 25000, 'SINGLE', 'ROCK'),
    (9,  4, 'Sticky Fingers',                 '1971-04-23', 12.99, 10000, 'ALBUM',  'ROCK'),
    (10, 5, 'Alive',                          '1991-07-07',  1.29,  9000, 'SINGLE', 'ROCK'),
    (11, 5, 'Ten',                            '1991-08-27', 10.99, 14000, 'ALBUM',  'ROCK');

-- ---------------------------------------------------------------
-- Canciones: un SINGLE tiene exactamente 1, un ALBUM tiene varias.
-- ---------------------------------------------------------------
INSERT INTO cancion (lanzamiento_id, titulo, duracion_seg) VALUES
    -- Singles
    (1,  'Flaca',                          277),
    (3,  'Come On to Me',                  251),
    (5,  'Hello',                          295),
    (7,  'Easy On Me',                     224),
    (8,  '(I Can''t Get No) Satisfaction', 223),
    (10, 'Alive',                          341),
    -- Album 2: Alta Suciedad (Andrés Calamaro)
    (2,  'Alta Suciedad',                  230),
    (2,  'Todo lo Demás',                  240),
    (2,  'Flaca',                          277),
    (2,  'Loco',                           207),
    (2,  'Donde Manda Marinero',           190),
    (2,  'Media Verónica',                 196),
    -- Album 4: McCartney (Paul McCartney)
    (4,  'The Lovely Linda',                43),
    (4,  'That Would Be Something',        158),
    (4,  'Valentine Day',                   99),
    (4,  'Every Night',                    151),
    (4,  'Junk',                           114),
    (4,  'Maybe I''m Amazed',              233),
    -- Album 6: 21 (Adele)
    (6,  'Rolling in the Deep',            228),
    (6,  'Rumour Has It',                  223),
    (6,  'Turning Tables',                 250),
    (6,  'Don''t You Remember',            243),
    (6,  'Set Fire to the Rain',           242),
    (6,  'Someone Like You',               285),
    -- Album 9: Sticky Fingers (The Rolling Stones)
    (9,  'Brown Sugar',                    228),
    (9,  'Sway',                           232),
    (9,  'Wild Horses',                    342),
    (9,  'Can''t You Hear Me Knocking',    434),
    (9,  'Bitch',                          218),
    (9,  'Dead Flowers',                   243),
    -- Album 11: Ten (Pearl Jam)
    (11, 'Once',                           231),
    (11, 'Even Flow',                      293),
    (11, 'Alive',                          340),
    (11, 'Why Go',                         199),
    (11, 'Black',                          343),
    (11, 'Jeremy',                         318);
