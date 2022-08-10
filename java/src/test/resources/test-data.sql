BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');

INSERT INTO user_accounts (user_id, f_name, l_name, email, phone, flagged)
    VALUES (1, 'Forest', 'Forest', 'forest@gmail.com', '1234567890', false);
INSERT INTO user_accounts (user_id, f_name, l_name, email, phone, flagged)
    VALUES (2, 'Jonathan', 'Jonathan', 'jonathan@gmail.com', '1134567890', false);
INSERT INTO user_accounts (user_id, f_name, l_name, email, phone, flagged)
    VALUES (3, 'Tyler', 'Tyler', 'tyler@gmail.com', '1224567890', false);

INSERT INTO images (image_name, image_type, image_data) 
    VALUES ('test', '.jpg', E'\x7f\x7f');
INSERT INTO images (image_name, image_type, image_data) 
    VALUES ('test2', '.jpg', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));
INSERT INTO images (image_name, image_type, image_data) 
    VALUES ('test3', '.png', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));
INSERT INTO images (image_name, image_type, image_data) 
    VALUES ('test4', '.png', (decode('013d7d16d7ad4fefb61bd95b765c8ceb', 'hex')));

INSERT INTO categories (category_desc) VALUES ('test');
INSERT INTO categories (category_desc) VALUES ('test2');

INSERT INTO potholes (pothole_location, pothole_name, account_id, image_id, category_id, active, upload_time)
    VALUES ('33.6609 °N, 95.5555° W', 'Test 1', 1, 1, 1, true, '2022-08-09 05:13:20');
INSERT INTO potholes (pothole_location, pothole_name, account_id, image_id, category_id, active, upload_time)
    VALUES ('34.6609 °N, 96.5555° W', 'Test 2', 2, 2, 1, true, '2022-08-09 05:13:21');
INSERT INTO potholes (pothole_location, pothole_name, account_id, image_id, category_id, active, upload_time)
    VALUES ('35.6609 °N, 97.5555° W', 'Test 3', 3, 3, 2, true, '2022-08-09 05:13:22');
INSERT INTO potholes (pothole_location, pothole_name, account_id, image_id, category_id, active, upload_time)
    VALUES ('36.6609 °N, 98.5555° W', 'Test 4', 3, 4, 2, true, '2022-08-09 05:13:23');

COMMIT TRANSACTION;
