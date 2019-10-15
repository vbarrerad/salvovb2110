INSERT INTO PLAYER (first_name, last_name, user_name) VALUES ('Jack', 'Bauer', 'j.bauer@ctu.gov')
INSERT INTO PLAYER (first_name, last_name, user_name) VALUES ('Chloe', 'O´Brian', 'j.bauer@ctu.gov')
INSERT INTO PLAYER (first_name, last_name, user_name) VALUES ('Kim', 'Bauer', 'kim_bauer@gmail.com')
INSERT INTO PLAYER (first_name, last_name, user_name) VALUES ('Tony', 'Almeida', 't.almeida@ctu.gov')

INSERT INTO GAME (creation_date) VALUES ('2019-09-14 10:15:30.072+03:00')
INSERT INTO GAME (creation_date) VALUES ('2019-09-14 11:15:30.072+03:00')
INSERT INTO GAME (creation_date) VALUES ('2019-09-14 12:15:30.072+03:00')

INSERT INTO GAME_PLAYER (id, game_id, player_id, join_date) VALUES ('1','2','3','2019-09-14 10:15:30.072+03:00')
INSERT INTO GAME_PLAYER (id, player_id, game_id, join_date) VALUES ('2','3','1','2019-09-15 10:15:30.072+03:00')
INSERT INTO GAME_PLAYER (id, player_id, game_id, join_date) VALUES ('3','1','2','2019-09-16 10:15:30.072+03:00')


INSERT INTO SHIP (type, game_player_id) VALUES ('cruise', '1')
INSERT INTO SHIP (type, game_player_id) VALUES ('submarine', '1')

INSERT INTO SHIP (ship_id, locations) VALUES ('1','a1')
INSERT INTO SHIP (ship_id, locations) VALUES ('1','a2')
INSERT INTO SHIP (ship_id, locations) VALUES ('1','a3')
INSERT INTO SHIP (ship_id, locations) VALUES ('1','c3')
INSERT INTO SHIP (ship_id, locations) VALUES ('1','c4')