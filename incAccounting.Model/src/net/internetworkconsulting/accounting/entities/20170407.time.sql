USE "%DATABASE%";

ALTER TABLE "Users" ADD COLUMN "Employees GUID" VARCHAR(32);
ALTER TABLE "Users" ADD CONSTRAINT "Employee<Users" FOREIGN KEY ("Employees GUID") REFERENCES "Employees" ("GUID");

CREATE TABLE "Time Sheets" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Users GUID" CHAR(32) NOT NULL,
	"Employees GUID" CHAR(32) NOT NULL,
	"Payroll Checks GUID" CHAR(32)
);

ALTER TABLE "Time Sheets" ADD CONSTRAINT "Time Sheets>User" FOREIGN KEY ("Users GUID") REFERENCES "Users" ("GUID");
ALTER TABLE "Time Sheets" ADD CONSTRAINT "Time Sheets>Employee" FOREIGN KEY ("Employees GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Time Sheets" ADD CONSTRAINT "Time Sheets>Payroll Check" FOREIGN KEY ("Payroll Checks GUID") REFERENCES "Payroll Checks" ("GUID");

CREATE TABLE "Time Entry Types" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Description" VARCHAR(128) NOT NULL UNIQUE,
	"Payable" BIT NOT NULL
);

INSERT INTO "Time Entry Types" ("GUID", "Description", "Payable") VALUES ('8fdb6b4c4cca4e349a836f0a6dac066e', 'Holiday', 1);
INSERT INTO "Time Entry Types" ("GUID", "Description", "Payable") VALUES ('e18486a9bff14b9aa6c015cd457b0f1e', 'Paid Time Off', 1);
INSERT INTO "Time Entry Types" ("GUID", "Description", "Payable") VALUES ('824ea506cfd545b5a4b400eb10819e51', 'Lunch', 0);
INSERT INTO "Time Entry Types" ("GUID", "Description", "Payable") VALUES ('ae50bbe1e0314314a90ed886485bd6a3', 'Break', 0);
INSERT INTO "Time Entry Types" ("GUID", "Description", "Payable") VALUES ('8ae26b9e04b844aa840939de0c53ab96', 'Working', 1);

CREATE TABLE "Time Entries" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Entry Types GUID" CHAR(32) NOT NULL,
	"Users GUID" CHAR(32) NOT NULL,
	"Employees GUID" CHAR(32) NOT NULL,
	"Contacts GUID" CHAR(32) NOT NULL,
	"Time Sheets GUID" CHAR(32),
	"Documents GUID" CHAR(32),
	"Jobs GUID" CHAR(32),
	"Departments GUID" CHAR(32),

	"Started" DATETIME NOT NULL,
	"Ended" DATETIME,

	"Description" VARCHAR(255) NOT NULL
);

ALTER TABLE "Time Entries" ADD CONSTRAINT "Time Entries>User" FOREIGN KEY ("Users GUID") REFERENCES "Users" ("GUID");
ALTER TABLE "Time Entries" ADD CONSTRAINT "Time Entries>TimeEntryType" FOREIGN KEY ("Entry Types GUID") REFERENCES "Time Entry Types" ("GUID");
ALTER TABLE "Time Entries" ADD CONSTRAINT "Time Entries>Employee" FOREIGN KEY ("Employees GUID") REFERENCES "Employees" ("GUID");
ALTER TABLE "Time Entries" ADD CONSTRAINT "Time Entries>Contact" FOREIGN KEY ("Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Time Entries" ADD CONSTRAINT "Time Entries>Time Sheet" FOREIGN KEY ("Time Sheets GUID") REFERENCES "Time Sheets" ("GUID");
ALTER TABLE "Time Entries" ADD CONSTRAINT "Time Entries>Document" FOREIGN KEY ("Documents GUID") REFERENCES "Documents" ("GUID");
ALTER TABLE "Time Entries" ADD CONSTRAINT "Time Entries>Job" FOREIGN KEY ("Jobs GUID") REFERENCES "Jobs" ("GUID");
ALTER TABLE "Time Entries" ADD CONSTRAINT "Time Entries>Department" FOREIGN KEY ("Departments GUID") REFERENCES "Departments" ("GUID");

UPDATE "Settings" SET "Key" = 'Password Length' WHERE "GUID" = 'ac17f9e7262b4d05ad137948a437a59a';

DELETE FROM "Permissions" WHERE "Securables GUID" IN (SELECT "GUID" FROM "Securables" WHERE "Display Name" LIKE 'Table - %');
DELETE FROM "Securables" WHERE "Display Name" LIKE 'Table - %';

INSERT INTO "Securables" ("Display Name", "GUID")
SELECT CONCAT('Table - ', LOWER(TABLE_NAME)), MD5(LOWER(TABLE_NAME)) FROM information_schema.TABLES WHERE TABLE_SCHEMA = '%DATABASE%';

UPDATE "Settings" SET "Value" = '2017.4.7' WHERE "Key" = 'Version Number';