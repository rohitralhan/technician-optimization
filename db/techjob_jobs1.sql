-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: techjob
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `jobs1`
--

DROP TABLE IF EXISTS `jobs1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jobs1` (
  `id` int NOT NULL AUTO_INCREMENT,
  `jobId` varchar(255) NOT NULL,
  `region` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `dispatchArea` varchar(45) DEFAULT NULL,
  `lati` varchar(45) DEFAULT NULL,
  `longi` varchar(45) DEFAULT NULL,
  `requiredSkill` varchar(2000) DEFAULT NULL,
  `skillLevel` int DEFAULT NULL,
  `requiredTools` varchar(2000) DEFAULT NULL,
  `ispulljob` varchar(45) DEFAULT NULL,
  `dispatch` varchar(45) DEFAULT NULL,
  `priority` varchar(45) DEFAULT NULL,
  `earlyStartDate` varchar(45) DEFAULT NULL,
  `dueDate` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `jobId_UNIQUE` (`jobId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobs1`
--

LOCK TABLES `jobs1` WRITE;
/*!40000 ALTER TABLE `jobs1` DISABLE KEYS */;
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (5,'NC2022096162043','905 CENTRAL','BRAMPTON','','43737397','-79699360','PROV - FIELD - CON/BUS - Connections',5,'Training - PF - Mobility Bullet','0','','','4/6/22 18:34','4/6/22 22:34','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (6,'NC2022096162104','905 CENTRAL','BRAMPTON','','43741675','-79692354','PROV - FIELD - CON - Access and Desktop - VDSL2',5,'Project - IPTV - Special','0','','','4/6/22 18:47','4/5/22 17:00','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (7,'NC2022096162078','905 CENTRAL','BRAMPTON','','43746582','-79698920','ASSUR - FIELD - CON - IPTV - Copper - SFU',5,'Technician - Climber','0','','','4/6/22 18:39','4/6/22 22:39','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (8,'NC2022096161720','705','BARRIE','','44224590','-79970500','ASSUR - FIELD - CON - Modem Drop',4,'Training - FTTx - Huawei ONT','-1','','','4/6/22 0:00','3/31/22 15:25','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (9,'NC2022096161721','705','BARRIE','','44226139','-79961769','ASSUR - FIELD - CON - ADSL/ADSL2+',4,'Training - WiFi Pod','-1','','','4/6/22 0:00','3/31/22 15:27','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (10,'NC2022097162280','613','BROCKVILLE','','44590139','-75682571','ASSUR - FIELD - BUS - BTC - IPTV - FTTB',3,'Site - Non-Dedicated','-1','','','4/7/22 0:00','4/7/22 14:41','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (11,'NC2022073160320','613','BROCKVILLE','','44588607','-75684223','ASSUR - FIELD - CON - FTTH',3,'Technician - Non-Climber','-1','','','3/14/22 0:00','3/13/22 6:16','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (12,'NC2022073160188','705','COLLINGWOOD','','44498737','-79795903','ASSUR - FIELD - VRGN - RES INTERNET - PBOND',4,'Technician - FTTx - Brownfield GLB','-1','','','3/14/22 0:00','3/12/22 16:25','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (13,'NC2022086161356','705','COLLINGWOOD','','44513067','-79811465','ASSUR - FIELD - WHSL - RES - xDSL',4,'Training - WiFi Pod','-1','','','3/27/22 0:00','3/27/22 18:34','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (14,'NC2022075160679','416 EAST','DOWNTOWN','','43641500','-79411442','PROV - FIELD - CON/BUS - Connections',4,'Technician - Dedicated Load - Business','-1','','','3/16/22 0:00','3/16/22 23:19','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (15,'NC2022099162557','416 EAST','DOWNTOWN','','43644849','-79384369','PROV - FIELD - WHSL - Internet',4,'Site - Non-Dedicated|Project - IPTV - Special','-1','','','4/9/22 0:00','4/9/22 16:49','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (16,'NC2022097162296','ERR QUEBEC','DRUMMOND','','45880203','-72546093','ASSUR - FIELD - CON - VDSL2',1,'Site - Non-Dedicated','-1','','','4/7/22 0:00','4/7/22 17:01','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (17,'NC2022096162011','ERR QUEBEC','DRUMMOND','','45882951','-72542627','ASSUR - FIELD - BUS - ADSL/ADSL2+',1,'Site - Non-Dedicated','-1','','','4/6/22 0:00','4/5/22 15:56','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (19,'AI202204120163959001','NB WESTERN','FREDERICTON','','45960772','-66642028','PROV - DATA - Fiber',3,'Site - Non-Dedicated','0','','','4/13/22 0:00','4/13/22 1:25','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (20,'AI202203200162789001','NB WESTERN','FREDERICTON','','45986502','-66934048','ASSUR - FIELD - BUS - FTTB',3,'Site - Non-Dedicated','0','','','3/20/22 0:00','3/20/22 5:58','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (21,'AI202204090163767001','NB WESTERN','FREDERICTON','','45980956','-66670332','ASSUR - DATA - IPVPN - Fiber',3,'Site - Non-Dedicated','0','','','4/9/22 0:00','4/9/22 4:20','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (22,'AI202202180161340001','NB WESTERN','FREDERICTON','','45916374','-66597094','ASSUR - DATA - Fiber',3,'Site - Non-Dedicated','0','','','2/18/22 0:00','2/18/22 12:44','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (23,'PE202203020161973001','PE','CHARLOTTETOWN PE','','46254984','-63114761','ASSUR - FIELD - CON - FTTH',3,'Special - Safety - Remote Area','0','','','11/20/17 0:00','11/20/17 23:59','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (24,'NC2022096161982','519','CHATHAM','','42593683','-82387184','ASSUR - CABLE - Demand - Single/269H/Pattern',4,'Vehicle - Truck with Generator','-1','','','4/6/22 0:00','4/5/22 0:28','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (25,'NC2022075160734','519','CHATHAM','','42362380','-82191710','ASSUR - FIELD - CON - Voice',2,'Tool - Voltage Detector and Ground Cord','-1','','','3/16/22 0:00','3/16/22 23:28','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (26,'NC2022052159483','519','KITCHENER','','43428055','-80465080','PROV - ORS - Norstar',3,'Special - Safety - Remote Area','-1','','','2/21/22 0:00','2/22/22 12:49','Open');
INSERT INTO `jobs1` (`id`, `jobId`, `region`, `district`, `dispatchArea`, `lati`, `longi`, `requiredSkill`, `skillLevel`, `requiredTools`, `ispulljob`, `dispatch`, `priority`, `earlyStartDate`, `dueDate`, `status`) VALUES (27,'NC2022096161753','519','KITCHENER','','43390285','-80363800','PROV - ORS - Toshiba',3,'Ladder - 28','-1','','','4/6/22 0:00','4/1/22 20:32','Open');
/*!40000 ALTER TABLE `jobs1` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-02 14:28:12
