begin transaction;

drop table if exists users;
drop table if exists user_accounts;
drop table if exists images;
drop table if exists potholes;
drop table if exists user_accounts;
drop table if exists categories;
drop table if exists severity_levels;
drop table if exists pothole_reports;
drop table if exists pothole_measurements;
drop table if exists pothole_categories;
drop table if exists pothole_service_statuses;
drop table if exists pothole_images;
drop table if exists pothole_severity_levels;

create table users (
    user_id serial constraint user_pk primary key,
    username varchar(50) not null unique,
    password_hash varchar(200) not null,
    user_role varchar(50) not null
);

create table user_accounts (
    account_id serial constraint user_account_pk primary key,
    user_id int not null unique references users(user_id),
    first_name varchar(35) not null,
    last_name varchar(50) not null,
    email_address varchar(70) not null unique,
    phone_number varchar(20) unique,
    is_flagged boolean default false,
    account_created timestamp not null default current_timestamp
);

create table images (
    image_id serial constraint image_pk primary key,
    image_name varchar(40),
    image_desc varchar(200),
    image_type varchar(40),
    image_data bytea not null,
    upload_time timestamp not null default current_timestamp
);

create table potholes (
    pothole_id serial constraint pothole_pk primary key,
    lat_coordinate decimal(8,6) not null,
    long_coordinate decimal(9,6) not null,
    pothole_name varchar(40),
    pothole_desc varchar(200),
    pothole_address varchar(100),
    is_filled boolean not null default false,
    is_within_city_jurisdiction boolean not null default true,
    pothole_confirmed_timestamp timestamp not null default current_timestamp,
    -- @TODO Constraint that if is_filled then resolved timestamp must not be null
    pothole_resolved_timestamp timestamp
);

create table service_statuses (
    service_status_id serial constraint service_status_pk primary key,
    status_name varchar(30) unique not null,
    status_desc varchar(50)
);

create table categories (
    category_id serial constraint category_pk primary key,
    category_name varchar(20) unique not null,
    category_desc varchar(50)
);

create table severity_levels (
    level_id serial constraint severity_level_pk primary key,
    level_name varchar(20) unique not null,
    level_desc varchar(50)
);

create table pothole_reports (
    pothole_report_id serial constraint pothole_report_pk primary key,
    reporter_id int not null references user_accounts(account_id),
    lat_coordinate decimal(8,6),
    long_coordinate decimal(9,6),
    description varchar(200),
    image_id int unique references images(image_id),
    category_id int references categories(category_id),
    pothole_id int references potholes(pothole_id),
    report_created timestamp not null default current_timestamp
);

create table pothole_measurements (
    pothole_measurement_id serial constraint pothole_measurement_pk primary key,
    measurer_id int not null references user_accounts(account_id),
    pothole_id int not null references potholes(pothole_id),
    category_id int not null references categories(category_id),
    depth_in_mm int not null,
    length_in_mm int not null,
    right_of_way_location varchar(20) not null,
    is_estimate boolean default true,
    measurement_created timestamp not null default current_timestamp
);

create table pothole_categories (
    pothole_category_id serial constraint pothole_category_pk primary key,
    categorizer_id int not null references user_accounts(account_id),
    pothole_id int not null references potholes(pothole_id),
    category_id int not null references categories(category_id),
    categorization_timestamp timestamp not null,
    category_source varchar(20)
);

create table pothole_service_statuses (
    pothole_service_status_id serial constraint pothole_service_status_pk primary key,
    pothole_id int not null references potholes(pothole_id),
    service_status_id int not null references service_statuses(service_status_id),
    service_status_timestamp timestamp not null default current_timestamp
);

create table pothole_images (
    pothole_image_id serial constraint pothole_image_pk primary key,
    pothole_id int not null references potholes(pothole_id),
    image_id int not null references images(image_id)
);

create table pothole_severity_levels (
    pothole_severity_id serial constraint pothole_severity_level_pk primary key,
    pothole_id int not null references potholes(pothole_id),
    severity_level_id int not null references severity_levels(level_id),
    severity_updated timestamp not null default current_timestamp
);

commit transaction;












