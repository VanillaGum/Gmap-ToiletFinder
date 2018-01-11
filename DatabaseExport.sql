-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: toilet_finder
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `toilet`
--

DROP TABLE IF EXISTS `toilet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toilet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet`
--

LOCK TABLES `toilet` WRITE;
/*!40000 ALTER TABLE `toilet` DISABLE KEYS */;
INSERT INTO `toilet` VALUES (1,NULL,1.3839067272586263,103.86483192443848),(2,NULL,1.3773425857522492,103.8933277130127);
/*!40000 ALTER TABLE `toilet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toilet_info`
--

DROP TABLE IF EXISTS `toilet_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toilet_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `toilet_id` int(11) NOT NULL,
  `rating` int(11) DEFAULT '0',
  `amt_of_rating` int(11) DEFAULT '0',
  `genderM` tinyint(1) NOT NULL,
  `toilet_group` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet_info`
--

LOCK TABLES `toilet_info` WRITE;
/*!40000 ALTER TABLE `toilet_info` DISABLE KEYS */;
INSERT INTO `toilet_info` VALUES (1,1,4,1,1,0),(2,2,3,1,1,0);
/*!40000 ALTER TABLE `toilet_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toilet_request`
--

DROP TABLE IF EXISTS `toilet_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toilet_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet_request`
--

LOCK TABLES `toilet_request` WRITE;
/*!40000 ALTER TABLE `toilet_request` DISABLE KEYS */;
INSERT INTO `toilet_request` VALUES (1,1.3823876985756578,103.8592529296875),(2,1.3844041318498561,103.86320114135742),(3,1.3823018928664086,103.85921001434326),(4,1.4059533141519138,103.87487411499023),(5,1.3791257387299976,103.85269224643707),(6,1.3892266717560315,103.86131286621094),(7,1.3882399087608555,103.81831169128418),(8,1.3884973252341084,103.87255668640137),(9,1.3912430992034028,103.8731575012207),(10,1.384249949844868,103.86435985565186),(11,1.3858373536597723,103.85921001434326),(12,1.388951826126219,103.86560440063477),(13,1.3843786583018942,103.85616302490234),(14,1.3882399087608555,103.86633396148682),(15,1.3851938117008136,103.86054039001465);
/*!40000 ALTER TABLE `toilet_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toilet_request_info`
--

DROP TABLE IF EXISTS `toilet_request_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toilet_request_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `toilet_request_id` int(11) NOT NULL,
  `approval` int(11) NOT NULL DEFAULT '0',
  `rating` int(11) DEFAULT '0',
  `amt_of_rating` int(11) DEFAULT '0',
  `genderM` tinyint(1) NOT NULL,
  `removal_flags` int(11) DEFAULT '0',
  `toilet_group` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet_request_info`
--

LOCK TABLES `toilet_request_info` WRITE;
/*!40000 ALTER TABLE `toilet_request_info` DISABLE KEYS */;
INSERT INTO `toilet_request_info` VALUES (1,1,1,0,0,1,0,0),(2,2,1,0,0,1,0,0),(3,3,1,0,0,1,0,0),(4,4,1,0,0,1,0,0),(5,5,1,0,0,1,0,0),(6,6,1,0,0,1,0,0),(7,7,1,0,0,1,0,0),(8,8,1,0,0,1,0,0),(9,9,1,0,0,1,0,0),(10,10,1,0,0,1,0,0),(11,11,1,0,0,1,0,0),(12,12,1,0,0,0,0,0),(13,13,1,0,0,1,0,0),(14,14,1,0,0,1,0,0),(15,15,1,0,0,-1,0,0);
/*!40000 ALTER TABLE `toilet_request_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-11 20:42:07
