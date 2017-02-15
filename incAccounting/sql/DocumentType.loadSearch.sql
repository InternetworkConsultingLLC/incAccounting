SELECT *
FROM
	(
	SELECT
		"Document Types"."GUID" AS "GUID",
		"Transaction Types"."Name" AS "Name",
		CASE
			WHEN "Accounts GUID" IS NULL THEN 'Not Posting'
			ELSE CONCAT("Accounts"."Number", ' ', "Nested Name")
		END AS "Account",
		CASE
			WHEN "Transaction Types"."Is Allowed" = 1 THEN 'Allowed'
			WHEN "Transaction Types"."Is Allowed" = 0 THEN 'Not Allowed'
		END AS "Is Allowed"
	FROM 
		"Document Types"
		JOIN "Transaction Types" ON "Transaction Types"."GUID" = "Document Types"."GUID"
		LEFT JOIN "Accounts" ON "Accounts"."GUID" = "Document Types"."Accounts GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Name"
