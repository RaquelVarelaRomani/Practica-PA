DROP TABLE IF EXISTS Purchase;
DROP TABLE IF EXISTS Session;
DROP TABLE IF EXISTS Movie;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS User;

CREATE TABLE User (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
                      password VARCHAR(60) NOT NULL,
                      firstName VARCHAR(60) NOT NULL,
                      lastName VARCHAR(60) NOT NULL,
                      email VARCHAR(60) NOT NULL,
                      role VARCHAR(20) NOT NULL,
                      CONSTRAINT UserPK PRIMARY KEY (id),
                      CONSTRAINT UserNameUniqueKey UNIQUE (userName)
) ENGINE = InnoDB;

CREATE INDEX UserIndexByUserName ON User (userName);

CREATE TABLE Room (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(60) NOT NULL,
                      capacity INT NOT NULL,
                      CONSTRAINT RoomPK PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE Movie (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       title VARCHAR(120) NOT NULL,
                       summary VARCHAR(500) NOT NULL,
                       durationMinutes INT NOT NULL,
                       CONSTRAINT MoviePK PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE Session (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         movieId BIGINT NOT NULL,
                         roomId BIGINT NOT NULL,
                         startTime DATETIME NOT NULL,
                         price DECIMAL(10,2) NOT NULL,
                         availableTickets INT NOT NULL,
                         version BIGINT NOT NULL DEFAULT 0,
                         CONSTRAINT SessionPK PRIMARY KEY (id),
                         CONSTRAINT SessionMovieIdFK FOREIGN KEY (movieId) REFERENCES Movie (id),
                         CONSTRAINT SessionRoomIdFK FOREIGN KEY (roomId) REFERENCES Room (id)
) ENGINE = InnoDB;

CREATE TABLE Purchase (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          purchaseDate DATETIME NOT NULL,
                          numberTickets INT NOT NULL,
                          creditCardNumber VARCHAR(19) NOT NULL,
                          delivered BOOLEAN NOT NULL DEFAULT FALSE,
                          userId BIGINT NOT NULL,
                          sessionId BIGINT NOT NULL,
                          CONSTRAINT PurchasePK PRIMARY KEY (id),
                          CONSTRAINT PurchaseUserIdFK FOREIGN KEY (userId) REFERENCES User (id),
                          CONSTRAINT PurchaseSessionIdFK FOREIGN KEY (sessionId) REFERENCES Session (id)
) ENGINE = InnoDB;
