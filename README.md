# Professional Development Template Application
-----------------------------------------------

This is a template for a web application that uses embedded Jetty. The sample code consists of a static html page, a database version system, a health check servlet and a simple servlet.

## Build Status
[ ![Codeship Status for ramesbury/professional-development](https://www.codeship.io/projects/3fbb3e90-26f2-0132-ef65-12761ece8b0c/status)](https://www.codeship.io/projects/37595)

## Environmental Variables

The environmental variable "database.password" needs to be set.

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
    
