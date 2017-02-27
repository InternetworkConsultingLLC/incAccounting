SELECT 
    DL."GUID" AS "Document Lines GUID",
	DL."Items GUID",
	DL."Quantity" - IFNULL(INDL."Decrease Quantity", 0) AS "Available",
	DL."Extension" / DL."Quantity" AS "Unit Cost"
FROM 
	(
		SELECT
			"Document Lines"."GUID",
            "Documents"."Reference Number",
            "Documents"."Date",
			"Document Lines"."Items GUID",
			"Document Lines"."Quantity",
			"Document Lines"."Extension"
		FROM 
			"Document Lines"
			JOIN "Documents" ON "Documents"."GUID" = "Documents GUID"
			JOIN "Document Types" ON "Documents"."Document Types GUID" = "Document Types"."GUID"
		WHERE
			"Document Types"."Is Posting" = 1
			AND "Document Types"."Is Sales Related" = "Document Types"."Is Credit Memo"
            AND NOT ISNULL("Documents"."Posted Transactions GUID")
	) DL 
	LEFT JOIN (
		SELECT "Increasing Document Lines GUID", SUM("Decrease Quantity") AS "Decrease Quantity" FROM "Item Postings" GROUP BY "Increasing Document Lines GUID"
	) INDL ON INDL."Increasing Document Lines GUID" = DL."GUID"
WHERE
	DL."Quantity" - IFNULL(INDL."Decrease Quantity", 0) <> 0
	AND "Items GUID" = {Items GUID}
ORDER BY
	DL."Date",
    DL."Reference Number",
	DL."Items GUID",
	DL."Quantity" - IFNULL(INDL."Decrease Quantity", 0)