DELETE FROM "Logs" WHERE DATEDIFF(NOW(), "Logs"."Occured") > 60
\q