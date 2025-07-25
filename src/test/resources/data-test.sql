-- Usuarios
INSERT INTO CHALLENGE.USUARIO (ID, NOMBRE, PASSWORD, ROLE) VALUES (1, 'test_user1', 'pass1', 'ADMIN');
INSERT INTO CHALLENGE.USUARIO (ID, NOMBRE, PASSWORD, ROLE) VALUES (2, 'test_user2', 'pass2', 'ADMIN');
INSERT INTO CHALLENGE.USUARIO (ID, NOMBRE, PASSWORD, ROLE) VALUES (3, 'test_user3', 'pass3', 'ADMIN');

-- Categorías
INSERT INTO CHALLENGE.CATEGORIA (ID, NOMBRE) VALUES (1, 'Test Category A');
INSERT INTO CHALLENGE.CATEGORIA (ID, NOMBRE) VALUES (2, 'Test Category B');
INSERT INTO CHALLENGE.CATEGORIA (ID, NOMBRE) VALUES (3, 'Test Category C');

-- Productos
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (1, 'Test Product 1', 1, 1000, 10);
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (2, 'Test Product 2', 1, 2000, 20);
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (3, 'Test Product 3', 2, 3000, 30);
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (4, 'Test Product 4', 3, 4000, 40);
INSERT INTO CHALLENGE.PRODUCTO (ID, NOMBRE, CATEGORIA_ID, PRECIO, STOCK) VALUES (5, 'Test Product 5', 3, 5000, 50);

-- Descuentos
INSERT INTO CHALLENGE.CATEGORIA_DESCUENTO (ID, CATEGORIA_ID, PORCENTAJE) VALUES (1, 1, 5);
INSERT INTO CHALLENGE.CATEGORIA_DESCUENTO (ID, CATEGORIA_ID, PORCENTAJE) VALUES (2, 2, 15);

-- Carritos
INSERT INTO CHALLENGE.CARRITO (estado,total,usuario_id) values ('OPEN',0,1);
INSERT INTO CHALLENGE.CARRITO (estado,total,usuario_id) values ('OPEN',0,1);
