FROM amazoncorretto:11
COPY target/hotel-book-reprice-pad.jar hotel-book-reprice-pad.jar
ENTRYPOINT ["java","-jar","/hotel-book-reprice-pad.jar"]
