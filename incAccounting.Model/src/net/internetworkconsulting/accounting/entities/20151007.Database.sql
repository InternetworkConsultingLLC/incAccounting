CREATE DATABASE "%DATABASE%";
USE "%DATABASE%";

CREATE TABLE "Users"(
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Is Allowed" BIT NOT NULL,
	"Display Name" VARCHAR(64) NOT NULL UNIQUE,
	"SQL User" VARCHAR(64) NOT NULL UNIQUE,
	"Email Address" VARCHAR(64) NOT NULL UNIQUE,
	"Password Date" DATE NOT NULL,
	"Add Computer" BIT NOT NULL
);

CREATE TABLE "Groups" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Is Allowed" BIT NOT NULL,
	"Display Name" VARCHAR(64) NOT NULL UNIQUE,
	"Email Address" VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE "Computers" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Description" VARCHAR(64) NOT NULL UNIQUE,
	"MAC Address" VARCHAR(64) NOT NULL UNIQUE,
	"Is Allowed" BIT NOT NULL
);

CREATE TABLE "Memberships" (
	"Users GUID" CHAR(32) NOT NULL,
	"Groups GUID" CHAR(32) NOT NULL
);

ALTER TABLE "Memberships" ADD CONSTRAINT "Unique Membership" UNIQUE ("Users GUID", "Groups GUID");
ALTER TABLE "Memberships" ADD CONSTRAINT "Group<Memberships" FOREIGN KEY ("Groups GUID") REFERENCES "Groups" ("GUID");
ALTER TABLE "Memberships" ADD CONSTRAINT "User<Memberships" FOREIGN KEY("Users GUID") REFERENCES "Users" ("GUID");

CREATE TABLE "Securables" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Display Name" VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE "Permissions" (
	"Groups GUID" CHAR(32) NOT NULL,
	"Securables GUID" CHAR(32) NOT NULL,
	"Can Create" BIT NOT NULL,
	"Can Read" BIT NOT NULL,
	"Can Update" BIT NOT NULL,
	"Can Delete" BIT NOT NULL
);

ALTER TABLE "Permissions" ADD CONSTRAINT "Unique Permissions" UNIQUE ("Securables GUID", "Groups GUID");
ALTER TABLE "Permissions" ADD CONSTRAINT "Group<Permissions" FOREIGN KEY("Groups GUID") REFERENCES "Groups" ("GUID");
ALTER TABLE "Permissions" ADD CONSTRAINT "Securable<Permissions" FOREIGN KEY("Securables GUID") REFERENCES "Securables" ("GUID");

CREATE TABLE "Settings" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Users GUID" CHAR(32) NULL,
	"Key" VARCHAR(64) NOT NULL,
	"Type" VARCHAR(64) NOT NULL,
	"Option Query" TEXT,
	"Value" TEXT NOT NULL
);

ALTER TABLE "Settings" ADD CONSTRAINT "Unique Settings User" UNIQUE ("Key", "Users GUID");
ALTER TABLE "Settings" ADD CONSTRAINT "User<Settings" FOREIGN KEY("Users GUID") REFERENCES "Users" ("GUID");

CREATE TABLE "Logs" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Occured" DATETIME NOT NULL,
	"Users GUID" CHAR(32) NOT NULL,
	"Computers GUID" CHAR(32) NOT NULL,
	"Code GUID" CHAR(32) NOT NULL, -- each logger should have a distinct GUID in the code, this is for cross referencing logs with code
	"Log" VARCHAR(32) NOT NULL,
	"Details" TEXT NOT NULL
);

ALTER TABLE "Logs" ADD CONSTRAINT "User<Logs" FOREIGN KEY("Users GUID") REFERENCES "Users" ("GUID");
ALTER TABLE "Logs" ADD CONSTRAINT "Computer<Logs" FOREIGN KEY("Computers GUID") REFERENCES "Computers" ("GUID");

CREATE TABLE "Reports" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Display Name" VARCHAR(128) UNIQUE NOT NULL,
	"HTML Template" VARCHAR(4095) NOT NULL
);
ALTER TABLE "Reports" ADD CONSTRAINT "Securable<Report" FOREIGN KEY("GUID") REFERENCES "Securables" ("GUID");

CREATE TABLE "Report Filters" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Reports GUID" CHAR(32) NOT NULL,

	"Prompt" VARCHAR(128) NOT NULL,
	"Data Type" VARCHAR(8) NOT NULL,
	"Query" VARCHAR(4096)
);
ALTER TABLE "Report Filters" ADD CONSTRAINT "Unique Filter Prompt" UNIQUE ("Reports GUID", "Prompt");
ALTER TABLE "Report Filters" ADD CONSTRAINT "Filters>Report" FOREIGN KEY ("Reports GUID") REFERENCES "Reports" ("GUID");

CREATE TABLE "Report Blocks" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Reports GUID" CHAR(32),
	"Parent Block GUID" CHAR(32),
	"Priority" INT NOT NULL,

	"Name" VARCHAR(128) NOT NULL,
	"SQL Query" VARCHAR(4096) NOT NULL	
);
ALTER TABLE "Report Blocks" ADD CONSTRAINT "Unique Block Name" UNIQUE ("Reports GUID", "Name");
ALTER TABLE "Report Blocks" ADD CONSTRAINT "Unique Block Order" UNIQUE ("Reports GUID", "Priority");
ALTER TABLE "Report Blocks" ADD CONSTRAINT "Blocks>Report" FOREIGN KEY ("Reports GUID") REFERENCES "Reports" ("GUID");
ALTER TABLE "Report Blocks" ADD CONSTRAINT "Children>Parent" FOREIGN KEY ("Parent Block GUID") REFERENCES "Report Blocks" ("GUID");

CREATE TABLE "Account Types" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Name" VARCHAR(128) NOT NULL UNIQUE,
	"Gets Closed" BIT NOT NULL
);

CREATE TABLE "Accounts" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Account Types GUID" CHAR(32) NOT NULL,
	"Parent Accounts GUID" CHAR(32),

	"Segment" VARCHAR(8) NOT NULL,
	"Number" VARCHAR(32) NOT NULL UNIQUE,
	"Name" VARCHAR(64) NOT NULL,
	"Nested Name" VARCHAR(1024) NOT NULL UNIQUE,
	"Is Allowed" BIT NOT NULL
);
ALTER TABLE "Accounts" ADD CONSTRAINT "Accounts>Account Type" FOREIGN KEY ("Account Types GUID") REFERENCES "Account Types" ("GUID");
ALTER TABLE "Accounts" ADD CONSTRAINT "Children>Parent Account" FOREIGN KEY ("Parent Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Accounts" ADD CONSTRAINT "Accounts Unique Segment" UNIQUE ("Parent Accounts GUID", "Segment");
ALTER TABLE "Accounts" ADD CONSTRAINT "Accounts Unique Name" UNIQUE ("Parent Accounts GUID", "Name");

CREATE TABLE "Reconciliations" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Accounts GUID" CHAR(32) NOT NULL,
	"Date" DATE NOT NULL,
	"Statement Ending Balance" DECIMAL(64,16) NOT NULL,
	"Off By" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Reconciliations" ADD CONSTRAINT "Account<Reconciliations" FOREIGN KEY ("Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Reconciliations" ADD CONSTRAINT "Reconciliations Unique Date" UNIQUE ("Accounts GUID", "Date");

CREATE TABLE "Jobs" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Parent Jobs GUID" CHAR(32),
	
	"Segment" VARCHAR(16) NOT NULL,
	"Number" VARCHAR(64) NOT NULL UNIQUE,
	"Name" VARCHAR(64) NOT NULL,
	"Nested Name" VARCHAR(1024) NOT NULL,
	"Is Allowed" BIT NOT NULL
);
ALTER TABLE "Jobs" ADD CONSTRAINT "Jobs Unique Segment" UNIQUE ("Parent Jobs GUID", "Segment");
ALTER TABLE "Jobs" ADD CONSTRAINT "Jobs Unique Name" UNIQUE ("Parent Jobs GUID", "Name");
ALTER TABLE "Jobs" ADD CONSTRAINT "Children>Parent Job" FOREIGN KEY ("Parent Jobs GUID") REFERENCES "Jobs" ("GUID");

