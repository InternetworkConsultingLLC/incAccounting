SELECT *
FROM
	(
		SELECT
			"GUID",
			"Display Name",
			CASE
				WHEN "Is Allowed" = 1 THEN 'Allowed'
				WHEN "Is Allowed" = 0 THEN 'Not Allowed'
			END AS "Is Allowed"
		FROM 
			"Contact Types"
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Display Name"
