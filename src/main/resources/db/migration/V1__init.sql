

create table news (
                      id bigint primary key auto_increment,
                      title text,
                      content text,
                      url varchar(1000),
                      created_date timestamp default now(),
                      modified_date timestamp default now()
);

create table links_to_be_solved (link varchar(1000));
create table links_solved (link varchar(1000));

insert into links_to_be_solved (link) values ( 'http://news.sina.cn/news_zt/szyw' )