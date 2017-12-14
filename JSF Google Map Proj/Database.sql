CREATE DATABASE toilet_finder;

USE toilet_finder;

CREATE TABLE toilet (
	id int auto_increment UNIQUE PRIMARY Key NOT NULL,
	name VARCHAR(200),
    latitude double,
    longitude double
);