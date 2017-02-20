SELECT *
FROM
	(
		SELECT 
			CONVERT("GUID", CHAR(1024)) AS "GUID",
			CONVERT("Display Name", CHAR(1024)) AS "Display Name",
			CONVERT("Contact Since", CHAR(1024)) AS "Contact Since",
			CONVERT("Email Address", CHAR(1024)) AS "Email Address",
			CONVERT("Office Phone", CHAR(1024)) AS "Office Phone",
			CONVERT("Mobile Phone", CHAR(1024)) AS "Mobile Phone",
 			CONVERT("Home Phone", CHAR(1024)) AS "Home Phone",
			CONVERT("Fax Number", CHAR(1024)) AS "Fax Number",
			CASE
				WHEN "Is Allowed" = 1 THEN 'True'
				WHEN "Is Allowed" = 0 THEN 'False'
			END AS "Is Allowed"
		FROM
			"Contacts"
		WHERE
			"GUID" IN ( SELECT  "Employees"."GUID" FROM "Employees" )
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Display Name"
