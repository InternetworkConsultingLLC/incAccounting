SELECT 
	"Payroll Field Values"."GUID",
	"Payroll Field Values"."Payroll Checks GUID",
	"Payroll Field Values"."Payroll Fields GUID",
	"Payroll Field Values"."Rate",
	"Payroll Field Values"."Quantity",
	"Payroll Field Values"."Total"
FROM
	"Payroll Field Values"
	JOIN "Payroll Fields" ON "Payroll Field Values"."Payroll Fields GUID" = "Payroll Fields"."GUID"
WHERE
	"Payroll Field Values"."Payroll Fields GUID" IN (
		SELECT "GUID" 
		FROM "Payroll Fields" 
		WHERE "Payroll Field Types GUID" = {Payroll Field Types GUID}
	)
	AND "Payroll Checks GUID" = {Payroll Checks GUID}
ORDER BY
	"Payroll Fields"."Name"
