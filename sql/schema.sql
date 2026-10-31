CREATE DATABASE IF NOT EXISTS discografica;

USE discografica;

CREATE USER IF NOT EXISTS 'disco_admin'@'localhost' IDENTIFIED BY 'd1sc04dm1n!';
GRANT ALL PRIVILEGES ON discografica.* TO 'disco_admin'@'localhost';

CREATE TABLE IF NOT EXISTS artista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('SOLISTA','BANDA') NOT NULL,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    pais VARCHAR(60) NOT NULL,
    estado ENUM('ACTIVO','INACTIVO') NOT NULL,
    nombre_real VARCHAR(100) NULL
);

CREATE TABLE IF NOT EXISTS contrato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    artista_id INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    porcentaje_regalias DECIMAL(5,2) NOT NULL,
    estado ENUM('VIGENTE', 'VENCIDO', 'RESCINDIDO') NOT NULL,

    FOREIGN KEY (artista_id) REFERENCES artista(id) ON DELETE RESTRICT,
    CHECK (fecha_fin > fecha_inicio),
    CHECK (porcentaje_regalias BETWEEN 0 AND 100)
);

CREATE TABLE IF NOT EXISTS lanzamiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    artista_id INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    precio DECIMAL(8,2) NOT NULL,
    unidades_vendidas INT DEFAULT 0,
    tipo ENUM('SINGLE','ALBUM') NOT NULL,
    genero ENUM('POP', 'ROCK', 'JAZZ', 'METAL', 'OTRO') NOT NULL,

    FOREIGN KEY (artista_id) REFERENCES artista(id) ON DELETE RESTRICT,
    CHECK (precio >= 0)
);

CREATE TABLE IF NOT EXISTS cancion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    lanzamiento_id INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    duracion_seg INT NOT NULL,

    FOREIGN KEY (lanzamiento_id) REFERENCES lanzamiento(id) ON DELETE CASCADE,
    CHECK (duracion_seg > 0)
);

CREATE TABLE IF NOT EXISTS banda_integrante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    artista_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,

    FOREIGN KEY (artista_id) REFERENCES artista(id) ON DELETE CASCADE
);