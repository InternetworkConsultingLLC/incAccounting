USE "%DATABASE%";

CREATE TABLE "Register Entries" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Contacts GUID" CHAR(32)
);
ALTER TABLE "Register Entries" ADD CONSTRAINT "Register Entries>Transaction Line" FOREIGN KEY ("GUID") REFERENCES "Transaction Lines" ("GUID");
ALTER TABLE "Register Entries" ADD CONSTRAINT "Register Entries>Contact" FOREIGN KEY ("Contacts GUID") REFERENCES "Contacts" ("GUID");

INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('38ec8da627f345eb9f0639c0931d5db8','Register Entry','/incAccounting?App=RegisterEntry','/incAccounting?App=RegisterEntry',1);
