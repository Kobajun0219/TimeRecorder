TRUNCATE TABLE employee;
TRUNCATE TABLE m_user;
TRUNCATE TABLE m_department;
--
INSERT INTO employee (id, name, age) 
VALUES('1', 'Tom', 30);

/* ユーザー マスタ */
INSERT INTO m_user (
	user_id 
	,password
	,user_name
	,birthday
	,age
	,gender
	,department_id
	,role
) VALUES 
('system@co.jp','$2a$10$bje/CdR59uGvqdK9q1BPLOfy/NfvbEk6xfZVUy7PQBOz65/kH9/du','システム管理者','2000-01-01',21,1,1,'ROLE_ADMIN')
,('user@co.jp','$2a$10$bje/CdR59uGvqdK9q1BPLOfy/NfvbEk6xfZVUy7PQBOz65/kH9/du','ユーザー 1','2000-01-01',21,2,2,'ROLE_GENERAL');

/* 部署 マスタ */
INSERT INTO m_department(
	department_id
	,department_name
) VALUES
(1,'システム 管理部')
,(2,'営業部')
--;