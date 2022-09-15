SELECT CONCAT(
	'DROP TABLE', GROUP_CONCAT(
		CONCAT('`',table_name,'`')
	),';'
) AS statement FROM information_schema.tables 
WHERE table_schema = 'sampledb' AND table_name LIKE '%';


CREATE TABLE IF NOT EXISTS employee( 
id VARCHAR(50) PRIMARY KEY, 
name VARCHAR(50), 
age INT
);

/* ユーザー マスタ */ 
CREATE TABLE IF NOT EXISTS m_user (
	 id int NOT NULL AUTO_INCREMENT PRIMARY KEY
	,user_mail VARCHAR(50) 
	,password VARCHAR(100)
	,user_name VARCHAR(50)
	,birthday DATE
	,age INT
	,gender INT
	,department_id INT
	,role VARCHAR(50)
);

/* 部署 マスタ */ 
CREATE TABLE IF NOT EXISTS m_department (
	department_id INT PRIMARY KEY
	,department_name VARCHAR( 50)
);

/* 給料 テーブル */
CREATE TABLE IF NOT EXISTS t_salary (
	user_id INT
	,y_month VARCHAR(50)
	,salary INT
	,PRIMARY KEY(user_id, y_month)
);

/*　時間テーブル */
CREATE TABLE IF NOT EXISTS user_time (
	 user_id INT
	,start_time DATETIME
	,finish_time DATETIME 
	,work_flag INT
	,message VARCHAR(50)
);