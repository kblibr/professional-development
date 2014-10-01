CREATE TABLE IF NOT EXISTS public.registrations
(
   id integer NOT NULL,
   clip_id integer,
   account_id integer,
   created integer,
   PRIMARY KEY (id)
);
ALTER TABLE public.registrations
  OWNER TO postgres;