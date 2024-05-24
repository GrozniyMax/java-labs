-- CREATE TABLES
--USERS
CREATE TABLE users(
    ID SERIAL PRIMARY KEY,
    user_login varchar(20) UNIQUE,
    user_password bytea
);

--Coordinates
CREATE TABLE coordinates(
                            ID SERIAL ,
                            x INTEGER NOT NULL ,
                            y FLOAT NOT NULL,
                            PRIMARY KEY (x,y),
                            CHECK ( x<=606 )

);

--House
CREATE TABLE house(
    ID SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    year INTEGER NOT NULL,
    number_of_floors INTEGER NOT NULL,
    CHECK ( year>0 AND number_of_floors>0)
);

--ENUMS
CREATE TYPE furnish AS ENUM('DESIGNER','NONE','LITTLE');
CREATE TYPE view AS ENUM('STREET','YARD','PARK','BAD','GOOD');
CREATE TYPE transport AS ENUM('NONE','LITTLE','NORMAL','ENOUGH');

--FLAT
CREATE TABLE flat (
    ID BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    coordinated_ID INTEGER REFERENCES coordinates(ID) NOT NULL,
    creation_Date TIMESTAMP NOT NULL,
    area INTEGER NOT NULL,
    number_of_rooms BIGINT NOT NULL,
    furnish furnish NOT NULL,
    view view NOT NULL,
    transport transport NOT NULL ,
    house_id INTEGER REFERENCES house(ID) NOT NULL,
    owner INTEGER REFERENCES users(ID) NOT NULL ,
    check (( 0<area AND area<=745) AND number_of_rooms>0)
);
