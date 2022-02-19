/*
DML script
*/

INSERT INTO CATEGORY(category_name) VALUES('Java');
INSERT INTO CATEGORY(category_name) VALUES('BookStudy');

INSERT INTO POST(category_id, tags, title, date, writer, likes, attached) VALUES ('1', '태그,이렇게,들어갈예정', '제목1','2021-10-27 12:00:00', '작성자', 1, 'File01,File02');
INSERT INTO POST(category_id, tags, title, date, writer, likes, attached) VALUES ('1', '아파치,자바,리눅스', '제목2','2021-10-27 12:00:00', '서진하', 12, 'File01');
INSERT INTO POST(category_id, tags, title, date, writer, likes, attached) VALUES ('1', 'Java,Stream,CompletableFuture', '제목3','2021-10-27 12:00:00', '서진하', 11, null);
INSERT INTO POST(category_id, tags, title, date, writer, likes, attached) VALUES ('2', 'Ubuntu,Spring', '제목4','2021-10-27 12:00:00', '서진하', 9, 'File01');
INSERT INTO POST(category_id, tags, title, date, writer, likes, attached) VALUES ('2', 'http,debug', '제목5','2021-10-27 12:00:00', '서진하', 100, null);

INSERT INTO COMMENT(post_id, follow_id, writer, contents, date, modified_date) VALUES ('1', null, '진하', '댓글내용입니닷', '2021-10-27 12:00:00', '2021-10-31 12:00:00');
INSERT INTO COMMENT(post_id, follow_id, writer, contents, date, modified_date) VALUES ('1', '1', '진하', '대댓내용입니다', '2021-10-27 12:00:00', '2021-10-31 12:00:00');

INSERT INTO CONTENT(post_id, contents) VALUES ('1','첫번째 포스트의 내용입니다');
INSERT INTO CONTENT(post_id, contents) VALUES ('2','두번째 포스트의 내용입니다');

INSERT INTO FILE(post_id, file_name, file_path, file_type) VALUES ('1','파일1이름','파일1경로','파일1타입');
INSERT INTO FILE(post_id, file_name, file_path, file_type) VALUES ('1','파일2이름','파일2경로','파일2타입');

/*
INSERT INTO TAG()
 */