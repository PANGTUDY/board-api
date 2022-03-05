/*
DML script
*/

INSERT INTO CATEGORY(category_name) VALUES('Java');
INSERT INTO CATEGORY(category_name) VALUES('BookStudy');
INSERT INTO CATEGORY(category_name) VALUES('ASMR');

INSERT INTO POST(category_id, tags, title, contents, date, writer, likes) VALUES ('1', '태그,이렇게,들어갈예정', '제목1', '내용1', '2021-10-27 12:00:00', '작성자', 1);
INSERT INTO POST(category_id, tags, title, contents, date, writer, likes) VALUES ('1', '아파치,자바,리눅스', '제목2', '내용2', '2021-10-27 12:00:00', '서진하', 12);
INSERT INTO POST(category_id, tags, title, contents, date, writer, likes) VALUES ('1', 'Java,Stream,CompletableFuture', '제목3', '내용3', '2021-10-27 12:00:00', '서진하', 11);
INSERT INTO POST(category_id, tags, title, contents, date, writer, likes) VALUES ('2', 'Ubuntu,Spring', '제목4', '내용4', '2021-10-27 12:00:00', '서진하', 9);
INSERT INTO POST(category_id, tags, title, contents, date, writer, likes) VALUES ('2', 'http,debug', '제목5', '내용5', '2021-10-27 12:00:00', '서진하', 100);

INSERT INTO COMMENT(post_id, writer, contents, date) VALUES ('1', '진하', '게시글1의 댓글1', '2021-10-27 12:00:00');
INSERT INTO COMMENT(post_id, writer, contents, date) VALUES ('1', '진하', '게시글1의 댓글2', '2021-10-27 12:00:00');
INSERT INTO COMMENT(post_id, writer, contents, date) VALUES ('2', '진하', '게시글2의 댓글1', '2021-10-28 12:00:00');
INSERT INTO COMMENT(post_id, writer, contents, date) VALUES ('2', '진하', '게시글2의 댓글2', '2021-10-28 12:00:00');

INSERT INTO FILE(post_id, file_name, file_path, file_type) VALUES ('1','파일1이름','파일1경로','파일1타입');
INSERT INTO FILE(post_id, file_name, file_path, file_type) VALUES ('1','파일2이름','파일2경로','파일2타입');

/*
INSERT INTO TAG()
 */