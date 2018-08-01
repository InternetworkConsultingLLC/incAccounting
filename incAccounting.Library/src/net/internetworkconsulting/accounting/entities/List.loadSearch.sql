-- SET @DisplayName = NULL;
-- SET @IsAllowed = 1;

SELECT * FROM 
	(
		SELECT 
			*
		FROM "Lists"
	) "TBL"
WHERE
	"Display Name" LIKE CONCAT('%', IFNULL(@DisplayName, ''), '%')
ORDER BY
	"Display Name"