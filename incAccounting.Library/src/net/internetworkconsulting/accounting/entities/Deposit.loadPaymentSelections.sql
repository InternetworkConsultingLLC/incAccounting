SELECT 
	* 
FROM "Payments"
WHERE
	(
		"Bank Deposits GUID" IS NULL OR "Bank Deposits GUID" = {Deposits GUID}
	)
	AND "Payment Types GUID" = '2c12d6167d654604be3f533c38f1ad1e'
ORDER BY
        "Date",
	"Contacts Display Name",
	"Their Number",
	"Our Number"
	