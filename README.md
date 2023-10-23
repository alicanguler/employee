Employee Service
The Employee Service application allows you to perform CRUD (Create, Update, Delete, and Retrieve) operations on employee records. It leverages PostgreSQL for database storage and RabbitMQ for event handling.

Getting Started
To run the application, follow these steps:

Start PostgreSQL and RabbitMQ using Docker Compose:

`docker-compose up -d`

Run the Employee Service application.

The application will initialize with two pre-existing employee records in the database.

Accessing Employee Records
To access employee records, you can make HTTP requests to the following endpoints:

Get All Employees: Retrieve all employees.

HTTP Method: GET
URL: http://localhost:8080/api/v1/employee
Authorization: No authorization required for this endpoint.

Create an Employee: Add a new employee record.
HTTP Method: POST
URL: http://localhost:8080/api/v1/employee
Headers:
Content-Type: application/json
Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
Example Request:

curl --location 'http://localhost:8080/api/v1/employee' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=' \
--data-raw '{
"firstName": "Alican",
"lastName": "Güler",
"email": "kou@example.com",
"birthday": "1996-01-15",
"hobbies": [
"Reading",
"Walking"
]
}'


Security
For security purposes, you can use the following credentials:

Username: username
Password: password


Feel free to adjust and expand this README as needed for your application's documentation.