create table answer(
                       Id_answer serial primary key,
                       Id_question int not null,
                       Answer varchar(500) not null
);

