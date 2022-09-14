SELECT EXISTS(
               SELECT
               FROM pg_catalog.pg_class c
                        JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
               WHERE n.nspname = 'public'
                 AND c.relname = 'group_permission'
           );
DELETE FROM group_permission;

SELECT EXISTS(
               SELECT
               FROM pg_catalog.pg_class c
                        JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
               WHERE n.nspname = 'public'
                 AND c.relname = 'permission'
           );
DELETE FROM permission;


SELECT EXISTS(
               SELECT
               FROM pg_catalog.pg_class c
                        JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
               WHERE n.nspname = 'public'
                 AND c.relname = 'user_group'
           );
DELETE FROM user_group;

SELECT EXISTS(
               SELECT
               FROM pg_catalog.pg_class c
                        JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
               WHERE n.nspname = 'public'
                 AND c.relname = 'group_'
           );
DELETE FROM group_;
ALTER SEQUENCE group__id_seq RESTART WITH 1;

SELECT EXISTS(
               SELECT
               FROM pg_catalog.pg_class c
                        JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
               WHERE n.nspname = 'public'
                 AND c.relname = 'permission'
           );
DELETE FROM permission;
ALTER SEQUENCE permission_id_seq RESTART WITH 1;


SELECT EXISTS(
               SELECT
               FROM pg_catalog.pg_class c
                        JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
               WHERE n.nspname = 'public'
                 AND c.relname = 'user_'
           );
DELETE FROM user_;
ALTER SEQUENCE user__id_seq RESTART WITH 1;






insert into group_ (id, name)
values (1, 'Administrador'),
       (2, 'Usuário');

insert into permission (id, name, description) values (nextval('permission_id_seq'), 'VIEW_USERS', 'Permite consultar usuários');
insert into permission (id, name, description) values (nextval('permission_id_seq'), 'EDIT_USERS', 'Permite criar ou editar usuários');
insert into permission (id, name, description) values (nextval('permission_id_seq'), 'EDIT_GROUPS', 'Permite criar ou editar grupos');


--Adiciona todas as permissoes no grupo do Administrador
insert into group_permission (group_id, permission_id)
select 1, id
from permission;

--Adiciona permissoes ao grupo do Usuário
insert into group_permission (group_id, permission_id)
select 2, id
from permission
where name like 'VIEW_%';

insert into group_permission (group_id, permission_id)
select 2, id
from permission
where name = 'EDIT_';


--passwords: 123
insert into user_ (id, name, email, password, creation_date)
values (nextval('user__id_seq'), 'admin', 'admin@gmail.com',
        '$2a$10$1U5ULwtCuzNafLNjXU57Y.FDcFu6cRObZBmM2vWztfG85wcun0T.q', now()),
       (nextval('user__id_seq'), 'Usuário', 'user@gmail.com',
        '$2a$10$1U5ULwtCuzNafLNjXU57Y.FDcFu6cRObZBmM2vWztfG85wcun0T.q', now());

insert into user_group (user_id, group_id)
values (1, 1),
       (1, 2),
       (2, 2);
