-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: ats
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airline`
--

DROP TABLE IF EXISTS `airline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `airline` (
  `airline_id` int NOT NULL,
  `airline_model` varchar(255) DEFAULT NULL,
  `airline_name` varchar(255) DEFAULT NULL,
  `number_of_seats` int NOT NULL,
  PRIMARY KEY (`airline_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airline`
--

LOCK TABLES `airline` WRITE;
/*!40000 ALTER TABLE `airline` DISABLE KEYS */;
INSERT INTO `airline` VALUES (20,'BI-101','Biman Bangladesh',160),(21,'BI-102','Biman Bangladesh',150),(23,'BI-103','Biman Bangladesh',250),(26,'BI-104','Biman Bangladesh',120),(49,'QA-101','Qatar Airways',150),(50,'QA-102','Qatar Airways',120);
/*!40000 ALTER TABLE `airline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `booking_number` int NOT NULL,
  `booked_seats` int NOT NULL,
  `booking_amount` double NOT NULL,
  `booking_date` datetime(6) NOT NULL,
  `flight_flight_id` int DEFAULT NULL,
  `passenger_user_id` int DEFAULT NULL,
  PRIMARY KEY (`booking_number`),
  KEY `FKfgvun0amyvoviy0gc6teawo16` (`flight_flight_id`),
  KEY `FKpb2dsa7rjfrchsbvxdmusqs8f` (`passenger_user_id`),
  CONSTRAINT `FKfgvun0amyvoviy0gc6teawo16` FOREIGN KEY (`flight_flight_id`) REFERENCES `flight` (`flight_id`),
  CONSTRAINT `FKpb2dsa7rjfrchsbvxdmusqs8f` FOREIGN KEY (`passenger_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight` (
  `flight_id` int NOT NULL,
  `arrival_date` date NOT NULL,
  `arrival_location` varchar(255) NOT NULL,
  `arrival_time` time NOT NULL,
  `departure_date` date NOT NULL,
  `departure_location` varchar(255) NOT NULL,
  `departure_time` time NOT NULL,
  `fare` double NOT NULL,
  `remaining_seats` int NOT NULL,
  `airline_airline_id` int DEFAULT NULL,
  PRIMARY KEY (`flight_id`),
  KEY `FKr3u8yeggnkp824oc93a4o12kn` (`airline_airline_id`),
  CONSTRAINT `FKr3u8yeggnkp824oc93a4o12kn` FOREIGN KEY (`airline_airline_id`) REFERENCES `airline` (`airline_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight`
--

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
INSERT INTO `flight` VALUES (22,'2024-04-30','Chittagong','13:33:00','2024-04-18','Dhaka','13:33:00',8000,150,23),(53,'2024-04-23','Chittagong','03:25:00','2024-04-22','Dhaka','23:25:00',7000,150,49),(54,'2024-04-24','Chittagong','18:51:00','2024-04-23','Dhaka','23:50:00',7000,150,49);
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (58);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `dtype` varchar(31) NOT NULL,
  `user_id` int NOT NULL,
  `user_contact` varchar(255) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `user_full_name` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `passenger_passport` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_33uo7vet9c79ydfuwg1w848f` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Passenger',1,'01888888888','user1@gmail.com','Updated User1','$2a$10$6LJ67eemW2UbOZj9XkSZhefaNY01.LoN6zlNVI0BqFZz15Uhp2T2C','BS5465'),('Passenger',11,'01888888888','user2@gmail.com','User2','$2a$10$sWLkIXOwTsSASWbx//ZhpeBOQD8N61iDlR0ZF.UrmQCOD.7RGeAei','BS5465'),('Passenger',16,'01738389415','abc@gmail.com','user1','$2a$10$r4TY8msY7UocWSpG5SOkVei6t7m8FEH.Z5PWJd1MZQpYda1CuCdHu','ASD1651'),('Passenger',32,'01738389415','user3@gmail.com','user3','$2a$10$03updzhMrmFQ5HXILrv4xOdMRKIDLphtJ84jSDIPEtRk7D6kKm.su','AF4646'),('Passenger',33,'0173845164','user4@gmail.com','user4','$2a$10$JOhXqPTpJm3lB4rjV84ar.YHzphJC9NQVJC50Cqp0JwD3YggZKwem','AF48489'),('Passenger',34,'007417258','user5@gmail.com','user5','$2a$10$Ks8tcFyua3jg4V81lWPEFuU8wpfh/dRniiSqSwvi0MsAWH1gsfyLi','ASD111'),('Passenger',55,'0516801610','user6@gmail.com','user6','$2a$10$8JYFuxvmRDAqJFk2QBoRweu.sZucHIro0w4idzGKrfFW4iiEUlIC.','ASDA444'),('Passenger',56,'156464568','user8@gmail.com','user8','$2a$10$K0GQUzgl0F/ZRTuObKRQBu1cuG.0EAbpTKhSU/ClS1cPx0qi3Aao2','ASDA54554'),('Passenger',57,'0115846','user9@gmail.com','user9','$2a$10$aMdZC/yJlYFO2XYbx0vEoux9qWFM.Uc8r2UKysLBVVbSQ2Vs9aRxK','ADS454'),('Admin',101,'01811918','admin1@gmail.com','Admin1','$2a$12$EJyuZUvFuGPI7ch5/DTpC.2euI62eJte.ERh6RiCLyPSb3OVcOY6u',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'ats'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-25 11:31:06
