SELECT
	CONCAT("Accounts"."Number", ' - ', "Accounts"."Nested Name") AS "Display",
--	"Accounts"."Number" AS "Display",
	"GUID" AS "Value"
FROM "Accounts"
ORDER BY
	CONCAT("Accounts"."Number", ' - ', "Accounts"."Nested Name")

