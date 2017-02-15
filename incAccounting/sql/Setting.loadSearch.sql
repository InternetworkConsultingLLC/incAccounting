SELECT *
FROM
	(
		SELECT 
			CONVERT(Settings."GUID", CHAR(1024)) AS "GUID",
			CONVERT("Key", CHAR(1024)) AS "Key",
			CONVERT(Users."SQL User", CHAR(1024)) AS "SQL User",
			CONVERT("Type", CHAR(1024)) AS "Type",
			CONVERT("Value", CHAR(1024)) AS "Value"
		FROM Settings
			LEFT JOIN Users ON Settings."Users GUID" = Users."GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Key",
	"SQL User"
