SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE Purchase;
TRUNCATE TABLE Session;
TRUNCATE TABLE Movie;
TRUNCATE TABLE Room;
TRUNCATE TABLE User;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------------------------------------------------------
-- Datos de prueba para la base de datos "paproject"
-- -----------------------------------------------------------------------------

-- Usuarios: viewer (VIEWER) y ticketseller (TICKET_SELLER), password "pa2526"
INSERT INTO User (userName, password, firstName, lastName, email, role)
VALUES ('viewer', '$2a$10$v.js2jCaX3xoKvkR6E2pbugMmZDBPlCAz2gA7EOIZhbkvsPFew/5u',
        'Viewer', 'User', 'viewer@udc.es', 'VIEWER');
INSERT INTO User (userName, password, firstName, lastName, email, role)
VALUES ('ticketseller', '$2a$10$v.js2jCaX3xoKvkR6E2pbugMmZDBPlCAz2gA7EOIZhbkvsPFew/5u',
        'Ticket', 'Seller', 'ticketseller@udc.es', 'TICKET_SELLER');

-- Salas: una con 9 butacas y otra con mas de 10
INSERT INTO Room (name, capacity) VALUES ('Sala 1', 50);
INSERT INTO Room (name, capacity) VALUES ('Sala 2', 9);

-- Peliculas
INSERT INTO Movie (title, summary, durationMinutes)
VALUES ('Dune', 'Paul Atreides viaja al planeta más peligroso del universo.', 155);
INSERT INTO Movie (title, summary, durationMinutes)
VALUES ('Star Wars', 'La lucha entre el bien y el mal en una galaxia lejana.', 121);

-- Sesiones de hoy:
-- Sesion 1: hoy a las 00:05 (pelicula 1, sala 1, 50 butacas disponibles)
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (1, 1, DATE_ADD(DATE(NOW()), INTERVAL '0 00:05' DAY_MINUTE), 8.50, 50);
-- Sesion 2: hoy a las 23:55 (pelicula 2, sala 2, 9 butacas disponibles)
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (2, 2, DATE_ADD(DATE(NOW()), INTERVAL '0 23:55' DAY_MINUTE), 7.00, 9);

-- Sesiones para los proximos 6 dias (2 por dia, alternando pelicula/sala)
-- Dia 1
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (1, 2, DATE_ADD(DATE(NOW()), INTERVAL '1 18:00' DAY_MINUTE), 8.50, 9);
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (2, 1, DATE_ADD(DATE(NOW()), INTERVAL '1 21:00' DAY_MINUTE), 7.00, 50);
-- Dia 2
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (2, 1, DATE_ADD(DATE(NOW()), INTERVAL '2 18:00' DAY_MINUTE), 7.00, 50);
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (1, 2, DATE_ADD(DATE(NOW()), INTERVAL '2 21:00' DAY_MINUTE), 8.50, 9);
-- Dia 3
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (1, 1, DATE_ADD(DATE(NOW()), INTERVAL '3 18:00' DAY_MINUTE), 8.50, 50);
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (2, 2, DATE_ADD(DATE(NOW()), INTERVAL '3 21:00' DAY_MINUTE), 7.00, 9);
-- Dia 4
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (2, 2, DATE_ADD(DATE(NOW()), INTERVAL '4 18:00' DAY_MINUTE), 7.00, 9);
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (1, 1, DATE_ADD(DATE(NOW()), INTERVAL '4 21:00' DAY_MINUTE), 8.50, 50);
-- Dia 5
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (1, 2, DATE_ADD(DATE(NOW()), INTERVAL '5 18:00' DAY_MINUTE), 8.50, 9);
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (2, 1, DATE_ADD(DATE(NOW()), INTERVAL '5 21:00' DAY_MINUTE), 7.00, 50);
-- Dia 6
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (2, 1, DATE_ADD(DATE(NOW()), INTERVAL '6 18:00' DAY_MINUTE), 7.00, 50);
INSERT INTO Session (movieId, roomId, startTime, price, availableTickets)
VALUES (1, 2, DATE_ADD(DATE(NOW()), INTERVAL '6 21:00' DAY_MINUTE), 8.50, 9);

-- 2 compras asociadas a la sesion 1 (hoy a las 00:05, sala 1 con 50 butacas)
-- Compra del viewer: 3 entradas
INSERT INTO Purchase (purchaseDate, numberTickets, creditCardNumber, delivered, userId, sessionId)
VALUES (DATE_ADD(DATE(NOW()), INTERVAL '0 00:01' DAY_MINUTE), 3, '1234567890123456', false, 1, 1);
-- Compra del viewer: 2 entradas
INSERT INTO Purchase (purchaseDate, numberTickets, creditCardNumber, delivered, userId, sessionId)
VALUES (DATE_ADD(DATE(NOW()), INTERVAL '0 00:02' DAY_MINUTE), 2, '1234567890123456', false, 1, 1);
-- Butacas disponibles sesion 1: 50 - 3 - 2 = 45
