-- Example data inserts with UUID values
insert into gruppe_dto(ID, GRUPPEN_NAME, GESCHLOSSEN, AUSGABE_GETAETIGT)
values ('11111111-1111-1111-1111-111111111111', 'Reisegruppe', false, true);

insert into ausgabe_dto(ID, GRUPPE_DTO, GRUPPE_DTO_KEY, KOSTEN)
values ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 0, 40);

insert into teilnehmer_dto(AUSGABE_DTO, AUSGABE_DTO_KEY, NAME)
values ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 0, 'MaxHub'),
       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 1, 'GitLisa');

insert into ausleger_dto(AUSGABE_DTO, NAME)
values ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'MaxHub');

insert into transaktion_dto(ID, GRUPPE_DTO, GRUPPE_DTO_KEY, NETTO_BETRAG)
values ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', 0, 40);

insert into zahler_dto(TRANSAKTION_DTO, NAME)
values ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'GitLisa');

insert into zahlungsempfaenger_dto(TRANSAKTION_DTO, NAME)
values ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'MaxHub');

insert into person_dto(GRUPPE_DTO, GRUPPE_DTO_KEY, NAME)
values ('11111111-1111-1111-1111-111111111111', 0, 'MaxHub'),
       ('11111111-1111-1111-1111-111111111111', 1, 'GitLisa');

insert into aktivitaet_dto(AUSGABE_DTO, NAME)
values ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Doener');

insert into gruppe_dto(ID, GRUPPEN_NAME, GESCHLOSSEN, AUSGABE_GETAETIGT)
values ('22222222-2222-2222-2222-222222222222', 'DieGang', false, false);

insert into ausgabe_dto(ID, GRUPPE_DTO, GRUPPE_DTO_KEY, KOSTEN)
values ('cccccccc-cccc-cccc-cccc-cccccccccccc', '22222222-2222-2222-2222-222222222222', 1, 100);

insert into teilnehmer_dto(AUSGABE_DTO, AUSGABE_DTO_KEY, NAME)
values ('cccccccc-cccc-cccc-cccc-cccccccccccc', 1, 'Freddy'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', 2, 'Otto');

insert into ausleger_dto(AUSGABE_DTO, NAME)
values ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Otto');

insert into transaktion_dto(ID, GRUPPE_DTO, GRUPPE_DTO_KEY, NETTO_BETRAG)
values ('dddddddd-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', 0, 40);

insert into zahler_dto(TRANSAKTION_DTO, NAME)
values ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Freddy');

insert into zahlungsempfaenger_dto(TRANSAKTION_DTO, NAME)
values ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Otto');

insert into person_dto(GRUPPE_DTO, GRUPPE_DTO_KEY, NAME)
values ('22222222-2222-2222-2222-222222222222', 1, 'Otto'),
       ('22222222-2222-2222-2222-222222222222', 2, 'Freddy');

insert into aktivitaet_dto(AUSGABE_DTO, NAME)
values ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Club');