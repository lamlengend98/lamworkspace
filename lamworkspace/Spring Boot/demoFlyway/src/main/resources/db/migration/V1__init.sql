CREATE TABLE users (
  id int(20) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  name varchar(100) NOT NULL,
  email varchar(100) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_username (username)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;