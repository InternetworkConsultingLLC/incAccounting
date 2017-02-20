SELECT *
FROM
	(
		SELECT 
			CONVERT("Child"."GUID", CHAR(1024)) AS "GUID",
			CONVERT("Parent"."Display Name", CHAR(1024)) AS "Parent Display Name",
			CONVERT("Child"."Display Name", CHAR(1024)) AS "Display Name",
			CONVERT("Child"."Contact Since", CHAR(1024)) AS "Contact Since",
--			CONVERT("Mailing"."Postal Code", CHAR(1024)) AS "Mailing Postal Code",
-- 			CONVERT("Shipping"."Postal Code", CHAR(1024)) AS "shipping Postal Code",
			CONVERT("Child"."Website", CHAR(1024)) AS "Website",
			CONVERT("Child"."Email Address", CHAR(1024)) AS "Email Address",
			CONVERT("Child"."Office Phone", CHAR(1024)) AS "Office Phone",
			CONVERT("Child"."Mobile Phone", CHAR(1024)) AS "Mobile Phone",
-- 			CONVERT("Child"."Home Phone", CHAR(1024)) AS "Home Phone",
			CONVERT("Child"."Fax Number", CHAR(1024)) AS "Fax Number",
			CASE
				WHEN "Child"."Is Allowed" = 1 THEN 'True'
				WHEN "Child"."Is Allowed" = 0 THEN 'False'
			END AS "Is Allowed"
		FROM
			"Contacts" "Child"
			LEFT JOIN "Contacts" "Parent" ON "Child"."Parent Contacts GUID" = "Parent"."GUID"
		WHERE
			"Child"."Parent Contacts GUID" IS NULL
-- 			OR "Child"."Parent Contacts GUID" IN (
-- 				SELECT "GUID"
-- 				FROM "Contacts" "Filter"
-- 				WHERE "Filter"."Parent Contacts GUID" IS NULL
-- 			)
	) TBL
WHERE
	%WHERE%
ORDER BY
	"Parent Display Name",
	"Display Name"
