This is a spring boot app using Java 11, maven and mongoDB. It requires the lombok plugin to be enabled with your chosen IDE. It also supports Docker.

This app can be run using the docker-compose.yml file. Run the "docker-compose up" command in the directory that contains the docker-compose.yml file.
This will build the snake eyes image and run it as a container. It will also pull the mongoDB image and openjdk11 image and run the mongo image as a container.

An alternate way to run the app will be to run the SnakeEyesApplication.java file and run MongoDB seperately on port 27017.

When the snake eyes server is running it will be exposed on port 8080. the swagger can be viewed unsing the following link:

http://localhost:8080/snakeeyes/swagger-ui.html#/

The snakeEyesPostmanCollection.json can be imported into postman which includes the three apis already setup and ready to go using the port 8080.

This service includes the following apis:

1) Play snake eyes: This service takes in the chosen stake from the user, which must be either 1.0, 3.0 or 10.0.
   It then retrieves the latest balance form MongoDB or if nothing exists in MongoDB will start you with a balance of 1000.
   It then calls a random number api which simulates roling 2 dice. 
   It will then generate a winnings of x30 stake if two 1s are rolled or x7 if any other double is rolled. 
   These winnings are then updated in the users balance using mongoDB.
   
2) Get Current Balance: This api will retrieve the latest balance fom mongoDB

3) Add To Balance: This api takes in a query param of the amount which the user wishes to add to their balance. It updates the balance document accordingly in MongoDB.
