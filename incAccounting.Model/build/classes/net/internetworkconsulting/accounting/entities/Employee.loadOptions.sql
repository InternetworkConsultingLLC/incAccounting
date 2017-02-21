SELECT 
	"GUID" AS "Value",
	"Display Name" AS "Display"
FROM
	"Contacts"
WHERE
	"GUID" IN ( SELECT  "Employees"."GUID" FROM  "Employees" )
ORDER BY
	"Display Name"