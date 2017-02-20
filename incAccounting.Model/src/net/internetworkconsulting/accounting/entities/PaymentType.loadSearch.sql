SELECT *
FROM
	(
	SELECT
		"Payment Types"."GUID" AS "GUID",
		"Transaction Types"."Name" AS "Name",
		CONCAT("Accounts"."Number", ' ', "Nested Name") AS "Account",
		CASE
			WHEN "Transaction Types"."Is Allowed" = 1 THEN 'Allowed'
			WHEN "Transaction Types"."Is Allowed" = 0 THEN 'Not Allowed'
		END AS "Is Allowed"
	FROM 
		"Payment Types"
		JOIN "Transaction Types" ON "Transaction Types"."GUID" = "Payment Types"."GUID"
		LEFT JOIN "Accounts" ON "Accounts"."GUID" = "Payment Types"."Accounts GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Name"
