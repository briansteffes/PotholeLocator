begin transaction;

-- USERS
insert into
    users (username, password_hash, user_role)
values
    ('admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN'),
    ('employee0', '$2a$10$q6CFBRZbeCEYDcexv/aEx.A8eVMQ7.0.RxbM2KqebOKmjUhen.84C','ROLE_EMPLOYEE'),
    ('employee1', '$2a$10$SBM9nT2szuGO7dpy.1IejucVoaXi/PSaUr8A/AEXibNWbbM4u2gRO', 'ROLE_EMPLOYEE'),
    ('user0', '$2a$10$UhAcU9u0oh24qC1xZOIqp.QWXLSpMQkK8D9lafQM4dc8ngo2daiMi', 'ROLE_USER'),
    ('user1', '$2a$10$oIMGysyOHdJm8RgxOtK0qeHbnfyjGRGxnxqkPUrTQcwBMdbM69r1.', 'ROLE_USER');

-- USER ACCOUNTS
-- Employee0 -> office worker
-- Employee1 -> field worker (taking measurements)
insert into
    user_accounts (user_id, first_name, last_name, email_address, phone_number)
values
    (1, 'Admin', 'Admin', 'admin@email.com', '1111111111'),
    (2, 'Employee0', 'Employee0', 'employee_0_@email.com', '2222222222'),
    (3, 'Employee1', 'Employee1', 'employee_1_@email.com', '3333333333'),
    (4, 'User0', 'User0', 'user_0_@email.com', '4444444444'),
    (5, 'User1', 'User1', 'user_1_@email.com', '5555555555');

-- @TODO images

-- POTHOLES
insert into
    potholes (lat_coordinate, long_coordinate, pothole_name, pothole_desc, pothole_address,
              is_filled, is_within_city_jurisdiction, pothole_confirmed_timestamp, pothole_resolved_timestamp)
values
    (33.653744425288544, -95.556320304096, 'Pothole_1', 'Texas 19, South of Washington',
     '840 S Main St, Paris, TX 75460', false, true, '2022-08-01 00:00:00 CDT', null),
    (33.661331127089156, -95.55523531419796, 'Pothole_2', 'Lamar Ave, East of 1st St NE',
     '101 Lamar Ave, Paris, TX 75460', true, true, '2022-08-02 00:00:00 CDT', '2022-08-02 12:00:00 CDT'),
    (33.67344036036571, -95.53226796430495, 'Pothole_3', 'Ballard Dr, Southeast of 20th St NE',
     '2150 Ballard Dr, Paris, TX 75460', false, true, '2022-08-03 00:00:00 CDT', null);

-- Service Statuses
insert into
    service_statuses (status_name, status_desc)
values
    ('Pending Validation', 'Default value for a new pothole generated from submission, until an employee can verify'),
    ('Validated', 'Value after an employee has determined this is a pothole and is city responsibility'),
    ('Pending Measurement', 'Awaiting measurement to determine position in priority queue'),
    ('Measured', 'Value after employee measures pothole in the field'),
    ('Pending Resolution', 'Awaiting road crews attention'),
    ('Resolved', 'Value after road crew has fixed the pothole');

-- Categories
insert into
    categories (category_name, category_desc)
values
    ('Circle', 'The edges are curved and the ratios of the major axis and minor axis are equal.'),
    ('Oval', 'The edges are curved and the ratios of the major axis and minor axis are not equal.'),
    ('Polygon', 'Polygon includes squares and diamond shapes.'),
    ('Mixed', 'Mixed type'),
    ('Uncategorized', 'Classification not possible');

-- Severity Levels
insert into
    severity_levels (level_name, level_desc)
values
    ('Non Issue', 'Does not need to be addressed'),
    ('Low', 'Keep an eye on it and repair when further deterioration occurs'),
    ('Medium', 'Add to road crew schedule, to be repaired within the wek'),
    ('High', 'Damage to vehicle likely, have repaired within 48 hours'),
    ('Urgent', 'Clear and present risk to life and property, disptach traffic management team and repair immediately');

-- Pothole Reports
insert into
    pothole_reports (reporter_id, lat_coordinate, long_coordinate, description,
                     category_id, pothole_id, report_created)
values
    (4, 33.65374629285229, -95.55623974132378, 'Pothole 1 User 0', 1, 1, '2022-07-31 00:00:00 CDT'),
    (4, 33.66129872905128, -95.5549751257245, 'Pothole 2 User 0', 1, 2, '2022-07-31 00:00:00 CDT'),
    (5, 33.66141816662331, -95.55532649510312, 'Pothole 2 User 1', 3, 2, '2022-07-31 12:00:00 CDT'),
    (4, 33.67336370762101, -95.53214986961878, 'Pothole 3 User 0', 2, 3, '2022-07-31 00:00:00 CDT'),
    (4, 33.664176563786434, -95.54606981577412, 'Pothole 4 (Unverified), User 0', 1, null, '2022-08-04 00:00:00 CDT');

-- Pothole Measurements (only pothole 1 and 2 have been measured), The employee agrees with category for 1st one, but not second.
-- @TODO: Should the measuring employee rate the severity?
insert into
    pothole_measurements (measurer_id, pothole_id, category_id, depth_in_mm, length_in_mm, right_of_way_location)
values
    (3, 1, 1, 3, 10, 'Non-wheel Path: Center'),
    (3, 2, 2, 26, 200, 'Wheel path');

-- Pothole Categories (all records in this table are a direct consequence of records in other tables)
insert into
    pothole_categories (categorizer_id, pothole_id, category_id, categorization_timestamp, category_source)
values
    (4, 1, 1, '2022-07-31 00:00:00 CDT', 'user-reported');

commit transaction;
