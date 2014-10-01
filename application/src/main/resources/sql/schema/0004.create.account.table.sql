CREATE TABLE IF NOT EXISTS public.account
(
   id integer NOT NULL, 
   name text, 
   password text, 
   email text, 
   sms_number text, 
   email_send boolean, 
   sms_send boolean, 
   PRIMARY KEY (id)
);
ALTER TABLE public.account
  OWNER TO postgres;