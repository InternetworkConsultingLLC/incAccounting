SELECT *
FROM
	(
		SELECT
			"Payroll Fields"."GUID",
			"Payroll Fields"."Name",
			"Payroll Field Types"."Name" AS "Payroll Field Types GUID"
		FROM
			"Payroll Fields"
			JOIN "Payroll Field Types" 
				ON "Payroll Fields"."Payroll Field Types GUID" = "Payroll Field Types"."GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Name"

