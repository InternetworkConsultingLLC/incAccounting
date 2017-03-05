ALTER TABLE "Reports" ADD COLUMN "Title" VARCHAR(255);

ALTER TABLE "Report Filters" ADD COLUMN "DELETE ME" BIGINT NOT NULL AUTO_INCREMENT UNIQUE;
ALTER TABLE "Report Filters" ADD COLUMN "Priority" BIGINT NOT NULL;
UPDATE "Report Filters" SET "Priority" = "DELETE ME";
ALTER TABLE "Report Filters" DROP COLUMN "DELETE ME";
ALTER TABLE "Report Filters" ADD CONSTRAINT "Unique Priority" UNIQUE ("Reports GUID", "Priority");