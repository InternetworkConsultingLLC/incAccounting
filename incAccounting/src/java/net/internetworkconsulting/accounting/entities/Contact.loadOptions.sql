SELECT 
	"GUID" AS "Value",
	"Display Name" AS "Display"
FROM
	"Contacts"
WHERE
	( "Parent Contacts GUID" IS NULL OR "Parent Contacts GUID" = 'fabca02484aa46eaa24c939c64779a2d' )
ORDER BY
	"Display Name"