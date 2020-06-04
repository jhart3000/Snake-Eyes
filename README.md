This is a spring boot app using Java 11, maven and mongoDB. It requires the lombok plugin to be enabled with your chosen IDE. It also supports Docker and Sonar Qube.

After cloning the repository from GitHub the project must be built using mvn clean install to generate the snakeeyes-0.0.1-SNAPSHOT.jar in the target folder.

Then the app can be run using the docker-compose.yml file if Docker is up and running on your device. 
Run the "docker-compose up" command in the directory that contains the docker-compose.yml file.
This will build the snake eyes image and run it as a container. It will also pull the mongoDB image and openjdk11 image and run the mongo image as a container.

An alternate way to run the app will be to run the SnakeEyesApplication.java file and run MongoDB separately on port 27017.

When the snake eyes server is running it will be exposed on port 8080. the swagger can be viewed using the following link:

http://localhost:8080/snakeeyes/swagger-ui.html#/

The snakeEyesPostmanCollection.json can be imported into postman which includes the three apis already setup and ready to go using the port 8080.

This service includes the following apis:

1) Play snake eyes:

   http://localhost:8080/snakeeyes/play?stake=10.0
   
   This api is a GET request and takes in the chosen stake from the user, which must be either 1.0, 2.0 or 10.0.
   It then retrieves the latest balance form MongoDB or if nothing exists in MongoDB will start you with a balance of 1000.
   It then calls a random number api which simulates rolling 2 dice. 
   It will then generate a winnings of x30 stake if two 1s are rolled or x7 if any other double is rolled. 
   These winnings are then updated in the users balance using mongoDB.
   
2) Get Current Balance: 

   http://localhost:8080/snakeeyes/getBalance
   
   This api is a GET request and will retrieve the latest balance fom mongoDB
   
3) Add To Balance: 

   http://localhost:8080/snakeeyes/addToBalance?amountToAdd=5000
   
   This api is a PUT request and takes in a query param of the amount which the user wishes to add to their balance. 
   It updates the balance document accordingly in MongoDB.
