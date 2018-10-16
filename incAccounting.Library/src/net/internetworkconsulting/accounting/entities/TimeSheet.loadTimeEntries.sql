-- SET @EmployeesGUID = 'cbe9d2b2422545ee99e2444732269c26';
-- SET @Starting = DATE('2017-05-10');
-- SET @TimeSheetsGUID = '40db77fba14d439f9b8d94cfad99bebd';
-- SET @Ending = DATE('2017-05-16');
SELECT 
	* 
FROM 
	"Time Entries" TE
WHERE
	TE."Employees GUID" = @EmployeesGUID
    AND (TE."Time Sheets GUID" = @TimeSheetsGUID OR TE."Time Sheets GUID" IS NULL)
	AND TE."Ended" IS NOT NULL
--	AND TE."Entry Types GUID" IN ( SELECT "GUID" FROM "Time Entry Types" WHERE "Is Paid" = 1 )
    AND DATE(TE."Started") >= DATE(@Starting)
    AND DATE(TE."Ended") <= DATE(@Ending)
ORDER BY
	TE."Started",
    TE."Ended"
