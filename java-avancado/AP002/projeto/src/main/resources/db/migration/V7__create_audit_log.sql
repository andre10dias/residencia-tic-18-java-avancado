CREATE SEQUENCE audit_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.audit_log
(
    id bigint NOT NULL,
    affected_resource character varying(255) COLLATE pg_catalog."default" NOT NULL,
    event_description character varying(255) COLLATE pg_catalog."default" NOT NULL,
    event_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    origin character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "timestamp" timestamp(6) without time zone NOT NULL,
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT audit_log_pkey PRIMARY KEY (id),
    CONSTRAINT event_name_uk UNIQUE (event_name)
)