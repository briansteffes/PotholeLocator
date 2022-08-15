INSERT INTO user_accounts(user_id, f_name, l_name, email, phone)
VALUES((SELECT user_id FROM users WHERE username = 'paris_resident'), 'Amanda', 'Yellow', 'paris@gmail.com', 1112223333);

INSERT INTO images (image_name, image_type, image_data)
VALUES('bad_pothole', '.jpg', pg_read_binary_file('C:\Users\Student\Pictures\potholes\bad_pothole.jpg'));
INSERT INTO images (image_name, image_type, image_data)
VALUES('uhh_pothole', '.jpg', pg_read_binary_file('C:\Users\Student\Pictures\potholes\uhh_pothole.jpg'));
INSERT INTO images (image_name, image_type, image_data)
VALUES('ugly_pothole', '.jpg', pg_read_binary_file('C:\Users\Student\Pictures\potholes\ugly_pothole.jpg'));
INSERT INTO images (image_name, image_type, image_data)
VALUES('yikes_pothole', '.jpg', pg_read_binary_file('C:\Users\Student\Pictures\potholes\yikes_pothole.jpg'));
INSERT INTO images (image_name, image_type, image_data)
VALUES('minor_pothole', '.jpg', pg_read_binary_file('C:\Users\Student\Pictures\potholes\uhh_pothole.jpg'));

INSERT INTO categories(category_desc) VALUES('Major');
INSERT INTO categories(category_desc) VALUES('Minor');

INSERT INTO statuses(status_desc) VALUES('Reported');
INSERT INTO statuses(status_desc) VALUES('Inspected');
INSERT INTO statuses(status_desc) VALUES('Repaired');

INSERT INTO potholes(lat, long, pothole_name, account_id, image_id, category_id, status_id, active)
VALUES (33.658923, -95.555521, 'Disaster on Pine Bluff Street', (SELECT account_id FROM user_accounts WHERE f_name = 'Amanda'),
        (SELECT image_id FROM images WHERE image_name = 'bad_pothole'), 
        (SELECT category_id FROM categories WHERE category_desc = 'Major'),
        (SELECT status_id FROM statuses WHERE status_desc = 'Inspected'), true);

INSERT INTO potholes(lat, long, pothole_name, account_id, image_id, category_id, status_id, active)
VALUES (33.658925, -95.555517, 'Awful Hole Near Dairy Queen', (SELECT account_id FROM user_accounts WHERE f_name = 'Amanda'),
        (SELECT image_id FROM images WHERE image_name = 'uhh_pothole'), 
        (SELECT category_id FROM categories WHERE category_desc = 'Major'),
        (SELECT status_id FROM statuses WHERE status_desc = 'Reported'), true);

INSERT INTO potholes(lat, long, pothole_name, account_id, image_id, category_id, status_id, active)
VALUES (33.658926, -95.555518, 'Horrible Pothole', (SELECT account_id FROM user_accounts WHERE f_name = 'Amanda'),
        (SELECT image_id FROM images WHERE image_name = 'yikes_pothole'), 
        (SELECT category_id FROM categories WHERE category_desc = 'Major'),
        (SELECT status_id FROM statuses WHERE status_desc = 'Reported'), true);

INSERT INTO potholes(lat, long, pothole_name, account_id, image_id, category_id, status_id, active)
VALUES (33.658927, -95.555517, 'Big Pothole on Hubbard Street', (SELECT account_id FROM user_accounts WHERE f_name = 'Amanda'),
        (SELECT image_id FROM images WHERE image_name = 'ugly_pothole'), 
        (SELECT category_id FROM categories WHERE category_desc = 'Major'),
        (SELECT status_id FROM statuses WHERE status_desc = 'Repaired'), true);

INSERT INTO potholes(lat, long, pothole_name, account_id, image_id, category_id, status_id, active)
VALUES (33.658932, -95.555516, 'Minor Pothole on E Booth Street', (SELECT account_id FROM user_accounts WHERE f_name = 'Amanda'),
        (SELECT image_id FROM images WHERE image_name = 'minor_pothole'), 
        (SELECT category_id FROM categories WHERE category_desc = 'Minor'),
        (SELECT status_id FROM statuses WHERE status_desc = 'Repaired'), true);
        
