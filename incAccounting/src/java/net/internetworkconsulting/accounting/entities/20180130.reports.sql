USE "%DATABASE%";

SET FOREIGN_KEY_CHECKS=0;

DELETE FROM "reports" WHERE "GUID"='8fdb0c8bbfb84ead88aed2ee3674b498';
INSERT INTO "reports" ( "Auto Load",  "Display Name",  "GUID",  "HTML Template",  "Query",  "Title" ) VALUES (0, 'List - Accounting - Time Entries', '8fdb0c8bbfb84ead88aed2ee3674b498', '<table class="list">\r\n    <tr>\r\n        <td><a href="~/incAccounting?App=TimeEntry" class="add">Add</a></td>\r\n        <td>Started</td>\r\n        <td>Hours</td>\r\n        <td>Employee</td>\r\n        <td>Type</td>\r\n        <td>Item</td>\r\n        <td>Contact</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href="~/incAccounting?App=TimeEntry&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D8fdb0c8bbfb84ead88aed2ee3674b498" class="delete">Delete</a></td>\r\n        <td><a href="~/incAccounting?App=TimeEntry&GUID={GUID}">{Started}</a></td>\r\n        <td><a href="~/incAccounting?App=TimeEntry&GUID={GUID}">{Hours}</a></td>\r\n        <td><a href="~/incAccounting?App=TimeEntry&GUID={GUID}">{Employee}</a></td>\r\n        <td><a href="~/incAccounting?App=TimeEntry&GUID={GUID}">{Type}</a></td>\r\n        <td><a href="~/incAccounting?App=TimeEntry&GUID={GUID}">{Item}</a></td>\r\n        <td><a href="~/incAccounting?App=TimeEntry&GUID={GUID}">{Contact}</a></td>\r\n        <td><a href="~/incAccounting?App=TimeEntry&GUID={GUID}">{Status}</a></td>\r\n    </tr>\r\n    <tr>\r\n        <td></td>\r\n        <td colspan="7"><a href="~/incAccounting?App=TimeEntry&GUID={GUID}">{Description}</a></td>\r\n    </tr>\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- END Row -->\r\n</table>', NULL, 'Time Entries' );
DELETE FROM "reports" WHERE "GUID"='84c704545c9140fb96ee5b9610fe632b';
INSERT INTO "reports" ( "Auto Load",  "Display Name",  "GUID",  "HTML Template",  "Query",  "Title" ) VALUES (0, 'List - Accounting - Bank Deposits', '84c704545c9140fb96ee5b9610fe632b', '<table class="list">\r\n    <tr>\r\n        <td><a href="~/incAccounting?App=Deposit" class="add">Add</a></td>\r\n        <td>Account</td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td class="right">Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href="~/incAccounting?App=Deposit&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D84c704545c9140fb96ee5b9610fe632b" class="delete">Delete</a></td>\r\n        <td><a href="~/incAccounting?App=Deposit&GUID={GUID}">{Account}</a></td>\r\n        <td><a href="~/incAccounting?App=Deposit&GUID={GUID}">{Date}</a></td>\r\n        <td><a href="~/incAccounting?App=Deposit&GUID={GUID}">{Number}</a></td>\r\n        <td class="right"><a href="~/incAccounting?App=Deposit&GUID={GUID}">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>', NULL, 'Bank Deposit List' );

SET FOREIGN_KEY_CHECKS=1;
