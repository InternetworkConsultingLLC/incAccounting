SELECT
-- 	CONCAT("Jobs"."Number", ' - ', "Jobs"."Name") AS "Display",
	"Jobs"."Number" AS "Display",
	"GUID" AS "Value"
FROM "Jobs"
ORDER BY
	CONCAT("Jobs"."Number", ' - ', "Jobs"."Name")

