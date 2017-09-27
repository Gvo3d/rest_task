#!/usr/bin/env bash
docker run --name rest -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -d postgres:9.6