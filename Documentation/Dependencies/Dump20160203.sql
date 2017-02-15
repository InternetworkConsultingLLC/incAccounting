-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: 127.0.0.1    Database: ibs
-- ------------------------------------------------------
-- Server version	5.7.10

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
-- Dumping data for table `Accounts`
--

LOCK TABLES `Accounts` WRITE;
/*!40000 ALTER TABLE `Accounts` DISABLE KEYS */;
INSERT INTO `Accounts` VALUES 
('01cca9fd623644f5a7bc7a0d8044d5b7','cf190cadde734f98a348d5f6bd112db6','f2d6b11ef27243c4a84c7b505f7e2947','1','1-2-1-2-1','Deperciation','Assets - Long Term - Fixed Assets - Vehicle T6Z93P - Deperciation',''),
('04895bc4a4854d94897d79e78ce0c0f2','ade6405dd31d40169b7ed222ecaa6b9e','694eb089c8ec4d87bd3666bd0701d6c9','3','5-2-1-3','Labor','Expenses - Operating - Administration - Labor',''),
('06664f2b01334dc783da3c5b3098d4ee','ade6405dd31d40169b7ed222ecaa6b9e','b4f9a532cb7f4d869f3a5bfcd3d00976','4','5-2-4','Employee','Expenses - Operating - Employee',''),
('071034a014714697b017ab1ea55156bd','ade6405dd31d40169b7ed222ecaa6b9e','694eb089c8ec4d87bd3666bd0701d6c9','6','5-2-1-6','Supplies','Expenses - Operating - Administration - Supplies',''),
('0908cf7f59c84e5f9aab4c2a632972ce','2ddbad1cf8d04f0d9c44fa9771a15306',NULL,'4','4','Revenue','Revenue',''),
('0b4c41c19a474ba1a1ecf6d91a6f8ee7','ade6405dd31d40169b7ed222ecaa6b9e','98f56066f72b45f5ab4fa97b39ef6735','2','5-2-2-2','Tolls','Expenses - Operating - Logistics - Tolls',''),
('0bb8559c95b7416eacabbee0fde3b86f','ddc9a0f637b64af2adeb19d0f6399e19','41371ecaaea349aa9a37665941f68c18','1','2-2-1','SBA 1038524','Liabilities - Long Term - SBA 1038524',''),
('0ca294b222fd4e3aaa772321563f2eb9','531c81d9f0c942aeb5b742160d6b3942','861d0c55f31a40be8edf065a51899a93','1','3-2-1','Jacob Solomon','Equity - Dividends - Jacob Solomon',''),
('13d4a5c31ceb49e7a434a10fcab97bc4','ddc9a0f637b64af2adeb19d0f6399e19','51a0b764ac364cbe983a5bdd95403996','2','2-1-2','Employee','Liabilities - Current - Employee',''),
('179c92593abf4cc9b7190e8c3ee03b05','cf190cadde734f98a348d5f6bd112db6','d2b936e048d640ceaacb37046f0b6410','1','1-2-1-1','Vehicle X45T953','Assets - Long Term - Fixed Assets - Vehicle X45T953',''),
('2071d565de844ccd9e2daf86f93d33e9','cf190cadde734f98a348d5f6bd112db6','b1a63303b4ab4f2c886d1803a3ba45a7','4','1-1-4','Payment Processing','Assets - Current - Payment Processing',''),
('207f4e30987042fcadc2df944efd5a40','ddc9a0f637b64af2adeb19d0f6399e19','51a0b764ac364cbe983a5bdd95403996','3','2-1-3','Credit Card','Liabilities - Current - Credit Card',''),
('2176e3c0e3034d438abc396930e1246b','ade6405dd31d40169b7ed222ecaa6b9e','36b862fa389b43e18f1a6e4e54da8fa4','2','5-1-1-2','Software','Expenses - Costs - Direct - Software',''),
('26982f276d7847d29fce310d725e75e2','ade6405dd31d40169b7ed222ecaa6b9e','509ad21a6a49448f8886897ebbddb5cd','9','5-9','Uknown','Expenses - Uknown',''),
('2b408268cb284229928fb9fdd8a18cb3','40f7e36772f240999fca4a9d2c1b536f','45196c4f2ad942e09470cdae954c9f19','1','3-1-1','Jacob Solomon','Equity - Stocks Sold - Jacob Solomon',''),
('2d1258ea2866402199ba15c217ba7bf3','ade6405dd31d40169b7ed222ecaa6b9e','98f56066f72b45f5ab4fa97b39ef6735','1','5-2-2-1','Fuel','Expenses - Operating - Logistics - Fuel',''),
('3152e4caa6d44dc08c4f396ad4a921c4','cf190cadde734f98a348d5f6bd112db6','b1a63303b4ab4f2c886d1803a3ba45a7','5','1-1-5','Undeposited Funds','Assets - Current - Undeposited Funds',''),
('36b862fa389b43e18f1a6e4e54da8fa4','ade6405dd31d40169b7ed222ecaa6b9e','bb21e1c19071470bb724faade25c51df','1','5-1-1','Direct','Expenses - Costs - Direct',''),
('3721608a5207492abdcaa513df341563','cf190cadde734f98a348d5f6bd112db6','b1a63303b4ab4f2c886d1803a3ba45a7','1','1-1-1','Petty Cash','Assets - Current - Petty Cash',''),
('41371ecaaea349aa9a37665941f68c18','ddc9a0f637b64af2adeb19d0f6399e19','9997a95e8783494091d828d47a8a84ec','2','2-2','Long Term','Liabilities - Long Term',''),
('45196c4f2ad942e09470cdae954c9f19','40f7e36772f240999fca4a9d2c1b536f','cca4aabb98634c45b4912d51ad4aa466','1','3-1','Stocks Sold','Equity - Stocks Sold',''),
('472b47da592341da8eb8c009a88f5824','cf190cadde734f98a348d5f6bd112db6','b1a63303b4ab4f2c886d1803a3ba45a7','2','1-1-2','Primary Checking','Assets - Current - Primary Checking',''),
('47df5816bb464641a046da24210bda7e','ade6405dd31d40169b7ed222ecaa6b9e','4acca274bd7241bb81e85fb8af37fe5d','3','5-2-3-3','Insurance','Expenses - Operating - Financial - Insurance',''),
('485683892a644d0c905ceaae5554b1f5','ade6405dd31d40169b7ed222ecaa6b9e','06664f2b01334dc783da3c5b3098d4ee','6','5-2-4-6','Lunches','Expenses - Operating - Employee - Lunches',''),
('49258926ce0b4befb2df152b17d546db','ddc9a0f637b64af2adeb19d0f6399e19','13d4a5c31ceb49e7a434a10fcab97bc4','5','2-1-2-5','Medicare Contribution','Liabilities - Current - Employee - Medicare Contribution',''),
('4acca274bd7241bb81e85fb8af37fe5d','ade6405dd31d40169b7ed222ecaa6b9e','b4f9a532cb7f4d869f3a5bfcd3d00976','3','5-2-3','Financial','Expenses - Operating - Financial',''),
('4cf248090e264c539591c77d2aad0935','ddc9a0f637b64af2adeb19d0f6399e19','13d4a5c31ceb49e7a434a10fcab97bc4','7','2-1-2-7','Federal Unemployment','Liabilities - Current - Employee - Federal Unemployment',''),
('4dcfd166c3964f2688b6e0132c0455b8','ade6405dd31d40169b7ed222ecaa6b9e','98f56066f72b45f5ab4fa97b39ef6735','5','5-2-2-5','Mileage','Expenses - Operating - Logistics - Mileage',''),
('4faf7b30de11449fb5e8becac0580c53','ddc9a0f637b64af2adeb19d0f6399e19','41371ecaaea349aa9a37665941f68c18','2','2-2-2','HCCU 3980552','Liabilities - Long Term - HCCU 3980552',''),
('509ad21a6a49448f8886897ebbddb5cd','ade6405dd31d40169b7ed222ecaa6b9e',NULL,'5','5','Expenses','Expenses',''),
('50cffaf9a93247769a6c68e1e5031164','2ddbad1cf8d04f0d9c44fa9771a15306','d14f23ee81e145bfb06fb95f8a23e8fa','2','4-2-2','Fees & Interest','Revenue - Indirect - Fees & Interest',''),
('51a0b764ac364cbe983a5bdd95403996','ddc9a0f637b64af2adeb19d0f6399e19','9997a95e8783494091d828d47a8a84ec','1','2-1','Current','Liabilities - Current',''),
('550aba5cdef84054b16d1960a879fd9f','cf190cadde734f98a348d5f6bd112db6','179c92593abf4cc9b7190e8c3ee03b05','1','1-2-1-1-1','Deperciation','Assets - Long Term - Fixed Assets - Vehicle X45T953 - Deperciation',''),
('58086bd0ba814d9781f148bff89fd875','ade6405dd31d40169b7ed222ecaa6b9e','06664f2b01334dc783da3c5b3098d4ee','5','5-2-4-5','Insurance','Expenses - Operating - Employee - Insurance',''),
('58db16b726d842aeb28e2dd5b6783f1f','ade6405dd31d40169b7ed222ecaa6b9e','bb21e1c19071470bb724faade25c51df','2','5-1-2','Indirect','Expenses - Costs - Indirect',''),
('5a23a1b6684242a8b64b51d7c9653c8e','ade6405dd31d40169b7ed222ecaa6b9e','694eb089c8ec4d87bd3666bd0701d6c9','5','5-2-1-5','Communications','Expenses - Operating - Administration - Communications',''),
('5e859ff7ccb840adb5b7f161fd441eac','ade6405dd31d40169b7ed222ecaa6b9e','06664f2b01334dc783da3c5b3098d4ee','3','5-2-4-3','Social Security','Expenses - Operating - Employee - Social Security',''),
('5f7dbdd3ec6242d2a0be14d28e6e45da','ddc9a0f637b64af2adeb19d0f6399e19','41371ecaaea349aa9a37665941f68c18','3','2-2-3','BOU 4098755','Liabilities - Long Term - BOU 4098755',''),
('615a1087cc7841cf9f8b19a51e88dab0','ade6405dd31d40169b7ed222ecaa6b9e','36b862fa389b43e18f1a6e4e54da8fa4','1','5-1-1-1','Hardware','Expenses - Costs - Direct - Hardware',''),
('694eb089c8ec4d87bd3666bd0701d6c9','ade6405dd31d40169b7ed222ecaa6b9e','b4f9a532cb7f4d869f3a5bfcd3d00976','1','5-2-1','Administration','Expenses - Operating - Administration',''),
('6b7ebc9e83fd462aa25fc10ce2e1c6ab','531c81d9f0c942aeb5b742160d6b3942','861d0c55f31a40be8edf065a51899a93','2','3-2-2','Regina Marcy','Equity - Dividends - Regina Marcy',''),
('70f12a3459a947438b9cf7b9970066a4','ade6405dd31d40169b7ed222ecaa6b9e','4acca274bd7241bb81e85fb8af37fe5d','2','5-2-3-2','Taxes & Fines','Expenses - Operating - Financial - Taxes & Fines',''),
('715c3700d05b44e98849b313c4b96aed','cf190cadde734f98a348d5f6bd112db6','b1a63303b4ab4f2c886d1803a3ba45a7','7','1-1-7','Vendor Deposits','Assets - Current - Vendor Deposits',''),
('7b12974b462e41e882d0aa86173ea651','cf190cadde734f98a348d5f6bd112db6','b1a63303b4ab4f2c886d1803a3ba45a7','8','1-1-8','Inventory','Assets - Current - Inventory',''),
('7d53919a16d94f1aa395890ca44b65b9','2ddbad1cf8d04f0d9c44fa9771a15306','0908cf7f59c84e5f9aab4c2a632972ce','1','4-1','Direct','Revenue - Direct',''),
('7e7bec6f66ef41088d7c01c706ecd1cc','ade6405dd31d40169b7ed222ecaa6b9e','98f56066f72b45f5ab4fa97b39ef6735','4','5-2-2-4','Logging','Expenses - Operating - Logistics - Logging',''),
('8133c0578f7943b3af82b9e6187d2f55','ddc9a0f637b64af2adeb19d0f6399e19','51a0b764ac364cbe983a5bdd95403996','4','2-1-4','Sales Tax','Liabilities - Current - Sales Tax',''),
('861d0c55f31a40be8edf065a51899a93','531c81d9f0c942aeb5b742160d6b3942','cca4aabb98634c45b4912d51ad4aa466','2','3-2','Dividends','Equity - Dividends',''),
('86715fd6f9964491819b413c0e96c515','ade6405dd31d40169b7ed222ecaa6b9e','58db16b726d842aeb28e2dd5b6783f1f','3','5-1-2-3','Tools of the Trade','Expenses - Costs - Indirect - Tools of the Trade',''),
('87a94c397a654f8e9868d275b31c7c27','6ee39c8c894e45fbb7892175e3365a34','cca4aabb98634c45b4912d51ad4aa466','3','3-3','Retained Earnings','Equity - Retained Earnings',''),
('87eaa30b7e2b471ab39dbd5f30094489','ade6405dd31d40169b7ed222ecaa6b9e','694eb089c8ec4d87bd3666bd0701d6c9','1','5-2-1-1','Meetings','Expenses - Operating - Administration - Meetings',''),
('903ac00b789441b1b6772716ed9144d6','2ddbad1cf8d04f0d9c44fa9771a15306','d14f23ee81e145bfb06fb95f8a23e8fa','1','4-2-1','Shipping & Handling','Revenue - Indirect - Shipping & Handling',''),
('90fcff2dab0542ce9e96f1fd261856ff','cf190cadde734f98a348d5f6bd112db6',NULL,'1','1','Assets','Assets',''),
('944f8e32395e4903b91208468be9cc41','2ddbad1cf8d04f0d9c44fa9771a15306','7d53919a16d94f1aa395890ca44b65b9','1','4-1-1','Hardware','Revenue - Direct - Hardware',''),
('94d7c9ef1e9840aa99b3e60b73cce22f','ade6405dd31d40169b7ed222ecaa6b9e','98f56066f72b45f5ab4fa97b39ef6735','7','5-2-2-7','Insurance','Expenses - Operating - Logistics - Insurance',''),
('95241bd686f84210a140044f4b76f3ef','cf190cadde734f98a348d5f6bd112db6','7b12974b462e41e882d0aa86173ea651','1','1-1-8-1','Raw Materials','Assets - Current - Inventory - Raw Materials',''),
('98f56066f72b45f5ab4fa97b39ef6735','ade6405dd31d40169b7ed222ecaa6b9e','b4f9a532cb7f4d869f3a5bfcd3d00976','2','5-2-2','Logistics','Expenses - Operating - Logistics',''),
('9997a95e8783494091d828d47a8a84ec','ddc9a0f637b64af2adeb19d0f6399e19',NULL,'2','2','Liabilities','Liabilities',''),
('9a125b51544f40288b825329d8ec9843','ade6405dd31d40169b7ed222ecaa6b9e','06664f2b01334dc783da3c5b3098d4ee','7','5-2-4-7','Education & Certifications','Expenses - Operating - Employee - Education & Certifications',''),
('a121aae183d04d9c9fd7c8469dffca21','ade6405dd31d40169b7ed222ecaa6b9e','58db16b726d842aeb28e2dd5b6783f1f','2','5-1-2-2','CC Processing Fees','Expenses - Costs - Indirect - CC Processing Fees',''),
('a8dc6615ef0d4c1998f62eea45e01005','ddc9a0f637b64af2adeb19d0f6399e19','51a0b764ac364cbe983a5bdd95403996','5','2-1-5','Customer Deposits','Liabilities - Current - Customer Deposits',''),
('a98b82c04a13405c82d9496fa938469a','cf190cadde734f98a348d5f6bd112db6','b1a63303b4ab4f2c886d1803a3ba45a7','6','1-1-6','Accounts Receivable','Assets - Current - Accounts Receivable',''),
('b1a63303b4ab4f2c886d1803a3ba45a7','cf190cadde734f98a348d5f6bd112db6','90fcff2dab0542ce9e96f1fd261856ff','1','1-1','Current','Assets - Current',''),
('b20ecca3c2e844bc9af2b77267ef6ba6','ade6405dd31d40169b7ed222ecaa6b9e','4acca274bd7241bb81e85fb8af37fe5d','1','5-2-3-1','Bank Interest & Fees','Expenses - Operating - Financial - Bank Interest & Fees',''),
('b4f9a532cb7f4d869f3a5bfcd3d00976','ade6405dd31d40169b7ed222ecaa6b9e','509ad21a6a49448f8886897ebbddb5cd','2','5-2','Operating','Expenses - Operating',''),
('b7471ab3dca94f5985eebc4efc07476c','ade6405dd31d40169b7ed222ecaa6b9e','694eb089c8ec4d87bd3666bd0701d6c9','4','5-2-1-4','Rent','Expenses - Operating - Administration - Rent',''),
('b9de1b750db4440982a67d9914ba1c77','ade6405dd31d40169b7ed222ecaa6b9e','06664f2b01334dc783da3c5b3098d4ee','4','5-2-4-4','Medicare','Expenses - Operating - Employee - Medicare',''),
('ba93a67ed4ed4757a9c0a2573207ccd7','ddc9a0f637b64af2adeb19d0f6399e19','13d4a5c31ceb49e7a434a10fcab97bc4','1','2-1-2-1','Federal Income Tax Withheld','Liabilities - Current - Employee - Federal Income Tax Withheld',''),
('bb21e1c19071470bb724faade25c51df','ade6405dd31d40169b7ed222ecaa6b9e','509ad21a6a49448f8886897ebbddb5cd','1','5-1','Costs','Expenses - Costs',''),
('be270d4a30f1464bac1b078bf43692bd','cf190cadde734f98a348d5f6bd112db6','b1a63303b4ab4f2c886d1803a3ba45a7','3','1-1-3','Payroll Checking','Assets - Current - Payroll Checking',''),
('c72fc4de68cd428db8284580bfc8a112','cf190cadde734f98a348d5f6bd112db6','7b12974b462e41e882d0aa86173ea651','2','1-1-8-2','Partial Assemblies','Assets - Current - Inventory - Partial Assemblies',''),
('c8565466b7384b7b859dedee410d6f0e','2ddbad1cf8d04f0d9c44fa9771a15306','7d53919a16d94f1aa395890ca44b65b9','2','4-1-2','Software','Revenue - Direct - Software',''),
('c9352dddd7b34ad6b06d81de888b1097','cf190cadde734f98a348d5f6bd112db6','90fcff2dab0542ce9e96f1fd261856ff','2','1-2','Long Term','Assets - Long Term',''),
('cca4aabb98634c45b4912d51ad4aa466','531c81d9f0c942aeb5b742160d6b3942',NULL,'3','3','Equity','Equity',''),
('cf706692fb4f4d9d818ae85126244a91','40f7e36772f240999fca4a9d2c1b536f','45196c4f2ad942e09470cdae954c9f19','2','3-1-2','Regina Marcy','Equity - Stocks Sold - Regina Marcy',''),
('cfec3b745244426ca95c7183fc86b07f','ade6405dd31d40169b7ed222ecaa6b9e','98f56066f72b45f5ab4fa97b39ef6735','3','5-2-2-3','Parking','Expenses - Operating - Logistics - Parking',''),
('d14f23ee81e145bfb06fb95f8a23e8fa','2ddbad1cf8d04f0d9c44fa9771a15306','0908cf7f59c84e5f9aab4c2a632972ce','2','4-2','Indirect','Revenue - Indirect',''),
('d2b936e048d640ceaacb37046f0b6410','cf190cadde734f98a348d5f6bd112db6','c9352dddd7b34ad6b06d81de888b1097','1','1-2-1','Fixed Assets','Assets - Long Term - Fixed Assets',''),
('d51d798e22434c73827abdf8094ab137','ddc9a0f637b64af2adeb19d0f6399e19','13d4a5c31ceb49e7a434a10fcab97bc4','4','2-1-2-4','Social Security Contribution','Liabilities - Current - Employee - Social Security Contribution',''),
('d6f4d13c6b73415f807424f9d8c4e2b0','ddc9a0f637b64af2adeb19d0f6399e19','51a0b764ac364cbe983a5bdd95403996','1','2-1-1','Accounts Payable','Liabilities - Current - Accounts Payable',''),
('d7f0321814c64a97a257d1dfe5be0853','ddc9a0f637b64af2adeb19d0f6399e19','13d4a5c31ceb49e7a434a10fcab97bc4','6','2-1-2-6','State Unemployment','Liabilities - Current - Employee - State Unemployment',''),
('d906c444324f4492a40ff91313df9f60','ade6405dd31d40169b7ed222ecaa6b9e','36b862fa389b43e18f1a6e4e54da8fa4','3','5-1-1-3','Labor','Expenses - Costs - Direct - Labor',''),
('e1a825e81f424610b8f0e39cb64cc04d','cf190cadde734f98a348d5f6bd112db6','c9352dddd7b34ad6b06d81de888b1097','3','1-2-3','Goodwill','Assets - Long Term - Goodwill',''),
('e38dc25cf5f9411e821e9e67dd0dee71','ade6405dd31d40169b7ed222ecaa6b9e','694eb089c8ec4d87bd3666bd0701d6c9','2','5-2-1-2','Marketing','Expenses - Operating - Administration - Marketing',''),
('e5b632bb7c444f47ab3d8e278cabc054','cf190cadde734f98a348d5f6bd112db6','c9352dddd7b34ad6b06d81de888b1097','2','1-2-2','Investments','Assets - Long Term - Investments',''),
('eb72dee532294ffc959077b843e660e0','2ddbad1cf8d04f0d9c44fa9771a15306','7d53919a16d94f1aa395890ca44b65b9','3','4-1-3','Labor','Revenue - Direct - Labor',''),
('eb8616b0a5bf4ee8b11445771eb5fd6f','ddc9a0f637b64af2adeb19d0f6399e19','13d4a5c31ceb49e7a434a10fcab97bc4','8','2-1-2-8','Insurance Withheld','Liabilities - Current - Employee - Insurance Withheld',''),
('ecf289453265448a855463b75d9ef2ed','ade6405dd31d40169b7ed222ecaa6b9e','694eb089c8ec4d87bd3666bd0701d6c9','7','5-2-1-7','IT','Expenses - Operating - Administration - IT',''),
('edadf3e55c284fba8bf5556e6f1aee31','ade6405dd31d40169b7ed222ecaa6b9e','58db16b726d842aeb28e2dd5b6783f1f','1','5-1-2-1','Shipping & Handling','Expenses - Costs - Indirect - Shipping & Handling',''),
('f0537703e784455abfeac0cf2af3a757','2ddbad1cf8d04f0d9c44fa9771a15306','7d53919a16d94f1aa395890ca44b65b9','4','4-1-4','Services','Revenue - Direct - Services',''),
('f05b72b94d4c41d6b47a2988d222e426','ade6405dd31d40169b7ed222ecaa6b9e','06664f2b01334dc783da3c5b3098d4ee','2','5-2-4-2','State Unemployment','Expenses - Operating - Employee - State Unemployment',''),
('f12ba1e7ea5c45689733fcb5eea998a8','ddc9a0f637b64af2adeb19d0f6399e19','13d4a5c31ceb49e7a434a10fcab97bc4','2','2-1-2-2','Social Security Withheld','Liabilities - Current - Employee - Social Security Withheld',''),
('f177f8d3c04b463b880e20b9b22a4d1b','ade6405dd31d40169b7ed222ecaa6b9e','36b862fa389b43e18f1a6e4e54da8fa4','4','5-1-1-4','Sevices','Expenses - Costs - Direct - Sevices',''),
('f2d6b11ef27243c4a84c7b505f7e2947','cf190cadde734f98a348d5f6bd112db6','d2b936e048d640ceaacb37046f0b6410','2','1-2-1-2','Vehicle T6Z93P','Assets - Long Term - Fixed Assets - Vehicle T6Z93P',''),
('f416969a56634d1894197cd11cb5ba9a','ddc9a0f637b64af2adeb19d0f6399e19','13d4a5c31ceb49e7a434a10fcab97bc4','3','2-1-2-3','Medicare Withheld','Liabilities - Current - Employee - Medicare Withheld',''),
('f4d00823e3a04f2bbc98eb3b2788c3db','cf190cadde734f98a348d5f6bd112db6','7b12974b462e41e882d0aa86173ea651','3','1-1-8-3','Completed Products','Assets - Current - Inventory - Completed Products',''),
('f6e0966ddd60431c894995ecd83456cc','ade6405dd31d40169b7ed222ecaa6b9e','98f56066f72b45f5ab4fa97b39ef6735','6','5-2-2-6','Maintinance','Expenses - Operating - Logistics - Maintinance',''),
('f784c8228eb6449cb086d1ec2a57bbd4','ade6405dd31d40169b7ed222ecaa6b9e','06664f2b01334dc783da3c5b3098d4ee','1','5-2-4-1','Federal Unemployment','Expenses - Operating - Employee - Federal Unemployment','');
/*!40000 ALTER TABLE `Accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Contact Notes`
--

LOCK TABLES `Contact Notes` WRITE;
/*!40000 ALTER TABLE `Contact Notes` DISABLE KEYS */;
INSERT INTO `Contact Notes` VALUES ('d2218abaca094fe2a6c6e86fd45181c0','c779d5ea107f44a58650a718ec5e8123','86b41969e95143c090fd93a4819c58a2','2016-02-09 21:13:24','\"Us\" Companies','The \"Us\" company is the company that this database represents.');
/*!40000 ALTER TABLE `Contact Notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Contact Types`
--

LOCK TABLES `Contact Types` WRITE;
/*!40000 ALTER TABLE `Contact Types` DISABLE KEYS */;
INSERT INTO `Contact Types` VALUES ('08971f44ca2e4dacbb6ecccedae75fc9','','Spouse'),
('4a2979dcbfc3406e987c9e79104d8114','','Residential'),
('4be76282830044c1bcfe44f728445c16','','Goverment'),
('63bff46f26ab409b8a67eb7008309ac5','','Officer'),
('7079212168624ffb8533b28859ca758b','','Contractor'),
('70c531331b884e22a9d839020919e37c','','Accounts Payable'),
('7ab7d6cce20848c1bc3fc1f14ca22af4','','Authorized Agent'),
('ac4de29f0c174e829232fffb96029388','','Us'),
('accf998ffa1e4021907da01f2dde9585','','Non-Profit');
/*!40000 ALTER TABLE `Contact Types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Contacts`
--

LOCK TABLES `Contacts` WRITE;
/*!40000 ALTER TABLE `Contacts` DISABLE KEYS */;
INSERT INTO `Contacts` VALUES 
('0a39aa256a614f7884ddaca2afb80757','c779d5ea107f44a58650a718ec5e8123','Roxanne Clemons','','2016-02-03',NULL,NULL,'roxannac@localhost',NULL,'281-659-6317',NULL,NULL,'2481 Burwell Heights Road',NULL,NULL,'Nederland','TX','77963',NULL,'a316c94792a6417c922f4b79fe23a236'),
('0dcc426b5bce4c11ad0bb8ff6f8cad3d',NULL,'John John Inc','','2016-02-03','0dcc426b5bce4c11ad0bb8ff6f8cad3d','www.johnjohninc.com',NULL,'713-221-3958',NULL,NULL,NULL,'1856 Lousiana St #2507',NULL,'USA','Houston','TX','77002','fa6888e3f16e40d48500364cc27495c5','9bb9116d2e2e484ea7545af7d94abc8f'),
('1b5ee8c4f5fd4a65bda664861a43bce8','c779d5ea107f44a58650a718ec5e8123','Jacob Solomon','','2016-02-03',NULL,NULL,'jacobs@localhost',NULL,'409-569-8532',NULL,NULL,'4049 Juniper Drive',NULL,'USA','Conroe','TX','78569',NULL,'63bff46f26ab409b8a67eb7008309ac5'),
('2762c5a8836145e584cd246cf8b32f52','c779d5ea107f44a58650a718ec5e8123','Lurlene Jarvis','','2016-02-03',NULL,NULL,'lurlenej@localhost',NULL,'713-952-6571',NULL,NULL,'1258 Sunburst Drive',NULL,'USA','La Port','TX','77569',NULL,'a316c94792a6417c922f4b79fe23a236'),
('5a70e0b1504946ab94b7a1d1c05af3a3',NULL,'United Petroleum Products','','2016-02-03','5a70e0b1504946ab94b7a1d1c05af3a3','www.unitedpetro.com',NULL,'281-549-3759',NULL,NULL,NULL,'18456 W Highway 221 N',NULL,'USA','Pasadena','TX','77589','de03d8df94b34400a7daa9a662f33922','9bb9116d2e2e484ea7545af7d94abc8f'),
('6c8d5068ee384fdf96d1c1f407eee0d7','c779d5ea107f44a58650a718ec5e8123','Regina Marcy','','2016-02-03',NULL,NULL,'reginam@localhost',NULL,'832-569-8523',NULL,NULL,'3770 Hall Street',NULL,'USA','Katy','TX','77456',NULL,'7079212168624ffb8533b28859ca758b'),
('a16cbcb5005d45c79ba301cc5d603844','c779d5ea107f44a58650a718ec5e8123','Peter Rios','','2016-02-03',NULL,NULL,'peterr@localhost',NULL,'713-563-9821',NULL,NULL,'5790 Hall Street',NULL,'USA','Pasadena','TX','77596',NULL,'a316c94792a6417c922f4b79fe23a236'),
('a8f6cd058bf14dddb34bfab870b83d01',NULL,'Total Automotive','','2016-02-03','a8f6cd058bf14dddb34bfab870b83d01','www.totalauto.com',NULL,'832-577-2945',NULL,NULL,NULL,'2481 Burwell Heights Road',NULL,'USA','Pasadena','TX','77569','ef16fb213f9a4818983a96f2c563c3a2','9bb9116d2e2e484ea7545af7d94abc8f'),
('a9b2127546414185ad6c9dea2412191b','c779d5ea107f44a58650a718ec5e8123','Larry Morgan','','2016-02-03',NULL,NULL,'larrym@localhost',NULL,'832-577-6589',NULL,NULL,'4838 Frosty Lane',NULL,'USA','Houston','TX','77006',NULL,'70c531331b884e22a9d839020919e37c'),
('afdfa32dbbe145ab9dd4279bf1ea6a82','da28690a053643ada5361946339b6ca2','Ramon McCabe','','2016-02-03',NULL,NULL,'rmccabe@hsc.org','281-485-3856','409-223-1934',NULL,NULL,'4328 Wood Duck Drive',NULL,'USA','Clear Lake','TX','77568',NULL,'7ab7d6cce20848c1bc3fc1f14ca22af4'),
('c4b370528c18443e875a2526bc769e3a',NULL,'Texas Comptroller of Public Accounts','','2016-02-03',NULL,'http://comptroller.texas.gov/taxinfo/sales/',NULL,'800-252-5555',NULL,NULL,NULL,'P.O. Box 13528','Capitol Station','USA','Austin','TX','78711-3528',NULL,'4be76282830044c1bcfe44f728445c16'),
('c779d5ea107f44a58650a718ec5e8123',NULL,'Internetwork Consulting LLC','','2016-02-03','c779d5ea107f44a58650a718ec5e8123','www.internetworkconsulting.net',NULL,'8332-606-3300',NULL,NULL,NULL,'3880 Greenhouse Rd #421',NULL,'USA','Houston','TX','77084','ef16fb213f9a4818983a96f2c563c3a2','ac4de29f0c174e829232fffb96029388'),
('d54809611e124e74b3c62245be78b63e','da28690a053643ada5361946339b6ca2','Anne Davis','','2016-02-03',NULL,NULL,'adavis@hsc.org','281-586-3952','832-663-9921',NULL,NULL,'1845 Bird Spring Lane',NULL,'USA','Kemah','TX','77565',NULL,'63bff46f26ab409b8a67eb7008309ac5'),
('da28690a053643ada5361946339b6ca2',NULL,'Houston Space Center','','2016-02-03','da28690a053643ada5361946339b6ca2','http://www.hsc.rg',NULL,'281-659-9856',NULL,NULL,NULL,'3770 Hall Street',NULL,'USA','Houston','TX','77096','ef16fb213f9a4818983a96f2c563c3a2','9bb9116d2e2e484ea7545af7d94abc8f');
/*!40000 ALTER TABLE `Contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Conversions`
--

LOCK TABLES `Conversions` WRITE;
/*!40000 ALTER TABLE `Conversions` DISABLE KEYS */;
INSERT INTO `Conversions` VALUES 
('02ba9635185f4cc2a76bcead86c4681d','fb0be5ae9ca04f7b8b54592ac5c367d2',10.0000000000000000,'adb0bf8814644b71a9e457e7f06eaa57',1.0000000000000000),
('0432a07cd49f4ce6839db405193c1380','0ddb78acaa804885b2a1c8194d9062ab',12.0000000000000000,'f456bd7d262f43cdb486c9db440e037a',1.0000000000000000),
('0e50868820a4452b82697704e42ac4d0','0bbd2b97414e4016819963c56cd68cc3',1.0000000000000000,'adb0bf8814644b71a9e457e7f06eaa57',2.4500000000000000),
('1baf1ff37ef04132a66e6dda13cbd659','adb0bf8814644b71a9e457e7f06eaa57',100.0000000000000000,'89334424ee1f4c88a3139aa0a93bfaa0',1.0000000000000000),
('2c4097364eb34f4fb9945e395b922a1f','5c838a6dfa9148179c168932e94d26eb',1.0000000000000000,'6f576d029c0b4141a8cb41413be05e61',1024.0000000000000000),
('421335521a3443cb95b264c62592861b','3bbc49e44c124bb89447e4ca9dd1b95f',3.0000000000000000,'159503b205a54554b945856599ab1dee',1.0000000000000000),
('47f14a49d0c545e789d74c348e5a3c00','0bbd2b97414e4016819963c56cd68cc3',12.0000000000000000,'3bbc49e44c124bb89447e4ca9dd1b95f',1.0000000000000000),
('4a008113cdbc4896b9cc2b5311f6b42a','a1e33d01c1fc46c2bf5928690a91da0a',1728.0000000000000000,'d294c78779bb49e081e58ce45fe6d78e',1.0000000000000000),
('4c9ea5ef571148deb794c5107e6cf9b0','0d775fe617fa40dc97327321299a5895',1024.0000000000000000,'a73c5345517440d68cecb20b800d6f0e',1.0000000000000000),
('53cf921a1f754d4985227cf9ed5e32b3','a73c5345517440d68cecb20b800d6f0e',1.0000000000000000,'0d775fe617fa40dc97327321299a5895',1024.0000000000000000),
('560910bf5bdf4d7c8c5d4e287ada5d49','0d775fe617fa40dc97327321299a5895',1.0000000000000000,'5c838a6dfa9148179c168932e94d26eb',1024.0000000000000000),
('566392a69bb24096b93d2938bf9f8071','159503b205a54554b945856599ab1dee',1.0000000000000000,'3bbc49e44c124bb89447e4ca9dd1b95f',3.0000000000000000),
('6cbfa801850b4538b413f114be427300','adb0bf8814644b71a9e457e7f06eaa57',2.4500000000000000,'0bbd2b97414e4016819963c56cd68cc3',1.0000000000000000),
('72995895c0a54693b53434b494ac9e56','f92e4468f181449cb6b3ab4329842adf',55.0000000000000000,'dbf9e3ab54c2458abf2553938d11a7cb',1.0000000000000000),
('74bdc3ebdeff4c5c8e082c8848c3fd3f','188faf8ce65a463f868645107b8629f9',60.0000000000000000,'b109a802ed5a4c2d9d4cda6637477ce2',1.0000000000000000),
('8104cd21e602454e9ad234b7925f885e','f456bd7d262f43cdb486c9db440e037a',1.0000000000000000,'0ddb78acaa804885b2a1c8194d9062ab',12.0000000000000000),
('8865a9a5797548ff85583d6e60b12d2a','77490df6e9e344f080d2163a73bdb99d',8.4500000000000000,'f92e4468f181449cb6b3ab4329842adf',1.0000000000000000),
('8e3d1d1700e24a9ea417b88866b727be','cb03ed4131124b4fa210b2781f9c6028',1.0000000000000000,'d294c78779bb49e081e58ce45fe6d78e',9.0000000000000000),
('917bfb416219429c87145296eb43629c','3c88fd0ae4254adead2d786d830bfb0c',16.0000000000000000,'77490df6e9e344f080d2163a73bdb99d',1.0000000000000000),
('970ab4a44f2241fab247ffc7cd69440e','77490df6e9e344f080d2163a73bdb99d',1.0000000000000000,'3c88fd0ae4254adead2d786d830bfb0c',16.0000000000000000),
('9db51b3885614a41816da1ce26542837','5c838a6dfa9148179c168932e94d26eb',1024.0000000000000000,'0d775fe617fa40dc97327321299a5895',1.0000000000000000),
('a93c8ffb4bb244c8a5737a54a99acda7','6f576d029c0b4141a8cb41413be05e61',1024.0000000000000000,'5c838a6dfa9148179c168932e94d26eb',1.0000000000000000),
('bb1d0d48823d40a5bf63cb24af73a33e','dbf9e3ab54c2458abf2553938d11a7cb',1.0000000000000000,'f92e4468f181449cb6b3ab4329842adf',55.0000000000000000),
('bb26c2a7fa2242ad9eb22e4c8aec92fe','89334424ee1f4c88a3139aa0a93bfaa0',1.0000000000000000,'adb0bf8814644b71a9e457e7f06eaa57',100.0000000000000000),
('be6cb6985cb742fab4644da16a61b8f8','adb0bf8814644b71a9e457e7f06eaa57',1.0000000000000000,'fb0be5ae9ca04f7b8b54592ac5c367d2',10.0000000000000000),
('c32260dbb17347c68a35b1070d945abe','d294c78779bb49e081e58ce45fe6d78e',1.0000000000000000,'a1e33d01c1fc46c2bf5928690a91da0a',1728.0000000000000000),
('c47f4e6e4cd14475b92a40c55d6c71c6','f92e4468f181449cb6b3ab4329842adf',155.0000000000000000,'1a409157af9f4359a7c0db9378c062f9',1.0000000000000000),
('c51e3b53851147bf93abc74d815142d3','d294c78779bb49e081e58ce45fe6d78e',9.0000000000000000,'cb03ed4131124b4fa210b2781f9c6028',1.0000000000000000),
('db45ee11f9434191b5a32234f6aad4f1','f92e4468f181449cb6b3ab4329842adf',1.0000000000000000,'77490df6e9e344f080d2163a73bdb99d',8.4500000000000000),
('de0a76de66034d6fa65c60f70d74462f','3bbc49e44c124bb89447e4ca9dd1b95f',1.0000000000000000,'0bbd2b97414e4016819963c56cd68cc3',12.0000000000000000),
('e17aafd855f44887af83cec3c25edc7a','b109a802ed5a4c2d9d4cda6637477ce2',1.0000000000000000,'188faf8ce65a463f868645107b8629f9',60.0000000000000000),
('e72c42510dfc42c58af9c8cc8a28e40d','1a409157af9f4359a7c0db9378c062f9',1.0000000000000000,'f92e4468f181449cb6b3ab4329842adf',155.0000000000000000);
/*!40000 ALTER TABLE `Conversions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Departments`
--

LOCK TABLES `Departments` WRITE;
/*!40000 ALTER TABLE `Departments` DISABLE KEYS */;
INSERT INTO `Departments` VALUES 
('09c09072314d43bd85c00dcf8bc1ad88',NULL,'SVC','SVC','Service','Service',''),
('1bf7bebae8d84a1e8fa7c846b495dcdf',NULL,'MRK','MRK','Marketing','Marketing',''),
('55e188f9e7c14fbe9f1ad720222c15c7',NULL,'DEV','DEV','Development','Development',''),
('6e20ef40082b437c8af0393a5b5e0733',NULL,'ADM','ADM','Administration','Administration','');
/*!40000 ALTER TABLE `Departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Document Lines`
--

LOCK TABLES `Document Lines` WRITE;
/*!40000 ALTER TABLE `Document Lines` DISABLE KEYS */;
INSERT INTO `Document Lines` VALUES 
('2cd8057ae4d5473391801e4c61dcc435','2038c6d0fabe45d4b44c619d8d709ca2',1,2.0000000000000000,'fe259c0aa2034ad3a7bc57a33f2eb2d0','0ddb78acaa804885b2a1c8194d9062ab','Windows Server 2012 Standard CAL',220.6800000000000000,441.3600000000000000,'','\0',NULL,NULL,'c8565466b7384b7b859dedee410d6f0e'),
('5bba76643cc846738f34e6217447fb00','b49b012dcd56401081580c2880fb3ca7',0,2.2500000000000000,'ff511c3738834e7eaaf086afdeb0ac07','0ddb78acaa804885b2a1c8194d9062ab','Deploy 2 computers.',114.0000000000000000,256.5000000000000000,'','\0',NULL,NULL,'eb72dee532294ffc959077b843e660e0'),
('5fda5c66c64441858e64fd88c7ac04fe','2038c6d0fabe45d4b44c619d8d709ca2',0,1.0000000000000000,'67ef8ddd53284c30ba6fb1ace574fabf','0ddb78acaa804885b2a1c8194d9062ab','Windows Server 2012 Standard',883.9400000000000000,883.9400000000000000,'','\0',NULL,NULL,'c8565466b7384b7b859dedee410d6f0e'),
('cae4af983296496c8e2aecc72da50b0a','b5233543de42425daa483811fbfc15d2',0,1.0000000000000000,'ff511c3738834e7eaaf086afdeb0ac07','0ddb78acaa804885b2a1c8194d9062ab','Computer Virus - cleanout out user profile, windows folers, program files, and temporary files.  Checked startup items - cleaner.  Virus scan - clean.',114.0000000000000000,114.0000000000000000,'','\0',NULL,NULL,'eb72dee532294ffc959077b843e660e0'),
('d1a8eed989334f938b2e55e74960bd4c','4bfc2fc8cf3b45c1b4c6e5da218ce4aa',0,1.0000000000000000,'ff511c3738834e7eaaf086afdeb0ac07','0ddb78acaa804885b2a1c8194d9062ab','Could not print.  Documents stuck in queue, cleared.  Printiner is IP - confirmed IP.  Downloaded driver and installed.  Working',114.0000000000000000,114.0000000000000000,'','\0',NULL,NULL,'eb72dee532294ffc959077b843e660e0');
/*!40000 ALTER TABLE `Document Lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Documents`
--

LOCK TABLES `Documents` WRITE;
/*!40000 ALTER TABLE `Documents` DISABLE KEYS */;
INSERT INTO `Documents` VALUES 
('2038c6d0fabe45d4b44c619d8d709ca2','81e2917ac5c34d1cb6f9d168cd0439db',NULL,'SZ2016010101','2016-02-06','2016-02-06','a98b82c04a13405c82d9496fa938469a','2038c6d0fabe45d4b44c619d8d709ca2','a8f6cd058bf14dddb34bfab870b83d01','Total Automotive',NULL,'COD',NULL,NULL,'a8f6cd058bf14dddb34bfab870b83d01','Total Automotive','2481 Burwell Heights Road',NULL,'Pasadena','TX','77569','USA',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ef16fb213f9a4818983a96f2c563c3a2',1325.3000000000000000,0.0000000000000000,0.0825000000000000,1325.3000000000000000,109.3372500000000000,1434.6372500000000000),
('4bfc2fc8cf3b45c1b4c6e5da218ce4aa','81e2917ac5c34d1cb6f9d168cd0439db',NULL,'1000','2016-01-01','2016-02-07','a98b82c04a13405c82d9496fa938469a','4bfc2fc8cf3b45c1b4c6e5da218ce4aa','da28690a053643ada5361946339b6ca2','Houston Space Center',NULL,'COD',NULL,'A1049-49JHS-HJ1572','da28690a053643ada5361946339b6ca2','Houston Space Center','3770 Hall Street',NULL,'Houston','TX','77096','USA','d54809611e124e74b3c62245be78b63e','Anne Davis','1845 Bird Spring Lane',NULL,'Kemah','TX','77565','USA','ef16fb213f9a4818983a96f2c563c3a2',114.0000000000000000,0.0000000000000000,0.0825000000000000,114.0000000000000000,9.4050000000000000,123.4050000000000000),
('b49b012dcd56401081580c2880fb3ca7','81e2917ac5c34d1cb6f9d168cd0439db',NULL,'1002','2016-02-07','2016-02-07','a98b82c04a13405c82d9496fa938469a','b49b012dcd56401081580c2880fb3ca7','5a70e0b1504946ab94b7a1d1c05af3a3','United Petroleum Products',NULL,'COD',NULL,NULL,'5a70e0b1504946ab94b7a1d1c05af3a3','United Petroleum Products','18456 W Highway 221 N',NULL,'Pasadena','TX','77589','USA','5a70e0b1504946ab94b7a1d1c05af3a3','United Petroleum Products','18456 W Highway 221 N',NULL,'Pasadena','TX','77589','USA','fa6888e3f16e40d48500364cc27495c5',256.5000000000000000,0.0000000000000000,0.0725000000000000,256.5000000000000000,18.5962500000000000,275.0962500000000000),
('b5233543de42425daa483811fbfc15d2','81e2917ac5c34d1cb6f9d168cd0439db',NULL,'1001','2016-02-07','2016-02-07','a98b82c04a13405c82d9496fa938469a','b5233543de42425daa483811fbfc15d2','a8f6cd058bf14dddb34bfab870b83d01','Total Automotive',NULL,'COD',NULL,NULL,'a8f6cd058bf14dddb34bfab870b83d01','Total Automotive','2481 Burwell Heights Road',NULL,'Pasadena','TX','77569','USA','a8f6cd058bf14dddb34bfab870b83d01','Total Automotive','2481 Burwell Heights Road',NULL,'Pasadena','TX','77569','USA','ef16fb213f9a4818983a96f2c563c3a2',114.0000000000000000,0.0000000000000000,0.0825000000000000,114.0000000000000000,9.4050000000000000,123.4050000000000000);
/*!40000 ALTER TABLE `Documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Groups`
--

LOCK TABLES `Groups` WRITE;
/*!40000 ALTER TABLE `Groups` DISABLE KEYS */;
INSERT INTO `Groups` VALUES 
('8b07377cbc7947e39ef6bb4f34fa2a4f','','Management','management@localhost'),
('cdc8ea4e1479445eb342d9340f1c9047','','Accounting','accounting@localhost'),
('d41eb898ceb24c5795b74bc298699022','','Technical Service','service@localhost');
/*!40000 ALTER TABLE `Groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Items`
--

LOCK TABLES `Items` WRITE;
/*!40000 ALTER TABLE `Items` DISABLE KEYS */;
INSERT INTO `Items` VALUES 
('02539f2d1bbc41bda1f2f5781ed5848c','cd304ab949ed4ba88d0763e524b1faeb','STD','S-MS-W10-STD','','c8565466b7384b7b859dedee410d6f0e','Windows 10 Home',1.3000000000000000,137.7400000000000000,'','2176e3c0e3034d438abc396930e1246b','Windows 10 Home',NULL,105.9500000000000000,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Windows 10 Home',''),
('0f0e7811b187480993092b547aad3c56','990d2841ab2a4eadb1e1b766b0faa7d6','SVR12','S-MS-SVR12','','c8565466b7384b7b859dedee410d6f0e','Server 2012',1.3000000000000000,NULL,'','2176e3c0e3034d438abc396930e1246b','Server 2012',NULL,NULL,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Server 2012',''),
('1c2afd6364bb473dabac60c9b242d4fe',NULL,'H','H','','944f8e32395e4903b91208468be9cc41','Hardware',1.3000000000000000,NULL,'','615a1087cc7841cf9f8b19a51e88dab0','Hardware',NULL,NULL,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Hardware',''),
('28ae87bddd1b4e75887d2154d9bcf325',NULL,'S','S','','c8565466b7384b7b859dedee410d6f0e','Software',1.3000000000000000,NULL,'','2176e3c0e3034d438abc396930e1246b','Software',NULL,NULL,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Software',''),
('67ef8ddd53284c30ba6fb1ace574fabf','0f0e7811b187480993092b547aad3c56','STD','S-MS-SVR12-STD','','c8565466b7384b7b859dedee410d6f0e','Windows Server 2012 Standard',1.3000000000000000,883.9400000000000000,'','2176e3c0e3034d438abc396930e1246b','Windows Server 2012 Standard',NULL,679.9500000000000000,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Windows Server 2012 Standard',''),
('6abd4b120726461aa5b94db189e305b9','cd304ab949ed4ba88d0763e524b1faeb','PRO','S-MS-W10-PRO','','c8565466b7384b7b859dedee410d6f0e','Windows 10 Professional',1.3000000000000000,181.9400000000000000,'','2176e3c0e3034d438abc396930e1246b','Windows 10 Professional',NULL,139.9500000000000000,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Windows 10 Professional',''),
('990d2841ab2a4eadb1e1b766b0faa7d6','28ae87bddd1b4e75887d2154d9bcf325','MS','S-MS','','c8565466b7384b7b859dedee410d6f0e','Microsoft',1.3000000000000000,NULL,'','2176e3c0e3034d438abc396930e1246b','Microsoft',NULL,NULL,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Microsoft',''),
('cd304ab949ed4ba88d0763e524b1faeb','990d2841ab2a4eadb1e1b766b0faa7d6','W10','S-MS-W10','','c8565466b7384b7b859dedee410d6f0e','Windows 10',1.3000000000000000,NULL,'','2176e3c0e3034d438abc396930e1246b','Windows 10',NULL,NULL,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Windows 10',''),
('fe259c0aa2034ad3a7bc57a33f2eb2d0','0f0e7811b187480993092b547aad3c56','5CAL','S-MS-SVR12-5CAL','','c8565466b7384b7b859dedee410d6f0e','Windows Server 2012 Standard CAL',1.3000000000000000,220.6800000000000000,'','2176e3c0e3034d438abc396930e1246b','Windows Server 2012 Standard CAL',NULL,169.7500000000000000,'0ddb78acaa804885b2a1c8194d9062ab','f4d00823e3a04f2bbc98eb3b2788c3db','Windows Server 2012 Standard CAL',''),
('ff511c3738834e7eaaf086afdeb0ac07',NULL,'L','L','','eb72dee532294ffc959077b843e660e0','Labor',NULL,114.0000000000000000,'','d906c444324f4492a40ff91313df9f60','Labor',NULL,NULL,'0ddb78acaa804885b2a1c8194d9062ab',NULL,NULL,'\0');
/*!40000 ALTER TABLE `Items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Jobs`
--

LOCK TABLES `Jobs` WRITE;
/*!40000 ALTER TABLE `Jobs` DISABLE KEYS */;
INSERT INTO `Jobs` VALUES ('02216bf85e994cbabd9ee002e509f314','187d2f925be44b139da354e5a18302a0','160201','INC-ACCT-160201','Sample Data','Internetwork Consulting LLC - incAccounting - Sample Data','',NULL),
('162797aacf284556a0ea703d33ed1c4d','c4b5dd1cb38f4c7f96c77f575e2ce91d','ACCT','JJI-ACCT','incAccounting','John John Inc - incAccounting','',NULL),
('16e2812cfa3c49329b05d1a3e7faa2ec','94a7091dd73547a099f6c79c121c3666','SRVC','INC-SRVC','incService','Internetwork Consulting LLC - incService','',NULL),
('1849fcfa7ac8407aa74b4c69bce84584',NULL,'HSC','HSC','Houston Space Center','Houston Space Center','',NULL),
('187d2f925be44b139da354e5a18302a0','94a7091dd73547a099f6c79c121c3666','ACCT','INC-ACCT','incAccounting','Internetwork Consulting LLC - incAccounting','',NULL),
('1a25abb8e3af4f028a7e841080c10874','187d2f925be44b139da354e5a18302a0','151225','INC-ACCT-151225','Redevelop Controller','Internetwork Consulting LLC - incAccounting - Redevelop Controller','',NULL),
('336c8215e109402299f352cd913facb1','83eee8c46a0e47d4a6a6312a893d8aa3','001','HSC-ARCH-001','Intergration','Houston Space Center - incArchivador - Intergration','',NULL),
('3b609645a860400384f1157a732a672e','162797aacf284556a0ea703d33ed1c4d','001','JJI-ACCT-001','Conversion','John John Inc - incAccounting - Conversion','',NULL),
('45aea77ada9a4ce88df7b5971f96e233','553910143c414ee29f5a46425db198d5','001','JJI-ARCH-001','Installation','John John Inc - incArchivador - Installation','',NULL),
('553910143c414ee29f5a46425db198d5','c4b5dd1cb38f4c7f96c77f575e2ce91d','ARCH','JJI-ARCH','incArchivador','John John Inc - incArchivador','',NULL),
('83eee8c46a0e47d4a6a6312a893d8aa3','1849fcfa7ac8407aa74b4c69bce84584','ARCH','HSC-ARCH','incArchivador','Houston Space Center - incArchivador','',NULL),
('9322905f3cb34268b0eb875c5b3772dd','94a7091dd73547a099f6c79c121c3666','ARCH','INC-ARCH','incArchivador','Internetwork Consulting LLC - incArchivador','',NULL),
('94a7091dd73547a099f6c79c121c3666',NULL,'INC','INC','Internetwork Consulting LLC','Internetwork Consulting LLC','',NULL),
('9dc11181e69f4110b33d81d5ff539b5c','553910143c414ee29f5a46425db198d5','002','JJI-ARCH-002','Intergration','John John Inc - incArchivador - Intergration','',NULL),
('a23ab64d59164e458a964f20248e8bc1','553910143c414ee29f5a46425db198d5','003','JJI-ARCH-003','Customization','John John Inc - incArchivador - Customization','',NULL),
('a8bd8f74c7f84f1297918b1b4ee3bdeb','83eee8c46a0e47d4a6a6312a893d8aa3','002','HSC-ARCH-002','Installation','Houston Space Center - incArchivador - Installation','',NULL),
('aea4e93a6bb749f381851fc91d42f14f','162797aacf284556a0ea703d33ed1c4d','002','JJI-ACCT-002','Configuration','John John Inc - incAccounting - Configuration','',NULL),
('bb1c38f56f44441da1bd46f0222f305a','162797aacf284556a0ea703d33ed1c4d','003','JJI-ACCT-003','Deployment','John John Inc - incAccounting - Deployment','',NULL),
('c4b5dd1cb38f4c7f96c77f575e2ce91d',NULL,'JJI','JJI','John John Inc','John John Inc','',NULL),
('d0090dbbe6664ef985a92d9406e697b3','187d2f925be44b139da354e5a18302a0','151002','INC-ACCT-151002','Proof of Concept','Internetwork Consulting LLC - incAccounting - Proof of Concept','',NULL),
('ed5e03dcfe60468a916aee743d9018b8','83eee8c46a0e47d4a6a6312a893d8aa3','003','HSC-ARCH-003','Customization','Houston Space Center - incArchivador - Customization','',NULL),
('f531df627f204310a9c13d0241ebe18f','162797aacf284556a0ea703d33ed1c4d','004','JJI-ACCT-004','Customization','John John Inc - incAccounting - Customization','',NULL);
/*!40000 ALTER TABLE `Jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Memberships`
--

LOCK TABLES `Memberships` WRITE;
/*!40000 ALTER TABLE `Memberships` DISABLE KEYS */;
INSERT INTO `Memberships` VALUES 
('2a82d4e3cf01417ba943612de782b3fa','8b07377cbc7947e39ef6bb4f34fa2a4f'),
('4afe36775b1c4d3f83659bd70929e912','8b07377cbc7947e39ef6bb4f34fa2a4f'),
('2a82d4e3cf01417ba943612de782b3fa','cdc8ea4e1479445eb342d9340f1c9047'),
('4afe36775b1c4d3f83659bd70929e912','cdc8ea4e1479445eb342d9340f1c9047'),
('7ad023488e4f4058ba8098600224dcca','cdc8ea4e1479445eb342d9340f1c9047'),
('2a82d4e3cf01417ba943612de782b3fa','d41eb898ceb24c5795b74bc298699022'),
('2be7654098d242718f81a3d322d19b20','d41eb898ceb24c5795b74bc298699022'),
('4afe36775b1c4d3f83659bd70929e912','d41eb898ceb24c5795b74bc298699022'),
('9b87942c54934ebd9a195cd931a2ae77','d41eb898ceb24c5795b74bc298699022'),
('e25aac8cfcad4add87f1e0112540f86a','d41eb898ceb24c5795b74bc298699022');
/*!40000 ALTER TABLE `Memberships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Sales Tax Memberships`
--

LOCK TABLES `Sales Tax Memberships` WRITE;
/*!40000 ALTER TABLE `Sales Tax Memberships` DISABLE KEYS */;
INSERT INTO `Sales Tax Memberships` VALUES 
('de03d8df94b34400a7daa9a662f33922','04885d93e0ff411bbba7a2b0967f817f'),
('ef16fb213f9a4818983a96f2c563c3a2','04885d93e0ff411bbba7a2b0967f817f'),
('fa6888e3f16e40d48500364cc27495c5','04885d93e0ff411bbba7a2b0967f817f'),
('de03d8df94b34400a7daa9a662f33922','084ea0e14bd64c9582a80dba4bcaa382'),
('ef16fb213f9a4818983a96f2c563c3a2','301560a2bb8e4228b2f70f0e341820d6'),
('fa6888e3f16e40d48500364cc27495c5','301560a2bb8e4228b2f70f0e341820d6'),
('ef16fb213f9a4818983a96f2c563c3a2','dc5ae65d3b9941d2b0495b5183da7253');
/*!40000 ALTER TABLE `Sales Tax Memberships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Sales Taxes`
--

LOCK TABLES `Sales Taxes` WRITE;
/*!40000 ALTER TABLE `Sales Taxes` DISABLE KEYS */;
INSERT INTO `Sales Taxes` VALUES 
('04885d93e0ff411bbba7a2b0967f817f','c4b370528c18443e875a2526bc769e3a','\0','State Sales Tax',0.0625000000000000,NULL),
('084ea0e14bd64c9582a80dba4bcaa382','c4b370528c18443e875a2526bc769e3a','\0','5101669 Harris - Ft Bend ESD 100',0.0100000000000000,NULL),
('12eaae46fa0c4b7e8e5eac32e73a4680','c4b370528c18443e875a2526bc769e3a','','Not Taxable',0.0000000000000000,'8133c0578f7943b3af82b9e6187d2f55'),
('301560a2bb8e4228b2f70f0e341820d6','c4b370528c18443e875a2526bc769e3a','\0','3101990 Houston MTA',0.0100000000000000,NULL),
('dc5ae65d3b9941d2b0495b5183da7253','c4b370528c18443e875a2526bc769e3a','\0','2101017 Houston',0.0100000000000000,NULL),
('de03d8df94b34400a7daa9a662f33922','c4b370528c18443e875a2526bc769e3a','','77494 Katy',0.0725000000000000,'8133c0578f7943b3af82b9e6187d2f55'),
('ef16fb213f9a4818983a96f2c563c3a2','c4b370528c18443e875a2526bc769e3a','','770XX Houston',0.0825000000000000,'8133c0578f7943b3af82b9e6187d2f55'),
('fa6888e3f16e40d48500364cc27495c5','c4b370528c18443e875a2526bc769e3a','','77084 Houston',0.0725000000000000,'8133c0578f7943b3af82b9e6187d2f55');
/*!40000 ALTER TABLE `Sales Taxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Transaction Lines`
--

LOCK TABLES `Transaction Lines` WRITE;
/*!40000 ALTER TABLE `Transaction Lines` DISABLE KEYS */;
INSERT INTO `Transaction Lines` VALUES 
('1387f6aa1d274ca2945432af92405599','b49b012dcd56401081580c2880fb3ca7',1,'Sales Tax 77084 Houston',-18.5962500000000000,NULL,NULL,'8133c0578f7943b3af82b9e6187d2f55',NULL),
('2cd8057ae4d5473391801e4c61dcc435','2038c6d0fabe45d4b44c619d8d709ca2',1,'Windows Server 2012 Standard CAL',-441.3600000000000000,NULL,NULL,'c8565466b7384b7b859dedee410d6f0e',NULL),
('36144faecaab45f6bb8afdaf29c08c34','b5233543de42425daa483811fbfc15d2',2,'Invoice 1001 Total',123.4050000000000000,NULL,NULL,'a98b82c04a13405c82d9496fa938469a',NULL),
('5bba76643cc846738f34e6217447fb00','b49b012dcd56401081580c2880fb3ca7',0,'Deploy 2 computers.',-256.5000000000000000,NULL,NULL,'eb72dee532294ffc959077b843e660e0',NULL),
('5fda5c66c64441858e64fd88c7ac04fe','2038c6d0fabe45d4b44c619d8d709ca2',0,'Windows Server 2012 Standard',-883.9400000000000000,NULL,NULL,'c8565466b7384b7b859dedee410d6f0e',NULL),
('6f9d510777d54fba992791d87101a3c1','b49b012dcd56401081580c2880fb3ca7',2,'Invoice 1002 Total',275.0962500000000000,NULL,NULL,'a98b82c04a13405c82d9496fa938469a',NULL),
('73907cfda6da447a8de39a12b97413a8','2038c6d0fabe45d4b44c619d8d709ca2',3,'Invoice SZ2016010101 Total',1434.6372500000000000,NULL,NULL,'a98b82c04a13405c82d9496fa938469a',NULL),
('9f9347b1f44b4788a4aa81ed53f3d9a7','4bfc2fc8cf3b45c1b4c6e5da218ce4aa',2,'Invoice 1000 Total',123.4050000000000000,NULL,NULL,'a98b82c04a13405c82d9496fa938469a',NULL),
('a10da555db5b4077b3f9679041228268','b5233543de42425daa483811fbfc15d2',1,'Sales Tax 770XX Houston',-9.4050000000000000,NULL,NULL,'8133c0578f7943b3af82b9e6187d2f55',NULL),
('b3800142f592440497453351f37c6912','4bfc2fc8cf3b45c1b4c6e5da218ce4aa',1,'Sales Tax 770XX Houston',-9.4050000000000000,NULL,NULL,'8133c0578f7943b3af82b9e6187d2f55',NULL),
('cae4af983296496c8e2aecc72da50b0a','b5233543de42425daa483811fbfc15d2',0,'Computer Virus - cleanout out user profile, windows folers, program files, and temporary files.  Checked startup items - cleaner.  Virus scan - clean.',-114.0000000000000000,NULL,NULL,'eb72dee532294ffc959077b843e660e0',NULL),
('d1a8eed989334f938b2e55e74960bd4c','4bfc2fc8cf3b45c1b4c6e5da218ce4aa',0,'Could not print.  Documents stuck in queue, cleared.  Printiner is IP - confirmed IP.  Downloaded driver and installed.  Working',-114.0000000000000000,NULL,NULL,'eb72dee532294ffc959077b843e660e0',NULL),
('d1e4ef29faf44d70ac164c8dd860611c','2038c6d0fabe45d4b44c619d8d709ca2',2,'Sales Tax 770XX Houston',-109.3372500000000000,NULL,NULL,'8133c0578f7943b3af82b9e6187d2f55',NULL);
/*!40000 ALTER TABLE `Transaction Lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Transactions`
--

LOCK TABLES `Transactions` WRITE;
/*!40000 ALTER TABLE `Transactions` DISABLE KEYS */;
INSERT INTO `Transactions` VALUES 
('2038c6d0fabe45d4b44c619d8d709ca2','2016-02-06','SZ2016010101','81e2917ac5c34d1cb6f9d168cd0439db'),
('4bfc2fc8cf3b45c1b4c6e5da218ce4aa','2016-01-01','1000','81e2917ac5c34d1cb6f9d168cd0439db'),
('b49b012dcd56401081580c2880fb3ca7','2016-02-07','1002','81e2917ac5c34d1cb6f9d168cd0439db'),
('b5233543de42425daa483811fbfc15d2','2016-02-07','1001','81e2917ac5c34d1cb6f9d168cd0439db');
/*!40000 ALTER TABLE `Transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Unit Measures`
--

LOCK TABLES `Unit Measures` WRITE;
/*!40000 ALTER TABLE `Unit Measures` DISABLE KEYS */;
INSERT INTO `Unit Measures` VALUES 
('0bbd2b97414e4016819963c56cd68cc3','Inches','IN',''),
('0d775fe617fa40dc97327321299a5895','Gigabyte','GB',''),
('0ddb78acaa804885b2a1c8194d9062ab','Each','EA',''),
('159503b205a54554b945856599ab1dee','Yard','YRD',''),
('188faf8ce65a463f868645107b8629f9','Minutes','MIN',''),
('1a409157af9f4359a7c0db9378c062f9','Tote','TTE',''),
('3bbc49e44c124bb89447e4ca9dd1b95f','Feet','FT',''),
('3c88fd0ae4254adead2d786d830bfb0c','Ounces','OZ',''),
('5c838a6dfa9148179c168932e94d26eb','Megabyte','MB',''),
('6f576d029c0b4141a8cb41413be05e61','Kilobyte','KB',''),
('77490df6e9e344f080d2163a73bdb99d','Pounds','LBS',''),
('89334424ee1f4c88a3139aa0a93bfaa0','Meter','M',''),
('a1e33d01c1fc46c2bf5928690a91da0a','Cubic Inch','IN3',''),
('a73c5345517440d68cecb20b800d6f0e','Terabyte','TB',''),
('adb0bf8814644b71a9e457e7f06eaa57','Centimeter','CM',''),
('b109a802ed5a4c2d9d4cda6637477ce2','Hours','HRS',''),
('cb03ed4131124b4fa210b2781f9c6028','Cubic Yard','YRD3',''),
('d294c78779bb49e081e58ce45fe6d78e','Cubic Feet','FT3',''),
('dbf9e3ab54c2458abf2553938d11a7cb','Barrel','BBL',''),
('f456bd7d262f43cdb486c9db440e037a','Dozen','DZN',''),
('f92e4468f181449cb6b3ab4329842adf','Gallon','GLN',''),
('fb0be5ae9ca04f7b8b54592ac5c367d2','Millimeter','MM','');
/*!40000 ALTER TABLE `Unit Measures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES 
('2a82d4e3cf01417ba943612de782b3fa','','Jacob Solomon','jacobs','jacobs@localhost','1969-12-31','\0'),
('2be7654098d242718f81a3d322d19b20','','Lurlene Jarvis','lurlenej','lurlenej@localhost','1969-12-31','\0'),
('4afe36775b1c4d3f83659bd70929e912','','Regina Marcy','reginam','reginam@localhost','1969-12-31','\0'),
('7ad023488e4f4058ba8098600224dcca','','Roxanne Clemons','roxannec','roxannec@localhost','1969-12-31','\0'),
('9b87942c54934ebd9a195cd931a2ae77','','Peter Rios','peterr','peterr@localhost','1969-12-31','\0'),
('e25aac8cfcad4add87f1e0112540f86a','','Larry Morgan','larrym','larrym@localhost','1969-12-31','\0');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


LOCK TABLES `Payroll Fields` WRITE;
/*!40000 ALTER TABLE `Payroll Fields` DISABLE KEYS */;
INSERT INTO `Payroll Fields` VALUES 
('0d381634c4b7465882e5fcb690b2506c','Week 2 Reg','af32731792b64a6081a6a1f73d9afca0','d906c444324f4492a40ff91313df9f60',NULL),
('233f245621904beb946ac5469ce57d1c','FUTA','2f542318ae174eaf8bb95f02ed8f6df5','f784c8228eb6449cb086d1ec2a57bbd4','4cf248090e264c539591c77d2aad0935'),
('2991024369374d18aba72c64a43143df','MC Contributed','2f542318ae174eaf8bb95f02ed8f6df5','b9de1b750db4440982a67d9914ba1c77','49258926ce0b4befb2df152b17d546db'),
('2e82ced4f342486b84e12a3c8bbed578','Bonus','af32731792b64a6081a6a1f73d9afca0','d906c444324f4492a40ff91313df9f60',NULL),
('37fb554a13aa4add889c21a89e7c075c','SUTA','2f542318ae174eaf8bb95f02ed8f6df5','f05b72b94d4c41d6b47a2988d222e426','d7f0321814c64a97a257d1dfe5be0853'),
('538e3bb3c2e94fc68c5b1e11a94fc358','Week 1 Reg','af32731792b64a6081a6a1f73d9afca0','04895bc4a4854d94897d79e78ce0c0f2',NULL),
('5aec5ea4806e4fe5958d224d7418e5b8','Health Insurance Contributed','2f542318ae174eaf8bb95f02ed8f6df5','58086bd0ba814d9781f148bff89fd875','eb8616b0a5bf4ee8b11445771eb5fd6f'),
('7311f184b44546b0955717b206e25db4','SS Contributed','2f542318ae174eaf8bb95f02ed8f6df5','d51d798e22434c73827abdf8094ab137','5e859ff7ccb840adb5b7f161fd441eac'),
('822c1f1949b64acd925787a522ab7536','MC Withheld','f1b0d26375e74b0eb1aeb53bace00499',NULL,'f416969a56634d1894197cd11cb5ba9a'),
('8f714b78283647e7a7f63723c2aef2cd','Health Insurance Withheld','af32731792b64a6081a6a1f73d9afca0','eb8616b0a5bf4ee8b11445771eb5fd6f',NULL),
('d2509b4fc4954354a2f8b862c341e1f5','Week 1 OT','af32731792b64a6081a6a1f73d9afca0','d906c444324f4492a40ff91313df9f60',NULL),
('e910ac87be22450697ca83ca468f7415','SS Withheld','f1b0d26375e74b0eb1aeb53bace00499',NULL,'f12ba1e7ea5c45689733fcb5eea998a8'),
('efb53a5a4c844ffea29989a5a633b392','Week 2 OT','af32731792b64a6081a6a1f73d9afca0','d906c444324f4492a40ff91313df9f60',NULL),
('f8a8bc7a291a433baa8966d700849059','FIT Withheld','f1b0d26375e74b0eb1aeb53bace00499',NULL,'ba93a67ed4ed4757a9c0a2573207ccd7');
/*!40000 ALTER TABLE `Payroll Fields` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-09 21:16:12
