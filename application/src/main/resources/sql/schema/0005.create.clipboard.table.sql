CREATE TABLE IF NOT EXISTS public.clipboard
(
   id integer NOT NULL,
   title text,
   description text,
   start_time integer,
   end_time integer,
   PRIMARY KEY (id)
);
ALTER TABLE public.clipboard
  OWNER TO postgres;