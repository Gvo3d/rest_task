docker run --name rest -p 5432:5432 -e POSTGRES_USER=rest -e POSTGRES_PASSWORD=root -d postgres:9.6
java -jar ROOT.jar