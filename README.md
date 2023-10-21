# Описание проекта
Проверка возможностей REST интерфейса. 
простые CRUD операции:

FIND ALL
curl -i http://localhost:8080/person/

BY ID
curl -i http://localhost:8080/person/1

CREATE
curl -H "Content-Type:application/json" -X POST -d "{\"login\":\"job4j@gmail.com\",\"password\":\"123\"}" http://localhost:8080/person/

UPDATE
curl -i -H "Content-Type: application/json" -X PUT -d "{\"id\":\"4\",\"login\":\"support@job4j.com\",\"password\":\"123\"}" http://localhost:8080/person/

DELETE
curl -i -X DELETE http://localhost:8080/person/4
# Стек технологий
- Java 17
- SpringBoot 2.7.6
- Hibernate
- PostgreSQL 14
- Checkstyle-plugin 3.1.2

# Требования к окружению
- Java 17
- Maven 3.8
- PostgreSQL 14
# Запуск проекта
1. В PostgreSQL создать базу данных fullstack_auth ```jdbc:postgresql://127.0.0.1:5432/fullstack_auth```
2. Собрать jar файл с помощью ```mvn install```
3. Запустить приложение с помощью собранного jar-файла ```java -jar target/job4j_persons-1.0.jar```
4. Перейти по адресу ```http://localhost:8080/index```
