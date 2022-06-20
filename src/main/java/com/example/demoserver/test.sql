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



create table groupItems(id int not null auto_increment primary key, name varchar(255) unique not null check ( name!=''),createdAt timestamp not null default CURRENT_TIMESTAMP,orgId int not null,
                constraint orgG_FK
                foreign key (orgId)
                references organisations(id)
                on delete NO ACTION
                on update no action
                       );

# create table attributes(id int not null auto_increment primary key ,groupId int not null ,
#     attr varchar(255),value varchar(255),
#                         constraint grpA_fk
#                             foreign key (groupId)
#                             references groupItems(id)
#                             on delete NO ACTION
#                             on update no action
#                        );

create table items(id int not null auto_increment primary key,
                   orgId int not null,
                   groupId int,
                   name varchar(255) not null check ( name!=''),
                   description varchar(255),
                   createdAt timestamp not null default CURRENT_TIMESTAMP,
                   costPrice int default 0,

                   sellingPrice int default 0,

                   constraint orgif_fk
                       foreign key(orgId)
                           references organisations(id)
                           on delete cascade
                           on update cascade,
                    constraint grp_fk
                        foreign key (groupId)
                            references groupItems(id)
                                on delete NO ACTION
                                on update no action

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
                                     on update cascade,

                            constraint unique_IW unique (itemId,warId)

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



create table users(id int not null auto_increment primary key, email varchar(255) unique not null check ( email!=''),password varchar(255) not null check ( password!=''));

#
insert into organisations(name) values ('TestOrganisation 1');
insert into organisations(name) values ('TestOrganisation 2');

insert into warehouses(name, orgId) values ('Warehouse 1',1);
insert into warehouses(name, orgId) values ('Warehouse 2',2);
#
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'shoe','shoe from nike',400,500);
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'pen','pen from cello',10,15);
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'pencil','pencil from apsara',5,7);
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'eraser','eraser from apsara',3,5);
insert into items(orgId, name, description,  costPrice, sellingPrice) values (1,'ruler','ruler from FC',15,20);
#
# insert into warehouseStocks(warId, itemId, count) values (1,1,100);
insert into warehouseStocks(warId, itemId, count) values (1,2,100);
insert into warehouseStocks(warId, itemId, count) values (1,3,100);
insert into warehouseStocks(warId, itemId, count) values (1,4,100);
insert into warehouseStocks(warId, itemId, count) values (1,5,100);

#
insert into warehouseStocks(warId, itemId, count) values (5,15,200);
insert into warehouseStocks(warId, itemId, count) values (5,16,200);
insert into warehouseStocks(warId, itemId, count) values (5,17,200);
insert into warehouseStocks(warId, itemId, count) values (5,18,200);
insert into warehouseStocks(warId, itemId, count) values (5,19,200);

select i.name, COALESCE(sum(o.quantity),0)  as totalSold,COALESCE(sum(wS.count),0) as stock  from items i left join inventoryDB.orders o on o.itemId = i.id
    left join warehouseStocks wS on wS.itemId = i.id group by i.id;

select dte, sum(quantity) as qt, sum(ie.count) as stock
from ((select orders.itemId as dte, quantity, 0 as count
       from orders
      ) union all
      (select itemId, 0, count
       from warehouseStocks
      )
     ) ie
group by dte
order by stock  ;


select i.cName, COALESCE(sum(o.quantity*o.price),0)  as totalSoldPrice ,count(i.id) as soldUnits,count(i.cName)   from orders o left join inventoryDB.invoices i on i.cName = o.cName group by cName;

select i.cName, @totalPrice= COALESCE(sum(o.quantity*o.price),0)  as totalSoldPrice  ,@temp= @totalPrice-(@totalPrice*i.discount)/100, @temp+(@temp*i.tax)/100 as withtax from orders o left join inventoryDB.invoices i on i.cName = o.cName group by cName;


select  i.cName from(select invoiceId,sum(quantity*price) as totalPrice from orders ) o left join invoices i on i.id=o.invoiceId group by i.cName;


select dte, sum(tot) as totaPricePaid, sum(ie.ct) as invoices
from ((select  orders.cName as dte, price*orders.quantity as tot, 0 as ct
       from orders
      ) union all
      (select invoices.cName,0, 1 from invoices )) ie group by dte;


# insert into


#
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',1,5.1,4.1);
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',1,5.2,4.2);
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',1,5.3,4.3);
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',1,5.4,4.4);
# insert into invoices( cName, orgId, discount, tax ) values ('sudhar',1,5.5,4.5);

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
