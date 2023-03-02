drop table member;
drop table feed;
drop table comment;

create table member(
	id varchar(20) primary key,
	pw varchar(20) not null,
	name varchar(10) not null,
	phone  varchar(20) not null
);

create table feed(
	num int primary key auto_increment,
	name varchar(20),
	writer varchar(20),
	content varchar(100),
	addTime date,
	foreign key (writer) references member (id)
);

create table comment(
	num int primary key auto_increment,
	ref int,
	step int,
	writer varchar(20),
	content varchar(100),
	addTime date,
	feedNum int,
	foreign key (writer) references member (id),
	foreign key (feedNum) references feed (num)
);

--dummy
insert into member (id,pw,name,phone) values('user1', '1234', 'user1','000-0000-0000');
insert into feed (name, writer, content, addTime) values('test1', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test2', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test3', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test4', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test5', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test6', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test7', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test8', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test9', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test10', 'user1','testing...', now());
insert into feed (name, writer, content, addTime) values('test11', 'user1','testing...', now());
commit;

select * from member;