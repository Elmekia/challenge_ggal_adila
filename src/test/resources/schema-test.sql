CREATE SCHEMA IF NOT EXISTS CHALLENGE;

CREATE TABLE CHALLENGE.USUARIO (
  ID BIGINT PRIMARY KEY AUTO_INCREMENT,
  NOMBRE VARCHAR(25),
  PASSWORD varchar(60),
  ROLE varchar(200)
);

CREATE TABLE CHALLENGE.CATEGORIA (
  ID BIGINT PRIMARY KEY AUTO_INCREMENT,
  NOMBRE VARCHAR(25)
);

CREATE TABLE CHALLENGE.PRODUCTO (
  ID BIGINT PRIMARY KEY AUTO_INCREMENT,
  NOMBRE VARCHAR(25),
  CATEGORIA_ID BIGINT,
  precio DECIMAL(10, 2),
  stock BIGINT,
  FOREIGN KEY (CATEGORIA_ID) REFERENCES CATEGORIA(id)
);

CREATE TABLE CHALLENGE.CARRITO (
  ID BIGINT PRIMARY KEY AUTO_INCREMENT,
  USUARIO_ID BIGINT,
  estado VARCHAR(20) DEFAULT 'ACTIVO',
  total DECIMAL(10,2) DEFAULT 0,
  FOREIGN KEY (USUARIO_ID) REFERENCES USUARIO(id)
);

CREATE TABLE CHALLENGE.CARRITO_PRODUCTO (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    carrito_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT,
    UNIQUE (carrito_id, producto_id),
    FOREIGN KEY (carrito_id) REFERENCES carrito(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

CREATE TABLE CHALLENGE.CATEGORIA_DESCUENTO (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    categoria_id BIGINT NOT NULL,
    porcentaje DECIMAL(5,2),
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);


