SELECT *
FROM
	(
		SELECT
			"Transactions"."GUID",
			CONVERT("Date", CHAR(1024)) AS "Date",
			"Reference Number",
			"Name"
		FROM "Transactions"
			JOIN "Transaction Types" ON "Transactions"."Transaction Types GUID" = "Transaction Types"."GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Date",
	"Reference Number",
	"Name"
