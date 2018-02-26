-- SET @DisplayName = NULL;
-- SET @IsAllowed = 1;

SELECT * FROM 
	(
		SELECT 
			*
		FROM "Groups"
	) "TBL"
WHERE
	"Display Name" LIKE CONCAT('%', IFNULL(@DisplayName, ''), '%')
	AND "Is Allowed" = IFNULL(@IsAllowed, "Is Allowed")
ORDER BY
	"Display Name"