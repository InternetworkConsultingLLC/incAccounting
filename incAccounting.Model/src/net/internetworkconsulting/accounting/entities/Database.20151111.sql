USE "%DATABASE%";

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
