BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');

insert into user_accounts (account_id, user_id, f_name, l_name, email, phone, flagged)
    values (Default, 1, 'A', 'A', 'a@email.com', 1111111111, false);

insert into user_accounts (account_id, user_id, f_name, l_name, email, phone, flagged)
    values (Default, 2, 'B', 'B', 'b@email.com', 2222222222, false);

insert into user_accounts (account_id, user_id, f_name, l_name, email, phone, flagged)
    values (Default, 3, 'C', 'C', 'c@email.com', 3333333333, false);

INSERT INTO images (image_id, image_name, image_type, image_data)
    VALUES (Default, 'test', '.jpg', E'\x7f\x7f');
INSERT INTO images (image_id, image_name, image_type, image_data)
    VALUES (Default, 'test2', '.jpg', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));
INSERT INTO images (image_id, image_name, image_type, image_data)
    VALUES (Default, 'test3', '.png', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));
INSERT INTO images (image_id, image_name, image_type, image_data)
    VALUES (Default, 'test4', '.png', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));

insert into categories (category_id, category_desc) values (DEFAULT, 'a');
insert into categories (category_id, category_desc) values (DEFAULT, 'b');
insert into categories (category_id, category_desc) values (DEFAULT, 'c');

insert into statuses (status_id, status_desc) values (DEFAULT, 'a');
insert into statuses (status_id, status_desc) values (DEFAULT, 'b');
insert into statuses (status_id, status_desc) values (DEFAULT, 'b');



INSERT INTO potholes(pothole_id, lat, long, pothole_name, account_id, image_id, category_id, status_id, active, upload_time)
VALUES (DEFAULT, 41.403381, 2.174031, 'Test 1', 1, 1, 1, 1, true, '2022-08-09 05:13:20');

INSERT INTO potholes(pothole_id, lat, long, pothole_name, account_id, image_id, category_id, status_id, active, upload_time)
VALUES (Default, 42.403381, 2.174031, 'Test 2', 2, 2, 1, 1, true, '2022-08-09 05:13:21');

INSERT INTO potholes(pothole_id, lat, long, pothole_name, account_id, image_id, category_id, status_id, active, upload_time)
VALUES (Default, 43.403381, 2.274031, 'Test 3', 3, 3, 2, 1, true, '2022-08-09 05:13:22');

INSERT INTO potholes(pothole_id, lat, long, pothole_name, account_id, image_id, category_id, status_id, active, upload_time)
VALUES (Default, 44.403381, 3.174031, 'Test 4', 3, 4, 2, 1, false, '2022-08-09 05:13:23');

COMMIT TRANSACTION;
