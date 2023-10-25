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

Регистрация нового пользователя с использованием jwt токена
curl -H "Content-Type: application/json" -X POST -d {"""login""":"""admin""","""password""":"""password"""} "http://localhost:8080/person/"

Авторизация ранее созданного пользователя и получение jwt токена http ответом
curl -i -H "Content-Type: application/json" -X POST -d {"""login""":"""admin""","""password""":"""password"""} "http://localhost:8080/login"

Использование jwt токена при для обращения к сервису
curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5OTAzNzkyMX0.-j9J-a9Z7L1r8hCznWIf_-Rogj6HpbxLp3gfeNAZIBJo09gXnIJLD2Y5v9Yj12f8I_CShMaKawjpf1FHorGrjA" http://localhost:8080/person/

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
