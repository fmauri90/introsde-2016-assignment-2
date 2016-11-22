# Assignment 02: RESTful Services

## [Introduction to Service Design and Engineering] | [University of Trento](http://www.unitn.it/)

This repository is the solution to the [second assignment](https://sites.google.com/a/unitn.it/introsde_2016-17/lab-sessions/assignments/assignment-2) of the course IntroSDE of the University of Trento. This assignment cover the following topics:

* [LAB05]: The REST architectural style & RESTful web services
* [LAB06]: CRUD RESTful Services
* [LAB07]: Reading and writing from Databases & JPA (Java Persistence API)

 
URL of my server: [https://maurizio-franchi-assignment2.herokuapp.com/sdelab](https://maurizio-franchi-assignment2.herokuapp.com/sdelab)  

### Code

(The class MeasureDefaultRange is not used in this assignment)

*[src/](src/)*: contains source code;  
*[src/ehealth](src/ehealth)*: contains all the code regarding the server  ;  
*[src/ehealth/dao](src/ehealth/dao)*: contains the data access object;  
*[src/ehealth/model](src/ehealth/model)*: contains the definition of *Person*, *LifeStatus*, *HealthMeasureHistory*, *MeasureDefinition* ;  
*[src/ehealth/resources](src/ehealth/resources)*: contains the resource classes;  
*[src/ehealth/wrapper](src/ehealth/wrapper)*: contains the wrapper used to format XML and JSON;  
*[src/client](src/client)*: contains the client;  
*[src/ehealth/App.java](src/ehealth/App.java)*: stand alone server;    
*[client-MyServer-json.log](client-MyServer-json.log)*: log file of the client calling [my server](https://maurizio-franchi-assignment2.herokuapp.com/sdelab) using XML format;  
*[client-MyServer-json.log](client-MyServer-json.log)*: log file of the client calling [my server](https://maurizio-franchi-assignment2.herokuapp.com/sdelab) using JSON format;  

### Installation

In order to execute this project you need the following technologies (in the brackets you see the version used to develop):

* Java (jdk1.8.0)
* ANT (version 1.9.4)

Then, clone the repository. Run in your terminal:

```
https://github.com/fmauri90/introsde-2016-assignment-2
```

and run the following command:
```
ant execute.client
```

### Usage

This project use an [ant build script](build.xml) to automate the compilation and the execution of specific part of the Java application.
```
ant execute.client
```
This command performs the following action:

* download and install ivy (dependency manager) and resolve the dependencies. *Ivy* and *WebContent/WEB-INF/lib/* folders are generated;
* create a build directory and compile the code in the src folder. You can find the compiled code in *build* folder;



You can send queries to my server:
```
execute.client.myServer
```

This command calls the following target:

 * `execute.client.myServer.xml` send REST queries to my server with the body in XML format and accept response in XML. The output is saved into [client-MyServer-xml.log](client-MyServer-xml.log);
 * `execute.client.myServer.json` send REST queries to my partner server with the body in JSON format and accept response in JSON. The output is saved into [client-MyServer-json.log](client-MyServer-json.log).

You can execute specific task. Before, you have to execute
```
ant install
```
and then one of the following command:

* `execute.client.partnerServer.xml`
* `execute.client.partnerServer.json`
* `execute.client.myServer.xml`
* `execute.client.myServer.json`
* `ant clean` this command deletes the folders created during the compile phase and the file created during the execution of the various targets. 

If you want to run the server locally then run:
```
ant install
ant start
```
In order to run the client querying your local server modify the variable *uriServer* in the [src/client/TestClient.java](src/client/TestClient.java).

### REST APIs

Request #1: [GET /person](#get-person)  
Request #2: [GET /person/{id}](#get-personid)   
Request #3: [PUT /person/{id}](#put-personid)   
Request #4: [POST /person](#post-person)    
Request #5: [DELETE /person/{id}](#delete-personid)  
Request #6: [GET /person/{id}/{measureType}](#get-personidmeasuretype)  
Request #7: [GET /person/{id}/{measureType}/{mid}](#get-personidmeasuretypemid)  
Request #8: [POST /person/{id}/{measureType}](#post-personidmeasuretype)  
Request #9: [GET /measureTypes](#get-measuretypes)  
Request #10: [PUT /person/{id}/{measureType}/{mid}](#put-personidmeasuretypemid)  
Request #11: [GET /person/{id}/{measureType}?before={beforeDate}&after={afterDate}](#get-personidmeasuretypebeforebeforedateafterafterdate)  
Request #12: [GET /person?measureType={measureType}&max={max}&min={min}](#get-personmeasuretypemeasuretypemaxmaxminmin)  
