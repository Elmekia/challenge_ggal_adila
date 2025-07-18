-- Usuarios
INSERT INTO CHALLENGE.USUARIO (ID, NOMBRE, PASSWORD, ROLE) VALUES (1, 'matias', '$2a$10$6BsUzCJJ0H9We7mxOOeh/uUHAEK3qrv.ho9O4AIuIIjzTNy18X2/O', 'ADMIN');
INSERT INTO CHALLENGE.USUARIO (ID, NOMBRE, PASSWORD, ROLE) VALUES (2, 'linda', '$2a$10$j9ZyvxQmnqjrWxE6Kw4wxep4pjNK/90GzIJCO/9mTI8F3oFA9PbyK', 'ADMIN');
INSERT INTO CHALLENGE.USUARIO (ID, NOMBRE, PASSWORD, ROLE) VALUES (3, 'pepe', '$2a$10$HrZ3eZNGAhd6wGuk3vUjoOs9qhM3prqGXJZHC9xeeKf0shV0kKNZe', 'ADMIN');
-- Categorías
INSERT INTO CHALLENGE.CATEGORIA (ID, NOMBRE) VALUES (1, 'Electrónica');
INSERT INTO CHALLENGE.CATEGORIA (ID, NOMBRE) VALUES (2, 'Libros');
INSERT INTO CHALLENGE.CATEGORIA (ID, NOMBRE) VALUES (3, 'Ropa');

-- Productos
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (1, 'Smartphone', 1, 450000, 50000);
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (2, 'Laptop', 1, 1200000, 10);
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (3, 'Novela', 2, 18000, 40);
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (4, 'Remera', 3, 25000, 100);
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (5, 'Jeans', 3, 55000, 50);

-- Descuentos
INSERT INTO CHALLENGE.CATEGORIA_DESCUENTO (ID, CATEGORIA_ID, PORCENTAJE) VALUES (1, 1, 10);
INSERT INTO CHALLENGE.CATEGORIA_DESCUENTO (ID, CATEGORIA_ID, PORCENTAJE) VALUES (2, 2, 20);