SELECT
	"Document Types"."GUID" AS "Value",
	"Transaction Types"."Name" AS "Display"
FROM 
	"Document Types"
	JOIN "Transaction Types" ON "Transaction Types"."GUID" = "Document Types"."GUID"
WHERE
	"Document Types"."Accounts GUID" IS NOT NULL 
ORDER BY
	"Name"
