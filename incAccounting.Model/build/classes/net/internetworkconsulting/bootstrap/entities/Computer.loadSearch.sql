SELECT *
FROM
	(
		SELECT 
			CONVERT("GUID", CHAR(1024)) AS "GUID",
			CONVERT("Description", CHAR(1024)) AS "Description",
			CONVERT("MAC Address", CHAR(1024)) AS "MAC Address",
			CASE
				WHEN "Is Allowed" = 1 THEN 'True'
				WHEN "Is Allowed" = 0 THEN 'False'
			END AS "Is Allowed"
		FROM "Computers"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Description",
	"MAC Address"
