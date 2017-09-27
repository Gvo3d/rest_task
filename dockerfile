FROM anapsix/alpine-java
MAINTAINER YakimovDenis
EXPOSE 8080
COPY ./target/ROOT.jar /home/ROOT.jar
RUN java -jar /home/ROOT.jar
ENTRYPOINT ["java","-jar","/home/ROOT.jar"]