SELECT
-- 	CONCAT("Items"."Number", ' - ', "Items"."Name") AS "Display",
	"Items"."Number" AS "Display",
	"GUID" AS "Value"
FROM "Items"
ORDER BY
	"Items"."Number"

