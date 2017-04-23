SELECT 
	* 
FROM 
	"Payroll Checks" PC
WHERE 
	PC."Posted Transactions GUID" IS {Nullable}
    AND PC."Is Template" = 0
ORDER BY 
	PC."Date" DESC,
    PC."Number"