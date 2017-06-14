SET FOREIGN_KEY_CHECKS=0;
DELETE FROM "reports" WHERE "GUID"='8fdb0c8bbfb84ead88aed2ee3674b498';
DELETE FROM "securables" WHERE "GUID"='8fdb0c8bbfb84ead88aed2ee3674b498';
DELETE FROM "report filters" WHERE "GUID"='d1702ada92934012b74ae61546d830e6';
DELETE FROM "report filters" WHERE "GUID"='812d931780b0491bb50eb2dc7087ba7e';
DELETE FROM "report filters" WHERE "GUID"='c3f6fa77c66841b6a03f964c41d59132';
DELETE FROM "report filters" WHERE "GUID"='9cf2ea4b68c8456b968ddfc77fef8bc9';
DELETE FROM "report filters" WHERE "GUID"='f3e9e2a616544204bbf96a647e615378';
DELETE FROM "report filters" WHERE "GUID"='c9049c5fa80840f685a3960387745bcf';
DELETE FROM "report blocks" WHERE "GUID"='33ece97e1f074621871a260226dbce53';
INSERT INTO "reports" ( "Auto Load",  "Display Name",  "GUID",  "HTML Template",  "Query",  "Title" ) VALUES (0, 'List - Accounting - Time Entries', '8fdb0c8bbfb84ead88aed2ee3674b498', '<table class="list">\r\n    <tr>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry" class="add">Add</a></td>\r\n        <td>Started</td>\r\n        <td>Hours</td>\r\n        <td>Employee</td>\r\n        <td>Type</td>\r\n        <td>Item</td>\r\n        <td>Contact</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D8fdb0c8bbfb84ead88aed2ee3674b498" class="delete">Delete</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}">{Started}</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}">{Hours}</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}">{Employee}</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}">{Type}</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}">{Item}</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}">{Contact}</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}">{Status}</a></td>\r\n    </tr>\r\n    <tr>\r\n        <td></td>\r\n        <td colspan="7"><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=TimeEntry&GUID={GUID}">{Description}</a></td>\r\n    </tr>\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- END Row -->\r\n</table>', NULL, 'Time Entries' );
INSERT INTO "securables" ( "Display Name",  "GUID" ) VALUES ('Report - List - Accounting - Time Entries', '8fdb0c8bbfb84ead88aed2ee3674b498');
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Date', 'd1702ada92934012b74ae61546d830e6', 1, 'Starting', 'SELECT DATE_ADD(DATE(NOW()), INTERVAL -12 MONTH) AS "Value"', '8fdb0c8bbfb84ead88aed2ee3674b498' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Date', '812d931780b0491bb50eb2dc7087ba7e', 2, 'Ending', 'SELECT DATE(NOW()) AS "Value"', '8fdb0c8bbfb84ead88aed2ee3674b498' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Text', 'c3f6fa77c66841b6a03f964c41d59132', 5, 'Employee', NULL, '8fdb0c8bbfb84ead88aed2ee3674b498' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Text', '9cf2ea4b68c8456b968ddfc77fef8bc9', 6, 'Contact', NULL, '8fdb0c8bbfb84ead88aed2ee3674b498' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Text', 'f3e9e2a616544204bbf96a647e615378', 7, 'Status', NULL, '8fdb0c8bbfb84ead88aed2ee3674b498' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Text', 'c9049c5fa80840f685a3960387745bcf', 8, 'Type', 'SELECT ''incomplete'' AS "Value"', '8fdb0c8bbfb84ead88aed2ee3674b498' );
INSERT INTO "report blocks" ( "GUID",  "Name",  "Parent Block GUID",  "Priority",  "Reports GUID",  "SQL Query" ) VALUES ('33ece97e1f074621871a260226dbce53', 'Row', NULL, 1, '8fdb0c8bbfb84ead88aed2ee3674b498', 'SELECT * FROM  (\r\n\tSELECT \r\n\t\tTE."GUID",\r\n\t\tTE."Started",\r\n        CAST(IFNULL(TIMESTAMPDIFF(MINUTE, TE."Started", TE."Ended"), TIMESTAMPDIFF(MINUTE, TE."Started", NOW())) / 60 AS DECIMAL(64,2)) AS "Hours",\r\n\t\tE."Display Name" AS "Employee",\r\n\t\tTET."Description" AS "Type",\r\n\t\tI."Number" AS "Item",\r\n\t\tC."Display Name" AS "Contact",\r\n\t\tCASE\r\n\t\t\tWHEN TET."Items GUID" IS NOT NULL AND TE."Documents GUID" IS NULL THEN ''Unbilled''\r\n\t\t\tWHEN TET."Items GUID" IS NOT NULL AND TE."Documents GUID" IS NULL THEN ''Invoiced''\r\n\t\t\tELSE ''Not Billable''\r\n\t\tEND AS "Status",\r\n\t\tTE."Description" AS "Description"\r\n\tFROM \r\n\t\t"Time Entries" TE\r\n\t\tJOIN "Contacts" E ON E."GUID" = TE."Employees GUID"\r\n\t\tJOIN "Time Entry Types" TET ON TET."GUID" = TE."Entry Types GUID"\r\n\t\tLEFT JOIN "Items" I ON I."GUID" = TET."Items GUID"\r\n\t\tLEFT JOIN "Contacts" C ON C."GUID" = TE."Contacts GUID"\r\n) \r\nTBL WHERE\r\n    DATE("Started") <= DATE({Ending}) \r\n    AND DATE("Started") >= DATE({Starting})\r\n    AND (\r\n        LENGTH(IFNULL({Employee}, '''')) = 0 OR\r\n        "Employee" LIKE CONCAT(''%'', IFNULL({Employee}, ''''), ''%'')\r\n    )\r\n    AND (\r\n        LENGTH(IFNULL({Type}, '''')) = 0 OR\r\n        "Type" LIKE CONCAT(''%'', IFNULL({Type}, ''''), ''%'')\r\n    )\r\n    AND (\r\n        LENGTH(IFNULL({Status}, '''')) = 0 OR\r\n        "Status" LIKE CONCAT(''%'', IFNULL({Status}, ''''), ''%'')\r\n    )\r\n    AND (\r\n        LENGTH(IFNULL({Contact}, '''')) = 0 OR\r\n        "Contact" LIKE CONCAT(''%'', IFNULL({Contact}, ''''), ''%'')\r\n    )\r\nORDER BY "Started" DESC, "Employee"' );

