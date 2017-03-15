USE "%DATABASE%";

CREATE TABLE "Item Postings" (
	"GUID" CHAR(32) NOT NULL PRIMARY KEY,
	"Increasing Document Lines GUID" CHAR(32) NOT NULL,
	"Decreasing Document Lines GUID" CHAR(32) NOT NULL,
	"Decrease Quantity" DECIMAL(64,16) NOT NULL
);
ALTER TABLE "Item Postings" ADD CONSTRAINT "Increasing Item Postings>Increasing Document Line" FOREIGN KEY ("Increasing Document Lines GUID") REFERENCES "Document Lines" ("GUID");
ALTER TABLE "Item Postings" ADD CONSTRAINT "Decreasing Item Postings>Decreasing Document Line" FOREIGN KEY ("Decreasing Document Lines GUID") REFERENCES "Document Lines" ("GUID");