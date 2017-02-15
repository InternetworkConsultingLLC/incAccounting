SELECT *
FROM "Payroll Checks" 
WHERE 
	"Employees GUID" = {Employees GUID}
	AND "Is Template" = 1
ORDER BY "Date" DESC
LIMIT 1
