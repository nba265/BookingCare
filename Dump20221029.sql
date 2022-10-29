-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: booking_care
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT 'PENDING',
  `customers_id` bigint DEFAULT NULL,
  `services_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgpm0dn7yxbk1dp5tu0cl8qful` (`customers_id`),
  KEY `FKrggc6w6hok5cjbru4dkv9wjk5` (`services_id`),
  CONSTRAINT `FKgpm0dn7yxbk1dp5tu0cl8qful` FOREIGN KEY (`customers_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FKrggc6w6hok5cjbru4dkv9wjk5` FOREIGN KEY (`services_id`) REFERENCES `services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,NULL,'2022-10-28 21:43:34','anhhuy bede',NULL,1,1),(2,NULL,'2022-10-28 21:48:33','anhhuy bede',NULL,2,1),(3,NULL,'2022-10-28 21:54:33','anhhuy bede',NULL,3,1),(4,NULL,'2022-10-28 22:15:20','anhhuy bede',NULL,4,1);
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `name_booking` varchar(255) DEFAULT NULL,
  `name_patient` varchar(255) DEFAULT NULL,
  `phone_booking` varchar(255) DEFAULT NULL,
  `phone_patient` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'1999-05-26','nbs265@gmail.com','MALE',NULL,'Nguyen Bao Anh',NULL,'0786660455'),(2,'1999-05-26','nbs265@gmail.com','MALE',NULL,'Nguyen Bao Anh',NULL,'0786660455'),(3,'1999-05-26','nbs265@gmail.com','MALE',NULL,'Nguyen Bao Anh',NULL,'0786660455'),(4,'1999-05-26','nbs265@gmail.com','MALE',NULL,'Nguyen Bao Anh',NULL,'0786660455');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital_cilinic`
--

DROP TABLE IF EXISTS `hospital_cilinic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hospital_cilinic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `manager_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9e7apd1at5cnq71ceegotdjbk` (`manager_id`),
  CONSTRAINT `FK9e7apd1at5cnq71ceegotdjbk` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_cilinic`
--

LOCK TABLES `hospital_cilinic` WRITE;
/*!40000 ALTER TABLE `hospital_cilinic` DISABLE KEYS */;
INSERT INTO `hospital_cilinic` VALUES (1,'Bệnh viện đa khoa',4),(2,'Phòng Khám Nguyễn Bảo Anh',5),(4,'Phòng khám Đa Khoa Huế',9),(7,'Benh vien hoan my',4);
/*!40000 ALTER TABLE `hospital_cilinic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `hospital_cilinic_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2f1lv2432d4qjh9cv6u2n7trp` (`hospital_cilinic_id`),
  CONSTRAINT `FK2f1lv2432d4qjh9cv6u2n7trp` FOREIGN KEY (`hospital_cilinic_id`) REFERENCES `hospital_cilinic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,NULL,'Khám Thường',100000,1),(2,NULL,'Khám Bảo Hiểm',0,1);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialist`
--

DROP TABLE IF EXISTS `specialist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `hospital_cilinic_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q1w9rthb2tydm8vu6j4gtu3b` (`name`),
  KEY `FKnq9du6x6fo1filkym70flxvyt` (`hospital_cilinic_id`),
  CONSTRAINT `FKnq9du6x6fo1filkym70flxvyt` FOREIGN KEY (`hospital_cilinic_id`) REFERENCES `hospital_cilinic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialist`
--

LOCK TABLES `specialist` WRITE;
/*!40000 ALTER TABLE `specialist` DISABLE KEYS */;
INSERT INTO `specialist` VALUES (1,'Tai, Mũi, Miệng',1),(2,'Mắt',1),(3,'Da',1),(4,'Đường tiêu hoá',1);
/*!40000 ALTER TABLE `specialist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `time_doctors`
--

DROP TABLE IF EXISTS `time_doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `time_doctors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `appointments_id` bigint DEFAULT NULL,
  `doctor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnl9qprb42d8l8n1mwxhsu5jvc` (`appointments_id`),
  KEY `FKilb7cd6hsp2i6iswqiovjvdw1` (`doctor_id`),
  CONSTRAINT `FKilb7cd6hsp2i6iswqiovjvdw1` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKnl9qprb42d8l8n1mwxhsu5jvc` FOREIGN KEY (`appointments_id`) REFERENCES `appointments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=446 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `time_doctors`
--

LOCK TABLES `time_doctors` WRITE;
/*!40000 ALTER TABLE `time_doctors` DISABLE KEYS */;
INSERT INTO `time_doctors` VALUES (342,'2022-08-31','08:00:00','08:30:00',NULL,8),(364,'2022-08-30','08:00:00','08:30:00',NULL,8),(365,'2022-08-28','08:30:00','09:00:00',NULL,8),(366,'2022-08-29','09:00:00','09:30:00',NULL,8),(367,'2022-08-29','09:30:00','10:00:00',NULL,8),(368,'2022-08-29','10:00:00','10:30:00',NULL,NULL),(369,'2022-08-29','10:30:00','11:00:00',NULL,NULL),(370,'2022-08-31','08:30:00','09:00:00',NULL,NULL),(371,'2022-08-31','09:00:00','09:30:00',NULL,NULL),(372,'2022-08-31','09:30:00','10:00:00',NULL,NULL),(373,'2022-08-31','10:00:00','10:30:00',NULL,NULL),(374,'2022-08-31','10:30:00','11:00:00',NULL,NULL),(375,'2022-08-27','08:30:00','09:00:00',NULL,NULL),(376,'2022-08-30','08:30:00','09:00:00',NULL,NULL),(377,'2022-08-30','09:00:00','09:30:00',NULL,NULL),(378,'2022-08-30','09:30:00','10:00:00',NULL,NULL),(379,'2022-08-30','10:00:00','10:30:00',NULL,NULL),(380,'2022-08-30','10:30:00','11:00:00',NULL,NULL),(381,'2022-08-26','08:00:00','08:30:00',NULL,NULL),(382,'2022-08-26','08:30:00','09:00:00',NULL,NULL),(383,'2022-08-26','09:00:00','09:30:00',NULL,NULL),(384,'2022-08-26','09:30:00','10:00:00',NULL,NULL),(385,'2022-08-26','10:00:00','10:30:00',NULL,NULL),(386,'2022-08-26','10:30:00','11:00:00',NULL,NULL),(387,'2022-09-03','08:00:00','08:30:00',NULL,NULL),(388,'2022-09-03','08:30:00','09:00:00',NULL,NULL),(389,'2022-09-03','09:00:00','09:30:00',NULL,NULL),(390,'2022-09-03','09:30:00','10:00:00',NULL,NULL),(391,'2022-09-03','10:00:00','10:30:00',NULL,NULL),(392,'2022-09-03','10:30:00','11:00:00',NULL,NULL),(393,'2022-09-04','08:00:00','08:30:00',NULL,NULL),(394,'2022-09-04','08:30:00','09:00:00',NULL,NULL),(395,'2022-09-04','09:00:00','09:30:00',NULL,NULL),(396,'2022-09-04','09:30:00','10:00:00',NULL,NULL),(397,'2022-09-04','10:00:00','10:30:00',NULL,NULL),(398,'2022-09-04','10:30:00','11:00:00',NULL,NULL),(399,'2022-09-05','08:00:00','08:30:00',NULL,NULL),(400,'2022-09-05','08:30:00','09:00:00',NULL,NULL),(401,'2022-09-05','09:00:00','09:30:00',NULL,NULL),(402,'2022-09-05','09:30:00','10:00:00',NULL,NULL),(403,'2022-09-05','10:00:00','10:30:00',NULL,NULL),(404,'2022-09-05','10:30:00','11:00:00',NULL,NULL),(405,'2022-09-06','08:00:00','08:30:00',NULL,NULL),(406,'2022-09-06','08:30:00','09:00:00',NULL,NULL),(407,'2022-09-06','09:00:00','09:30:00',NULL,NULL),(408,'2022-09-06','09:30:00','10:00:00',NULL,NULL),(409,'2022-09-06','10:00:00','10:30:00',NULL,NULL),(410,'2022-09-06','10:30:00','11:00:00',NULL,NULL),(411,'2022-09-07','08:00:00','08:30:00',NULL,7),(412,'2022-09-07','08:30:00','09:00:00',NULL,7),(413,'2022-09-07','09:00:00','09:30:00',NULL,7),(414,'2022-09-07','09:30:00','10:00:00',NULL,7),(415,'2022-09-07','10:00:00','10:30:00',NULL,7),(416,'2022-09-07','10:30:00','11:00:00',NULL,NULL),(417,'2022-09-08','08:00:00','08:30:00',NULL,7),(418,'2022-09-08','08:30:00','09:00:00',NULL,7),(419,'2022-09-08','09:00:00','09:30:00',NULL,7),(420,'2022-09-08','09:30:00','10:00:00',NULL,7),(421,'2022-09-08','10:00:00','10:30:00',NULL,7),(422,'2022-09-08','10:30:00','11:00:00',NULL,7),(423,'2022-09-09','08:00:00','08:30:00',NULL,6),(424,'2022-09-09','08:30:00','09:00:00',NULL,6),(425,'2022-09-09','09:00:00','09:30:00',NULL,7),(426,'2022-09-09','09:30:00','10:00:00',NULL,7),(427,'2022-09-09','10:00:00','10:30:00',NULL,7),(428,'2022-09-09','10:30:00','11:00:00',NULL,7),(429,'2022-09-10','08:00:00','08:30:00',NULL,8),(430,'2022-09-10','08:30:00','09:00:00',NULL,8),(431,'2022-09-10','09:00:00','09:30:00',NULL,8),(432,'2022-09-10','09:30:00','10:00:00',NULL,8),(433,'2022-09-10','10:00:00','10:30:00',NULL,8),(434,'2022-09-10','10:30:00','11:00:00',NULL,8),(435,'2022-09-13','08:00:00','08:30:00',NULL,8),(436,'2022-09-13','08:30:00','09:00:00',NULL,8),(437,'2022-09-14','08:00:00','08:30:00',NULL,8),(438,'2022-09-14','08:30:00','09:00:00',NULL,8),(439,'2022-09-21','08:00:00','08:30:00',4,8),(440,'2022-09-21','08:30:00','09:00:00',NULL,8),(441,'2022-09-21','09:00:00','09:30:00',NULL,8),(442,'2022-09-21','09:30:00','10:00:00',NULL,8),(443,'2022-10-28','10:00:00','09:00:00',NULL,8),(444,'2022-10-29','10:00:00','09:00:00',NULL,8),(445,'2022-10-29','10:00:00','09:00:00',NULL,8);
/*!40000 ALTER TABLE `time_doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `hospital_cilinic_id` bigint DEFAULT NULL,
  `specialist_id` bigint DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  KEY `FKrtgjvs4wig769bsuhxy3h5qx3` (`hospital_cilinic_id`),
  KEY `FK64ucchq6bas6ucv0iy1ufdirr` (`specialist_id`),
  CONSTRAINT `FK64ucchq6bas6ucv0iy1ufdirr` FOREIGN KEY (`specialist_id`) REFERENCES `specialist` (`id`),
  CONSTRAINT `FKrtgjvs4wig769bsuhxy3h5qx3` FOREIGN KEY (`hospital_cilinic_id`) REFERENCES `hospital_cilinic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'123 Nguyen Van Linh','2022-10-25','','admin@gmail.com',NULL,'Nguyen Bao Anh','MALE','VN','$2a$10$XOjM6JHlYQX18.cVCYnKFOCWYbXNlIyuijy3.rReDXN786aAPjMBq',NULL,'ACTIVE',NULL,NULL,NULL,'admin'),(4,'123 Nguyen Van Linh','2022-10-25','','1232324233@gmail.com',NULL,'NguyenBaoAnh','MALE','VN','$2a$10$XOjM6JHlYQX18.cVCYnKFOCWYbXNlIyuijy3.rReDXN786aAPjMBq',NULL,'ACTIVE',NULL,NULL,NULL,'manager1'),(5,'123 Nguyen Van Linh','2022-10-25','Bs','12342332@gmail.com',NULL,'NguyenAnhHuy','MALE','VN','abc',NULL,'ACTIVE',NULL,NULL,NULL,NULL),(6,'123 Nguyen Van Linh','2022-10-25','Bs','12334@gmail.com',NULL,'NguyenBaoAnh','MALE','VN','abc',NULL,'ACTIVE',2,3,NULL,NULL),(7,'123 Nguyen Van Linh','2022-10-25','Bs.CKI','nguyenbaoanh@gmail.com',NULL,'Nguyễn Bảo Anh','MALE','VN','abc',NULL,'ACTIVE',1,1,NULL,NULL),(8,'123 Nguyen Van Linh','2022-10-25','Ths.Bs','abc@gmail.com',NULL,'NguyenAnhHuy','MALE','VN','$2a$10$XOjM6JHlYQX18.cVCYnKFOCWYbXNlIyuijy3.rReDXN786aAPjMBq',NULL,'ACTIVE',1,1,NULL,'doctor1'),(9,'123 Nguyen Van Linh','2022-10-25',NULL,'anhuybede123@gmail.com',NULL,'Phan Tự Minh Duy','MALE',NULL,NULL,'07666034453','ACTIVE',NULL,NULL,NULL,NULL),(10,'123 Nguyen Van Linh','2022-10-26',NULL,'nguyenbaoan12h@gmail.com',NULL,'Phan Tự Minh Duy','MALE',NULL,NULL,'07666034453','ACTIVE',NULL,NULL,NULL,NULL),(11,'123 Nguyen Van Linh','2022-10-26',NULL,'nguyenbaoan1223h@gmail.com',NULL,'Phan Tự Minh Duy','MALE',NULL,NULL,'07666034453','ACTIVE',NULL,NULL,NULL,NULL),(12,'123 Nguyen Van Linh','2022-10-26',NULL,'nguyenbaoan12223h@gmail.com',NULL,'Phan Tự Minh Duy','MALE',NULL,NULL,'07666034453','ACTIVE',NULL,NULL,NULL,NULL),(18,NULL,NULL,NULL,'nbs265@gmail.com',NULL,NULL,NULL,NULL,'$2a$10$XOjM6JHlYQX18.cVCYnKFOCWYbXNlIyuijy3.rReDXN786aAPjMBq',NULL,'ACTIVE',NULL,NULL,NULL,'nba26599');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_MANAGER'),(3,'ROLE_DOCTOR'),(4,'ROLE_USER');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_relationship`
--

DROP TABLE IF EXISTS `user_role_relationship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_relationship` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKjrky4py4v4t0v3aw7mmc92sne` (`role_id`),
  CONSTRAINT `FKehp88qoke5p45o64h3j6p08q5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKjrky4py4v4t0v3aw7mmc92sne` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_relationship`
--

LOCK TABLES `user_role_relationship` WRITE;
/*!40000 ALTER TABLE `user_role_relationship` DISABLE KEYS */;
INSERT INTO `user_role_relationship` VALUES (1,1),(4,2),(5,2),(9,2),(6,3),(7,3),(8,3),(10,4),(11,4),(12,4),(18,4);
/*!40000 ALTER TABLE `user_role_relationship` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-29  1:23:51
