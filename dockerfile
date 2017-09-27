FROM anapsix/alpine-java
MAINTAINER YakimovDenis
COPY ./target/ROOT.jar /home/ROOT.jar
RUN java -jar /home/ROOT.jar