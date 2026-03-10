-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "paproject" database.
-- -----------------------------------------------------------------------------
INSERT INTO Room (name, capacity) VALUES ('Sala 1', 100);

-- FUNC-1
INSERT INTO Movie (title, summary, durationMinutes)
VALUES ('Dune', 'Paul Atreides viaja al planeta más peligroso del universo.', 155);
INSERT INTO Movie (title, summary, durationMinutes)
VALUES ('Star Wars', 'La lucha entre el bien y el mal en una galaxia lejana.', 121);

INSERT INTO Session (movieId, roomId, startTime, price)
VALUES (1, 1, '2026-03-04 21:00:00', 8.50);
INSERT INTO Session (movieId, roomId, startTime, price)
VALUES (2, 1, '2026-03-04 22:30:00', 7.00);

