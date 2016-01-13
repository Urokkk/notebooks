CREATE TABLE book
(
  id bigserial NOT NULL,
  CONSTRAINT book_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE book
  OWNER TO postgres;

CREATE TABLE person
(
  id bigserial NOT NULL,
  name character varying(255),
  CONSTRAINT person_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE person
  OWNER TO postgres;

CREATE TABLE phone
(
  id bigserial NOT NULL,
  phone character varying(255),
  person_id bigint,
  CONSTRAINT phone_pkey PRIMARY KEY (id ),
  CONSTRAINT fk_person_id FOREIGN KEY (person_id)
      REFERENCES person (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE phone
  OWNER TO postgres;

CREATE TABLE address
(
  id bigserial NOT NULL,
  address character varying(255),
  person_id bigint,
  CONSTRAINT address_pkey PRIMARY KEY (id ),
  CONSTRAINT fk_person_id FOREIGN KEY (person_id)
      REFERENCES person (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE address
  OWNER TO postgres;

ALTER TABLE person ADD COLUMN book_id bigint,
ADD CONSTRAINT fk_book_id FOREIGN KEY (book_id)
REFERENCES book (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;