USE "%DATABASE%";

SET FOREIGN_KEY_CHECKS=0;

UPDATE "Settings" SET "Key" = 'Password Length' WHERE "GUID" = 'ac17f9e7262b4d05ad137948a437a59a';

DELETE FROM "Report Blocks" WHERE "Reports GUID" IN ('6d4ea7d4624e48509e915e019ca1f7a9', 'a817ee365045448fb67c75db9b586ec8');
DELETE FROM "Report Filters" WHERE "Reports GUID" IN ('6d4ea7d4624e48509e915e019ca1f7a9', 'a817ee365045448fb67c75db9b586ec8');
DELETE FROM "Reports" WHERE "GUID" IN ('6d4ea7d4624e48509e915e019ca1f7a9', 'a817ee365045448fb67c75db9b586ec8');
DELETE FROM "Securables" WHERE "GUID" IN ('6d4ea7d4624e48509e915e019ca1f7a9', 'a817ee365045448fb67c75db9b586ec8');

INSERT INTO "Report Blocks" ("GUID", "Reports GUID", "Parent Block GUID", "Priority", "Name", "SQL Query") VALUES ('91cb388ac26041219bdc7216c26cb75a','6d4ea7d4624e48509e915e019ca1f7a9',NULL,0,'Account','SELECT\r\n  \"Accounts\".\"GUID\",\r\n  \"Accounts\".\"Number\",\r\n  \"Accounts\".\"Name\",\r\n  \"Account Types\".\"Name\" AS \"Account Types Name\",\r\n  CAST(\r\n    IFNULL(\r\n      ( \r\n        SELECT SUM(\"Transaction Lines\".\"Debit\") \r\n        FROM \r\n          \"Transaction Lines\" \r\n          JOIN \"Transactions\" \r\n            ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n        WHERE \r\n          \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n          AND DATE(\"Transactions\".\"Date\") <= DATE({As Of Date})\r\n      )\r\n    , 0) AS DECIMAL(64,2)) AS \"Balance\"\r\nFROM \r\n  \"Accounts\"\r\n  JOIN \"Account Types\" \r\n    ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nORDER BY \"Number\"');
INSERT INTO "Report Blocks" ("GUID", "Reports GUID", "Parent Block GUID", "Priority", "Name", "SQL Query") VALUES ('fdfa5f51a32c4dda806863d21a2622bb','6d4ea7d4624e48509e915e019ca1f7a9',NULL,9,'Footer','SELECT\r\n	SUM(\"Balance\") AS \"Balance\"\r\nFROM (\r\n	SELECT\r\n	  \"Accounts\".\"GUID\",\r\n	  \"Accounts\".\"Number\",\r\n	  \"Accounts\".\"Name\",\r\n	  \"Account Types\".\"Name\" AS \"Account Types Name\",\r\n	  CAST(\r\n		IFNULL(\r\n		  ( \r\n			SELECT SUM(\"Transaction Lines\".\"Debit\") \r\n			FROM \r\n			  \"Transaction Lines\" \r\n			  JOIN \"Transactions\" \r\n				ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n			WHERE \r\n			  \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n			  AND DATE(\"Transactions\".\"Date\") <= DATE({As Of Date})\r\n		  )\r\n		, 0) AS DECIMAL(64,2)) AS \"Balance\"\r\n	FROM \r\n	  \"Accounts\"\r\n	  JOIN \"Account Types\" \r\n		ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\n	ORDER BY \"Number\"\r\n) TBL');
INSERT INTO "Report Filters" ("GUID", "Reports GUID", "Prompt", "Data Type", "Query", "Priority") VALUES ('bf95eba88fc84992be19d2ea414f8e8c','6d4ea7d4624e48509e915e019ca1f7a9','As Of Date','Date','SELECT NOW() AS \"Value\"',26);
INSERT INTO "Reports" ("GUID", "Display Name", "HTML Template", "Title", "Auto Load") VALUES ('6d4ea7d4624e48509e915e019ca1f7a9','Report - Accounting - Trial Balance','<style>\r\n    table a { text-decoration: none; }\r\n</style>\r\n\r\n<table width=\"100%\">\r\n    <tr>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black\">Name</td>\r\n        <td class=\"black center\">Type</td>\r\n        <td class=\"black right\">Balance</td>\r\n    </tr>\r\n    <!-- BEGIN Account -->\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=ReportView&AutoLoad=t&GUID=ee4f00312ff44e3d941c6a3445bf93b0&As Of={As Of Date}&Account={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incBootstrap?App=ReportView&AutoLoad=t&GUID=ee4f00312ff44e3d941c6a3445bf93b0&As Of={As Of Date}&Account={GUID}\">{Name}</a></td>\r\n        <td class=\"center\"><a href=\"~/incBootstrap?App=ReportView&AutoLoad=t&GUID=ee4f00312ff44e3d941c6a3445bf93b0&As Of={As Of Date}&Account={GUID}\">{Account Types Name}</a></td>\r\n        <td class=\"right\"><a href=\"~/incBootstrap?App=ReportView&AutoLoad=t&GUID=ee4f00312ff44e3d941c6a3445bf93b0&As Of={As Of Date}&Account={GUID}\">{Balance}</a></td>\r\n    </tr>\r\n    <!-- END Account -->\r\n    <!-- BEGIN Footer -->\r\n    <tr>\r\n        <td colspan=\"3\" class=\"right black\">Trial Balance:</td>\r\n        <td class=\"right\">{Balance}</td>\r\n    </tr>\r\n    <!-- END Footer -->\r\n</table>','Trial Balance','\0');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('6d4ea7d4624e48509e915e019ca1f7a9','Report - Report - Accounting - Trial Balance');

INSERT INTO "Report Blocks" ("GUID", "Reports GUID", "Parent Block GUID", "Priority", "Name", "SQL Query") VALUES ('010109ae9b974f6294b19e542379a32f','a817ee365045448fb67c75db9b586ec8',NULL,1,'Row','SELECT \r\n	T.\"GUID\" AS \"GUID\",\r\n    T.\"Date\" AS \"Date\",\r\n    T.\"Reference Number\" AS \"Number\",\r\n    CAST(OFF.\"Debit\" AS DECIMAL(64,2)) AS \"Amount\",\r\n    TY.\"Name\" AS \"Type\",\r\n    TY.\"Edit URL\" AS \"Edit URL\"\r\nFROM\r\n	( SELECT \"Transactions GUID\", SUM(Debit) AS Debit FROM \"Transaction Lines\" GROUP BY \"Transactions GUID\" HAVING SUM(Debit) <> 0 ) OFF\r\n    JOIN \"Transactions\" T ON OFF.\"Transactions GUID\" = T.\"GUID\"\r\n    JOIN \"Transaction Types\" TY ON T.\"Transaction Types GUID\" = TY.\"GUID\"');
INSERT INTO "Reports" ("GUID", "Display Name", "HTML Template", "Title", "Auto Load") VALUES ('a817ee365045448fb67c75db9b586ec8','Report - Accounting - Out of Balance','<table class=\"list\">\r\n    <tr>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Type</td>\r\n        <td>Amount</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Type}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Amount}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Out of Balance','\0');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('a817ee365045448fb67c75db9b586ec8','Report - Report - Accounting - Out of Balance');

SET FOREIGN_KEY_CHECKS=1;