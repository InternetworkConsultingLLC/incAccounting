SELECT *
FROM
	(
		SELECT 
			CONVERT("Logs"."GUID", CHAR(1024)) AS "GUID",
			CONVERT("Occured", CHAR(1024)) AS "Occured",
			CONVERT("Log", CHAR(1024)) AS "Log",
			CONVERT("Users"."SQL User", CHAR(1024)) AS "SQL USer",
			CONVERT("Computers"."Description", CHAR(1024)) AS "Computer",
			CONVERT("Code GUID", CHAR(1024)) AS "Code GUID",
			CONVERT("Details", CHAR(64)) AS "Details"
		FROM Logs
			JOIN Computers ON Computers.GUID = Logs."Computers GUID"
			JOIN Users ON Users.GUID = Logs."Users GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Occured",
	"Log",
	"SQL User"
