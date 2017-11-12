CREATE DATABASE IF NOT EXISTS `wallet_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE wallet_db;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user_authorities` (
  `user_id` int(11) NOT NULL,
  `authorities` int(11) DEFAULT NULL,
  KEY `FKhiiib540jf74gksgb87oofni` (`user_id`),
  CONSTRAINT `FKhiiib540jf74gksgb87oofni` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcryp6xe1g3wmw066ty3juhdpo` (`type`,`name`),
  KEY `FK7ffrpnxaflomhdh0qfk2jcndo` (`user_id`),
  CONSTRAINT `FK7ffrpnxaflomhdh0qfk2jcndo` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4a1uurs8rtj4xnah2j9uguec0` (`name`),
  KEY `FKi56jbsjqlnp0j0xjmi8d2qmeg` (`user_id`),
  CONSTRAINT `FKi56jbsjqlnp0j0xjmi8d2qmeg` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `entry_date` date DEFAULT NULL,
  `entry_type` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `source_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf7mt5po7olj7eiwqlt6t3qc7j` (`category_id`),
  KEY `FKlc84qpytoriece3lletxirhf` (`source_id`),
  KEY `FKftvnb1ll0a4l98cgcif439dvp` (`user_id`),
  CONSTRAINT `FKf7mt5po7olj7eiwqlt6t3qc7j` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKftvnb1ll0a4l98cgcif439dvp` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKlc84qpytoriece3lletxirhf` FOREIGN KEY (`source_id`) REFERENCES `source` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;
