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
                      discount float not null default 0.0,
                      tax float not null default 0.0,
                      createdAt timestamp not null default CURRENT_TIMESTAMP,
                      constraint org_fk
                          foreign key (orgId)
                              references organisations(id)
                              on delete cascade
                              on update cascade );

create table warehouseStocks(id int not null auto_increment primary key,
                             warId int not null,
                             itemId int not null,
                             count int not null default 0,

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

create table orders(id int not null auto_increment primary key ,
                    invoiceId int not null ,
                    cName varchar(255) not null check ( cName!='' ),
                    itemId int not null ,
                    orgId int not null ,
                    warId int not null ,
                    price int not null ,
                    quantity int not null default 1,
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
                            on update cascade,
                    constraint stockId_fk
                            foreign key (warId)
                            references warehouses(id)
                            on delete cascade
                            on update cascade
);


#
# insert into organisations(name) values ('TestOrganisation 1');
# insert into organisations(name) values ('TestOrganisation 2');
#
# insert into warehouses(name, orgId) values ('Warehouse 1',1);
# insert into warehouses(name, orgId) values ('Warehouse 2',2);
#
# insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'shoe','shoe from nike',400,500);
# insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'pen','pen from cello',10,15);
# insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'pencil','pencil from apsara',5,7);
# insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'eraser','eraser from apsara',3,5);
# insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'ruler','ruler from FC',15,20);
#
# insert into warehouseStocks(warId, itemId, count) values (1,15,100);
# insert into warehouseStocks(warId, itemId, count) values (1,16,100);
# insert into warehouseStocks(warId, itemId, count) values (1,17,100);
# insert into warehouseStocks(warId, itemId, count) values (1,18,100);
# insert into warehouseStocks(warId, itemId, count) values (1,19,100);
#
#
# insert into warehouseStocks(warId, itemId, count) values (5,15,200);
# insert into warehouseStocks(warId, itemId, count) values (5,16,200);
# insert into warehouseStocks(warId, itemId, count) values (5,17,200);
# insert into warehouseStocks(warId, itemId, count) values (5,18,200);
# insert into warehouseStocks(warId, itemId, count) values (5,19,200);
#
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',1,5.1,4.1);
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',3,5.2,4.2);
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',1,5.3,4.3);
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',3,5.4,4.4);
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',1,5.5,4.5);
#
# insert into orders( invoiceId, cName, itemId, orgId,quantity, price,stockId ) values (1,'sudhar',15,1,5,500,2);
# insert into orders( invoiceId, cName, itemId, orgId,quantity, price ,stockId) values (1,'sudhar',16,1,3,15,1);
# insert into orders( invoiceId, cName, itemId, orgId,quantity, price ,stockId) values (1,'sudhar',17,1,4,7,1);
# insert into orders( invoiceId, cName, itemId, orgId,quantity, price ,stockId) values (1,'sudhar',18,1,2,5,2);
# insert into orders( invoiceId, cName, itemId, orgId,quantity, price ,stockId) values (1,'sudhar',19,1,1,20,1);
#
#
#
#
#
#
