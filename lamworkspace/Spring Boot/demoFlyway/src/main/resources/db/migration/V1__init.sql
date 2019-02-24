CREATE TABLE mail
(
  id          int(20)      NOT NULL AUTO_INCREMENT,
  title       varchar(100) NOT NULL,
  emailSender varchar(100) NOT NULL,
  content     text DEFAULT NULL,
  PRIMARY KEY (id)
)