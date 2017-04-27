-- SET @IsSalesRelated = 1;
-- SET @PaymentsGUID = 'f105fc5c5b4040a3b25559738b5213e2';
-- SET @ContactsGUID = '5882bcba33eb40e1bc2db51553c0e4ad';
SELECT * FROM
	(
		SELECT 
			SQ2."Is Credit Memo",
			SQ2."Payments GUID",
			SQ2."Documents GUID",
			SQ2."Name",
			SQ2."Date",
			SQ2."Reference Number",
			SQ2."Customer Reference",
			CAST(SQ2."Total" - SUM(SQ2."Paid") AS DECIMAL(64, 2)) AS "Balance",
			CAST(SUM(SQ2."Amount") AS DECIMAL(64, 2)) AS "Amount"
		FROM
			(
				SELECT
					SQ1."Is Credit Memo",
					@PaymentsGUID AS "Payments GUID",
					SQ1."Documents GUID",
					SQ1."Name",
					SQ1."Date",
					SQ1."Reference Number",
					SQ1."Customer Reference",
					SQ1."Total",
					CASE
						WHEN SQ1."Payments GUID" <> @PaymentsGUID THEN SQ1."Payments Amount"
						ELSE 0
					END AS "Paid",
					CASE
						WHEN SQ1."Payments GUID" = @PaymentsGUID THEN SQ1."Payments Amount"
						ELSE 0
					END AS "Amount"
				FROM (
					SELECT
						D."GUID" AS "Documents GUID",
						DTTT."Name" "Name",
						DT."Is Sales Related",
						DT."Is Credit Memo",
						D."Date",
						D."Reference Number",
						D."Customer Reference",
						CASE
							WHEN (DT."Is Sales Related" + DT."Is Credit Memo" + @IsSalesRelated) & 0x1 = 0 THEN 1
							ELSE -1
						END * D."Total" AS "Total",
						PTTT."Name" AS "Payments Type",
						PT."Is Sales Related" AS "Payments Sales",
						P."Their Number" AS "Payments Number",
						P."GUID" AS "Payments GUID",
						CASE
							WHEN @IsSalesRelated = PT."Is Sales Related" THEN 1
							ELSE -1
						END * CASE
							WHEN DT."Is Credit Memo" = 1 THEN -1
							ELSE 1
						END * PA."Amount" AS "Payments Amount",
						DT."Is Sales Related" + DT."Is Credit Memo" + @IsSalesRelated AS "Parity"
					FROM
						"Documents" D
						JOIN "Contacts" C ON C."GUID" = D."Contacts GUID"
						JOIN "Document Types" DT ON D."Document Types GUID" = DT."GUID"
						JOIN "Transaction Types" DTTT ON D."Document Types GUID" = DTTT."GUID"
						LEFT JOIN "Payment Applications" PA ON PA."Documents GUID" = D."GUID"
						LEFT JOIN "Payments" P ON P."GUID" = PA."Payments GUID"
						LEFT JOIN "Payment Types" PT ON PT."GUID" = P."Payment Types GUID"
						LEFT JOIN "Transaction Types" PTTT ON PT."GUID" = PTTT."GUID"
					WHERE
						D."Contacts GUID" = @ContactsGUID
				) SQ1
			) SQ2
		GROUP BY
			SQ2."Is Credit Memo",
			SQ2."Payments GUID",
			SQ2."Documents GUID",
			SQ2."Name",
			SQ2."Date",
			SQ2."Reference Number",
			SQ2."Customer Reference",
			SQ2."Total"
	) SQ3
WHERE
	SQ3."Balance" <> 0
ORDER BY
	SQ3."Date" ASC,
    SQ3."Name",
    SQ3."Balance"