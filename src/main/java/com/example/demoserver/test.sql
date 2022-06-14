use inventoryDB;

create table organisations(id int not null auto_increment primary key ,
            name varchar(255) unique not null check ( name!=''),
            created timestamp default current_timestamp);

create table warehouses(id int not null auto_increment primary key,
        name varchar(255) unique not null check ( name!=''),
        orgId int not null,
        constraint og_fk
        foreign key (orgId) references organisations(id) on delete cascade  on update cascade
);

create table items(id int not null auto_increment primary key,
                   orgId int not null,
                   name varchar(255) not null check ( name!=''),
                   description varchar(255),
                   createdAt timestamp not null default CURRENT_TIMESTAMP,
                   costPrice int default 0,

                   sellingPrice int default 0,

                   constraint orgif_fk
                       foreign key(orgId)
                           references organisations(id)
                           on delete cascade
                           on update cascade

);


create table invoices(id int not null auto_increment primary key,
                      cName varchar(255) not null check ( cName!='' ),
                      orgId int not null ,
                      createdAt timestamp not null default CURRENT_TIMESTAMP,
                      constraint org_fk
                          foreign key (orgId)
                              references organisations(id)
                              on delete cascade
                              on update cascade );


create table orders(id int not null auto_increment primary key ,
                    invoiceId int not null ,
                    cName varchar(255) not null check ( cName!='' ),
                    itemId int not null ,
                    orgId int not null ,
                    price int not null ,
                    discount float default 0.0,
                    tax float default  0.0,
                    createdAt timestamp not null default CURRENT_TIMESTAMP,
                    constraint organ_fk
                        foreign key (orgId)
                            references organisations(id)
                            on delete cascade
                            on update cascade,
                    constraint item_fk
                        foreign key (itemId)
                            references items(id)
                            on delete cascade
                            on update cascade,
                    constraint invoice_fk
                        foreign key (invoiceId)
                            references invoices(id)
                            on delete cascade
                            on update cascade
);

create table warehouseStocks(id int not null auto_increment primary key,
        warId int not null,
        itemId int not null,
        count int default 0,

         constraint war_fk
             foreign key (warId)
                 references warehouses(id)
                 on delete cascade
                 on update cascade,

         constraint it_fk
             foreign key (itemId)
                 references items(id)
                 on delete cascade
                 on update cascade
);

insert into organisations(name) values ('TestOrganisation 1');
insert into organisations(name) values ('TestOrganisation 2');

insert into warehouses(name, orgId) values ('Warehouse 1',1);
insert into warehouses(name, orgId) values ('Warehouse 2',2);

insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'shoe','shoe from nike',400,500);
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'pen','pen from cello',10,15);
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'pencil','pencil from apsara',5,7);
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'eraser','eraser from apsara',3,5);
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'ruler','ruler from FC',15,20);

insert into warehouseStocks(warId, itemId, count) values (1,1,100);
insert into warehouseStocks(warId, itemId, count) values (1,2,100);
insert into warehouseStocks(warId, itemId, count) values (1,3,100);
insert into warehouseStocks(warId, itemId, count) values (1,4,100);
insert into warehouseStocks(warId, itemId, count) values (1,5,100);


insert into warehouseStocks(warId, itemId, count) values (2,1,200);
insert into warehouseStocks(warId, itemId, count) values (2,2,200);
insert into warehouseStocks(warId, itemId, count) values (2,3,200);
insert into warehouseStocks(warId, itemId, count) values (2,4,200);
insert into warehouseStocks(warId, itemId, count) values (2,5,200);