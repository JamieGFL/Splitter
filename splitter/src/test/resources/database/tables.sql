drop table if exists aktivitaet_dto;
drop table if exists person_dto;
drop table if exists zahlungsempfaenger_dto;
drop table if exists zahler_dto;
drop table if exists ausleger_dto;
drop table if exists teilnehmer_dto;
drop table if exists ausgabe_dto;
drop table if exists transaktion_dto;
drop table if exists gruppe_dto;



create table gruppe_dto
(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    gruppen_name varchar(20),
    geschlossen boolean,
    ausgabe_getaetigt boolean
);

create table ausgabe_dto
(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    gruppe_dto UUID references gruppe_dto (id),
    gruppe_dto_key integer,
    kosten numeric
);

create table teilnehmer_dto
(
    id serial primary key,
    ausgabe_dto UUID references ausgabe_dto (id),
    ausgabe_dto_key int,
    name text
);

create table ausleger_dto
(
    id serial primary key,
    ausgabe_dto UUID references ausgabe_dto (id),
    name text
);

create table transaktion_dto
(
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    gruppe_dto UUID references gruppe_dto (id),
    gruppe_dto_key integer,
    netto_betrag numeric
);

create table zahler_dto
(
    id serial primary key,
    transaktion_dto UUID references transaktion_dto (id),
    name text

);

create table zahlungsempfaenger_dto
(
    id serial primary key,
    transaktion_dto UUID references transaktion_dto (id),
    name text
);


create table person_dto
(
    id serial primary key,
    gruppe_dto UUID references gruppe_dto (id),
    gruppe_dto_key int,
    name text

);


create table aktivitaet_dto
(
    ausgabe_dto UUID primary key references ausgabe_dto (id),
    name text
);