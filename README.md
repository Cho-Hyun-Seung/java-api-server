# DB Script

## Area DB
```
CREATE DATABASE testdb;

SHOW DATABASES;

GRANT ALL PRIVILEGES ON testdb.* TO 'swcamp'@'%';


USE testdb;

CREATE TABLE AREA(
	area_id INT,
	area_code INT NOT NULL,
	area_name VARCHAR(50) NOT NULL,
	parent_area_id INT,
	PRIMARY KEY(area_id),
	FOREIGN KEY (parent_area_id) REFERENCES AREA(area_id)
) ENGINE = INNODB DEFAULT CHARACTER SET=UTF8;
```