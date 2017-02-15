SELECT *
FROM
	(
		SELECT
			"GUID",
			"Display Name",
			CONVERT(CONVERT("Tax Rate", DECIMAL(64,4)), CHAR(1024)) AS "Tax Rate",
			CASE
				WHEN "Is Group" = 1 THEN 'Group'
				WHEN "Is Group" = 0 THEN 'Authority'
			END AS "Is Group"
		FROM
			"Sales Taxes"
		ORDER BY
			"Display Name"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Display Name"
