SELECT
	"Display Name" AS "Display",
	"GUID" AS "Value"
FROM
	"Sales Taxes"
WHERE NOT "Is Group" = 0
ORDER BY
	"Display Name"