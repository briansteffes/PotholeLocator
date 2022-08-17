INSERT INTO images (image_name, image_type)
VALUES('bad_pothole', '.jpg');
INSERT INTO images (image_name, image_type)
VALUES('uhh_pothole', '.jpg');
INSERT INTO images (image_name, image_type)
VALUES('ugly_pothole', '.jpg');
INSERT INTO images (image_name, image_type)
VALUES('yikes_pothole', '.jpg');
INSERT INTO images (image_name, image_type)
VALUES('minor_pothole', '.jpg');

INSERT INTO categories(category_desc) VALUES('Major');
INSERT INTO categories(category_desc) VALUES('Minor');

INSERT INTO statuses(status_desc) VALUES('Reported');
INSERT INTO statuses(status_desc) VALUES('Inspected');
INSERT INTO statuses(status_desc) VALUES('Repaired');

INSERT INTO potholes(lat, long, pothole_name, account_id, category_id, status_id, image_id, upload_time)
VALUES (33.661141, -95.529704, 'Awful Pothole Near Dairy Queen', 861, 1, 1, 1, '2022-8-08 02:33:21');

INSERT INTO potholes(lat, long, pothole_name, account_id, category_id, status_id, image_id, upload_time)
VALUES (33.658465, -95.527390, 'Big Pothole on Hubbard Street', 1523, 1, 2, 1, '2022-07-11 11:42:11');

INSERT INTO potholes(lat, long, pothole_name, account_id, category_id, status_id, image_id, upload_time)
VALUES (33.666060, -95.545775, 'Minor Pothole on E Booth Street', 9641, 2, 1, 1, '2022-08-08 06:30:18');

INSERT INTO potholes(lat, long, pothole_name, account_id, category_id, status_id, image_id, upload_time)
VALUES (33.658926, -95.555518, 'Horrible Pothole! Please Fix', 7053, 1, 1, 1, '2022-07-29 10:10:10');

INSERT INTO potholes(lat, long, pothole_name, account_id, category_id, status_id, image_id, upload_time)
VALUES (33.660555, -95.551912, 'Small Pothole on Business 82', 7053, 2, 2, 1, '2022-08-16 10:19:10');

INSERT INTO potholes(lat, long, pothole_name, account_id, category_id, status_id, image_id, upload_time)
VALUES (33.648846, -95.527067, 'S Collegiate Dr Near Police Station', 9641, 2, 1, 1, '2022-08-09 03:30:18');

INSERT INTO potholes(lat, long, pothole_name, account_id, category_id, status_id, image_id, upload_time)
VALUES (33.659693, -95.515043, 'Large Hole at 33rd St SE in Right Lane', 861, 1, 1, 1, '2022-08-11 03:30:18');

INSERT INTO potholes(lat, long, pothole_name, account_id, category_id, status_id, image_id, upload_time)
VALUES (33.672998, -95.556078, 'Pothole in Front of Burgerland; FIX NOW', 1523, 2, 1, 1, '2022-08-13 03:30:18');