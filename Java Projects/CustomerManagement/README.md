Customer Management System
This project is a basic Customer Management System built with the Spring Framework to demonstrate Inversion of Control (IoC) and Dependency Injection (DI).

Build Tool: Maven
Maven was chosen because:

It is widely used and supported in Java projects.
It simplifies project setup and dependency management.
It integrates well with IntelliJ IDEA.

Steps Taken

Initialize Project:

Created a new Spring Boot project using Spring Initializr with Maven.

Added dependencies: Spring Web, Spring Boot DevTools, Spring Data JPA, and H2 Database.
Design the System:

Created a Customer class with attributes id, name, email, and contactNumber.

Marked Customer as a Spring bean using @Component.
Implement IoC and DI:

Created a CustomerService interface for CRUD operations.

Implemented CustomerService in CustomerServiceImpl with a mock database (List<Customer>).
Used setter injection for the Customer bean and constructor injection for NotificationService.
Applied @Autowired for dependency injection.
Test the System:

Created a MainApp class to run the application.

Performed CRUD operations and simulated email notifications with print statements.
Document the Project:

Challenges

Learning how to properly use setter and constructor injection.
Resolving configuration issues for Spring beans.
Assumptions
Notifications are simulated with print statements.
The mock database uses a simple List<Customer>.


Conclusion
This project shows basic use of the Spring Framework, focusing on IoC and Dependency Injection.
