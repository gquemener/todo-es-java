CREATE SEQUENCE domainevententry_globalindex_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE IF NOT EXISTS "domainevententry" (
    "globalindex" bigint DEFAULT nextval('domainevententry_globalindex_seq') NOT NULL,
    "aggregateidentifier" character varying(255) NOT NULL,
    "sequencenumber" bigint NOT NULL,
    "type" character varying(255),
    "eventidentifier" character varying(255) NOT NULL,
    "metadata" bytea,
    "payload" bytea NOT NULL,
    "payloadrevision" character varying(255),
    "payloadtype" character varying(255) NOT NULL,
    "timestamp" character varying(255) NOT NULL,
    CONSTRAINT "domainevententry_aggregateidentifier_sequencenumber_key" UNIQUE ("aggregateidentifier", "sequencenumber"),
    CONSTRAINT "domainevententry_eventidentifier_key" UNIQUE ("eventidentifier"),
    CONSTRAINT "domainevententry_pkey" PRIMARY KEY ("globalindex")
) WITH (oids = false);


CREATE TABLE IF NOT EXISTS "snapshotevententry" (
    "aggregateidentifier" character varying(255) NOT NULL,
    "sequencenumber" bigint NOT NULL,
    "type" character varying(255) NOT NULL,
    "eventidentifier" character varying(255) NOT NULL,
    "metadata" bytea,
    "payload" bytea NOT NULL,
    "payloadrevision" character varying(255),
    "payloadtype" character varying(255) NOT NULL,
    "timestamp" character varying(255) NOT NULL,
    CONSTRAINT "snapshotevententry_eventidentifier_key" UNIQUE ("eventidentifier"),
    CONSTRAINT "snapshotevententry_pkey" PRIMARY KEY ("aggregateidentifier", "sequencenumber")
) WITH (oids = false);

CREATE TABLE IF NOT EXISTS "tokenentry" (
    "processorname" VARCHAR(255) NOT NULL,
    "segment" INTEGER NOT NULL,
    "token" bytea NULL,
    "tokentype" VARCHAR(255) NULL,
    "timestamp" VARCHAR(255) NULL,
    "owner" VARCHAR(255) NULL,
    PRIMARY KEY ("processorname", "segment")
);
-- 2022-11-23 08:14:07.340016+00
