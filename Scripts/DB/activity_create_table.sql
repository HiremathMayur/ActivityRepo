connect activity;
create table activity (
  id int(11) not null AUTO_INCREMENT
  , name varchar(40)
  , user_name varchar(20)
  , attribute json
  , primary key(id)
  , constraint fk_user foreign key (user_name) references user(user_name)
);
