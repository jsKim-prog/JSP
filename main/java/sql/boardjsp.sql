create table member(
	id varchar2(10) not null, --id
	pass varchar2(10) not null, --pw
	name varchar2(30) not null, --name
	regidate Date default sysdate not null, --생성날짜
	primary key(id) --기본키 생성
);
--회원용 테이블

select * from member;

create table board(
	num number primary key, --게시물 번호(시퀀스)
	title varchar2(200) not null, --제목
	content varchar2(2000) not null, --내용
	id varchar2(10) not null, --작성자(member=id)
	postdate Date default sysdate not null, --작성일
	visitcount number(6) --조회수
);
--게시판용 테이블
select * from board;

--외래키 설정(부모 member->자식board)
alter table board add constraint board_member_fk foreign key (id) references member(id);

--시퀀스 설정(자동번호 주입)
create sequence seq_board_num
	increment by 1 --증가값1
	start with 1  --시작값1
	minvalue 1 --최소값1
	nomaxvalue --최대값 없음
	nocycle --반복 없음
	nocache; --미리 만들지 않음
	
-- drop sequence seq_board_num ; 시퀀스 객체 삭제

--더미데이터
insert into member (id, pass, name) values ('kkw', '1234', '김기원');
insert into member (id, pass, name) values ('kkk', '1234', '김지선');
insert into member (id, pass, name) values ('aaa', '1234', '문지현');
insert into member (id, pass, name) values ('bbb', '1234', '이영훈');	
insert into member (id, pass, name) values ('ccc', '1234', '조영재');	
insert into member (id, pass, name) values ('ddd', '1234', '함시은');
	
insert into board (num, title, content, id, postdate, visitcount) values(seq_board_num.nextval,'제목1', '내용1', 'kkk', sysdate, 0);
insert into board (num, title, content, id, postdate, visitcount) values(seq_board_num.nextval,'제목2', '내용2', 'kkw', sysdate, 0);
insert into board (num, title, content, id, postdate, visitcount) values(seq_board_num.nextval,'제목3', '내용3', 'aaa', sysdate, 0);
insert into board (num, title, content, id, postdate, visitcount) values(seq_board_num.nextval,'제목4', '내용4', 'bbb', sysdate, 0);
insert into board (num, title, content, id, postdate, visitcount) values(seq_board_num.nextval,'제목5', '내용6', 'ccc', sysdate, 0);
insert into board (num, title, content, id, postdate, visitcount) values(seq_board_num.nextval,'제목5', '내용7', 'ddd', sysdate, 0);

select * from member where id='kkw' and pass='1234'
select count(*) from board where title like '%제%'

select B.*, M.name from member M inner join board B on M.id = B.id where num = '15';
update board set visitcount = visitcount+1 where num ='15';


select * from (select Tb.*, rownum rNum from (select * from BOARD 
order by num desc ) Tb ) where rNum between 1 and 10;

create table myfile (
	idx number primary key,
	name varchar2(50) not null,
	title varchar2(200) not null,
	cate varchar2(30),
	ofile varchar2(100) not null, --원본파일명
	sfile varchar2(100) not null, --저장파일명
	postdate date default sysdate not null
);

select * from myfile;
--drop table myfile;