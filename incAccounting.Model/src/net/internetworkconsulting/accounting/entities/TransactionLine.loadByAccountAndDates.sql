SELECT
	TL."GUID",
	TL."Transactions GUID",
	TL."Sort Order",
	TL."Description",
	TL."Debit",
	TL."Jobs GUID",
	TL."Departments GUID",
	TL."Accounts GUID",
	TL."Reconciliations GUID"
FROM
	"Transaction Lines" TL
	JOIN "Transactions" T ON TL."Transactions GUID" = T."GUID"
WHERE
	TL."Accounts GUID" = {Account}
	AND DATE(T."Date") >= DATE({Starting})
	AND DATE(T."Date") <= DATE({Ending})
ORDER BY
	T."Date",
	TL."Debit",
	T."Reference Number"
	
	