SELECT *
FROM
	(
		SELECT 
			CONVERT(Securables."GUID", CHAR(1024)) AS "GUID",
			CONVERT(Securables."Display Name", CHAR(1024)) AS "Display Name"
		FROM Securables
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Display Name"