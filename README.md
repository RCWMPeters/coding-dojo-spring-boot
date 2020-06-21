René Peters's Spring Boot "Weather" application
---

Welcome to René Peters's Spring Boot "Weather" application!

### Introduction

This is a simple application that requests its data from [OpenWeather](https://openweathermap.org/) and stores the 
result in a database. The result is stored as a JSON string in the database with some of the values from the json string
also stored as seperate properties in the entity. Those are the properties for which queries can and have been defined.
The application can be run in a docker container.

### Building and starting the application
It is assumed here that Maven and Docker have been installed on the machine where you are trying to run this application.
To build and start the application the following must be done. Go to the directory where the project has been saved
and execute the following command from the command prompt:
>**mvn clean install** 

This will build the jar file. Now execute the following command from the command prompt:
>**docker-compose up -d --build**

This will build the docker image for the Spring Boot "Weather" application, download the image for the Postgres database
and create and start a container for each. All in detached mode. If the Spring Boot "Weather" application image 
has already been build, the "--build" command line parameter can be omitted.

To stop the application aka the containers execute the following command:
>**docker-compose stop**

To start the application after containers have already been created, execute the following command:
>**docker-compose up -d --no-recreate**

**There is more to docker then can be described here. Please checkout the docker documentation for this.**

### The functional features

Retrieve weather report for a particular city and store result in a database table.
>**curl localhost/weather/report/arnhem** 

Retrieve the total number of entries in the database table.
>**curl localhost/weather/count** 

Retrieve the number of entries in the database table for a particular 'city'.
>**curl localhost/weather/countbycity/nijmegen** 

Retrieve the number of entries in the database table for a particular 'name'
>**curl localhost/weather/countbyname/Gemeente%20Arnhem** 

### Logging

The application logs to a file named 'openweathermap.log' in the root directory of the project (and creates backups when
it is 'full'). 

### The database and the database schema

- It is assumed that the target database is a Postgres database. That assumption is based on the fact that a Postgres
reference was found in the POM.XML file.
- The database schema is very simplistic at the moment. The 'WeatherResponse' is stored as a JSON string in the table. 
Some of the attributes of the 'WeatherResponse' have been given dedicated attributes in the table which means that their 
value is not only available from the JSON string attribute but also directly. The attributes from the JSON string chosen
for this are some attributes for which queries have been defined. If it turns out we need more attributes with information
from the JSON string as individual attributes, this is still possible. Simply add the attributes to the table (and java 
code obviously) and run some script or code which takes the value from the JSON string and set it in/on the newly created 
attribute in the existing record (basically updating the record with information already contained in the record). So no
information is lost!
- For the Unit tests an in memory database is used.

### Production grade?
The short answer: no it is not. First of all because it doesn't do much useful stuff. So bringing this to production 
would be embarrassing ;-)

But there are some other more important considerations.

- This implementation assumes a docker environment, but perhaps that is not desired. However, for the intentions of this
exercise it is useful as the application can be easily tested by a third party and check that it actually works (on a Postgres 
database). Provided Maven and Docker are installed. No need to install Postgres.
- The database is re-created every time the application is started (docker-compose up -d). That is useful in testing, but
a disaster in production.
- Even if the database is created only the first time (the application properties need to be changed for that), when the
Docker container is removed, the database will also be removed. To overcome that a volume would need to be bind mounted 
to the Docker container. Then the data will not be removed even if the docker container is.
- An create script is provided, but it is untested.
- When the value of one or more constants need to be changed (the irony) this would require a rebuild of the application.
For that reason we would like those configurable (URL, API key).

### How to obtain the code

The code can be found at: 

Since you are reading this I assume you found it.

### The API key
The API key used in this application is: 5348ca659222b8f32b8f0fc41e00d498

If you want to have one of your own then go to the [OpenWeather Sign up](https://openweathermap.org/appid) page.

### Footnote
If you have any question regarding this application please contact me at +31 6 11 600 811 or rene@burodevelopment.nl
