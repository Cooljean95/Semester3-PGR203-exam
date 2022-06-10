create table Question(
                         Id_question serial primary key,
                         Title varchar(300) not null,
                         Text varchar(1000) not null,
                         LowL varchar(100),
                         HighL varchar(100)

);



