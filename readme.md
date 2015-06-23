This project aims to learn how to use gatling as a stress tool. The system under test is a tiny Springboot
web application.

This application is an hotel reservation website.

Requirements
============

* Maven 3.2.5 => Use internet connection
* JDK 1.8.0u45
* Scala 2.11.6
* A cool IDE supporting scala and java (Intellij IDEA, Eclipse)

Steps
=====

1. Checkout the project and make it build in your favorite IDE.
2. Launch the Springboot application SampleDataJpaApplication#main
3. Check it works
http://localhost:8080/does_it_work.html

Simulation
==========

An example scenario of a user hotel reservation

1. Find a hotel in atlanta  
GET http://localhost:8080/country/usa/city/atlanta/hotels

2. Look details for Doubletree hotel in Atlanta  
GET http://localhost:8080/country/usa/city/atlanta/hotel/Doubletree

3. Have a look at hotel reviews  
GET http://localhost:8080/country/usa/city/atlanta/hotel/Doubletree/reviews

Weeks later...

4. Post review to this hotel  
POST http://localhost:8080/country/usa/city/atlanta/hotel/Doubletree/reviews

{"rating": "EXCELLENT",
            "checkInDate": "2006-01-12",
            "tripType": "FRIENDS",
            "title": "GREAT",
            "details": "GuENIAL"
}



