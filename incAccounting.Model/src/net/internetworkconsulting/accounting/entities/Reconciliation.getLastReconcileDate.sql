-- SET @Account = 'f296a5db0df0405a9ba7b4c1414f7dbe';
-- SET @Date = CAST('2017-06-15' AS DATETIME);

SELECT MAX("Date") AS "Value"
FROM "Reconciliations" R
WHERE
	R."Accounts GUID" = @Account
    AND R."Date" < DATE(@Date);