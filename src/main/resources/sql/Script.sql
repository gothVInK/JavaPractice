create table contribution(
contribution_code integer primary key,
contribution_name text,
months integer,
rate integer
)

select * from contribution



create table client(
client_code integer primary key,
client_name text,
passport_number integer,
client_adress text,
phone_number integer
)

select * from client
insert into client (client_code) values (5)



create table client_account(
account_number integer,
client_code integer references client,
contribution_code integer references contribution,
opening_date date,
closing_date date,
invested_amount real
)

select * from client_account
insert into client_account (client_code) values (5)