
begin;
create table company(
    id serial primary key,
    name varchar(255) not null
);

create table role(
    id serial primary key,
    name varchar(50) not null
);

create table "user"(
    id varchar(50) primary key,
    title varchar(255) not null,
    firstName varchar(255) not null,
    middleName varchar(255),
    lastName varchar(255) not null,
    createdBy varchar(50) references "user"(id),
    createdAt date not null,
    email varchar(255) not null,
    password varchar(255) not null,
    passwordSalt varchar(50)
);

insert into role (name) values ('Admin');
insert into role (name) values ('Manufacture');
insert into role (name) values ('Producer');
insert into role (name) values ('Actor');

alter table "user" add column role int references role(id);
alter table "user" add column company int references company(id);

create table language(
language_id serial primary key,
name varchar(50) not null unique
);

create table programinformation(
programinformation_id serial primary key,
Description varchar(500),
title varchar(50) not null,
language_id int references language(language_id)
);

create table program(
program_id varchar(50) Primary key,
programinformation_id int references programinformation(programinformation_id) not null
);

create table credit(
credit_id serial primary key,
"user" varchar(50) references "user"(id) not null,
program varchar(50) references program(program_id) not null
);


create table companyProgram(
company_id serial primary key,
program varchar(50) references program(program_id) not null,
company int references company(id) not null
);

alter table programinformation rename programinformation_id to id;
alter table program rename  program_id to id;
alter table language rename  language_id to id;
alter table credit rename  credit_id to id;
alter table companyProgram rename company_id to id;

alter table company alter column id TYPE BIGINT;
alter table companyprogram alter column id type bigint;
alter table companyprogram alter column company type bigint;
alter table credit alter column id type bigint;
alter table language alter column id type bigint;
alter table program alter column programinformation_id type bigint;
alter table programinformation alter column language_id type BIGINT;

alter table "user"
    drop constraint user_createdby_fkey;

alter table "user" alter column createdBy type bigint using createdby::bigint;

alter table credit
    drop constraint credit_user_fkey;

alter table credit alter column "user" type BIGINT using id::bigint;

alter table "user" alter column id type bigint using id::bigint;

alter table credit
    add constraint credit_user_fkey foreign key ("user") references "user"(id);

alter table "user"
    add constraint user_createdby_fkey foreign key (createdby) references "user"(id);

alter table role alter column id type bigint;

alter table "user" alter role type bigint;

alter table credit
    drop constraint credit_program_fkey;

alter table companyprogram
    drop constraint companyprogram_program_fkey;

alter table program alter id type bigint using id::bigint;
alter table companyprogram alter program type bigint using program::bigint;
alter table credit alter program type bigint using program::bigint;

alter table credit
    add constraint credit_program_fkey foreign key (program) references program(id);

alter table companyprogram
    add constraint companyprogram_program_fkey foreign key (program) references program(id);

create table programproducer
(
    id bigserial primary key unique,
    producer_id bigint references "user"(id),
    program_id bigint references  program(id)
);

CREATE SEQUENCE program_id_seq;

ALTER SEQUENCE program_id_seq
OWNED BY program.id;

alter table program alter id set default nextval('program_id_seq'::regclass);

alter table programinformation alter column  id type bigint;

CREATE SEQUENCE user_id_seq;

ALTER SEQUENCE user_id_seq
OWNED BY "user".id;

alter table "user" alter id set default nextval('user_id_seq'::regclass);

alter table programinformation add column program_id int references program(id);
alter table programinformation alter column language_id set not null;

alter table program
    drop constraint program_programinformation_id_fkey;

alter table program drop column  programinformation_id;
alter table programinformation alter column program_id set not null;

alter table credit add column timestamp_for_deletion timestamptz;
alter table programproducer add column timestamp_for_deletion timestamptz;

alter table programinformation alter column description type varChar(1000);
alter table programinformation alter column title type varchar(100);

commit;
end;