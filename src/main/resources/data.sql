/*
DML script
*/
INSERT INTO POST(category_id, tags, follow_id, title, date, writer, likes, attached) VALUES ('1', '태그,이렇게,들어갈예정', null,'제목1','2021-10-27T12:00:00', '작성자', 1, 'File01,File02');
INSERT INTO POST(category_id, tags, follow_id, title, date, writer, likes, attached) VALUES ('1', '아파치,자바,리눅스', null,'제목2','2021-10-27T12:00:00', '서진하', 12, 'File01');
INSERT INTO POST(category_id, tags, follow_id, title, date, writer, likes, attached) VALUES ('1', 'Java,Stream,CompletableFuture', 1,'제목3','2021-10-27T12:00:00', '서진하', 11, null);
INSERT INTO POST(category_id, tags, follow_id, title, date, writer, likes, attached) VALUES ('2', 'Ubuntu,Spring', 2,'제목4','2021-10-27T12:00:00', '서진하', 9, 'File01');
INSERT INTO POST(category_id, tags, follow_id, title, date, writer, likes, attached) VALUES ('2', 'http,debug', 4,'제목5','2021-10-27T12:00:00', '서진하', 100, null);