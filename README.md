# Проект по "Технологиям разработки платформенно независимых приложений" в 4 семестре

Тема проекта: **library system**.

Тут много чего не хватает(всяких там проверочек ввода,
юнит тестов), да и вообще я кринжово сделал
но мне было впадлу вообще этим проектом
заниматься поэтому имеем что имеем.

## Установка

Для работы этого нереального проекта карандашом вам
понадобиться как минимум поставить MariaDB и Tomcat9.
Соответственно сам проект засунуть
в `tomcat9/webapps/library` где бы он у вас ни был.

Далее вам надо создать таблицы в бд. У меня они выглядят
так.


```
MariaDB [library]> show tables;
+-------------------+
| Tables_in_library |
+-------------------+
| BOOKS             |
| RENT_RECORDS      |
| ROLES             |
| USERS             |
+-------------------+


MariaDB [library]> desc BOOKS;
+-----------+--------------+------+-----+---------+----------------+
| Field     | Type         | Null | Key | Default | Extra          |
+-----------+--------------+------+-----+---------+----------------+
| Id        | int(11)      | NO   | PRI | NULL    | auto_increment |
| Name      | varchar(255) | YES  | UNI | NULL    |                |
| Author    | varchar(255) | YES  |     | NULL    |                |
| Genre     | varchar(255) | YES  |     | NULL    |                |
| Available | int(11)      | NO   |     | NULL    |                |
+-----------+--------------+------+-----+---------+----------------+


MariaDB [library]> desc RENT_RECORDS;
+-----------+---------+------+-----+---------+----------------+
| Field     | Type    | Null | Key | Default | Extra          |
+-----------+---------+------+-----+---------+----------------+
| Id        | int(11) | NO   | PRI | NULL    | auto_increment |
| Book_id   | int(11) | NO   | MUL | NULL    |                |
| User_id   | int(11) | NO   | MUL | NULL    |                |
| Rented_on | date    | NO   |     | NULL    |                |
| Due_to    | date    | NO   |     | NULL    |                |
+-----------+---------+------+-----+---------+----------------+


MariaDB [library]> desc ROLES;
+-------+--------------+------+-----+---------+----------------+
| Field | Type         | Null | Key | Default | Extra          |
+-------+--------------+------+-----+---------+----------------+
| id    | int(11)      | NO   | PRI | NULL    | auto_increment |
| name  | varchar(255) | NO   |     | NULL    |                |
+-------+--------------+------+-----+---------+----------------+


MariaDB [library]> desc USERS;
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| Id           | int(11)      | NO   | PRI | NULL    | auto_increment |
| Username     | varchar(255) | NO   | UNI | NULL    |                |
| Password     | varchar(255) | NO   |     | NULL    |                |
| Role_id      | int(11)      | NO   | MUL | NULL    |                |
| Name         | varchar(255) | NO   |     | NULL    |                |
| Rented_books | int(11)      | YES  |     | 0       |                |
+--------------+--------------+------+-----+---------+----------------+
```

В `src/session/ConnectionManager.java` поменяйте логины и пароли к
бд.

Скомпилируйте все классы которые находятся в
`WEB-INF/src`. Для этого, перейдите в эту директорию
и тупа пропишите `make` вроде должно заработать.

Если tomcat робит, то сайт должен быть доступен по адресу
`localhost:8080/library/login`


~ Всьо ~
