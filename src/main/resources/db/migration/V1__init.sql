create table news
(
    id            bigint primary key auto_increment,
    title         text character set utf8mb4,
    content       text character set utf8mb4,
    date          timestamp,
    url           varchar(1000),
    created_date  timestamp default now(),
    modified_date timestamp default now()
) DEFAULT CHARSET = utf8mb4;


create table finance_news
(
    id            bigint primary key auto_increment,
    title         text character set utf8mb4,
    content       text character set utf8mb4,
    date          timestamp,
    url           varchar(1000),
    created_date  timestamp default now(),
    modified_date timestamp default now()
) DEFAULT CHARSET = utf8mb4;

create table sports_news
(
    id            bigint primary key auto_increment,
    title         text character set utf8mb4,
    content       text character set utf8mb4,
    date          timestamp,
    url           varchar(1000),
    created_date  timestamp default now(),
    modified_date timestamp default now()
) DEFAULT CHARSET = utf8mb4;

create table ent_news
(
    id            bigint primary key auto_increment,
    title         text character set utf8mb4,
    content       text character set utf8mb4,
    date          timestamp,
    url           varchar(1000),
    created_date  timestamp default now(),
    modified_date timestamp default now()
) DEFAULT CHARSET = utf8mb4;

create table mil_news
(
    id            bigint primary key auto_increment,
    title         text character set utf8mb4,
    content       text character set utf8mb4,
    date          timestamp,
    url           varchar(1000),
    created_date  timestamp default now(),
    modified_date timestamp default now()
) DEFAULT CHARSET = utf8mb4;

create table tech_news
(
    id            bigint primary key auto_increment,
    title         text character set utf8mb4,
    content       text character set utf8mb4,
    date          timestamp,
    url           varchar(1000),
    created_date  timestamp default now(),
    modified_date timestamp default now()
) DEFAULT CHARSET = utf8mb4;



create table links_to_be_solved
(
    link varchar(1000)
) DEFAULT CHARSET = utf8mb4;
create table links_solved
(
    link varchar(1000)
) DEFAULT CHARSET = utf8mb4;

insert into links_to_be_solved
values ('https://sina.cn/index/feed?from=touch&Ver=20');