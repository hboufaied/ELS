# Start BE application
mvn clean spring-boot:run

Acces to url http://localhost:8080/swagger-ui.html to show the definition of API

Call the endpoint http://localhost:8080/api/v1/employee/upload
curl -X POST --header 'Content-Type: multipart/form-data' -d FILE_PATH 'http://localhost:8080/api/v1/employee/upload'

# Start Angular application
Step to create and start the application :

npm install -g @angular/cli
ng new els-employee-front
cd els-employee-front
npm install ng2-file-upload --save
ng serve
