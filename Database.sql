/*
DROP DATABASE toilet_finder;
*/

CREATE DATABASE toilet_finder;

USE toilet_finder;

CREATE TABLE toilet (
	id int auto_increment UNIQUE PRIMARY Key NOT NULL,
	name VARCHAR(200),
    latitude double,
    longitude double
);

CREATE TABLE toilet_info (
	id int auto_increment UNIQUE PRIMARY KEY NOT NULL,
    toilet_id int NOT NULL,
    rating int DEFAULT 0,
    amt_of_rating int DEFAULT 0,
    genderM boolean NOT NULL
);

CREATE TABLE toilet_request (
	id int auto_increment UNIQUE PRIMARY KEY NOT NULL,
    latitude double NOT NULL,
    longitude double NOT NULL,
    approval int DEFAULT 0,
    rating int DEFAULT 0,
    amt_of_rating int DEFAULT 0,
    genderM boolean,
    removal_flags int DEFAULT 0
);