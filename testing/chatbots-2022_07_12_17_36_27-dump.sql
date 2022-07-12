-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: chatbots
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.22-MariaDB

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
-- Table structure for table `chatbot`
--

DROP TABLE IF EXISTS `chatbot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatbot` (
  `id` varchar(36) NOT NULL,
  `CHATBOTNAME` varchar(32) NOT NULL,
  `CREATEDAT` date DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CHATBOT_user_id` (`user_id`),
  CONSTRAINT `FK_CHATBOT_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatbot`
--

LOCK TABLES `chatbot` WRITE;
/*!40000 ALTER TABLE `chatbot` DISABLE KEYS */;
INSERT INTO `chatbot` VALUES ('35b8b2f5-8bf3-438f-b7eb-222820298b2f','MRVN','2022-07-12','5275df08-3b68-4bc1-923c-c5cfb9296492');
/*!40000 ALTER TABLE `chatbot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatbotunknowntexts`
--

DROP TABLE IF EXISTS `chatbotunknowntexts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatbotunknowntexts` (
  `id` varchar(36) NOT NULL,
  `AMOUNTUSED` int(11) DEFAULT NULL,
  `chatbot_id` varchar(36) DEFAULT NULL,
  `unknown_text_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CHATBOTUNKNOWNTEXTS_chatbot_id` (`chatbot_id`),
  KEY `FK_CHATBOTUNKNOWNTEXTS_unknown_text_id` (`unknown_text_id`),
  CONSTRAINT `FK_CHATBOTUNKNOWNTEXTS_chatbot_id` FOREIGN KEY (`chatbot_id`) REFERENCES `chatbot` (`id`),
  CONSTRAINT `FK_CHATBOTUNKNOWNTEXTS_unknown_text_id` FOREIGN KEY (`unknown_text_id`) REFERENCES `text` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatbotunknowntexts`
--

LOCK TABLES `chatbotunknowntexts` WRITE;
/*!40000 ALTER TABLE `chatbotunknowntexts` DISABLE KEYS */;
INSERT INTO `chatbotunknowntexts` VALUES ('4a45d8ec-32e3-41a9-94db-25503261d2e2',0,'35b8b2f5-8bf3-438f-b7eb-222820298b2f','c4d7c037-8672-442c-95b8-ed8e2f5688e8'),('cdd44922-3592-46e7-95c7-6b9c1bde48e0',0,'35b8b2f5-8bf3-438f-b7eb-222820298b2f','8c99b9c9-86c4-4211-bf12-90e2a997ca23');
/*!40000 ALTER TABLE `chatbotunknowntexts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionanswer`
--

DROP TABLE IF EXISTS `questionanswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionanswer` (
  `id` varchar(36) NOT NULL,
  `chatbot_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_QUESTIONANSWER_chatbot_id` (`chatbot_id`),
  CONSTRAINT `FK_QUESTIONANSWER_chatbot_id` FOREIGN KEY (`chatbot_id`) REFERENCES `chatbot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionanswer`
--

LOCK TABLES `questionanswer` WRITE;
/*!40000 ALTER TABLE `questionanswer` DISABLE KEYS */;
INSERT INTO `questionanswer` VALUES ('6d7e945a-afb3-4046-b42e-d8ceeb7d98d5','35b8b2f5-8bf3-438f-b7eb-222820298b2f');
/*!40000 ALTER TABLE `questionanswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionansweranswer`
--

DROP TABLE IF EXISTS `questionansweranswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionansweranswer` (
  `id` varchar(36) NOT NULL,
  `AMOUNTUSED` int(11) DEFAULT NULL,
  `answer_id` varchar(36) DEFAULT NULL,
  `question_answer_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_QUESTIONANSWERANSWER_answer_id` (`answer_id`),
  KEY `FK_QUESTIONANSWERANSWER_question_answer_id` (`question_answer_id`),
  CONSTRAINT `FK_QUESTIONANSWERANSWER_answer_id` FOREIGN KEY (`answer_id`) REFERENCES `text` (`id`),
  CONSTRAINT `FK_QUESTIONANSWERANSWER_question_answer_id` FOREIGN KEY (`question_answer_id`) REFERENCES `questionanswer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionansweranswer`
--

LOCK TABLES `questionansweranswer` WRITE;
/*!40000 ALTER TABLE `questionansweranswer` DISABLE KEYS */;
INSERT INTO `questionansweranswer` VALUES ('4b676ea1-b4d2-4886-9546-56daaef63475',0,'ba723493-455e-431e-82a5-82fd4fcedbd4','6d7e945a-afb3-4046-b42e-d8ceeb7d98d5'),('9fe803c9-c63e-415e-97b9-09311e661e2a',0,'4ee7e06e-bd67-491e-ad15-b74803ac7d27','6d7e945a-afb3-4046-b42e-d8ceeb7d98d5'),('b881d514-4904-447f-afdb-431403552874',0,'fd6a338b-fbd4-4c41-ba1f-5999c68fc9bc','6d7e945a-afb3-4046-b42e-d8ceeb7d98d5');
/*!40000 ALTER TABLE `questionansweranswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionanswerquestion`
--

DROP TABLE IF EXISTS `questionanswerquestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionanswerquestion` (
  `id` varchar(36) NOT NULL,
  `AMOUNTUSED` int(11) DEFAULT NULL,
  `question_id` varchar(36) DEFAULT NULL,
  `question_answer_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_QUESTIONANSWERQUESTION_question_answer_id` (`question_answer_id`),
  KEY `FK_QUESTIONANSWERQUESTION_question_id` (`question_id`),
  CONSTRAINT `FK_QUESTIONANSWERQUESTION_question_answer_id` FOREIGN KEY (`question_answer_id`) REFERENCES `questionanswer` (`id`),
  CONSTRAINT `FK_QUESTIONANSWERQUESTION_question_id` FOREIGN KEY (`question_id`) REFERENCES `text` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionanswerquestion`
--

LOCK TABLES `questionanswerquestion` WRITE;
/*!40000 ALTER TABLE `questionanswerquestion` DISABLE KEYS */;
INSERT INTO `questionanswerquestion` VALUES ('97b57b62-fa09-4736-a2fd-99268f7eb9b6',0,'62991c82-1fa2-4c0f-a678-32f5f4cc3e44','6d7e945a-afb3-4046-b42e-d8ceeb7d98d5'),('f3d6b772-a11f-4f33-8904-c74c5dd9e8cd',0,'f4cade77-db22-45f1-8aa2-f7e18a80901e','6d7e945a-afb3-4046-b42e-d8ceeb7d98d5'),('f897ccee-e2da-4687-b705-9b7a9324e017',0,'1bdfb106-3010-4e46-82bd-4241aa8b21a6','6d7e945a-afb3-4046-b42e-d8ceeb7d98d5');
/*!40000 ALTER TABLE `questionanswerquestion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `id` varchar(36) NOT NULL,
  `FAVOURITE` tinyint(1) DEFAULT 0,
  `RATING` varchar(255) DEFAULT NULL,
  `chatbot_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_RATING_chatbot_id` (`chatbot_id`),
  KEY `FK_RATING_user_id` (`user_id`),
  CONSTRAINT `FK_RATING_chatbot_id` FOREIGN KEY (`chatbot_id`) REFERENCES `chatbot` (`id`),
  CONSTRAINT `FK_RATING_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES ('3d843559-0738-4961-9a24-bdffe250b710',1,'UPVOTE','35b8b2f5-8bf3-438f-b7eb-222820298b2f','5275df08-3b68-4bc1-923c-c5cfb9296492');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence`
--

DROP TABLE IF EXISTS `sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence`
--

LOCK TABLES `sequence` WRITE;
/*!40000 ALTER TABLE `sequence` DISABLE KEYS */;
INSERT INTO `sequence` VALUES ('uuid',0);
/*!40000 ALTER TABLE `sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text`
--

DROP TABLE IF EXISTS `text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `text` (
  `id` varchar(36) NOT NULL,
  `text` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text`
--

LOCK TABLES `text` WRITE;
/*!40000 ALTER TABLE `text` DISABLE KEYS */;
INSERT INTO `text` VALUES ('1bdfb106-3010-4e46-82bd-4241aa8b21a6','Question 3'),('4ee7e06e-bd67-491e-ad15-b74803ac7d27','Answer 1'),('62991c82-1fa2-4c0f-a678-32f5f4cc3e44','Question 1'),('8c99b9c9-86c4-4211-bf12-90e2a997ca23','Unknown Text 1'),('ba723493-455e-431e-82a5-82fd4fcedbd4','Answer 3'),('c4d7c037-8672-442c-95b8-ed8e2f5688e8','Unknown Text 2'),('f4cade77-db22-45f1-8aa2-f7e18a80901e','Question 2'),('fd6a338b-fbd4-4c41-ba1f-5999c68fc9bc','Answer 2');
/*!40000 ALTER TABLE `text` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `PASSWORD` varchar(64) NOT NULL,
  `USERROLE` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('5275df08-3b68-4bc1-923c-c5cfb9296492','64f52a3d3fabe6c658849fb3dbda3a8104e0bbb823ddee7940d8797d3d4203c1','USER','user'),('8d300e16-39f0-4a74-9bf3-e8c93e27e528','64f52a3d3fabe6c658849fb3dbda3a8104e0bbb823ddee7940d8797d3d4203c1','ADMIN','admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-12 17:36:27
