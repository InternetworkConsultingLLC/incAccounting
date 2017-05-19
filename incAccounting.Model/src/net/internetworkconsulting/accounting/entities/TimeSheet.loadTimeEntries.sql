-- SET @EmployeesGUID = '';
-- SET @TimeSheetsGUID = '';
SELECT 
	* 
FROM 
	"Time Entries" TE
WHERE
	TE."Employees GUID" = @EmployeesGUID
    AND (TE."Time Sheets GUID" = @TimeSheetsGUID OR TE."Time Sheets GUID" IS NULL)
	AND TE."Ended" IS NOT NULL
    AND TE."Entry Types GUID" IN ( SELECT "GUID" FROM "Time Entry Types" WHERE "Is Paid" = 1 )
ORDER BY
	TE."Started",
    TE."Ended"
