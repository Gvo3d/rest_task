docker run --name rest -p 5433:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -d postgres:9.6
java -jar ROOT.jar