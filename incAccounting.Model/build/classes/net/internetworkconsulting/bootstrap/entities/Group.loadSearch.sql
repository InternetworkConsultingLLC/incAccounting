SELECT *
FROM
	(
		SELECT 
			CONVERT("GUID", CHAR(1024)) AS "GUID",
			CASE
				WHEN "Is Allowed" = 1 THEN 'True'
				WHEN "Is Allowed" = 0 THEN 'False'
			END AS "Is Allowed",
			CONVERT("Display Name", CHAR(1024)) AS "Display Name",
			CONVERT("Email Address", CHAR(1024)) AS "Email Address"
		FROM Groups
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Display Name",
	"Email Address"
