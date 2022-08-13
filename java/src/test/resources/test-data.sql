BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');

insert into user_accounts (user_id, f_name, l_name, email, phone, flagged)
    values (1, 'A', 'A', 'a@email.com', 1111111111, false);

insert into user_accounts (user_id, f_name, l_name, email, phone, flagged)
    values (2, 'B', 'B', 'b@email.com', 2222222222, false);

insert into user_accounts (user_id, f_name, l_name, email, phone, flagged)
    values (3, 'C', 'C', 'c@email.com', 3333333333, false);

INSERT INTO images (image_name, image_type, image_data) 
    VALUES ('test', '.jpg', E'\x7f\x7f');
INSERT INTO images (image_name, image_type, image_data) 
    VALUES ('test2', '.jpg', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));
INSERT INTO images (image_name, image_type, image_data) 
    VALUES ('test3', '.png', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));
INSERT INTO images (image_name, image_type, image_data) 
    VALUES ('test4', '.png', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));

insert into categories (category_desc) values ('a');
insert into categories (category_desc) values ('b');
insert into categories (category_desc) values ('c');

insert into statuses (status_desc) values ('a');
insert into statuses (status_desc) values ('b');
insert into statuses (status_desc) values ('b');



INSERT INTO potholes(pothole_id, lat, long, pothole_name, account_id, image_id, category_id, status_id, active, upload_time)
VALUES (1, 41.40338, 2.1740, 'Test 1', 1, 1, 1, 1, true, '2022-08-09 05:13:20');

INSERT INTO potholes(pothole_id, lat, long, pothole_name, account_id, image_id, category_id, status_id, active, upload_time)
VALUES (2, 42.40338,2.17403, 'Test 2', 2, 2, 1, 1, true, '2022-08-09 05:13:20');

INSERT INTO potholes(pothole_id, lat, long, pothole_name, account_id, image_id, category_id, status_id, active, upload_time)
VALUES (3, 43.40338,2.27403, 'Test 3', 3, 3, 2, 1, true, '2022-08-09 05:13:20');

INSERT INTO potholes(pothole_id, lat, long, pothole_name, account_id, image_id, category_id, status_id, active, upload_time)
VALUES (4, 44.40338,3.17403, 'Test 4', 3, 4, 2, 1, true, '2022-08-09 05:13:20');

COMMIT TRANSACTION;
