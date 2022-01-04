# Next Action Backend
Steps to setup the infrastructure for this service:
* Run docker-compose:
```shell
docker-compose -f docker-compose.yml up
```
* Run script to create DynamoDB tables:
```shell
./create-tables.sh
```