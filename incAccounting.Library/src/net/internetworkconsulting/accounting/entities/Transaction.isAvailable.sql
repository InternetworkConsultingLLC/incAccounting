SELECT * 
FROM 
	"transactions" T
    JOIN "transaction lines" TL ON T."GUID" = TL."Transactions GUID"
WHERE
	T."Reference Number" = @Reference
    AND TL."Accounts Guid" = @Account