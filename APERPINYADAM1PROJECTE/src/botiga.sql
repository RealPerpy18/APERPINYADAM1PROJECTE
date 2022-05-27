--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    dni character varying(9) NOT NULL,
    nom character varying(50),
    correu character varying(30),
    telefon character varying(9),
    adreca character varying(50),
    contrasenya character varying(50)
);


ALTER TABLE public.client OWNER TO postgres;

--
-- Name: producte; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.producte (
    codi integer NOT NULL,
    descripcio character varying(50),
    seccio character varying(20),
    preu numeric(4,2),
    stock integer,
    iva integer
);


ALTER TABLE public.producte OWNER TO postgres;

--
-- Name: codi_prod_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.codi_prod_seq
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.codi_prod_seq OWNER TO postgres;

--
-- Name: codi_prod_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.codi_prod_seq OWNED BY public.producte.codi;


--
-- Name: factura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.factura (
    num_factura bigint NOT NULL,
    dni_client character varying(9),
    data_compra date
);


ALTER TABLE public.factura OWNER TO postgres;

--
-- Name: linia_factures; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.linia_factures (
    num_factura bigint,
    codi_prod integer,
    unitats integer
);


ALTER TABLE public.linia_factures OWNER TO postgres;

--
-- Name: producte codi; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producte ALTER COLUMN codi SET DEFAULT nextval('public.codi_prod_seq'::regclass);


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client (dni, nom, correu, telefon, adreca, contrasenya) FROM stdin;
41533839W	Arnau Perpinyà Tor	aperpinyator@gmail.com	123456789	Carrer vermell n61	123456
\.


--
-- Data for Name: factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.factura (num_factura, dni_client, data_compra) FROM stdin;
-148089648	41533839W	2022-05-27
\.


--
-- Data for Name: linia_factures; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.linia_factures (num_factura, codi_prod, unitats) FROM stdin;
-148089648	1009	1
-148089648	1011	2
-148089648	1013	1
\.


--
-- Data for Name: producte; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.producte (codi, descripcio, seccio, preu, stock, iva) FROM stdin;
1005	Pernil Dolç	Embutit	1.65	20	21
1006	Llogurt grec	Llogurts	0.75	35	12
1007	Pernil Iberic	Embutit	4.35	15	21
1008	Formatge havarti	Embutit	2.15	20	15
1009	Filet de vedella	Carn	14.35	8	12
1010	Llogurts de maduixa	Llogurts	1.10	30	21
1011	Fuet	Embutit	2.15	25	13
1012	Pa de Pages	Pa	1.05	6	15
1013	Pit de pollastre	Carn	3.45	15	21
1014	Cuixa de pollastre	Carn	2.35	20	21
\.


--
-- Name: codi_prod_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.codi_prod_seq', 1014, true);


--
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (dni);


--
-- Name: factura factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura
    ADD CONSTRAINT factura_pkey PRIMARY KEY (num_factura);


--
-- Name: producte producte_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.producte
    ADD CONSTRAINT producte_pkey PRIMARY KEY (codi);


--
-- Name: factura factura_dni_client_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura
    ADD CONSTRAINT factura_dni_client_fkey FOREIGN KEY (dni_client) REFERENCES public.client(dni);


--
-- PostgreSQL database dump complete
--

