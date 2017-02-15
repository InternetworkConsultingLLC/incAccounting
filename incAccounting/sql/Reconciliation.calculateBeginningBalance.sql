SELECT 
	IFNULL(SUM(IFNULL("debit", 0)), 0) AS "Balance"
FROM 
	"reconciliations"
    JOIN "transaction lines" ON "transaction lines"."reconciliations guid" = "reconciliations"."guid"
WHERE
	"reconciliations"."date" <= {Date}
    AND "transaction lines"."accounts guid" = {Accounts GUID}