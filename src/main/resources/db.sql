CREATE TABLE `roles` (
  id int NOT NULL AUTO_INCREMENT,
  description varchar(25) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE `users` (
  id int NOT NULL AUTO_INCREMENT,
  age int NOT NULL,
  firstName varchar(30) NOT NULL,
  lastName varchar(35) NOT NULL,
  pass varchar(25) NOT NULL,
  email varchar(35) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE `rolesOfUsers` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `roleId` int NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





INSERT INTO `pp3-1-5`.`users` (`id`, `age`, `first_name`, `last_name`, `password`, `email_address`) VALUES ('1', '35', 'Luk', 'Sky', 'user', 'user@mail.ru');
INSERT INTO `pp3-1-5`.`users` (`id`, `age`, `first_name`, `last_name`, `password`, `email_address`) VALUES ('35', 'Obi', 'Van', 'user', 'user@mail.ru');

INSERT INTO `pp3-1-5`.`roles` (`id`, `name`) VALUES ('1', 'USER');
INSERT INTO `pp3-1-5`.`roles` (`name`) VALUES ('2', 'ADMIN');

INSERT INTO `pp3-1-5`.`rolesOfUsers` (`userId`, `role_id`) VALUES ('1', '1');
INSERT INTO `pp3-1-5`.`rolesOfUsers` (`user_id`, `role_id`) VALUES ('2');
