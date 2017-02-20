SELECT *
FROM
	(
		SELECT 
			"GUID",
			CONVERT("Date", CHAR(1024)) AS "Date",
			"Number",
			"Pay To The Order Of",
			CONVERT( CONVERT("Paycheck Amount", DECIMAL(64, 2)), CHAR(1024)) AS "Paycheck Amount",
			CASE
				WHEN "Is Template" = 1 THEN 'Template'
				ELSE 'Check'
			END AS "Is Template"
		FROM 
			"Payroll Checks"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Date" DESC,
	"Number" DESC