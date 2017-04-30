"c:\Program Files\MySQL\MySQL Server 5.7\bin\mysqldump.exe" --user=root --host=localhost --password="PASSWORD" "DATABASE" > DATABASE-%DATE:~7,2%.sql
"c:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe" -u root -h localhost --password="PASSWORD" -B < DATABASE-maint.sql
"C:\Program Files\7-Zip\7z.exe" a DATABASE-%DATE:~7,2%.7z DATABASE-%DATE:~7,2%.sql
del DATABASE-%DATE:~7,2%.sql

REM The following copies the script to a shared location for development
REM net use z: /delete /yes
REM net use z: "\\SERVER\SHARE\FOLDERS"
REM copy DATABASE-%DATE:~7,2%.7z z:
REM del DATABASE-%DATE:~7,2%.7z