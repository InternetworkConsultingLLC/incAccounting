SELECT 
	"Transaction Lines"."GUID",
	"Transaction Lines"."Transactions GUID",
	"Transaction Lines"."Sort Order",
	"Transaction Lines"."Description",
	"Transaction Lines"."Debit",
	"Transaction Lines"."Jobs GUID",
	"Transaction Lines"."Departments GUID",
	"Transaction Lines"."Accounts GUID",
	"Transaction Lines"."Reconciliations GUID"
FROM 
	"Transaction Lines"
	JOIN "Transactions" ON "Transactions"."GUID" = "Transactions GUID"
WHERE 
	("Reconciliations GUID"={RECONCILIATIONS GUID} OR "Reconciliations GUID" IS NULL)
	AND "Date" <= {DATE}
    AND "Accounts GUID" = {ACCOUNTS GUID}
ORDER BY 
	"Date",
	"Debit" DESC