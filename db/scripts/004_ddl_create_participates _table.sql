CREATE TABLE participates (
   id SERIAL PRIMARY KEY,
   post_id int not null REFERENCES auto_post(id),
   user_id int not null REFERENCES user_post(id)
);