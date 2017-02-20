SELECT *
FROM
	(
		SELECT 
			CONVERT("GUID", CHAR(1024)) AS "GUID",
			CONVERT("Number", CHAR(1024)) AS "Number",
			CONVERT("Name", CHAR(1024)) AS "Name",
			CASE
				WHEN "Is Allowed" = 1 THEN 'True'
				WHEN "Is Allowed" = 0 THEN 'False'
			END AS "Is Allowed"
		FROM "Departments"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Number",
	"Name"

