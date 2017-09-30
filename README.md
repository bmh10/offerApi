# Offer API

### General Info

* App uses embedded Jetty for web server
* Jersey + JAX-RS libraries are used to provide RESTful API
* Tests are written using JUnit and Mockito

### Starting the server

* To compile and run tests from commandline:
      mvn clean install

* To run the server from commandline:
      mvn exec:java -Dexec.mainClass=org.offer.App

* If on Linux run.sh can be executed to perform both of the above

* Once the server is running, the offer API can be interacted with - see offerApiExamples.sh for example usage

### Assumptions

 - Currently no security e.g. anyone can create an offer for any merchant
 - Offers are currently just stored in memory so are lost when the server stops