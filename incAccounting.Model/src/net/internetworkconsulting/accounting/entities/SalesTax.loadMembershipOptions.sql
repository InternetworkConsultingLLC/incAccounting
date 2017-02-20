SELECT
	*,
	( 
		SELECT COUNT(*) FROM "Sales Tax Memberships" 
		WHERE 
			"Child Sales Taxes GUID" = "Sales Taxes"."GUID"
			AND "Parent Sales Taxes GUID" = {Group GUID}
	) AS "Is Included"
FROM "Sales Taxes" 
WHERE "Is Group" = 0
ORDER BY "Display Name"