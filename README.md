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

## Place DB
```sql
CREATE TABLE `place` (
	`place_id`	INT	NOT NULL	PRIMARY KEYplace AUTO_INCREMENT	COMMENT '장소 ID',
	`title`	VARCHAR(50)	NOT NULL	COMMENT '장소 명',
	`detail`	TEXT	NULL	COMMENT '장소 설명',
	`image`	VARCHAR(255)	NULL	COMMENT '장소 이미지',
	`address`	VARCHAR(255)	NOT NULL	COMMENT '장소 주소',
	`location`	POINT	NOT NULL	COMMENT '장소 좌표',
	`area_id`	INT	NOT NULL	COMMENT '지역 ID',
	`category`	ENUM('MUSEUM', 'HISTORIC_SITE', 'FOLK_VILLAGE')	NOT NULL,
	FOREIGN KEY (area_id) REFERENCES AREA(area_id)
) ENGINE = INNODB DEFAULT CHARACTER SET=UTF8;
```