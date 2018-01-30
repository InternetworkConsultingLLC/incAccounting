SELECT 
	IFNULL(SUM(IFNULL("debit", 0)), 0) AS "Balance"
FROM 
	"Reconciliations"
    JOIN "Transaction Lines" ON "Transaction Lines"."Reconciliations GUID" = "Reconciliations"."GUID"
WHERE
	DATE("Reconciliations"."Date") <= DATE({Date})
    AND "Transaction Lines"."Accounts GUID" = {Accounts GUID}
    AND "Reconciliations GUID" IS NOT NULL
    AND "Reconciliations GUID" <> {Reconciliations GUID}