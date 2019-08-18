-- Drop table

-- DROP TABLE public."storage";

CREATE TABLE public."storage" (
	id serial NOT NULL,
	"name" varchar(55) NOT NULL,
	email character varying(55) NOT NULL,
	status varchar(55) NOT NULL,
	created timestamp NULL,
	updated timestamp NULL
);
CREATE INDEX storage_id_idx ON public.storage USING btree (id);

-- Permissions

ALTER TABLE public."storage" OWNER TO "www-data";
GRANT ALL ON TABLE public."storage" TO "www-data";