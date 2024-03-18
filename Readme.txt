Project description:

    Techs used => Java, SpringBoot, MySQL, JPA/Hibernate, REST APIs, Spring Security

 ->This project features the Task management application.

 ->I've structured this Project code in MVC architectural layout, used MySQL DB for local database,

 ->connected DB using JDBC and handled using JPA/Hibernate ORM.

 ->Created model classes and its relevant repository interfaces to connect and work with the Relational-tables in DB.

 ->Whereas, controller layer acts as the endpoint and contains list of APIs, Service layer provides the business logics to those endpoints in controller layer.

 ->Utilized useful dependencies to improve project's functionality and performance like.., Lombok,Swagger..etc.

 ->Implemented functionalities like DTO(request & response classes for model classes) and Transformers (contains functions : DTO->model,model->DTO) and finally

 ->Implemented user-based authentication using Spring security.


 ***  Admin credentials ( userName = admin , password = admin )   ***


Instructions to run locally :

1.Install the required softwares.
    -> java JDK
    -> Maven
    -> Mysql server
    -> Intellij IDEA - community edition
    -> Postman (OPTIONAL - for testing APis)
    -> DBeaver (OPTIONAL - Database management app)

2. Clone this project to local folder and open it with Intellij

3. initiate connection to database
    ->create a mysql database with name as "taskmanagement" locally
    ->In Intellij,go-to src->resource->application.properties
    ->Replace the username and password with your mysql credentials

3. Go to pom.XML file and load Maven to update the dependencies(Incase)

4. Now run TaskManagementApplication.java file (the starting point of the project)

    -> To enable the admin account hit this URL( http://localhost:8080/user/enableAdmin ) in postman.
        =>  ***  Admin credentials ( userName = admin , password = admin )   ***

    -> hit this post API URL ( http://localhost:8080/user/signup?userName=user1&password=pass1 ) to sign your account in postman.
    -> Now, you can perform all the APIs easily using SWAGGER(a simple UI) dependency, which I've imported in pom.xml. access with this url - http://localhost:8080/swagger-ui/index.html# on your browser
    -> Initially it'll take you login page,enter the proper credentials,
    -> Now, you are good to go, access and can perform the APIs.

    ->to switch user accounts, hit http://localhost:8080/logout and again hit http://localhost:8080/swagger-ui/index.html# on your browser



*** Input format for APIs :

1. signup (POST)
    URl : http://localhost:8080/user/signup?userName=pranav&password=pranav123
    body : {}

1.1 Enable Admin account
    URL : http://localhost:8080/user/enableAdmin
    body : {}

2. Login
    URL : http://localhost:8080/login
    body : {
                "userName":"pranav",
                "password":"pranav123"
           }

3. logout
    URL : http://localhost:8080/logout
    body : {}

4. POST APIs

    4.1 add task
    URL : http://localhost:8080/task/addTask
    body : {
                "title":"string",
                "description":"string",
                "dueDate":"date",
                "status":"PENDING or IN_PROGRESS or COMPLETED"
            }

5. GET APIs

    5.2 get task
    URL : http://localhost:8080/task/getTask?taskId=1
    body : {}

    5.3 get taskList
    URL : http://localhost:8080/task/getallTasks
    body : {}

    5.4 order task list by ascending order with title
    URL : http://localhost:8080/task/getTasksByTitleAscendingOrder
    body : {}

    5.5 order task list by descending order with title
    URL : http://localhost:8080/task/getTasksByTitleDescendingOrder
    body : {}

    5.6 filter tasks by status
    URL : http://localhost:8080/task/getTasksByStatus?status=PENDING
    body : {}

6. PUT APIs

    6.1 update task title
    URL : http://localhost:8080/task/updateTitle?taskId=1
    body : {"newTitle":"new_title"}

    6.2 update status
    URL : http://localhost:8080/task/updateStatus?taskId=1
    body : {"newStatus":"COMPLETED"}

    6.3 update description
    URL : http://localhost:8080/task/updateDescription?taskId=1
    body : {"description":"new description"}

    6.4 update due-date
    URL : http://localhost:8080/task/updateDueDate?taskId=1
    body : {"newDate":"01/01/24"}

7. DELETE APIs

    7.1 delete a task
    URL : http://localhost:8080/task/deleteTask?taskId=1
    body : {}