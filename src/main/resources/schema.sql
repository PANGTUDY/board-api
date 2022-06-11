/*
DDL script
*/

CREATE TABLE CATEGORY
(
    category_id   INT(20) AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(256) not null
);

CREATE TABLE POST
(
    post_id     INT(20) AUTO_INCREMENT PRIMARY KEY,
    category_id INT(20)      not null,
    tags        VARCHAR(256) not null,
    title       VARCHAR(50)  not null,
    contents    VARCHAR(500)  not null,
    date        datetime     not null,
    writer_id   INT(20)      not null,
    likes       INT(20),
    FOREIGN KEY (category_id) REFERENCES CATEGORY (category_id) ON DELETE CASCADE
);

CREATE TABLE COMMENT
(
    comment_id    INT(20) AUTO_INCREMENT PRIMARY KEY,
    post_id       INT(20)      not null,
    writer_id        INT(20)  not null,
    contents      VARCHAR(100) not null,
    date          datetime     not null,
    FOREIGN KEY (post_id) REFERENCES POST (post_id) ON DELETE CASCADE
);

CREATE TABLE FILE
(
    post_id   INT(20),
    file_id   INT(20) AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(100) not null,
    file_path VARCHAR(300) not null,
    file_type VARCHAR(50)  not null,
    FOREIGN KEY (post_id) REFERENCES POST (post_id) ON DELETE CASCADE
);

CREATE TABLE LIKES
(
    post_id  INT(20)    not null,
    user_id  INT(20)    not null
    -- FOREIGN KEY (user_id) REFERENCES USER (user_id) ON DELETE CASCADE // 유저 삭제 시 테이블에서 제거
);
