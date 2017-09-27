FROM anapsix/alpine-java
MAINTAINER YakimovDenis
EXPOSE 8080
COPY ./ROOT.jar /home/ROOT.jar
CMD ["java","-jar","/home/ROOT.jar"]
ENTRYPOINT ["java","-jar","/home/ROOT.jar"]