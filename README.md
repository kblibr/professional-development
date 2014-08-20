# Professional Development Template Application
-----------------------------------------------

This is a template for a web application that uses embedded Jetty. The sample code consists of a static html page, a database version system, a health check servlet and a simple servlet.

## Running the application locally

First, from the root of the project, build the project with:

    $mvn clean install

Second, from the root of the project, run the project with:

    $java -cp application/target/classes:application/target/lib/* -Ddatabase.password=<password> org.familysearch.professional.development.Main

Third, from the acceptance-tests directory, run the acceptance tests with:

    $mvn -Prun-acceptance-tests -Plocalhost verify
    
## Todo:

* Fix Rolling file appender
* Exec maven plugin
* Investigate path parameters
* Unmarshal response in acceptance tests
* Improve logging
* Gradle
    
