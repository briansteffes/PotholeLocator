BEGIN TRANSACTION;

DROP TABLE IF EXISTS potholes, categories, statuses, images, user_accounts, users, pothole_statuses CASCADE;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE user_accounts (
        account_id SERIAL,
        user_id int NOT NULL UNIQUE,
        f_name varchar(35) NOT NULL,
        l_name varchar(50) NOT NULL,
        email varchar(70) NOT NULL,
        phone bigint NOT NULL,
        flagged boolean NOT NULL DEFAULT FALSE,
        account_created timestamp not null default current_timestamp,
        CONSTRAINT PK_account_id PRIMARY KEY (account_id),
        CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE images (
        image_id SERIAL not null,
        image_name varchar(40),
        image_type varchar(40),
        image_data bytea default null,
        upload_time timestamp not null default current_timestamp,
        CONSTRAINT PK_image_id PRIMARY KEY (image_id)
);

CREATE TABLE statuses (
        status_id serial NOT NULL,
        status_desc varchar(20),
        CONSTRAINT PK_status_id PRIMARY KEY (status_id)
);

CREATE TABLE categories (
        category_id serial NOT NULL,
	category_desc varchar(10) NOT NULL,
	CONSTRAINT PK_category_id PRIMARY KEY (category_id)
);

CREATE TABLE potholes (
        pothole_id SERIAL,
        lat decimal(8,6),
        long decimal(9,6),
        pothole_name varchar(40),
        account_id int,
        image_id int DEFAULT 1,
        category_id int DEFAULT 1,
        status_id int DEFAULT 1,
        active boolean DEFAULT true,
        upload_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT PK_pothole_id PRIMARY KEY (pothole_id),
        CONSTRAINT FK_account_id FOREIGN KEY (account_id) REFERENCES user_accounts(account_id),
        CONSTRAINT FK_image_id FOREIGN KEY (image_id) REFERENCES images(image_id),
        CONSTRAINT FK_category_id FOREIGN KEY (category_id) REFERENCES categories(category_id),
        CONSTRAINT FK_status_id FOREIGN KEY (status_id) REFERENCES statuses(status_id)
);

COMMIT TRANSACTION;

