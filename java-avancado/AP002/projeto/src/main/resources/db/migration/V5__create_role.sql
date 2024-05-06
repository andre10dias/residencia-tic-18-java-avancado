CREATE SEQUENCE role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL DEFAULT nextval('role_id_seq'::regclass),
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id),
    CONSTRAINT nome_ukey UNIQUE (nome)
)