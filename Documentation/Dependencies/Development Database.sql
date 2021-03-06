-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: ia-incllc
-- ------------------------------------------------------
-- Server version	5.7.15-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Account Types`
--

DROP TABLE IF EXISTS `Account Types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account Types` (
  `GUID` char(32) NOT NULL,
  `Name` varchar(128) NOT NULL,
  `Gets Closed` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account Types`
--

LOCK TABLES `Account Types` WRITE;
/*!40000 ALTER TABLE `Account Types` DISABLE KEYS */;
INSERT INTO `Account Types` (`GUID`, `Name`, `Gets Closed`) VALUES ('2ddbad1cf8d04f0d9c44fa9771a15306','Revenue','');
INSERT INTO `Account Types` (`GUID`, `Name`, `Gets Closed`) VALUES ('40f7e36772f240999fca4a9d2c1b536f','Equity Stays Open','\0');
INSERT INTO `Account Types` (`GUID`, `Name`, `Gets Closed`) VALUES ('531c81d9f0c942aeb5b742160d6b3942','Equity Gets Closed','');
INSERT INTO `Account Types` (`GUID`, `Name`, `Gets Closed`) VALUES ('6ee39c8c894e45fbb7892175e3365a34','Retained Earnings','\0');
INSERT INTO `Account Types` (`GUID`, `Name`, `Gets Closed`) VALUES ('ade6405dd31d40169b7ed222ecaa6b9e','Expenses','');
INSERT INTO `Account Types` (`GUID`, `Name`, `Gets Closed`) VALUES ('cf190cadde734f98a348d5f6bd112db6','Assets','\0');
INSERT INTO `Account Types` (`GUID`, `Name`, `Gets Closed`) VALUES ('ddc9a0f637b64af2adeb19d0f6399e19','Liability','\0');
/*!40000 ALTER TABLE `Account Types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Accounts`
--

DROP TABLE IF EXISTS `Accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Accounts` (
  `GUID` char(32) NOT NULL,
  `Account Types GUID` char(32) NOT NULL,
  `Parent Accounts GUID` char(32) DEFAULT NULL,
  `Segment` varchar(8) NOT NULL,
  `Number` varchar(32) NOT NULL,
  `Name` varchar(64) NOT NULL,
  `Nested Name` varchar(1024) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Number` (`Number`),
  UNIQUE KEY `Nested Name` (`Nested Name`),
  UNIQUE KEY `Accounts Unique Segment` (`Parent Accounts GUID`,`Segment`),
  UNIQUE KEY `Accounts Unique Name` (`Parent Accounts GUID`,`Name`),
  KEY `Accounts>Account Type` (`Account Types GUID`),
  CONSTRAINT `Accounts>Account Type` FOREIGN KEY (`Account Types GUID`) REFERENCES `Account Types` (`GUID`),
  CONSTRAINT `Children>Parent Account` FOREIGN KEY (`Parent Accounts GUID`) REFERENCES `Accounts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Accounts`
--

LOCK TABLES `Accounts` WRITE;
/*!40000 ALTER TABLE `Accounts` DISABLE KEYS */;
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('0234c5a2760d429ea28f56d8a7bc1098','ade6405dd31d40169b7ed222ecaa6b9e','0d0dc355180a498c98c8d1e97dd1a3e6','1','521','Communications','Expenses - Office - Communications','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('045a452dcedb49e39d07d091413297ce','531c81d9f0c942aeb5b742160d6b3942','77687e6a71f4427d99c2dca24ad1c621','2','312','Lurlene B Jarvis','Equity - Shares - Lurlene B Jarvis','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('0769a92cf6c84d7e90e27d1dd2969810','2ddbad1cf8d04f0d9c44fa9771a15306','1d8ff83fa1a1425595133141036624a2','1','421','Fees and Interest','Revenue - Indirect - Fees and Interest','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('0d0dc355180a498c98c8d1e97dd1a3e6','ade6405dd31d40169b7ed222ecaa6b9e','3b476f80daa84ef9ba136cd511831d6b','2','52','Office','Expenses - Office','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('15cb2f897a9f4a979aa966873dad17a5','531c81d9f0c942aeb5b742160d6b3942','fbbe1de49f3b44a6a3907265cfa4ec1a','2','32','Distributions','Equity - Distributions','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('174f527b44614ff9a072ec9275f0613f','ddc9a0f637b64af2adeb19d0f6399e19','e22e059ec84841adaa2e11ddb504ccad','6','2136','Social Security','Liability - Current - Employee - Social Security','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('177fd8570db344ec974dfe6f04948a45','ade6405dd31d40169b7ed222ecaa6b9e','0d0dc355180a498c98c8d1e97dd1a3e6','6','526','Technology','Expenses - Office - Technology','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('1a1c764444434ac8832471b6ea7cf1a4','ddc9a0f637b64af2adeb19d0f6399e19','e22e059ec84841adaa2e11ddb504ccad','3','2133','Federal Unemployment','Liability - Current - Employee - Federal Unemployment','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('1d8ff83fa1a1425595133141036624a2','2ddbad1cf8d04f0d9c44fa9771a15306','a5f317be0a8a4c7abbe950a3e6bf2acc','2','42','Indirect','Revenue - Indirect','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('21154875c581474499defecdc7a1be3e','ade6405dd31d40169b7ed222ecaa6b9e','36a562a669424dfbbc3720cbb6b32859','5','535','MediCare','Expenses - Employee - MediCare','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('21911b853d1644a6bd7ec2ee26cf0be7','ddc9a0f637b64af2adeb19d0f6399e19','e22e059ec84841adaa2e11ddb504ccad','4','2134','Insurance Withheld','Liability - Current - Employee - Insurance Withheld','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('2af4878099684f1daecfc4668d16dbd2','ade6405dd31d40169b7ed222ecaa6b9e','d2cbfb10cb7e46f58fab969395aabe83','3','5123','Tools','Expenses - Cost of Goods and Services - Indirect - Tools','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('3021fe7009b54825bd53696a34dcc600','ade6405dd31d40169b7ed222ecaa6b9e','f20389ccd5ab4a819575c7ddc657d586','2','542','Fuel','Expenses - Logistics - Fuel','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('32b52d72c76145828992035b86671b54','ade6405dd31d40169b7ed222ecaa6b9e','0d0dc355180a498c98c8d1e97dd1a3e6','5','525','Supplies','Expenses - Office - Supplies','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('342f493a37d84199afdaf96981a48daf','2ddbad1cf8d04f0d9c44fa9771a15306','880bcd3b5ec54d0885f9973de00495b8','1','411','Engineering','Revenue - Direct - Engineering','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('34c0422c84664a57be1a92c8e087ae55','ade6405dd31d40169b7ed222ecaa6b9e','36a562a669424dfbbc3720cbb6b32859','3','533','Insurance','Expenses - Employee - Insurance','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('35d75e7af67544f293010da48ad5bd68','cf190cadde734f98a348d5f6bd112db6','6068ab201db742e0aa79987cccd4b40f','2','12','Long Term','Assets - Long Term','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('36a562a669424dfbbc3720cbb6b32859','ade6405dd31d40169b7ed222ecaa6b9e','3b476f80daa84ef9ba136cd511831d6b','3','53','Employee','Expenses - Employee','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('3b476f80daa84ef9ba136cd511831d6b','ade6405dd31d40169b7ed222ecaa6b9e',NULL,'5','5','Expenses','Expenses','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('3baa2153e37945ff86c73f9a3c961686','531c81d9f0c942aeb5b742160d6b3942','77687e6a71f4427d99c2dca24ad1c621','1','311','Larry M Morgan','Equity - Shares - Larry M Morgan','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('3bed1cad8e4c43c792f35ab9fb582718','cf190cadde734f98a348d5f6bd112db6','7518a94aa013470ba694c28205b421a8','4','114','Primary Checking','Assets - Current - Primary Checking','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('3f630577888e4abc8f2c4c220362afb8','ade6405dd31d40169b7ed222ecaa6b9e','0d0dc355180a498c98c8d1e97dd1a3e6','3','523','Professional Services','Expenses - Office - Professional Services','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('445dc744fcc244ddb853290e91efba7e','cf190cadde734f98a348d5f6bd112db6','7518a94aa013470ba694c28205b421a8','7','117','Inventory','Assets - Current - Inventory','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('44f8a57a06b74750bba19d68b98a4dfb','ddc9a0f637b64af2adeb19d0f6399e19','e22e059ec84841adaa2e11ddb504ccad','2','2132','Federal Taxes','Liability - Current - Employee - Federal Taxes','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('478a214d398c42299e201dcc58cceb2c','ade6405dd31d40169b7ed222ecaa6b9e','f20389ccd5ab4a819575c7ddc657d586','4','544','Maintenance','Expenses - Logistics - Maintenance','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('4aa43c494003422eabc96954a9e5dc82','ddc9a0f637b64af2adeb19d0f6399e19','e22e059ec84841adaa2e11ddb504ccad','7','2137','State Unemployement','Liability - Current - Employee - State Unemployement','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('4b889a262c1645a19e6fdb6657e0ee41','cf190cadde734f98a348d5f6bd112db6','e8650ae6d3ae461fa7599dcbbd64caaf','1','1211','Accumulated Depreciation','Assets - Long Term - Vehicle XHG9K43 - Accumulated Depreciation','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('4ffba0ce6e1541aa91aedac15ce9e372','ddc9a0f637b64af2adeb19d0f6399e19',NULL,'2','2','Liability','Liability','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('536b61d1dd524ab093d3d7d1744afeff','ade6405dd31d40169b7ed222ecaa6b9e','f20389ccd5ab4a819575c7ddc657d586','3','543','Lodging','Expenses - Logistics - Lodging','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('58c5a1d7520c494db10e472ad6e51aa4','ade6405dd31d40169b7ed222ecaa6b9e','36a562a669424dfbbc3720cbb6b32859','4','534','Meals','Expenses - Employee - Meals','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('6068ab201db742e0aa79987cccd4b40f','cf190cadde734f98a348d5f6bd112db6',NULL,'1','1','Assets','Assets','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('649dc64f083d415d93e29159dc187665','ddc9a0f637b64af2adeb19d0f6399e19','e22e059ec84841adaa2e11ddb504ccad','1','2131','Child Support','Liability - Current - Employee - Child Support','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('6e44f65d6ed848a2bb2003820f3b39ef','cf190cadde734f98a348d5f6bd112db6','7518a94aa013470ba694c28205b421a8','1','111','Accounts Receivable','Assets - Current - Accounts Receivable','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('6ec3bf026eaa44bc8d88e03f0b4474d3','ade6405dd31d40169b7ed222ecaa6b9e','b660439401364ca7a3bd7b7dfcc61a01','2','51122','Customer','Expenses - Cost of Goods and Services - Direct - Engineering - Customer','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('73540d82939046738d682d30670a1487','ade6405dd31d40169b7ed222ecaa6b9e','f20389ccd5ab4a819575c7ddc657d586','1','541','Airfare','Expenses - Logistics - Airfare','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('7518a94aa013470ba694c28205b421a8','cf190cadde734f98a348d5f6bd112db6','6068ab201db742e0aa79987cccd4b40f','1','11','Current','Assets - Current','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('756c8a8d21b5452dbcec15fe47dffd33','ade6405dd31d40169b7ed222ecaa6b9e','f20389ccd5ab4a819575c7ddc657d586','5','545','Parking','Expenses - Logistics - Parking','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('77687e6a71f4427d99c2dca24ad1c621','531c81d9f0c942aeb5b742160d6b3942','fbbe1de49f3b44a6a3907265cfa4ec1a','1','31','Shares','Equity - Shares','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('78d1b5ab55994ca9b10680bae0f2d562','2ddbad1cf8d04f0d9c44fa9771a15306','880bcd3b5ec54d0885f9973de00495b8','3','413','Products','Revenue - Direct - Products','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('7ba5e0319a3d45f19322c9990c4cb57d','ade6405dd31d40169b7ed222ecaa6b9e','f20389ccd5ab4a819575c7ddc657d586','6','546','Per Diem','Expenses - Logistics - Per Diem','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('7c2a75c39b794c16b625052683bfabee','ade6405dd31d40169b7ed222ecaa6b9e','36a562a669424dfbbc3720cbb6b32859','6','536','Social Security','Expenses - Employee - Social Security','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('7cc46b6416fa49229ba5fc1633b2e3d7','2ddbad1cf8d04f0d9c44fa9771a15306','1d8ff83fa1a1425595133141036624a2','2','422','Sales Tax Discounts','Revenue - Indirect - Sales Tax Discounts','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('82c95ce723cd4fa4873f2f8f31e9e037','cf190cadde734f98a348d5f6bd112db6','445dc744fcc244ddb853290e91efba7e','2','1172','Finished Products','Assets - Current - Inventory - Finished Products','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('86e351148f9a45bcb4a192712a1f527e','ade6405dd31d40169b7ed222ecaa6b9e','b660439401364ca7a3bd7b7dfcc61a01','1','51121','Product','Expenses - Cost of Goods and Services - Direct - Engineering - Product','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('880bcd3b5ec54d0885f9973de00495b8','2ddbad1cf8d04f0d9c44fa9771a15306','a5f317be0a8a4c7abbe950a3e6bf2acc','1','41','Direct','Revenue - Direct','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('88787a70a2ae48528898d3cc64855bdd','ddc9a0f637b64af2adeb19d0f6399e19','badc5daf369c4ac498f075dcff38ea92','1','221','Vehicle Loan','Liability - Long Term - Vehicle Loan','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('88e4bbbc43d4471498919392473c216b','ade6405dd31d40169b7ed222ecaa6b9e','b3f04ff547164d7abf5e707d98deed7f','1','5111','Customer Service','Expenses - Cost of Goods and Services - Direct - Customer Service','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('8da1eceb2c394ebaa40eae025ada5144','ade6405dd31d40169b7ed222ecaa6b9e','3b476f80daa84ef9ba136cd511831d6b','5','55','Financial','Expenses - Financial','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('92c54566fdf84f939b4b814d372a444e','ade6405dd31d40169b7ed222ecaa6b9e','f20389ccd5ab4a819575c7ddc657d586','7','547','Tolls','Expenses - Logistics - Tolls','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('92e4d0ede48244d9b5b285d911befd13','ade6405dd31d40169b7ed222ecaa6b9e','36a562a669424dfbbc3720cbb6b32859','2','532','Fringe Benfits','Expenses - Employee - Fringe Benfits','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('985f4fa5a0494b1098063ec6dda5095c','ade6405dd31d40169b7ed222ecaa6b9e','d2cbfb10cb7e46f58fab969395aabe83','2','5122','Shipping and Handling','Expenses - Cost of Goods and Services - Indirect - Shipping and Handling','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('98f8603f0b4244aa8857930f68191a4d','ade6405dd31d40169b7ed222ecaa6b9e','8da1eceb2c394ebaa40eae025ada5144','4','554','Fines','Expenses - Financial - Fines','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('99a57b95e802471590f0991a13969b74','ade6405dd31d40169b7ed222ecaa6b9e','b3f04ff547164d7abf5e707d98deed7f','4','5114','Production','Expenses - Cost of Goods and Services - Direct - Production','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('9c190a29443a4d2cab792fab50ea7376','ade6405dd31d40169b7ed222ecaa6b9e','8da1eceb2c394ebaa40eae025ada5144','2','552','Interest','Expenses - Financial - Interest','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('9c931c306a804911bcc9f5a62ddbf516','ade6405dd31d40169b7ed222ecaa6b9e','3b476f80daa84ef9ba136cd511831d6b','9','59','Unknown','Expenses - Unknown','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('9ef18c0e6aa246c283bd812aa0ff405c','ddc9a0f637b64af2adeb19d0f6399e19','d5b2973084b5442cbc874d0b0b9b8266','1','211','Accounts Payable','Liability - Current - Accounts Payable','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('9f04b72d6cee49a29d85f283d2d9e5bb','531c81d9f0c942aeb5b742160d6b3942','77687e6a71f4427d99c2dca24ad1c621','3','313','Peter J Rios','Equity - Shares - Peter J Rios','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('9fc41eb7bd8b400a8d1069b6934ef30d','ade6405dd31d40169b7ed222ecaa6b9e','36a562a669424dfbbc3720cbb6b32859','1','531','Federal Unemployment','Expenses - Employee - Federal Unemployment','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('a10699098de44f64860a516a17a7a1e0','531c81d9f0c942aeb5b742160d6b3942','fbbe1de49f3b44a6a3907265cfa4ec1a','3','33','Retained Earnings','Equity - Retained Earnings','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('a5f317be0a8a4c7abbe950a3e6bf2acc','2ddbad1cf8d04f0d9c44fa9771a15306',NULL,'4','4','Revenue','Revenue','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('a912e4823d2a416bbcb5cbf7255d4c31','ade6405dd31d40169b7ed222ecaa6b9e','b3f04ff547164d7abf5e707d98deed7f','3','5113','Materials','Expenses - Cost of Goods and Services - Direct - Materials','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('a94bb67059ca4cf7801470b485d5f361','2ddbad1cf8d04f0d9c44fa9771a15306','880bcd3b5ec54d0885f9973de00495b8','2','412','Materials','Revenue - Direct - Materials','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('b3f04ff547164d7abf5e707d98deed7f','ade6405dd31d40169b7ed222ecaa6b9e','d51c4ac1eecd48aeb09669f1a0e89f01','1','511','Direct','Expenses - Cost of Goods and Services - Direct','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('b660439401364ca7a3bd7b7dfcc61a01','ade6405dd31d40169b7ed222ecaa6b9e','b3f04ff547164d7abf5e707d98deed7f','2','5112','Engineering','Expenses - Cost of Goods and Services - Direct - Engineering','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('ba1af79553d2419d8eeacff403c95046','cf190cadde734f98a348d5f6bd112db6','7518a94aa013470ba694c28205b421a8','3','113','Petty Cash','Assets - Current - Petty Cash','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('badc5daf369c4ac498f075dcff38ea92','ddc9a0f637b64af2adeb19d0f6399e19','4ffba0ce6e1541aa91aedac15ce9e372','2','22','Long Term','Liability - Long Term','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('bc0d1212d4f3482199d4303328fb6de5','ade6405dd31d40169b7ed222ecaa6b9e','d2cbfb10cb7e46f58fab969395aabe83','1','5121','Credit Card Processing','Expenses - Cost of Goods and Services - Indirect - Credit Card Processing','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('bff1870e972c4a24bf41c1957aa5eeff','ddc9a0f637b64af2adeb19d0f6399e19','e22e059ec84841adaa2e11ddb504ccad','5','2135','MediCare','Liability - Current - Employee - MediCare','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('c01655fb70854cc0af8e703063c8376d','ddc9a0f637b64af2adeb19d0f6399e19','d5b2973084b5442cbc874d0b0b9b8266','2','212','Customer Deposits','Liability - Current - Customer Deposits','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('c4ced50bf99c442a9398bc84ab2bf908','cf190cadde734f98a348d5f6bd112db6','7518a94aa013470ba694c28205b421a8','5','115','Undeposited Funds','Assets - Current - Undeposited Funds','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('d2cbfb10cb7e46f58fab969395aabe83','ade6405dd31d40169b7ed222ecaa6b9e','d51c4ac1eecd48aeb09669f1a0e89f01','2','512','Indirect','Expenses - Cost of Goods and Services - Indirect','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('d51c4ac1eecd48aeb09669f1a0e89f01','ade6405dd31d40169b7ed222ecaa6b9e','3b476f80daa84ef9ba136cd511831d6b','1','51','Cost of Goods and Services','Expenses - Cost of Goods and Services','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('d5b2973084b5442cbc874d0b0b9b8266','ddc9a0f637b64af2adeb19d0f6399e19','4ffba0ce6e1541aa91aedac15ce9e372','1','21','Current','Liability - Current','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('d625729a665840178bceb16f5eaec637','cf190cadde734f98a348d5f6bd112db6','7518a94aa013470ba694c28205b421a8','2','112','Employee Advances','Assets - Current - Employee Advances','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('e22e059ec84841adaa2e11ddb504ccad','ddc9a0f637b64af2adeb19d0f6399e19','d5b2973084b5442cbc874d0b0b9b8266','3','213','Employee','Liability - Current - Employee','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('e2a65cf7723544928785e80cef9ce27c','2ddbad1cf8d04f0d9c44fa9771a15306','1d8ff83fa1a1425595133141036624a2','3','423','Shipping and Handling','Revenue - Indirect - Shipping and Handling','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('e8650ae6d3ae461fa7599dcbbd64caaf','cf190cadde734f98a348d5f6bd112db6','35d75e7af67544f293010da48ad5bd68','1','121','Vehicle XHG9K43','Assets - Long Term - Vehicle XHG9K43','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('e93871fccc0c427d8cb32456c84eee21','ddc9a0f637b64af2adeb19d0f6399e19','d5b2973084b5442cbc874d0b0b9b8266','4','214','Sales Tax','Liability - Current - Sales Tax','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('e9f9ad317dc1447fb2d9e4a2215728b8','ade6405dd31d40169b7ed222ecaa6b9e','8da1eceb2c394ebaa40eae025ada5144','1','551','Fees','Expenses - Financial - Fees','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('ea19c9e8d62b4b83b3346d642444993a','ade6405dd31d40169b7ed222ecaa6b9e','0d0dc355180a498c98c8d1e97dd1a3e6','2','522','Marketing','Expenses - Office - Marketing','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('ebece718ad734b3e991ab331075b5e7f','cf190cadde734f98a348d5f6bd112db6','7518a94aa013470ba694c28205b421a8','6','116','Vendor Deposits','Assets - Current - Vendor Deposits','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('f1505bc01bbb4c66adaeec6fabad99b6','531c81d9f0c942aeb5b742160d6b3942','77687e6a71f4427d99c2dca24ad1c621','4','314','Roxanne C Clemons','Equity - Shares - Roxanne C Clemons','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('f18d72cbb8f842fd9194eeb2f0a6b189','2ddbad1cf8d04f0d9c44fa9771a15306','880bcd3b5ec54d0885f9973de00495b8','4','414','Service','Revenue - Direct - Service','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('f20389ccd5ab4a819575c7ddc657d586','ade6405dd31d40169b7ed222ecaa6b9e','3b476f80daa84ef9ba136cd511831d6b','4','54','Logistics','Expenses - Logistics','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('f4340533e7e44c7bbc6d01b8ae843120','ade6405dd31d40169b7ed222ecaa6b9e','36a562a669424dfbbc3720cbb6b32859','8','538','Education','Expenses - Employee - Education','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('f6fbfe41d35748ef8a3fac8234c90353','ade6405dd31d40169b7ed222ecaa6b9e','8da1eceb2c394ebaa40eae025ada5144','3','553','Taxes','Expenses - Financial - Taxes','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('f7a4eaae78a149abb82f09b45fde6626','cf190cadde734f98a348d5f6bd112db6','445dc744fcc244ddb853290e91efba7e','1','1171','Materials','Assets - Current - Inventory - Materials','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('fb5724e350f4403b9c3aac89478f6c78','ade6405dd31d40169b7ed222ecaa6b9e','0d0dc355180a498c98c8d1e97dd1a3e6','4','524','Equipment','Expenses - Office - Equipment','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('fbbe1de49f3b44a6a3907265cfa4ec1a','531c81d9f0c942aeb5b742160d6b3942',NULL,'3','3','Equity','Equity','');
INSERT INTO `Accounts` (`GUID`, `Account Types GUID`, `Parent Accounts GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('fe86f0725fd1415386a990927d328f72','ade6405dd31d40169b7ed222ecaa6b9e','36a562a669424dfbbc3720cbb6b32859','7','537','State Unemployment','Expenses - Employee - State Unemployment','');
/*!40000 ALTER TABLE `Accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Bank Deposits`
--

DROP TABLE IF EXISTS `Bank Deposits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Bank Deposits` (
  `GUID` char(32) NOT NULL,
  `Accounts GUID` char(32) NOT NULL,
  `Date` date NOT NULL,
  `Number` varchar(64) NOT NULL,
  `Items` int(11) NOT NULL,
  `Total` decimal(64,16) NOT NULL,
  `Posted Transactions GUID` char(32) DEFAULT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Number` (`Number`),
  KEY `Bank Deposit>Transaction` (`Posted Transactions GUID`),
  CONSTRAINT `Bank Deposit>Transaction` FOREIGN KEY (`Posted Transactions GUID`) REFERENCES `Transactions` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bank Deposits`
--

LOCK TABLES `Bank Deposits` WRITE;
/*!40000 ALTER TABLE `Bank Deposits` DISABLE KEYS */;
/*!40000 ALTER TABLE `Bank Deposits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Computers`
--

DROP TABLE IF EXISTS `Computers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Computers` (
  `GUID` char(32) NOT NULL,
  `Description` varchar(64) NOT NULL,
  `MAC Address` varchar(64) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Description` (`Description`),
  UNIQUE KEY `MAC Address` (`MAC Address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Computers`
--

LOCK TABLES `Computers` WRITE;
/*!40000 ALTER TABLE `Computers` DISABLE KEYS */;
/*!40000 ALTER TABLE `Computers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contact Notes`
--

DROP TABLE IF EXISTS `Contact Notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contact Notes` (
  `GUID` char(32) NOT NULL,
  `Contacts GUID` char(32) NOT NULL,
  `Users GUID` char(32) NOT NULL,
  `Dated` datetime NOT NULL,
  `Subject` varchar(128) NOT NULL,
  `Memorandum` varchar(2048) NOT NULL,
  PRIMARY KEY (`GUID`),
  KEY `Contact Notes>Contact` (`Contacts GUID`),
  CONSTRAINT `Contact Notes>Contact` FOREIGN KEY (`Contacts GUID`) REFERENCES `Contacts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contact Notes`
--

LOCK TABLES `Contact Notes` WRITE;
/*!40000 ALTER TABLE `Contact Notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `Contact Notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contact Types`
--

DROP TABLE IF EXISTS `Contact Types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contact Types` (
  `GUID` char(32) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  `Display Name` varchar(64) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Display Name` (`Display Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contact Types`
--

LOCK TABLES `Contact Types` WRITE;
/*!40000 ALTER TABLE `Contact Types` DISABLE KEYS */;
INSERT INTO `Contact Types` (`GUID`, `Is Allowed`, `Display Name`) VALUES ('19d53fbdf8a342a4a68dd5a907fa6ed0','','Goverment');
INSERT INTO `Contact Types` (`GUID`, `Is Allowed`, `Display Name`) VALUES ('4134430d7bb64a97b95f7862bea83644','','Business');
INSERT INTO `Contact Types` (`GUID`, `Is Allowed`, `Display Name`) VALUES ('77c1c5bad818403f8ae836a3fc7fd84b','','Individual');
INSERT INTO `Contact Types` (`GUID`, `Is Allowed`, `Display Name`) VALUES ('7c85de861e784b9f8dcb4a456e267869','','Employee');
INSERT INTO `Contact Types` (`GUID`, `Is Allowed`, `Display Name`) VALUES ('de87079fa05f4d4fab68bdd63731bc37','','Non-Profit');
/*!40000 ALTER TABLE `Contact Types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contacts`
--

DROP TABLE IF EXISTS `Contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contacts` (
  `GUID` char(32) NOT NULL,
  `Parent Contacts GUID` char(32) DEFAULT NULL,
  `Display Name` varchar(128) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  `Contact Since` date NOT NULL,
  `Default Shipping Contacts GUID` char(32) DEFAULT NULL,
  `Website` varchar(256) DEFAULT NULL,
  `Email Address` varchar(256) DEFAULT NULL,
  `Office Phone` varchar(32) DEFAULT NULL,
  `Mobile Phone` varchar(32) DEFAULT NULL,
  `Home Phone` varchar(32) DEFAULT NULL,
  `Fax Number` varchar(32) DEFAULT NULL,
  `Line 1` varchar(128) DEFAULT NULL,
  `Line 2` varchar(128) DEFAULT NULL,
  `Country` varchar(3) DEFAULT NULL,
  `City` varchar(64) DEFAULT NULL,
  `State` varchar(32) DEFAULT NULL,
  `Postal Code` varchar(16) DEFAULT NULL,
  `Tax Group GUID` char(32) NOT NULL,
  `Contact Types GUID` char(32) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Display Name` (`Display Name`),
  UNIQUE KEY `Contacts Unique Display Name` (`Display Name`,`Parent Contacts GUID`),
  KEY `Parent Contact<Children Contacts` (`Parent Contacts GUID`),
  KEY `Default Shipping Contact<Shipping Parent Contact` (`Default Shipping Contacts GUID`),
  KEY `Contacts>Contact Type` (`Contact Types GUID`),
  CONSTRAINT `Contacts>Contact Type` FOREIGN KEY (`Contact Types GUID`) REFERENCES `Contact Types` (`GUID`),
  CONSTRAINT `Default Shipping Contact<Shipping Parent Contact` FOREIGN KEY (`Default Shipping Contacts GUID`) REFERENCES `Contacts` (`GUID`),
  CONSTRAINT `Parent Contact<Children Contacts` FOREIGN KEY (`Parent Contacts GUID`) REFERENCES `Contacts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contacts`
--

LOCK TABLES `Contacts` WRITE;
/*!40000 ALTER TABLE `Contacts` DISABLE KEYS */;
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('0743e84160e64b258028c35c9ced9a29','f734dd0165d64ceb8fca8c840fb8f6bc','Anne W Davis','','2017-02-21',NULL,NULL,'anne@lauraashley.com',NULL,'906-392-8173',NULL,NULL,'1845 Bird Spring Lane',NULL,NULL,'Kemah','TX','77565','6ab15fc300964e84bb5722553ad8276f','77c1c5bad818403f8ae836a3fc7fd84b');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('25f92d984d89402aac7829d36109ea7c',NULL,'Tower Records','','2016-10-21',NULL,'www.towerrecords.com',NULL,'239-398-9916',NULL,NULL,NULL,'1258 Sunburst Drive','Suite #402',NULL,'Fort Myers','FL','33901','502fd04bc5da462f98d013dfa50d808e','4134430d7bb64a97b95f7862bea83644');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('55986b697b0d40a4a9980405972eafb0','6ac66b8e0a7f47b38c673ad2ac9639c2','Susam NcNew','','2017-03-07',NULL,NULL,NULL,NULL,NULL,'281-653-6966',NULL,'4542 Kempwood Ave',NULL,NULL,'Houston','TX','77083','6ab15fc300964e84bb5722553ad8276f','77c1c5bad818403f8ae836a3fc7fd84b');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('6ac66b8e0a7f47b38c673ad2ac9639c2','fabca02484aa46eaa24c939c64779a2d','Jason McNew','','2017-01-01',NULL,NULL,'jmcnew495@hotmail.com',NULL,NULL,'281-659-6585',NULL,'4542 Kempwood Ave',NULL,NULL,'Houston','TX','77083','6ab15fc300964e84bb5722553ad8276f','7c85de861e784b9f8dcb4a456e267869');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('6c0c4a2d443a420aac26f3097315d9d4',NULL,'MicroPC Direct','','2017-02-21',NULL,'www.micropcdirect.com',NULL,'713-569-8532',NULL,NULL,NULL,'1942 Houston Ave #2495',NULL,NULL,'Houston','TX','75698','502fd04bc5da462f98d013dfa50d808e','4134430d7bb64a97b95f7862bea83644');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('706cd3bb6d82431c90ede0b7495e87f5',NULL,'Kinney Shoes','','2017-02-21',NULL,NULL,'sales@kinneyshoes.com','440-876-4047',NULL,NULL,NULL,'3484 Flynn Street',NULL,NULL,'Cleveland','OH','44115','502fd04bc5da462f98d013dfa50d808e','4134430d7bb64a97b95f7862bea83644');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('846458ad6b3a46feb4cd92534ea67610',NULL,'Maloley\'s Finer Foods','','2014-03-09',NULL,NULL,NULL,'702-833-9450',NULL,NULL,NULL,'3770 Hall Street #2A',NULL,NULL,'Las Vegas','NV','89101','502fd04bc5da462f98d013dfa50d808e','4134430d7bb64a97b95f7862bea83644');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('849a8f09a95b42efa0acc667e197538c',NULL,'The Flying Bear','','2015-08-06',NULL,'www.flyingbear.com',NULL,'607-569-6398',NULL,NULL,'607-460-1907','4838 Frosty Lane',NULL,NULL,'Hammondsport','NY','14840','502fd04bc5da462f98d013dfa50d808e','4134430d7bb64a97b95f7862bea83644');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('8f38aeae078e4b599c78f245877e9974','846458ad6b3a46feb4cd92534ea67610','Ramon McCabe','','2014-03-11',NULL,NULL,'rmccabe@gmail.com',NULL,NULL,NULL,NULL,'3770 Hall Street #2A',NULL,NULL,'Las Vegas','NV','89101','502fd04bc5da462f98d013dfa50d808e','77c1c5bad818403f8ae836a3fc7fd84b');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('8fdffeec023746889c2268eb322c7b84','25f92d984d89402aac7829d36109ea7c','Jacob Solomon','','2016-10-21',NULL,NULL,'jacob@towerrecords.com',NULL,'702-460-1907',NULL,NULL,'1258 Sunburst Drive','Suite #402',NULL,'Fort Myers','FL','33901','502fd04bc5da462f98d013dfa50d808e','77c1c5bad818403f8ae836a3fc7fd84b');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('d53bf653fc1649ffa0716d54986aa626','849a8f09a95b42efa0acc667e197538c','Regina Marcy','','2015-08-06',NULL,NULL,'regina@flyingbear.com',NULL,'239-398-9916',NULL,NULL,'4838 Frosty Lane',NULL,NULL,'Hammondsport','NY','14840','502fd04bc5da462f98d013dfa50d808e','77c1c5bad818403f8ae836a3fc7fd84b');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('e9bb39955da94941bb1793d559df89ad','849a8f09a95b42efa0acc667e197538c','Benjamin King','','2015-09-02',NULL,NULL,'ben@flyingbear.com',NULL,'989-833-9450',NULL,NULL,'4838 Frosty Lane',NULL,NULL,'Hammondsport','NY','14840','502fd04bc5da462f98d013dfa50d808e','77c1c5bad818403f8ae836a3fc7fd84b');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('f734dd0165d64ceb8fca8c840fb8f6bc',NULL,'Laura Ashley Mother & Child','','2017-02-21',NULL,'www.lauraashley.com',NULL,'281-524-2814',NULL,NULL,NULL,'1845 Bird Spring Lane',NULL,NULL,'Kemah','TX','77565','6ab15fc300964e84bb5722553ad8276f','4134430d7bb64a97b95f7862bea83644');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('fabca02484aa46eaa24c939c64779a2d',NULL,'Thoughtstorm LLC','','2007-07-15',NULL,'www.thoughtstorm.com',NULL,'409-727-4666',NULL,NULL,'562-271-9154','2481 Burwell Heights Road',NULL,NULL,'Nederland','TX','77627','502fd04bc5da462f98d013dfa50d808e','4134430d7bb64a97b95f7862bea83644');
INSERT INTO `Contacts` (`GUID`, `Parent Contacts GUID`, `Display Name`, `Is Allowed`, `Contact Since`, `Default Shipping Contacts GUID`, `Website`, `Email Address`, `Office Phone`, `Mobile Phone`, `Home Phone`, `Fax Number`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Tax Group GUID`, `Contact Types GUID`) VALUES ('fb5108cb45414e01bea8fc968c2f6556',NULL,'Texas Comptroller of Public Accounts','','2017-02-21',NULL,'www.sos.texas.us',NULL,'512-359-2942',NULL,NULL,NULL,'1945 Liberty Chains',NULL,NULL,'Austin','TX','76953','502fd04bc5da462f98d013dfa50d808e','19d53fbdf8a342a4a68dd5a907fa6ed0');
/*!40000 ALTER TABLE `Contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Conversions`
--

DROP TABLE IF EXISTS `Conversions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Conversions` (
  `GUID` char(32) NOT NULL,
  `Left Unit Measures GUID` char(32) NOT NULL,
  `Left Quantity` decimal(64,16) NOT NULL,
  `Right Unit Measures GUID` char(32) NOT NULL,
  `Right Quantity` decimal(64,16) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Unique Conversion` (`Left Unit Measures GUID`,`Right Unit Measures GUID`),
  KEY `Right Conversions>Right Unit Measure` (`Right Unit Measures GUID`),
  CONSTRAINT `Left Conversions>Left Unit Measure` FOREIGN KEY (`Left Unit Measures GUID`) REFERENCES `Unit Measures` (`GUID`),
  CONSTRAINT `Right Conversions>Right Unit Measure` FOREIGN KEY (`Right Unit Measures GUID`) REFERENCES `Unit Measures` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Conversions`
--

LOCK TABLES `Conversions` WRITE;
/*!40000 ALTER TABLE `Conversions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Conversions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Departments`
--

DROP TABLE IF EXISTS `Departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Departments` (
  `GUID` char(32) NOT NULL,
  `Parent Departments GUID` char(32) DEFAULT NULL,
  `Segment` varchar(16) NOT NULL,
  `Number` varchar(64) NOT NULL,
  `Name` varchar(64) NOT NULL,
  `Nested Name` varchar(1024) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Number` (`Number`),
  UNIQUE KEY `Departments Unique Segment` (`Parent Departments GUID`,`Segment`),
  UNIQUE KEY `Departments Unique Name` (`Parent Departments GUID`,`Name`),
  CONSTRAINT `Children>Parent Department` FOREIGN KEY (`Parent Departments GUID`) REFERENCES `Departments` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Departments`
--

LOCK TABLES `Departments` WRITE;
/*!40000 ALTER TABLE `Departments` DISABLE KEYS */;
INSERT INTO `Departments` (`GUID`, `Parent Departments GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('026bfce95b904eea905024afa5b7538b',NULL,'ADM','ADM','Administration','Administration','');
INSERT INTO `Departments` (`GUID`, `Parent Departments GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('1b6efa26bf434a4388a4e2676b7b0fc6',NULL,'DEV','DEV','Development','Development','');
INSERT INTO `Departments` (`GUID`, `Parent Departments GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('46def36297d54767b1f988e858ac48e2',NULL,'SERV','SERV','Service','Service','');
INSERT INTO `Departments` (`GUID`, `Parent Departments GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('5132bda2bd8e4a43967bd01b77a9b801',NULL,'MRK','MRK','Marketing','Marketing','');
INSERT INTO `Departments` (`GUID`, `Parent Departments GUID`, `Segment`, `Number`, `Name`, `Nested Name`, `Is Allowed`) VALUES ('cd62822e5c664dbeadda558e3b4a3957',NULL,'PROD','PROD','Production','Production','');
/*!40000 ALTER TABLE `Departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Document Lines`
--

DROP TABLE IF EXISTS `Document Lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Document Lines` (
  `GUID` char(32) NOT NULL,
  `Documents GUID` char(32) NOT NULL,
  `Sort Order` int(11) NOT NULL,
  `Quantity` decimal(64,16) NOT NULL,
  `Items GUID` char(32) DEFAULT NULL,
  `Unit Measures GUID` char(32) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Unit Price` decimal(64,16) NOT NULL,
  `Extension` decimal(64,16) NOT NULL,
  `Is Taxed` bit(1) DEFAULT NULL,
  `Is Backwards Calculated` bit(1) NOT NULL,
  `Jobs GUID` char(32) DEFAULT NULL,
  `Departments GUID` char(32) DEFAULT NULL,
  `Accounts GUID` char(32) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Document Lines Unique Sort Order` (`Documents GUID`,`Sort Order`),
  KEY `Document Lines>Account` (`Accounts GUID`),
  KEY `Document Lines>Job` (`Jobs GUID`),
  KEY `Document Lines>Unit Measure` (`Unit Measures GUID`),
  KEY `Document Lines>Item` (`Items GUID`),
  KEY `Document Lines>Department` (`Departments GUID`),
  CONSTRAINT `Document Lines>Account` FOREIGN KEY (`Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Document Lines>Department` FOREIGN KEY (`Departments GUID`) REFERENCES `Departments` (`GUID`),
  CONSTRAINT `Document Lines>Document` FOREIGN KEY (`Documents GUID`) REFERENCES `Documents` (`GUID`),
  CONSTRAINT `Document Lines>Item` FOREIGN KEY (`Items GUID`) REFERENCES `Items` (`GUID`),
  CONSTRAINT `Document Lines>Job` FOREIGN KEY (`Jobs GUID`) REFERENCES `Jobs` (`GUID`),
  CONSTRAINT `Document Lines>Unit Measure` FOREIGN KEY (`Unit Measures GUID`) REFERENCES `Unit Measures` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Document Lines`
--

LOCK TABLES `Document Lines` WRITE;
/*!40000 ALTER TABLE `Document Lines` DISABLE KEYS */;
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('02a45d7526544edc8f82336e979c5121','f6825a41c0894681a6e68f348ebe1203',2,1.0000000000000000,'5e67cf7ffe38469fbd0b1d3393900b31','aa3bd32ce48d4fddbdb40c42fdbcb551','Intel i5-6400 6MB\r\n4 x 2.7Ghz @ 65W\r\nIntel HD Graphics 530',237.8900000000000000,237.8900000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('03a5dcc3e831489e81f5668a9c896513','f6825a41c0894681a6e68f348ebe1203',6,1.0000000000000000,'06160567b0684844998831f5dbdd1803','aa3bd32ce48d4fddbdb40c42fdbcb551','4 Drive Hot Swap Cage\r\n3 x 5.25\" to 4 x 3.5\"',64.9900000000000000,64.9900000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('0651e5fcfd4841a291455b2b3245fa44','ca7b4a58c79c452da1d023404db62f97',6,30.0000000000000000,'d52b902c47384b73944d22a1456202be','aa3bd32ce48d4fddbdb40c42fdbcb551','SanDisk 480G SSD',125.0000000000000000,3750.0000000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('14ef0dd6ff3946808da8cf60f31ecc3e','327d0128b64d4750a75edf413d918a16',1,1.0000000000000000,NULL,NULL,'Labor',114.0000000000000000,114.0000000000000000,'\0','\0',NULL,NULL,'f18d72cbb8f842fd9194eeb2f0a6b189');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('35f72449ef974aba81256b2853c16596','6e1701097c104c049bed06a1357807d5',3,5.0000000000000000,'02a59f5d9e7a4f54862e751b4287a8d9','aa3bd32ce48d4fddbdb40c42fdbcb551','Black MicoATX Case\r\n2x USB Front, 3 x 5.25\" External',30.9900000000000000,154.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('4f82f76888c64d4c85daa227a91573c9','f6825a41c0894681a6e68f348ebe1203',5,4.0000000000000000,'8223006dfa69442a883ab878b511b5db','aa3bd32ce48d4fddbdb40c42fdbcb551','1TB SATA Hard Drive\r\n7200 RPM - 6Gbps',62.3900000000000000,249.5600000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('5c8123c88ee04bbc803497cd2c6bd4dd','6e1701097c104c049bed06a1357807d5',2,5.0000000000000000,'946c56a5d27240fca80c7e48f0a3ef1e','aa3bd32ce48d4fddbdb40c42fdbcb551','Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630',314.9900000000000000,1574.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('62f59a462e6040d28a6440edd414daf0','f6825a41c0894681a6e68f348ebe1203',7,1.0000000000000000,'be4cc5e8a4be465d94e2dcb776bcbdad','aa3bd32ce48d4fddbdb40c42fdbcb551','SATA Cable',0.7400000000000000,0.7400000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('7760956ca6cb43828d73f305474791f1','6e1701097c104c049bed06a1357807d5',7,30.0000000000000000,'be4cc5e8a4be465d94e2dcb776bcbdad','aa3bd32ce48d4fddbdb40c42fdbcb551','SATA Cable',0.5700000000000000,17.1000000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('7b3c6883c6b547c0a9b2a88d4bb371cd','ca7b4a58c79c452da1d023404db62f97',3,5.0000000000000000,'02a59f5d9e7a4f54862e751b4287a8d9','aa3bd32ce48d4fddbdb40c42fdbcb551','Black MicoATX Case\r\n2x USB Front, 3 x 5.25\" External',30.9900000000000000,154.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('8bb9e2c883de4f38828f141534af7c32','327d0128b64d4750a75edf413d918a16',0,1.0000000000000000,'ff363675d6144033924c4b32f408f451','aa3bd32ce48d4fddbdb40c42fdbcb551','450+ Watt ATX Power Supply\r\n4 x SATA Powers',29.8900000000000000,29.8900000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('8e49361f080d4740a979a40ed800f59b','6e1701097c104c049bed06a1357807d5',5,5.0000000000000000,'22611b93221c4516a8153de36503aec7','aa3bd32ce48d4fddbdb40c42fdbcb551','Icy Dock MB326SP-B\r\n1 x 5.25\" to 6 x 2.5\"\r\nSATA Hot-Swap',53.9900000000000000,269.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('9104d157455e441fa630a843edb0c756','f6825a41c0894681a6e68f348ebe1203',1,1.0000000000000000,'e5b5aa8c6c5c4cfea04e1e4d57511dc7','aa3bd32ce48d4fddbdb40c42fdbcb551','2 x 4GB DDR4 Memory',72.1400000000000000,72.1400000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('938f50ff05134044acce366dfd5d348d','ca7b4a58c79c452da1d023404db62f97',7,30.0000000000000000,'be4cc5e8a4be465d94e2dcb776bcbdad','aa3bd32ce48d4fddbdb40c42fdbcb551','SATA Cable',0.5700000000000000,17.1000000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('a78dbf36cad645bcb6f03d49fb556ded','1193f11925d94a85a702f03567f63091',0,2.0000000000000000,'946c56a5d27240fca80c7e48f0a3ef1e','aa3bd32ce48d4fddbdb40c42fdbcb551','Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630',409.4900000000000000,818.9800000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('b17f80507de141f0964e012b1d492fa5','f6825a41c0894681a6e68f348ebe1203',4,1.0000000000000000,'1a51d2cca32f4f7cbe556ed4bb87184c','aa3bd32ce48d4fddbdb40c42fdbcb551','Asus H110M-E/M.2, MicroATX, LGA1151, 4 x SATA 6Gbps, 1 x PCIx16, 2 x PCIx1, 2 x DDR4 288 pin, Sound, LAN, DVI, HDMI, 6 x USB',58.4900000000000000,58.4900000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('bdd513d1b6de48459e0f6f83c401a8cd','6e1701097c104c049bed06a1357807d5',6,30.0000000000000000,'d52b902c47384b73944d22a1456202be','aa3bd32ce48d4fddbdb40c42fdbcb551','SanDisk 480G SSD',125.0000000000000000,3750.0000000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('c0d0abfd8d3740dcb90b365530f714fe','ca7b4a58c79c452da1d023404db62f97',2,5.0000000000000000,'946c56a5d27240fca80c7e48f0a3ef1e','aa3bd32ce48d4fddbdb40c42fdbcb551','Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630',314.9900000000000000,1574.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('c53ab85143a54052b7f4bc46c7bdda3b','ca7b4a58c79c452da1d023404db62f97',4,5.0000000000000000,'e59c63948bb3434eb9f191a879800e8a','aa3bd32ce48d4fddbdb40c42fdbcb551','ASRock B150M-HDV, MicroATX, LGA1151, 2 x DDR4 288 pin, 1 x PCIx16, 2 x PCIx1, 6 x SATA 6Gbps, Sound, VGA, DVI, HDMI, 6 x USB',59.9900000000000000,299.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('cd59e1c33ea84cea8cd7df1a415c8f98','6e1701097c104c049bed06a1357807d5',1,5.0000000000000000,'2f9dbf9d07fd49248ed6a5003bb715fd','aa3bd32ce48d4fddbdb40c42fdbcb551','2 x 16GB DDR4',208.9900000000000000,1044.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('e0df0cd58c9447ecaeddd4f218e38285','f6825a41c0894681a6e68f348ebe1203',0,1.0000000000000000,'ff363675d6144033924c4b32f408f451','aa3bd32ce48d4fddbdb40c42fdbcb551','450+ Watt ATX Power Supply\r\n4 x SATA Powers',29.8900000000000000,29.8900000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('ea9100ef004a40d0aae31689079919d6','ca7b4a58c79c452da1d023404db62f97',5,5.0000000000000000,'22611b93221c4516a8153de36503aec7','aa3bd32ce48d4fddbdb40c42fdbcb551','Icy Dock MB326SP-B\r\n1 x 5.25\" to 6 x 2.5\"\r\nSATA Hot-Swap',53.9900000000000000,269.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('f1755ebfe68f4771aa394b1e5107e252','ca7b4a58c79c452da1d023404db62f97',0,5.0000000000000000,'ff363675d6144033924c4b32f408f451','aa3bd32ce48d4fddbdb40c42fdbcb551','450+ Watt ATX Power Supply\r\n4 x SATA Powers',22.9900000000000000,114.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('f6f602699339424ca5b37149007c40a2','6e1701097c104c049bed06a1357807d5',0,5.0000000000000000,'ff363675d6144033924c4b32f408f451','aa3bd32ce48d4fddbdb40c42fdbcb551','450+ Watt ATX Power Supply\r\n4 x SATA Powers',22.9900000000000000,114.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('f84323ee375a4a97b3e997d7e4a8bec6','6e1701097c104c049bed06a1357807d5',4,5.0000000000000000,'e59c63948bb3434eb9f191a879800e8a','aa3bd32ce48d4fddbdb40c42fdbcb551','ASRock B150M-HDV, MicroATX, LGA1151, 2 x DDR4 288 pin, 1 x PCIx16, 2 x PCIx1, 6 x SATA 6Gbps, Sound, VGA, DVI, HDMI, 6 x USB',59.9900000000000000,299.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('f8e7a236fcf84a71bd8a8d7d3db96f7d','f6825a41c0894681a6e68f348ebe1203',3,1.0000000000000000,'02a59f5d9e7a4f54862e751b4287a8d9','aa3bd32ce48d4fddbdb40c42fdbcb551','Black MicoATX Case\r\n2x USB Front, 3 x 5.25\" External',40.2900000000000000,40.2900000000000000,'','\0',NULL,NULL,'a94bb67059ca4cf7801470b485d5f361');
INSERT INTO `Document Lines` (`GUID`, `Documents GUID`, `Sort Order`, `Quantity`, `Items GUID`, `Unit Measures GUID`, `Description`, `Unit Price`, `Extension`, `Is Taxed`, `Is Backwards Calculated`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`) VALUES ('fc169d69f08a477cb1108cc7a7868447','ca7b4a58c79c452da1d023404db62f97',1,5.0000000000000000,'2f9dbf9d07fd49248ed6a5003bb715fd','aa3bd32ce48d4fddbdb40c42fdbcb551','2 x 16GB DDR4',208.9900000000000000,1044.9500000000000000,'','\0',NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626');
/*!40000 ALTER TABLE `Document Lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Document Types`
--

DROP TABLE IF EXISTS `Document Types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Document Types` (
  `GUID` char(32) NOT NULL,
  `Is Sales Related` bit(1) NOT NULL,
  `Is Credit Memo` bit(1) NOT NULL,
  `Is Posting` bit(1) NOT NULL,
  `Accounts GUID` char(32) DEFAULT NULL,
  PRIMARY KEY (`GUID`),
  KEY `Document Types>Account` (`Accounts GUID`),
  CONSTRAINT `Document Type>Transaction Type` FOREIGN KEY (`GUID`) REFERENCES `Transaction Types` (`GUID`),
  CONSTRAINT `Document Types>Account` FOREIGN KEY (`Accounts GUID`) REFERENCES `Accounts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Document Types`
--

LOCK TABLES `Document Types` WRITE;
/*!40000 ALTER TABLE `Document Types` DISABLE KEYS */;
INSERT INTO `Document Types` (`GUID`, `Is Sales Related`, `Is Credit Memo`, `Is Posting`, `Accounts GUID`) VALUES ('276db4afcf634b6fbc4a5821c9858ab9','','\0','\0',NULL);
INSERT INTO `Document Types` (`GUID`, `Is Sales Related`, `Is Credit Memo`, `Is Posting`, `Accounts GUID`) VALUES ('5f756fc5f7c5493ca0d86f2d0ead2fda','','\0','\0',NULL);
INSERT INTO `Document Types` (`GUID`, `Is Sales Related`, `Is Credit Memo`, `Is Posting`, `Accounts GUID`) VALUES ('6632ec00f5824aeca4a49bf21cbdaece','\0','','','9ef18c0e6aa246c283bd812aa0ff405c');
INSERT INTO `Document Types` (`GUID`, `Is Sales Related`, `Is Credit Memo`, `Is Posting`, `Accounts GUID`) VALUES ('81e2917ac5c34d1cb6f9d168cd0439db','','\0','','6e44f65d6ed848a2bb2003820f3b39ef');
INSERT INTO `Document Types` (`GUID`, `Is Sales Related`, `Is Credit Memo`, `Is Posting`, `Accounts GUID`) VALUES ('86af180c412f40c5a660678e3895694b','','','','6e44f65d6ed848a2bb2003820f3b39ef');
INSERT INTO `Document Types` (`GUID`, `Is Sales Related`, `Is Credit Memo`, `Is Posting`, `Accounts GUID`) VALUES ('9d3821afd6fb47f9b2713d3cc574ceff','\0','\0','','9ef18c0e6aa246c283bd812aa0ff405c');
INSERT INTO `Document Types` (`GUID`, `Is Sales Related`, `Is Credit Memo`, `Is Posting`, `Accounts GUID`) VALUES ('dedf79eddf7c4e348918d42e25b53309','\0','\0','\0',NULL);
INSERT INTO `Document Types` (`GUID`, `Is Sales Related`, `Is Credit Memo`, `Is Posting`, `Accounts GUID`) VALUES ('e56b2b6aa42b479890085b74b69275f3','\0','\0','\0',NULL);
/*!40000 ALTER TABLE `Document Types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Documents`
--

DROP TABLE IF EXISTS `Documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Documents` (
  `GUID` char(32) NOT NULL,
  `Document Types GUID` char(32) NOT NULL,
  `Parent Documents GUID` char(32) DEFAULT NULL,
  `Reference Number` varchar(128) DEFAULT NULL,
  `Date` date NOT NULL,
  `Due Date` date NOT NULL,
  `Posted Accounts GUID` char(32) DEFAULT NULL,
  `Posted Transactions GUID` char(32) DEFAULT NULL,
  `Contacts GUID` char(32) NOT NULL,
  `Contacts Display Name` varchar(128) NOT NULL,
  `Customer Reference` varchar(128) DEFAULT NULL,
  `Terms` varchar(128) DEFAULT NULL,
  `Shipping Method` varchar(128) DEFAULT NULL,
  `Shipping Number` varchar(128) DEFAULT NULL,
  `Billing Contacts GUID` char(32) DEFAULT NULL,
  `Billing Address Display Name` varchar(128) NOT NULL,
  `Billing Address Line 1` varchar(128) DEFAULT NULL,
  `Billing Address Line 2` varchar(128) DEFAULT NULL,
  `Billing Address City` varchar(64) DEFAULT NULL,
  `Billing Address State` varchar(32) DEFAULT NULL,
  `Billing Address Postal Code` varchar(16) DEFAULT NULL,
  `Billing Address Country` varchar(3) DEFAULT NULL,
  `Shipping Contacts GUID` char(32) DEFAULT NULL,
  `Shipping Address Display Name` varchar(128) DEFAULT NULL,
  `Shipping Address Line 1` varchar(128) DEFAULT NULL,
  `Shipping Address Line 2` varchar(128) DEFAULT NULL,
  `Shipping Address City` varchar(64) DEFAULT NULL,
  `Shipping Address State` varchar(32) DEFAULT NULL,
  `Shipping Address Postal Code` varchar(16) DEFAULT NULL,
  `Shipping Address Country` varchar(3) DEFAULT NULL,
  `Sales Taxes GUID` char(32) NOT NULL,
  `Taxable Subtotal` decimal(64,16) NOT NULL,
  `Nontaxable Subtotal` decimal(64,16) NOT NULL,
  `Tax Rate` decimal(64,16) NOT NULL,
  `Subtotal` decimal(64,16) NOT NULL,
  `Taxes` decimal(64,16) NOT NULL,
  `Total` decimal(64,16) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Documents Unique Reference Number` (`Document Types GUID`,`Reference Number`),
  KEY `Child Documents>Parent Document` (`Parent Documents GUID`),
  KEY `Documents>Contact` (`Contacts GUID`),
  KEY `Documents>Sales Tax` (`Sales Taxes GUID`),
  KEY `Billing Documents>Billing Contact` (`Billing Contacts GUID`),
  KEY `Shipping Documents>Shipping Contact` (`Shipping Contacts GUID`),
  KEY `Documents>Account` (`Posted Accounts GUID`),
  KEY `Document>Transaction` (`Posted Transactions GUID`),
  CONSTRAINT `Billing Documents>Billing Contact` FOREIGN KEY (`Billing Contacts GUID`) REFERENCES `Contacts` (`GUID`),
  CONSTRAINT `Child Documents>Parent Document` FOREIGN KEY (`Parent Documents GUID`) REFERENCES `Documents` (`GUID`),
  CONSTRAINT `Document>Transaction` FOREIGN KEY (`Posted Transactions GUID`) REFERENCES `Transactions` (`GUID`),
  CONSTRAINT `Documents>Account` FOREIGN KEY (`Posted Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Documents>Contact` FOREIGN KEY (`Contacts GUID`) REFERENCES `Contacts` (`GUID`),
  CONSTRAINT `Documents>Document Type` FOREIGN KEY (`Document Types GUID`) REFERENCES `Document Types` (`GUID`),
  CONSTRAINT `Documents>Sales Tax` FOREIGN KEY (`Sales Taxes GUID`) REFERENCES `Sales Taxes` (`GUID`),
  CONSTRAINT `Shipping Documents>Shipping Contact` FOREIGN KEY (`Shipping Contacts GUID`) REFERENCES `Contacts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Documents`
--

LOCK TABLES `Documents` WRITE;
/*!40000 ALTER TABLE `Documents` DISABLE KEYS */;
INSERT INTO `Documents` (`GUID`, `Document Types GUID`, `Parent Documents GUID`, `Reference Number`, `Date`, `Due Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Contacts GUID`, `Contacts Display Name`, `Customer Reference`, `Terms`, `Shipping Method`, `Shipping Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Shipping Contacts GUID`, `Shipping Address Display Name`, `Shipping Address Line 1`, `Shipping Address Line 2`, `Shipping Address City`, `Shipping Address State`, `Shipping Address Postal Code`, `Shipping Address Country`, `Sales Taxes GUID`, `Taxable Subtotal`, `Nontaxable Subtotal`, `Tax Rate`, `Subtotal`, `Taxes`, `Total`) VALUES ('1193f11925d94a85a702f03567f63091','81e2917ac5c34d1cb6f9d168cd0439db',NULL,'1002','2017-01-11','2017-01-11','6e44f65d6ed848a2bb2003820f3b39ef','1193f11925d94a85a702f03567f63091','fabca02484aa46eaa24c939c64779a2d','Thoughtstorm LLC',NULL,'COD',NULL,NULL,'fabca02484aa46eaa24c939c64779a2d','Thoughtstorm LLC','2481 Burwell Heights Road',NULL,'Nederland','TX','77627',NULL,'fabca02484aa46eaa24c939c64779a2d','Thoughtstorm LLC','2481 Burwell Heights Road',NULL,'Nederland','TX','77627',NULL,'6ab15fc300964e84bb5722553ad8276f',818.9800000000000000,0.0000000000000000,0.0825000000000000,818.9800000000000000,67.5700000000000000,886.5500000000000000);
INSERT INTO `Documents` (`GUID`, `Document Types GUID`, `Parent Documents GUID`, `Reference Number`, `Date`, `Due Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Contacts GUID`, `Contacts Display Name`, `Customer Reference`, `Terms`, `Shipping Method`, `Shipping Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Shipping Contacts GUID`, `Shipping Address Display Name`, `Shipping Address Line 1`, `Shipping Address Line 2`, `Shipping Address City`, `Shipping Address State`, `Shipping Address Postal Code`, `Shipping Address Country`, `Sales Taxes GUID`, `Taxable Subtotal`, `Nontaxable Subtotal`, `Tax Rate`, `Subtotal`, `Taxes`, `Total`) VALUES ('327d0128b64d4750a75edf413d918a16','81e2917ac5c34d1cb6f9d168cd0439db',NULL,'1001','2017-02-26','2017-02-26','6e44f65d6ed848a2bb2003820f3b39ef','327d0128b64d4750a75edf413d918a16','706cd3bb6d82431c90ede0b7495e87f5','Kinney Shoes',NULL,'COD',NULL,NULL,'706cd3bb6d82431c90ede0b7495e87f5','Kinney Shoes','3484 Flynn Street',NULL,'Cleveland','OH','44115',NULL,'706cd3bb6d82431c90ede0b7495e87f5','Kinney Shoes','3484 Flynn Street',NULL,'Cleveland','OH','44115',NULL,'6ab15fc300964e84bb5722553ad8276f',29.8900000000000000,114.0000000000000000,0.0825000000000000,143.8900000000000000,2.4700000000000000,146.3600000000000000);
INSERT INTO `Documents` (`GUID`, `Document Types GUID`, `Parent Documents GUID`, `Reference Number`, `Date`, `Due Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Contacts GUID`, `Contacts Display Name`, `Customer Reference`, `Terms`, `Shipping Method`, `Shipping Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Shipping Contacts GUID`, `Shipping Address Display Name`, `Shipping Address Line 1`, `Shipping Address Line 2`, `Shipping Address City`, `Shipping Address State`, `Shipping Address Postal Code`, `Shipping Address Country`, `Sales Taxes GUID`, `Taxable Subtotal`, `Nontaxable Subtotal`, `Tax Rate`, `Subtotal`, `Taxes`, `Total`) VALUES ('6e1701097c104c049bed06a1357807d5','9d3821afd6fb47f9b2713d3cc574ceff','ca7b4a58c79c452da1d023404db62f97','1001','2017-01-05','2017-01-05','9ef18c0e6aa246c283bd812aa0ff405c','6e1701097c104c049bed06a1357807d5','6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct',NULL,'COD',NULL,NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct','1942 Houston Ave #2495',NULL,'Houston','TX','75698',NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct','1942 Houston Ave #2495',NULL,'Houston','TX','75698',NULL,'502fd04bc5da462f98d013dfa50d808e',7226.8000000000000000,0.0000000000000000,0.0000000000000000,7226.8000000000000000,0.0000000000000000,7226.8000000000000000);
INSERT INTO `Documents` (`GUID`, `Document Types GUID`, `Parent Documents GUID`, `Reference Number`, `Date`, `Due Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Contacts GUID`, `Contacts Display Name`, `Customer Reference`, `Terms`, `Shipping Method`, `Shipping Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Shipping Contacts GUID`, `Shipping Address Display Name`, `Shipping Address Line 1`, `Shipping Address Line 2`, `Shipping Address City`, `Shipping Address State`, `Shipping Address Postal Code`, `Shipping Address Country`, `Sales Taxes GUID`, `Taxable Subtotal`, `Nontaxable Subtotal`, `Tax Rate`, `Subtotal`, `Taxes`, `Total`) VALUES ('ca7b4a58c79c452da1d023404db62f97','e56b2b6aa42b479890085b74b69275f3',NULL,'1002','2017-02-22','2017-02-22',NULL,NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct',NULL,'COD',NULL,NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct','1942 Houston Ave #2495',NULL,'Houston','TX','75698',NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct','1942 Houston Ave #2495',NULL,'Houston','TX','75698',NULL,'502fd04bc5da462f98d013dfa50d808e',7226.8000000000000000,0.0000000000000000,0.0000000000000000,7226.8000000000000000,0.0000000000000000,7226.8000000000000000);
INSERT INTO `Documents` (`GUID`, `Document Types GUID`, `Parent Documents GUID`, `Reference Number`, `Date`, `Due Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Contacts GUID`, `Contacts Display Name`, `Customer Reference`, `Terms`, `Shipping Method`, `Shipping Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Shipping Contacts GUID`, `Shipping Address Display Name`, `Shipping Address Line 1`, `Shipping Address Line 2`, `Shipping Address City`, `Shipping Address State`, `Shipping Address Postal Code`, `Shipping Address Country`, `Sales Taxes GUID`, `Taxable Subtotal`, `Nontaxable Subtotal`, `Tax Rate`, `Subtotal`, `Taxes`, `Total`) VALUES ('f6825a41c0894681a6e68f348ebe1203','276db4afcf634b6fbc4a5821c9858ab9',NULL,'1001','2017-02-22','2017-02-22',NULL,NULL,'706cd3bb6d82431c90ede0b7495e87f5','Kinney Shoes',NULL,'COD',NULL,NULL,'706cd3bb6d82431c90ede0b7495e87f5','Kinney Shoes','3484 Flynn Street',NULL,'Cleveland','OH','44115',NULL,'706cd3bb6d82431c90ede0b7495e87f5','Kinney Shoes','3484 Flynn Street',NULL,'Cleveland','OH','44115',NULL,'502fd04bc5da462f98d013dfa50d808e',753.9900000000000000,0.0000000000000000,0.0000000000000000,753.9900000000000000,0.0000000000000000,753.9900000000000000);
/*!40000 ALTER TABLE `Documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employees`
--

DROP TABLE IF EXISTS `Employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Employees` (
  `GUID` char(32) NOT NULL,
  `First Name` varchar(32) NOT NULL,
  `Last Name` varchar(32) NOT NULL,
  `Suffix` varchar(8) DEFAULT NULL,
  `Tax ID` varchar(16) DEFAULT NULL,
  `Date of Birth` date DEFAULT NULL,
  `Is Male` bit(1) DEFAULT NULL,
  `Ethnicity` varchar(32) DEFAULT NULL,
  `Job Title` varchar(64) DEFAULT NULL,
  `Supervisor Contacts GUID` char(32) NOT NULL,
  `Date Hirred` date DEFAULT NULL,
  `Date Terminated` date DEFAULT NULL,
  `Date Verified` date DEFAULT NULL,
  `Insurance` bit(1) NOT NULL,
  `Vacation` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  KEY `Subordinates>Supervisor` (`Supervisor Contacts GUID`),
  CONSTRAINT `Employee>Contact` FOREIGN KEY (`GUID`) REFERENCES `Contacts` (`GUID`),
  CONSTRAINT `Subordinates>Supervisor` FOREIGN KEY (`Supervisor Contacts GUID`) REFERENCES `Contacts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employees`
--

LOCK TABLES `Employees` WRITE;
/*!40000 ALTER TABLE `Employees` DISABLE KEYS */;
INSERT INTO `Employees` (`GUID`, `First Name`, `Last Name`, `Suffix`, `Tax ID`, `Date of Birth`, `Is Male`, `Ethnicity`, `Job Title`, `Supervisor Contacts GUID`, `Date Hirred`, `Date Terminated`, `Date Verified`, `Insurance`, `Vacation`) VALUES ('6ac66b8e0a7f47b38c673ad2ac9639c2','Jason','McNew',NULL,'475-95-2948','1968-07-03','\0',NULL,'Technical Leas','fabca02484aa46eaa24c939c64779a2d',NULL,NULL,NULL,'\0','\0');
/*!40000 ALTER TABLE `Employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Groups`
--

DROP TABLE IF EXISTS `Groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Groups` (
  `GUID` char(32) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  `Display Name` varchar(64) NOT NULL,
  `Email Address` varchar(64) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Display Name` (`Display Name`),
  UNIQUE KEY `Email Address` (`Email Address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Groups`
--

LOCK TABLES `Groups` WRITE;
/*!40000 ALTER TABLE `Groups` DISABLE KEYS */;
INSERT INTO `Groups` (`GUID`, `Is Allowed`, `Display Name`, `Email Address`) VALUES ('11eede08a5f34402a2547edc6aad2529','','Everyone','everyone@localhost');
INSERT INTO `Groups` (`GUID`, `Is Allowed`, `Display Name`, `Email Address`) VALUES ('f541b846c9224fc687420fce2a0ec8b1','','System Administrators','postmaster@localhost');
/*!40000 ALTER TABLE `Groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Item Postings`
--

DROP TABLE IF EXISTS `Item Postings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Item Postings` (
  `GUID` char(32) NOT NULL,
  `Increasing Document Lines GUID` char(32) NOT NULL,
  `Decreasing Document Lines GUID` char(32) NOT NULL,
  `Decrease Quantity` decimal(64,16) NOT NULL,
  PRIMARY KEY (`GUID`),
  KEY `Increasing Item Postings>Increasing Document Line` (`Increasing Document Lines GUID`),
  KEY `Decreasing Item Postings>Decreasing Document Line` (`Decreasing Document Lines GUID`),
  CONSTRAINT `Decreasing Item Postings>Decreasing Document Line` FOREIGN KEY (`Decreasing Document Lines GUID`) REFERENCES `Document Lines` (`GUID`),
  CONSTRAINT `Increasing Item Postings>Increasing Document Line` FOREIGN KEY (`Increasing Document Lines GUID`) REFERENCES `Document Lines` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Item Postings`
--

LOCK TABLES `Item Postings` WRITE;
/*!40000 ALTER TABLE `Item Postings` DISABLE KEYS */;
INSERT INTO `Item Postings` (`GUID`, `Increasing Document Lines GUID`, `Decreasing Document Lines GUID`, `Decrease Quantity`) VALUES ('4f581145d2204fc78dc8d5d18cabd63f','5c8123c88ee04bbc803497cd2c6bd4dd','a78dbf36cad645bcb6f03d49fb556ded',2.0000000000000000);
INSERT INTO `Item Postings` (`GUID`, `Increasing Document Lines GUID`, `Decreasing Document Lines GUID`, `Decrease Quantity`) VALUES ('af95da5332ab4cd29bd159da06f9d166','f6f602699339424ca5b37149007c40a2','8bb9e2c883de4f38828f141534af7c32',1.0000000000000000);
/*!40000 ALTER TABLE `Item Postings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Items`
--

DROP TABLE IF EXISTS `Items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Items` (
  `GUID` char(32) NOT NULL,
  `Parent Items GUID` char(32) DEFAULT NULL,
  `Segment` varchar(16) NOT NULL,
  `Number` varchar(64) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  `Sales Accounts GUID` char(32) DEFAULT NULL,
  `Sales Description` varchar(512) DEFAULT NULL,
  `Sales Mark Up` decimal(64,16) DEFAULT NULL,
  `Sales Unit Price` decimal(64,16) DEFAULT NULL,
  `Is Sales Taxed` bit(1) NOT NULL,
  `Purchase Accounts GUID` char(32) DEFAULT NULL,
  `Purchase Description` varchar(512) DEFAULT NULL,
  `Purchase Contacts GUID` char(32) DEFAULT NULL,
  `Last Unit Cost` decimal(64,16) DEFAULT NULL,
  `Inventory Unit Measures GUID` char(32) NOT NULL,
  `Inventory Accounts GUID` char(32) DEFAULT NULL,
  `Inventory Description` varchar(512) DEFAULT NULL,
  `Is Serialized` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Number` (`Number`),
  UNIQUE KEY `Items Unique Segment` (`Segment`,`Parent Items GUID`),
  KEY `Child Items>Parent Item` (`Parent Items GUID`),
  KEY `Sales Items>Sales Acount` (`Sales Accounts GUID`),
  KEY `Purchase Items>Purchase Acount` (`Purchase Accounts GUID`),
  KEY `Items>Purchase Contact` (`Purchase Contacts GUID`),
  KEY `Items>Inventory Unit Measure` (`Inventory Unit Measures GUID`),
  KEY `Inventory Items>Inventory Account` (`Inventory Accounts GUID`),
  CONSTRAINT `Child Items>Parent Item` FOREIGN KEY (`Parent Items GUID`) REFERENCES `Items` (`GUID`),
  CONSTRAINT `Inventory Items>Inventory Account` FOREIGN KEY (`Inventory Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Items>Inventory Unit Measure` FOREIGN KEY (`Inventory Unit Measures GUID`) REFERENCES `Unit Measures` (`GUID`),
  CONSTRAINT `Items>Purchase Contact` FOREIGN KEY (`Purchase Contacts GUID`) REFERENCES `Contacts` (`GUID`),
  CONSTRAINT `Purchase Items>Purchase Acount` FOREIGN KEY (`Purchase Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Sales Items>Sales Acount` FOREIGN KEY (`Sales Accounts GUID`) REFERENCES `Accounts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Items`
--

LOCK TABLES `Items` WRITE;
/*!40000 ALTER TABLE `Items` DISABLE KEYS */;
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('02a59f5d9e7a4f54862e751b4287a8d9','c97b0e5252c145ea8d58c9a9ef146521','-ANTEC-VSK4000E','MATX-CS-ANTEC-VSK4000E','','a94bb67059ca4cf7801470b485d5f361','Black MicoATX Case\r\n2x USB Front, 3 x 5.25\" External',1.3000000000000000,40.2900000000000000,'',NULL,'Black MicoATX Case\r\n2x USB Front, 3 x 5.25\" External',NULL,30.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Black MicoATX Case\r\n2x USB Front, 3 x 5.25\" External','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('06160567b0684844998831f5dbdd1803','787259c1f366486db348cdd54c1ec218','-3X525TO4X35-RW','SATA3-3X525TO4X35-RW','','a94bb67059ca4cf7801470b485d5f361','4 Drive Hot Swap Cage\r\n3 x 5.25\" to 4 x 3.5\"',1.3000000000000000,64.9900000000000000,'',NULL,'4 Drive Hot Swap Cage\r\n3 x 5.25\" to 4 x 3.5\"',NULL,49.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','4 Drive Hot Swap Cage\r\n3 x 5.25\" to 4 x 3.5\"','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('11150c2ddc894fb69356efb0fc7b079a','41739b22c6fb41c892625d4c195be666','-240G','SATA3-25-240G','','a94bb67059ca4cf7801470b485d5f361','Kingston 240GB SSD',1.3000000000000000,100.0900000000000000,'',NULL,'Kingston 240GB SSD',NULL,76.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Kingston 240GB SSD','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('1a51d2cca32f4f7cbe556ed4bb87184c','39762a39858c400c91d23abb10966436','-ASUS-H110M-E','MATX-MB-ASUS-H110M-E','','a94bb67059ca4cf7801470b485d5f361','Asus H110M-E/M.2, MicroATX, LGA1151, 4 x SATA 6Gbps, 1 x PCIx16, 2 x PCIx1, 2 x DDR4 288 pin, Sound, LAN, DVI, HDMI, 6 x USB',1.3000000000000000,58.4900000000000000,'',NULL,'Asus H110M-E/M.2, MicroATX, LGA1151, 4 x SATA 6Gbps, 1 x PCIx16, 2 x PCIx1, 2 x DDR4 288 pin, Sound, LAN, DVI, HDMI, 6 x USB',NULL,44.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Asus H110M-E/M.2, MicroATX, LGA1151, 4 x SATA 6Gbps, 1 x PCIx16, 2 x PCIx1, 2 x DDR4 288 pin, Sound, LAN, DVI, HDMI, 6 x USB','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('1d26f16cee4343ec9c4b259dc31a579d','ad8b8e35e44642a09bd18b6b662ca60d','-CS','MITX-CS','','a94bb67059ca4cf7801470b485d5f361','Cool Master mini-ITX Case\r\n3 x 3.5\" HDD or 4 x 2.5\" HDD\r\nATX Power Supply Needed',1.3000000000000000,51.9900000000000000,'',NULL,'Cool Master mini-ITX Case\r\n3 x 3.5\" HDD or 4 x 2.5\" HDD\r\nATX Power Supply Needed',NULL,39.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Cool Master mini-ITX Case\r\n3 x 3.5\" HDD or 4 x 2.5\" HDD\r\nATX Power Supply Needed','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('2078072a5ca24e509c85f1c180afb1ff',NULL,'DDR4','DDR4','','f7a4eaae78a149abb82f09b45fde6626',NULL,1.3000000000000000,NULL,'\0',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('22611b93221c4516a8153de36503aec7','787259c1f366486db348cdd54c1ec218','-1X525TO6X25-ID','SATA3-1X525TO6X25-ID','','a94bb67059ca4cf7801470b485d5f361','Icy Dock MB326SP-B\r\n1 x 5.25\" to 6 x 2.5\"\r\nSATA Hot-Swap',1.3000000000000000,70.1900000000000000,'',NULL,'Icy Dock MB326SP-B\r\n1 x 5.25\" to 6 x 2.5\"\r\nSATA Hot-Swap',NULL,53.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Icy Dock MB326SP-B\r\n1 x 5.25\" to 6 x 2.5\"\r\nSATA Hot-Swap','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('2a2f1d41cdbc4da88daa868964fa3592','ad8b8e35e44642a09bd18b6b662ca60d','-MB','MITX-MB','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('2ef40e50baa840f68d76c79ea3cd8ab6',NULL,'LGA1151','LGA1151','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('2f9dbf9d07fd49248ed6a5003bb715fd','304a04c342ce4956b897b30b4046a56d','-2X16G','DDR4-288-2X16G','','f7a4eaae78a149abb82f09b45fde6626','2 x 16GB DDR4',1.3000000000000000,271.6900000000000000,'\0',NULL,'2 x 16GB DDR4',NULL,208.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','2 x 16GB DDR4','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('304a04c342ce4956b897b30b4046a56d','2078072a5ca24e509c85f1c180afb1ff','-288','DDR4-288','','f7a4eaae78a149abb82f09b45fde6626',NULL,1.3000000000000000,NULL,'\0',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('39762a39858c400c91d23abb10966436','a60cf4ff930943b3b2912708188190be','-MB','MATX-MB','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('41739b22c6fb41c892625d4c195be666','787259c1f366486db348cdd54c1ec218','-25','SATA3-25','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('5e67cf7ffe38469fbd0b1d3393900b31','2ef40e50baa840f68d76c79ea3cd8ab6','-I56400','LGA1151-I56400','','a94bb67059ca4cf7801470b485d5f361','Intel i5-6400 6MB\r\n4 x 2.7Ghz @ 65W\r\nIntel HD Graphics 530',1.3000000000000000,237.8900000000000000,'',NULL,'Intel i5-6400 6MB\r\n4 x 2.7Ghz @ 65W\r\nIntel HD Graphics 530',NULL,182.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Intel i5-6400 6MB\r\n4 x 2.7Ghz @ 65W\r\nIntel HD Graphics 530','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('787259c1f366486db348cdd54c1ec218',NULL,'SATA3','SATA3','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('8223006dfa69442a883ab878b511b5db','fbf9158a271241d3a744cb91f2caa62c','-1TB','SATA3-35-1TB','','a94bb67059ca4cf7801470b485d5f361','1TB SATA Hard Drive\r\n7200 RPM - 6Gbps',1.3000000000000000,62.3900000000000000,'',NULL,'1TB SATA Hard Drive\r\n7200 RPM - 6Gbps',NULL,47.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','1TB SATA Hard Drive\r\n7200 RPM - 6Gbps','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('8b89648cb7f34df6b37b545f99390778','1d26f16cee4343ec9c4b259dc31a579d','-CME110','MITX-CS-CME110','','a94bb67059ca4cf7801470b485d5f361','Cool Master Elite 110\r\n3 x 3.5\" HDD or 4 x 2.5\" HDD\r\nATX Power Supply Required',NULL,51.9900000000000000,'',NULL,'Cool Master Elite 110\r\n3 x 3.5\" HDD or 4 x 2.5\" HDD\r\nATX Power Supply Required',NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Cool Master Elite 110\r\n3 x 3.5\" HDD or 4 x 2.5\" HDD\r\nATX Power Supply Required','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('946c56a5d27240fca80c7e48f0a3ef1e','2ef40e50baa840f68d76c79ea3cd8ab6','-I77700','LGA1151-I77700','','a94bb67059ca4cf7801470b485d5f361','Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630',1.3000000000000000,409.4900000000000000,'',NULL,'Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630',NULL,314.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('a2b63f5d3c854f27901f0b4611317eca','2a2f1d41cdbc4da88daa868964fa3592','-ASR-H110M-ITX','MITX-MB-ASR-H110M-ITX','','a94bb67059ca4cf7801470b485d5f361','ASRock H110M-ITX\r\n2 x 288 pin DDR4 <= 32GB\r\n4 x SATA3\r\nSound, DVI, HDMI, LAN, 6 x USB, 1 x PCIx16',1.3000000000000000,77.9900000000000000,'',NULL,'ASRock H110M-ITX\r\n2 x 288 pin DDR4 <= 32GB\r\n4 x SATA3\r\nSound, DVI, HDMI, LAN, 6 x USB, 1 x PCIx16',NULL,59.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','ASRock H110M-ITX\r\n2 x 288 pin DDR4 <= 32GB\r\n4 x SATA3\r\nSound, DVI, HDMI, LAN, 6 x USB, 1 x PCIx16','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('a60cf4ff930943b3b2912708188190be',NULL,'MATX','MATX','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('aca3b230bb2347cf8f789206290b6155','cc38818b31774aacb514f79ddae42eb6','-PS','ATX-PS','','a94bb67059ca4cf7801470b485d5f361',NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('ad8b8e35e44642a09bd18b6b662ca60d',NULL,'MITX','MITX','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('be4cc5e8a4be465d94e2dcb776bcbdad','787259c1f366486db348cdd54c1ec218','-CABLE','SATA3-CABLE','','a94bb67059ca4cf7801470b485d5f361','SATA Cable',1.3000000000000000,0.7400000000000000,'',NULL,'SATA Cable',NULL,0.5700000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','SATA Cable','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('c2318701ec3749a99fc2098a07db0124','fbf9158a271241d3a744cb91f2caa62c','-2TB','SATA3-35-2TB','','a94bb67059ca4cf7801470b485d5f361','2TB SATA Hard Drive\r\n7200 RPM - 6Gbps',1.3000000000000000,90.9900000000000000,'',NULL,'2TB SATA Hard Drive\r\n7200 RPM - 6Gbps',NULL,69.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','2TB SATA Hard Drive\r\n7200 RPM - 6Gbps','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('c97b0e5252c145ea8d58c9a9ef146521','a60cf4ff930943b3b2912708188190be','-CS','MATX-CS','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('cc38818b31774aacb514f79ddae42eb6',NULL,'ATX','ATX','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,0.0000000000000000,'',NULL,NULL,NULL,0.0000000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('d27f930647c9465cbfa1d61d9cbd2775','787259c1f366486db348cdd54c1ec218','-3X525TO4X35-ST','SATA3-3X525TO4X35-ST','','a94bb67059ca4cf7801470b485d5f361','StarTech 3 x 5.25\" to 4 x 3.5\" \r\nSATA Hot Swap; 4 Cables',1.3000000000000000,111.7900000000000000,'',NULL,'StarTech 3 x 5.25\" to 4 x 3.5\" \r\nSATA Hot Swap; 4 Cables',NULL,85.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','StarTech 3 x 5.25\" to 4 x 3.5\" \r\nSATA Hot Swap; 4 Cables','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('d52b902c47384b73944d22a1456202be','41739b22c6fb41c892625d4c195be666','-480G','SATA3-25-480G','','a94bb67059ca4cf7801470b485d5f361','SanDisk 480G SSD',1.3000000000000000,162.5000000000000000,'',NULL,'SanDisk 480G SSD',NULL,125.0000000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','SanDisk 480G SSD','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('e59c63948bb3434eb9f191a879800e8a','a60cf4ff930943b3b2912708188190be','-MB-ASR-B150MHDV','MATX-MB-ASR-B150MHDV','','a94bb67059ca4cf7801470b485d5f361','ASRock B150M-HDV, MicroATX, LGA1151, 2 x DDR4 288 pin, 1 x PCIx16, 2 x PCIx1, 6 x SATA 6Gbps, Sound, VGA, DVI, HDMI, 6 x USB',1.3000000000000000,77.9900000000000000,'',NULL,'ASRock B150M-HDV, MicroATX, LGA1151, 2 x DDR4 288 pin, 1 x PCIx16, 2 x PCIx1, 6 x SATA 6Gbps, Sound, VGA, DVI, HDMI, 6 x USB',NULL,59.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','ASRock B150M-HDV, MicroATX, LGA1151, 2 x DDR4 288 pin, 1 x PCIx16, 2 x PCIx1, 6 x SATA 6Gbps, Sound, VGA, DVI, HDMI, 6 x USB','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('e5b5aa8c6c5c4cfea04e1e4d57511dc7','304a04c342ce4956b897b30b4046a56d','-2X4G','DDR4-288-2X4G','','f7a4eaae78a149abb82f09b45fde6626','2 x 4GB DDR4 Memory',1.3000000000000000,72.1400000000000000,'\0',NULL,'2 x 4GB DDR4 Memory',NULL,55.4900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','2 x 4GB DDR4 Memory','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('e5c8dc3f3d3b4899941ce006783ea46d','2a2f1d41cdbc4da88daa868964fa3592','-ASR-H110M-ITX/A','MITX-MB-ASR-H110M-ITX/A','','a94bb67059ca4cf7801470b485d5f361','ASRock H110M-ITX\r\n2 x 288 pin DDR4 <= 32GB\r\n4 x SATA3\r\nSound, DVI, HDMI, LAN, 6 x USB, 1 x PCIx16, WiFi',1.3000000000000000,92.2900000000000000,'',NULL,'ASRock H110M-ITX\r\n2 x 288 pin DDR4 <= 32GB\r\n4 x SATA3\r\nSound, DVI, HDMI, LAN, 6 x USB, 1 x PCIx16, WiFi',NULL,70.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','ASRock H110M-ITX\r\n2 x 288 pin DDR4 <= 32GB\r\n4 x SATA3\r\nSound, DVI, HDMI, LAN, 6 x USB, 1 x PCIx16, WiFi','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('f14bd92569c4485db5091a44c9a604b8','304a04c342ce4956b897b30b4046a56d','-2X8G','DDR4-288-2X8G','','f7a4eaae78a149abb82f09b45fde6626','2 x 8GB DDR4',1.3000000000000000,141.6700000000000000,'\0',NULL,'2 x 8GB DDR4',NULL,108.9800000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','2 x 8GB DDR4','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('f79314f18b1e4a84851e9b0e18cbea60','2ef40e50baa840f68d76c79ea3cd8ab6','-G4600','LGA1151-G4600','','a94bb67059ca4cf7801470b485d5f361','Intel G4600 3MB\r\n2 x 3.6Ghz @ 51W\r\nIntel Graphics HD 630',1.3000000000000000,116.9900000000000000,'',NULL,'Intel G4600 3MB\r\n2 x 3.6Ghz @ 51W\r\nIntel Graphics HD 630',NULL,89.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','Intel G4600 3MB\r\n2 x 3.6Ghz @ 51W\r\nIntel Graphics HD 630','\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('fbf9158a271241d3a744cb91f2caa62c','787259c1f366486db348cdd54c1ec218','-35','SATA3-35','','a94bb67059ca4cf7801470b485d5f361',NULL,1.3000000000000000,NULL,'',NULL,NULL,NULL,NULL,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626',NULL,'\0');
INSERT INTO `Items` (`GUID`, `Parent Items GUID`, `Segment`, `Number`, `Is Allowed`, `Sales Accounts GUID`, `Sales Description`, `Sales Mark Up`, `Sales Unit Price`, `Is Sales Taxed`, `Purchase Accounts GUID`, `Purchase Description`, `Purchase Contacts GUID`, `Last Unit Cost`, `Inventory Unit Measures GUID`, `Inventory Accounts GUID`, `Inventory Description`, `Is Serialized`) VALUES ('ff363675d6144033924c4b32f408f451','aca3b230bb2347cf8f789206290b6155','-450W','ATX-PS-450W','','a94bb67059ca4cf7801470b485d5f361','450+ Watt ATX Power Supply\r\n4 x SATA Powers',1.3000000000000000,29.8900000000000000,'',NULL,'450+ Watt ATX Power Supply\r\n4 x SATA Powers',NULL,22.9900000000000000,'aa3bd32ce48d4fddbdb40c42fdbcb551','f7a4eaae78a149abb82f09b45fde6626','450+ Watt ATX Power Supply\r\n4 x SATA Powers','\0');
/*!40000 ALTER TABLE `Items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Jobs`
--

DROP TABLE IF EXISTS `Jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Jobs` (
  `GUID` char(32) NOT NULL,
  `Parent Jobs GUID` char(32) DEFAULT NULL,
  `Segment` varchar(16) NOT NULL,
  `Number` varchar(64) NOT NULL,
  `Name` varchar(64) NOT NULL,
  `Nested Name` varchar(1024) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  `Contacts GUID` char(32) DEFAULT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Number` (`Number`),
  UNIQUE KEY `Jobs Unique Segment` (`Parent Jobs GUID`,`Segment`),
  UNIQUE KEY `Jobs Unique Name` (`Parent Jobs GUID`,`Name`),
  KEY `Jobs>Contact` (`Contacts GUID`),
  CONSTRAINT `Children>Parent Job` FOREIGN KEY (`Parent Jobs GUID`) REFERENCES `Jobs` (`GUID`),
  CONSTRAINT `Jobs>Contact` FOREIGN KEY (`Contacts GUID`) REFERENCES `Contacts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Jobs`
--

LOCK TABLES `Jobs` WRITE;
/*!40000 ALTER TABLE `Jobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `Jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Logs`
--

DROP TABLE IF EXISTS `Logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Logs` (
  `GUID` char(32) NOT NULL,
  `Occured` datetime NOT NULL,
  `Users GUID` char(32) NOT NULL,
  `Computers GUID` char(32) NOT NULL,
  `Code GUID` char(32) NOT NULL,
  `Log` varchar(32) NOT NULL,
  `Details` text NOT NULL,
  PRIMARY KEY (`GUID`),
  KEY `User<Logs` (`Users GUID`),
  KEY `Computer<Logs` (`Computers GUID`),
  CONSTRAINT `Computer<Logs` FOREIGN KEY (`Computers GUID`) REFERENCES `Computers` (`GUID`),
  CONSTRAINT `User<Logs` FOREIGN KEY (`Users GUID`) REFERENCES `Users` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Logs`
--

LOCK TABLES `Logs` WRITE;
/*!40000 ALTER TABLE `Logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `Logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Memberships`
--

DROP TABLE IF EXISTS `Memberships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Memberships` (
  `Users GUID` char(32) NOT NULL,
  `Groups GUID` char(32) NOT NULL,
  UNIQUE KEY `Unique Membership` (`Users GUID`,`Groups GUID`),
  KEY `Group<Memberships` (`Groups GUID`),
  CONSTRAINT `Group<Memberships` FOREIGN KEY (`Groups GUID`) REFERENCES `Groups` (`GUID`),
  CONSTRAINT `User<Memberships` FOREIGN KEY (`Users GUID`) REFERENCES `Users` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Memberships`
--

LOCK TABLES `Memberships` WRITE;
/*!40000 ALTER TABLE `Memberships` DISABLE KEYS */;
INSERT INTO `Memberships` (`Users GUID`, `Groups GUID`) VALUES ('86b41969e95143c090fd93a4819c58a2','f541b846c9224fc687420fce2a0ec8b1');
/*!40000 ALTER TABLE `Memberships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payment Applications`
--

DROP TABLE IF EXISTS `Payment Applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payment Applications` (
  `Payments GUID` char(32) NOT NULL,
  `Documents GUID` char(32) NOT NULL,
  `Amount` decimal(64,16) NOT NULL,
  UNIQUE KEY `Payments Unique Document` (`Payments GUID`,`Documents GUID`),
  KEY `Payment Applications>Document` (`Documents GUID`),
  CONSTRAINT `Payment Applications>Document` FOREIGN KEY (`Documents GUID`) REFERENCES `Documents` (`GUID`),
  CONSTRAINT `Payment Applications>Payment` FOREIGN KEY (`Payments GUID`) REFERENCES `Payments` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payment Applications`
--

LOCK TABLES `Payment Applications` WRITE;
/*!40000 ALTER TABLE `Payment Applications` DISABLE KEYS */;
INSERT INTO `Payment Applications` (`Payments GUID`, `Documents GUID`, `Amount`) VALUES ('3ead7e8f5ded4883ad12104484b322af','1193f11925d94a85a702f03567f63091',443.5500000000000000);
INSERT INTO `Payment Applications` (`Payments GUID`, `Documents GUID`, `Amount`) VALUES ('609b536c31e24bff91288a9544f8fc28','6e1701097c104c049bed06a1357807d5',2000.0000000000000000);
INSERT INTO `Payment Applications` (`Payments GUID`, `Documents GUID`, `Amount`) VALUES ('bc19746b5b244f588b768998b4a0a39c','6e1701097c104c049bed06a1357807d5',2613.4000000000000000);
INSERT INTO `Payment Applications` (`Payments GUID`, `Documents GUID`, `Amount`) VALUES ('daf5303bfaa64dd68892afe873d848b4','327d0128b64d4750a75edf413d918a16',146.3600000000000000);
/*!40000 ALTER TABLE `Payment Applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payment Types`
--

DROP TABLE IF EXISTS `Payment Types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payment Types` (
  `GUID` char(32) NOT NULL,
  `Accounts GUID` char(32) DEFAULT NULL,
  `Is Sales Related` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  KEY `Payment Types>Account` (`Accounts GUID`),
  CONSTRAINT `Payment Type>Transaction Type` FOREIGN KEY (`GUID`) REFERENCES `Transaction Types` (`GUID`),
  CONSTRAINT `Payment Types>Account` FOREIGN KEY (`Accounts GUID`) REFERENCES `Accounts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payment Types`
--

LOCK TABLES `Payment Types` WRITE;
/*!40000 ALTER TABLE `Payment Types` DISABLE KEYS */;
INSERT INTO `Payment Types` (`GUID`, `Accounts GUID`, `Is Sales Related`) VALUES ('2c12d6167d654604be3f533c38f1ad1e','c4ced50bf99c442a9398bc84ab2bf908','');
INSERT INTO `Payment Types` (`GUID`, `Accounts GUID`, `Is Sales Related`) VALUES ('a714a873202f4f12bb29a42ed8ed9b5c','3bed1cad8e4c43c792f35ab9fb582718','\0');
/*!40000 ALTER TABLE `Payment Types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payment Types Document Types`
--

DROP TABLE IF EXISTS `Payment Types Document Types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payment Types Document Types` (
  `Document Types GUID` char(32) NOT NULL,
  `Payment Types GUID` char(32) NOT NULL,
  UNIQUE KEY `Payment Type Unique Document Type` (`Payment Types GUID`,`Document Types GUID`),
  KEY `Payment Types Document Types>Document Types` (`Document Types GUID`),
  CONSTRAINT `Payment Types Document Types>Document Types` FOREIGN KEY (`Document Types GUID`) REFERENCES `Document Types` (`GUID`),
  CONSTRAINT `Payment Types Document Types>Payment Type` FOREIGN KEY (`Payment Types GUID`) REFERENCES `Payment Types` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payment Types Document Types`
--

LOCK TABLES `Payment Types Document Types` WRITE;
/*!40000 ALTER TABLE `Payment Types Document Types` DISABLE KEYS */;
INSERT INTO `Payment Types Document Types` (`Document Types GUID`, `Payment Types GUID`) VALUES ('6632ec00f5824aeca4a49bf21cbdaece','a714a873202f4f12bb29a42ed8ed9b5c');
INSERT INTO `Payment Types Document Types` (`Document Types GUID`, `Payment Types GUID`) VALUES ('81e2917ac5c34d1cb6f9d168cd0439db','2c12d6167d654604be3f533c38f1ad1e');
INSERT INTO `Payment Types Document Types` (`Document Types GUID`, `Payment Types GUID`) VALUES ('86af180c412f40c5a660678e3895694b','2c12d6167d654604be3f533c38f1ad1e');
INSERT INTO `Payment Types Document Types` (`Document Types GUID`, `Payment Types GUID`) VALUES ('9d3821afd6fb47f9b2713d3cc574ceff','a714a873202f4f12bb29a42ed8ed9b5c');
/*!40000 ALTER TABLE `Payment Types Document Types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payments`
--

DROP TABLE IF EXISTS `Payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payments` (
  `GUID` char(32) NOT NULL,
  `Payment Types GUID` char(32) NOT NULL,
  `Bank Deposits GUID` char(32) DEFAULT NULL,
  `Our Number` varchar(128) DEFAULT NULL,
  `Date` date NOT NULL,
  `Posted Accounts GUID` char(32) NOT NULL,
  `Posted Transactions GUID` char(32) DEFAULT NULL,
  `Prepayment Documents GUID` char(32) DEFAULT NULL,
  `Expense Lines Documents GUID` char(32) DEFAULT NULL,
  `Contacts GUID` char(32) NOT NULL,
  `Contacts Display Name` varchar(128) NOT NULL,
  `Their Number` varchar(128) DEFAULT NULL,
  `Billing Contacts GUID` char(32) DEFAULT NULL,
  `Billing Address Display Name` varchar(128) NOT NULL,
  `Billing Address Line 1` varchar(128) DEFAULT NULL,
  `Billing Address Line 2` varchar(128) DEFAULT NULL,
  `Billing Address City` varchar(64) DEFAULT NULL,
  `Billing Address State` varchar(32) DEFAULT NULL,
  `Billing Address Postal Code` varchar(16) DEFAULT NULL,
  `Billing Address Country` varchar(3) DEFAULT NULL,
  `Total` decimal(64,16) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Our Number` (`Our Number`),
  UNIQUE KEY `Posted Transactions GUID` (`Posted Transactions GUID`),
  UNIQUE KEY `Payments Unique Their Number` (`Contacts GUID`,`Their Number`),
  KEY `Payments>Payment Type` (`Payment Types GUID`),
  KEY `Payments>Account` (`Posted Accounts GUID`),
  KEY `Billing Payments>Billing Contact` (`Billing Contacts GUID`),
  KEY `Payments>Bank Deposits` (`Bank Deposits GUID`),
  CONSTRAINT `Billing Payments>Billing Contact` FOREIGN KEY (`Billing Contacts GUID`) REFERENCES `Contacts` (`GUID`),
  CONSTRAINT `Contact Payments>Contact` FOREIGN KEY (`Contacts GUID`) REFERENCES `Contacts` (`GUID`),
  CONSTRAINT `Payment>Transaction` FOREIGN KEY (`Posted Transactions GUID`) REFERENCES `Transactions` (`GUID`),
  CONSTRAINT `Payments>Account` FOREIGN KEY (`Posted Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Payments>Bank Deposits` FOREIGN KEY (`Bank Deposits GUID`) REFERENCES `Bank Deposits` (`GUID`),
  CONSTRAINT `Payments>Payment Type` FOREIGN KEY (`Payment Types GUID`) REFERENCES `Payment Types` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payments`
--

LOCK TABLES `Payments` WRITE;
/*!40000 ALTER TABLE `Payments` DISABLE KEYS */;
INSERT INTO `Payments` (`GUID`, `Payment Types GUID`, `Bank Deposits GUID`, `Our Number`, `Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Prepayment Documents GUID`, `Expense Lines Documents GUID`, `Contacts GUID`, `Contacts Display Name`, `Their Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Total`) VALUES ('3ead7e8f5ded4883ad12104484b322af','2c12d6167d654604be3f533c38f1ad1e',NULL,'1004','2017-03-09','c4ced50bf99c442a9398bc84ab2bf908','3ead7e8f5ded4883ad12104484b322af',NULL,NULL,'fabca02484aa46eaa24c939c64779a2d','Thoughtstorm LLC',NULL,'fabca02484aa46eaa24c939c64779a2d','Thoughtstorm LLC','2481 Burwell Heights Road',NULL,'Nederland','TX','77627',NULL,443.5500000000000000);
INSERT INTO `Payments` (`GUID`, `Payment Types GUID`, `Bank Deposits GUID`, `Our Number`, `Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Prepayment Documents GUID`, `Expense Lines Documents GUID`, `Contacts GUID`, `Contacts Display Name`, `Their Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Total`) VALUES ('609b536c31e24bff91288a9544f8fc28','a714a873202f4f12bb29a42ed8ed9b5c',NULL,'1003','2017-01-15','3bed1cad8e4c43c792f35ab9fb582718','609b536c31e24bff91288a9544f8fc28',NULL,NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct',NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct','1942 Houston Ave #2495',NULL,'Houston','TX','75698',NULL,2000.0000000000000000);
INSERT INTO `Payments` (`GUID`, `Payment Types GUID`, `Bank Deposits GUID`, `Our Number`, `Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Prepayment Documents GUID`, `Expense Lines Documents GUID`, `Contacts GUID`, `Contacts Display Name`, `Their Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Total`) VALUES ('bc19746b5b244f588b768998b4a0a39c','a714a873202f4f12bb29a42ed8ed9b5c',NULL,'1002','2017-02-02','3bed1cad8e4c43c792f35ab9fb582718','bc19746b5b244f588b768998b4a0a39c',NULL,NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct',NULL,'6c0c4a2d443a420aac26f3097315d9d4','MicroPC Direct','1942 Houston Ave #2495',NULL,'Houston','TX','75698',NULL,2613.4000000000000000);
INSERT INTO `Payments` (`GUID`, `Payment Types GUID`, `Bank Deposits GUID`, `Our Number`, `Date`, `Posted Accounts GUID`, `Posted Transactions GUID`, `Prepayment Documents GUID`, `Expense Lines Documents GUID`, `Contacts GUID`, `Contacts Display Name`, `Their Number`, `Billing Contacts GUID`, `Billing Address Display Name`, `Billing Address Line 1`, `Billing Address Line 2`, `Billing Address City`, `Billing Address State`, `Billing Address Postal Code`, `Billing Address Country`, `Total`) VALUES ('daf5303bfaa64dd68892afe873d848b4','2c12d6167d654604be3f533c38f1ad1e',NULL,'1001','2017-03-01','c4ced50bf99c442a9398bc84ab2bf908','daf5303bfaa64dd68892afe873d848b4',NULL,NULL,'706cd3bb6d82431c90ede0b7495e87f5','Kinney Shoes','1564','706cd3bb6d82431c90ede0b7495e87f5','Kinney Shoes','3484 Flynn Street',NULL,'Cleveland','OH','44115',NULL,146.3600000000000000);
/*!40000 ALTER TABLE `Payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payroll Checks`
--

DROP TABLE IF EXISTS `Payroll Checks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payroll Checks` (
  `GUID` char(32) NOT NULL,
  `Is Template` bit(1) NOT NULL,
  `Number` varchar(64) DEFAULT NULL,
  `Date` date NOT NULL,
  `Employees GUID` char(32) NOT NULL,
  `Pay To The Order Of` varchar(128) DEFAULT NULL,
  `Line 1` varchar(128) DEFAULT NULL,
  `Line 2` varchar(128) DEFAULT NULL,
  `Country` varchar(3) DEFAULT NULL,
  `City` varchar(64) DEFAULT NULL,
  `State` varchar(32) DEFAULT NULL,
  `Postal Code` varchar(16) DEFAULT NULL,
  `Accounts GUID` char(32) NOT NULL,
  `Posted Transactions GUID` char(32) DEFAULT NULL,
  `Compensation` decimal(64,16) NOT NULL,
  `Adjusted Gross` decimal(64,16) NOT NULL,
  `Employee Paid` decimal(64,16) NOT NULL,
  `Paycheck Amount` decimal(64,16) NOT NULL,
  `Company Paid` decimal(64,16) NOT NULL,
  `Total Costs` decimal(64,16) NOT NULL,
  `Duration` int(11) NOT NULL,
  `Ending` date NOT NULL,
  PRIMARY KEY (`GUID`),
  KEY `Payroll Checks>Employee GUID` (`Employees GUID`),
  KEY `Payroll Checks>Accounts GUID` (`Accounts GUID`),
  KEY `Payroll Checks>Transaction` (`Posted Transactions GUID`),
  CONSTRAINT `Payroll Checks>Accounts GUID` FOREIGN KEY (`Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Payroll Checks>Employee GUID` FOREIGN KEY (`Employees GUID`) REFERENCES `Employees` (`GUID`),
  CONSTRAINT `Payroll Checks>Transaction` FOREIGN KEY (`Posted Transactions GUID`) REFERENCES `Transactions` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payroll Checks`
--

LOCK TABLES `Payroll Checks` WRITE;
/*!40000 ALTER TABLE `Payroll Checks` DISABLE KEYS */;
INSERT INTO `Payroll Checks` (`GUID`, `Is Template`, `Number`, `Date`, `Employees GUID`, `Pay To The Order Of`, `Line 1`, `Line 2`, `Country`, `City`, `State`, `Postal Code`, `Accounts GUID`, `Posted Transactions GUID`, `Compensation`, `Adjusted Gross`, `Employee Paid`, `Paycheck Amount`, `Company Paid`, `Total Costs`, `Duration`, `Ending`) VALUES ('81d83d300d754d17bab985fbd02678be','',NULL,'2017-01-01','6ac66b8e0a7f47b38c673ad2ac9639c2','Jason McNew','4542 Kempwood Ave',NULL,NULL,'Houston','TX','77083','3bed1cad8e4c43c792f35ab9fb582718',NULL,960.0000000000000000,880.4700000000000000,106.5300000000000000,773.9400000000000000,156.3100000000000000,1116.3100000000000000,2,'2017-01-01');
/*!40000 ALTER TABLE `Payroll Checks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payroll Field Types`
--

DROP TABLE IF EXISTS `Payroll Field Types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payroll Field Types` (
  `GUID` char(32) NOT NULL,
  `Name` varchar(32) NOT NULL,
  `Debit Required` bit(1) NOT NULL,
  `Credit Required` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payroll Field Types`
--

LOCK TABLES `Payroll Field Types` WRITE;
/*!40000 ALTER TABLE `Payroll Field Types` DISABLE KEYS */;
INSERT INTO `Payroll Field Types` (`GUID`, `Name`, `Debit Required`, `Credit Required`) VALUES ('2f542318ae174eaf8bb95f02ed8f6df5','Company Paid','','');
INSERT INTO `Payroll Field Types` (`GUID`, `Name`, `Debit Required`, `Credit Required`) VALUES ('af32731792b64a6081a6a1f73d9afca0','Gross Expense','','\0');
INSERT INTO `Payroll Field Types` (`GUID`, `Name`, `Debit Required`, `Credit Required`) VALUES ('f1b0d26375e74b0eb1aeb53bace00499','Employee Paid','\0','');
/*!40000 ALTER TABLE `Payroll Field Types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payroll Field Values`
--

DROP TABLE IF EXISTS `Payroll Field Values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payroll Field Values` (
  `GUID` char(32) NOT NULL,
  `Payroll Checks GUID` char(32) NOT NULL,
  `Payroll Fields GUID` char(32) NOT NULL,
  `Rate` decimal(64,16) NOT NULL,
  `Quantity` decimal(64,16) NOT NULL,
  `Total` decimal(64,16) NOT NULL,
  PRIMARY KEY (`GUID`),
  KEY `Payroll Field Values>Payroll Check` (`Payroll Checks GUID`),
  KEY `Payroll Field Values>Payroll Field` (`Payroll Fields GUID`),
  CONSTRAINT `Payroll Field Values>Payroll Check` FOREIGN KEY (`Payroll Checks GUID`) REFERENCES `Payroll Checks` (`GUID`),
  CONSTRAINT `Payroll Field Values>Payroll Field` FOREIGN KEY (`Payroll Fields GUID`) REFERENCES `Payroll Fields` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payroll Field Values`
--

LOCK TABLES `Payroll Field Values` WRITE;
/*!40000 ALTER TABLE `Payroll Field Values` DISABLE KEYS */;
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('05a16acc2ccf476aa8fc259fe50c4bf5','81d83d300d754d17bab985fbd02678be','d9f94916467345b283b0ec95cd7625f5',18.0000000000000000,0.0000000000000000,0.0000000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('309ecdf9a56d4baca86c3abdfd74c4d4','81d83d300d754d17bab985fbd02678be','d7ec6d4b3c3843028ffe3afb7f2d631a',54.5900000000000000,1.0000000000000000,54.5900000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('3650580d3cf849fda1be17722607d2f5','81d83d300d754d17bab985fbd02678be','56d8559d82904c569e561f02ede67474',4.1400000000000000,1.0000000000000000,4.1400000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('4851cd04cb5647b7aa7727dc115a292a','81d83d300d754d17bab985fbd02678be','b3b3b1797d8d4dfe9a0059db9b0e2996',18.0000000000000000,0.0000000000000000,0.0000000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('59841680f1624f26bc45773602a7e0c7','81d83d300d754d17bab985fbd02678be','68b1230b30f84adb8f6ff88b24a1f80f',12.7700000000000000,1.0000000000000000,12.7700000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('67a73730e2ea48e2bdac7f5c05d9e0c1','81d83d300d754d17bab985fbd02678be','be2f732cb1e94247a35a92fa2b15dfa3',12.0000000000000000,40.0000000000000000,480.0000000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('9479e9b25ce045288d5989d8cd8c407d','81d83d300d754d17bab985fbd02678be','fac58e6336054e9dada6a914e4c49f7a',12.7700000000000000,1.0000000000000000,12.7700000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('95aa89409d694e4baaf4b4fd5b9578c3','81d83d300d754d17bab985fbd02678be','9493c17d8445494285cbe1d30d9414d5',-79.5300000000000000,1.0000000000000000,-79.5300000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('a90ffff0443c408b9fc093a132016ee8','81d83d300d754d17bab985fbd02678be','ce69c68f99d44828a4ab2be67ebafa43',54.5900000000000000,1.0000000000000000,54.5900000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('b7145e4849fe4ba29b57a05a13f6d3b7','81d83d300d754d17bab985fbd02678be','eb1a9e7602bc41ce907e49d62cdef3e0',5.2800000000000000,1.0000000000000000,5.2800000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('bf22a7cff79e4f358f5d83a430a58d7f','81d83d300d754d17bab985fbd02678be','ad261d2abca6487eb1bc5a72fa6518b9',79.5300000000000000,1.0000000000000000,79.5300000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('e19d1463b05a4eed87966db2f631b949','81d83d300d754d17bab985fbd02678be','45cfe3afd16c4a188d21d16369f9b0d1',0.0000000000000000,0.0000000000000000,0.0000000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('e3248903c93f4fdca58fc5fa7964897e','81d83d300d754d17bab985fbd02678be','3e24c2f9265647fababd426951ee739f',39.1700000000000000,1.0000000000000000,39.1700000000000000);
INSERT INTO `Payroll Field Values` (`GUID`, `Payroll Checks GUID`, `Payroll Fields GUID`, `Rate`, `Quantity`, `Total`) VALUES ('eaf61c46640a40a2b8f4b4e3f20c203d','81d83d300d754d17bab985fbd02678be','63f8f6b44cce4777a8b9f10b86cb8225',12.0000000000000000,40.0000000000000000,480.0000000000000000);
/*!40000 ALTER TABLE `Payroll Field Values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payroll Fields`
--

DROP TABLE IF EXISTS `Payroll Fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payroll Fields` (
  `GUID` char(32) NOT NULL,
  `Name` varchar(32) NOT NULL,
  `Payroll Field Types GUID` char(32) NOT NULL,
  `Debit Accounts GUID` char(32) DEFAULT NULL,
  `Credit Accounts GUID` char(32) DEFAULT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Name` (`Name`),
  KEY `Payroll Fields>Payroll Field Type` (`Payroll Field Types GUID`),
  KEY `Debit Payroll Fields>Debit Account` (`Debit Accounts GUID`),
  KEY `Credit Payroll Fields>Credit Account` (`Credit Accounts GUID`),
  CONSTRAINT `Credit Payroll Fields>Credit Account` FOREIGN KEY (`Credit Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Debit Payroll Fields>Debit Account` FOREIGN KEY (`Debit Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Payroll Fields>Payroll Field Type` FOREIGN KEY (`Payroll Field Types GUID`) REFERENCES `Payroll Field Types` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payroll Fields`
--

LOCK TABLES `Payroll Fields` WRITE;
/*!40000 ALTER TABLE `Payroll Fields` DISABLE KEYS */;
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('3e24c2f9265647fababd426951ee739f','FIT','f1b0d26375e74b0eb1aeb53bace00499',NULL,'44f8a57a06b74750bba19d68b98a4dfb');
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('45cfe3afd16c4a188d21d16369f9b0d1','Bonus','af32731792b64a6081a6a1f73d9afca0','b3f04ff547164d7abf5e707d98deed7f',NULL);
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('56d8559d82904c569e561f02ede67474','SUTA','2f542318ae174eaf8bb95f02ed8f6df5','fe86f0725fd1415386a990927d328f72','4aa43c494003422eabc96954a9e5dc82');
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('63f8f6b44cce4777a8b9f10b86cb8225','Week 1 Wages','af32731792b64a6081a6a1f73d9afca0','b3f04ff547164d7abf5e707d98deed7f',NULL);
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('68b1230b30f84adb8f6ff88b24a1f80f','MC Withheld','f1b0d26375e74b0eb1aeb53bace00499',NULL,'bff1870e972c4a24bf41c1957aa5eeff');
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('9493c17d8445494285cbe1d30d9414d5','Health Insurance Withheld','af32731792b64a6081a6a1f73d9afca0','21911b853d1644a6bd7ec2ee26cf0be7',NULL);
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('ad261d2abca6487eb1bc5a72fa6518b9','Health Insurance Contributed','2f542318ae174eaf8bb95f02ed8f6df5','34c0422c84664a57be1a92c8e087ae55','21911b853d1644a6bd7ec2ee26cf0be7');
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('b3b3b1797d8d4dfe9a0059db9b0e2996','Week 2 OT','af32731792b64a6081a6a1f73d9afca0','b3f04ff547164d7abf5e707d98deed7f',NULL);
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('be2f732cb1e94247a35a92fa2b15dfa3','Week 2 Wages','af32731792b64a6081a6a1f73d9afca0','b3f04ff547164d7abf5e707d98deed7f',NULL);
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('ce69c68f99d44828a4ab2be67ebafa43','SS Withheld','f1b0d26375e74b0eb1aeb53bace00499',NULL,'174f527b44614ff9a072ec9275f0613f');
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('d7ec6d4b3c3843028ffe3afb7f2d631a','SS Contributed','2f542318ae174eaf8bb95f02ed8f6df5','7c2a75c39b794c16b625052683bfabee','174f527b44614ff9a072ec9275f0613f');
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('d9f94916467345b283b0ec95cd7625f5','Week 1 OT','af32731792b64a6081a6a1f73d9afca0','b3f04ff547164d7abf5e707d98deed7f',NULL);
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('eb1a9e7602bc41ce907e49d62cdef3e0','FUTA','2f542318ae174eaf8bb95f02ed8f6df5','9fc41eb7bd8b400a8d1069b6934ef30d','1a1c764444434ac8832471b6ea7cf1a4');
INSERT INTO `Payroll Fields` (`GUID`, `Name`, `Payroll Field Types GUID`, `Debit Accounts GUID`, `Credit Accounts GUID`) VALUES ('fac58e6336054e9dada6a914e4c49f7a','MC Contributed','2f542318ae174eaf8bb95f02ed8f6df5','21154875c581474499defecdc7a1be3e','bff1870e972c4a24bf41c1957aa5eeff');
/*!40000 ALTER TABLE `Payroll Fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Permissions`
--

DROP TABLE IF EXISTS `Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Permissions` (
  `Groups GUID` char(32) NOT NULL,
  `Securables GUID` char(32) NOT NULL,
  `Can Create` bit(1) NOT NULL,
  `Can Read` bit(1) NOT NULL,
  `Can Update` bit(1) NOT NULL,
  `Can Delete` bit(1) NOT NULL,
  UNIQUE KEY `Unique Permissions` (`Securables GUID`,`Groups GUID`),
  KEY `Group<Permissions` (`Groups GUID`),
  CONSTRAINT `Group<Permissions` FOREIGN KEY (`Groups GUID`) REFERENCES `Groups` (`GUID`),
  CONSTRAINT `Securable<Permissions` FOREIGN KEY (`Securables GUID`) REFERENCES `Securables` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Permissions`
--

LOCK TABLES `Permissions` WRITE;
/*!40000 ALTER TABLE `Permissions` DISABLE KEYS */;
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','12ceff2290bb9039beaa8f36d5dec226','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','1c8d2e4380181d9b2c0429dce7378d38','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','2a85420faee85c0a1aa204a3ee713ba4','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','31112aca11d0e9e6eb7db96f317dda49','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','45888a4da062f16a099a7f7c6cc15ee0','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','4a8a1dc4a61260a1d51e3b9f8bb5f18f','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','6332798b12e537b25b1c6ad254e14f54','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','720cdbbebae7124d2c3fdc1b46664655','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','74296745c146fc4ffc4afda0f19f1f2c','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','7b75551eba1477306b17861d66595527','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','965f19fd66f3bbd02a54f47952b68083','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','9aa698f602b1e5694855cee73a683488','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','9b945efebb006547a94415eadaa12921','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','9dea4016dbcc290b773ab2fae678aaa8','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','a37ede293936e29279ed543129451ec3','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','a7a7b26872b3e2d00de7bb7b1452b5a8','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','aa0a678d8950cf58d676ff1df2aa08ce','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','b2d37ae1cedf42ff874289b721860af2','','\0','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','b748fae7af491847c7a3fcb4db6e13b1','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','c8276ec6738a4fc0bb0fe7af7815f045','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','c8cf2b64be19b0234578a5b582f86a87','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','d08ccf52b4cdd08e41cfb99ec42e0b29','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','d1e2f3dc6323be332c0590e0496f63ac','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','e4eed2a7d7b7558dfe63d4f4fd18ce67','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','f28128b38efbc6134dc40751ee21fd29','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','f4f70727dc34561dfde1a3c529b6205c','\0','','\0','\0');
INSERT INTO `Permissions` (`Groups GUID`, `Securables GUID`, `Can Create`, `Can Read`, `Can Update`, `Can Delete`) VALUES ('11eede08a5f34402a2547edc6aad2529','f9aae5fda8d810a29f12d1e61b4ab25f','\0','','\0','\0');
/*!40000 ALTER TABLE `Permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reconciliations`
--

DROP TABLE IF EXISTS `Reconciliations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reconciliations` (
  `GUID` char(32) NOT NULL,
  `Accounts GUID` char(32) NOT NULL,
  `Date` date NOT NULL,
  `Statement Ending Balance` decimal(64,16) NOT NULL,
  `Off By` decimal(64,16) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Reconciliations Unique Date` (`Accounts GUID`,`Date`),
  CONSTRAINT `Account<Reconciliations` FOREIGN KEY (`Accounts GUID`) REFERENCES `Accounts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reconciliations`
--

LOCK TABLES `Reconciliations` WRITE;
/*!40000 ALTER TABLE `Reconciliations` DISABLE KEYS */;
/*!40000 ALTER TABLE `Reconciliations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Report Blocks`
--

DROP TABLE IF EXISTS `Report Blocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Report Blocks` (
  `GUID` char(32) NOT NULL,
  `Reports GUID` char(32) DEFAULT NULL,
  `Parent Block GUID` char(32) DEFAULT NULL,
  `Priority` int(11) NOT NULL,
  `Name` varchar(128) NOT NULL,
  `SQL Query` varchar(4096) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Unique Block Name` (`Reports GUID`,`Name`),
  UNIQUE KEY `Unique Block Order` (`Reports GUID`,`Priority`),
  KEY `Children>Parent` (`Parent Block GUID`),
  CONSTRAINT `Blocks>Report` FOREIGN KEY (`Reports GUID`) REFERENCES `Reports` (`GUID`),
  CONSTRAINT `Children>Parent` FOREIGN KEY (`Parent Block GUID`) REFERENCES `Report Blocks` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Report Blocks`
--

LOCK TABLES `Report Blocks` WRITE;
/*!40000 ALTER TABLE `Report Blocks` DISABLE KEYS */;
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('0ad0468ea1e742968479de41e0906bbf','33abdf4f68f44d73b23fabe400280576',NULL,0,'Row','SELECT * FROM \r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Display Name\" AS \"Name\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n                WHEN \"Is Allowed\" <> 0 THEN \'Allowed\'\r\n			END AS \"Status\"\r\n		FROM \"Users\"\r\n	) \"TBL\"\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('0b2c90eb1f6f4fb09d50d8f2bd9a5eb1','f64ba5f5752a4473a5946fe68ddde21f',NULL,1,'Row','SELECT\r\n	\"GUID\",\r\n	\"Date\",\r\n    \"Contact\",\r\n    CASE \r\n        WHEN \"Their Number\" IS NOT NULL THEN CONCAT(\'Check \', \"Their Number\")\r\n        ELSE CONCAT(\'Receipt \', \"Our Number\")\r\n    END AS \"Number\",\r\n    \"Doc Type\",\r\n    \"Doc Number\",\r\n    CAST((\"Taxable\" * \"Percent\") AS DECIMAL(64,2)) AS \"Taxable\",\r\n    CAST((\"Nontaxable\" * \"Percent\") AS DECIMAL(64,2)) AS \"Nontaxable\",\r\n    CAST((\"Taxes\" * \"Percent\") AS DECIMAL(64,2)) AS \"Taxes\",\r\n    CAST(\"Pymt Total\" AS DECIMAL(64,2)) AS \"Pymt Total\",\r\n    \"Tax Rate\",\r\n    \"Tax Name\",\r\n    \"Edit URL\"\r\nFROM (\r\n	SELECT\r\n			P.\"GUID\",\r\n			P.\"Date\",\r\n			P.\"Contacts Display Name\" AS \"Contact\",\r\n			P.\"Their Number\",\r\n			P.\"Our Number\",\r\n            P.\"Total\" AS \"Pymt Total\",\r\n			CASE\r\n				WHEN D.\"Total\" <> 0 THEN P.\"Total\" / D.\"Total\" \r\n				ELSE 1\r\n			END AS \"Percent\",\r\n			D.\"Date\" AS \"Doc Date\",\r\n			DTT.\"Name\" AS \"Doc Type\",\r\n			D.\"Reference Number\" AS \"Doc Number\",\r\n			CASE\r\n				WHEN DTT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n				ELSE -1\r\n			END * CAST( CASE\r\n				WHEN D.\"Tax Rate\" <> 0 THEN D.\"Taxable Subtotal\"\r\n				ELSE 0\r\n			END AS DECIMAL(64,2) ) AS \"Taxable\",\r\n			CASE\r\n				WHEN DTT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n				ELSE -1\r\n			END * CAST( CASE\r\n				WHEN D.\"Tax Rate\" <> 0 THEN D.\"Nontaxable Subtotal\"\r\n				ELSE D.\"Nontaxable Subtotal\" + D.\"Taxable Subtotal\"\r\n			END AS DECIMAL(64,2) ) AS \"Nontaxable\",\r\n			CASE\r\n				WHEN DTT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n				ELSE -1\r\n			END * CAST( D.\"Taxes\" AS DECIMAL(64,2) ) AS \"Taxes\",\r\n			CASE\r\n				WHEN DTT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n				ELSE -1\r\n			END * CAST( D.\"Total\" AS DECIMAL(64,2) ) AS \"Total\",\r\n			CAST( (D.\"Tax Rate\" * 100) AS DECIMAL(64,2) ) AS \"Tax Rate\",\r\n			ST.\"Display Name\" AS \"Tax Name\",    \r\n			PTT.\"Edit URL\"\r\n		FROM \r\n			\"Payment Applications\" PA\r\n			JOIN \"Payments\" P ON PA.\"Payments GUID\" = P.\"GUID\"\r\n			JOIN \"Documents\" D ON PA.\"Documents GUID\" = D.\"GUID\"\r\n			JOIN \"Transaction Types\" DTT ON DTT.\"GUID\" = D.\"Document Types GUID\"\r\n			JOIN \"Transaction Types\" PTT ON PTT.\"GUID\" = P.\"Payment Types GUID\"\r\n			JOIN \"Sales Taxes\" ST ON ST.\"GUID\" = D.\"Sales Taxes GUID\"\r\n		WHERE\r\n			P.\"Payment Types GUID\" = \'2c12d6167d654604be3f533c38f1ad1e\'\r\n        	AND DATE(P.\"Date\") >= DATE({Starting})\r\n            AND DATE(P.\"Date\") <= DATE({Ending})\r\n) TBL');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('107731514d504aba8af5305be30741e7','88898672973041059e47961735e0c047',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Display Name\" AS \"Name\",\r\n			\"Abbreviation\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM \"Unit Measures\"\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Abbreviation\" LIKE CONCAT(\'%\', IFNULL({Abbreviation}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"\r\n	');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('161a67c036bc4792ab9ff1beecfb03ab','ea6fdeb8d76247aa876dcab709b5de6e',NULL,20,'AssetTotal','SELECT\r\n    SUM(\r\n        CAST(\r\n            IFNULL(\r\n                ( \r\n                    SELECT SUM(\"Transaction Lines\".\"Debit\") \r\n                    FROM \r\n                        \"Transaction Lines\" \r\n                        JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                    WHERE \r\n                        \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                        AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n                )\r\n        , 0) AS DECIMAL(64,2))\r\n    ) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" = \'cf190cadde734f98a348d5f6bd112db6\'');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('161c34c5f83046f5815750c5e5b6d246','30357e5cfb164c29a741e77f3de1cce7',NULL,0,'Row','SELECT\r\n	\"Documents\".\"GUID\",\r\n	\"Documents\".\"Date\",\r\n	\"Documents\".\"Reference Number\" AS \"Number\",\r\n	\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n	CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\"\r\nFROM\r\n	\"Documents\"\r\nWHERE\r\n	\"Documents\".\"Document Types GUID\" = \'6632ec00f5824aeca4a49bf21cbdaece\'\r\n    AND DATE(\"Documents\".\"Date\") <= DATE({Ending})\r\n    AND DATE(\"Documents\".\"Date\") >= DATE({Starting})\r\n    AND \"Documents\".\"Contacts Display Name\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n	AND \"Documents\".\"Reference Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')  ');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('170c3378007146cabad30296ddd6d941','290198ebc76e43cbb7be15de3e3d0ad7',NULL,0,'Row','SELECT * FROM \r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Display Name\" AS \"Name\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n                WHEN \"Is Allowed\" <> 0 THEN \'Allowed\'\r\n			END AS \"Status\"\r\n		FROM \"Groups\"\r\n	) \"TBL\"\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('1defd1d941184f1e8d2396d3c98125f7','cefd88dd219a4b6f8bc6dd30fd77f058',NULL,0,'Row','SELECT\r\n	\"Documents\".\"GUID\",\r\n	\"Documents\".\"Date\",\r\n	\"Documents\".\"Reference Number\" AS \"Number\",\r\n	\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n	CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\"\r\nFROM\r\n	\"Documents\"\r\nWHERE\r\n	\"Documents\".\"Document Types GUID\" = \'dedf79eddf7c4e348918d42e25b53309\'\r\n    AND DATE(\"Documents\".\"Date\") <= DATE({Ending})\r\n    AND DATE(\"Documents\".\"Date\") >= DATE({Starting})\r\n    AND \"Documents\".\"Contacts Display Name\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n	AND \"Documents\".\"Reference Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')  ');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('201a2d40490b4e149f87309dbd551f46','c30ad5f313f44d15b9d5a75dcc7200ad',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT \r\n			\"Items\".\"GUID\",\r\n            \"Items\".\"Number\",\r\n            IFNULL(\"Items\".\"Sales Description\", \'\') AS \"Description\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM \"Items\"\r\n	) TBL\r\nWHERE\r\n	\"Description\" LIKE CONCAT(\'%\', IFNULL({Description}, \'\'), \'%\')\r\n	AND \"Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Number\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('22589e8e0e874f2981abc9fd1a287637','bdcddb7c80a34302a83a23ac1ed5d309',NULL,0,'Row','SELECT * FROM \r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Display Name\" AS \"Name\"\r\n		FROM \"Securables\"\r\n	) \"TBL\"\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('286ad154cfd64d2d8e4145b619d705c6','fcb89d0333e4418f829803e34887edb7',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT\r\n			\"Payment Types\".\"GUID\",\r\n			\"Name\" AS \"Name\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM \r\n			\"Payment Types\"\r\n			JOIN \"Transaction Types\" ON \"Transaction Types\".\"GUID\" = \"Payment Types\".\"GUID\"\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('2fbcbc0929aa48c0aa5c8d446105af91','b48f778f1bbf4b87b7e37c97324d9b48',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT\r\n			\"Payments\".\"GUID\",\r\n			\"Transaction Types\".\"Name\" AS \"Type\",\r\n			\"Payments\".\"Date\",\r\n			\"Payments\".\"Our Number\",\r\n			\"Payments\".\"Contacts Display Name\" AS \"Contact\",\r\n			\"Payments\".\"Their Number\",\r\n			CONVERT(\"Payments\".\"Total\", DECIMAL(64,2)) AS \"Total\"\r\n		FROM \r\n			\"Payments\"\r\n			JOIN \"Transaction Types\" ON \"Payments\".\"Payment Types GUID\" = \"Transaction Types\".\"GUID\"\r\n        WHERE\r\n        	\"Payment Types GUID\" = \'a714a873202f4f12bb29a42ed8ed9b5c\'\r\n            AND DATE(\"Payments\".\"Date\") <= DATE({Ending})\r\n            AND DATE(\"Payments\".\"Date\") >= DATE({Starting})\r\n	) TBL\r\nWHERE\r\n    \"Contact\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\nORDER BY\r\n    \"Date\", \"Our Number\", \"Contact\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('3ccae1e5c106446b9db5d0d2919f7617','6552470b48204c06b7a23271b472b6b7',NULL,0,'Row','SELECT * FROM\r\n    (\r\n    	SELECT\r\n    	    \"GUID\",\r\n    		\"Display Name\" AS \"Name\",\r\n            CONCAT(ROUND(\"Tax Rate\" * 100, 2), \'%\') AS \"Rate\"\r\n        FROM \"Sales Taxes\"\r\n        WHERE	\"Is Group\" = 1\r\n    ) TBL \r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Rate\" LIKE CONCAT(\'%\', IFNULL({Rate}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('40f4ddd6b6484b128c7b8246c2eae664','f6c1313616f24bcd827863490e2a661b',NULL,40,'ExpenseTotal','SELECT\r\n    SUM(\r\n        CAST(\r\n            IFNULL(\r\n                ( \r\n                    SELECT SUM(\"Transaction Lines\".\"Debit\") * -1\r\n                    FROM \r\n                        \"Transaction Lines\" \r\n                        JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                    WHERE \r\n                        \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                        AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n                )\r\n        , 0) AS DECIMAL(64,2))\r\n    ) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" = \'ade6405dd31d40169b7ed222ecaa6b9e\'');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('438552bcb3e44b898c2d36b6e9324e65','ea6fdeb8d76247aa876dcab709b5de6e',NULL,50,'NetWorth','SELECT\r\n    SUM(\r\n        CAST(\r\n            IFNULL(\r\n                ( \r\n                    SELECT SUM(\"Transaction Lines\".\"Debit\") \r\n                    FROM \r\n                        \"Transaction Lines\" \r\n                        JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                    WHERE \r\n                        \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                        AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n                )\r\n        , 0) AS DECIMAL(64,2))\r\n    ) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" IN (\'cf190cadde734f98a348d5f6bd112db6\', \'ddc9a0f637b64af2adeb19d0f6399e19\')');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('4689213448154b60ba8c4af3f0ad7465','2fc5847e6eba4c5784ce3258bd5b8f53',NULL,0,'Row','SELECT\r\n	\"Documents\".\"GUID\",\r\n	\"Documents\".\"Date\",\r\n	\"Documents\".\"Reference Number\" AS \"Number\",\r\n	\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n	CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\"\r\nFROM\r\n	\"Documents\"\r\nWHERE\r\n	\"Documents\".\"Document Types GUID\" = \'9d3821afd6fb47f9b2713d3cc574ceff\'\r\n    AND DATE(\"Documents\".\"Date\") <= DATE({Ending})\r\n    AND DATE(\"Documents\".\"Date\") >= DATE({Starting})\r\n    AND \"Documents\".\"Contacts Display Name\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n	AND \"Documents\".\"Reference Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')  ');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('4764261065be4f6398e5013ce8ed2b9b','08f4949be17a4d90a6a3a75b05b17fdf',NULL,1,'Row','SELECT * FROM (\r\n	SELECT\r\n		\"GUID\",\r\n		\"Contact\",\r\n		\"Date\",\r\n		\"Type\",\r\n		\"Number\",\r\n		CAST(\r\n			CASE\r\n				WHEN \"Type GUID\" = \'9d3821afd6fb47f9b2713d3cc574ceff\' THEN \"Total\" * 1\r\n				WHEN \"Type GUID\" = \'6632ec00f5824aeca4a49bf21cbdaece\' THEN \"Total\" * -1\r\n			END AS DECIMAL(64,2)\r\n		) AS \"Total\",\r\n		CAST(\r\n			CASE\r\n				WHEN \"Type GUID\" = \'9d3821afd6fb47f9b2713d3cc574ceff\' THEN \"Total\" * 1\r\n				WHEN \"Type GUID\" = \'6632ec00f5824aeca4a49bf21cbdaece\' THEN \"Total\" * -1\r\n			END - IFNULL(( \r\n				SELECT SUM(\"Amount\") \r\n				FROM \r\n					\"Payment Applications\" APP \r\n					JOIN \"Payments\" ON \"Payments\".\"GUID\" = APP.\"Payments GUID\"\r\n				WHERE \r\n					APP.\"Documents GUID\" = TBL.\"GUID\"\r\n					AND DATE(\"Payments\".\"Date\") <= DATE({As Of})\r\n			), 0) AS DECIMAL(64,2)\r\n		) AS \"Balance\",\r\n		\"Age\"\r\n	FROM (\r\n		SELECT\r\n			\"Documents\".\"GUID\",\r\n			\"Documents\".\"Date\",\r\n			\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n			\"Transaction Types\".\"Name\" AS \"Type\",\r\n			\"Document Types GUID\" AS \"Type GUID\",\r\n			\"Documents\".\"Reference Number\" AS \"Number\",\r\n			CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\",\r\n			\"Transaction Types\".\"Edit URL\",\r\n			DATEDIFF(DATE({As Of}), \"Documents\".\"Date\") AS \"Age\"\r\n		FROM\r\n			\"Documents\"\r\n			JOIN \"Transaction Types\" ON \"Transaction Types\".\"GUID\" = \"Document Types GUID\"\r\n		WHERE\r\n			\"Document Types GUID\" IN (\'9d3821afd6fb47f9b2713d3cc574ceff\', \'6632ec00f5824aeca4a49bf21cbdaece\')\r\n			AND DATE(\"Documents\".\"Date\") <= DATE({As Of})\r\n	) TBL\r\n) TBL2\r\n	WHERE \"Balance\" <> 0');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('496038127611493a85537d090fffb10c','f6c1313616f24bcd827863490e2a661b',NULL,10,'Revenue','SELECT\r\n    \"Accounts\".\"GUID\",\r\n    \"Accounts\".\"Number\",\r\n    \"Accounts\".\"Name\",\r\n    \"Account Types\".\"Name\" AS \"Type\",\r\n    CAST(\r\n        IFNULL(\r\n            ( \r\n                SELECT SUM(\"Transaction Lines\".\"Debit\") * -1 \r\n                FROM \r\n                    \"Transaction Lines\" \r\n                    JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                WHERE \r\n                    \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                    AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n            )\r\n    , 0) AS DECIMAL(64,2)) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" = \'2ddbad1cf8d04f0d9c44fa9771a15306\'\r\nORDER BY \"Number\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('4f90103182ee4813a905a84c2187452f','01f2dff753214482b4a1ac02f6640914',NULL,1,'Row','SELECT\r\n    D.\"GUID\" AS \"GUID\",\r\n    D.\"Date\" AS \"Date\",\r\n	D.\"Contacts Display Name\" AS \"Contact\",\r\n	TT.\"Name\" AS \"Type\",\r\n    D.\"Reference Number\" AS \"Number\",\r\n	CASE\r\n		WHEN TT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n        ELSE -1\r\n	END * CAST( CASE\r\n		WHEN D.\"Tax Rate\" <> 0 THEN D.\"Taxable Subtotal\"\r\n        ELSE 0\r\n	END AS DECIMAL(64,2) ) AS \"Taxable\",\r\n	CASE\r\n		WHEN TT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n        ELSE -1\r\n	END * CAST( CASE\r\n		WHEN D.\"Tax Rate\" <> 0 THEN D.\"Nontaxable Subtotal\"\r\n        ELSE D.\"Nontaxable Subtotal\" + D.\"Taxable Subtotal\"\r\n	END AS DECIMAL(64,2) ) AS \"Nontaxable\",\r\n    CASE\r\n		WHEN TT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n        ELSE -1\r\n	END * CAST( D.\"Taxes\" AS DECIMAL(64,2) ) AS \"Taxes\",\r\n    CASE\r\n		WHEN TT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n        ELSE -1\r\n	END * CAST( D.\"Total\" AS DECIMAL(64,2) ) AS \"Total\",\r\n    CAST( (D.\"Tax Rate\" * 100) AS DECIMAL(64,2) ) AS \"Tax Rate\",\r\n    ST.\"Display Name\" AS \"Tax Name\",\r\n    TT.\"Edit URL\"\r\nFROM \r\n    \"Documents\" D\r\n    JOIN \"Transaction Types\" TT ON TT.\"GUID\" = D.\"Document Types GUID\"\r\n    JOIN \"Sales Taxes\" ST ON ST.\"GUID\" = D.\"Sales Taxes GUID\"\r\nWHERE\r\n	TT.\"GUID\" IN (\'86af180c412f40c5a660678e3895694b\', \'81e2917ac5c34d1cb6f9d168cd0439db\')\r\n	AND DATE(D.\"Date\") >= DATE({Starting})\r\n    AND DATE(D.\"Date\") <= DATE({Ending})');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('5e82728029904c07a1f26808365af87a','ea6fdeb8d76247aa876dcab709b5de6e',NULL,30,'Liability','SELECT\r\n    \"Accounts\".\"GUID\",\r\n    \"Accounts\".\"Number\",\r\n    \"Accounts\".\"Name\",\r\n    \"Account Types\".\"Name\" AS \"Type\",\r\n    CAST(\r\n        IFNULL(\r\n            ( \r\n                SELECT SUM(\"Transaction Lines\".\"Debit\") \r\n                FROM \r\n                    \"Transaction Lines\" \r\n                    JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                WHERE \r\n                    \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                    AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n            )\r\n    , 0) AS DECIMAL(64,2)) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" = \'ddc9a0f637b64af2adeb19d0f6399e19\'\r\nORDER BY \"Number\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('61880deb23e64637842c816d972e5152','767a1911c68b4878a2ffc8dfd6974bc5',NULL,0,'Row','SELECT * FROM \r\n	(\r\n		SELECT \r\n			\"Logs\".\"GUID\",\r\n			\"Occured\",\r\n			\"Users\".\"Display Name\" AS \"User\",\r\n			\"Log\",\r\n            REPLACE(REPLACE(CASE\r\n				WHEN LOCATE(\'http\', \"Logs\".\"Details\") > 0 THEN LEFT(\"Logs\".\"Details\", LOCATE(\'http\', \"Logs\".\"Details\") - 3)\r\n				WHEN LENGTH(\"Logs\".\"Details\") < 255 THEN \"Logs\".\"Details\"\r\n                ELSE LEFT(\"Logs\".\"Details\", 255)\r\n			END, \'>\', \'&gt;\'), \'<\', \'&lt;\') AS \"Details\"\r\n		FROM \r\n			\"Logs\"\r\n			JOIN \"Users\" ON \"Users\".\"GUID\" = \"Users GUID\"\r\n			JOIN \"Computers\" ON \"Computers\".\"GUID\" = \"Computers GUID\"\r\n		WHERE\r\n			DATE(\"Occured\") <= DATE({Ending})\r\n			AND DATE(\"Occured\") >= DATE({Starting})        \r\n	) \"TBL\"\r\nWHERE\r\n	\"Details\" LIKE CONCAT(\'%\', IFNULL({Details}, \'\'), \'%\')\r\n	AND \"Log\" LIKE CONCAT(\'%\', IFNULL({Log}, \'\'), \'%\')\r\n	AND \"User\" LIKE CONCAT(\'%\', IFNULL({User}, \'\'), \'%\')\r\nORDER BY\r\n	\"Occured\" DESC');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('6297a89330d74faeb4886f9cadb9b5c8','f6c1313616f24bcd827863490e2a661b',NULL,50,'PL','SELECT\r\n    SUM(\r\n        CAST(\r\n            IFNULL(\r\n                ( \r\n                    SELECT SUM(\"Transaction Lines\".\"Debit\") * -1\r\n                    FROM \r\n                        \"Transaction Lines\" \r\n                        JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                    WHERE \r\n                        \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                        AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n                )\r\n        , 0) AS DECIMAL(64,2))\r\n    ) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" IN (\'ade6405dd31d40169b7ed222ecaa6b9e\', \'2ddbad1cf8d04f0d9c44fa9771a15306\')');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('66030acdb15546a5b8ba6b0c9246535c','ed6c823f890a470fa23b637dae910117',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT\r\n			\"GUID\",\r\n			\"Display Name\" AS \"Name\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM \r\n			\"Contact Types\"\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('6864a4f8ce694b22a297bac69ba25400','6eeab128b44446b6b67556f7418298ba',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Date\",\r\n			\"Number\",\r\n			\"Pay To The Order Of\" AS \"Employee\",\r\n			CONVERT(\"Paycheck Amount\", DECIMAL(64, 2)) AS \"Amount\",\r\n			CASE\r\n				WHEN \"Is Template\" = 1 THEN \'Template\'\r\n				ELSE \'Check\'\r\n			END AS \"Type\"\r\n		FROM \r\n			\"Payroll Checks\"\r\n        WHERE\r\n		    DATE(\"Date\") <= DATE({Ending})\r\n            AND DATE(\"Date\") >= DATE({Starting})\r\n	) TBL\r\nWHERE\r\n	\"Type\" LIKE CONCAT(\'%\', IFNULL({Type}, \'\'), \'%\')\r\n	AND IFNULL(\"Number\", \'\') LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')\r\n	AND \"Employee\" LIKE CONCAT(\'%\', IFNULL({Employee}, \'\'), \'%\')\r\n	AND \"Amount\" LIKE CONCAT(\'%\', IFNULL({Amount}, \'\'), \'%\')\r\n');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('69d3f986db384d9caaefe10657fbfcb7','92ecd1b71cf24a9b82538fcd9b1bf136',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT\r\n			\"Document Types\".\"GUID\",\r\n			\"Name\" AS \"Name\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM \r\n			\"Document Types\"\r\n			JOIN \"Transaction Types\" ON \"Transaction Types\".\"GUID\" = \"Document Types\".\"GUID\"\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('6e232099609b4dfa8b0f45783e7bc195','ea6fdeb8d76247aa876dcab709b5de6e',NULL,10,'Asset','SELECT\r\n    \"Accounts\".\"GUID\",\r\n    \"Accounts\".\"Number\",\r\n    \"Accounts\".\"Name\",\r\n    \"Account Types\".\"Name\" AS \"Type\",\r\n    CAST(\r\n        IFNULL(\r\n            ( \r\n                SELECT SUM(\"Transaction Lines\".\"Debit\") \r\n                FROM \r\n                    \"Transaction Lines\" \r\n                    JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                WHERE \r\n                    \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                    AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n            )\r\n    , 0) AS DECIMAL(64,2)) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" = \'cf190cadde734f98a348d5f6bd112db6\'\r\nORDER BY \"Number\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('74df5d659bad473b94de835226cc1e8b','f64ba5f5752a4473a5946fe68ddde21f',NULL,2,'Total','SELECT \r\n    CAST( SUM(\"Taxable\") AS DECIMAL(64,2) ) AS \"Taxable\",\r\n    CAST( SUM(\"Nontaxable\") AS DECIMAL(64,2) ) AS \"Nontaxable\",\r\n    CAST( SUM(\"Taxes\") AS DECIMAL(64,2) ) AS \"Taxes\",\r\n    CAST( SUM(\"Pymt Total\") AS DECIMAL(64,2) ) AS \"Total\"\r\nFROM \r\n    (\r\n\r\n        SELECT\r\n        	\"GUID\",\r\n        	\"Date\",\r\n            \"Contact\",\r\n            CASE \r\n                WHEN \"Their Number\" IS NOT NULL THEN CONCAT(\'Check \', \"Their Number\")\r\n                ELSE CONCAT(\'Receipt \', \"Our Number\")\r\n            END AS \"Number\",\r\n            \"Doc Type\",\r\n            \"Doc Number\",\r\n            CAST((\"Taxable\" * \"Percent\") AS DECIMAL(64,2)) AS \"Taxable\",\r\n            CAST((\"Nontaxable\" * \"Percent\") AS DECIMAL(64,2)) AS \"Nontaxable\",\r\n            CAST((\"Taxes\" * \"Percent\") AS DECIMAL(64,2)) AS \"Taxes\",\r\n            CAST(\"Pymt Total\" AS DECIMAL(64,2)) AS \"Pymt Total\",\r\n            \"Tax Rate\",\r\n            \"Tax Name\",\r\n            \"Edit URL\"\r\n        FROM (\r\n        	SELECT\r\n        			P.\"GUID\",\r\n        			P.\"Date\",\r\n        			P.\"Contacts Display Name\" AS \"Contact\",\r\n        			P.\"Their Number\",\r\n        			P.\"Our Number\",\r\n                    P.\"Total\" AS \"Pymt Total\",\r\n        			CASE\r\n        				WHEN D.\"Total\" <> 0 THEN P.\"Total\" / D.\"Total\" \r\n        				ELSE 1\r\n        			END AS \"Percent\",\r\n        			D.\"Date\" AS \"Doc Date\",\r\n        			DTT.\"Name\" AS \"Doc Type\",\r\n        			D.\"Reference Number\" AS \"Doc Number\",\r\n        			CASE\r\n        				WHEN DTT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n        				ELSE -1\r\n        			END * CAST( CASE\r\n        				WHEN D.\"Tax Rate\" <> 0 THEN D.\"Taxable Subtotal\"\r\n        				ELSE 0\r\n        			END AS DECIMAL(64,2) ) AS \"Taxable\",\r\n        			CASE\r\n        				WHEN DTT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n        				ELSE -1\r\n        			END * CAST( CASE\r\n        				WHEN D.\"Tax Rate\" <> 0 THEN D.\"Nontaxable Subtotal\"\r\n        				ELSE D.\"Nontaxable Subtotal\" + D.\"Taxable Subtotal\"\r\n        			END AS DECIMAL(64,2) ) AS \"Nontaxable\",\r\n        			CASE\r\n        				WHEN DTT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n        				ELSE -1\r\n        			END * CAST( D.\"Taxes\" AS DECIMAL(64,2) ) AS \"Taxes\",\r\n        			CASE\r\n        				WHEN DTT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n        				ELSE -1\r\n        			END * CAST( D.\"Total\" AS DECIMAL(64,2) ) AS \"Total\",\r\n        			CAST( (D.\"Tax Rate\" * 100) AS DECIMAL(64,2) ) AS \"Tax Rate\",\r\n        			ST.\"Display Name\" AS \"Tax Name\",    \r\n        			PTT.\"Edit URL\"\r\n        		FROM \r\n        			\"Payment Applications\" PA\r\n        			JOIN \"Payments\" P ON PA.\"Payments GUID\" = P.\"GUID\"\r\n        			JOIN \"Documents\" D ON PA.\"Documents GUID\" = D.\"GUID\"\r\n        			JOIN \"Transaction Types\" DTT ON DTT.\"GUID\" = D.\"Document Types GUID\"\r\n        			JOIN \"Transaction Types\" PTT ON PTT.\"GUID\" = P.\"Payment Types GUID\"\r\n        			JOIN \"Sales Taxes\" ST ON ST.\"GUID\" = D.\"Sales Taxes GUID\"\r\n        		WHERE\r\n        			P.\"Payment Types GUID\" = \'2c12d6167d654604be3f533c38f1ad1e\'\r\n                	AND DATE(P.\"Date\") >= DATE({Starting})\r\n                    AND DATE(P.\"Date\") <= DATE({Ending})\r\n        ) TBL\r\n\r\n    ) TBL2');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('7b71ef0991c546a69eee2ac990896117','cef027b31c704b17b9f97bc9489626cb',NULL,1,'Groups','SELECT * \r\nFROM \"Groups\" \r\nWHERE\r\n\"Groups\".\"Display Name\" LIKE CONCAT(\'%\', {Name}, \'%\') OR {Name} IS NULL');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('7ba54a189c9a47cdbcb4aaf434c55164','1b739cb0b80649589760652098d0ff9c',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Number\",\r\n			\"Name\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM \"Departments\"\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Number\",\r\n	\"Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('7c89d923db3c4c42919675216c9ac18f','7f8c6d9d299049779046ad7eb70884fa',NULL,0,'Row','SELECT\r\n	\"Documents\".\"GUID\",\r\n	\"Documents\".\"Date\",\r\n	\"Documents\".\"Reference Number\" AS \"Number\",\r\n	\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n	CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\"\r\nFROM\r\n	\"Documents\"\r\nWHERE\r\n	\"Documents\".\"Document Types GUID\" = \'e56b2b6aa42b479890085b74b69275f3\'\r\n    AND DATE(\"Documents\".\"Date\") <= DATE({Ending})\r\n    AND DATE(\"Documents\".\"Date\") >= DATE({Starting})\r\n    AND \"Documents\".\"Contacts Display Name\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n	AND \"Documents\".\"Reference Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')  ');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('7ea9908ec62d456bab857ed989c13ad3','ee4f00312ff44e3d941c6a3445bf93b0',NULL,1,'Row','SELECT\r\n    \"Transactions\".\"GUID\" AS \"GUID\",\r\n	\"Transactions\".\"Date\" AS \"Date\",\r\n    \"Transaction Types\".\"Name\" AS \"Type\",\r\n    \"Transactions\".\"Reference Number\" AS \"Number\",\r\n    \"Transaction Lines\".\"Description\" AS \"Description\",\r\n    CAST(\"Transaction Lines\".\"Debit\" AS DECIMAL(64,2)) AS \"Debit\",    \r\n    \"Transaction Types\".\"Edit URL\"\r\nFROM\r\n	\"Transactions\"\r\n    JOIN \"Transaction Lines\" ON \"Transaction Lines\".\"Transactions GUID\" = \"Transactions\".\"GUID\"\r\n    JOIN \"Transaction Types\" ON \"Transactions\".\"Transaction Types GUID\" = \"Transaction Types\".\"GUID\"\r\n    JOIN \"Accounts\" ON \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\"\r\nWHERE\r\n	DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n	AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n    AND \"Accounts GUID\" = IFNULL({Account}, \'\')\r\nORDER BY\r\n    \"Transactions\".\"Date\" DESC,\r\n    CAST(\"Transaction Lines\".\"Debit\" AS DECIMAL(64,2)),\r\n    \"Transactions\".\"Reference Number\",\r\n    \"Transaction Lines\".\"Sort Order\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('806b7499b0ca4676abb85e1bee2e6b08','84c704545c9140fb96ee5b9610fe632b',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT\r\n			\"Bank Deposits\".\"GUID\" As \"GUID\",\r\n			\"Accounts\".\"Name\" AS \"Account\",\r\n			CONVERT(\"Bank Deposits\".\"Date\", CHAR(1024)) AS \"Date\",\r\n			\"Bank Deposits\".\"Number\",\r\n			CONVERT(\"Bank Deposits\".\"Total\", DECIMAL(64,2)) AS \"Total\"\r\n		FROM \r\n			\"Bank Deposits\"\r\n			JOIN \"Accounts\" ON \"Bank Deposits\".\"Accounts GUID\" = \"Accounts\".\"GUID\"\r\n		WHERE\r\n		    DATE(\"Bank Deposits\".\"Date\") <= DATE({Ending})\r\n            AND DATE(\"Bank Deposits\".\"Date\") >= DATE({Starting})\r\n\r\n	) TBL\r\nWHERE\r\n	\"Account\" LIKE CONCAT(\'%\', IFNULL({Account}, \'\'), \'%\')\r\n	AND \"Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')\r\n	AND \"Total\" LIKE CONCAT(\'%\', IFNULL({Total}, \'\'), \'%\')\r\n');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('8299e140db0046feb7fa9605620f76f4','dd57e42a165047a38c5655dcc1b76576',NULL,0,'Row','SELECT * FROM \r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Description\" AS \"Description\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n                WHEN \"Is Allowed\" <> 0 THEN \'Allowed\'\r\n			END AS \"Status\"\r\n		FROM \"Computers\"\r\n	) \"TBL\"\r\nWHERE\r\n	\"Description\" LIKE CONCAT(\'%\', IFNULL({Description}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('91cb388ac26041219bdc7216c26cb75a','6d4ea7d4624e48509e915e019ca1f7a9',NULL,0,'Account','SELECT\r\n  \"Accounts\".\"GUID\",\r\n  \"Accounts\".\"Number\",\r\n  \"Accounts\".\"Name\",\r\n  \"Account Types\".\"Name\" AS \"Account Types Name\",\r\n  CAST(\r\n    IFNULL(\r\n      ( \r\n        SELECT SUM(\"Transaction Lines\".\"Debit\") \r\n        FROM \r\n          \"Transaction Lines\" \r\n          JOIN \"Transactions\" \r\n            ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n        WHERE \r\n          \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n          AND DATE(\"Transactions\".\"Date\") <= DATE({As Of Date})\r\n      )\r\n    , 0) AS DECIMAL(64,2)) AS \"Balance\"\r\nFROM \r\n  \"Accounts\"\r\n  JOIN \"Account Types\" \r\n    ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nORDER BY \"Number\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('99e65dccb16741738eed92c4046c2679','01f2dff753214482b4a1ac02f6640914',NULL,2,'Total','SELECT \r\n    CAST( SUM(TBL.\"Taxable\") AS DECIMAL(64,2) ) AS \"Taxable\",\r\n    CAST( SUM(TBL.\"Nontaxable\") AS DECIMAL(64,2) ) AS \"Nontaxable\",\r\n    CAST( SUM(TBL.\"Taxes\") AS DECIMAL(64,2) ) AS \"Taxes\",\r\n    CAST( SUM(TBL.\"Total\") AS DECIMAL(64,2) ) AS \"Total\"\r\nFROM \r\n    (\r\n        SELECT\r\n            D.\"Date\" AS \"Date\",\r\n        	D.\"Contacts Display Name\" AS \"Contact\",\r\n        	TT.\"Name\" AS \"Type\",\r\n            D.\"Reference Number\" AS \"Number\",\r\n        	CASE\r\n        		WHEN TT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n                ELSE -1\r\n        	END * CAST( CASE\r\n        		WHEN D.\"Tax Rate\" <> 0 THEN D.\"Taxable Subtotal\"\r\n                ELSE 0\r\n        	END AS DECIMAL(64,2) ) AS \"Taxable\",\r\n        	CASE\r\n        		WHEN TT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n                ELSE -1\r\n        	END * CAST( CASE\r\n        		WHEN D.\"Tax Rate\" <> 0 THEN D.\"Nontaxable Subtotal\"\r\n                ELSE D.\"Nontaxable Subtotal\" + D.\"Taxable Subtotal\"\r\n        	END AS DECIMAL(64,2) ) AS \"Nontaxable\",\r\n            CASE\r\n        		WHEN TT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n                ELSE -1\r\n        	END * CAST( D.\"Taxes\" AS DECIMAL(64,2) ) AS \"Taxes\",\r\n            CASE\r\n        		WHEN TT.\"GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN 1\r\n                ELSE -1\r\n        	END * CAST( D.\"Total\" AS DECIMAL(64,2) ) AS \"Total\",\r\n            CAST( (D.\"Tax Rate\" * 100) AS DECIMAL(64,2) ) AS \"Tax Rate\",\r\n            ST.\"Display Name\" AS \"Tax Name\"\r\n        FROM \r\n            \"Documents\" D\r\n            JOIN \"Transaction Types\" TT ON TT.\"GUID\" = D.\"Document Types GUID\"\r\n            JOIN \"Sales Taxes\" ST ON ST.\"GUID\" = D.\"Sales Taxes GUID\"\r\n        WHERE\r\n        	TT.\"GUID\" IN (\'86af180c412f40c5a660678e3895694b\', \'81e2917ac5c34d1cb6f9d168cd0439db\')\r\n        	AND DATE(D.\"Date\") >= DATE({Starting})\r\n            AND DATE(D.\"Date\") <= DATE({Ending})\r\n    ) TBL');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('a3a44a67d9c943dcb8fc912cc28124c6',NULL,'7b71ef0991c546a69eee2ac990896117',1,'Users','SELECT * FROM \"Users\" \nWHERE \"Users\".\"GUID\" IN (\n\nSELECT \"Users GUID\" FROM \"Memberships\" \nWHERE \"Memberships\".\"Groups GUID\" = {GUID}\n\n)');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('a489818547ff4698a213c16845671591','a64f1e848b4b4b009f669e133d201478',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Display Name\" AS \"Name\",\r\n			\"Home Phone\" AS \"Home\",\r\n			\"Mobile Phone\" AS \"Mobile\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM\r\n			\"Contacts\"\r\n		WHERE\r\n			\"GUID\" IN ( SELECT  \"Employees\".\"GUID\" FROM \"Employees\" )\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND ( \"Home\" LIKE CONCAT(\'%\', IFNULL({Phone}, \'\'), \'%\') OR \"Mobile\" LIKE CONCAT(\'%\', IFNULL({Phone}, \'\'), \'%\') ) \r\n    AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"\r\n');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('a8d5651c2f9d4e78a9ffcfd867cf8fbc','b8f0ac2f50ba4d40b227c841846dee15',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT\r\n			\"Payments\".\"GUID\",\r\n			\"Transaction Types\".\"Name\" AS \"Type\",\r\n			\"Payments\".\"Date\",\r\n			\"Payments\".\"Our Number\",\r\n			\"Payments\".\"Contacts Display Name\" AS \"Contact\",\r\n			\"Payments\".\"Their Number\",\r\n			CONVERT(\"Payments\".\"Total\", DECIMAL(64,2)) AS \"Total\"\r\n		FROM \r\n			\"Payments\"\r\n			JOIN \"Transaction Types\" ON \"Payments\".\"Payment Types GUID\" = \"Transaction Types\".\"GUID\"\r\n        WHERE\r\n        	\"Payment Types GUID\" = \'2c12d6167d654604be3f533c38f1ad1e\'\r\n            AND DATE(\"Payments\".\"Date\") <= DATE({Ending})\r\n            AND DATE(\"Payments\".\"Date\") >= DATE({Starting})\r\n	) TBL\r\nWHERE\r\n    \"Contact\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\nORDER BY\r\n    \"Date\", \"Our Number\", \"Contact\"\r\n');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('b48c56ef493c4f4eb0c6a3cadd33f691','57808ea5bb1948d8a1ddce32a3233e10',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Display Name\" AS \"Name\",\r\n			\"Office Phone\" AS \"Office\",\r\n			\"Mobile Phone\" AS \"Mobile\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM\r\n			\"Contacts\"\r\n		WHERE\r\n			\"Parent Contacts GUID\" IS NULL\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND ( \"Office\" LIKE CONCAT(\'%\', IFNULL({Phone}, \'\'), \'%\') OR \"Mobile\" LIKE CONCAT(\'%\', IFNULL({Phone}, \'\'), \'%\') ) \r\n    AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"\r\n');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('b58f5d49b7834af8ab021d6b31dfbbdc','ea6fdeb8d76247aa876dcab709b5de6e',NULL,40,'LiabilityTotal','SELECT\r\n    SUM(\r\n        CAST(\r\n            IFNULL(\r\n                ( \r\n                    SELECT SUM(\"Transaction Lines\".\"Debit\") \r\n                    FROM \r\n                        \"Transaction Lines\" \r\n                        JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                    WHERE \r\n                        \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                        AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n                )\r\n        , 0) AS DECIMAL(64,2))\r\n    ) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" = \'ddc9a0f637b64af2adeb19d0f6399e19\'');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('b9858a22109f4269894d111c69dc79bd','d638b37131514266a0fa8df8dc0b39a6',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT \r\n			\"GUID\",\r\n			\"Number\",\r\n			\"Name\",\r\n			CASE\r\n				WHEN \"Is Allowed\" = 1 THEN \'Allowed\'\r\n				WHEN \"Is Allowed\" = 0 THEN \'Disabled\'\r\n			END AS \"Status\"\r\n		FROM \"Jobs\"\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')\r\n	AND \"Status\" LIKE CONCAT(\'%\', IFNULL({Status}, \'\'), \'%\')\r\nORDER BY\r\n	\"Number\",\r\n	\"Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('bbca1a9d7d4e4afc9e92699ba71e5a59','f6c1313616f24bcd827863490e2a661b',NULL,30,'Expense','SELECT\r\n    \"Accounts\".\"GUID\",\r\n    \"Accounts\".\"Number\",\r\n    \"Accounts\".\"Name\",\r\n    \"Account Types\".\"Name\" AS \"Type\",\r\n    CAST(\r\n        IFNULL(\r\n            ( \r\n                SELECT SUM(\"Transaction Lines\".\"Debit\") * -1\r\n                FROM \r\n                    \"Transaction Lines\" \r\n                    JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                WHERE \r\n                    \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                    AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n            )\r\n    , 0) AS DECIMAL(64,2)) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" = \'ade6405dd31d40169b7ed222ecaa6b9e\'\r\nORDER BY \"Number\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('bfea1d2af2e1489d835581dcc8e9aec0','a7d53239fa82450db2e9d07594e7cce9',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT\r\n			\"Payroll Fields\".\"GUID\",\r\n			\"Payroll Fields\".\"Name\",\r\n			\"Payroll Field Types\".\"Name\" AS \"Type\"\r\n		FROM\r\n			\"Payroll Fields\"\r\n			JOIN \"Payroll Field Types\" \r\n				ON \"Payroll Fields\".\"Payroll Field Types GUID\" = \"Payroll Field Types\".\"GUID\"\r\n	) TBL\r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Type\" LIKE CONCAT(\'%\', IFNULL({Type}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('c23b928f69c64a4fa349c4393170dc68','352f588dfcd147fda29b8b4cdc2fcdfc',NULL,0,'Row','SELECT\r\n	\"Documents\".\"GUID\",\r\n	\"Documents\".\"Date\",\r\n	\"Documents\".\"Reference Number\" AS \"Number\",\r\n	\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n	CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\"\r\nFROM\r\n	\"Documents\"\r\nWHERE\r\n	\"Documents\".\"Document Types GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\'\r\n    AND DATE(\"Documents\".\"Date\") <= DATE({Ending})\r\n    AND DATE(\"Documents\".\"Date\") >= DATE({Starting})\r\n	AND \"Documents\".\"Contacts Display Name\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n	AND \"Documents\".\"Reference Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')    ');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('c5629584f1314e30b2f33ddfd1a672ec','7788774678b84145b3681511b0cc9d40',NULL,0,'Row','SELECT *\r\nFROM\r\n	(\r\n		SELECT\r\n			\"Reconciliations\".\"GUID\",\r\n			\"Date\",\r\n			CONCAT(\"Accounts\".\"Number\", \' \', \"Accounts\".\"Name\") AS \"Account\",\r\n			CONVERT(\"Statement Ending Balance\", DECIMAL(64,2)) AS \"Balance\"\r\n		FROM \"Reconciliations\"\r\n			JOIN \"Accounts\" ON \"Accounts\".\"GUID\" = \"Reconciliations\".\"Accounts GUID\"\r\n		WHERE\r\n		    DATE(\"Date\") <= DATE({Ending})\r\n            AND DATE(\"Date\") >= DATE({Starting})\r\n	) TBL\r\nWHERE\r\n	\"Account\" LIKE CONCAT(\'%\', IFNULL({Account}, \'\'), \'%\')\r\n	AND \"Balance\" LIKE CONCAT(\'%\', IFNULL({Balance}, \'\'), \'%\')\r\n');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('c6106908073e42faadf6acd3472a3d0f','75ecee50436c4a7d8095b30f794f87f9',NULL,0,'Row','SELECT \r\n	\"GUID\",\r\n    \"Display Name\" AS \"Name\"\r\nFROM \"Reports\"\r\nWHERE\r\n    \"Display Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\nORDER BY \"Display Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('ccd8f3223f53435db75c145b54b98fba','f6c1313616f24bcd827863490e2a661b',NULL,20,'RevenueTotal','SELECT\r\n    SUM(\r\n        CAST(\r\n            IFNULL(\r\n                ( \r\n                    SELECT SUM(\"Transaction Lines\".\"Debit\") * -1\r\n                    FROM \r\n                        \"Transaction Lines\" \r\n                        JOIN \"Transactions\" ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\n                    WHERE \r\n                        \"Transaction Lines\".\"Accounts GUID\" = \"Accounts\".\"GUID\" \r\n                        AND DATE(\"Transactions\".\"Date\") <= DATE({As Of})\r\n                )\r\n        , 0) AS DECIMAL(64,2))\r\n    ) AS \"Debits\"\r\nFROM \r\n    \"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Account Types\".\"GUID\" = \'2ddbad1cf8d04f0d9c44fa9771a15306\'');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('d469da5dc0d94410bd69de0ef0b02378','766ef959263043c79244f932e49ff61d',NULL,0,'Row','SELECT * FROM\r\n    (\r\n    	SELECT\r\n    	    \"GUID\",\r\n    		\"Display Name\" AS \"Name\",\r\n            CONCAT(ROUND(\"Tax Rate\" * 100, 2), \'%\') AS \"Rate\"\r\n        FROM \"Sales Taxes\"\r\n        WHERE	\"Is Group\" = 0\r\n    ) TBL \r\nWHERE\r\n	\"Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n	AND \"Rate\" LIKE CONCAT(\'%\', IFNULL({Rate}, \'\'), \'%\')\r\nORDER BY\r\n	\"Name\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('e20c0fe3559944f58fd3c2f4d831fa97','a92a85f5192e4dbb80cba3a67ad31448',NULL,0,'Row','SELECT * FROM \r\n    (\r\n    	SELECT\r\n    		\"Transactions\".\"GUID\",\r\n    		\"Date\",\r\n    		\"Transactions\".\"Reference Number\" AS \"Number\",\r\n    		\"Transaction Types\".\"Name\" AS \"Type\",\r\n    		CAST(\"Debits\".\"Debit\" AS DECIMAL(64,2)) AS \"Amount\"\r\n    	FROM\r\n    		\"Transactions\"\r\n    		JOIN \"Transaction Types\" ON \"Transaction Types\".\"GUID\" = \"Transactions\".\"Transaction Types GUID\"\r\n    		JOIN ( \r\n    			SELECT \r\n    				\"Transactions GUID\", \r\n    				SUM(\"Transaction Lines\".\"Debit\") AS \"Debit\" \r\n    			FROM \r\n    				\"Transaction Lines\" \r\n    			WHERE\r\n    				\"Transaction Lines\".\"Debit\" > 0\r\n    			GROUP BY \"Transactions GUID\"\r\n    		) \"Debits\" ON \"Transactions\".\"GUID\" = \"Transactions GUID\"\r\n    	WHERE\r\n    	    DATE(\"Date\") <= DATE({Ending})\r\n            AND DATE(\"Date\") >= DATE({Starting})\r\n    ) TBL	\r\nWHERE\r\n	\"Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')\r\n	AND \"Type\" LIKE CONCAT(\'%\', IFNULL({Type}, \'\'), \'%\')\r\n	AND \"Amount\" LIKE CONCAT(\'%\', IFNULL({Amount}, \'\'), \'%\')');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('e4c0cfd6b36643ac8374d7ae018b36d0','c545d2f279be49e9a471a43033d01c7f',NULL,0,'Row','SELECT * FROM \r\n	(\r\n		SELECT \r\n			\"Settings\".\"GUID\",\r\n            \"Key\" AS \"Key\",\r\n			IFNULL(\"Display Name\", \'\') AS \"User\"\r\n		FROM \r\n			\"Settings\"\r\n			LEFT JOIN \"Users\" ON \"Users GUID\" = \"Users\".\"GUID\"\r\n	) \"TBL\"\r\nWHERE\r\n	\"Key\" LIKE CONCAT(\'%\', IFNULL({Key}, \'\'), \'%\')\r\n	AND \"User\" LIKE CONCAT(\'%\', IFNULL({User}, \'\'), \'%\')');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('e54c8ab136754eaa960dfc17538671f4','06db3e1b55e54bc2babf0424e0843d5b',NULL,0,'Row','SELECT\r\n    \"Accounts\".\"GUID\" AS \"GUID\",\r\n    \"Accounts\".\"Number\" AS \"Number\",\r\n    \"Accounts\".\"Nested Name\" AS \"Name\",\r\n    \"Account Types\".\"Name\" AS \"Type\",\r\n    CASE\r\n        WHEN \"Accounts\".\"Is Allowed\" = 1 THEN \'True\'\r\n        ELSE \'False\'\r\n    END AS \"Allowed\"\r\nFROM\r\n	\"Accounts\"\r\n    JOIN \"Account Types\" ON \"Accounts\".\"Account Types GUID\" = \"Account Types\".\"GUID\"\r\nWHERE\r\n    \"Accounts\".\"Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')\r\n    AND \"Accounts\".\"Nested Name\" LIKE CONCAT(\'%\', IFNULL({Name}, \'\'), \'%\')\r\n    AND \"Account Types\".\"Name\" LIKE CONCAT(\'%\', IFNULL({Type}, \'\'), \'%\')\r\nORDER BY\r\n    \"Accounts\".\"Number\"');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('f0e0abf91c4646b8b8c84d9708dcc848','b88b0031fc2844eb874c5be27f8be003',NULL,1,'Row','SELECT * FROM (\r\n	SELECT\r\n		\"GUID\",\r\n		\"Contact\",\r\n		\"Date\",\r\n		\"Type\",\r\n		\"Number\",\r\n		CAST(\r\n			CASE\r\n				WHEN \"Type GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN \"Total\" * 1\r\n				WHEN \"Type GUID\" = \'86af180c412f40c5a660678e3895694b\' THEN \"Total\" * -1\r\n			END AS DECIMAL(64,2)\r\n		) AS \"Total\",\r\n		CAST(\r\n			CASE\r\n				WHEN \"Type GUID\" = \'81e2917ac5c34d1cb6f9d168cd0439db\' THEN \"Total\" * 1\r\n				WHEN \"Type GUID\" = \'86af180c412f40c5a660678e3895694b\' THEN \"Total\" * -1\r\n			END - IFNULL(( \r\n				SELECT SUM(\"Amount\") \r\n				FROM \r\n					\"Payment Applications\" APP \r\n					JOIN \"Payments\" ON \"Payments\".\"GUID\" = APP.\"Payments GUID\"\r\n				WHERE \r\n					APP.\"Documents GUID\" = TBL.\"GUID\"\r\n					AND DATE(\"Payments\".\"Date\") <= DATE({As Of})\r\n			), 0) AS DECIMAL(64,2)\r\n		) AS \"Balance\",\r\n		\"Age\"\r\n	FROM (\r\n		SELECT\r\n			\"Documents\".\"GUID\",\r\n			\"Documents\".\"Date\",\r\n			\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n			\"Transaction Types\".\"Name\" AS \"Type\",\r\n			\"Document Types GUID\" AS \"Type GUID\",\r\n			\"Documents\".\"Reference Number\" AS \"Number\",\r\n			CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\",\r\n			\"Transaction Types\".\"Edit URL\",\r\n			DATEDIFF(DATE({As Of}), \"Documents\".\"Date\") AS \"Age\"\r\n		FROM\r\n			\"Documents\"\r\n			JOIN \"Transaction Types\" ON \"Transaction Types\".\"GUID\" = \"Document Types GUID\"\r\n		WHERE\r\n			\"Document Types GUID\" IN (\'86af180c412f40c5a660678e3895694b\', \'81e2917ac5c34d1cb6f9d168cd0439db\')\r\n			AND DATE(\"Documents\".\"Date\") <= DATE({As Of})\r\n	) TBL\r\n) TBL2\r\n	WHERE \"Balance\" <> 0');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('f6be2c103b2e4acbb0e4329c66489967','3293e7a3b08744c38975accc635d7573',NULL,0,'Row','SELECT\r\n	\"Documents\".\"GUID\",\r\n	\"Documents\".\"Date\",\r\n	\"Documents\".\"Reference Number\" AS \"Number\",\r\n	\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n	CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\"\r\nFROM\r\n	\"Documents\"\r\nWHERE\r\n	\"Documents\".\"Document Types GUID\" = \'86af180c412f40c5a660678e3895694b\'\r\n    AND DATE(\"Documents\".\"Date\") <= DATE({Ending})\r\n    AND DATE(\"Documents\".\"Date\") >= DATE({Starting})\r\n    AND \"Documents\".\"Contacts Display Name\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n	AND \"Documents\".\"Reference Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')  ');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('f8ecb7bab1cb4415b7ab56b185bc61e0','078c5cd40a024563bfde3641352d4e43',NULL,0,'Row','SELECT\r\n	\"Documents\".\"GUID\",\r\n	\"Documents\".\"Date\",\r\n	\"Documents\".\"Reference Number\" AS \"Number\",\r\n	\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n	CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\"\r\nFROM\r\n	\"Documents\"\r\nWHERE\r\n	\"Documents\".\"Document Types GUID\" = \'276db4afcf634b6fbc4a5821c9858ab9\'\r\n    AND DATE(\"Documents\".\"Date\") <= DATE({Ending})\r\n    AND DATE(\"Documents\".\"Date\") >= DATE({Starting})\r\n    AND \"Documents\".\"Contacts Display Name\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n	AND \"Documents\".\"Reference Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')  ');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('fc3644f970bd47cb91c89e44d71a5946','97c04a5ce5024ad4b2859908da8f6e1a',NULL,0,'Rows','SELECT\r\n	\"Documents\".\"GUID\",\r\n	\"Documents\".\"Date\",\r\n	\"Documents\".\"Reference Number\" AS \"Number\",\r\n	\"Documents\".\"Contacts Display Name\" AS \"Contact\",\r\n	CAST(\"Documents\".\"Total\" AS DECIMAL(64,2)) AS \"Total\"\r\nFROM\r\n	\"Documents\"\r\nWHERE\r\n	\"Documents\".\"Document Types GUID\" = \'5f756fc5f7c5493ca0d86f2d0ead2fda\'\r\n    AND DATE(\"Documents\".\"Date\") <= DATE({Ending})\r\n    AND DATE(\"Documents\".\"Date\") >= DATE({Starting})\r\n    AND \"Documents\".\"Contacts Display Name\" LIKE CONCAT(\'%\', IFNULL({Contact}, \'\'), \'%\')\r\n	AND \"Documents\".\"Reference Number\" LIKE CONCAT(\'%\', IFNULL({Number}, \'\'), \'%\')  ');
INSERT INTO `Report Blocks` (`GUID`, `Reports GUID`, `Parent Block GUID`, `Priority`, `Name`, `SQL Query`) VALUES ('fdfa5f51a32c4dda806863d21a2622bb','6d4ea7d4624e48509e915e019ca1f7a9',NULL,9,'Footer','SELECT SUM(\"Transaction Lines\".\"Debit\") \r\nFROM \r\n  \"Transaction Lines\" \r\n  JOIN \"Transactions\" \r\n    ON \"Transactions\".\"GUID\" = \"Transaction Lines\".\"Transactions GUID\"\r\nWHERE \r\n  DATE(\"Transactions\".\"Date\") <= DATE({As Of Date})\r\n');
/*!40000 ALTER TABLE `Report Blocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Report Filters`
--

DROP TABLE IF EXISTS `Report Filters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Report Filters` (
  `GUID` char(32) NOT NULL,
  `Reports GUID` char(32) NOT NULL,
  `Prompt` varchar(128) NOT NULL,
  `Data Type` varchar(8) NOT NULL,
  `Query` varchar(4096) DEFAULT NULL,
  `Priority` bigint(20) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Unique Filter Prompt` (`Reports GUID`,`Prompt`),
  UNIQUE KEY `Unique Priority` (`Reports GUID`,`Priority`),
  CONSTRAINT `Filters>Report` FOREIGN KEY (`Reports GUID`) REFERENCES `Reports` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Report Filters`
--

LOCK TABLES `Report Filters` WRITE;
/*!40000 ALTER TABLE `Report Filters` DISABLE KEYS */;
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('09457ef71311430a8331c3934633fb42','f6c1313616f24bcd827863490e2a661b','As Of','Date','SELECT NOW() AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('0ae26c9bbd524859aafa021b7c123ad4','2fc5847e6eba4c5784ce3258bd5b8f53','Contact','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('0bb9daf6519342238b27fc575f057354','d638b37131514266a0fa8df8dc0b39a6','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('11b32d796cb64b0fb838f73ff6f8e7ae','6552470b48204c06b7a23271b472b6b7','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('13d9398355bc46f7af5f2b519b51c3ee','766ef959263043c79244f932e49ff61d','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('174a1416306c48a28a679aa97fa8302f','7f8c6d9d299049779046ad7eb70884fa','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('180f5a32350940b1aa01b8f3b413d155','57808ea5bb1948d8a1ddce32a3233e10','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('18f65ebbfd28475bb65af4c629603329','d638b37131514266a0fa8df8dc0b39a6','Status','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('190d9f1ebd9145bb98c56554a5d1adf3','f64ba5f5752a4473a5946fe68ddde21f','Starting','Date','SELECT DATE(CONCAT(YEAR(NOW()), \'-\', MONTH(NOW()), \'-01\')) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('1a802f7e4d7f441db4a5e7039eb0597a','6552470b48204c06b7a23271b472b6b7','Rate','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('1c0991f2fc5140c39c93d607cbe8934b','3293e7a3b08744c38975accc635d7573','Contact','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('220a65a01e694d22b593cc84edbc118f','06db3e1b55e54bc2babf0424e0843d5b','Type','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('23c21e28ab2741b8b14a51faab6ba7f3','06db3e1b55e54bc2babf0424e0843d5b','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('24e41fdad6704b8bafc8b8cdc7e068ff','01f2dff753214482b4a1ac02f6640914','Ending','Date','SELECT DATE_ADD(DATE(CONCAT(YEAR(NOW()), \'-\', MONTH(DATE_ADD(NOW(), INTERVAL 1 MONTH)) , \'-01\')), INTERVAL -1 DAY) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('28bd55f1fdc6481da527c53ef01bb2ad','c30ad5f313f44d15b9d5a75dcc7200ad','Description','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('2af212b35bfd457eb44ef5f8bea364e8','c30ad5f313f44d15b9d5a75dcc7200ad','Number','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('2c2e6123729246c88987cc489915d29d','2fc5847e6eba4c5784ce3258bd5b8f53','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('3252d903eefc40c8b314436919e3748c','767a1911c68b4878a2ffc8dfd6974bc5','Details','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('3b115f7f58ce454a99774a9359f7912f','6eeab128b44446b6b67556f7418298ba','Employee','Text',NULL,8);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('3b35f6988ef942e7bc9b127e33bbab5c','078c5cd40a024563bfde3641352d4e43','Contact','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('3c5369f88dc34c408f82058dee5ce128','97c04a5ce5024ad4b2859908da8f6e1a','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('3ccb9351c9c44d08bb5c542b4cc50861','84c704545c9140fb96ee5b9610fe632b','Number','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('41bfa905869c4720a46755f5cea81847','92ecd1b71cf24a9b82538fcd9b1bf136','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('46d7246ecfa546b3973fc7593a2f1846','c30ad5f313f44d15b9d5a75dcc7200ad','Status','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('46feb2614dd54b4497c4bcab0d737848','2fc5847e6eba4c5784ce3258bd5b8f53','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('48add6a603744a39946b0e88cc743702','08f4949be17a4d90a6a3a75b05b17fdf','As Of','Date','SELECT DATE(NOW()) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('48fed68397ff484db6aff667bef33e9c','a7d53239fa82450db2e9d07594e7cce9','Type','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('4a42441d147e46c3b7bd7206c9b6cb9c','cefd88dd219a4b6f8bc6dd30fd77f058','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('4b8d4b4ca161463ab30f274daccb77d3','01f2dff753214482b4a1ac02f6640914','Starting','Date','SELECT DATE(CONCAT(YEAR(NOW()), \'-\', MONTH(NOW()), \'-01\')) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('4c787377f6fc412babf9c802be0536c7','7788774678b84145b3681511b0cc9d40','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('4cd67585991a4dd48c154d73fb019cce','6eeab128b44446b6b67556f7418298ba','Type','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('4cf4b49b51f7481690ccaabf02a1cc3c','06db3e1b55e54bc2babf0424e0843d5b','Name','Text',NULL,6);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('4d4f6ae45d234d12bb3d426bf3a5a2b3','30357e5cfb164c29a741e77f3de1cce7','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('4dccae8a39484a72a3ec20efe1a14ae0','7f8c6d9d299049779046ad7eb70884fa','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('526af409d0534e25a9f3f6ea7e9f2fd2','a92a85f5192e4dbb80cba3a67ad31448','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('53a5ecce086441a6a1b4e5b5ca75c963','352f588dfcd147fda29b8b4cdc2fcdfc','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('5436a67191d24bdab89c995657069579','a92a85f5192e4dbb80cba3a67ad31448','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('54e6af39c04d4dc1ba9596ef6485327f','bdcddb7c80a34302a83a23ac1ed5d309','Name','Text',NULL,9);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('5505fb20d53b45928212bcc55041532b','1b739cb0b80649589760652098d0ff9c','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('555367718ba04aa49b4892b4bcd91058','7f8c6d9d299049779046ad7eb70884fa','Contact','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('5d7e578b0c114cbdb8eaf5592d4b9b76','84c704545c9140fb96ee5b9610fe632b','Total','Text',NULL,7);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('5febe2d6cf0945b2b94ffaaf27fbcba0','290198ebc76e43cbb7be15de3e3d0ad7','Status','Text',NULL,10);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('61fb46f573684594afffd37d3b9aa70b','33abdf4f68f44d73b23fabe400280576','Name','Text',NULL,11);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('62673e0b945d40f49e012629de6dbdd0','97c04a5ce5024ad4b2859908da8f6e1a','Contact','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('62a2b59571a84b36b4f267cb0c9356e1','b88b0031fc2844eb874c5be27f8be003','As Of','Date','SELECT DATE(NOW()) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('647197488f1343788e69f03f9c8406de','b8f0ac2f50ba4d40b227c841846dee15','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('69831453e88d41348aead8e986c35bec','97c04a5ce5024ad4b2859908da8f6e1a','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('6b590038c3874c1cb1aefb9315201933','3293e7a3b08744c38975accc635d7573','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('6cde8445bf144381893bd5f01ebc0802','30357e5cfb164c29a741e77f3de1cce7','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('6ffb00efdcf94e5086a42792d745c191','6eeab128b44446b6b67556f7418298ba','Number','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('731f328ef8e146b1aaceaa64b0ea9cce','6eeab128b44446b6b67556f7418298ba','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('73c79f2994bc407bb21e7b7279aa07b0','078c5cd40a024563bfde3641352d4e43','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('74d22a9667414db4bc00efd7b21d99f4','290198ebc76e43cbb7be15de3e3d0ad7','Name','Text',NULL,16);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('7595e1aa263c4a589cbec72d6a68cdf9','57808ea5bb1948d8a1ddce32a3233e10','Phone','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('766a1bdae1cc4a9cade0dfb24467e25a','ee4f00312ff44e3d941c6a3445bf93b0','As Of','Date','SELECT DATE(NOW()) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('78b4db98c7a04501855c5a3c391b6f0b','84c704545c9140fb96ee5b9610fe632b','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('7d97107ca1674180b4068bc1574ec5da','88898672973041059e47961735e0c047','Abbreviation','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('7dc099cca4fc43a28dc358c7178a969b','b48f778f1bbf4b87b7e37c97324d9b48','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('7eb197aed90b44e49a5264f953482d3d','30357e5cfb164c29a741e77f3de1cce7','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('7ff13e51837844f599318f63356f88e1','ed6c823f890a470fa23b637dae910117','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('85cef3072d574173acac9920a8a0e25c','2fc5847e6eba4c5784ce3258bd5b8f53','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('87ae753d04ae42e994a98a9d65828f48','6eeab128b44446b6b67556f7418298ba','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('8a164615f68049ba8fa376d6852952a1','078c5cd40a024563bfde3641352d4e43','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('8caefdf2c23949758e8b389be01a6b81','767a1911c68b4878a2ffc8dfd6974bc5','Log','Text',NULL,5);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('9033ae3ed89d44a58ba59955804fa22c','92ecd1b71cf24a9b82538fcd9b1bf136','Status','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('91ba0bc35f844c57986cb9799420ce23','766ef959263043c79244f932e49ff61d','Rate','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('952e2851ff2c4e1889e9e4285f08cb33','33abdf4f68f44d73b23fabe400280576','Status','Text',NULL,18);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('95a50b5a7a1d4dafb8cb47e31eae0944','1b739cb0b80649589760652098d0ff9c','Number','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('95eb9f9ce2904c7c87e73588a64e10c9','a92a85f5192e4dbb80cba3a67ad31448','Amount','Text',NULL,5);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('97738c738d8544ea8acdea0ea26150a4','84c704545c9140fb96ee5b9610fe632b','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('97c8528972554d89998c3a51ecba17a3','cef027b31c704b17b9f97bc9489626cb','Name','Text',NULL,19);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('9c0071a1aae748ae96fb7ab12a8bd954','88898672973041059e47961735e0c047','Status','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('a24863b8a32542ada09b284fcb5ea29b','c545d2f279be49e9a471a43033d01c7f','Key','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('a29c49fada3445139aa4e5b2a0195b78','a64f1e848b4b4b009f669e133d201478','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('a2a08f80b2db40419587a6a7ab76f0f0','3293e7a3b08744c38975accc635d7573','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('a4415520c82348b2ab52a67c0251468a','7788774678b84145b3681511b0cc9d40','Balance','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('a608bff94b524de4b021979f250c1633','352f588dfcd147fda29b8b4cdc2fcdfc','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('a849f72b8baa4887846f16bebae37bb9','7f8c6d9d299049779046ad7eb70884fa','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('a93c5790c7ef49ee8c44a85db750a2b1','767a1911c68b4878a2ffc8dfd6974bc5','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -1 DAY) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('aa97443093cc4ecfa2228de21091bd41','078c5cd40a024563bfde3641352d4e43','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('b5d730ced09b4f6a92cd8e00d913e554','cefd88dd219a4b6f8bc6dd30fd77f058','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('b6cbd706dd8643d1a720c498ccbc0694','352f588dfcd147fda29b8b4cdc2fcdfc','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('b6d2233766c14995822fe30091ca94e2','7788774678b84145b3681511b0cc9d40','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('ba9d0fa25e2b41dd970c57a0e83ad599','30357e5cfb164c29a741e77f3de1cce7','Contact','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('bdcf4354211e42d08ae9e13266d9bf95','7788774678b84145b3681511b0cc9d40','Account','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('be1442b905944a9c9e07cbd434be229a','d638b37131514266a0fa8df8dc0b39a6','Number','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('beed573db2584c2e91599ad9c6384c89','a92a85f5192e4dbb80cba3a67ad31448','Type','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('bf69d14c58e94048a48738836e162529','6eeab128b44446b6b67556f7418298ba','Amount','Text',NULL,7);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('bf95eba88fc84992be19d2ea414f8e8c','6d4ea7d4624e48509e915e019ca1f7a9','As Of Date','Date','SELECT NOW() AS \"Value\"',26);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('c12008ba10684dfcb21f858d0594fb67','57808ea5bb1948d8a1ddce32a3233e10','Status','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('c4d9ab2a32ad4a7ea649e2d24ed5095b','767a1911c68b4878a2ffc8dfd6974bc5','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('c872105a3878449e852a44ff20b038fa','a64f1e848b4b4b009f669e133d201478','Status','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('c8ebcb2c3053462cb1bc83801e060ec5','84c704545c9140fb96ee5b9610fe632b','Account','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('c9233186754e4728a14ed06c79bd8824','b48f778f1bbf4b87b7e37c97324d9b48','Ending','Date','SELECT DATE(NOW()) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('c997e666f0f644ada71087c02a7fe80b','b48f778f1bbf4b87b7e37c97324d9b48','Contact','Text',NULL,6);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('d08e583325e04b10921f77489a53bca7','dd57e42a165047a38c5655dcc1b76576','Description','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('d31cf441a234402ca312c6d19161a09b','b8f0ac2f50ba4d40b227c841846dee15','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('d35db3a92eeb48df9f024816f5625870','1b739cb0b80649589760652098d0ff9c','Status','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('d55c4b3abf124cf99e413a6f26f46522','97c04a5ce5024ad4b2859908da8f6e1a','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('d8ddc8a30503492586945067d095d85e','ea6fdeb8d76247aa876dcab709b5de6e','As Of','Date','SELECT NOW() AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('dc3f43ce456f4db481d04a3b6459297f','a7d53239fa82450db2e9d07594e7cce9','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('dcd2623b33c4413185929922e578f778','767a1911c68b4878a2ffc8dfd6974bc5','User','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('ddcb7d1240ee488bbed0f4ac293a5581','ee4f00312ff44e3d941c6a3445bf93b0','Account','Enum','SELECT\r\n	CONCAT(\"Accounts\".\"Number\", \' \', \"Accounts\".\"Nested Name\") AS \"Display\",\r\n    \"Accounts\".\"GUID\" AS \"Value\"\r\nFROM \"Accounts\"\r\nORDER BY CONCAT(\"Accounts\".\"Number\", \' \', \"Accounts\".\"Nested Name\")',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('e428fcf370f446dea0216b44322542d5','b8f0ac2f50ba4d40b227c841846dee15','Contact','Text',NULL,6);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('e87d7743eb3941218930aefa16d7c0c5','a64f1e848b4b4b009f669e133d201478','Phone','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('e9fe8b01aa0544c495b08dad8c8ed464','c545d2f279be49e9a471a43033d01c7f','User','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('eba93a02804a409ca0dd3baa28a2bf81','cefd88dd219a4b6f8bc6dd30fd77f058','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('f18f16d5e73f4e7fbd3d330033d1cf7d','88898672973041059e47961735e0c047','Name','Text',NULL,1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('f478e9aeb31545e4b975939088da45a1','fcb89d0333e4418f829803e34887edb7','Status','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('f47ab2ea09e449feb43e1a927dc04850','f64ba5f5752a4473a5946fe68ddde21f','Ending','Date','SELECT DATE_ADD(DATE(CONCAT(YEAR(NOW()), \'-\', MONTH(DATE_ADD(NOW(), INTERVAL 1 MONTH)) , \'-01\')), INTERVAL -1 DAY) AS \"Value\"',2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('f4dfe66ac2904ea08027d528755d37ad','352f588dfcd147fda29b8b4cdc2fcdfc','Contact','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('f8b9470600034a5eb2d40d49e97a1e4f','3293e7a3b08744c38975accc635d7573','Number','Text',NULL,3);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('f8cf82777dad4cc098d4a82572fa3d67','ed6c823f890a470fa23b637dae910117','Status','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('faa5a644c3194d868897d9df083cf270','dd57e42a165047a38c5655dcc1b76576','Status','Text',NULL,2);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('fd65cf48a3fa4f05aa05fb6524ede3ba','a92a85f5192e4dbb80cba3a67ad31448','Starting','Date','SELECT DATE_ADD(DATE(NOW()), INTERVAL -3 MONTH) AS \"Value\"',1);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('fda84b037be24d61af2813cb3a103854','cefd88dd219a4b6f8bc6dd30fd77f058','Contact','Text',NULL,4);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('fe0913cc5ef74edf817e18faa592ad8d','75ecee50436c4a7d8095b30f794f87f9','Name','Text',NULL,29);
INSERT INTO `Report Filters` (`GUID`, `Reports GUID`, `Prompt`, `Data Type`, `Query`, `Priority`) VALUES ('ffc07fbd2fa74353936c8eb7a50efff8','fcb89d0333e4418f829803e34887edb7','Name','Text',NULL,1);
/*!40000 ALTER TABLE `Report Filters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reports`
--

DROP TABLE IF EXISTS `Reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reports` (
  `GUID` char(32) NOT NULL,
  `Display Name` varchar(128) NOT NULL,
  `HTML Template` varchar(4095) NOT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Auto Load` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Display Name` (`Display Name`),
  CONSTRAINT `Securable<Report` FOREIGN KEY (`GUID`) REFERENCES `Securables` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reports`
--

LOCK TABLES `Reports` WRITE;
/*!40000 ALTER TABLE `Reports` DISABLE KEYS */;
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('01f2dff753214482b4a1ac02f6640914','Report - Accounting - Sales Tax - Accural','<style>\r\n    table a { text-decoration: none; }\r\n</style>\r\n\r\n<table width=\"100%\">\r\n    <tr>\r\n        <td class=\"black\">Contact</td>\r\n        <td class=\"black\">Date</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black right\">Taxable</td>\r\n        <td class=\"black right\">Nontaxable</td>\r\n        <td class=\"black right\">Taxes</td>\r\n        <td class=\"black right\">Total</td>\r\n        <td class=\"black right\">Rate</td>\r\n        <td class=\"black\">Name</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Contact}</a></td>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Type}</a></td>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Number}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Taxable}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Nontaxable}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Taxes}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Total}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Tax Rate}</a></td>\r\n        <td class=\"\"><a href=\"{Edit URL}&GUID={GUID}\">{Tax Name}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- BEGIN Total -->\r\n    <tr>\r\n        <td colspan=\"4\" class=\"right black\">&nbsp;</td>\r\n        <td class=\"right black\">{Taxable}</td>\r\n        <td class=\"right black\">{Nontaxable}</td>\r\n        <td class=\"right black\">{Taxes}</td>\r\n        <td class=\"right black\">{Total}</td>\r\n        <td colspan=\"2\">&nbsp;</td>\r\n    </tr>\r\n    <!-- END Total -->    \r\n</table>','Sales Tax Accural','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('06db3e1b55e54bc2babf0424e0843d5b','List - Accounting - Accounts','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Account\" class=\"add\">Add</a></td>\r\n        <td>Number</td>\r\n        <td>Name</td>\r\n        <td>Type</td>\r\n        <!--<td>Allowed</td>-->\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Account&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D06db3e1b55e54bc2babf0424e0843d5b\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Account&GUID={GUID}\">{Number}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Account&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Account&GUID={GUID}\">{Type}</a></td>\r\n        <!--<td class=\"top\"><a href=\"~/incAccounting?App=Account&GUID={GUID}\">{Allowed}</a></td>-->\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Accounts','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('078c5cd40a024563bfde3641352d4e43','List - Accounting - Sales Quotes','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&Document Types GUID=276db4afcf634b6fbc4a5821c9858ab9\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Contact</td>\r\n        <td>Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D078c5cd40a024563bfde3641352d4e43\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Contact}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Sales Quote List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('08f4949be17a4d90a6a3a75b05b17fdf','Report - Accounting - Aged Payables','<table width=\"100%\">\r\n    <tr>\r\n        <td class=\"black\">Contact</td>\r\n        <td class=\"black\">Date</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black right\">Total</td>\r\n        <td class=\"black right\">Balance</td>\r\n        <td class=\"black right\">Age</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td>{Contact}</td>\r\n        <td>{Date}</td>\r\n        <td>{Type}</td>\r\n        <td>{Number}</td>\r\n        <td class=\"right\">{Total}</td>\r\n        <td class=\"right\">{Balance}</td>\r\n        <td class=\"right\">{Age}</td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Aged Payables','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('18afbfdf422246b1a549f34dad94f8b7','Screen - Bootstrap - Splash','<p>\r\n	Copyright (C) 2016  Internetwork Consulting LLC\r\n<p>\r\n<p>\r\n	This program is free software: you can redistribute it and/or modify\r\n	it under the terms of the GNU General Public License as published by\r\n	the Free Software Foundation, version 3 of the License.\r\n</p>\r\n<p>\r\n	This program is distributed in the hope that it will be useful,\r\n	but WITHOUT ANY WARRANTY; without even the implied warranty of\r\n	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\r\n	GNU General Public License for more details.\r\n</p>\r\n<p>\r\n	You should have received a copy of the GNU General Public License\r\n	along with this program.  If not, see <a href=\"http://www.gnu.org/licenses/\">http://www.gnu.org/licenses/</a>.\r\n</p>\r\n\r\n<h2>Support</h2>\r\n<p>\r\n	Internetwork Consulting LLC has created this application in the hopes that it\r\n	can serve as a basis for a general purpose accounting system comparable to \r\n	Sage 50 or QuickBooks.  While we provide the software under a GNU License, we\r\n	hope to receive the customization, support, and development work in relation to\r\n	this application.\r\n</p>\r\n\r\n<p><b>Call Us: 832-606-3300!</b></p>','incAcccounting','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('1b739cb0b80649589760652098d0ff9c','List - Accounting - Department','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Department\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Department&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D1b739cb0b80649589760652098d0ff9c\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Department&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Department&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Department List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('290198ebc76e43cbb7be15de3e3d0ad7','List - Bootstrap - Groups','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=Group\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Group&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D290198ebc76e43cbb7be15de3e3d0ad7\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Group&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Group&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Group List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('2fc5847e6eba4c5784ce3258bd5b8f53','List - Accounting - Purchase Invoices','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&Document Types GUID=9d3821afd6fb47f9b2713d3cc574ceff\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Contact</td>\r\n        <td>Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D2fc5847e6eba4c5784ce3258bd5b8f53\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Contact}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Purchase Invoice List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('30357e5cfb164c29a741e77f3de1cce7','List - Accounting - Purchase Credit Memos','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&Document Types GUID=6632ec00f5824aeca4a49bf21cbdaece\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Contact</td>\r\n        <td>Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D30357e5cfb164c29a741e77f3de1cce7\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Contact}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Purchase Credit Memo List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('3293e7a3b08744c38975accc635d7573','List - Accounting - Sales Credit Memos','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&Document Types GUID=86af180c412f40c5a660678e3895694b\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Contact</td>\r\n        <td>Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D3293e7a3b08744c38975accc635d7573\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Contact}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Sales Credit Memo List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('33abdf4f68f44d73b23fabe400280576','List - Bootstrap - Users','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=User\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=User&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D33abdf4f68f44d73b23fabe400280576\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=User&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=User&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','User List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('352f588dfcd147fda29b8b4cdc2fcdfc','List - Accounting - Sales Invoices','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&Document Types GUID=81e2917ac5c34d1cb6f9d168cd0439db\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Contact</td>\r\n        <td>Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D352f588dfcd147fda29b8b4cdc2fcdfc\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Contact}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Sales Invoice List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('57808ea5bb1948d8a1ddce32a3233e10','List - Accounting - Contacts','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Contact\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Office</td>\r\n        <td>Mobile</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Contact&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D57808ea5bb1948d8a1ddce32a3233e10\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Contact&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Contact&GUID={GUID}\">{Office}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Contact&GUID={GUID}\">{Mobile}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Contact&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Contact List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('6552470b48204c06b7a23271b472b6b7','List - Accounting - Sales Tax Groups','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=SalesTax&Is Group=1\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Rate</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=SalesTax&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D766ef959263043c79244f932e49ff61d\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=SalesTax&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=SalesTax&GUID={GUID}\">{Rate}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Sales Tax Group List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('6d4ea7d4624e48509e915e019ca1f7a9','Report - Accounting - Trial Balance','<style>\r\n    table a { text-decoration: none; }\r\n</style>\r\n\r\n<table width=\"100%\">\r\n    <tr>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black\">Name</td>\r\n        <td class=\"black center\">Type</td>\r\n        <td class=\"black right\">Balance</td>\r\n    </tr>\r\n    <!-- BEGIN Account -->\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=ReportView&AutoLoad=t&GUID=ee4f00312ff44e3d941c6a3445bf93b0&As Of={As Of Date}&Account={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incBootstrap?App=ReportView&AutoLoad=t&GUID=ee4f00312ff44e3d941c6a3445bf93b0&As Of={As Of Date}&Account={GUID}\">{Name}</a></td>\r\n        <td class=\"center\"><a href=\"~/incBootstrap?App=ReportView&AutoLoad=t&GUID=ee4f00312ff44e3d941c6a3445bf93b0&As Of={As Of Date}&Account={GUID}\">{Account Types Name}</a></td>\r\n        <td class=\"right\"><a href=\"~/incBootstrap?App=ReportView&AutoLoad=t&GUID=ee4f00312ff44e3d941c6a3445bf93b0&As Of={As Of Date}&Account={GUID}\">{Balance}</a></td>\r\n    </tr>\r\n    <!-- END Account -->\r\n    <!-- BEGIN Footer -->\r\n    <tr>\r\n        <td colspan=\"3\" class=\"right black\">Trial Balance:</td>\r\n        <td class=\"right\">{Balance}</td>\r\n    </tr>\r\n    <!-- END Footer -->\r\n</table>','Trial Balance','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('6eeab128b44446b6b67556f7418298ba','List - Accounting - Payroll Checks','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=PayrollCheck\" class=\"add\">Add</a></td>\r\n        <td>Account</td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td class=\"right\">Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=PayrollCheck&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D84c704545c9140fb96ee5b9610fe632b\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=PayrollCheck&GUID={GUID}\">{Account}</a></td>\r\n        <td><a href=\"~/incAccounting?App=PayrollCheck&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=PayrollCheck&GUID={GUID}\">{Number}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=PayrollCheck&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Payroll Check List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('75ecee50436c4a7d8095b30f794f87f9','List - Bootstrap - Reports','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=Report\" class=\"add\">Add</a></td>\r\n        <td>Display Name</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Report&GUID={GUID}&&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D75ecee50436c4a7d8095b30f794f87f9\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Report&GUID={GUID}\">{Name}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Report List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('766ef959263043c79244f932e49ff61d','List - Accounting - Sales Tax Authorities','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=SalesTax\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Rate</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=SalesTax&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D766ef959263043c79244f932e49ff61d\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=SalesTax&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=SalesTax&GUID={GUID}\">{Rate}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Sales Tax Authorities List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('767a1911c68b4878a2ffc8dfd6974bc5','List - Bootstrap - Logs','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=Log\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Log&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D767a1911c68b4878a2ffc8dfd6974bc5\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Log&GUID={GUID}\">{Occured}</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Log&GUID={GUID}\">{User}</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Log&GUID={GUID}\">{Log}</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Log&GUID={GUID}\">{Details}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Log List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('7788774678b84145b3681511b0cc9d40','List - Accounting - Reconciliations','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Reconciliation\" class=\"add\">Add</a></td>\r\n        <td>Account</td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td class=\"right\">Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Reconciliation&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D7788774678b84145b3681511b0cc9d40\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Reconciliation&GUID={GUID}\">{Account}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Reconciliation&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Reconciliation&GUID={GUID}\">{Number}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Reconciliation&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Reconciliation List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('79b2fe6a9758417994b8fb59c25bc815','Screen - Bootstrap - Not Implimented','The feature or screen you are looing for is not yet impimented.','Not Implimented','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('7f8c6d9d299049779046ad7eb70884fa','List - Accounting - Purchase Orders','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&Document Types GUID=e56b2b6aa42b479890085b74b69275f3\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Contact</td>\r\n        <td>Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D7f8c6d9d299049779046ad7eb70884fa\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Contact}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Purchase Order List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('84c704545c9140fb96ee5b9610fe632b','List - Accounting - Bank Deposits','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Deposit\" class=\"add\">Add</a></td>\r\n        <td>Account</td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td class=\"right\">Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Deposit&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D84c704545c9140fb96ee5b9610fe632b\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Deposit&GUID={GUID}\">{Account}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Deposit&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Deposit&GUID={GUID}\">{Number}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Deposit&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Bank Deposit List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('88898672973041059e47961735e0c047','List - Accounting - Unit Mesures','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=UnitMeasure\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Abbreviation</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=UnitMeasure&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D88898672973041059e47961735e0c047\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=UnitMeasure&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=UnitMeasure&GUID={GUID}\">{Abbreviation}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=UnitMeasure&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Unit Measure List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('92ecd1b71cf24a9b82538fcd9b1bf136','List - Accounting - Document Types','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=DocumentType\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=DocumentType&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D92ecd1b71cf24a9b82538fcd9b1bf136\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=DocumentType&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=DocumentType&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Document Type List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('97c04a5ce5024ad4b2859908da8f6e1a','List - Accounting - Sales Orders','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&Document Types GUID=5f756fc5f7c5493ca0d86f2d0ead2fda\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Contact</td>\r\n        <td>Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3D97c04a5ce5024ad4b2859908da8f6e1a\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Contact}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Sales Order List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('a64f1e848b4b4b009f669e133d201478','List - Accounting - Employees','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Employee\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Office</td>\r\n        <td>Mobile</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Employee&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Da64f1e848b4b4b009f669e133d201478\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Employee&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Employee&GUID={GUID}\">{Home}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Employee&GUID={GUID}\">{Mobile}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Employee&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Employee List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('a7d53239fa82450db2e9d07594e7cce9','List - Accounting - Payroll Fields','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=PayrollField\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=PayrollField&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Da7d53239fa82450db2e9d07594e7cce9\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=PayrollField&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=PayrollField&GUID={GUID}\">{Type}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Payroll Field List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('a92a85f5192e4dbb80cba3a67ad31448','List - Accounting - Transactions','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Transaction\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Type</td>\r\n        <td>Amount</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Transaction&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Da92a85f5192e4dbb80cba3a67ad31448\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Transaction&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Transaction&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Transaction&GUID={GUID}\">{Type}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Transaction&GUID={GUID}\">{Amount}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Transaction List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('b48f778f1bbf4b87b7e37c97324d9b48','List - Accounting - Purchase Payments','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Payment&Payment Types GUID=a714a873202f4f12bb29a42ed8ed9b5c\" class=\"add\">Add</a></td>\r\n        <td>Type</td>\r\n        <td>Date</td>\r\n        <td>Our Number</td>\r\n        <td>Contact</td>\r\n        <td>Their Number</td>\r\n        <td class=\"right\">Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Db8f0ac2f50ba4d40b227c841846dee15\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Type}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Our Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Contact}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Their Number}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Purchase Payment List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('b88b0031fc2844eb874c5be27f8be003','Report - Accounting - Aged Receivables','<table width=\"100%\">\r\n    <tr>\r\n        <td class=\"black\">Contact</td>\r\n        <td class=\"black\">Date</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black right\">Total</td>\r\n        <td class=\"black right\">Balance</td>\r\n        <td class=\"black right\">Age</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td>{Contact}</td>\r\n        <td>{Date}</td>\r\n        <td>{Type}</td>\r\n        <td>{Number}</td>\r\n        <td class=\"right\">{Total}</td>\r\n        <td class=\"right\">{Balance}</td>\r\n        <td class=\"right\">{Age}</td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Aged Receivables','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('b8f0ac2f50ba4d40b227c841846dee15','List - Accounting - Sales Payments','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Payment&Payment Types GUID=2c12d6167d654604be3f533c38f1ad1e\" class=\"add\">Add</a></td>\r\n        <td>Type</td>\r\n        <td>Date</td>\r\n        <td>Our Number</td>\r\n        <td>Contact</td>\r\n        <td>Their Number</td>\r\n        <td class=\"right\">Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Db8f0ac2f50ba4d40b227c841846dee15\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Type}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Our Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Contact}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Their Number}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Payment&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Sales Payment List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('bdcddb7c80a34302a83a23ac1ed5d309','List - Bootstrap - Securables','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=Securable\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Securable&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Dbdcddb7c80a34302a83a23ac1ed5d309\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Securable&GUID={GUID}\">{Name}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Securable List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('c30ad5f313f44d15b9d5a75dcc7200ad','List - Accounting - Item','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Item\" class=\"add\">Add</a></td>\r\n        <td>Number</td>\r\n        <td>Description</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Item&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Dc30ad5f313f44d15b9d5a75dcc7200ad\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Item&GUID={GUID}\">{Number}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Item&GUID={GUID}\">{Description}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Item&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Department List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('c545d2f279be49e9a471a43033d01c7f','List - Bootstrap - Settings','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=Setting\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>User</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Setting&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Dc545d2f279be49e9a471a43033d01c7f\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Setting&GUID={GUID}\">{Key}</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Setting&GUID={GUID}\">{User}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Setting List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('cef027b31c704b17b9f97bc9489626cb','Report - Security - Group Membership','<!-- STOCK REPORT --><!-- BEGIN Groups -->\r\n<h2>{Display Name}</h2>\r\n<div class=\"columns\">\r\n<!-- BEGIN Users -->\r\n<p>\r\n<b>{Display Name}</b><br />\r\n{SQL User}<br />\r\n{Email Address}\r\n</p>\r\n<!-- END Users -->\r\n</div>\r\n<!-- END Groups -->','Group Membership','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('cefd88dd219a4b6f8bc6dd30fd77f058','List - Accounting - Purchase Quotes','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&Document Types GUID=dedf79eddf7c4e348918d42e25b53309\" class=\"add\">Add</a></td>\r\n        <td>Date</td>\r\n        <td>Number</td>\r\n        <td>Contact</td>\r\n        <td>Total</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Dcefd88dd219a4b6f8bc6dd30fd77f058\" class=\"delete\">Delete</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Number}</a></td>\r\n        <td><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Contact}</a></td>\r\n        <td class=\"right\"><a href=\"~/incAccounting?App=Document&GUID={GUID}\">{Total}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Purchase Quote List','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('d638b37131514266a0fa8df8dc0b39a6','List - Accounting - Job','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=Job\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Job&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Dd638b37131514266a0fa8df8dc0b39a6\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Job&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=Job&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Job List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('dd57e42a165047a38c5655dcc1b76576','List - Bootstrap - Computers','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incBootstrap?App=Computer\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Computer&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Ddd57e42a165047a38c5655dcc1b76576\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Computer&GUID={GUID}\">{Description}</a></td>\r\n        <td class=\"top\"><a href=\"~/incBootstrap?App=Computer&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Computer List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('ea6fdeb8d76247aa876dcab709b5de6e','Report - Accounting - Net Worth','<table width=\"100%\">\r\n    <tr><td colspan=\"4\"><h2>Assets</h2></td></tr>\r\n    <tr>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black\">Name</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black right\">Debits</td>\r\n    </tr>\r\n    <!-- BEGIN Asset -->\r\n    <tr>\r\n        <td>{Number}</td>\r\n        <td>{Name}</td>\r\n        <td>{Type}</td>\r\n        <td class=\"right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END Asset -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- BEGIN AssetTotal -->\r\n    <tr>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td class=\"black right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END AssetTotal -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <tr><td colspan=\"4\"><h2>Liabilities</h2></td></tr>\r\n    <tr>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black\">Name</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black right\">Debits</td>\r\n    </tr>\r\n    <!-- BEGIN Liability -->\r\n    <tr>\r\n        <td>{Number}</td>\r\n        <td>{Name}</td>\r\n        <td>{Type}</td>\r\n        <td class=\"right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END Liability -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- BEGIN LiabilityTotal -->\r\n    <tr>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td class=\"black right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END LiabilityTotal -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <tr><td colspan=\"4\"><h2>Net Worth</h2></td></tr>\r\n    <!-- BEGIN NetWorth -->\r\n    <tr>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td class=\"black right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END NetWorth -->\r\n</table>','Net Worth','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('ed6c823f890a470fa23b637dae910117','List - Accounting - Contact Types','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=ContactType\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=ContactType&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Ded6c823f890a470fa23b637dae910117\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=ContactType&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=ContactType&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Contact Type List','');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('ee4f00312ff44e3d941c6a3445bf93b0','Report - Accounting - Ledger','<style>\r\n    table a { text-decoration: none; }\r\n</style>\r\n\r\n<table width=\"100%\">\r\n    <tr>\r\n        <td class=\"black\">Date</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black\">Description</td>\r\n        <td class=\"black right\">Debit</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"{Edit URL}&GUID={GUID}\">{Date}</a></td>\r\n        <td class=\"top\"><a href=\"{Edit URL}&GUID={GUID}\">{Type}</a></td>\r\n        <td class=\"top\"><a href=\"{Edit URL}&GUID={GUID}\">{Number}</a></td>\r\n        <td class=\"top\"><a href=\"{Edit URL}&GUID={GUID}\">{Description}</a></td>\r\n        <td class=\"top right\"><a href=\"{Edit URL}&GUID={GUID}\">{Debit}</a></td>\r\n    </tr>\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- END Row -->\r\n</table>','Account Ledger','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('f64ba5f5752a4473a5946fe68ddde21f','Report - Accounting - Sales Tax - Cash','<style>\r\n    table a { text-decoration: none; }\r\n</style>\r\n\r\n<table width=\"100%\">\r\n    <tr>\r\n        <td class=\"black\">Contact</td>\r\n        <td class=\"black\">Date</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black right\">Taxable</td>\r\n        <td class=\"black right\">Nontaxable</td>\r\n        <td class=\"black right\">Taxes</td>\r\n        <td class=\"black right\">Total</td>\r\n        <td class=\"black right\">Rate</td>\r\n        <td class=\"black\">Name</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Contact}</a></td>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Date}</a></td>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">Payment</a></td>\r\n        <td><a href=\"{Edit URL}&GUID={GUID}\">{Number}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Taxable}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Nontaxable}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Taxes}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Pymt Total}</a></td>\r\n        <td class=\"right\"><a href=\"{Edit URL}&GUID={GUID}\">{Tax Rate}</a></td>\r\n        <td class=\"\"><a href=\"{Edit URL}&GUID={GUID}\">{Tax Name}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- BEGIN Total -->\r\n    <tr>\r\n        <td colspan=\"4\" class=\"right black\">&nbsp;</td>\r\n        <td class=\"right black\">{Taxable}</td>\r\n        <td class=\"right black\">{Nontaxable}</td>\r\n        <td class=\"right black\">{Taxes}</td>\r\n        <td class=\"right black\">{Total}</td>\r\n        <td colspan=\"2\">&nbsp;</td>\r\n    </tr>\r\n    <!-- END Total -->    \r\n</table>','Sales Tax Cash','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('f6c1313616f24bcd827863490e2a661b','Report - Accounting - Profit and Loss','<table width=\"100%\">\r\n    <tr><td colspan=\"4\"><h2>Revenue</h2></td></tr>\r\n    <tr>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black\">Name</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black right\">Debits</td>\r\n    </tr>\r\n    <!-- BEGIN Revenue -->\r\n    <tr>\r\n        <td>{Number}</td>\r\n        <td>{Name}</td>\r\n        <td>{Type}</td>\r\n        <td class=\"right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END Revenue -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- BEGIN RevenueTotal -->\r\n    <tr>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td class=\"black right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END RevenueTotal -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <tr><td colspan=\"4\"><h2>Expenses</h2></td></tr>\r\n    <tr>\r\n        <td class=\"black\">Number</td>\r\n        <td class=\"black\">Name</td>\r\n        <td class=\"black\">Type</td>\r\n        <td class=\"black right\">Debits</td>\r\n    </tr>\r\n    <!-- BEGIN Expense -->\r\n    <tr>\r\n        <td>{Number}</td>\r\n        <td>{Name}</td>\r\n        <td>{Type}</td>\r\n        <td class=\"right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END Expense -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <!-- BEGIN ExpenseTotal -->\r\n    <tr>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td class=\"black right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END ExpenseTotal -->\r\n    <tr><td>&nbsp;</td></tr>\r\n    <tr><td colspan=\"4\"><h2>Profit or Loss</h2></td></tr>\r\n    <!-- BEGIN PL -->\r\n    <tr>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td>&nbsp;</td>\r\n        <td class=\"black right\">{Debits}</td>\r\n    </tr>\r\n    <!-- END PL -->\r\n</table>','Profit and Loss','\0');
INSERT INTO `Reports` (`GUID`, `Display Name`, `HTML Template`, `Title`, `Auto Load`) VALUES ('fcb89d0333e4418f829803e34887edb7','List - Accounting - Payment Types','<table class=\"list\">\r\n    <tr>\r\n        <td><a href=\"~/incAccounting?App=PaymentType\" class=\"add\">Add</a></td>\r\n        <td>Name</td>\r\n        <td>Status</td>\r\n    </tr>\r\n    <!-- BEGIN Row -->\r\n    <tr>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=PaymentType&GUID={GUID}&Action=Delete&Return=~%2FincBootstrap%3FApp%3DReportView%26GUID%3Dfcb89d0333e4418f829803e34887edb7\" class=\"delete\">Delete</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=PaymentType&GUID={GUID}\">{Name}</a></td>\r\n        <td class=\"top\"><a href=\"~/incAccounting?App=PaymentType&GUID={GUID}\">{Status}</a></td>\r\n    </tr>\r\n    <!-- END Row -->\r\n</table>','Payment Type List','');
/*!40000 ALTER TABLE `Reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sales Tax Memberships`
--

DROP TABLE IF EXISTS `Sales Tax Memberships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sales Tax Memberships` (
  `Parent Sales Taxes GUID` char(32) NOT NULL,
  `Child Sales Taxes GUID` char(32) NOT NULL,
  UNIQUE KEY `Tax Memberships Unique Parent Child` (`Parent Sales Taxes GUID`,`Child Sales Taxes GUID`),
  KEY `Child Tax Memberships>Child Sales Tax` (`Child Sales Taxes GUID`),
  CONSTRAINT `Child Tax Memberships>Child Sales Tax` FOREIGN KEY (`Child Sales Taxes GUID`) REFERENCES `Sales Taxes` (`GUID`),
  CONSTRAINT `Parent Tax Memberships>Parent Sales Tax` FOREIGN KEY (`Parent Sales Taxes GUID`) REFERENCES `Sales Taxes` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sales Tax Memberships`
--

LOCK TABLES `Sales Tax Memberships` WRITE;
/*!40000 ALTER TABLE `Sales Tax Memberships` DISABLE KEYS */;
INSERT INTO `Sales Tax Memberships` (`Parent Sales Taxes GUID`, `Child Sales Taxes GUID`) VALUES ('6ab15fc300964e84bb5722553ad8276f','21871865910b4951992d4e0d1d71e7a2');
INSERT INTO `Sales Tax Memberships` (`Parent Sales Taxes GUID`, `Child Sales Taxes GUID`) VALUES ('6ab15fc300964e84bb5722553ad8276f','47614399eea942649c734059a79fcd2b');
INSERT INTO `Sales Tax Memberships` (`Parent Sales Taxes GUID`, `Child Sales Taxes GUID`) VALUES ('6ab15fc300964e84bb5722553ad8276f','e0c26ba8bd1b4454ae9a619b43aeb5f0');
/*!40000 ALTER TABLE `Sales Tax Memberships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sales Taxes`
--

DROP TABLE IF EXISTS `Sales Taxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sales Taxes` (
  `GUID` char(32) NOT NULL,
  `Contacts GUID` char(32) DEFAULT NULL,
  `Is Group` bit(1) NOT NULL,
  `Display Name` varchar(128) DEFAULT NULL,
  `Tax Rate` decimal(64,16) NOT NULL,
  `Accounts GUID` char(32) DEFAULT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Display Name` (`Display Name`),
  KEY `Sales Taxes>Contact` (`Contacts GUID`),
  KEY `Sales Taxes>Account` (`Accounts GUID`),
  CONSTRAINT `Sales Taxes>Account` FOREIGN KEY (`Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Sales Taxes>Contact` FOREIGN KEY (`Contacts GUID`) REFERENCES `Contacts` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sales Taxes`
--

LOCK TABLES `Sales Taxes` WRITE;
/*!40000 ALTER TABLE `Sales Taxes` DISABLE KEYS */;
INSERT INTO `Sales Taxes` (`GUID`, `Contacts GUID`, `Is Group`, `Display Name`, `Tax Rate`, `Accounts GUID`) VALUES ('21871865910b4951992d4e0d1d71e7a2','fb5108cb45414e01bea8fc968c2f6556','\0','3101990 -  HOUSTON MTA',0.0100000000000000,NULL);
INSERT INTO `Sales Taxes` (`GUID`, `Contacts GUID`, `Is Group`, `Display Name`, `Tax Rate`, `Accounts GUID`) VALUES ('47614399eea942649c734059a79fcd2b','fb5108cb45414e01bea8fc968c2f6556','\0','STATE SALES TAX',0.0625000000000000,NULL);
INSERT INTO `Sales Taxes` (`GUID`, `Contacts GUID`, `Is Group`, `Display Name`, `Tax Rate`, `Accounts GUID`) VALUES ('502fd04bc5da462f98d013dfa50d808e',NULL,'','Exempt',0.0000000000000000,'e93871fccc0c427d8cb32456c84eee21');
INSERT INTO `Sales Taxes` (`GUID`, `Contacts GUID`, `Is Group`, `Display Name`, `Tax Rate`, `Accounts GUID`) VALUES ('6ab15fc300964e84bb5722553ad8276f','fb5108cb45414e01bea8fc968c2f6556','','Default',0.0825000000000000,'e93871fccc0c427d8cb32456c84eee21');
INSERT INTO `Sales Taxes` (`GUID`, `Contacts GUID`, `Is Group`, `Display Name`, `Tax Rate`, `Accounts GUID`) VALUES ('e0c26ba8bd1b4454ae9a619b43aeb5f0','fb5108cb45414e01bea8fc968c2f6556','\0','4516985 - HOUSTON ESD',0.0100000000000000,NULL);
/*!40000 ALTER TABLE `Sales Taxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Securables`
--

DROP TABLE IF EXISTS `Securables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Securables` (
  `GUID` char(32) NOT NULL,
  `Display Name` varchar(64) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Display Name` (`Display Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Securables`
--

LOCK TABLES `Securables` WRITE;
/*!40000 ALTER TABLE `Securables` DISABLE KEYS */;
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('bbb9a35380834fe9976ad7184976f0d6','Bank Deposit');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('c8276ec6738a4fc0bb0fe7af7815f045','Control - Options');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('6632ec00f5824aeca4a49bf21cbdaece','Document - Purchase Credit');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('9d3821afd6fb47f9b2713d3cc574ceff','Document - Purchase Invoice');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('e56b2b6aa42b479890085b74b69275f3','Document - Purchase Order');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('dedf79eddf7c4e348918d42e25b53309','Document - Purchase Quote');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('86af180c412f40c5a660678e3895694b','Document - Sales Credit');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('81e2917ac5c34d1cb6f9d168cd0439db','Document - Sales Invoice');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('5f756fc5f7c5493ca0d86f2d0ead2fda','Document - Sales Order');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('276db4afcf634b6fbc4a5821c9858ab9','Document - Sales Quote');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('a714a873202f4f12bb29a42ed8ed9b5c','Payment - Purchase');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('2c12d6167d654604be3f533c38f1ad1e','Payment - Sales');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('06db3e1b55e54bc2babf0424e0843d5b','Report - List - Accounting - Accounts');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('84c704545c9140fb96ee5b9610fe632b','Report - List - Accounting - Bank Deposits');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('ed6c823f890a470fa23b637dae910117','Report - List - Accounting - Contact Types');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('57808ea5bb1948d8a1ddce32a3233e10','Report - List - Accounting - Contacts');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('1b739cb0b80649589760652098d0ff9c','Report - List - Accounting - Department');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('92ecd1b71cf24a9b82538fcd9b1bf136','Report - List - Accounting - Document Types');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('a64f1e848b4b4b009f669e133d201478','Report - List - Accounting - Employees');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('c30ad5f313f44d15b9d5a75dcc7200ad','Report - List - Accounting - Item');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('d638b37131514266a0fa8df8dc0b39a6','Report - List - Accounting - Job');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('fcb89d0333e4418f829803e34887edb7','Report - List - Accounting - Payment Types');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('6eeab128b44446b6b67556f7418298ba','Report - List - Accounting - Payroll Checks');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('a7d53239fa82450db2e9d07594e7cce9','Report - List - Accounting - Payroll Fields');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('30357e5cfb164c29a741e77f3de1cce7','Report - List - Accounting - Purchase Credit Memos');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('2fc5847e6eba4c5784ce3258bd5b8f53','Report - List - Accounting - Purchase Invoices');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('7f8c6d9d299049779046ad7eb70884fa','Report - List - Accounting - Purchase Orders');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('b48f778f1bbf4b87b7e37c97324d9b48','Report - List - Accounting - Purchase Payments');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('cefd88dd219a4b6f8bc6dd30fd77f058','Report - List - Accounting - Purchase Quotes');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('7788774678b84145b3681511b0cc9d40','Report - List - Accounting - Reconciliations');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('3293e7a3b08744c38975accc635d7573','Report - List - Accounting - Sales Credit Memos');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('352f588dfcd147fda29b8b4cdc2fcdfc','Report - List - Accounting - Sales Invoices');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('97c04a5ce5024ad4b2859908da8f6e1a','Report - List - Accounting - Sales Orders');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('b8f0ac2f50ba4d40b227c841846dee15','Report - List - Accounting - Sales Payments');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('078c5cd40a024563bfde3641352d4e43','Report - List - Accounting - Sales Quotes');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('766ef959263043c79244f932e49ff61d','Report - List - Accounting - Sales Tax Authorities');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('6552470b48204c06b7a23271b472b6b7','Report - List - Accounting - Sales Tax Groups');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('a92a85f5192e4dbb80cba3a67ad31448','Report - List - Accounting - Transactions');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('88898672973041059e47961735e0c047','Report - List - Accounting - Unit Mesures');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('dd57e42a165047a38c5655dcc1b76576','Report - List - Bootstrap - Computers');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('290198ebc76e43cbb7be15de3e3d0ad7','Report - List - Bootstrap - Groups');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('767a1911c68b4878a2ffc8dfd6974bc5','Report - List - Bootstrap - Logs');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('75ecee50436c4a7d8095b30f794f87f9','Report - List - Bootstrap - Reports');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('bdcddb7c80a34302a83a23ac1ed5d309','Report - List - Bootstrap - Securables');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('c545d2f279be49e9a471a43033d01c7f','Report - List - Bootstrap - Settings');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('33abdf4f68f44d73b23fabe400280576','Report - List - Bootstrap - Users');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('08f4949be17a4d90a6a3a75b05b17fdf','Report - Report - Accounting - Aged Payables');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('b88b0031fc2844eb874c5be27f8be003','Report - Report - Accounting - Aged Receivables');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('ee4f00312ff44e3d941c6a3445bf93b0','Report - Report - Accounting - Ledger');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('ea6fdeb8d76247aa876dcab709b5de6e','Report - Report - Accounting - Net Worth');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('f6c1313616f24bcd827863490e2a661b','Report - Report - Accounting - Profit and Loss');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('01f2dff753214482b4a1ac02f6640914','Report - Report - Accounting - Sales Tax - Accural');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('f64ba5f5752a4473a5946fe68ddde21f','Report - Report - Accounting - Sales Tax - Cash');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('6d4ea7d4624e48509e915e019ca1f7a9','Report - Report - Accounting - Trial Balance');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('cef027b31c704b17b9f97bc9489626cb','Report - Report - Security - Group Membership');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('79b2fe6a9758417994b8fb59c25bc815','Report - Screen - Bootstrap - Not Implimented');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('18afbfdf422246b1a549f34dad94f8b7','Report - Screen - Bootstrap - Splash');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('720cdbbebae7124d2c3fdc1b46664655','Table - Account Types');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('9b945efebb006547a94415eadaa12921','Table - Accounts');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('45888a4da062f16a099a7f7c6cc15ee0','Table - Computers');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('74296745c146fc4ffc4afda0f19f1f2c','Table - Contact Notes');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('2a85420faee85c0a1aa204a3ee713ba4','Table - Contact Types');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('9aa698f602b1e5694855cee73a683488','Table - Contacts');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('6332798b12e537b25b1c6ad254e14f54','Table - Conversions');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('c8cf2b64be19b0234578a5b582f86a87','Table - Departments');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('965f19fd66f3bbd02a54f47952b68083','Table - Document Lines');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('1c8d2e4380181d9b2c0429dce7378d38','Table - Document Types');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('f28128b38efbc6134dc40751ee21fd29','Table - Documents');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('a37ede293936e29279ed543129451ec3','Table - Groups');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('9dea4016dbcc290b773ab2fae678aaa8','Table - Items');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('12ceff2290bb9039beaa8f36d5dec226','Table - Jobs');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('b2d37ae1cedf42ff874289b721860af2','Table - Logs');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('a7a7b26872b3e2d00de7bb7b1452b5a8','Table - Memberships');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('d08ccf52b4cdd08e41cfb99ec42e0b29','Table - Permissions');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('4a8a1dc4a61260a1d51e3b9f8bb5f18f','Table - Reconciliations');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('66b3ea16f68c96570fad11647b1fba50','Table - Report Blocks');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('755a0e7f295f45dacc4af7776f5150f3','Table - Report Filters');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('c91c7b93c28cd18741b71f727ee81ee3','Table - Reports');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('aa0a678d8950cf58d676ff1df2aa08ce','Table - Sales Tax Memberships');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('b748fae7af491847c7a3fcb4db6e13b1','Table - Securables');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('f4f70727dc34561dfde1a3c529b6205c','Table - Settings');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('e4eed2a7d7b7558dfe63d4f4fd18ce67','Table - Transaction Lines');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('d1e2f3dc6323be332c0590e0496f63ac','Table - Transaction Types');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('31112aca11d0e9e6eb7db96f317dda49','Table - Transactions');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('7b75551eba1477306b17861d66595527','Table - Unit Measures');
INSERT INTO `Securables` (`GUID`, `Display Name`) VALUES ('f9aae5fda8d810a29f12d1e61b4ab25f','Table - Users');
/*!40000 ALTER TABLE `Securables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Settings`
--

DROP TABLE IF EXISTS `Settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Settings` (
  `GUID` char(32) NOT NULL,
  `Users GUID` char(32) DEFAULT NULL,
  `Key` varchar(64) NOT NULL,
  `Type` varchar(64) NOT NULL,
  `Option Query` text,
  `Value` text NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Unique Settings User` (`Key`,`Users GUID`),
  KEY `User<Settings` (`Users GUID`),
  CONSTRAINT `User<Settings` FOREIGN KEY (`Users GUID`) REFERENCES `Users` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Settings`
--

LOCK TABLES `Settings` WRITE;
/*!40000 ALTER TABLE `Settings` DISABLE KEYS */;
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('14d3dcc01a5e43dcb1e9c1ac8c4a6e5f',NULL,'Document - Last Purchase Order Number','String',NULL,'1002');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('2e12bc7dc5e341ef85b7d36ff3780c5f',NULL,'Document - Rate Decimals','Number',NULL,'4');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('304d586e860949618823020b31726375',NULL,'Document - Last Purchase Invoice Number','String',NULL,'1001');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('3c96991313f8469ca3eec199532fa54e',NULL,'Document - Terms','String',NULL,'COD');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('4782e6b9f5b5456bb157b8c8808b79d7',NULL,'Password Length (1-4)','Number',NULL,'6');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('51658f8886794c7a9cfde896cb3c4128',NULL,'Password Complexity (1-4)','Number',NULL,'3');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('573d17208b0548e7826cc6946dc6aed2',NULL,'Transaction Line Setting Decimals','Number',NULL,'2');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('5a7ff3d2306649f9ad5ba3b643c7e3fb',NULL,'Document - Last Purchase Quote Number','String',NULL,'1000');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('5b348af08ed74449b6e8d9f8fde0b9d9',NULL,'Check MAC Address','Boolean',NULL,'F');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('6ecf14ce9cb44d4994d40ab18466fd28',NULL,'Enforce Computers','Boolean',NULL,'1');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('717fab529e644612bbafe4157f63468c',NULL,'Document - Last Sales Credit Number','String',NULL,'1000');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('765cc625a13b4bea8f0ebdbd2099c49a',NULL,'Version Number','String',NULL,'2016.1.5.dev');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('83eebc02afee4255989393142d06b14a',NULL,'Document - Last Sales Quote Number','String',NULL,'1001');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('868df1649d644a5bbdbe57745b4ba7d7',NULL,'Document - Last Sales Invoice Number','String',NULL,'1002');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('883bd957572f4b2fb2b1d1b4fb78b63e',NULL,'Document - Money Decimals','Number',NULL,'2');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('93d1c19c5ebb40c295863f539483f7bd',NULL,'Document - Last Sales Order Number','String',NULL,'1000');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('9854cf707039487196a2b33a0ba53b15',NULL,'Segment Seperator Enabled','Boolean',NULL,'f');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('9db50006ecfc41d2b9e0b845b896ad66',NULL,'Document - Last Purchase Credit Number','String',NULL,'1000');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('a59ed55dc2e347a0a9df8a9cf698f2a0',NULL,'History Length','Number',NULL,'4');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('b439fbc1bef9474fb3bf9d4136b21731',NULL,'Conversions - Decimals','Number',NULL,'8');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('b68d89042c744346a3b06ab894f336c7',NULL,'Segment Seperator Symbol','String',NULL,'-');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('c80614f4218847ebac11e1bfebf6fb7d',NULL,'Document - Post on Save','Boolean',NULL,'T');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('d6641698125946c5a109a96c06c4fd9b',NULL,'Document - Quantity Decimals','Number',NULL,'2');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('eeb78b46cc624b9a9a39e1083dd06bec',NULL,'Password Age (Days)','Number',NULL,'45');
INSERT INTO `Settings` (`GUID`, `Users GUID`, `Key`, `Type`, `Option Query`, `Value`) VALUES ('f53b854831174c6d978624930c142a4b',NULL,'Document - Due Days','Number',NULL,'0');
/*!40000 ALTER TABLE `Settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transaction Lines`
--

DROP TABLE IF EXISTS `Transaction Lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Transaction Lines` (
  `GUID` char(32) NOT NULL,
  `Transactions GUID` char(32) NOT NULL,
  `Sort Order` int(11) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Debit` decimal(64,16) NOT NULL,
  `Jobs GUID` char(32) DEFAULT NULL,
  `Departments GUID` char(32) DEFAULT NULL,
  `Accounts GUID` char(32) NOT NULL,
  `Reconciliations GUID` char(32) DEFAULT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Transaction Lines Unique Sort Order` (`Transactions GUID`,`Sort Order`),
  KEY `Transaction Lines>Account` (`Accounts GUID`),
  KEY `Transaction Lines>Reconciliation` (`Reconciliations GUID`),
  KEY `Transaction Lines>Job` (`Jobs GUID`),
  KEY `Transaction Lines>Department` (`Departments GUID`),
  CONSTRAINT `Transaction Lines>Account` FOREIGN KEY (`Accounts GUID`) REFERENCES `Accounts` (`GUID`),
  CONSTRAINT `Transaction Lines>Department` FOREIGN KEY (`Departments GUID`) REFERENCES `Departments` (`GUID`),
  CONSTRAINT `Transaction Lines>Job` FOREIGN KEY (`Jobs GUID`) REFERENCES `Jobs` (`GUID`),
  CONSTRAINT `Transaction Lines>Reconciliation` FOREIGN KEY (`Reconciliations GUID`) REFERENCES `Reconciliations` (`GUID`),
  CONSTRAINT `Transaction Lines>Transaction` FOREIGN KEY (`Transactions GUID`) REFERENCES `Transactions` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction Lines`
--

LOCK TABLES `Transaction Lines` WRITE;
/*!40000 ALTER TABLE `Transaction Lines` DISABLE KEYS */;
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('0345b66006344d409db8f7a52562c85a','6e1701097c104c049bed06a1357807d5',4,'ASRock B150M-HDV, MicroATX, LGA1151, 2 x DDR4 288 pin, 1 x PCIx16, 2 x PCIx1, 6 x SATA 6Gbps, Sound, VGA, DVI, HDMI, 6 x USB',299.9500000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('0ecf04fbbb474f629e3281e28181e1b3','1193f11925d94a85a702f03567f63091',1,'Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630',-189.0000000000000000,NULL,NULL,'a94bb67059ca4cf7801470b485d5f361',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('0f6ab5b307f447a28a1a4045950f1880','327d0128b64d4750a75edf413d918a16',0,'450+ Watt ATX Power Supply\r\n4 x SATA Powers',-22.9900000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('144ef2109b874da6ac3e322b2054f504','bedb7ae220f24eca90d8fe9f49c91c92',2,'1000 Shares',-5000.0000000000000000,NULL,NULL,'f1505bc01bbb4c66adaeec6fabad99b6',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('1611e7becf6c4ad599d06a88cc9c2ce9','3ead7e8f5ded4883ad12104484b322af',1,'Sales Payment 1004; Their Number null',443.5500000000000000,NULL,NULL,'c4ced50bf99c442a9398bc84ab2bf908',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('19dc63c0c99148ba8b5d06cf72c4bfa8','609b536c31e24bff91288a9544f8fc28',0,'Purchase Invoice 1001; Their Number null',2000.0000000000000000,NULL,NULL,'9ef18c0e6aa246c283bd812aa0ff405c',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('1dea1e8de6564b67b465ab6f35b5da58','609b536c31e24bff91288a9544f8fc28',1,'Purchase Payment 1003; Their Number null',-2000.0000000000000000,NULL,NULL,'3bed1cad8e4c43c792f35ab9fb582718',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('283f56bd993d410c98955cb5c639cb1a','6e1701097c104c049bed06a1357807d5',6,'SanDisk 480G SSD',3750.0000000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('40ba8fd82e6c4df4a10c20bbb9ae4f68','bc19746b5b244f588b768998b4a0a39c',1,'Purchase Payment 1002; Their Number null',-2613.4000000000000000,NULL,NULL,'3bed1cad8e4c43c792f35ab9fb582718',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('425d0864984d423c92e4aecca3abd4f6','327d0128b64d4750a75edf413d918a16',3,'Sales Tax Default',-2.4700000000000000,NULL,NULL,'e93871fccc0c427d8cb32456c84eee21',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('43ed2a72bf34419e8b1b33229f168fef','bedb7ae220f24eca90d8fe9f49c91c92',4,'1000 Shares',-5000.0000000000000000,NULL,NULL,'045a452dcedb49e39d07d091413297ce',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('45836e7d5a8f45dcb936ceacf06985ef','bedb7ae220f24eca90d8fe9f49c91c92',1,'Initial Deposit',500.0000000000000000,NULL,NULL,'ba1af79553d2419d8eeacff403c95046',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('4fc66d5d30d44749aff7fec50d37174b','daf5303bfaa64dd68892afe873d848b4',0,'Sales Invoice 1001; Their Number null',-146.3600000000000000,NULL,NULL,'6e44f65d6ed848a2bb2003820f3b39ef',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('539010e0230c46538d263a313072f5a8','6e1701097c104c049bed06a1357807d5',2,'Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630',1574.9500000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('5faf69d29dcf458d89a8b78ca3129fb4','daf5303bfaa64dd68892afe873d848b4',1,'Sales Payment 1001; Their Number 1564',146.3600000000000000,NULL,NULL,'c4ced50bf99c442a9398bc84ab2bf908',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('66bff4b3db9247b4927280e0833df77e','6e1701097c104c049bed06a1357807d5',7,'SATA Cable',17.1000000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('6843224d71a6481a90ce1eee78866423','1193f11925d94a85a702f03567f63091',3,'Invoice 1002 Total',886.5500000000000000,NULL,NULL,'6e44f65d6ed848a2bb2003820f3b39ef',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('92b8a90a27764a5da10db8537ebdb9d7','3ead7e8f5ded4883ad12104484b322af',0,'Sales Invoice 1002; Their Number null',-443.5500000000000000,NULL,NULL,'6e44f65d6ed848a2bb2003820f3b39ef',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('99f0cc52e47247ee842e754128ed48ab','6e1701097c104c049bed06a1357807d5',9,'Invoice 1001 Total',-7226.8000000000000000,NULL,NULL,'9ef18c0e6aa246c283bd812aa0ff405c',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('a84463f79e4c41679f5706a246e8e572','6e1701097c104c049bed06a1357807d5',3,'Black MicoATX Case\r\n2x USB Front, 3 x 5.25\" External',154.9500000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('ae171fae8b58417a9b34624739cd5d65','327d0128b64d4750a75edf413d918a16',2,'Labor',-114.0000000000000000,NULL,NULL,'f18d72cbb8f842fd9194eeb2f0a6b189',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('c04ac6eafcf94e439fe1cf9dc8ead89d','bedb7ae220f24eca90d8fe9f49c91c92',3,'1000 Shares',-5000.0000000000000000,NULL,NULL,'9f04b72d6cee49a29d85f283d2d9e5bb',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('c13a114299cd4cb7ac6183a0ed18f0e2','bedb7ae220f24eca90d8fe9f49c91c92',0,'Initial Deposit',19500.0000000000000000,NULL,NULL,'3bed1cad8e4c43c792f35ab9fb582718',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('c175a2cc8e9f4eb583a52503e0430762','327d0128b64d4750a75edf413d918a16',4,'Invoice 1001 Total',146.3600000000000000,NULL,NULL,'6e44f65d6ed848a2bb2003820f3b39ef',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('d840897393fd48ae96c294b8af02a7fe','6e1701097c104c049bed06a1357807d5',0,'450+ Watt ATX Power Supply\r\n4 x SATA Powers',114.9500000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('db055123a8844d6ab4fdf137e62b2d75','6e1701097c104c049bed06a1357807d5',5,'Icy Dock MB326SP-B\r\n1 x 5.25\" to 6 x 2.5\"\r\nSATA Hot-Swap',269.9500000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('e235de6cddaa4634934562b699ffd2aa','bc19746b5b244f588b768998b4a0a39c',0,'Purchase Invoice 1001; Their Number null',2613.4000000000000000,NULL,NULL,'9ef18c0e6aa246c283bd812aa0ff405c',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('effcd9737a604edf864436695cbb81fa','1193f11925d94a85a702f03567f63091',0,'Intel I7-7700 8MB\r\n4 x 3.6Ghz @ 65W\r\nIntel HD Graphics 630',-629.9800000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('f8de00f412a54085934e2bfdc5d0ade9','bedb7ae220f24eca90d8fe9f49c91c92',5,'1000 Shares',-5000.0000000000000000,NULL,NULL,'3baa2153e37945ff86c73f9a3c961686',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('fcda781252ff4a238c054628d2121937','6e1701097c104c049bed06a1357807d5',1,'2 x 16GB DDR4',1044.9500000000000000,NULL,NULL,'f7a4eaae78a149abb82f09b45fde6626',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('fd675f75f5b34d60865f961974e586fa','1193f11925d94a85a702f03567f63091',2,'Sales Tax Default',-67.5700000000000000,NULL,NULL,'e93871fccc0c427d8cb32456c84eee21',NULL);
INSERT INTO `Transaction Lines` (`GUID`, `Transactions GUID`, `Sort Order`, `Description`, `Debit`, `Jobs GUID`, `Departments GUID`, `Accounts GUID`, `Reconciliations GUID`) VALUES ('fd734787982b48d1a9870103d59abbad','327d0128b64d4750a75edf413d918a16',1,'450+ Watt ATX Power Supply\r\n4 x SATA Powers',-6.9000000000000000,NULL,NULL,'a94bb67059ca4cf7801470b485d5f361',NULL);
/*!40000 ALTER TABLE `Transaction Lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transaction Types`
--

DROP TABLE IF EXISTS `Transaction Types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Transaction Types` (
  `GUID` char(32) NOT NULL,
  `Name` varchar(64) NOT NULL,
  `Edit URL` varchar(255) NOT NULL,
  `List URL` varchar(255) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transaction Types`
--

LOCK TABLES `Transaction Types` WRITE;
/*!40000 ALTER TABLE `Transaction Types` DISABLE KEYS */;
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('14a778aeb6e04b7fa1dd31e4445e9b18','Payroll Check','/incAccounting?App=PayrollCheck','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Payroll Checks','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('1d648ca26c9c40c59e1483aa262656c2','Transaction','/incAccounting?App=Transaction','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Transactions','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('276db4afcf634b6fbc4a5821c9858ab9','Sales Quote','/incAccounting?App=Document','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Quotes','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('2c12d6167d654604be3f533c38f1ad1e','Sales Payment','/incAccounting?App=Payment','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Payments','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('5f756fc5f7c5493ca0d86f2d0ead2fda','Sales Order','/incAccounting?App=Document','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Orders','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('6632ec00f5824aeca4a49bf21cbdaece','Purchase Credit','/incAccounting?App=Document','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Credit Memos','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('81e2917ac5c34d1cb6f9d168cd0439db','Sales Invoice','/incAccounting?App=Document','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Invoices','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('86af180c412f40c5a660678e3895694b','Sales Credit','/incAccounting?App=Document','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Sales Credit Memos','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('9d3821afd6fb47f9b2713d3cc574ceff','Purchase Invoice','/incAccounting?App=Document','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Invoices','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('a714a873202f4f12bb29a42ed8ed9b5c','Purchase Payment','/incAccounting?App=Payment','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Payments','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('bbb9a35380834fe9976ad7184976f0d6','Bank Deposit','/incAccounting?App=Deposit','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Bank Deposits','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('dedf79eddf7c4e348918d42e25b53309','Purchase Quote','/incAccounting?App=Document','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Quotes','');
INSERT INTO `Transaction Types` (`GUID`, `Name`, `Edit URL`, `List URL`, `Is Allowed`) VALUES ('e56b2b6aa42b479890085b74b69275f3','Purchase Order','/incAccounting?App=Document','~/incBootstrap?App=ReportView&Display Name=List - Accounting - Purchase Orders','');
/*!40000 ALTER TABLE `Transaction Types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transactions`
--

DROP TABLE IF EXISTS `Transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Transactions` (
  `GUID` char(32) NOT NULL,
  `Date` date NOT NULL,
  `Reference Number` varchar(128) NOT NULL,
  `Transaction Types GUID` char(32) DEFAULT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Transactionds Unique Reference Number` (`Transaction Types GUID`,`Reference Number`),
  CONSTRAINT `Transactions>Transaction Type` FOREIGN KEY (`Transaction Types GUID`) REFERENCES `Transaction Types` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transactions`
--

LOCK TABLES `Transactions` WRITE;
/*!40000 ALTER TABLE `Transactions` DISABLE KEYS */;
INSERT INTO `Transactions` (`GUID`, `Date`, `Reference Number`, `Transaction Types GUID`) VALUES ('1193f11925d94a85a702f03567f63091','2017-01-11','1002','81e2917ac5c34d1cb6f9d168cd0439db');
INSERT INTO `Transactions` (`GUID`, `Date`, `Reference Number`, `Transaction Types GUID`) VALUES ('327d0128b64d4750a75edf413d918a16','2017-02-26','1001','81e2917ac5c34d1cb6f9d168cd0439db');
INSERT INTO `Transactions` (`GUID`, `Date`, `Reference Number`, `Transaction Types GUID`) VALUES ('3ead7e8f5ded4883ad12104484b322af','2017-03-09','1004','2c12d6167d654604be3f533c38f1ad1e');
INSERT INTO `Transactions` (`GUID`, `Date`, `Reference Number`, `Transaction Types GUID`) VALUES ('609b536c31e24bff91288a9544f8fc28','2017-01-15','1003','a714a873202f4f12bb29a42ed8ed9b5c');
INSERT INTO `Transactions` (`GUID`, `Date`, `Reference Number`, `Transaction Types GUID`) VALUES ('6e1701097c104c049bed06a1357807d5','2017-01-05','1001','9d3821afd6fb47f9b2713d3cc574ceff');
INSERT INTO `Transactions` (`GUID`, `Date`, `Reference Number`, `Transaction Types GUID`) VALUES ('bc19746b5b244f588b768998b4a0a39c','2017-02-02','1002','a714a873202f4f12bb29a42ed8ed9b5c');
INSERT INTO `Transactions` (`GUID`, `Date`, `Reference Number`, `Transaction Types GUID`) VALUES ('bedb7ae220f24eca90d8fe9f49c91c92','2017-01-01','BB20070715','1d648ca26c9c40c59e1483aa262656c2');
INSERT INTO `Transactions` (`GUID`, `Date`, `Reference Number`, `Transaction Types GUID`) VALUES ('daf5303bfaa64dd68892afe873d848b4','2017-03-01','1001','2c12d6167d654604be3f533c38f1ad1e');
/*!40000 ALTER TABLE `Transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Unit Measures`
--

DROP TABLE IF EXISTS `Unit Measures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Unit Measures` (
  `GUID` char(32) NOT NULL,
  `Display Name` varchar(128) NOT NULL,
  `Abbreviation` varchar(8) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Display Name` (`Display Name`),
  UNIQUE KEY `Abbreviation` (`Abbreviation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Unit Measures`
--

LOCK TABLES `Unit Measures` WRITE;
/*!40000 ALTER TABLE `Unit Measures` DISABLE KEYS */;
INSERT INTO `Unit Measures` (`GUID`, `Display Name`, `Abbreviation`, `Is Allowed`) VALUES ('aa3bd32ce48d4fddbdb40c42fdbcb551','Each','ea','');
/*!40000 ALTER TABLE `Unit Measures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `GUID` char(32) NOT NULL,
  `Is Allowed` bit(1) NOT NULL,
  `Display Name` varchar(64) NOT NULL,
  `SQL User` varchar(64) NOT NULL,
  `Email Address` varchar(64) NOT NULL,
  `Password Date` date NOT NULL,
  `Add Computer` bit(1) NOT NULL,
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `Display Name` (`Display Name`),
  UNIQUE KEY `SQL User` (`SQL User`),
  UNIQUE KEY `Email Address` (`Email Address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` (`GUID`, `Is Allowed`, `Display Name`, `SQL User`, `Email Address`, `Password Date`, `Add Computer`) VALUES ('186ccf6bb3174efabd1602ef63181ecb','','Jason McNew','jason','jason@localhost','1969-12-31','\0');
INSERT INTO `Users` (`GUID`, `Is Allowed`, `Display Name`, `SQL User`, `Email Address`, `Password Date`, `Add Computer`) VALUES ('86b41969e95143c090fd93a4819c58a2','','Administrator','administrator','administrator@localhost','2017-02-21','\0');
INSERT INTO `Users` (`GUID`, `Is Allowed`, `Display Name`, `SQL User`, `Email Address`, `Password Date`, `Add Computer`) VALUES ('dac3fce70c3645ac9874915c8dfa89d9','','Shawn Samson','shawn','shawn@localhost','1969-12-31','\0');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-10  9:48:11
