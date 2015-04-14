DROP TABLE ClientTable;

CREATE TABLE ClientTable (Email VARCHAR(200), Address VARCHAR(200), Client_ID VARCHAR(200), PRIMARY KEY (Client_ID));

INSERT INTO ClientTable (Email, Address, Client_ID) VALUES ('Client_0@blah.com','Client_0 Address','Client_0'),('Client_1@blah.com','Client_1 Address','Client_1'),('Client_2@blah.com','Client_2 Address','Client_2'),('Client_3@blah.com','Client_3 Address','Client_3'),('Client_4@blah.com','Client_4 Address','Client_4'),('Client_5@blah.com','Client_5 Address','Client_5'),('Client_6@blah.com','Client_6 Address','Client_6'),('Client_7@blah.com','Client_7 Address','Client_7'),('Client_8@blah.com','Client_8 Address','Client_8'),('Client_9@blah.com','Client_9 Address','Client_9');
