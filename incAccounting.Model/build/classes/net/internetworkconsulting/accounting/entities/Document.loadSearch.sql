SELECT *
FROM
	(
		SELECT
			CONVERT("Documents"."GUID", CHAR(1024)) AS "GUID",
			CONVERT("Name", CHAR(1024)) AS "Document Type",
			CONVERT("Date", CHAR(1024)) AS "Date",
			CONVERT("Due Date", CHAR(1024)) AS "Due Date",
			CONVERT("Reference Number", CHAR(1024)) AS "Reference Number",
			CONVERT("Contacts Display Name", CHAR(1024)) AS "Contacts Display Name",
			CONVERT(CONVERT("Total", DECIMAL(64,2)), CHAR(1024)) AS "Total"
		FROM
			"Documents"
			JOIN "Transaction Types" ON "Documents"."Document Types GUID" = "Transaction Types"."GUID"
	) TBL
WHERE
	%WHERE%
ORDER BY
	CONVERT("Reference Number", CHAR(1024))
