CREATE USER 'usrstore'@'localhost' IDENTIFIED BY 'pswstore';
CREATE DATABASE dbstore
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
GRANT SELECT, INSERT, UPDATE, DELETE ON dbstore.* TO 'usrstore'@'localhost';
USE dbstore;
CREATE TABLE `products` (
`id` INT(4) NOT NULL AUTO_INCREMENT,
`code` VARCHAR(15) NOT NULL UNIQUE,
`description` VARCHAR(128) DEFAULT NULL,
`price` DOUBLE DEFAULT 0.0,
`stock` INT DEFAULT 0,
PRIMARY KEY (`id`)
);
INSERT INTO `products` (`id`, `code`, `description`, `price`, `stock`) 
VALUES
(1, 'code01', 'desc01', 1001, 101),
(2, 'code02', 'desc02', 1002, 102),
(3, 'code03', 'desc03', 1003, 103),
(4, 'code04', 'desc04', 1004, 104),
(5, 'code05', 'desc05', 1005, 105),
(6, 'code06', 'desc06', 1006, 106),
(7, 'code07', 'desc07', 1007, 107),
(8, 'code08', 'desc08', 1008, 108),
(9, 'code09', 'desc09', 1009, 109),
(10, 'code10', 'desc10', 1010, 110),
(11, 'code11', 'desc11', 1011, 111),
(12, 'code12', 'desc12', 1012, 112),
(13, 'code13', 'desc13', 1013, 113),
(14, 'code14', 'desc14', 1014, 114),
(15, 'code15', 'desc15', 1015, 115);
