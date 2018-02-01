SELECT * FROM 
	(
		SELECT 
			*
		FROM "Users"
	) "TBL"
WHERE
	"Display Name" LIKE CONCAT('%', IFNULL(@Name, ''), '%')