create table news
(
    id            bigint primary key auto_increment,
    title         text character set utf8mb4,
    content       text character set utf8mb4,
    date          timestamp,
    url           varchar(1000),
    created_date  timestamp default now(),
    modified_date timestamp default now(),
    news_type     varchar(50) not null
) DEFAULT CHARSET = utf8mb4;


create table news_index
(
    id   int primary key not null,
    type varchar(50) character set utf8mb4 not null
) DEFAULT CHARSET = utf8mb4;


create table links_to_be_solved
(
    link varchar(1000)
) DEFAULT CHARSET = utf8mb4;
create table links_solved
(
    link varchar(1000)
) DEFAULT CHARSET = utf8mb4;

insert into news_index
values (1, 'news'),
       (2, 'ent'),
       (3, 'finance'),
       (4, 'mil'),
       (5, 'sports'),
       (6, 'tech');

insert into links_to_be_solved
values ('https://sina.cn/index/feed?from=touch&Ver=20');