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
  `id` varchar(255) NOT NULL,
  `CHATBOTNAME` varchar(255) NOT NULL,
  `CREATEDAT` date DEFAULT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CHATBOT_owner_id` (`owner_id`),
  CONSTRAINT `FK_CHATBOT_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatbot`
--

LOCK TABLES `chatbot` WRITE;
/*!40000 ALTER TABLE `chatbot` DISABLE KEYS */;
INSERT INTO `chatbot` VALUES ('809dca09-d7e8-4afa-a0e0-456c8db50997','MRVN','2022-05-20','4fbf34d6-352d-4d8e-b357-af2155da8616');
/*!40000 ALTER TABLE `chatbot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatbot_unknowntexts`
--

DROP TABLE IF EXISTS `chatbot_unknowntexts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatbot_unknowntexts` (
  `chatbot_id` varchar(255) NOT NULL,
  `unknownTexts_id` varchar(255) NOT NULL,
  PRIMARY KEY (`chatbot_id`,`unknownTexts_id`),
  KEY `FK_Chatbot_unknownTexts_unknownTexts_id` (`unknownTexts_id`),
  CONSTRAINT `FK_Chatbot_unknownTexts_chatbot_id` FOREIGN KEY (`chatbot_id`) REFERENCES `chatbot` (`id`),
  CONSTRAINT `FK_Chatbot_unknownTexts_unknownTexts_id` FOREIGN KEY (`unknownTexts_id`) REFERENCES `text` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatbot_unknowntexts`
--

LOCK TABLES `chatbot_unknowntexts` WRITE;
/*!40000 ALTER TABLE `chatbot_unknowntexts` DISABLE KEYS */;
INSERT INTO `chatbot_unknowntexts` VALUES ('809dca09-d7e8-4afa-a0e0-456c8db50997','16a07c29-d5ce-48dd-9d13-0b6bf15f62b2'),('809dca09-d7e8-4afa-a0e0-456c8db50997','b8e9f175-120d-4258-8a0f-2731c62ab479');
/*!40000 ALTER TABLE `chatbot_unknowntexts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionanswer`
--

DROP TABLE IF EXISTS `questionanswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionanswer` (
  `id` varchar(255) NOT NULL,
  `chatbot_id` varchar(255) DEFAULT NULL,
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
INSERT INTO `questionanswer` VALUES ('4c09a663-01db-4fa1-8559-3e33eaeb215d','809dca09-d7e8-4afa-a0e0-456c8db50997');
/*!40000 ALTER TABLE `questionanswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionanswer_answers`
--

DROP TABLE IF EXISTS `questionanswer_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionanswer_answers` (
  `questionAnswer_id` varchar(255) NOT NULL,
  `answers_id` varchar(255) NOT NULL,
  PRIMARY KEY (`questionAnswer_id`,`answers_id`),
  KEY `FK_QuestionAnswer_answers_answers_id` (`answers_id`),
  CONSTRAINT `FK_QuestionAnswer_answers_answers_id` FOREIGN KEY (`answers_id`) REFERENCES `text` (`id`),
  CONSTRAINT `FK_QuestionAnswer_answers_questionAnswer_id` FOREIGN KEY (`questionAnswer_id`) REFERENCES `questionanswer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionanswer_answers`
--

LOCK TABLES `questionanswer_answers` WRITE;
/*!40000 ALTER TABLE `questionanswer_answers` DISABLE KEYS */;
INSERT INTO `questionanswer_answers` VALUES ('4c09a663-01db-4fa1-8559-3e33eaeb215d','0ee29235-d6f4-4c4a-937c-3c09f321ac43'),('4c09a663-01db-4fa1-8559-3e33eaeb215d','3d1afe5c-264c-43bf-b55e-9b8f52193d89'),('4c09a663-01db-4fa1-8559-3e33eaeb215d','dbc80255-14e5-4776-851d-1a0309e4d945');
/*!40000 ALTER TABLE `questionanswer_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionanswer_questions`
--

DROP TABLE IF EXISTS `questionanswer_questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionanswer_questions` (
  `questionAnswer_id` varchar(255) NOT NULL,
  `questions_id` varchar(255) NOT NULL,
  PRIMARY KEY (`questionAnswer_id`,`questions_id`),
  KEY `FK_QuestionAnswer_questions_questions_id` (`questions_id`),
  CONSTRAINT `FK_QuestionAnswer_questions_questionAnswer_id` FOREIGN KEY (`questionAnswer_id`) REFERENCES `questionanswer` (`id`),
  CONSTRAINT `FK_QuestionAnswer_questions_questions_id` FOREIGN KEY (`questions_id`) REFERENCES `text` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionanswer_questions`
--

LOCK TABLES `questionanswer_questions` WRITE;
/*!40000 ALTER TABLE `questionanswer_questions` DISABLE KEYS */;
INSERT INTO `questionanswer_questions` VALUES ('4c09a663-01db-4fa1-8559-3e33eaeb215d','1b09115d-88fb-409e-af1a-317add8b3ceb'),('4c09a663-01db-4fa1-8559-3e33eaeb215d','22638698-3a77-41ce-a752-9fab6284ad09'),('4c09a663-01db-4fa1-8559-3e33eaeb215d','d633cd2b-01e2-4654-b7b5-fe1ae3849680');
/*!40000 ALTER TABLE `questionanswer_questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `id` varchar(255) NOT NULL,
  `FAVOURITE` tinyint(1) DEFAULT 0,
  `RATING` varchar(255) DEFAULT NULL,
  `chatbot_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
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
INSERT INTO `rating` VALUES ('88a3a2c4-a2a9-4463-a3d2-da78317e2e05',0,'UPVOTE','809dca09-d7e8-4afa-a0e0-456c8db50997','4fbf34d6-352d-4d8e-b357-af2155da8616');
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
  `id` varchar(255) NOT NULL,
  `AMOUNTUSED` int(11) DEFAULT NULL,
  `TEXT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text`
--

LOCK TABLES `text` WRITE;
/*!40000 ALTER TABLE `text` DISABLE KEYS */;
INSERT INTO `text` VALUES ('0ee29235-d6f4-4c4a-937c-3c09f321ac43',0,'Answer 1'),('16a07c29-d5ce-48dd-9d13-0b6bf15f62b2',0,'Unknown Text 1'),('1b09115d-88fb-409e-af1a-317add8b3ceb',0,'Question 3'),('22638698-3a77-41ce-a752-9fab6284ad09',0,'Question 1'),('3d1afe5c-264c-43bf-b55e-9b8f52193d89',0,'Answer 2'),('b8e9f175-120d-4258-8a0f-2731c62ab479',0,'Unknown Text 2'),('d633cd2b-01e2-4654-b7b5-fe1ae3849680',0,'Question 2'),('dbc80255-14e5-4776-851d-1a0309e4d945',0,'Answer 3');
/*!40000 ALTER TABLE `text` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `USERROLE` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('4fbf34d6-352d-4d8e-b357-af2155da8616','admin','ADMIN','admin'),('b4a168ab-0edc-4206-9fe3-4a88d33dde8d','user','USER','user');
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

-- Dump completed on 2022-05-20 16:31:02
