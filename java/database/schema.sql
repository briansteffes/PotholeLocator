BEGIN TRANSACTION;

DROP TABLE IF EXISTS potholes, categories, images, user_accounts, users;

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
        email varchar(70) NOT NULL UNIQUE,
        phone bigint NOT NULL UNIQUE,
        flagged boolean NOT NULL DEFAULT FALSE,
        CONSTRAINT PK_account_id PRIMARY KEY (account_id),
        CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE images (
        image_id SERIAL,
        image_name varchar(40),
        image_type varchar(40),
        image_data bytea,
        CONSTRAINT PK_image_id PRIMARY KEY (image_id)
);

CREATE TABLE categories (
        category_id serial NOT NULL,
	category_desc varchar(10) NOT NULL,
	CONSTRAINT PK_category_id PRIMARY KEY (category_id)
);

CREATE TABLE potholes (
        pothole_id SERIAL,
        pothole_location varchar(40),
        pothole_name varchar(40),
        account_id int,
        image_id int,
        category_id int,
        active boolean,
        upload_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT PK_pothole_id PRIMARY KEY (pothole_id),
        CONSTRAINT FK_account_id FOREIGN KEY (account_id) REFERENCES user_accounts(account_id),
        CONSTRAINT image_id FOREIGN KEY (image_id) REFERENCES images(image_id),
        CONSTRAINT category_id FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

COMMIT TRANSACTION;

