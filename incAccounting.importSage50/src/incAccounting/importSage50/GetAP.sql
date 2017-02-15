SELECT
	TransactionDate,
	JrnlHdr.PostOrder,
	Description,
	CustVendId,
	MainAmount,
	Reference,
	IFNULL(PYMTS.Amount, 0) * -1 AS "Payments",
	IFNULL(CMEMOS.Amount, 0) AS "Credits",
	CONVERT(CONVERT((MainAmount + IFNULL(PYMTS.Amount, 0) - IFNULL(CMEMOS.Amount, 0)) * 100, SQL_BIGINT), SQL_DECIMAL) / 100 AS "Balance"
FROM JrnlHdr
	LEFT JOIN (
		SELECT
			SUM(JrnlRow.Amount) AS "Amount",
			JrnlRow.LinkToAnotherTrx AS "PostOrder"
		FROM 
			JrnlRow
			JOIN JrnlHdr ON  JrnlHdr.PostOrder = JrnlRow.PostOrder
		WHERE 
			Journal = 2
			AND LinkToAnotherTrx <> 0
			AND CONVERT(JrnlHdr.TransactionDate, SQL_DATE) < CONVERT('2017-01-01', SQL_DATE)
		GROUP BY
			JrnlRow.LinkToAnotherTrx			
	) PYMTS ON PYMTS.PostOrder = JrnlHdr.PostOrder
	LEFT JOIN (
		SELECT
			SUM(JrnlRow.Amount) AS "Amount",
			JrnlRow.LinkToAnotherTrx AS "PostOrder"
		FROM 
			JrnlRow
			JOIN JrnlHdr ON  JrnlHdr.PostOrder = JrnlRow.PostOrder
		WHERE 
			Journal = 4 AND JournalEx = 12
			AND LinkToAnotherTrx <> 0
			AND CONVERT(JrnlHdr.TransactionDate, SQL_DATE) < CONVERT('2017-01-01', SQL_DATE)
		GROUP BY
			JrnlRow.LinkToAnotherTrx			
	) CMEMOS ON CMEMOS.PostOrder = JrnlHdr.PostOrder
WHERE
	JrnlHdr.JrnlKey_Journal = 4 -- purchhase journal
	AND JournalEx <> 12
	AND CONVERT(CONVERT((MainAmount + IFNULL(PYMTS.Amount, 0) - IFNULL(CMEMOS.Amount, 0)) * 100, SQL_BIGINT), SQL_DECIMAL) / 100 <> 0