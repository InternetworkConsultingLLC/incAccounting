SELECT IFNULL(SUM(TL."Debit") , 0) AS "Value"
FROM 
	"Transaction Lines" TL
    JOIN "Transactions" T ON TL."Transactions GUID" = T."GUID"
WHERE "Accounts GUID" = {GUID} AND DATE({As Of Date}) > T."Date"