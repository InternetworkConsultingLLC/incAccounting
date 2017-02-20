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

INSERT INTO "Payroll Field Types" ("GUID", "Name", "Debit Required", "Credit Required") VALUES
('af32731792b64a6081a6a1f73d9afca0', 'Gross Expense', 1, 0),
('2f542318ae174eaf8bb95f02ed8f6df5', 'Company Paid', 1, 1),
('f1b0d26375e74b0eb1aeb53bace00499', 'Employee Paid', 0, 1);

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