CREATE TABLE "Departments" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Parent Departments GUID" CHAR(32),

	"Segment" VARCHAR(16) NOT NULL,
	"Number" VARCHAR(64) NOT NULL UNIQUE,
	"Name" VARCHAR(64) NOT NULL,
	"Nested Name" VARCHAR(1024) NOT NULL,
	"Is Allowed" BIT NOT NULL
);
ALTER TABLE "Departments" ADD CONSTRAINT "Departments Unique Segment" UNIQUE ("Parent Departments GUID", "Segment");
ALTER TABLE "Departments" ADD CONSTRAINT "Departments Unique Name" UNIQUE ("Parent Departments GUID", "Name");
ALTER TABLE "Departments" ADD CONSTRAINT "Children>Parent Department" FOREIGN KEY ("Parent Departments GUID") REFERENCES "Departments" ("GUID");

CREATE TABLE "Transaction Types" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Name" VARCHAR(64) NOT NULL UNIQUE,

	"Edit URL" VARCHAR(255) NOT NULL,
	"List URL" VARCHAR(255) NOT NULL,
	"Is Allowed" BIT NOT NULL
);

CREATE TABLE "Transactions" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Date" DATE NOT NULL,
	"Reference Number" VARCHAR(128) NOT NULL,
	"Transaction Types GUID" CHAR(32)
);
ALTER TABLE "Transactions" ADD CONSTRAINT "Transactionds Unique Reference Number" UNIQUE ("Transaction Types GUID", "Reference Number");
ALTER TABLE "Transactions" ADD CONSTRAINT "Transactions>Transaction Type" FOREIGN KEY ("Transaction Types GUID") REFERENCES "Transaction Types" ("GUID");

CREATE TABLE "Transaction Lines" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Transactions GUID" CHAR(32) NOT NULL,
	
	"Sort Order" INT NOT NULL,
	"Description" VARCHAR(255) NOT NULL,
	"Debit" DECIMAL(64,16) NOT NULL,
	"Jobs GUID" CHAR(32) NULL,
	"Departments GUID" CHAR(32) NULL,
	"Accounts GUID" CHAR(32) NOT NULL,
	"Reconciliations GUID" CHAR(32) NULL
);
ALTER TABLE "Transaction Lines" ADD CONSTRAINT "Transaction Lines Unique Sort Order" UNIQUE ("Transactions GUID", "Sort Order");
ALTER TABLE "Transaction Lines" ADD CONSTRAINT "Transaction Lines>Transaction" FOREIGN KEY ("Transactions GUID") REFERENCES "Transactions" ("GUID");
ALTER TABLE "Transaction Lines" ADD CONSTRAINT "Transaction Lines>Account" FOREIGN KEY ("Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Transaction Lines" ADD CONSTRAINT "Transaction Lines>Reconciliation" FOREIGN KEY ("Reconciliations GUID") REFERENCES "Reconciliations" ("GUID");
ALTER TABLE "Transaction Lines" ADD CONSTRAINT "Transaction Lines>Job" FOREIGN KEY ("Jobs GUID") REFERENCES "Jobs" ("GUID");
ALTER TABLE "Transaction Lines" ADD CONSTRAINT "Transaction Lines>Department" FOREIGN KEY ("Departments GUID") REFERENCES "Departments" ("GUID");

CREATE TABLE "Sales Taxes" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Contacts GUID" CHAR(32),
	"Is Group" BIT NOT NULL,
	"Display Name" VARCHAR(128) UNIQUE,
	"Tax Rate" DECIMAL(64,16) NOT NULL
);

CREATE TABLE "Sales Tax Memberships" (
	"Parent Sales Taxes GUID" CHAR(32) NOT NULL,
	"Child Sales Taxes GUID" CHAR(32) NOT NULL
);
ALTER TABLE "Sales Tax Memberships" ADD CONSTRAINT "Tax Memberships Unique Parent Child" UNIQUE ("Parent Sales Taxes GUID", "Child Sales Taxes GUID");
ALTER TABLE "Sales Tax Memberships" ADD CONSTRAINT "Child Tax Memberships>Child Sales Tax" FOREIGN KEY ("Child Sales Taxes GUID") REFERENCES "Sales Taxes" ("GUID");
ALTER TABLE "Sales Tax Memberships" ADD CONSTRAINT "Parent Tax Memberships>Parent Sales Tax" FOREIGN KEY ("Parent Sales Taxes GUID") REFERENCES "Sales Taxes" ("GUID");

CREATE TABLE "Contacts" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Parent Contacts GUID" CHAR(32),
	"Display Name" VARCHAR(128) UNIQUE NOT NULL,
	"Is Allowed" BIT NOT NULL,
	"Contact Since" DATE NOT NULL,

	"Default Shipping Contacts GUID" CHAR(32),

	"Website" VARCHAR(256),
	"Email Address" VARCHAR(256),

	"Office Phone" VARCHAR(32),
	"Mobile Phone" VARCHAR(32),
	"Home Phone" VARCHAR(32),
	"Fax Number" VARCHAR(32),

	"Line 1" VARCHAR(128),
	"Line 2" VARCHAR(128),
	"Country" VARCHAR(3),
	"City" VARCHAR(64),
	"State" VARCHAR(32),
	"Postal Code" VARCHAR(16),
	"Tax Group GUID" CHAR(32) NOT NULL
);
ALTER TABLE "Contacts" ADD CONSTRAINT "Parent Contact<Children Contacts" FOREIGN KEY ("Parent Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Contacts" ADD CONSTRAINT "Default Shipping Contact<Shipping Parent Contact" FOREIGN KEY ("Default Shipping Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Contacts" ADD CONSTRAINT "Contacts Unique Display Name" UNIQUE ("Display Name", "Parent Contacts GUID");
ALTER TABLE "Sales Taxes" ADD CONSTRAINT "Sales Taxes>Contact" FOREIGN KEY ("Contacts GUID") REFERENCES "Contacts" ("GUID");

CREATE TABLE "Contact Notes" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Contacts GUID" CHAR(32) NOT NULL,
	"Users GUID" CHAR(32) NOT NULL,

	"Dated" DATETIME NOT NULL,
	"Subject" VARCHAR(128) NOT NULL,
	"Memorandum" VARCHAR(2048) NOT NULL
);
ALTER TABLE "Contact Notes" ADD CONSTRAINT "Contact Notes>Contact" FOREIGN KEY ("Contacts GUID") REFERENCES "Contacts" ("GUID");

CREATE TABLE "Unit Measures" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Display Name" VARCHAR(128) NOT NULL UNIQUE,
	"Abbreviation" VARCHAR(8) NOT NULL UNIQUE,
	"Is Allowed" BIT NOT NULL
);

