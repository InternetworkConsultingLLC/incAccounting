SELECT
	"Display Name" AS "Display",
	"GUID" AS "Value"
FROM
	"Contacts"
WHERE
	"Parent Contacts GUID" = {GUID}
	OR "GUID" = {GUID}