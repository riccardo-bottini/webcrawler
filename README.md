# WebCrawler

## Description
Crawls every URL starting from the input link within the same domain.

## Requirements
* JDK 17
* Maven 3.5.2

## Installation and how to run
In the webcrawler directory:
```
mvn clean install

java -jar target/webcrawler-1.0-SNAPSHOT-jar-with-dependencies.jar <your URL>
```
The output is stored in target/output.txt (or in your path if you start the jar from another directory)