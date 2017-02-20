SELECT *
FROM
	(
		SELECT
			"Payments"."GUID",
			"Transaction Types"."Name",
			CONVERT("Payments"."Date", CHAR(1024)) AS "Date",
			"Payments"."Our Number",
			"Payments"."Contacts Display Name",
			"Payments"."Their Number",
			CONVERT(CONVERT("Payments"."Total", DECIMAL(64,2)), CHAR(1024)) AS "Total"
		FROM 
			"Payments"
			JOIN "Transaction Types" ON "Payments"."Payment Types GUID" = "Transaction Types"."GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Date", "Our Number"
--	"Name", "Our Number", "Their Number"
