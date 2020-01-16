CREATE TABLE IF NOT EXISTS users (
	username VARCHAR(64) PRIMARY KEY,
	email VARCHAR(64),
	password VARCHAR(64)
);
CREATE TABLE IF NOT EXISTS score (
	username VARCHAR(64) NOT NULL,
	highestWeekScore int(64) NOT NULL,
	scoreA int(64) NOT NULL,
	FOREIGN KEY (username) REFERENCES users(username)
);
CREATE TABLE IF NOT EXISTS games (
	username VARCHAR(64) NOT NULL,
	gamesP int(64) NOT NULL,
	highestL int(64) NOT NULL,
	FOREIGN KEY (username) REFERENCES users(username)
);
CREATE TABLE IF NOT EXISTS badges (
	username VARCHAR(64) NOT NULL,
	award VARCHAR(64) NOT NULL
);