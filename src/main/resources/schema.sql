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
    contents    VARCHAR(50)  not null,
    date        datetime     not null,
    writer      VARCHAR(50)  not null,
    likes       INT(20),
    attached    VARCHAR(200),
    FOREIGN KEY (category_id) REFERENCES CATEGORY (category_id) ON DELETE CASCADE
);

CREATE TABLE COMMENT
(
    comment_id    INT(20) AUTO_INCREMENT PRIMARY KEY,
    post_id       INT(20)      not null,
    follow_id     INT(20),
    writer        VARCHAR(50)  not null,
    contents      VARCHAR(500) not null,
    date          datetime     not null,
    modified_date datetime,
    FOREIGN KEY (post_id) REFERENCES POST (post_id) ON DELETE CASCADE
);

CREATE TABLE CONTENT
(
    post_id  INT(20),
    contents VARCHAR(10000) not null,
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


CREATE TABLE TAG
(
    post_id  VARCHAR(20)    not null,
    post_ids VARCHAR(10000) not null,
    FOREIGN KEY (post_id) REFERENCES POST (post_id) ON DELETE CASCADE
);
