SELECT
	"Document Types"."GUID" AS "Value",
	"Transaction Types"."Name" AS "Display"
FROM 
	"Document Types"
	JOIN "Transaction Types" ON "Transaction Types"."GUID" = "Document Types"."GUID"
ORDER BY
	"Name"
