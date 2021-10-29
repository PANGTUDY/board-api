/*
DDL script
*/

CREATE TABLE POST (
    post_id INT(20) AUTO_INCREMENT PRIMARY KEY,
    category_id INT(20) not null,
    tags VARCHAR(256) not null,
    follow_id INT(20),
    title VARCHAR(50) not null,
    date datetime not null,
    writer VARCHAR(16) not null,
    likes INT(20),
    attached VARCHAR(200)
);