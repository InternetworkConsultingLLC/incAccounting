USE "%DATABASE%";

SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE "Time Entry Types" CHANGE "Payable" "Is Payable" BIT NOT NULL;
ALTER TABLE "Time Entry Types" ADD "Items GUID" CHAR(32);
ALTER TABLE "Time Entry Types" ADD CONSTRAINT "Time Entry Types>Item" FOREIGN KEY ("Items GUID") REFERENCES "Items" ("GUID");
ALTER TABLE "Time Entry Types" DROP "Is Payable";

ALTER TABLE "Time Entries" DROP FOREIGN KEY "Time Entries>User";
ALTER TABLE "Time Entries" DROP "Users GUID";

ALTER TABLE "Time Sheets" ADD "Number" VARCHAR(128) NOT NULL UNIQUE;

ALTER TABLE "Time Entries" CHANGE "Contacts GUID" "Contacts GUID" CHAR(32);
ALTER TABLE "Time Entries" CHANGE "Description" "Description" VARCHAR(255);

INSERT INTO "Time Entry Types" ("GUID", "Description") VALUES ('d99313c888db4f71bd45c43cd09b492a', 'Incomplete');

INSERT INTO "report blocks" ("GUID", "Reports GUID", "Parent Block GUID", "Priority", "Name", "SQL Query") VALUES ('33ece97e1f074621871a260226dbce53','8fdb0c8bbfb84ead88aed2ee3674b498',NULL,1,'Row','SELECT * FROM  (\r\n	SELECT \r\n		TE.\"GUID\",\r\n		TE.\"Started\",\r\n        CAST(IFNULL(TIMESTAMPDIFF(MINUTE, TE.\"Started\", TE.\"Ended\"), TIMESTAMPDIFF(MINUTE, TE.\"Started\", NOW())) / 60 AS DECIMAL(64,2)) AS \"Hours\",\r\n		E.\"Display Name\" AS \"Employee\",\r\n		TET.\"Description\" AS \"Type\",\r\n		I.\"Number\" AS \"Item\",\r\n		C.\"Display Name\" AS \"Contact\",\r\n		CASE\r\n			WHEN TET.\"Items GUID\" IS NOT NULL AND TE.\"Documents GUID\" IS NULL THEN \'Unbilled\'\r\n			WHEN TET.\"Items GUID\" IS NOT NULL AND TE.\"Documents GUID\" IS NULL THEN \'Invoiced\'\r\n			ELSE \'Not Billable\'\r\n		END AS \"Status\",\r\n		CASE\r\n			WHEN LENGTH(TE.\"Description\") > 32 THEN CONCAT(LEFT(TE.\"Description\", 32), \'...\')\r\n			ELSE TE.\"Description\"\r\n		END AS \"Description\"\r\n	FROM \r\n		\"Time Entries\" TE\r\n		JOIN \"Contacts\" E ON E.\"GUID\" = TE.\"Employees GUID\"\r\n		JOIN \"Time Entry Types\" TET ON TET.\"GUID\" = TE.\"Entry Types GUID\"\r\n		LEFT JOIN \"Items\" I ON I.\"GUID\" = TET.\"Items GUID\"\r\n		LEFT JOIN \"Contacts\" C ON C.\"GUID\" = TE.\"Contacts GUID\"\r\n) \r\nTBL \r\nWHERE\r\n    DATE(\"Started\") <= DATE({Ending}) \r\n    AND DATE(\"Started\") >= DATE({Starting})\r\n    AND (\r\n        LENGTH(IFNULL({Employee}, \'\')) = 0 OR\r\n        \"Employee\" LIKE CONCAT(\'%\', IFNULL({Employee}, \'\'), \'%\')\r\n    )\r\n    AND (\r\n        LENGTH(IFNULL({Type}, \'\')) = 0 OR\r\n        \"Type\" LIKE CONCAT(\'%\', IFNULL({Type}, \'\'), \'%\')\r\n    )\r\n    AND (\r\n        LENGTH(IFNULL({Status}, \'\')) = 0 OR\r\n        \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\n    )\r\n    AND (\r\n        LENGTH(IFNULL({Contact}, \'\')) = 0 OR\r\n        \"Contact\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n    )\r\nORDER BY \"Started\" DESC, \"Employee\"');
INSERT INTO "report filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('812d931780b0491bb50eb2dc7087ba7e','8fdb0c8bbfb84ead88aed2ee3674b498','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',3);
INSERT INTO "report filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('9cf2ea4b68c8456b968ddfc77fef8bc9','8fdb0c8bbfb84ead88aed2ee3674b498','Contact','Text',NULL,6);
INSERT INTO "report filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('c3f6fa77c66841b6a03f964c41d59132','8fdb0c8bbfb84ead88aed2ee3674b498','Employee','Text',NULL,5);
INSERT INTO "report filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('c9049c5fa80840f685a3960387745bcf','8fdb0c8bbfb84ead88aed2ee3674b498','Type','Text','SELECT \'incomplete\' AS \"Value\"',8);
INSERT INTO "report filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('f3e9e2a616544204bbf96a647e615378','8fdb0c8bbfb84ead88aed2ee3674b498','Status','Text',NULL,7);
INSERT INTO "reports" ("GUID", "Display Name", "HTML Template", "Title", "Auto Load") VALUES ('8fdb0c8bbfb84ead88aed2ee3674b498','List - Accounting - Time Entries','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry\" class=\"add\">Add</a></td>\r\n        <td>Started</td>\r\n        <td>Hours</td>\r\n        <td>Employee</td>\r\n        <td>Type</td>\r\n        <td>Item</td>\r\n        <td>Contact</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D8fdb0c8bbfb84ead88aed2ee3674b498\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}\">{Started}</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}\">{Hours}</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}\">{Employee}</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}\">{Type}</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}\">{Item}</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}\">{Contact}</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <tr>\r\n        <td></td>\r\n        <td colspan=\"7\"><a href=\"~/incAccounting?App=TimeEntry&GUID={GUID}\">{Description}</a></td>\r\n    </tr>\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- END Row -->\r\n</table>','Time Entries','\0');
INSERT INTO "securables" ("GUID", "Display Name") VALUES ('8fdb0c8bbfb84ead88aed2ee3674b498','Report - List - Accounting - Time Entries');

