# Service Department Visits

This is a demo Vaadin application created with Spring Boot. It is a queue management system allowing customers to reserve time in queue and specialists to accept/complete/cancel visits. Application is currently hosted on AWS.

## Functionality
2 roles are supported:
- Customer (unauthenticated user)
- Specialist (authenticated user)

### Customer
Unauthenticated users can interact with "Book Time" screen where they can choose a specialist and book a visit in queue. Once booked, they get a reservation code and approximate waiting time which is currently calculated based on formula

`Wait Time = Average visit time x Earlier visits`, where
 - Earlier visits is the number of active/waiting visits before this visit
 - Average time is based on the following conditions:
   - If a specialist has some completed visits, average time is calculated for that specialist
   - Otherwise, average time is calculated for all specialists
   - If there are no completed visits by any specialist, system defaults to 7 minutes.
Additionally, customers can cancel the visit.

### Specialist
Specialists must authenticate at /login endpoint. Specialist accounts must be created by manual data insertion into database. For simplicity purposes, system is already pre-populated with the following specialists and their credentials:
- gabrielle.patel@nfq.com // test
- brian.robinson@nfq.com // test
- eduardo.haugen@nfq.com // test

Once authenticated, Service Department link appears in the navigation menu. Service department screen allows the specialist to:
- View up to 6 visits in queue (1 active + 5 waiting)
- Accept or Cancel next customer in line. Only 1 visit can be "active" (accepted).
- Complete or Cancel current visit. Only active (accepted) visit can be completed.

Either specialist action immediately updates the screen.


## Running the Application
The project is a standard Maven project, so you can import it to your IDE of choice. The project was created from https://start.vaadin.com.

There are two ways to run the application. Before starting it, make sure to have mysql server running and update connection properties in `src/main/resources/application.properties` file.
 - To run from the command line, use `mvn` and open [http://localhost:8080](http://localhost:8080) in your browser.
 - Another way is to to run the `Application` class directly from your IDE.
 
