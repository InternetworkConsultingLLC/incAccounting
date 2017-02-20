SELECT *
FROM
	(
		SELECT 
			CONVERT("GUID", CHAR(1024)) AS "GUID",
			CONVERT("Display Name", CHAR(1024)) AS "Display Name"
		FROM "Reports"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Display Name"