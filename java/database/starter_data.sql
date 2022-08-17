begin transaction;

insert into
    users (username, password_hash, user_role)
values
    ('admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN'),
    ('employee0', '$2a$10$q6CFBRZbeCEYDcexv/aEx.A8eVMQ7.0.RxbM2KqebOKmjUhen.84C','ROLE_EMPLOYEE'),
    ('employee1', '$2a$10$SBM9nT2szuGO7dpy.1IejucVoaXi/PSaUr8A/AEXibNWbbM4u2gRO', 'ROLE_EMPLOYEE'),
    ('user0', '$2a$10$UhAcU9u0oh24qC1xZOIqp.QWXLSpMQkK8D9lafQM4dc8ngo2daiMi', 'ROLE_USER'),
    ('user1', '$2a$10$oIMGysyOHdJm8RgxOtK0qeHbnfyjGRGxnxqkPUrTQcwBMdbM69r1.', 'ROLE_USER');

insert into
    user_accounts (user_id, first_name, last_name, email_address, phone_number)
values
    (1, 'Admin', 'Admin', 'admin@email.com', 1111111111),
    (2, 'Employee0', 'Employee0', 'employee_0_@email.com', 2222222222),
    (3, 'Employee1', 'Employee1', 'employee_1_@email.com', 3333333333),
    (4, 'User0', 'User0', 'user_0_@email.com', 4444444444),
    (5, 'User1', 'User1', 'user_1_@email.com', 5555555555);

insert into
    images (image_name, image_type)
values
    ('bad_pothole', '.jpg'),
    ('uhh_pothole', '.jpg'),
    ('ugly_pothole', '.jpg'),
    ('yikes_pothole', '.jpg'),
    ('minor_pothole', '.jpg');

insert into
    statuses (status_desc)



