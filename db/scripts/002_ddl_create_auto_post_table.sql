create table auto_post
(
    id  serial primary key,
    description varchar(255) not null,
    created timestamp without time zone,
    auto_user_id int REFERENCES auto_user(id)

);
