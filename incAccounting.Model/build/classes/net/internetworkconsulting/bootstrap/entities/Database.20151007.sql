CREATE DATABASE "%DATABASE%";
USE "%DATABASE%";

CREATE TABLE "Users"(
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Is Allowed" BIT NOT NULL,
	"Display Name" VARCHAR(64) NOT NULL UNIQUE,
	"SQL User" VARCHAR(64) NOT NULL UNIQUE,
	"Email Address" VARCHAR(64) NOT NULL UNIQUE,
	"Password Date" DATE NOT NULL,
	"Add Computer" BIT NOT NULL
);

CREATE TABLE "Groups" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Is Allowed" BIT NOT NULL,
	"Display Name" VARCHAR(64) NOT NULL UNIQUE,
	"Email Address" VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE "Computers" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Description" VARCHAR(64) NOT NULL UNIQUE,
	"MAC Address" VARCHAR(64) NOT NULL UNIQUE,
	"Is Allowed" BIT NOT NULL
);

CREATE TABLE "Memberships" (
	"Users GUID" CHAR(32) NOT NULL,
	"Groups GUID" CHAR(32) NOT NULL
);

ALTER TABLE "Memberships" ADD CONSTRAINT "Unique Membership" UNIQUE ("Users GUID", "Groups GUID");
ALTER TABLE "Memberships" ADD CONSTRAINT "Group<Memberships" FOREIGN KEY ("Groups GUID") REFERENCES "Groups" ("GUID");
ALTER TABLE "Memberships" ADD CONSTRAINT "User<Memberships" FOREIGN KEY("Users GUID") REFERENCES "Users" ("GUID");

CREATE TABLE "Securables" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Display Name" VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE "Permissions" (
	"Groups GUID" CHAR(32) NOT NULL,
	"Securables GUID" CHAR(32) NOT NULL,
	"Can Create" BIT NOT NULL,
	"Can Read" BIT NOT NULL,
	"Can Update" BIT NOT NULL,
	"Can Delete" BIT NOT NULL
);

ALTER TABLE "Permissions" ADD CONSTRAINT "Unique Permissions" UNIQUE ("Securables GUID", "Groups GUID");
ALTER TABLE "Permissions" ADD CONSTRAINT "Group<Permissions" FOREIGN KEY("Groups GUID") REFERENCES "Groups" ("GUID");
ALTER TABLE "Permissions" ADD CONSTRAINT "Securable<Permissions" FOREIGN KEY("Securables GUID") REFERENCES "Securables" ("GUID");

CREATE TABLE "Settings" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Users GUID" CHAR(32) NULL,
	"Key" VARCHAR(64) NOT NULL,
	"Type" VARCHAR(64) NOT NULL,
	"Option Query" TEXT,
	"Value" TEXT NOT NULL
);

ALTER TABLE "Settings" ADD CONSTRAINT "Unique Settings User" UNIQUE ("Key", "Users GUID");
ALTER TABLE "Settings" ADD CONSTRAINT "User<Settings" FOREIGN KEY("Users GUID") REFERENCES "Users" ("GUID");

CREATE TABLE "Logs" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Occured" DATETIME NOT NULL,
	"Users GUID" CHAR(32) NOT NULL,
	"Computers GUID" CHAR(32) NOT NULL,
	"Code GUID" CHAR(32) NOT NULL, -- each logger should have a distinct GUID in the code, this is for cross referencing logs with code
	"Log" VARCHAR(32) NOT NULL,
	"Details" TEXT NOT NULL
);

ALTER TABLE "Logs" ADD CONSTRAINT "User<Logs" FOREIGN KEY("Users GUID") REFERENCES "Users" ("GUID");
ALTER TABLE "Logs" ADD CONSTRAINT "Computer<Logs" FOREIGN KEY("Computers GUID") REFERENCES "Computers" ("GUID");

CREATE TABLE "Reports" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Display Name" VARCHAR(128) UNIQUE NOT NULL,
	"HTML Template" VARCHAR(4095) NOT NULL
);
ALTER TABLE "Reports" ADD CONSTRAINT "Securable<Report" FOREIGN KEY("GUID") REFERENCES "Securables" ("GUID");

CREATE TABLE "Report Filters" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Reports GUID" CHAR(32) NOT NULL,

	"Prompt" VARCHAR(128) NOT NULL,
	"Data Type" VARCHAR(8) NOT NULL,
	"Query" VARCHAR(4096)
);
ALTER TABLE "Report Filters" ADD CONSTRAINT "Unique Filter Prompt" UNIQUE ("Reports GUID", "Prompt");
ALTER TABLE "Report Filters" ADD CONSTRAINT "Filters>Report" FOREIGN KEY ("Reports GUID") REFERENCES "Reports" ("GUID");

CREATE TABLE "Report Blocks" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Reports GUID" CHAR(32),
	"Parent Block GUID" CHAR(32),
	"Priority" INT NOT NULL,

	"Name" VARCHAR(128) NOT NULL,
	"SQL Query" VARCHAR(4096) NOT NULL	
);
ALTER TABLE "Report Blocks" ADD CONSTRAINT "Unique Block Name" UNIQUE ("Reports GUID", "Name");
ALTER TABLE "Report Blocks" ADD CONSTRAINT "Unique Block Order" UNIQUE ("Reports GUID", "Priority");
ALTER TABLE "Report Blocks" ADD CONSTRAINT "Blocks>Report" FOREIGN KEY ("Reports GUID") REFERENCES "Reports" ("GUID");
ALTER TABLE "Report Blocks" ADD CONSTRAINT "Children>Parent" FOREIGN KEY ("Parent Block GUID") REFERENCES "Report Blocks" ("GUID");

