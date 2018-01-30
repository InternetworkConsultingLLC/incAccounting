-- SET @Name = 'Shawn';
-- SET @Status = 'Allowed';

SELECT * FROM 
	(
		SELECT 
			"GUID",
			"Display Name" AS "Name",
			CASE
				WHEN "Is Allowed" = 0 THEN 'Disabled'
                WHEN "Is Allowed" <> 0 THEN 'Allowed'
			END AS "Status"
		FROM "Users"
	) "TBL"
WHERE
	"Name" LIKE CONCAT('%', IFNULL(@Name, ''), '%')
	AND "Status" LIKE CONCAT('%', IFNULL(@Status, ''), '%')