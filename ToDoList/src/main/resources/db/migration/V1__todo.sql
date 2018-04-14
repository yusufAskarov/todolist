create sequence user_roles_role_id_seq;
create sequence task_status_status_id_seq;
create sequence users_user_id_seq;
create sequence task_task_id_seq;

CREATE TABLE user_roles
(
  role_id integer NOT NULL DEFAULT nextval('user_roles_role_id_seq'::regclass),
  role_code character varying(255),
  role_description character varying(255),
  role_name character varying(255),
  CONSTRAINT user_roles_pkey PRIMARY KEY (role_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_roles
  OWNER TO postgres;


CREATE TABLE task_status
(
  status_id integer NOT NULL DEFAULT nextval('task_status_status_id_seq'::regclass),
  status_code character varying(255),
  status_description character varying(255),
  status_name character varying(255),
  CONSTRAINT task_status_pkey PRIMARY KEY (status_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE task_status
  OWNER TO postgres;


CREATE TABLE users
(
  user_id bigint NOT NULL DEFAULT nextval('users_user_id_seq'::regclass),
  age integer NOT NULL,
  birth_day date,
  create_time timestamp without time zone NOT NULL,
  email character varying(255),
  first_name character varying(255),
  gender character varying(255),
  is_arc integer,
  last_name character varying(255),
  middle_name character varying(255),
  password character varying(255),
  update_time timestamp without time zone NOT NULL,
  user_name character varying(255),
  role_id integer,
  active integer,
  CONSTRAINT users_pkey PRIMARY KEY (user_id),
  CONSTRAINT fkh555fyoyldpyaltlb7jva35j2 FOREIGN KEY (role_id)
      REFERENCES user_roles (role_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;

CREATE TABLE task
(
  task_id bigint NOT NULL DEFAULT nextval('task_task_id_seq'::regclass),
  create_time timestamp without time zone NOT NULL,
  fact_finish_date date,
  is_arch integer,
  note character varying(255),
  planned_finish_date date,
  task character varying(255),
  task_description character varying(255),
  update_time timestamp without time zone NOT NULL,
  status_id integer,
  user_id bigint,
  task_name character varying(255),
  CONSTRAINT task_pkey PRIMARY KEY (task_id),
  CONSTRAINT fkbhwpp8tr117vvbxhf5sbkdkc9 FOREIGN KEY (user_id)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkhbyh5kk1fpobspep796vg5psq FOREIGN KEY (status_id)
      REFERENCES task_status (status_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE task
  OWNER TO postgres;

insert into task_status(status_id,status_code, status_name, status_description)
values(1,'inprocess','В процессе','Задача в процессе выполнения');

insert into task_status(status_id,status_code, status_name, status_description)
values(2,'finished','Завершен','Задача завершена');

insert into task_status(status_id,status_code, status_name, status_description)
values(3,'deleted','Удален','Удаленная задача');

insert into user_roles(role_id,role_code,role_name, role_description)
values(1,'admin','Администратор','Администратор системы');

insert into user_roles(role_id,role_code,role_name, role_description)
values(2,'client','Клиент','Обычный пользователь');