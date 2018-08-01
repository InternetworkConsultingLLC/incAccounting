SELECT
	"Payroll Fields"."GUID" AS "Value",
	"Payroll Fields"."Name" AS "Display"
FROM
	"Payroll Fields"
WHERE
	"Payroll Field Types GUID" = {Type GUID}
ORDER BY
	"Payroll Fields"."Name"