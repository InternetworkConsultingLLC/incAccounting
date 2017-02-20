SELECT *
FROM
	(
		SELECT 
			CONVERT("GUID", CHAR(1024)) AS "GUID",
			CONVERT("SQL User", CHAR(1024)) AS "SQL User",
			CONVERT("Display Name", CHAR(1024)) AS "Display Name",
			CONVERT("Email Address", CHAR(1024)) AS "Email Address",
			CASE
				WHEN "Is Allowed" = 0 THEN 'False'
				WHEN "Is Allowed" = 1 THEN 'True'
			END AS "Is Allowed",
			CASE
				WHEN "Add Computer" = 0 THEN 'False'
				WHEN "Add Computer" = 1 THEN 'True'
			END AS "Add Computer"
		FROM Users
	) TBL
WHERE
	%WHERE%
ORDER BY
	"SQL User",
	"Display Name",
	"Email Address"