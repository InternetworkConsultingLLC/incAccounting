SELECT
	"Payment Types"."GUID" AS "Value",
	"Transaction Types"."Name" AS "Display"
FROM 
	"Payment Types"
	JOIN "Transaction Types" ON "Transaction Types"."GUID" = "Payment Types"."GUID"
ORDER BY
	"Name"
