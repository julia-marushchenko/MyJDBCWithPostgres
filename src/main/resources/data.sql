DROP SCHEMA IF EXISTS shop cascade;
CREATE SCHEMA shop;
CREATE TABLE shop.services (
	service_id UUID PRIMARY KEY,
	name VARCHAR UNIQUE,
	price NUMERIC(12,2)
);

CREATE TABLE shop.customers (
	customer_id UUID PRIMARY KEY,
	first_name VARCHAR,
	last_name VARCHAR,
	email VARCHAR UNIQUE,
	phone VARCHAR,
	address VARCHAR
);

CREATE TABLE shop.vendors (
	vendor_id UUID PRIMARY KEY,
	name VARCHAR NOT NULL,
	contact VARCHAR,
	phone VARCHAR,
	email VARCHAR UNIQUE,
	address VARCHAR
);

CREATE TABLE shop.products (
	product_id UUID PRIMARY KEY,
	name VARCHAR UNIQUE,
	price NUMERIC(12,2)
);