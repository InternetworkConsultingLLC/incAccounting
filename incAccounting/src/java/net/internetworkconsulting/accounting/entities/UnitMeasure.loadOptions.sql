SELECT
-- 	CONCAT("Display Name", ' (', "Abbreviation", ')') AS "Display",
	"Abbreviation" AS "Display",
	"GUID" AS "Value"
FROM "Unit Measures"
ORDER BY "Display Name", "Abbreviation"