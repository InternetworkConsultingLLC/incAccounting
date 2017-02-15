SELECT *
FROM
	(
		SELECT 
			CONVERT("GUID", CHAR(1024)) AS "GUID",
			CONVERT("Number", CHAR(1024)) AS "Number",
			CASE
				WHEN "Is Allowed" = 1 THEN 'True'
				WHEN "Is Allowed" = 0 THEN 'False'
			END AS "Is Allowed"
		FROM "Items"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Number"