CREATE TABLE "Conversions" (
	-- LEFT EQUALS RIGHT
	-- 1 FT = 12 IN
	-- 1 YRD = 3 FT
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Left Unit Measures GUID" CHAR(32) NOT NULL,
	"Left Quantity" DECIMAL(64,16) NOT NULL,
	"Right Unit Measures GUID" CHAR(32) NOT NULL,
	"Right Quantity" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Conversions" ADD CONSTRAINT "Unique Conversion" UNIQUE ("Left Unit Measures GUID", "Right Unit Measures GUID");
ALTER TABLE "Conversions" ADD CONSTRAINT "Left Conversions>Left Unit Measure" FOREIGN KEY ("Left Unit Measures GUID") REFERENCES "Unit Measures" ("GUID");
ALTER TABLE "Conversions" ADD CONSTRAINT "Right Conversions>Right Unit Measure" FOREIGN KEY ("Right Unit Measures GUID") REFERENCES "Unit Measures" ("GUID");

CREATE TABLE "Items" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Parent Items GUID" CHAR(32),
	
	"Segment" VARCHAR(16) NOT NULL,
	"Number" VARCHAR(64) NOT NULL UNIQUE,
	"Is Allowed" BIT NOT NULL,
	
	"Sales Accounts GUID" CHAR(32),
	"Sales Description" VARCHAR(512),
	"Sales Mark Up" DECIMAL(64,16),
	"Sales Unit Price" DECIMAL(64,16),
	"Is Sales Taxed" BIT NOT NULL,

	"Purchase Accounts GUID" CHAR(32),
	"Purchase Description" VARCHAR(512),
	"Purchase Contacts GUID" CHAR(32),
	"Purchase Part Number" VARCHAR(64),
	"Last Unit Cost" DECIMAL(64,16),

	-- the following should only be used, and required, when a inventory 
	-- account is present
	"Inventory Unit Measures GUID" CHAR(32) NOT NULL,
	"Inventory Accounts GUID" CHAR(32),
	"Inventory Description" VARCHAR(512),
	"Is Serialized" BIT NOT NULL
);
ALTER TABLE "Items" ADD CONSTRAINT "Child Items>Parent Item" FOREIGN KEY ("Parent Items GUID") REFERENCES "Items" ("GUID");
ALTER TABLE "Items" ADD CONSTRAINT "Items Unique Segment" UNIQUE ("Segment", "Parent Items GUID");
ALTER TABLE "Items" ADD CONSTRAINT "Sales Items>Sales Acount" FOREIGN KEY ("Sales Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Items" ADD CONSTRAINT "Purchase Items>Purchase Acount" FOREIGN KEY ("Purchase Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Items" ADD CONSTRAINT "Items>Purchase Contact" FOREIGN KEY ("Purchase Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Items" ADD CONSTRAINT "Items>Inventory Unit Measure" FOREIGN KEY ("Inventory Unit Measures GUID") REFERENCES "Unit Measures" ("GUID");
ALTER TABLE "Items" ADD CONSTRAINT "Inventory Items>Inventory Account" FOREIGN KEY ("Inventory Accounts GUID") REFERENCES "Accounts" ("GUID");

CREATE TABLE "Document Types" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Is Sales Related" BIT NOT NULL,
	"Is Credit Memo" BIT NOT NULL,
	"Is Posting" BIT NOT NULL
);
ALTER TABLE "Document Types" ADD CONSTRAINT "Document Type>Transaction Type" FOREIGN KEY ("GUID") REFERENCES "Transaction Types" ("GUID");

CREATE TABLE "Documents" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Document Types GUID" CHAR(32) NOT NULL,
	"Parent Documents GUID" CHAR(32),

	"Reference Number" VARCHAR(128),
	"Date" DATE NOT NULL,
	"Due Date" DATE NOT NULL,

	"Posted Accounts GUID" CHAR(32),
	"Posted Transactions GUID" CHAR(32),

	"Contacts GUID" CHAR(32) NOT NULL,
	"Contacts Display Name" VARCHAR(128) NOT NULL,
	"Customer Reference" VARCHAR(128),
	"Terms" VARCHAR(128),
	"Shipping Method" VARCHAR(128),
	"Shipping Number" VARCHAR(128),

	"Billing Contacts GUID" CHAR(32),
	"Billing Address Display Name" VARCHAR(128) NOT NULL,	
	"Billing Address Line 1" VARCHAR(128),
	"Billing Address Line 2" VARCHAR(128),
	"Billing Address City" VARCHAR(64),
	"Billing Address State" VARCHAR(32),
	"Billing Address Postal Code" VARCHAR(16),
	"Billing Address Country" VARCHAR(3),

	"Shipping Contacts GUID" CHAR(32),
	"Shipping Address Display Name" VARCHAR(128),	
	"Shipping Address Line 1" VARCHAR(128),
	"Shipping Address Line 2" VARCHAR(128),
	"Shipping Address City" VARCHAR(64),
	"Shipping Address State" VARCHAR(32),
	"Shipping Address Postal Code" VARCHAR(16),
	"Shipping Address Country" VARCHAR(3),

	"Sales Taxes GUID" CHAR(32) NOT NULL,

	"Taxable Subtotal" DECIMAL(64,16) NOT NULL,
	"Nontaxable Subtotal" DECIMAL(64,16) NOT NULL,
	"Tax Rate" DECIMAL(64,16) NOT NULL,
	"Subtotal" DECIMAL(64,16) NOT NULL,
	"Taxes" DECIMAL(64,16) NOT NULL,
	"Total" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Documents" ADD CONSTRAINT "Documents Unique Reference Number" UNIQUE ("Document Types GUID", "Reference Number");
ALTER TABLE "Documents" ADD CONSTRAINT "Documents>Document Type" FOREIGN KEY ("Document Types GUID") REFERENCES "Document Types" ("GUID");
ALTER TABLE "Documents" ADD CONSTRAINT "Child Documents>Parent Document" FOREIGN KEY ("Parent Documents GUID") REFERENCES "Documents" ("GUID");
ALTER TABLE "Documents" ADD CONSTRAINT "Documents>Contact" FOREIGN KEY ("Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Documents" ADD CONSTRAINT "Documents>Sales Tax" FOREIGN KEY ("Sales Taxes GUID") REFERENCES "Sales Taxes" ("GUID");
ALTER TABLE "Documents" ADD CONSTRAINT "Billing Documents>Billing Contact" FOREIGN KEY ("Billing Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Documents" ADD CONSTRAINT "Shipping Documents>Shipping Contact" FOREIGN KEY ("Shipping Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Documents" ADD CONSTRAINT "Documents>Account" FOREIGN KEY ("Posted Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Documents" ADD CONSTRAINT "Document>Transaction" FOREIGN KEY ("Posted Transactions GUID") REFERENCES "Transactions" ("GUID");

CREATE TABLE "Document Lines" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Documents GUID" CHAR(32) NOT NULL,
	
	"Sort Order" INT NOT NULL,

	"Quantity" DECIMAL(64,16) NOT NULL,
	"Items GUID" CHAR(32),
	"Unit Measures GUID" CHAR(32),

	"Description" VARCHAR(255),

	"Unit Price" DECIMAL(64,16) NOT NULL,
	"Extension" DECIMAL(64,16) NOT NULL,
	"Is Taxed" BIT,
	"Is Backwards Calculated" BIT NOT NULL,

	"Jobs GUID" CHAR(32) NULL,
	"Departments GUID" CHAR(32) NULL,
	"Accounts GUID" CHAR(32) NOT NULL
);
ALTER TABLE "Document Lines" ADD CONSTRAINT "Document Lines Unique Sort Order" UNIQUE ("Documents GUID", "Sort Order");
ALTER TABLE "Document Lines" ADD CONSTRAINT "Document Lines>Document" FOREIGN KEY ("Documents GUID") REFERENCES "Documents" ("GUID");
ALTER TABLE "Document Lines" ADD CONSTRAINT "Document Lines>Account" FOREIGN KEY ("Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Document Lines" ADD CONSTRAINT "Document Lines>Job" FOREIGN KEY ("Jobs GUID") REFERENCES "Jobs" ("GUID");
ALTER TABLE "Document Lines" ADD CONSTRAINT "Document Lines>Unit Measure" FOREIGN KEY ("Unit Measures GUID") REFERENCES "Unit Measures" ("GUID");
ALTER TABLE "Document Lines" ADD CONSTRAINT "Document Lines>Item" FOREIGN KEY ("Items GUID") REFERENCES "Items" ("GUID");
ALTER TABLE "Document Lines" ADD CONSTRAINT "Document Lines>Department" FOREIGN KEY ("Departments GUID") REFERENCES "Departments" ("GUID");

CREATE TABLE "Contact Types" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Is Allowed" BIT NOT NULL,
	"Display Name" VARCHAR(64) NOT NULL UNIQUE
);

ALTER TABLE "Contacts" ADD COLUMN "Contact Types GUID" CHAR(32) NOT NULL;
ALTER TABLE "Contacts" ADD CONSTRAINT "Contacts>Contact Type" FOREIGN KEY ("Contact Types GUID") REFERENCES "Contact Types" ("GUID");

ALTER TABLE "Jobs" ADD COLUMN "Contacts GUID" CHAR(32);
ALTER TABLE "Jobs" ADD CONSTRAINT "Jobs>Contact" FOREIGN KEY ("Contacts GUID") REFERENCES "Contacts" ("GUID");

ALTER TABLE "Items" DROP COLUMN "Purchase Part Number";

ALTER TABLE "Document Types" ADD COLUMN "Accounts GUID" CHAR(32);
ALTER TABLE "Document Types" ADD CONSTRAINT "Document Types>Account" FOREIGN KEY ("Accounts GUID") REFERENCES "Accounts" ("GUID");

ALTER TABLE "Sales Taxes"  ADD COLUMN "Accounts GUID" CHAR(32);
ALTER TABLE "Sales Taxes" ADD CONSTRAINT "Sales Taxes>Account" FOREIGN KEY ("Accounts GUID") REFERENCES "Accounts" ("GUID");

CREATE TABLE "Payment Types" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Accounts GUID" CHAR(32),
	"Is Sales Related" BIT NOT NULL
);
ALTER TABLE "Payment Types" ADD CONSTRAINT "Payment Type>Transaction Type" FOREIGN KEY ("GUID") REFERENCES "Transaction Types" ("GUID");
ALTER TABLE "Payment Types" ADD CONSTRAINT "Payment Types>Account" FOREIGN KEY ("Accounts GUID") REFERENCES "Accounts" ("GUID");

CREATE TABLE "Payment Types Document Types" (
	"Document Types GUID" CHAR(32) NOT NULL,
	"Payment Types GUID" CHAR(32) NOT NULL
);
ALTER TABLE "Payment Types Document Types" ADD CONSTRAINT "Payment Types Document Types>Document Types" FOREIGN KEY ("Document Types GUID") REFERENCES "Document Types" ("GUID");
ALTER TABLE "Payment Types Document Types" ADD CONSTRAINT "Payment Types Document Types>Payment Type" FOREIGN KEY ("Payment Types GUID") REFERENCES "Payment Types" ("GUID");
ALTER TABLE "Payment Types Document Types" ADD CONSTRAINT "Payment Type Unique Document Type" UNIQUE ("Payment Types GUID", "Document Types GUID");

CREATE TABLE "Payments" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Payment Types GUID" CHAR(32) NOT NULL,
	"Bank Deposits GUID" CHAR(32),
	"Our Number" VARCHAR(128) UNIQUE,
	"Date" DATE NOT NULL,

	"Posted Accounts GUID" CHAR(32) NOT NULL,
	"Posted Transactions GUID" CHAR(32) UNIQUE,
	"Prepayment Documents GUID" CHAR(32),
	"Expense Lines Documents GUID" CHAR(32),

	"Contacts GUID" CHAR(32) NOT NULL,
	"Contacts Display Name" VARCHAR(128) NOT NULL,
	"Their Number" VARCHAR(128),

	"Billing Contacts GUID" CHAR(32),
	"Billing Address Display Name" VARCHAR(128) NOT NULL,	
	"Billing Address Line 1" VARCHAR(128),
	"Billing Address Line 2" VARCHAR(128),
	"Billing Address City" VARCHAR(64),
	"Billing Address State" VARCHAR(32),
	"Billing Address Postal Code" VARCHAR(16),
	"Billing Address Country" VARCHAR(3),

	"Total" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Payments" ADD CONSTRAINT "Payments>Payment Type" FOREIGN KEY ("Payment Types GUID") REFERENCES "Payment Types" ("GUID");
ALTER TABLE "Payments" ADD CONSTRAINT "Payments>Account" FOREIGN KEY ("Posted Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Payments" ADD CONSTRAINT "Contact Payments>Contact" FOREIGN KEY ("Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Payments" ADD CONSTRAINT "Billing Payments>Billing Contact" FOREIGN KEY ("Billing Contacts GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Payments" ADD CONSTRAINT "Payment>Transaction" FOREIGN KEY ("Posted Transactions GUID") REFERENCES "Transactions" ("GUID");
ALTER TABLE "Payments" ADD CONSTRAINT "Payments Unique Their Number" UNIQUE ("Contacts GUID", "Their Number");

CREATE TABLE "Payment Applications" (
	"Payments GUID" CHAR(32) NOT NULL,
	"Documents GUID" CHAR(32) NOT NULL,
	"Amount" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Payment Applications" ADD CONSTRAINT "Payment Applications>Payment" FOREIGN KEY ("Payments GUID") REFERENCES "Payments" ("GUID");
ALTER TABLE "Payment Applications" ADD CONSTRAINT "Payment Applications>Document" FOREIGN KEY ("Documents GUID") REFERENCES "Documents" ("GUID");
ALTER TABLE "Payment Applications" ADD CONSTRAINT "Payments Unique Document" UNIQUE ("Payments GUID", "Documents GUID");

CREATE TABLE "Bank Deposits" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Accounts GUID" CHAR(32) NOT NULL,
	"Date" DATE NOT NULL,
	"Number" VARCHAR(64) NOT NULL  UNIQUE,
	"Items" INT NOT NULL,
	"Total" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Payments" ADD CONSTRAINT "Payments>Bank Deposits" FOREIGN KEY ("Bank Deposits GUID") REFERENCES "Bank Deposits" ("GUID");

ALTER TABLE "Bank Deposits" ADD COLUMN "Posted Transactions GUID" CHAR(32);
ALTER TABLE "Bank Deposits" ADD CONSTRAINT "Bank Deposit>Transaction" FOREIGN KEY ("Posted Transactions GUID") REFERENCES "Transactions" ("GUID");

CREATE TABLE "Employees" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"First Name" VARCHAR(32) NOT NULL,
	"Last Name" VARCHAR(32) NOT NULL,
	"Suffix" VARCHAR(8),
	"Tax ID" VARCHAR(16),

	"Date of Birth" DATE,
	"Is Male" BIT,
	"Ethnicity" VARCHAR(32),

	"Job Title" VARCHAR(64),
	"Supervisor Contacts GUID" CHAR(32) NOT NULL,

	"Date Hirred" DATE,
	"Date Terminated" DATE,
	"Date Verified" DATE,

	"Insurance" BIT NOT NULL,
	"Vacation" BIT NOT NULL
);
ALTER TABLE "Employees" ADD CONSTRAINT "Employee>Contact" FOREIGN KEY ("GUID") REFERENCES "Contacts" ("GUID");
ALTER TABLE "Employees" ADD CONSTRAINT "Subordinates>Supervisor" FOREIGN KEY ("Supervisor Contacts GUID") REFERENCES "Contacts" ("GUID");

CREATE TABLE "Payroll Field Types" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Name" VARCHAR(32) NOT NULL UNIQUE,
	"Debit Required" BIT NOT NULL,
	"Credit Required" BIT NOT NULL
);

INSERT INTO "Payroll Field Types" ("GUID", "Name", "Debit Required", "Credit Required") VALUES ('2f542318ae174eaf8bb95f02ed8f6df5','Company Paid',1,1);
INSERT INTO "Payroll Field Types" ("GUID", "Name", "Debit Required", "Credit Required") VALUES ('af32731792b64a6081a6a1f73d9afca0','Gross Expense',1,0);
INSERT INTO "Payroll Field Types" ("GUID", "Name", "Debit Required", "Credit Required") VALUES ('f1b0d26375e74b0eb1aeb53bace00499','Employee Paid',0,1);

CREATE TABLE "Payroll Fields" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Name" VARCHAR(32) NOT NULL UNIQUE,
	"Payroll Field Types GUID" CHAR(32) NOT NULL,
	"Debit Accounts GUID" CHAR(32),
	"Credit Accounts GUID" CHAR(32)
);
ALTER TABLE "Payroll Fields" ADD CONSTRAINT "Payroll Fields>Payroll Field Type" FOREIGN KEY ("Payroll Field Types GUID") REFERENCES "Payroll Field Types" ("GUID");
ALTER TABLE "Payroll Fields" ADD CONSTRAINT "Debit Payroll Fields>Debit Account" FOREIGN KEY ("Debit Accounts GUID") REFERENCES "Accounts" ("GUID");
ALTER TABLE "Payroll Fields" ADD CONSTRAINT "Credit Payroll Fields>Credit Account" FOREIGN KEY ("Credit Accounts GUID") REFERENCES "Accounts" ("GUID");

CREATE TABLE "Payroll Checks" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Is Template" BIT NOT NULL,
	"Number" VARCHAR(64),
	"Date" DATE NOT NULL,

	"Employees GUID" CHAR(32) NOT NULL,
	"Pay To The Order Of" VARCHAR(128),
	"Line 1" VARCHAR(128),
	"Line 2" VARCHAR(128),
	"Country" VARCHAR(3),
	"City" VARCHAR(64),
	"State" VARCHAR(32),
	"Postal Code" VARCHAR(16),

	"Accounts GUID" CHAR(32) NOT NULL,
	"Posted Transactions GUID" CHAR(32),

	"Compensation" DECIMAL(64,16) NOT NULL,
	"Adjusted Gross" DECIMAL(64,16) NOT NULL,
	"Employee Paid" DECIMAL(64,16) NOT NULL,
	"Paycheck Amount" DECIMAL(64,16) NOT NULL,
	"Company Paid" DECIMAL(64,16) NOT NULL,
	"Total Costs" DECIMAL(64,16) NOT NULL,

	"Duration" INT NOT NULL,
	"Ending" DATE NOT NULL
);
ALTER TABLE "Payroll Checks" ADD CONSTRAINT "Payroll Checks>Employee GUID" FOREIGN KEY ("Employees GUID") REFERENCES "Employees"("GUID");
ALTER TABLE "Payroll Checks" ADD CONSTRAINT "Payroll Checks>Accounts GUID" FOREIGN KEY ("Accounts GUID") REFERENCES "Accounts"("GUID");
ALTER TABLE "Payroll Checks" ADD CONSTRAINT "Payroll Checks>Transaction" FOREIGN KEY ("Posted Transactions GUID") REFERENCES "Transactions"("GUID");

CREATE TABLE "Payroll Field Values" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Payroll Checks GUID" CHAR(32) NOT NULL,
	"Payroll Fields GUID" CHAR(32) NOT NULL,

	"Rate" DECIMAL(64,16) NOT NULL,
	"Quantity" DECIMAL(64,16) NOT NULL,
	"Total" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Payroll Field Values" ADD CONSTRAINT "Payroll Field Values>Payroll Check" FOREIGN KEY ("Payroll Checks GUID") REFERENCES "Payroll Checks"("GUID");
ALTER TABLE "Payroll Field Values" ADD CONSTRAINT "Payroll Field Values>Payroll Field" FOREIGN KEY ("Payroll Fields GUID") REFERENCES "Payroll Fields"("GUID");

INSERT INTO "Account Types" ("GUID", "Name", "Gets Closed") VALUES ('2ddbad1cf8d04f0d9c44fa9771a15306','Revenue',1);
INSERT INTO "Account Types" ("GUID", "Name", "Gets Closed") VALUES ('40f7e36772f240999fca4a9d2c1b536f','Equity Stays Open',0);
INSERT INTO "Account Types" ("GUID", "Name", "Gets Closed") VALUES ('531c81d9f0c942aeb5b742160d6b3942','Equity Gets Closed',1);
INSERT INTO "Account Types" ("GUID", "Name", "Gets Closed") VALUES ('6ee39c8c894e45fbb7892175e3365a34','Retained Earnings',0);
INSERT INTO "Account Types" ("GUID", "Name", "Gets Closed") VALUES ('ade6405dd31d40169b7ed222ecaa6b9e','Expenses',1);
INSERT INTO "Account Types" ("GUID", "Name", "Gets Closed") VALUES ('cf190cadde734f98a348d5f6bd112db6','Assets',0);
INSERT INTO "Account Types" ("GUID", "Name", "Gets Closed") VALUES ('ddc9a0f637b64af2adeb19d0f6399e19','Liability',0);

INSERT INTO "Contact Types" ("GUID", "Is Allowed", "Display Name") VALUES ('4134430d7bb64a97b95f7862bea83644',1,'Business');
INSERT INTO "Contact Types" ("GUID", "Is Allowed", "Display Name") VALUES ('77c1c5bad818403f8ae836a3fc7fd84b',1,'Individual');
INSERT INTO "Contact Types" ("GUID", "Is Allowed", "Display Name") VALUES ('7c85de861e784b9f8dcb4a456e267869',1,'Employee');

INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('14a778aeb6e04b7fa1dd31e4445e9b18','Payroll Check','/incAccounting?App=PayrollCheck','/incAccounting?App=PayrollCheck',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('1d648ca26c9c40c59e1483aa262656c2','Transaction','/incAccounting?App=Transaction','/incAccounting?App=Transaction',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('276db4afcf634b6fbc4a5821c9858ab9','Sales Quote','/incAccounting?App=Document','/incAccounting?App=Document',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('2c12d6167d654604be3f533c38f1ad1e','Sales Payment','/incAccounting?App=Payment','/incAccounting?App=Payment',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('5f756fc5f7c5493ca0d86f2d0ead2fda','Sales Order','/incAccounting?App=Document','/incAccounting?App=Document',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('6632ec00f5824aeca4a49bf21cbdaece','Purchase Credit','/incAccounting?App=Document','/incAccounting?App=Document',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('81e2917ac5c34d1cb6f9d168cd0439db','Sales Invoice','/incAccounting?App=Document','/incAccounting?App=Document',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('86af180c412f40c5a660678e3895694b','Sales Credit','/incAccounting?App=Document','/incAccounting?App=Document',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('9d3821afd6fb47f9b2713d3cc574ceff','Purchase Invoice','/incAccounting?App=Document','/incAccounting?App=Document',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('a714a873202f4f12bb29a42ed8ed9b5c','Purchase Payment','/incAccounting?App=Payment','/incAccounting?App=Payment',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('bbb9a35380834fe9976ad7184976f0d6','Bank Deposit','/incAccounting?App=Deposit','/incAccounting?App=Deposit',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('dedf79eddf7c4e348918d42e25b53309','Purchase Quote','/incAccounting?App=Document','/incAccounting?App=Document',1);
INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('e56b2b6aa42b479890085b74b69275f3','Purchase Order','/incAccounting?App=Document','/incAccounting?App=Document',1);

INSERT INTO "Document Types" ("GUID", "Is Sales Related", "Is Credit Memo", "Is Posting", "Accounts GUID") VALUES ('276db4afcf634b6fbc4a5821c9858ab9',1,0,0,NULL);
INSERT INTO "Document Types" ("GUID", "Is Sales Related", "Is Credit Memo", "Is Posting", "Accounts GUID") VALUES ('5f756fc5f7c5493ca0d86f2d0ead2fda',1,0,0,NULL);
INSERT INTO "Document Types" ("GUID", "Is Sales Related", "Is Credit Memo", "Is Posting", "Accounts GUID") VALUES ('6632ec00f5824aeca4a49bf21cbdaece',0,1,1,NULL);
INSERT INTO "Document Types" ("GUID", "Is Sales Related", "Is Credit Memo", "Is Posting", "Accounts GUID") VALUES ('81e2917ac5c34d1cb6f9d168cd0439db',1,0,1,NULL);
INSERT INTO "Document Types" ("GUID", "Is Sales Related", "Is Credit Memo", "Is Posting", "Accounts GUID") VALUES ('86af180c412f40c5a660678e3895694b',1,1,1,NULL);
INSERT INTO "Document Types" ("GUID", "Is Sales Related", "Is Credit Memo", "Is Posting", "Accounts GUID") VALUES ('9d3821afd6fb47f9b2713d3cc574ceff',0,0,1,NULL);
INSERT INTO "Document Types" ("GUID", "Is Sales Related", "Is Credit Memo", "Is Posting", "Accounts GUID") VALUES ('dedf79eddf7c4e348918d42e25b53309',0,0,0,NULL);
INSERT INTO "Document Types" ("GUID", "Is Sales Related", "Is Credit Memo", "Is Posting", "Accounts GUID") VALUES ('e56b2b6aa42b479890085b74b69275f3',0,0,0,NULL);

INSERT INTO "Payment Types" ("GUID", "Accounts GUID", "Is Sales Related") VALUES ('2c12d6167d654604be3f533c38f1ad1e',NULL,1);
INSERT INTO "Payment Types" ("GUID", "Accounts GUID", "Is Sales Related") VALUES ('a714a873202f4f12bb29a42ed8ed9b5c',NULL,0);

INSERT INTO "Payment Types Document Types" ("Document Types GUID", "Payment Types GUID") VALUES ('6632ec00f5824aeca4a49bf21cbdaece','a714a873202f4f12bb29a42ed8ed9b5c');
INSERT INTO "Payment Types Document Types" ("Document Types GUID", "Payment Types GUID") VALUES ('81e2917ac5c34d1cb6f9d168cd0439db','2c12d6167d654604be3f533c38f1ad1e');
INSERT INTO "Payment Types Document Types" ("Document Types GUID", "Payment Types GUID") VALUES ('86af180c412f40c5a660678e3895694b','2c12d6167d654604be3f533c38f1ad1e');
INSERT INTO "Payment Types Document Types" ("Document Types GUID", "Payment Types GUID") VALUES ('9d3821afd6fb47f9b2713d3cc574ceff','a714a873202f4f12bb29a42ed8ed9b5c');

INSERT INTO "Sales Taxes" ("GUID", "Contacts GUID", "Is Group", "Display Name", "Tax Rate", "Accounts GUID") VALUES ('502fd04bc5da462f98d013dfa50d808e',NULL,1,'Exempt',0.0000000000000000,NULL);
INSERT INTO "Contacts" ("GUID", "Parent Contacts GUID", "Display Name", "Is Allowed", "Contact Since", "Default Shipping Contacts GUID", "Website", "Email Address", "Office Phone", "Mobile Phone", "Home Phone", "Fax Number", "Line 1", "Line 2", "Country", "City", "State", "Postal Code", "Tax Group GUID", "Contact Types GUID") VALUES ('fabca02484aa46eaa24c939c64779a2d',NULL,'YOUR COMPANY - RENAME ME',1,'2017-03-09',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'502fd04bc5da462f98d013dfa50d808e','4134430d7bb64a97b95f7862bea83644');

INSERT INTO "Users" ("GUID", "Is Allowed", "Display Name", "SQL User", "Email Address", "Password Date", "Add Computer") VALUES ('86b41969e95143c090fd93a4819c58a2',1,'Administrator','administrator','administrator@localhost','2017-03-09',1);

INSERT INTO "Groups" ("GUID", "Is Allowed", "Display Name", "Email Address") VALUES ('11eede08a5f34402a2547edc6aad2529',1,'Everyone','everyone@localhost');
INSERT INTO "Groups" ("GUID", "Is Allowed", "Display Name", "Email Address") VALUES ('f541b846c9224fc687420fce2a0ec8b1',1,'System Administrators','postmaster@localhost');

INSERT INTO "Memberships" ("Users GUID", "Groups GUID") VALUES ('86b41969e95143c090fd93a4819c58a2','f541b846c9224fc687420fce2a0ec8b1');

INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('bbb9a35380834fe9976ad7184976f0d6','Bank Deposit');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('c8276ec6738a4fc0bb0fe7af7815f045','Control - Options');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('6632ec00f5824aeca4a49bf21cbdaece','Document - Purchase Credit');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('9d3821afd6fb47f9b2713d3cc574ceff','Document - Purchase Invoice');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('e56b2b6aa42b479890085b74b69275f3','Document - Purchase Order');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('dedf79eddf7c4e348918d42e25b53309','Document - Purchase Quote');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('86af180c412f40c5a660678e3895694b','Document - Sales Credit');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('81e2917ac5c34d1cb6f9d168cd0439db','Document - Sales Invoice');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('5f756fc5f7c5493ca0d86f2d0ead2fda','Document - Sales Order');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('276db4afcf634b6fbc4a5821c9858ab9','Document - Sales Quote');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('a714a873202f4f12bb29a42ed8ed9b5c','Payment - Purchase');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('2c12d6167d654604be3f533c38f1ad1e','Payment - Sales');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('720cdbbebae7124d2c3fdc1b46664655','Table - Account Types');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('9b945efebb006547a94415eadaa12921','Table - Accounts');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('45888a4da062f16a099a7f7c6cc15ee0','Table - Computers');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('74296745c146fc4ffc4afda0f19f1f2c','Table - Contact Notes');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('2a85420faee85c0a1aa204a3ee713ba4','Table - Contact Types');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('9aa698f602b1e5694855cee73a683488','Table - Contacts');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('6332798b12e537b25b1c6ad254e14f54','Table - Conversions');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('c8cf2b64be19b0234578a5b582f86a87','Table - Departments');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('965f19fd66f3bbd02a54f47952b68083','Table - Document Lines');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('1c8d2e4380181d9b2c0429dce7378d38','Table - Document Types');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('f28128b38efbc6134dc40751ee21fd29','Table - Documents');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('a37ede293936e29279ed543129451ec3','Table - Groups');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('9dea4016dbcc290b773ab2fae678aaa8','Table - Items');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('12ceff2290bb9039beaa8f36d5dec226','Table - Jobs');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('b2d37ae1cedf42ff874289b721860af2','Table - Logs');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('a7a7b26872b3e2d00de7bb7b1452b5a8','Table - Memberships');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('d08ccf52b4cdd08e41cfb99ec42e0b29','Table - Permissions');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('4a8a1dc4a61260a1d51e3b9f8bb5f18f','Table - Reconciliations');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('66b3ea16f68c96570fad11647b1fba50','Table - Report Blocks');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('755a0e7f295f45dacc4af7776f5150f3','Table - Report Filters');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('c91c7b93c28cd18741b71f727ee81ee3','Table - Reports');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('aa0a678d8950cf58d676ff1df2aa08ce','Table - Sales Tax Memberships');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('b748fae7af491847c7a3fcb4db6e13b1','Table - Securables');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('f4f70727dc34561dfde1a3c529b6205c','Table - Settings');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('e4eed2a7d7b7558dfe63d4f4fd18ce67','Table - Transaction Lines');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('d1e2f3dc6323be332c0590e0496f63ac','Table - Transaction Types');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('31112aca11d0e9e6eb7db96f317dda49','Table - Transactions');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('7b75551eba1477306b17861d66595527','Table - Unit Measures');
INSERT INTO "Securables" ("GUID", "Display Name") VALUES ('f9aae5fda8d810a29f12d1e61b4ab25f','Table - Users');

INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','12ceff2290bb9039beaa8f36d5dec226',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','1c8d2e4380181d9b2c0429dce7378d38',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','2a85420faee85c0a1aa204a3ee713ba4',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','31112aca11d0e9e6eb7db96f317dda49',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','45888a4da062f16a099a7f7c6cc15ee0',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','4a8a1dc4a61260a1d51e3b9f8bb5f18f',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','6332798b12e537b25b1c6ad254e14f54',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','720cdbbebae7124d2c3fdc1b46664655',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','74296745c146fc4ffc4afda0f19f1f2c',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','7b75551eba1477306b17861d66595527',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','965f19fd66f3bbd02a54f47952b68083',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','9aa698f602b1e5694855cee73a683488',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','9b945efebb006547a94415eadaa12921',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','9dea4016dbcc290b773ab2fae678aaa8',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','a37ede293936e29279ed543129451ec3',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','a7a7b26872b3e2d00de7bb7b1452b5a8',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','aa0a678d8950cf58d676ff1df2aa08ce',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','b2d37ae1cedf42ff874289b721860af2',1,0,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','b748fae7af491847c7a3fcb4db6e13b1',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','c8276ec6738a4fc0bb0fe7af7815f045',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','c8cf2b64be19b0234578a5b582f86a87',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','d08ccf52b4cdd08e41cfb99ec42e0b29',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','d1e2f3dc6323be332c0590e0496f63ac',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','e4eed2a7d7b7558dfe63d4f4fd18ce67',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','f28128b38efbc6134dc40751ee21fd29',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','f4f70727dc34561dfde1a3c529b6205c',0,1,0,0);
INSERT INTO "Permissions" ("Groups GUID", "Securables GUID", "Can Create", "Can Read", "Can Update", "Can Delete") VALUES ('11eede08a5f34402a2547edc6aad2529','f9aae5fda8d810a29f12d1e61b4ab25f',0,1,0,0);

INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('03737dafb5764f828126f1642ebf4c93',NULL,'Document - Last Sales Credit Number','String',NULL,'1000');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('0724bc756d5d4cd7a998f4ce186c550b',NULL,'Document - Last Purchase Order Number','String',NULL,'1000');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('177d88c1012c42019566579dc3910d51',NULL,'Password Complexity (1-4)','Number',NULL,'3');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('399fa709063447259bc613c1bf84863e',NULL,'Conversions - Decimals','Number',NULL,'8');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('41f5350849444a06b6007c717664e595',NULL,'Document - Last Sales Invoice Number','String',NULL,'1000');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('47f8bfb64e5a4e64aa70aaae34add1af',NULL,'Segment Seperator Enabled','Boolean',NULL,'T');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('51c59e001eef4416a3066782d6dba979',NULL,'Document - Last Sales Quote Number','String',NULL,'1000');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('626a4f36bba0495fb005599d8a152818',NULL,'Document - Last Purchase Quote Number','String',NULL,'1000');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('6aea55e36fb3412c837216448879369b',NULL,'Document - Last Purchase Credit Number','String',NULL,'1000');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('6d7c3367294245f99824de9f6da3ef40',NULL,'Document - Last Sales Order Number','String',NULL,'1000');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('8cead83092174022836b4340bf8da744',NULL,'Segment Seperator Symbol','String',NULL,'-');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('94a348283d654ad0b9e45b4e2db1af1f',NULL,'Password Age (Days)','Number',NULL,'45');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('96bafaff82cf43ae9b8f31f36468606e',NULL,'Version Number','String',NULL,'2016.1.5.dev');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('abc162852129465e95f3c498ba355f86',NULL,'History Length','Number',NULL,'4');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('ac17f9e7262b4d05ad137948a437a59a',NULL,'Password Length (1-4)','Number',NULL,'6');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('b0fb2fc242d94189a2352a90ef481ac0',NULL,'Document - Rate Decimals','Number',NULL,'4');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('b1b8097d133a40b695c994d5f1760678',NULL,'Check MAC Address','Boolean',NULL,'F');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('babaa35c8acc47ea89b8b675a66f2a51',NULL,'Document - Post on Save','Boolean',NULL,'T');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('bc405ce2f8bc4166b626411b90b57181',NULL,'Document - Quantity Decimals','Number',NULL,'2');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('be93f84dfb644309b1c82b4088de18b0',NULL,'Enforce Computers','Boolean',NULL,'1');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('ca8d29ebf97e416ea02cc9bef639fb32',NULL,'Document - Terms','String',NULL,'COD');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('d6d9a8ece95c4e5f8659fbcf1e29073d',NULL,'Document - Last Purchase Invoice Number','String',NULL,'1000');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('ecd5610eb9964341bd24caca910958a2',NULL,'Document - Due Days','Number',NULL,'0');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('f747f355a1294355810820150b620899',NULL,'Document - Money Decimals','Number',NULL,'2');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") VALUES ('fceafe4704bb441f99a90a3009c09520',NULL,'Transaction Line Setting Decimals','Number',NULL,'2');

CREATE TABLE "Item Postings" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Increasing Document Lines GUID" CHAR(32) NOT NULL,
	"Decreasing Document Lines GUID" CHAR(32) NOT NULL,
	"Decrease Quantity" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Item Postings" ADD CONSTRAINT "Increasing Item Postings>Increasing Document Line" FOREIGN KEY ("Increasing Document Lines GUID") REFERENCES "Document Lines" ("GUID");
ALTER TABLE "Item Postings" ADD CONSTRAINT "Decreasing Item Postings>Decreasing Document Line" FOREIGN KEY ("Decreasing Document Lines GUID") REFERENCES "Document Lines" ("GUID");

ALTER TABLE "Reports" ADD COLUMN "Title" VARCHAR(255);

ALTER TABLE "Report Filters" ADD COLUMN "DELETE ME" BIGINT NOT NULL AUTO_INCREMENT UNIQUE;
ALTER TABLE "Report Filters" ADD COLUMN "Priority" BIGINT NOT NULL;
UPDATE "Report Filters" SET "Priority" = "DELETE ME";
ALTER TABLE "Report Filters" DROP COLUMN "DELETE ME";
ALTER TABLE "Report Filters" ADD CONSTRAINT "Unique Priority" UNIQUE ("Reports GUID", "Priority");

ALTER TABLE "Reports" ADD COLUMN "Auto Load" BIT;
UPDATE "Reports" SET "Auto Load" = 0;
ALTER TABLE "Reports" MODIFY COLUMN "Auto Load" BIT NOT NULL;

UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=PayrollCheck', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Payroll Checks' WHERE `GUID`='14a778aeb6e04b7fa1dd31e4445e9b18';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Transaction', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Transactions' WHERE `GUID`='1d648ca26c9c40c59e1483aa262656c2';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Document', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Quotes' WHERE `GUID`='276db4afcf634b6fbc4a5821c9858ab9';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Payment', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Payments' WHERE `GUID`='2c12d6167d654604be3f533c38f1ad1e';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Document', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Orders' WHERE `GUID`='5f756fc5f7c5493ca0d86f2d0ead2fda';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Document', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Credit Memos' WHERE `GUID`='6632ec00f5824aeca4a49bf21cbdaece';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Document', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Invoices' WHERE `GUID`='81e2917ac5c34d1cb6f9d168cd0439db';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Document', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Credit Memos' WHERE `GUID`='86af180c412f40c5a660678e3895694b';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Document', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Invoices' WHERE `GUID`='9d3821afd6fb47f9b2713d3cc574ceff';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Payment', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Payments' WHERE `GUID`='a714a873202f4f12bb29a42ed8ed9b5c';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Deposit', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Bank Deposits' WHERE `GUID`='bbb9a35380834fe9976ad7184976f0d6';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Document', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Quotes' WHERE `GUID`='dedf79eddf7c4e348918d42e25b53309';
UPDATE `Transaction Types` SET `Edit URL`='/incAccounting?App=Document', `List URL`='~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Orders' WHERE `GUID`='e56b2b6aa42b479890085b74b69275f3';

CREATE TABLE "Register Entries" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Contacts GUID" CHAR(32)
);
ALTER TABLE "Register Entries" ADD CONSTRAINT "Register Entries>Transaction Line" FOREIGN KEY ("GUID") REFERENCES "Transaction Lines" ("GUID");
ALTER TABLE "Register Entries" ADD CONSTRAINT "Register Entries>Contact" FOREIGN KEY ("Contacts GUID") REFERENCES "Contacts" ("GUID");

INSERT INTO "Transaction Types" ("GUID", "Name", "Edit URL", "List URL", "Is Allowed") VALUES ('38ec8da627f345eb9f0639c0931d5db8','Register Entry','/incAccounting?App=RegisterEntry','/incAccounting?App=RegisterEntry',1);

ALTER TABLE "Logs" DROP FOREIGN KEY "Computer<Logs";
ALTER TABLE "Logs" DROP COLUMN "Computers GUID";
DROP TABLE "Computers";
DELETE FROM "Permissions" WHERE "Securables GUID" = '45888a4da062f16a099a7f7c6cc15ee0';
DELETE FROM "Securables" WHERE "GUID" = '45888a4da062f16a099a7f7c6cc15ee0';
DELETE FROM "Settings" WHERE "GUID" = 'be93f84dfb644309b1c82b4088de18b0';
ALTER TABLE "Users" ADD COLUMN "Password Hash" VARCHAR(2048);
ALTER TABLE "Users" DROP COLUMN "SQL User";
ALTER TABLE "Users" DROP COLUMN "Add Computer";
UPDATE "Users" SET "Password Hash" = '1000:2da86ad258b99745be189bd4b4e24cc4:63ff1e10e1e36ddcd50588793077385058a828ac3edebbfd5b9cd28d89be29bd61f37f4c59acf8d3b58a5b2d9a3b037c9362495196062ef2680ce664519b2698' WHERE "GUID" = '86b41969e95143c090fd93a4819c58a2';

UPDATE "Settings" SET "Value" = '2017.3.17' WHERE "Key" = 'Version Number'
