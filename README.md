# Next Action Backend
Steps to setup the infrastructure for this service:
* Run docker-compose:
```shell
docker-compose -f docker-compose.yml up
```
* Run script to create DynamoDB tables (replace the endpoint-url value with your own Docker IP)
```shell
./create-tables.sh
```
* Replace the dynamo.endpoint value in the `application.yml` file. My docker is configured in a different way, your docker endpoint will probably be `localhost` instead of a fixed IP.