INSERT INTO "report blocks" ("GUID", "Reports GUID", "Parent Block GUID", "Priority", "Name", "SQL Query") VALUES ('021e05b6bcfa4bdfa823f5f8cb1fae5c','a51f368f999a4ebdb900a1e1cfac9ebb',NULL,1,'Row','SELECT\r\n	\"GUID\",\r\n    \"Description\"\r\nFROM \"Time Entry Types\"\r\nORDER BY \"Description\"');
INSERT INTO "reports" ("GUID", "Display Name", "HTML Template", "Title", "Auto Load") VALUES ('a51f368f999a4ebdb900a1e1cfac9ebb','List - Accounting - Time Entry Types','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=TimeEntryType\" class=\"add\">Add</a></td>\r\n        <td>Description</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=TimeEntryType&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Da51f368f999a4ebdb900a1e1cfac9ebb\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeEntryType&GUID={GUID}\">{Description}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>',' Time Entry Types','\0');
INSERT INTO "securables" ("GUID", "Display Name") VALUES ('a51f368f999a4ebdb900a1e1cfac9ebb','Report - List - Accounting - Time Entry Types');

ALTER TABLE "Time Sheets" DROP FOREIGN KEY "Time Sheets>User";
ALTER TABLE "Time Sheets" DROP "Users GUID";

ALTER TABLE "Time Sheets" DROP FOREIGN KEY "Time Sheets>Payroll Check";
ALTER TABLE "Time Sheets" DROP "Payroll Checks GUID";

ALTER TABLE "Time Sheets" ADD "Period Ending" DATE NOT NULL;
ALTER TABLE "Time Sheets" ADD "Regular Hours" DECIMAL(64, 16) NOT NULL;
ALTER TABLE "Time Sheets" ADD "Overtime Hours" DECIMAL(64, 16) NOT NULL;
ALTER TABLE "Time Sheets" ADD "Total Hours" DECIMAL(64, 16) NOT NULL;
ALTER TABLE "Time Sheets" ADD CONSTRAINT "Unique Time Sheet" UNIQUE ("Employees GUID", "Period Ending");

INSERT INTO "report blocks" ("GUID", "Reports GUID", "Parent Block GUID", "Priority", "Name", "SQL Query") VALUES ('a391ad8fc3014fcba756c791656e492d','8e32580ad6aa45d592e4182876a0a23a',NULL,1,'Row','SELECT\r\n    *\r\nFROM (\r\n\r\n    SELECT \r\n    	TS.\"GUID\",\r\n        E.\"Display Name\" AS \"Employee\",\r\n        TS.\"Period Ending\" AS \"Ending\",\r\n        CAST(TS.\"Total Hours\" AS DECIMAL(64,16)) AS \"Hours\"\r\n    FROM \r\n    	\"Time Sheets\" TS\r\n        JOIN \"Contacts\" E ON TS.\"Employees GUID\" = E.\"GUID\"\r\n) TBL\r\nORDER BY\r\n    \"Ending\"');
INSERT INTO "report filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('8a185563e2324254a2377621fc6d35bd','8e32580ad6aa45d592e4182876a0a23a','Employee','Text',NULL,1);
INSERT INTO "report filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('db177768ee634236ac08d2b0184f60ca','8e32580ad6aa45d592e4182876a0a23a','Starting','Date',NULL,2);
INSERT INTO "report filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('0f65127f303346ce846e6f4899614bfb','8e32580ad6aa45d592e4182876a0a23a','Ending','Date',NULL,3);
INSERT INTO "reports" ("GUID", "Display Name", "HTML Template", "Title", "Auto Load") VALUES ('8e32580ad6aa45d592e4182876a0a23a','List - Accounting - Time Sheets','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=TimeSheet\" class=\"add\">Add</a></td>\r\n        <td>Employee</td>\r\n        <td>Ending</td>\r\n        <td>Total Hours</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=TimeSheet&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Da51f368f999a4ebdb900a1e1cfac9ebb\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeSheet&GUID={GUID}\">{Employee}</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeSheet&GUID={GUID}\">{Ending}</a></td>\r\n        <td><a href=\"~/incAccounting?App=TimeSheet&GUID={GUID}\">{Hours}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>',' Time Sheets','\0');
INSERT INTO "securables" ("GUID", "Display Name") VALUES ('8e32580ad6aa45d592e4182876a0a23a','Report - List - Accounting - Time Sheets');

ALTER TABLE "Time Sheets" DROP "Regular Hours";
ALTER TABLE "Time Sheets" DROP "Overtime Hours";

ALTER TABLE "Time Entry Types" ADD "Is Paid" BIT;
UPDATE "Time Entry Types" SET "Is Paid" = 0;
ALTER TABLE "Time Entry Types" CHANGE "Is Paid" "Is Paid" BIT NOT NULL;

ALTER TABLE "Time Sheets" ADD "Period Starting" DATE NOT NULL;

UPDATE "Settings" SET "Value" = '2017.5.12' WHERE "Key" = 'Version Number';

SET FOREIGN_KEY_CHECKS=1;