DELETE FROM "reports" WHERE "GUID"='84c704545c9140fb96ee5b9610fe632b';
DELETE FROM "securables" WHERE "GUID"='84c704545c9140fb96ee5b9610fe632b';
DELETE FROM "report filters" WHERE "GUID"='78b4db98c7a04501855c5a3c391b6f0b';
DELETE FROM "report filters" WHERE "GUID"='97738c738d8544ea8acdea0ea26150a4';
DELETE FROM "report filters" WHERE "GUID"='c8ebcb2c3053462cb1bc83801e060ec5';
DELETE FROM "report filters" WHERE "GUID"='3ccb9351c9c44d08bb5c542b4cc50861';
DELETE FROM "report filters" WHERE "GUID"='5d7e578b0c114cbdb8eaf5592d4b9b76';
DELETE FROM "report blocks" WHERE "GUID"='806b7499b0ca4676abb85e1bee2e6b08';
INSERT INTO "reports" ( "Auto Load",  "Display Name",  "GUID",  "HTML Template",  "Query",  "Title" ) VALUES (0, 'List - Accounting - Bank Deposits', '84c704545c9140fb96ee5b9610fe632b', '<table class="list">\r\n    <tr>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=Deposit" class="add">Add</a></td>\r\n        <td>Account</td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td class="right">Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=Deposit&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D84c704545c9140fb96ee5b9610fe632b" class="delete">Delete</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=Deposit&GUID={GUID}">{Account}</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=Deposit&GUID={GUID}">{Date}</a></td>\r\n        <td><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=Deposit&GUID={GUID}">{Number}</a></td>\r\n        <td class="right"><a href="https://svr2012-web:8181/incAccounting/incAccounting?App=Deposit&GUID={GUID}">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>', NULL, 'Bank Deposit List' );
INSERT INTO "securables" ( "Display Name",  "GUID" ) VALUES ('Report - List - Accounting - Bank Deposits', '84c704545c9140fb96ee5b9610fe632b');
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Date', '78b4db98c7a04501855c5a3c391b6f0b', 1, 'Starting', 'SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS "Value"', '84c704545c9140fb96ee5b9610fe632b' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Date', '97738c738d8544ea8acdea0ea26150a4', 2, 'Ending', 'SELECT DATE(NOW()) AS "Value"', '84c704545c9140fb96ee5b9610fe632b' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Text', 'c8ebcb2c3053462cb1bc83801e060ec5', 3, 'Account', NULL, '84c704545c9140fb96ee5b9610fe632b' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Text', '3ccb9351c9c44d08bb5c542b4cc50861', 4, 'Number', NULL, '84c704545c9140fb96ee5b9610fe632b' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Text', '5d7e578b0c114cbdb8eaf5592d4b9b76', 7, 'Total', NULL, '84c704545c9140fb96ee5b9610fe632b' );
INSERT INTO "report blocks" ( "GUID",  "Name",  "Parent Block GUID",  "Priority",  "Reports GUID",  "SQL Query" ) VALUES ('806b7499b0ca4676abb85e1bee2e6b08', 'Row', NULL, 0, '84c704545c9140fb96ee5b9610fe632b', 'SELECT *\r\nFROM\r\n\t(\r\n\t\tSELECT\r\n\t\t\t"Bank Deposits"."GUID" As "GUID",\r\n\t\t\t"Accounts"."Name" AS "Account",\r\n\t\t\tCONVERT("Bank Deposits"."Date", CHAR(1024)) AS "Date",\r\n\t\t\t"Bank Deposits"."Number",\r\n\t\t\tCONVERT("Bank Deposits"."Total", DECIMAL(64,2)) AS "Total"\r\n\t\tFROM \r\n\t\t\t"Bank Deposits"\r\n\t\t\tJOIN "Accounts" ON "Bank Deposits"."Accounts GUID" = "Accounts"."GUID"\r\n\t\tWHERE\r\n\t\t    DATE("Bank Deposits"."Date") <= DATE({Ending})\r\n            AND DATE("Bank Deposits"."Date") >= DATE({Starting})\r\n\r\n\t) TBL\r\nWHERE\r\n\t"Account" LIKE CONCAT(''%'', IFNULL({Account}, ''''), ''%'')\r\n\tAND "Number" LIKE CONCAT(''%'', IFNULL({Number}, ''''), ''%'')\r\n\tAND "Total" LIKE CONCAT(''%'', IFNULL({Total}, ''''), ''%'')\r\nORDER BY\r\n    "Date" DESC,\r\n    "Number"\r\n' );

DELETE FROM "reports" WHERE "GUID"='01f2dff753214482b4a1ac02f6640914';
DELETE FROM "securables" WHERE "GUID"='01f2dff753214482b4a1ac02f6640914';
DELETE FROM "report filters" WHERE "GUID"='4b8d4b4ca161463ab30f274daccb77d3';
DELETE FROM "report filters" WHERE "GUID"='24e41fdad6704b8bafc8b8cdc7e068ff';
DELETE FROM "report blocks" WHERE "GUID"='4f90103182ee4813a905a84c2187452f';
DELETE FROM "report blocks" WHERE "GUID"='99e65dccb16741738eed92c4046c2679';
INSERT INTO "reports" ( "Auto Load",  "Display Name",  "GUID",  "HTML Template",  "Query",  "Title" ) VALUES (0, 'Report - Accounting - Sales Tax - Accural', '01f2dff753214482b4a1ac02f6640914', '<style>\r\n    table a { text-decoration: none; }\r\n    div.ported { width: 100em; }\r\n</style>\r\n\r\n<table width="100%">\r\n    <tr>\r\n        <td class="black">Contact</td>\r\n        <td class="black">Date</td>\r\n        <td class="black">Type</td>\r\n        <td class="black">Number</td>\r\n        <td class="black right">Taxable</td>\r\n        <td class="black right">Nontaxable</td>\r\n        <td class="black right">Taxes</td>\r\n        <td class="black right">Total</td>\r\n        <td class="black right">Rate</td>\r\n        <td class="black">Name</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href="{Edit URL}&GUID={GUID}">{Contact}</a></td>\r\n        <td><a href="{Edit URL}&GUID={GUID}">{Date}</a></td>\r\n        <td><a href="{Edit URL}&GUID={GUID}">{Type}</a></td>\r\n        <td><a href="{Edit URL}&GUID={GUID}">{Number}</a></td>\r\n        <td class="right"><a href="{Edit URL}&GUID={GUID}">{Taxable}</a></td>\r\n        <td class="right"><a href="{Edit URL}&GUID={GUID}">{Nontaxable}</a></td>\r\n        <td class="right"><a href="{Edit URL}&GUID={GUID}">{Taxes}</a></td>\r\n        <td class="right"><a href="{Edit URL}&GUID={GUID}">{Total}</a></td>\r\n        <td class="right"><a href="{Edit URL}&GUID={GUID}">{Tax Rate}</a></td>\r\n        <td class=""><a href="{Edit URL}&GUID={GUID}">{Tax Name}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- BEGIN Total -->\r\n    <tr>\r\n        <td colspan="4" class="right black">&nbsp;</td>\r\n        <td class="right black">{Taxable}</td>\r\n        <td class="right black">{Nontaxable}</td>\r\n        <td class="right black">{Taxes}</td>\r\n        <td class="right black">{Total}</td>\r\n        <td colspan="2">&nbsp;</td>\r\n    </tr>\r\n    <!-- END Total -->    \r\n</table>', NULL, 'Sales Tax Accural' );
INSERT INTO "securables" ( "Display Name",  "GUID" ) VALUES ('Report - Report - Accounting - Sales Tax - Accural', '01f2dff753214482b4a1ac02f6640914');
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Date', '4b8d4b4ca161463ab30f274daccb77d3', 1, 'Starting', 'SELECT DATE(CONCAT(YEAR(NOW()), ''-'', MONTH(NOW()), ''-01'')) AS "Value"', '01f2dff753214482b4a1ac02f6640914' );
INSERT INTO "report filters" ( "Data Type",  "GUID",  "Priority",  "Prompt",  "Query",  "Reports GUID" ) VALUES ('Date', '24e41fdad6704b8bafc8b8cdc7e068ff', 2, 'Ending', 'SELECT DATE_ADD(DATE(CONCAT(YEAR(NOW()), ''-'', MONTH(DATE_ADD(NOW(), INTERVAL 1 MONTH)) , ''-01'')), INTERVAL -1 DAY) AS "Value"', '01f2dff753214482b4a1ac02f6640914' );
INSERT INTO "report blocks" ( "GUID",  "Name",  "Parent Block GUID",  "Priority",  "Reports GUID",  "SQL Query" ) VALUES ('4f90103182ee4813a905a84c2187452f', 'Row', NULL, 1, '01f2dff753214482b4a1ac02f6640914', 'SELECT\r\n    D."GUID" AS "GUID",\r\n    D."Date" AS "Date",\r\n\tD."Contacts Display Name" AS "Contact",\r\n\tTT."Name" AS "Type",\r\n    LEFT(D."Reference Number", 24) AS "Number",\r\n\tCASE\r\n\t\tWHEN TT."GUID" = ''81e2917ac5c34d1cb6f9d168cd0439db'' THEN 1\r\n        ELSE -1\r\n\tEND * CAST( CASE\r\n\t\tWHEN D."Tax Rate" <> 0 THEN D."Taxable Subtotal"\r\n        ELSE 0\r\n\tEND AS DECIMAL(64,2) ) AS "Taxable",\r\n\tCASE\r\n\t\tWHEN TT."GUID" = ''81e2917ac5c34d1cb6f9d168cd0439db'' THEN 1\r\n        ELSE -1\r\n\tEND * CAST( CASE\r\n\t\tWHEN D."Tax Rate" <> 0 THEN D."Nontaxable Subtotal"\r\n        ELSE D."Nontaxable Subtotal" + D."Taxable Subtotal"\r\n\tEND AS DECIMAL(64,2) ) AS "Nontaxable",\r\n    CASE\r\n\t\tWHEN TT."GUID" = ''81e2917ac5c34d1cb6f9d168cd0439db'' THEN 1\r\n        ELSE -1\r\n\tEND * CAST( D."Taxes" AS DECIMAL(64,2) ) AS "Taxes",\r\n    CASE\r\n\t\tWHEN TT."GUID" = ''81e2917ac5c34d1cb6f9d168cd0439db'' THEN 1\r\n        ELSE -1\r\n\tEND * CAST( D."Total" AS DECIMAL(64,2) ) AS "Total",\r\n    CAST( (D."Tax Rate" * 100) AS DECIMAL(64,2) ) AS "Tax Rate",\r\n    ST."Display Name" AS "Tax Name",\r\n    TT."Edit URL"\r\nFROM \r\n    "Documents" D\r\n    JOIN "Transaction Types" TT ON TT."GUID" = D."Document Types GUID"\r\n    JOIN "Sales Taxes" ST ON ST."GUID" = D."Sales Taxes GUID"\r\nWHERE\r\n\tTT."GUID" IN (''86af180c412f40c5a660678e3895694b'', ''81e2917ac5c34d1cb6f9d168cd0439db'')\r\n\tAND DATE(D."Date") >= DATE({Starting})\r\n    AND DATE(D."Date") <= DATE({Ending})\r\n    AND D."GUID" NOT IN ( SELECT DISTINCT "Payments"."Prepayment Documents GUID" FROM "Payments" )' );
INSERT INTO "report blocks" ( "GUID",  "Name",  "Parent Block GUID",  "Priority",  "Reports GUID",  "SQL Query" ) VALUES ('99e65dccb16741738eed92c4046c2679', 'Total', NULL, 2, '01f2dff753214482b4a1ac02f6640914', 'SELECT \r\n    CAST( SUM(TBL."Taxable") AS DECIMAL(64,2) ) AS "Taxable",\r\n    CAST( SUM(TBL."Nontaxable") AS DECIMAL(64,2) ) AS "Nontaxable",\r\n    CAST( SUM(TBL."Taxes") AS DECIMAL(64,2) ) AS "Taxes",\r\n    CAST( SUM(TBL."Total") AS DECIMAL(64,2) ) AS "Total"\r\nFROM \r\n    (\r\n        SELECT\r\n            D."Date" AS "Date",\r\n        \tD."Contacts Display Name" AS "Contact",\r\n        \tTT."Name" AS "Type",\r\n            D."Reference Number" AS "Number",\r\n        \tCASE\r\n        \t\tWHEN TT."GUID" = ''81e2917ac5c34d1cb6f9d168cd0439db'' THEN 1\r\n                ELSE -1\r\n        \tEND * CAST( CASE\r\n        \t\tWHEN D."Tax Rate" <> 0 THEN D."Taxable Subtotal"\r\n                ELSE 0\r\n        \tEND AS DECIMAL(64,2) ) AS "Taxable",\r\n        \tCASE\r\n        \t\tWHEN TT."GUID" = ''81e2917ac5c34d1cb6f9d168cd0439db'' THEN 1\r\n                ELSE -1\r\n        \tEND * CAST( CASE\r\n        \t\tWHEN D."Tax Rate" <> 0 THEN D."Nontaxable Subtotal"\r\n                ELSE D."Nontaxable Subtotal" + D."Taxable Subtotal"\r\n        \tEND AS DECIMAL(64,2) ) AS "Nontaxable",\r\n            CASE\r\n        \t\tWHEN TT."GUID" = ''81e2917ac5c34d1cb6f9d168cd0439db'' THEN 1\r\n                ELSE -1\r\n        \tEND * CAST( D."Taxes" AS DECIMAL(64,2) ) AS "Taxes",\r\n            CASE\r\n        \t\tWHEN TT."GUID" = ''81e2917ac5c34d1cb6f9d168cd0439db'' THEN 1\r\n                ELSE -1\r\n        \tEND * CAST( D."Total" AS DECIMAL(64,2) ) AS "Total",\r\n            CAST( (D."Tax Rate" * 100) AS DECIMAL(64,2) ) AS "Tax Rate",\r\n            ST."Display Name" AS "Tax Name"\r\n        FROM \r\n            "Documents" D\r\n            JOIN "Transaction Types" TT ON TT."GUID" = D."Document Types GUID"\r\n            JOIN "Sales Taxes" ST ON ST."GUID" = D."Sales Taxes GUID"\r\n        WHERE\r\n        \tTT."GUID" IN (''86af180c412f40c5a660678e3895694b'', ''81e2917ac5c34d1cb6f9d168cd0439db'')\r\n        \tAND DATE(D."Date") >= DATE({Starting})\r\n            AND DATE(D."Date") <= DATE({Ending})\r\n            AND D."GUID" NOT IN ( SELECT DISTINCT "Payments"."Prepayment Documents GUID" FROM "Payments" )\r\n    ) TBL' );
SET FOREIGN_KEY_CHECKS=1
