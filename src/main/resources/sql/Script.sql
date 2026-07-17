create sequence contribution_serial start  500
create table contribution(
	contribution_code integer primary key default nextval ('contribution_serial'),
	contribution_name text not null,
	months integer not null,
	rate real not null
)

create sequence client_serial start  100
create table client(
	client_code integer primary key default nextval('client_serial'),
	first_name text not null,
	middle_name text,
	last_name text  not null,
	passport_number text not null,
	client_adress text not null,
	phone_number text not null
)

create sequence client_account_serial start  1001
create table client_account(
	account_number integer primary key not null default nextval ('client_account_serial'),
	client_code integer references client not null,
	contribution_code integer references contribution not null,
	opening_date date not null,
	closing_date date not null,
	invested_amount real not null
)
























