# A Java (JSF + Spring + JPA ) Web Application for a CRUD operation

This is a web application for a CRUD of School in a java. The application uses the spring framework integrated with JSF, JPA, and Hibernate. The mysql is used as the database system.

Before use this project, set the username and password of the database in the __applicationContext.xml__ file. The file is in src/main/webapp/WEB-INF/ folder.

This project uses Maven to manage the dependencies, build the source code and package the application.

Thanks!

steps :
1- open mySQL WorkPench to configure our Database
2- run command  CREATE DATABASE schoolDemoDb;
3-  let's create table

		CREATE TABLE `school` (
		`id` INTEGER NOT NULL  AUTO_INCREMENT,
		`name` VARCHAR(500),
		PRIMARY KEY(`id`)
		)
		ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8

4- let's add some records as samples 

		 -- we can run the following scripts as a test data 
			INSERT INTO school (id, name) VALUES (1, "Oxford.School");
			INSERT INTO school (id, name) VALUES (2, "Kamal School");
			INSERT INTO school (id, name) VALUES (3, "Goenka International School");
			INSERT INTO school (id, name) VALUES (4, "DAV School");
			INSERT INTO school (id, name) VALUES (5, "Ryan School");
			INSERT INTO school (id, name) VALUES (6, "Delhi School");
			INSERT INTO school (id, name) VALUES (7, "Khaitan School");
	
5- Extract project Jar File to any Destination.
6- open eclipse and click import >  maven Projects > browse for destination that exctrac=ted from step one 
7- mark pom file and click finish 
8- open applicationContext.xml to configure our dataSource 
9- in url property type databaseName after port number like "3306/schoolDemoDb?useSSL=false" 
10- setting ssl to false in order to skip security authintication 
11- set userName and password of your mySql Database.
12- add tomcat server it's a light weight server.
13- right Click run as and maven clean 
14- right Click run as and maven install 
15- right Click run as and maven build  and in goals type   jetty:run   command.
16- after jetty start go to url :  http://localhost:8080/demoSchoolMain.xhtml 
17- now you can access application and source code easily.

...thanks and best regardless ..
ahmed Dowidar

