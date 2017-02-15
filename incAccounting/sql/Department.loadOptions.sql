SELECT
--	CONCAT("Departments"."Number", ' - ', "Departments"."Name") AS "Display",
	"Departments"."Number" AS "Display",
	"GUID" AS "Value"
FROM "Departments"
ORDER BY
	CONCAT("Departments"."Number", ' - ', "Departments"."Name")

