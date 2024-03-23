To start **song-service**:

```
docker run --name metadata_db -e POSTGRES_PASSWORD=root -e POSTGRES_DB=metadata -p 5434:5432 -d postgres
gradlew song-service:bootRun
```

To start **resource-service**:

```
docker run --name resources_db -e POSTGRES_PASSWORD=root -e POSTGRES_DB=resources -p 5433:5432 -d postgres
gradlew resource-service:bootRun
```

You can also start application, using **docker-compose.yml** file - run command

```
docker-compose up
```

The next ports will be exposed if you use **docker-compose.yml** file:
8080 - for resource-service
5433 - to have opportunity to get access to resource-service db
5434 - to have opportunity to get access to song-service db
If you use the approach with separate launch then the port 7000 also will be used for song-service.
