# Project Title

ING Sample Application

## Description

The project was designed to address the following challenges:
- Create a GitHub profile if you don't have one
- Use git in a verbose manner, push even if you wrote only one class
- Create a Java, maven based project, Springboot for the web part
- No front-end, you can focus on backend structure
- Implement some functions, for example: add-product, find-product, change-price or others
- Implement a basic authentication mechanism and role based endpoint access
- Design error mechanism and handling plus logging
- Write unit tests, at least for one class
- Use Java 17+ features
- Add a small Readme to document the project

The project was created with all these in mind, BE only (Java + SpringBoot). No FE code was added.
Basic functions for product handling were added: getProductByName(or get all if no string is sent), getProductById, addProduct and patchProduct.
A basic auth mechanism was added with protection from brute force attacks. After 5 failed login attempts, the user will be blocked for 300 seconds. Endpoints are role based, USER/ADMIN. For example, an ADMIN can use all the endpoints, the USER can use only endpoints that fetch data.
I have also added custom error handling and logging.
Unit tests were added for 2 service classes.
Record classes were used in order to comply with Java 17 features.
A postman collection was added for testing purposes.

## Getting Started

### Dependencies

Nothing worth mentioning other than those inside pom.xml. A .env file will be shared alongside the link to the repository.

### Installing

- Java 17, Docker, Postman
- Add the .env file to the project

### Executing program

- docker-compose up -d
- add users in the database manually (There are no endpoints for users. Add one user with role USER and another with role ADMIN. Use https://bcrypt-generator.com to add a password in the database for said users.)
- Use postman 

## Authors

Cosmin-Valentin Dima

## Version History

* 0.1
    * Initial Release

## Acknowledgments

Inspiration, code snippets, etc.
* [DomPizzie README template](https://gist.github.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc)
* [Baeldung Bruteforce](https://www.baeldung.com/spring-security-block-brute-force-authentication-attempts)
* [Baeldung Validation](https://www.baeldung.com/spring-boot-bean-validation)
* [Baeldung Various other topics](https://www.baeldung.com)
* [Stackoverflow various questions](https://stackoverflow.com)
* [Medium Bestpractices](https://medium.com/@anandjeyaseelan10/spring-boot-project-structure-explained-best-practices-c2ba46ea57eb)
* [Mvn repository](https://mvnrepository.com)
* [Bcrypt Hash Generator](https://bcrypt-generator.com)
