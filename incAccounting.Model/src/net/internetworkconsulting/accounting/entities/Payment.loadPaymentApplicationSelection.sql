SELECT
	"Document Types"."Is Credit Memo",
	{Payments GUID} AS "Payments GUID",
	"Documents"."GUID" AS "Documents GUID",
	"Transaction Types"."Name",
	"Documents"."Date",
	"Documents"."Reference Number",
	"Documents"."Customer Reference",
	CASE
		WHEN "Document Types"."Is Credit Memo" = 1 THEN  ("Documents"."Total" - COALESCE("OtherPayments"."Amount", 0)) * - 1 
		WHEN "Document Types"."Is Credit Memo" = 0 THEN  ("Documents"."Total" - COALESCE("OtherPayments"."Amount", 0)) * 1 
	END AS "Balance",
	CASE
		WHEN "Document Types"."Is Credit Memo" = 1 THEN  COALESCE("MyPayments"."Amount", 0) * - 1 
		WHEN "Document Types"."Is Credit Memo" = 0 THEN  COALESCE("MyPayments"."Amount", 0) * 1 
	END AS "Amount"
FROM 
	"Documents"
	JOIN "Transaction Types" ON "Documents"."Document Types GUID" = "Transaction Types"."GUID"
	JOIN "Document Types" ON "Documents"."Document Types GUID" = "Document Types"."GUID"
	LEFT JOIN (
		SELECT "Documents GUID", SUM("Amount") AS "Amount"
		FROM "Payment Applications"
		WHERE "Payments GUID" <> {Payments GUID}
		GROUP BY "Documents GUID"
	) AS "OtherPayments" ON "Documents"."GUID" = "OtherPayments"."Documents GUID"
	LEFT JOIN (
		SELECT "Documents GUID", SUM("Amount") AS "Amount"
		FROM "Payment Applications"
		WHERE "Payments GUID" = {Payments GUID}
		GROUP BY "Documents GUID"
	) AS "MyPayments" ON "Documents"."GUID" = "MyPayments"."Documents GUID"
WHERE
	"Documents"."Document Types GUID" IN (
		SELECT "Document Types GUID" FROM "Payment Types Document Types" WHERE "Payment Types GUID" = {Payment Types GUID}
	)
	AND "Documents"."Contacts GUID" = {Contacts GUID}
	AND "Documents"."Total" - COALESCE("OtherPayments"."Amount", 0) <> 0
