FROM amazoncorretto:11

WORKDIR /opt/app

COPY target/hotel-book-reprice-pad.jar /opt/app/hotel-book-reprice-pad.jar

ENTRYPOINT ["/usr/bin/java"]
CMD ["-Dspring.profiles.active=docker", "-Dorg.apache.catalina.STRICT_SERVLET_COMPLIANCE=true", "-jar", "/opt/app/hotel-book-reprice-pad.jar"]

EXPOSE 8080