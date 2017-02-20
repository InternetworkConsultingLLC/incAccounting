SELECT *
FROM
	(
		SELECT
			"Reconciliations"."GUID",
			CONVERT("Date", CHAR(1024)) AS "Date",
			CONCAT("Accounts"."Number", ' ', "Accounts"."Name") AS "Account",
			CONVERT(CONVERT("Statement Ending Balance", DECIMAL(64,2)), CHAR(1024)) AS "Balance"
		FROM "Reconciliations"
			JOIN "Accounts" ON "Accounts"."GUID" = "Reconciliations"."Accounts GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Date",
	"Account"
