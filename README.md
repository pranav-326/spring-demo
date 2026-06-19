# Employee Management Demo
### This is a RESTful API built using Spring boot and JPA/Hibernate
It handles Software Engineer entities using Spring Boot with postgres as a database. My first learning towards backend with Java using Spring-Boot and Hibernate/JPA


Ping at *localhost:8080/software-engineers*

### Endpoints

`GET`
```
/get-engineers 
```
For getting all the stored engineers in the database

---
`POST`
```
/post-engineer
```
Creating a new Software Engineer by adding details as json in the request body

---
`GET`
```
/get-by-id/{id}
```
For getting the Engineer by his ID 

---
`DELETE`
```
/{id}
```
Delete the engineer with the that specific ID