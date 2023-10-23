Employee Service
The Employee Service application allows you to perform CRUD (Create, Read, Update, and Delete) operations on employee records. It leverages PostgreSQL for database storage and RabbitMQ for event handling.

Getting Started
To run the application, follow these steps:

Start PostgreSQL and RabbitMQ using Docker Compose:

`docker-compose up -d`

Run the Employee Service application.

The application will initialize with two pre-existing employee records in the database.

Accessing Employee Records
Get All Employees
Retrieve a list of all employees.

HTTP Method: GET
URL: http://localhost:8080/api/v1/employee
Authorization: No authorization required for this endpoint.



Get Employee by ID
Retrieve an employee record by specifying the employee's unique ID

HTTP Method: GET
URL: http://localhost:8080/api/v1/employee/{employeeId}
URL Example: http://localhost:8080/api/v1/employee/e4d0837a-61b9-4eef-9e60-1e44a7f1ecce
Authorization: No authorization required for this endpoint.



Create Employee
Add a new employee record. The request body must be in JSON format.

HTTP Method: POST

URL: http://localhost:8080/api/v1/employee

Headers:

Content-Type: application/json
Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
Example Request:


`curl --location 'http://localhost:8080/api/v1/employee' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=' \
--data-raw '{
"firstName": "Alican",
"lastName": "Güler",
"email": "kou@example.com",
"birthday": "1996-01-15",
"hobbies": ["Reading", "Walking"]
}'`


Update Employee
Update an existing employee's information.
HTTP Method: PATCH

URL: http://localhost:8080/api/v1/employee/{employeeId}

URL Example: http://localhost:8080/api/v1/employee/123e4567-e89b-12d3-a456-426655440000

Headers:

Content-Type: application/json
Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
Example Request:


`curl --location 'http://localhost:8080/api/v1/employee/123e4567-e89b-12d3-a456-426655440000' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=' \
--data-raw '{
"firstName": "UpdatedName",
"email": "newemail@example.com"
}'`


Delete Employee by ID
Delete an employee record by specifying the employee's unique ID.

HTTP Method: DELETE
URL: http://localhost:8080/api/v1/employee/{employeeId}
URL Example: http://localhost:8080/api/v1/employee/123e4567-e89b-12d3-a456-426655440000
Authorization: No authorization required for this endpoint.
Security
For security purposes, you can use the following credentials:


Username: username

Password: password

Feel free to adjust and expand this README as needed for your application's documentation.