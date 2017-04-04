INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") 
VALUES ('2c1f10ede8134fb8b1bab2f3e12a026d', NULL, 'Payment - Last Sales Receipt Number', 'String', NULL, '100');
INSERT INTO "Settings" ("GUID", "Users GUID", "Key", "Type", "Option Query", "Value") 
VALUES ('ce9ccd7650d94033a7657cf1c65d2da1', NULL, 'Deposit - Lastes Number', 'String', NULL, '1000');

ALTER TABLE "Accounts" ADD COLUMN "Last Number" VARCHAR(128);
UPDATE "Accounts" SET "Last Number" = '1' WHERE "Last Number" IS NULL;
ALTER TABLE "Accounts" CHANGE COLUMN "Last Number" "Last Number" VARCHAR(128) NOT NULL;

ALTER TABLE "Payments" DROP INDEX "Our Number";
ALTER TABLE "Payments" ADD CONSTRAINT "Payents Unique Payment Type Account Our number" UNIQUE ("Posted Accounts GUID", "Payment Types GUID", "Our Number");

UPDATE "Settings" SET "Value" = '2017.4.1' WHERE "Key" = 'Version Number';