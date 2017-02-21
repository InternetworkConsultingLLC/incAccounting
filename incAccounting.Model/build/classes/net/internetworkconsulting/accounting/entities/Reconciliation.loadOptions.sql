SELECT
	"Reconciliations"."GUID" AS "Value",
	CONCAT(CONVERT("Date", CHAR(1024)), ' ', "Accounts"."Number", ' ', "Accounts"."Name") AS "Display"
FROM "Reconciliations"
	JOIN "Accounts" ON "Accounts"."GUID" = "Reconciliations"."Accounts GUID"
ORDER BY CONCAT(CONVERT("Date", CHAR(1024)), ' ', "Accounts"."Number", ' ', "Accounts"."Name")