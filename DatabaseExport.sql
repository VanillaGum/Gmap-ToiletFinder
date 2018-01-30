-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: map_proj
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet`
--

LOCK TABLES `toilet` WRITE;
/*!40000 ALTER TABLE `toilet` DISABLE KEYS */;
INSERT INTO `toilet` VALUES (1,NULL,1.4033967888631766,103.92199516296387),(2,NULL,1.39,103.85809421539307),(3,NULL,1.384060909285942,103.82380485534668),(4,NULL,1.3686305906227134,103.9572286605835);
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
  `removal_flags` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet_info`
--

LOCK TABLES `toilet_info` WRITE;
/*!40000 ALTER TABLE `toilet_info` DISABLE KEYS */;
INSERT INTO `toilet_info` VALUES (1,1,0,0,0,0,0),(2,2,2,2,0,0,0),(3,3,0,0,1,0,0),(5,4,4,1,0,0,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet_request`
--

LOCK TABLES `toilet_request` WRITE;
/*!40000 ALTER TABLE `toilet_request` DISABLE KEYS */;
INSERT INTO `toilet_request` VALUES (2,1.366871562828626,103.9604902267456),(3,1.3480075179351878,103.86234283447266);
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet_request_info`
--

LOCK TABLES `toilet_request_info` WRITE;
/*!40000 ALTER TABLE `toilet_request_info` DISABLE KEYS */;
INSERT INTO `toilet_request_info` VALUES (2,2,3,19,5,1,12),(3,3,3,20,6,0,2);
/*!40000 ALTER TABLE `toilet_request_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toilet_request_user_upvotes`
--

DROP TABLE IF EXISTS `toilet_request_user_upvotes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toilet_request_user_upvotes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `toilet_request_id` int(11) NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `vote` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toilet_request_user_upvotes`
--

LOCK TABLES `toilet_request_user_upvotes` WRITE;
/*!40000 ALTER TABLE `toilet_request_user_upvotes` DISABLE KEYS */;
/*!40000 ALTER TABLE `toilet_request_user_upvotes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-30 13:34:25
