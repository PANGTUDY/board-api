/*
DML script
*/

INSERT INTO CATEGORY(category_name) VALUES('Java');
INSERT INTO CATEGORY(category_name) VALUES('팡터디');
INSERT INTO CATEGORY(category_name) VALUES('스터디 홈페이지 개발');
INSERT INTO CATEGORY(category_name) VALUES('MySQL');
INSERT INTO CATEGORY(category_name) VALUES('NodeJs');

INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('1', '자바,Java,팡터디,SPRING', '첫 게시글', '게시글의 내용은 500자로 제한합니다.', '1955-05-19 12:24:40', '1', 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('1', '자바,제임스 고슬링', '자바 창시자는 누굴까', '제임스 고슬링은 프로그래밍 언어 ''자바(JAVA)''의 창시자이자 가장 영향력 있는 프로그래머 중 한 사람으로 꼽히는 인물입니다. 자바 이외에도 멀티 프로세서용 유닉스와 컴파일러, 메일 시스템, 데이터 인식 시스템 등을 개발한 바 있습니다.', '1955-05-19 12:24:40', 1, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('1', '아파치,자바,리눅스', '자바 기본 문법', '자바 프로그램은 기본적으로 클래스 구조에서 시작 합니다. 클래스는 객체지향 개념에서 객체를 정의하는 틀로서 많은 객체지향 프로그램 언어의 기본 구조 입니다.', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('1', 'Java,Stream,CompletableFuture', '자바 프로그래밍 기초', '내용3', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('2', 'Junit,Spring', '팡터디 개설!', '주요기능 및 파트 분담', '2021-09-01 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('3', '팡터디,PANGTUDY', 'Spring 게시판 개발(1)', '화면을 설계합니다.', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('3', 'SPRING', 'Spring 게시판 개발(2)', 'API를 설계합니다.', '2021-11-03 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('3', 'http,REST,swagger', 'Spring 게시판 개발(3)', '게시판의 주요 기능은 다음과 같습니다.-  북마크 공유 페이지 : 유저별 크롬 즐겨찾기(북마크) 공유하는 공간', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', 'MySQL 인덱스 설정 방법과 실행 계획 확인', 'MySQL에서는 하나의 쿼리를 실행하면 하나의 테이블의 하나의 인덱스만 설정되기 때문에 여러 개의 인텍스를 지정하고 싶은 경우에는 콤마를 이용하여 여러 컬럼을 설정하여 지정', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '10번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '11번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '12번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '13번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '14번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '15번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '16번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '17번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '18번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '19번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '20번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '21번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '22번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '23번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '24번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '25번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '26번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '27번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '28번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '29번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '30번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '31번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '32번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '33번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '34번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '35번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '36번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '37번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '38번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '39번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '40번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '41번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '42번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '43번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '44번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '45번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '46번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '47번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '48번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '49번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '50번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);
INSERT INTO POST(category_id, tags, title, contents, date, writer_id, likes) VALUES ('4', 'mysql,likes,index', '51번째 게시글', ' 게시글 내용', '2021-10-27 12:00:00', 2, 0);

INSERT INTO COMMENT(post_id, writer_id, contents, date) VALUES ('1', '1', '게시글1의 댓글1. 게시글은 100자로 제한합니다.', '2021-10-27 12:00:00');
INSERT INTO COMMENT(post_id, writer_id, contents, date) VALUES ('1', '2', '게시글1의 댓글2', '2021-10-27 12:00:00');
INSERT INTO COMMENT(post_id, writer_id, contents, date) VALUES ('2', '3', '게시글2의 댓글1', '2021-10-28 12:00:00');
INSERT INTO COMMENT(post_id, writer_id, contents, date) VALUES ('2', '4', '게시글2의 댓글2', '2021-10-28 12:00:00');
INSERT INTO COMMENT(post_id, writer_id, contents, date) VALUES ('2', '3', '게시글2의 댓글3', '2021-10-28 12:00:00');
INSERT INTO COMMENT(post_id, writer_id, contents, date) VALUES ('3', '7', '게시글3의 댓글1', '2021-10-28 12:00:00');