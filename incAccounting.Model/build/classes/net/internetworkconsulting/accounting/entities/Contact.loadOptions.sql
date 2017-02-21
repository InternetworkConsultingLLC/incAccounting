SELECT 
	"Child"."GUID" AS "Value",
	CASE
		WHEN "Parent"."Display Name" IS NULL THEN "Child"."Display Name"
		WHEN NOT "Parent"."Display Name" IS NULL THEN  CONCAT("Parent"."Display Name", ' - ', "Child"."Display Name")
	END AS "Display"
FROM
	"Contacts" "Child"
	LEFT JOIN "Contacts" "Parent" ON "Child"."Parent Contacts GUID" = "Parent"."GUID"
WHERE
	"Child"."Parent Contacts GUID" IS NULL
-- 	OR "Child"."Parent Contacts GUID" IN (
-- 		SELECT "GUID"
-- 		FROM "Contacts" "Filter"
-- 		WHERE "Filter"."Parent Contacts GUID" IS NULL
-- 	)
ORDER BY
	CASE
		WHEN "Parent"."Display Name" IS NULL THEN "Child"."Display Name"
		WHEN NOT "Parent"."Display Name" IS NULL THEN  CONCAT("Parent"."Display Name", ' - ', "Child"."Display Name")
	END