SELECT *
FROM
	(
		SELECT
			"Bank Deposits"."GUID" As "GUID",
			"Accounts"."Name",
			CONVERT("Bank Deposits"."Date", CHAR(1024)) AS "Date",
			"Bank Deposits"."Number",
			CONVERT(CONVERT("Bank Deposits"."Total", DECIMAL(64,2)), CHAR(1024)) AS "Total"
		FROM 
			"Bank Deposits"
			JOIN "Accounts" ON "Bank Deposits"."Accounts GUID" = "Accounts"."GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Date" DESC, "Number"
