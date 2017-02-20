SELECT *
FROM
	(
		SELECT 
			CONVERT("GUID", CHAR(1024)) AS "GUID",
			CONVERT("Display Name", CHAR(1024)) AS "Display Name",
			CONVERT("Abbreviation", CHAR(1024)) AS "Abbreviation",
			CASE
				WHEN "Is Allowed" = 1 THEN 'True'
				WHEN "Is Allowed" = 0 THEN 'False'
			END AS "Is Allowed"
		FROM "Unit Measures"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Display Name",
	"Abbreviation"
