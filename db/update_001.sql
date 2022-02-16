create table employees
(
    id               serial primary key not null,
    firstName        varchar(2000)      not null,
    lastName         varchar(2000)      not null,
    tin              integer unique     not null,
    dateOfEmployment timestamp          not null
);

insert into employees (firstName, lastName, tin, dateOfEmployment)
values ('pete', 'smith', 123456789, '2004-10-19 10:23:54');
insert into employees (firstName, lastName, tin, dateOfEmployment)
values ('ban', 'john', 987654321, '2005-05-20 16:25:44');
insert into employees (firstName, lastName, tin, dateOfEmployment)
values ('ivan', 'ivanov', 123987789, '2010-02-11 08:32:55');

create table persons
(
    id          serial primary key not null,
    login       varchar(2000)      not null,
    password    varchar(2000)      not null,
    employee_id integer            not null references employees (id)
);

insert into persons (login, password, employee_id)
values ('pete', '123', 1);
insert into persons (login, password, employee_id)
values ('ban', '123', 2);
insert into persons (login, password, employee_id)
values ('ivan', '123', 3);