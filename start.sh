docker run --name rest --network=restnet --network-alias restgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -d postgres:9.6
java -jar ROOT.jar