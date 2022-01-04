# Table USER
aws dynamodb create-table \
   --table-name User \
   --attribute-definitions AttributeName=email,AttributeType=S \
   --key-schema AttributeName=email,KeyType=HASH \
   --provisioned-throughput ReadCapacityUnits=3,WriteCapacityUnits=3 \
   --endpoint-url http://192.168.33.100:4569

# Table NEXT ACTION
aws dynamodb create-table \
   --table-name NextAction \
   --attribute-definitions AttributeName=userId,AttributeType=S AttributeName=taskId,AttributeType=S \
   --key-schema AttributeName=userId,KeyType=HASH AttributeName=taskId,KeyType=RANGE \
   --provisioned-throughput ReadCapacityUnits=3,WriteCapacityUnits=3 \
   --endpoint-url http://192.168.33.100:4569